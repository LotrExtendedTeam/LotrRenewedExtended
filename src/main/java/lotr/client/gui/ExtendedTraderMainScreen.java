package lotr.client.gui;

import com.github.maximuslotro.lotrExtended.common.network.ExtendedPacketHandler;
import com.github.maximuslotro.lotrExtended.common.network.ExtendedSPacketOpenCoinExchange;
import com.github.maximuslotro.lotrExtended.common.network.ExtendedSPacketTalkWithTrader;
import com.mojang.blaze3d.matrix.MatrixStack;
import lotr.common.entity.npc.ExtendedTraderEntity;
import lotr.common.entity.npc.NPCEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;

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
        this.addButton(new Button(this.guiLeft + (this.xSize - 120) / 2, this.guiTop + 115, 57, 20, new StringTextComponent("Talk"),
                button -> talkWithNPC()));
        tradeButton = this.addButton(new Button(this.guiLeft + ((this.xSize - 120) / 2) + 63, this.guiTop + 115, 57, 20, new StringTextComponent("Trade"), button -> System.out.println("")));
        this.addButton(new Button(this.guiLeft + (this.xSize - 120) / 2, this.guiTop + 140, 120, 20, new StringTextComponent("Exchange Coins"), button -> trader.openCoinExchange(player)));
    }

    private void talkWithNPC() {
        ExtendedPacketHandler.sendToServer(new ExtendedSPacketOpenCoinExchange(entity.getId(), false));
        ExtendedPacketHandler.sendToServer(new ExtendedSPacketTalkWithTrader(entity.getId()));
        Minecraft mc = Minecraft.getInstance();
        if(mc.player != null) {
        	mc.player.closeContainer();
        }
    }

    @Override
    public void render(MatrixStack matStack, int mouseX, int mouseY, float tick) {
        renderBackground(matStack);
        renderButtonTooltipIfHovered(matStack, tradeButton, new StringTextComponent("This feature has not been implemented yet"), mouseX, mouseY);

        TextComponent title = new StringTextComponent(entity.getName().getString());
        this.font.draw(matStack, title, guiLeft + ((this.xSize - font.width(title)) / 2), guiTop + 95, 16777215);

        super.render(matStack, mouseX, mouseY, tick);
    }

    @Override
    public void onClose() {
        super.onClose();
        // notify the server that player is no longer interacting with this trader
        ExtendedPacketHandler.sendToServer(new ExtendedSPacketOpenCoinExchange(entity.getId(), false));
        trader.removePlayerFromTrading(player); // remove the player from trading on the client side as well
    }

}
