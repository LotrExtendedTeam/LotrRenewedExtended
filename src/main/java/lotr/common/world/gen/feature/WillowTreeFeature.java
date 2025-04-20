package lotr.common.world.gen.feature;

import com.mojang.serialization.Codec;
import lotr.common.init.ExtendedBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WillowTreeFeature extends Feature<NoFeatureConfig> implements IFeatureConfig {
    public WillowTreeFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    private int minHeight = 8;
    private int maxHeight = 13;

    private boolean needsWater = true;

    //private BlockState woodBlock = ExtendedBlocks.WILLOW_LOG.get().defaultBlockState();

    @Override
    public boolean place(ISeedReader world, ChunkGenerator chunkGenerator, Random random, BlockPos pos, NoFeatureConfig config) {
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
                            if (!isReplaceable(world, new BlockPos(x, y, z), true))
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
        // TODO: Re-enable this check when done with testing
        /*
        boolean isSoil = below.canSustainPlant(world, pos.below(), Direction.UP, (LOTRSaplingBlock) ExtendedBlocks.WILLOW_SAPLING.get());
        if (!isSoil) canGrow = false;
        if (this.needsWater) {
            boolean water = false;
            int attempts = 4;
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
        */

        if (!canGrow) return false;

        // notify block below
        // TODO: Maybe look into making sure grow plant event works here eventually
        below.onNeighborChange(world, pos.below(), pos);
        List<BlockPos> vineGrows = new ArrayList<BlockPos>();

        // generate branches?
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
                this.setBlock(world, new BlockPos(m, n, k1), ExtendedBlocks.WILLOW_LOG.get().defaultBlockState());
            }
            spawnLeafCluster(world, random, new BlockPos(m, n, k1));
            vineGrows.add(new BlockPos(m, n, k1));
        }

        // generate trunk?
        for (int yOffset = 0; yOffset < treeHeight; yOffset++) {
            BlockPos currentPos = new BlockPos(pos.getX(), pos.getY() + yOffset, pos.getZ());
            setBlock(world, currentPos, ExtendedBlocks.WILLOW_LOG.get().defaultBlockState());
            if (yOffset == treeHeight - 1) {
                spawnLeafCluster(world, random, currentPos);
                vineGrows.add(currentPos);
            }
        }
        for (int i1 = pos.getX() - 1; i1 <= pos.getX() + 1; i1++) {
            for (int k1 = pos.getZ() - 1; k1 <= pos.getZ() + 1; k1++) {
                int i2 = i1 - pos.getX();
                int k2 = k1 - pos.getZ();
                if (Math.abs(i2) != Math.abs(k2)) {
                    int rootX = i1;
                    int rootY = pos.getY() + 1 + random.nextInt(2);
                    int rootZ = k1;
                    int roots = 0;

                    while (isReplaceable(world, new BlockPos(rootX, rootY, k1), true)) {
                        setBlock(world, new BlockPos(rootX, rootY, rootZ), ExtendedBlocks.WILLOW_LOG.get().defaultBlockState());
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
        for (int i1 = pos.getX() - leafRange; i1 <= pos.getX() + leafRange; i1++) {
            for (int j1 = pos.getY() - leafRange; j1 <= pos.getY() + leafRange; j1++) {
                for (int k1 = pos.getZ() - leafRange; k1 <= pos.getZ() + leafRange; k1++) {
                    int i2 = i1 - pos.getX();
                    int j2 = j1 - pos.getY();
                    int k2 = k1 - pos.getZ();
                    int dist = i2 * i2 + j2 * j2 + k2 * k2;
                    int taxicab = Math.abs(i2) + Math.abs(j2) + Math.abs(k2);
                    if ((dist < leafRangeSqLess || (dist < leafRangeSq && random.nextInt(3) == 0)) && taxicab <= 4) {
                        BlockPos cPos = new BlockPos(i1, j1, k1);
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

        for (int i1 = pos.getX() - leafRange; i1 <= pos.getX() + leafRange; i1++) {
            for (int j1 = pos.getY() - leafRange; j1 <= pos.getY() + leafRange; j1++) {
                for (int k1 = pos.getZ() - leafRange; k1 <= pos.getZ() + leafRange; k1++) {
                    int i2 = i1 - pos.getX();
                    int j2 = j1 - pos.getY();
                    int k2 = k1 - pos.getZ();
                    int dist = i2 * i2 + j2 * j2 + k2 * k2;
                    if (dist < leafRangeSq) {
                        BlockPos cPos = new BlockPos(i1, j1, k1);
                        BlockState block = world.getBlockState(cPos);

                        if (block.getBlock() == ExtendedBlocks.WILLOW_LEAVES.get()) {
                            int vineChance = 2;

                            BlockPos pos1 = new BlockPos(i1 - 1, j1, k1);
                            BlockState state1 = world.getBlockState(pos1);
                            if (random.nextInt(vineChance) == 0 && state1.getBlock().isAir(state1, world, pos1))
                                growVines(world, random, pos1);

                            BlockPos pos2 = new BlockPos(i1 + 1, j1, k1);
                            BlockState state2 = world.getBlockState(pos2);
                            if (random.nextInt(vineChance) == 0 && state2.getBlock().isAir(state2, world, pos2))
                                growVines(world, random, pos2);

                            BlockPos pos3 = new BlockPos(i1, j1, k1 - 1);
                            BlockState state3 = world.getBlockState(pos3);
                            if (random.nextInt(vineChance) == 0 && state3.getBlock().isAir(state3, world, pos3))
                                growVines(world, random, pos3);

                            BlockPos pos4 = new BlockPos(i1, j1, k1 + 1);
                            BlockState state4 = world.getBlockState(pos4);
                            if (random.nextInt(vineChance) == 0 && state4.getBlock().isAir(state4, world, pos4))
                                growVines(world, random, pos4);
                        }
                    }
                }
            }
        }
    }

    private void growVines(ISeedReader world, Random random, BlockPos pos) {
        int y = pos.getY();
        setBlock(world, pos, ExtendedBlocks.WILLOW_VINES.get().defaultBlockState());

        int vines = 0;
        while (true) {
            y--;
            BlockPos cPos = new BlockPos(pos.getX(), y, pos.getZ());
            BlockState cState = world.getBlockState(cPos);

            // stop growing when we hit something or they exceed a random length
            if (!cState.getBlock().isAir(cState, world, cPos) || vines >= 2 + random.nextInt(4)) return;

            setBlock(world, cPos, ExtendedBlocks.WILLOW_VINES.get().defaultBlockState());
            vines++;
        }
    }

    private int getIntInRange(Random random, int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }

    private static boolean isReplaceable(IWorld pLevel, BlockPos pPos, boolean pReplacePlants) {
        return pLevel.isStateAtPosition(pPos, (p_236320_1_) -> {
            Material material = p_236320_1_.getMaterial();
            return p_236320_1_.getMaterial().isReplaceable() || pReplacePlants && material == Material.PLANT;
        });
    }


}
