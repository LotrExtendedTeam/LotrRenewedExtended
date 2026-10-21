package com.github.maximuslotro.lotrrextended.common.item;

import java.util.List;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.common.network.ExtendedPacketHandler;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedSOpenStoneTextSignPacket;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedStoneTextSignTileEntity;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.Tags;

public class ExtendedChiselItem extends Item {
	private final boolean causesGlowing;
	private final Block block;

	public ExtendedChiselItem(Block pBlock, Properties pProperties, boolean glowing) {
		super(pProperties);
		this.block = pBlock;
		this.causesGlowing = glowing;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack pStack, @Nullable World pLevel, List<ITextComponent> pTooltip, ITooltipFlag pFlag) {
		super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
		if(causesGlowing) {
			pTooltip.add(1, new TranslationTextComponent("gui.lotrextended.chisel.moon").withStyle(TextFormatting.BLUE));
		}
	}
	@Override
	public ActionResultType useOn(ItemUseContext pContext) {
		ActionResultType actionresulttype = this.place(new BlockItemUseContext(pContext));
		return !actionresulttype.consumesAction() && this.isEdible() ? this.use(pContext.getLevel(), pContext.getPlayer(), pContext.getHand()).getResult() : actionresulttype;
	}

	public ActionResultType place(BlockItemUseContext pContext) {
		if (!pContext.canPlace()) {
			return ActionResultType.FAIL;
		} else {
			BlockState blockstate = this.getPlacementState(pContext);
			if (blockstate == null) {
				return ActionResultType.FAIL;
			} else if (!this.placeBlock(pContext, blockstate)) {
				return ActionResultType.FAIL;
			} else {
				BlockPos blockpos = pContext.getClickedPos();
				World world = pContext.getLevel();
				PlayerEntity playerentity = pContext.getPlayer();
				ItemStack itemstack = pContext.getItemInHand();
				BlockState blockstate1 = world.getBlockState(blockpos);
				Block block = blockstate1.getBlock();
				if (block == blockstate.getBlock()) {
					blockstate1 = this.updateBlockStateFromTag(blockpos, world, itemstack, blockstate1);
					this.updateCustomBlockEntityTag(blockpos, world, playerentity, itemstack, blockstate1);
					block.setPlacedBy(world, blockpos, blockstate1, playerentity, itemstack);
					if (playerentity instanceof ServerPlayerEntity) {
						CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity)playerentity, blockpos, itemstack);
					}
				}

				SoundType soundtype = blockstate1.getSoundType(world, blockpos, pContext.getPlayer());
				world.playSound(playerentity, blockpos, this.getPlaceSound(blockstate1, world, blockpos, pContext.getPlayer()), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
				if (playerentity == null || !playerentity.abilities.instabuild) {
					// Extended Start
					itemstack.hurtAndBreak(1, playerentity, (player) -> player.broadcastBreakEvent(playerentity.getUsedItemHand()));
					// Extended End
				}

				return ActionResultType.sidedSuccess(world.isClientSide);
			}
		}
	}
	protected SoundEvent getPlaceSound(BlockState state, World world, BlockPos pos, PlayerEntity entity) {
		return state.getSoundType(world, pos, entity).getPlaceSound();
	}

	protected BlockState getPlacementState(BlockItemUseContext pContext) {
		BlockState blockstate1 = null;
		IWorldReader iworldreader = pContext.getLevel();
		BlockPos blockpos = pContext.getClickedPos();

		for(Direction direction : pContext.getNearestLookingDirections()) {
			if (direction.getAxis().isHorizontal()) {
				BlockState blockstate2 = block.getStateForPlacement(pContext);
				if (blockstate2 != null && blockstate2.canSurvive(iworldreader, blockpos)) {
					blockstate1 = blockstate2;
					break;
				}
			}
		}

		return blockstate1 != null && iworldreader.isUnobstructed(blockstate1, blockpos, ISelectionContext.empty()) ? blockstate1 : null;
	}

	protected boolean updateCustomBlockEntityTag(BlockPos pPos, World pLevel, @Nullable PlayerEntity pPlayer, ItemStack pStack, BlockState pState) {
		boolean flag = updateCustomBlockEntityTag(pLevel, pPlayer, pPos, pStack);
		if (!pLevel.isClientSide && !flag && pPlayer != null) {
			ExtendedStoneTextSignTileEntity signtileentity = (ExtendedStoneTextSignTileEntity)pLevel.getBlockEntity(pPos);
			signtileentity.setAllowedPlayerEditor(pPlayer);
			signtileentity.setHasGlowingText(causesGlowing);
			if(causesGlowing && signtileentity.getColor()!=DyeColor.BLACK) {
				signtileentity.setColor(DyeColor.LIGHT_GRAY);
			}
			ExtendedPacketHandler.sendToClient(new ExtendedSOpenStoneTextSignPacket(((ExtendedStoneTextSignTileEntity)pLevel.getBlockEntity(pPos)).getBlockPos()), (ServerPlayerEntity) pPlayer);
		}
		return flag;
	}
	public static boolean updateCustomBlockEntityTag(World pLevel, @Nullable PlayerEntity pPlayer, BlockPos pPos, ItemStack pStack) {
		MinecraftServer minecraftserver = pLevel.getServer();
		if (minecraftserver == null) {
			return false;
		} else {
			CompoundNBT compoundnbt = pStack.getTagElement("BlockEntityTag");
			if (compoundnbt != null) {
				TileEntity tileentity = pLevel.getBlockEntity(pPos);
				if (tileentity != null) {
					if (!pLevel.isClientSide && tileentity.onlyOpCanSetNbt() && (pPlayer == null || !pPlayer.canUseGameMasterBlocks())) {
						return false;
					}

					CompoundNBT compoundnbt1 = tileentity.save(new CompoundNBT());
					CompoundNBT compoundnbt2 = compoundnbt1.copy();
					compoundnbt1.merge(compoundnbt);
					compoundnbt1.putInt("x", pPos.getX());
					compoundnbt1.putInt("y", pPos.getY());
					compoundnbt1.putInt("z", pPos.getZ());
					if (!compoundnbt1.equals(compoundnbt2)) {
						tileentity.load(pLevel.getBlockState(pPos), compoundnbt1);
						tileentity.setChanged();
						return true;
					}
				}
			}

			return false;
		}
	}
	private BlockState updateBlockStateFromTag(BlockPos pPos, World pLevel, ItemStack pStack, BlockState pState) {
		BlockState blockstate = pState;
		CompoundNBT compoundnbt = pStack.getTag();
		if (compoundnbt != null) {
			CompoundNBT compoundnbt1 = compoundnbt.getCompound("BlockStateTag");
			StateContainer<Block, BlockState> statecontainer = pState.getBlock().getStateDefinition();

			for(String s : compoundnbt1.getAllKeys()) {
				Property<?> property = statecontainer.getProperty(s);
				if (property != null) {
					String s1 = compoundnbt1.get(s).getAsString();
					blockstate = updateState(blockstate, property, s1);
				}
			}
		}

		if (blockstate != pState) {
			pLevel.setBlock(pPos, blockstate, 2);
		}

		return blockstate;
	}

	private static <T extends Comparable<T>> BlockState updateState(BlockState pState, Property<T> pProperty, String pValueIdentifier) {
		return pProperty.getValue(pValueIdentifier).map((p_219986_2_) -> {
			return pState.setValue(pProperty, p_219986_2_);
		}).orElse(pState);
	}

	protected boolean placeBlock(BlockItemUseContext pContext, BlockState pState) {
		return pContext.getLevel().setBlock(pContext.getClickedPos(), pState, 11);
	}

	@Override
	public boolean isValidRepairItem(ItemStack p_82789_1_, ItemStack repairItem) {
		return Tags.Items.INGOTS_IRON.contains(repairItem.getItem());
	}

	public boolean doesCauseGlowing() {
		return causesGlowing;
	}
}
