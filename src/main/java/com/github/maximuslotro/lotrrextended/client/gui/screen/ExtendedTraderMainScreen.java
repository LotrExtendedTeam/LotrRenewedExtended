package com.github.maximuslotro.lotrrextended.client.gui.screen;

import com.github.maximuslotro.lotrrextended.common.network.ExtendedCTalkWithNPCPacket;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedPacketHandler;
import com.mojang.blaze3d.matrix.MatrixStack;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.ExtendedICaptainEntity;
import lotr.common.entity.npc.ExtendedIMerchantEntity;
import lotr.common.entity.npc.ExtendedITraderEntity;
import lotr.common.entity.npc.NPCEntity;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ExtendedTraderMainScreen extends ExtendedAbstractWindowedScreen {
	private NPCEntity entity;
	private final PlayerEntity player;

	private Button coinButton;
	private Button tradeButton;
	private Button hireButton;
	private Button talkButton;

	private boolean canTraderExchange = false;
	private boolean canTraderTrade = false;
	private boolean canTraderHire = false;
	public ExtendedTraderMainScreen(NPCEntity entity, PlayerEntity player) {
		super(new StringTextComponent(entity.getName().getString()), 176, 256);
		if(!(entity instanceof ExtendedITraderEntity)) {
			throw new IllegalArgumentException("Recieved trader NPC that does not impliment ExtendedTraderEntity");
		}
		this.entity = entity;
		this.player = player;
	}

	@Override
	public void init() {
		super.init();
		// Talk button
		talkButton = this.addButton(new Button(this.leftPos + (this.imageWidth - 120) / 2, this.topPos + 115, 120, 20, new TranslationTextComponent("gui.lotrextended.entity.talk_button"), button -> talkWithNPC()) {
			@Override
			public void renderToolTip(MatrixStack pPoseStack, int pMouseX, int pMouseY) {
				ExtendedTraderMainScreen.this.renderTooltip(pPoseStack, font.split(new TranslationTextComponent("gui.lotrextended.entity.talk_button.tooltip", entity.getName()), 200), pMouseX, pMouseY);
			}
		});
		// Trade Button
		if(entity instanceof ExtendedIMerchantEntity) {
			talkButton.setWidth(57);
			tradeButton = this.addButton(new Button(this.leftPos + ((this.imageWidth - 120) / 2) + 63, this.topPos + 115, 57, 20, new TranslationTextComponent("gui.lotrextended.trading.trade_button"), button -> tradeWithNPC()) {
				@Override
				public void renderToolTip(MatrixStack pPoseStack, int pMouseX, int pMouseY) {
					if(canTraderTrade) {
						ExtendedTraderMainScreen.this.renderTooltip(pPoseStack, font.split(new TranslationTextComponent("gui.lotrextended.trading.trade_button.tooltip"), 200), pMouseX, pMouseY);
					}else {
						if (!LOTRMod.PROXY.getSidedAttackTarget(entity).isPresent()) {
							ExtendedTraderMainScreen.this.renderTooltip(pPoseStack, font.split(new TranslationTextComponent("gui.lotrextended.trading.trader_alignment_too_low"), 200), pMouseX, pMouseY);
						}else {
							ExtendedTraderMainScreen.this.renderTooltip(pPoseStack, font.split(new TranslationTextComponent("gui.lotrextended.trading.trader_unfrendly"), 200), pMouseX, pMouseY);
						}
					}
				}
			});
		}
		// Coin Exchange Button
		coinButton = this.addButton(new Button(this.leftPos + (this.imageWidth - 120) / 2, this.topPos + 140, 120, 20, new TranslationTextComponent("gui.lotrextended.trading.exchange_button"), button -> tradeCoinsWithNPC()) {
			@Override
			public void renderToolTip(MatrixStack pPoseStack, int pMouseX, int pMouseY) {
				if(canTraderExchange) {
					ExtendedTraderMainScreen.this.renderTooltip(pPoseStack, font.split(new TranslationTextComponent("gui.lotrextended.trading.exchange_button.tooltip"), 200), pMouseX, pMouseY);
				}else {
					if (!LOTRMod.PROXY.getSidedAttackTarget(entity).isPresent()) {
						ExtendedTraderMainScreen.this.renderTooltip(pPoseStack, font.split(new TranslationTextComponent("gui.lotrextended.trading.trader_alignment_too_low"), 200), pMouseX, pMouseY);
					}else {
						ExtendedTraderMainScreen.this.renderTooltip(pPoseStack, font.split(new TranslationTextComponent("gui.lotrextended.trading.trader_unfrendly"), 200), pMouseX, pMouseY);
					}
				}
			}
		});
		if(entity instanceof ExtendedICaptainEntity) {
			hireButton = this.addButton(new Button(this.leftPos + (this.imageWidth - 120) / 2, this.topPos + 165, 120, 20, new TranslationTextComponent("gui.lotrextended.hiring.hire_button"), button -> hireFromNPC()) {
				@Override
				public void renderToolTip(MatrixStack pPoseStack, int pMouseX, int pMouseY) {
					if(canTraderHire) {
						ExtendedTraderMainScreen.this.renderTooltip(pPoseStack, font.split(new TranslationTextComponent("gui.lotrextended.hiring.hire_button.tooltip"), 200), pMouseX, pMouseY);
					}else {
						if (!LOTRMod.PROXY.getSidedAttackTarget(entity).isPresent()) {
							ExtendedTraderMainScreen.this.renderTooltip(pPoseStack, font.split(new TranslationTextComponent("gui.lotrextended.hiring.captain_alignment_too_low"), 200), pMouseX, pMouseY);
						}else {
							ExtendedTraderMainScreen.this.renderTooltip(pPoseStack, font.split(new TranslationTextComponent("gui.lotrextended.hiring.captain_unfriendly"), 200), pMouseX, pMouseY);
						}
					}
				}
			});
		}
	}

	@Override
	public void tick() {
		super.tick();
		canTraderExchange = ((ExtendedITraderEntity)entity).canExchangeCoinsWithPlayer(player);
		coinButton.active = canTraderExchange;
		if (entity instanceof ExtendedIMerchantEntity) {
			canTraderTrade = ((ExtendedIMerchantEntity)entity).canTradeWithPlayer(player) 
				&& (!((ExtendedIMerchantEntity)entity).isInteractingWithPlayer() 
					|| ((ExtendedIMerchantEntity)entity).isInteractingWithPlayer() && ((ExtendedIMerchantEntity)entity).getPlayerInteractingWith().get()==player.getUUID());
			tradeButton.active = canTraderTrade;
		}
		if (entity instanceof ExtendedICaptainEntity) {
			canTraderHire = ((ExtendedICaptainEntity)entity).canHireToPlayer(player);
			hireButton.active = canTraderHire;
		}
	}

	private void talkWithNPC() {
		ExtendedPacketHandler.sendToServer(new ExtendedCTalkWithNPCPacket(entity.getId()));
		this.onClose();
	}

	private void tradeWithNPC() {
		// Ensure the client does not disturb the server when traders can't be traded with
		if (canTraderTrade) {
			((ExtendedIMerchantEntity)entity).openTradeMenu(player);
		} else {
			sendClientErrorMessage();
		}
	}

	private void tradeCoinsWithNPC() {
		// Ensure the client does not disturb the server when traders can't be traded with
		if (canTraderExchange) {
			((ExtendedITraderEntity)entity).openCoinExchange(player);
		} else {
			sendClientErrorMessage();
		}
	}

	private void hireFromNPC() {
		// Ensure the client does not disturb the server when traders can't be traded with
		if (canTraderHire) {
			((ExtendedICaptainEntity)entity).openHireUnitMenu(player);
		} else {
			sendClientErrorMessage();
		}
	}

	private void sendClientErrorMessage() {
		if (!LOTRMod.PROXY.getSidedAttackTarget(entity).isPresent()) {
			player.sendMessage(new TranslationTextComponent("gui.lotrextended.trading.trader_alignment_too_low"), Util.NIL_UUID);
		}else {
			player.sendMessage(new TranslationTextComponent("gui.lotrextended.trading.trader_unfrendly"), Util.NIL_UUID);
		}
		this.onClose();
	}

	@Override
	public void render(MatrixStack matStack, int mouseX, int mouseY, float tick) {
		renderBackground(matStack);
		this.font.draw(matStack, title, this.leftPos + ((this.imageWidth - font.width(title)) / 2), this.topPos + 95, 16777215);
		super.render(matStack, mouseX, mouseY, tick);
	}
}
