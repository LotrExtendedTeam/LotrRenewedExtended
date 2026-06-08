package com.github.maximuslotro.lotrrextended.common.inventory.container;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.common.advancements.ExtendedCriteriaTriggers;
import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedContainerTypes;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedBowItem;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedBrandingIronItem;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedChiselItem;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedCrossbowItem;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedGemstoneColoringItem;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedModifierScrollItem;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedThrowableAxeItem;
import com.github.maximuslotro.lotrrextended.common.modifiers.ExtendedModifier;
import com.github.maximuslotro.lotrrextended.common.modifiers.ExtendedModifiers;
import com.github.maximuslotro.lotrrextended.common.utils.ExtendedModifierUtils;

import io.netty.buffer.Unpooled;
import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRItems;
import lotr.common.item.DaggerItem;
import lotr.common.item.ItemOwnership;
import lotr.common.item.SmokingPipeItem;
import lotr.common.item.SpearItem;
import net.minecraft.block.AnvilBlock;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.inventory.container.RepairContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.FlintAndSteelItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShearsItem;
import net.minecraft.item.ShieldItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolItem;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntReferenceHolder;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.Tags;

/**
 * Based off of {@link RepairContainer}
 * */
public class ExtendedRepairContainerNew extends Container {
	public static final ResourceLocation EMPTY_SLOT_INGOT = new ResourceLocation(LotrExtendedMod.MODID, "item/empty_slot_ingot");
	public static final ITextComponent CONTAINER_TITLE = new TranslationTextComponent("container.lotrextended.repair");
	protected final CraftResultInventory resultSlots = new CraftResultInventory();
	protected final IInventory inputSlots = new Inventory(3) {
		/**
		 * For tile entities, ensures the chunk containing the tile entity is saved to disk later - the game won't think
		 * it hasn't changed and skip it.
		 */
		@Override
		public void setChanged() {
			super.setChanged();
			ExtendedRepairContainerNew.this.slotsChanged(this);
		}
	};
	protected final IWorldPosCallable access;
	protected final PlayerEntity player;
	private String itemName;
	// All are recalculated on slot changes IntReferenceHolder
	// Sync calculated costs
	private final IntReferenceHolder cost = IntReferenceHolder.standalone();
	private final IntReferenceHolder reforgeCost = IntReferenceHolder.standalone();
	private final IntReferenceHolder engraveCost = IntReferenceHolder.standalone();
	// Sync error types, 0 no error, 1+ differing errors
	private final IntReferenceHolder incompatible = IntReferenceHolder.standalone();


	public ExtendedRepairContainerNew(int pContainerId, PlayerInventory pPlayerInventory) {
		this(pContainerId, pPlayerInventory, IWorldPosCallable.NULL);
	}

	public ExtendedRepairContainerNew(int pContainerId, PlayerInventory pPlayerInventory, PacketBuffer extraData) {
		this(pContainerId, pPlayerInventory, IWorldPosCallable.NULL, extraData);
	}

	public ExtendedRepairContainerNew(int pContainerId, PlayerInventory pPlayerInventory, IWorldPosCallable pAccess) {
		this(pContainerId, pPlayerInventory, pAccess, new PacketBuffer(Unpooled.buffer()));
	}

	public ExtendedRepairContainerNew(int pContainerId, PlayerInventory pPlayerInventory, IWorldPosCallable pAccess, PacketBuffer extraData) {
		super(ExtendedContainerTypes.EXTENDED_ANVIL.get(), pContainerId);
		this.access = pAccess;
		this.player = pPlayerInventory.player;
		this.addDataSlot(this.cost);
		this.addDataSlot(this.incompatible);
		this.addDataSlot(this.reforgeCost);
		this.addDataSlot(this.engraveCost);
		this.addSlot(new Slot(this.inputSlots, 0, 27, 58));
		this.addSlot(new Slot(this.inputSlots, 1, 76, 47));
		this.addSlot(new Slot(this.inputSlots, 2, 76, 70) {
			{
				setBackground(PlayerContainer.BLOCK_ATLAS, EMPTY_SLOT_INGOT);
			}
		});
		// Inventory only has space of 1, but we are giving index of 3 because craft inventory does not care about slot index
		this.addSlot(new Slot(this.resultSlots, 3, 134, 58) {
			/**
			 * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
			 */
			@Override
			public boolean mayPlace(ItemStack pStack) {
				return false;
			}

			/**
			 * Return whether this slot's stack can be taken from this slot.
			 */
			@Override
			public boolean mayPickup(PlayerEntity pPlayer) {
				return ExtendedRepairContainerNew.this.mayPickup(pPlayer, this.hasItem());
			}

			@Override
			public ItemStack onTake(PlayerEntity pPlayer, ItemStack pStack) {
				return ExtendedRepairContainerNew.this.onTake(pPlayer, pStack);
			}
		});

		for(int i = 0; i < 3; ++i) {
			for(int j = 0; j < 9; ++j) {
				this.addSlot(new Slot(pPlayerInventory, j + i * 9 + 9, 8 + j * 18, 116 + i * 18));
			}
		}

		for(int k = 0; k < 9; ++k) {
			this.addSlot(new Slot(pPlayerInventory, k, 8 + k * 18, 174));
		}

	}

