package com.github.maximuslotro.lotrrextended.client.gui.screen.inventory;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.client.gui.widget.button.ExtendedImageAddOffsetButton;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedUnitOrders;
import com.github.maximuslotro.lotrrextended.common.inventory.container.ExtendedHiredUnitContainer;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedCOpenHiredEquipmentMenuPacket;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedCSetHiredUnitOrdersPacket;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedPacketHandler;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import lotr.common.entity.npc.ExtendedNPCEntity;
import lotr.common.entity.npc.data.ExtendedNpcHiredUnitInfoHolder;
import net.minecraft.client.gui.IHasContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public class ExtendedHiredUnitScreen extends ContainerScreen<ExtendedHiredUnitContainer> implements IHasContainer<ExtendedHiredUnitContainer> {

	private static final ResourceLocation CONTAINER_BACKGROUND = new ResourceLocation(LotrExtendedMod.MODID,"textures/gui/hired_unit_overview_menu.png");
	private static final ResourceLocation ORDER_BUTTONS = new ResourceLocation(LotrExtendedMod.MODID, "textures/gui/order_buttons.png");

	private DecimalFormat df = new DecimalFormat("#.#");
	int sidebarX;
	int row1;
	int col1;

	// buttons
	private ExtendedImageAddOffsetButton patrolButton;
	private ExtendedImageAddOffsetButton stationaryButton;
	private Button equipmentButton;

	// Current entity states
	private ExtendedUnitOrders orders;

	public ExtendedHiredUnitScreen(ExtendedHiredUnitContainer unitInventoryContainer, PlayerInventory pPlayerInventory, ITextComponent pTitle) {
		super(unitInventoryContainer, pPlayerInventory, pTitle);

		this.passEvents = false;
		this.imageHeight = 256;
		this.inventoryLabelY = 130;
		this.imageWidth = 226;
		df.setRoundingMode(RoundingMode.CEILING);
		sidebarX = 80;

		this.orders = unitInventoryContainer.initialOrders;
	}

	@Override
	public void render(MatrixStack p_98418_, int p_98419_, int p_98420_, float p_98421_) {
		this.renderBackground(p_98418_);
		super.render(p_98418_, p_98419_, p_98420_, p_98421_);

		this.renderTooltip(p_98418_, p_98419_, p_98420_);
	}

	@Override
	protected void init() {
		super.init();

		row1 = topPos + 42;
		col1 = leftPos + sidebarX + 62;

		// TODO: At some point add a guard mode
		this.patrolButton = addButton(new ExtendedImageAddOffsetButton(col1, row1, 16, 12, 0, 0, 13, ORDER_BUTTONS, 
				btn -> {
					if(orders != ExtendedUnitOrders.PATROL_PLAYER) {
						orders = ExtendedUnitOrders.PATROL_PLAYER;
						ExtendedPacketHandler.sendToServer(new ExtendedCSetHiredUnitOrdersPacket(menu.getCompanion().getId(), ExtendedUnitOrders.PATROL_PLAYER));
					}
				}) {
			@Override
			public boolean shouldShowEnabled() {
				return orders.equals(ExtendedUnitOrders.PATROL_PLAYER);
			}
		}

				);
		this.stationaryButton = addButton(new ExtendedImageAddOffsetButton(col1, row1 + 14, 16, 12, 34, 0, 13, ORDER_BUTTONS,
				btn -> {
					if(orders != ExtendedUnitOrders.GUARD_STATIONARY) {
						orders = ExtendedUnitOrders.GUARD_STATIONARY;
						ExtendedPacketHandler.sendToServer(new ExtendedCSetHiredUnitOrdersPacket(menu.getCompanion().getId(), ExtendedUnitOrders.GUARD_STATIONARY));
					}
				}) {
			@Override
			public boolean shouldShowEnabled() {
				return orders.equals(ExtendedUnitOrders.GUARD_STATIONARY);
			}
		}
				);

		equipmentButton = this.addButton(new Button(this.leftPos + 8, this.topPos + 18, 57, 20,  new TranslationTextComponent("gui.lotrextended.menu.equipment"), button -> openEquipmentMenu()));
	}

	private void openEquipmentMenu() {
		ExtendedPacketHandler.sendToServer(new ExtendedCOpenHiredEquipmentMenuPacket(menu.getCompanion().getId()));
	}

	@Override
	protected void renderBg(MatrixStack p_98413_, float p_98414_, int p_98415_, int p_98416_) {
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bind(CONTAINER_BACKGROUND);
		int i = (this.width - this.imageWidth) / 2;
		int j = ((this.height - this.imageHeight) / 2) + 2;
		this.blit(p_98413_, i, j, 0, 0, this.imageWidth, this.imageHeight);
	}

	@Override
	protected void renderLabels(MatrixStack matrix, int p_230451_2_, int p_230451_3_) {
		ExtendedNpcHiredUnitInfoHolder info =((ExtendedNPCEntity)menu.getCompanion()).getUnitInfo();
		this.font.draw(matrix, this.title, (float)this.titleLabelX, (float)this.titleLabelY, 4210752);
		this.font.draw(matrix, this.inventory.getDisplayName(), (float)this.inventoryLabelX, (float)this.inventoryLabelY, 4210752);

		this.font.draw(matrix, new TranslationTextComponent("gui.lotrextended.hired_unit_tooltip.title.companion").withStyle(TextFormatting.UNDERLINE), sidebarX, this.titleLabelY + 12, 4210752);

		this.font.draw(matrix, new TranslationTextComponent(menu.profile.getEntityUnitTypeName()), sidebarX, this.titleLabelY + 22, 4210752);

		this.font.draw(matrix, new TranslationTextComponent("gui.lotrextended.hired_unit_tooltip.title.health").withStyle(TextFormatting.UNDERLINE), sidebarX, this.titleLabelY + 32, 4210752);
		this.font.draw(matrix, new StringTextComponent(df.format(info.getHiredUnitHealth()) + "/" + info.getHiredUnitMaxHealth()), sidebarX, this.titleLabelY + 42, 4210752);
		this.font.draw(matrix, new TranslationTextComponent("gui.lotrextended.hired_unit_tooltip.title.level", info.getUnitLevel()), sidebarX, this.titleLabelY + 56, 4210752);
		this.font.draw(matrix, new TranslationTextComponent("gui.lotrextended.hired_unit_tooltip.title.xp", info.getCurrentXp() + "/" + info.getMaxXp()), sidebarX, this.titleLabelY + 64, 4210752);
		this.font.draw(matrix, new TranslationTextComponent("gui.lotrextended.hired_unit_tooltip.title.mob_kills", info.getMobKills()), sidebarX, this.titleLabelY + 73, 4210752);
	}

	@Override
	protected void renderTooltip(MatrixStack stack, int x, int y) {
		super.renderTooltip(stack, x, y);

		if (this.patrolButton.isHovered()) {
			List<ITextComponent> tooltips = new ArrayList<>();
			if (orders==ExtendedUnitOrders.PATROL_PLAYER) {
				tooltips.add(new TranslationTextComponent("gui.lotrextended.hired_unit_tooltip.follows_on_title"));
				tooltips.add(new TranslationTextComponent("gui.lotrextended.hired_unit_tooltip.follows_on_description").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));
				this.renderComponentTooltip(stack, tooltips, x, y);
			}else {
				tooltips.add(new TranslationTextComponent("gui.lotrextended.hired_unit_tooltip.follows_off_title"));
				tooltips.add(new TranslationTextComponent("gui.lotrextended.hired_unit_tooltip.follows_off_description").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));
				this.renderComponentTooltip(stack, tooltips, x, y);
			}
		}
		if (this.stationaryButton.isHovered()) {
			List<ITextComponent> tooltips = new ArrayList<>();
			if (orders==ExtendedUnitOrders.GUARD_STATIONARY) {
				tooltips.add(new TranslationTextComponent("gui.lotrextended.hired_unit_tooltip.stationary_on_title"));
				tooltips.add(new TranslationTextComponent("gui.lotrextended.hired_unit_tooltip.stationary_on_description").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));
			} else {
				tooltips.add(new TranslationTextComponent("gui.lotrextended.hired_unit_tooltip.stationary_off_title"));
				tooltips.add(new TranslationTextComponent("gui.lotrextended.hired_unit_tooltip.stationary_off_description").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));
			}
			this.renderComponentTooltip(stack, tooltips, x, y);
		}
	}
}