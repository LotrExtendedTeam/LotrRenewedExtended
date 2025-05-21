package com.github.maximuslotro.lotrrextended.mixins.lotr.common.item;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;

import lotr.common.data.FastTravelDataModule;
import lotr.common.data.LOTRLevelData;
import lotr.common.item.RedBookItem;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.SPacketOpenScreen;
import lotr.common.util.LOTRUtil;
import lotr.common.world.map.CustomWaypointStructureHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

/**
 * Removed waypoint gamerule reference to add split-type waypoint config settings
 * @Mixin (RedBookItem.class)
 * */
@Mixin(RedBookItem.class)
public class MixinRedBookItem {

	/**
	 * Plays the failed sound when waypoint creation is denied
	 * @Shadow (remap = false)
	 * @param world The world for the sound to be played at
	 * @param pos The position at which the sound will be played at
	 * */
	@Shadow(remap = false)
	private void playFailedSound(World world, BlockPos pos) {}
	/**
	 * Plays the open book sound
	 * @Shadow (remap = false)
	 * @param world The world for the sound to be played at
	 * @param pos The position at which the sound will be played at
	 * */
	@Shadow(remap = false)
	private void playOpenScreenSound(World world, BlockPos pos) {}

	/**
	 * @reason Need to change waypoint Gamerule call
	 * @author maximuslotro
	 * @Overwrite (remap = false)
	 * @param world The world for the waypoint to be generated at
	 * @param pos The position at which the waypoint will be generated at
	 * @param player The player that is initiating the waypoint creation
	 * @return Returns TRUE if the waypoint was successfully created
	 */
	@Overwrite(remap = false)
	public boolean createCustomWaypointStructure(World world, BlockPos pos, PlayerEntity player) {
		if (!world.isClientSide) {
			ServerWorld sWorld = (ServerWorld) world;
			boolean canBeCWPStructure = CustomWaypointStructureHandler.INSTANCE.isFocalPoint(world, pos);
			if (canBeCWPStructure) {
				// Extended Start
				if (!ExtendedServerConfig.enableRenewedWaypointCreation.get()) {
					LOTRUtil.sendMessage(player, new TranslationTextComponent("chat.lotr.cwp.create.disabled"));
					return false;
				}
				// Extended End
				if (CustomWaypointStructureHandler.INSTANCE.hasAdjacentWaypointMarker(world, pos)) {
					LOTRUtil.sendMessage(player, new TranslationTextComponent("chat.lotr.cwp.alreadyExists"));
					playFailedSound(world, pos);
					return true;
				}
				FastTravelDataModule ftData = LOTRLevelData.sidedInstance(world).getData(player).getFastTravelData();
				if (!ftData.canCreateOrAdoptMoreCustomWaypoints()) {
					LOTRUtil.sendMessage(player, new TranslationTextComponent("chat.lotr.cwp.limit"));
					playFailedSound(world, pos);
					return true;
				}
				boolean isCompleteStructure = CustomWaypointStructureHandler.INSTANCE.isFocalPointOfCompletableStructure(sWorld, pos, msg -> LOTRUtil.sendMessage(player, msg));
				if (isCompleteStructure) {
					CustomWaypointStructureHandler.INSTANCE.setPlayerClickedOnBlockToCreate(player, pos);
					LOTRPacketHandler.sendTo(new SPacketOpenScreen(SPacketOpenScreen.Type.CREATE_CUSTOM_WAYPOINT), (ServerPlayerEntity) player);
					playOpenScreenSound(world, pos);
				} else {
					playFailedSound(world, pos);
				}
				return true;
			}
		}
		return false;
	}
}
