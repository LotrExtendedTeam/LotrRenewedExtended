package com.github.maximuslotro.lotroExtended.mixins.lotr.common.data;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import lotr.common.data.FastTravelDataModule;
import lotr.common.world.map.Waypoint;
import net.minecraft.entity.player.ServerPlayerEntity;

@Mixin(FastTravelDataModule.class)
public abstract class MixinFastTravelDataModule {

	/**
	 * @author maximuslotro
	 * @reason because injecting into a code tree is hard
	 * */
    @Overwrite(remap = false)
    private void fastTravelTo(ServerPlayerEntity player, Waypoint waypoint) {}
}
