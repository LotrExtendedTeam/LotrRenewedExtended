package com.github.maximuslotro.lotrextended.mixins.lotr.common.entity.npc;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import lotr.common.entity.npc.OrcEntity;
import lotr.common.entity.npc.SpawnEquipmentTable;
import lotr.common.entity.npc.UrukEntity;
import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.IItemProvider;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

/**
 * Need to change Uruk Orc equipment
 * @Mixin (UrukArcherEntity.class)
 * */
@Mixin(UrukEntity.class)
public abstract class MixinUrukEntity extends OrcEntity{
	@Final
	@Shadow(remap=false)
	private static SpawnEquipmentTable WEAPONS;
	/**
	 * Mixins with extend classes must add a constructor, but this will be stripped on runtime and will cease to exist
	 * @param type A placeholder variable instance of UrukEntity
	 * @param world A placeholder variable instance of World
	 * */
	public MixinUrukEntity(EntityType<? extends UrukEntity> type, World world) {
		super(type, world);
	}
	
	/**
	 * @author maximuslotro
	 * @reason Need to add uruk shields to equipment pool
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
		this.setItemSlot(EquipmentSlotType.FEET, new ItemStack((IItemProvider) LOTRItems.URUK_BOOTS.get()));
		this.setItemSlot(EquipmentSlotType.LEGS, new ItemStack((IItemProvider) LOTRItems.URUK_LEGGINGS.get()));
		this.setItemSlot(EquipmentSlotType.CHEST, new ItemStack((IItemProvider) LOTRItems.URUK_CHESTPLATE.get()));
		if (this.random.nextInt(10) != 0) {
			this.setItemSlot(EquipmentSlotType.HEAD, new ItemStack((IItemProvider) LOTRItems.URUK_HELMET.get()));
		}
		// Extended Start
		this.setItemSlot(EquipmentSlotType.OFFHAND, new ItemStack(ExtendedItems.URUK_SHIELD.get()));
		// Extended End
		return spawnData;
	}
}
