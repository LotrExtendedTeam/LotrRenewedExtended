package com.github.maximuslotro.lotrrextended.client.gui.screen.inventory;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedUnitCombatTypes;
import com.github.maximuslotro.lotrrextended.common.inventory.container.ExtendedHiredUnitEquipmentContainer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public class ExtendedHiredUnitEquipmentScreen extends ExtendedHiredUnitScreen<ExtendedHiredUnitEquipmentContainer> {
	private static final ResourceLocation CONTAINER_BACKGROUND = new ResourceLocation(LotrExtendedMod.MODID,"textures/gui/container/unit_equipment_menu.png");
	/** The mouse x-position recorded during the last rendered frame. */
	private float xMouse;
	/** The mouse y-position recorded during the last renderered frame. */
	private float yMouse;
	DecimalFormat df = new DecimalFormat("0.#");

	public ExtendedHiredUnitEquipmentScreen(ExtendedHiredUnitEquipmentContainer unitEquipmentContainer, PlayerInventory pPlayerInventory, ITextComponent pTitle) {
		super(unitEquipmentContainer, pPlayerInventory, pTitle);
		df.setRoundingMode(RoundingMode.CEILING);
		setIndex(1);
	}

	@Override
	public void render(MatrixStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTicks) {
		this.renderBackground(pMatrixStack);
		this.xMouse = (float)pMouseX;
		this.yMouse = (float)pMouseY;
		super.render(pMatrixStack, pMouseX, pMouseY, pPartialTicks);
		if (getMenu().getBaseInventory() != null) {
			if(!getMenu().getBaseInventory().getItem(0).isEmpty()) {
				renderBaseGearSlot(leftPos + 109, topPos + 43, getMenu().getBaseInventory().getItem(0));
			}
			if(!getMenu().getBaseInventory().getItem(1).isEmpty()) {
				renderBaseGearSlot(leftPos + 109, topPos + 61, getMenu().getBaseInventory().getItem(1));
			}
			if(!getMenu().getBaseInventory().getItem(2).isEmpty()) {
				renderBaseGearSlot(leftPos + 109, topPos + 79, getMenu().getBaseInventory().getItem(2));
			}
			if(!getMenu().getBaseInventory().getItem(3).isEmpty()) {
				renderBaseGearSlot(leftPos + 109, topPos + 97, getMenu().getBaseInventory().getItem(3));
			}
			if(!getMenu().getBaseInventory().getItem(5).isEmpty()) {
				renderBaseGearSlot(leftPos + 127, topPos + 61, getMenu().getBaseInventory().getItem(5));
			}
			if(!getMenu().getBaseInventory().getItem(4).isEmpty()) {
				renderBaseGearSlot(leftPos + 127, topPos + 79, getMenu().getBaseInventory().getItem(4));
			}
			if(!getMenu().getBaseInventory().getItem(9).isEmpty()) {
				renderBaseGearSlot(leftPos + 127, topPos + 97, getMenu().getBaseInventory().getItem(9));
			}
		}
		this.renderTooltip(pMatrixStack, pMouseX, pMouseY);
	}

	@Override
	protected void renderBg(MatrixStack pMatrixStack, float pPartialTicks, int pMouseX, int pMouseY) {
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bind(CONTAINER_BACKGROUND);
		this.blit(pMatrixStack, leftPos, topPos, 0, 0, imageWidth, imageHeight);
		if(getMenu().profile.getUnitType()==ExtendedUnitCombatTypes.RANGED) {
			this.blit(pMatrixStack, leftPos+127, topPos+53, 176, 0, 42, 43);
		}
		InventoryScreen.renderEntityInInventory(leftPos + 44, topPos + 110, 17, leftPos + 44 - this.xMouse, topPos + 110 - this.yMouse, getMenu().getCompanion());
	}

	@Override
	protected void renderLabels(MatrixStack matrix, int pMouseX, int pMouseY) {
		this.font.draw(matrix, title, titleLabelX - (font.width(title)/2), titleLabelY, 4210752);
		this.font.draw(matrix, inventory.getDisplayName(), inventoryLabelX, inventoryLabelY, 4210752);
		// show armor points
		if (getMenu().getCompanion() != null) {
			double armor = getMenu().getCompanion().getAttributeValue(Attributes.ARMOR);
			double armorT = getMenu().getCompanion().getAttributeValue(Attributes.ARMOR_TOUGHNESS);
			//double attackD = entity.getAttributeValue(Attributes.ATTACK_DAMAGE);
			//double attackS = entity.getAttributeValue(Attributes.ATTACK_SPEED);
			this.font.draw(matrix, new TranslationTextComponent("gui.lotrextended.hired_unit_tooltip.title.armor").withStyle(TextFormatting.ITALIC), 6, 23, 4210752);
			this.font.draw(matrix, new TranslationTextComponent("gui.lotrextended.hired_unit_tooltip.title.armor_points", df.format(armor)), 6, 34, 15790320);
			this.font.draw(matrix, new TranslationTextComponent("gui.lotrextended.hired_unit_tooltip.title.armor_toughness", df.format(armorT)), 6, 46, 15790320);
			//this.font.draw(matrix, new TranslationTextComponent("gui.lotrextended.hired_unit_tooltip.title.attack_damage", df.format(attackD)), 80, this.titleLabelY + 60, 4210752);
			//this.font.draw(matrix, new TranslationTextComponent("gui.lotrextended.hired_unit_tooltip.title.attack_speed", df.format(attackS)), 80, this.titleLabelY + 80, 4210752);
		}
	}

	@Override
	protected void renderTooltip(MatrixStack stack, int pMouseX, int pMouseY) {
		super.renderTooltip(stack, pMouseX, pMouseY);
		// render tooltips for base gear
		if (isHovering(109, 43, pMouseX, pMouseY) && !getMenu().getBaseInventory().getItem(0).isEmpty()) {
			this.renderTooltip(stack, getMenu().getBaseInventory().getItem(0), pMouseX, pMouseY);
		}
		if (isHovering(109, 61, pMouseX, pMouseY) && !getMenu().getBaseInventory().getItem(1).isEmpty()) {
			this.renderTooltip(stack, getMenu().getBaseInventory().getItem(1), pMouseX, pMouseY);
		}
		if (isHovering(109, 79, pMouseX, pMouseY) && !getMenu().getBaseInventory().getItem(2).isEmpty()) {
			this.renderTooltip(stack, getMenu().getBaseInventory().getItem(2), pMouseX, pMouseY);
		}
		if (isHovering(109, 97, pMouseX, pMouseY) && !getMenu().getBaseInventory().getItem(3).isEmpty()) {
			this.renderTooltip(stack, getMenu().getBaseInventory().getItem(3), pMouseX, pMouseY);
		}
		if (getMenu().getRangedSlot()!=null && isHovering(127, 61, pMouseX, pMouseY) && !getMenu().getBaseInventory().getItem(5).isEmpty()) {
			this.renderTooltip(stack, getMenu().getBaseInventory().getItem(5), pMouseX, pMouseY);
		}
		if (isHovering(127, 79, pMouseX, pMouseY) && !getMenu().getBaseInventory().getItem(4).isEmpty()) {
			this.renderTooltip(stack, getMenu().getBaseInventory().getItem(4), pMouseX, pMouseY);
		}
		if (isHovering(127, 97, pMouseX, pMouseY) && !getMenu().getBaseInventory().getItem(9).isEmpty()) {
			this.renderTooltip(stack, getMenu().getBaseInventory().getItem(9), pMouseX, pMouseY);
		}
	}

	private void renderBaseGearSlot(int slotX, int slotY, ItemStack stack) {
		// Set rendering offsets
		this.setBlitOffset(100);
		this.itemRenderer.blitOffset = 100.0F;

		if(stack.hasTag() && stack.getTag().contains("langTitle")) {
			String name = stack.getTag().getString("langTitle");
			stack.setHoverName(new TranslationTextComponent(name).withStyle(TextFormatting.BOLD).withStyle(TextFormatting.GOLD));
		}
		// Render the item in the slot
		RenderSystem.enableDepthTest();
		this.itemRenderer.renderAndDecorateItem(this.minecraft.player, stack, slotX, slotY);
		this.itemRenderer.renderGuiItemDecorations(this.font, stack, slotX, slotY, null);

		// Reset rendering offsets
		this.itemRenderer.blitOffset = 0.0F;
		this.setBlitOffset(0);
	}

	private boolean isHovering(int hoverX, int hoverY, double mouseX, double mouseY) {
		// Adjust the mouse coordinates based on the position of the component
		double adjustedMouseX = mouseX - this.leftPos;
		double adjustedMouseY = mouseY - this.topPos;

		// Check if the adjusted mouse coordinates are within the bounds of the component
		boolean isWithinXBounds = adjustedMouseX >= (hoverX - 1) && adjustedMouseX < (hoverX + 16 + 1);
		boolean isWithinYBounds = adjustedMouseY >= (hoverY - 1) && adjustedMouseY < (hoverY + 16 + 1);

		return isWithinXBounds && isWithinYBounds;
	}
}
