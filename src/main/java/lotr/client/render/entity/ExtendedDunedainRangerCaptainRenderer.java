package lotr.client.render.entity;

import lotr.client.render.entity.layers.coinlayers.DunedainManHeldCoinLayer;
import net.minecraft.client.renderer.entity.EntityRendererManager;

public class ExtendedDunedainRangerCaptainRenderer extends ExtendedDunedainRangerRenderer {

	public ExtendedDunedainRangerCaptainRenderer(EntityRendererManager mgr) {
		super(mgr);
		this.addLayer(new DunedainManHeldCoinLayer(this));
	}
}
