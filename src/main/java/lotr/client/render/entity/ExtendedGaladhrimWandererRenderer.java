package lotr.client.render.entity;

import lotr.client.render.GenderedRandomTextureVariants;
import lotr.common.entity.npc.ExtendedAbstractCloakingElfEntity;
import lotr.common.entity.npc.ExtendedGaladhrimWandererEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class ExtendedGaladhrimWandererRenderer extends ExtendedAbstractElfWandererRenderer<ExtendedGaladhrimWandererEntity> {

	private static final GenderedRandomTextureVariants SKINS = new GenderedRandomTextureVariants("lotr", "textures/entity/elf/galadhrim");

	public ExtendedGaladhrimWandererRenderer(EntityRendererManager mgr) {
		super(mgr);
	}

	@Override
	public ResourceLocation getTextureLocation(ExtendedAbstractCloakingElfEntity elf) {
		return SKINS.getRandomSkin(elf);
	}
}
