package com.github.maximuslotro.lotrrextended.common.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class ExtendedMultiSphereFeature extends Feature<ExtendedMultiSphereConfig> {
    public ExtendedMultiSphereFeature(Codec<ExtendedMultiSphereConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(ISeedReader worldgenlevel, ChunkGenerator chunkGenerator, Random random, BlockPos blockpos, ExtendedMultiSphereConfig config) {
        boolean flag = false;
        if (worldgenlevel.getFluidState(blockpos).is(FluidTags.WATER)) {
            int r = config.radius.sample(random);
            int h = 1 + random.nextInt(config.halfHeight);

            //centered at blockpos, try to fill a cylinder with radius r and height 2h
            for(int j = blockpos.getX() - r; j <= blockpos.getX() + r; j++) {
                for(int l = blockpos.getZ() - r; l <= blockpos.getZ() + r; l++) {
                    int x = j - blockpos.getX();
                    int y = l - blockpos.getZ();
                    if (x * x + y * y <= r * r) {
                        for(int k = blockpos.getY() - h; k <= blockpos.getY() + h; k++) {
                            BlockPos check = new BlockPos(j, k, l);
                            Block block = worldgenlevel.getBlockState(check).getBlock();

                            for(BlockState blockstate : config.targets) {
                                if (blockstate.is(block)) {
                                    int choice = random.nextInt(config.states.size());
                                    worldgenlevel.setBlock(check, config.states.get(choice), 2);
                                    flag = true;
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        return flag;
    }
}
