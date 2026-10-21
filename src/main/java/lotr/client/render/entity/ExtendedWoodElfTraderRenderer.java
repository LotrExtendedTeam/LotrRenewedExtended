package lotr.client.render.entity;

import lotr.client.render.entity.layers.ExtendedHeldCoinLayer;
import lotr.client.render.entity.model.ElfModel;
import lotr.common.entity.npc.WoodElfEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;

public class ExtendedWoodElfTraderRenderer extends WoodElfRenderer<WoodElfEntity>{
	public ExtendedWoodElfTraderRenderer (EntityRendererManager mgr) {
		super(mgr);
		this.addLayer(new ExtendedHeldCoinLayer<WoodElfEntity, ElfModel<WoodElfEntity>>(this));
	}
}
