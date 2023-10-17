package com.github.maximuslotro.lotroExtended.mixins.common.init;

import lotr.client.render.entity.model.armor.LOTRModelMorgulHelmet;
import lotr.client.render.entity.model.armor.*;
import lotr.client.render.entity.model.armor.LOTRArmorModels;
import lotr.client.render.entity.model.armor.SpecialArmorModelHolder;
import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRItems;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Mixin(LOTRArmorModels.class)
public class MixinLOTRArmorModels {
    @Final
    private static final Map<Item, SpecialArmorModelHolder> specialArmorModels = new HashMap<>();
    private static boolean hasSetup = false;

    static {
        if (!hasSetup) {
            addSpecialArmorModel(() -> ExtendedItems.MORGUL_HELMET.get(), LOTRModelMorgulHelmet::new);
            addSpecialArmorModel(LOTRItems.GONDOR_HELMET, GondorHelmetModel::new);
            addSpecialArmorModel(LOTRItems.LINDON_HELMET, HighElvenHelmetModel::new);
            addSpecialArmorModel(LOTRItems.RIVENDELL_HELMET, HighElvenHelmetModel::new);
            addSpecialArmorModel(LOTRItems.GALADHRIM_HELMET, GaladhrimHelmetModel::new);
            addSpecialArmorModel(LOTRItems.HARAD_WARLORD_HELMET, HaradWarlordHelmetModel::new);
            addSpecialArmorModel(LOTRItems.UMBAR_HELMET, UmbarHelmetModel::new);
            addSpecialArmorModel(LOTRItems.URUK_HELMET, UrukHelmetModel::new);
            addSpecialArmorModel(LOTRItems.URUK_BERSERKER_HELMET, UrukHelmetModel::new);
            addSpecialArmorModel(LOTRItems.DORWINION_ELVEN_HELMET, DorwinionElvenHelmetModel::new);
            addSpecialArmorModel(LOTRItems.ROHAN_MARSHAL_HELMET, RohanMarshalHelmetModel::new);
            addSpecialArmorModel(LOTRItems.WINGED_GONDOR_HELMET, WingedGondorHelmetModel::new);
            addSpecialArmorModel(LOTRItems.HARNENNOR_HELMET, HarnennorHelmetModel::new);
            addSpecialArmorModel(LOTRItems.HARNENNOR_CHESTPLATE, HarnennorChestplateModel::new);
            addSpecialArmorModel(LOTRItems.DOL_AMROTH_HELMET, DolAmrothHelmetModel::new);
            addSpecialArmorModel(LOTRItems.DOL_AMROTH_CHESTPLATE, DolAmrothChestplateModel::new);
            addSpecialArmorModel(LOTRItems.ARNOR_HELMET, ArnorHelmetModel::new);
            addSpecialArmorModel(LOTRItems.FINE_PLATE, PlateOnHeadModel::new);
            addSpecialArmorModel(LOTRItems.STONEWARE_PLATE, PlateOnHeadModel::new);
            addSpecialArmorModel(LOTRItems.WOODEN_PLATE, PlateOnHeadModel::new);
            hasSetup = true;
        }
    }

    @Inject(method = "setup()V", at = @At("RETURN"), remap = false)
    private static void setup(CallbackInfo ci) {
    }

    private static void addSpecialArmorModel(Supplier<Item> itemSup, SpecialArmorModelHolder.SpecialArmorModelFactory factory) {
        specialArmorModels.put(itemSup.get(), new SpecialArmorModelHolder(factory));
    }
}
