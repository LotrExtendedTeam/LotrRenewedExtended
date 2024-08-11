package com.github.maximuslotro.lotrrextended.client.render.entity.model;

import java.util.function.Function;

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

	abstract void renderExtendedToBuffer(T entity, MatrixStack matrixStack, IVertexBuilder buffer, float ageInTicks, int packedLight, int packedOverlay, float red, float green, float blue, float alpha);
}
