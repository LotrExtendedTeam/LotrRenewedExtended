package com.github.maximuslotro.lotrextended.mixins.lotr.common.entity.npc;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import lotr.common.entity.npc.BreeGuardEntity;
import lotr.common.entity.npc.BreeManEntity;
import lotr.common.entity.npc.SpawnEquipmentTable;
import lotr.common.entity.npc.util.LeatherDyeUtil;
import lotr.common.init.ExtendedItems;
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

/**
 * Need to change Bree Guard equipment
 * @Mixin (BreeGuardEntity.class)
 * Extends BreeManEntity to access sub-methods, but made abstract so the mixin in transitively applied
 * */
@Mixin(BreeGuardEntity.class)
public abstract class MixinBreeGuardEntity extends BreeManEntity {


	/**
	 * Need to access for finalizeSpawn method
	 * @Shadow (remap=false)
	 * @Final Mark the field to be essentially final
	 * */
	@Final
	@Shadow(remap=false)
	private static SpawnEquipmentTable WEAPONS;
	/**
	 * Need to access for finalizeSpawn method
	 * @Shadow (remap=false)
	 * @Final Mark the field to be essentially final
	 * */
	@Final
	@Shadow(remap=false)
	private static int[] SUIT_COLORS;

	/**
	 * Mixins with extend classes must add a constructor, but this will be stripped on runtime and will cease to exist
	 * @param type A placeholder variable instance of BreeManEntity
	 * @param world A placeholder variable instance of World
	 * */
	public MixinBreeGuardEntity(EntityType<? extends BreeManEntity> type, World world) {
		super(type, world);
	}

	/**
	 * @author maximuslotro
	 * @reason Need to add Bree shield to equipment pool
	 * @Overwrite (remap = false)
	 * @param sw A local IServerWorld instance
	 * @param diff The local DifficultyInstance
	 * @param reason The SpawnReason for his spawn
	 * @param spawnData Additional ILivingEntityData for this spawn
	 * @param dataTag Additional CompoundNBT for this spawn
	 * @return An ILivingEntityData instance
	 */
	@Override
	@Overwrite(remap = false)
	public ILivingEntityData finalizeSpawn(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT dataTag) {
		spawnData = super.finalizeSpawn(sw, diff, reason, spawnData, dataTag);
		this.npcItemsInv.setMeleeWeapon(WEAPONS.getRandomItem(this.random));
		this.npcItemsInv.setIdleItemsFromMeleeWeapons();
		if (this.random.nextInt(3) == 0) {
			this.setItemSlot(EquipmentSlotType.FEET, new ItemStack(Items.CHAINMAIL_BOOTS));
		} else {
			this.setItemSlot(EquipmentSlotType.FEET, LeatherDyeUtil.dyeLeather(Items.LEATHER_BOOTS, 3354152));
		}

		if (this.random.nextInt(3) == 0) {
			this.setItemSlot(EquipmentSlotType.LEGS, new ItemStack(Items.CHAINMAIL_LEGGINGS));
		} else {
			this.setItemSlot(EquipmentSlotType.LEGS, LeatherDyeUtil.dyeLeather(Items.LEATHER_LEGGINGS, SUIT_COLORS, this.random));
		}

		if (this.random.nextInt(3) == 0) {
			this.setItemSlot(EquipmentSlotType.CHEST, new ItemStack(Items.CHAINMAIL_CHESTPLATE));
		} else {
			this.setItemSlot(EquipmentSlotType.CHEST, LeatherDyeUtil.dyeLeather(Items.LEATHER_CHESTPLATE, SUIT_COLORS, this.random));
		}

		this.setItemSlot(EquipmentSlotType.HEAD, new ItemStack(Items.IRON_HELMET));
		// Extended Start
		this.setItemSlot(EquipmentSlotType.OFFHAND, new ItemStack(ExtendedItems.BREE_SHIELD.get()));
		// Extended End
		return spawnData;
	}
}
