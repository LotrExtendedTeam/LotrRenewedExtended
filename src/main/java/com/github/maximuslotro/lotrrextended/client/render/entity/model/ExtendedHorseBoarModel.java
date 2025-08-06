package com.github.maximuslotro.lotrrextended.client.render.entity.model;

import com.github.maximuslotro.lotrrextended.common.entity.passive.ExtendedHorseBoarEntity;

import net.minecraft.client.renderer.entity.model.HorseModel;
import net.minecraft.client.renderer.entity.model.PigModel;
import net.minecraft.client.renderer.entity.model.QuadrupedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

/**
 * Based upon {@link HorseModel} and {@link PigModel}
 * @param <T> The entity instance extending {@link ExtendedHorseBoarEntity} for this model to render
 * 
 */
public class ExtendedHorseBoarModel<T extends ExtendedHorseBoarEntity> extends QuadrupedModel<T> {

	//public ExtendedHorseBoarModel(float scale) {
	//	super(6, scale, false, 4.0F, 4.0F, 2.0F, 2.0F, 24);
	//	//this.head.texOffs(16, 16).addBox(-2.0F, 0.0F, -9.0F, 4.0F, 3.0F, 1.0F, scale);
	//}

	private final ModelRenderer saddle_r1;
	private final ModelRenderer mane_full_r1;
	private final ModelRenderer mane_saddle_r1;
	private final ModelRenderer body_r1;
	private final ModelRenderer ear_r1;
	private final ModelRenderer ear_r2;
	private final ModelRenderer tusk_r1;
	private final ModelRenderer head_r1;

	public ExtendedHorseBoarModel(float scale) {
		super(0, scale, false, 4.0F, 4.0F, 2.0F, 2.0F, 24);
		texWidth = 64;
		texHeight = 64;

		body = new ModelRenderer(this);
		body.setPos(0.0F, 11.0F, -1.0F);
		setRotationAngle(body, 1.5708F, 0.0F, 0.0F);
		

		saddle_r1 = new ModelRenderer(this);
		saddle_r1.setPos(4.0F, 5.0F, -5.0F);
		body.addChild(saddle_r1);
		setRotationAngle(saddle_r1, -0.0436F, 0.0F, 0.0F);
		saddle_r1.texOffs(0, 46).addBox(-9.0F, -9.0F, -1.0F, 10.0F, 9.0F, 9.0F, 0.5F, false);

		mane_full_r1 = new ModelRenderer(this);
		mane_full_r1.setPos(0.0F, 0.0F, 1.0F);
		body.addChild(mane_full_r1);
		setRotationAngle(mane_full_r1, -0.0436F, 0.0F, 0.0F);
		mane_full_r1.texOffs(48, 27).addBox(0.0F, -9.0F, 2.0F, 0.0F, 12.0F, 3.0F, 0.0F, false);

		mane_saddle_r1 = new ModelRenderer(this);
		mane_saddle_r1.setPos(0.0F, -7.0F, 1.0F);
		body.addChild(mane_saddle_r1);
		setRotationAngle(mane_saddle_r1, -0.0436F, 0.0F, 0.0F);
		mane_saddle_r1.texOffs(48, 30).addBox(0.0F, -2.0F, 2.0F, 0.0F, 5.0F, 3.0F, 0.0F, false);

		body_r1 = new ModelRenderer(this);
		body_r1.setPos(0.0F, 13.0F, -2.0F);
		body.addChild(body_r1);
		setRotationAngle(body_r1, -0.0436F, 0.0F, 0.0F);
		body_r1.texOffs(0, 0).addBox(-5.0F, -22.0F, -5.0F, 10.0F, 19.0F, 10.0F, 0.0F, false);

		head = new ModelRenderer(this);
		head.setPos(0.0F, 13.0F, -9.0F);
		head.texOffs(0, 29).addBox(-4.0F, -7.0F, -8.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);

		ear_r1 = new ModelRenderer(this);
		ear_r1.setPos(1.0F, 4.0F, 4.0F);
		head.addChild(ear_r1);
		setRotationAngle(ear_r1, 0.0F, 0.0F, -0.3491F);
		ear_r1.texOffs(0, 0).addBox(-3.0F, -13.0F, -9.0F, 3.0F, 4.0F, 1.0F, 0.0F, false);

		ear_r2 = new ModelRenderer(this);
		ear_r2.setPos(2.0F, 5.0F, 4.0F);
		head.addChild(ear_r2);
		setRotationAngle(ear_r2, 0.0F, 0.0F, 0.3491F);
		ear_r2.texOffs(0, 5).addBox(-3.0F, -13.0F, -9.0F, 3.0F, 4.0F, 1.0F, 0.0F, false);

		tusk_r1 = new ModelRenderer(this);
		tusk_r1.setPos(-3.0F, 6.0F, 1.0F);
		head.addChild(tusk_r1);
		setRotationAngle(tusk_r1, 0.1745F, 0.0F, 0.0F);
		tusk_r1.texOffs(4, 29).addBox(-1.0F, -11.0F, -9.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		tusk_r1.texOffs(0, 29).addBox(6.0F, -11.0F, -9.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);

		head_r1 = new ModelRenderer(this);
		head_r1.setPos(0.0F, 9.0F, 6.0F);
		head.addChild(head_r1);
		setRotationAngle(head_r1, 0.1745F, 0.0F, 0.0F);
		head_r1.texOffs(40, 22).addBox(-3.0F, -14.0F, -14.0F, 6.0F, 4.0F, 3.0F, 0.0F, false);

		leg0 = new ModelRenderer(this);
		leg0.setPos(-3.0F, 17.0F, 7.0F);
		setRotationAngle(leg0, 0.0F, -0.0436F, 0.0F);
		leg0.texOffs(32, 40).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 7.0F, 4.0F, 0.0F, false);

		leg1 = new ModelRenderer(this);
		leg1.setPos(3.0F, 17.0F, 7.0F);
		leg1.texOffs(40, 11).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 7.0F, 4.0F, 0.0F, false);

