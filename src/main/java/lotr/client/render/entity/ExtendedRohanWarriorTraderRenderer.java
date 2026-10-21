package lotr.client.render.entity;

import lotr.client.render.entity.layers.ExtendedHeldCoinLayer;
import lotr.client.render.entity.model.ManModel;
import lotr.common.entity.npc.RohanManEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;

public class ExtendedRohanWarriorTraderRenderer extends RohirrimWarriorRenderer{

	public ExtendedRohanWarriorTraderRenderer(EntityRendererManager mgr) {
		super(mgr);
		this.addLayer(new ExtendedHeldCoinLayer<RohanManEntity, ManModel<RohanManEntity>>(this));

	}

}
