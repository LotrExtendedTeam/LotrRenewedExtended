package lotr.client.render.entity;

import lotr.client.render.entity.layers.ExtendedHeldCoinLayer;
import lotr.client.render.entity.model.ElfModel;
import lotr.common.entity.npc.GaladhrimElfEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;

public class ExtendedGaladhrimElfTraderRenderer extends GaladhrimElfRenderer<GaladhrimElfEntity>{
	public ExtendedGaladhrimElfTraderRenderer (EntityRendererManager mgr) {
		super(mgr);
		this.addLayer(new ExtendedHeldCoinLayer<GaladhrimElfEntity, ElfModel<GaladhrimElfEntity>>(this));
	}
}
