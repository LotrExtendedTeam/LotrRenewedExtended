package lotr.client.render.entity.model.armor;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public abstract class ExtendedSpecialArmorModel<E extends LivingEntity> extends SpecialArmorModel<E> {

	protected ExtendedSpecialArmorModel(BipedModel<E> referenceBipedModel, float modelSize, float yOffset, int texWidth, int texHeight) {
		super(referenceBipedModel, modelSize, yOffset, texWidth, texHeight);
	}

	protected ExtendedSpecialArmorModel(BipedModel<E> referenceBipedModel, float modelSize, int texWidth, int texHeight) {
		super(referenceBipedModel, modelSize, 0.0f, texWidth, texHeight);
	}

	public ExtendedSpecialArmorModel(BipedModel<E> referenceBipedModel, float modelSize) {
		super(referenceBipedModel, modelSize);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}