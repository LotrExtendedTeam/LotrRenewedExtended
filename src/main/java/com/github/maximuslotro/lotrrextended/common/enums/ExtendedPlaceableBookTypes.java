package com.github.maximuslotro.lotrrextended.common.enums;

import java.util.function.Supplier;

import lotr.common.init.LOTRItems;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.IStringSerializable;

public enum ExtendedPlaceableBookTypes implements IStringSerializable {
	BOOK("book", ()->Items.BOOK),
	//WRITABLE_BOOK("writable_book", ()->Items.WRITABLE_BOOK),
	//WRITTEN_BOOK("written_book", ()->Items.WRITTEN_BOOK),
	RED_BOOK("red_book", ()->LOTRItems.RED_BOOK.get()),
	BOOK_OF_TRUE_SILVER("true_silver_book", ()->LOTRItems.BOOK_OF_TRUE_SILVER.get()),
	NONE("none", ()->Items.AIR);

	private final String name;
	private final Supplier<Item> itemType;

	private ExtendedPlaceableBookTypes(String pName, Supplier<Item> pItemType) {
		this.name = pName;
		this.itemType = pItemType;
	}

	@Override
	public String toString() {
		return this.name;
	}

	@Override
	public String getSerializedName() {
		return this.name;
	}

	public Item getItemType() {
		return itemType.get();
	}

	public boolean notEmpty() {
		return this != NONE;
	}

	public boolean isEmpty() {
		return this == NONE;
	}

	public static ExtendedPlaceableBookTypes getByItem(Item item) {
		for (ExtendedPlaceableBookTypes type : values()) {
			if (type.getItemType() == item && item != Items.AIR) {
				return type;
			}
		}
		return NONE;
	}
}
