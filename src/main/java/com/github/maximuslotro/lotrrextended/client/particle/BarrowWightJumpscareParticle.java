package com.github.maximuslotro.lotrrextended.client.particle;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import lotr.client.render.entity.ExtendedBarrowWightRenderer;
import lotr.client.render.entity.model.ExtendedBarrowWightModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BarrowWightJumpscareParticle extends Particle {
    private final Model model = new ExtendedBarrowWightModel<>(0.0f);
    private final RenderType renderType = RenderType.entityTranslucent(ExtendedBarrowWightRenderer.SKINS.getRandomSkin());

    private BarrowWightJumpscareParticle(ClientWorld clientWorld, double pX, double pY, double pZ) {
        super(clientWorld, pX, pY, pZ);
        this.gravity = 0.0F;
        this.lifetime = 30;
    }

    public IParticleRenderType getRenderType() {
        return IParticleRenderType.CUSTOM;
    }

    public void render(IVertexBuilder pBuffer, ActiveRenderInfo pRenderInfo, float pPartialTicks) {
        float f = ((float)this.age + pPartialTicks) / (float)this.lifetime;
        float f1 = MathHelper.cos(f * (float)Math.PI/2);
        MatrixStack matrixstack = new MatrixStack();
        matrixstack.mulPose(pRenderInfo.rotation());
        matrixstack.scale(-1.0F, -1.0F, 2-f1);
        matrixstack.translate(0.0D, 0.0D, 1.5D);
        IRenderTypeBuffer.Impl irendertypebuffer$impl = Minecraft.getInstance().renderBuffers().bufferSource();
        IVertexBuilder ivertexbuilder = irendertypebuffer$impl.getBuffer(this.renderType);
        this.model.renderToBuffer(matrixstack, ivertexbuilder, 15728880, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, f1);
        irendertypebuffer$impl.endBatch();
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements IParticleFactory<BasicParticleType> {

        public Particle createParticle(BasicParticleType pType, ClientWorld pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            return new BarrowWightJumpscareParticle(pLevel, pX, pY, pZ);
        }
    }
}