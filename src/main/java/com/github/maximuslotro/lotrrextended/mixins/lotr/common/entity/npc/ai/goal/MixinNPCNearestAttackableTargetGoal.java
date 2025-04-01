package com.github.maximuslotro.lotrrextended.mixins.lotr.common.entity.npc.ai.goal;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import lotr.common.data.LOTRLevelData;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.entity.npc.ai.goal.NPCNearestAttackableTargetGoal;
import lotr.common.fac.AlignmentPredicates;
import lotr.common.fac.FactionPointers;
import net.minecraft.entity.player.PlayerEntity;

/**
 * @reason Fix hostile aligned mobs not attacking the player
 * @Mixin(NPCNearestAttackableTargetGoal.class)
 * */
@Mixin(NPCNearestAttackableTargetGoal.class)
public class MixinNPCNearestAttackableTargetGoal {

	/**
	 * @reason Need to get the NPCEntity instance of this target goal
	 * @Shadow (remap=false)
	 * @Final Mark the field to be essentially final
	 * */
	@Shadow(remap=false)
	@Final
	private NPCEntity theNPC;

	/**
	 * @author maximuslotro
	 * @reason Fix hostile aligned mobs not attacking players
	 * @param player The PlayerEntity being targeted
	 * @return True if the player can be targeted
	 */
	@Overwrite(remap=false)
	protected boolean isPlayerSuitableAlignmentTarget(PlayerEntity player) {
		// Extended Start
		if(FactionPointers.HOSTILE.matches(theNPC.getFaction())) {
			return true;
		}
		// Extended End
		return LOTRLevelData.getSidedData(player).getAlignmentData().hasAlignment(theNPC.getFaction(), AlignmentPredicates.NEGATIVE);
	}
}
