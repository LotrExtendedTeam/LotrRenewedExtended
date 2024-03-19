package com.github.maximuslotro.lotrrextended.common.eventhandlers;

import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;
import com.github.maximuslotro.lotrrextended.mixinhooks.MixinHooks;

import lotr.common.entity.npc.ExtendedDunedainRangerEntity;
import lotr.common.entity.npc.ExtendedTraderEntity;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.tileentity.KegTileEntity;
import lotr.common.util.ExtendedTraderHelper;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class WorldEventHandlers {
    @SubscribeEvent
    public static void devSpawnerListener(final PlayerInteractEvent event) {
    	if(event.getPlayer().level.isClientSide || !ExtendedServerConfig.enableDevMode.get()) {
    		return;
    	}
    	TileEntity tileentity = event.getPlayer().level.getBlockEntity(event.getPos());
    	if(tileentity==null) {
    		return;
    	}
		if (tileentity instanceof LockableLootTileEntity&&((LockableLootTileEntity)tileentity).lootTable!=null && !event.getPlayer().isCrouching()) {
			event.setCanceled(true);
			TextComponent comp = new StringTextComponent("Chest/Barrel opening disabled because devworld loot-table is set!");
			comp.withStyle(TextFormatting.YELLOW);
			event.getPlayer().sendMessage(comp, event.getPlayer().getUUID());
			TextComponent comp2 = new StringTextComponent("Location: "+((LockableLootTileEntity)tileentity).lootTable.toString());
			comp2.withStyle(TextFormatting.YELLOW);
			event.getPlayer().sendMessage(comp2, event.getPlayer().getUUID());
			return;
		}
		if (tileentity instanceof KegTileEntity&&MixinHooks.getKegLootTable((KegTileEntity)tileentity)!=null && !event.getPlayer().isCrouching()) {
			event.setCanceled(true);
			TextComponent comp = new StringTextComponent("Keg opening disabled because devworld loot-table is set!");
			comp.withStyle(TextFormatting.YELLOW);
			event.getPlayer().sendMessage(comp, event.getPlayer().getUUID());
			TextComponent comp2 = new StringTextComponent("Location: "+MixinHooks.getKegLootTable((KegTileEntity)tileentity).toString());
			comp2.withStyle(TextFormatting.YELLOW);
			event.getPlayer().sendMessage(comp2, event.getPlayer().getUUID());
			return;
		}
    }

	@SubscribeEvent
	public static void onEntityRightClick(PlayerInteractEvent.EntityInteract event) {
		if (event.getPlayer().getItemInHand(event.getHand()).getItem() instanceof SpawnEggItem) {
			if (event.getTarget() instanceof ExtendedTraderEntity) {
				System.out.println("Right-clicked a trader with a SpawnEgg");
				ExtendedTraderEntity trader = (ExtendedTraderEntity) event.getTarget();

				if (trader.canTradeWith(event.getPlayer())) {
					ExtendedTraderHelper.openTraderGui((NPCEntity) event.getTarget(), event.getPlayer(), trader);
				} else {
					if (!event.getPlayer().level.isClientSide)
						event.getPlayer().sendMessage(new TranslationTextComponent("gui.lotrextended.trading.trader_alignment_too_low"), event.getPlayer().getUUID());
				}
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public static void preventCloakedRangersFromBeingTargeted(LivingSetAttackTargetEvent event) {
		if (event.getTarget() instanceof ExtendedDunedainRangerEntity) {
			ExtendedDunedainRangerEntity ranger = (ExtendedDunedainRangerEntity) event.getTarget();
			if (ranger.isCloaked()) {
				// set target to null
				if (event.getEntityLiving() instanceof NPCEntity) {
					NPCEntity npc = (NPCEntity) event.getEntityLiving();
					npc.setTarget(null);
				}
			}
		}
	}

}
