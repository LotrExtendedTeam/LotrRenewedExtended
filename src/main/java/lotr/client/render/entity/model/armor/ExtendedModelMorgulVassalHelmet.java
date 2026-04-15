package lotr.client.render.entity.model.armor;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class ExtendedModelMorgulVassalHelmet<E extends LivingEntity> extends ExtendedSpecialArmorModel<E> {
	private final ModelRenderer SpikeLS_r1;
	private final ModelRenderer SpikeRS_r1;
	private final ModelRenderer SpikeF_r1;
	private final ModelRenderer SpikeL_r1;
	private final ModelRenderer SpikeR_r1;

	public ExtendedModelMorgulVassalHelmet(BipedModel<E> referenceBipedModel) {
		this(referenceBipedModel, 1.0F);
	}

	public ExtendedModelMorgulVassalHelmet(BipedModel<E> referenceBipedModel, float f) {
		super(referenceBipedModel, f, 64, 32);
		clearNonHelmetParts();

		head = new ModelRenderer(this);
		head.setPos(0.0F, 0.0F, 0.0F);
		head.texOffs(0, 0).addBox(-4.0F, -9.0F, -4.0F, 8.0F, 11.0F, 8.0F, 0.7F, false);
		head.texOffs(0, 19).addBox(-4.0F, -15.4F, -4.0F, 8.0F, 5.0F, 8.0F, 0.7F, false);

		SpikeLS_r1 = new ModelRenderer(this);
		SpikeLS_r1.setPos(4.75F, -8.0F, 0.5F);
		head.addChild(SpikeLS_r1);
		setRotationAngle(SpikeLS_r1, 0.0F, -0.48F, 0.0F);
		SpikeLS_r1.texOffs(38, 21).addBox(-1.5F, -10.0F, 0.0F, 3.0F, 11.0F, 0.0F, 0.0F, true);

		SpikeRS_r1 = new ModelRenderer(this);
		SpikeRS_r1.setPos(-4.75F, -8.0F, 0.5F);
		head.addChild(SpikeRS_r1);
		setRotationAngle(SpikeRS_r1, 0.0F, 0.48F, 0.0F);
		SpikeRS_r1.texOffs(38, 21).addBox(-1.5F, -10.0F, 0.0F, 3.0F, 11.0F, 0.0F, 0.0F, false);

		SpikeF_r1 = new ModelRenderer(this);
		SpikeF_r1.setPos(0.0F, -8.0F, -2.75F);
		head.addChild(SpikeF_r1);
		setRotationAngle(SpikeF_r1, 0.4363F, 0.0F, 0.0F);
		SpikeF_r1.texOffs(32, 20).addBox(0.0F, -9.0F, -2.0F, 0.0F, 9.0F, 3.0F, 0.0F, false);

		SpikeL_r1 = new ModelRenderer(this);
		SpikeL_r1.setPos(3.0F, -8.5F, -3.75F);
		head.addChild(SpikeL_r1);
		setRotationAngle(SpikeL_r1, 0.4349F, -0.0368F, 0.0791F);
		SpikeL_r1.texOffs(1, 0).addBox(0.0F, -6.0F, -1.0F, 0.0F, 6.0F, 2.0F, 0.0F, true);

		SpikeR_r1 = new ModelRenderer(this);
		SpikeR_r1.setPos(-3.0F, -8.5F, -3.75F);
		head.addChild(SpikeR_r1);
		setRotationAngle(SpikeR_r1, 0.4349F, 0.0368F, -0.0791F);
		SpikeR_r1.texOffs(1, 0).addBox(0.0F, -6.0F, -1.0F, 0.0F, 6.0F, 2.0F, 0.0F, false);
	}
}