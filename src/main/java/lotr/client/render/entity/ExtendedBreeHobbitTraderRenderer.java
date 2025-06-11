package lotr.client.render.entity;

import lotr.client.render.entity.layers.coinlayers.HobbitHeldCoinLayer;
import net.minecraft.client.renderer.entity.EntityRendererManager;

public class ExtendedBreeHobbitTraderRenderer extends HobbitRenderer {

	public ExtendedBreeHobbitTraderRenderer(EntityRendererManager mgr) {
		super(mgr);
		this.addLayer(new HobbitHeldCoinLayer(this));
	}
}
