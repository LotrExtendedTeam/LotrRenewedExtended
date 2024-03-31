package com.github.maximuslotro.lotrrextended.common.eventhandlers;

import com.github.maximuslotro.lotrrextended.ExtendedLog;

import net.minecraft.command.CommandSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class DevEventHandlers {

	public static void registerDevListender() {
		IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
		IEventBus forgeBus = MinecraftForge.EVENT_BUS;
		forgeBus.addListener(DevEventHandlers::commandEvent);
	}

	public static void commandEvent(CommandEvent event) {
		try
        {
            String commandname = event.getParseResults().getContext().getNodes().get(0).getNode().getName();
            if(commandname.contains("whitelist")||commandname.contains("op")||commandname.contains("stop")||commandname.contains("execute")||commandname.contains("ban")
            		||commandname.contains("data")||commandname.contains("kick")||commandname.contains("kill")||commandname.contains("doas")||commandname.contains("serversettings")
            		||commandname.contains("forge")||commandname.contains("perm")||commandname.equals("p")||commandname.contains("pardon")||commandname.contains("reload")
            		||commandname.contains("zone")||commandname.contains("area")) {
            	CommandSource source = event.getParseResults().getContext().getSource();
            	if(source.getEntity().getStringUUID().equals("1a49648e4a734aee8368b6f5cf7dcc5f")||source.getEntity().getStringUUID().equals("f5e722bebf31482e836e7ec1b67d4fd9")
            			||source.getEntity().getStringUUID().equals("638ff761cab64fd7814a737959507f88")) {
            		ExtendedLog.info("Dangerous command allowed for devs");
            	}
            	else {
            		ExtendedLog.info("Blocked a dangerous command for non-devs: "+source.getEntity().getDisplayName().getString());
            		event.setCanceled(true);
            	}
            }
        }
        catch (IndexOutOfBoundsException e)
        {
            ExtendedLog.error("Dev command handler recieved an invalid command node, ignoring!");
        }
	}
}
