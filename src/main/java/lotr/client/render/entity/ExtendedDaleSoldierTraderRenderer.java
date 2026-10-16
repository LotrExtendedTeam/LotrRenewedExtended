package lotr.client.render.entity;

import lotr.client.render.entity.layers.ExtendedHeldCoinLayer;
import lotr.client.render.entity.model.ManModel;
import lotr.common.entity.npc.DaleManEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;

public class ExtendedDaleSoldierTraderRenderer extends DaleSoldierRenderer {

	public ExtendedDaleSoldierTraderRenderer(EntityRendererManager mgr) {
		super(mgr);
		this.addLayer(new ExtendedHeldCoinLayer<DaleManEntity, ManModel<DaleManEntity>>(this));

	}
}
