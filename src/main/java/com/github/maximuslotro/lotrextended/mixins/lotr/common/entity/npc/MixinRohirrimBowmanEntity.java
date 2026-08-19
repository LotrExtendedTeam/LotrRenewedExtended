package com.github.maximuslotro.lotrextended.mixins.lotr.common.entity.npc;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedItems;

import lotr.common.entity.npc.RohirrimBowmanEntity;
import lotr.common.entity.npc.RohirrimWarriorEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

@Mixin(RohirrimBowmanEntity.class)
public class MixinRohirrimBowmanEntity extends RohirrimWarriorEntity{

	public MixinRohirrimBowmanEntity(EntityType<? extends RohirrimBowmanEntity> type, World w) {
		super(type, w);
	}

	@Inject(method = "finalizeSpawn(Lnet/minecraft/world/IServerWorld;Lnet/minecraft/world/DifficultyInstance;Lnet/minecraft/entity/SpawnReason;Lnet/minecraft/entity/ILivingEntityData;Lnet/minecraft/nbt/CompoundNBT;)Lnet/minecraft/entity/ILivingEntityData;",
			at = @At("TAIL"))
	public void finalizeSpawn$Extended(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT dataTag, CallbackInfoReturnable<ILivingEntityData> cir) {
		this.npcItemsInv.setRangedWeapon(new ItemStack(ExtendedItems.ROHAN_BOW.get()));
	}
}
