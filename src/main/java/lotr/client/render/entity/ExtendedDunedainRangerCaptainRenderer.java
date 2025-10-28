package lotr.client.render.entity;

import lotr.client.render.entity.layers.ExtendedHeldCoinLayer;
import lotr.client.render.entity.model.ExtendedRangerModel;
import lotr.common.entity.npc.ExtendedDunedainRangerEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;

public class ExtendedDunedainRangerCaptainRenderer extends ExtendedDunedainRangerRenderer {

	public ExtendedDunedainRangerCaptainRenderer(EntityRendererManager mgr) {
		super(mgr);
		this.addLayer(new ExtendedHeldCoinLayer<ExtendedDunedainRangerEntity, ExtendedRangerModel<ExtendedDunedainRangerEntity>>(this));
	}
}
