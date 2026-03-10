package lotr.common.entity.npc;

import lotr.common.entity.npc.ai.goal.NPCMeleeAttackGoal;
import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class ExtendedUrukBerserkerEntity extends UrukEntity{

	public ExtendedUrukBerserkerEntity(EntityType<? extends UrukEntity> type,
			World w) {
		super(type, w);
	}

	@Override
	protected Goal createOrcAttackGoal() {
		return new NPCMeleeAttackGoal(this, 1.6D);
	}

	public static AttributeModifierMap.MutableAttribute createAttributes() {
		return UrukEntity.regAttrs().add(Attributes.MAX_HEALTH, 30.0D)
				.add(Attributes.FOLLOW_RANGE, 24.0D)
				.add(Attributes.ATTACK_DAMAGE, 2.0D)
				.add(Attributes.KNOCKBACK_RESISTANCE, 0.4D)
				.add(Attributes.ATTACK_KNOCKBACK, 0.9D)
				.add(Attributes.ARMOR_TOUGHNESS, 5);
	}

	@Override
	public ILivingEntityData finalizeSpawn(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT dataTag) {
		spawnData = super.finalizeSpawn(sw, diff, reason, spawnData, dataTag);
		this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRItems.URUK_BERSERKER_CLEAVER.get()));
        this.npcItemsInv.setIdleItemsFromMeleeWeapons();
        this.setItemSlot(EquipmentSlotType.FEET, new ItemStack(ExtendedItems.BROWN_WARG_BOOTS.get()));
		this.setItemSlot(EquipmentSlotType.LEGS, new ItemStack(ExtendedItems.BROWN_WARG_LEGGINGS.get()));
		this.setItemSlot(EquipmentSlotType.CHEST, new ItemStack(ExtendedItems.BROWN_WARG_CHESTPLATE.get()));
		this.setItemSlot(EquipmentSlotType.HEAD, new ItemStack(LOTRItems.URUK_BERSERKER_HELMET.get()));
		this.setItemSlot(EquipmentSlotType.OFFHAND, ItemStack.EMPTY);
		return spawnData;
	}

	@Override
	protected float getVoicePitch() {
		return super.getVoicePitch() * 0.80F;
	}
}
