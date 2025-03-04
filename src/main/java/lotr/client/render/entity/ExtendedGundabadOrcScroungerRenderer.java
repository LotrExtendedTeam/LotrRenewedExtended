package lotr.client.render.entity;

import lotr.client.render.entity.layers.coinlayers.SmallOrcHeldCoinLayer;
import net.minecraft.client.renderer.entity.EntityRendererManager;

public class ExtendedGundabadOrcScroungerRenderer extends SmallOrcRenderer {
	public ExtendedGundabadOrcScroungerRenderer(EntityRendererManager mgr) {
		super(mgr);
		this.addLayer(new SmallOrcHeldCoinLayer(this));
	}
}
