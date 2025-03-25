package com.github.maximuslotro.lotrrextended.mixins.lotr.common.block;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import com.github.maximuslotro.lotrrextended.common.config.ExtendedClientConfig;

import lotr.common.block.FlowerLikeBlock;
import lotr.common.block.WildPipeweedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * @reason Adding a toggle to disable pipeweed particles
 * @Mixin (WildPipeweedBlock.class)
 * */
@Mixin(WildPipeweedBlock.class)
public class MixinWildPipeweedBlock extends FlowerLikeBlock {
	/**
	 * @author maximuslotro
	 * @reason Add toggle to disable pipeweed particles
	 * @Overwrite
	 * @param state The state of the animated block
	 * @param world The world to spawn particles in
	 * @param pos The pos of the block to spawn particles
	 * @param rand A random instance if should spawn particles
	 * */
	@Override
	@OnlyIn(Dist.CLIENT)
	@Overwrite
	public void animateTick(BlockState state, World world, BlockPos pos, Random rand) {
		if (rand.nextInt(4) == 0 && ExtendedClientConfig.enablePlantParticles.get()) {
			Vector3d offset = state.getOffset(world, pos);
			double x = pos.getX() + offset.x;
			double y = pos.getY() + offset.y;
			double z = pos.getZ() + offset.z;
			x += MathHelper.nextFloat(rand, 0.1F, 0.9F);
			y += MathHelper.nextFloat(rand, 0.5F, 0.75F);
			z += MathHelper.nextFloat(rand, 0.1F, 0.9F);
			world.addParticle(ParticleTypes.SMOKE, x, y, z, 0.0D, 0.0D, 0.0D);
		}

	}
}
