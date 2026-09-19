package com.github.maximuslotro.lotrrextended.client.gui.screen;

import com.github.maximuslotro.lotrrextended.common.network.ExtendedCOpenHiredOverviewMenuPacket;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedCTalkWithNPCPacket;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedCUnitDismissPacket;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedPacketHandler;
import com.mojang.blaze3d.matrix.MatrixStack;

import lotr.common.entity.npc.NPCEntity;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public class ExtendedUnitMainScreen extends ExtendedAbstractWindowedScreen {

	private NPCEntity entity;
	private final PlayerEntity player;

	private Button talkButton;
	private Button inventoryButton;
	private Button dismissButton;
	private Button transferButton;
	private Button dismissConfirmButton;
	private Button transferConfirmButton;

	private final boolean hasHireData;
	public ExtendedUnitMainScreen(NPCEntity entity, PlayerEntity player, boolean hasHireData) {
		super(new StringTextComponent("Unit Menu"), 176, 256);
		this.entity = entity;
		this.player = player;
		this.hasHireData = hasHireData;
	}

	@Override
	public void init() {
		super.init();
		talkButton = this.addButton(new Button(this.leftPos + (this.imageWidth - 120) / 2, this.topPos + 115, 120, 20, new TranslationTextComponent("gui.lotrextended.entity.talk_button"), button -> talkWithNPC()) {
			@Override
			public void renderToolTip(MatrixStack pPoseStack, int pMouseX, int pMouseY) {
				ExtendedUnitMainScreen.this.renderTooltip(pPoseStack, font.split(new TranslationTextComponent("gui.lotrextended.entity.talk_button.tooltip", entity.getName()), 200), pMouseX, pMouseY);
			}
		});
		inventoryButton = this.addButton(new Button(this.leftPos + (this.imageWidth - 120) / 2, this.topPos + 140, 120, 20, new TranslationTextComponent("gui.lotrextended.hired_unit.interface_button"), button -> openInterface()) {
			@Override
			public void renderToolTip(MatrixStack pPoseStack, int pMouseX, int pMouseY) {
				ExtendedUnitMainScreen.this.renderTooltip(pPoseStack, font.split(new TranslationTextComponent("gui.lotrextended.hired_unit.interface_button.tooltip"), 200), pMouseX, pMouseY);
			}
		});
		dismissButton = this.addButton(new Button(this.leftPos + (this.imageWidth - 120) / 2, this.topPos + 165, 57, 20, new TranslationTextComponent("gui.lotrextended.hired_unit.dismiss_button"), button -> dismissUnit()) {
			@Override
			public void renderToolTip(MatrixStack pPoseStack, int pMouseX, int pMouseY) {
				if(hasHireData) {
					ExtendedUnitMainScreen.this.renderTooltip(pPoseStack, font.split(new TranslationTextComponent("gui.lotrextended.hired_unit.dismiss_button.tooltip"), 200), pMouseX, pMouseY);
				}else {
					ExtendedUnitMainScreen.this.renderTooltip(pPoseStack, font.split(new TranslationTextComponent("gui.lotrextended.hired_unit.error.old_hiring_tooltip_1_11"), 200), pMouseX, pMouseY);
				}
			}
		});
		transferButton = this.addButton(new Button(this.leftPos + ((this.imageWidth - 120) / 2) + 63, this.topPos + 165, 57, 20, new TranslationTextComponent("gui.lotrextended.hired_unit.transfer_button"), button -> transferUnit()) {
			@Override
			public void renderToolTip(MatrixStack pPoseStack, int pMouseX, int pMouseY) {
				if(hasHireData) {
					ExtendedUnitMainScreen.this.renderTooltip(pPoseStack, font.split(new TranslationTextComponent("gui.lotrextended.hired_unit.transfer_button.tooltip"), 200), pMouseX, pMouseY);
				}else {
					ExtendedUnitMainScreen.this.renderTooltip(pPoseStack, font.split(new TranslationTextComponent("gui.lotrextended.hired_unit.error.old_hiring_tooltip_1_11"), 200), pMouseX, pMouseY);
				}
			}
		});
		dismissConfirmButton = this.addButton(new Button(this.leftPos + (this.imageWidth - 120) / 2, this.topPos + 190, 120, 20, new TranslationTextComponent("gui.lotrextended.hired_unit.dismiss_button_confirm").withStyle(TextFormatting.RED), button -> dismissUnitConfirmed()) {
			@Override
			public void renderToolTip(MatrixStack pPoseStack, int pMouseX, int pMouseY) {
				ExtendedUnitMainScreen.this.renderTooltip(pPoseStack, font.split(new TranslationTextComponent("gui.lotrextended.hired_unit.dismiss_button_confirm.tooltip").withStyle(TextFormatting.RED), 200), pMouseX, pMouseY);
			}
		});
		dismissConfirmButton.visible = false;
		transferConfirmButton = this.addButton(new Button(this.leftPos + (this.imageWidth - 120) / 2, this.topPos + 190, 120, 20, new TranslationTextComponent("gui.lotrextended.hired_unit.transfer_button_confirm").withStyle(TextFormatting.RED), button -> transferUnitConfirmed()) {
			@Override
			public void renderToolTip(MatrixStack pPoseStack, int pMouseX, int pMouseY) {
				ExtendedUnitMainScreen.this.renderTooltip(pPoseStack, font.split(new TranslationTextComponent("gui.lotrextended.hired_unit.transfer_button_confirm.tooltip").withStyle(TextFormatting.RED), 200), pMouseX, pMouseY);
			}
		});
		transferConfirmButton.visible = false;
		// disable bellow buttons if no hiring data present
		dismissButton.active = hasHireData;
		transferButton.active = hasHireData;
		dismissConfirmButton.active = hasHireData;
		transferConfirmButton.active = hasHireData;


	}

	@Override
	public void tick() {
		super.tick();
		if(dismissConfirmButton.visible && transferConfirmButton.visible) {
			//Disable both buttons if for some reason both are rendering
			dismissConfirmButton.visible = false;
			transferConfirmButton.visible = false;
		}
	}

	private void talkWithNPC() {
		ExtendedPacketHandler.sendToServer(new ExtendedCTalkWithNPCPacket(entity.getId()));
		this.onClose();
	}

	private void openInterface() {
		ExtendedPacketHandler.sendToServer(new ExtendedCOpenHiredOverviewMenuPacket(entity.getId()));
	}

	private void dismissUnit() {
		if (hasHireData) {
			dismissConfirmButton.visible=true;
			transferConfirmButton.visible=false;
		}
	}

	private void dismissUnitConfirmed() {
		if (hasHireData) {
			ExtendedPacketHandler.sendToServer(new ExtendedCUnitDismissPacket(entity.getId()));
			this.onClose();
		}
	}

	private void transferUnit() {
		if (hasHireData) {
			dismissConfirmButton.visible=false;
			transferConfirmButton.visible=true;
		}
	}

	private void transferUnitConfirmed() {
		if (hasHireData) {
			//ExtendedPacketHandler.sendToServer(new ExtendedCTalkWithNPCPacket(entity.getId()));
			this.onClose();
		}
	}

	@Override
	public void render(MatrixStack matStack, int mouseX, int mouseY, float tick) {
		renderBackground(matStack);
		TextComponent title = new StringTextComponent(entity.getName().getString());
		this.font.draw(matStack, title, this.leftPos + ((this.imageWidth - font.width(title)) / 2), this.topPos + 95, 16777215);
		super.render(matStack, mouseX, mouseY, tick);
	}
}
