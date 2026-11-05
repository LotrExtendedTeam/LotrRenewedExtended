package com.github.maximuslotro.lotrextended.mixins.lotr.common.entity.npc;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedItems;

import lotr.common.entity.npc.SpawnEquipmentTable;
import lotr.common.entity.npc.UrukEntity;
import lotr.common.init.LOTRItems;
import net.minecraft.util.IItemProvider;

/**
 * Need to change Uruk Orc equipment
 * @Mixin (UrukArcherEntity.class)
 * */
@Mixin(UrukEntity.class)
public class MixinUrukEntity{

	/**
	 * Need to add new equipment to the equipment pool
	 * @Shadow (remap=false)
	 * @Final Mark the field to be essentially final
	 * @Mutable Tell Mixin that it can modify the Final field
	 * */
	@Final
	@Mutable
	@Shadow(remap=false)
	private static final SpawnEquipmentTable WEAPONS = SpawnEquipmentTable.of(
			LOTRItems.URUK_CLEAVER, LOTRItems.URUK_CLEAVER,LOTRItems.URUK_CLEAVER, LOTRItems.URUK_CLEAVER,
			ExtendedItems.URUK_PIKE, ExtendedItems.URUK_PIKE, 
			LOTRItems.URUK_DAGGER, LOTRItems.URUK_DAGGER, 
			LOTRItems.URUK_SPEAR);

	@ModifyArg(method = "finalizeSpawn(Lnet/minecraft/world/IServerWorld;Lnet/minecraft/world/DifficultyInstance;Lnet/minecraft/entity/SpawnReason;Lnet/minecraft/entity/ILivingEntityData;Lnet/minecraft/nbt/CompoundNBT;)Lnet/minecraft/entity/ILivingEntityData;",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;<init>(Lnet/minecraft/util/IItemProvider;)V", ordinal = 4))
	private IItemProvider injected(IItemProvider regularItem) {
		return ExtendedItems.URUK_SHIELD.get();
	}
}
