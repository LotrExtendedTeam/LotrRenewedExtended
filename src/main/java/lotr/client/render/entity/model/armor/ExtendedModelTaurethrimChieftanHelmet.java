package lotr.client.render.entity.model.armor;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class ExtendedModelTaurethrimChieftanHelmet<E extends LivingEntity> extends ExtendedSpecialArmorModel<E> {
	private ModelRenderer crest;

	public ExtendedModelTaurethrimChieftanHelmet(BipedModel<E> referenceBipedModel) {
		this(referenceBipedModel, 1.0F);
	}

	public ExtendedModelTaurethrimChieftanHelmet(BipedModel<E> referenceBipedModel, float f) {
		super(referenceBipedModel, f);
		clearNonHelmetParts();
		head = new ModelRenderer(this, 0, 0);
		head.setPos(0.0F, 0.0F, 0.0F);
		head.addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, f);
		head.texOffs(32,0).addBox(-5.0f, -9.0f, 0.0f, 10, 6, 3, f);
		ModelRenderer crest = new ModelRenderer(this,0,16);
		crest.setPos(0.0f,-f,0.0f);
		crest.addBox(-8.0f, -23.0f, 0.0f, 16, 14, 0, 0.0f);
		setRotationAngle(crest, -0.17453292519943295f, 0.0f, 0.0F);
		head.addChild(crest);
	}
}
