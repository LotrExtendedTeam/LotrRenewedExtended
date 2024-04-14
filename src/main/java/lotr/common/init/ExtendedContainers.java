package lotr.common.init;

import lotr.common.inv.ExtendedBookshelfContainer;
import lotr.common.inv.ExtendedCoinExchangeContainer;
import lotr.common.inv.ExtendedTradeContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.fml.RegistryObject;

public class ExtendedContainers {
	public static RegistryObject<ContainerType<ExtendedCoinExchangeContainer>> COIN_TRADING;
	public static RegistryObject<ContainerType<ExtendedTradeContainer>> TRADING;
	public static RegistryObject<ContainerType<ExtendedBookshelfContainer>> BOOKSHELF;
}
