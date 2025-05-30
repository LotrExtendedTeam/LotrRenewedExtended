package com.github.maximuslotro.lotrrextended.mixins.lotr.client.gui.inv;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.maximuslotro.lotrrextended.client.gui.screen.inventory.ExtendedCoinExchangeScreen;
import com.github.maximuslotro.lotrrextended.client.gui.screen.inventory.ExtendedHiredUnitEquipmentScreen;
import com.github.maximuslotro.lotrrextended.client.gui.screen.inventory.ExtendedHiredUnitScreen;
import com.github.maximuslotro.lotrrextended.client.gui.screen.inventory.ExtendedTradeScreen;

import lotr.client.gui.inv.ContainerScreenHelper;
import lotr.common.init.ExtendedContainers;
import net.minecraft.client.gui.ScreenManager;

/**
 * Need to register Extended's container screens
 * @Mixin (ContainerScreenHelper.class)
 * */
@Mixin(ContainerScreenHelper.class)
public class MixinContainerScreenHelper {

	/**
	 * @reason Registers Extended's Screens alongside Renewed's
	 * @Inject (method = "registerScreens()V", at = @At("HEAD"), remap=false)
	 * */
	@Inject(method = "registerScreens()V", at = @At("HEAD"), remap=false)
	private static void addAdditionalScreens(CallbackInfo ci) {
		ScreenManager.register(ExtendedContainers.COIN_TRADING.get(), ExtendedCoinExchangeScreen::new);
		ScreenManager.register(ExtendedContainers.TRADING.get(), ExtendedTradeScreen::new);
		ScreenManager.register(ExtendedContainers.HIRED_UNIT_EQUIPMENT.get(), ExtendedHiredUnitEquipmentScreen::new);
		ScreenManager.register(ExtendedContainers.HIRED_UNIT_CONTAINER.get(), ExtendedHiredUnitScreen::new);
	}
}
