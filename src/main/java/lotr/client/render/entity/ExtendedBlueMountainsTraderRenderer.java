package lotr.client.render.entity;

import lotr.client.render.entity.layers.coinlayers.DwarfHeldCoinLayer;
import net.minecraft.client.renderer.entity.EntityRendererManager;

public class ExtendedBlueMountainsTraderRenderer extends BlueDwarfRenderer {

	public ExtendedBlueMountainsTraderRenderer(EntityRendererManager mgr) {
		super(mgr);
		this.addLayer(new DwarfHeldCoinLayer(this));
	}
}

