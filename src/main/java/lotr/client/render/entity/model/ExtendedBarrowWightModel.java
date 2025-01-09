package lotr.client.render.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import lotr.common.entity.npc.ExtendedBarrowWrightEntity;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelHelper;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;

public class ExtendedBarrowWightModel<E extends ExtendedBarrowWrightEntity> extends BipedModel<E> {
	private final ModelRenderer helmet;
	private final ModelRenderer skirt_r1;
	private final ModelRenderer skirt_r2;
	private final ModelRenderer skirt_r3;
	private final ModelRenderer skirt_r4;
	private final ModelRenderer head_Normal_Piece;
	private final ModelRenderer head_Angry_Piece;

	// TODO code in modelSize
	public ExtendedBarrowWightModel(float modelSize) {
		super(modelSize);
		texWidth = 96;
		texHeight = 96;

		head = new ModelRenderer(this);
		head.setPos(0.0F, -11.0f, -1.0F);
		// Normal head
		//head.texOffs(32, 19).addBox(-4.0F, -9.0F, -3.0F, 8.0F, 9.0F, 8.0F, 0.0F, false);
		// Angry Head
		//head.texOffs(32, 0).addBox(-4.0F, -9.0F, -3.0F, 8.0F, 11.0F, 8.0F, 0.0F, false);
		head.texOffs(64, 44).addBox(-3.0F, -5.0F, -3.01F, 6.0F, 1.0F, 0.0F, 0.0F, false);
		head.texOffs(0, 25).addBox(-4.0F, -9.0F, -3.0F, 8.0F, 12.0F, 8.0F, 0.25F, false);

		head_Normal_Piece = new ModelRenderer(this);
		head_Normal_Piece.texOffs(32, 19).addBox(-4.0F, -9.0F, -3.0F, 8.0F, 9.0F, 8.0F, 0.0F, false);
		head.addChild(head_Normal_Piece);
		head_Angry_Piece = new ModelRenderer(this);
		head_Angry_Piece.visible=false;
		head_Angry_Piece.texOffs(32, 0).addBox(-4.0F, -9.0F, -3.0F, 8.0F, 11.0F, 8.0F, 0.0F, false);
		head.addChild(head_Angry_Piece);

		helmet = new ModelRenderer(this);
		helmet.setPos(0.0F, 35.0F, 1.0F);
		head.addChild(helmet);
		helmet.texOffs(32, 36).addBox(-4.0F, -44.0F, -4.0F, 8.0F, 9.0F, 8.0F, 0.5F, false);
		helmet.texOffs(64, 0).addBox(-3.0F, -45.25F, -3.0F, 6.0F, 1.0F, 6.0F, 0.5F, false);
		helmet.texOffs(64, 23).addBox(-2.0F, -46.25F, -2.0F, 4.0F, 1.0F, 4.0F, 0.5F, false);

		body = new ModelRenderer(this);
		body.setPos(0.0F, 24.0F, 0.0F);
		body.texOffs(0, 0).addBox(-5.0F, -36.0F, -3.0F, 10.0F, 19.0F, 6.0F, 0.0F, false);

		skirt_r1 = new ModelRenderer(this);
		skirt_r1.setPos(0.0F, -17.0F, 3.0F);
		body.addChild(skirt_r1);
		setRotationAngle(skirt_r1, 0.2618F, 0.0F, 0.0F);
		skirt_r1.texOffs(64, 15).addBox(-5.0F, 0.0F, 0.0F, 10.0F, 8.0F, 0.0F, 0.0F, false);

		skirt_r2 = new ModelRenderer(this);
		skirt_r2.setPos(-5.0F, -17.0F, 0.0F);
		body.addChild(skirt_r2);
		setRotationAngle(skirt_r2, 0.2618F, -1.5708F, 0.0F);
		skirt_r2.texOffs(64, 36).addBox(-3.0F, 0.0F, 0.0F, 6.0F, 8.0F, 0.0F, 0.0F, false);

		skirt_r3 = new ModelRenderer(this);
		skirt_r3.setPos(5.0F, -17.0F, 0.0F);
		body.addChild(skirt_r3);
		setRotationAngle(skirt_r3, -0.2618F, -1.5708F, 0.0F);
		skirt_r3.texOffs(64, 28).addBox(-3.0F, 0.0F, 0.0F, 6.0F, 8.0F, 0.0F, 0.0F, false);

		skirt_r4 = new ModelRenderer(this);
		skirt_r4.setPos(0.0F, -17.0F, -3.0F);
		body.addChild(skirt_r4);
		setRotationAngle(skirt_r4, -0.2618F, 0.0F, 0.0F);
		skirt_r4.texOffs(64, 7).addBox(-5.0F, 0.0F, 0.0F, 10.0F, 8.0F, 0.0F, 0.0F, false);

		rightArm = new ModelRenderer(this);
		rightArm.setPos(5.0F, -9.0F, 0.0F);
		rightArm.texOffs(32, 53).addBox(0.0F, -3.0F, -2.5F, 5.0F, 11.0F, 5.0F, 0.0F, false);
		rightArm.texOffs(0, 45).addBox(0.5F, -2.0F, -2.0F, 4.0F, 18.0F, 4.0F, 0.0F, false);

		leftArm = new ModelRenderer(this);
		leftArm.setPos(-5.0F, -9.0F, 0.0F);
		leftArm.texOffs(52, 53).addBox(-5.0F, -3.0F, -2.5F, 5.0F, 11.0F, 5.0F, 0.0F, false);
		leftArm.texOffs(16, 45).addBox(-4.5F, -2.0F, -2.0F, 4.0F, 18.0F, 4.0F, 0.0F, false);
		leftArm.mirror = true;

		rightLeg = new ModelRenderer(this, 0, 16);
		leftLeg = new ModelRenderer(this, 0, 16);
	}

