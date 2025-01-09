package lotr.client.render.entity.layers;

import lotr.client.render.entity.model.ExtendedBarrowWightModel;
import lotr.common.entity.npc.ExtendedBarrowWightEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.AbstractEyesLayer;
import net.minecraft.util.ResourceLocation;

public class BarrowWightEyesLayer<T extends ExtendedBarrowWightEntity> extends AbstractEyesLayer<T, ExtendedBarrowWightModel<T>> {
    private static final RenderType BARROW_WIGHT_EYES = RenderType.eyes(new ResourceLocation("lotrextended:textures/entity/barrow_wight/eyes/eyes.png"));


    public BarrowWightEyesLayer(IEntityRenderer<T, ExtendedBarrowWightModel<T>> p_i226039_1_) {
        super(p_i226039_1_);
    }

    @Override
    public RenderType renderType() {
        return BARROW_WIGHT_EYES;
    }
}
