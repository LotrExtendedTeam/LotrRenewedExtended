package com.github.maximuslotro.lotrextended.mixins.lotr.common.entity.npc;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import lotr.common.entity.npc.UrukEntity;
import lotr.common.init.ExtendedItems;
import net.minecraft.util.IItemProvider;

/**
 * Need to change Uruk Orc equipment
 * @Mixin (UrukArcherEntity.class)
 * */
@Mixin(UrukEntity.class)
public class MixinUrukEntity{

	@ModifyArg(method = "finalizeSpawn(Lnet/minecraft/world/IServerWorld;Lnet/minecraft/world/DifficultyInstance;Lnet/minecraft/entity/SpawnReason;Lnet/minecraft/entity/ILivingEntityData;Lnet/minecraft/nbt/CompoundNBT;)Lnet/minecraft/entity/ILivingEntityData;",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;<init>(Lnet/minecraft/util/IItemProvider;)V", ordinal = 4))
	private IItemProvider injected(IItemProvider regularItem) {
		return ExtendedItems.URUK_SHIELD.get();
	}
}
