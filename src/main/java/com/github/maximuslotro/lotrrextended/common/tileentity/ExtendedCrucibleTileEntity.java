package com.github.maximuslotro.lotrrextended.common.tileentity;

import java.util.List;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.common.block.ExtendedCrucibleBlock;
import com.github.maximuslotro.lotrrextended.common.inventory.container.ExtendedCrucibleContainer;
import com.github.maximuslotro.lotrrextended.common.recipe.ExtendedCrucibleRecipe;
import com.github.maximuslotro.lotrrextended.common.recipe.ExtendedRecipeTypes;
import com.google.common.collect.Lists;

import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import lotr.common.init.ExtendedTileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IRecipeHelperPopulator;
import net.minecraft.inventory.IRecipeHolder;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

public class ExtendedCrucibleTileEntity extends LockableTileEntity implements ISidedInventory, IRecipeHolder, IRecipeHelperPopulator, ITickableTileEntity {
	private static final int[] SLOTS_FOR_UP = new int[]{0};
	private static final int[] SLOTS_FOR_DOWN = new int[]{1, 2, 3};
	private static final int[] SLOTS_FOR_SIDES = new int[]{1};
	// 0 - Input, 1 - Fuel, 2 & 3 - Output
	protected NonNullList<ItemStack> items = NonNullList.withSize(4, ItemStack.EMPTY);
	private int litTime;
	private int litDuration;
	private int meltingProgress;
	private int meltingTotalTime;
	protected final IIntArray dataAccess = new IIntArray() {
		@Override
		public int get(int pIndex) {
			switch(pIndex) {
				case 0:
					return ExtendedCrucibleTileEntity.this.litTime;
				case 1:
					return ExtendedCrucibleTileEntity.this.litDuration;
				case 2:
					return ExtendedCrucibleTileEntity.this.meltingProgress;
				case 3:
					return ExtendedCrucibleTileEntity.this.meltingTotalTime;
				default:
					return 0;
			}
		}

		@Override
		public void set(int pIndex, int pValue) {
			switch(pIndex) {
				case 0:
					ExtendedCrucibleTileEntity.this.litTime = pValue;
					break;
				case 1:
					ExtendedCrucibleTileEntity.this.litDuration = pValue;
					break;
				case 2:
					ExtendedCrucibleTileEntity.this.meltingProgress = pValue;
					break;
				case 3:
					ExtendedCrucibleTileEntity.this.meltingTotalTime = pValue;
			}

		}

		@Override
		public int getCount() {
			return 4;
		}
	};
	private final Object2IntOpenHashMap<ResourceLocation> recipesUsed = new Object2IntOpenHashMap<>();

	private ExtendedCrucibleTileEntity(TileEntityType<?> tileEntityType) {
		super(tileEntityType);
	}

	public ExtendedCrucibleTileEntity() {
		this(ExtendedTileEntities.CRUCIBLE.get());
	}

	private boolean isLit() {
		return this.litTime > 0;
	}

	@Override
	public void load(BlockState p_230337_1_, CompoundNBT p_230337_2_) {
		super.load(p_230337_1_, p_230337_2_);
		this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(p_230337_2_, this.items);
		this.litTime = p_230337_2_.getInt("BurnTime");
		this.meltingProgress = p_230337_2_.getInt("MeltTime");
		this.meltingTotalTime = p_230337_2_.getInt("MeltTimeTotal");
		this.litDuration = this.getBurnDuration(this.items.get(1));
		CompoundNBT compoundnbt = p_230337_2_.getCompound("RecipesUsed");
		for(String s : compoundnbt.getAllKeys()) {
			this.recipesUsed.put(new ResourceLocation(s), compoundnbt.getInt(s));
		}

	}

	@Override
	public CompoundNBT save(CompoundNBT pCompound) {
		super.save(pCompound);
		pCompound.putInt("BurnTime", this.litTime);
		pCompound.putInt("MeltTime", this.meltingProgress);
		pCompound.putInt("MeltTimeTotal", this.meltingTotalTime);
		ItemStackHelper.saveAllItems(pCompound, this.items);
		CompoundNBT compoundnbt = new CompoundNBT();
		this.recipesUsed.forEach((recipeId, count) -> {
			compoundnbt.putInt(recipeId.toString(), count);}
				);
		pCompound.put("RecipesUsed", compoundnbt);
		return pCompound;
	}

