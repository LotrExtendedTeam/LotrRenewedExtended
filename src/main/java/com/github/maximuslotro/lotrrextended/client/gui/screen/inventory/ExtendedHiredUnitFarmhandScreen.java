package com.github.maximuslotro.lotrrextended.client.gui.screen.inventory;

import java.util.ArrayList;
import java.util.List;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.client.gui.widget.button.ExtendedTextureOffsetButton;
import com.github.maximuslotro.lotrrextended.common.inventory.container.ExtendedHiredUnitFarmhandContainer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ExtendedHiredUnitFarmhandScreen extends ExtendedHiredUnitScreen<ExtendedHiredUnitFarmhandContainer> {

	private static final ResourceLocation CONTAINER_BACKGROUND = new ResourceLocation(LotrExtendedMod.MODID,"textures/gui/hired_unit_farmhand_menu.png");

	public ExtendedHiredUnitFarmhandScreen(ExtendedHiredUnitFarmhandContainer unitEquipmentContainer, PlayerInventory pPlayerInventory, ITextComponent pTitle) {
		super(unitEquipmentContainer, pPlayerInventory, pTitle);
		setIndex(2);
	}

	@Override
	public void init() {
		super.init();
		// General stuff
		this.addButton(new ExtendedTextureOffsetButton(this.leftPos + 155, this.topPos + 37, 176, 54, 10, 10, new StringTextComponent("INFO"), button -> {}, CONTAINER_BACKGROUND){
			@Override
			public int hoverOffset() {
				return 10;
			}
			@Override
			public void renderToolTip(MatrixStack pPoseStack, int pMouseX, int pMouseY) {
				ExtendedHiredUnitFarmhandScreen.this.renderTooltip(pPoseStack, font.split(new TranslationTextComponent("gui.lotrextended.hired_unit_farmhand.info.tooltip"), 200), pMouseX, pMouseY);
			}
		});	
	}
	@Override
	public void render(MatrixStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTicks) {
		this.renderBackground(pMatrixStack);
		super.render(pMatrixStack, pMouseX, pMouseY, pPartialTicks);
		this.renderTooltip(pMatrixStack, pMouseX, pMouseY);
	}

	@Override
	protected void renderBg(MatrixStack pMatrixStack, float pPartialTicks, int pMouseX, int pMouseY) {
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bind(CONTAINER_BACKGROUND);
		this.blit(pMatrixStack, leftPos, topPos, 0, 0, imageWidth, imageHeight);

		if (getMenu().farmhandOutput.getContainerSize() < 1) {
			this.blit(pMatrixStack, leftPos + 16, topPos + 92, 176, 36, 72, 18);
		}
		if (getMenu().farmhandOutput.getContainerSize() < 2) { 
			this.blit(pMatrixStack, leftPos + 88, topPos + 92, 176, 36, 72, 18);
		}
		if (getMenu().farmhandOutput.getContainerSize() < 9) {
			this.blit(pMatrixStack, leftPos + 52, topPos + 110, 176, 36, 72, 18);
		}
		int[] xCoordinates = {
				70, 88,   // Center of top row (Slots 1, 2)
				52, 106,  // Mid-outer top row (Slots 3, 4)
				34, 124,  // Outer top row     (Slots 5, 6)
				16, 142,  // Far edges top row (Slots 7, 8)
				70, 88,   // Center bottom row (Slots 9, 10)
				52, 106   // Edges bottom row  (Slots 11, 12)
		};
		for (int i = 0; i < getMenu().farmhandOutput.getContainerSize(); ++i) {
			int xPos = xCoordinates[i];
			int yPos = (i < 8) ? 92 : 110;
			// Draw the 18x18 active slot border
			this.blit(pMatrixStack, leftPos + xPos, topPos + yPos, 176, 18, 18, 18);
		}
		int barLength = (int) (42 * getMenu().getFarmhandInfo().getCurrentFarmerXp()/getMenu().getFarmhandInfo().getMaxFarmerXp());
		fill(pMatrixStack, 67, 40, 67 + barLength, 43, 0x1CC226);
	}

	@Override
	protected void renderLabels(MatrixStack matrix, int pMouseX, int pMouseY) {
		this.font.draw(matrix, title, titleLabelX - (font.width(title)/2), titleLabelY, 4210752);
		this.font.draw(matrix, inventory.getDisplayName(), inventoryLabelX, inventoryLabelY, 4210752);
		TranslationTextComponent level = new TranslationTextComponent("gui.lotrextended.hired_unit_farmhand.level", getMenu().getFarmhandInfo().getFarmerLevel());
		this.font.draw(matrix, level, titleLabelX - (font.width(level)/2), 30, 4210752);
		TranslationTextComponent crops = new TranslationTextComponent("gui.lotrextended.hired_unit_farmhand.crops_farmed", getMenu().getFarmhandInfo().getCropsHarvested());
		this.font.draw(matrix, crops, 16, 83, 4210752);
		//drawScaled(matrix, crops, titleLabelX - (font.width(crops)/2) * 0.8F, 84, 4210752, 0.8F);
		ITextComponent currentXP = new StringTextComponent(Integer.toString(getMenu().getFarmhandInfo().getCurrentFarmerXp()));
		ITextComponent maxXP = new StringTextComponent(Integer.toString(getMenu().getFarmhandInfo().getMaxFarmerXp()));
		drawScaled(matrix, currentXP, 64 -font.width(currentXP) * 0.67F, 39, 4210752, 0.67F);
		drawScaled(matrix, maxXP, 112, 39, 4210752, 0.67F);
	}

	@Override
	protected void renderTooltip(MatrixStack stack, int pMouseX, int pMouseY) {
		super.renderTooltip(stack, pMouseX, pMouseY);
		if (this.isHovering(67, 40, 42, 3, pMouseX, pMouseY)) {
			List<ITextComponent> tooltips = new ArrayList<>();
			tooltips.add(new TranslationTextComponent("gui.lotrextended.hired_unit_farmhand.level.tooltip", getMenu().getFarmhandInfo().getCurrentFarmerXp(), getMenu().getFarmhandInfo().getMaxFarmerXp()));
			this.renderComponentTooltip(stack, tooltips, pMouseX, pMouseY);
		}
	}
}
