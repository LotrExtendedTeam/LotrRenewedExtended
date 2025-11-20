package lotr.client.render.entity;

import lotr.client.render.entity.layers.ExtendedHeldCoinLayer;
import lotr.client.render.entity.model.DwarfModel;
import lotr.common.entity.npc.DwarfEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;

public class ExtendedBlueMountainsTraderRenderer extends BlueDwarfRenderer {

	public ExtendedBlueMountainsTraderRenderer(EntityRendererManager mgr) {
		super(mgr);
		this.addLayer(new ExtendedHeldCoinLayer<DwarfEntity, DwarfModel<DwarfEntity>>(this));
	}
}

