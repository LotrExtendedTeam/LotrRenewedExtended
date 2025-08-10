package com.github.maximuslotro.lotrrextended.client.render.entity;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.client.render.entity.model.ExtendedDuckModel;
import com.github.maximuslotro.lotrrextended.common.entity.passive.ExtendedDuckEntity;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedEntityGender;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ExtendedDuckRenderer extends ExtendedMobRenderer<ExtendedDuckEntity, ExtendedDuckModel<ExtendedDuckEntity>> {
	private static final ResourceLocation BOB_DUCK_LOCATION = new ResourceLocation(LotrExtendedMod.MODID, "textures/entity/animals/duck/bob_duck.png");
	private static final ResourceLocation MALE_DUCK_LOCATION = new ResourceLocation(LotrExtendedMod.MODID, "textures/entity/animals/duck/male/male_duck.png");
	private static final ResourceLocation FEMALE_DUCK_LOCATION = new ResourceLocation(LotrExtendedMod.MODID, "textures/entity/animals/duck/female/female_duck.png");
	private static final ResourceLocation DOMESTICATED_DUCK_LOCATION = new ResourceLocation(LotrExtendedMod.MODID, "textures/entity/animals/duck/domesticated_duck.png");

	public ExtendedDuckRenderer(EntityRendererManager p_i4entityRendererManager) {
		super(p_i4entityRendererManager, new ExtendedDuckModel<>(), 0.3F);
	}

	@Override
	public ResourceLocation getTexture(ExtendedDuckEntity duck) {
		if(duck.isDomesticated() || duck.getGender() == ExtendedEntityGender.GENDERLESS) {
			return DOMESTICATED_DUCK_LOCATION;
		}
		if(duck.isBob()) {
			return BOB_DUCK_LOCATION;
		}
		if(duck.getGender() == ExtendedEntityGender.MALE) {
			return MALE_DUCK_LOCATION;
		}
		if(duck.getGender() == ExtendedEntityGender.FEMALE) {
			return FEMALE_DUCK_LOCATION;
		}
		return null;
	}

	@Override
	protected float getBob(ExtendedDuckEntity duck, float p_77044_2_) {
		float f = MathHelper.lerp(p_77044_2_, duck.oFlap, duck.flap);
		float f1 = MathHelper.lerp(p_77044_2_, duck.oFlapSpeed, duck.flapSpeed);
		return (MathHelper.sin(f) + 1.0F) * f1;
	}
}