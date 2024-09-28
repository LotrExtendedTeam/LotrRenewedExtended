package com.github.maximuslotro.lotrrextended.mixins.lotr.client.gui.inv;

import com.mojang.blaze3d.matrix.MatrixStack;
import lotr.client.gui.inv.AlloyForgeScreen;
import lotr.client.gui.inventory.ExtendedCoinExchangeScreen;
import lotr.common.inv.AbstractAlloyForgeContainer;
import lotr.common.util.CoinUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AlloyForgeScreen.class)
public abstract class MixinAlloyForgeScreen extends ContainerScreen<AbstractAlloyForgeContainer> {

    public MixinAlloyForgeScreen(AbstractAlloyForgeContainer p_i51105_1_, PlayerInventory p_i51105_2_, ITextComponent p_i51105_3_) {
        super(p_i51105_1_, p_i51105_2_, p_i51105_3_);
    }

    @Inject(method = "renderLabels", at = @At("TAIL"))
    protected void renderLabels(MatrixStack matStack, int x, int y, CallbackInfo info) {
        Minecraft mc = Minecraft.getInstance();
        assert mc.player != null;

        int amount = CoinUtils.totalValueInPlayerInventory(mc.player.inventory);
        if (amount > 0) {
            String amountStr = String.valueOf(amount);
            fill(matStack, 180, 14 + 135, 180 + 19 + mc.font.width(amountStr), 29 + 135, 0x99000000);
            mc.font.draw(matStack, amountStr, 196, 18 + 135, 0xFFFFFF);
            mc.getTextureManager().bind(ExtendedCoinExchangeScreen.GUI_TEXTURE);
            blit(matStack, 179, 13 + 135, 176, 77, 18, 18);
        }
    }
}
