package lotr.client.render.entity;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import lotr.client.render.RandomTextureVariants;
import lotr.client.render.entity.layers.BarrowWightEyesLayer;
import lotr.client.render.entity.model.ExtendedBarrowWightModel;
import lotr.common.entity.npc.ExtendedBarrowWrightEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class ExtendedBarrowWightRenderer extends BipedRenderer<ExtendedBarrowWrightEntity, ExtendedBarrowWightModel<ExtendedBarrowWrightEntity>> {
	public static final RandomTextureVariants SKINS = RandomTextureVariants.loadSkinsList(new ResourceLocation(LotrExtendedMod.MODID, "textures/entity/barrow_wight"));

    public ExtendedBarrowWightRenderer(EntityRendererManager mgr) {
        super(mgr, new ExtendedBarrowWightModel<>(0.0F), 0.5F);
        this.addLayer(new BarrowWightEyesLayer<>(this));
    }

    @Override
    public ResourceLocation getTextureLocation(ExtendedBarrowWrightEntity man) {
        return SKINS.getRandomSkin(man);
    }

    @Override
	protected void scale(ExtendedBarrowWrightEntity entity, MatrixStack matStack, float f) {
		super.scale(entity, matStack, f);
		//matStack.scale(0.965f, 0.965F, 0.965F);
		//matStack.scale(0.965f, 1.05F, 0.965f);
	}


    @Override
    public void render(ExtendedBarrowWrightEntity pEntity, float pEntityYaw, float pPartialTicks, MatrixStack pMatrixStack, IRenderTypeBuffer pBuffer, int pPackedLight) {

        pMatrixStack.pushPose();
        this.model.attackTime = this.getAttackAnim(pEntity, pPartialTicks);

        float f = MathHelper.rotLerp(pPartialTicks, pEntity.yBodyRotO, pEntity.yBodyRot);
        float f1 = MathHelper.rotLerp(pPartialTicks, pEntity.yHeadRotO, pEntity.yHeadRot);
        float f2 = f1 - f;
        float f5 = pEntity.animationPosition - pEntity.animationSpeed * (1.0F - pPartialTicks);
        float f8 = MathHelper.lerp(pPartialTicks, pEntity.animationSpeedOld, pEntity.animationSpeed);
        float f7 = pEntity.tickCount + pPartialTicks;
        float f6 = MathHelper.lerp(pPartialTicks, pEntity.xRotO, pEntity.xRot);

        this.setupRotations(pEntity, pMatrixStack, f7, f, pPartialTicks);
        pMatrixStack.scale(-1.0F, -1.0F, 1.0F);
        this.scale(pEntity, pMatrixStack, pPartialTicks);
        pMatrixStack.translate(0.0D, -1.501D, 0.0D);
        this.model.prepareMobModel(pEntity, f5, f8, pPartialTicks);
        this.model.setupAnim(pEntity, f7*0.1f, 0.2f, f7, f2 , f6);

        IVertexBuilder ivertexbuilder = pBuffer.getBuffer(getRenderType(pEntity, false, false, false));
        int i = getOverlayCoords(pEntity, this.getWhiteOverlayProgress(pEntity, pPartialTicks));
        this.model.renderToBuffer(pMatrixStack, ivertexbuilder, pPackedLight, i, 1.0F, 1.0F, 1.0F, 0.5f);
        if (!pEntity.isSpectator()) {
            for(LayerRenderer<ExtendedBarrowWrightEntity, ExtendedBarrowWightModel<ExtendedBarrowWrightEntity>> layerrenderer : this.layers) {
                layerrenderer.render(pMatrixStack, pBuffer, pPackedLight, pEntity, f5, f8, pPartialTicks, f7, f2, f6);
            }
        }
        pMatrixStack.popPose();
    }

    @Override
    @Nullable
    protected RenderType getRenderType(ExtendedBarrowWrightEntity ranger, boolean p_230496_2_, boolean p_230496_3_, boolean p_230496_4_) {
        return RenderType.entityCutoutNoCull(getTextureLocation(ranger));
    }
}