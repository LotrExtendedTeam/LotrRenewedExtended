package com.github.maximuslotro.lotrextended.mixins.lotr.common.block;

import java.util.Optional;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.github.maximuslotro.lotrrextended.common.data.ExtendedLevelData;
import com.github.maximuslotro.lotrrextended.common.map.conquest.ExtendedConquestRegion;

import lotr.common.block.FactionCraftingTableBlock;
import lotr.common.data.LOTRLevelData;
import lotr.common.data.LOTRPlayerData;
import lotr.common.fac.Faction;
import lotr.common.fac.FactionPointer;
import net.minecraft.entity.player.PlayerEntity;

@Mixin(FactionCraftingTableBlock.class)
public class MixinFactionCraftingTableBlock {
	@Final
	@Shadow(remap=false)
	private FactionPointer tableFac;

	@Inject(method = "hasRequiredAligment(Lnet/minecraft/entity/player/PlayerEntity;)Z", at = @At("HEAD"), cancellable = true, remap=false)
	private void hasRequiredAligment$Extended(PlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
		final Optional<Faction> optFaction = this.tableFac.resolveFaction(player.level);
		if(!player.level.isClientSide() && optFaction.isPresent()) {
			LOTRPlayerData playerData = LOTRLevelData.getSidedData(player);
			Faction pedegeFaction = playerData.getAlignmentData().getPledgeFaction();
			if(pedegeFaction!=null && optFaction.get().isBadRelation(pedegeFaction)) {
				ExtendedConquestRegion region = ExtendedLevelData.serverInstance().getConquestData().getConquestSquare(player.blockPosition());
				if(region.getConquest(pedegeFaction.getName())>=500) {
					cir.setReturnValue(true);
				};
			}
		}
	}
}
