package com.github.maximuslotro.lotrrextended.client.render.entity;

import java.util.Map;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.client.render.entity.layers.ExtendedBoarArmorLayer;
import com.github.maximuslotro.lotrrextended.client.render.entity.model.ExtendedHorseBoarModel;
import com.github.maximuslotro.lotrrextended.common.entity.passive.ExtendedHorseBoarEntity;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedBoarColors;
import com.google.common.collect.Maps;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;

public class ExtendedBoarRenderer extends ExtendedMobRenderer<ExtendedHorseBoarEntity, ExtendedHorseBoarModel<ExtendedHorseBoarEntity>> {
	private static final Map<ExtendedBoarColors, ResourceLocation> LOCATION_BY_VARIANT = Util.make(Maps.newEnumMap(ExtendedBoarColors.class), (p_239384_0_) -> {
	      p_239384_0_.put(ExtendedBoarColors.REGULAR, new ResourceLocation(LotrExtendedMod.MODID, "textures/entity/animals/boar/boar1.png"));
	      p_239384_0_.put(ExtendedBoarColors.REGULAR_LIGHT, new ResourceLocation(LotrExtendedMod.MODID, "textures/entity/animals/boar/boar5.png"));
	      p_239384_0_.put(ExtendedBoarColors.REGULAR_VERY_LIGHT, new ResourceLocation(LotrExtendedMod.MODID, "textures/entity/animals/boar/boar2.png"));
	      p_239384_0_.put(ExtendedBoarColors.DARKER, new ResourceLocation(LotrExtendedMod.MODID, "textures/entity/animals/boar/boar3.png"));
	      p_239384_0_.put(ExtendedBoarColors.DARK, new ResourceLocation(LotrExtendedMod.MODID, "textures/entity/animals/boar/boar4.png"));
	   });

	public ExtendedBoarRenderer(EntityRendererManager p_i47198_1_) {
		super(p_i47198_1_, new ExtendedHorseBoarModel<>(0.0f), 0.7F);
		this.addLayer(new ExtendedBoarArmorLayer(this));
	}

	/**
	 * Returns the location of an entity's texture.
	 */
	@Override
	public ResourceLocation getTexture(ExtendedHorseBoarEntity entity) {
		return LOCATION_BY_VARIANT.get(entity.getVariant());
	}
}