package lotr.client.gui.inv;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import lotr.client.gui.widget.button.ExtendedCoinExchangeButton;

import lotr.common.inv.ExtendedCoinExchangeContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;

/**
 * @author RichardB122
 */
public class ExtendedCoinExchangeScreen extends ContainerScreen<ExtendedCoinExchangeContainer> {

    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation("lotr:gui/coin_exchange.png");
    ExtendedCoinExchangeButton buttonLeft;
    ExtendedCoinExchangeButton buttonRight;

    public ExtendedCoinExchangeScreen(ExtendedCoinExchangeContainer p_i51105_1_, PlayerInventory p_i51105_2_) {
        super(p_i51105_1_, p_i51105_2_, new StringTextComponent("Coin Exchange"));
    }

    @Override
    protected void renderBg(MatrixStack matrix, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bind(GUI_TEXTURE);
        int i = (this.width - this.imageWidth) / 2;
        int j = ((this.height - this.imageHeight) / 2) + 2;
        this.blit(matrix, i, j, 0, 0, this.imageWidth, this.imageHeight);
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

        int i = (this.width - this.imageWidth) / 2;
        int j = 28;
        int k = 16;

        this.buttonLeft = addButton(new ExtendedCoinExchangeButton(48,  44,
                32, 16,
                176, 0,
                16,
                GUI_TEXTURE,
                btn -> {
                    attemptToExchange(0);
                })
        );
        this.buttonRight = addButton(new ExtendedCoinExchangeButton(102,  44,
                32, 16,
                208, 0,
                16,
                GUI_TEXTURE,
                btn -> {
                   attemptToExchange(1);
                })
        );

    }

    @Override
    protected void renderTooltip(MatrixStack stack, int x, int y) {
        super.renderTooltip(stack, x, y);

        // TODO: Add hovered for left and right buttons
    }

    private void attemptToExchange(int mode) {
        if (mode == 0) {
            // attempt to exchange left
            // TODO: Check coins and send packet
            System.out.println("Attempt to exchange left");
        } else {
            // attempt to exchange right
            // TODO: Check coins and send packet
            System.out.println("Attempt to exchange left");
        }
    }

}