	@Override
	public void tick() {
		boolean startLit = this.isLit();
		boolean litChanged = false;
		if (this.isLit()) {
			--this.litTime;
		}

		if (!this.level.isClientSide) {
			ItemStack fuelStack = this.items.get(1);
			if (this.isLit() || !fuelStack.isEmpty() && !this.items.get(0).isEmpty()) {
				ExtendedCrucibleRecipe curcibleRecipe = this.level.getRecipeManager().getRecipeFor(ExtendedRecipeTypes.CRUCIBLE, this, this.level).orElse(null);
				if (!this.isLit() && this.canMelt(curcibleRecipe)) {
					this.litTime = this.getBurnDuration(fuelStack);
					this.litDuration = this.litTime;
					if (this.isLit()) {
						litChanged = true;
						if (fuelStack.hasContainerItem())
							this.items.set(1, fuelStack.getContainerItem());
						else
							if (!fuelStack.isEmpty()) {
								Item item = fuelStack.getItem();
								fuelStack.shrink(1);
								if (fuelStack.isEmpty()) {
									this.items.set(1, fuelStack.getContainerItem());
								}
							}
					}
				}

				if (this.isLit() && this.canMelt(curcibleRecipe)) {
					++this.meltingProgress;
					if (this.meltingProgress == this.meltingTotalTime) {
						this.meltingProgress = 0;
						this.meltingTotalTime = this.getTotalMeltingTime();
						this.melt(curcibleRecipe);
						litChanged = true;
					}
				} else {
					this.meltingProgress = 0;
				}
			} else if (!this.isLit() && this.meltingProgress > 0) {
				this.meltingProgress = MathHelper.clamp(this.meltingProgress - 2, 0, this.meltingTotalTime);
			}

			if (startLit != this.isLit()) {
				litChanged = true;
				this.level.setBlock(this.worldPosition, this.level.getBlockState(this.worldPosition).setValue(ExtendedCrucibleBlock.LIT, Boolean.valueOf(this.isLit())), 3);
			}
		}

		if (litChanged) {
			this.setChanged();
		}

	}

	protected boolean canMelt(@Nullable ExtendedCrucibleRecipe pRecipe) {
		if (!this.items.get(0).isEmpty() && pRecipe != null) {
			ItemStack resultFull = pRecipe.assemble1(this);
			ItemStack resultFraction = pRecipe.assemble2(this);
			if (resultFull.isEmpty() && resultFraction.isEmpty()) {
				// No result valid
				return false;
			}else if (resultFraction.isEmpty()) {
				// Only full result valid
				ItemStack outputFull = this.items.get(2);
				if (outputFull.isEmpty()) {
					return true;
				} else if (!outputFull.sameItem(resultFull)) {
					return false;
				} else if (outputFull.getCount() + resultFull.getCount() <= this.getMaxStackSize() 
						&& outputFull.getCount() + resultFull.getCount() <= outputFull.getMaxStackSize()) { 
					return true;
				} else {
					return outputFull.getCount() + resultFull.getCount() <= resultFull.getMaxStackSize(); //Forge sanity fallback
				}
			}else if (resultFull.isEmpty()) {
				// Only fraction result valid
				ItemStack outputFraction = this.items.get(3);
				if (outputFraction.isEmpty()) {
					return true;
				} else if (!outputFraction.sameItem(resultFraction)) {
					return false;
				} else if (outputFraction.getCount() + resultFraction.getCount() <= this.getMaxStackSize() 
						&& outputFraction.getCount() + resultFraction.getCount() <= outputFraction.getMaxStackSize()) { 
					return true;
				} else {
					return outputFraction.getCount() + resultFraction.getCount() <= resultFraction.getMaxStackSize(); //Forge sanity fallback
				}
			}else {
				// Both result valid
				ItemStack outputFull = this.items.get(2);
				ItemStack outputFraction = this.items.get(3);
				boolean fullValid = outputFull.isEmpty() || (outputFull.sameItem(resultFull) 
								&& outputFull.getCount() + resultFull.getCount() <= this.getMaxStackSize() 
								&& outputFull.getCount() + resultFull.getCount() <= outputFull.getMaxStackSize());
				boolean fractionValid = outputFraction.isEmpty() || (outputFraction.sameItem(resultFraction) 
					    		&& outputFraction.getCount() + resultFraction.getCount() <= this.getMaxStackSize() 
					    		&& outputFraction.getCount() + resultFraction.getCount() <= outputFraction.getMaxStackSize());
				return fullValid && fractionValid;
			}
		} else {
			return false;
		}
	}

