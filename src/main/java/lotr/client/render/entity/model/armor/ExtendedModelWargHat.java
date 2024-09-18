package lotr.client.render.entity.model.armor;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class ExtendedModelWargHat<E extends LivingEntity> extends ExtendedSpecialArmorModel<E> {
	private final ModelRenderer ear_r1;
	private final ModelRenderer ear_r2;
	private final ModelRenderer fur_r1;
	private final ModelRenderer fur_r2;

	public ExtendedModelWargHat(BipedModel<E> referenceBipedModel) {
		this(referenceBipedModel, 1.0F);
	}

	public ExtendedModelWargHat(BipedModel<E> referenceBipedModel, float f) {
		super(referenceBipedModel, f, 64, 64);
		clearNonHelmetParts();

		head = new ModelRenderer(this);
		head.setPos(0.0F, 0.0F, 0.0F);
		head.texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 1.0F, false);
		head.texOffs(0, 16).addBox(-3.0F, -7.75F, -9.0F, 6.0F, 3.0F, 5.0F, 0.0F, false);
		head.texOffs(17, 19).addBox(-3.0F, -4.75F, -9.0F, 6.0F, 1.0F, 5.0F, 0.0F, false);

		ear_r1 = new ModelRenderer(this);
		ear_r1.setPos(0.0F, -9.0F, 0.0F);
		head.addChild(ear_r1);
		setRotationAngle(ear_r1, 0.0F, -0.2618F, 0.3054F);
		ear_r1.texOffs(0, 24).addBox(1.5F, -5.5F, -1.5F, 4.0F, 6.0F, 2.0F, 0.0F, false);

		ear_r2 = new ModelRenderer(this);
		ear_r2.setPos(-2.0F, -9.0F, 0.0F);
		head.addChild(ear_r2);
		setRotationAngle(ear_r2, 0.0F, 0.2618F, -0.3054F);
		ear_r2.texOffs(0, 24).addBox(-3.5F, -5.0F, -1.0F, 4.0F, 6.0F, 2.0F, 0.0F, false);

		fur_r1 = new ModelRenderer(this);
		fur_r1.setPos(8.0F, -2.0F, 1.0F);
		head.addChild(fur_r1);
		setRotationAngle(fur_r1, 0.0F, 0.7854F, 0.0F);
		fur_r1.texOffs(12, 20).addBox(-0.5F, -5.0F, -4.0F, 0.0F, 5.0F, 5.0F, 0.0F, false);

		fur_r2 = new ModelRenderer(this);
		fur_r2.setPos(-7.0F, -2.0F, 2.0F);
		head.addChild(fur_r2);
		setRotationAngle(fur_r2, 0.0F, -0.7854F, 0.0F);
		fur_r2.texOffs(12, 20).addBox(-1.0F, -5.0F, -4.0F, 0.0F, 5.0F, 5.0F, 0.0F, false);
	}
}