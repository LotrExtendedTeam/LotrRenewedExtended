package com.github.maximuslotro.lotrrextended.client.gui.screen.inventory;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.client.gui.widget.button.ExtendedImageAddOffsetButton;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedUnitOrders;
import com.github.maximuslotro.lotrrextended.common.inventory.container.ExtendedHiredUnitOverviewContainer;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedCSetHiredUnitOrdersPacket;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedPacketHandler;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import lotr.common.entity.npc.ExtendedINPCEntity;
import lotr.common.entity.npc.data.ExtendedNpcHiredUnitInfoHolder;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public class ExtendedHiredUnitOverviewScreen extends ExtendedHiredUnitScreen<ExtendedHiredUnitOverviewContainer> {

	private static final ResourceLocation CONTAINER_BACKGROUND = new ResourceLocation(LotrExtendedMod.MODID,"textures/gui/hired_unit_overview_menu.png");
	private static final ResourceLocation ORDER_BUTTONS = new ResourceLocation(LotrExtendedMod.MODID, "textures/gui/order_buttons.png");

	private DecimalFormat df = new DecimalFormat("#.#");
	int sidebarX;
	int row1;
	int col1;

	// buttons
	private ExtendedImageAddOffsetButton patrolButton;
	private ExtendedImageAddOffsetButton stationaryButton;

	// Current entity states
	private ExtendedUnitOrders orders;

	public ExtendedHiredUnitOverviewScreen(ExtendedHiredUnitOverviewContainer unitInventoryContainer, PlayerInventory pPlayerInventory, ITextComponent pTitle) {
		super(unitInventoryContainer, pPlayerInventory, pTitle);
		df.setRoundingMode(RoundingMode.CEILING);
		sidebarX = 80;
		this.orders = unitInventoryContainer.initialOrders;
		setIndex(0);
	}

	@Override
	public void render(MatrixStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTicks) {
		this.renderBackground(pMatrixStack);
		super.render(pMatrixStack, pMouseX, pMouseY, pPartialTicks);
		this.renderTooltip(pMatrixStack, pMouseX, pMouseY);
	}

	@Override
	protected void init() {
		super.init();

		row1 = topPos + 42;
		col1 = leftPos + sidebarX + 62;

		// TODO: At some point add a guard mode
		this.patrolButton = addButton(new ExtendedImageAddOffsetButton(col1, row1, 16, 12, 0, 0, 13, ORDER_BUTTONS, 
			btn -> {
				if(orders != ExtendedUnitOrders.FOLLOW_PLAYER) {
					orders = ExtendedUnitOrders.FOLLOW_PLAYER;
					ExtendedPacketHandler.sendToServer(new ExtendedCSetHiredUnitOrdersPacket(menu.getCompanion().getId(), ExtendedUnitOrders.FOLLOW_PLAYER));
				}
			}) {
			@Override
			public boolean shouldShowEnabled() {
				return orders.equals(ExtendedUnitOrders.FOLLOW_PLAYER);
			}
		}

			);
		this.stationaryButton = addButton(new ExtendedImageAddOffsetButton(col1, row1 + 14, 16, 12, 34, 0, 13, ORDER_BUTTONS,
			btn -> {
				if(orders != ExtendedUnitOrders.STATIONARY) {
					orders = ExtendedUnitOrders.STATIONARY;
					ExtendedPacketHandler.sendToServer(new ExtendedCSetHiredUnitOrdersPacket(menu.getCompanion().getId(), ExtendedUnitOrders.STATIONARY));
				}
			}) {
			@Override
			public boolean shouldShowEnabled() {
				return orders.equals(ExtendedUnitOrders.STATIONARY);
			}
		}
			);
	}

	@Override
	protected void renderBg(MatrixStack pMatrixStack, float pPartialTicks, int pMouseX, int pMouseY) {
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bind(CONTAINER_BACKGROUND);
		this.blit(pMatrixStack, leftPos, topPos, 0, 0, this.imageWidth, this.imageHeight);
	}

	@Override
	protected void renderLabels(MatrixStack matrix, int pMouseX, int pMouseY) {
		ExtendedNpcHiredUnitInfoHolder info =((ExtendedINPCEntity)menu.getCompanion()).getUnitInfo();
		this.font.draw(matrix, title, titleLabelX - (font.width(title)/2), titleLabelY, 4210752);
		this.font.draw(matrix, inventory.getDisplayName(), inventoryLabelX, inventoryLabelY, 4210752);

		this.font.draw(matrix, new TranslationTextComponent("gui.lotrextended.hired_unit_tooltip.title.companion").withStyle(TextFormatting.UNDERLINE), sidebarX, this.titleLabelY + 12, 4210752);

		this.font.draw(matrix, new TranslationTextComponent(menu.profile.getEntityUnitTypeName()), sidebarX, this.titleLabelY + 22, 4210752);

		this.font.draw(matrix, new TranslationTextComponent("gui.lotrextended.hired_unit_tooltip.title.health").withStyle(TextFormatting.UNDERLINE), sidebarX, this.titleLabelY + 32, 4210752);
		this.font.draw(matrix, new StringTextComponent(df.format(info.getHiredUnitHealth()) + "/" + info.getHiredUnitMaxHealth()), sidebarX, this.titleLabelY + 42, 4210752);
		this.font.draw(matrix, new TranslationTextComponent("gui.lotrextended.hired_unit_tooltip.title.level", info.getUnitLevel()), sidebarX, this.titleLabelY + 56, 4210752);
		this.font.draw(matrix, new TranslationTextComponent("gui.lotrextended.hired_unit_tooltip.title.xp", info.getCurrentXp() + "/" + info.getMaxXp()), sidebarX, this.titleLabelY + 64, 4210752);
		this.font.draw(matrix, new TranslationTextComponent("gui.lotrextended.hired_unit_tooltip.title.mob_kills", info.getMobKills()), sidebarX, this.titleLabelY + 73, 4210752);
	}

	@Override
	protected void renderTooltip(MatrixStack stack, int pMouseX, int pMouseY) {
		super.renderTooltip(stack, pMouseX, pMouseY);

		if (this.patrolButton.isHovered()) {
			List<ITextComponent> tooltips = new ArrayList<>();
			if (orders==ExtendedUnitOrders.FOLLOW_PLAYER) {
				tooltips.add(new TranslationTextComponent("gui.lotrextended.hired_unit_tooltip.follows_on_title"));
				tooltips.add(new TranslationTextComponent("gui.lotrextended.hired_unit_tooltip.follows_on_description").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));
				this.renderComponentTooltip(stack, tooltips, pMouseX, pMouseY);
			}else {
				tooltips.add(new TranslationTextComponent("gui.lotrextended.hired_unit_tooltip.follows_off_title"));
				tooltips.add(new TranslationTextComponent("gui.lotrextended.hired_unit_tooltip.follows_off_description").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));
				this.renderComponentTooltip(stack, tooltips, pMouseX, pMouseY);
			}
		}
		if (this.stationaryButton.isHovered()) {
			List<ITextComponent> tooltips = new ArrayList<>();
			if (orders==ExtendedUnitOrders.STATIONARY) {
				tooltips.add(new TranslationTextComponent("gui.lotrextended.hired_unit_tooltip.stationary_on_title"));
				tooltips.add(new TranslationTextComponent("gui.lotrextended.hired_unit_tooltip.stationary_on_description").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));
			} else {
				tooltips.add(new TranslationTextComponent("gui.lotrextended.hired_unit_tooltip.stationary_off_title"));
				tooltips.add(new TranslationTextComponent("gui.lotrextended.hired_unit_tooltip.stationary_off_description").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));
			}
			this.renderComponentTooltip(stack, tooltips, pMouseX, pMouseY);
		}
	}
}