package com.github.maximuslotro.lotrrextended.mixins.lotr.common.entity.npc;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Overwrite;

import lotr.common.entity.npc.GundabadOrcEntity;
import lotr.common.entity.npc.SpawnEquipmentTable;
import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

@Mixin(GundabadOrcEntity.class)
public abstract class MixinGundabadOrcEntity extends GundabadOrcEntity{

	public MixinGundabadOrcEntity(EntityType<? extends GundabadOrcEntity> type, World w) {
		super(type, w);
	}

	@Final
	@Mutable
	private static final SpawnEquipmentTable WEAPONS = SpawnEquipmentTable.of(Items.STONE_SWORD, Items.STONE_AXE, Items.STONE_PICKAXE, 
			LOTRItems.STONE_DAGGER, LOTRItems.STONE_SPEAR, Items.IRON_SWORD, Items.IRON_AXE, Items.IRON_PICKAXE, LOTRItems.IRON_DAGGER, 
			LOTRItems.IRON_SPEAR, LOTRItems.BRONZE_SWORD, LOTRItems.BRONZE_AXE, LOTRItems.BRONZE_PICKAXE, LOTRItems.BRONZE_DAGGER, 
			LOTRItems.BRONZE_SPEAR, LOTRItems.MORDOR_SCIMITAR, LOTRItems.MORDOR_AXE, LOTRItems.MORDOR_PICKAXE, LOTRItems.MORDOR_DAGGER, 
			LOTRItems.MORDOR_SPEAR, ExtendedItems.BONE_SWORD, ExtendedItems.BONE_AXE, ExtendedItems.BONE_PICKAXE, ExtendedItems.BONE_DAGGER, 
			ExtendedItems.BONE_SPEAR);
	@Final
	@Mutable
	private static final SpawnEquipmentTable HELMETS = SpawnEquipmentTable.of(Items.LEATHER_HELMET, LOTRItems.BRONZE_HELMET, 
			LOTRItems.BONE_HELMET, LOTRItems.MORDOR_HELMET, ExtendedItems.BROWN_WARG_HELMET, ExtendedItems.GRAY_WARG_HELMET, ExtendedItems.SILVER_WARG_HELMET);
	@Final
	@Mutable
	private static final SpawnEquipmentTable CHESTPLATES = SpawnEquipmentTable.of(Items.LEATHER_CHESTPLATE, LOTRItems.BRONZE_CHESTPLATE, 
			LOTRItems.BONE_CHESTPLATE, LOTRItems.MORDOR_CHESTPLATE, ExtendedItems.BROWN_WARG_CHESTPLATE, ExtendedItems.GRAY_WARG_CHESTPLATE, 
			ExtendedItems.SILVER_WARG_CHESTPLATE);
	@Final
	@Mutable
	private static final SpawnEquipmentTable LEGGINGS = SpawnEquipmentTable.of(Items.LEATHER_LEGGINGS, LOTRItems.BRONZE_LEGGINGS, 
			LOTRItems.BONE_LEGGINGS, LOTRItems.MORDOR_LEGGINGS, ExtendedItems.BROWN_WARG_LEGGINGS, ExtendedItems.GRAY_WARG_LEGGINGS, 
			ExtendedItems.SILVER_WARG_LEGGINGS);
	@Final
	@Mutable
	private static final SpawnEquipmentTable BOOTS = SpawnEquipmentTable.of(Items.LEATHER_BOOTS, LOTRItems.BRONZE_BOOTS, 
			LOTRItems.BONE_BOOTS, LOTRItems.MORDOR_BOOTS, ExtendedItems.BROWN_WARG_BOOTS, ExtendedItems.GRAY_WARG_BOOTS, ExtendedItems.SILVER_WARG_BOOTS);

	
	/**
	 * @author maximuslotro
	 * @reason Need to add Gundabad shield to equipment pool
	 * */
	@Overwrite
	public ILivingEntityData finalizeSpawn(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT dataTag) {
		spawnData = super.finalizeSpawn(sw, diff, reason, spawnData, dataTag);
		npcItemsInv.setMeleeWeapon(WEAPONS.getRandomItem(random));
		npcItemsInv.setIdleItemsFromMeleeWeapons();
		setItemSlot(EquipmentSlotType.FEET, BOOTS.getRandomItem(random));
		setItemSlot(EquipmentSlotType.LEGS, LEGGINGS.getRandomItem(random));
		setItemSlot(EquipmentSlotType.CHEST, CHESTPLATES.getRandomItem(random));
		if (random.nextInt(3) != 0) {
			setItemSlot(EquipmentSlotType.HEAD, HELMETS.getRandomItem(random));
		}

		if (random.nextFloat() < 0.2F) {
			setItemSlot(EquipmentSlotType.OFFHAND, new ItemStack(ExtendedItems.GUNDABAD_SHIELD.get()));
		}

		return spawnData;
	}
}
