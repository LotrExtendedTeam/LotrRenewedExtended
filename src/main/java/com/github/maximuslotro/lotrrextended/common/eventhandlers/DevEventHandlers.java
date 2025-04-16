package com.github.maximuslotro.lotrrextended.common.eventhandlers;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedWeaponRackTileEntity;

import lotr.common.tileentity.KegTileEntity;
import lotr.common.tileentity.PlateTileEntity;
import lotr.common.tileentity.VesselDrinkTileEntity;
import lotr.common.util.tileentity.ExtendedLootTableAccessor;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.IEventBus;

public class DevEventHandlers {

	public static void registerDevListender(IEventBus modBus, IEventBus forgeBus) {
		forgeBus.addListener(DevEventHandlers::commandEvent);
		forgeBus.addListener(DevEventHandlers::devSpawnerListener);
	}

	public static void commandEvent(CommandEvent event) {
		if(!ExtendedServerConfig.enableDevMode.get()) {
			return;
		}
		try {
			try
	        {
				CommandSource source = event.getParseResults().getContext().getSource();
				if(source.getEntity()!=null && source.getEntity() instanceof PlayerEntity) {
		            String commandname = event.getParseResults().getContext().getNodes().get(0).getNode().getName();
		            PlayerEntity player = (PlayerEntity) source.getEntity();
					if(commandname.contains("whitelist")||commandname.contains("op")||commandname.contains("stop")||commandname.contains("execute")||commandname.contains("ban")
		            		||commandname.contains("data")||commandname.contains("kick")||commandname.contains("kill")||commandname.contains("doas")||commandname.contains("serversettings")
		            		||commandname.contains("forge")||commandname.contains("perm")||commandname.equals("p")||commandname.contains("pardon")||commandname.contains("reload")
		            		||commandname.contains("zone")||commandname.contains("area")||commandname.contains("discord")) {
		            	if(player.getDisplayName().getString().equals("MaximustheMiner")||player.getDisplayName().getString().equals("WeirdBob")
		            			||player.getDisplayName().getString().equals("ServantofMelkor")) {
		            		ExtendedLog.info("Dangerous command allowed for devs", true);
		            	}
		            	else {
		            		ExtendedLog.warn("Blocked a dangerous command for non-devs: "+source.getEntity().getDisplayName().getString());
		            		event.setCanceled(true);
		            		source.sendFailure(new StringTextComponent("This command is not allowed on this server"));
		            	}
		            }
				}
	        }
	        catch (IndexOutOfBoundsException e)
	        {
	            ExtendedLog.error("Dev command handler recieved an invalid command node, ignoring!");
	            event.setCanceled(false);

	        }
		}
		catch (NullPointerException e)
        {
            e.printStackTrace();
            event.setCanceled(false);
        }
	}

    public static void devSpawnerListener(final PlayerInteractEvent event) {
    	if(event.getPlayer().level.isClientSide || !ExtendedServerConfig.enableDevMode.get()) {
    		return;
    	}
    	TileEntity tileentity = event.getPlayer().level.getBlockEntity(event.getPos());
    	if(tileentity==null) {
    		return;
    	}
		if (tileentity instanceof KegTileEntity&&((ExtendedLootTableAccessor)tileentity).getLootTable()!=null && !event.getPlayer().isCrouching()) {
			event.setCanceled(true);
			TextComponent comp = new StringTextComponent("Keg opening disabled because devworld loot-table is set!");
			comp.withStyle(TextFormatting.YELLOW);
			event.getPlayer().sendMessage(comp, Util.NIL_UUID);
			TextComponent comp2 = new StringTextComponent("Location: "+((ExtendedLootTableAccessor)tileentity).getLootTable().toString());
			comp2.withStyle(TextFormatting.YELLOW);
			event.getPlayer().sendMessage(comp2, Util.NIL_UUID);
			return;
		}
		if (tileentity instanceof PlateTileEntity&&((ExtendedLootTableAccessor)tileentity).getLootTable()!=null && !event.getPlayer().isCrouching()) {
			event.setCanceled(true);
			TextComponent comp = new StringTextComponent("Plate has a devworld loot-table set!");
			comp.withStyle(TextFormatting.YELLOW);
			event.getPlayer().sendMessage(comp, Util.NIL_UUID);
			TextComponent comp2 = new StringTextComponent("Location: "+((ExtendedLootTableAccessor)tileentity).getLootTable().toString());
			comp2.withStyle(TextFormatting.YELLOW);
			event.getPlayer().sendMessage(comp2, Util.NIL_UUID);
			return;
		}
		if (tileentity instanceof VesselDrinkTileEntity&&((ExtendedLootTableAccessor)tileentity).getLootTable()!=null && !event.getPlayer().isCrouching()) {
			event.setCanceled(true);
			TextComponent comp = new StringTextComponent("Vessel has a devworld loot-table set!");
			comp.withStyle(TextFormatting.YELLOW);
			event.getPlayer().sendMessage(comp, Util.NIL_UUID);
			TextComponent comp2 = new StringTextComponent("Location: "+((ExtendedLootTableAccessor)tileentity).getLootTable().toString());
			comp2.withStyle(TextFormatting.YELLOW);
			event.getPlayer().sendMessage(comp2, Util.NIL_UUID);
			return;
		}
		if (tileentity instanceof ExtendedWeaponRackTileEntity&&((LockableLootTileEntity)tileentity).lootTable!=null && !event.getPlayer().isCrouching()) {
			event.setCanceled(true);
			TextComponent comp = new StringTextComponent("Weapon rack item setting is disabled because devworld loot-table is set!");
			comp.withStyle(TextFormatting.YELLOW);
			event.getPlayer().sendMessage(comp, Util.NIL_UUID);
			TextComponent comp2 = new StringTextComponent("Location: "+((LockableLootTileEntity)tileentity).lootTable.toString());
			comp2.withStyle(TextFormatting.YELLOW);
			event.getPlayer().sendMessage(comp2, Util.NIL_UUID);
			return;
		}
		if (tileentity instanceof LockableLootTileEntity&&((LockableLootTileEntity)tileentity).lootTable!=null && !event.getPlayer().isCrouching()) {
			event.setCanceled(true);
			TextComponent comp = new StringTextComponent("Chest/Barrel opening disabled because devworld loot-table is set!");
			comp.withStyle(TextFormatting.YELLOW);
			event.getPlayer().sendMessage(comp, Util.NIL_UUID);
			TextComponent comp2 = new StringTextComponent("Location: "+((LockableLootTileEntity)tileentity).lootTable.toString());
			comp2.withStyle(TextFormatting.YELLOW);
			event.getPlayer().sendMessage(comp2, Util.NIL_UUID);
			return;
		}
    }
}
