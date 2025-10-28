package lotr.client.render.entity;

import lotr.client.render.GenderedRandomTextureVariants;
import lotr.client.render.entity.layers.ExtendedHeldCoinLayer;
import lotr.client.render.entity.model.ManModel;
import lotr.common.entity.npc.BreeManEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class ExtendedBreeManTraderRenderer extends BreeManRenderer {
	private static final GenderedRandomTextureVariants SKINS = new GenderedRandomTextureVariants("lotr", "textures/entity/bree/bree");

	public ExtendedBreeManTraderRenderer(EntityRendererManager mgr) {
		super(mgr);
		this.addLayer(new ExtendedHeldCoinLayer<BreeManEntity, ManModel<BreeManEntity>>(this));
	}

	@Override
	public ResourceLocation getTextureLocation(BreeManEntity man) {
		return SKINS.getRandomSkin(man);
	}
}
