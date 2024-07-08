package lotr.client.gui.inv;

import com.github.maximuslotro.lotrrextended.common.network.ExtendedPacketHandler;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedCPacketOpenCoinExchange;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedCPacketTradeSellItems;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import lotr.client.gui.widget.button.TradeSellButton;
import lotr.common.inv.ExtendedTradeContainer;

import lotr.common.util.CoinUtils;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

/**
 * @author RichardB122
 */
public class ExtendedTradeScreen extends ContainerScreen<ExtendedTradeContainer> {

    private int guiLeft;

    private int guiTop;

    private ExtendedTradeContainer container;
    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation("lotr:gui/trade.png");

    private PlayerEntity player;

    public ExtendedTradeScreen(ExtendedTradeContainer container, PlayerInventory playerInventory, ITextComponent title) {
        super(container, playerInventory, title);

        this.imageHeight = 270;
        this.imageWidth = 176;
        this.guiLeft = (this.width - this.imageWidth) / 2;
        this.guiTop = (this.height - this.imageHeight) / 2;
        this.container = container;
        this.player = playerInventory.player;
    }

    @Override
    public void render(MatrixStack matrix, int p_98419_, int p_98420_, float p_98421_) {
        this.renderBackground(matrix);
        super.render(matrix, p_98419_, p_98420_, p_98421_);
        this.renderTooltip(matrix, p_98419_, p_98420_);
    }

    @Override
    protected void renderBg(MatrixStack matrix, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bind(GUI_TEXTURE);
        int i = (this.width - this.imageWidth) / 2;
        int j = ((this.height - this.imageHeight) / 2);
        blit(matrix, i, j, 0, 0, this.imageWidth, this.imageHeight, 280, 280);
    }

    @Override
    protected void renderLabels(MatrixStack matrix, int p_230451_2_, int p_230451_3_) {
        // render coin amount in inventory
        fill(matrix, 180, 188, 180 + 19 + this.font.width(String.valueOf(CoinUtils.totalValueInPlayerInventory(player.inventory))), 203, 0x99000000);
        this.minecraft.getTextureManager().bind(GUI_TEXTURE);
        blit(matrix, 179, 187, 176, 54, 18, 18, 280, 280);
        this.font.draw(matrix, String.valueOf(CoinUtils.totalValueInPlayerInventory(player.inventory)), 196, 192, 0xFFFFFFFF);

        // render labels
        this.font.draw(matrix, container.trader.getTraderName(), (((imageWidth - 32) - font.width(container.trader.getTraderName())) / 2) + 16, 11, 4210752);
        this.font.draw(matrix, this.inventory.getDisplayName(), 8, this.inventoryLabelY, 4210752);
        this.font.draw(matrix, new TranslationTextComponent("gui.lotrextended.trading.buy"), 8, 30, 4210752);
        this.font.draw(matrix, new TranslationTextComponent("gui.lotrextended.trading.sell"), 8, 82, 4210752);
        this.font.draw(matrix, new TranslationTextComponent("gui.lotrextended.trading.sell_offers"), 8, 131, 4210752);
        if (container.getSellValue() > 0) {
            this.font.draw(matrix, "x " + container.getSellValue(), 99, 168, 4210752);
        }

        // render prices
        for (int i = 0; i < container.getSoldPrices().length; i++) {
            int amount = container.getSoldPrices()[i];
            // TODO: Maybe make trades eventually completely get used up like they do in Legacy? For now this isn't needed
            //  anymore
            /*if (container.getSoldQuantities()[i] == 0) {
                this.minecraft.getTextureManager().bind(GUI_TEXTURE);
                //blit(matrix, 8 + (i * 18), 57, 194, 18, 18, 18, 280, 280);
            }*/

            // TODO: Figure out how to render font at smaller size
            String priceStr = String.valueOf(container.getSoldPrices()[i]);
            if (container.getSoldQuantities()[i] == 0) priceStr = "...";
            this.font.draw(matrix, priceStr, 8 + (18 * i) + ((18 - this.font.width(priceStr)) / 2), 62, 4210752);
        }

        for (int i = 0; i < container.getBoughtPrices().length; i++) {
            // TODO: Maybe make trades eventually completely get used up like they do in Legacy? For now this isn't needed
            //  anymore
            /*if (container.getBoughtQuantities()[i] == 0) {
                this.minecraft.getTextureManager().bind(GUI_TEXTURE);
                //blit(matrix, 8 + (i * 18), 109, 194, 18, 18, 18, 280, 280);
            }*/

            // TODO: Figure out how to render font at smaller size
            String priceStr = String.valueOf(container.getBoughtPrices()[i]);
            if (container.getBoughtQuantities()[i] == 0) priceStr = "...";
            this.font.draw(matrix, priceStr, 8 + (18 * i) + ((18 - this.font.width(priceStr)) / 2), 114, 4210752);
        }

        // render a blocker in front of items sold to player
        for (int i = 0; i < container.getSoldQuantities().length; i++) {
            float amountUsed = (float) (container.getSoldOriginalQuantities()[i] - container.getSoldQuantities()[i]) / container.getSoldOriginalQuantities()[i];
            int pixels = (int) (amountUsed * 18);
            if (pixels > 18) pixels = 18;
            if (pixels > 0) {
                fill(matrix, 7 + (i * 18), 39, 7 + (i * 18) + pixels, 39 + 18, 0xE6373737);
            }
        }
        // render a blocker in front of items bought from the player
        this.minecraft.getTextureManager().bind(GUI_TEXTURE);
        for (int i = 0; i < container.getBoughtQuantities().length; i++) {
            float amountUsed = (float) (container.getBoughtOriginalQuantities()[i] - container.getBoughtQuantities()[i]) / container.getBoughtOriginalQuantities()[i];
            int pixels = (int) (amountUsed * 18);
            if (pixels > 18) pixels = 18;
            if (pixels > 0) {
                fill(matrix, 7 + (i * 18), 91, 7 + (i * 18) + pixels, 91 + 18, 0xE6373737);
            }
        }
    }

    @Override
    public void init() {
        super.init();
        this.imageHeight = 270;
        this.imageWidth = 176;

        this.inventoryLabelY = 178;

        this.guiLeft = (this.width - this.imageWidth) / 2;
        this.guiTop = (this.height - this.imageHeight) / 2;

        addButton(new TradeSellButton( guiLeft + 79,  guiTop + 164, container, btn -> sellItems()));
    }

    private void sellItems() {
        if (container.getSellValue() > 0) {
            container.trader.playTradeSound();
            ExtendedPacketHandler.sendToServer(new ExtendedCPacketTradeSellItems());
        }
    }

    @Override
    public void onClose() {
        super.onClose();
        // notify the server that player is no longer interacting with this trader
        ExtendedPacketHandler.sendToServer(new ExtendedCPacketOpenCoinExchange(container.traderNPC.getId(), false));
        container.trader.removePlayerFromTrading(player); // remove the player from trading on the client side as well
    }

}
