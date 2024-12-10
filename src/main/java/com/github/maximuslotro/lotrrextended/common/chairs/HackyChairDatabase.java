package com.github.maximuslotro.lotrrextended.common.chairs;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.annotation.Nullable;

import net.minecraft.block.StairsBlock;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.Half;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class HackyChairDatabase {
	protected List<StairChairType> chairs = new CopyOnWriteArrayList<StairChairType>();
	public final static String CHAIRTAG = "ExtChair";
	public HackyChairDatabase() {
		MinecraftForge.EVENT_BUS.register(this);
	}

	/**
	 * Ok, some funky stuff is being done here to be able to delete each chair before moving to the next.
	 * A chair is being used to obtain a threadsafe version of itself that can be deleated without causing {@Link ConcurrentModificationException}
	 * */
	public void handleServerStopping() {
		for(final StairChairType chair: chairs) {
			StairChairType internalThreadSafeChair = getChairByPlayer(chair.getSittingPlayer());
			if(internalThreadSafeChair != null) {
				internalThreadSafeChair.killChair();
			}
		}
	}

	@SubscribeEvent(receiveCanceled = true)
	public void extendedPlayerChairClick(RightClickBlock event) {
		// Check for valid player
		if(event.getPlayer().getUseItem().isEmpty() && !event.getPlayer().isShiftKeyDown() && !event.getPlayer().isPassenger()) {
			// Check for valid block location
			if(getChairByPosition(event.getPos(), event.getWorld())==null && event.getWorld().isEmptyBlock(event.getPos().above())) {
				// Check for valid block type
				if(event.getWorld().getBlockState(event.getPos()).getBlock() instanceof StairsBlock) {
					// Check valid orientation
					if(event.getWorld().getBlockState(event.getPos()).getValue(BlockStateProperties.HALF)==Half.BOTTOM) {
						chairs.add(new StairChairType(event.getPos(), event.getPlayer()));
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void extendedPlayerChairMove(LivingUpdateEvent event) {
		if(event.getEntity() instanceof BatEntity) {
			StairChairType eventChair = null;
			for (final StairChairType chair : chairs) {
				if (chair.getChair().equals(event.getEntity())) {
					eventChair = getChairByPlayer(chair.getSittingPlayer());
					System.out.println("UpdateFoundChair");
					event.setCanceled(true);
					break;
				}
			}
			if(eventChair!=null) {
				if(eventChair.getChair().isVehicle()) {
					System.out.println("Killing enpty passanger");
					//eventChair.killChair();
				}
			}else {
				if(event.getEntity().getPersistentData().getBoolean(CHAIRTAG)) {
					System.out.println("Killing currupted chair");
					event.getEntity().remove();
				}
			}
		}
	}

	@SubscribeEvent
	public void extendedBreakChairEvent(BreakEvent event) {
		StairChairType chair = getChairByPosition(event.getPos(), event.getPlayer().level);
		if(chair != null) {
			System.out.println("BlockBreak");
			event.getPlayer().removeVehicle();
			chair.killChair();
		}
	}

	@SubscribeEvent
	public void extendedPlayerChairLoggedOut(PlayerLoggedOutEvent event) {
		StairChairType chair = getChairByPlayer(event.getPlayer());
		if(chair != null) {
			System.out.println("PlayerLogged");
			event.getPlayer().removeVehicle();
			chair.killChair();
		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void extendedPlayerChairAttackedEvent(LivingAttackEvent e) {
		if(e.getEntity() instanceof PlayerEntity) {
			StairChairType chair = getChairByPlayer((PlayerEntity)e.getEntity());
			if(chair != null) {
				System.out.println("PlayerAttacked");
				((PlayerEntity)e.getEntity()).removeVehicle();
				chair.killChair();
			}
		}
	}

	@Nullable
	public StairChairType getChairByPlayer(PlayerEntity player) {
		for(StairChairType chair : chairs) {
			if(chair.getSittingPlayer().equals(player)) {
				System.out.println("FoundChair Player");
				return chair;
			}
		}
		return null;
	}

	@Nullable
	public StairChairType getChairByPosition(BlockPos pos, World uniqueLevel) {
		for(StairChairType chair : chairs) {
			if(chair.getChairPosition().equals(pos) && chair.getSittingPlayer().level.equals(uniqueLevel)) {
				System.out.println("Found Chair Pos");
				return chair;
			}
		}
		return null;
	}
}
