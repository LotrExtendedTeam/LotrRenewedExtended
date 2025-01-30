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

@Mixin(LOTRPlayerData.class)
public class MixinLOTRPlayerData implements ExtendedLOTRPlayerData{

	private ExtendedFellowshipDataModule fellowshipData;

	private ExtendedMiscDataModule extendedMiscData;

	@Shadow(remap=false)
	private <M extends PlayerDataModule> M addModule(String code, Function<LOTRPlayerData, M> moduleConstructor) {
		return null;
	}

	@Inject(method = "<init>(Llotr/common/data/LOTRLevelData;Ljava/util/UUID;)V", at = @At("TAIL"), remap=false)
	public <M extends PlayerDataModule> void constructor(LOTRLevelData level, UUID player, CallbackInfo info) {
		fellowshipData = this.addModule("Fellowships", ExtendedFellowshipDataModule::new);
		extendedMiscData = this.addModule("ExtendedMisc", ExtendedMiscDataModule::new);
	}

	@Override
	public ExtendedFellowshipDataModule getFellowshipData() {
		return fellowshipData;
	}

	@Override
	public ExtendedMiscDataModule getExtendedMiscData() {
		return extendedMiscData;
	}
}
