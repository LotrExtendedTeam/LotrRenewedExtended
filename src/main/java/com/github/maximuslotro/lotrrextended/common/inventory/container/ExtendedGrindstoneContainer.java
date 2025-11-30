package com.github.maximuslotro.lotrrextended.common.inventory.container;

import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedContainerTypes;
import com.github.maximuslotro.lotrrextended.common.modifiers.ExtendedModifiers;
import com.github.maximuslotro.lotrrextended.common.utils.ExtendedModifierUtils;

import io.netty.buffer.Unpooled;
import lotr.common.item.ExtendedBarrowBlade;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

/**
 * Based off of {@link net.minecraft.inventory.container.GrindstoneContainer}
 * 
 * */
public class ExtendedGrindstoneContainer extends Container {
	public static final ITextComponent CONTAINER_TITLE = new TranslationTextComponent("container.grindstone_title");
	/** The inventory slot that stores the output of the crafting recipe. */
	private final IInventory resultSlots = new CraftResultInventory();
	private final IInventory repairSlots = new Inventory(2) {
		/**
		 * For tile entities, ensures the chunk containing the tile entity is saved to disk later - the game won't think
		 * it hasn't changed and skip it.
		 */
		@Override
		public void setChanged() {
			super.setChanged();
			ExtendedGrindstoneContainer.this.slotsChanged(this);
		}
	};
	private final IWorldPosCallable access;

	public ExtendedGrindstoneContainer(int pContainerId, PlayerInventory pPlayerInventory) {
		this(pContainerId, pPlayerInventory, IWorldPosCallable.NULL, new PacketBuffer(Unpooled.buffer()));
	}

	public ExtendedGrindstoneContainer(int pContainerId, PlayerInventory pPlayerInventory, PacketBuffer extraData) {
		this(pContainerId, pPlayerInventory, IWorldPosCallable.NULL, extraData);
	}

	public ExtendedGrindstoneContainer(int pContainerId, PlayerInventory pPlayerInventory, IWorldPosCallable pAccess) {
		this(pContainerId, pPlayerInventory, pAccess, new PacketBuffer(Unpooled.buffer()));
	}

