package com.github.maximuslotro.lotrrextended.common.block;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.common.entity.item.ExtendedOrcBombEntity;

import lotr.common.init.LOTRItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TNTBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class ExtendedOrcBombBlock extends TNTBlock {
	private static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D);
	private final int strength;
	private final boolean fire;

	public ExtendedOrcBombBlock(Properties props, int strength, boolean fire) {
		super(props);
		this.strength = strength;
		this.fire = fire;
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
		return SHAPE;
	}

	@Override
	public ActionResultType use(BlockState pState, World pLevel, BlockPos pPos, PlayerEntity pPlayer, Hand pHand, BlockRayTraceResult pHit) {
		ItemStack itemstack = pPlayer.getItemInHand(pHand);
		Item item = itemstack.getItem();
		if (item != LOTRItems.ORC_TORCH.get()) {
			return ActionResultType.PASS;
		} else {
			catchFire(pState, pLevel, pPos, pHit.getDirection(), pPlayer);
			pLevel.setBlock(pPos, Blocks.AIR.defaultBlockState(), 11);
			if (!pPlayer.isCreative()) {
				itemstack.hurtAndBreak(1, pPlayer, (player) -> {
					player.broadcastBreakEvent(pHand);
				});
			}

			return ActionResultType.sidedSuccess(pLevel.isClientSide);
		}
	}

	@Override
	public void wasExploded(World pLevel, BlockPos pPos, Explosion pExplosion) {
		if (!pLevel.isClientSide) {
			ExtendedOrcBombEntity tntentity = new ExtendedOrcBombEntity(pLevel, (double)pPos.getX() + 0.5D, (double)pPos.getY(), (double)pPos.getZ() + 0.5D, pExplosion.getSourceMob());
			tntentity.setFuse((short)(pLevel.random.nextInt(20) + 10 + pLevel.random.nextInt(10 * (strength + 1))));
			tntentity.setStrength(strength);
			tntentity.setFire(fire);
			pLevel.addFreshEntity(tntentity);
		}
	}

	@Override
	public void catchFire(BlockState state, World world, BlockPos pos, @Nullable net.minecraft.util.Direction face, @Nullable LivingEntity igniter) {
		explodeExtended(world, pos, igniter, strength, fire);
	}

	private static void explodeExtended(World pLevel, BlockPos pPos, @Nullable LivingEntity pEntity, int pStrength, boolean pFire) {
		if (!pLevel.isClientSide) {
			ExtendedOrcBombEntity tntentity = new ExtendedOrcBombEntity(pLevel, (double)pPos.getX() + 0.5D, (double)pPos.getY(), (double)pPos.getZ() + 0.5D, pEntity);
			tntentity.setFuse((short)(80 + 20 * (pStrength + 1)));
			tntentity.setStrength(pStrength);
			tntentity.setFire(pFire);
			pLevel.addFreshEntity(tntentity);
			pLevel.playSound(null, tntentity.getX(), tntentity.getY(), tntentity.getZ(), SoundEvents.TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
		}
	}

	@Override
	public int getFireSpreadSpeed(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
		return 15;
	}

	@Override
	public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
		return 100;
	}

	public int getBombStrength() {
		return strength;
	}

	public boolean isBombFire() {
		return fire;
	}

	@Override
	public boolean isPathfindable(BlockState pState, IBlockReader pLevel, BlockPos pPos, PathType pType) {
		return false;
	}
}
