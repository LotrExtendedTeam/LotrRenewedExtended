package com.github.maximuslotro.lotrrextended.common.utils;

import lotr.common.entity.npc.*;
import lotr.common.init.ExtendedItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Util;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.github.maximuslotro.lotrrextended.common.item.ExtendedSmithScrollItem;
import com.github.maximuslotro.lotrrextended.common.modifiers.ExtendedModifierRarity;
import com.github.maximuslotro.lotrrextended.common.modifiers.ExtendedModifierTypes;
import com.github.maximuslotro.lotrrextended.common.modifiers.ExtendedModifiers;
import com.github.maximuslotro.lotrrextended.common.modifiers.ExtendedModifier;
import com.google.common.collect.Lists;

/**
 * Based on {@link EnchantmentHelper}
 */
public class ExtendedModifierUtils {
	private static final String NBTMODIFIER = "LOTREnchantments";
	public static void addModifier(ItemStack stack, ExtendedModifier modifier) {
		CompoundNBT tag = stack.getOrCreateTag();
		if (!tag.contains(NBTMODIFIER, 9)) {
			tag.put(NBTMODIFIER, new ListNBT());
		}
		ListNBT listnbt = tag.getList(NBTMODIFIER, 10);
		CompoundNBT compoundnbt = new CompoundNBT();
		compoundnbt.putString("key", modifier.getNbtKey());
		listnbt.add(compoundnbt);
	}

	public static void setModifiers(ItemStack stack, List<ExtendedModifier> modifiers) {
		clearModifiers(stack);
		ListNBT listnbt = new ListNBT();
		for(ExtendedModifier modifier : modifiers) {
			CompoundNBT compoundnbt = new CompoundNBT();
			compoundnbt.putString("key", modifier.getNbtKey());
			listnbt.add(compoundnbt);
		}
		if(!listnbt.isEmpty()) {
			stack.getOrCreateTag().put(NBTMODIFIER, new ListNBT());
		}
	}

	public static void removeModifier(ItemStack stack, ExtendedModifier modifier) {
		if (!getModifierList(stack).contains(modifier)) {
			return;
		}

		ListNBT listnbt = stack.getTag().getList(NBTMODIFIER, 10); //Null tag already validated by getModifierList
		List<CompoundNBT> entriesToRemove = new ArrayList<>();
		for (INBT nbt : listnbt) {
			CompoundNBT entry = (CompoundNBT) nbt;
			if (entry.getString("key").equals(modifier.getNbtKey())) entriesToRemove.add(entry);
		}

		listnbt.removeAll(entriesToRemove);
		if(listnbt.isEmpty()) {
			stack.getTag().remove(NBTMODIFIER);
			if(stack.getTag().isEmpty()) {
				stack.setTag(null);
			}
		}
	}

	public static boolean clearModifiers(ItemStack stack) {
		if (!hasModifiers(stack)) {
			return false;
		}
		CompoundNBT tag = stack.getTag(); //Null tag already validated by getModifierList
		tag.remove(NBTMODIFIER);
		if(tag.isEmpty()) {
			stack.setTag(null);
		}
		return true;
	}

