package com.github.maximuslotro.lotrrextended.common.tileentity;

import java.util.List;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.common.block.ExtendedMillstoneBlock;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedTags;
import com.github.maximuslotro.lotrrextended.common.recipe.ExtendedMillstoneRecipe;
import com.github.maximuslotro.lotrrextended.common.recipe.ExtendedRecipes;
import com.google.common.collect.Lists;

import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
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
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.ItemTags;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableTileEntity;
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

public class ExtendedMillstoneTileEntity extends LockableTileEntity implements ISidedInventory, IRecipeHolder, IRecipeHelperPopulator, ITickableTileEntity {
	private static final int[] SLOTS_FOR_UP = new int[]{0};
	private static final int[] SLOTS_FOR_DOWN = new int[]{2, 1};
	private static final int[] SLOTS_FOR_SIDES = new int[]{1};
	// 0 - Input, 1- Fuel/Additive, 2 -Output
	protected NonNullList<ItemStack> items = NonNullList.withSize(3, ItemStack.EMPTY);
	private int millingProgress;
	private int millingTotalTime;
	protected final IIntArray dataAccess = new IIntArray() {
		@Override
		public int get(int pIndex) {
			switch(pIndex) {
			case 0:
				return ExtendedMillstoneTileEntity.this.millingProgress;
			case 1:
				return ExtendedMillstoneTileEntity.this.millingTotalTime;
			default:
				return 0;
			}
		}

		@Override
		public void set(int pIndex, int pValue) {
			switch(pIndex) {
			case 0:
				ExtendedMillstoneTileEntity.this.millingProgress = pValue;
				break;
			case 1:
				ExtendedMillstoneTileEntity.this.millingTotalTime = pValue;
			}

		}

		@Override
		public int getCount() {
			return 2;
		}
	};
	private final Object2IntOpenHashMap<ResourceLocation> recipesUsed = new Object2IntOpenHashMap<>();
	protected final IRecipeType<ExtendedMillstoneRecipe> recipeType;
	LazyOptional<? extends IItemHandler>[] handlers = SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);

	public ExtendedMillstoneTileEntity() {
		super(null);
		this.recipeType = ExtendedRecipes.MILLSTONE;
	}

	private static boolean isNeverAFurnaceFuel(Item pItem) {
		return ItemTags.NON_FLAMMABLE_WOOD.contains(pItem);
	}

	private boolean isPowered() {
		return this.getBlockState().getValue(ExtendedMillstoneBlock.POWERED);
	}

	@Override
	public void load(BlockState p_230337_1_, CompoundNBT p_230337_2_) { //TODO: MARK
		super.load(p_230337_1_, p_230337_2_);
		this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(p_230337_2_, this.items);
		this.millingProgress = p_230337_2_.getInt("MillingTime");
		this.millingTotalTime = p_230337_2_.getInt("MillingTimeTotal");
		CompoundNBT compoundnbt = p_230337_2_.getCompound("RecipesUsed");

		for(String s : compoundnbt.getAllKeys()) {
			this.recipesUsed.put(new ResourceLocation(s), compoundnbt.getInt(s));
		}

	}

	@Override
	public CompoundNBT save(CompoundNBT pCompound) {
		super.save(pCompound);
		pCompound.putInt("MillingTime", this.millingProgress);
		pCompound.putInt("MillingTimeTotal", this.millingTotalTime);
		ItemStackHelper.saveAllItems(pCompound, this.items);
		CompoundNBT compoundnbt = new CompoundNBT();
		this.recipesUsed.forEach((p_235643_1_, p_235643_2_) -> {
			compoundnbt.putInt(p_235643_1_.toString(), p_235643_2_);
		});
		pCompound.put("RecipesUsed", compoundnbt);
		return pCompound;
	}

	@Override
	public void tick() {
		if (!this.level.isClientSide) {
			//ItemStack itemstack = this.items.get(1);
			if (this.isPowered() && !this.items.get(0).isEmpty()) {
				IRecipe<?> irecipe = this.level.getRecipeManager().getRecipeFor(this.recipeType, this, this.level).orElse(null);
				if (this.isPowered() && this.canMill(irecipe)) {
					++this.millingProgress;
					if (this.millingProgress == this.millingTotalTime) {
						this.millingProgress = 0;
						this.millingTotalTime = this.getTotalMillingTime();
						this.mill(irecipe);
						// Handle Items that have replaced use items
//						if (itemstack.hasContainerItem()) {
//							this.items.set(1, itemstack.getContainerItem());
//						} else if (itemstack.getItem() instanceof VesselDrinkItem) {
//							this.items.set(1, ((VesselDrinkItem)itemstack.getItem()).getContainerItem(itemstack));
//						} else if (itemstack.getItem() instanceof PotionItem) {
//							this.items.set(1, new ItemStack(Items.GLASS_BOTTLE));
//						} else if (!itemstack.isEmpty()) {
//							Item item = itemstack.getItem();
//							itemstack.shrink(1);
//							if (itemstack.isEmpty()) {
//								this.items.set(1, itemstack.getContainerItem());
//							}
//						}
					}
				} else {
					this.millingProgress = 0;
				}
			} else if (!this.isPowered() && this.millingProgress > 0) {
				this.millingProgress = MathHelper.clamp(this.millingProgress - 2, 0, this.millingTotalTime);
			}
		}
	}

	@SuppressWarnings("unchecked")
	protected boolean canMill(@Nullable IRecipe<?> recipe) {
		if (!this.items.get(0).isEmpty() && recipe != null) {
			ItemStack itemstack = ((IRecipe<ISidedInventory>) recipe).assemble(this);
			if (itemstack.isEmpty()) {
				return false;
			} else {
				ItemStack itemstack1 = this.items.get(2);
				if (itemstack1.isEmpty()) {
					return true;
				} else if (!itemstack1.sameItem(itemstack)) {
					return false;
				} else if (itemstack1.getCount() + itemstack.getCount() <= this.getMaxStackSize() && itemstack1.getCount() + itemstack.getCount() <= itemstack1.getMaxStackSize()) { // Forge fix: make furnace respect stack sizes in furnace recipes
					return true;
				} else {
					return itemstack1.getCount() + itemstack.getCount() <= itemstack.getMaxStackSize(); // Forge fix: make furnace respect stack sizes in furnace recipes
				}
			}
		} else {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	private void mill(@Nullable IRecipe<?> recipe) {
		if (recipe != null && this.canMill(recipe)) {
			ItemStack itemstack = this.items.get(0);
			ItemStack itemstack1 = ((IRecipe<ISidedInventory>) recipe).assemble(this);
			ItemStack itemstack2 = this.items.get(2);
			if (itemstack2.isEmpty()) {
				this.items.set(2, itemstack1.copy());
			} else if (itemstack2.getItem() == itemstack1.getItem()) {
				itemstack2.grow(itemstack1.getCount());
			}

			if (!this.level.isClientSide) {
				this.setRecipeUsed(recipe);
			}

			//if (itemstack.getItem() == Blocks.WET_SPONGE.asItem() && !this.items.get(1).isEmpty() && this.items.get(1).getItem() == Items.BUCKET) {
			//	this.items.set(1, new ItemStack(Items.WATER_BUCKET));
			//}

			itemstack.shrink(1);
		}
	}

	protected int getTotalMillingTime() {
		return this.level.getRecipeManager().getRecipeFor(this.recipeType, this, this.level).map(ExtendedMillstoneRecipe::getMillingTime).orElse(200);
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
		if (pIndex == 2) {
			return false;
		} else if (pIndex != 1) {
			return true;
		} else {
			ItemStack itemstack = this.items.get(1);
			return ExtendedTags.Items.MILLSTONE_LUBRICANTS.contains(itemstack.getItem());
		}
	}

	/**
	 * Returns true if automation can extract the given item in the given slot from the given side.
	 */
	@Override
	public boolean canTakeItemThroughFace(int pIndex, ItemStack pStack, Direction pDirection) {
		if (pDirection == Direction.DOWN && pIndex == 1) {
			Item item = pStack.getItem();
			if (ExtendedTags.Items.MILLSTONE_LUBRICANTS.contains(item)) {
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
		boolean flag = !pStack.isEmpty() && pStack.sameItem(itemstack) && ItemStack.tagMatches(pStack, itemstack);
		this.items.set(pIndex, pStack);
		if (pStack.getCount() > this.getMaxStackSize()) {
			pStack.setCount(this.getMaxStackSize());
		}

		if (pIndex == 0 && !flag) {
			this.millingTotalTime = this.getTotalMillingTime();
			this.millingProgress = 0;
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

	public void awardUsedRecipesAndPopExperience(PlayerEntity p_235645_1_) {
		List<IRecipe<?>> list = this.getRecipesToAwardAndPopExperience(p_235645_1_.level, p_235645_1_.position());
		p_235645_1_.awardRecipes(list);
		this.recipesUsed.clear();
	}

	public List<IRecipe<?>> getRecipesToAwardAndPopExperience(World p_235640_1_, Vector3d p_235640_2_) {
		List<IRecipe<?>> list = Lists.newArrayList();

		for(Entry<ResourceLocation> entry : this.recipesUsed.object2IntEntrySet()) {
			p_235640_1_.getRecipeManager().byKey(entry.getKey()).ifPresent((p_235642_4_) -> {
				list.add(p_235642_4_);
				createExperience(p_235640_1_, p_235640_2_, entry.getIntValue(), ((ExtendedMillstoneRecipe)p_235642_4_).getExperience());
			});
		}

		return list;
	}

	private static void createExperience(World p_235641_0_, Vector3d p_235641_1_, int p_235641_2_, float p_235641_3_) {
		int i = MathHelper.floor(p_235641_2_ * p_235641_3_);
		float f = MathHelper.frac(p_235641_2_ * p_235641_3_);
		if (f != 0.0F && Math.random() < f) {
			++i;
		}

		while(i > 0) {
			int j = ExperienceOrbEntity.getExperienceValue(i);
			i -= j;
			p_235641_0_.addFreshEntity(new ExperienceOrbEntity(p_235641_0_, p_235641_1_.x, p_235641_1_.y, p_235641_1_.z, j));
		}

	}

	@Override
	public void fillStackedContents(RecipeItemHelper pHelper) {
		for(ItemStack itemstack : this.items) {
			pHelper.accountStack(itemstack);
		}

	}

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
		return new TranslationTextComponent("container.lotrextended.millstone");
	}

	@Override
	protected Container createMenu(int pId, PlayerInventory pPlayer) {
		// TODO Auto-generated method stub
		return null;
	}
}