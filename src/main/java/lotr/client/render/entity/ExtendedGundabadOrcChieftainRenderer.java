package lotr.client.render.entity;

import lotr.client.render.entity.layers.SmallOrcHeldCoinLayer;
import net.minecraft.client.renderer.entity.EntityRendererManager;

public class ExtendedGundabadOrcChieftainRenderer extends ExtendedGundabadUrukRenderer {
    public ExtendedGundabadOrcChieftainRenderer (EntityRendererManager mgr) {
        super(mgr);
        // TODO: Make a LargeOrcHeldCoinLayer
        this.addLayer(new SmallOrcHeldCoinLayer(this));
    }
}
