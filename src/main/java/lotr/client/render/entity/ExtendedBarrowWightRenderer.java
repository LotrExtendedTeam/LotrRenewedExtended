package lotr.client.render.entity;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import lotr.client.render.RandomTextureVariants;
import lotr.client.render.entity.model.ExtendedBarrowWightModel;
import lotr.common.entity.npc.ExtendedBarrowWrightEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

public class ExtendedBarrowWightRenderer extends BipedRenderer<ExtendedBarrowWrightEntity, ExtendedBarrowWightModel<ExtendedBarrowWrightEntity>> {
    public static final RandomTextureVariants SKINS = RandomTextureVariants.loadSkinsList(new ResourceLocation(LotrExtendedMod.MODID, "textures/entity/barrow_wight"));

    public ExtendedBarrowWightRenderer(EntityRendererManager mgr) {
        super(mgr, new ExtendedBarrowWightModel<ExtendedBarrowWrightEntity>(0.0F), 0.5F);

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
        pMatrixStack.scale(-1.0F, -1.0F, 1.0F);
        pMatrixStack.translate(0.0D, -1.501D, 0.0D);
        float f = MathHelper.rotLerp(pPartialTicks, pEntity.yBodyRotO, pEntity.yBodyRot);
        float f1 = MathHelper.rotLerp(pPartialTicks, pEntity.yHeadRotO, pEntity.yHeadRot);
        float f2 = f1 - f;
        this.model.setupAnim(pEntity, pEntity.animationPosition - pEntity.animationSpeed * (1.0F - pPartialTicks), MathHelper.lerp(pPartialTicks, pEntity.animationSpeedOld, pEntity.animationSpeed), (float)pEntity.tickCount + pPartialTicks, f2 , MathHelper.lerp(pPartialTicks, pEntity.xRotO, pEntity.xRot));
        IVertexBuilder ivertexbuilder = pBuffer.getBuffer(getRenderType(pEntity, false, false, false));
        this.model.renderToBuffer(pMatrixStack, ivertexbuilder, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 0.5f);
    }

    @Override
    @Nullable
    protected RenderType getRenderType(ExtendedBarrowWrightEntity ranger, boolean p_230496_2_, boolean p_230496_3_, boolean p_230496_4_) {
        ResourceLocation resourceLocation = ExtendedBarrowWightRenderer.SKINS.getRandomSkin(ranger);
        return RenderType.entityCutoutNoCull(resourceLocation);
    }

}
