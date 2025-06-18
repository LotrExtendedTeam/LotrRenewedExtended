package com.github.maximuslotro.lotrrextended.mixins.lotr.common.fac;

import java.util.List;
import java.util.UUID;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import com.google.common.base.Predicates;

import lotr.common.LOTRMod;
import lotr.common.data.ExtendedFellowshipDataModule;
import lotr.common.data.ExtendedLOTRPlayerData;
import lotr.common.data.LOTRLevelData;
import lotr.common.data.LOTRPlayerData;
import lotr.common.data.PlayerMessageType;
import lotr.common.entity.npc.ExtendedNPCEntity;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.fac.AlignmentPredicates;
import lotr.common.fac.EntityFactionHelper;
import lotr.common.fac.Faction;
import lotr.common.fellowship.ExtendedFellowship;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;

/**
 * Add fellowship and hired unit pve logic to Renewed's entity damage checker
 * @Mixin (EntityFactionHelper.class)
 * */
@Mixin(EntityFactionHelper.class)
public class MixinEntityFactionHelper {

//	@Inject(method = "canPlayerCauseDamageToTarget(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/entity/Entity;Z)Z",
//			at =@At("HEAD"),
//			remap=false,
//			cancellable = true)
//	private static void checkFellowshipPvp(PlayerEntity attacker, Entity target, boolean warnFriendlyFire, CallbackInfoReturnable<Boolean> cir) {
//		if (target instanceof PlayerEntity && ((PlayerEntity) target) != attacker) {
//			ExtendedFellowshipDataModule fellowshipModule = ((ExtendedLOTRPlayerData)LOTRLevelData.getSidedData(attacker)).getFellowshipData();
//			List<ExtendedFellowship> fellowships = fellowshipModule.getFellowships();
//	        for (ExtendedFellowship fs : fellowships) {
//	          if (fs.isPreventPVP() && fs.isInFellowship(((PlayerEntity) target).getUUID()))
//	        	  cir.setReturnValue(false);
//	        }
//		}
//	}

	/**
	 * Check if an entity is aligned to a faction
	 * @Shadow (remap=false)
	 * @param entity The entity to be checked
	 * @return True if the entity is aligned to any faction
	 * */
	@Shadow(remap=false)
	private static boolean isAlignedToSomeFaction(Entity entity) {return false;}

	/**
	 * Gets the faction an entity is a part of
	 * @Shadow (remap=false)
	 * @param entity The entity get the Faction for
	 * @return The Faction for the given Entity, or null if there is no faction for the entity
	 * */
	@Shadow(remap=false)
	public static Faction getFaction(Entity entity) {return null;}

	/**
	 * Determines if a player can cause damage to a target entity, with protection against
	 * fellowship PvP and hired unit friendly fire.
	 * 
	 * @author maximuslotro
	 * @reason Add fellowship PvP and hired unit friendly fire protection
	 * 
	 * @param attacker The player attempting to cause damage.
	 * @param target The target entity to be damaged.
	 * @param warnFriendlyFire Whether to warn the attacker about friendly fire.
	 * @return True if damage is allowed, false if it should be prevented.
	 * @Overwrite (remap=false)
	 */
	@Overwrite(remap=false)
	public static boolean canPlayerCauseDamageToTarget(PlayerEntity attacker, Entity target, boolean warnFriendlyFire) {
		LOTRPlayerData playerData = LOTRLevelData.getSidedData(attacker);
		boolean friendlyFire = false;
		boolean friendlyFireEnabled = playerData.getAlignmentData().isFriendlyFireEnabled();
		if (target instanceof PlayerEntity && target != attacker) {
			// Extended Start
			ExtendedFellowshipDataModule fellowshipModule = ((ExtendedLOTRPlayerData)playerData).getFellowshipData();
			List<ExtendedFellowship> fellowships = fellowshipModule.getFellowships();
			for (ExtendedFellowship fs : fellowships) {
				if (fs.isPreventPVP() && fs.isInFellowship(target.getUUID())) {
					return false;
				}
			}
			// Extended End
		}

		Entity alignedTarget = isAlignedToSomeFaction(target) ? target : target.getPassengers().stream().filter(MixinEntityFactionHelper::isAlignedToSomeFaction).findFirst().orElse(null);
		if (alignedTarget != null) {
			Faction targetFaction = getFaction(alignedTarget);
			if (alignedTarget instanceof MobEntity && !LOTRMod.PROXY.getSidedAttackTarget((MobEntity) alignedTarget).filter(Predicates.equalTo(attacker)).isPresent() && playerData.getAlignmentData().hasAlignment(targetFaction, AlignmentPredicates.POSITIVE)) {
				friendlyFire = true;
			}
		}
		// Extended Start
		if(alignedTarget instanceof NPCEntity && ((ExtendedNPCEntity)alignedTarget).getUnitInfo().isNpcActiveDuty()) {
			UUID hiredUnitOwnerUUID = ((ExtendedNPCEntity)alignedTarget).getUnitInfo().getOwnerUUID();
			if(hiredUnitOwnerUUID!=null) {
				// Check if player can hurt their units
				if (hiredUnitOwnerUUID.equals(attacker.getUUID())) {
					return ((ExtendedLOTRPlayerData)playerData).getExtendedMiscData().canHurtHiredUnits();
				}
				else {
					// Check if unit is owned by fellowship member and friendly fire is being protected
					ExtendedFellowshipDataModule fellowshipModule = ((ExtendedLOTRPlayerData)playerData).getFellowshipData();
					List<ExtendedFellowship> fellowships = fellowshipModule.getFellowships();
					for (ExtendedFellowship fs : fellowships) {
						if (fs.isPreventHiredFF() && fs.isInFellowship(hiredUnitOwnerUUID)) {
							return false;
						}
					}
				}
			}
		}
		// Extended End
		if (friendlyFireEnabled || !friendlyFire) {
			return true;
		}
		if (warnFriendlyFire && !attacker.level.isClientSide) {
			playerData.getMessageData().sendMessageIfNotReceived(PlayerMessageType.FRIENDLY_FIRE);
		}

		return false;
	}
}
