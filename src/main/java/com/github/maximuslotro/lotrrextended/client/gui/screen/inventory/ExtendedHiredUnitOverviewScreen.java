package com.github.maximuslotro.lotrrextended.client.gui.screen.inventory;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.client.gui.widget.button.ExtendedAddOffsetImageButton;
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
	private static final ResourceLocation CONTAINER_BACKGROUND = new ResourceLocation(LotrExtendedMod.MODID,"textures/gui/container/unit_overview_menu.png");
	private static final ResourceLocation ORDER_BUTTONS = new ResourceLocation(LotrExtendedMod.MODID, "textures/gui/order_buttons.png");
	private DecimalFormat df = new DecimalFormat("#.#");

	// buttons
	private ExtendedAddOffsetImageButton stationaryButton;
	private ExtendedAddOffsetImageButton followsButton;
	private ExtendedAddOffsetImageButton farmingButton;

	// Current entity states
	private ExtendedUnitOrders orders;

	public ExtendedHiredUnitOverviewScreen(ExtendedHiredUnitOverviewContainer unitInventoryContainer, PlayerInventory pPlayerInventory, ITextComponent pTitle) {
		super(unitInventoryContainer, pPlayerInventory, pTitle);
		df.setRoundingMode(RoundingMode.CEILING);
		setIndex(0);
		this.orders = unitInventoryContainer.initialOrders;
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

		this.stationaryButton = addButton(new ExtendedAddOffsetImageButton(leftPos + 142, topPos + 42, 16, 12, 0, 0, 12, ORDER_BUTTONS,
			btn -> {
				if(ExtendedNpcHiredUnitInfoHolder.canTypeAcceptOrders(getMenu().profile.getUnitType(), ExtendedUnitOrders.STATIONARY)) {
					orders = ExtendedUnitOrders.STATIONARY;
					ExtendedPacketHandler.sendToServer(new ExtendedCSetHiredUnitOrdersPacket(menu.getCompanion().getId(), ExtendedUnitOrders.STATIONARY));
				}
			}) {
			@Override
			public boolean shouldShowEnabled() {
				return orders.equals(ExtendedUnitOrders.STATIONARY);
			}
		});
		this.followsButton = addButton(new ExtendedAddOffsetImageButton(leftPos + 142, topPos + 55, 16, 12, 16, 0, 12, ORDER_BUTTONS, 
			btn -> {
				if(ExtendedNpcHiredUnitInfoHolder.canTypeAcceptOrders(getMenu().profile.getUnitType(), ExtendedUnitOrders.FOLLOW_PLAYER)) {
					orders = ExtendedUnitOrders.FOLLOW_PLAYER;
					ExtendedPacketHandler.sendToServer(new ExtendedCSetHiredUnitOrdersPacket(menu.getCompanion().getId(), ExtendedUnitOrders.FOLLOW_PLAYER));
				}
			}) {
			@Override
			public boolean shouldShowEnabled() {
				return orders.equals(ExtendedUnitOrders.FOLLOW_PLAYER);
			}
		});
		this.farmingButton = addButton(new ExtendedAddOffsetImageButton(leftPos + 142, topPos + 68, 16, 12, 48, 0, 12, ORDER_BUTTONS, 
			btn -> {
				if(ExtendedNpcHiredUnitInfoHolder.canTypeAcceptOrders(getMenu().profile.getUnitType(), ExtendedUnitOrders.FARMING)) {
					orders = ExtendedUnitOrders.FARMING;
					ExtendedPacketHandler.sendToServer(new ExtendedCSetHiredUnitOrdersPacket(menu.getCompanion().getId(), ExtendedUnitOrders.FARMING));
				}
			}) {
			@Override
			public boolean shouldShowEnabled() {
				return orders.equals(ExtendedUnitOrders.FARMING);
			}
		});
		farmingButton.active=getMenu().profile.getUnitType().isFarming();
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

		this.font.draw(matrix, new TranslationTextComponent("gui.lotrextended.hired_unit_tooltip.title.companion").withStyle(TextFormatting.UNDERLINE), 8, this.titleLabelY + 12, 4210752);

		this.font.draw(matrix, new TranslationTextComponent(menu.profile.getEntityUnitTypeName()), 8, this.titleLabelY + 22, 4210752);

		this.font.draw(matrix, new TranslationTextComponent("gui.lotrextended.hired_unit_tooltip.title.health").withStyle(TextFormatting.UNDERLINE), 8, this.titleLabelY + 32, 4210752);
		this.font.draw(matrix, new StringTextComponent(df.format(info.getHiredUnitHealth()) + "/" + info.getHiredUnitMaxHealth()), 8, this.titleLabelY + 42, 4210752);
		this.font.draw(matrix, new TranslationTextComponent("gui.lotrextended.hired_unit_tooltip.title.level", info.getUnitLevel()), 8, this.titleLabelY + 56, 4210752);
		this.font.draw(matrix, new TranslationTextComponent("gui.lotrextended.hired_unit_tooltip.title.xp", info.getCurrentXp() + "/" + info.getMaxXp()), 8, this.titleLabelY + 64, 4210752);
		this.font.draw(matrix, new TranslationTextComponent("gui.lotrextended.hired_unit_tooltip.title.mob_kills", info.getMobKills()), 8, this.titleLabelY + 73, 4210752);
	}

	@Override
	protected void renderTooltip(MatrixStack stack, int pMouseX, int pMouseY) {
		super.renderTooltip(stack, pMouseX, pMouseY);

		renderTooltipForUnitButtons(stack, pMouseX, pMouseY, stationaryButton, ExtendedUnitOrders.STATIONARY, "stationary");
		renderTooltipForUnitButtons(stack, pMouseX, pMouseY, followsButton, ExtendedUnitOrders.FOLLOW_PLAYER, "follows");
		renderTooltipForUnitButtons(stack, pMouseX, pMouseY, farmingButton, ExtendedUnitOrders.FARMING, "farming");
	}

	public void renderTooltipForUnitButtons(MatrixStack stack, int pMouseX, int pMouseY, ExtendedAddOffsetImageButton button, ExtendedUnitOrders orderType, String designator) {
		if (button.isHovered()) {
			List<ITextComponent> tooltips = new ArrayList<>();
			String status = orders==orderType? "gui.lotrextended.base.on" : "gui.lotrextended.base.off";
			String message = "gui.lotrextended.hired_unit_tooltip."+ designator + (orders==orderType ? "_on_description" : "_off_description");
			if(!button.active) {
				status = "gui.lotrextended.base.disabled";
				message = "gui.lotrextended.hired_unit_tooltip.description_disabled";
			}
			tooltips.add(new TranslationTextComponent("gui.lotrextended.hired_unit_tooltip."+designator+"_title", new TranslationTextComponent(status)));
			tooltips.add(new TranslationTextComponent(message).withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));
			this.renderComponentTooltip(stack, tooltips, pMouseX, pMouseY);
		}
	}
}