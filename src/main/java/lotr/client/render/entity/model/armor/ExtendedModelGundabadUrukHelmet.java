package lotr.client.render.entity.model.armor;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class ExtendedModelGundabadUrukHelmet<E extends LivingEntity> extends ExtendedSpecialArmorModel<E> {

	public ExtendedModelGundabadUrukHelmet(BipedModel<E> referenceBipedModel) {
		this(referenceBipedModel, 1.0F);
	}

	public ExtendedModelGundabadUrukHelmet(BipedModel<E> referenceBipedModel, float f) {
		super(referenceBipedModel, f, 64, 32);
		clearNonHelmetParts();

		head = new ModelRenderer(this, 0 , 0);
		head.addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, f);
		head.setPos(0.0F, 0.0F, 0.0F);
		ModelRenderer horn1 = new ModelRenderer(this, 32, 0);
		horn1.setPos(-f, -f, -f);
		horn1.addBox(-7.0F, -12.0F, 0.5F, 3.0f, 8.0f, 0.0f, 0.0F);
		setRotationAngle(horn1, 0.0f, 0.0f, 0.10471976f);
		ModelRenderer horn2 = new ModelRenderer(this, 32, 0);
		horn2.setPos(f, -f, -f);
		horn2.mirror = true;
		horn2.addBox(4.0F, -12.0F, 0.5F, 3.0f, 8.0f, 0.0f, 0.0F);
		setRotationAngle(horn2, 0.0f, 0.0f, -0.10471976f);
		head.addChild(horn1);
		head.addChild(horn2);
	}
}