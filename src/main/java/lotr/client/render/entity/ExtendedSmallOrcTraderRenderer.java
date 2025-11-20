package lotr.client.render.entity;

import lotr.client.render.entity.layers.ExtendedHeldCoinLayer;
import lotr.client.render.entity.model.OrcModel;
import lotr.common.entity.npc.OrcEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;

public class ExtendedSmallOrcTraderRenderer extends SmallOrcRenderer {
	public ExtendedSmallOrcTraderRenderer(EntityRendererManager mgr) {
		super(mgr);
		this.addLayer(new ExtendedHeldCoinLayer<OrcEntity, OrcModel<OrcEntity>>(this));
	}
}
