package com.github.maximuslotro.lotrrextended.client.render.entity.model;

import com.google.common.collect.ImmutableList;

import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ExtendedDuckModel<T extends Entity> extends AgeableModel<T> {
	private final ModelRenderer body;
	private final ModelRenderer head;
	private final ModelRenderer beak;
	private final ModelRenderer leg0;
	private final ModelRenderer leg1;
	private final ModelRenderer wing0;
	private final ModelRenderer wing0_r1;
	private final ModelRenderer wing1;
	private final ModelRenderer wing1_r1;

	public ExtendedDuckModel() {
		texWidth = 64;
		texHeight = 64;

		body = new ModelRenderer(this);
		body.setPos(0.0F, 16.0F, 0.0F);
		setRotationAngle(body, 1.5708F, 0.0F, 0.0F);
		body.texOffs(0, 0).addBox(-3.0F, -4.0F, -4.0F, 6.0F, 9.0F, 5.0F, 0.0F, false);

		head = new ModelRenderer(this);
		head.setPos(0.0F, 15.0F, -4.0F);
		head.texOffs(16, 18).addBox(-2.0F, -5.0F, 0.0F, 4.0F, 5.0F, 3.0F, 0.0F, false);

		beak = new ModelRenderer(this);
		beak.setPos(0.0F, 0.0F, 0.0F);
		head.addChild(beak);
		beak.texOffs(9, 18).addBox(-1.5F, -3.0F, -2.0F, 3.0F, 1.0F, 2.0F, 0.0F, false);

		leg0 = new ModelRenderer(this);
		leg0.setPos(-2.0F, 19.0F, 1.0F);
		leg0.texOffs(22, 0).addBox(-1.0F, 1.0F, -3.0F, 3.0F, 4.0F, 3.0F, 0.0F, false);

		leg1 = new ModelRenderer(this);
		leg1.setPos(1.0F, 19.0F, 1.0F);
		leg1.texOffs(24, 7).addBox(-1.0F, 1.0F, -3.0F, 3.0F, 4.0F, 3.0F, 0.0F, false);

		wing0 = new ModelRenderer(this);
		wing0.setPos(-3.0F, 13.0F, 0.0F);

		wing0_r1 = new ModelRenderer(this);
		wing0_r1.setPos(3.0F, 12.0F, 0.0F);
		wing0.addChild(wing0_r1);
		setRotationAngle(wing0_r1, -0.2182F, 0.0F, 0.0F);
		wing0_r1.texOffs(15, 7).addBox(-4.0F, -10.0F, -4.0F, 1.0F, 4.0F, 7.0F, 0.0F, false);

		wing1 = new ModelRenderer(this);
		wing1.setPos(3.0F, 13.0F, 0.0F);

		wing1_r1 = new ModelRenderer(this);
		wing1_r1.setPos(-3.0F, 12.0F, 0.0F);
		wing1.addChild(wing1_r1);
		setRotationAngle(wing1_r1, -0.2182F, 0.0F, 0.0F);
		wing1_r1.texOffs(0, 14).addBox(3.0F, -10.0F, -4.0F, 1.0F, 4.0F, 7.0F, 0.0F, false);
	}

	protected Iterable<ModelRenderer> headParts() {
		return ImmutableList.of(this.head, this.beak);
	}

	protected Iterable<ModelRenderer> bodyParts() {
		return ImmutableList.of(this.body, this.leg0, this.leg1, this.wing0, this.wing1);
	}

	public void setupAnim(T p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {
		this.head.xRot = p_225597_6_ * ((float) Math.PI / 180F);
		this.head.yRot = p_225597_5_ * ((float) Math.PI / 180F);
		this.beak.xRot = this.head.xRot;
		this.beak.yRot = this.head.yRot;
		this.body.xRot = ((float) Math.PI / 2F);
		this.leg0.xRot = MathHelper.cos(p_225597_2_ * 0.6662F) * 1.4F * p_225597_3_;
		this.leg1.xRot = MathHelper.cos(p_225597_2_ * 0.6662F + (float) Math.PI) * 1.4F * p_225597_3_;
		this.wing0.zRot = p_225597_4_;
		this.wing1.zRot = -p_225597_4_;
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}