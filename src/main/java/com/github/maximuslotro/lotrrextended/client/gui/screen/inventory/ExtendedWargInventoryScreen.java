package com.github.maximuslotro.lotrrextended.client.gui.screen.inventory;

import com.github.maximuslotro.lotrrextended.common.inventory.ExtendedWargInventoryContainer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import lotr.common.entity.npc.ExtendedIRidableEntity;
import lotr.common.entity.npc.WargEntity;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.entity.IEquipable;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ExtendedWargInventoryScreen extends ExtendedContainerScreen<ExtendedWargInventoryContainer> {
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
		this.blit(pMatrixStack, leftPos, topPos, 0, 0, this.imageWidth, this.imageHeight);
		//      if (this.horse instanceof AbstractChestedHorseEntity) {
		//         AbstractChestedHorseEntity abstractchestedhorseentity = (AbstractChestedHorseEntity)this.horse;
		//         if (abstractchestedhorseentity.hasChest()) {
		//            this.blit(pMatrixStack, i + 79, j + 17, 0, this.imageHeight, abstractchestedhorseentity.getInventoryColumns() * 18, 54);
		//         }
		//      }

		if (((IEquipable)warg).isSaddleable()) {
			this.blit(pMatrixStack, leftPos + 7, topPos + 35 - 18, 18, this.imageHeight + 54, 18, 18);
		}

		if (((ExtendedIRidableEntity)warg).canWearArmor()) {
			this.blit(pMatrixStack, leftPos + 7, topPos + 35, 0, this.imageHeight + 54, 18, 18);
		}

		InventoryScreen.renderEntityInInventory(leftPos + 51, topPos + 60, 17, leftPos + 51 - this.xMouse, topPos + 25 - this.yMouse, this.warg);
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
