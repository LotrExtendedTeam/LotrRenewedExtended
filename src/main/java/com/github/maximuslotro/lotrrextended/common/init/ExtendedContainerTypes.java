package com.github.maximuslotro.lotrrextended.common.init;

import java.util.function.Supplier;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.common.datagen.DataGenValueHolder;
import com.github.maximuslotro.lotrrextended.common.inventory.container.ExtendedBookshelfContainer;
import com.github.maximuslotro.lotrrextended.common.inventory.container.ExtendedCoinExchangeContainer;
import com.github.maximuslotro.lotrrextended.common.inventory.container.ExtendedCrucibleContainer;
import com.github.maximuslotro.lotrrextended.common.inventory.container.ExtendedGrindstoneContainer;
import com.github.maximuslotro.lotrrextended.common.inventory.container.ExtendedHiredUnitOverviewContainer;
import com.github.maximuslotro.lotrrextended.common.inventory.container.ExtendedHiredUnitEquipmentContainer;
import com.github.maximuslotro.lotrrextended.common.inventory.container.ExtendedHiredUnitFarmhandContainer;
import com.github.maximuslotro.lotrrextended.common.inventory.container.ExtendedRepairContainerNew;
import com.github.maximuslotro.lotrrextended.common.inventory.container.ExtendedSmithingTableContainer;
import com.github.maximuslotro.lotrrextended.common.inventory.container.ExtendedStoneGrinderContainer;
import com.github.maximuslotro.lotrrextended.common.inventory.container.ExtendedTradeContainer;

import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DatagenModLoader;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ExtendedContainerTypes {
	public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, LotrExtendedMod.MODID);

	public static RegistryObject<ContainerType<ExtendedCoinExchangeContainer>> COIN_TRADING = register("coin_exchange", "Coin Exchange", () -> IForgeContainerType.create(ExtendedCoinExchangeContainer::new));
	public static RegistryObject<ContainerType<ExtendedTradeContainer>> TRADING = register("trading", "Trading", () -> IForgeContainerType.create(ExtendedTradeContainer::new));
	public static RegistryObject<ContainerType<ExtendedBookshelfContainer>> BOOKSHELF = register("bookshelf", "Bookshelf", () -> IForgeContainerType.create(ExtendedBookshelfContainer::threeRows));
	public static RegistryObject<ContainerType<ExtendedStoneGrinderContainer>> STONE_GRINDER = register("stone_grinder", "Stone Grinder", () -> IForgeContainerType.create(ExtendedStoneGrinderContainer::new));
	public static RegistryObject<ContainerType<ExtendedHiredUnitOverviewContainer>> HIRED_UNIT_OVERVIEW = register("hired_unit_overview", "Unit Overview", () -> IForgeContainerType.create(ExtendedHiredUnitOverviewContainer::new));
	public static RegistryObject<ContainerType<ExtendedHiredUnitEquipmentContainer>> HIRED_UNIT_EQUIPMENT = register("hired_unit_equipment", "Unit Equipment", () -> IForgeContainerType.create(ExtendedHiredUnitEquipmentContainer::new));
	public static RegistryObject<ContainerType<ExtendedRepairContainerNew>> EXTENDED_ANVIL = register("repair", null, () -> IForgeContainerType.create(ExtendedRepairContainerNew::new));
	public static RegistryObject<ContainerType<ExtendedSmithingTableContainer>> EXTENDED_SMITHING = register("smithing", null, () -> IForgeContainerType.create(ExtendedSmithingTableContainer::new));
	public static RegistryObject<ContainerType<ExtendedGrindstoneContainer>> EXTENDED_GRINDSTONE = register("grindstone", null, () -> IForgeContainerType.create(ExtendedGrindstoneContainer::new));
	public static RegistryObject<ContainerType<ExtendedCrucibleContainer>> CRUCIBLE = register("crucible", "Crucible", () -> IForgeContainerType.create(ExtendedCrucibleContainer::new));
	public static RegistryObject<ContainerType<ExtendedHiredUnitFarmhandContainer>> HIRED_UNIT_FARMHAND = register("hired_unit_farmhand", "Farmhand Supplies", () -> IForgeContainerType.create(ExtendedHiredUnitFarmhandContainer::new));

	private static <T extends Container> RegistryObject<ContainerType<T>> register(String name, String defaultName, Supplier<ContainerType<T>> sup) {
		if(DatagenModLoader.isRunningDataGen() && defaultName!=null) {
    		DataGenValueHolder.containerTitlesHolders.put("container."+LotrExtendedMod.MODID+"."+name, defaultName);
    	}
		return CONTAINERS.register(name, sup);
	}

	public static void register(IEventBus modBus) {
		CONTAINERS.register(modBus);
	}
}