	public static void incrementBaneProgress(ItemStack stack, Entity entity, PlayerEntity player) {
		// exit the method if the item already has a bane
		if (containsBane(getModifierList(stack))) {
			return;
		}

		// exit the method if there is applicable bane for that entity
		ExtendedModifier modifier = getBaneByEntity(entity);
		// Only allow earned banes to be progressed
		if (modifier == null || modifier.getRarity()!=ExtendedModifierRarity.EARNED) {
			return;
		}

		CompoundNBT tag = stack.getOrCreateTag(); // Use here as we want to make a tag
		if (!tag.contains("LOTRBaneProgress", 9)) {
			tag.put("LOTRBaneProgress", new ListNBT());
		}

		ListNBT listnbt = tag.getList("LOTRBaneProgress", 10); // Already filters for non CompoundNBT
		int updatedKillCount = 0;
		boolean containsProgress = false;
		for (INBT nbt : listnbt) {
			CompoundNBT entry = (CompoundNBT) nbt;
			if (entry.getString("key").equals(modifier.getNbtKey())) {
				containsProgress = true;
				updatedKillCount = entry.getInt("kills") + 1;
				entry.putInt("kills", updatedKillCount);
			}
		}

		if (!containsProgress) {
			updatedKillCount = 1;
			CompoundNBT compoundnbt = new CompoundNBT();
			compoundnbt.putString("key", modifier.getNbtKey());
			compoundnbt.putInt("kills", updatedKillCount);
			listnbt.add(compoundnbt);
		}

		if (updatedKillCount >= 100) {
			// announce in chat that the item has acquired the bane
			if (player != null) {
				player.sendMessage(new TranslationTextComponent("enchant.lotrextended.bane.earn", stack.getDisplayName(), new TranslationTextComponent("enchant.lotrextended."+ modifier.getLocalKey() + ".race")), Util.NIL_UUID);
				for (ServerPlayerEntity cPlayer : player.getServer().getPlayerList().getPlayers()) {
					if (cPlayer.getUUID() == player.getUUID()) continue;
					cPlayer.sendMessage(new TranslationTextComponent("enchant.lotrextended.bane.earn_name", player.getName(), stack.getDisplayName(), new TranslationTextComponent("enchant.lotrextended."+ modifier.getLocalKey() + ".race")), Util.NIL_UUID);
				}
			}

			ExtendedModifierUtils.addModifier(stack, modifier);
			tag.remove("LOTRBaneProgress");
			if(tag.isEmpty()) {
				stack.setTag(null);
			}
		}
	}

	public static String getBaneData(ItemStack stack) {
		StringBuilder result = new StringBuilder();

		if (!stack.hasTag() || !stack.getTag().contains("LOTRBaneProgress", 9)) {
			return "";
		}

		ListNBT listnbt = stack.getTag().getList("LOTRBaneProgress", 10); // Already filters for non CompoundNBT
		for (INBT nbt : listnbt) {
			CompoundNBT entry = (CompoundNBT) nbt;
			result.append(entry.getString("key")).append(": ").append(entry.getInt("kills") + ", ");
		}

		if (result.length() > 2) {
			return result.toString().substring(0, result.length() - 2);
		} else {
			return result.toString();
		}
	}

	public static boolean containsBane(List<ExtendedModifier> modifiers) {
		return modifiers.stream().anyMatch((modifier)-> modifier.getModifierType()==ExtendedModifierTypes.BANE);
	}

	public static boolean hasModifiers(ItemStack stack) {
		if (stack.hasTag() && stack.getTag().contains(NBTMODIFIER, 9)) {
			return !stack.getTag().getList(NBTMODIFIER, 10).isEmpty();
		} else {
			return false;
		}
	}

	public static List<ExtendedModifier> getModifierList(ItemStack stack) {
		if(stack.getItem() == ExtendedItems.MODIFIER_SCROLL.get()){
			List<ExtendedModifier> result = new ArrayList<>();
			if(!ExtendedSmithScrollItem.getModifier(stack).isUnknown()) {
				result.add(ExtendedSmithScrollItem.getModifier(stack));
			}
			return result;
		}else {
			if (!hasModifiers(stack)) {
				return new ArrayList<>();
			}
			List<ExtendedModifier> result = new ArrayList<>();
			ListNBT listnbt = stack.getTag().getList(NBTMODIFIER, 10);
			for (INBT nbt : listnbt) {
				CompoundNBT entry = (CompoundNBT) nbt;//Already checked above getList(NBTMODIFIER, 10)
				ExtendedModifier modifier = ExtendedModifiers.MODIFIER_MAP.get(entry.getString("key"));
				if (modifier != null) {
					result.add(modifier);
				}
			}
			return result;
		}
	}

	// Do we need this?
	public static Map<ExtendedModifierTypes, Integer> getModifierListByLevel(ItemStack stack) {
		List<ExtendedModifier> mergableModifiers = ExtendedModifierUtils.getModifierList(stack);
		Map<ExtendedModifierTypes, Integer> map = new LinkedHashMap<>();
		for(ExtendedModifier modifier : mergableModifiers) {
			map.put(modifier.getModifierType(), modifier.getModifierLevel());
		}
		return map;
	}


	public static boolean isBaneEffectiveAgainst(ExtendedModifier modifier, Entity entity) {
		return getBaneByEntity(entity) == modifier;
	}

