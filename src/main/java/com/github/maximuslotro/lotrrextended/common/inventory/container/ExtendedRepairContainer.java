package com.github.maximuslotro.lotrrextended.common.inventory.container;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedContainerTypes;

import io.netty.buffer.Unpooled;
import net.minecraft.block.AnvilBlock;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.RepairContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntReferenceHolder;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Based off of {@link RepairContainer}
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
	public int repairItemCountCost;
	private String itemName;
	private final IntReferenceHolder cost = IntReferenceHolder.standalone();
	public static final ITextComponent CONTAINER_TITLE = new TranslationTextComponent("container.lotrextended.repair");

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
				return (pPlayer.abilities.instabuild || pPlayer.experienceLevel >= ExtendedRepairContainer.this.cost.get()) && ExtendedRepairContainer.this.cost.get() > 0;
			}

			@Override
			public ItemStack onTake(PlayerEntity pPlayer, ItemStack pStack) {
				return ExtendedRepairContainer.this.onTake(pPlayer, pStack);
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

	protected ItemStack onTake(PlayerEntity pPlayer, ItemStack pInputItem) {
		if (!pPlayer.abilities.instabuild) {
			pPlayer.giveExperienceLevels(-this.cost.get());
		}

		//float breakChance = net.minecraftforge.common.ForgeHooks.onAnvilRepair(pPlayer, pInputItem, ExtendedRepairContainer.this.inputSlots.getItem(0), ExtendedRepairContainer.this.inputSlots.getItem(1));

		this.inputSlots.setItem(0, ItemStack.EMPTY);
		if (this.repairItemCountCost > 0) {
			ItemStack itemstack = this.inputSlots.getItem(1);
			if (!itemstack.isEmpty() && itemstack.getCount() > this.repairItemCountCost) {
				itemstack.shrink(this.repairItemCountCost);
				this.inputSlots.setItem(1, itemstack);
			} else {
				this.inputSlots.setItem(1, ItemStack.EMPTY);
			}
		} else {
			this.inputSlots.setItem(1, ItemStack.EMPTY);
		}

		this.cost.set(0);
		this.access.execute((world, pos) -> {
			BlockState blockstate = world.getBlockState(pos);
			if (!pPlayer.abilities.instabuild && blockstate.is(BlockTags.ANVIL) && pPlayer.getRandom().nextFloat() < 0.12f) { // breakChance set to default value
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
		ItemStack itemstack = this.inputSlots.getItem(0);
		this.cost.set(1);
		int extraCost = 0; // Total additional cost from various actions
		int repairBaseCost = 0;  // How many materials consumed in repair
		int renameCost = 0; // Cost for renaming
		if (itemstack.isEmpty()) {
			this.resultSlots.setItem(0, ItemStack.EMPTY);
			this.cost.set(0);
		} else {
			ItemStack resultItem = itemstack.copy();
			ItemStack materialItem = this.inputSlots.getItem(1);
			Map<Enchantment, Integer> mbaseEnchantments = EnchantmentHelper.getEnchantments(resultItem);
			repairBaseCost = + itemstack.getBaseRepairCost() + (materialItem.isEmpty() ? 0 : materialItem.getBaseRepairCost());
			this.repairItemCountCost = 0;
			boolean canPeformEnchanting = false;

			if (!materialItem.isEmpty()) {
				//if (!net.minecraftforge.common.ForgeHooks.onAnvilChange(this, itemstack, itemstack2, resultSlots, itemName, j, this.player)) return;
				canPeformEnchanting = materialItem.getItem() == Items.ENCHANTED_BOOK && !EnchantedBookItem.getEnchantments(materialItem).isEmpty();
				if (resultItem.isDamageableItem() && resultItem.getItem().isValidRepairItem(itemstack, materialItem)) {
					int repairAmount = Math.min(resultItem.getDamageValue(), resultItem.getMaxDamage() / 4);
					if (repairAmount <= 0) {
						this.resultSlots.setItem(0, ItemStack.EMPTY);
						this.cost.set(0);
						return;
					}

					int materialsUsed;
					for(materialsUsed = 0; repairAmount > 0 && materialsUsed < materialItem.getCount(); ++materialsUsed) {
						int newDamage = resultItem.getDamageValue() - repairAmount;
						resultItem.setDamageValue(newDamage);
						++extraCost;
						repairAmount = Math.min(resultItem.getDamageValue(), resultItem.getMaxDamage() / 4);
					}

					this.repairItemCountCost = materialsUsed;
				} else {
					if (!canPeformEnchanting && (resultItem.getItem() != materialItem.getItem() || !resultItem.isDamageableItem())) {
						this.resultSlots.setItem(0, ItemStack.EMPTY);
						this.cost.set(0);
						return;
					}

					if (resultItem.isDamageableItem() && !canPeformEnchanting) {
						int durabilityLeftBase = itemstack.getMaxDamage() - itemstack.getDamageValue();
						int durabilityLeftMaterial = materialItem.getMaxDamage() - materialItem.getDamageValue();
						int bonusDurability = durabilityLeftMaterial + resultItem.getMaxDamage() * 12 / 100;
						int combinedDurability = durabilityLeftBase + bonusDurability;
						int newDurability = resultItem.getMaxDamage() - combinedDurability;
						if (newDurability < 0) {
							newDurability = 0;
						}

						if (newDurability < resultItem.getDamageValue()) {
							resultItem.setDamageValue(newDurability);
							extraCost += 2;
						}
					}

					Map<Enchantment, Integer> materialEnchantments = EnchantmentHelper.getEnchantments(materialItem);
					boolean hasIncompatibleEnchant = false;
					boolean hasSuccessfulMerge = false;

					for(Enchantment enchantment : materialEnchantments.keySet()) {
						if (enchantment != null) {
							int baseLevel = mbaseEnchantments.getOrDefault(enchantment, 0);
							int materialLevel = materialEnchantments.get(enchantment);
							int newLevel = (baseLevel == materialLevel) ? materialLevel + 1 : Math.max(materialLevel, baseLevel);
							boolean canApply = enchantment.canEnchant(itemstack);
							if (this.player.abilities.instabuild || itemstack.getItem() == Items.ENCHANTED_BOOK) {
								canApply = true;
							}

							for(Enchantment existing : mbaseEnchantments.keySet()) {
								if (existing != enchantment && !enchantment.isCompatibleWith(existing)) {
									canApply = false;
									++extraCost;
								}
							}

							if (!canApply) {
								hasSuccessfulMerge = true;
							} else {
								hasIncompatibleEnchant = true;
								if (newLevel > enchantment.getMaxLevel()) {
									newLevel = enchantment.getMaxLevel();
								}

								mbaseEnchantments.put(enchantment, newLevel);
								int rarityCost = 0;
								switch(enchantment.getRarity()) {
								case COMMON:
									rarityCost = 1;
									break;
								case UNCOMMON:
									rarityCost = 2;
									break;
								case RARE:
									rarityCost = 4;
									break;
								case VERY_RARE:
									rarityCost = 8;
								}

								if (canPeformEnchanting) {
									rarityCost = Math.max(1, rarityCost / 2);
								}

								extraCost += rarityCost * newLevel;
								if (itemstack.getCount() > 1) {
									extraCost = 40; // Max cost if stack > 1
								}
							}
						}
					}

					if (hasSuccessfulMerge && !hasIncompatibleEnchant) {
						this.resultSlots.setItem(0, ItemStack.EMPTY);
						this.cost.set(0);
						return;
					}
				}
			}

			if (StringUtils.isBlank(this.itemName)) {
				if (itemstack.hasCustomHoverName()) {
					renameCost = 1;
					extraCost += renameCost;
					resultItem.resetHoverName();
				}
			} else if (!this.itemName.equals(itemstack.getHoverName().getString())) {
				renameCost = 1;
				extraCost += renameCost;
				resultItem.setHoverName(new StringTextComponent(this.itemName));
			}
			if (canPeformEnchanting && !resultItem.isBookEnchantable(materialItem)) resultItem = ItemStack.EMPTY;

			this.cost.set(repairBaseCost + extraCost);
			if (extraCost <= 0) {
				resultItem = ItemStack.EMPTY;
			}

			if (renameCost == extraCost && renameCost > 0 && this.cost.get() >= 40) {
				this.cost.set(39);
			}

			if (this.cost.get() >= 40 && !this.player.abilities.instabuild) {
				resultItem = ItemStack.EMPTY;
			}

			if (!resultItem.isEmpty()) {
				int newRepairCost = resultItem.getBaseRepairCost();
				if (!materialItem.isEmpty() && newRepairCost < materialItem.getBaseRepairCost()) {
					newRepairCost = materialItem.getBaseRepairCost();
				}

				if (renameCost != extraCost || renameCost == 0) {
					newRepairCost = calculateIncreasedRepairCost(newRepairCost);
				}

				resultItem.setRepairCost(newRepairCost);
				EnchantmentHelper.setEnchantments(mbaseEnchantments, resultItem);
			}

			this.resultSlots.setItem(0, resultItem);
			this.broadcastChanges();
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
			if (pIndex == 2) {
				if (!this.moveItemStackTo(itemstack1, 3, 39, true)) {
					return ItemStack.EMPTY;
				}

				slot.onQuickCraft(itemstack1, itemstack);
			} else if (pIndex != 0 && pIndex != 1) {
				if (pIndex >= 3 && pIndex < 39) {
					int i = this.shouldQuickMoveToAdditionalSlot(itemstack) ? 1 : 0;
					if (!this.moveItemStackTo(itemstack1, i, 2, false)) {
						return ItemStack.EMPTY;
					}
				}
			} else if (!this.moveItemStackTo(itemstack1, 3, 39, false)) {
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
		if (this.getSlot(2).hasItem()) {
			ItemStack itemstack = this.getSlot(2).getItem();
			if (StringUtils.isBlank(pNewName)) {
				itemstack.resetHoverName();
			} else {
				itemstack.setHoverName(new StringTextComponent(this.itemName));
			}
		}

		this.createResult();
	}

	/**
	 * Get's the maximum xp cost
	 * @return The cost for the operation
	 */
	@OnlyIn(Dist.CLIENT)
	public int getCost() {
		return this.cost.get();
	}

	public void setMaximumCost(int value) {
		this.cost.set(value);
	}
}