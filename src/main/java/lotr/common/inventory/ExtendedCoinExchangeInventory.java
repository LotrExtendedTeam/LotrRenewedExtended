package lotr.common.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IInventoryChangedListener;
import net.minecraft.inventory.Inventory;

/**
 * @author RichardB122
 */
public class ExtendedCoinExchangeInventory extends Inventory implements IInventoryChangedListener {

    private final ExtendedCoinExchangeContainer container;

    public ExtendedCoinExchangeInventory(ExtendedCoinExchangeContainer container, int i) {
        super(i);
        this.container = container;
        addListener(this);
    }

    @Override
    public void containerChanged(IInventory inventory) {
        container.slotsChanged(this);
    }

}