	protected boolean mayPickup(PlayerEntity pPlayer, boolean pHasStack) {
		if(!resultSlots.getItem(0).isEmpty()) {
			if(this.cost.get()==0) {
				return true;
			}
			ItemStack materialItem = inputSlots.getItem(2);
			if(isValidRepairMaterial(inputSlots.getItem(0), materialItem) && materialItem.getCount() >= this.cost.get()) {
				return true;
			}
		}
		return false;
	}

	protected ItemStack onTake(PlayerEntity pPlayer, ItemStack pInputItem) {
		// Cache the cost here because as soon as setItem is called, the cost will be reset
		int localCost = this.cost.get();
		this.inputSlots.setItem(0, ItemStack.EMPTY);
		ItemStack merged = this.inputSlots.getItem(1);
		// If item exists in merging slot, always decrement since logic will 
		//fail output if merging slot isn't valid
		if (!merged.isEmpty()) {
			// Only decrement by one, since never more than one item from merge slot is ever needed
			merged.shrink(1);
			this.inputSlots.setItem(1, merged);
		} else {
			this.inputSlots.setItem(1, ItemStack.EMPTY);
		}
		// Only decrement material slot if there is a recipe cost
		if (localCost > 0) {
			ItemStack material = this.inputSlots.getItem(2);
			if (!material.isEmpty()) {
				// Shrink material count by recipe cost
				material.shrink(localCost);
				if (!material.isEmpty()) {
					this.inputSlots.setItem(2, material);
				} else {
					this.inputSlots.setItem(2, ItemStack.EMPTY);
				}
			}
		}
		this.cost.set(0);
		hurtOrDamageAnvilSound();
		return pInputItem;
	}

	@Override
	public void slotsChanged(IInventory pInventory) {
		super.slotsChanged(pInventory);
		if (pInventory == this.inputSlots) {
			this.createResult();
		}
	}

	protected boolean isValidBlock(BlockState pState) {
		return pState.is(BlockTags.ANVIL);
	}

	/**
	 * Called when the container is closed.
	 */
	@Override
	public void removed(PlayerEntity pPlayer) {
		super.removed(pPlayer);
		this.access.execute((p_234647_2_, p_234647_3_) -> {
			this.clearContainer(pPlayer, p_234647_2_, this.inputSlots);
		});
	}

	/**
	 * Determines whether supplied player can use this container
	 */
	@Override
	public boolean stillValid(PlayerEntity pPlayer) {
		return this.access.evaluate((world, blockPos) -> {
			return !this.isValidBlock(world.getBlockState(blockPos)) ? false : pPlayer.distanceToSqr(blockPos.getX() + 0.5D, blockPos.getY() + 0.5D, blockPos.getZ() + 0.5D) <= 64.0D;
		}, true);
	}

