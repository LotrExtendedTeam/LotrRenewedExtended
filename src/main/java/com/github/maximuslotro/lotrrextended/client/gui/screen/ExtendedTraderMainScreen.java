package com.github.maximuslotro.lotrrextended.client.gui.screen;

import com.github.maximuslotro.lotrrextended.common.network.ExtendedCStopInteractingWithInteractablePacket;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedCTalkWithTraderPacket;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedPacketHandler;
import com.mojang.blaze3d.matrix.MatrixStack;

import lotr.common.entity.npc.ExtendedTraderEntity;
import lotr.common.entity.npc.NPCEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ExtendedTraderMainScreen extends ExtendedBasicAbstractScreen {

    private NPCEntity entity;
    private final ExtendedTraderEntity trader;
    private final PlayerEntity player;

    private Button coinButton;
    private Button tradeButton;
    
    private boolean canTraderTrade = false;
    public ExtendedTraderMainScreen(NPCEntity entity, ExtendedTraderEntity trader, PlayerEntity player) {
        super(new StringTextComponent("Trader Menu"), 176, 256);

        this.entity = entity;
        this.trader = trader;
        this.player = player;
    }

    @Override
    public void init() {
    	super.init();
        this.addButton(new Button(this.leftPos + (this.imageWidth - 120) / 2, this.topPos + 115, 57, 20, new TranslationTextComponent("gui.lotrextended.trading.talk_button"), button -> talkWithNPC()) {
        	@Override
			public void renderToolTip(MatrixStack pPoseStack, int pMouseX, int pMouseY) {
        		ExtendedTraderMainScreen.this.renderTooltip(pPoseStack, font.split(new TranslationTextComponent("gui.lotrextended.trading.talk_button.tooltip", entity.getName()), 200), pMouseX, pMouseY);
			}
        });
        tradeButton = this.addButton(new Button(this.leftPos + ((this.imageWidth - 120) / 2) + 63, this.topPos + 115, 57, 20, new TranslationTextComponent("gui.lotrextended.trading.trade_button"), button -> tradeWithNPC()) {
        	@Override
			public void renderToolTip(MatrixStack pPoseStack, int pMouseX, int pMouseY) {
        		if(canTraderTrade) {
            		ExtendedTraderMainScreen.this.renderTooltip(pPoseStack, font.split(new TranslationTextComponent("gui.lotrextended.trading.trade_button.tooltip"), 200), pMouseX, pMouseY);
        		}else {
        			if (trader.isFriendly(player)) {
                		ExtendedTraderMainScreen.this.renderTooltip(pPoseStack, font.split(new TranslationTextComponent("gui.lotrextended.trading.trader_alignment_too_low"), 200), pMouseX, pMouseY);
        			}else {
                		ExtendedTraderMainScreen.this.renderTooltip(pPoseStack, font.split(new TranslationTextComponent("gui.lotrextended.trading.trader_unfrendly"), 200), pMouseX, pMouseY);
        			}
        		}
			}
        });
        coinButton = this.addButton(new Button(this.leftPos + (this.imageWidth - 120) / 2, this.topPos + 140, 120, 20, new TranslationTextComponent("gui.lotrextended.trading.exchange_button"), button -> tradeCoinsWithNPC()) {
        	@Override
			public void renderToolTip(MatrixStack pPoseStack, int pMouseX, int pMouseY) {
        		if(canTraderTrade) {
            		ExtendedTraderMainScreen.this.renderTooltip(pPoseStack, font.split(new TranslationTextComponent("gui.lotrextended.trading.exchange_button.tooltip"), 200), pMouseX, pMouseY);
        		}else {
        			if (trader.isFriendly(player)) {
                		ExtendedTraderMainScreen.this.renderTooltip(pPoseStack, font.split(new TranslationTextComponent("gui.lotrextended.trading.trader_alignment_too_low"), 200), pMouseX, pMouseY);
        			}else {
                		ExtendedTraderMainScreen.this.renderTooltip(pPoseStack, font.split(new TranslationTextComponent("gui.lotrextended.trading.trader_unfrendly"), 200), pMouseX, pMouseY);
        			}
        		}
			}
        });
    }

	@Override
	public void tick() {
		super.tick();
		canTraderTrade = trader.canTradeWithPlayer(player);
		coinButton.active = canTraderTrade;
		tradeButton.active = canTraderTrade;
	}
		
    private void talkWithNPC() {
    	stopTradingClientServer();
        ExtendedPacketHandler.sendToServer(new ExtendedCTalkWithTraderPacket(entity.getId()));
        closeThisMenu();
    }

	private void tradeWithNPC() {
		// Ensure the client does not disturb the server when traders can't be traded with
		if (canTraderTrade) {
			trader.openTradeMenu(player);
		} else {
			sendClientErrorMessage();
		}
	}

	private void tradeCoinsWithNPC() {
		// Ensure the client does not disturb the server when traders can't be traded with
		if (canTraderTrade) {
			trader.openCoinExchange(player);
		} else {
			sendClientErrorMessage();
		}
	}

	private void sendClientErrorMessage() {
		if (trader.isFriendly(player)) {
			player.sendMessage(new TranslationTextComponent("gui.lotrextended.trading.trader_alignment_too_low"), Util.NIL_UUID);
			stopTradingClientServer();
			closeThisMenu();
		}else {
			player.sendMessage(new TranslationTextComponent("gui.lotrextended.trading.trader_unfrendly"), Util.NIL_UUID);
			stopTradingClientServer();
			closeThisMenu();
		}
	}

    @Override
    public void render(MatrixStack matStack, int mouseX, int mouseY, float tick) {
        renderBackground(matStack);

        TextComponent title = new StringTextComponent(entity.getName().getString());
        this.font.draw(matStack, title, this.leftPos + ((this.imageWidth - font.width(title)) / 2), this.topPos + 95, 16777215);

        super.render(matStack, mouseX, mouseY, tick);
    }

    @Override
    public void onClose() {
        super.onClose();
        stopTradingClientServer();
    }

    private void closeThisMenu() {
    	Minecraft mc = Minecraft.getInstance();
		if(mc.player != null) {
			mc.player.closeContainer();
		}
    }

    public void stopTradingClientServer() {
    	// notify the server that player is no longer interacting with this trader
        ExtendedPacketHandler.sendToServer(new ExtendedCStopInteractingWithInteractablePacket(entity.getId()));
        trader.removePlayerFromInteracting(player); // remove the player from trading on the client side as well
    }
}
