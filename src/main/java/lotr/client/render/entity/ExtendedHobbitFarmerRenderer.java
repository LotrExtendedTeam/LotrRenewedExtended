package lotr.client.render.entity;

import lotr.client.render.GenderedRandomTextureVariants;
import lotr.client.render.RandomTextureVariants;
import lotr.client.render.entity.layers.HobbitHeldCoinLayer;
import lotr.client.render.entity.layers.HobbitOutfitLayer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.inventory.EquipmentSlotType;

public class ExtendedHobbitFarmerRenderer extends HobbitRenderer {

    private static final GenderedRandomTextureVariants SKINS = new GenderedRandomTextureVariants("lotr", "textures/entity/hobbit/hobbit");

    public ExtendedHobbitFarmerRenderer(EntityRendererManager mgr) {
        super(mgr);
        this.addLayer(new HobbitHeldCoinLayer(this));
    }


}
