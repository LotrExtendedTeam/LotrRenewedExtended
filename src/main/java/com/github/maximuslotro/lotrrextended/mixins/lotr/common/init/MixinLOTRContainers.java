package com.github.maximuslotro.lotrrextended.mixins.lotr.common.init;

import java.util.function.Supplier;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.maximuslotro.lotrrextended.common.datagen.DataGenValueHolder;
import com.github.maximuslotro.lotrrextended.common.inventory.container.ExtendedBookshelfContainer;
import com.github.maximuslotro.lotrrextended.common.inventory.container.ExtendedCoinExchangeContainer;
import com.github.maximuslotro.lotrrextended.common.inventory.container.ExtendedHiredUnitContainer;
import com.github.maximuslotro.lotrrextended.common.inventory.container.ExtendedHiredUnitEquipmentContainer;
import com.github.maximuslotro.lotrrextended.common.inventory.container.ExtendedStoneGrinderContainer;
import com.github.maximuslotro.lotrrextended.common.inventory.container.ExtendedTradeContainer;

import lotr.common.init.ExtendedContainers;
import lotr.common.init.LOTRContainers;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.DatagenModLoader;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

/**
 * Used to register new containers added by Extended
 * @Mixin (LOTRContainers.class)
 * */
@Mixin(LOTRContainers.class)
public class MixinLOTRContainers {
	/**
	 * Get the block DeferredRegister for Renewed's containers
	 * @Shadow (remap=false)
	 * @Final Mark the field to be essentially final
	 * */
	@Shadow(remap = false)
	@Final
	public static DeferredRegister<ContainerType<?>> CONTAINERS;

	/**
	 * @reason Register Extended's containers after Renewed's static registering is done, but before the container differed register is registered
	 * @Inject (method = "register()V", at = @At("HEAD"), remap=false)
	 * @param ci The local CallbackInfo instance for this Inject
	 * */
	@Inject(method = "register()V", at = @At("HEAD"), remap = false)
	private static void bootstrapExtendedContainers(CallbackInfo ci) {
		ExtendedContainers.COIN_TRADING = register("coin_exchange", "Coin Exchange", () -> IForgeContainerType.create(ExtendedCoinExchangeContainer::new));
		ExtendedContainers.TRADING = register("trading", "Trading", () -> IForgeContainerType.create(ExtendedTradeContainer::new));
		ExtendedContainers.BOOKSHELF = registerExtended("bookshelf", "Bookshelf", () -> IForgeContainerType.create(ExtendedBookshelfContainer::threeRows));
		ExtendedContainers.STONE_GRINDER = registerExtended("stone_grinder", "Stone Grinder", () -> IForgeContainerType.create(ExtendedStoneGrinderContainer::new));
		ExtendedContainers.HIRED_UNIT_CONTAINER = register("hired_unit_menu", "Hired Unit Inventory", () -> IForgeContainerType.create(ExtendedHiredUnitContainer::new));
		ExtendedContainers.HIRED_UNIT_EQUIPMENT = register("hired_unit_equipment", "Update Equipment", () -> IForgeContainerType.create(ExtendedHiredUnitEquipmentContainer::new));
	}

	/**
	 * Register our own containers under the lotr namespace, with dataGen handling
	 * @param name A String name of the container
	 * @param defaultName A String English Localized name of the container for dataGen
	 * @param sup A ContainerType supplier to build
	 * @return A built RegistryObject for the container 
	 * */
	@Unique
	private static <T extends Container> RegistryObject<ContainerType<T>> register(String name, String defaultName, Supplier<ContainerType<T>> sup) {
		if(DatagenModLoader.isRunningDataGen()) {
    		DataGenValueHolder.containerTitlesHolders.put("container.lotr."+name, defaultName);
    	}
		return CONTAINERS.register(name, sup);
	}
	/**
	 * Register our own containers under the lotrextended namespace, with dataGen handling
	 * @param name A String name of the container
	 * @param defaultName A String English Localized name of the container for dataGen
	 * @param sup A ContainerType supplier to build
	 * @return A built RegistryObject for the container 
	 * */
	@Unique
	private static <T extends Container> RegistryObject<ContainerType<T>> registerExtended(String name, String defaultName, Supplier<ContainerType<T>> sup) {
		if(DatagenModLoader.isRunningDataGen()) {
    		DataGenValueHolder.containerTitlesHolders.put("container.lotrextended."+name, defaultName);
    	}
		return CONTAINERS.register(name, sup);
	}
}
