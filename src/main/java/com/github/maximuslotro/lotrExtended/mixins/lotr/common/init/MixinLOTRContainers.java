package com.github.maximuslotro.lotrExtended.mixins.lotr.common.init;

import java.util.function.Supplier;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.maximuslotro.lotrExtended.common.datagen.DataGenValueHolder;

import lotr.common.init.ExtendedContainers;
import lotr.common.init.LOTRContainers;
import lotr.common.inv.ExtendedCoinExchangeContainer;
import lotr.common.inv.ExtendedTradeContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.DatagenModLoader;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

@Mixin(LOTRContainers.class)
public class MixinLOTRContainers {

	@Shadow(remap = false)
	@Final
	public static DeferredRegister<ContainerType<?>> CONTAINERS;
	
	@Inject(method = "register()V", at = @At("HEAD"), remap = false)
	private static void bootstrapExtendedContainers(CallbackInfo ci) {
		ExtendedContainers.COIN_TRADING = register("coin_exchange", "Coin Exchange", () -> {
			return IForgeContainerType.create(ExtendedCoinExchangeContainer::new);
		});
		ExtendedContainers.TRADING = CONTAINERS.register("trading", () -> {
			return IForgeContainerType.create(ExtendedTradeContainer::new);
		});
	}

	private static <T extends Container> RegistryObject<ContainerType<T>> register(String name, String defaultName, Supplier<ContainerType<T>> sup) {
		if(DatagenModLoader.isRunningDataGen()) {
    		DataGenValueHolder.containerTitlesHolders.put("container.lotr."+name, "Coin Exchange");
    	}
		return CONTAINERS.register(name, sup);
	}
}
