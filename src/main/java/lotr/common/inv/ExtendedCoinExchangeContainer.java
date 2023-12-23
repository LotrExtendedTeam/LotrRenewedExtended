package lotr.common.inv;

import lotr.common.entity.npc.NPCEntity;
import lotr.common.init.ExtendedItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * @author RichardB122
 */
public class ExtendedCoinExchangeContainer extends Container {

    private World level;

    public NPCEntity traderNPC;

    public boolean exchanged = false;

    public IInventory coinInputInv = (IInventory) new ExtendedCoinExchangeInventory(this, 1);

    public IInventory exchangeInv = (IInventory) new ExtendedCoinExchangeInventory(this, 2);

    public ExtendedCoinExchangeContainer(int containerId, PlayerEntity player, NPCEntity npc) {
        super(null, containerId);

        this.level = player.level;
        this.traderNPC = npc;

        Slot inputSlot = new Slot(this.coinInputInv, 0, 80, 46) {
            public boolean mayPlace(ItemStack stack) {
                return super.mayPlace(stack) && isValidCoin(stack);
            }
        };
        addSlot(inputSlot);

        addSlot(new ExtendedCoinResultSlot(this, exchangeInv, 0, 26, 46));
        addSlot(new ExtendedCoinResultSlot(this, exchangeInv, 1, 134, 46));

        int i;
        for (i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++)
                addSlot(new Slot(player.inventory, j + i * 9 + 9, 8 + j * 18, 106 + i * 18));
        }
        for (i = 0; i < 9; i++) {
            addSlot(new Slot(player.inventory, i, 8 + i * 18, 164));
        }

