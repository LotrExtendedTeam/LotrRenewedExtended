package com.github.maximuslotro.lotrrextended.mixins.lotr.client.render.entity.model.armor;

import java.util.function.Supplier;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import lotr.client.render.entity.model.armor.ExtendedArnorHelmetModel;
import lotr.client.render.entity.model.armor.ExtendedModelGoldenTaurethrimHelmet;
import lotr.client.render.entity.model.armor.ExtendedModelGundabadUrukHelmet;
import lotr.client.render.entity.model.armor.ExtendedModelLeatherHat;
import lotr.client.render.entity.model.armor.ExtendedModelMorgulHelmet;
import lotr.client.render.entity.model.armor.ExtendedModelTaurethrimChieftanHelmet;
import lotr.client.render.entity.model.armor.ExtendedModelWargHat;
import lotr.client.render.entity.model.armor.LOTRArmorModels;
import lotr.client.render.entity.model.armor.SpecialArmorModelHolder.SpecialArmorModelFactory;
import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRItems;
import net.minecraft.item.Item;

@Mixin(LOTRArmorModels.class)
public class MixinLOTRArmorModels {

	@Shadow(remap = false)
	private static void addSpecialArmorModel(Supplier<Item> itemSup, SpecialArmorModelFactory factory) {}

	@Inject(method = "setup()V", at = @At("RETURN"), remap = false)
	private static void setup(CallbackInfo ci) {
		// Overwrite renewed arnor helmet model
		addSpecialArmorModel(LOTRItems.ARNOR_HELMET, ExtendedArnorHelmetModel::new);

		// Add Extended armor models
		addSpecialArmorModel(ExtendedItems.MORGUL_HELMET, ExtendedModelMorgulHelmet::new);
		addSpecialArmorModel(ExtendedItems.TAURETHRIM_CHIEFTAIN_HELMET, ExtendedModelTaurethrimChieftanHelmet::new);
		addSpecialArmorModel(ExtendedItems.GOLDEN_TAURETHRIM_HELMET, ExtendedModelGoldenTaurethrimHelmet::new);
		addSpecialArmorModel(ExtendedItems.LEATHER_HAT, ExtendedModelLeatherHat::new);
		addSpecialArmorModel(ExtendedItems.BLACK_WARG_HELMET, ExtendedModelWargHat::new);
		addSpecialArmorModel(ExtendedItems.BROWN_WARG_HELMET, ExtendedModelWargHat::new);
		addSpecialArmorModel(ExtendedItems.GRAY_WARG_HELMET, ExtendedModelWargHat::new);
		addSpecialArmorModel(ExtendedItems.SILVER_WARG_HELMET, ExtendedModelWargHat::new);
		addSpecialArmorModel(ExtendedItems.WHITE_WARG_HELMET, ExtendedModelWargHat::new);
		addSpecialArmorModel(ExtendedItems.GUNDABAD_URUK_HELMET, ExtendedModelGundabadUrukHelmet::new);
	}
}
