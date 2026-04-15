package lotr.client.render.entity.model.armor;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class ExtendedModelMorgulVassalChestplate<E extends LivingEntity> extends ExtendedSpecialArmorModel<E> {
	private final ModelRenderer Details_r1;
	private final ModelRenderer Details_r2;

	public ExtendedModelMorgulVassalChestplate(BipedModel<E> referenceBipedModel) {
		this(referenceBipedModel, 1.0F);
	}

	public ExtendedModelMorgulVassalChestplate(BipedModel<E> referenceBipedModel, float f) {
		super(referenceBipedModel, f, 64, 64);
		this.clearNonChestplateParts();

		body = new ModelRenderer(this);
		body.setPos(0.0F, 0.0F, 0.0F);
		body.texOffs(24, 0).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 10.0F, 4.0F, 0.71F, false);
		body.texOffs(0, 0).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.3F, false);
		body.texOffs(0, 16).addBox(-5.0F, -1.0F, -3.0F, 10.0F, 5.0F, 6.0F, 0.0F, false);

		rightArm = new ModelRenderer(this);
		rightArm.setPos(-5.0F, 2.0F, 0.0F);
		rightArm.texOffs(0, 27).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.7F, true);
		rightArm.texOffs(52, 0).addBox(-5.0F, -1.25F, -2.5F, 1.0F, 3.0F, 5.0F, 0.1F, false);
		rightArm.texOffs(53, 9).addBox(-4.75F, 1.5F, -2.0F, 1.0F, 2.0F, 4.0F, 0.0F, false);

		Details_r1 = new ModelRenderer(this);
		Details_r1.setPos(-2.25F, -2.0F, 0.0F);
		rightArm.addChild(Details_r1);
		setRotationAngle(Details_r1, 0.0F, 0.0F, -0.5236F);
		Details_r1.texOffs(44, 0).addBox(-3.0F, -6.0F, 0.0F, 6.0F, 4.0F, 0.0F, 0.0F, true);
		Details_r1.texOffs(16, 27).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 4.0F, 6.0F, 0.1F, false);

		leftArm = new ModelRenderer(this);
		leftArm.setPos(5.0F, 2.0F, 0.0F);
		leftArm.texOffs(0, 27).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.7F, false);
		leftArm.texOffs(52, 0).addBox(4.0F, -1.5F, -2.5F, 1.0F, 3.0F, 5.0F, 0.1F, true);
		leftArm.texOffs(53, 9).addBox(3.75F, 1.5F, -2.0F, 1.0F, 2.0F, 4.0F, 0.0F, true);

		Details_r2 = new ModelRenderer(this);
		Details_r2.setPos(2.25F, -2.0F, 0.0F);
		leftArm.addChild(Details_r2);
		setRotationAngle(Details_r2, 0.0F, 0.0F, 0.5236F);
		Details_r2.texOffs(44, 0).addBox(-3.0F, -6.0F, 0.0F, 6.0F, 4.0F, 0.0F, 0.0F, false);
		Details_r2.texOffs(16, 27).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 4.0F, 6.0F, 0.1F, true);
	}
}