package lotr.client.render.entity;

import lotr.client.render.GenderedRandomTextureVariants;
import lotr.client.render.entity.layers.DunedainCapeLayer;
import lotr.client.render.entity.model.ExtendedRangerModel;
import lotr.common.entity.npc.ExtendedDunedainRangerEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import javax.annotation.Nullable;

public class ExtendedDunedainRangerRenderer extends LOTRBipedRenderer<ExtendedDunedainRangerEntity, ExtendedRangerModel<ExtendedDunedainRangerEntity>> {
    public static final GenderedRandomTextureVariants SKINS = new GenderedRandomTextureVariants("lotr", "textures/entity/dunedain_north/ranger");

    public ExtendedDunedainRangerRenderer(EntityRendererManager mgr) {
        super(mgr, ExtendedRangerModel::new, new ExtendedRangerModel(0.5F), new ExtendedRangerModel(1.0F), 0.5F);
        this.addLayer(new DunedainCapeLayer(this));
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
