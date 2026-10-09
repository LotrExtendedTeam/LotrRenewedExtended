package com.github.maximuslotro.lotrextended.mixins.lotr.common.entity.npc;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedItems;

import lotr.common.entity.npc.RivendellElfEntity;
import lotr.common.entity.npc.RivendellWarriorEntity;
import lotr.common.entity.npc.SpawnEquipmentTable;
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
* A Mixin class that modifies the behavior of {@link RivendellWarriorEntity}.
* <p>
* This Mixin extends {@link RivendellElfEntity} and injects additional logic
* into the entity's spawning process. Specifically, it ensures that
* Rivendell Warriors are equipped with a Rivendell Shield in their offhand
* when they spawn, and are given a Rivendell Bow.
* </p>
* @Mixin (RivendellWarriorEntity.class)
*/
@Mixin(RivendellWarriorEntity.class)
public class MixinRivendellWarriorEntity extends RivendellElfEntity{
	
	@Final
	@Mutable
	@Shadow(remap=false)
	private static final SpawnEquipmentTable WEAPONS = SpawnEquipmentTable.of(
			LOTRItems.RIVENDELL_SWORD, LOTRItems.RIVENDELL_SWORD, LOTRItems.RIVENDELL_SWORD, LOTRItems.RIVENDELL_SWORD,
			LOTRItems.RIVENDELL_SPEAR, ExtendedItems.RIVENDELL_LONGSPEAR);

	/**
     * Required constructor for Mixins extending classes.
     * <p>
     * Although this constructor is declared here, it will be removed at runtime
     * during the Mixin transformation process. It only exists to satisfy
     * compilation requirements when extending {@link RivendellElfEntity}.
     * </p>
     *
     * @param type The {@link EntityType} of this {@link RivendellElfEntity}.
     * @param w    The {@link World} in which this entity exists.
     */
	public MixinRivendellWarriorEntity(EntityType<? extends RivendellElfEntity> type, World w) {
		super(type, w);
	}
	/**
     * Injected method called at the end of {@code finalizeSpawn}.
     * <p>
     * This ensures that every Rivendell Warrior is automatically given
     * a Rivendell Shield in their offhand and a Rivendell Bow when they are spawned into the world.
     * </p>
     *
     * @param sw        The {@link IServerWorld} where the entity is being spawned.
     * @param diff      The {@link DifficultyInstance} representing world difficulty scaling.
     * @param reason    The {@link SpawnReason} that triggered this spawn event.
     * @param spawnData The {@link ILivingEntityData} for spawn-related entity state, may be null.
     * @param dataTag   The {@link CompoundNBT} containing entity data, may be null.
     * @param cir       The {@link CallbackInfoReturnable} used by Mixin to continue or cancel execution.
     * @Inject (method = "finalizeSpawn(Lnet/minecraft/world/IServerWorld;Lnet/minecraft/world/DifficultyInstance;Lnet/minecraft/entity/SpawnReason;Lnet/minecraft/entity/ILivingEntityData;Lnet/minecraft/nbt/CompoundNBT;)Lnet/minecraft/entity/ILivingEntityData;", at = @At("TAIL"))
     */
	@Inject(method = "finalizeSpawn(Lnet/minecraft/world/IServerWorld;Lnet/minecraft/world/DifficultyInstance;Lnet/minecraft/entity/SpawnReason;Lnet/minecraft/entity/ILivingEntityData;Lnet/minecraft/nbt/CompoundNBT;)Lnet/minecraft/entity/ILivingEntityData;",
			at = @At("TAIL"))
	public void finalizeSpawn$Extended(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT dataTag, CallbackInfoReturnable<ILivingEntityData> cir) {
		setItemSlot(EquipmentSlotType.OFFHAND, new ItemStack(ExtendedItems.RIVENDELL_SHIELD.get()));
		this.npcItemsInv.setRangedWeapon(new ItemStack(ExtendedItems.RIVENDELL_BOW.get()));
	}
}