	@Override
	public void setupAnim(E pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
		boolean flag = pEntity.getFallFlyingTicks() > 4;
		boolean flag1 = pEntity.isVisuallySwimming();
		this.head.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
		if (flag) {
			this.head.xRot = (-(float)Math.PI / 4F);
		} else if (this.swimAmount > 0.0F) {
			if (flag1) {
				this.head.xRot = this.rotlerpRad(this.swimAmount, this.head.xRot, (-(float)Math.PI / 4F));
			} else {
				this.head.xRot = this.rotlerpRad(this.swimAmount, this.head.xRot, pHeadPitch * ((float)Math.PI / 180F));
			}
		} else {
			this.head.xRot = pHeadPitch * ((float)Math.PI / 180F);
		}

		/*
		this.body.yRot = 0.0F;
		this.rightArm.z = 0.0F;
		this.rightArm.x = -5.0F;
		this.leftArm.z = 0.0F;
		this.leftArm.x = 5.0F;
		*/



		float f = 1.0F;



		if (flag) {
			f = (float)pEntity.getDeltaMovement().lengthSqr();
			f = f / 0.2F;
			f = f * f * f;
		}

		if (f < 1.0F) {
			f = 1.0F;
		}


		this.rightArm.xRot = MathHelper.cos(pLimbSwing * 0.6662F + (float)Math.PI) * 2.0F * pLimbSwingAmount * 0.5F / f;
		this.leftArm.xRot = MathHelper.cos(pLimbSwing * 0.6662F) * 2.0F * pLimbSwingAmount * 0.5F / f;
		this.rightArm.zRot = 0.0F;
		this.leftArm.zRot = 0.0F;
		this.rightLeg.xRot = MathHelper.cos(pLimbSwing * 0.6662F) * 1.4F * pLimbSwingAmount / f;
		this.leftLeg.xRot = MathHelper.cos(pLimbSwing * 0.6662F + (float)Math.PI) * 1.4F * pLimbSwingAmount / f;
		this.rightLeg.yRot = 0.0F;
		this.leftLeg.yRot = 0.0F;
		this.rightLeg.zRot = 0.0F;
		this.leftLeg.zRot = 0.0F;
		if (this.riding) {
			this.rightArm.xRot += (-(float)Math.PI / 5F);
			this.leftArm.xRot += (-(float)Math.PI / 5F);
			this.rightLeg.xRot = -1.4137167F;
			this.rightLeg.yRot = ((float)Math.PI / 10F);
			this.rightLeg.zRot = 0.07853982F;
			this.leftLeg.xRot = -1.4137167F;
			this.leftLeg.yRot = (-(float)Math.PI / 10F);
			this.leftLeg.zRot = -0.07853982F;
		}

		this.rightArm.yRot = 0.0F;
		this.leftArm.yRot = 0.0F;

		/*
		boolean flag2 = pEntity.getMainArm() == HandSide.RIGHT;
		boolean flag3 = flag2 ? this.leftArmPose.isTwoHanded() : this.rightArmPose.isTwoHanded();
		if (flag2 != flag3) {
			this.poseLeftArm(pEntity);
			this.poseRightArm(pEntity);
		} else {
			this.poseRightArm(pEntity);
			this.poseLeftArm(pEntity);
		}
		 */

		this.setupAttackAnimation(pEntity, pAgeInTicks);

		/*
		this.body.xRot = 0.0F;
		this.rightLeg.z = 0.1F;
		this.leftLeg.z = 0.1F;
		this.rightLeg.y = 12.0F;
		this.leftLeg.y = 12.0F;
		this.head.y = 0.0F;
		this.body.y = 0.0F;
		this.leftArm.y = 2.0F;
		this.rightArm.y = 2.0F;
		 */

		ModelHelper.bobArms(this.rightArm, this.leftArm, pAgeInTicks);
		if (this.swimAmount > 0.0F) {
			float f1 = pLimbSwing % 26.0F;
			HandSide handside = this.getAttackArm(pEntity);
			float f2 = handside == HandSide.RIGHT && this.attackTime > 0.0F ? 0.0F : this.swimAmount;
			float f3 = handside == HandSide.LEFT && this.attackTime > 0.0F ? 0.0F : this.swimAmount;
			if (f1 < 14.0F) {
				this.leftArm.xRot = this.rotlerpRad(f3, this.leftArm.xRot, 0.0F);
				this.rightArm.xRot = MathHelper.lerp(f2, this.rightArm.xRot, 0.0F);
				this.leftArm.yRot = this.rotlerpRad(f3, this.leftArm.yRot, (float)Math.PI);
				this.rightArm.yRot = MathHelper.lerp(f2, this.rightArm.yRot, (float)Math.PI);
				//leftArm.zRot = this.rotlerpRad(f3, this.leftArm.zRot, (float)Math.PI + 1.8707964F * this.quadraticArmUpdate(f1) / this.quadraticArmUpdate(14.0F));
				//this.rightArm.zRot = MathHelper.lerp(f2, this.rightArm.zRot, (float)Math.PI - 1.8707964F * this.quadraticArmUpdate(f1) / this.quadraticArmUpdate(14.0F));
			} else if (f1 >= 14.0F && f1 < 22.0F) {
				float f6 = (f1 - 14.0F) / 8.0F;
				this.leftArm.xRot = this.rotlerpRad(f3, this.leftArm.xRot, ((float)Math.PI / 2F) * f6);
				this.rightArm.xRot = MathHelper.lerp(f2, this.rightArm.xRot, ((float)Math.PI / 2F) * f6);
				this.leftArm.yRot = this.rotlerpRad(f3, this.leftArm.yRot, (float)Math.PI);
				this.rightArm.yRot = MathHelper.lerp(f2, this.rightArm.yRot, (float)Math.PI);
				this.leftArm.zRot = this.rotlerpRad(f3, this.leftArm.zRot, 5.012389F - 1.8707964F * f6);
				this.rightArm.zRot = MathHelper.lerp(f2, this.rightArm.zRot, 1.2707963F + 1.8707964F * f6);
			} else if (f1 >= 22.0F && f1 < 26.0F) {
				float f4 = (f1 - 22.0F) / 4.0F;
				this.leftArm.xRot = this.rotlerpRad(f3, this.leftArm.xRot, ((float)Math.PI / 2F) - ((float)Math.PI / 2F) * f4);
				this.rightArm.xRot = MathHelper.lerp(f2, this.rightArm.xRot, ((float)Math.PI / 2F) - ((float)Math.PI / 2F) * f4);
				this.leftArm.yRot = this.rotlerpRad(f3, this.leftArm.yRot, (float)Math.PI);
				this.rightArm.yRot = MathHelper.lerp(f2, this.rightArm.yRot, (float)Math.PI);
				this.leftArm.zRot = this.rotlerpRad(f3, this.leftArm.zRot, (float)Math.PI);
				this.rightArm.zRot = MathHelper.lerp(f2, this.rightArm.zRot, (float)Math.PI);
			}

			float f7 = 0.3F;
			float f5 = 0.33333334F;
			this.leftLeg.xRot = MathHelper.lerp(this.swimAmount, this.leftLeg.xRot, 0.3F * MathHelper.cos(pLimbSwing * 0.33333334F + (float)Math.PI));
			this.rightLeg.xRot = MathHelper.lerp(this.swimAmount, this.rightLeg.xRot, 0.3F * MathHelper.cos(pLimbSwing * 0.33333334F));
		}

		this.hat.copyFrom(this.head);

		if (pEntity.isAggressive())
			ModelHelper.animateZombieArms(this.leftArm, this.rightArm, pEntity.isAggressive(), this.attackTime, pAgeInTicks);
	}


