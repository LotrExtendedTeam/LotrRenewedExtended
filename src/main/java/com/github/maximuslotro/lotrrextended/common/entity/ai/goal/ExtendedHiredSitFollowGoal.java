package com.github.maximuslotro.lotrrextended.common.entity.ai.goal;

import java.util.EnumSet;
import java.util.Random;

import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedUnitOrders;

import lotr.common.entity.npc.ExtendedNPCEntity;
import lotr.common.entity.npc.NPCEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.WalkNodeProcessor;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.world.IWorldReader;

/**
 * A custom goal for hired NPCs that enables different follow or stationary behaviors depending on their unit orders.
 * Supports patrolling the player or standing guard at a location, with specific behavior depending on the environment and order state.
 * 
 * Based on {@link net.minecraft.entity.ai.goal.SitGoal} and {@link net.minecraft.entity.ai.goal.FollowMobGoal} and {@link net.minecraft.entity.ai.goal.FollowOwnerGoal}.
 */
public class ExtendedHiredSitFollowGoal extends Goal {
	/** The NPC executing this goal. */
	private final NPCEntity entity;
	/** The current order type that determines NPC behavior. */
	private ExtendedUnitOrders currentOrderType;
	/** The owner the NPC should follow or monitor. */
	private LivingEntity owner;
	/** The world reader for environment checks. */
	private final IWorldReader level;
	/** Speed modifier for NPC movement. */
	private final double speedModifier;
	/** Path navigator for movement control. */
	private final PathNavigator navigation;
	/** Timer for recalculating paths. */
	private int timeToRecalcPath;
	/** Distance at which NPC stops following the owner. */
	private final float stopDistance;
	/** Distance at which NPC starts following the owner. */
	private final float startDistance;
	/** Original water pathfinding cost. */
	private float oldWaterCost;
	/**
     * Constructs a new ExtendedHiredSitFollowGoal.
     *
     * @param npc The NPC executing this goal.
     * @param speedModifier Movement speed modifier.
     * @param goalStartDistance Distance to start following.
     * @param goalEndDistance Distance to stop following.
     * @param canFly Whether this NPC can fly.
     */
	public ExtendedHiredSitFollowGoal(NPCEntity npc, double speedModifier, float goalStartDistance, float goalEndDistance, boolean canFly) {
		this.entity = npc;
		this.level = entity.level;
		this.speedModifier = speedModifier;
		this.navigation = entity.getNavigation();
		this.startDistance = goalStartDistance;
		this.stopDistance = goalEndDistance;
		if (!(entity.getNavigation() instanceof GroundPathNavigator) && !(entity.getNavigation() instanceof FlyingPathNavigator)) {
			throw new IllegalArgumentException("Unsupported mob type for FollowOwnerGoal");
		}
	}
	/**
     * Determines whether this goal should continue executing.
     *
     * @return true if the goal should continue, false otherwise.
     */
	@Override
	public boolean canContinueToUse() {
		// Check if orders have changed
		if(currentOrderType!=((ExtendedNPCEntity)entity).getUnitInfo().getOrders()) {
			return false;
		}
		if(currentOrderType==ExtendedUnitOrders.PATROL_PLAYER) {
			if (this.navigation.isDone()) {
				return false;
			} // Stop entity following player if entity has been activated for combat
			else if ((entity.getLastHurtByMob() != null && entity.getLastHurtByMob() != ((ExtendedNPCEntity)this.entity).getUnitInfo().getUnitOwner()) || entity.getTarget() != null) {
				return false;
			} else {
				return this.entity.distanceToSqr(this.owner) > (stopDistance * stopDistance); // Continue following player if to far away squared
			}
		}
		if(currentOrderType==ExtendedUnitOrders.GUARD_STATIONARY) {
			// Keep sitting unless attacked, or unit has been taken off ground or put underwater
			return (entity.getLastHurtByMob() == null || entity.getLastHurtByMob() == ((ExtendedNPCEntity)this.entity).getUnitInfo().getUnitOwner()) && entity.isOnGround();
		}
		return false;
	}
	/**
     * Checks if the goal can be used at the current moment.
     * Also initializes state like current order type and owner if applicable.
     *
     * @return true if the goal can start, false otherwise.
     */
	@Override
	public boolean canUse() {
		if (!((ExtendedNPCEntity)this.entity).getUnitInfo().isNpcActiveDuty() || ((ExtendedNPCEntity)this.entity).getUnitInfo().getOrders().isUnknown()) {
			return false;
		}
		this.currentOrderType = ((ExtendedNPCEntity)entity).getUnitInfo().getOrders();
		if(currentOrderType==ExtendedUnitOrders.PATROL_PLAYER) {
			this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
			LivingEntity livingOwner = ((ExtendedNPCEntity)entity).getUnitInfo().getUnitOwner();
			if (livingOwner == null) { // Null owner means player is offline, so we should stay put // TODO implementing stay put (disable random walking)
				return false;
			} else if (livingOwner.isSpectator()) {
				return false;
			} else if (this.entity.distanceToSqr(livingOwner) < (this.startDistance * this.startDistance)) { // Start following player if too far away, but not as close as the stop follow distance
				return false;
			} // Disable entity following player when it's in active combat
			else if ((entity.getLastHurtByMob() != null && entity.getLastHurtByMob() != livingOwner) || entity.getTarget() != null) {
				return false;
			} else {
				this.owner = livingOwner;
				return true;
			}
		}else if(currentOrderType==ExtendedUnitOrders.GUARD_STATIONARY) {
			this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
			if (!this.entity.isOnGround()) {
				return false;
			} else {
				LivingEntity livingOwner = ((ExtendedNPCEntity)this.entity).getUnitInfo().getUnitOwner();
				if (livingOwner == null) { // Null owner means player is offline, so we should stay put
					return true; // Sit if owner if offline
				} else { // Enable unit stationary "sitting" if player is close
					// Unit should stand still if the player is far, if horn used in the future
					return true; //this.entity.distanceToSqr(livingOwner) < 144; // within 12 square blocks of unit
				}
			}
		}
		return false;
	}
	/**
     * Initializes the goal behavior, e.g., adjusts pathfinding or stops navigation.
     */
	@Override
	public void start() {
		if(currentOrderType==ExtendedUnitOrders.PATROL_PLAYER) {
			this.timeToRecalcPath = 0;
			// Remove unit water malus when following player
			this.oldWaterCost = this.entity.getPathfindingMalus(PathNodeType.WATER);
			this.entity.setPathfindingMalus(PathNodeType.WATER, 0.0F);
		}else if(currentOrderType==ExtendedUnitOrders.GUARD_STATIONARY) {
			this.entity.getNavigation().stop();
		}
	}
	/**
     * Resets the goal state once execution ends.
     */
	@Override
	public void stop() {
		if(currentOrderType==ExtendedUnitOrders.PATROL_PLAYER) {
			this.owner = null;
			this.navigation.stop();
			// Restore previous unit water malus when done following player
			this.entity.setPathfindingMalus(PathNodeType.WATER, this.oldWaterCost);
		}else if(currentOrderType==ExtendedUnitOrders.GUARD_STATIONARY) {
		}
	}
	/**
     * Executes behavior every tick, including navigation and optional teleportation.
     */
	@Override
	public void tick() {
		if(currentOrderType==ExtendedUnitOrders.PATROL_PLAYER) {
			this.entity.getLookControl().setLookAt(this.owner, 10.0F, this.entity.getMaxHeadXRot());
			if (--this.timeToRecalcPath <= 0) {
				this.timeToRecalcPath = 10;
				if (!this.entity.isLeashed() && !this.entity.isPassenger()) {
					if (this.entity.distanceToSqr(this.owner) >= entity.getAttributeValue(Attributes.FOLLOW_RANGE)*entity.getAttributeValue(Attributes.FOLLOW_RANGE)) {
						if(teleportToOwner(this.entity, owner, false)) {
							this.navigation.stop();
						}
					} else {
						this.navigation.moveTo(this.owner, this.speedModifier);
					}
				}
			}
		}else if(currentOrderType==ExtendedUnitOrders.GUARD_STATIONARY) {
		}
	}
	/**
     * Attempts to teleport the NPC to its owner if they are too far apart.
	 * @param entity The unit entity to teleport
	 * @param owner The owner of the unit
	 * @param forceTeleport Should force teleport unit into owner if no surrounding area found
	 * @return True if teleported
     */
	public static boolean teleportToOwner(Entity entity, Entity owner, Boolean forceTeleport) {
		BlockPos blockpos = owner.blockPosition();

		for(int i = 0; i < 10; ++i) {
			int j = randomIntInclusive(owner.random, -3, 3);
			int k = randomIntInclusive(owner.random, -1, 1);
			int l = randomIntInclusive(owner.random, -3, 3);
			boolean teleported = maybeTeleportTo(entity, owner, blockpos.getX() + j, blockpos.getY() + k, blockpos.getZ() + l);
			if (teleported) {
				return true;
			}
		}
		if(forceTeleport) {
			entity.moveTo(owner.position());
			return true;
		}
		return false;
	}
	/**
     * Generates a random integer within an inclusive range.
	 *
     * @param rand     The random to query for this math
     * @param minRange Minimum value (inclusive).
     * @param maxRange Maximum value (inclusive).
     * @return A random integer in the given range.
     */
	private static int randomIntInclusive(Random rand, int minRange, int maxRange) {
		return rand.nextInt(maxRange - minRange + 1) + minRange;
	}
	/**
     * Attempts teleportation to a specific position if valid.
     *
     * @param entity The unit entity to teleport
	 * @param owner The owner of the unit
     * @param x X-coordinate of target position.
     * @param y Y-coordinate of target position.
     * @param z Z-coordinate of target position.
     * @return true if teleportation succeeded, false otherwise.
     */
	private static boolean maybeTeleportTo(Entity entity, Entity owner, int x, int y, int z) {
		if (Math.abs(x - owner.getX()) < 2.0D && Math.abs(z - owner.getZ()) < 2.0D) {
			return false;
		} else if (!canTeleportTo(entity, new BlockPos(x, y, z)) || !canTeleportFrom(entity)) {
			return false;
		} else {
			entity.moveTo(x + 0.5D, y, z + 0.5D, entity.yRot, entity.xRot);
			return true;
		}
	}
	/**
     * Validates whether the NPC can teleport from its current location.
     *
     * @param entity The unit entity to teleport
     * @return true if teleportation is allowed, false otherwise.
     */
	private static boolean canTeleportFrom(Entity entity) {
		return entity.isOnGround() || entity.isInWaterOrBubble();
	}
	/**
     * Validates if a specific teleportation destination is allowed.
     *
     * @param entity The entity to target with
     * @param teleportToPos Target position to teleport to.
     * @return true if the destination is valid for teleportation, false otherwise.
     */
	private static boolean canTeleportTo(Entity entity, BlockPos teleportToPos) {
		BlockState belowTeleportState = entity.level.getBlockState(teleportToPos.below());
		boolean full = Block.isFaceFull(belowTeleportState.getCollisionShape(entity.level, teleportToPos, ISelectionContext.empty()), Direction.UP);
		if(belowTeleportState.getBlock() instanceof StairsBlock || belowTeleportState.getBlock() instanceof SlabBlock) {
			full = true;
		}
		boolean isSolid = belowTeleportState.getMaterial().isSolid();

		PathNodeType pathnodetype = WalkNodeProcessor.getBlockPathTypeStatic(entity.level, teleportToPos.mutable());
		//ExtendedLog.info("full:"+full+", isSolid:"+isSolid+", pathnodetype:"+pathnodetype.name());
		if ((pathnodetype != PathNodeType.WALKABLE && pathnodetype != PathNodeType.OPEN) || !isSolid || !full) {
			return false;
		} else {
			if(pathnodetype == PathNodeType.OPEN && !isSolid && !full) {
				BlockState belowTeleportStateAbove = entity.level.getBlockState(teleportToPos);
				// Check above eg, lower type slabs
				boolean fullAbove = Block.isFaceFull(belowTeleportStateAbove.getCollisionShape(entity.level, teleportToPos, ISelectionContext.empty()), Direction.UP);
				if(belowTeleportStateAbove.getBlock() instanceof StairsBlock || belowTeleportStateAbove.getBlock() instanceof SlabBlock) {
					fullAbove = true;
				}
				boolean isSolidAbove = belowTeleportStateAbove.getMaterial().isSolid();
				//ExtendedLog.info("fullAbove:"+fullAbove+", isSolidAbove:"+isSolidAbove+", pathnodetype:"+pathnodetype.name());

				if(!fullAbove || !isSolidAbove) {
					return false;
				}
				if (belowTeleportStateAbove.getBlock() instanceof LeavesBlock) {
					return false;
				} else {
					BlockPos blockpos = teleportToPos.subtract(entity.blockPosition());
					return entity.level.noCollision(entity, entity.getBoundingBox().move(blockpos).move(0, 1, 0));
				}
			}
			if (belowTeleportState.getBlock() instanceof LeavesBlock) {
				return false;
			} else {
				BlockPos blockpos = teleportToPos.subtract(entity.blockPosition());
				return entity.level.noCollision(entity, entity.getBoundingBox().move(blockpos));
			}
		}
	}
}