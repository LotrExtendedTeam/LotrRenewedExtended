package lotr.client.render.entity;

import lotr.client.render.entity.layers.ExtendedHeldCoinLayer;
import lotr.client.render.entity.model.ManModel;
import lotr.common.entity.npc.DunlendingEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;

public class ExtendedDunlendingTraderRenderer extends DunlendingRenderer {
	public ExtendedDunlendingTraderRenderer(EntityRendererManager mgr) {
		super(mgr);
		this.addLayer(new ExtendedHeldCoinLayer<DunlendingEntity, ManModel<DunlendingEntity>>(this));
	}
}
