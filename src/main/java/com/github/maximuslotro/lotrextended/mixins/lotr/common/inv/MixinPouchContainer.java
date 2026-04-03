package com.github.maximuslotro.lotrextended.mixins.lotr.common.inv;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import lotr.common.inv.PouchContainer;
import lotr.common.item.PouchItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;

@Mixin(PouchContainer.class)
public abstract class MixinPouchContainer extends Container {

	@Final
	@Shadow(remap = false)
	private ItemStack pouchItem;

	protected MixinPouchContainer(ContainerType<?> pMenuType, int pContainerId) {
		super(pMenuType, pContainerId);
	}

	/**
	 * @author Maximuslotro
	 * @reason Fixes crash on partial inventory casting AirItem to PouchItem
	 */
	@Override
	@Overwrite
	public void removed(PlayerEntity player) {
		super.removed(player);
		// Extended Start
		if(!pouchItem.isEmpty()) {
			player.level.playSound((PlayerEntity) null, player.getX(), player.getY(), player.getZ(), ((PouchItem) pouchItem.getItem()).getCloseSound(), SoundCategory.PLAYERS, 1.0F, 1.0F);
		}
		// Extended End
		//player.level.playSound((PlayerEntity) null, player.getX(), player.getY(), player.getZ(), ((PouchItem) pouchItem.getItem()).getCloseSound(), SoundCategory.PLAYERS, 1.0F, 1.0F);
	}
}
