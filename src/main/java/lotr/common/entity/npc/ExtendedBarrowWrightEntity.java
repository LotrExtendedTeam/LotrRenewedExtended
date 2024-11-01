package lotr.common.entity.npc;

import lotr.common.util.GameRuleUtil;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap.MutableAttribute;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class ExtendedBarrowWrightEntity extends NPCEntity {
	public ExtendedBarrowWrightEntity(EntityType<? extends NPCEntity> type, World w) {
		super(type, w);
		this.spawnRequiresSurfaceBlock = true;
	}

	public static MutableAttribute regAttrs() {
		return NPCEntity.registerBaseNPCAttributes()
			.add(Attributes.MAX_HEALTH, 50.0D)
			.add(Attributes.FOLLOW_RANGE, 40.0D)
			.add(Attributes.MOVEMENT_SPEED, 0.2D)
			.add(Attributes.ATTACK_DAMAGE, 6.0D);
	}

	@Override
	public void die(DamageSource damagesource) {
		super.die(damagesource);
		if (!this.level.isClientSide && GameRuleUtil.canDropLoot(this.level) && this.random.nextInt(5) == 0) {
		}

	}
//	@Override
//	public AxisAlignedBB getBoundingBox() {
//		return new AxisAlignedBB(this.getX() - 0.5, this.getY(), this.getZ() - 0.5, this.getX() + 1.5, this.getY() + 1.0, this.getZ() + 0.5);
//	}


	public CreatureAttribute getMobType() {
		return CreatureAttribute.UNDEAD;
	}
}