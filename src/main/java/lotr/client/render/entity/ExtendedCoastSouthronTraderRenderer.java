package lotr.client.render.entity;

import lotr.client.render.entity.layers.ExtendedHeldCoinLayer;
import lotr.client.render.entity.model.ManModel;
import lotr.common.entity.npc.ManEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;

public class ExtendedCoastSouthronTraderRenderer<E extends ManEntity> extends CoastSouthronRenderer<E>{

	public ExtendedCoastSouthronTraderRenderer(EntityRendererManager mgr) {
		super(mgr);
		this.addLayer(new ExtendedHeldCoinLayer<E, ManModel<E>>(this));

	}

}
