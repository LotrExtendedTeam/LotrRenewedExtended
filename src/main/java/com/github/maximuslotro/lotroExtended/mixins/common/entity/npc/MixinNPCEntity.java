package com.github.maximuslotro.lotroExtended.mixins.common.entity.npc;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import lotr.common.entity.npc.NPCEntity;
import lotr.common.entity.npc.WargEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootTable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;

@Mixin(NPCEntity.class)
public class MixinNPCEntity{

	@Inject(method = "dropFromLootTable(Lnet/minecraft/util/DamageSource;Z)V", at = @At("HEAD"), remap=true, cancellable = true)
	public void overrideWolfDrops(DamageSource source, boolean attackedRecently, CallbackInfo info) {
		if(((LivingEntity)(Object)(this)) instanceof WargEntity) {
			info.cancel();
			WargEntity entity = ((WargEntity)(Object)(this));
			ResourceLocation resourcelocation;
			if(entity.getWargType().getId()==1) {
				resourcelocation = new ResourceLocation("lotr", "entities/common/wargs/grey_warg");
			}else if(entity.getWargType().getId()==2) {
				resourcelocation = new ResourceLocation("lotr", "entities/common/wargs/black_warg");
			}else if(entity.getWargType().getId()==3) {
				resourcelocation = new ResourceLocation("lotr", "entities/common/wargs/silver_warg");
			}else if(entity.getWargType().getId()==4) {
				resourcelocation = new ResourceLocation("lotr", "entities/common/wargs/white_warg");
			}else {
				resourcelocation = entity.getLootTable();
			}
			LootTable loottable = entity.level.getServer().getLootTables().get(resourcelocation);
			LootContext.Builder lootcontext$builder = entity.createLootContext(attackedRecently, source);
			LootContext ctx = lootcontext$builder.create(LootParameterSets.ENTITY);
			loottable.getRandomItems(ctx).forEach(entity::spawnAtLocation);
		}
	}
}