	public void createResult() {
		// Setup parameters
		ItemStack inputItem = this.inputSlots.getItem(0);
		this.incompatible.set(0);
		this.engraveCost.set(0);
		this.reforgeCost.set(0);
		this.cost.set(1);
		int repairCost = 0;
		int baseCost = 0;
		int renameCost = 0;
		int mergingCost = 0;
		// Cancel if no input item
		if (inputItem.isEmpty() || inputItem.getCount()>1) {
			info("P1: Input item empty");
			this.resultSlots.setItem(0, ItemStack.EMPTY);
			this.cost.set(0);
			return;
		}
		// Setup stacks and modifiers
		ItemStack resultItem = inputItem.copy();
		ItemStack mergableItem = this.inputSlots.getItem(1);
		ItemStack resourceItem = this.inputSlots.getItem(2);
		Map<Enchantment, Integer> inputEnchants = EnchantmentHelper.getEnchantments(resultItem);
		List<ExtendedModifier> inputModifiers = ExtendedModifierUtils.getModifierList(resultItem);
		// Setup base cost
		baseCost = inputItem.getBaseRepairCost() + (mergableItem.isEmpty() ? 0 : mergableItem.getBaseRepairCost());
		boolean isEnchantingItem = false;
		boolean isModifierItem = false;
		boolean isScrollOrBook = false;
		boolean canCombineItemModifiers = false;
		// Logic if there is an item in merge slot
		if (!mergableItem.isEmpty()) {
			info("P2: Has mergable item");
			//if (!net.minecraftforge.common.ForgeHooks.onAnvilChange(this, inputItem, mergableItem, resultSlots, itemName, baseCost, this.player)) return;
			isEnchantingItem = mergableItem.getItem() == Items.ENCHANTED_BOOK && !EnchantedBookItem.getEnchantments(mergableItem).isEmpty();
			isModifierItem = (mergableItem.getItem() == ExtendedItems.MODIFIER_SCROLL.get() && !ExtendedModifierScrollItem.getModifier(mergableItem).isUnknown()) || mergableItem.getItem() == LOTRItems.BOOK_OF_TRUE_SILVER.get();
			isScrollOrBook = isEnchantingItem || isModifierItem;
			if (!isScrollOrBook && resultItem.getItem() == mergableItem.getItem() && resultItem.isDamageableItem()) {
				int durabilityLeftBase = inputItem.getMaxDamage() - inputItem.getDamageValue();
				int durabilityLeftMaterial = mergableItem.getMaxDamage() - mergableItem.getDamageValue();
				int bonusDurability = durabilityLeftMaterial + resultItem.getMaxDamage() * 12 / 100;
				int combinedDurability = durabilityLeftBase + bonusDurability;
				int newDurability = resultItem.getMaxDamage() - combinedDurability;
				if (newDurability < 0) {
					newDurability = 0;
				}
				if (newDurability < resultItem.getDamageValue()) {
					resultItem.setDamageValue(newDurability);
					mergingCost += 2; // TODO add more dynamic cost
				}
			}
			if (isScrollOrBook || (resultItem.getItem() == mergableItem.getItem() && resultItem.isDamageableItem())) {
				canCombineItemModifiers= true;
			}
		}
		if(isEnchantingItem && !ExtendedServerConfig.enableVanillaEnchanting.get()) {
			this.resultSlots.setItem(0, ItemStack.EMPTY);
			this.cost.set(0);
			return;
		}
		if(ExtendedServerConfig.enableVanillaEnchanting.get()) {
			if(!mergableItem.isEmpty() && canCombineItemModifiers) {
				Map<Enchantment, Integer> mergableEnchants = EnchantmentHelper.getEnchantments(mergableItem);
				boolean enchantApplied = false;
				boolean enchantInvalid = false;
				for(Enchantment mergableEnchant : mergableEnchants.keySet()) {
					if (mergableEnchant != null) {
						int slot0Level = inputEnchants.getOrDefault(mergableEnchant, 0);
						int slot1Level = mergableEnchants.get(mergableEnchant);
						slot1Level = slot0Level == slot1Level ? slot1Level + 1 : Math.max(slot1Level, slot0Level);
						boolean isApplicable = mergableEnchant.canEnchant(inputItem);
						if (this.player.abilities.instabuild || inputItem.getItem() == Items.ENCHANTED_BOOK) {
							isApplicable = true;
						}
						for(Enchantment inputEnchant : inputEnchants.keySet()) {
							if (inputEnchant != mergableEnchant && !mergableEnchant.isCompatibleWith(inputEnchant)) {
								isApplicable = false;
								++mergingCost;
							}
						}
						if (!isApplicable) {
							enchantInvalid = true;
						} else {
							enchantApplied = true;
							if (slot1Level > mergableEnchant.getMaxLevel()) {
								slot1Level = mergableEnchant.getMaxLevel();
							}
							inputEnchants.put(mergableEnchant, slot1Level);
							int rairityCost = 0;
							switch(mergableEnchant.getRarity()) {
								case COMMON:
									rairityCost = 1;
									break;
								case UNCOMMON:
									rairityCost = 2;
									break;
								case RARE:
									rairityCost = 4;
									break;
								case VERY_RARE:
									rairityCost = 8;
							}
							if (isEnchantingItem) {
								rairityCost = Math.max(1, rairityCost / 2);
							}
							mergingCost += rairityCost * slot1Level;
						}
					}
				}
				if (enchantInvalid && !enchantApplied) {
					this.resultSlots.setItem(0, ItemStack.EMPTY);
					this.cost.set(0);
					return;
				}
			}
		}else {
			inputEnchants.clear();
		}
		if(ExtendedServerConfig.enableExtendedModifiers.get()) {
			info("P4: Modifiers");
			if(!mergableItem.isEmpty() && canCombineItemModifiers) {
				info("P4.1: Modifiers and valid item");
				// Returns a safe built list, not based on nbt
				List<ExtendedModifier> mergableModifiers = ExtendedModifierUtils.getModifierList(mergableItem);
				if(isModifierItem) {
					if(mergableItem.getItem() == LOTRItems.BOOK_OF_TRUE_SILVER.get()) {
						mergableModifiers.add(ExtendedModifiers.TRUE);
					}else {
						mergableModifiers.add(ExtendedModifierScrollItem.getModifier(mergableItem));
					}
				}
				boolean hasIncompatibleModifier = false;
				boolean maxedLevelForBook = false;
				boolean maximumModifiersReached = false;
				int currentModifierCount = (int) inputModifiers.stream().filter(m -> m.getRarity().countsTowardsLimit()).count();
				boolean didMerge=false;
				for(ExtendedModifier mergingModifier : mergableModifiers) {
					/*************CHECK_ITEM*******************/
					// Skip modifier if it can't be applied to the item
					if (!mergingModifier.canApplyToItem(inputItem)) {
						// Scrolls should not skip modifiers, reject recipe
						if(isModifierItem) {
							hasIncompatibleModifier = true;
							info("P4.2: Discarding invalid modifier for scroll, rejecting");
							break; // atomic merge rule
						}
						info("P4.2.1: Skipping modifier invalid for item: "+mergingModifier.getLocalKey());
						continue;
					}
					/*************CHECK_TIER*******************/
					// Use the lowest tier value if the modifier does not exist on the original item, so that the new item can override this lowest possible value.
					ExtendedModifier existingModifier = inputModifiers.stream()
							.filter(m -> m.getModifierType() == mergingModifier.getModifierType())
							.findFirst().orElse(ExtendedModifiers.UNKNOWN);// UNKNOWN's tier is 0 
					int outputSlotLevel = 0;
					// Split logic so a tier one modifier + unknown that defaults to lowest tier does trigger (slot1Level + 1)
					if(!existingModifier.isUnknown()) {
						int slot0Level = existingModifier.getModifierTeir();
						int slot1Level = mergingModifier.getModifierTeir();
						// Stop low tier books still applying to items with higher tier modifiers
						if(slot1Level < slot0Level && isModifierItem) {
							// Scrolls should not apply when below existing modifier tiers, reject recipe
							hasIncompatibleModifier = true;
							info("P4.3: Discarding invalid scroll tier modifier, rejecting");
							break; // atomic merge rule
						}
						// If same then increase level, otherwise get the max of the two levels
						outputSlotLevel = (slot0Level == slot1Level) ? slot1Level + 1 : Math.max(slot1Level, slot0Level);
					}else {
						outputSlotLevel = mergingModifier.getModifierTeir();
					}
					/*************CHECK_MAX_TIER***************/
					// If applied modifier pushes past max tier, skip it or reject if is applied from scroll
					if (outputSlotLevel > mergingModifier.getMaxLevel()) {
						// Scrolls should not apply there is no higher tier, reject recipe
						if (isModifierItem) {
							maxedLevelForBook = true;
							info("P4.4: Discarding invalid maxed modifer scroll, rejecting");
							break; // atomic merge rule
						}
						info("P4.4.1: Skipping modifier maxed exists: "+mergingModifier.getLocalKey());
						continue;
					}
					/*************CHECK_COMPATIBILITY**********/
					// Check if modifier can apply alongside other modifiers
					boolean incompatibleWithExisting = false;
					// Break if incompatible modifiers exist, don't want to just delete them
					for(ExtendedModifier existing : inputModifiers) {
						// Only check against modifiers of different types
						if (mergingModifier.getModifierType()!=existing.getModifierType() && !mergingModifier.isCompatableModifier(existing.getModifierType())) {
							incompatibleWithExisting = true;
							break;
						}
					}
					if(incompatibleWithExisting) {
						// Reject recipe if there are modifier conflicts, don't want to discard modifiers, reject recipe
						hasIncompatibleModifier = true;
						info("P4.5: Rejecting incompatable modifier, rejecting");
						break; // atomic merge rule
					}
					/*************CHECK_MODIFIER_CAP***********/
					// If modifier does not exist yet, and we are at max count, reject recipe
					if (existingModifier.isUnknown() && mergingModifier.getRarity().countsTowardsLimit() && currentModifierCount >= 3) {
						maximumModifiersReached = true;
						info("P4.6: Rejecting to many modifers, rejecting");
						break; // atomic merge rule
					}
					/*************GET_NEW_TIER*****************/
					// Get the list of modifiers for this type
					List<ExtendedModifier> list = ExtendedModifiers.MODIFIERS_BY_TYPE.get(mergingModifier.getModifierType());
					// Find the modifier with the desired level
					final int outputSlotLevelEffectiveFinal = outputSlotLevel;
					ExtendedModifier result = list.stream().filter(m -> m.getModifierTeir() == outputSlotLevelEffectiveFinal).findFirst()
							.orElse(ExtendedModifiers.UNKNOWN); // fallback if no exact match
					if(result.isUnknown()) {
						info("P4.7: Found unknown modifer, rejecting");
						// Should never happen because we checked tiers above, sanity check
						hasIncompatibleModifier = true;
						break; // atomic merge rule
					}
					if (!result.canApplyToItem(inputItem)) {
						// Scrolls should not skip modifiers, reject recipe
						if(isModifierItem) {
							hasIncompatibleModifier = true;
							info("P4.7.1: Discarding invalid upgraded modifier for scroll, rejecting");
							break; // atomic merge rule
						}
						info("P4.7.2: Skipping modifier invalid for item: "+result.getLocalKey());
						continue;
					}
					/*************UPDATE_LISTS*****************/
					// Remove old modifier of the same type
					inputModifiers.removeIf(m -> m.getModifierType() == mergingModifier.getModifierType());
					// only add merge cost if is positive modifier
					if(outputSlotLevel>0) {
						info("P4.8: Adding merger cost");
						mergingCost += Math.max(1, result.getModifierValue());
					}
					didMerge=true;
					inputModifiers.add(result);
					// Update modifier count if modifier was previously unknown to the list
					if(existingModifier.isUnknown() && result.getRarity().countsTowardsLimit()) {
						currentModifierCount++;
						info("P4.9: Increasing modifier count");
					}
					info("P4.10: End modifier loop");
				}
				if (maxedLevelForBook || maximumModifiersReached || hasIncompatibleModifier) {
					info("P4.11: Rejecting invalid combination");
					resultItem = ItemStack.EMPTY;
					this.cost.set(0);
					if (maxedLevelForBook) {
						this.incompatible.set(1);
					} else if (maximumModifiersReached) {
						this.incompatible.set(2);
					}else{
						this.incompatible.set(3);
					}
					return;
				}
				// Handle merging negative tier items
				if(didMerge && mergingCost==0) {
					info("P4.12: Negitive tier item merge");
					mergingCost +=1;
				}
			}
		}else {
			inputModifiers.clear();
		}
		// The repair cost should always include the value of present modifiers and enchants
		// Since the increase cost is +2 instead of *2+1 this balances things out
		for(Enchantment modifier : inputEnchants.keySet()) {
			int rairityCost = 0;
			switch(modifier.getRarity()) {
				case COMMON:
					rairityCost = 1;
					break;
				case UNCOMMON:
					rairityCost = 2;
					break;
				case RARE:
					rairityCost = 4;
					break;
				case VERY_RARE:
					rairityCost = 8;
			}
			baseCost+= Math.max(1, rairityCost);
		}
		for(ExtendedModifier modifier : inputModifiers) {
			baseCost+= Math.max(1, modifier.getModifierValue());
		}
		if (StringUtils.isBlank(this.itemName)) {
			info("P5: Item Renaming");
			if (inputItem.hasCustomHoverName()) {
				renameCost = 1;
				resultItem.resetHoverName();
				info("P5.1: Reset name");
			}
		} else if (!this.itemName.equals(inputItem.getHoverName().getString())) {
			renameCost = 1;
			resultItem.setHoverName(new StringTextComponent(this.itemName));
			info("P5.2: Set name");
		}
		if (resultItem.isDamageableItem() && isValidRepairMaterial(inputItem, resourceItem)) {
			info("P6: Valid repair item");

			String ownerName = ItemOwnership.getCurrentOwner(inputItem) != null ? ItemOwnership.getCurrentOwner(inputItem).getString() : null;
			if(ownerName==null||!player.getDisplayName().getString().equals(ownerName)) {
				this.engraveCost.set(2);
			}else {
				this.engraveCost.set(0);
			}
			info("P6.1: Finding reforge cost");
			int reforgeMaterialCost = 0;
			int reforgeCurrentDamage = resultItem.getDamageValue();
			int repairStep = resultItem.getMaxDamage() / 4;
			while (reforgeCurrentDamage > 0) {
				int reforgeRepairAmount = Math.min(reforgeCurrentDamage, repairStep);
				info("P6.1.1: Reforge Amount: " + reforgeRepairAmount + ", currentDamage: " + reforgeCurrentDamage + ", material used: " + reforgeMaterialCost);
				reforgeCurrentDamage -= reforgeRepairAmount;
				reforgeMaterialCost++;
				info("P6.1.2: Repair Amount: " + reforgeRepairAmount + ", newDamage: " + reforgeCurrentDamage);
			}
			this.reforgeCost.set(reforgeMaterialCost+2);
			if(inputItem.getItem() instanceof ArmorItem) {
				this.reforgeCost.set(reforgeMaterialCost+3);
			}else if(shouldCheapReforge(inputItem.getItem())) {
				this.reforgeCost.set(reforgeMaterialCost+1);
			}
			info("P6.2: Finding repair cost");
			int repairAmount = Math.min(resultItem.getDamageValue(), resultItem.getMaxDamage() / 4);
			// Handle relative resource count since without this, the price increased as material is added, not correct!
			int resourceCountExcludingBase = resourceItem.getCount() - mergingCost - renameCost; // don't try to repair if rename or merge cost is <= available cost
			if(resourceCountExcludingBase>0 && repairAmount>0) {
				int resourceCount = resourceCountExcludingBase - baseCost;// Calculate for resources over base cost
				if(resourceCount>0) { // Repair using amount left
					for(int materialsUsed = 0; repairAmount > 0 && materialsUsed < resourceCount; ++materialsUsed) {
						info("P6.2.1: Repair Amount: "+repairAmount+", currentDamage: "+resultItem.getDamageValue()+", material used: "+materialsUsed);
						int newDamage = resultItem.getDamageValue() - repairAmount;
						resultItem.setDamageValue(newDamage);
						++repairCost;
						repairAmount = Math.min(resultItem.getDamageValue(), resultItem.getMaxDamage() / 4);
						info("P6.2.2: Repair Amount: "+repairAmount+", newDamage: "+newDamage+", newRepairCost: "+repairCost);
					}
				}else { // Repair only once since repair material is present but does not have enough material
					repairCost=1;
					int newDamage = resultItem.getDamageValue() - repairAmount;
					resultItem.setDamageValue(newDamage);
					info("P6.3: Repair Amount: "+repairAmount+", currentDamage: "+resultItem.getDamageValue()+", material used: "+repairCost);
				}
			}
		}
		boolean isGemColoring = false;
		if(!mergableItem.isEmpty() && mergableItem.getItem() instanceof ExtendedGemstoneColoringItem) {
			info("P7: Valid gem item");
			if(!this.canApplyColor(resultItem, ((ExtendedGemstoneColoringItem)mergableItem.getItem()).getExtendedGemstoneColor())) {
				this.resultSlots.setItem(0, ItemStack.EMPTY);
				this.incompatible.set(4);
				this.cost.set(0);
				return;
			}
			info("P7.1: Valid gem coloring");
			applyColor(resultItem, ((ExtendedGemstoneColoringItem)mergableItem.getItem()).getExtendedGemstoneColor());
			isGemColoring = true;
		}
		if (isEnchantingItem && !resultItem.isBookEnchantable(mergableItem)) {
			info("P8: Rejecting invalid book");
			resultItem = ItemStack.EMPTY;
			this.cost.set(0);
			return;
		}
		// Set pending final cost
		info("CostBase: "+baseCost+", repair: " + repairCost +", merge: "+ mergingCost+", rename: " + renameCost);
		this.cost.set(baseCost + repairCost + mergingCost + renameCost);
		// If nothing has been done, clear output
		if (repairCost <= 0 && renameCost<=0 && mergingCost<=0 && !isGemColoring) {
			resultItem = ItemStack.EMPTY;
			info("P9: Set empty because no costs or coloring");
		}
		// If not modifying item, make rename cheap
		if (repairCost==0 && mergingCost==0 && renameCost>0) {
			info("P10: Set rename cost to 1");
			this.cost.set(renameCost);//always 1
			// Allow items to be renamed if they don't have a repair material
			if (!shouldHardcodedForceRenameCosts(resultItem.getItem())) {
				this.cost.set(0);
				info("P10.1: Set override rename cost to 0");
			}
		}
		// Can't repair/combine and gem color at same time, so remove gem cost if not renaming
		if(repairCost==0 && mergingCost==0 && isGemColoring && renameCost==0) {
			//info("isGemColoring && !isRenaming");
			this.cost.set(0);
			info("P11: Only gem coloring, set cost to zero");
		}
		// Allow creative bypass for expensive items
		if (this.cost.get() > 63 && !this.player.abilities.instabuild) {
			resultItem = ItemStack.EMPTY;
			info("P12: Reject max cost if not creative");
		}
		if (!resultItem.isEmpty()) {
			info("P13: Setting new repair cost");
			int newRepairCost = resultItem.getBaseRepairCost();
			if (!mergableItem.isEmpty() && newRepairCost < mergableItem.getBaseRepairCost()) {
				newRepairCost = mergableItem.getBaseRepairCost();
			}
			if (repairCost>0 || mergingCost>0) {
				newRepairCost += 2;
			}
			info("P13.1: OLD: "+resultItem.getBaseRepairCost()+", NEW: "+newRepairCost);

			resultItem.setRepairCost(newRepairCost);
			ExtendedModifierUtils.setModifiers(resultItem, inputModifiers);
			EnchantmentHelper.setEnchantments(inputEnchants, resultItem);
		}
		info("P14: Done");
		this.resultSlots.setItem(0, resultItem);
		this.broadcastChanges();
	}

