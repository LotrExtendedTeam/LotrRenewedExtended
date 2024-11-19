package com.github.maximuslotro.lotrrextended.client.particle;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
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
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BarrowWightJumpscareParticle extends Particle {
    private final Model model = new ExtendedBarrowWightModel<>(0.0f);
    private final RenderType renderType = RenderType.entityTranslucent(new ResourceLocation("lotrextended:textures/particle/scare_wight_1.png"));
    private float angle;


    private BarrowWightJumpscareParticle(ClientWorld clientWorld, double pX, double pY, double pZ) {
        super(clientWorld, pX, pY, pZ);
        this.gravity = 0.0F;
        this.lifetime = 60;
        this.angle = clientWorld.random.nextFloat();
    }

    public IParticleRenderType getRenderType() {
        return IParticleRenderType.CUSTOM;
    }

    @Override
    public boolean shouldCull() {
        return false;
    }

    public void render(IVertexBuilder pBuffer, ActiveRenderInfo pRenderInfo, float pPartialTicks) {
        float f = ((float)this.age + pPartialTicks) / (float)this.lifetime;
        float alpha = 0.25f*MathHelper.sin(f * (float)Math.PI);
        MatrixStack matrixstack = new MatrixStack();
        matrixstack.mulPose(pRenderInfo.rotation());
        matrixstack.mulPose(Vector3f.ZP.rotationDegrees(45.0f*(2*angle - 1)));
        matrixstack.mulPose(Vector3f.XP.rotationDegrees(-150.0F * f + 90.0F));
        matrixstack.scale(-1.0F, -1.0F, 1.0F);
        matrixstack.translate(0, 0, 1.0D);
        IRenderTypeBuffer.Impl irendertypebuffer$impl = Minecraft.getInstance().renderBuffers().bufferSource();
        IVertexBuilder ivertexbuilder = irendertypebuffer$impl.getBuffer(this.renderType);
        this.model.renderToBuffer(matrixstack, ivertexbuilder, 15728880, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, alpha);
        irendertypebuffer$impl.endBatch();
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements IParticleFactory<BasicParticleType> {

        public Particle createParticle(BasicParticleType pType, ClientWorld pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            return new BarrowWightJumpscareParticle(pLevel, pX, pY, pZ);
        }
    }
}