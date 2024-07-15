package com.github.maximuslotro.lotrrextended.client.render.entity;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.client.render.entity.model.ExtendedDuckModel;
import com.github.maximuslotro.lotrrextended.common.entity.passive.ExtendedDuckEntity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ExtendedDuckRenderer extends MobRenderer<ExtendedDuckEntity, ExtendedDuckModel<ExtendedDuckEntity>> {
	private static final ResourceLocation BOB_DUCK_LOCATION = new ResourceLocation(LotrExtendedMod.MODID, "textures/entity/duck/bob_duck.png");
	private static final ResourceLocation MALE_DUCK_LOCATION = new ResourceLocation(LotrExtendedMod.MODID, "textures/entity/duck/male/male_duck.png");
	private static final ResourceLocation FEMALE_DUCK_LOCATION = new ResourceLocation(LotrExtendedMod.MODID, "textures/entity/duck/female/female_duck.png");

	public ExtendedDuckRenderer(EntityRendererManager p_i4entityRendererManager) {
		super(p_i4entityRendererManager, new ExtendedDuckModel<>(), 0.3F);
	}

	public ResourceLocation getTextureLocation(ExtendedDuckEntity entity) {
		return MALE_DUCK_LOCATION;
	}

	protected float getBob(ExtendedDuckEntity entity, float p_77044_2_) {
		float f = MathHelper.lerp(p_77044_2_, entity.oFlap, entity.flap);
		float f1 = MathHelper.lerp(p_77044_2_, entity.oFlapSpeed, entity.flapSpeed);
		return (MathHelper.sin(f) + 1.0F) * f1;
	}
}
