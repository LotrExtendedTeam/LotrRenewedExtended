package com.github.maximuslotro.lotrextended.mixins.lotr.common.entity.npc;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import lotr.common.entity.npc.IsengardSnagaEntity;
import lotr.common.entity.npc.SpawnEquipmentTable;
import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRItems;
import net.minecraft.item.Items;
import net.minecraft.util.IItemProvider;

/**
 * Need to change Isengard Orc equipment
 * @Mixin (GundabadOrcEntity.class)
 * */
@Mixin(IsengardSnagaEntity.class)
public class MixinIsengardSnagaEntity{

	/**
	 * Need to add new equipment to the equipment pool
	 * @Shadow (remap=false)
	 * @Final Mark the field to be essentially final
	 * @Mutable Tell Mixin that it can modify the Final field
	 * */
	@Final
	@Mutable
	@Shadow(remap=false)
	private static final SpawnEquipmentTable WEAPONS = SpawnEquipmentTable.of(Items.STONE_SWORD, Items.STONE_AXE, Items.STONE_PICKAXE,
			LOTRItems.STONE_DAGGER, LOTRItems.STONE_SPEAR, Items.IRON_SWORD, Items.IRON_AXE, Items.IRON_PICKAXE, LOTRItems.IRON_DAGGER,
			LOTRItems.IRON_SPEAR, LOTRItems.BRONZE_SWORD, LOTRItems.BRONZE_AXE, LOTRItems.BRONZE_PICKAXE, LOTRItems.BRONZE_DAGGER,
			LOTRItems.BRONZE_SPEAR, LOTRItems.URUK_CLEAVER, LOTRItems.URUK_AXE, LOTRItems.URUK_PICKAXE, LOTRItems.URUK_DAGGER, LOTRItems.URUK_SPEAR,
			ExtendedItems.BONE_SWORD, ExtendedItems.BONE_AXE, ExtendedItems.BONE_PICKAXE, ExtendedItems.BONE_DAGGER, ExtendedItems.BONE_SPEAR);
	/**
	 * Need to add new equipment to the equipment pool
	 * @Shadow (remap=false)
	 * @Final Mark the field to be essentially final
	 * @Mutable Tell Mixin that it can modify the Final field
	 * */
	@Final
	@Mutable
	@Shadow(remap=false)
	private static final SpawnEquipmentTable HELMETS = SpawnEquipmentTable.of(
			Items.LEATHER_HELMET, LOTRItems.BRONZE_HELMET, LOTRItems.BONE_HELMET,
			Items.LEATHER_HELMET, LOTRItems.BRONZE_HELMET, LOTRItems.BONE_HELMET,
			Items.LEATHER_HELMET, LOTRItems.BRONZE_HELMET, LOTRItems.BONE_HELMET,
			Items.LEATHER_HELMET, LOTRItems.BRONZE_HELMET, LOTRItems.BONE_HELMET,
			Items.LEATHER_HELMET, LOTRItems.BRONZE_HELMET, LOTRItems.BONE_HELMET,
			ExtendedItems.BROWN_WARG_HELMET, ExtendedItems.GRAY_WARG_HELMET, ExtendedItems.BLACK_WARG_HELMET);
	/**
	 * Need to add new equipment to the equipment pool
	 * @Shadow (remap=false)
	 * @Final Mark the field to be essentially final
	 * @Mutable Tell Mixin that it can modify the Final field
	 * */
	@Final
	@Mutable
	@Shadow(remap=false)
	private static final SpawnEquipmentTable CHESTPLATES = SpawnEquipmentTable.of(
			Items.LEATHER_CHESTPLATE, LOTRItems.BRONZE_CHESTPLATE, LOTRItems.BONE_CHESTPLATE, LOTRItems.URUK_CHESTPLATE,
			Items.LEATHER_CHESTPLATE, LOTRItems.BRONZE_CHESTPLATE, LOTRItems.BONE_CHESTPLATE, LOTRItems.URUK_CHESTPLATE,
			Items.LEATHER_CHESTPLATE, LOTRItems.BRONZE_CHESTPLATE, LOTRItems.BONE_CHESTPLATE, LOTRItems.URUK_CHESTPLATE,
			Items.LEATHER_CHESTPLATE, LOTRItems.BRONZE_CHESTPLATE, LOTRItems.BONE_CHESTPLATE, LOTRItems.URUK_CHESTPLATE,
			Items.LEATHER_CHESTPLATE, LOTRItems.BRONZE_CHESTPLATE, LOTRItems.BONE_CHESTPLATE, LOTRItems.URUK_CHESTPLATE,
			ExtendedItems.BROWN_WARG_CHESTPLATE, ExtendedItems.GRAY_WARG_CHESTPLATE, ExtendedItems.BLACK_WARG_CHESTPLATE);
	/**
	 * Need to add new equipment to the equipment pool
	 * @Shadow (remap=false)
	 * @Final Mark the field to be essentially final
	 * @Mutable Tell Mixin that it can modify the Final field
	 * */
	@Final
	@Mutable
	@Shadow(remap=false)
	private static final SpawnEquipmentTable LEGGINGS = SpawnEquipmentTable.of(
			Items.LEATHER_LEGGINGS, LOTRItems.BRONZE_LEGGINGS, LOTRItems.BONE_LEGGINGS, LOTRItems.URUK_LEGGINGS,
			Items.LEATHER_LEGGINGS, LOTRItems.BRONZE_LEGGINGS, LOTRItems.BONE_LEGGINGS, LOTRItems.URUK_LEGGINGS,
			Items.LEATHER_LEGGINGS, LOTRItems.BRONZE_LEGGINGS, LOTRItems.BONE_LEGGINGS, LOTRItems.URUK_LEGGINGS,
			Items.LEATHER_LEGGINGS, LOTRItems.BRONZE_LEGGINGS, LOTRItems.BONE_LEGGINGS, LOTRItems.URUK_LEGGINGS,
			Items.LEATHER_LEGGINGS, LOTRItems.BRONZE_LEGGINGS, LOTRItems.BONE_LEGGINGS, LOTRItems.URUK_LEGGINGS,
			ExtendedItems.BROWN_WARG_LEGGINGS, ExtendedItems.GRAY_WARG_LEGGINGS, ExtendedItems.BLACK_WARG_LEGGINGS);
	/**
	 * Need to add new equipment to the equipment pool
	 * @Shadow (remap=false)
	 * @Final Mark the field to be essentially final
	 * @Mutable Tell Mixin that it can modify the Final field
	 * */
	@Final
	@Mutable
	@Shadow(remap=false)
	private static final SpawnEquipmentTable BOOTS = SpawnEquipmentTable.of(
			Items.LEATHER_BOOTS, LOTRItems.BRONZE_BOOTS, LOTRItems.BONE_BOOTS, LOTRItems.URUK_BOOTS,
			Items.LEATHER_BOOTS, LOTRItems.BRONZE_BOOTS, LOTRItems.BONE_BOOTS, LOTRItems.URUK_BOOTS,
			Items.LEATHER_BOOTS, LOTRItems.BRONZE_BOOTS, LOTRItems.BONE_BOOTS, LOTRItems.URUK_BOOTS,
			Items.LEATHER_BOOTS, LOTRItems.BRONZE_BOOTS, LOTRItems.BONE_BOOTS, LOTRItems.URUK_BOOTS,
			Items.LEATHER_BOOTS, LOTRItems.BRONZE_BOOTS, LOTRItems.BONE_BOOTS, LOTRItems.URUK_BOOTS,
			ExtendedItems.BROWN_WARG_BOOTS, ExtendedItems.GRAY_WARG_BOOTS, ExtendedItems.BLACK_WARG_BOOTS);

	@ModifyArg(method = "finalizeSpawn(Lnet/minecraft/world/IServerWorld;Lnet/minecraft/world/DifficultyInstance;Lnet/minecraft/entity/SpawnReason;Lnet/minecraft/entity/ILivingEntityData;Lnet/minecraft/nbt/CompoundNBT;)Lnet/minecraft/entity/ILivingEntityData;",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;<init>(Lnet/minecraft/util/IItemProvider;)V", ordinal = 0))
	private IItemProvider injected(IItemProvider regularItem) {
		return ExtendedItems.URUK_SHIELD.get();
	}
}
