package com.github.maximuslotro.lotrrextended.mixins.lotr.client.gui.inv;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import lotr.client.gui.inv.ContainerScreenHelper;
import lotr.client.gui.inventory.ExtendedCoinExchangeScreen;
import lotr.client.gui.inventory.ExtendedTradeScreen;
import lotr.common.init.ExtendedContainers;
import net.minecraft.client.gui.ScreenManager;

import com.github.maximuslotro.lotrrextended.client.gui.screen.ExtendedHiredUnitScreen;
import com.github.maximuslotro.lotrrextended.client.gui.screen.ExtendedHiredUnitEquipmentScreen;

@Mixin(ContainerScreenHelper.class)
public class MixinContainerScreenHelper {

	@Inject(method = "registerScreens()V", at = @At("HEAD"), remap=false)
    private static void addAdditionalScreens(CallbackInfo ci) {
		ScreenManager.register(ExtendedContainers.COIN_TRADING.get(), ExtendedCoinExchangeScreen::new);
        ScreenManager.register(ExtendedContainers.TRADING.get(), ExtendedTradeScreen::new);
        ScreenManager.register(ExtendedContainers.HIRED_UNIT_EQUIPMENT.get(), ExtendedHiredUnitEquipmentScreen::new);
        ScreenManager.register(ExtendedContainers.HIRED_UNIT_CONTAINER.get(), ExtendedHiredUnitScreen::new);
    }
}
