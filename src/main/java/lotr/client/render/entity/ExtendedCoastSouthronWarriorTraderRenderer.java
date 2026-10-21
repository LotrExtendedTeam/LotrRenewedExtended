package lotr.client.render.entity;

import lotr.client.render.entity.layers.ExtendedHeldCoinLayer;
import lotr.client.render.entity.model.ManModel;
import lotr.common.entity.npc.ManEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;

public class ExtendedCoastSouthronWarriorTraderRenderer<E extends ManEntity> extends CoastSouthronWarriorRenderer<E>{

	public ExtendedCoastSouthronWarriorTraderRenderer(EntityRendererManager mgr) {
		super(mgr);
		this.addLayer(new ExtendedHeldCoinLayer<E, ManModel<E>>(this));

	}

}
