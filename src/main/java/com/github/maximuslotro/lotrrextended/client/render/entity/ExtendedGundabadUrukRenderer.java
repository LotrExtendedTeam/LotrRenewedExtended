package com.github.maximuslotro.lotrrextended.client.render.entity;

import lotr.client.render.RandomTextureVariants;
import lotr.client.render.entity.LargeOrcRenderer;
import lotr.common.entity.npc.OrcEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class ExtendedGundabadUrukRenderer extends LargeOrcRenderer {
	private static final RandomTextureVariants SKINS = RandomTextureVariants.loadSkinsList("lotr", "textures/entity/orc/orc");

	public ExtendedGundabadUrukRenderer(EntityRendererManager mgr) {
		super(mgr);
	}

	@Override
	public ResourceLocation getTextureLocation(OrcEntity orc) {
		return SKINS.getRandomSkin(orc);
	}
}

