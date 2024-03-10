package lotr.client.render.entity;

import lotr.client.render.RandomTextureVariants;
import lotr.common.entity.npc.ExtendedOddmentCollector;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class ExtendedOddmentCollectorRenderer extends AbstractManRenderer<ExtendedOddmentCollector> {
    private static final RandomTextureVariants SKINS = RandomTextureVariants.loadSkinsList("lotr", "textures/entity/oddment_collector");

    public ExtendedOddmentCollectorRenderer(EntityRendererManager mgr) {
        super(mgr);
        // TODO: Create a HumanHeldCoinLayer
        // this.addLayer(new HobbitHeldCoinLayer(this));
    }

    public ResourceLocation getEntityTexture(ExtendedOddmentCollector man) {
        return SKINS.getRandomSkin(man);
    }
}
