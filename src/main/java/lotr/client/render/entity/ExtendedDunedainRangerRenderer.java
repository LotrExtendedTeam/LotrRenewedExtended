package lotr.client.render.entity;

import lotr.client.render.RandomTextureVariants;
import lotr.client.render.entity.layers.OddmentCollectorHeldCoinLayer;
import lotr.common.entity.npc.ExtendedDunedainRangerEntity;
import lotr.common.entity.npc.ExtendedOddmentCollectorEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class ExtendedDunedainRangerRenderer extends AbstractManRenderer<ExtendedDunedainRangerEntity> {
    private static final RandomTextureVariants SKINS = RandomTextureVariants.loadSkinsList("lotr", "textures/entity/oddment_collectors");

    public ExtendedDunedainRangerRenderer(EntityRendererManager mgr) {
        super(mgr);
    }

    @Override
    public ResourceLocation getTextureLocation(ExtendedDunedainRangerEntity man) {
       return SKINS.getRandomSkin(man);
    }
}
