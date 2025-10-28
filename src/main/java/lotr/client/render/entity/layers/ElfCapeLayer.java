package lotr.client.render.entity.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import lotr.client.render.entity.model.ExtendedCloakingElfModel;
import lotr.common.entity.npc.ExtendedAbstractCloakingElfEntity;
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

public class ElfCapeLayer<E extends ExtendedAbstractCloakingElfEntity> extends LayerRenderer<E, ExtendedCloakingElfModel<E>> {

    public ElfCapeLayer(IEntityRenderer<E, ExtendedCloakingElfModel<E>> elf) {
        super(elf);
    }

    @Override
	public void render(MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, E elf, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (elf.getCapeTextureLocation() != null) {
            ItemStack chestItemStack = elf.getItemBySlot(EquipmentSlotType.CHEST);
            if (chestItemStack.getItem() != Items.ELYTRA) {
                matrixStack.pushPose();
                matrixStack.translate(0.0, 0.0, 0.125);
                double offsetX = MathHelper.lerp(partialTicks, elf.xCloakO, elf.xCloak) - MathHelper.lerp(partialTicks, elf.xo, elf.getX());
                double offsetY = MathHelper.lerp(partialTicks, elf.yCloakO, elf.yCloak) - MathHelper.lerp(partialTicks, elf.yo, elf.getY());
                double offsetZ = MathHelper.lerp(partialTicks, elf.zCloakO, elf.zCloak) - MathHelper.lerp(partialTicks, elf.zo, elf.getZ());
                float bodyRotationYaw = elf.yBodyRotO + (elf.yBodyRot - elf.yBodyRotO);
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

                float bob = MathHelper.lerp(partialTicks, elf.oBob, elf.bob);
                verticalOffset += MathHelper.sin(MathHelper.lerp(partialTicks, elf.walkDistO, elf.walkDist) * 6.0F) * 32.0F * bob;
                if (elf.isCrouching()) {
                    verticalOffset += 25.0F;
                }

                matrixStack.mulPose(Vector3f.XP.rotationDegrees(6.0F + xRotationOffset / 2.0F + verticalOffset));
                matrixStack.mulPose(Vector3f.ZP.rotationDegrees(zRotationOffset / 2.0F));
                matrixStack.mulPose(Vector3f.YP.rotationDegrees(180.0F - zRotationOffset / 2.0F));

                IVertexBuilder vertexBuilder = buffer.getBuffer(RenderType.entitySolid(elf.getCapeTextureLocation()));
                getParentModel().renderCape(elf, matrixStack, vertexBuilder, combinedLight, OverlayTexture.NO_OVERLAY);
                matrixStack.popPose();
            }
        }
    }

}