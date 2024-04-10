package lotr.client.render.entity;

import lotr.client.render.GenderedRandomTextureVariants;
import lotr.client.render.entity.layers.DunedainRangerArmorLayer;
import lotr.client.render.entity.layers.DunedainRangerCapeLayer;
import lotr.client.render.entity.model.ExtendedRangerModel;
import lotr.common.entity.npc.ExtendedDunedainRangerEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ExtendedDunedainRangerRenderer extends LOTRBipedRenderer<ExtendedDunedainRangerEntity, ExtendedRangerModel<ExtendedDunedainRangerEntity>> {
    public static final GenderedRandomTextureVariants SKINS = new GenderedRandomTextureVariants("lotr", "textures/entity/dunedain_north/ranger");

    public ExtendedDunedainRangerRenderer(EntityRendererManager mgr) {
        super(mgr, ExtendedRangerModel::new, new ExtendedRangerModel<ExtendedDunedainRangerEntity>(0.5F), new ExtendedRangerModel<ExtendedDunedainRangerEntity>(1.0F), 0.5F);

        // Identify the layers to remove
        List<LayerRenderer<ExtendedDunedainRangerEntity, ExtendedRangerModel<ExtendedDunedainRangerEntity>>> layersToRemove = new ArrayList<>();
        for (LayerRenderer<ExtendedDunedainRangerEntity, ExtendedRangerModel<ExtendedDunedainRangerEntity>> layer : this.layers) {
            if (layer instanceof BipedArmorLayer) {
                layersToRemove.add(layer);
            }
        }
        // Remove the identified layers
        for (LayerRenderer<ExtendedDunedainRangerEntity, ExtendedRangerModel<ExtendedDunedainRangerEntity>> layer : layersToRemove) {
            this.layers.remove(layer);
        }

        this.addLayer(new DunedainRangerArmorLayer(this, new ExtendedRangerModel<ExtendedDunedainRangerEntity>(0.5F), new ExtendedRangerModel<ExtendedDunedainRangerEntity>(1.0F)));
        this.addLayer(new DunedainRangerCapeLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(ExtendedDunedainRangerEntity man) {
        return SKINS.getRandomSkin(man);
    }

    @Override
    @Nullable
    protected RenderType getRenderType(ExtendedDunedainRangerEntity ranger, boolean p_230496_2_, boolean p_230496_3_, boolean p_230496_4_) {
        ResourceLocation resourceLocation = ExtendedDunedainRangerRenderer.SKINS.getRandomSkin(ranger);
        if (ranger.isCloaked()) {
            return RenderType.itemEntityTranslucentCull(resourceLocation);
        } else {
            return RenderType.entityCutoutNoCull(resourceLocation);
        }
    }

}