        //func_75130_a(this.coinInputInv);
        // TODO: is this line equivalent to the above and is it necessary?
        //slotsChanged(coinInputInv);
    }

    public static boolean isValidCoin(ItemStack stack) {
        return stack.getItem() == ExtendedItems.SILVER_COIN_ONE.get() ||
                stack.getItem() == ExtendedItems.SILVER_COIN_TEN.get() ||
                stack.getItem() == ExtendedItems.SILVER_COIN_HUNDRED.get();
    }

    @Override
    public boolean stillValid(PlayerEntity p_75145_1_) {
        return false;
    }

    /*
    public class LOTRContainerCoinExchange  {

        private World theWorld;

        public LOTREntityNPC theTraderNPC;

        public boolean exchanged = false;

        public boolean func_75145_c(EntityPlayer entityplayer) {
            return true;
        }

        public void handleExchangePacket(int slot) {
            if (!this.exchanged)
                if (this.coinInputInv.func_70301_a(0) != null && slot >= 0 && slot < this.exchangeInv.func_70302_i_() && this.exchangeInv.func_70301_a(slot) != null) {
                    this.exchanged = true;
                    int coins = (this.exchangeInv.func_70301_a(slot)).field_77994_a;
                    int coinsTaken = 0;
                    if (slot == 0) {
                        coinsTaken = coins / 10;
                    } else if (slot == 1) {
                        coinsTaken = coins * 10;
                    }
                    this.coinInputInv.func_70298_a(0, coinsTaken);
                    for (int i = 0; i < this.exchangeInv.func_70302_i_(); i++) {
                        if (i != slot)
                            this.exchangeInv.func_70299_a(i, null);
                    }
                    func_75142_b();
                    this.theTraderNPC.playTradeSound();
                }
        }

        public void func_75132_a(ICrafting crafting) {
            sendClientExchangedData(crafting);
            super.func_75132_a(crafting);
        }

        public void func_75142_b() {
            for (int i = 0; i < this.field_75149_d.size(); i++) {
                ICrafting crafting = this.field_75149_d.get(i);
                sendClientExchangedData(crafting);
            }
            super.func_75142_b();
        }

        private void sendClientExchangedData(ICrafting crafting) {
            crafting.func_71112_a(this, 0, this.exchanged ? 1 : 0);
        }

        @SideOnly(Side.CLIENT)
        public void func_75137_b(int i, int j) {
            if (i == 0)
                this.exchanged = (j == 1);
        }

        public void func_75130_a(IInventory inv) {
            if (inv == this.coinInputInv) {
                if (!this.exchanged) {
                    ItemStack coin = this.coinInputInv.func_70301_a(0);
                    if (coin != null && coin.field_77994_a > 0 && isValidCoin(coin)) {
                        int coins = coin.field_77994_a;
                        int coinType = coin.func_77960_j();
                        if (coinType > 0) {
                            int coinsFloor = coins;
                            while (coinsFloor * 10 > this.exchangeInv.func_70297_j_())
                                coinsFloor--;
                            this.exchangeInv.func_70299_a(0, new ItemStack(LOTRMod.silverCoin, coinsFloor * 10, coinType - 1));
                        } else {
                            this.exchangeInv.func_70299_a(0, null);
                        }
                        if (coinType < LOTRItemCoin.values.length - 1 && coins >= 10) {
                            this.exchangeInv.func_70299_a(1, new ItemStack(LOTRMod.silverCoin, coins / 10, coinType + 1));
                        } else {
                            this.exchangeInv.func_70299_a(1, null);
                        }
                    } else {
                        this.exchangeInv.func_70299_a(0, null);
                        this.exchangeInv.func_70299_a(1, null);
                    }
                }
            } else if (inv == this.exchangeInv) {
                if (this.exchanged) {
                    boolean anyItems = false;
                    for (int i = 0; i < this.exchangeInv.func_70302_i_(); i++) {
                        if (this.exchangeInv.func_70301_a(i) != null)
                            anyItems = true;
                    }
                    if (!anyItems) {
                        this.exchanged = false;
                        func_75130_a(this.coinInputInv);
                    }
                }
            }
            super.func_75130_a(inv);
        }

        public void func_75134_a(EntityPlayer entityplayer) {
            super.func_75134_a(entityplayer);
            if (!entityplayer.field_70170_p.field_72995_K) {
                int i;
                for (i = 0; i < this.coinInputInv.func_70302_i_(); i++) {
                    ItemStack itemstack = this.coinInputInv.func_70304_b(i);
                    if (itemstack != null)
                        entityplayer.func_71019_a(itemstack, false);
                }
                if (this.exchanged)
                    for (i = 0; i < this.exchangeInv.func_70302_i_(); i++) {
                        ItemStack itemstack = this.exchangeInv.func_70304_b(i);
                        if (itemstack != null)
                            entityplayer.func_71019_a(itemstack, false);
                    }
            }
        }

        public ItemStack func_82846_b(EntityPlayer entityplayer, int i) {
            ItemStack itemstack = null;
            Slot slot = this.field_75151_b.get(i);
            if (slot != null && slot.func_75216_d()) {
                ItemStack itemstack1 = slot.func_75211_c();
                itemstack = itemstack1.func_77946_l();
                if (i < 3) {
                    if (!func_75135_a(itemstack1, 3, 39, true))
                        return null;
                    func_75130_a(slot.field_75224_c);
                } else {
                    boolean flag = false;
                    Slot coinSlot = this.field_75151_b.get(0);
                    ItemStack coinStack = coinSlot.func_75211_c();
                    if (coinSlot.func_75214_a(itemstack1))
                        if (func_75135_a(itemstack1, 0, 1, true))
                            flag = true;
                    if (!flag)
                        if (i >= 3 && i < 30) {
                            if (!func_75135_a(itemstack1, 30, 39, false))
                                return null;
                        } else if (!func_75135_a(itemstack1, 3, 30, false)) {
                            return null;
                        }
                }
                if (itemstack1.field_77994_a == 0) {
                    slot.func_75215_d(null);
                    func_75142_b();
                } else {
                    slot.func_75218_e();
                }
                if (itemstack1.field_77994_a == itemstack.field_77994_a)
                    return null;
                slot.func_82870_a(entityplayer, itemstack1);
            }
            return itemstack;
        }

        protected void func_75133_b(int i, int j, boolean flag, EntityPlayer entityplayer) {}*/

}
