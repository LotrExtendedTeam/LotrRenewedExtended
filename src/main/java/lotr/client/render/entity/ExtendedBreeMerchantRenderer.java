package lotr.client.render.entity;

import lotr.client.render.GenderedRandomTextureVariants;
import lotr.common.entity.npc.ExtendedBreelandMerchantEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class ExtendedBreeMerchantRenderer extends BreeManRenderer {
        private static final GenderedRandomTextureVariants SKINS = new GenderedRandomTextureVariants("lotr", "textures/entity/bree/bree");

        public ExtendedBreeMerchantRenderer(EntityRendererManager mgr) {
            super(mgr);
        }

        public ResourceLocation getEntityTexture(ExtendedBreelandMerchantEntity man) {
            return SKINS.getRandomSkin(man);
        }
}