	public ExtendedGrindstoneContainer(int pContainerId, PlayerInventory pPlayerInventory, final IWorldPosCallable pAccess, PacketBuffer extraData) {
		super(ExtendedContainerTypes.EXTENDED_GRINDSTONE.get(), pContainerId);
		this.access = pAccess;
		this.addSlot(new Slot(this.repairSlots, 0, 49, 19) {
			/**
			 * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
			 */
			@Override
			public boolean mayPlace(ItemStack pStack) {
				return pStack.isDamageableItem() || pStack.getItem() == Items.ENCHANTED_BOOK || pStack.isEnchanted() || ExtendedModifierUtils.hasModifiers(pStack);
			}
		});
		this.addSlot(new Slot(this.repairSlots, 1, 49, 40) {
			/**
			 * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
			 */
			@Override
			public boolean mayPlace(ItemStack pStack) {
				return pStack.isDamageableItem() || pStack.getItem() == Items.ENCHANTED_BOOK || pStack.isEnchanted() || ExtendedModifierUtils.hasModifiers(pStack);
			}
		});
		this.addSlot(new Slot(this.resultSlots, 2, 129, 34) {
			/**
			 * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
			 */
			@Override
			public boolean mayPlace(ItemStack pStack) {
				return false;
			}

			@Override
			public ItemStack onTake(PlayerEntity pPlayer, ItemStack pStack) {
				pAccess.execute((p_216944_1_, p_216944_2_) -> {
					int xpAmount = this.getExperienceAmount(p_216944_1_);
					while(xpAmount > 0) {
						int teiredAmount = ExperienceOrbEntity.getExperienceValue(xpAmount);
						xpAmount -= teiredAmount;
						p_216944_1_.addFreshEntity(new ExperienceOrbEntity(p_216944_1_, p_216944_2_.getX(), p_216944_2_.getY() + 0.5D, p_216944_2_.getZ() + 0.5D, teiredAmount));
					}

					p_216944_1_.levelEvent(1042, p_216944_2_, 0);
				});
				ExtendedGrindstoneContainer.this.repairSlots.setItem(0, ItemStack.EMPTY);
				ExtendedGrindstoneContainer.this.repairSlots.setItem(1, ItemStack.EMPTY);
				return pStack;
			}

			/**
			 * Returns the total amount of XP stored in all of the input slots of this container. The return value is
			 * randomized, so that it returns between 50% and 100% of the total XP.
			 */
			private int getExperienceAmount(World p_216942_1_) {
				int l = 0;
				l = l + this.getExperienceFromItem(ExtendedGrindstoneContainer.this.repairSlots.getItem(0));
				l = l + this.getExperienceFromItem(ExtendedGrindstoneContainer.this.repairSlots.getItem(1));
				if (l > 0) {
					int i1 = (int)Math.ceil((double)l / 2.0D);
					return i1 + p_216942_1_.random.nextInt(i1);
				} else {
					return 0;
				}
			}

			/**
			 * Returns the total amount of XP stored in the enchantments of this stack.
			 */
			private int getExperienceFromItem(ItemStack p_216943_1_) {
				int l = 0;
				Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(p_216943_1_);
				for(Entry<Enchantment, Integer> entry : map.entrySet()) {
					Enchantment enchantment = entry.getKey();
					Integer integer = entry.getValue();
					if (!enchantment.isCurse()) {
						l += enchantment.getMinCost(integer);
					}
				}
				return l;
			}
		});

		for(int i = 0; i < 3; ++i) {
			for(int j = 0; j < 9; ++j) {
				this.addSlot(new Slot(pPlayerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for(int k = 0; k < 9; ++k) {
			this.addSlot(new Slot(pPlayerInventory, k, 8 + k * 18, 142));
		}

	}

	/**
	 * Callback for when the crafting matrix is changed.
	 */
	@Override
	public void slotsChanged(IInventory pInventory) {
		super.slotsChanged(pInventory);
		if (pInventory == this.repairSlots) {
			this.createResult();
		}

	}

	private void createResult() {
		ItemStack leftInput = this.repairSlots.getItem(0);
		ItemStack rightInput = this.repairSlots.getItem(1);
		boolean hasAnyInput = !leftInput.isEmpty() || !rightInput.isEmpty();
		boolean hasBothInputs = !leftInput.isEmpty() && !rightInput.isEmpty();
		if (!hasAnyInput) {
			this.resultSlots.setItem(0, ItemStack.EMPTY);
		} else {
			boolean hasNonEnchantedItem = (!leftInput.isEmpty() && leftInput.getItem() != Items.ENCHANTED_BOOK && !leftInput.isEnchanted() && !ExtendedModifierUtils.hasModifiers(leftInput)) 
				|| (!rightInput.isEmpty() && rightInput.getItem() != Items.ENCHANTED_BOOK && !rightInput.isEnchanted() && !ExtendedModifierUtils.hasModifiers(rightInput));
			if (leftInput.getCount() > 1 || rightInput.getCount() > 1 || (!hasBothInputs && hasNonEnchantedItem)) {
				this.resultSlots.setItem(0, ItemStack.EMPTY);
				this.broadcastChanges();
				return;
			}
			int resultItemCount = 1;
			int newDamageValue;
			ItemStack resultItem;
			if (hasBothInputs) {
				if (leftInput.getItem() != rightInput.getItem()) {
					this.resultSlots.setItem(0, ItemStack.EMPTY);
					this.broadcastChanges();
					return;
				}
				int leftRemainingDurability = leftInput.getMaxDamage() - leftInput.getDamageValue();
				int rightRemainingDurability = leftInput.getMaxDamage() - rightInput.getDamageValue();
				int combinedDurability = leftRemainingDurability + rightRemainingDurability + leftInput.getMaxDamage() * 5 / 100;
				newDamageValue = Math.max(leftInput.getMaxDamage() - combinedDurability, 0);
				resultItem = this.mergeEnchants(leftInput, rightInput); //But not modifiers as we don't do that for modifiers
				if (!resultItem.isRepairable()) {
					newDamageValue = leftInput.getDamageValue();
				}
				if (!resultItem.isDamageableItem() || !resultItem.isRepairable()) {
					if (!ItemStack.matches(leftInput, rightInput)) {
						this.resultSlots.setItem(0, ItemStack.EMPTY);
						this.broadcastChanges();
						return;
					}
					resultItemCount = 2;
				}
			} else {
				newDamageValue = !leftInput.isEmpty() ? leftInput.getDamageValue() : rightInput.getDamageValue();
				resultItem = !leftInput.isEmpty() ? leftInput : rightInput;
			}
			this.resultSlots.setItem(0, this.removeNonCurses(resultItem, newDamageValue, resultItemCount));
		}
		this.broadcastChanges();
	}

	private ItemStack mergeEnchants(ItemStack pCopyTo, ItemStack pCopyFrom) {
		ItemStack itemstack = pCopyTo.copy();
		Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(pCopyFrom);
		for(Entry<Enchantment, Integer> entry : map.entrySet()) {
			Enchantment enchantment = entry.getKey();
			if (!enchantment.isCurse() || EnchantmentHelper.getItemEnchantmentLevel(enchantment, itemstack) == 0) {
				itemstack.enchant(enchantment, entry.getValue());
			}
		}
		return itemstack;
	}

	/**
	 * Removes all enchantments from the {@link ItemStack}. Note that the curses are not removed.
	 * @param pStack Stack to remove data from
	 * @param pDamage Damage value to set
	 * @param pCount Stack count to set
	 * @return A result itemstack
	 */
	private ItemStack removeNonCurses(ItemStack pStack, int pDamage, int pCount) {
		ItemStack itemstack = pStack.copy();
		itemstack.removeTagKey("Enchantments");
		itemstack.removeTagKey("StoredEnchantments");
		if (pDamage > 0) {
			itemstack.setDamageValue(pDamage);
		} else {
			itemstack.removeTagKey("Damage");
		}
		// Extended Start
		itemstack.removeTagKey(ExtendedModifierUtils.NBTMODIFIER);
		// Preserve Wight Bane on barrow blades
		if(pStack.getItem() instanceof ExtendedBarrowBlade) {
			ExtendedModifierUtils.addModifier(pStack, ExtendedModifiers.WIGHT_BANE);
		}
		// Extended End
		itemstack.setCount(pCount);
		// TODO add handling for cursed modifiers in the future
		Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(pStack).entrySet().stream().filter((p_217012_0_) -> {
			return p_217012_0_.getKey().isCurse();
		}).collect(Collectors.toMap(Entry::getKey, Entry::getValue));
		EnchantmentHelper.setEnchantments(map, itemstack);
		itemstack.setRepairCost(0);
		if (itemstack.getItem() == Items.ENCHANTED_BOOK && map.size() == 0) {
			itemstack = new ItemStack(Items.BOOK);
			if (pStack.hasCustomHoverName()) {
				itemstack.setHoverName(pStack.getHoverName());
			}
		}
		for(int i = 0; i < map.size(); ++i) {
			itemstack.setRepairCost(itemstack.getBaseRepairCost()*2 +1);
		}
		return itemstack;
	}

	/**
	 * Called when the container is closed.
	 */
	@Override
	public void removed(PlayerEntity pPlayer) {
		super.removed(pPlayer);
		this.access.execute((p_217009_2_, p_217009_3_) -> {
			this.clearContainer(pPlayer, p_217009_2_, this.repairSlots);
		});
	}

	/**
	 * Determines whether supplied player can use this container
	 */
	@Override
	public boolean stillValid(PlayerEntity pPlayer) {
		return stillValid(this.access, pPlayer, Blocks.GRINDSTONE);
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
			ItemStack itemstack2 = this.repairSlots.getItem(0);
			ItemStack itemstack3 = this.repairSlots.getItem(1);
			if (pIndex == 2) {
				if (!this.moveItemStackTo(itemstack1, 3, 39, true)) {
					return ItemStack.EMPTY;
				}

				slot.onQuickCraft(itemstack1, itemstack);
			} else if (pIndex != 0 && pIndex != 1) {
				if (!itemstack2.isEmpty() && !itemstack3.isEmpty()) {
					if (pIndex >= 3 && pIndex < 30) {
						if (!this.moveItemStackTo(itemstack1, 30, 39, false)) {
							return ItemStack.EMPTY;
						}
					} else if (pIndex >= 30 && pIndex < 39 && !this.moveItemStackTo(itemstack1, 3, 30, false)) {
						return ItemStack.EMPTY;
					}
				} else if (!this.moveItemStackTo(itemstack1, 0, 2, false)) {
					return ItemStack.EMPTY;
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
}