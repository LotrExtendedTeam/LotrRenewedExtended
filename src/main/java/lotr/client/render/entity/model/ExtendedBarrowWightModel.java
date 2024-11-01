package lotr.client.render.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import lotr.common.entity.npc.ExtendedBarrowWrightEntity;
import net.minecraft.client.renderer.model.ModelRenderer;

public class ExtendedBarrowWightModel<E extends ExtendedBarrowWrightEntity> extends LOTRBipedModel<E> {
	private final ModelRenderer wight;
	private final ModelRenderer helmet;
	private final ModelRenderer skirt_r1;
	private final ModelRenderer skirt_r2;
	private final ModelRenderer skirt_r3;
	private final ModelRenderer skirt_r4;

	public ExtendedBarrowWightModel(float f) {
		this(f, false);
	}

	public ExtendedBarrowWightModel(boolean smallArm) {
		this(0.0f, smallArm);
	}

	public ExtendedBarrowWightModel(float f, boolean smallArms) {
		super(f, 0.0f, false, smallArms);
		texWidth = 96;
		texHeight = 96;

		wight = new ModelRenderer(this);
		wight.setPos(1.0F, -1.0F, -3.0F);
		

		head = new ModelRenderer(this);
		head.setPos(2.0F, 27.0F, 0.0F);
		wight.addChild(head);
		head.texOffs(32, 19).addBox(-7.0F, 9.0F, -1.0F, 8.0F, 9.0F, 8.0F, 0.0F, false);
		head.texOffs(32, 0).addBox(-7.0F, 7.0F, -1.0F, 8.0F, 11.0F, 8.0F, 0.0F, false);
		head.texOffs(64, 44).addBox(-6.0F, 13.0F, -1.01F, 6.0F, 1.0F, 0.0F, 0.0F, false);
		head.texOffs(0, 25).addBox(-7.0F, 6.0F, -1.0F, 8.0F, 12.0F, 8.0F, 0.25F, false);

		helmet = new ModelRenderer(this);
		helmet.setPos(0.0F, 0.0F, 0.0F);
		head.addChild(helmet);
		helmet.texOffs(32, 36).addBox(-7.0F, 9.0F, -1.0F, 8.0F, 9.0F, 8.0F, 0.5F, false);
		helmet.texOffs(64, 0).addBox(-6.0F, 18.25F, 0.0F, 6.0F, 1.0F, 6.0F, 0.5F, false);
		helmet.texOffs(64, 23).addBox(-5.0F, 19.25F, 1.0F, 4.0F, 1.0F, 4.0F, 0.5F, false);

		body = new ModelRenderer(this);
		body.setPos(0.0F, 0.0F, 0.0F);
		wight.addChild(body);
		body.texOffs(0, 0).addBox(-6.0F, 18.0F, 0.0F, 10.0F, 19.0F, 6.0F, 0.0F, false);

		skirt_r1 = new ModelRenderer(this);
		skirt_r1.setPos(2.0F, -8.0F, 14.0F);
		body.addChild(skirt_r1);
		setRotationAngle(skirt_r1, 0.2618F, 0.0F, 0.0F);
		skirt_r1.texOffs(64, 15).addBox(-8.0F, 19.2F, -1.0F, 10.0F, 8.0F, 0.0F, 0.0F, false);

		skirt_r2 = new ModelRenderer(this);
		skirt_r2.setPos(-14.0F, -8.0F, 6.0F);
		body.addChild(skirt_r2);
		setRotationAngle(skirt_r2, 0.2618F, -1.5708F, 0.0F);
		skirt_r2.texOffs(64, 36).addBox(-6.0F, 19.2F, -1.0F, 6.0F, 8.0F, 0.0F, 0.0F, false);

		skirt_r3 = new ModelRenderer(this);
		skirt_r3.setPos(10.0F, -8.0F, 6.0F);
		body.addChild(skirt_r3);
		setRotationAngle(skirt_r3, -0.2618F, -1.5708F, 0.0F);
		skirt_r3.texOffs(64, 28).addBox(-6.0F, 18.8F, -1.0F, 6.0F, 8.0F, 0.0F, 0.0F, false);

		skirt_r4 = new ModelRenderer(this);
		skirt_r4.setPos(2.0F, -8.0F, -6.0F);
		body.addChild(skirt_r4);
		setRotationAngle(skirt_r4, -0.2618F, 0.0F, 0.0F);
		skirt_r4.texOffs(64, 7).addBox(-8.0F, 18.8F, -1.0F, 10.0F, 8.0F, 0.0F, 0.0F, false);

		rightArm = new ModelRenderer(this);
		rightArm.setPos(0.0F, 0.0F, 0.0F);
		wight.addChild(rightArm);
		rightArm.texOffs(32, 53).addBox(4.0F, 26.0F, 0.5F, 5.0F, 11.0F, 5.0F, 0.0F, false);
		rightArm.texOffs(0, 45).addBox(4.5F, 18.0F, 1.0F, 4.0F, 18.0F, 4.0F, 0.0F, false);

		leftArm = new ModelRenderer(this);
		leftArm.setPos(0.0F, 0.0F, 0.0F);
		wight.addChild(leftArm);
		leftArm.texOffs(52, 53).addBox(-11.0F, 26.0F, 0.5F, 5.0F, 11.0F, 5.0F, 0.0F, false);
		leftArm.texOffs(16, 45).addBox(-10.5F, 18.0F, 1.0F, 4.0F, 18.0F, 4.0F, 0.0F, false);
	}

	@Override
	public void setupAnim(E entity, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {
		super.setupAnim(entity, p_225597_2_, p_225597_3_, p_225597_4_, p_225597_5_, p_225597_6_);
	}

	@Override
	public void prepareMobModel(E ranger, float p_212843_2_, float p_212843_3_, float p_212843_4_) {
		super.prepareMobModel(ranger, p_212843_2_, p_212843_3_, p_212843_4_);
	}

	//    @Override
	//    public void renderToBuffer(MatrixStack matStack, IVertexBuilder buf, int packedLight, int packedOverlay, float r, float g, float b, float a) {
	//    	super.renderToBuffer(matStack, buf, packedLight, packedOverlay, r, g, b, a);
	//    }

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		//wight.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		super.renderToBuffer(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}