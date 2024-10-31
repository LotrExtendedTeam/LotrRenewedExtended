package lotr.common.init;

import com.github.maximuslotro.lotrrextended.common.inventory.ExtendedBookshelfContainer;
import com.github.maximuslotro.lotrrextended.common.inventory.container.ExtendedGrinderContainer;

import lotr.common.inventory.ExtendedCoinExchangeContainer;
import lotr.common.inventory.ExtendedTradeContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.fml.RegistryObject;

public class ExtendedContainers {
	public static RegistryObject<ContainerType<ExtendedCoinExchangeContainer>> COIN_TRADING;
	public static RegistryObject<ContainerType<ExtendedTradeContainer>> TRADING;
	public static RegistryObject<ContainerType<ExtendedBookshelfContainer>> BOOKSHELF;
	public static RegistryObject<ContainerType<ExtendedGrinderContainer>> MILLSTONE;
}
