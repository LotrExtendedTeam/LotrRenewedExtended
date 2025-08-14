package com.github.maximuslotro.lotrrextended.common.inventory.container;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedContainers;
import com.github.maximuslotro.lotrrextended.common.inventory.container.ExtendedCoinExchangeSlots.ExtendedInputCoinSlot;
import com.github.maximuslotro.lotrrextended.common.inventory.container.ExtendedCoinExchangeSlots.ExtendedOutputCoinSlot;
import com.github.maximuslotro.lotrrextended.common.inventory.container.ExtendedCoinExchangeSlots.ExtendedVisualCoinSlot;

import lotr.common.entity.npc.ExtendedTraderEntity;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.init.ExtendedItems;
import lotr.common.util.CoinUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.IContainerListener;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

/**
 * @author RichardB122
 * @author maximuslotro
 */
public class ExtendedCoinExchangeContainer extends Container implements IContainerListener {
	public static final ITextComponent CONTAINER_TITLE = new TranslationTextComponent("container.lotr.coin_exchange");

	public Slot inputSlot;
	public Slot leftVisualOutputSlot;
	public Slot rightVisualOutputSlot;
	public Slot leftOutputSlot;
	public Slot rightOutputSlot;

	// 0->Input, 1-> Left Visual Output, 2-> Right Visual Output, 3-> Left output, 4-> Right output
	public Inventory coinSlots = new Inventory(5);

	public final ExtendedTraderEntity trader;

