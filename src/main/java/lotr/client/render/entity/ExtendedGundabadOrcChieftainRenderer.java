package lotr.client.render.entity;

import lotr.client.render.entity.layers.coinlayers.SmallOrcHeldCoinLayer;
import net.minecraft.client.renderer.entity.EntityRendererManager;

public class ExtendedGundabadOrcChieftainRenderer extends LargeOrcRenderer {
	public ExtendedGundabadOrcChieftainRenderer (EntityRendererManager mgr) {
		super(mgr);
		this.addLayer(new SmallOrcHeldCoinLayer(this));
	}
}
