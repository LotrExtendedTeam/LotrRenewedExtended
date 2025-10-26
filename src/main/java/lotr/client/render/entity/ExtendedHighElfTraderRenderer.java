package lotr.client.render.entity;

import lotr.client.render.entity.layers.coinlayers.HighElfHeldCoinLayer;
import lotr.common.entity.npc.ElfEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;

public class ExtendedHighElfTraderRenderer<E extends ElfEntity> extends HighElfRenderer<E>{

	public ExtendedHighElfTraderRenderer(EntityRendererManager mgr) {
		super(mgr);
		this.addLayer(new HighElfHeldCoinLayer<E>(this));
	}
}
