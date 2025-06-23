package lotr.common.entity.npc.ai.goal;

import lotr.common.data.ExtendedLOTRPlayerData;
import lotr.common.data.LOTRLevelData;
import lotr.common.entity.npc.ExtendedNPCEntity;
import lotr.common.entity.npc.NPCEntity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

/**
 * A customized extension of {@link NPCHurtByTargetGoal} that introduces additional logic 
 * to prevent friendly fire between NPC units owned by the same player.
 * <p>
 * This goal ensures that when an NPC unit is hurt, it will not automatically retaliate 
 * against other hired NPCs owned by the same player—unless explicitly allowed in the player’s configuration.
 */
public class ExtendedNPCUnitHurtByTargetGoal extends NPCHurtByTargetGoal {
	/**
     * The extended NPC entity using this goal.
     */
	private final ExtendedNPCEntity extendedEntity;
	/**
     * Constructs a new targeting goal for the specified extended NPC.
     *
     * @param entity The base {@link NPCEntity} instance; expected to be an instance of {@link ExtendedNPCEntity}.
     */
	public ExtendedNPCUnitHurtByTargetGoal(NPCEntity entity) {
		super(entity);
		this.extendedEntity = (ExtendedNPCEntity) entity;
	}
	/**
     * Determines whether the given potential target can be attacked.
     * <p>
     * Overrides the base logic to block attacks against allied NPCs hired by the same player,
     * unless explicitly permitted in player configuration via {@code canHiredUnitAttackOtherHiredUnit()}.
     *
     * @param potentialTarget The entity being considered for attack.
     * @param targetPredicate The predicate used to evaluate target validity.
     * @return {@code true} if the target can be attacked, {@code false} otherwise.
     */
	@Override
	protected boolean canAttack(LivingEntity potentialTarget, EntityPredicate targetPredicate) {
		if (potentialTarget instanceof ExtendedNPCEntity && extendedEntity.getUnitInfo().isNpcActiveDuty()) {
			ExtendedNPCEntity eTarget = ((ExtendedNPCEntity)potentialTarget);
			if (eTarget.getUnitInfo().isNpcActiveDuty() && eTarget.getUnitInfo().getOwnerUUID().equals(extendedEntity.getUnitInfo().getOwnerUUID())) {
				if (!((ExtendedLOTRPlayerData)LOTRLevelData.serverInstance().getData((PlayerEntity) extendedEntity.getUnitInfo().getUnitOwner())).getExtendedMiscData().canHiredUnitAttackOtherHiredUnit()) {
					return false;
				}
			}
		}
		return super.canAttack(potentialTarget, targetPredicate);
	}
}
