package lotr.client.render.entity;

import lotr.client.render.GenderedRandomTextureVariants;
import lotr.common.entity.npc.ExtendedAbstractElfWandererEntity;
import lotr.common.entity.npc.ExtendedRivendellWandererEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class ExtendedGaladhrimWandererRenderer extends ExtendedAbstractElfWandererRenderer<ExtendedRivendellWandererEntity> {

    private static final GenderedRandomTextureVariants SKINS = new GenderedRandomTextureVariants("lotr", "textures/entity/elf/galadhrim");

    public ExtendedGaladhrimWandererRenderer(EntityRendererManager mgr) {
        super(mgr);
    }

    public ResourceLocation getTextureLocation(ExtendedAbstractElfWandererEntity elf) {
        return SKINS.getRandomSkin(elf);
    }
}
