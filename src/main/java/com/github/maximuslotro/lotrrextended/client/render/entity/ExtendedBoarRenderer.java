package com.github.maximuslotro.lotrrextended.client.render.entity;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.common.entity.passive.ExtendedHorseBoarEntity;
import com.github.maximuslotro.lotrrextended.common.enums.EntityGender;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.client.renderer.entity.model.PigModel;
import net.minecraft.util.ResourceLocation;

public class ExtendedBoarRenderer extends ExtendedMobRenderer<ExtendedHorseBoarEntity, PigModel<ExtendedHorseBoarEntity>> {
	private static final ResourceLocation MALE_BOAR_LOCATION = new ResourceLocation(LotrExtendedMod.MODID, "textures/entity/animals/boar/male/boar_male.png");
	private static final ResourceLocation FEMALE_BOAR_LOCATION = new ResourceLocation(LotrExtendedMod.MODID, "textures/entity/animals/boar/female/boar_female.png");

	public ExtendedBoarRenderer(EntityRendererManager p_i47198_1_) {
		super(p_i47198_1_, new PigModel<>(), 0.7F);
		this.addLayer(new SaddleLayer<>(this, new PigModel<>(0.5F), new ResourceLocation("textures/entity/pig/pig_saddle.png")));
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