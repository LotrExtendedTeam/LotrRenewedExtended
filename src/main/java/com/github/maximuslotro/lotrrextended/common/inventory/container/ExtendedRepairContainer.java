package com.github.maximuslotro.lotrrextended.common.inventory.container;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedContainerTypes;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedGemstoneColoringItem;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedModifierScrollItem;
import com.github.maximuslotro.lotrrextended.common.modifiers.ExtendedModifierRarity;
import com.github.maximuslotro.lotrrextended.common.modifiers.ExtendedModifiers;
import com.github.maximuslotro.lotrrextended.common.modifiers.ExtendedModifier;
import com.github.maximuslotro.lotrrextended.common.utils.ExtendedModifierUtils;

import io.netty.buffer.Unpooled;
import lotr.common.init.ExtendedItems;
import lotr.common.item.ItemOwnership;
import net.minecraft.block.AnvilBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.RepairContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntReferenceHolder;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Based off of {@link RepairContainer}
 * Something is broken with the class in regards to saving old data using {@code lastCost} and {@code lastGemColoring} to try rectifying this
 * */
public class ExtendedRepairContainer extends Container {

	protected final CraftResultInventory resultSlots = new CraftResultInventory();
	protected final IInventory inputSlots = new Inventory(3) {
		@Override
		public void setChanged() {
			super.setChanged();
			ExtendedRepairContainer.this.slotsChanged(this);
		}
	};
	protected final IWorldPosCallable access;
	protected final PlayerEntity player;
	private String itemName;
	private final IntReferenceHolder cost = IntReferenceHolder.standalone();
	private final IntReferenceHolder incompatable = IntReferenceHolder.standalone();
	private final IntReferenceHolder gemprocessing = IntReferenceHolder.standalone();
	private final IntReferenceHolder toManyModifiers = IntReferenceHolder.standalone();
	private final IntReferenceHolder reforgeCost = IntReferenceHolder.standalone();
	private final IntReferenceHolder engraveCost = IntReferenceHolder.standalone();
	private int lastCost = 0;
	public static final ITextComponent CONTAINER_TITLE = new TranslationTextComponent("container.lotrextended.repair");
	private boolean isGemColoring = false;
	private boolean lastGemColoring = false;

	public ExtendedRepairContainer(int pContainerId, PlayerInventory pPlayerInventory) {
		this(pContainerId, pPlayerInventory, IWorldPosCallable.NULL, new PacketBuffer(Unpooled.buffer()));
	}

	public ExtendedRepairContainer(int pContainerId, PlayerInventory pPlayerInventory, PacketBuffer extraData) {
		this(pContainerId, pPlayerInventory, IWorldPosCallable.NULL, extraData);
	}

	public ExtendedRepairContainer(int pContainerId, PlayerInventory pPlayerInventory, IWorldPosCallable pAccess) {
		this(pContainerId, pPlayerInventory, pAccess, new PacketBuffer(Unpooled.buffer()));
	}

