package com.github.maximuslotro.lotrrextended.client.gui.screen.inventory;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedUnitOperationsType;
import com.github.maximuslotro.lotrrextended.common.inventory.container.ExtendedHiredUnitEquipmentContainer;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedCOpenHiredMenuPacket;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedPacketHandler;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import lotr.common.entity.npc.NPCEntity;
import net.minecraft.client.gui.IHasContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public class ExtendedHiredUnitEquipmentScreen extends ContainerScreen<ExtendedHiredUnitEquipmentContainer> implements IHasContainer<ExtendedHiredUnitEquipmentContainer> {

	private static final ResourceLocation CONTAINER_BACKGROUND = new ResourceLocation(LotrExtendedMod.MODID,"textures/gui/hired_unit_upgrade_equipment_menu.png");
	private static final ResourceLocation CONTAINER_BACKGROUND_MELEE = new ResourceLocation(LotrExtendedMod.MODID,"textures/gui/hired_unit_upgrade_equipment_menu_melee.png");

	DecimalFormat df = new DecimalFormat("0.0");

	private final NPCEntity entity;

	public ExtendedHiredUnitEquipmentScreen(ExtendedHiredUnitEquipmentContainer unitEquipmentContainer, PlayerInventory pPlayerInventory, ITextComponent pTitle) {
		super(unitEquipmentContainer, pPlayerInventory, pTitle);

		this.passEvents = false;
		this.imageHeight = 222;
		this.inventoryLabelY = 130;
		this.imageWidth = 176;
		df.setRoundingMode(RoundingMode.CEILING);

		entity = menu.getCompanion();
	}

	@Override
	public void render(MatrixStack p_98418_, int p_98419_, int p_98420_, float p_98421_) {
		this.renderBackground(p_98418_);
		super.render(p_98418_, p_98419_, p_98420_, p_98421_);

		if (getMenu().getBaseInventory() != null) {
			if(!getMenu().getBaseInventory().getItem(0).isEmpty()) {
				renderBaseGearSlot(leftPos + 25, topPos + 31, getMenu().getBaseInventory().getItem(0));
			}
			if(!getMenu().getBaseInventory().getItem(1).isEmpty()) {
				renderBaseGearSlot(leftPos + 25, topPos + 49, getMenu().getBaseInventory().getItem(1));
			}
			if(!getMenu().getBaseInventory().getItem(2).isEmpty()) {
				renderBaseGearSlot(leftPos + 25, topPos + 67, getMenu().getBaseInventory().getItem(2));
			}
			if(!getMenu().getBaseInventory().getItem(3).isEmpty()) {
				renderBaseGearSlot(leftPos + 25, topPos + 85, getMenu().getBaseInventory().getItem(3));
			}
			if(!getMenu().getBaseInventory().getItem(4).isEmpty()) {
				renderBaseGearSlot(leftPos + 44, topPos + 67, getMenu().getBaseInventory().getItem(4));
			}
			if(!getMenu().getBaseInventory().getItem(5).isEmpty()) {
				renderBaseGearSlot(leftPos + 44, topPos + 49, getMenu().getBaseInventory().getItem(5));
			}
			if(!getMenu().getBaseInventory().getItem(9).isEmpty()) {
				renderBaseGearSlot(leftPos + 44, topPos + 85, getMenu().getBaseInventory().getItem(9));
			}
		}

		this.renderTooltip(p_98418_, p_98419_, p_98420_);
	}

	@Override
	protected void init() {
		super.init();

		addButton(new Button(this.leftPos -57, this.topPos+6, 57, 20,  new TranslationTextComponent("gui.lotrextended.menu.back"), button -> openMainMenu()));
	}

	@Override
	protected void renderBg(MatrixStack p_98413_, float p_98414_, int p_98415_, int p_98416_) {
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		if(menu.profile.getAttackType()==ExtendedUnitOperationsType.RANGED) {
			this.minecraft.getTextureManager().bind(CONTAINER_BACKGROUND);
		}else {
			this.minecraft.getTextureManager().bind(CONTAINER_BACKGROUND_MELEE);
		}
		this.blit(p_98413_, leftPos, topPos+2, 0, 0, imageWidth, imageHeight);
	}

	@Override
	protected void renderLabels(MatrixStack matrix, int p_230451_2_, int p_230451_3_) {
		this.font.draw(matrix, title, (48 - (font.width(title) / 2)), 6, 4210752);
		this.font.draw(matrix, this.inventory.getDisplayName(), this.inventoryLabelX, this.inventoryLabelY, 4210752);

		// show armor points
		if (this.entity != null) {
			double armor = entity.getAttributeValue(Attributes.ARMOR);
			double armorT = entity.getAttributeValue(Attributes.ARMOR_TOUGHNESS);
			//double attackD = entity.getAttributeValue(Attributes.ATTACK_DAMAGE);
			//double attackS = entity.getAttributeValue(Attributes.ATTACK_SPEED);
			TextComponent armorTitle= new TranslationTextComponent("gui.lotrextended.hired_unit_tooltip.title.armor");
			this.font.draw(matrix, armorTitle.withStyle(TextFormatting.BLUE).withStyle(TextFormatting.ITALIC), 90, 50, 4210752);
			this.font.draw(matrix, new TranslationTextComponent("gui.lotrextended.hired_unit_tooltip.title.armor_points", df.format(armor)), 93, 60, 4210752);
			this.font.draw(matrix, new TranslationTextComponent("gui.lotrextended.hired_unit_tooltip.title.armor_toughness", df.format(armorT)), 92, 68, 4210752);
			//this.font.draw(matrix, new TranslationTextComponent("gui.lotrextended.hired_unit_tooltip.title.attack_damage", df.format(attackD)), 80, this.titleLabelY + 60, 4210752);
			//this.font.draw(matrix, new TranslationTextComponent("gui.lotrextended.hired_unit_tooltip.title.attack_speed", df.format(attackS)), 80, this.titleLabelY + 80, 4210752);
		}
	}

	@Override
	protected void renderTooltip(MatrixStack stack, int x, int y) {
		super.renderTooltip(stack, x, y);

		// render tooltips for base gear
		if (isHovering(25, 31, x, y) && !getMenu().getBaseInventory().getItem(0).isEmpty()) {
			this.renderTooltip(stack, getMenu().getBaseInventory().getItem(0), x, y);
		}
		if (isHovering(25, 49, x, y) && !getMenu().getBaseInventory().getItem(1).isEmpty()) {
			this.renderTooltip(stack, getMenu().getBaseInventory().getItem(1), x, y);
		}
		if (isHovering(25, 67, x, y) && !getMenu().getBaseInventory().getItem(2).isEmpty()) {
			this.renderTooltip(stack, getMenu().getBaseInventory().getItem(2), x, y);
		}
		if (isHovering(25, 85, x, y) && !getMenu().getBaseInventory().getItem(3).isEmpty()) {
			this.renderTooltip(stack, getMenu().getBaseInventory().getItem(3), x, y);
		}
		if (isHovering(44, 67, x, y) && !getMenu().getBaseInventory().getItem(4).isEmpty()) {
			this.renderTooltip(stack, getMenu().getBaseInventory().getItem(4), x, y);
		}
		if (getMenu().getRangedSlot()!=null && isHovering(44, 49, x, y) && !getMenu().getBaseInventory().getItem(5).isEmpty()) {
			this.renderTooltip(stack, getMenu().getBaseInventory().getItem(5), x, y);
		}
		if (isHovering(44, 85, x, y) && !getMenu().getBaseInventory().getItem(9).isEmpty()) {
			this.renderTooltip(stack, getMenu().getBaseInventory().getItem(9), x, y);
		}
	}

	private void openMainMenu() {
		ExtendedPacketHandler.sendToServer(new ExtendedCOpenHiredMenuPacket(menu.entityId));
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
