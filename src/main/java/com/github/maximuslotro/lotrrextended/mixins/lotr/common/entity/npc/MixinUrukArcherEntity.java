package com.github.maximuslotro.lotrrextended.mixins.lotr.common.entity.npc;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import lotr.common.entity.npc.UrukArcherEntity;
import lotr.common.init.ExtendedItems;
import net.minecraft.util.IItemProvider;

/**
 * @reason Need to change Uruk Orc Archer equipment
 * @Mixin (UrukArcherEntity.class)
 * */
@Mixin(UrukArcherEntity.class)
public class MixinUrukArcherEntity {
	/**
	 * @reason Replaced setting the bow with the an Orc Bow
	 * @ModifyArg (method = "finalizeSpawn(Lnet/minecraft/world/IServerWorld;Lnet/minecraft/world/DifficultyInstance;Lnet/minecraft/entity/SpawnReason;Lnet/minecraft/entity/ILivingEntityData;Lnet/minecraft/nbt/CompoundNBT;)Lnet/minecraft/entity/ILivingEntityData;",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;<init>(Lnet/minecraft/util/IItemProvider;)V"), index = 0)
	 * @param regularItem The regular item to be set
	 * @return An orc bow instance
	 * */
	@ModifyArg(method = "finalizeSpawn(Lnet/minecraft/world/IServerWorld;Lnet/minecraft/world/DifficultyInstance;Lnet/minecraft/entity/SpawnReason;Lnet/minecraft/entity/ILivingEntityData;Lnet/minecraft/nbt/CompoundNBT;)Lnet/minecraft/entity/ILivingEntityData;",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;<init>(Lnet/minecraft/util/IItemProvider;)V"), index = 0)
	private IItemProvider injected(IItemProvider regularItem) {
		return ExtendedItems.ORC_BOW.get();
	}
}
