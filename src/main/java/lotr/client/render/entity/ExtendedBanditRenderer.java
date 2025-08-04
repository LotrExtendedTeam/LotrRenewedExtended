package lotr.client.render.entity;

import lotr.client.render.GenderedRandomTextureVariants;
import lotr.client.render.RandomTextureVariants;
import lotr.common.entity.npc.BreeManEntity;
import lotr.common.entity.npc.ExtendedAbstractElfWandererEntity;
import lotr.common.entity.npc.ExtendedBanditEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class ExtendedBanditRenderer extends AbstractManRenderer<ExtendedBanditEntity> {
    private static final RandomTextureVariants SKINS = RandomTextureVariants.loadSkinsList("lotr", "textures/entity/bandit");

    public ExtendedBanditRenderer(EntityRendererManager mgr) {
        super(mgr);
    }
    @Override
    public ResourceLocation getTextureLocation(ExtendedBanditEntity bandit) {
        return SKINS.getRandomSkin(bandit);
    }
}
