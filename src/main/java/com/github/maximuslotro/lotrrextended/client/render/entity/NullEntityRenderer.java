package com.github.maximuslotro.lotrrextended.client.render.entity;

import net.minecraft.client.renderer.culling.ClippingHelper;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class NullEntityRenderer extends EntityRenderer<Entity>{

	protected NullEntityRenderer(EntityRendererManager p_i46179_1_) {
		super(p_i46179_1_);
	}

	public boolean shouldRender(Entity pLivingEntity, ClippingHelper pCamera, double pCamX, double pCamY, double pCamZ) {
		return false;
	}

	@Override
	public ResourceLocation getTextureLocation(Entity pEntity) {
		return null;
	}

}
