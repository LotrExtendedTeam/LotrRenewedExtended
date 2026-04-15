package com.github.maximuslotro.lotrextended.mixins.lotr.client.render.entity.model.armor;

import java.util.function.Supplier;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import lotr.client.render.entity.model.armor.ExtendedArnorHelmetModel;
import lotr.client.render.entity.model.armor.ExtendedModelGalvornHelmet;
import lotr.client.render.entity.model.armor.ExtendedModelGoldenTaurethrimHelmet;
import lotr.client.render.entity.model.armor.ExtendedModelGundabadUrukHelmet;
import lotr.client.render.entity.model.armor.ExtendedModelLeatherHat;
import lotr.client.render.entity.model.armor.ExtendedModelMorgulVassalChestplate;
import lotr.client.render.entity.model.armor.ExtendedModelMorgulVassalHelmet;
import lotr.client.render.entity.model.armor.ExtendedModelMorgulVassalLeggings;
import lotr.client.render.entity.model.armor.ExtendedModelTaurethrimChieftanHelmet;
import lotr.client.render.entity.model.armor.ExtendedModelWargHat;
import lotr.client.render.entity.model.armor.HighElvenHelmetModel;
import lotr.client.render.entity.model.armor.LOTRArmorModels;
import lotr.client.render.entity.model.armor.SpecialArmorModelHolder.SpecialArmorModelFactory;
import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRItems;
import net.minecraft.item.Item;
/**
 * 
 * Need to register our own custom armor pieces
 * @Mixin (LOTRArmorModels.class)
 * */
@Mixin(LOTRArmorModels.class)
public class MixinLOTRArmorModels {

	/**
	 * Method to register new special armor renderers
	 * @Shadow (remap = false)
	 * @param itemSup A supplier of the renderable item to register
	 * @param factory The actual model renderer that will be used to render the armor
	 * */
	@Shadow(remap = false)
	private static void addSpecialArmorModel(Supplier<Item> itemSup, SpecialArmorModelFactory factory) {}

	/**
	 * @reason Since Renewed has a built-in custom armor rendering system, just register our armor there
	 * @Inject (method = "setup()V", at = @At("RETURN"), remap = false)
	 * @param ci The local CallbackInfo instance attached to this injection
	 * */
	@SuppressWarnings("unchecked")
	@Inject(method = "setup()V", at = @At("RETURN"), remap = false)
	private static void setup(CallbackInfo ci) {
		// Overwrite renewed arnor helmet model
		addSpecialArmorModel(LOTRItems.ARNOR_HELMET, ExtendedArnorHelmetModel::new);

		// Add Extended armor models
		//addSpecialArmorModel(ExtendedItems.MORGUL_HELMET, ExtendedModelMorgulHelmet::new);
		addSpecialArmorModel(ExtendedItems.TAURETHRIM_CHIEFTAIN_HELMET, ExtendedModelTaurethrimChieftanHelmet::new);
		addSpecialArmorModel(ExtendedItems.GOLDEN_TAURETHRIM_HELMET, ExtendedModelGoldenTaurethrimHelmet::new);
		addSpecialArmorModel(ExtendedItems.LEATHER_HAT, ExtendedModelLeatherHat::new);
		addSpecialArmorModel(ExtendedItems.BLACK_WARG_HELMET, ExtendedModelWargHat::new);
		addSpecialArmorModel(ExtendedItems.BROWN_WARG_HELMET, ExtendedModelWargHat::new);
		addSpecialArmorModel(ExtendedItems.GRAY_WARG_HELMET, ExtendedModelWargHat::new);
		addSpecialArmorModel(ExtendedItems.SILVER_WARG_HELMET, ExtendedModelWargHat::new);
		addSpecialArmorModel(ExtendedItems.WHITE_WARG_HELMET, ExtendedModelWargHat::new);
		addSpecialArmorModel(ExtendedItems.GUNDABAD_URUK_HELMET, ExtendedModelGundabadUrukHelmet::new);
		addSpecialArmorModel(ExtendedItems.IMLADRIS_GUARD_HELMET, HighElvenHelmetModel::new);
		addSpecialArmorModel(ExtendedItems.EREGION_HELMET, HighElvenHelmetModel::new);
		addSpecialArmorModel(ExtendedItems.GALVORN_HELMET, ExtendedModelGalvornHelmet::new);
		addSpecialArmorModel(ExtendedItems.MORGUL_VASSAL_HELMET, ExtendedModelMorgulVassalHelmet::new);
		addSpecialArmorModel(ExtendedItems.MORGUL_VASSAL_CHESTPLATE, ExtendedModelMorgulVassalChestplate::new);
		addSpecialArmorModel(ExtendedItems.MORGUL_VASSAL_LEGGINGS, ExtendedModelMorgulVassalLeggings::new);
	}
}
