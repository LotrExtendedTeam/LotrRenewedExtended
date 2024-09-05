package lotr.client.render.entity.model.armor;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class ExtendedModelGoldenTaurethrimHelmet extends ExtendedSpecialArmorModel<LivingEntity> {
    private ModelRenderer crest;

    public ExtendedModelGoldenTaurethrimHelmet(BipedModel<LivingEntity> referenceBipedModel) {
        this(referenceBipedModel, 1.0F);
    }

    public ExtendedModelGoldenTaurethrimHelmet(BipedModel<LivingEntity> referenceBipedModel, float f) {
        super(referenceBipedModel, f);
        clearNonHelmetParts();
        head = new ModelRenderer(this, 0, 0);
        head.setPos(0.0F, 0.0F, 0.0F);
        head.addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, f);
        ModelRenderer crest = new ModelRenderer(this,32,0);
        crest.setPos(0.0f,-f,0.0f);
        crest.addBox(-7.0f, -20.0f, 0.0f, 14, 12, 0, 0.0f);
        crest.xRot = -0.06981317007977318f;
        ModelRenderer tusks1 = new ModelRenderer(this, 0, 16);
        tusks1.setPos(-3.5f - f, 0.0f + f, -4.0f - f);
        tusks1.addBox(0.0f, -6.0f, -5.0f, 0, 6, 6, 0.0f);
        tusks1.xRot = 0.3490658503988659f;
        tusks1.yRot = 0.5235987755982988f;
        ModelRenderer tusks2 = new ModelRenderer(this, 0, 16);
        tusks2.setPos(-3.5f - f, 0.0f + f, -4.0f - f);
        tusks2.addBox(0.0f, -6.0f, -5.0f, 0, 6, 6, 0.0f);
        tusks2.xRot = 0.3490658503988659f;
        tusks2.yRot = -0.3490658503988659f;
        ModelRenderer tusks3 = new ModelRenderer(this, 0, 16);
        tusks3.setPos(3.5f + f, 0.0f + f, -4.0f - f);
        tusks3.addBox(0.0f, -6.0f, -5.0f, 0, 6, 6, 0.0f);
        tusks3.xRot = 0.3490658503988659f;
        tusks3.yRot = 0.3490658503988659f;
        ModelRenderer tusks4 = new ModelRenderer(this, 0, 16);
        tusks4.setPos(3.5f + f, 0.0f + f, -4.0f - f);
        tusks4.addBox(0.0f, -6.0f, -5.0f, 0, 6, 6, 0.0f);
        tusks4.xRot = 0.3490658503988659f;
        tusks4.yRot = -0.5235987755982988f;
        head.addChild(crest);
        head.addChild(tusks1);
        head.addChild(tusks2);
        head.addChild(tusks3);
        head.addChild(tusks4);
    }
}
