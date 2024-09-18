package lotr.client.render.entity.model.armor;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class ExtendedModelMorgulHelmet<E extends LivingEntity> extends ExtendedSpecialArmorModel<E> {
    private ModelRenderer[] spikes = new ModelRenderer[8];

    public ExtendedModelMorgulHelmet(BipedModel<E> referenceBipedModel) {
        this(referenceBipedModel, 1.0F);
    }

    public ExtendedModelMorgulHelmet(BipedModel<E> referenceBipedModel, float f) {
        super(referenceBipedModel, f);
        clearNonHelmetParts();
        head = new ModelRenderer(this, 0, 0);
        head.setPos(0.0F, 0.0F, 0.0F);
        head.addBox(-4.0F, -8.0F, -4.0F, 8.0F, 12.0F, 8.0F, f);
        head.texOffs(0,20).addBox(-3.5f, -18.0f, -3.5f, 7, 10, 1, f);

        for (int i = 0; i < spikes.length; ++i) {
            spikes[i] = new ModelRenderer(this, 16, 20);
            spikes[i].setPos(0.0F, 0.0F, 0.0F);
        }

        spikes[0].addBox(-1.0F, -5.5F, -10.0F, 1.0F, 1.0F, 4.0F);
        spikes[0].xRot = -0.3490658503988659F;
        spikes[0].yRot = 0.3490658503988659F;
        spikes[1].addBox(0.0F, -5.5F, -10.0F, 1.0F, 1.0F, 4.0F);
        spikes[1].xRot = -0.3490658503988659F;
        spikes[1].yRot = -0.3490658503988659F;
        spikes[2].addBox(6.0F, -5.5F, -1.0F, 4.0F, 1.0F, 1.0F);
        spikes[2].zRot = -0.3490658503988659F;
        spikes[2].yRot = 0.3490658503988659F;
        spikes[3].addBox(6.0F, -5.5F, 0.0F, 4.0F, 1.0F, 1.0F);
        spikes[3].zRot = -0.3490658503988659F;
        spikes[3].yRot = -0.3490658503988659F;
        spikes[4].addBox(0.0F, -5.5F, 6.0F, 1.0F, 1.0F, 4.0F);
        spikes[4].xRot = 0.3490658503988659F;
        spikes[4].yRot = 0.3490658503988659F;
        spikes[5].addBox(-1.0F, -5.5F, 6.0F, 1.0F, 1.0F, 4.0F);
        spikes[5].xRot = 0.3490658503988659F;
        spikes[5].yRot = -0.3490658503988659F;
        spikes[6].addBox(-10.0F, -5.5F, 0.0F, 4.0F, 1.0F, 1.0F);
        spikes[6].zRot = 0.3490658503988659F;
        spikes[6].yRot = 0.3490658503988659F;
        spikes[7].addBox(-10.0F, -5.5F, -1.0F, 4.0F, 1.0F, 1.0F);
        spikes[7].zRot = 0.3490658503988659F;
        spikes[7].yRot = -0.3490658503988659F;


        for (int i = 0; i < spikes.length; ++i) {
            head.addChild(spikes[i]);
        }
    }
}
