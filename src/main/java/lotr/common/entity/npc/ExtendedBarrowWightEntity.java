package lotr.common.entity.npc;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedSounds;
import com.github.maximuslotro.lotrrextended.common.utils.ExtendedEffectUtils;

import lotr.common.entity.npc.ai.AttackGoalsHolder;
import lotr.common.entity.npc.ai.goal.NPCMeleeAttackGoal;
import lotr.common.init.LOTRBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap.MutableAttribute;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class ExtendedBarrowWightEntity extends NPCEntity {
	public ExtendedBarrowWightEntity(EntityType<? extends NPCEntity> type, World w) {
		super(type, w);
		this.spawnRequiresSurfaceBlock = true;
		this.spawnRequiresDarkness = true;
	}

	public static MutableAttribute createAttributes() {
		return NPCEntity.registerBaseNPCAttributes()
			.add(Attributes.MAX_HEALTH, 70.0D)
			.add(Attributes.FOLLOW_RANGE, 40.0D)
			.add(Attributes.MOVEMENT_SPEED, 0.2D)
			.add(Attributes.ARMOR, 6.0D)
			.add(Attributes.ATTACK_DAMAGE, 7.0D);
	}

	@Override
	protected double calculateDefaultNPCReach() {
		return 0.5F * getBbWidth() + 1.7F;
	}

	@Override
	protected void addNPCAI() {
		super.addNPCAI();
		this.goalSelector.addGoal(0, new SwimGoal(this));
		addAttackGoal(2);
		this.goalSelector.addGoal(4, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(5, new LookAtGoal(this, PlayerEntity.class, 12.0F, 0.02F));
		this.goalSelector.addGoal(6, new LookAtGoal(this, NPCEntity.class, 8.0F, 0.02F));
		this.goalSelector.addGoal(7, new LookAtGoal(this, LivingEntity.class, 12.0F, 0.02F));
		this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
	}

	@Override
	public boolean causeFallDamage(float pFallDistance, float pDamageMultiplier) {
		return false;
	}

	@Override
	protected void initialiseAttackGoals(AttackGoalsHolder holder) {
		holder.setMeleeAttackGoal(new NPCMeleeAttackGoal(this, 1.5D));
	}

	@Override
	protected void addNPCTargetingAI() {
		addAggressiveTargetingGoals();
	}

	@Override
	public void makeStuckInBlock(BlockState pState, Vector3d pMotionMultiplier) {
		if (!pState.is(Blocks.COBWEB) && !pState.is(Blocks.SWEET_BERRY_BUSH) && !pState.is(LOTRBlocks.HANGING_WEB.get())) {
			super.makeStuckInBlock(pState, pMotionMultiplier);
		}
	}

	@Override
	public boolean doHurtTarget(Entity target) {
		if (super.doHurtTarget(target)) {
			if (target instanceof LivingEntity) {
				LivingEntity livingTarget = (LivingEntity)target;
				int effectDuration = ExtendedEffectUtils.calculateDifficulityBasedEffectTime(level.getDifficulty().getId());
				if(effectDuration>0) {
					livingTarget.addEffect(new EffectInstance(Effects.WITHER, effectDuration * 20, 0));
					livingTarget.addEffect(new EffectInstance(Effects.DIG_SLOWDOWN, effectDuration * 20, 0));
					livingTarget.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, effectDuration * 20, 0));
				}
			}
			return true;
		}
		return false;
	}

	//	@Override
	//	public AxisAlignedBB getBoundingBox() {
	//		return new AxisAlignedBB(this.getX() - 0.5, this.getY(), this.getZ() - 0.5, this.getX() + 1.5, this.getY() + 1.0, this.getZ() + 0.5);
	//	}

	@Override
	public CreatureAttribute getMobType() {
		return CreatureAttribute.UNDEAD;
	}

	@Override
	@Nullable
	protected SoundEvent getHurtSound(DamageSource pDamageSource) {
		return SoundEvents.GENERIC_HURT;
	}

	@Override
	@Nullable
	protected SoundEvent getDeathSound() {
		return ExtendedSounds.WIGHT_DEATH.get();
	}

	@Override
	@Nullable
	protected SoundEvent getAmbientSound() {
		return ExtendedSounds.WIGHT_AMBIENCE.get();
	}

	@Override
	protected void playStepSound(BlockPos pPos, BlockState pBlock) {}
}