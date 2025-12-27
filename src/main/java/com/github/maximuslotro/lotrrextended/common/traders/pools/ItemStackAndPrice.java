package com.github.maximuslotro.lotrrextended.common.traders.pools;

import net.minecraft.item.ItemStack;

import java.util.Optional;

public class ItemStackAndPrice {

    private ItemStack itemStack;
    private Optional<Integer> avgPrice;

    public ItemStackAndPrice(ItemStack stack) {
        this.itemStack = stack;
        this.avgPrice = Optional.empty();
    }

    public ItemStackAndPrice(ItemStack stack, int avgPrice) {
        this.itemStack = stack;
        this.avgPrice = Optional.of(avgPrice);
    }

    public Optional<Integer> getPrice() {
        return avgPrice;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

}
