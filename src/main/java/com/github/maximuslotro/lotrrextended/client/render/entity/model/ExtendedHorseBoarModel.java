package com.github.maximuslotro.lotrrextended.client.render.entity.model;

import com.github.maximuslotro.lotrrextended.common.entity.passive.ExtendedHorseBoarEntity;

import net.minecraft.client.renderer.entity.model.QuadrupedModel;
import net.minecraft.client.renderer.model.ModelRenderer;

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
		boolean flag = pEntity.isSaddled();
		boolean flag1 = pEntity.isVehicle();

		saddle_r1.visible = flag;
		mane_saddle_r1.visible = flag;
		mane_full_r1.visible = !flag;
	}

	@Override
	public void prepareMobModel(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTick) {
		super.prepareMobModel(pEntity, pLimbSwing, pLimbSwingAmount, pPartialTick);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}
