package com.github.maximuslotro.lotrrextended.common.item;

import java.util.Collections;

import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolItem;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ExtendedMattockItem extends ToolItem {

	public ExtendedMattockItem(IItemTier pTier, Properties pProperties) {
		this(6.0f, -2.8f, pTier, pProperties);
	}

	public ExtendedMattockItem(float pAttackDamageModifier, float pAttackSpeedModifier, IItemTier pTier, Properties pProperties) {
		super(pAttackDamageModifier, pAttackSpeedModifier, pTier, Collections.emptySet(), 
			pProperties.addToolType(net.minecraftforge.common.ToolType.PICKAXE, pTier.getLevel()).addToolType(net.minecraftforge.common.ToolType.AXE, pTier.getLevel()));
	}

	@Override
	public float getDestroySpeed(ItemStack pStack, BlockState pState) {
		Material material = pState.getMaterial();
		if (material == Material.METAL || material == Material.HEAVY_METAL || material == Material.STONE || AxeItem.DIGGABLE_MATERIALS.contains(material)) {
			return this.speed;
		}
		if (pState.isToolEffective(net.minecraftforge.common.ToolType.PICKAXE) || pState.isToolEffective(net.minecraftforge.common.ToolType.AXE)) {
			return this.speed;
		}
		if (PickaxeItem.DIGGABLES.contains(pState.getBlock()) || AxeItem.OTHER_DIGGABLE_BLOCKS.contains(pState.getBlock())) {
			return this.speed;
		}
		return 1.0F;
	}

	@Override
	public ActionResultType useOn(ItemUseContext pContext) {
		World world = pContext.getLevel();
		BlockPos blockpos = pContext.getClickedPos();
		BlockState blockstate = world.getBlockState(blockpos);
		BlockState block = blockstate.getToolModifiedState(world, blockpos, pContext.getPlayer(), pContext.getItemInHand(), net.minecraftforge.common.ToolType.AXE);
		if (block != null) {
			PlayerEntity playerentity = pContext.getPlayer();
			world.playSound(playerentity, blockpos, SoundEvents.AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
			if (!world.isClientSide) {
				world.setBlock(blockpos, block, 11);
				if (playerentity != null) {
					pContext.getItemInHand().hurtAndBreak(1, playerentity, (p_220040_1_) -> {
						p_220040_1_.broadcastBreakEvent(pContext.getHand());
					});
				}
			}

			return ActionResultType.sidedSuccess(world.isClientSide);
		} else {
			return ActionResultType.PASS;
		}
	}

	@Override
	public boolean isCorrectToolForDrops(BlockState pState) {
		if (pState.getHarvestTool() == net.minecraftforge.common.ToolType.PICKAXE
			|| pState.getHarvestTool() == net.minecraftforge.common.ToolType.AXE) {
			return this.getTier().getLevel() >= pState.getHarvestLevel();
		}
		Material material = pState.getMaterial();
		return material == Material.STONE || material == Material.METAL || material == Material.HEAVY_METAL;
	}
}
