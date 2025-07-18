package lotr.common.world.gen.feature;

import com.mojang.serialization.Codec;
import lotr.common.block.LOTRSaplingBlock;
import lotr.common.init.ExtendedBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.VineBlock;
import net.minecraft.block.material.Material;
import net.minecraft.state.BooleanProperty;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldWriter;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WillowTreeFeature extends Feature<NoFeatureConfig> implements IFeatureConfig {
    private boolean needsWater;
    private int minHeight = 8;
    private int maxHeight = 13;

    public WillowTreeFeature(Codec<NoFeatureConfig> codec, boolean needsWater) {
        super(codec);
        this.needsWater = needsWater;
    }

    @Override
    public boolean place(ISeedReader world, ChunkGenerator chunkGenerator, Random random, BlockPos pos, NoFeatureConfig config) {
        BlockState woodBlock = ExtendedBlocks.WILLOW_LOG.get().defaultBlockState();

        int treeHeight = getIntInRange(random, this.minHeight, this.maxHeight);
        boolean canGrow = true;

        // determine if there is space for tree ad blocks are replaceable
        if (pos.getY() >= 1 && treeHeight + 1 <= 256) {
            for (int y = pos.getY(); y <= pos.getY() + treeHeight + 1; y++) {
                int range = 1;
                if (y == pos.getY())
                    range = 0;
                if (y >= pos.getY() + treeHeight - 1)
                    range = 2;
                for (int x = pos.getX() - range; x <= pos.getX() + range && canGrow; x++) {
                    for (int z = pos.getZ() - range; z <= pos.getZ() + range && canGrow; z++) {
                        if (y >= 0 && y < 256) {
                            if (!isReplaceable(world, new BlockPos(x, y, z)))
                                canGrow = false;
                        } else {
                            canGrow = false;
                        }
                    }
                }
            }
        } else {
            canGrow = false;
        }

        BlockState below = world.getBlockState(pos.below());
        // determine if it is close enough to water
        boolean isSoil = below.canSustainPlant(world, pos.below(), Direction.UP, (LOTRSaplingBlock) ExtendedBlocks.WILLOW_SAPLING.get());
        if (!isSoil) canGrow = false;

        if (this.needsWater) {
            boolean water = false;
            int attempts = 6;
            for (int l = 0; l < attempts; l++) {
                int x = pos.getX() + getIntInRange(random, -12, 12);
                int y = pos.getY() + getIntInRange(random, -8, 4);
                int z = pos.getZ() + getIntInRange(random, -12, 12);
                if (world.getBlockState(new BlockPos(x, y, z)).getMaterial() == Material.WATER) {
                    water = true;
                    break;
                }
            }
            if (!water)
                return false;
        }

        if (!canGrow) return false;

        // notify block below
        // TODO: Maybe look into making sure grow plant event works here eventually
        below.onNeighborChange(world, pos.below(), pos);
        List<BlockPos> vineGrows = new ArrayList<>();

        // generate branches
        int angle = 0;
        while (angle < 360) {
            angle += 30 + random.nextInt(30);
            float angleR = (float)Math.toRadians(angle);
            float sin = MathHelper.sin(angleR);
            float cos = MathHelper.cos(angleR);
            int base = pos.getY() + treeHeight - 3 - random.nextInt(3);
            int length = 2 + random.nextInt(4);
            int m = pos.getX();
            int n = base;
            int k1 = pos.getZ();
            for (int l = 0; l < length; l++) {
                if (l > 0)
                    if (l % 4 == 0 || random.nextInt(3) == 0)
                        n++;
                if (random.nextFloat() < Math.abs(cos))
                    m = (int)(m + Math.signum(cos));
                if (random.nextFloat() < Math.abs(sin))
                    k1 = (int)(k1 + Math.signum(sin));
                this.setBlock(world, new BlockPos(m, n, k1), woodBlock);
            }
            spawnLeafCluster(world, random, new BlockPos(m, n, k1));
            vineGrows.add(new BlockPos(m, n, k1));
        }

        // generate trunk
        for (int yOffset = 0; yOffset < treeHeight; yOffset++) {
            BlockPos currentPos = new BlockPos(pos.getX(), pos.getY() + yOffset, pos.getZ());
            setBlock(world, currentPos, woodBlock);
            if (yOffset == treeHeight - 1) {
                spawnLeafCluster(world, random, currentPos);
                vineGrows.add(currentPos);
            }
        }

        // generate roots
        for (int x = pos.getX() - 1; x <= pos.getX() + 1; x++) {
            for (int z = pos.getZ() - 1; z <= pos.getZ() + 1; z++) {
                int dx = x - pos.getX();
                int dz = z - pos.getZ();
                if (Math.abs(dx) != Math.abs(dz)) {
                    int rootX = x;
                    int rootY = pos.getY() + 1 + random.nextInt(2);
                    int rootZ = z;
                    int roots = 0;

                    while (isReplaceable(world, new BlockPos(rootX, rootY, z))) {
                        setBlock(world, new BlockPos(rootX, rootY, rootZ), woodBlock);
                        // TODO: Add plant grow event back at some point?
                        //world.func_147439_a(rootX, rootY - 1, rootZ).onPlantGrow(world, rootX, rootY - 1, rootZ, rootX, rootY, rootZ);

                        rootY--;
                        roots++;
                        if (roots > 4 + random.nextInt(3)) break;
                    }
                }
            }
        }
        vineGrows.forEach(position -> spawnVineCluster(world, random, position));

        return true;
    }

    private void spawnLeafCluster(ISeedReader world, Random random, BlockPos pos) {
        int leafRange = 3;
        int leafRangeSq = leafRange * leafRange;
        int leafRangeSqLess = (int)((leafRange - 0.5D) * (leafRange - 0.5D));

        for (int x = pos.getX() - leafRange; x <= pos.getX() + leafRange; x++) {
            for (int y = pos.getY() - leafRange; y <= pos.getY() + leafRange; y++) {
                for (int z = pos.getZ() - leafRange; z <= pos.getZ() + leafRange; z++) {
                    int dx = x - pos.getX();
                    int dy = y - pos.getY();
                    int dz = z - pos.getZ();
                    int dist = dx * dx + dy * dy + dz * dz;
                    int taxicab = Math.abs(dx) + Math.abs(dy) + Math.abs(dz);
                    if ((dist < leafRangeSqLess || (dist < leafRangeSq && random.nextInt(3) == 0)) && taxicab <= 4) {
                        BlockPos cPos = new BlockPos(x, y, z);
                        BlockState block = world.getBlockState(cPos);
                        if (block.getMaterial().isReplaceable() || block.getMaterial() == Material.PLANT)
                            setBlock(world, cPos, ExtendedBlocks.WILLOW_LEAVES.get().defaultBlockState());
                    }
                }
            }
        }
    }

    private void spawnVineCluster(ISeedReader world, Random random, BlockPos pos) {
        int leafRange = 3;
        int leafRangeSq = leafRange * leafRange;

        // loop through all blocks in range in 3d space
        for (int x = pos.getX() - leafRange; x <= pos.getX() + leafRange; x++) {
            for (int y = pos.getY() - leafRange; y <= pos.getY() + leafRange; y++) {
                for (int z = pos.getZ() - leafRange; z <= pos.getZ() + leafRange; z++) {
                    // calculate distance
                    int dx = x - pos.getX();
                    int dy = y - pos.getY();
                    int dz = z - pos.getZ();
                    int dist = dx * dx + dy * dy + dz * dz;

                    // attempt to generate a vine if in range and a willow leaf
                    if (dist < leafRangeSq) {
                        BlockPos cPos = new BlockPos(x, y, z);
                        BlockState block = world.getBlockState(cPos);

                        if (block.getBlock().equals(ExtendedBlocks.WILLOW_LEAVES.get())) {
                            int vineChance = 2;

                            // loop through and handle all facing directions
                            Direction[] directions = {Direction.WEST, Direction.EAST, Direction.NORTH, Direction.SOUTH};
                            for (Direction dir : directions) {
                                BlockPos offsetPos = cPos.relative(dir);
                                BooleanProperty vineProperty = VineBlock.PROPERTY_BY_DIRECTION.get(dir.getOpposite());
                                if (random.nextInt(vineChance) == 0 && Feature.isAir(world, offsetPos)) {
                                    addHangingVine(world, offsetPos, vineProperty);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void addHangingVine(IWorldGenerationReader world, BlockPos pos, BooleanProperty property) {
        this.placeVine(world, pos, property);
        int i = 4;

        for(BlockPos blockpos = pos.below(); Feature.isAir(world, blockpos) && i > 0; --i) {
            this.placeVine(world, blockpos, property);
            blockpos = blockpos.below();
        }
    }

    protected void placeVine(IWorldWriter world, BlockPos pos, BooleanProperty property) {
        this.setBlock(world, pos, ExtendedBlocks.WILLOW_VINES.get().defaultBlockState().setValue(property, Boolean.valueOf(true)));
    }

    private int getIntInRange(Random random, int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }

    private static boolean isReplaceable(IWorld pLevel, BlockPos pPos) {
        final boolean REPLACE_PLANTS = true;
        return pLevel.isStateAtPosition(pPos, (p_236320_1_) -> {
            Material material = p_236320_1_.getMaterial();
            return p_236320_1_.getMaterial().isReplaceable() || REPLACE_PLANTS && material == Material.PLANT;
        });
    }

}
