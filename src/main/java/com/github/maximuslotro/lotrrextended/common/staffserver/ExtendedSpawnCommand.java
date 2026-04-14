package com.github.maximuslotro.lotrrextended.common.staffserver;

import java.util.EnumSet;


import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.server.SPlayerPositionLookPacket;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.server.TicketType;

public class ExtendedSpawnCommand {
	private static final SimpleCommandExceptionType INVALID_POSITION = new SimpleCommandExceptionType(new TranslationTextComponent("commands.teleport.invalidPosition"));
	private static final SimpleCommandExceptionType NO_VECHICLES = new SimpleCommandExceptionType(new StringTextComponent("Must not be riding!"));
	private static final SimpleCommandExceptionType IN_COMBAT = new SimpleCommandExceptionType(new StringTextComponent("You cannot teleport while in combat!").withStyle(TextFormatting.RED));

	public static void register(CommandDispatcher<CommandSource> pDispatcher) {
		pDispatcher.register(Commands.literal("spawnShire").executes((commandSource) -> {
			return tp(commandSource.getSource(), new BlockPos(0, 72, 0), 90, 0, "lotr:middle_earth");}));
		pDispatcher.register(Commands.literal("spawn").executes((commandSource) -> {
			return tp(commandSource.getSource(), new BlockPos(-86, 114, 6), 8, 8, "minecraft:overworld");}));
		pDispatcher.register(Commands.literal("lobby").executes((commandSource) -> {
			return tp(commandSource.getSource(), new BlockPos(-86, 114, 6), 8, 8, "minecraft:overworld");}));
	}

	private static int tp(CommandSource pSource, BlockPos pos, int pYaw, int pPitch, String dimension) throws CommandSyntaxException {
		if(!canEntityTeleport(pSource.getPlayerOrException())) {
			throw NO_VECHICLES.create();
		}
		if (ExtendedCombatTracker.isInCombat(pSource.getEntity().getUUID())) {
			throw IN_COMBAT.create();
		}
		RegistryKey<World> registrykey = RegistryKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(dimension));
		ServerWorld serverworld = pSource.getServer().getLevel(registrykey);
		pSource.withLevel(serverworld);
		performTeleport(pSource, pSource.getPlayerOrException(), serverworld, pos.getX(), pos.getY(), pos.getZ(), pYaw, pPitch);
		return Command.SINGLE_SUCCESS;
	}

	private static void performTeleport(CommandSource pSource, Entity pEntity, ServerWorld pLevel, double pX, double pY, double pZ, float pYaw, float pPitch) throws CommandSyntaxException {
		net.minecraftforge.event.entity.living.EntityTeleportEvent.TeleportCommand event = net.minecraftforge.event.ForgeEventFactory.onEntityTeleportCommand(pEntity, pX, pY, pZ);
		if (event.isCanceled()) return;
		pX = event.getTargetX(); pY = event.getTargetY(); pZ = event.getTargetZ();
		BlockPos blockpos = new BlockPos(pX, pY, pZ);
		if (!World.isInSpawnableBounds(blockpos)) {
			throw INVALID_POSITION.create();
		} else {
			if (pEntity instanceof ServerPlayerEntity) {
				ChunkPos chunkpos = new ChunkPos(new BlockPos(pX, pY, pZ));
				pLevel.getChunkSource().addRegionTicket(TicketType.POST_TELEPORT, chunkpos, 1, pEntity.getId());
				pEntity.stopRiding();
				if (((ServerPlayerEntity)pEntity).isSleeping()) {
					((ServerPlayerEntity)pEntity).stopSleepInBed(true, true);
				}

				if (pLevel == pEntity.level) {
					((ServerPlayerEntity)pEntity).connection.teleport(pX, pY, pZ, pYaw, pPitch, EnumSet.noneOf(SPlayerPositionLookPacket.Flags.class));
				} else {
					((ServerPlayerEntity)pEntity).teleportTo(pLevel, pX, pY, pZ, pYaw, pPitch);
				}

				pEntity.setYHeadRot(pYaw);
			} else {
				float f1 = MathHelper.wrapDegrees(pYaw);
				float f = MathHelper.wrapDegrees(pPitch);
				f = MathHelper.clamp(f, -90.0F, 90.0F);
				if (pLevel == pEntity.level) {
					pEntity.moveTo(pX, pY, pZ, f1, f);
					pEntity.setYHeadRot(f1);
				} else {
					pEntity.unRide();
					Entity entity = pEntity;
					pEntity = pEntity.getType().create(pLevel);
					if (pEntity == null) {
						return;
					}

					pEntity.restoreFrom(entity);
					pEntity.moveTo(pX, pY, pZ, f1, f);
					pEntity.setYHeadRot(f1);
					pLevel.addFromAnotherDimension(pEntity);
				}
			}

			if (!(pEntity instanceof LivingEntity) || !((LivingEntity)pEntity).isFallFlying()) {
				pEntity.setDeltaMovement(pEntity.getDeltaMovement().multiply(1.0D, 0.0D, 1.0D));
				pEntity.setOnGround(true);
			}

			if (pEntity instanceof CreatureEntity) {
				((CreatureEntity)pEntity).getNavigation().stop();
			}

		}
	}
	private static boolean canEntityTeleport(Entity entity) {
		// use vanilla portal logic from BlockPortal#onEntityCollision
		return !entity.isPassenger() && !entity.isVehicle() && entity.canChangeDimensions();
	}
}