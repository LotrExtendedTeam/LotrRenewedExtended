package com.github.maximuslotro.lotrrextended.client.render.entity;

import com.github.maximuslotro.lotrrextended.common.entity.item.ExtendedBoatEntity;

import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.util.ResourceLocation;

public class ExtendedBoatRenderer extends BoatRenderer {

	public ExtendedBoatRenderer(EntityRendererManager pManager) {
		super(pManager);
	}

	@Override
	public ResourceLocation getTextureLocation(BoatEntity pEntity) {
		return ((ExtendedBoatEntity)pEntity).getBoatTypeExtended().getTextureLocation();
	}
}