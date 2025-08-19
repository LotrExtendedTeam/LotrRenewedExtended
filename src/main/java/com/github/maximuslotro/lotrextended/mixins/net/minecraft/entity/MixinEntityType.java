package com.github.maximuslotro.lotrextended.mixins.net.minecraft.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;

import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

@Mixin(EntityType.class)
public class MixinEntityType {

	@Shadow
	private ResourceLocation lootTable;

	@SuppressWarnings({"rawtypes", "deprecation"})
	@Inject(method = "getDefaultLootTable()Lnet/minecraft/util/ResourceLocation;", at = @At("HEAD"), cancellable = true)
	public void getDefaultLootTable(CallbackInfoReturnable<ResourceLocation> ci) {
		ResourceLocation resourcelocationtable;
		if (lootTable == null) {
			resourcelocationtable = Registry.ENTITY_TYPE.getKey((EntityType)(Object)this);
		}
		else {
			resourcelocationtable = lootTable;
		}
		if(resourcelocationtable.getNamespace().equals("lotr")) {
			lootTable = new ResourceLocation(LotrExtendedMod.MODID, "entities/" + resourcelocationtable.getPath());
			ci.setReturnValue(lootTable);
		}
	}
}
