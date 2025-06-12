package lotr.client.render.entity;

import lotr.client.render.entity.layers.coinlayers.DwarfHeldCoinLayer;
import net.minecraft.client.renderer.entity.EntityRendererManager;

public class ExtendedBlueMountainsRenderer extends BlueDwarfRenderer {

	public ExtendedBlueMountainsRenderer(EntityRendererManager mgr) {
		super(mgr);
		this.addLayer(new DwarfHeldCoinLayer(this));
	}
}

