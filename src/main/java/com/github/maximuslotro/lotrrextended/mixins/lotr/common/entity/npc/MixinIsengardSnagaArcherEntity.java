package com.github.maximuslotro.lotrrextended.mixins.lotr.common.entity.npc;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import lotr.common.entity.npc.IsengardSnagaArcherEntity;
import lotr.common.entity.npc.SpawnEquipmentTable;
import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRItems;
import net.minecraft.util.IItemProvider;
/**
 * Need to change Isengard Orc Archer equipment
 * @Mixin (IsengardSnagaArcherEntity.class)
 * */
@Mixin(IsengardSnagaArcherEntity.class)
public class MixinIsengardSnagaArcherEntity{

	/**
	 * Need to add bone dagger to equipment pool
	 * @Shadow (remap=false)
	 * @Final Mark the field to be essentially final
	 * @Mutable Tell Mixin that it can modify the Final field
	 * */
	@Final
	@Mutable
	@Shadow(remap=false)
	private static final SpawnEquipmentTable DAGGERS = SpawnEquipmentTable.of(LOTRItems.STONE_DAGGER, LOTRItems.IRON_DAGGER,
			LOTRItems.BRONZE_DAGGER, LOTRItems.URUK_DAGGER, ExtendedItems.BONE_DAGGER);
	
	/**
	 * @reason Replaced setting the bow with the an Orc Bow
	 * @ModifyArg (method = "finalizeSpawn(Lnet/minecraft/world/IServerWorld;Lnet/minecraft/world/DifficultyInstance;Lnet/minecraft/entity/SpawnReason;Lnet/minecraft/entity/ILivingEntityData;Lnet/minecraft/nbt/CompoundNBT;)Lnet/minecraft/entity/ILivingEntityData;",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;&lt;init&gt;(Lnet/minecraft/util/IItemProvider;)V"), index = 0)
	 * @param regularItem The regular item to be set
	 * @return An orc bow instance
	 * */
	@ModifyArg(method = "finalizeSpawn(Lnet/minecraft/world/IServerWorld;Lnet/minecraft/world/DifficultyInstance;Lnet/minecraft/entity/SpawnReason;Lnet/minecraft/entity/ILivingEntityData;Lnet/minecraft/nbt/CompoundNBT;)Lnet/minecraft/entity/ILivingEntityData;",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;<init>(Lnet/minecraft/util/IItemProvider;)V"), index = 0)
	private IItemProvider injected(IItemProvider regularItem) {
		return ExtendedItems.ORC_BOW.get();
	}
}
