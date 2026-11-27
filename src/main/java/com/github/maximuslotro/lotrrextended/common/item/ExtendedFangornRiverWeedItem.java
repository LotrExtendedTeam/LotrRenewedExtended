package com.github.maximuslotro.lotrrextended.common.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.world.World;

public class ExtendedFangornRiverWeedItem extends BlockItem {
	public ExtendedFangornRiverWeedItem(Block pBLock, Properties pProperties) {
		super(pBLock, pProperties);
	}

	@Override
	public ActionResultType useOn(ItemUseContext pContext) {
		return ActionResultType.PASS;
	}

	@Override
	public ActionResult<ItemStack> use(World pLevel, PlayerEntity pPlayer, Hand pHand) {
		BlockRayTraceResult blockraytraceresult = getPlayerPOVHitResult(pLevel, pPlayer, RayTraceContext.FluidMode.SOURCE_ONLY);
		BlockRayTraceResult blockraytraceresult1 = blockraytraceresult.withPosition(blockraytraceresult.getBlockPos().above());
		ActionResultType actionresulttype = super.useOn(new ItemUseContext(pPlayer, pHand, blockraytraceresult1));
		return new ActionResult<>(actionresulttype, pPlayer.getItemInHand(pHand));
	}
}
