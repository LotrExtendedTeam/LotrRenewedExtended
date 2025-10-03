package com.github.maximuslotro.lotrrextended.common.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntitySummonArgument;
import net.minecraft.command.arguments.NBTCompoundTagArgument;
import net.minecraft.command.arguments.SuggestionProviders;
import net.minecraft.command.arguments.Vec3Argument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class ExtendedSummonCommand {
	private static final SimpleCommandExceptionType ERROR_FAILED = new SimpleCommandExceptionType(new TranslationTextComponent("commands.summon.failed"));
	private static final SimpleCommandExceptionType ERROR_DUPLICATE_UUID = new SimpleCommandExceptionType(new TranslationTextComponent("commands.summon.failed.uuid"));
	private static final SimpleCommandExceptionType INVALID_POSITION = new SimpleCommandExceptionType(new TranslationTextComponent("commands.summon.invalidPosition"));

	public static void register(CommandDispatcher<CommandSource> pDispatcher) {
		pDispatcher.register(Commands.literal("lotrExtendedSummon").requires((commandSource) -> {
			return commandSource.hasPermission(2);
		}).then(Commands.argument("entity", EntitySummonArgument.id()).suggests(SuggestionProviders.SUMMONABLE_ENTITIES).executes((commandSource) -> {
			return spawnEntity(commandSource.getSource(), EntitySummonArgument.getSummonableEntity(commandSource, "entity"), commandSource.getSource().getPosition(), 
				new CompoundNBT(), true);
		}).then(Commands.argument("pos", Vec3Argument.vec3()).executes((commandSource) -> {
			return spawnEntity(commandSource.getSource(), EntitySummonArgument.getSummonableEntity(commandSource, "entity"), Vec3Argument.getVec3(commandSource, "pos"), 
				new CompoundNBT(), true);
		}).then(Commands.argument("nbt", NBTCompoundTagArgument.compoundTag()).executes((commandSource) -> {
			return spawnEntity(commandSource.getSource(), EntitySummonArgument.getSummonableEntity(commandSource, "entity"), Vec3Argument.getVec3(commandSource, "pos"), 
				NBTCompoundTagArgument.getCompoundTag(commandSource, "nbt"), false);
		}).then(Commands.argument("randomize", BoolArgumentType.bool()).executes((commandSource) -> {
			return spawnEntity(commandSource.getSource(), EntitySummonArgument.getSummonableEntity(commandSource, "entity"), Vec3Argument.getVec3(commandSource, "pos"), 
				NBTCompoundTagArgument.getCompoundTag(commandSource, "nbt"), BoolArgumentType.getBool(commandSource, "randomize"));
		}))))));
	}

	private static int spawnEntity(CommandSource pSource, ResourceLocation pType, Vector3d pPos, CompoundNBT pNbt, boolean randomize) throws CommandSyntaxException {
		BlockPos blockpos = new BlockPos(pPos);
		if (!World.isInSpawnableBounds(blockpos)) {
			throw INVALID_POSITION.create();
		} else {
			CompoundNBT compoundnbt = pNbt.copy();
			compoundnbt.putString("id", pType.toString());
			ServerWorld serverworld = pSource.getLevel();
			Entity entity = EntityType.loadEntityRecursive(compoundnbt, serverworld, (p_218914_1_) -> {
				p_218914_1_.moveTo(pPos.x, pPos.y, pPos.z, p_218914_1_.yRot, p_218914_1_.xRot);
				return p_218914_1_;
			});
			if (entity == null) {
				throw ERROR_FAILED.create();
			} else {
				if (randomize && entity instanceof MobEntity) {
					((MobEntity)entity).finalizeSpawn(pSource.getLevel(), pSource.getLevel().getCurrentDifficultyAt(entity.blockPosition()), SpawnReason.COMMAND, (ILivingEntityData)null, (CompoundNBT)null);
				}

				if (!serverworld.tryAddFreshEntityWithPassengers(entity)) {
					throw ERROR_DUPLICATE_UUID.create();
				} else {
					pSource.sendSuccess(new TranslationTextComponent("commands.summon.success", entity.getDisplayName()), true);
					return 1;
				}
			}
		}
	}
}