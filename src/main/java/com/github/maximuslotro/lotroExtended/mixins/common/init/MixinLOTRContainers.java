package com.github.maximuslotro.lotroExtended.mixins.common.init;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import lotr.common.init.ExtendedContainers;
import lotr.common.init.LOTRContainers;
import lotr.common.inv.ExtendedCoinExchangeContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.registries.DeferredRegister;

@Mixin(LOTRContainers.class)
public class MixinLOTRContainers {

	@Shadow(remap = false)
	@Final
	public static DeferredRegister<ContainerType<?>> CONTAINERS;
	
	@Inject(method = "register()V", at = @At("HEAD"), remap = false)
	private static void bootstrapExtendedContainers(CallbackInfo ci) {
		ExtendedContainers.COIN_TRADING = CONTAINERS.register("coin_trading", () -> {
			return IForgeContainerType.create(ExtendedCoinExchangeContainer::new);
		});
	}
}