	public static int calculateIncreasedRepairCost(int pOldRepairCost) {
		return pOldRepairCost * 2 + 1;
	}


	/**
	 * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
	 * inventory and the other inventory(s).
	 */
	@Override
	public ItemStack quickMoveStack(PlayerEntity pPlayer, int pIndex) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.slots.get(pIndex);
		if (slot != null && slot.hasItem()) {
			ItemStack stackToMove = slot.getItem();
			itemstack = stackToMove.copy();
			if (pIndex == 3) {
				if (!this.moveItemStackTo(stackToMove, 4, 40, true)) {
					return ItemStack.EMPTY;
				}
				slot.onQuickCraft(stackToMove, itemstack);
			} else if (pIndex != 0 && pIndex != 1 && pIndex != 2) {
				if (pIndex >= 4 && pIndex < 40) {
					int i = this.shouldQuickMoveToAdditionalSlot(itemstack) ? 1 : 0;
					if (!this.moveItemStackTo(stackToMove, i, 3, false)) {
						return ItemStack.EMPTY;
					}
				}
			} else if (!this.moveItemStackTo(stackToMove, 4, 40, false)) {
				return ItemStack.EMPTY;
			}
			if (stackToMove.isEmpty()) {
				slot.set(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}
			if (stackToMove.getCount() == itemstack.getCount()) {
				return ItemStack.EMPTY;
			}
			slot.onTake(pPlayer, stackToMove);
		}

		return itemstack;
	}

