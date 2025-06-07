package com.github.maximuslotro.lotrrextended.mixins.lotr.common.entity.npc;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import lotr.common.entity.npc.BlueDwarfEntity;
import lotr.common.entity.npc.BlueDwarfWarriorEntity;
import lotr.common.entity.npc.DwarfEntity;
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

/**
 * Need to change Blue Mountains Dwarf equipment
 * @Mixin (BlueDwarfWarriorEntity.class)
 * */
@Mixin(BlueDwarfWarriorEntity.class)
public abstract class MixinBlueDwarfWarriorEntity extends BlueDwarfEntity{

	/**
	 * Mixins with extend classes must add a constructor, but this will be stripped on runtime and will cease to exist
	 * @param type A placeholder variable instance of DwarfEntity
	 * @param w A placeholder variable instance of World
	 * */
	public MixinBlueDwarfWarriorEntity(EntityType<? extends DwarfEntity> type, World w) {
		super(type, w);
	}

	/**
	 * Need to get the weapons pool
	 * @Shadow (remap=false)
	 * @Final Mark the field to be essentially final
	 * */
	@Shadow(remap = false)
	@Final
	private static SpawnEquipmentTable WEAPONS;

	/**
	 * @author maximuslotro
	 * @reason Need to add blue dwarven shield and can't override single stack instance
	 * @Overwrite (remap = false)
	 * @param sw A local IServerWorld instance
	 * @param diff The local DifficultyInstance
	 * @param reason The SpawnReason for his spawn
	 * @param spawnData Additional ILivingEntityData for this spawn
	 * @param dataTag Additional CompoundNBT for this spawn
	 * @return An ILivingEntityData instance
	 */
	@Override
	public ILivingEntityData finalizeSpawn(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT dataTag) {
		spawnData = super.finalizeSpawn(sw, diff, reason, spawnData, dataTag);
		npcItemsInv.setMeleeWeapon(WEAPONS.getRandomItem(random));
		npcItemsInv.setIdleItemsFromMeleeWeapons();
		setItemSlot(EquipmentSlotType.FEET, new ItemStack(LOTRItems.BLUE_DWARVEN_BOOTS.get()));
		setItemSlot(EquipmentSlotType.LEGS, new ItemStack(LOTRItems.BLUE_DWARVEN_LEGGINGS.get()));
		setItemSlot(EquipmentSlotType.CHEST, new ItemStack(LOTRItems.BLUE_DWARVEN_CHESTPLATE.get()));
		if (random.nextInt(10) != 0) {
			setItemSlot(EquipmentSlotType.HEAD, new ItemStack(LOTRItems.BLUE_DWARVEN_HELMET.get()));
		}

		setItemSlot(EquipmentSlotType.OFFHAND, new ItemStack(ExtendedItems.BLUE_DUNEDAIN_SHIELD.get()));
		return spawnData;
	}
}
