package com.github.maximuslotro.lotrrextended.client.render.entity;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.client.render.entity.model.ExtendedHorseBoarModel;
import com.github.maximuslotro.lotrrextended.common.entity.passive.ExtendedHorseBoarEntity;
import com.github.maximuslotro.lotrrextended.common.enums.EntityGender;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class ExtendedBoarRenderer extends ExtendedMobRenderer<ExtendedHorseBoarEntity, ExtendedHorseBoarModel<ExtendedHorseBoarEntity>> {
	private static final ResourceLocation MALE_BOAR_LOCATION = new ResourceLocation(LotrExtendedMod.MODID, "textures/entity/animals/boar/male/boar_male.png");
	private static final ResourceLocation FEMALE_BOAR_LOCATION = new ResourceLocation(LotrExtendedMod.MODID, "textures/entity/animals/boar/female/boar_female.png");

	public ExtendedBoarRenderer(EntityRendererManager p_i47198_1_) {
		super(p_i47198_1_, new ExtendedHorseBoarModel<>(0.0f), 0.7F);
	}

	/**
	 * Returns the location of an entity's texture.
	 */
	@Override
	public ResourceLocation getTexture(ExtendedHorseBoarEntity entity) {
		if(entity.getGender() == EntityGender.MALE) {
			return MALE_BOAR_LOCATION;
		}
		if(entity.getGender() == EntityGender.FEMALE) {
			return FEMALE_BOAR_LOCATION;
		}
		return null;
	}
}