	public ExtendedRepairContainer(int pContainerId, PlayerInventory pPlayerInventory, IWorldPosCallable pAccess, PacketBuffer extraData) {
		super(ExtendedContainerTypes.EXTENDED_ANVIL.get(), pContainerId);
		this.access = pAccess;
		this.player = pPlayerInventory.player;
		this.addDataSlot(this.cost);
		this.addDataSlot(incompatable);
		this.addDataSlot(gemprocessing);
		this.addDataSlot(toManyModifiers);
		this.addDataSlot(reforgeCost);
		this.addDataSlot(engraveCost);
		this.slots.clear();
		this.addSlot(new Slot(this.inputSlots, 0, 27, 58));
		this.addSlot(new Slot(this.inputSlots, 1, 76, 47));
		this.addSlot(new Slot(this.inputSlots, 2, 76, 70));
		// Inventory only has space of 1, but we are giving index of 3 because craft inventory does not care about slot index
		this.addSlot(new Slot(this.resultSlots, 3, 134, 58) {
			/**
			 * Check if the stack is allowed to be placed in this slot.
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
				//if(pPlayer.abilities.instabuild && (ExtendedRepairContainer.this.cost.get() > 0 || isGemColoring)) {
				//	return true;
				//}
				if(ExtendedRepairContainer.this.inputSlots.getItem(0).getItem().isValidRepairItem(ExtendedRepairContainer.this.inputSlots.getItem(0), ExtendedRepairContainer.this.inputSlots.getItem(2))) {
					if(ExtendedRepairContainer.this.cost.get() > 0 && ExtendedRepairContainer.this.inputSlots.getItem(2).getCount() >= ExtendedRepairContainer.this.cost.get()){
						return true;
					}
				}
				if(ExtendedRepairContainer.this.cost.get()==0 && isGemColoring && ExtendedRepairContainer.this.inputSlots.getItem(1).getCount()>=1) {
					return true;
				}
				return false;
			}

			@Override
			public ItemStack onTake(PlayerEntity pPlayer, ItemStack pStack) {
				ExtendedRepairContainer.this.onTake(pPlayer, pStack);
				return super.onTake(pPlayer, pStack);
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
	/**
	 * Something is broken with the class in regards to saving old data using {@code lastCost} and {@code lastGemColoring} to try rectifying this
	 * @param pPlayer The player taking the item
	 * @param pInputItem the stack being taken
	 * @return unused, returns the input stack
	 * */
	protected ItemStack onTake(PlayerEntity pPlayer, ItemStack pInputItem) {
		//float breakChance = net.minecraftforge.common.ForgeHooks.onAnvilRepair(pPlayer, pInputItem, ExtendedRepairContainer.this.inputSlots.getItem(0), ExtendedRepairContainer.this.inputSlots.getItem(1));
		this.inputSlots.setItem(0, ItemStack.EMPTY);
		if (this.cost.get()>0 || lastCost>0) {
			ItemStack resourceSlot = this.inputSlots.getItem(2);
			if (!resourceSlot.isEmpty() && resourceSlot.getCount() > (this.cost.get()>0 ? this.cost.get() :lastCost)) {
				resourceSlot.shrink(this.cost.get()>0 ? this.cost.get() :lastCost);
				this.inputSlots.setItem(2, resourceSlot);
			} else {
				this.inputSlots.setItem(2, ItemStack.EMPTY);
			}
			ItemStack combinerSlot = this.inputSlots.getItem(1);
			if (!combinerSlot.isEmpty() && combinerSlot.getCount() > 1) {
				combinerSlot.shrink(1);
				this.inputSlots.setItem(1, combinerSlot);
			} else {
				this.inputSlots.setItem(1, ItemStack.EMPTY);
			}
		} else {
			if(isGemColoring || lastGemColoring) {
				ItemStack combinerSlot = this.inputSlots.getItem(1);
				if (!combinerSlot.isEmpty() && combinerSlot.getCount() > 1) {
					combinerSlot.shrink(1);
					this.inputSlots.setItem(1, combinerSlot);
				} else {
					this.inputSlots.setItem(1, ItemStack.EMPTY);
				}
			}
		}
		lastCost=0;
		lastGemColoring=false;
		this.cost.set(0);
		this.gemprocessing.set(0);
		this.incompatable.set(0);
		this.toManyModifiers.set(0);
		this.reforgeCost.set(0);
		this.engraveCost.set(0);
		hurtOrDamageAnvilSound();
		return pInputItem;
	}

	/**
	 * Callback for when the crafting matrix is changed.
	 */
	@Override
	public void slotsChanged(IInventory pInventory) {
		super.slotsChanged(pInventory);
		if (pInventory == this.inputSlots) {
			this.createResult();
		}
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
		return this.access.evaluate((p_234646_2_, p_234646_3_) -> {
			return !p_234646_2_.getBlockState(p_234646_3_).is(BlockTags.ANVIL) ? false : pPlayer.distanceToSqr((double)p_234646_3_.getX() + 0.5D, (double)p_234646_3_.getY() + 0.5D, (double)p_234646_3_.getZ() + 0.5D) <= 64.0D;
		}, true);
	}

	/**
	 * called when the Anvil Input Slot changes, calculates the new result and puts it in the output slot
	 */
	public void createResult() {
		ExtendedLog.info("createResult");
		ItemStack inputItem = this.inputSlots.getItem(0);
		this.gemprocessing.set(0);
		this.incompatable.set(0);
		this.toManyModifiers.set(0);
		this.engraveCost.set(0);
		this.reforgeCost.set(0);
		int repairCost = 0; // Total additional cost from repairs
		int baseCost = 0;  // How many materials consumed in repair
		int renameCost = 0; // Cost for renaming
		lastGemColoring = false;
		lastCost = 0;
		if (inputItem.isEmpty()) {
			this.resultSlots.setItem(0, ItemStack.EMPTY);
			if(this.cost.get()>0) {
				lastCost=this.cost.get();
			}
			//new Exception("Debug stack trace").printStackTrace();
			lastGemColoring = isGemColoring;
			this.cost.set(0);
			ExtendedLog.info("ItemEmpty");
		} else {
			ItemStack resultItem = inputItem.copy();
			ItemStack mergableItem = this.inputSlots.getItem(1);
			ItemStack resourceItem = this.inputSlots.getItem(2);
			List<ExtendedModifier> inputModifiers = ExtendedModifierUtils.getModifierList(resultItem);
			baseCost = inputItem.getBaseRepairCost() + (mergableItem.isEmpty() ? 0 : mergableItem.getBaseRepairCost());
			this.cost.set(0);
			isGemColoring = false;
			boolean hasApplicableScroll = false;

			if (!mergableItem.isEmpty()) {
				ExtendedLog.info("HasMergable");
				//if (!net.minecraftforge.common.ForgeHooks.onAnvilChange(this, itemstack, itemstack2, resultSlots, itemName, j, this.player)) return;
				hasApplicableScroll = (mergableItem.getItem() == ExtendedItems.MODIFIER_SCROLL.get() && !ExtendedModifierScrollItem.getModifier(mergableItem).isUnknown());
				if(!hasApplicableScroll && mergableItem.getItem() instanceof ExtendedGemstoneColoringItem) {
					isGemColoring=true;
				} else {
					if (!hasApplicableScroll && (resultItem.getItem() != mergableItem.getItem() || !resultItem.isDamageableItem())) {
						this.resultSlots.setItem(0, ItemStack.EMPTY);
						this.cost.set(0);
						this.gemprocessing.set(0);
						this.incompatable.set(0);
						this.toManyModifiers.set(0);
						this.broadcastChanges();
						return;
					}

					if (resultItem.isDamageableItem() && !hasApplicableScroll) {
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
							repairCost += 2; // TODO calculate based upon item damage values
						}
					}
					List<ExtendedModifier> mergableModifiers = ExtendedModifierUtils.getModifierList(mergableItem);
					boolean hasInCompatibleModifier = false;

					int currentModifierCount = (int) inputModifiers.stream().filter(m -> m.getRarity() == ExtendedModifierRarity.BASIC || m.getRarity() == ExtendedModifierRarity.SKILLFUL).count();
					int newModifierCount=0;
					//boolean notAllModifiersAtMax = false;
					for(ExtendedModifier modifier : mergableModifiers) {
						// Use the lowest tier value if the modifier does not exist on the original item, so that the new item can override this lowest possible value.
						ExtendedModifier existingModifier = inputModifiers.stream()
							.filter(m -> m.getModifierType() == modifier.getModifierType())
							.findFirst().orElse(ExtendedModifiers.UNKNOWN);
						int slot0Level = !existingModifier.isUnknown() ? existingModifier.getModifierLevel() : modifier.getMinLevel();
						int slot1Level = modifier.getModifierLevel();
						int outputSlotLevel = (slot0Level == slot1Level) ? slot1Level + 1 : Math.max(slot1Level, slot0Level);
						if (!modifier.canApplyToItem(inputItem)) {
							if(hasApplicableScroll) {
								hasInCompatibleModifier = true;
								ExtendedLog.info("cant apply to item");
							}
							continue;
						}						
						for(ExtendedModifier existing : inputModifiers) {
							if (modifier.getModifierType()!=existing.getModifierType() && !modifier.canApplyToModifier(existing.getModifierType())) {
								hasInCompatibleModifier = true;
								ExtendedLog.info("hasInCompatibleModifier");
							}
						}
						if(hasInCompatibleModifier) {
							continue;
						}
						boolean maxedLevelForBook = false;
						if (outputSlotLevel > modifier.getMaxLevel()) {
							if (hasApplicableScroll) {
								maxedLevelForBook = true;
								ExtendedLog.info("surpassed max level on item1");
							}else {
								ExtendedLog.info("surpassed max level on item2");
								continue;
							}
						}
						//notAllModifiersAtMax=true;
						boolean maximumModifiersReached = false;
						if ((modifier.getRarity() == ExtendedModifierRarity.BASIC || modifier.getRarity() == ExtendedModifierRarity.SKILLFUL) 
							&& currentModifierCount+newModifierCount>=3 && existingModifier.isUnknown()) {
							maximumModifiersReached = true;
							ExtendedLog.info("maximumModifiersReached");
						}
						if(maximumModifiersReached){
							if(hasApplicableScroll) {
								//Only trigger when modifier scroll is placed
								this.cost.set(0);
								this.gemprocessing.set(0);
								this.incompatable.set(0);
								this.toManyModifiers.set(1);
								this.resultSlots.setItem(0, ItemStack.EMPTY);
								this.broadcastChanges();
								ExtendedLog.info("to many modifiers");
								return;
							}
							continue;// skip 3 max warning if combining items, not using scrolls
						}else if(maxedLevelForBook){
							//Only trigger when modifier scroll is placed
							this.cost.set(0);
							this.gemprocessing.set(0);
							this.incompatable.set(0);
							this.toManyModifiers.set(2);
							this.resultSlots.setItem(0, ItemStack.EMPTY);
							this.broadcastChanges();
							ExtendedLog.info("maxed modifier level");
							return;
						}else {

							// Remove old modifier of the same type
							inputModifiers.removeIf(m -> m.getModifierType() == modifier.getModifierType());

							// Get the list of modifiers for this type
							List<ExtendedModifier> list = ExtendedModifiers.MODIFIERS_BY_TYPE.get(modifier.getModifierType());

							// Find the modifier with the desired level
							final int outoutLevel = outputSlotLevel;
							ExtendedModifier result = list.stream()
								.filter(m -> m.getModifierLevel() == outoutLevel)
								.findFirst()
								.orElse(ExtendedModifiers.UNKNOWN); // fallback if no exact match

							// result now contains the modifier of the same type with the requested level
							inputModifiers.add(result);
							int rarityCost = 0;
							switch(modifier.getRarity()) {
							case BASIC:
								rarityCost = 1;
								break;
							case SKILLFUL:
								rarityCost = 2;
								break;
							case APPLIED:
								rarityCost = 4;
								break;
							default:
								rarityCost = 100;
								break;
							}

							if (hasApplicableScroll) {
								rarityCost = Math.max(1, rarityCost / 2);
							}

							repairCost += rarityCost * outputSlotLevel;
							if (inputItem.getCount() > 1) {
								repairCost = 40; // Max cost if stack > 1
							}
							if (modifier.getRarity() == ExtendedModifierRarity.BASIC || modifier.getRarity() == ExtendedModifierRarity.SKILLFUL) {
								newModifierCount++;
							}
						}
					}

					if (hasInCompatibleModifier){//)|| (mergableModifiers.size()>0 && !notAllModifiersAtMax)) {
						this.cost.set(0);
						this.gemprocessing.set(0);
						this.incompatable.set(1);
						this.toManyModifiers.set(0);
						this.resultSlots.setItem(0, ItemStack.EMPTY);
						this.broadcastChanges();
						ExtendedLog.info("sent hasInCompatibleModifier");
						return;
					}
				}
			}else if (resultItem.isDamageableItem() && resultItem.getItem().isValidRepairItem(inputItem, resourceItem)) {
				ExtendedLog.info("Damagable and Repair");
				int repairAmount = Math.min(resultItem.getDamageValue(), resultItem.getMaxDamage() / 4);
				this.engraveCost.set(2);
				this.reforgeCost.set(2);
				if(inputItem.getItem() instanceof ArmorItem) {
					this.reforgeCost.set(3);
				}
				int newRepairAmount = repairAmount;
				for(int materialsUsed = 0; newRepairAmount > 0 && materialsUsed < resourceItem.getCount(); ++materialsUsed) {
					int newDamage = resultItem.getDamageValue() - newRepairAmount;
					resultItem.setDamageValue(newDamage);
					++repairCost;
					newRepairAmount = Math.min(resultItem.getDamageValue(), resultItem.getMaxDamage() / 4);
					this.reforgeCost.set(this.reforgeCost.get()+materialsUsed);
				}
			}
			if (StringUtils.isBlank(this.itemName)) {
				if (inputItem.hasCustomHoverName()) {
					renameCost =+1;
					resultItem.resetHoverName();
				}
			} else if (!this.itemName.equals(inputItem.getHoverName().getString())) {
				ExtendedLog.info("Set item name");
				renameCost =+1;
				resultItem.setHoverName(new StringTextComponent(this.itemName));
			}
			if(isGemColoring) {
				if(!this.canApplyColor(resultItem, ((ExtendedGemstoneColoringItem)mergableItem.getItem()).getExtendedGemstoneColor())) {
					isGemColoring=false;
					this.cost.set(0);
					this.gemprocessing.set(1);
					this.incompatable.set(0);
					this.toManyModifiers.set(0);
					this.resultSlots.setItem(0, ItemStack.EMPTY);
					return;
				}
				applyColor(resultItem, ((ExtendedGemstoneColoringItem)mergableItem.getItem()).getExtendedGemstoneColor());
			}
			this.cost.set(baseCost + repairCost + renameCost);
			if (repairCost <= 0 && renameCost==0 && !isGemColoring) {
				ExtendedLog.info("repairCost <= 0 && renameCost==0 && !isGemColoring");
				resultItem = ItemStack.EMPTY;
			}

			if (repairCost==0 && renameCost > 0 && this.cost.get() >= 40) {
				ExtendedLog.info("repairCost==0 && renameCost > 0 && this.cost.get() >= 40");
				this.cost.set(39);
			}

			if (this.cost.get() >= 40 && !this.player.abilities.instabuild && !isGemColoring) {
				ExtendedLog.info("this.cost.get() >= 40 && !this.player.abilities.instabuild && !isGemColoring");
				resultItem = ItemStack.EMPTY;
			}
			if(isGemColoring && renameCost == 0) {
				ExtendedLog.info("isGemColoring && renameCost == 0");
				this.cost.set(0);
			}
			if (!resultItem.isEmpty()) {
				int newRepairCost = resultItem.getBaseRepairCost();
				if (!mergableItem.isEmpty() && newRepairCost < mergableItem.getBaseRepairCost()) {
					newRepairCost = mergableItem.getBaseRepairCost();
				}

				if (repairCost>0 || renameCost == 0 && !isGemColoring) {
					newRepairCost = calculateIncreasedRepairCost(newRepairCost);
				}

				resultItem.setRepairCost(newRepairCost);
				ExtendedModifierUtils.setModifiers(resultItem, inputModifiers);
			}
			ExtendedLog.info("Has EG: "+engraveCost.get()+", RC: "+this.reforgeCost.get());
			this.resultSlots.setItem(0, resultItem);
			this.broadcastChanges();
		}
	}

	public void reforgeItem() {
		ItemStack inputItem = this.inputSlots.getItem(0);
		int reforgeCostCopy = this.reforgeCost.get();
		if (!inputItem.isEmpty() && reforgeCostCopy > 0) {
			ItemStack resourceItem = this.inputSlots.getItem(2);
			if(inputItem.getItem().isValidRepairItem(inputItem, resourceItem) && resourceItem.getCount()>=reforgeCostCopy) {
				if(inputItem.isDamageableItem()) {
					inputItem.setDamageValue(0);
				}
				inputItem = ExtendedModifierUtils.generateItemModifiers(this.player.random, inputItem, true, true);
				hurtOrDamageAnvilSound();
				if (resourceItem.getCount()>reforgeCostCopy) {
					resourceItem.shrink(reforgeCostCopy);
					this.inputSlots.setItem(2, resourceItem);
				} else {
					this.inputSlots.setItem(2, ItemStack.EMPTY);
				}
				this.inputSlots.setItem(0, inputItem);
				this.reforgeCost.set(0);
				this.broadcastChanges();
				this.createResult();
			}
		}
	}

	public void engraveItem() {
		ItemStack inputItem = this.inputSlots.getItem(0);
		int engraveCostCopy = this.engraveCost.get();
		if (!inputItem.isEmpty() && engraveCostCopy > 0) {
			ItemStack resourceItem = this.inputSlots.getItem(2);
			if(inputItem.getItem().isValidRepairItem(inputItem, resourceItem) && resourceItem.getCount()>=engraveCostCopy) {
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
			}
		}
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
			ItemStack itemstack1 = slot.getItem();
			itemstack = itemstack1.copy();
			if (pIndex == 3) {
				if (!this.moveItemStackTo(itemstack1, 4, 40, true)) {
					return ItemStack.EMPTY;
				}

				slot.onQuickCraft(itemstack1, itemstack);
			} else if (pIndex != 0 && pIndex != 1 && pIndex != 2) {
				if (pIndex >= 4 && pIndex < 40) {
					int i = this.shouldQuickMoveToAdditionalSlot(itemstack) ? 1 : 0;
					if (!this.moveItemStackTo(itemstack1, 0, 3, false)) {
						return ItemStack.EMPTY;
					}
				}
			} else if (!this.moveItemStackTo(itemstack1, 4, 40, false)) {
				return ItemStack.EMPTY;
			}

			if (itemstack1.isEmpty()) {
				slot.set(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}

			if (itemstack1.getCount() == itemstack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTake(pPlayer, itemstack1);
		}

		return itemstack;
	}

	protected boolean shouldQuickMoveToAdditionalSlot(ItemStack pStack) {
		return false;
	}

	public static int calculateIncreasedRepairCost(int pOldRepairCost) {
		return pOldRepairCost * 2 + 1;
	}

	/**
	 * used by the Anvil GUI to update the Item Name being typed by the player
	 * @param pNewName The new name for the item
	 */
	public void setItemName(String pNewName) {
		this.itemName = pNewName;
		if (this.getSlot(3).hasItem()) {
			ItemStack itemstack = this.getSlot(3).getItem();
			if (StringUtils.isBlank(pNewName)) {
				itemstack.resetHoverName();
			} else {
				itemstack.setHoverName(new StringTextComponent(this.itemName));
			}
		}

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
	@OnlyIn(Dist.CLIENT)
	public int getCost() {
		return this.cost.get();
	}

	@OnlyIn(Dist.CLIENT)
	public boolean getGemIncompatable() {
		return this.gemprocessing.get()==1? true : false;
	}

	@OnlyIn(Dist.CLIENT)
	public int getToManyModifiers() {
		return this.toManyModifiers.get();
	}

	@OnlyIn(Dist.CLIENT)
	public boolean getIncompatable() {
		return this.incompatable.get()==1? true : false;
	}

	@OnlyIn(Dist.CLIENT)
	public int getReforgeCost() {
		return this.reforgeCost.get();
	}

	@OnlyIn(Dist.CLIENT)
	public int getEngraveCost() {
		return this.engraveCost.get();
	}

	public void hurtOrDamageAnvilSound() {
		this.access.execute((world, pos) -> {
			BlockState blockstate = world.getBlockState(pos);
			if (!player.abilities.instabuild && blockstate.is(BlockTags.ANVIL) && player.getRandom().nextFloat() < 0.12f) { // breakChance set to default value
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
}