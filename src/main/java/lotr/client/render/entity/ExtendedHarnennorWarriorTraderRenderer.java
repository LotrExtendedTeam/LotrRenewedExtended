package lotr.client.render.entity;

import lotr.client.render.entity.layers.ExtendedHeldCoinLayer;
import lotr.client.render.entity.model.ManModel;
import lotr.common.entity.npc.HarnedhrimEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;

public class ExtendedHarnennorWarriorTraderRenderer extends HarnennorWarriorRenderer {

	public ExtendedHarnennorWarriorTraderRenderer(EntityRendererManager mgr) {
		super(mgr);
		this.addLayer(new ExtendedHeldCoinLayer<HarnedhrimEntity, ManModel<HarnedhrimEntity>>(this));

	}
}
