package lotr.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import lotr.common.entity.npc.ExtendedTraderEntity;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.network.ExtendedPacketHandler;
import lotr.common.network.ExtendedSPacketOpenCoinExchange;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;

public class ExtendedTraderMainScreen extends BasicIngameScreen {

    private NPCEntity entity;
    private final ExtendedTraderEntity trader;
    private final PlayerEntity player;

    private int xSize = 176;

    private int ySize = 256;

    private int guiLeft;

    private int guiTop;

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
        this.addButton(new Button(this.guiLeft + 40, this.guiTop + 40, 120, 20, new StringTextComponent("Talk"), button -> System.out.println("Todo: make them talk")));
        this.addButton(new Button(this.guiLeft + 40, this.guiTop + 75, 120, 20, new StringTextComponent("Exchange Coins"), button -> trader.openCoinExchange(player)));
    }

    @Override
    public void render(MatrixStack matStack, int mouseX, int mouseY, float tick) {
        renderBackground(matStack);
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
