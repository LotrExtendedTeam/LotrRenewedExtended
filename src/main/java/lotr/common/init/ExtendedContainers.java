package lotr.common.init;

import com.github.maximuslotro.lotrrextended.common.inventory.container.ExtendedBookshelfContainer;
import com.github.maximuslotro.lotrrextended.common.inventory.container.ExtendedCoinExchangeContainer;
import com.github.maximuslotro.lotrrextended.common.inventory.container.ExtendedHiredUnitContainer;
import com.github.maximuslotro.lotrrextended.common.inventory.container.ExtendedHiredUnitEquipmentContainer;
import com.github.maximuslotro.lotrrextended.common.inventory.container.ExtendedStoneGrinderContainer;
import com.github.maximuslotro.lotrrextended.common.inventory.container.ExtendedTradeContainer;

import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.fml.RegistryObject;

public class ExtendedContainers {
	public static RegistryObject<ContainerType<ExtendedCoinExchangeContainer>> COIN_TRADING;
	public static RegistryObject<ContainerType<ExtendedTradeContainer>> TRADING;
	public static RegistryObject<ContainerType<ExtendedBookshelfContainer>> BOOKSHELF;
	public static RegistryObject<ContainerType<ExtendedStoneGrinderContainer>> STONE_GRINDER;
	public static RegistryObject<ContainerType<ExtendedHiredUnitEquipmentContainer>> HIRED_UNIT_EQUIPMENT;
	public static RegistryObject<ContainerType<ExtendedHiredUnitContainer>> HIRED_UNIT_CONTAINER;
}
