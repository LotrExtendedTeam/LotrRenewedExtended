package com.github.maximuslotro.lotrrextended.common.eventhandlers;


import com.github.maximuslotro.lotrrextended.common.block.beds.ExtendedBedrollBlock;

import lotr.common.entity.npc.ExtendedDunedainRangerEntity;
import lotr.common.entity.npc.ExtendedTraderEntity;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.item.LOTRSpawnEggItem;
import lotr.common.util.ExtendedTraderHelper;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerSetSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class WorldEventHandlers {

	@SubscribeEvent
	public static void onEntityRightClick(PlayerInteractEvent.EntityInteract event) {
		if (event.getPlayer().getItemInHand(event.getHand()).getItem() instanceof SpawnEggItem ||
				event.getPlayer().getItemInHand(event.getHand()).getItem() instanceof LOTRSpawnEggItem) {
			if (event.getTarget() instanceof ExtendedTraderEntity) {
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

	@SubscribeEvent
	public static void handleBedRollCancelSpawn(PlayerSetSpawnEvent event) {
		if(event.getPlayer().level.getBlockState(event.getNewSpawn()).getBlock() instanceof ExtendedBedrollBlock) {
			event.setCanceled(true);
		}
	}
}
