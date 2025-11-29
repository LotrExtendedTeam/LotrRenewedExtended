package com.github.maximuslotro.lotrrextended.client.render.entity;

import com.github.maximuslotro.lotrrextended.common.entity.special.ExtendedInvasionSpawnerEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

public class ExtendedInvasionSpawnerEntityRenderer extends EntityRenderer<ExtendedInvasionSpawnerEntity> {

    public ExtendedInvasionSpawnerEntityRenderer(EntityRendererManager entityRenderDispatcher) {
        super(entityRenderDispatcher);
    }

    @Override
    public void render(ExtendedInvasionSpawnerEntity entity, float pEntityYaw, float pPartialTicks, MatrixStack pMatrixStack, IRenderTypeBuffer pBuffer, int pPackedLight) {
        pMatrixStack.pushPose();

        // Apply rotation - interpolate between previous and current rotation for smooth animation
        float interpolatedYaw = entity.yRotO + (entity.yRot - entity.yRotO) * pPartialTicks;
        pMatrixStack.mulPose(Vector3f.YP.rotationDegrees(-interpolatedYaw));

        pMatrixStack.translate(0, 1.75F, 0);
        pMatrixStack.scale(3F, 3F, 3F);

        ItemStack item = getItemFromFactionKey(entity.getFactionKey());
        Minecraft.getInstance().getItemRenderer().renderStatic(item, ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND, pPackedLight, OverlayTexture.NO_OVERLAY, pMatrixStack, pBuffer);

        pMatrixStack.popPose();
    }

    private ItemStack getItemFromFactionKey(String faction) {
        ItemStack heldItem = new ItemStack(ExtendedItems.GUNDABAD_URUK_CLEAVER.get(), 1);
        String factionKey = "";
        String modifier = "";
        if (faction.contains(",")) {
            String[] split = faction.split(",");
            if (split.length == 2) {
                factionKey = split[0];
                modifier = split[1];
            }
        } else {
            factionKey = faction;
        }
        if (!factionKey.isEmpty()) {
            switch (factionKey) {
                case "lotr:northern_orcs":
                    heldItem = new ItemStack(ExtendedItems.GUNDABAD_URUK_CLEAVER.get(), 1);
                    break;
                case "lotr:dunedain_north":
                    heldItem = new ItemStack(ExtendedItems.DUNEDAIN_BOW.get(), 1);
                    break;
                case "lotr:high_elves":
                    if (!modifier.isEmpty()) {
                        if (modifier.equals("rivendell")) heldItem = new ItemStack(LOTRItems.RIVENDELL_SWORD.get(), 1);
                        if (modifier.equals("lindon")) heldItem = new ItemStack(LOTRItems.LINDON_SWORD.get(), 1);
                    }
                    break;
            }
        }

        return heldItem;
    }

    @Override
    public ResourceLocation getTextureLocation(ExtendedInvasionSpawnerEntity entity) {
        return null;
    }

    @Override
    protected boolean shouldShowName(ExtendedInvasionSpawnerEntity entity) {
        return false;
    }
}
