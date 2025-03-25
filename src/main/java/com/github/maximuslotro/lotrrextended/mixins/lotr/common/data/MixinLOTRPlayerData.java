package com.github.maximuslotro.lotrrextended.mixins.lotr.common.data;

import java.util.UUID;
import java.util.function.Function;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import lotr.common.data.ExtendedFellowshipDataModule;
import lotr.common.data.ExtendedLOTRPlayerData;
import lotr.common.data.ExtendedMiscDataModule;
import lotr.common.data.LOTRLevelData;
import lotr.common.data.LOTRPlayerData;
import lotr.common.data.PlayerDataModule;

/**
 * @reason To add additional information modules to the player module
 * @Mixin (LOTRPlayerData.class)
 * */
@Mixin(LOTRPlayerData.class)
public class MixinLOTRPlayerData implements ExtendedLOTRPlayerData{

	/**
	 * Local instance of ExtendedFellowshipDataModule for this player
	 * */
	private ExtendedFellowshipDataModule fellowshipData;

	/**
	 * Local instance of ExtendedMiscDataModule for this player
	 * */
	private ExtendedMiscDataModule extendedMiscData;

	/**
	 * @reason Used to add new PlayerDataModule to this LOTRPlayerData instance
	 * @Shadow (remap=false)
	 * @param code The ID of this module
	 * @param moduleConstructor A functional constructor for making the new PlayerDataModule
	 * @return A new PlayerDataModule
	 * */
	@Shadow(remap=false)
	private <M extends PlayerDataModule> M addModule(String code, Function<LOTRPlayerData, M> moduleConstructor) {
		return null;
	}

	/**
	 * @reason Adds a new ExtendedFellowshipDataModule and ExtendedMiscDataModule to this LOTRPlayerData instance
	 * @Inject (method = "<init>(Llotr/common/data/LOTRLevelData;Ljava/util/UUID;)V", at = @At("TAIL"), remap=false)
	 * @param level The parent LOTRLevelData of this LOTRPlayerData
	 * @param player The UUID of this LOTRPlayerData
	 * @param info The local CallbackInfo instance for this Inject
	 * */
	@Inject(method = "<init>(Llotr/common/data/LOTRLevelData;Ljava/util/UUID;)V", at = @At("TAIL"), remap=false)
	public <M extends PlayerDataModule> void constructor(LOTRLevelData level, UUID player, CallbackInfo info) {
		fellowshipData = this.addModule("Fellowships", ExtendedFellowshipDataModule::new);
		extendedMiscData = this.addModule("ExtendedMisc", ExtendedMiscDataModule::new);
	}

	/**
	 * Gets the ExtendedFellowshipDataModule from this class on runtime via ExtendedLOTRPlayerData
	 * @return The ExtendedFellowshipDataModule of this LOTRPlayerData instance
	 * */
	@Override
	public ExtendedFellowshipDataModule getFellowshipData() {
		return fellowshipData;
	}

	/**
	 * Gets the ExtendedMiscDataModule from this class on runtime via ExtendedLOTRPlayerData
	 * @return The ExtendedMiscDataModule of this LOTRPlayerData instance
	 * */
	@Override
	public ExtendedMiscDataModule getExtendedMiscData() {
		return extendedMiscData;
	}
}
