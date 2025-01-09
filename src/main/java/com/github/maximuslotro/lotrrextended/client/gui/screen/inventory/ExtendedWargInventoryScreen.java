package com.github.maximuslotro.lotrrextended.client.gui.screen.inventory;

import com.github.maximuslotro.lotrrextended.common.inventory.ExtendedWargInventoryContainer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import lotr.common.entity.npc.ExtendedRidableWargEntity;
import lotr.common.entity.npc.WargEntity;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.entity.IEquipable;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ExtendedWargInventoryScreen extends ContainerScreen<ExtendedWargInventoryContainer> {
   private static final ResourceLocation WARG_INVENTORY_LOCATION = new ResourceLocation("textures/gui/container/horse.png");
   /** The Warg whose inventory is currently being accessed. */
   private final WargEntity warg;
   /** The mouse x-position recorded during the last rendered frame. */
   private float xMouse;
   /** The mouse y-position recorded during the last renderered frame. */
   private float yMouse;

   public ExtendedWargInventoryScreen(ExtendedWargInventoryContainer pWargInventoryMenu, PlayerInventory pPlayerInventory, WargEntity pWarg) {
      super(pWargInventoryMenu, pPlayerInventory, pWarg.getDisplayName());
      this.warg = pWarg;
      this.passEvents = false;
   }

   @Override
protected void renderBg(MatrixStack pMatrixStack, float pPartialTicks, int pX, int pY) {
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.minecraft.getTextureManager().bind(WARG_INVENTORY_LOCATION);
      int i = (this.width - this.imageWidth) / 2;
      int j = (this.height - this.imageHeight) / 2;
      this.blit(pMatrixStack, i, j, 0, 0, this.imageWidth, this.imageHeight);
//      if (this.horse instanceof AbstractChestedHorseEntity) {
//         AbstractChestedHorseEntity abstractchestedhorseentity = (AbstractChestedHorseEntity)this.horse;
//         if (abstractchestedhorseentity.hasChest()) {
//            this.blit(pMatrixStack, i + 79, j + 17, 0, this.imageHeight, abstractchestedhorseentity.getInventoryColumns() * 18, 54);
//         }
//      }

      if (((IEquipable)warg).isSaddleable()) {
         this.blit(pMatrixStack, i + 7, j + 35 - 18, 18, this.imageHeight + 54, 18, 18);
      }

      if (((ExtendedRidableWargEntity)warg).canWearArmor()) {
         //if (this.warg instanceof LlamaEntity) {
         //   this.blit(pMatrixStack, i + 7, j + 35, 36, this.imageHeight + 54, 18, 18);
         //} else {
            this.blit(pMatrixStack, i + 7, j + 35, 0, this.imageHeight + 54, 18, 18);
         //}
      }

      InventoryScreen.renderEntityInInventory(i + 51, j + 60, 17, (float)(i + 51) - this.xMouse, (float)(j + 75 - 50) - this.yMouse, this.warg);
   }

   @Override
public void render(MatrixStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTicks) {
      this.renderBackground(pMatrixStack);
      this.xMouse = (float)pMouseX;
      this.yMouse = (float)pMouseY;
      super.render(pMatrixStack, pMouseX, pMouseY, pPartialTicks);
      this.renderTooltip(pMatrixStack, pMouseX, pMouseY);
   }
}