	public static ExtendedModifier getBaneByEntity(Entity entity) {
		ExtendedModifier bane = null;
		if (entity instanceof OrcEntity) {
			bane = ExtendedModifiers.ORC_BANE;
		} else if (entity instanceof ElfEntity) {
			bane = ExtendedModifiers.ELF_BANE;
		} else if (entity instanceof DwarfEntity) {
			bane = ExtendedModifiers.DWARF_BANE;
		} else if (entity instanceof WargEntity) {
			bane = ExtendedModifiers.WARG_BANE;
		} else if (entity instanceof ExtendedBarrowWightEntity) {
			bane = ExtendedModifiers.WIGHT_BANE;
		}
		return bane;
	}

	/**
	 * Applies a random modifier to the specified item.
	 */
	public static ItemStack generateItemModifiers(Random pRandom, ItemStack pStack, boolean reforging, boolean noNegatives) {
		List<ExtendedModifier> list = selectModifiers(pRandom, pStack, reforging, noNegatives);
		if(!list.isEmpty()) {
			if (pStack.getItem() instanceof ExtendedSmithScrollItem) {
				ExtendedSmithScrollItem.setModifier(pStack, list.get(0));
			} else {
				setModifiers(pStack, list);
			}
		}

		return pStack;
	}

	/**
	 * Create a list of random modifiers that can be added together to the ItemStack;
	 */
	public static List<ExtendedModifier> selectModifiers(Random pRandom, ItemStack pItemStack, boolean reforging, boolean noNegatives) {
		List<ExtendedModifier> newModifiers = Lists.newArrayList();
		Item item = pItemStack.getItem();
		if(item == ExtendedItems.MODIFIER_SCROLL.get()) {
			noNegatives = true;
		}
		if (pItemStack.getItemEnchantability() <= 0) {
			return newModifiers;
		} else {
			List<ExtendedModifier> applicableModifiers = getAvailableModifiersResults(reforging, pItemStack, noNegatives);
			if (!applicableModifiers.isEmpty()) {
				if(item == ExtendedItems.MODIFIER_SCROLL.get()) {
					newModifiers.add(WeightedRandom.getRandomItem(pRandom, applicableModifiers));
					return newModifiers;
				}
				int chance = pRandom.nextInt(100);
				if(chance < 60) {
					newModifiers.add(WeightedRandom.getRandomItem(pRandom, applicableModifiers));
					filterCompatibleModifiers(applicableModifiers, Util.lastOf(newModifiers));
					if(chance < 15 && !applicableModifiers.isEmpty()) {
						newModifiers.add(WeightedRandom.getRandomItem(pRandom, applicableModifiers));
						filterCompatibleModifiers(applicableModifiers, Util.lastOf(newModifiers));
						if(chance == 1 && !applicableModifiers.isEmpty()) {
							newModifiers.add(WeightedRandom.getRandomItem(pRandom, applicableModifiers));
						}
					}
				}else if(chance < 15) {
					newModifiers.add(WeightedRandom.getRandomItem(pRandom, applicableModifiers));
				}
			}
			return newModifiers;
		}
	}

	public static void filterCompatibleModifiers(List<ExtendedModifier> pDataList, ExtendedModifier pData) {
		Iterator<ExtendedModifier> iterator = pDataList.iterator();
		while(iterator.hasNext()) {
			if (!pData.canApplyToModifier((iterator.next()).getModifierType())) {
				iterator.remove();
			}
		}
	}

	public static List<ExtendedModifier> getAvailableModifiersResults(boolean reforging, ItemStack pStack, boolean noNegatives) {
		List<ExtendedModifier> list = Lists.newArrayList();
		boolean scroll = pStack.getItem() == ExtendedItems.MODIFIER_SCROLL.get();

		for(ExtendedModifier modifier : ExtendedModifiers.ALL) {
			if (modifier.weight>0 && (scroll ? modifier.isAllowedOnScrolls() : true)) {
				if(noNegatives && modifier.getModifierLevel()<0) {
					continue;
				}
				if(!reforging && modifier.getRarity().equals(ExtendedModifierRarity.SKILLFUL)) {
					continue;
				}
				list.add(modifier);
			}
		}
		return list;
	}
}
