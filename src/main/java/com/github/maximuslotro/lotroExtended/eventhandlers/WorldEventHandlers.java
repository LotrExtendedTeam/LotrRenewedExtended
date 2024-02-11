package com.github.maximuslotro.lotroExtended.eventhandlers;

import com.github.maximuslotro.lotroExtended.config.ExtendedServerConfig;

import net.minecraft.block.ContainerBlock;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class WorldEventHandlers {
    @SubscribeEvent
    public static void devSpawnerListener(final PlayerInteractEvent event) {
    	if(event.getPlayer().level.isClientSide || !ExtendedServerConfig.enableDevMode.get()) {
    		return;
    	}
        if (event.getPlayer().level.getBlockState(event.getPos()).getBlock() instanceof ContainerBlock) {
        	TileEntity tileentity = event.getPlayer().level.getBlockEntity(event.getPos());
			if (tileentity instanceof LockableLootTileEntity&&((LockableLootTileEntity)tileentity).lootTable!=null && !event.getPlayer().isCrouching()) {
				event.setCanceled(true);
				TextComponent comp = new StringTextComponent("Chest opening disabled because devworld loot-table is set!");
				comp.withStyle(TextFormatting.YELLOW);
				event.getPlayer().sendMessage(comp, event.getPlayer().getUUID());
				TextComponent comp2 = new StringTextComponent("Location: "+((LockableLootTileEntity)tileentity).lootTable.toString());
				comp2.withStyle(TextFormatting.YELLOW);
				event.getPlayer().sendMessage(comp2, event.getPlayer().getUUID());
			}
       }
    }
}
