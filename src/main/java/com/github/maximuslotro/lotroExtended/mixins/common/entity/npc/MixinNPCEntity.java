package com.github.maximuslotro.lotroExtended.mixins.common.entity.npc;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import lotr.common.entity.npc.NPCEntity;
import lotr.common.entity.npc.WargEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootTable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

@Mixin(NPCEntity.class)
public class MixinNPCEntity{

	@Inject(method = "dropFromLootTable(Lnet/minecraft/util/DamageSource;Z)V", at = @At("HEAD"), remap=true, cancellable = true)
	public void overrideWolfDrops(DamageSource source, boolean attackedRecently, CallbackInfo info) {
		if(((LivingEntity)(Object)(this)) instanceof WargEntity) {
			info.cancel();
			WargEntity entity = ((WargEntity)(Object)(this));
			ResourceLocation resourcelocation = entity.getLootTable();
			LootTable loottable = entity.level.getServer().getLootTables().get(resourcelocation);
			LootContext.Builder lootcontext$builder = entity.createLootContext(attackedRecently, source);
			LootContext ctx = lootcontext$builder.create(LootParameterSets.ENTITY);
			loottable.getRandomItems(ctx).forEach(entity::spawnAtLocation);
			System.out.println(entity.getWargType().getId());
			System.out.println(resourcelocation.toString());
		}
	}
}
