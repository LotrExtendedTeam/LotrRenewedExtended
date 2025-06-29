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
 * Mixin for {@link ThrownPlateEntity} that injects custom logic to respect banner protection rules
 * when attempting to break glass blocks.
 * <p>
 * Overrides the vanilla {@code breakGlass} method to include a permission check via the
 * {@link ExtendedBannerProtectionManager}, ensuring plates cannot break protected glass blocks.
 *
 * @Mixin (ThrownPlateEntity.class)
 */
@Mixin(ThrownPlateEntity.class)
public abstract class MixinThrownPlateEntity extends ProjectileItemEntity{

	/**
     * Required constructor for mixins extending Minecraft classes.
     * <p>
     * Although this constructor is mandatory for compilation, it will be stripped at runtime
     * and should never be invoked.
     *
     * @param type  The entity type (placeholder).
     * @param world The world instance (placeholder).
     */
	public MixinThrownPlateEntity(EntityType<? extends ProjectileItemEntity> type, World world) {
		super(type, world);
	}
	/**
     * Overwrites the default glass-breaking behavior of {@link ThrownPlateEntity}
     * to incorporate banner-based region protection.
     * <p>
     * If the block at the specified position is glass and not protected by a banner,
     * the block will be broken; otherwise, it will remain intact.
     *
     * @author maximuslotro
	 * @reason Adds Banner protection logic into the glass breaking mechanic
     * @param pos The block position to check and attempt to break.
     * @return {@code true} if the glass block was successfully broken; {@code false} otherwise.
     */
	@Overwrite(remap = false)
	private boolean breakGlass(BlockPos pos) {
		BlockState state = level.getBlockState(pos);
		if (state.getMaterial() == Material.GLASS) {
			// Extended Start
			boolean bannerProtection = ExtendedBannerProtectionManager.getProtectionStatus(level, pos, new SingleProtectionInstance.LaunchedProtection(this), true, "breakGlass").isNotAllowed();
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
