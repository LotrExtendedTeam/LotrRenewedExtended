package com.github.maximuslotro.lotrrextended.client.render.entity.model;

import java.util.function.Function;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public abstract class NonLivingModel<T extends Entity> extends Model {
	protected NonLivingModel() {
		this(RenderType::entityCutoutNoCull);
	}

	protected NonLivingModel(Function<ResourceLocation, RenderType> p_i225945_1_) {
		super(p_i225945_1_);
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		ExtendedLog.info("renderToBuffer should not be called! This is a DEV error!");
	}
	abstract void renderExtendedToBuffer(T entity, MatrixStack matrixStack, IVertexBuilder buffer, float ageInTicks, int packedLight, int packedOverlay, float red, float green, float blue, float alpha);

	// https://stackoverflow.com/a/23157705
	public static float scale(final float valueIn, final float baseMin, final float baseMax, final float limitMin, final float limitMax) {
        return ((limitMax - limitMin) * (valueIn - baseMin) / (baseMax - baseMin)) + limitMin;
    }
}
