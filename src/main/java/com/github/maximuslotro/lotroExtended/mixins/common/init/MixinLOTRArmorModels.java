package com.github.maximuslotro.lotroExtended.mixins.common.init;

import lotr.client.render.entity.model.armor.LOTRModelMorgulHelmet;
import lotr.client.render.entity.model.armor.*;
import lotr.client.render.entity.model.armor.LOTRArmorModels;
import lotr.client.render.entity.model.armor.SpecialArmorModelHolder.SpecialArmorModelFactory;
import lotr.common.init.ExtendedItems;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Supplier;

@Mixin(LOTRArmorModels.class)
public class MixinLOTRArmorModels {
	
	@Shadow(remap = false)
	private static void addSpecialArmorModel(Supplier<Item> itemSup, SpecialArmorModelFactory factory) {}

    @Inject(method = "setup()V", at = @At("RETURN"), remap = false)
    private static void setup(CallbackInfo ci) {
        addSpecialArmorModel(ExtendedItems.MORGUL_HELMET, LOTRModelMorgulHelmet::new);
        addSpecialArmorModel(ExtendedItems.TAURETHRIM_CHIEFTAN_HELMET, LOTRModelTaurethrimChieftanHelmet::new);
        addSpecialArmorModel(ExtendedItems.GOLDEN_TAURETHRIM_HELMET, LOTRModelGoldenTaurethrimHelmet::new);
    }
}
