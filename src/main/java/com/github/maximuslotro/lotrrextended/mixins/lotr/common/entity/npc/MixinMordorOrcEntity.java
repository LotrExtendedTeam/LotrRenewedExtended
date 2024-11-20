package com.github.maximuslotro.lotrrextended.mixins.lotr.common.entity.npc;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import lotr.common.entity.npc.MordorOrcEntity;
import lotr.common.entity.npc.OrcEntity;
import lotr.common.entity.npc.SpawnEquipmentTable;
import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

@Mixin(MordorOrcEntity.class)
public abstract class MixinMordorOrcEntity extends OrcEntity{

	@Shadow(remap = false)
	@Final
	private static SpawnEquipmentTable WEAPONS;

	public MixinMordorOrcEntity(EntityType<? extends OrcEntity> type, World w) {
		super(type, w);
	}

	private static final SpawnEquipmentTable HELMETS = SpawnEquipmentTable
		.of(LOTRItems.MORDOR_HELMET, LOTRItems.MORDOR_HELMET, LOTRItems.MORDOR_HELMET, LOTRItems.MORDOR_HELMET, LOTRItems.MORDOR_HELMET, LOTRItems.MORDOR_HELMET, 
			ExtendedItems.BLACK_WARG_HELMET, ExtendedItems.BROWN_WARG_HELMET);

	private static final SpawnEquipmentTable CHESTPLATES = SpawnEquipmentTable
		.of(LOTRItems.MORDOR_CHESTPLATE, LOTRItems.MORDOR_CHESTPLATE, LOTRItems.MORDOR_CHESTPLATE, LOTRItems.MORDOR_CHESTPLATE, LOTRItems.MORDOR_CHESTPLATE, 
			ExtendedItems.BLACK_WARG_CHESTPLATE, ExtendedItems.BROWN_WARG_CHESTPLATE);

	private static final SpawnEquipmentTable LEGGINGS = SpawnEquipmentTable
		.of(LOTRItems.MORDOR_LEGGINGS, LOTRItems.MORDOR_LEGGINGS, LOTRItems.MORDOR_LEGGINGS, LOTRItems.MORDOR_LEGGINGS, LOTRItems.MORDOR_LEGGINGS, 
			ExtendedItems.BLACK_WARG_LEGGINGS, ExtendedItems.BROWN_WARG_LEGGINGS);

	private static final SpawnEquipmentTable BOOTS = SpawnEquipmentTable
		.of(LOTRItems.MORDOR_BOOTS, LOTRItems.MORDOR_BOOTS, LOTRItems.MORDOR_BOOTS, LOTRItems.MORDOR_BOOTS, LOTRItems.MORDOR_BOOTS, 
			ExtendedItems.BLACK_WARG_BOOTS, ExtendedItems.BROWN_WARG_BOOTS);

	/**
	 * @author maximuslotro
	 * @reason Need to add Fur Armors to equipment pool
	 */
	@Overwrite(remap = false)
	public ILivingEntityData finalizeSpawn(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT dataTag) {
		spawnData = super.finalizeSpawn(sw, diff, reason, spawnData, dataTag);
		npcItemsInv.setMeleeWeapon(WEAPONS.getRandomItem(random));
		npcItemsInv.setIdleItemsFromMeleeWeapons();
		setItemSlot(EquipmentSlotType.FEET, BOOTS.getRandomItem(random));
		setItemSlot(EquipmentSlotType.LEGS, LEGGINGS.getRandomItem(random));
		setItemSlot(EquipmentSlotType.CHEST, CHESTPLATES.getRandomItem(random));
		if (random.nextInt(5) != 0) {
			setItemSlot(EquipmentSlotType.HEAD, HELMETS.getRandomItem(random));
		}

		if (random.nextFloat() < 0.5F) {
			setItemSlot(EquipmentSlotType.OFFHAND, new ItemStack(LOTRItems.MORDOR_SHIELD.get()));
		}

		return spawnData;
	}
}
