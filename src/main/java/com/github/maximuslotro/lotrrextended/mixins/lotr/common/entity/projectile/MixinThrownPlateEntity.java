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
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * @reason Add banner protection checking to thrown plates
 * @Mixin (ThrownPlateEntity.class)
 * */
@Mixin(ThrownPlateEntity.class)
public abstract class MixinThrownPlateEntity extends ProjectileItemEntity{

	/**
	 * @reason Mixins with extend classes must add a constructor, but this will be stripped on runtime and will cease to exist
	 * @param type A placeholder variable instance of ProjectileItemEntity
	 * @param world A placeholder variable instance of World
	 * */
	public MixinThrownPlateEntity(EntityType<? extends ProjectileItemEntity> type, World world) {
		super(type, world);
	}

	/**
	 * @author maximuslotro
	 * @reason Adds Banner protection logic into the glass breaking mechanic
	 * @Overwrite (remap = false)
	 * @param pos The BlockPos to try to break
	 * @return True if the block can be broken
	 */
	@Overwrite(remap = false)
	private boolean breakGlass(BlockPos pos) {
		BlockState state = level.getBlockState(pos);
		if (state.getMaterial() == Material.GLASS) {
			// Extended Start
			boolean bannerProtection = ExtendedBannerProtectionManager.isBannerProtected(level, pos, new SingleProtectionInstance.LaunchedProtection(this), true, "breakGlass");
			// Extended End
			if (!bannerProtection) {
				level.levelEvent(null, 2001, pos, Block.getId(state));
				level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
				return true;
			}
		}
		return false;
	}
}
