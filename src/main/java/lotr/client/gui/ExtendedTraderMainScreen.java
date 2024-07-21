package lotr.client.gui;

import com.github.maximuslotro.lotrrextended.common.network.ExtendedPacketHandler;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedCPacketStopTradingWithTrader;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedCPacketTalkWithTrader;
import com.mojang.blaze3d.matrix.MatrixStack;
import lotr.common.entity.npc.ExtendedTraderEntity;
import lotr.common.entity.npc.NPCEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ExtendedTraderMainScreen extends BasicIngameScreen {

    private NPCEntity entity;
    private final ExtendedTraderEntity trader;
    private final PlayerEntity player;

    private int xSize = 176;

    private int ySize = 256;

    private int guiLeft;

    private int guiTop;

    private Button tradeButton;

    public ExtendedTraderMainScreen(NPCEntity entity, ExtendedTraderEntity trader, PlayerEntity player) {
        super(new StringTextComponent("Trader Menu"));

        this.entity = entity;
        this.trader = trader;
        this.player = player;
    }

    @Override
    public void init() {
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
        this.addButton(new Button(this.guiLeft + (this.xSize - 120) / 2, this.guiTop + 115, 57, 20, new TranslationTextComponent("gui.lotrextended.trading.talk_button"), button -> talkWithNPC()));
        tradeButton = this.addButton(new Button(this.guiLeft + ((this.xSize - 120) / 2) + 63, this.guiTop + 115, 57, 20, new TranslationTextComponent("gui.lotrextended.trading.trade_button"), button -> tradeWithNPC()));
        this.addButton(new Button(this.guiLeft + (this.xSize - 120) / 2, this.guiTop + 140, 120, 20, new TranslationTextComponent("gui.lotrextended.trading.exchange_button"), button -> tradeCoinsWithNPC()));
    }

    private void talkWithNPC() {
    	// notify the server that player is no longer interacting with this trader
        ExtendedPacketHandler.sendToServer(new ExtendedCPacketStopTradingWithTrader(entity.getId()));
        trader.removePlayerFromTrading(player); // remove the player from trading on the client side as well

        ExtendedPacketHandler.sendToServer(new ExtendedCPacketTalkWithTrader(entity.getId()));
        Minecraft mc = Minecraft.getInstance();
        if(mc.player != null) {
            mc.player.closeContainer();
        }
    }

    private void tradeWithNPC() {
    	// Ensure the client does not disturb the server when traders can't be traded with
    	if (trader.canTradeWith(player)) {
    		trader.openTradeMenu(player);
        }
    }

    private void tradeCoinsWithNPC() {
    	// Ensure the client does not disturb the server when traders can't be traded with
    	if (trader.canTradeWith(player)) {
    		trader.openCoinExchange(player);
        }
    }

    @Override
    public void render(MatrixStack matStack, int mouseX, int mouseY, float tick) {
        renderBackground(matStack);

        TextComponent title = new StringTextComponent(entity.getName().getString());
        this.font.draw(matStack, title, guiLeft + ((this.xSize - font.width(title)) / 2), guiTop + 95, 16777215);

        super.render(matStack, mouseX, mouseY, tick);
    }

    @Override
    public void onClose() {
        super.onClose();
        // notify the server that player is no longer interacting with this trader
        ExtendedPacketHandler.sendToServer(new ExtendedCPacketStopTradingWithTrader(entity.getId()));
        trader.removePlayerFromTrading(player); // remove the player from trading on the client side as well
    }
}
