package com.github.maximuslotro.lotrrextended.common.utils;

import lotr.common.entity.npc.*;
import lotr.common.init.ExtendedItems;
import lotr.common.item.ExtendedBarrowBlade;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.ItemAttributeModifierEvent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

import com.github.maximuslotro.lotrrextended.common.item.ExtendedModifierScrollItem;
import com.github.maximuslotro.lotrrextended.common.modifiers.ExtendedModifierRarity;
import com.github.maximuslotro.lotrrextended.common.modifiers.ExtendedModifierScroll;
import com.github.maximuslotro.lotrrextended.common.modifiers.ExtendedModifierType;
import com.github.maximuslotro.lotrrextended.common.modifiers.ExtendedModifiers;
import com.github.maximuslotro.lotrrextended.common.modifiers.ExtendedModifier;
import com.google.common.collect.Lists;

/**
 * Utility class for applying, storing, and managing {@link ExtendedModifier}s on
 * {@link ItemStack}s. Inspired by {@link EnchantmentHelper}, this class provides
 * methods to add, remove, clear, and track modifiers via custom NBT data.
 */
public class ExtendedModifierUtils {
	private static final UUID[] ARMOR_MODIFIER_UUID_PER_SLOT = new UUID[]{UUID.fromString("b9827b1d-b122-4d33-8c77-17f3c1474bbc"), UUID.fromString("998485c8-cae8-4bad-b0b6-c355b1a9f6d8"), UUID.fromString("41a79edd-697d-4cb2-8a3b-d3f827bbd514"), UUID.fromString("00ee8c07-a23c-4a0b-9cc5-780bb888d9ff")};
	protected static final UUID ATTACK_REACH_MODIFIER = UUID.fromString("889c253a-f9f9-4ce9-a9a1-5148a7e2b1a7");
	protected static final UUID ATTACK_SPEED_MODIFIER = UUID.fromString("17d1ae62-924b-4df4-bdcc-5dae72cb3ab8");
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
			stack.removeTagKey(NBTMODIFIER);
		}
	}
	/**
	 * Removes all {@link ExtendedModifier}s of a specific type from the given {@link ItemStack}.
	 * <p>
	 * This method first retrieves the current list of modifiers from the stack,
	 * removes all modifiers matching the specified {@link ExtendedModifierType},
	 * and then updates the stack with the filtered list.
	 * </p>
	 *
	 * @param pStack        the {@link ItemStack} to modify
	 * @param pModifierType the {@link ExtendedModifierType} to remove
	 */
	public static void removeModifierType(ItemStack pStack, ExtendedModifierType pModifierType) {
		List<ExtendedModifier> mergableModifiers = getModifierList(pStack);
		removeModifierType(mergableModifiers, pModifierType);
		setModifiers(pStack, mergableModifiers);
	}
	/**
	 * Removes all {@link ExtendedModifier}s of a specific type from the provided list.
	 * <p>
	 * Unlike {@link #removeModifierType(ItemStack, ExtendedModifierType)}, this method
	 * operates directly on a list without modifying an {@link ItemStack}'s NBT.
	 * </p>
	 *
	 * @param mergableModifiers the list of {@link ExtendedModifier}s to filter
	 * @param pModifierType     the {@link ExtendedModifierType} to remove
	 */
	public static void removeModifierType(List<ExtendedModifier> mergableModifiers, ExtendedModifierType pModifierType) {
		Iterator<ExtendedModifier> iterator = mergableModifiers.iterator();
		while(iterator.hasNext()) {
			if (pModifierType==iterator.next().getModifierType()) {
				iterator.remove();
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
		stack.removeTagKey(NBTMODIFIER);
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
		if (!getModifierByTypeSafe(stack, ExtendedModifierType.BANE).isUnknown()) {
			return;
		}
		// Filter for items banes can be applied to
		if (!ExtendedModifierType.BANE.isItemApplicable(stack)) {
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
			stack.removeTagKey("LOTRBaneProgress");
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
	 * WARNING, this method is unsafe for retrieving applied modifiers since it can contain non-applicable modifiers.
	 * Use {@link ExtendedModifierUtils#getModifierByTypeSafe} or {@link ExtendedModifierUtils#getModifierListByTypeSafe} as it actively checks for this.
	 * </p>
	 * @param stack the {@link ItemStack} whose modifiers should be retrieved
	 * @return a {@link List} of {@link ExtendedModifier} instances for the item;
	 *         the list will be empty if no modifiers are present
	 */
	public static List<ExtendedModifier> getModifierList(ItemStack stack) {
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
	/**
	 * Retrieves the first {@link ExtendedModifier} of a specific type from the given {@link ItemStack}.
	 * <p>
	 * If no modifier of the specified type is present, {@link ExtendedModifiers#UNKNOWN} is returned.
	 * </p>
	 * <p>
	 * If the returned modifier is not applicable to the given item, {@link ExtendedModifiers#UNKNOWN} is returned.
	 * </p>
	 *
	 * @param stack the {@link ItemStack} to inspect
	 * @param pType the {@link ExtendedModifierType} to search for
	 * @return the first matching {@link ExtendedModifier}, or UNKNOWN if none exist
	 */
	public static ExtendedModifier getModifierByTypeSafe(ItemStack stack, ExtendedModifierType pType) {
		ExtendedModifier modifierRet = getModifierList(stack).stream().filter((modifier)-> modifier.getModifierType()==pType).findFirst().orElse(ExtendedModifiers.UNKNOWN);
		if(!modifierRet.canApplyToItem(stack)) {
			return ExtendedModifiers.UNKNOWN;
		}
		return modifierRet;
	}
	/**
	 * Builds a map of {@link ExtendedModifierType} to {@link ExtendedModifier} from
	 * the given {@link ItemStack}.
	 * <p>
	 * Only one modifier per type is stored in the resulting map. If multiple modifiers
	 * of the same type exist, the last encountered is kept.
	 * </p>
	 * <p>
	 * If the returned modifier is not applicable to the given item, {@link ExtendedModifiers#UNKNOWN} is returned.
	 * </p>
	 *
	 * @param stack the {@link ItemStack} to inspect
	 * @return a {@link Map} associating each modifier type with its corresponding modifier
	 */
	public static Map<ExtendedModifierType, ExtendedModifier> getModifierListByTypeSafe(ItemStack stack) {
		List<ExtendedModifier> mergableModifiers = getModifierList(stack);
		Map<ExtendedModifierType, ExtendedModifier> map = new LinkedHashMap<>();
		for(ExtendedModifier modifier : mergableModifiers) {
			if(modifier.canApplyToItem(stack)) {
				map.put(modifier.getModifierType(), modifier);
			}
		}
		return map;
	}
	/**
	 * Synchronizes attribute modifiers for an {@link ItemStack} during the
	 * {@link ItemAttributeModifierEvent}.
	 * <p>
	 * This method ensures that modifiers such as melee reach and attack speed
	 * are applied or removed depending on the {@link ExtendedModifier}s attached
	 * to the item. It uses fixed UUIDs to prevent duplicate stacking and to allow
	 * proper replacement of modifiers when values change.
	 * </p>
	 *
	 * <p>Processing steps:</p>
	 * <ol>
	 *   <li>Verify the slot type.</li>
	 *   <li>Apply modifiers based on current {@link ExtendedModifierType}s present.</li>
	 * </ol>
	 *
	 * @param pEvent the {@link ItemAttributeModifierEvent} containing the {@link ItemStack} and attribute context
	 */
	public static void processItemAttributes(ItemAttributeModifierEvent pEvent) {
		if(pEvent.getSlotType()==EquipmentSlotType.MAINHAND) {
			Map<ExtendedModifierType, ExtendedModifier> modifiers = getModifierListByTypeSafe(pEvent.getItemStack());
			if(modifiers.containsKey(ExtendedModifierType.MELEE_REACH)) {
				pEvent.addModifier(ForgeMod.REACH_DISTANCE.get(), new AttributeModifier(ATTACK_REACH_MODIFIER, "Weapon modifier", modifiers.get(ExtendedModifierType.MELEE_REACH).getModifierTeir()>0 ? 0.25F : -0.25F, Operation.MULTIPLY_BASE));
			}
			if(modifiers.containsKey(ExtendedModifierType.MELEE_SPEED)) {
				pEvent.addModifier(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", modifiers.get(ExtendedModifierType.MELEE_SPEED).getModifierTeir()>0 ? 0.25F : -0.25F, Operation.MULTIPLY_BASE));
			}
		}
		if (pEvent.getItemStack().getItem() instanceof ArmorItem && ((ArmorItem) pEvent.getItemStack().getItem()).getSlot()==pEvent.getSlotType()) {
			Map<ExtendedModifierType, ExtendedModifier> modifiers = getModifierListByTypeSafe(pEvent.getItemStack());
			UUID uuid = ARMOR_MODIFIER_UUID_PER_SLOT[pEvent.getSlotType().getIndex()];
			if(modifiers.containsKey(ExtendedModifierType.TOUGHNESS)) {
				pEvent.addModifier(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, "Armor toughness", modifiers.get(ExtendedModifierType.TOUGHNESS).getModifierEffect(), Operation.ADDITION));
			}
			if(modifiers.containsKey(ExtendedModifierType.PROTECTION)) {
				pEvent.addModifier(Attributes.ARMOR, new AttributeModifier(uuid, "Armor modifier", modifiers.get(ExtendedModifierType.PROTECTION).getModifierEffect(), Operation.ADDITION));
			}
		}
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
		if(reforging) {
			List<ExtendedModifier> existingNonRemovable = getModifierList(pStack).stream().filter(m -> !m.getRarity().countsTowardsLimit()).collect(Collectors.toList());
			list.addAll(existingNonRemovable);
		}
		if(pStack.getItem() instanceof ExtendedBarrowBlade && !list.contains(ExtendedModifiers.WIGHT_BANE)) {
			removeModifierType(list, ExtendedModifierType.BANE);
			list.add(ExtendedModifiers.WIGHT_BANE);
		}
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
			if(chance < 60) {
				newModifiers.add(WeightedRandom.getRandomItem(pRandom, applicableModifiers));
				filterCompatibleModifiers(applicableModifiers, Util.lastOf(newModifiers));
				if(chance < 15 && !applicableModifiers.isEmpty()) {
					newModifiers.add(WeightedRandom.getRandomItem(pRandom, applicableModifiers));
					filterCompatibleModifiers(applicableModifiers, Util.lastOf(newModifiers));
					if(pRandom.nextInt(100) == 0 && !applicableModifiers.isEmpty()) {
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
			if (!pData.isCompatableModifier((iterator.next()).getModifierType())) {
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
	 * Maps a vanilla {@link Enchantment} to a corresponding {@link ExtendedModifierType}.
	 * <p>
	 * If the enchantment is not explicitly mapped, {@link ExtendedModifierType#UNKNOWN} is returned.
	 * </p>
	 *
	 * @param enchantment the vanilla enchantment to map
	 * @param shouldFloat if true, allows returning float-based modifiers for tools
	 * @param testCase Used to validate combined enchantments
	 * @return the corresponding {@link ExtendedModifierType}, or UNKNOWN if unmapped
	 */
	public static ExtendedModifierType vanillaEnchantToModifier(Enchantment enchantment, boolean shouldFloat, ItemStack testCase) {
		if (enchantment == Enchantments.MOB_LOOTING || enchantment == Enchantments.BLOCK_FORTUNE) {
			return ExtendedModifierType.LOOTING;
		} else if (enchantment == Enchantments.KNOCKBACK) {
			return ExtendedModifierType.MEELE_KNOCKBACK;
		} else if (enchantment == Enchantments.FLAMING_ARROWS) {
			return ExtendedModifierType.INFERNAL;
		} else if (enchantment == Enchantments.FIRE_ASPECT) {
			if(!testCase.isEmpty() && !(testCase.getItem() instanceof SwordItem)) {
				return ExtendedModifierType.UNKNOWN;
			}
			return ExtendedModifierType.INFERNAL;
		} else if (enchantment == Enchantments.SILK_TOUCH) {
			return ExtendedModifierType.TOOL_SILK;
		} else if (enchantment == Enchantments.ALL_DAMAGE_PROTECTION) {
			return ExtendedModifierType.PROTECTION;
		} else if (enchantment == Enchantments.FIRE_PROTECTION) {
			return ExtendedModifierType.PROTECTION_FIRE;
		} else if (enchantment == Enchantments.PROJECTILE_PROTECTION) {
			return ExtendedModifierType.PROTECTION_RANGED;
		} else if (enchantment == Enchantments.FALL_PROTECTION) {
			return ExtendedModifierType.PROTECTION_FALLING;
		} else if (enchantment == Enchantments.BLAST_PROTECTION) {
			return ExtendedModifierType.PROTECTION_BLAST;
		} else if (enchantment == Enchantments.PUNCH_ARROWS) {
			return ExtendedModifierType.RANGED_KNOCKBACK;
		} else if (enchantment == Enchantments.BLOCK_EFFICIENCY && shouldFloat) {
			return ExtendedModifierType.TOOL_SPEED;
		} else if (enchantment == Enchantments.POWER_ARROWS && shouldFloat) {
			return ExtendedModifierType.RANGED_SPEED;
		} else if (enchantment == Enchantments.FISHING_LUCK) {
			return ExtendedModifierType.FISHING_LUCK;
		} else if (enchantment == Enchantments.FISHING_SPEED) {
			return ExtendedModifierType.FISHING_SPEED;
		} else if (enchantment == Enchantments.DEPTH_STRIDER) {
			return ExtendedModifierType.UNDERWATER_MOVEMENT;
		} else if (enchantment == Enchantments.AQUA_AFFINITY) {
			return ExtendedModifierType.UNDERWATER_BREATHING;
		} else if (enchantment == Enchantments.RESPIRATION) {
			return ExtendedModifierType.UNDERWATER_BREATHING;
		} else if (enchantment == Enchantments.QUICK_CHARGE) {
			return ExtendedModifierType.CHARGE_SPEED;
		} else {
			// Not mapped or blocked → return UNKNOWN
			return ExtendedModifierType.UNKNOWN;
		}
	}
	/**
	 * Retrieves the level of a given enchantment on an item stack as a float.
	 * <p>
	 * Unlike vanilla {@link EnchantmentHelper#getItemEnchantmentLevel}, this method
	 * returns a float and adds any effects from {@link ExtendedModifierType}.
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
			ExtendedModifier modifier = ExtendedModifierUtils.getModifierByTypeSafe(pStack, ExtendedModifierUtils.vanillaEnchantToModifier(pEnchantment, true, pStack));
			if(!modifier.isUnknown()) {
				output += modifier.getModifierEffect();
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
}