	public void reforgeItem() {
		ItemStack inputItem = this.inputSlots.getItem(0);
		ItemStack modifierItem = this.inputSlots.getItem(1);
		int reforgeCostCopy = this.reforgeCost.get();
		if (!inputItem.isEmpty() && reforgeCostCopy > 0) {
			ItemStack resourceItem = this.inputSlots.getItem(2);
			if(isValidRepairMaterial(inputItem, resourceItem) && resourceItem.getCount()>=reforgeCostCopy) {
				if(inputItem.isDamageableItem()) {
					inputItem.setDamageValue(0);
				}
				ExtendedModifierUtils.generateItemModifiersTagged(this.player.random, inputItem, true, true, modifierItem);
				hurtOrDamageAnvilSound();
				if (resourceItem.getCount()>reforgeCostCopy) {
					resourceItem.shrink(reforgeCostCopy);
					this.inputSlots.setItem(2, resourceItem);
				} else {
					this.inputSlots.setItem(2, ItemStack.EMPTY);
				}
				inputItem.setRepairCost(0);
				this.inputSlots.setItem(0, inputItem);
				this.reforgeCost.set(0);
				this.broadcastChanges();
				this.createResult();
				if(player instanceof ServerPlayerEntity) {
					ExtendedCriteriaTriggers.ABSTRACT_STRING_TRIGGER.trigger((ServerPlayerEntity) player, "reforged");
				}
			}
		}
	}