	private void melt(@Nullable ExtendedCrucibleRecipe pRecipe) {
		if (pRecipe != null && this.canMelt(pRecipe)) {
			ItemStack inputStack = this.items.get(0);
			ItemStack outputStack1 = this.items.get(2);
			ItemStack outputStack2 = this.items.get(3);
			ItemStack resultStack1 = pRecipe.assemble1(this);
			ItemStack resultStack2 = pRecipe.assemble2(this);
			// calculated guessed offset for non-damaged items from legacy
			// hard to calculate as legacy presumably rounds up at the end, and not many recipes have values that will
			// be dropped lower than max when rounded up
			float countPercent1 = outputStack1.getCount() * 0.75F;
			float countPercent2 = outputStack2.getCount() * 0.75F;
			// further reduce output size by condition of damageable items
			if(inputStack.isDamageableItem()) {
				countPercent1 *= ((float)(inputStack.getMaxDamage()-inputStack.getDamageValue()))/inputStack.getMaxDamage();
				countPercent2 *= ((float)(inputStack.getMaxDamage()-inputStack.getDamageValue()))/inputStack.getMaxDamage();
			}
			// TODO instead of rounding, for applicable materials use nuggets instead of ingots as fraction percentage
			int count1 = Math.round(countPercent1);
			int count2 = Math.round(countPercent2);
			if(!resultStack1.isEmpty() && count1>0) {
				if (outputStack1.isEmpty()) {
					this.items.set(2, resultStack1.copy());
				} else if (outputStack1.getItem() == resultStack1.getItem()) {
					outputStack1.grow(resultStack1.getCount());
				}
			}
			if(!resultStack2.isEmpty() && count2>0) {
				if (outputStack2.isEmpty()) {
					this.items.set(3, resultStack2.copy());
				} else if (outputStack2.getItem() == resultStack2.getItem()) {
					outputStack2.grow(resultStack2.getCount());
				}
			}
			if (!this.level.isClientSide) {
				this.setRecipeUsed(pRecipe);
			}
			inputStack.shrink(1);
		}
	}

	protected int getBurnDuration(ItemStack pFuel) {
		if (pFuel.isEmpty()) {
			return 0;
		} else {
			return net.minecraftforge.common.ForgeHooks.getBurnTime(pFuel, IRecipeType.SMELTING);
		}
	}

	protected int getTotalMeltingTime() {
		return this.level.getRecipeManager().getRecipeFor(ExtendedRecipeTypes.CRUCIBLE, this, this.level).map(ExtendedCrucibleRecipe::getMeltingTime).orElse(200);
	}


	@Override
	public int[] getSlotsForFace(Direction pSide) {
		if (pSide == Direction.DOWN) {
			return SLOTS_FOR_DOWN;
		} else {
			return pSide == Direction.UP ? SLOTS_FOR_UP : SLOTS_FOR_SIDES;
		}
	}

	/**
	 * Returns true if automation can insert the given item in the given slot from the given side.
	 */
	@Override
	public boolean canPlaceItemThroughFace(int pIndex, ItemStack pItemStack, @Nullable Direction pDirection) {
		return this.canPlaceItem(pIndex, pItemStack);
	}

	/**
	 * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot. For guis use Slot.isItemValid
	 */
	@Override
	public boolean canPlaceItem(int pIndex, ItemStack pStack) {
		if (pIndex == 2 || pIndex == 3) {
			return false;
		} else if (pIndex != 1) {
			return true;
		} else {
			return getBurnDuration(pStack) > 0;
		}
	}

