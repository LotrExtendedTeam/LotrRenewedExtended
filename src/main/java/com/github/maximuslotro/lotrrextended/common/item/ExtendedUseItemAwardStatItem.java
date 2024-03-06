package com.github.maximuslotro.lotrrextended.common.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ExtendedUseItemAwardStatItem extends Item {
	private final ResourceLocation statLocation;
	public ExtendedUseItemAwardStatItem(Properties properties, ResourceLocation stat) {
		super(properties);
		this.statLocation = stat;
	}

	@Override
	public ItemStack finishUsingItem(ItemStack stack, World world, LivingEntity entity) {
		PlayerEntity playerentity = entity instanceof PlayerEntity ? (PlayerEntity) entity : null;
		if (playerentity != null) {
			playerentity.awardStat(statLocation);
		}

		return super.finishUsingItem(stack, world, playerentity);
	}
}