	public void engraveItem() {
		ItemStack inputItem = this.inputSlots.getItem(0);
		int engraveCostCopy = this.engraveCost.get();
		if (!inputItem.isEmpty() && engraveCostCopy > 0) {
			ItemStack resourceItem = this.inputSlots.getItem(2);
			if(isValidRepairMaterial(inputItem, resourceItem) && resourceItem.getCount()>=engraveCostCopy) {
				ItemOwnership.setCurrentOwner(inputItem, player.getDisplayName());
				hurtOrDamageAnvilSound();
				if (resourceItem.getCount()>engraveCostCopy) {
					resourceItem.shrink(engraveCostCopy);
					this.inputSlots.setItem(2, resourceItem);
				} else {
					this.inputSlots.setItem(2, ItemStack.EMPTY);
				}
				this.inputSlots.setItem(0, inputItem);
				this.engraveCost.set(0);
				this.broadcastChanges();
				this.createResult();
				if(player instanceof ServerPlayerEntity) {
					ExtendedCriteriaTriggers.ABSTRACT_STRING_TRIGGER.trigger((ServerPlayerEntity) player, "engraved");
				}
			}
		}
	}

	protected boolean shouldQuickMoveToAdditionalSlot(ItemStack pStack) {
		return false;
	}

	public void setItemName(String pNewName) {
		this.itemName = pNewName;
		/*
		if (this.getSlot(3).hasItem()) {
			ItemStack itemstack = this.getSlot(3).getItem();
			if (StringUtils.isBlank(pNewName)) {
				itemstack.resetHoverName();
			} else {
				itemstack.setHoverName(new StringTextComponent(this.itemName));
			}
		}*/

		this.createResult();
	}