		leg2 = new ModelRenderer(this);
		leg2.setPos(-3.0F, 17.0F, -7.0F);
		leg2.texOffs(40, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 7.0F, 4.0F, 0.0F, false);

		leg3 = new ModelRenderer(this);
		leg3.setPos(3.0F, 17.0F, -7.0F);
		leg3.texOffs(32, 29).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 7.0F, 4.0F, 0.0F, false);
	}

	/**
	 * Sets this entity's model rotation angles
	 */
	@Override
	public void setupAnim(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
		super.setupAnim(pEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
		boolean saddled = pEntity.isSaddled();
		boolean flag1 = pEntity.isVehicle();
		boolean male = pEntity.getGender().isMale();
		
		saddle_r1.visible = saddled;
		mane_saddle_r1.visible = saddled;
		mane_full_r1.visible = !saddled;
		tusk_r1.visible = male;
	}

	@Override
	public void prepareMobModel(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTick) {
		super.prepareMobModel(pEntity, pLimbSwing, pLimbSwingAmount, pPartialTick);
		float partBodyRot = MathHelper.rotlerp(pEntity.yBodyRotO, pEntity.yBodyRot, pPartialTick);
		float partHeadRot = MathHelper.rotlerp(pEntity.yHeadRotO, pEntity.yHeadRot, pPartialTick);
		float partRot = MathHelper.lerp(pPartialTick, pEntity.xRotO, pEntity.xRot);
		float partHeadBodyDiffRot = partHeadRot - partBodyRot;
		float bodyRotRad = partRot * ((float)Math.PI / 180F);
		if (partHeadBodyDiffRot > 20.0F) {
			partHeadBodyDiffRot = 20.0F;
		}

		if (partHeadBodyDiffRot < -20.0F) {
			partHeadBodyDiffRot = -20.0F;
		}

		if (pLimbSwingAmount > 0.2F) {
			bodyRotRad += MathHelper.cos(pLimbSwing * 0.4F) * 0.15F * pLimbSwingAmount;
		}

		//float f5 = pEntity.getEatAnim(pPartialTick);
		//float f6 = pEntity.getStandAnim(pPartialTick);
		//float f7 = 1.0F - f6;
		//float f8 = pEntity.getMouthAnim(pPartialTick);
		//boolean flag = pEntity.tailCounter != 0;
		float f9 = (float)pEntity.tickCount + pPartialTick;
		//this.head.y = 4.0F;
		//this.head.z = -12.0F;
		this.body.xRot = 0.0F;
		this.head.xRot = ((float)Math.PI / 6F) + bodyRotRad;
		this.head.yRot = partHeadBodyDiffRot * ((float)Math.PI / 180F);
		//float f10 = pEntity.isInWater() ? 0.2F : 1.0F;
		//float f11 = MathHelper.cos(f10 * pLimbSwing * 0.6662F + (float)Math.PI);
		//float f12 = f11 * 0.8F * pLimbSwingAmount;
		//float f13 = (1.0F - Math.max(f6, f5)) * (((float)Math.PI / 6F) + bodyRotRad + f8 * MathHelper.sin(f9) * 0.05F);
		//this.head.xRot = f6 * (0.2617994F + bodyRotRad) + f5 * (2.1816616F + MathHelper.sin(f9) * 0.05F) + f13;
		//this.head.yRot = f6 * partHeadBodyDiffRot * ((float)Math.PI / 180F) + (1.0F - Math.max(f6, f5)) * this.head.yRot;
		//this.head.y = f6 * -4.0F + f5 * 11.0F + (1.0F - Math.max(f6, f5)) * this.head.y;
		//this.head.z = f6 * -4.0F + f5 * -12.0F + (1.0F - Math.max(f6, f5)) * this.head.z;
		//this.body.xRot = f6 * (-(float)Math.PI / 4F) + f7 * this.body.xRot;
		//float f14 = 0.2617994F * f6;
		//float f15 = MathHelper.cos(f9 * 0.6F + (float)Math.PI);
		//this.leg2.y = 2.0F * f6 + 14.0F * f7;
		//this.leg2.z = -6.0F * f6 - 10.0F * f7;
		//this.leg3.y = this.leg2.y;
		//this.leg3.z = this.leg2.z;
		//float f16 = ((-(float)Math.PI / 3F) + f15) * f6 + f12 * f7;
		//float f17 = ((-(float)Math.PI / 3F) - f15) * f6 - f12 * f7;
		//this.leg0.xRot = f14 - f11 * 0.5F * pLimbSwingAmount * f7;
		//this.leg1.xRot = f14 + f11 * 0.5F * pLimbSwingAmount * f7;
		//this.leg2.xRot = f16;
		//this.leg3.xRot = f17;
		//boolean flag1 = pEntity.isBaby();
		//this.body.y = flag1 ? 10.8F : 0.0F;
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}
