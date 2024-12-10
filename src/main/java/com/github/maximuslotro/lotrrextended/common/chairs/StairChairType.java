package com.github.maximuslotro.lotrrextended.common.chairs;

import com.github.maximuslotro.lotrrextended.LotrExtendedModServer;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;

public class StairChairType {
	private BlockPos chairPosition;
	private PlayerEntity sittingPlayer;
	private BatEntity chair;

	public StairChairType(BlockPos pos, PlayerEntity player) {
		chairPosition = pos;
		sittingPlayer = player;
		
		chair = new BatEntity(EntityType.BAT, player.level);
		//chair.setInvisible(true);
		chair.setInvulnerable(true);
		chair.setSilent(true);
		chair.setNoAi(true);
		chair.getPersistentData().putBoolean(HackyChairDatabase.CHAIRTAG, true);
		chair.moveTo(chairPosition, 0, 0);
		
		player.level.addFreshEntity(chair);
		
		((ServerPlayerEntity)sittingPlayer).startRiding(player, false);
		System.out.println("Made Chair");
	}

	public void killChair() {
		System.out.println("Killing Chair");

		chair.remove();
		LotrExtendedModServer.hackyChairManager.chairs.remove(this);
		System.out.println("Killed Chair");
	}

	public BlockPos getChairPosition() {
		return chairPosition;
	}

	public PlayerEntity getSittingPlayer() {
		return sittingPlayer;
	}

	public BatEntity getChair() {
		return chair;
	}
}
