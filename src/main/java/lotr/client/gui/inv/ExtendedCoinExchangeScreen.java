package lotr.client.gui.inv;

import com.github.maximuslotro.lotrrextended.common.network.ExtendedPacketHandler;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedSPacketExchangeCoins;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedSPacketOpenCoinExchange;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import lotr.common.inv.ExtendedCoinExchangeContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

/**
 * @author RichardB122
 */
public class ExtendedCoinExchangeScreen extends ContainerScreen<ExtendedCoinExchangeContainer> {

    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation("lotr:gui/coin_exchange.png");

    ExtendedCoinExchangeContainer container;

    PlayerEntity player;

    ImageButton buttonLeft;
    ImageButton buttonRight;

    public ExtendedCoinExchangeScreen(ExtendedCoinExchangeContainer container, PlayerInventory p_i51105_2_, ITextComponent title) {
        super(container, p_i51105_2_, title);

        this.container = container;
        this.player = p_i51105_2_.player;
        this.imageHeight = 188;
        this.imageWidth = 176;
    }

    @Override
    public void render(MatrixStack p_98418_, int p_98419_, int p_98420_, float p_98421_) {
        this.renderBackground(p_98418_);
        super.render(p_98418_, p_98419_, p_98420_, p_98421_);
        this.renderTooltip(p_98418_, p_98419_, p_98420_);
    }

    @Override
    protected void renderBg(MatrixStack matrix, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bind(GUI_TEXTURE);
        int i = (this.width - this.imageWidth) / 2;
        int j = ((this.height - this.imageHeight) / 2);
        this.blit(matrix, i, j, 0, 0, this.imageWidth, this.imageHeight);

        if (this.menu.exchanged && this.menu.sideExchanged == 0) {
            this.blit(matrix, i + 21, j + 41, 176, 51, 26, 26);
        }

        if (this.menu.exchanged && this.menu.sideExchanged == 1) {
            this.blit(matrix, i + 129, j + 41, 176, 51, 26, 26);
        }
    }

    @Override
    protected void renderLabels(MatrixStack matrix, int p_230451_2_, int p_230451_3_) {
        this.font.draw(matrix, this.title, (float) this.titleLabelX, (float) this.titleLabelY, 4210752);
        this.font.draw(matrix, this.inventory.getDisplayName(), (float) this.inventoryLabelX, (float) this.inventoryLabelY, 4210752);
    }

    @Override
    public void init() {
        super.init();
        this.imageHeight = 188;
        this.imageWidth = 176;

        this.titleLabelY = 7;
        this.inventoryLabelY = this.imageHeight - 95;

        int i = (this.width - this.imageWidth) / 2;
        int j = ((this.height - this.imageHeight) / 2);

        this.buttonLeft = addButton(new ImageButton(i + 44,  j + 45,
                32, 16,
                176, 0,
                16,
                GUI_TEXTURE,
                btn -> {
                    attemptToExchange(0);
                })
        );
        this.buttonRight = addButton(new ImageButton( i + 102,  j + 45,
                32, 16,
                208, 0,
                16,
                GUI_TEXTURE,
                btn -> {
                   attemptToExchange(1);
                })
        );
    }

    private void attemptToExchange(int mode) {
        this.menu.recalculateResultSlots(menu.inputSlot.getItem());

        if (mode == 0 && !this.menu.leftSlot.getItem().equals(ItemStack.EMPTY)) {
            // attempt to exchange left
            if (!this.menu.inputSlot.getItem().equals(ItemStack.EMPTY)) {
                this.menu.exchanged = true;
                this.menu.sideExchanged = mode;
                ExtendedPacketHandler.sendToServer(new ExtendedSPacketExchangeCoins(mode));
            }
        } else if(mode == 1 && !this.menu.rightSlot.getItem().equals(ItemStack.EMPTY)) {
            // attempt to exchange right
            if (!this.menu.inputSlot.getItem().equals(ItemStack.EMPTY)) {
                this.menu.exchanged = true;
                this.menu.sideExchanged = mode;
                ExtendedPacketHandler.sendToServer(new ExtendedSPacketExchangeCoins(mode));
            }
        }
    }

    @Override
    public void onClose() {
        super.onClose();
        // notify the server that player is no longer interacting with this trader
        ExtendedPacketHandler.sendToServer(new ExtendedSPacketOpenCoinExchange(container.traderNPC.getId(), false));
        container.trader.removePlayerFromTrading(player); // remove the player from trading on the client side as well
    }

}
