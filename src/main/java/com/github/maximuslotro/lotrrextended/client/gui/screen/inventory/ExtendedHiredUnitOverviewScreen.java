package com.github.maximuslotro.lotrrextended.client.gui.screen.inventory;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.client.gui.widget.ExtendedEmptyTextFieldWidget;
import com.github.maximuslotro.lotrrextended.client.gui.widget.ExtendedSlider;
import com.github.maximuslotro.lotrrextended.client.gui.widget.button.ExtendedAddOffsetImageButton;
import com.github.maximuslotro.lotrrextended.client.gui.widget.button.ExtendedTextureOffsetButton;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedUnitOrders;
import com.github.maximuslotro.lotrrextended.common.inventory.container.ExtendedHiredUnitOverviewContainer;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedCSetHiredUnitOrdersPacket;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedCSetHiredUnitRadiusPacket;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedCSetHiredUnitSquadPacket;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedPacketHandler;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import lotr.common.entity.npc.ExtendedINPCEntity;
import lotr.common.entity.npc.data.ExtendedNpcHiredUnitInfoHolder;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public class ExtendedHiredUnitOverviewScreen extends ExtendedHiredUnitContainerScreen<ExtendedHiredUnitOverviewContainer> {
	private static final ResourceLocation CONTAINER_BACKGROUND = new ResourceLocation(LotrExtendedMod.MODID,"textures/gui/container/unit_overview_menu.png");
	private static final ResourceLocation ORDER_BUTTONS = new ResourceLocation(LotrExtendedMod.MODID, "textures/gui/order_buttons.png");
	private DecimalFormat df = new DecimalFormat("#.#");

	// buttons
	private ExtendedAddOffsetImageButton stationaryButton;
	private ExtendedAddOffsetImageButton followsButton;
	private ExtendedAddOffsetImageButton farmingButton;
	private ExtendedAddOffsetImageButton guardButton;
	private ExtendedSlider rangeSlider;
	private ExtendedEmptyTextFieldWidget squadTextField;
	private boolean textFieldVisible=false;

	// Current entity states
	private ExtendedUnitOrders orders;
	private ExtendedUnitOrders lastOrders = null;
	private double radius;
	private String squadName;

	public ExtendedHiredUnitOverviewScreen(ExtendedHiredUnitOverviewContainer unitInventoryContainer, PlayerInventory pPlayerInventory, ITextComponent pTitle) {
		super(unitInventoryContainer, pPlayerInventory, pTitle);
		df.setRoundingMode(RoundingMode.CEILING);
		setIndex(0);
		this.orders = unitInventoryContainer.initialOrders;
		this.radius = ((ExtendedINPCEntity)menu.getCompanion()).getUnitInfo().getGuardRadius();
		this.squadName = ((ExtendedINPCEntity)menu.getCompanion()).getUnitInfo().getSquad();
	}

	@Override
	protected void init() {
		super.init();
		this.addButton(new ExtendedTextureOffsetButton(this.leftPos + 161, this.topPos + 5, 40, 0, 10, 10, new StringTextComponent("SquadsButton"), button -> {
			textFieldVisible = !textFieldVisible;
		}, EXTENDED_WIDGETS){
			@Override
			public int hoverOffset() {
				return 10;
			}
			@Override
			public void renderToolTip(MatrixStack pPoseStack, int pMouseX, int pMouseY) {
				ExtendedHiredUnitOverviewScreen.this.renderTooltip(pPoseStack, font.split(new TranslationTextComponent("gui.lotrextended.hired_unit.squad_button.tooltip"), 200), pMouseX, pMouseY);
			}
		});
		this.stationaryButton = addButton(new ExtendedAddOffsetImageButton(leftPos + 142, topPos + 42, 16, 12, 0, 0, 12, ORDER_BUTTONS,
				btn -> handleSetOrders(ExtendedUnitOrders.STATIONARY)) {
			@Override
			public boolean shouldShowEnabled() {
				return orders.equals(ExtendedUnitOrders.STATIONARY);
			}
		});
		this.followsButton = addButton(new ExtendedAddOffsetImageButton(leftPos + 142, topPos + 55, 16, 12, 16, 0, 12, ORDER_BUTTONS, 
				btn -> handleSetOrders(ExtendedUnitOrders.FOLLOW_PLAYER)) {
			@Override
			public boolean shouldShowEnabled() {
				return orders.equals(ExtendedUnitOrders.FOLLOW_PLAYER);
			}
		});
		this.guardButton = addButton(new ExtendedAddOffsetImageButton(leftPos + 142, topPos + 68, 16, 12, 32, 0, 12, ORDER_BUTTONS, 
				btn -> handleSetOrders(ExtendedUnitOrders.GUARD_AREA)) {
			@Override
			public boolean shouldShowEnabled() {
				return orders.equals(ExtendedUnitOrders.GUARD_AREA);
			}
		});
		this.farmingButton = addButton(new ExtendedAddOffsetImageButton(leftPos + 142, topPos + 81, 16, 12, 48, 0, 12, ORDER_BUTTONS, 
				btn -> handleSetOrders(ExtendedUnitOrders.FARMING)) {
			@Override
			public boolean shouldShowEnabled() {
				return orders.equals(ExtendedUnitOrders.FARMING);
			}
		});
		rangeSlider = addButton(new ExtendedSlider(leftPos+38, topPos + 85, 100, 20, "gui.lotrextended.hired_unit.range.guard", 1, 64, 1, 
				() -> radius, this::handleRadiusSlider).setToolTip((wdget, matrixStack, pMouseX, pMouseY)->{
					ExtendedHiredUnitOverviewScreen.this.renderTooltip(matrixStack, new TranslationTextComponent("gui.lotrextended.hired_unit.range.tooltip"), pMouseX, pMouseY);
				}));
		rangeSlider.active = orders.isHasRadius();
		farmingButton.active = getMenu().profile.getUnitType().isFarming();
		guardButton.active = !farmingButton.active;
		this.squadTextField = this.addButton(new ExtendedEmptyTextFieldWidget(this.font, this.leftPos + this.imageWidth, this.topPos + 0, 90, 20, new TranslationTextComponent("gui.lotrextended.horn_select.squad.textfield"), new TranslationTextComponent("gui.lotrextended.hiring.squad_assign")));
		this.squadTextField.setMaxLength(20);
		this.squadTextField.setValue(squadName);
		this.squadTextField.setResponder(this::handleSquadInput);
		squadTextField.visible=false;
	}

	@Override
	protected boolean hasClickedOutside(double pMouseX, double pMouseY, int pGuiLeft, int pGuiTop, int pMouseButton) {      
		if(this.squadTextField != null && this.squadTextField.visible && pMouseX >= (pGuiLeft + this.imageWidth) && pMouseX < (pGuiLeft + this.imageWidth + 90) && pMouseY >= pGuiTop && pMouseY < (pGuiTop + 20)) {
			return false;
		}
		return super.hasClickedOutside(pMouseX, pMouseY, pGuiLeft, pGuiTop, pMouseButton);
	}

	@Override
	public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
		if (this.squadTextField != null && this.squadTextField.isVisible() && this.squadTextField.isFocused()) {
			if (this.squadTextField.keyPressed(pKeyCode, pScanCode, pModifiers)) {
				return true;
			}
			InputMappings.Input mouseKey = InputMappings.getKey(pKeyCode, pScanCode);
			if (this.minecraft.options.keyInventory.isActiveAndMatches(mouseKey)) {
				return true;
			}
		}
		return super.keyPressed(pKeyCode, pScanCode, pModifiers);
	}

	@Override
	public void tick() {
		super.tick();
		squadTextField.tick();
	}

	@Override
	public void render(MatrixStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTicks) {
		this.renderBackground(pMatrixStack);
		rangeSlider.active = orders.isHasRadius();
		if(lastOrders!=orders) {
			if(orders==ExtendedUnitOrders.GUARD_AREA) {
				rangeSlider.setMessageKey("gui.lotrextended.hired_unit.range.guard");
				rangeSlider.updateMessage();
			}else if(orders==ExtendedUnitOrders.FARMING) {
				rangeSlider.setMessageKey("gui.lotrextended.hired_unit.range.farming");
				rangeSlider.updateMessage();
			}
			lastOrders= orders;
		}
		squadTextField.visible=textFieldVisible;
		super.render(pMatrixStack, pMouseX, pMouseY, pPartialTicks);
		this.renderTooltip(pMatrixStack, pMouseX, pMouseY);
	}

	public void handleSetOrders(ExtendedUnitOrders pOrders) {
		if(ExtendedNpcHiredUnitInfoHolder.canTypeAcceptOrders(getMenu().profile.getUnitType(), pOrders)) {
			orders = pOrders;
			ExtendedPacketHandler.sendToServer(new ExtendedCSetHiredUnitOrdersPacket(menu.getCompanion().getId(), orders));
		}
	}

	public void handleRadiusSlider(double pRadius) {
		this.radius = pRadius;
		ExtendedPacketHandler.sendToServer(new ExtendedCSetHiredUnitRadiusPacket(menu.getCompanion().getId(), (int) Math.floor(radius)));
	}

	public void handleSquadInput(String pSquad) {
		this.squadName = pSquad;
		ExtendedPacketHandler.sendToServer(new ExtendedCSetHiredUnitSquadPacket(menu.getCompanion().getId(), pSquad));
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

		this.font.draw(matrix, new TranslationTextComponent("gui.lotrextended.hired_unit.companion").withStyle(TextFormatting.UNDERLINE), 8, this.titleLabelY + 12, 4210752);

		this.font.draw(matrix, new TranslationTextComponent(menu.profile.getEntityUnitTypeName()), 8, this.titleLabelY + 22, 4210752);

		this.font.draw(matrix, new TranslationTextComponent("gui.lotrextended.hired_unit.health").withStyle(TextFormatting.UNDERLINE), 8, this.titleLabelY + 32, 4210752);
		this.font.draw(matrix, new StringTextComponent(df.format(info.getHiredUnitHealth()) + "/" + info.getHiredUnitMaxHealth()), 8, this.titleLabelY + 42, 4210752);
		// TODO move to same rendering system as farming menu
		this.font.draw(matrix, new TranslationTextComponent("gui.lotrextended.hired_unit.combat_level", info.getUnitLevel()), 8, this.titleLabelY + 56, 4210752);
		this.font.draw(matrix, new TranslationTextComponent("gui.lotrextended.hired_unit.xp", info.getCurrentXp() + "/" + info.getMaxXp()), 8, this.titleLabelY + 64, 4210752);
		this.font.draw(matrix, new TranslationTextComponent("gui.lotrextended.hired_unit.mob_kills", info.getMobKills()), 8, this.titleLabelY + 73, 4210752);
	}

	@Override
	protected void renderTooltip(MatrixStack stack, int pMouseX, int pMouseY) {
		super.renderTooltip(stack, pMouseX, pMouseY);

		renderTooltipForUnitButtons(stack, pMouseX, pMouseY, stationaryButton, ExtendedUnitOrders.STATIONARY, "stationary");
		renderTooltipForUnitButtons(stack, pMouseX, pMouseY, followsButton, ExtendedUnitOrders.FOLLOW_PLAYER, "follows");
		renderTooltipForUnitButtons(stack, pMouseX, pMouseY, farmingButton, ExtendedUnitOrders.FARMING, "farming");
		renderTooltipForUnitButtons(stack, pMouseX, pMouseY, guardButton, ExtendedUnitOrders.GUARD_AREA, "guard");
	}

	public void renderTooltipForUnitButtons(MatrixStack stack, int pMouseX, int pMouseY, ExtendedAddOffsetImageButton button, ExtendedUnitOrders orderType, String designator) {
		if (button.isHovered()) {
			List<ITextComponent> tooltips = new ArrayList<>();
			String status = orders==orderType? "gui.lotrextended.base.on" : "gui.lotrextended.base.off";
			String message = "gui.lotrextended.hired_unit.order."+ designator + (orders==orderType ? ".on.tooltip" : ".off.tooltip");
			if(!button.active) {
				status = "gui.lotrextended.base.disabled";
				message = "gui.lotrextended.hired_unit.order.disabled.tooltip";
			}
			tooltips.add(new TranslationTextComponent("gui.lotrextended.hired_unit.order."+designator+".title.tooltip", new TranslationTextComponent(status)));
			tooltips.add(new TranslationTextComponent(message).withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));
			this.renderComponentTooltip(stack, tooltips, pMouseX, pMouseY);
		}
	}
}