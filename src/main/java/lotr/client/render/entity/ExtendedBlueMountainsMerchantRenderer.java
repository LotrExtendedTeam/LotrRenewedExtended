package lotr.client.render.entity;

import lotr.client.render.GenderedRandomTextureVariants;
import lotr.common.entity.npc.DwarfEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class ExtendedBlueMountainsMerchantRenderer extends BlueDwarfRenderer {
    private static final GenderedRandomTextureVariants SKINS = new GenderedRandomTextureVariants("lotr", "textures/entity/dwarf/blue_mountains");

    public ExtendedBlueMountainsMerchantRenderer(EntityRendererManager mgr) {
        super(mgr);
    }

    public ResourceLocation getEntityTexture(DwarfEntity dwarf) {
        return SKINS.getRandomSkin(dwarf);
    }
}

