package com.github.maximuslotro.lotrrextended.mixins.lotr.common.entity.projectile;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import com.github.maximuslotro.lotrrextended.common.bannerprotection.ExtendedBannerProtectionManager;
import com.github.maximuslotro.lotrrextended.common.bannerprotection.ExtendedBannerProtectionManager.SingleProtectionInstance;

import lotr.common.entity.projectile.ThrownPlateEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(ThrownPlateEntity.class)
public abstract class MixinThrownPlateEntity extends ProjectileItemEntity{

	public MixinThrownPlateEntity(EntityType<? extends ProjectileItemEntity> p_i50155_1_, World p_i50155_2_) {
		super(p_i50155_1_, p_i50155_2_);
	}

	/**
	 * @reason Easy to replace method
	 * @author maximuslotro
	 * */
	@Overwrite(remap = false)
	private boolean breakGlass(BlockPos pos) {
		BlockState state = level.getBlockState(pos);
		if (state.getMaterial() == Material.GLASS) {
			boolean bannerProtection = ExtendedBannerProtectionManager.isBannerProtected(level, pos, new SingleProtectionInstance.LaunchedProtection(this), true);
			if (!bannerProtection) {
				level.levelEvent((PlayerEntity) null, 2001, pos, Block.getId(state));
				level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
				return true;
			}
		}

		return false;
	}
}
