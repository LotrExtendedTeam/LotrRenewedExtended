package lotr.common.inv;

import lotr.common.entity.npc.NPCEntity;
import lotr.common.init.ExtendedContainers;
import lotr.common.init.ExtendedItems;
import lotr.common.network.ExtendedCPacketUpdateExchangeCoinContainer;
import lotr.common.network.ExtendedPacketHandler;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.IContainerListener;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import org.lwjgl.system.CallbackI;

/**
 * @author RichardB122
 */
public class ExtendedCoinExchangeContainer extends Container implements IContainerListener {
    public static final ITextComponent CONTAINER_TITLE = new TranslationTextComponent("gui.lotr.container.coinexchange");

    private World level;

    public NPCEntity traderNPC;

    public boolean exchanged = false;
    public int sideExchanged;

    private PlayerEntity player;

    private IInventory coinInputInv = new ExtendedCoinExchangeInventory(this, 1);

    private IInventory exchangeInv = new ExtendedCoinExchangeInventory(this, 2);

    public Slot inputSlot;
    public Slot leftSlot;
    public Slot rightSlot;
    private ItemStack lastInputSlot = null;

    public ExtendedCoinExchangeContainer(int containerId, PlayerInventory inventory, PacketBuffer extraData) {
        super(ExtendedContainers.COIN_TRADING.get(), containerId);

        this.player = inventory.player;
        this.level = inventory.player.level;
        this.traderNPC = (NPCEntity) this.level.getEntity(extraData.readVarInt());

        inputSlot = addSlot(new Slot(this.coinInputInv, 0, 80, 35) {
            public boolean mayPlace(ItemStack stack) {
                return super.mayPlace(stack) && isValidCoin(stack);
            }

            public boolean mayPickup(PlayerEntity p_82869_1_) {
                return !exchanged;
            }

            @Override
            public ItemStack onTake(PlayerEntity player, ItemStack stack) {
                recalculateResultSlots(stack);
                return stack;
            }
        });

        // the slotChanged method is used to help recalculate the preview for the coin exchanges
        addSlotListener(this);

        leftSlot = addSlot(new ExtendedCoinResultSlot(this, exchangeInv, 0, 26, 35));
        rightSlot = addSlot(new ExtendedCoinResultSlot(this, exchangeInv, 1, 134, 35));

        int i;
        for (i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++)
                addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 95 + i * 18));
        }
        for (i = 0; i < 9; i++) {
            addSlot(new Slot(inventory, i, 8 + i * 18, 153));
        }
    }

    public void recalculateResultSlots(ItemStack itemStack) {
        System.out.println("RecalculateResultSlots - itemStack: " + itemStack);
        if (inputSlot.getItem().getItem() == ExtendedItems.SILVER_COIN_ONE.get()) {
            leftSlot.set(ItemStack.EMPTY);
            exchangeInv.setItem(0, ItemStack.EMPTY);
            leftSlot.setChanged();

            if (inputSlot.getItem().getCount() >= 10) {
                int tenCoins = inputSlot.getItem().getCount() / 10;
                rightSlot.set(new ItemStack(ExtendedItems.SILVER_COIN_TEN.get(), tenCoins));
                rightSlot.setChanged();
                exchangeInv.setItem(1, new ItemStack(ExtendedItems.SILVER_COIN_TEN.get(), tenCoins));
            }
        } else if (inputSlot.getItem().getItem() == ExtendedItems.SILVER_COIN_TEN.get()) {
            int oneCoins = inputSlot.getItem().getCount() * 10;
            if (oneCoins > 64) oneCoins = 60;
            leftSlot.set(new ItemStack(ExtendedItems.SILVER_COIN_ONE.get(), oneCoins));
            leftSlot.setChanged();
            exchangeInv.setItem(0, new ItemStack(ExtendedItems.SILVER_COIN_ONE.get(), oneCoins));

            if (inputSlot.getItem().getCount() >= 10) {
                int hundredCoins = inputSlot.getItem().getCount() / 10;
                rightSlot.set(new ItemStack(ExtendedItems.SILVER_COIN_HUNDRED.get(), hundredCoins));
                rightSlot.setChanged();
                exchangeInv.setItem(1, new ItemStack(ExtendedItems.SILVER_COIN_HUNDRED.get(), hundredCoins));
            } else {
                rightSlot.set(ItemStack.EMPTY);
                exchangeInv.setItem(1, ItemStack.EMPTY);
                rightSlot.setChanged();
            }
        } else if (inputSlot.getItem().getItem() == ExtendedItems.SILVER_COIN_HUNDRED.get()) {
            int tenCoins = inputSlot.getItem().getCount() * 10;
            if (tenCoins > 64) tenCoins = 60;
            leftSlot.set(new ItemStack(ExtendedItems.SILVER_COIN_TEN.get(), tenCoins));
            leftSlot.setChanged();
            exchangeInv.setItem(0, new ItemStack(ExtendedItems.SILVER_COIN_TEN.get(), tenCoins));

            rightSlot.set(ItemStack.EMPTY);
            rightSlot.setChanged();
            exchangeInv.setItem(1, ItemStack.EMPTY);
        } else if (inputSlot.getItem().equals(ItemStack.EMPTY)) {
            System.out.println("Emptying both slots");
            exchangeInv.setItem(0, ItemStack.EMPTY);
            exchangeInv.setItem(1, ItemStack.EMPTY);
            leftSlot.set(ItemStack.EMPTY);
            leftSlot.setChanged();
            rightSlot.set(ItemStack.EMPTY);
            rightSlot.setChanged();
        }
    }

    public void handleExchange(int side) {
        if (!exchanged && isValidCoin(inputSlot.getItem())) {
            exchanged = true;
            sideExchanged = side;
            int difference = value(inputSlot.getItem()) - value(exchangeInv.getItem(side));
            int value = valueOfItem(inputSlot.getItem().getItem());
            if (difference > 0 && value > 0) {
                int amount = difference / value;
                inputSlot.set(new ItemStack(inputSlot.getItem().getItem(), amount));
                inputSlot.setChanged();
            } else {
                inputSlot.set(ItemStack.EMPTY);
                inputSlot.setChanged();
            }

            // clear out the other side of the converter
            // i.e. if we convert left clear the right side
            if (side == 0) {
                rightSlot.set(ItemStack.EMPTY);
                exchangeInv.setItem(1, ItemStack.EMPTY);
                rightSlot.setChanged();
            } else if (side == 1) {
                leftSlot.set(ItemStack.EMPTY);
                exchangeInv.setItem(0, ItemStack.EMPTY);
                leftSlot.setChanged();
            }
        }
    }

    private static int valueOfItem(Item item) {
        if (item.equals(ExtendedItems.SILVER_COIN_ONE.get())) {
            return 1;
        } else if (item.equals(ExtendedItems.SILVER_COIN_TEN.get())) {
            return 10;
        } else if (item.equals(ExtendedItems.SILVER_COIN_HUNDRED.get())) {
            return 100;
        }
        return 0;
    }

    private static int value(ItemStack stack) {
       if (!isValidCoin(stack)) return 0;
       return valueOfItem(stack.getItem()) * stack.getCount();
    }

    public ItemStack quickMoveStack(PlayerEntity player, int slotId) {
        Slot slot = this.slots.get(slotId);
        ItemStack itemstack = slot.getItem();

        if (slot == inputSlot) {
            if (player.inventory.add(inputSlot.getItem())) {
                inputSlot.set(ItemStack.EMPTY);
                inputSlot.setChanged();
                slotsChanged(player.inventory);
            }
            return ItemStack.EMPTY;
        }

        if (slot == leftSlot) {
            System.out.println("Left slot called");
            if (exchanged && sideExchanged == 0 && (player.inventory.add(leftSlot.getItem()))) {
                leftSlot.set(ItemStack.EMPTY);
                leftSlot.setChanged();
                slotsChanged(player.inventory);
                exchanged = false;
                sideExchanged = -1;
                recalculateResultSlots(inputSlot.getItem());
            }
            return ItemStack.EMPTY;
        }

        if (slot == rightSlot) {
            System.out.println("Right slot called");
            if (exchanged && sideExchanged == 1 && (player.inventory.add(rightSlot.getItem()))) {
                rightSlot.set(ItemStack.EMPTY);
                rightSlot.setChanged();
                slotsChanged(player.inventory);
                exchanged = false;
                sideExchanged = -1;
                recalculateResultSlots(inputSlot.getItem());
            }
            return ItemStack.EMPTY;
        }

        // verify the item is a coin
        if (!(isValidCoin(itemstack))) {
            return ItemStack.EMPTY;
        }

        // handle moving from any other slot
        System.out.println("Every other slot called");

        if (itemstack.getItem().equals(inputSlot.getItem().getItem()) && inputSlot.getItem().getCount() <= 64) {
            System.out.println("Existing Stack");
            int newAmount = inputSlot.getItem().getCount() + itemstack.getCount();
            if (newAmount > 64) {
                itemstack.setCount(newAmount - 64);
                inputSlot.set(new ItemStack(inputSlot.getItem().getItem(), 64));
                coinInputInv.setItem(0, new ItemStack(inputSlot.getItem().getItem(), 64));
                inputSlot.setChanged();
                slot.set(itemstack);
                slot.setChanged();
                return ItemStack.EMPTY;
            } else {
                inputSlot.set(new ItemStack(inputSlot.getItem().getItem(), newAmount));
                coinInputInv.setItem(0, new ItemStack(inputSlot.getItem().getItem(), newAmount));
                inputSlot.setChanged();
                slot.set(ItemStack.EMPTY);
                slot.setChanged();
                return ItemStack.EMPTY;
            }
        } else if (inputSlot.getItem().equals(ItemStack.EMPTY)) {
            System.out.println("Empty Stack");
            inputSlot.set(itemstack);
            coinInputInv.setItem(0, itemstack);
            inputSlot.setChanged();
            slot.set(ItemStack.EMPTY);
            slot.setChanged();
            return ItemStack.EMPTY;
        }

        return itemstack;
    }

    public static boolean isValidCoin(ItemStack stack) {
        return stack.getItem() == ExtendedItems.SILVER_COIN_ONE.get() ||
                stack.getItem() == ExtendedItems.SILVER_COIN_TEN.get() ||
                stack.getItem() == ExtendedItems.SILVER_COIN_HUNDRED.get();
    }

    public ItemStack clicked(int slot1, int slot2, ClickType click, PlayerEntity player) {
        // TODO: ItemStack does not seem to sync correctly with the server
        //  when two stacks of the same coin type are merged or swapped in the input slot.
        Slot s1 = slots.get(slot1);
        Slot s2 = slots.get(slot2);
        System.out.println("Slot1: " + s1 + ", Slot2: " + s2 + ", click: " + click + ", player: " + player);

        return super.clicked(slot1, slot2, click, player);
    }

    @Override
    public boolean stillValid(PlayerEntity p_75145_1_) {
        return true;
    }

    public static void writeContainerInitData(PacketBuffer extraData, int entityId) {
		extraData.writeVarInt(entityId);
	}

    @Override
    public void slotChanged(Container p_71111_1_, int p_71111_2_, ItemStack p_71111_3_) {
        if (!inputSlot.getItem().equals(lastInputSlot) && !exchanged) {
            recalculateResultSlots(inputSlot.getItem());
            lastInputSlot = inputSlot.getItem();
        }
    }
    @Override
    public void setContainerData(Container p_71111_1_, int p_71112_2_, int p_71112_3_) {
        // this method is not needed
    }
    @Override
    public void refreshContainer(Container p_71110_1_, NonNullList<ItemStack> p_71110_2_) {
        // this method is not needed
    }
}
