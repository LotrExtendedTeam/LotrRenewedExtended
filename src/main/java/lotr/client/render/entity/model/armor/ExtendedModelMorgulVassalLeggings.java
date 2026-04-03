package lotr.client.render.entity.model.armor;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class ExtendedModelMorgulVassalLeggings<E extends LivingEntity> extends ExtendedSpecialArmorModel<E> {

	public ExtendedModelMorgulVassalLeggings(BipedModel<E> referenceBipedModel) {
		this(referenceBipedModel, 1.0F);
	}

	public ExtendedModelMorgulVassalLeggings(BipedModel<E> referenceBipedModel, float f) {
		super(referenceBipedModel, f, 32, 32);
		this.clearArmorPartsExcept(this.rightLeg, this.leftLeg);

		body = new ModelRenderer(this);
		body.setPos(0.0F, 0.0F, 0.0F);
		body.texOffs(0, 16).addBox(-4.0F, 7.0F, -2.0F, 8.0F, 5.0F, 4.0F, 0.55F, false);

		rightLeg = new ModelRenderer(this);
		rightLeg.setPos(-1.9F, 12.0F, 0.0F);
		rightLeg.texOffs(0, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.3F, false);
		rightLeg.texOffs(16, 0).addBox(-2.25F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.71F, false);

		leftLeg = new ModelRenderer(this);
		leftLeg.setPos(1.9F, 12.0F, 0.0F);
		leftLeg.texOffs(0, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.3F, true);
		leftLeg.texOffs(16, 0).addBox(-1.75F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.71F, true);
	}
}