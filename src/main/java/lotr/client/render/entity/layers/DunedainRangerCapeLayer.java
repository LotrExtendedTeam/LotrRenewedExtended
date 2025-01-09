package lotr.client.render.entity.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import lotr.client.render.entity.model.ExtendedRangerModel;
import lotr.common.entity.npc.ExtendedDunedainRangerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

public class DunedainRangerCapeLayer extends LayerRenderer<ExtendedDunedainRangerEntity, ExtendedRangerModel<ExtendedDunedainRangerEntity>> {
    public DunedainRangerCapeLayer(IEntityRenderer<ExtendedDunedainRangerEntity, ExtendedRangerModel<ExtendedDunedainRangerEntity>> renderer) {
        super(renderer);
    }

    @Override
	public void render(MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, ExtendedDunedainRangerEntity ranger, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (ranger.getCloakTextureLocation() != null) {
            ItemStack chestItemStack = ranger.getItemBySlot(EquipmentSlotType.CHEST);
            if (chestItemStack.getItem() != Items.ELYTRA) {
                matrixStack.pushPose();
                matrixStack.translate(0.0, 0.0, 0.125);
                double offsetX = MathHelper.lerp(partialTicks, ranger.xCloakO, ranger.xCloak) - MathHelper.lerp(partialTicks, ranger.xo, ranger.getX());
                double offsetY = MathHelper.lerp(partialTicks, ranger.yCloakO, ranger.yCloak) - MathHelper.lerp(partialTicks, ranger.yo, ranger.getY());
                double offsetZ = MathHelper.lerp(partialTicks, ranger.zCloakO, ranger.zCloak) - MathHelper.lerp(partialTicks, ranger.zo, ranger.getZ());
                float bodyRotationYaw = ranger.yBodyRotO + (ranger.yBodyRot - ranger.yBodyRotO);
                double sinYaw = MathHelper.sin(bodyRotationYaw * 0.017453292F);
                double cosYaw = -MathHelper.cos(bodyRotationYaw * 0.017453292F);
                float verticalOffset = (float)offsetY * 10.0F;
                verticalOffset = MathHelper.clamp(verticalOffset, -6.0F, 32.0F);
                float xRotationOffset = (float)(offsetX * sinYaw + offsetZ * cosYaw) * 100.0F;
                xRotationOffset = MathHelper.clamp(xRotationOffset, 0.0F, 150.0F);
                float zRotationOffset = (float)(offsetX * cosYaw - offsetZ * sinYaw) * 100.0F;
                zRotationOffset = MathHelper.clamp(zRotationOffset, -20.0F, 20.0F);
                if (xRotationOffset < 0.0F) {
                    xRotationOffset = 0.0F;
                }

                float bob = MathHelper.lerp(partialTicks, ranger.oBob, ranger.bob);
                verticalOffset += MathHelper.sin(MathHelper.lerp(partialTicks, ranger.walkDistO, ranger.walkDist) * 6.0F) * 32.0F * bob;
                if (ranger.isCrouching()) {
                    verticalOffset += 25.0F;
                }

                matrixStack.mulPose(Vector3f.XP.rotationDegrees(6.0F + xRotationOffset / 2.0F + verticalOffset));
                matrixStack.mulPose(Vector3f.ZP.rotationDegrees(zRotationOffset / 2.0F));
                matrixStack.mulPose(Vector3f.YP.rotationDegrees(180.0F - zRotationOffset / 2.0F));

                IVertexBuilder vertexBuilder = null;
                if (ranger.isCloaked()) {
                    vertexBuilder = buffer.getBuffer(RenderType.itemEntityTranslucentCull(ranger.getCloakTextureLocation()));
                } else {
                    vertexBuilder = buffer.getBuffer(RenderType.entitySolid(ranger.getCloakTextureLocation()));
                }

                getParentModel().renderCloak(ranger, matrixStack, vertexBuilder, combinedLight, OverlayTexture.NO_OVERLAY);
                matrixStack.popPose();
            }
        }
    }
}