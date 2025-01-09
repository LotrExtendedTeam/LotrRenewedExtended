package com.github.maximuslotro.lotrrextended.common.world.gen.feature;

import java.util.Random;

import com.mojang.serialization.Codec;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

public class ExtendedHolyUnderwaterSphereFeature extends Feature<ExtendedHoleySphereConfig> {
    public ExtendedHolyUnderwaterSphereFeature(Codec<ExtendedHoleySphereConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(ISeedReader worldgenlevel, ChunkGenerator chunkGenerator, Random random, BlockPos blockpos, ExtendedHoleySphereConfig config) {
        boolean flag = false;
        if (worldgenlevel.getFluidState(blockpos).is(FluidTags.WATER)) {
            int r = config.radius.sample(random);
            int h = 1 + random.nextInt(config.halfHeight);

            //centered at blockpos, try to fill a sphere with radius r and height h
            for(int i = blockpos.getX() - r; i <= blockpos.getX() + r; i++) {
                for(int k = blockpos.getZ() - r; k <= blockpos.getZ() + r; k++) {
                    int xFromCenter = i - blockpos.getX();
                    int zFromCenter = k - blockpos.getZ();
                    // ensure in cylinder
                    if (xFromCenter * xFromCenter + zFromCenter * zFromCenter <= r * r) {
                        for(int j = blockpos.getY() - h; j <= blockpos.getY() + h; j++) {
//                        	int dx = i - blockpos.getX();
//                        	int dy = j - blockpos.getY();
//                        	int dz = k - blockpos.getZ();
//                        	int distanceCubeSquared = dx * dx + dy * dy + dz * dz;
//                        	// Compare squared distance with squared radius in sphere
//                        	if (distanceCubeSquared > r * r) {
//                        	    continue;
//                        	}
                            BlockPos check = new BlockPos(i, j, k);
                            Block block = worldgenlevel.getBlockState(check).getBlock();

                            for(BlockState blockstate : config.targets) {
                                if (blockstate.is(block)) {
                                	// Discard block if given chance
                                	//int discardChance = Math.min(distanceCubeSquared * config.discardChance, 100);
                                	if(random.nextInt(100)>config.discardChance) {
                                		break;
                                	}
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
