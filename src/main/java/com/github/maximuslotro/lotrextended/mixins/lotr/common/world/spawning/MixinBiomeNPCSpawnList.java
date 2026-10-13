package com.github.maximuslotro.lotrextended.mixins.lotr.common.world.spawning;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;
import com.github.maximuslotro.lotrrextended.common.data.ExtendedLevelData;

import lotr.common.world.spawning.BiomeNPCSpawnList;
import lotr.common.init.LOTRDimensions;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;


@Mixin(BiomeNPCSpawnList.class)
public abstract class MixinBiomeNPCSpawnList {
    @ModifyExpressionValue(method = "getRandomSpawnEntry", at = @At(value = "CONSTANT", args = "floatValue=0.0"), remap = false)
    private float getConquestForSpawning$Extended(float original, Random rand, World world, BlockPos pos, @Local BiomeNPCSpawnList.FactionContainer cont) {
        if (!(world instanceof ServerWorld) || world.dimension() != LOTRDimensions.MIDDLE_EARTH_WORLD_KEY || !ExtendedServerConfig.enableConquest.get()) return original;
        ExtendedLevelData levelData = ExtendedLevelData.serverInstance();
        if (levelData.needsLoad()) return original;
        float conquest = levelData.getConquestData().getConquestSquare(pos).getConquest(cont.getFaction().getName());
        return levelData.getConquestData().getConquestSquare(pos).getConquest(cont.getFaction().getName());
    }
}