	public void applyColor(ItemStack stack, TextFormatting gemColor) {
		IFormattableTextComponent newName = stack.getHoverName().copy();
		stack.setHoverName(newName.withStyle(gemColor));
	}

	public boolean canApplyColor(ItemStack stack, TextFormatting gemColor) {
		if(!stack.hasCustomHoverName()) {
			return true;
		}
		ITextComponent name = stack.getHoverName();
		// TODO better color checking for more formatted item names
		if(name.getStyle().getColor()==Color.fromLegacyFormat(gemColor)) {
			return false;
		}
		return true;
	}

	/**
	 * Get's the maximum xp cost
	 * @return The cost for the operation
	 */
	public int getCost() {
		return this.cost.get();
	}

	public boolean getMaxedLevelForBook() {
		return this.incompatible.get()==1;
	}

	public boolean getMaximumModifiersReached() {
		return this.incompatible.get()==2;
	}

	public boolean getIncompatible() {
		return this.incompatible.get()==3;
	}

	public boolean getGemIncompatible() {
		return this.incompatible.get()==4;
	}

	public int getReforgeCost() {
		return this.reforgeCost.get();
	}

	public int getEngraveCost() {
		return this.engraveCost.get();
	}

	public void hurtOrDamageAnvilSound() {
		this.access.execute((world, pos) -> {
			BlockState blockstate = world.getBlockState(pos);
			//  Chance is 1/16 that of vanilla
			if (!player.abilities.instabuild && blockstate.is(BlockTags.ANVIL) && player.getRandom().nextFloat() < 0.0075f) { // 0.12 is default vanilla value
				BlockState blockstate1 = AnvilBlock.damage(blockstate);
				if (blockstate1 == null) {
					world.removeBlock(pos, false);
					world.levelEvent(1029, pos, 0);
				} else {
					world.setBlock(pos, blockstate1, 2);
					world.levelEvent(1030, pos, 0);
				}
			} else {
				world.levelEvent(1030, pos, 0);
			}
		});
	}

