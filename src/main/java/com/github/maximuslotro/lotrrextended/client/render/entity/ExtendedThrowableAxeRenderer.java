package com.github.maximuslotro.lotrrextended.client.render.entity;

import com.github.maximuslotro.lotrrextended.common.entity.projectile.ExtendedThrowableAxeEntity;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

public class ExtendedThrowableAxeRenderer extends EntityRenderer<ExtendedThrowableAxeEntity> {
    
    private final ItemRenderer itemRenderer;
    
    public ExtendedThrowableAxeRenderer(EntityRendererManager renderManager, ItemRenderer itemRenderer) {
        super(renderManager);
        this.itemRenderer = itemRenderer;
    }

    @Override
    public void render(ExtendedThrowableAxeEntity entity, float entityYaw, float partialTicks, 
                      MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight) {
        
        matrixStack.pushPose();
        
        // Apply rotation for spinning effect
        matrixStack.mulPose(Vector3f.YP.rotationDegrees(entity.xRot));
        matrixStack.mulPose(Vector3f.ZP.rotationDegrees(entity.yRot));
        
        // Scale the axe
        matrixStack.scale(1.0F, 1.0F, 1.0F);
        
        // Get the axe item stack
        ItemStack axeStack = entity.getProjectileItem();
        
        // Render the item
        this.itemRenderer.renderStatic(axeStack, ItemCameraTransforms.TransformType.GROUND, 
                                     packedLight, OverlayTexture.NO_OVERLAY, 
                                     matrixStack, buffer);
        
        matrixStack.popPose();
        
        super.render(entity, entityYaw, partialTicks, matrixStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(ExtendedThrowableAxeEntity entity) {
        // This method is required but not used since we're rendering the item directly
        return new ResourceLocation("minecraft", "textures/item/iron_axe.png");
    }
}