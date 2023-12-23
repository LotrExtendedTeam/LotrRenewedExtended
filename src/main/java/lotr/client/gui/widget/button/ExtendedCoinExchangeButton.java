package lotr.client.gui.widget.button;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.util.ResourceLocation;

/**
 * @author RichardB122
 */
public class ExtendedCoinExchangeButton extends ImageButton {

    public ExtendedCoinExchangeButton(int p_i51134_1_, int p_i51134_2_, int p_i51134_3_, int p_i51134_4_, int p_i51134_5_, int p_i51134_6_, int p_i51134_7_, ResourceLocation p_i51134_8_, IPressable p_i51134_9_) {
        super(p_i51134_1_, p_i51134_2_, p_i51134_3_, p_i51134_4_, p_i51134_5_, p_i51134_6_, p_i51134_7_, p_i51134_8_, p_i51134_9_);
    }

    // TODO: Do we need to override renderButton at all?
    /*
    @Override
    public void renderButton(MatrixStack p_94282_, int p_94283_, int p_94284_, float p_94285_) {
        if (this.name.equals("alert")) {
            if (CompanionScreen.this.companion.isAlert()) {
                this.xTexStart = 0;
            } else {
                this.xTexStart = 17;
            }
        } else if (this.name.equals("patrolling")) {
            if (CompanionScreen.this.companion.isFollowing()) {
                this.xTexStart = 0;
            } else if (CompanionScreen.this.companion.isPatrolling()){
                this.xTexStart = 17;
            } else {
                this.xTexStart = 34;
            }
        } else if (this.name.equals("stationery")) {
            if (CompanionScreen.this.companion.isStationary()) {
                this.xTexStart = 0;
            } else {
                this.xTexStart = 17;
            }
        }
        RenderSystem.enableBlend();
        super.renderButton(p_94282_, p_94283_, p_94284_, p_94285_);
        RenderSystem.disableBlend();
    }
}*/
}
