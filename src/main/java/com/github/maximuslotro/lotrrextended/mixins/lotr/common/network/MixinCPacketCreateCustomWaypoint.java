package com.github.maximuslotro.lotrrextended.mixins.lotr.common.network;

import java.lang.reflect.Field;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;

import lotr.common.LOTRLog;
import lotr.common.data.FastTravelDataModule;
import lotr.common.init.LOTRWorldTypes;
import lotr.common.item.RedBookItem;
import lotr.common.network.CPacketCreateCustomWaypoint;
import lotr.common.stat.LOTRStats;
import lotr.common.util.LOTRUtil;
import lotr.common.util.UsernameHelper;
import lotr.common.world.map.CustomWaypoint;
import lotr.common.world.map.CustomWaypointStructureHandler;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;

@Mixin(CPacketCreateCustomWaypoint.class)
public class MixinCPacketCreateCustomWaypoint {

	/**
	 * @reason Because I'm Lazy and didn't want to write an inject
	 * @author maximuslotro
	 * */
	@Overwrite(remap = false)
	private static void doCreateCustomWaypoint(CPacketCreateCustomWaypoint packet, ServerPlayerEntity player, FastTravelDataModule ftData) {
		ServerWorld world = player.getLevel();
		if (!ExtendedServerConfig.enableRenewedWaypointCreation.get()) {
			LOTRUtil.sendMessage(player, new TranslationTextComponent("chat.lotr.cwp.create.disabled.gamerule"));
		} else if (!LOTRWorldTypes.hasMapFeatures(world)) {
			LOTRUtil.sendMessage(player, new TranslationTextComponent("chat.lotr.cwp.create.disabled.worldtype"));
		} else if (!ftData.canCreateOrAdoptMoreCustomWaypoints()) {
			LOTRLog.warn("Player %s tried to create a custom waypoint but has reached their limit", UsernameHelper.getRawUsername(player));
		} else {
			String waypointName = "";
			String waypointLore = "";
			try {
				Field field = CPacketCreateCustomWaypoint.class.getField("name");
				waypointName = (String) field.get(packet);
			} catch (SecurityException | IllegalAccessException | IllegalArgumentException | NoSuchFieldException e) {
				e.printStackTrace();
			}
			try {
				Field field = CPacketCreateCustomWaypoint.class.getField("lore");
				waypointLore = (String) field.get(packet);
			} catch (SecurityException | IllegalAccessException | IllegalArgumentException | NoSuchFieldException e) {
				e.printStackTrace();
			}
			if (waypointName.isEmpty()) {
				LOTRLog.warn("Player %s tried to create a custom waypoint with a blank name", UsernameHelper.getRawUsername(player));
			} else if (waypointName.length() > 40) {
				LOTRLog.warn("Player %s tried to create a custom waypoint with a name too long (%s)", UsernameHelper.getRawUsername(player), waypointName);
			} else if (waypointLore.length() > 160) {
				LOTRLog.warn("Player %s tried to create a custom waypoint with lore too long (%s)", UsernameHelper.getRawUsername(player), waypointLore);
			} else {
				boolean isPublic = false;
				try {
					Field field = CPacketCreateCustomWaypoint.class.getField("isPublic");
					isPublic = (Boolean) field.get(packet);
				} catch (SecurityException | IllegalAccessException | IllegalArgumentException | NoSuchFieldException e) {
					e.printStackTrace();
				}
				BlockPos waypointPos = CustomWaypointStructureHandler.INSTANCE.getPlayerClickedOnBlockToCreate(player);
				if (waypointPos == null) {
					LOTRLog.warn("Player %s tried to create a custom waypoint without having clicked on a block", UsernameHelper.getRawUsername(player));
				} else {
					Vector3d playerPos = player.position();
					if (waypointPos.distSqr(playerPos, false) >= 64.0D) {
						LOTRLog.warn("Player %s tried to create a custom waypoint on a block too far away (player pos = %s, clicked pos = %s)", UsernameHelper.getRawUsername(player), playerPos.toString(), waypointPos.toString());
					} else if (!CustomWaypointStructureHandler.INSTANCE.isFocalPointOfCompletableStructure(world, waypointPos)) {
						LOTRLog.warn("Player %s tried to create a custom waypoint without a completed structure (player pos = %s, clicked pos = %s)", UsernameHelper.getRawUsername(player), playerPos.toString(), waypointPos.toString());
					} else if (CustomWaypointStructureHandler.INSTANCE.hasAdjacentWaypointMarker(world, waypointPos)) {
						LOTRLog.warn("Player %s tried to create a custom waypoint at an already completed structure (player pos = %s, clicked pos = %s)", UsernameHelper.getRawUsername(player), playerPos.toString(), waypointPos.toString());
					} else {
						CustomWaypointStructureHandler.INSTANCE.clearPlayerClickedOnBlockToCreate(player);
						CustomWaypoint createdWaypoint = ftData.createNewCustomWaypoint(waypointName, waypointLore, isPublic, waypointPos);
						CustomWaypointStructureHandler.INSTANCE.completeStructureWithCreatedWaypoint(player, createdWaypoint);
						LOTRUtil.sendMessage(player, new TranslationTextComponent("chat.lotr.cwp.create", waypointName, ftData.getNumCustomWaypoints(), ftData.getMaxCustomWaypoints()));
						RedBookItem.playCompleteWaypointActionSound(world, waypointPos);
						player.awardStat(LOTRStats.CREATE_CUSTOM_WAYPOINT);
					}
				}
			}
		}
	}
}
