package com.github.maximuslotro.lotrrextended.client.render.entity;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.client.render.entity.model.ExtendedHedgehogModel;
import com.github.maximuslotro.lotrrextended.common.entity.passive.ExtendedHedgehogEntity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ExtendedHedgehogRenderer extends ExtendedMobRenderer<ExtendedHedgehogEntity, ExtendedHedgehogModel<ExtendedHedgehogEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(LotrExtendedMod.MODID, "textures/entity/animals/hedgehog/hedgehog.png");

	public ExtendedHedgehogRenderer(EntityRendererManager p_i4entityRendererManager) {
		super(p_i4entityRendererManager, new ExtendedHedgehogModel<>(), 0.3F);
	}

	@Override
	public ResourceLocation getTexture(ExtendedHedgehogEntity duck) {
		return LOCATION;
	}
}