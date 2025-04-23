package com.github.maximuslotro.lotrrextended.common.block;

import lotr.common.block.LOTRSaplingBlock;
import lotr.common.world.biome.ExtendedBiomeFeatures;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.trees.Tree;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class ExtendedWillowTreeSapling extends LOTRSaplingBlock {

    public ExtendedWillowTreeSapling(Tree tree) {
        super(tree);
    }

    public void advanceTree(ServerWorld pLevel, BlockPos pPos, BlockState pState, Random pRand) {
        if (pState.getValue(STAGE) == 0) {
            pLevel.setBlock(pPos, pState.cycle(STAGE), 4);
        } else {
            if (!net.minecraftforge.event.ForgeEventFactory.saplingGrowTree(pLevel, pRand, pPos)) return;
            growTree(pLevel, pLevel.getChunkSource().getGenerator(), pPos, pState, pRand);
        }
    }

    public boolean growTree(ServerWorld pLevel, ChunkGenerator pChunkGenerator, BlockPos pPos, BlockState pState, Random pRand) {
        ConfiguredFeature<NoFeatureConfig, ?> feature = ExtendedBiomeFeatures.willowTreeNoWaterRequirement();
        if (feature == null) {
            return false;
        } else {
            pLevel.setBlock(pPos, Blocks.AIR.defaultBlockState(), 4);
            if (feature.place(pLevel, pChunkGenerator, pRand, pPos)) {
                return true;
            } else {
                pLevel.setBlock(pPos, pState, 4);
                return false;
            }
        }
    }
}
