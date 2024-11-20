package lotr.common.entity.npc;

import com.github.maximuslotro.lotrrextended.common.utils.EffectDurationUtils;

import lotr.common.entity.npc.ai.AttackGoalsHolder;
import lotr.common.entity.npc.ai.goal.NPCMeleeAttackGoal;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap.MutableAttribute;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.world.World;

public class ExtendedBarrowWrightEntity extends NPCEntity {
	public ExtendedBarrowWrightEntity(EntityType<? extends NPCEntity> type, World w) {
		super(type, w);
		this.spawnRequiresSurfaceBlock = true;
		this.spawnRequiresDarkness = true;
	}

	public static MutableAttribute regAttrs() {
		return NPCEntity.registerBaseNPCAttributes()
			.add(Attributes.MAX_HEALTH, 70.0D)
			.add(Attributes.FOLLOW_RANGE, 40.0D)
			.add(Attributes.MOVEMENT_SPEED, 0.20D)
			.add(Attributes.ARMOR, 6.5D)
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
	protected void initialiseAttackGoals(AttackGoalsHolder holder) {
		holder.setMeleeAttackGoal(new NPCMeleeAttackGoal(this, 1.5D));
	}

	@Override
	protected void addNPCTargetingAI() {
		addAggressiveTargetingGoals();
	}

	@Override
	public boolean doHurtTarget(Entity target) {
		if (super.doHurtTarget(target)) {
			if (target instanceof LivingEntity) {
				LivingEntity livingTarget = (LivingEntity)target;
				int effectDuration = EffectDurationUtils.calculateDifficulityBasedEffectTime(level.getDifficulty().getId());
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


	public CreatureAttribute getMobType() {
		return CreatureAttribute.UNDEAD;
	}
}