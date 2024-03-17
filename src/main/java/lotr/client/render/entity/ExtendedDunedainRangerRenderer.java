package lotr.client.render.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import lotr.client.render.GenderedRandomTextureVariants;
import lotr.client.render.entity.layers.DunedainCapeLayer;
import lotr.client.render.entity.model.ExtendedRangerModel;
import lotr.common.entity.npc.ExtendedDunedainRangerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class ExtendedDunedainRangerRenderer extends LOTRBipedRenderer<ExtendedDunedainRangerEntity, ExtendedRangerModel<ExtendedDunedainRangerEntity>> {
    private static final GenderedRandomTextureVariants SKINS = new GenderedRandomTextureVariants("lotr", "textures/entity/dunedain_north/ranger");

    public ExtendedDunedainRangerRenderer(EntityRendererManager mgr) {
        super(mgr, ExtendedRangerModel::new, new ExtendedRangerModel(0.5F), new ExtendedRangerModel(1.0F), 0.5F);
        this.addLayer(new DunedainCapeLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(ExtendedDunedainRangerEntity man) {
       return SKINS.getRandomSkin(man);
    }

    @Override
    public void render(ExtendedDunedainRangerEntity entity, float yaw, float partialTicks, MatrixStack matStack, IRenderTypeBuffer buf, int packedLight) {
        super.render(entity, yaw, partialTicks, matStack, buf, packedLight);
    }
}