	@Override
	protected void setupAttackAnimation(ExtendedBarrowWrightEntity p_230486_1_, float p_230486_2_) {
		if (!(this.attackTime <= 0.0F)) {
			for (HandSide handSide : HandSide.values()) {
				ModelRenderer modelrenderer = this.getArm(handSide);
				float f = this.attackTime;
				this.body.yRot = MathHelper.sin(MathHelper.sqrt(f) * ((float) Math.PI * 2F)) * 0.2F;

				/** This shifts the arms into the barrow wight's chest lol
				 *
				 this.rightArm.z = MathHelper.sin(this.body.yRot) * 5.0F;
				 this.rightArm.x = -MathHelper.cos(this.body.yRot) * 5.0F;
				 this.leftArm.z = -MathHelper.sin(this.body.yRot) * 5.0F;
				 this.leftArm.x = MathHelper.cos(this.body.yRot) * 5.0F;
				 */

				this.rightArm.yRot += this.body.yRot;
				this.leftArm.yRot += this.body.yRot;
				this.leftArm.xRot += this.body.yRot;
				f = 1.0F - this.attackTime;
				f = f * f;
				f = f * f;
				f = 1.0F - f;
				float f1 = MathHelper.sin(f * (float) Math.PI);
				float f2 = MathHelper.sin(this.attackTime * (float) Math.PI) * -(this.head.xRot - 0.7F) * 0.75F;
				modelrenderer.xRot = (float) ((double) modelrenderer.xRot - ((double) f1 * 1.2D + (double) f2));
				modelrenderer.yRot += this.body.yRot * 2.0F;
				modelrenderer.zRot += MathHelper.sin(this.attackTime * (float) Math.PI) * -0.4F;
			}
		}
	}

	@Override
	public void prepareMobModel(E wight, float p_212843_2_, float p_212843_3_, float p_212843_4_) {
		if(wight.isAggressive()) {
			head_Angry_Piece.visible=true;
			head_Normal_Piece.visible=false;
		}else{
			head_Angry_Piece.visible=false;
			head_Normal_Piece.visible=true;
		}
		super.prepareMobModel(wight, p_212843_2_, p_212843_3_, p_212843_4_);
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		rightArm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		leftArm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}