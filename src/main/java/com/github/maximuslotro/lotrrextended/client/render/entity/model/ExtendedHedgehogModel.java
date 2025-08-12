package com.github.maximuslotro.lotrrextended.client.render.entity.model;

import com.google.common.collect.ImmutableList;

import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ExtendedHedgehogModel<T extends Entity> extends AgeableModel<T> {
	private final ModelRenderer body;
	private final ModelRenderer needle_r1;
	private final ModelRenderer needle_r2;
	private final ModelRenderer needle_r3;
	private final ModelRenderer needle_r4;
	private final ModelRenderer needle_r5;
	private final ModelRenderer needle_r6;
	private final ModelRenderer needle_r7;
	private final ModelRenderer needle_r8;
	private final ModelRenderer needle_r9;
	private final ModelRenderer needle_r10;
	private final ModelRenderer needle_r11;
	private final ModelRenderer needle_r12;
	private final ModelRenderer needle_r13;
	private final ModelRenderer needle_r14;
	private final ModelRenderer needle_r15;
	private final ModelRenderer needle_r16;
	private final ModelRenderer needle_r17;
	private final ModelRenderer head;
	private final ModelRenderer Snout;
	private final ModelRenderer RightEar;
	private final ModelRenderer Ear1_r1;
	private final ModelRenderer LeftEar;
	private final ModelRenderer Ear0_r1;
	private final ModelRenderer leg0;
	private final ModelRenderer leg1;
	private final ModelRenderer leg2;
	private final ModelRenderer leg3;

	public ExtendedHedgehogModel() {
		texWidth = 32;
		texHeight = 32;

		body = new ModelRenderer(this);
		body.setPos(0.0F, 23.0F, 0.0F);
		body.texOffs(0, 0).addBox(-3.0F, -4.0F, -3.0F, 6.0F, 4.0F, 7.0F, 0.0F, false);

		needle_r1 = new ModelRenderer(this);
		needle_r1.setPos(-2.0F, -3.0F, 4.0F);
		body.addChild(needle_r1);
		setRotationAngle(needle_r1, -1.3963F, 0.0F, 0.0F);
		needle_r1.texOffs(-2, 19).addBox(0.0F, -0.25F, -1.0F, 1.0F, 0.0F, 2.0F, 0.0F, false);
		needle_r1.texOffs(-2, 19).addBox(4.0F, -0.25F, -1.0F, 1.0F, 0.0F, 2.0F, 0.0F, false);
		needle_r1.texOffs(-2, 19).addBox(2.0F, -0.25F, -1.0F, 1.0F, 0.0F, 2.0F, 0.0F, false);

		needle_r2 = new ModelRenderer(this);
		needle_r2.setPos(-3.0F, -2.0F, 4.0F);
		body.addChild(needle_r2);
		setRotationAngle(needle_r2, -1.3963F, 0.0F, 0.0F);
		needle_r2.texOffs(-2, 19).addBox(0.0F, -0.25F, -1.0F, 1.0F, 0.0F, 2.0F, 0.0F, false);
		needle_r2.texOffs(-2, 19).addBox(2.0F, -0.25F, -1.0F, 1.0F, 0.0F, 2.0F, 0.0F, false);
		needle_r2.texOffs(-2, 19).addBox(4.0F, -0.25F, -1.0F, 1.0F, 0.0F, 2.0F, 0.0F, false);

		needle_r3 = new ModelRenderer(this);
		needle_r3.setPos(4.0F, -4.0F, 0.0F);
		body.addChild(needle_r3);
		setRotationAngle(needle_r3, 0.2182F, 0.0F, 1.5708F);
		needle_r3.texOffs(-2, 19).addBox(0.0F, 0.75F, -1.0F, 1.0F, 0.0F, 2.0F, 0.0F, false);

		needle_r4 = new ModelRenderer(this);
		needle_r4.setPos(4.0F, -4.0F, 3.0F);
		body.addChild(needle_r4);
		setRotationAngle(needle_r4, 0.2182F, 0.0F, 1.5708F);
		needle_r4.texOffs(-2, 19).addBox(0.0F, 0.75F, -1.0F, 1.0F, 0.0F, 2.0F, 0.0F, false);

		needle_r5 = new ModelRenderer(this);
		needle_r5.setPos(4.0F, -3.0F, -1.0F);
		body.addChild(needle_r5);
		setRotationAngle(needle_r5, 0.2182F, 0.0F, 1.5708F);
		needle_r5.texOffs(-2, 19).addBox(0.0F, 0.75F, -1.0F, 1.0F, 0.0F, 2.0F, 0.0F, false);

		needle_r6 = new ModelRenderer(this);
		needle_r6.setPos(4.0F, -3.0F, 2.0F);
		body.addChild(needle_r6);
		setRotationAngle(needle_r6, 0.2182F, 0.0F, 1.5708F);
		needle_r6.texOffs(-2, 19).addBox(0.0F, 0.75F, -1.0F, 1.0F, 0.0F, 2.0F, 0.0F, false);

		needle_r7 = new ModelRenderer(this);
		needle_r7.setPos(4.0F, -2.0F, 3.0F);
		body.addChild(needle_r7);
		setRotationAngle(needle_r7, 0.2182F, 0.0F, 1.5708F);
		needle_r7.texOffs(-2, 19).addBox(0.0F, 0.75F, -1.0F, 1.0F, 0.0F, 2.0F, 0.0F, false);

		needle_r8 = new ModelRenderer(this);
		needle_r8.setPos(4.0F, -2.0F, 0.0F);
		body.addChild(needle_r8);
		setRotationAngle(needle_r8, 0.2182F, 0.0F, 1.5708F);
		needle_r8.texOffs(-2, 19).addBox(0.0F, 0.75F, -1.0F, 1.0F, 0.0F, 2.0F, 0.0F, false);

		needle_r9 = new ModelRenderer(this);
		needle_r9.setPos(-3.0F, -2.0F, 0.0F);
		body.addChild(needle_r9);
		setRotationAngle(needle_r9, -0.2182F, 0.0F, 1.5708F);
		needle_r9.texOffs(-2, 19).addBox(0.0F, 0.25F, -1.0F, 1.0F, 0.0F, 2.0F, 0.0F, false);

		needle_r10 = new ModelRenderer(this);
		needle_r10.setPos(-3.0F, -2.0F, 3.0F);
		body.addChild(needle_r10);
		setRotationAngle(needle_r10, -0.2182F, 0.0F, 1.5708F);
		needle_r10.texOffs(-2, 19).addBox(0.0F, 0.25F, -1.0F, 1.0F, 0.0F, 2.0F, 0.0F, false);

		needle_r11 = new ModelRenderer(this);
		needle_r11.setPos(-3.0F, -3.0F, 2.0F);
		body.addChild(needle_r11);
		setRotationAngle(needle_r11, -0.2182F, 0.0F, 1.5708F);
		needle_r11.texOffs(-2, 19).addBox(0.0F, 0.25F, -1.0F, 1.0F, 0.0F, 2.0F, 0.0F, false);

		needle_r12 = new ModelRenderer(this);
		needle_r12.setPos(-3.0F, -3.0F, -1.0F);
		body.addChild(needle_r12);
		setRotationAngle(needle_r12, -0.2182F, 0.0F, 1.5708F);
		needle_r12.texOffs(-2, 19).addBox(0.0F, 0.25F, -1.0F, 1.0F, 0.0F, 2.0F, 0.0F, false);

		needle_r13 = new ModelRenderer(this);
		needle_r13.setPos(-3.0F, -4.0F, 3.0F);
		body.addChild(needle_r13);
		setRotationAngle(needle_r13, -0.2182F, 0.0F, 1.5708F);
		needle_r13.texOffs(-2, 19).addBox(0.0F, 0.25F, -1.0F, 1.0F, 0.0F, 2.0F, 0.0F, false);

		needle_r14 = new ModelRenderer(this);
		needle_r14.setPos(-3.0F, -4.0F, 0.0F);
		body.addChild(needle_r14);
		setRotationAngle(needle_r14, -0.2182F, 0.0F, 1.5708F);
		needle_r14.texOffs(-2, 19).addBox(0.0F, 0.25F, -1.0F, 1.0F, 0.0F, 2.0F, 0.0F, false);

		needle_r15 = new ModelRenderer(this);
		needle_r15.setPos(-3.0F, -4.0F, -1.0F);
		body.addChild(needle_r15);
		setRotationAngle(needle_r15, 0.2182F, 0.0F, 0.0F);
		needle_r15.texOffs(-2, 19).addBox(0.0F, -0.25F, -1.0F, 1.0F, 0.0F, 2.0F, 0.0F, false);
		needle_r15.texOffs(-2, 19).addBox(2.0F, -0.25F, -1.0F, 1.0F, 0.0F, 2.0F, 0.0F, false);
		needle_r15.texOffs(-2, 19).addBox(4.0F, -0.25F, -1.0F, 1.0F, 0.0F, 2.0F, 0.0F, false);

		needle_r16 = new ModelRenderer(this);
		needle_r16.setPos(-2.0F, -4.0F, 1.0F);
		body.addChild(needle_r16);
		setRotationAngle(needle_r16, 0.2182F, 0.0F, 0.0F);
		needle_r16.texOffs(-2, 19).addBox(0.0F, -0.25F, -1.0F, 1.0F, 0.0F, 2.0F, 0.0F, false);
		needle_r16.texOffs(-2, 19).addBox(2.0F, -0.25F, -1.0F, 1.0F, 0.0F, 2.0F, 0.0F, false);
		needle_r16.texOffs(-2, 19).addBox(4.0F, -0.25F, -1.0F, 1.0F, 0.0F, 2.0F, 0.0F, false);

		needle_r17 = new ModelRenderer(this);
		needle_r17.setPos(1.0F, -4.0F, 3.0F);
		body.addChild(needle_r17);
		setRotationAngle(needle_r17, 0.2182F, 0.0F, 0.0F);
		needle_r17.texOffs(-2, 19).addBox(0.0F, -0.25F, -1.0F, 1.0F, 0.0F, 2.0F, 0.0F, false);
		needle_r17.texOffs(-2, 19).addBox(-2.0F, -0.25F, -1.0F, 1.0F, 0.0F, 2.0F, 0.0F, false);
		needle_r17.texOffs(-2, 19).addBox(-4.0F, -0.25F, -1.0F, 1.0F, 0.0F, 2.0F, 0.0F, false);

		head = new ModelRenderer(this);
		head.setPos(0.0F, 21.9F, -2.5F);
		head.texOffs(0, 11).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 3.0F, 2.0F, 0.0F, false);

		Snout = new ModelRenderer(this);
		Snout.setPos(0.0F, 0.5F, -2.5F);
		head.addChild(Snout);
		Snout.texOffs(10, 11).addBox(-1.5F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F, 0.0F, false);

		RightEar = new ModelRenderer(this);
		RightEar.setPos(-1.0F, -1.0F, -1.0F);
		head.addChild(RightEar);
		

		Ear1_r1 = new ModelRenderer(this);
		Ear1_r1.setPos(0.0F, 0.0F, 0.5F);
		RightEar.addChild(Ear1_r1);
		setRotationAngle(Ear1_r1, 0.0F, 0.0F, -0.7854F);
		Ear1_r1.texOffs(0, 0).addBox(-1.0F, -2.25F, -1.0F, 2.0F, 2.0F, 1.0F, -0.25F, false);

		LeftEar = new ModelRenderer(this);
		LeftEar.setPos(1.0F, -1.0F, -1.0F);
		head.addChild(LeftEar);
		

		Ear0_r1 = new ModelRenderer(this);
		Ear0_r1.setPos(0.0F, 0.0F, 0.5F);
		LeftEar.addChild(Ear0_r1);
		setRotationAngle(Ear0_r1, 0.0F, 0.0F, 0.7854F);
		Ear0_r1.texOffs(0, 3).addBox(-1.0F, -2.25F, -1.0F, 2.0F, 2.0F, 1.0F, -0.25F, false);

		leg0 = new ModelRenderer(this);
		leg0.setPos(-1.5F, 22.0F, -1.5F);
		setRotationAngle(leg0, 0.0F, -0.0436F, 0.0F);
		leg0.texOffs(4, 16).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);

		leg1 = new ModelRenderer(this);
		leg1.setPos(1.5F, 22.0F, -1.5F);
		leg1.texOffs(0, 16).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);

		leg2 = new ModelRenderer(this);
		leg2.setPos(-1.5F, 22.0F, 2.5F);
		leg2.texOffs(15, 15).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);

		leg3 = new ModelRenderer(this);
		leg3.setPos(1.5F, 22.0F, 2.5F);
		leg3.texOffs(12, 13).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
	}

	@Override
	protected Iterable<ModelRenderer> headParts() {
		return ImmutableList.of(this.head);
	}

	@Override
	protected Iterable<ModelRenderer> bodyParts() {
		return ImmutableList.of(this.body, this.leg0, this.leg1, this.leg2, this.leg3);
	}

	@Override
	public void setupAnim(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
		this.head.xRot = pHeadPitch * ((float) Math.PI / 180F);
		this.head.yRot = pNetHeadYaw * ((float) Math.PI / 180F);
		//this.body.xRot = ((float) Math.PI / 2F);
		this.leg0.xRot = MathHelper.cos(pLimbSwing * 0.6662F) * 1.4F * pLimbSwingAmount;
		this.leg1.xRot = MathHelper.cos(pLimbSwing * 0.6662F + (float) Math.PI) * 1.4F * pLimbSwingAmount;
		this.leg2.xRot = MathHelper.cos(pLimbSwing * 0.6662F) * 1.4F * pLimbSwingAmount;
		this.leg3.xRot = MathHelper.cos(pLimbSwing * 0.6662F + (float) Math.PI) * 1.4F * pLimbSwingAmount;
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}