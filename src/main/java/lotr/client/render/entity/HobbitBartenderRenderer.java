package lotr.client.render.entity;

import lotr.client.render.GenderedRandomTextureVariants;
import lotr.client.render.RandomTextureVariants;
import lotr.client.render.entity.layers.HobbitOutfitLayer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.inventory.EquipmentSlotType;

public class HobbitBartenderRenderer extends HobbitRenderer {

    private static final GenderedRandomTextureVariants SKINS = new GenderedRandomTextureVariants("lotr", "textures/entity/hobbit/hobbit");

    // TODO: What is the way to load just one texture instead of doing this hacky method?
    private static final RandomTextureVariants OUTFITS = RandomTextureVariants.loadSkinsList("lotr", "textures/entity/hobbit/outfit/bartender");

    public HobbitBartenderRenderer(EntityRendererManager mgr) {
        super(mgr);
        this.addLayer(new HobbitOutfitLayer<>(this, OUTFITS, EquipmentSlotType.CHEST));
    }


}
