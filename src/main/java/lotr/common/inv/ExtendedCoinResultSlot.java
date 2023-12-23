package lotr.common.inv;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

/**
 * @author RichardB122
 */
public class ExtendedCoinResultSlot extends Slot {

    private final ExtendedCoinExchangeContainer container;

    public ExtendedCoinResultSlot(ExtendedCoinExchangeContainer container, IInventory inventory, int i, int j, int k) {
        super(inventory, i, j, k);
        this.container = container;
    }

    public boolean mayPickup(PlayerEntity player) {
        return container.exchanged;
    }

    public boolean mayPlace(ItemStack stack) {
        return false;
    }

}
