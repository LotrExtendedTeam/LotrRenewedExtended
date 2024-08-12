package com.github.maximuslotro.lotrrextended.mixins.lotr.common.entity.npc;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;

import lotr.common.entity.npc.IsengardSnagaEntity;
import lotr.common.entity.npc.SpawnEquipmentTable;
import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRItems;
import net.minecraft.item.Items;

@Mixin(IsengardSnagaEntity.class)
public abstract class MixinIsengardSnagaEntity{

	@Final
	@Mutable
	private static final SpawnEquipmentTable WEAPONS = SpawnEquipmentTable.of(Items.STONE_SWORD, Items.STONE_AXE, Items.STONE_PICKAXE, 
			LOTRItems.STONE_DAGGER, LOTRItems.STONE_SPEAR, Items.IRON_SWORD, Items.IRON_AXE, Items.IRON_PICKAXE, LOTRItems.IRON_DAGGER, 
			LOTRItems.IRON_SPEAR, LOTRItems.BRONZE_SWORD, LOTRItems.BRONZE_AXE, LOTRItems.BRONZE_PICKAXE, LOTRItems.BRONZE_DAGGER, 
			LOTRItems.BRONZE_SPEAR, LOTRItems.URUK_CLEAVER, LOTRItems.URUK_AXE, LOTRItems.URUK_PICKAXE, LOTRItems.URUK_DAGGER, LOTRItems.URUK_SPEAR, 
			ExtendedItems.BONE_SWORD, ExtendedItems.BONE_AXE, ExtendedItems.BONE_PICKAXE, ExtendedItems.BONE_DAGGER, ExtendedItems.BONE_SPEAR);
	@Final
	@Mutable
	private static final SpawnEquipmentTable HELMETS = SpawnEquipmentTable.of(Items.LEATHER_HELMET, LOTRItems.BRONZE_HELMET, 
			LOTRItems.BONE_HELMET, ExtendedItems.BROWN_WARG_HELMET, ExtendedItems.GRAY_WARG_HELMET, ExtendedItems.SILVER_WARG_HELMET, ExtendedItems.BLACK_WARG_HELMET);
	@Final
	@Mutable
	private static final SpawnEquipmentTable CHESTPLATES = SpawnEquipmentTable.of(Items.LEATHER_CHESTPLATE, LOTRItems.BRONZE_CHESTPLATE, 
			LOTRItems.BONE_CHESTPLATE, LOTRItems.URUK_CHESTPLATE, ExtendedItems.BROWN_WARG_CHESTPLATE, ExtendedItems.GRAY_WARG_CHESTPLATE, 
			ExtendedItems.SILVER_WARG_CHESTPLATE, ExtendedItems.BLACK_WARG_CHESTPLATE);
	@Final
	@Mutable
	private static final SpawnEquipmentTable LEGGINGS = SpawnEquipmentTable.of(Items.LEATHER_LEGGINGS, LOTRItems.BRONZE_LEGGINGS, 
			LOTRItems.BONE_LEGGINGS, LOTRItems.URUK_LEGGINGS, ExtendedItems.BROWN_WARG_LEGGINGS, ExtendedItems.GRAY_WARG_LEGGINGS, 
			ExtendedItems.SILVER_WARG_LEGGINGS, ExtendedItems.BLACK_WARG_LEGGINGS);
	@Final
	@Mutable
	private static final SpawnEquipmentTable BOOTS = SpawnEquipmentTable.of(Items.LEATHER_BOOTS, LOTRItems.BRONZE_BOOTS, 
			LOTRItems.BONE_BOOTS, LOTRItems.URUK_BOOTS, ExtendedItems.BROWN_WARG_BOOTS, ExtendedItems.GRAY_WARG_BOOTS, ExtendedItems.SILVER_WARG_BOOTS, 
			ExtendedItems.BLACK_WARG_BOOTS);
	
//	/**
//	 * @author maximuslotro
//	 * @reason Need to add Isengard shield to equipment pool
//	 * */
//	@Overwrite
//	public ILivingEntityData finalizeSpawn(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT dataTag) {
//		spawnData = super.finalizeSpawn(sw, diff, reason, spawnData, dataTag);
//		this.npcItemsInv.setMeleeWeapon(WEAPONS.getRandomItem(this.random));
//		this.npcItemsInv.setIdleItemsFromMeleeWeapons();
//		this.setItemSlot(EquipmentSlotType.FEET, BOOTS.getRandomItem(this.random));
//		this.setItemSlot(EquipmentSlotType.LEGS, LEGGINGS.getRandomItem(this.random));
//		this.setItemSlot(EquipmentSlotType.CHEST, CHESTPLATES.getRandomItem(this.random));
//		if (this.random.nextInt(3) != 0) {
//			this.setItemSlot(EquipmentSlotType.HEAD, HELMETS.getRandomItem(this.random));
//		}
//
//		if (this.random.nextFloat() < 0.2F) {
//			this.setItemSlot(EquipmentSlotType.OFFHAND, new ItemStack(Items.SHIELD));
//		}
//
//		return spawnData;
//	}
}
