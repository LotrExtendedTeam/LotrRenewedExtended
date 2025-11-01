package lotr.common.entity.npc;

import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class ExtendedRivendellGuardEntity extends RivendellWarriorEntity {

	public ExtendedRivendellGuardEntity(EntityType<? extends ExtendedRivendellGuardEntity> type, World w) {
		super(type, w);
	}

	public static AttributeModifierMap.MutableAttribute createAttributes() {
		return RivendellWarriorEntity.regAttrs().add(Attributes.MAX_HEALTH, 40.0D);
	}

	@Override
	public ILivingEntityData finalizeSpawn(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT dataTag) {
		spawnData = super.finalizeSpawn(sw, diff, reason, spawnData, dataTag);
		this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRItems.RIVENDELL_SWORD.get()));
		this.npcItemsInv.setRangedWeapon(new ItemStack(Items.BOW));
		this.npcItemsInv.setIdleItemsFromMeleeWeapons();
		setItemSlot(EquipmentSlotType.FEET, new ItemStack(ExtendedItems.IMLADRIS_GUARD_BOOTS.get()));
		setItemSlot(EquipmentSlotType.LEGS, new ItemStack(ExtendedItems.IMLADRIS_GUARD_LEGGINGS.get()));
		setItemSlot(EquipmentSlotType.CHEST, new ItemStack(ExtendedItems.IMLADRIS_GUARD_CHESTPLATE.get()));
		setItemSlot(EquipmentSlotType.HEAD, new ItemStack(ExtendedItems.IMLADRIS_GUARD_HELMET.get()));
		setItemSlot(EquipmentSlotType.OFFHAND, new ItemStack(ExtendedItems.IMLADRIS_GUARD_SHIELD.get()));
		return spawnData;
	}
}