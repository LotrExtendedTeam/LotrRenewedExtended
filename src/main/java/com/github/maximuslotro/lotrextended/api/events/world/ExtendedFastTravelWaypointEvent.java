package com.github.maximuslotro.lotrextended.api.events.world;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.event.lifecycle.IModBusEvent;

/**
 * Event fired during the fast travel process to a waypoint, providing context about the player,
 * the world, the original position, and the target travel position.
 * This event is extended by Pre and Post subclasses to represent stages before and after the travel.
 */
public class ExtendedFastTravelWaypointEvent extends Event implements IModBusEvent {
	/**
     * The player initiating the fast travel.
     */
	private ServerPlayerEntity player;
	/**
     * The world in which the fast travel occurs.
     */
	private ServerWorld world;
	/**
     * The position to which the player intends to travel.
     */
	private BlockPos travelPos;
	/**
     * The player's original position before travel.
     */
	private BlockPos originalPos;
	/**
     * Constructs the event with player, world, original position, and travel target position.
     *
     * @param player the player performing the fast travel
     * @param world the world context of the fast travel
     * @param originalPos the original position of the player before travel
     * @param travelPos the target position for fast travel
     */
	private ExtendedFastTravelWaypointEvent(ServerPlayerEntity player, ServerWorld world, BlockPos originalPos, BlockPos travelPos) {
		this.player = player;
		this.world = world;
		this.originalPos = originalPos;
		this.travelPos = travelPos;
	}
	/**
     * Returns the player who is fast traveling.
     *
     * @return the player entity
     */
	public ServerPlayerEntity getPlayer() {
		return player;
	}
	/**
     * Returns the world where the fast travel is taking place.
     *
     * @return the world instance
     */
	public ServerWorld getWorld() {
		return world;
	}
	/**
     * Returns the target position for the fast travel.
     *
     * @return the destination position
     */
	public BlockPos getTravelPos() {
		return travelPos;
	}
	/**
     * Returns the original position of the player before fast travel.
     *
     * @return the starting position
     */
	public BlockPos getOriginalPos() {
		return originalPos;
	}
	/**
     * Event fired before the fast travel action occurs.
     */
	public static class Pre extends ExtendedFastTravelWaypointEvent {
		/**
         * Constructs the Pre event with player, world, original position, and travel target position.
         *
         * @param player the player performing the fast travel
         * @param world the world context
         * @param originalPos the starting position before travel
         * @param travelPos the target position for travel
         */
		public Pre(ServerPlayerEntity player, ServerWorld world, BlockPos originalPos, BlockPos travelPos) {
			super(player, world, originalPos, travelPos);
		}
	}

	/**
     * Event fired after the fast travel action has completed.
     */
	public static class Post extends ExtendedFastTravelWaypointEvent {
		/**
         * Constructs the Post event with player, world, original position, and travel target position.
         *
         * @param player the player performing the fast travel
         * @param world the world context
         * @param originalPos the starting position before travel
         * @param travelPos the target position for travel
         */
		public Post(ServerPlayerEntity player, ServerWorld world, BlockPos originalPos, BlockPos travelPos) {
			super(player, world, originalPos, travelPos);
		}
	}
}