	/**
	 * Returns true if automation can extract the given item in the given slot from the given side.
	 */
	@Override
	public boolean canTakeItemThroughFace(int pIndex, ItemStack pStack, Direction pDirection) {
		if (pDirection == Direction.DOWN && pIndex == 1) {
			Item item = pStack.getItem();
			if (item != Items.BUCKET) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Returns the number of slots in the inventory.
	 */
	@Override
	public int getContainerSize() {
		return this.items.size();
	}

	@Override
	public boolean isEmpty() {
		for(ItemStack itemstack : this.items) {
			if (!itemstack.isEmpty()) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Returns the stack in the given slot.
	 */
	@Override
	public ItemStack getItem(int pIndex) {
		return this.items.get(pIndex);
	}

	/**
	 * Removes up to a specified number of items from an inventory slot and returns them in a new stack.
	 */
	@Override
	public ItemStack removeItem(int pIndex, int pCount) {
		return ItemStackHelper.removeItem(this.items, pIndex, pCount);
	}

	/**
	 * Removes a stack from the given slot and returns it.
	 */
	@Override
	public ItemStack removeItemNoUpdate(int pIndex) {
		return ItemStackHelper.takeItem(this.items, pIndex);
	}

	/**
	 * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
	 */
	@Override
	public void setItem(int pIndex, ItemStack pStack) {
		ItemStack itemstack = this.items.get(pIndex);
		boolean sameStack = !pStack.isEmpty() && pStack.sameItem(itemstack) && ItemStack.tagMatches(pStack, itemstack);
		this.items.set(pIndex, pStack);
		if (pStack.getCount() > this.getMaxStackSize()) {
			pStack.setCount(this.getMaxStackSize());
		}

		if (pIndex == 0 && !sameStack) {
			this.meltingTotalTime = this.getTotalMeltingTime();
			this.meltingProgress = 0;
			this.setChanged();
		}
	}

	/**
	 * Don't rename this method to canInteractWith due to conflicts with Container
	 */
	@Override
	public boolean stillValid(PlayerEntity pPlayer) {
		if (this.level.getBlockEntity(this.worldPosition) != this) {
			return false;
		} else {
			return pPlayer.distanceToSqr(this.worldPosition.getX() + 0.5D, this.worldPosition.getY() + 0.5D, this.worldPosition.getZ() + 0.5D) <= 64.0D;
		}
	}

	@Override
	public void clearContent() {
		this.items.clear();
	}

	@Override
	public void setRecipeUsed(@Nullable IRecipe<?> pRecipe) {
		if (pRecipe != null) {
			ResourceLocation resourcelocation = pRecipe.getId();
			this.recipesUsed.addTo(resourcelocation, 1);
		}

	}

	@Override
	@Nullable
	public IRecipe<?> getRecipeUsed() {
		return null;
	}

	@Override
	public void awardUsedRecipes(PlayerEntity pPlayer) {
	}

	public void awardUsedRecipesAndPopExperience(PlayerEntity pPlayer) {
		List<IRecipe<?>> list = this.getRecipesToAwardAndPopExperience(pPlayer.level, pPlayer.position());
		pPlayer.awardRecipes(list);
		this.recipesUsed.clear();
	}

	public List<IRecipe<?>> getRecipesToAwardAndPopExperience(World level, Vector3d position) {
		List<IRecipe<?>> list = Lists.newArrayList();

		for(Entry<ResourceLocation> entry : this.recipesUsed.object2IntEntrySet()) {
			level.getRecipeManager().byKey(entry.getKey()).ifPresent((recipe) -> {
				list.add(recipe);
				createExperience(level, position, entry.getIntValue(), ((ExtendedCrucibleRecipe)recipe).getExperience());
			});
		}

		return list;
	}

	private static void createExperience(World level, Vector3d position, int recipeCount, float recipeExp) {
		int i = MathHelper.floor(recipeCount * recipeExp);
		float f = MathHelper.frac(recipeCount * recipeExp);
		if (f != 0.0F && Math.random() < f) {
			++i;
		}

		while(i > 0) {
			int j = ExperienceOrbEntity.getExperienceValue(i);
			i -= j;
			level.addFreshEntity(new ExperienceOrbEntity(level, position.x, position.y, position.z, j));
		}

	}

	@Override
	public void fillStackedContents(RecipeItemHelper pHelper) {
		for(ItemStack itemstack : this.items) {
			pHelper.accountStack(itemstack);
		}
	}

	LazyOptional<? extends IItemHandler>[] handlers = SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
		if (!this.remove && facing != null && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			if (facing == Direction.UP)
				return handlers[0].cast();
			else if (facing == Direction.DOWN)
				return handlers[1].cast();
			else
				return handlers[2].cast();
		}
		return super.getCapability(capability, facing);
	}

	@Override
	protected void invalidateCaps() {
		super.invalidateCaps();
		for (LazyOptional<? extends IItemHandler> handler : handlers)
			handler.invalidate();
	}

	@Override
	protected ITextComponent getDefaultName() {
		return new TranslationTextComponent("container.lotrextended.crucible");
	}

	@Override
	protected Container createMenu(int pId, PlayerInventory pPlayer) {
		return new ExtendedCrucibleContainer(pId, pPlayer, this, this.dataAccess, null);
	}
}