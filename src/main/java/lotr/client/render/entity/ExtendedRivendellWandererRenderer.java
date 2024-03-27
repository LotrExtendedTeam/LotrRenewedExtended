package lotr.client.render.entity;

import lotr.client.render.GenderedRandomTextureVariants;
import lotr.common.entity.npc.ExtendedRivendellWandererEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;

public class ExtendedRivendellWandererRenderer extends HighElfRenderer<ExtendedRivendellWandererEntity> {

    private static final GenderedRandomTextureVariants SKINS = new GenderedRandomTextureVariants("lotr", "textures/entity/elf/high_elf");

    public ExtendedRivendellWandererRenderer(EntityRendererManager mgr) {
        super(mgr);
    }
}
