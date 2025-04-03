package lotr.client.render.entity;

import lotr.client.render.GenderedRandomTextureVariants;
import lotr.client.render.entity.layers.coinlayers.BreeManHeldCoinLayer;
import lotr.common.entity.npc.BreeManEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class ExtendedBreeSmithRenderer extends BreeManRenderer {
	private static final GenderedRandomTextureVariants SKINS = new GenderedRandomTextureVariants("lotr", "textures/entity/bree/bree");

	public ExtendedBreeSmithRenderer(EntityRendererManager mgr) {
		super(mgr);
		this.addLayer(new BreeManHeldCoinLayer(this));
	}

	@Override
	public ResourceLocation getTextureLocation(BreeManEntity man) {
		return SKINS.getRandomSkin(man);
	}
}
