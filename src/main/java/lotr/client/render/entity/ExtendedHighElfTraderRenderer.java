package lotr.client.render.entity;

import lotr.client.render.entity.layers.ExtendedHeldCoinLayer;
import lotr.client.render.entity.model.ElfModel;
import lotr.common.entity.npc.ElfEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;

public class ExtendedHighElfTraderRenderer<E extends ElfEntity> extends HighElfRenderer<E>{

	public ExtendedHighElfTraderRenderer(EntityRendererManager mgr) {
		super(mgr);
		this.addLayer(new ExtendedHeldCoinLayer<E, ElfModel<E>>(this));
	}
}
