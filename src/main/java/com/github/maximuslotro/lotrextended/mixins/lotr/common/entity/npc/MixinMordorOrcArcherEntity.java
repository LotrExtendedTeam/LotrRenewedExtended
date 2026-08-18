package com.github.maximuslotro.lotrextended.mixins.lotr.common.entity.npc;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedItems;

import lotr.common.entity.npc.MordorOrcArcherEntity;
import lotr.common.entity.npc.MordorOrcEntity;
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
 * Need to change Mordor Orc Archer equipment
 * @Mixin (MordorOrcArcherEntity.class)
 * */
@Mixin(MordorOrcArcherEntity.class)
public abstract class MixinMordorOrcArcherEntity extends MordorOrcEntity{

	/**
	 * Mixins with extend classes must add a constructor, but this will be stripped on runtime and will cease to exist
	 * @param type A placeholder variable instance of MordorOrcEntity
	 * @param world A placeholder variable instance of World
	 * */
	public MixinMordorOrcArcherEntity(EntityType<? extends MordorOrcEntity> type, World world) {
		super(type, world);
	}

	/**
	 * @author maximuslotro
	 * @reason Need to add Orc bows to equipment pool
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
		this.npcItemsInv.setRangedWeapon(new ItemStack(ExtendedItems.ORC_BOW.get()));
		this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRItems.MORDOR_DAGGER.get()));
		this.npcItemsInv.setIdleItemsFromRangedWeapons();
		this.setItemSlot(EquipmentSlotType.OFFHAND, ItemStack.EMPTY);
		return spawnData;
	}
}
