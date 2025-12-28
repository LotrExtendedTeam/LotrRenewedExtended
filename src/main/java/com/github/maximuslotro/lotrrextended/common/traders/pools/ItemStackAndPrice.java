package com.github.maximuslotro.lotrrextended.common.traders.pools;

import net.minecraft.item.ItemStack;

public class ItemStackAndPrice {

    private ItemStack itemStack;
    private int averageCost;

    public ItemStackAndPrice(ItemStack stack, int pAverageCost) {
        this.itemStack = stack;
        this.averageCost = pAverageCost;
    }

	/**
	 * Gets the average cost of the item
	 * @return The average cost of the item
	 * */
	public int getAverageCost(){
		return averageCost;
	}

    public ItemStack getItemStack() {
        return itemStack;
    }

}
