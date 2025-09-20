package com.github.maximuslotro.lotrrextended.common.utils;

import lotr.common.entity.npc.*;
import lotr.common.init.ExtendedItems;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedModifierScrollItem;
import com.github.maximuslotro.lotrrextended.common.modifiers.ExtendedModifierRarity;
import com.github.maximuslotro.lotrrextended.common.modifiers.ExtendedModifierScroll;
import com.github.maximuslotro.lotrrextended.common.modifiers.ExtendedModifierTypes;
import com.github.maximuslotro.lotrrextended.common.modifiers.ExtendedModifiers;
import com.github.maximuslotro.lotrrextended.common.modifiers.ExtendedModifier;
import com.google.common.collect.Lists;

/**
 * Utility class for applying, storing, and managing {@link ExtendedModifier}s on
 * {@link ItemStack}s. Inspired by {@link EnchantmentHelper}, this class provides
 * methods to add, remove, clear, and track modifiers via custom NBT data.
 */
public class ExtendedModifierUtils {
	public static final String NBTMODIFIER = "REModifiers";
	/**
     * Adds a single {@link ExtendedModifier} to the given {@link ItemStack}.
     * <p>
     * If the item does not already have a modifier list, one is created and
     * stored under the {@code NBTMODIFIER} tag.
     * </p>
     *
     * @param stack    the {@link ItemStack} to modify
     * @param modifier the {@link ExtendedModifier} to add
     */
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
	/**
     * Replaces all existing modifiers on the given {@link ItemStack} with the provided list.
     * <p>
     * Existing modifiers are cleared before adding the new ones. If the list is empty,
     * no changes will be made to the item.
     * </p>
     *
     * @param stack     the {@link ItemStack} to update
     * @param modifiers the list of {@link ExtendedModifier}s to set
     */
	public static void setModifiers(ItemStack stack, List<ExtendedModifier> modifiers) {
		clearModifiers(stack);
		ListNBT listnbt = new ListNBT();
		for(ExtendedModifier modifier : modifiers) {
			CompoundNBT compoundnbt = new CompoundNBT();
			compoundnbt.putString("key", modifier.getNbtKey());
			listnbt.add(compoundnbt);
		}
		if(!listnbt.isEmpty()) {
			stack.getOrCreateTag().put(NBTMODIFIER, listnbt);
		}
	}
	/**
     * Removes a specific {@link ExtendedModifier} from the given {@link ItemStack}.
     * <p>
     * If the modifier is not present, the method does nothing. When the last modifier
     * is removed, the {@code NBTMODIFIER} tag is also removed, and if the tag
     * becomes empty, the entire NBT tag is cleared from the stack.
     * </p>
     *
     * @param stack    the {@link ItemStack} to modify
     * @param modifier the {@link ExtendedModifier} to remove
     */
	public static void removeModifier(ItemStack stack, ExtendedModifier modifier) {
		if (!getModifierList(stack).contains(modifier)) {
			return;
		}
		ListNBT listnbt = stack.getTag().getList(NBTMODIFIER, 10); //Null tag already validated by getModifierList
		List<CompoundNBT> entriesToRemove = new ArrayList<>();
		for (INBT nbt : listnbt) {
			CompoundNBT entry = (CompoundNBT) nbt;
			if (entry.getString("key").equals(modifier.getNbtKey())) {
				entriesToRemove.add(entry);
			}
		}
		listnbt.removeAll(entriesToRemove);
		if(listnbt.isEmpty()) {
			stack.getTag().remove(NBTMODIFIER);
			if(stack.getTag().isEmpty()) {
				stack.setTag(null);
			}
		}
	}
	/**
     * Clears all {@link ExtendedModifier}s from the given {@link ItemStack}.
     * <p>
     * This removes the {@code NBTMODIFIER} tag from the item, and if
     * the NBT data becomes empty, the tag is fully cleared.
     * </p>
     *
     * @param stack the {@link ItemStack} to clear
     * @return {@code true} if one or more modifiers were cleared,
     *         {@code false} if the item had no modifiers
     */
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
	/**
     * Progresses the "bane" achievement mechanic for the given {@link ItemStack} when it kills an entity.
     * <p>
     * - If the item already has a bane, nothing happens. <br>
     * - If no applicable bane exists for the entity, nothing happens. <br>
     * - Otherwise, the kill count is tracked in the {@code "LOTRBaneProgress"} tag until it reaches 100,
     *   at which point the bane modifier is awarded to the item, players are notified via chat,
     *   and the progress data is cleared.
     * </p>
     *
     * @param stack  the {@link ItemStack} that inflicted the kill
     * @param entity the killed {@link Entity}
     * @param player the {@link PlayerEntity} responsible for the kill (used for chat notifications)
     */
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
	/**
	 * Retrieves a formatted string representing the bane progress data
	 * stored in the given {@link ItemStack}'s NBT.
	 * <p>
	 * Each entry in the "LOTRBaneProgress" tag includes a "key" (the bane type)
	 * and the number of "kills" associated with it. Entries are concatenated into
	 * a string of the form:
	 * <pre>
	 * key1: kills1, key2: kills2, ...
	 * </pre>
	 * If no valid bane progress data exists, an empty string is returned.
	 * </p>
	 *
	 * @param stack the {@link ItemStack} containing potential bane progress data
	 * @return a formatted string of bane progress entries, or an empty string if none exist
	 */
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
	/**
	 * Determines if the given list of modifiers contains at least one
	 * bane-type modifier.
	 *
	 * @param modifiers the list of {@link ExtendedModifier} objects to search
	 * @return {@code true} if a bane modifier is present, {@code false} otherwise
	 */
	public static boolean containsBane(List<ExtendedModifier> modifiers) {
		return modifiers.stream().anyMatch((modifier)-> modifier.getModifierType()==ExtendedModifierTypes.BANE);
	}
	/**
	 * Checks whether the given {@link ItemStack} has any modifiers stored
	 * in its NBT data under the {@code NBTMODIFIER} tag.
	 *
	 * @param stack the {@link ItemStack} being evaluated
	 * @return {@code true} if the item has one or more stored modifiers,
	 *         {@code false} otherwise
	 */
	public static boolean hasModifiers(ItemStack stack) {
		if (stack.hasTag() && stack.getTag().contains(NBTMODIFIER, 9)) {
			return !stack.getTag().getList(NBTMODIFIER, 10).isEmpty();
		} else {
			return false;
		}
	}
	/**
     * Checks whether the given {@link ItemStack} has been tagged as having random modifiers applied.
     * <p>
     * This is determined by inspecting the {@code "ERModAdded"} boolean tag stored in the
     * {@link CompoundNBT} of the stack. If the tag exists and is set to
     * {@code true}, the item has had modifiers added using
     * {@link #generateItemModifiersTagged(Random, ItemStack, boolean, boolean)}.
     * </p>
     *
     * @param stack the {@link ItemStack} to check
     * @return {@code true} if the item has the {@code "ERModAdded"} tag set to {@code true}, otherwise {@code false}
     * @see #addModifierAddedTag(ItemStack)
     * @see #generateItemModifiersTagged(Random, ItemStack, boolean, boolean)
     */
	public static boolean hasRandomModifiers(ItemStack stack) {
		if (stack.hasTag() && stack.getTag().contains("ERModAdded")) {
			return stack.getTag().getBoolean("ERModAdded");
		} else {
			return false;
		}
	}
	/**
     * Adds or updates the {@code "ERModAdded"} tag on the given {@link ItemStack}, marking
     * it as having random modifiers applied.
     * <p>
     * This method does not apply modifiers by itself — it simply ensures that the item
     * can be recognized as modified by {@link #hasRandomModifiers(ItemStack)}.
     * </p>
     *
     * @param stack the {@link ItemStack} to tag
     * @see #hasRandomModifiers(ItemStack)
     * @see #generateItemModifiersTagged(Random, ItemStack, boolean, boolean)
     */
	public static void addModifierAddedTag(ItemStack stack) {
		stack.getOrCreateTag().putBoolean("ERModAdded", true);
	}
	/**
	 * Retrieves all modifiers associated with the given {@link ItemStack}.
	 * <p>
	 * Behavior depends on item type:
	 * <ul>
	 *   <li>If the item is a {@link ExtendedModifierScrollItem}, only its single stored
	 *       modifier is returned (if known).</li>
	 *   <li>Otherwise, all modifiers stored in the item's NBT list under
	 *       {@code NBTMODIFIER} are returned.</li>
	 * </ul>
	 * </p>
	 *
	 * @param stack the {@link ItemStack} whose modifiers should be retrieved
	 * @return a {@link List} of {@link ExtendedModifier} instances for the item;
	 *         the list will be empty if no modifiers are present
	 */
	public static List<ExtendedModifier> getModifierList(ItemStack stack) {
		if(stack.getItem() == ExtendedItems.MODIFIER_SCROLL.get()){
			List<ExtendedModifier> result = new ArrayList<>();
			if(!ExtendedModifierScrollItem.getModifier(stack).isUnknown()) {
				result.add(ExtendedModifierScrollItem.getModifier(stack));
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

	public static Map<ExtendedModifierTypes, ExtendedModifier> getModifierListByType(ItemStack stack) {
		List<ExtendedModifier> mergableModifiers = getModifierList(stack);
		Map<ExtendedModifierTypes, ExtendedModifier> map = new LinkedHashMap<>();
		for(ExtendedModifier modifier : mergableModifiers) {
			map.put(modifier.getModifierType(), modifier);
		}
		return map;
	}

	/**
	 * Checks whether the specified {@link ExtendedModifier} is effective as a "bane"
	 * against the given {@link Entity}.
	 * <p>
	 * This method determines the entity's corresponding bane modifier via
	 * {@link #getBaneByEntity(Entity)} and compares it to the provided modifier.
	 * </p>
	 *
	 * @param modifier the {@link ExtendedModifier} to test for effectiveness
	 * @param entity   the target {@link Entity} being evaluated
	 * @return {@code true} if the modifier is the correct bane for the entity type,
	 *         {@code false} otherwise
	 */
	public static boolean isBaneEffectiveAgainst(ExtendedModifier modifier, Entity entity) {
		return getBaneByEntity(entity) == modifier;
	}
	/**
	 * Determines the specific bane {@link ExtendedModifier} that corresponds
	 * to the given {@link Entity}'s type.
	 * <p>
	 * Each supported entity type maps to a unique bane modifier, for example:
	 * <ul>
	 *   <li>{@link OrcEntity} → {@link ExtendedModifiers#ORC_BANE}</li>
	 *   <li>{@link ElfEntity} → {@link ExtendedModifiers#ELF_BANE}</li>
	 *   <li>{@link DwarfEntity} → {@link ExtendedModifiers#DWARF_BANE}</li>
	 *   <li>{@link WargEntity} → {@link ExtendedModifiers#WARG_BANE}</li>
	 *   <li>{@link ExtendedBarrowWightEntity} → {@link ExtendedModifiers#WIGHT_BANE}</li>
	 * </ul>
	 * If the entity does not match any supported type, {@code null} is returned.
	 * </p>
	 *
	 * @param entity the {@link Entity} whose corresponding bane modifier should be retrieved
	 * @return the matching {@link ExtendedModifier}, or {@code null} if no bane applies
	 */
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
     * Generates and applies one or more modifiers to the given {@link ItemStack}, then
     * marks the item with a persistent tag indicating that modifiers have been added.
     * <p>
     * This method behaves like {@link #generateItemModifiers(Random, ItemStack, boolean, boolean)},
     * but additionally sets the {@code "ERModAdded"} tag on the {@link ItemStack}. This tag can be
     * checked using {@link #hasRandomModifiers(ItemStack)} to determine whether the item has been
     * processed by the modifier system.
     * </p>
     *
     * @param pRandom      the {@link Random} instance used to generate modifier selection
     * @param pStack       the {@link ItemStack} to which modifiers will be applied
     * @param reforging    whether the item is being reforged (affects which modifiers are eligible)
     * @param noNegatives  if true, negative modifiers are excluded from the selection pool
     * @return the modified {@link ItemStack}, tagged as having modifiers added
     * @see #generateItemModifiers(Random, ItemStack, boolean, boolean)
     * @see #hasRandomModifiers(ItemStack)
     * @see #addModifierAddedTag(ItemStack)
     */
	public static ItemStack generateItemModifiersTagged(Random pRandom, ItemStack pStack, boolean reforging, boolean noNegatives) {
		generateItemModifiers(pRandom, pStack, reforging, noNegatives);
		addModifierAddedTag(pStack);
		return pStack;
	}

	/**
	 * Applies one or more randomly selected modifiers to the given {@link ItemStack}.
	 * <p>
	 * If the item is an {@link ExtendedModifierScrollItem}, only a single modifier can be
	 * applied. Otherwise, multiple compatible modifiers may be added depending on chance.
	 * </p>
	 *
	 * @param pRandom      the {@link Random} instance used to generate modifier selection
	 * @param pStack       the {@link ItemStack} to which modifiers will be applied
	 * @param reforging    whether the item is being reforged (affects which modifiers are eligible)
	 * @param noNegatives  if true, negative modifiers are excluded from the selection pool
	 * @return the modified {@link ItemStack}, possibly with one or more modifiers applied
	 */
	public static ItemStack generateItemModifiers(Random pRandom, ItemStack pStack, boolean reforging, boolean noNegatives) {
		List<ExtendedModifier> list = selectModifiers(pRandom, pStack, reforging, noNegatives);
		if (pStack.getItem() instanceof ExtendedModifierScrollItem) {
			if(!list.isEmpty()) {
				ExtendedModifierScrollItem.setModifier(pStack, list.get(0));
			}
		} else {
			setModifiers(pStack, list);
		}
		return pStack;
	}

	/**
	 * Selects a list of modifiers that can be applied to the specified {@link ItemStack}.
	 * <p>
	 * The number and type of modifiers depends on random chance and item type:
	 * <ul>
	 *   <li>If the item is a modifier scroll, only one positive modifier will be selected.</li>
	 *   <li>Otherwise, up to three modifiers may be chosen depending on probability thresholds.</li>
	 *   <li>Selected modifiers are filtered to ensure compatibility with each other.</li>
	 * </ul>
	 * </p>
	 *
	 * @param pRandom      the {@link Random} instance used to pick modifiers
	 * @param pItemStack   the {@link ItemStack} being modified
	 * @param reforging    whether the item is being reforged (affects which modifiers are eligible)
	 * @param noNegatives  if true, excludes all negative modifiers from selection
	 * @return a {@link List} of {@link ExtendedModifier} instances that can be applied to the item
	 */
	public static List<ExtendedModifier> selectModifiers(Random pRandom, ItemStack pItemStack, boolean reforging, boolean noNegatives) {
		List<ExtendedModifier> newModifiers = Lists.newArrayList();
		boolean isScroll = pItemStack.getItem() == ExtendedItems.MODIFIER_SCROLL.get();
		if(isScroll) {
			noNegatives = true;
		}
		List<ExtendedModifier> applicableModifiers = getAvailableModifiersResults(reforging, pItemStack, noNegatives, isScroll);
		if (!applicableModifiers.isEmpty()) {
			if(isScroll) {
				newModifiers.add(WeightedRandom.getRandomItem(pRandom, applicableModifiers));
				return newModifiers;
			}
			int chance = pRandom.nextInt(100);
			ExtendedLog.info("chance: "+chance);
			if(chance < 70) {
				newModifiers.add(WeightedRandom.getRandomItem(pRandom, applicableModifiers));
				filterCompatibleModifiers(applicableModifiers, Util.lastOf(newModifiers));
				if(chance < 15 && !applicableModifiers.isEmpty()) {
					newModifiers.add(WeightedRandom.getRandomItem(pRandom, applicableModifiers));
					filterCompatibleModifiers(applicableModifiers, Util.lastOf(newModifiers));
					if(chance == 0 && !applicableModifiers.isEmpty()) {
						newModifiers.add(WeightedRandom.getRandomItem(pRandom, applicableModifiers));
					}
				}
			}else if(chance < 15) {
				newModifiers.add(WeightedRandom.getRandomItem(pRandom, applicableModifiers));
			}
		}
		return newModifiers;
	}
	/**
	 * Removes modifiers from the given list that are not compatible with the specified modifier.
	 * <p>
	 * Each modifier defines which other types it can be combined with. Any incompatible
	 * modifiers will be removed from the list to ensure only valid combinations remain.
	 * </p>
	 *
	 * @param pDataList the list of potential {@link ExtendedModifier} candidates
	 * @param pData     the {@link ExtendedModifier} already chosen, used to filter incompatibilities
	 */
	public static void filterCompatibleModifiers(List<ExtendedModifier> pDataList, ExtendedModifier pData) {
		Iterator<ExtendedModifier> iterator = pDataList.iterator();
		while(iterator.hasNext()) {
			if (!pData.canApplyToModifier((iterator.next()).getModifierType())) {
				iterator.remove();
			}
		}
	}
	/**
	 * Builds a pool of all available modifiers that may be applied to an item,
	 * taking into account reforging rules, item type, and whether negative
	 * modifiers are permitted.
	 * <p>
	 * This method checks modifier weight, rarity, and scroll compatibility
	 * before including them in the returned list.
	 * </p>
	 *
	 * @param reforging    whether the item is being reforged (excludes certain rarities if false)
	 * @param pStack       the {@link ItemStack} being evaluated
	 * @param noNegatives  if true, excludes modifiers with negative levels
	 * @param isScroll     whether the item is a modifier scroll (restricts allowed modifiers)
	 * @return a {@link List} of {@link ExtendedModifier} objects eligible for selection
	 */
	public static List<ExtendedModifier> getAvailableModifiersResults(boolean reforging, ItemStack pStack, boolean noNegatives, boolean isScroll) {
		List<ExtendedModifier> list = Lists.newArrayList();

		for(ExtendedModifier modifier : ExtendedModifiers.ALL) {
			// Bypass non-random modifiers and additionally ones that can't be applied to scrolls if using scrolls.
			if (modifier.weight>0 && (isScroll ? modifier.isAllowedOnScrolls() : true)) {
				if(noNegatives && modifier.getModifierTeir()<1) {
					continue;
				}
				if(!reforging && modifier.getRarity().equals(ExtendedModifierRarity.SKILLFUL)) {
					continue;
				}
				if(!modifier.canApplyToItem(pStack) && !(pStack.getItem()==ExtendedItems.MODIFIER_SCROLL.get() && modifier.getScroll()!=ExtendedModifierScroll.NOSCROLL)) {
					continue;
				}
				list.add(modifier);
			}
		}
		return list;
	}
	/**
	 * Maps a vanilla {@link Enchantment} to a corresponding {@link ExtendedModifierTypes}.
	 * <p>
	 * If the enchantment is not explicitly mapped, {@link ExtendedModifierTypes#UNKNOWN} is returned.
	 * </p>
	 *
	 * @param enchantment the vanilla enchantment to map
	 * @param shouldFloat if true, allows returning float-based modifiers for tools
	 * @return the corresponding {@link ExtendedModifierTypes}, or UNKNOWN if unmapped
	 */
	public static ExtendedModifierTypes vanillaEnchantToModifier(Enchantment enchantment, boolean shouldFloat) {
		if (enchantment == Enchantments.MOB_LOOTING || enchantment == Enchantments.BLOCK_FORTUNE) {
			return ExtendedModifierTypes.LOOTING;
		} else if (enchantment == Enchantments.KNOCKBACK) {
			return ExtendedModifierTypes.MEELE_KNOCKBACK;
		} else if (enchantment == Enchantments.FIRE_ASPECT || enchantment == Enchantments.FLAMING_ARROWS) {
			return ExtendedModifierTypes.INFERNAL;
		} else if (enchantment == Enchantments.SILK_TOUCH) {
			return ExtendedModifierTypes.TOOL_SILK;
		} else if (enchantment == Enchantments.ALL_DAMAGE_PROTECTION) {
			return ExtendedModifierTypes.PROTECTION;
		} else if (enchantment == Enchantments.FIRE_PROTECTION) {
			return ExtendedModifierTypes.PROTECTION_FIRE;
		} else if (enchantment == Enchantments.PROJECTILE_PROTECTION) {
			return ExtendedModifierTypes.PROTECTION_RANGED;
		} else if (enchantment == Enchantments.FALL_PROTECTION) {
			return ExtendedModifierTypes.PROTECTION_FALLING;
		} else if (enchantment == Enchantments.BLAST_PROTECTION) {
			return ExtendedModifierTypes.PROTECTION_BLAST;
		} else if (enchantment == Enchantments.PUNCH_ARROWS) {
			return ExtendedModifierTypes.RANGED_KNOCKBACK;
		} else if (enchantment == Enchantments.BLOCK_EFFICIENCY && shouldFloat) {
			return ExtendedModifierTypes.TOOL_SPEED;
		} else if (enchantment == Enchantments.POWER_ARROWS && shouldFloat) {
			return ExtendedModifierTypes.RANGED_SPEED;
		} else {
			// Not mapped or blocked → return UNKNOWN
			return ExtendedModifierTypes.UNKNOWN;
		}
	}
	/**
	 * Retrieves the level of a given enchantment on an item stack as a float.
	 * <p>
	 * Unlike vanilla {@link EnchantmentHelper#getItemEnchantmentLevel}, this method
	 * returns a float and adds any effects from {@link ExtendedModifierTypes}.
	 * </p>
	 *
	 * @param pEnchantment the enchantment to check
	 * @param pStack       the item stack
	 * @return the enchantment level as a float, including extended modifier effects
	 */
	public static float getItemEnchantmentLevelFloat(Enchantment pEnchantment, ItemStack pStack) {
		if (pStack.isEmpty()) {
			return 0.0f;
		} else {
			ResourceLocation resourcelocation = Registry.ENCHANTMENT.getKey(pEnchantment);
			ListNBT listnbt = pStack.getEnchantmentTags();

			float output = 0.0f;
			for(int i = 0; i < listnbt.size(); ++i) {
				CompoundNBT compoundnbt = listnbt.getCompound(i);
				ResourceLocation resourcelocation1 = ResourceLocation.tryParse(compoundnbt.getString("id"));
				if (resourcelocation1 != null && resourcelocation1.equals(resourcelocation)) {
					output = MathHelper.clamp(compoundnbt.getInt("lvl"), 0, 255);
					break;
				}
			}
			ExtendedModifierTypes modifierType = ExtendedModifierUtils.vanillaEnchantToModifier(pEnchantment, true);
			if(modifierType != ExtendedModifierTypes.UNKNOWN) {
				Map<ExtendedModifierTypes, ExtendedModifier> modifiers = ExtendedModifierUtils.getModifierListByType(pStack);
				if(modifiers.containsKey(modifierType)) {
					output += modifiers.get(modifierType).getModifierEffect();
				}
			}
			return output;
		}
	}
	/**
	 * Retrieves the highest float-level of a given enchantment across all items equipped by an entity.
	 * <p>
	 * Works similarly to {@link EnchantmentHelper#getEnchantmentLevel}, but returns a float
	 * and includes extended modifier effects.
	 * </p>
	 *
	 * @param pEnchantment the enchantment to check
	 * @param pEntity      the living entity
	 * @return the highest enchantment level as a float across all equipped items
	 */
	public static float getEnchantmentLevelFloat(Enchantment pEnchantment, LivingEntity pEntity) {
		Iterable<ItemStack> iterable = pEnchantment.getSlotItems(pEntity).values();
		if (iterable == null) {
			return 0.0f;
		} else {
			float finalLevel = 0.0f;

			for(ItemStack itemstack : iterable) {
				float localLevel = getItemEnchantmentLevelFloat(pEnchantment, itemstack);
				if (localLevel > finalLevel) {
					finalLevel = localLevel;
				}
			}

			return finalLevel;
		}
	}
	/**
	 * Convenience method to get the effective block efficiency for a living entity,
	 * based on the BLOCK_EFFICIENCY enchantment and extended modifiers.
	 *
	 * @param pEntity the entity to check
	 * @return the effective block-breaking efficiency as a float
	 */
	public static float getBlockEfficiencyFloat(LivingEntity pEntity) {
		return getEnchantmentLevelFloat(Enchantments.BLOCK_EFFICIENCY, pEntity);
	}
}
