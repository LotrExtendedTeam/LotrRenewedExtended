package com.github.maximuslotro.lotrrextended.common.datafixers;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;

public class ExtendedStructureDataFixer {
	public static boolean remapUsingExtendedMappings(CompoundNBT root) {
		boolean changed = false;
		if(root.contains("palette", 9)) {
			ListNBT palette = root.getList("palette", 10);
			for(int i = 0; i < palette.size(); i++) {
				CompoundNBT state = palette.getCompound(i);
				ResourceLocation oldId = new ResourceLocation(state.getString("Name"));
				Block newBlock = ExtendedMissingMappings.resolveBlock(oldId);
				if(newBlock != null && newBlock.getRegistryName() != null) {
					ResourceLocation newId = newBlock.getRegistryName();
					if(!newId.equals(oldId)) {
						state.putString("Name", newId.toString());
						changed = true;
					}
				}
			}
		}
		if(root.contains("blocks", 9)) {
			ListNBT blocks = root.getList("blocks", 10);
			for(int i = 0; i < blocks.size(); i++) {
				CompoundNBT entry = blocks.getCompound(i);
				if(!entry.contains("nbt", 10)) continue;
				CompoundNBT tileNbt = entry.getCompound("nbt");
				if(tileNbt.contains("id", 8)) {
					String oldId = tileNbt.getString("id");
					String newId = ExtendedMissingMappings.resolveBlockEntity(oldId);
					if(newId != null && !newId.equals(oldId)) {
						tileNbt.putString("id", newId);
						changed = true;
					}
				}
				// Container tile entities (chests, barrels, hoppers, etc.)
				if(remapItemStackList(tileNbt, "Items")) {
					changed = true;
				}
				// Lecterns
				if(remapItemStackTag(tileNbt, "Book")) {
					changed = true;
				}
				// Plates
				if(remapItemStackTag(tileNbt, "FoodItem")) {
					changed = true;
				}
				// Vessels
				if(remapItemStackTag(tileNbt, "DrinkItem")) {
					changed = true;
				}
				// Large Pots old
				if(remapItemStackTag(tileNbt, "contained_item")) {
					changed = true;
				}
				// Weaponrack old
				if(remapItemStackTag(tileNbt, "contained_item")) {
					changed = true;
				}
				// Large Pots & Weaponrack new
				if(remapItemStackTag(tileNbt, "Item")) {
					changed = true;
				}
			}
		}
		if(root.contains("entities", 9)) {
			ListNBT entities = root.getList("entities", 10);
			for(int i = 0; i < entities.size(); i++) {
				CompoundNBT entry = entities.getCompound(i);
				if(!entry.contains("nbt", 10)) continue;
				CompoundNBT entityNbt = entry.getCompound("nbt");
				if(!entityNbt.contains("id", 8)) continue;
				ResourceLocation oldId = new ResourceLocation(entityNbt.getString("id"));
				EntityType<?> newEntity = ExtendedMissingMappings.resolveEntity(oldId);
				if(newEntity != null && newEntity.getRegistryName() != null) {
					ResourceLocation newId = newEntity.getRegistryName();
					if(!newId.equals(oldId)) {
						entityNbt.putString("id", newId.toString());
						changed = true;
					}
				}
				if(remapItemStackList(entityNbt, "HandItems")) changed = true;
				if(remapItemStackList(entityNbt, "ArmorItems")) changed = true;
				if(remapItemStackList(entityNbt, "Items")) changed = true;
				if(remapItemStackTag(entityNbt, "Item")) changed = true;
			}
		}
		return changed;
	}

	private static boolean remapItemStackList(CompoundNBT entityNbt, String listKey) {
		if(!entityNbt.contains(listKey, 9)) {
			return false;
		}
		boolean changed = false;
		ListNBT list = entityNbt.getList(listKey, 10);
		for(int i = 0; i < list.size(); i++) {
			if(remapItemId(list.getCompound(i))) {
				changed = true;
			}
		}
		return changed;
	}

	private static boolean remapItemStackTag(CompoundNBT entityNbt, String tagKey) {
		if(!entityNbt.contains(tagKey, 10)) {
			return false;
		}
		return remapItemId(entityNbt.getCompound(tagKey));
	}

	private static boolean remapItemId(CompoundNBT stackNbt) {
		if(!stackNbt.contains("id", 8)) {
			return false;
		}
		String rawId = stackNbt.getString("id");
		if(rawId.isEmpty() || "minecraft:air".equals(rawId)) {
			return false;
		}
		ResourceLocation oldId = ResourceLocation.tryParse(rawId);
		if(oldId == null) {
			return false;
		}
		Item newItem = ExtendedMissingMappings.resolveItem(oldId);
		if(newItem != null && newItem.getRegistryName() != null) {
			ResourceLocation newId = newItem.getRegistryName();
			if(!newId.equals(oldId)) {
				stackNbt.putString("id", newId.toString());
				return true;
			}
		}
		return false;
	}
}
