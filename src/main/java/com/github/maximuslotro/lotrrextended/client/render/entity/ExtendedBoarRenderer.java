package com.github.maximuslotro.lotrrextended.client.render.entity;

import com.github.maximuslotro.lotrrextended.common.entity.passive.ExtendedBoarEntity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.client.renderer.entity.model.PigModel;
import net.minecraft.util.ResourceLocation;

public class ExtendedBoarRenderer extends MobRenderer<ExtendedBoarEntity, PigModel<ExtendedBoarEntity>> {
	private static final ResourceLocation PIG_LOCATION = new ResourceLocation("textures/entity/pig/pig.png");

	public ExtendedBoarRenderer(EntityRendererManager p_i47198_1_) {
		super(p_i47198_1_, new PigModel<>(), 0.7F);
		this.addLayer(new SaddleLayer<>(this, new PigModel<>(0.5F), new ResourceLocation("textures/entity/pig/pig_saddle.png")));
	}

	/**
	 * Returns the location of an entity's texture.
	 */
	@Override
	public ResourceLocation getTextureLocation(ExtendedBoarEntity pEntity) {
		return PIG_LOCATION;
	}
}