package com.github.maximuslotro.lotrrextended.mixins.lotr.common.block;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;

import lotr.common.block.DripstoneBlock;
import lotr.common.block.DripstoneBlock.Type;
import lotr.common.init.LOTRDamageSources;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.state.EnumProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * @reason Add patch do allow for customization of dripstone damage height
 * @Mixin(DripstoneBlock.class)
 * */
@Mixin(DripstoneBlock.class)
public abstract class MixinDripstoneBlock extends Block{

	/**
	 * @reason Mixins with extend classes must add a constructor, but this will be stripped on runtime and will cease to exist
	 * @param properties A placeholder variable instance of Properties
	 * */
	public MixinDripstoneBlock(Properties properties) {
		super(properties);
	}

	/**
	 * @reason Get the property holder for dripstone types
	 * @Final
	 * @Shadow(remap=false)
	 * */
	@Final
	@Shadow(remap=false)
	public static EnumProperty<Type> DRIPSTONE_TYPE;

	/**
	 * @author maximuslotro
	 * @reason Adding a config setting to make dripstone less deadly
	 * @Overwrite
	 * @param world The world for this event
	 * @param pos The position for this event
	 * @param entity The entity that fell
	 * @param fallDistance The height from which the entity fell
	 * */
	@Override
	@Overwrite
	public void fallOn(World world, BlockPos pos, Entity entity, float fallDistance) {
		if (entity instanceof LivingEntity) {
			if(fallDistance < ExtendedServerConfig.dripstoneMinimumDamageHeight.get()) {
				return;
			}
			ExtendedLog.info("Damaging with height of"+fallDistance);
			DripstoneBlock.Type type = world.getBlockState(pos).getValue(DRIPSTONE_TYPE);
			if (type == DripstoneBlock.Type.STALAGMITE || type == DripstoneBlock.Type.STALAGMITE_DOUBLE_POINT) {
				float damage = fallDistance * 2.0F + 1.0F;
				entity.hurt(LOTRDamageSources.STALAGMITE, damage);
			}
		}
	}
}
