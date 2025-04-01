package lotr.client.render.entity;

import lotr.client.render.RandomTextureVariants;
import lotr.client.render.entity.layers.OddmentCollectorHeldCoinLayer;
import lotr.common.entity.npc.ExtendedOddmentCollectorEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class ExtendedOddmentCollectorRenderer extends AbstractManRenderer<ExtendedOddmentCollectorEntity> {
	private static final RandomTextureVariants SKINS = RandomTextureVariants.loadSkinsList("lotr", "textures/entity/oddment_collectors");

	public ExtendedOddmentCollectorRenderer(EntityRendererManager mgr) {
		super(mgr);
		this.addLayer(new OddmentCollectorHeldCoinLayer(this));
	}

	@Override
	public ResourceLocation getTextureLocation(ExtendedOddmentCollectorEntity man) {
		return SKINS.getRandomSkin(man);
	}
}
