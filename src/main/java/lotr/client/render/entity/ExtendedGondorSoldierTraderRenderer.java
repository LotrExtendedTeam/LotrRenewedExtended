package lotr.client.render.entity;

import lotr.client.render.entity.layers.ExtendedHeldCoinLayer;
import lotr.client.render.entity.model.ManModel;
import lotr.common.entity.npc.GondorManEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;

public class ExtendedGondorSoldierTraderRenderer extends GondorSoldierRenderer{

	public ExtendedGondorSoldierTraderRenderer(EntityRendererManager mgr) {
		super(mgr);
		this.addLayer(new ExtendedHeldCoinLayer<GondorManEntity, ManModel<GondorManEntity>>(this));

	}

}
