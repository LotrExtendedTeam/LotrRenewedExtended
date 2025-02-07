package lotr.common.inventory;

import com.github.maximuslotro.lotrrextended.common.network.ExtendedPacketHandler;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedSUpdateExchangeCoinContainerPacket;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

/**
 * @author RichardB122
 */
public class ExtendedCoinResultSlot extends Slot {

    private final ExtendedCoinExchangeContainer container;

    private final int slotNumber;

    public ExtendedCoinResultSlot(ExtendedCoinExchangeContainer container, IInventory inventory, int i, int j, int k) {
        super(inventory, i, j, k);
        slotNumber = i;
        this.container = container;
    }

    @Override
	public boolean mayPickup(PlayerEntity player) {
        return container.exchanged && slotNumber == container.sideExchanged;
    }

    @Override
    public ItemStack onTake(PlayerEntity player, ItemStack stack) {
        boolean exchanged = container.exchanged;

        if (exchanged) {
            container.exchanged = false;
            container.sideExchanged = -1;

            if (!player.level.isClientSide) {
                System.out.println("player.level.isClientSide: false - attempting to send packet");
                ExtendedPacketHandler.sendToClient(new ExtendedSUpdateExchangeCoinContainerPacket(false, container.sideExchanged), (ServerPlayerEntity) player);
            }
            container.recalculateResultSlots(container.inputSlot.getItem());
        }

        return stack;
    }

    @Override
	public boolean mayPlace(ItemStack stack) {
        return false;
    }

}