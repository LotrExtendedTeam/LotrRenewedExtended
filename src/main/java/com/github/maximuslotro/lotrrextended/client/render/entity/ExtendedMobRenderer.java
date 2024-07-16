package com.github.maximuslotro.lotrrextended.client.render.entity;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class ExtendedMobRenderer<T extends MobEntity, M extends EntityModel<T>> extends LivingRenderer<T, M> {
	private static final ResourceLocation MISSING_TEXTURE_LOCATION = new ResourceLocation(LotrExtendedMod.MODID, "textures/entity/missing.png");

	public ExtendedMobRenderer(EntityRendererManager entityRenderDispatcher, M model, float shadowRadius) {
		super(entityRenderDispatcher, model, shadowRadius);
	}

	@Override
	public ResourceLocation getTextureLocation(T entity) {
		ResourceLocation texture = getTexture(entity);
		if(texture==null) {
			return MISSING_TEXTURE_LOCATION;
		}
		return texture;
	}

	@Nullable
	public abstract ResourceLocation getTexture(T entity);
}
