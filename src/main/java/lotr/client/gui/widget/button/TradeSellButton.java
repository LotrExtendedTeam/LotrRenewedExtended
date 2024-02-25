package lotr.client.gui.widget.button;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import lotr.common.inv.ExtendedTradeContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.util.ResourceLocation;

public class TradeSellButton extends ImageButton {

    private final int xTexStart;
    private final int yTexStart;
    private final int textureWidth;
    private final int textureHeight;

    private static final ResourceLocation TEXTURE = new ResourceLocation("lotr:gui/trade.png");
    private ExtendedTradeContainer container;

    public TradeSellButton(int x, int y, ExtendedTradeContainer container, IPressable pressable) {
        super(x, y, 18, 18, 176, 18, 18, TEXTURE, 280, 280, pressable);
        this.container = container;
        xTexStart = 176;
        yTexStart = 0;
        textureWidth = 280;
        textureHeight = 280;
    }

    @Override
    public void renderButton(MatrixStack p_230431_1_, int p_230431_2_, int p_230431_3_, float p_230431_4_) {
        Minecraft lvt_5_1_ = Minecraft.getInstance();
        lvt_5_1_.getTextureManager().bind(TEXTURE);

        int yLocation = yTexStart;
        if (container.getSellValue() > 0) {
            yLocation += 18;
            if (this.isHovered()) {
                yLocation += 18;
            }
        }

        RenderSystem.enableDepthTest();
        blit(p_230431_1_, this.x, this.y, (float)this.xTexStart, (float) yLocation, this.width, this.height, this.textureWidth, this.textureHeight);
        if (this.isHovered()) {
            this.renderToolTip(p_230431_1_, p_230431_2_, p_230431_3_);
        }

    }
}