	public ExtendedCoinExchangeContainer(int containerId, PlayerInventory inventory, PacketBuffer extraData) {
		super(ExtendedContainers.COIN_TRADING.get(), containerId);

		this.trader = (ExtendedTraderEntity) inventory.player.level.getEntity(extraData.readVarInt());

		inputSlot = addSlot(new ExtendedInputCoinSlot(this.coinSlots, 0, 80, 46) {
			@Override
			public void setChanged() {
				super.setChanged();
				reCheckVirtual();
			}
			@Override
			public ItemStack onTake(PlayerEntity player, ItemStack stack) {
				reCheckVirtual();
				return super.onTake(player, stack);
			}
		});
		leftOutputSlot = addSlot(new ExtendedOutputCoinSlot(coinSlots, 3, 26, 46) {
			@Override
			public void setChanged() {
				super.setChanged();
				reCheckVirtual();
			}
			@Override
			public ItemStack onTake(PlayerEntity player, ItemStack stack) {
				reCheckVirtual();
				return super.onTake(player, stack);
			}
		});
		rightOutputSlot = addSlot(new ExtendedOutputCoinSlot(coinSlots, 4, 134, 46) {
			@Override
			public void setChanged() {
				super.setChanged();
				reCheckVirtual();
			}
			@Override
			public ItemStack onTake(PlayerEntity player, ItemStack stack) {
				reCheckVirtual();
				return super.onTake(player, stack);
			}
		});
		leftVisualOutputSlot = addSlot(new ExtendedVisualCoinSlot(coinSlots, 1, 26, 46));
		rightVisualOutputSlot = addSlot(new ExtendedVisualCoinSlot(coinSlots, 2, 134, 46));

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++)
				addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 106 + i * 18));
		}
		for (int i = 0; i < 9; i++) {
			addSlot(new Slot(inventory, i, 8 + i * 18, 164));
		}

		// the slotChanged method is used to help recalculate the preview for the coin exchanges
		addSlotListener(this);
	}

	/**
	 * Calculates and exchanges coins according to which button was pressed
	 * @param side An integer representing left coin slot if 0, and right coin slot if 1
	 * */
	public void handleExchange(int side) {
		boolean converted = false;
		// Check for valid input item during exchange
		if (!((NPCEntity)trader).level.isClientSide() && CoinUtils.isValidCoin(inputSlot.getItem())) {
			// Check side for transfer and if valid item
			if (side==1 && inputSlot.getItem().getItem() == ExtendedItems.SILVER_COIN_ONE.get()) {
				converted = convertCurrencyUp(ExtendedItems.SILVER_COIN_ONE.get(), ExtendedItems.SILVER_COIN_TEN.get(), false);
			} else if (side==1 && inputSlot.getItem().getItem() == ExtendedItems.SILVER_COIN_TEN.get()) {
				converted = convertCurrencyUp(ExtendedItems.SILVER_COIN_TEN.get(), ExtendedItems.SILVER_COIN_HUNDRED.get(), false);
			} else if (side==0 && inputSlot.getItem().getItem() == ExtendedItems.SILVER_COIN_TEN.get()) {
				converted = convertCurrencyDown(ExtendedItems.SILVER_COIN_TEN.get(), ExtendedItems.SILVER_COIN_ONE.get(), false);
			} else if (side==0 && inputSlot.getItem().getItem() == ExtendedItems.SILVER_COIN_HUNDRED.get()) {
				converted = convertCurrencyDown(ExtendedItems.SILVER_COIN_HUNDRED.get(), ExtendedItems.SILVER_COIN_TEN.get(), false);
			}
			if(converted) {
				trader.playTradeSound(); // attempt to play sound due to successful convert
			}
		}
	}

	/**
	 * Re-checks if virtual coins can be generated
	 * */
	public void reCheckVirtual() {
		// Check for valid input item during exchange
		if (CoinUtils.isValidCoin(inputSlot.getItem())) {
			if (inputSlot.getItem().getItem() == ExtendedItems.SILVER_COIN_ONE.get()) {
				rightVisualOutputSlot.set(ItemStack.EMPTY);
				leftVisualOutputSlot.set(ItemStack.EMPTY);
				convertCurrencyUp(ExtendedItems.SILVER_COIN_ONE.get(), ExtendedItems.SILVER_COIN_TEN.get(), true);
			} else if (inputSlot.getItem().getItem() == ExtendedItems.SILVER_COIN_TEN.get()) {
				rightVisualOutputSlot.set(ItemStack.EMPTY);
				leftVisualOutputSlot.set(ItemStack.EMPTY);
				convertCurrencyUp(ExtendedItems.SILVER_COIN_TEN.get(), ExtendedItems.SILVER_COIN_HUNDRED.get(), true);
				convertCurrencyDown(ExtendedItems.SILVER_COIN_TEN.get(), ExtendedItems.SILVER_COIN_ONE.get(), true);
			} else if (inputSlot.getItem().getItem() == ExtendedItems.SILVER_COIN_HUNDRED.get()) {
				rightVisualOutputSlot.set(ItemStack.EMPTY);
				leftVisualOutputSlot.set(ItemStack.EMPTY);
				convertCurrencyDown(ExtendedItems.SILVER_COIN_HUNDRED.get(), ExtendedItems.SILVER_COIN_TEN.get(), true);
			}
		}
	}
	
	/**
	 * Converts a higher denomination currency into a lower denomination currency
	 * @param fromItem The Item to convert from
	 * @param toItem The Item to convert to
	 * @param virtual Should calculations only update the virtial slot
	 * @return True if currency was converted
	 * */
	public boolean convertCurrencyDown(Item fromItem, Item toItem, boolean virtual){
		int newLowerCoins = inputSlot.getItem().getCount() * 10;
		if (newLowerCoins > 64) {
			// Cap maximum transfer amount due to down converting
			newLowerCoins = 60;
		}
		if(leftOutputSlot.getItem().isEmpty()) {
			if(virtual) {
				leftVisualOutputSlot.set(new ItemStack(toItem, newLowerCoins));
				this.broadcastChanges();
				return false;
			}else {
				rightVisualOutputSlot.set(ItemStack.EMPTY);
				leftVisualOutputSlot.set(ItemStack.EMPTY);
			}
			leftOutputSlot.set(new ItemStack(toItem, newLowerCoins));
			inputSlot.set(new ItemStack(fromItem, inputSlot.getItem().getCount() - (newLowerCoins/ 10)));
			return true;
		}else {
			if(virtual) {
				return false;
			}else {
				rightVisualOutputSlot.set(ItemStack.EMPTY);
				leftVisualOutputSlot.set(ItemStack.EMPTY);
			}
			if(leftOutputSlot.getItem().getItem().equals(toItem)) {
				int currentHigherCoinsCount = leftOutputSlot.getItem().getCount();
				int numberOfHigherToRemove = 0;
				for(int i = 1; i < 7; i++) {
					if(currentHigherCoinsCount + (10*i)<=64) {
						numberOfHigherToRemove = i;
					}
				}
				if(numberOfHigherToRemove * 10 > newLowerCoins) {
					ExtendedLog.warn("DEVELOPMENT ERROR IN "+fromItem.getRegistryName().toString()+" DOWN COIN EXCHANGE CALCULATION " + inputSlot.getItem().getCount() + ":" + leftOutputSlot.getItem().getCount());
					ExtendedLog.warn("PLEASE REPORT THIS MESSAGE AND MESSAGE DATA!");
					numberOfHigherToRemove = 6;
				}
				if(numberOfHigherToRemove!=0) {
					leftOutputSlot.set(new ItemStack(toItem, currentHigherCoinsCount + (numberOfHigherToRemove * 10)));
					inputSlot.set(new ItemStack(fromItem, inputSlot.getItem().getCount() - numberOfHigherToRemove));
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Converts a lower denomination currency into a higher denomination currency
	 * @param fromItem The Item to convert from
	 * @param toItem The Item to convert to
	 * @param virtual Should calculations only update the virtial slot
	 * @return True if currency was converted
	 * */
	public boolean convertCurrencyUp(Item fromItem, Item toItem, boolean virtual){
		if (inputSlot.getItem().getCount() >= 10) {
			// Since max stack size is 64, 64/10 will always be less than 64
			int newHigherCoins = inputSlot.getItem().getCount() / 10;
			int lowerCoinsRemainder = inputSlot.getItem().getCount() % 10;
			if(rightOutputSlot.getItem().isEmpty()) {
				if(virtual) {
					rightVisualOutputSlot.set(new ItemStack(toItem, newHigherCoins));
					return false;
				}else {
					rightVisualOutputSlot.set(ItemStack.EMPTY);
					leftVisualOutputSlot.set(ItemStack.EMPTY);
				}
				//Since max stack is less than 64, don't do checking
				rightOutputSlot.set(new ItemStack(toItem, newHigherCoins));
				inputSlot.set(new ItemStack(fromItem, lowerCoinsRemainder));
				return true;
			}else {
				if(virtual) {
					return false;
				}else {
					rightVisualOutputSlot.set(ItemStack.EMPTY);
					leftVisualOutputSlot.set(ItemStack.EMPTY);
				}
				if(rightOutputSlot.getItem().getItem().equals(toItem)) {
					if(rightOutputSlot.getItem().getCount()==64) {
						return false;
					}
					int addedHigherCoinsCount = newHigherCoins+ rightOutputSlot.getItem().getCount();
					// Check for max stack size because of additional stacks
					if(addedHigherCoinsCount>64) {
						rightOutputSlot.set(new ItemStack(toItem, 64));
						inputSlot.set(new ItemStack(fromItem, (addedHigherCoinsCount-64) * 10 + lowerCoinsRemainder));
						return true;
					}
					else {
						rightOutputSlot.set(new ItemStack(toItem, addedHigherCoinsCount));
						inputSlot.set(new ItemStack(fromItem, lowerCoinsRemainder));
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public ItemStack quickMoveStack(PlayerEntity player, int slotId) {
		Slot slot = this.slots.get(slotId);
		// From slot item
		ItemStack itemstack = slot.getItem();
		// Moving from input slot
		if (slot == inputSlot) {
			if (player.inventory.add(inputSlot.getItem())) {
				inputSlot.set(ItemStack.EMPTY);
				slotsChanged(player.inventory);
			}
			return ItemStack.EMPTY;
		}

		// Moving from lower output slot
		if (slot == leftOutputSlot) {
			if (player.inventory.add(leftOutputSlot.getItem())) {
				leftOutputSlot.set(ItemStack.EMPTY);
				slotsChanged(player.inventory);
			}
			return ItemStack.EMPTY;
		}

		// Moving from higher output slot
		if (slot == rightOutputSlot) {
			if (player.inventory.add(rightOutputSlot.getItem())) {
				rightOutputSlot.set(ItemStack.EMPTY);
				slotsChanged(player.inventory);
			}
			return ItemStack.EMPTY;
		}

		// Disable shifting from visual slots
		if (slot == leftVisualOutputSlot || slot == rightVisualOutputSlot) {
			return ItemStack.EMPTY;
		}

		
		// verify the shifted item is a coin
		if (!(CoinUtils.isValidCoin(itemstack))) {
			return ItemStack.EMPTY;
		}

		// handle moving from any other (probably inventory) slot into input slot if matching items
		if (itemstack.getItem().equals(inputSlot.getItem().getItem()) && inputSlot.getItem().getCount() <= 64) {
			int newAmount = inputSlot.getItem().getCount() + itemstack.getCount();
			if (newAmount > 64) {
				itemstack.setCount(newAmount - 64);
				inputSlot.set(new ItemStack(inputSlot.getItem().getItem(), 64));
				slot.set(itemstack);
				return ItemStack.EMPTY;
			} else {
				inputSlot.set(new ItemStack(inputSlot.getItem().getItem(), newAmount));
				slot.set(ItemStack.EMPTY);
				return ItemStack.EMPTY;
			}
		} else if (!inputSlot.getItem().isEmpty() && !inputSlot.getItem().getItem().equals(itemstack.getItem())) {
			// We need to return ItemStack.EMPTY if the item cannot be put in this slot or it will infinitely loop
			return ItemStack.EMPTY;
		} else if (inputSlot.getItem().isEmpty()) {
			// Directly place item in input slot since it is empty and we already validated it as a coin
			inputSlot.set(itemstack);
			slot.set(ItemStack.EMPTY);
			return ItemStack.EMPTY;
		}
		return itemstack;
	}

	@Override
	public boolean stillValid(PlayerEntity p_75145_1_) {
		return true;
	}

	public static void writeContainerInitData(PacketBuffer extraData, int entityId) {
		extraData.writeVarInt(entityId);
	}

	@Override
	public ItemStack clicked(int slot1, int slot2, ClickType click, PlayerEntity player) {
		if(slot1==this.leftVisualOutputSlot.index || slot1==this.rightVisualOutputSlot.index) {
			return super.clicked(slot1, slot2, click, player);
		}
		ItemStack stack = super.clicked(slot1, slot2, click, player);
		reCheckVirtual();
		if(player.inventory.getCarried()==this.inputSlot.getItem() || inputSlot.getItem().isEmpty()) {
			rightVisualOutputSlot.set(ItemStack.EMPTY);
			leftVisualOutputSlot.set(ItemStack.EMPTY);
		}
		return stack;
	}
	
	@Override
	public void slotChanged(Container pContainerToSend, int pSlotInd, ItemStack pStack) {
		if(pSlotInd==this.leftVisualOutputSlot.index || pSlotInd==this.rightVisualOutputSlot.index) {
			return;
		}
		reCheckVirtual();
	}
	@Override
	public void setContainerData(Container p_71111_1_, int p_71112_2_, int p_71112_3_) {
		// This method is not needed
	}

	@Override
	public void refreshContainer(Container p_71110_1_, NonNullList<ItemStack> p_71110_2_) {
		// This method is not needed
	}

	@Override
	public void removed(PlayerEntity pPlayer) {
		super.removed(pPlayer);
		if (!leftOutputSlot.getItem().isEmpty()) {
			pPlayer.drop(leftOutputSlot.getItem(), false);
		}
		if (!rightOutputSlot.getItem().isEmpty()) {
			pPlayer.drop(rightOutputSlot.getItem(), false);
		}
		if (!inputSlot.getItem().isEmpty()) {
			pPlayer.drop(inputSlot.getItem(), false);
		}
	}

	@Override
	public void slotsChanged(IInventory pInventory) {
		super.slotsChanged(pInventory);
	}
}
