package lotr.client.render.entity;

import com.mojang.blaze3d.matrix.MatrixStack;

import lotr.common.entity.npc.BreeManEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;

public class ExtendedHiredBreeGuardRenderer extends BreeManRenderer {

    public ExtendedHiredBreeGuardRenderer (EntityRendererManager mgr) {
        super(mgr);
    }

    @Override
    public void render(BreeManEntity entity, float yaw, float partialTicks, MatrixStack matStack, IRenderTypeBuffer buf, int packedLight) {
        super.render(entity, yaw, partialTicks, matStack, buf, packedLight);
    }

}
