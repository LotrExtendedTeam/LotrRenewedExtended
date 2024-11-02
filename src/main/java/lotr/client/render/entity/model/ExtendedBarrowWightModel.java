package lotr.client.render.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import lotr.common.entity.npc.ExtendedBarrowWrightEntity;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class ExtendedBarrowWightModel<E extends ExtendedBarrowWrightEntity> extends BipedModel<E> {
	private final ModelRenderer helmet;
	private final ModelRenderer skirt_r1;
	private final ModelRenderer skirt_r2;
	private final ModelRenderer skirt_r3;
	private final ModelRenderer skirt_r4;

	// TODO code in modelSize
	public ExtendedBarrowWightModel(float modelSize) {
		super(modelSize);
		texWidth = 96;
		texHeight = 96;

		head = new ModelRenderer(this);
		head.setPos(0.0F, 0.0f+modelSize, -1.0F);
		head.texOffs(32, 19).addBox(-4.0F, -9.0F, -3.0F, 8.0F, 9.0F, 8.0F, 0.0F, false);
		head.texOffs(32, 0).addBox(-4.0F, -9.0F, -3.0F, 8.0F, 11.0F, 8.0F, 0.0F, false);
		head.texOffs(64, 44).addBox(-3.0F, -5.0F, -3.01F, 6.0F, 1.0F, 0.0F, 0.0F, false);
		head.texOffs(0, 25).addBox(-4.0F, -9.0F, -3.0F, 8.0F, 12.0F, 8.0F, 0.25F, false);

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
	public void setupAnim(E entity, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {
		super.setupAnim(entity, p_225597_2_, p_225597_3_, p_225597_4_, p_225597_5_, p_225597_6_);
	}

	@Override
	public void prepareMobModel(E ranger, float p_212843_2_, float p_212843_3_, float p_212843_4_) {
		super.prepareMobModel(ranger, p_212843_2_, p_212843_3_, p_212843_4_);
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