package lotr.common.inv;

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

    // TODO: Not sure if the above implementation is equivalent to this
    //  onInventoryChanged
    /*public void func_70296_d() {
        super.func_70296_d();
        CoinExchangeContainer.LOTRContainerCoinExchange.this.func_75130_a((IInventory)this);
    }*/


}
