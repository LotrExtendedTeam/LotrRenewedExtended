package lotr.client.render.entity;

import lotr.client.render.RandomTextureVariants;
import lotr.common.entity.npc.BreeManEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class ExtendedOddmentCollectorRenderer extends BreeManRenderer {
    private static final RandomTextureVariants SKINS = RandomTextureVariants.loadSkinsList("lotr", "textures/entity/oddment_collectors");

    public ExtendedOddmentCollectorRenderer(EntityRendererManager mgr) {
        super(mgr);
        // TODO: Create a HumanHeldCoinLayer
        // this.addLayer(new HobbitHeldCoinLayer(this));
    }

    @Override
    public ResourceLocation getEntityTexture(BreeManEntity man) {
        return SKINS.getRandomSkin(man);
    }
}