	public static boolean isValidRepairMaterial(ItemStack inputItem, ItemStack repairMaterial) {
		boolean regularRapair = inputItem.getItem().isValidRepairItem(inputItem, repairMaterial);
		if(!regularRapair) {
			if(!(inputItem.getItem() instanceof ExtendedBowItem) && inputItem.getItem() instanceof BowItem) {
				return repairMaterial.getItem() == Items.STRING;
			}
			if(inputItem.getItem() instanceof FishingRodItem) {
				return repairMaterial.getItem() == Items.STRING;
			}
			if(inputItem.getItem() instanceof ShearsItem) {
				return Tags.Items.INGOTS_IRON.contains(repairMaterial.getItem());
			}
			if(inputItem.getItem() instanceof SmokingPipeItem) {
				return Tags.Items.LEATHER.contains(repairMaterial.getItem());
			}
			if(!(inputItem.getItem() instanceof ExtendedCrossbowItem) && inputItem.getItem() instanceof CrossbowItem) {
				return Tags.Items.INGOTS_IRON.contains(repairMaterial.getItem());
			}
			if(inputItem.getItem() instanceof FlintAndSteelItem) {
				return Tags.Items.INGOTS_IRON.contains(repairMaterial.getItem());
			}
		}
		return regularRapair;
	}

	// TODO hook this up with data tag lists so additional items can be blacklisted from free renames
	public static boolean shouldHardcodedForceRenameCosts(Item item) {
		return item instanceof SwordItem || item instanceof ArmorItem || item instanceof ToolItem || item instanceof FlintAndSteelItem ||
				item instanceof BowItem || item instanceof FishingRodItem || item instanceof ShearsItem || 
				item instanceof SmokingPipeItem || item instanceof CrossbowItem || item instanceof ShieldItem|| 
				item instanceof ExtendedBrandingIronItem || item instanceof ExtendedChiselItem || item instanceof ExtendedThrowableAxeItem;
	}

	public static boolean shouldCheapReforge(Item item) {
		return item instanceof ShovelItem || item instanceof SpearItem || item instanceof DaggerItem || item instanceof CrossbowItem
			|| item instanceof ExtendedChiselItem || item instanceof ExtendedBrandingIronItem || item instanceof FlintAndSteelItem;
	}

	private static void info(String message) {
		if(ExtendedServerConfig.enableSpecialLogging.get()) {
			ExtendedLog.info(message);
		}
	}
}