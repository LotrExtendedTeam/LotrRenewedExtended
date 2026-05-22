package com.github.maximuslotro.lotrrextended.common.tileentity;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.common.block.ExtendedStoneGrinderBlock;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedTags;
import com.github.maximuslotro.lotrrextended.common.inventory.container.ExtendedStoneGrinderContainer;
import com.github.maximuslotro.lotrrextended.common.recipe.ExtendeStoneGrinderRecipe;
import com.github.maximuslotro.lotrrextended.common.recipe.ExtendedRecipeTypes;
import com.google.common.collect.Lists;

import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import lotr.common.init.ExtendedTileEntities;
import lotr.common.item.VesselDrinkItem;
import net.minecraft.block.Block;
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
import net.minecraft.item.PotionItem;
import net.minecraft.item.crafting.IRecipe;
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

public class ExtendedStoneGrinderTileEntity extends LockableTileEntity implements ISidedInventory, IRecipeHolder, IRecipeHelperPopulator, ITickableTileEntity {
	private static final int[] SLOTS_FOR_UP = new int[]{0};
	private static final int[] SLOTS_FOR_DOWN = new int[]{2, 1};
	private static final int[] SLOTS_FOR_SIDES = new int[]{0, 1};
	// 0 - Input, 1- Fuel/Additive, 2 -Output
	protected NonNullList<ItemStack> items = NonNullList.withSize(3, ItemStack.EMPTY);
	private int grindingProgress = 0;
	private int grindingTotalTime = 0;
	private boolean usingLubrication = false;
	protected final IIntArray dataAccess = new IIntArray() {
		@Override
		public int get(int pIndex) {
			switch(pIndex) {
			case 0:
				return ExtendedStoneGrinderTileEntity.this.grindingProgress;
			case 1:
				return ExtendedStoneGrinderTileEntity.this.grindingTotalTime;
			case 2:
				return ExtendedStoneGrinderTileEntity.this.usingLubrication ? 1 : 0;
			default:
				return 0;
			}
		}
		@Override
		public void set(int pIndex, int pValue) {
			switch(pIndex) {
			case 0:
				ExtendedStoneGrinderTileEntity.this.grindingProgress = pValue;
				break;
			case 1:
				ExtendedStoneGrinderTileEntity.this.grindingTotalTime = pValue;
				break;
			case 2:
				ExtendedStoneGrinderTileEntity.this.usingLubrication = pValue != 0;
			}
		}
		@Override
		public int getCount() {
			return 3;
		}
	};
	private final Object2IntOpenHashMap<ResourceLocation> recipesUsed = new Object2IntOpenHashMap<>();
	LazyOptional<? extends IItemHandler>[] handlers = SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);
	public double spin = 0;
	public double spinO = 0;
	public int isOpen;

	private ExtendedStoneGrinderTileEntity(TileEntityType<?> tileEntityType) {
		super(tileEntityType);
	}

	public ExtendedStoneGrinderTileEntity() {
		this(ExtendedTileEntities.STONE_GRINDER.get());
	}

	private boolean isPowered() {
		return this.getBlockState().getValue(ExtendedStoneGrinderBlock.POWERED);
	}

	@Override
	public void load(BlockState p_230337_1_, CompoundNBT p_230337_2_) { //TODO: MARK
		super.load(p_230337_1_, p_230337_2_);
		this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(p_230337_2_, this.items);
		this.grindingProgress = p_230337_2_.getInt("GrindingTime");
		this.grindingTotalTime = p_230337_2_.getInt("GrindingTimeTotal");
		CompoundNBT compoundnbt = p_230337_2_.getCompound("RecipesUsed");

		for(String s : compoundnbt.getAllKeys()) {
			this.recipesUsed.put(new ResourceLocation(s), compoundnbt.getInt(s));
		}

	}

	@Override
	public CompoundNBT save(CompoundNBT pCompound) {
		super.save(pCompound);
		pCompound.putInt("GrindingTime", this.grindingProgress);
		pCompound.putInt("GrindingTimeTotal", this.grindingTotalTime);
		ItemStackHelper.saveAllItems(pCompound, this.items);
		CompoundNBT compoundnbt = new CompoundNBT();
		this.recipesUsed.forEach((p_235643_1_, p_235643_2_) -> compoundnbt.putInt(p_235643_1_.toString(), p_235643_2_));
		pCompound.put("RecipesUsed", compoundnbt);
		return pCompound;
	}

	@Override
	public void tick() {
		if (!this.level.isClientSide) {
			if(canLubeItem(this.items.get(1))){
				if (this.grindingProgress<100 && !this.usingLubrication) {
					usingLubrication=true;
				}
			}else {
				usingLubrication=false;
			}
			if (this.isPowered() && !this.items.get(0).isEmpty()) {
				ExtendeStoneGrinderRecipe irecipe = this.level.getRecipeManager().getRecipeFor(ExtendedRecipeTypes.STONE_GRINDER, this, this.level).orElse(null);
				if (this.isPowered() && this.canGrind(irecipe)) {
					++this.grindingProgress;
					if (this.grindingProgress == this.grindingTotalTime) {
						this.grindingProgress = 0;
						this.grindingTotalTime = this.getTotalMillingTime();
						this.grind(irecipe);
					}
				} else {
					this.grindingProgress = 0;
				}
			} else if (!this.isPowered() && this.grindingProgress > 0) {
				this.grindingProgress = MathHelper.clamp(this.grindingProgress - 2, 0, this.grindingTotalTime);
			}
		}
		if(this.level.isClientSide()) {
			if(this.getLevel()!=null && this.getLevel().getBlockState(this.getBlockPos()).getValue(ExtendedStoneGrinderBlock.POWERED)) {
				this.spinO = this.spin;
				this.spin = (this.spin + 0.01) % 360.0D;
			}else {
				this.spinO = this.spin = 0;
			}
		}
	}

	@Override
	public boolean triggerEvent(int pId, int pType) {
		if (pId == 1) {
			this.isOpen = pType;
			return true;
		} else {
			return super.triggerEvent(pId, pType);
		}
	}

	@Override
	public void startOpen(PlayerEntity pPlayer) {
		if (!pPlayer.isSpectator()) {
			if (this.isOpen < 0) {
				this.isOpen = 0;
			}
			// TODO fix opening not syncing
			++this.isOpen;
			this.signalOpenCount();
		}

	}

	@Override
	public void stopOpen(PlayerEntity pPlayer) {
		if (!pPlayer.isSpectator()) {
			--this.isOpen;
			this.signalOpenCount();
		}

	}
	protected void signalOpenCount() {
		Block block = this.getBlockState().getBlock();
		if (block instanceof ExtendedStoneGrinderBlock) {
			this.level.blockEvent(this.worldPosition, block, 1, this.isOpen);
			this.level.updateNeighborsAt(this.worldPosition, block);
		}
	}

	public boolean canLubeItem(ItemStack pStack) {
		return ExtendedTags.Items.STONE_GRINDER_LUBRICANTS.contains(pStack.getItem());
	}

	protected boolean canGrind(@Nullable ExtendeStoneGrinderRecipe recipe) {
		if (!this.items.get(0).isEmpty() && recipe != null) {
			ItemStack itemstack = recipe.assemble(this);
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

	private void grind(@Nullable ExtendeStoneGrinderRecipe recipe) {
		if (recipe != null && this.canGrind(recipe)) {
			ItemStack inputStack = this.items.get(0);
			ItemStack resultStack = recipe.assemble(this);
			ItemStack outputStack = this.items.get(2);
			boolean needsLubeRemoving = false;
			Random rand = this.level.random;
			if(recipe.getBreakChance()>0.0f) {
				if(rand.nextFloat()<recipe.getBreakChance()&&!this.usingLubrication) {
					inputStack.shrink(1);
					return;
				}
				needsLubeRemoving = true;

			}
			resultStack.setCount(recipe.getCount(rand));
			if (outputStack.isEmpty()) {
				this.items.set(2, resultStack.copy());
			} else if (ItemStack.isSame(resultStack, outputStack) && ItemStack.tagMatches(resultStack, outputStack)) {
				outputStack.grow(resultStack.getCount());
			}

			if (!this.level.isClientSide) {
				this.setRecipeUsed(recipe);
			}

			if(this.usingLubrication && needsLubeRemoving) {
				ItemStack itemstack3 = this.items.get(1);
				// Handle Items that have replaced use items
				if (itemstack3.hasContainerItem()) {
					this.items.set(1, itemstack3.getContainerItem());
				} else if (itemstack3.getItem() instanceof VesselDrinkItem) {
					this.items.set(1, itemstack3.getItem().getContainerItem(itemstack3));
				} else if (itemstack3.getItem() instanceof PotionItem) {
					this.items.set(1, new ItemStack(Items.GLASS_BOTTLE));
				} else if (!itemstack3.isEmpty()) {
					Item item = itemstack3.getItem();
					itemstack3.shrink(1);
					if (itemstack3.isEmpty()) {
						this.items.set(1, itemstack3.getContainerItem());
					}
				}
				this.usingLubrication = false;
			}
			//if (itemstack.getItem() == Blocks.WET_SPONGE.asItem() && !this.items.get(1).isEmpty() && this.items.get(1).getItem() == Items.BUCKET) {
			//	this.items.set(1, new ItemStack(Items.WATER_BUCKET));
			//}
			inputStack.shrink(1);
		}
	}

	protected int getTotalMillingTime() {
		return this.level.getRecipeManager().getRecipeFor(ExtendedRecipeTypes.STONE_GRINDER, this, this.level).map(ExtendeStoneGrinderRecipe::getMillingTime).orElse(200);
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
		} else if (pIndex == 0) {
			return !canLubeItem(pStack);
		} else {
			return canLubeItem(pStack);
		}
	}

	/**
	 * Returns true if automation can extract the given item in the given slot from the given side.
	 */
	@Override
	public boolean canTakeItemThroughFace(int pIndex, ItemStack pStack, Direction pDirection) {
		if (pDirection == Direction.DOWN && pIndex == 1) {
			if (canLubeItem(pStack)) {
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
			this.grindingTotalTime = this.getTotalMillingTime();
			this.grindingProgress = 0;
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
				createExperience(p_235640_1_, p_235640_2_, entry.getIntValue(), ((ExtendeStoneGrinderRecipe)p_235642_4_).getExperience());
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
		return new TranslationTextComponent("container.lotrextended.stone_grinder");
	}

	@Override
	protected Container createMenu(int pId, PlayerInventory pPlayer) {
		return new ExtendedStoneGrinderContainer(pId, pPlayer, this, this.dataAccess, null);
	}
}