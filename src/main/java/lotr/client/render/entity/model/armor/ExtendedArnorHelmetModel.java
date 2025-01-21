package lotr.client.render.entity.model.armor;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class ExtendedArnorHelmetModel<E extends LivingEntity> extends ExtendedSpecialArmorModel<E> {
	public ExtendedArnorHelmetModel(BipedModel<E> referenceBipedModel) {
		this(referenceBipedModel, 1.0F);
	}

	public ExtendedArnorHelmetModel(BipedModel<E> referenceBipedModel, float f) {
		super(referenceBipedModel, f, 32, 32);
		clearNonHelmetParts();
		head = new ModelRenderer(this, 0, 0);
		head.setPos(0.0F, 0.0F, 0.0F);
		head.addBox(-4.0F, -8.0F, -4.0F, 8.0F, 9.0F, 8.0F, f);
		head.texOffs(0, 16).addBox(5.0F, -17.0F, -4.0F, 0.0F, 8.0F, 8.0F, 0.0F, true);
		head.texOffs(0, 16).addBox(-5.0F, -17.0F, -4.0F, 0.0F, 8.0F, 8.0F, 0.0F, true);
	}
}
