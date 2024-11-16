package com.github.maximuslotro.lotrrextended.mixins.lotr.common.entity.npc.ai.goal;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import lotr.common.data.LOTRLevelData;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.entity.npc.ai.goal.NPCNearestAttackableTargetGoal;
import lotr.common.fac.AlignmentPredicates;
import net.minecraft.entity.player.PlayerEntity;

@Mixin(NPCNearestAttackableTargetGoal.class)
public class MixinNPCNearestAttackableTargetGoal {

	@Shadow(remap=false)
	@Final
	private NPCEntity theNPC;
	
	@Overwrite(remap=false)
	/**
	 * @author maximuslotro
	 * @reason testing
	 * */
	protected boolean isPlayerSuitableAlignmentTarget(PlayerEntity player) {
		boolean result = LOTRLevelData.getSidedData(player).getAlignmentData().hasAlignment(theNPC.getFaction(), AlignmentPredicates.NEGATIVE);
		System.out.println("Entity targeting player result: "+result);
		return result;
	}
}
