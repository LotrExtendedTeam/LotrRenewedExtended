package lotr.client.render.entity;

import lotr.client.render.entity.layers.ExtendedHeldCoinLayer;
import lotr.client.render.entity.model.HobbitModel;
import lotr.common.entity.npc.HobbitEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;

public class ExtendedBreeHobbitTraderRenderer extends HobbitRenderer {

	public ExtendedBreeHobbitTraderRenderer(EntityRendererManager mgr) {
		super(mgr);
		this.addLayer(new ExtendedHeldCoinLayer<HobbitEntity, HobbitModel<HobbitEntity>>(this));
	}
}
