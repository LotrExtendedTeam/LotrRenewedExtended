package lotr.client.render.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import lotr.client.render.RandomTextureVariants;
import lotr.client.render.entity.layers.DunedainCapeLayer;
import lotr.client.render.entity.layers.OddmentCollectorHeldCoinLayer;
import lotr.client.render.entity.model.ExtendedRangerModel;
import lotr.client.render.entity.model.LOTRBipedModel;
import lotr.client.render.entity.model.ManModel;
import lotr.common.entity.npc.AbstractMannishEntity;
import lotr.common.entity.npc.ExtendedDunedainRangerEntity;
import lotr.common.entity.npc.ExtendedOddmentCollectorEntity;
import lotr.common.entity.npc.HobbitEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class ExtendedDunedainRangerRenderer extends LOTRBipedRenderer<ExtendedDunedainRangerEntity, ExtendedRangerModel<ExtendedDunedainRangerEntity>> {
    private static final RandomTextureVariants SKINS = RandomTextureVariants.loadSkinsList("lotr", "textures/entity/oddment_collectors");

    public ExtendedDunedainRangerRenderer(EntityRendererManager mgr) {
        super(mgr, ExtendedRangerModel::new, new ExtendedRangerModel(0.5F), new ExtendedRangerModel(1.0F), 0.5F);
        this.addLayer(new DunedainCapeLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(ExtendedDunedainRangerEntity man) {
       return SKINS.getRandomSkin(man);
    }

    public ResourceLocation getEntityTexture(ExtendedDunedainRangerEntity entity) {
        return SKINS.getRandomSkin(entity);
    }

    @Override
    public void render(ExtendedDunedainRangerEntity entity, float yaw, float partialTicks, MatrixStack matStack, IRenderTypeBuffer buf, int packedLight) {
        super.render(entity, yaw, partialTicks, matStack, buf, packedLight);

        /*
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, 0.0F, 0.125F);
        GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-10.0F, 1.0F, 0.0F, 0.0F);
        func_110776_a(capeTexture);
        this.capeModel.func_78111_c(0.0625F);
        GL11.glPopMatrix();
        */
    }
}
