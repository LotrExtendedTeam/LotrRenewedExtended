package com.github.maximuslotro.lotrextended.mixins.lotr.common.entity.npc.ai.goal;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import lotr.common.data.LOTRLevelData;
import lotr.common.entity.npc.ExtendedBanditEntity;
import lotr.common.entity.npc.ExtendedINPCEntity;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.entity.npc.ai.goal.NPCNearestAttackableTargetGoal;
import lotr.common.fac.AlignmentPredicates;
import lotr.common.fac.FactionPointers;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

/**
 * Mixin applied to {@link NPCNearestAttackableTargetGoal} to modify the targeting behavior of NPCs.
 * <p>
 * Ensures that hostile-aligned NPCs correctly target players, even when vanilla alignment logic fails.
 * Also causes hired units to target bandits
 * </p>
 *
 * @reason Fixes an issue where hostile-aligned mobs would not attack players as intended.
 * @Mixin (NPCNearestAttackableTargetGoal.class)
 */
@Mixin(NPCNearestAttackableTargetGoal.class)
public class MixinNPCNearestAttackableTargetGoal {

	/**
	 * Reference to the owning {@link NPCEntity} of this goal.
	 * <p>
	 * This field is marked {@code final} and shadowed from the original class for use in the mixin.
	 * </p>
	 *
	 * @see NPCEntity
	 * @Shadow (remap=false)
	 * @Final Mark the field to be essentially final
	 */
	@Shadow(remap=false)
	@Final
	private NPCEntity theNPC;

	/**
	 * Make hired units target bandits
	 * 
	 * @author maximuslotro
	 * @reason Make hired units target bandits
	 * @Overwrite (remap=false)
	 * @param entity The {@link LivingEntity} being evaluated as a target.
	 * @return {@code true} if the entity should be targeted
	 */
	@Overwrite(remap=false)
	protected boolean canNPCTarget(LivingEntity entity) {
		if (entity != theNPC.getVehicle() && !theNPC.hasPassenger(entity)) {
			if(entity instanceof PlayerEntity) {
				return isPlayerSuitableAlignmentTarget((PlayerEntity) entity);
			}
			// Extended Start
			if(entity instanceof ExtendedBanditEntity) {
				return ((ExtendedINPCEntity)theNPC).getUnitInfo().isNpcActiveDuty() && ((ExtendedINPCEntity)theNPC).getUnitInfo().getOrders().doesOrderAllowsCombat();
			}
			// Extended End
			return true;
		}
		return false;
	}
	
	/**
	 * Overwrites the method that determines if a player is a suitable alignment target.
	 * <p>
	 * Allows hostile factions to always target players, bypassing the default alignment check.
	 * </p>
	 * 
	 * @author maximuslotro
	 * @reason Fix hostile aligned mobs not attacking players
	 * @Overwrite (remap=false)
	 * @param player The {@link PlayerEntity} being evaluated as a target.
	 * @return {@code true} if the player should be targeted based on faction hostility or alignment data.
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
