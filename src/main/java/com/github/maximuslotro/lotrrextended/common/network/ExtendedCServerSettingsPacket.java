package com.github.maximuslotro.lotrrextended.common.network;

import java.util.function.Supplier;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public class ExtendedCServerSettingsPacket {
	private final ExtendedSettingToggles type;
	private final ValueType valueType;
	private final Object value; // stores Boolean, Integer, or Double

	public ExtendedCServerSettingsPacket(ExtendedSettingToggles type, Object value) {
		this.type = type;
		this.valueType = type.getValueType();
		// sanity check
		switch (valueType) {
			case BOOLEAN:
				if (!(value instanceof Boolean)) throw new IllegalArgumentException("Expected Boolean");
				break;
			case INT:
				if (!(value instanceof Integer)) throw new IllegalArgumentException("Expected Integer");
				break;
			case DOUBLE:
				if (!(value instanceof Double)) throw new IllegalArgumentException("Expected Double");
				break;
		}
		this.value = value;
	}

	public static void encode(ExtendedCServerSettingsPacket packet, PacketBuffer buf) {
		buf.writeEnum(packet.type);
		switch (packet.valueType) {
			case BOOLEAN:
				buf.writeBoolean((Boolean) packet.value);
				break;
			case INT:
				buf.writeInt((Integer) packet.value);
				break;
			case DOUBLE:
				buf.writeDouble((Double) packet.value);
				break;
		}
	}

	public static ExtendedCServerSettingsPacket decode(PacketBuffer buf) {
		ExtendedSettingToggles type = buf.readEnum(ExtendedSettingToggles.class);
		Object value;
		switch (type.getValueType()) {
			case BOOLEAN:
				value = buf.readBoolean();
				break;
			case INT:
				value = buf.readInt();
				break;
			case DOUBLE:
				value = buf.readDouble();
				break;
			default:
				throw new IllegalStateException("Unknown ValueType");
		}
		return new ExtendedCServerSettingsPacket(type, value);
	}

	public static void handle(ExtendedCServerSettingsPacket packet, Supplier<NetworkEvent.Context> context) {
		context.get().setPacketHandled(true);
		ServerPlayerEntity player = context.get().getSender();
		if(ServerLifecycleHooks.getCurrentServer().isDedicatedServer() &&
				!ServerLifecycleHooks.getCurrentServer().getPlayerList().isOp(player.getGameProfile())) {
			ExtendedLog.warn("{} tried editing server settings while not OP in multiplayer!", player.getName().getString());
			return;
		}
		try {
			switch (packet.getType()) {
				case BANNER_PROTECTION:
					setBoolean(ExtendedServerConfig.enableBannerProtection, packet.getBoolean());
					break;
				case FELLOWSHIP_CREATION:
					setBoolean(ExtendedServerConfig.enableFellowshipCreation, packet.getBoolean());
					break;
				case RENEWED_WAYPOINT_CREATE:
					setBoolean(ExtendedServerConfig.enableRenewedWaypointCreation, packet.getBoolean());
					break;
				case LEGACY_WAYPOINT_CREATE:
					setBoolean(ExtendedServerConfig.enableLegacyWaypointCreation, packet.getBoolean());
					break;
				case DEFAULT_WAYPOINT_TELEPORT:
					setBoolean(ExtendedServerConfig.enableDefaultWaypointTeleportation, packet.getBoolean());
					break;
				case CUSTOM_WAYPOINT_TELEPORT:
					setBoolean(ExtendedServerConfig.enableCustomWaypointTeleportation, packet.getBoolean());
					break;
				case FEAST_MODE:
					setBoolean(ExtendedServerConfig.enableLegacyFeastMode, packet.getBoolean());
					break;
				case DECREASED_HUNGER:
					setBoolean(ExtendedServerConfig.enableHungerDecrease, packet.getBoolean());
					break;
				case GOBAL_NPC_SPAWN_DAMPING:
					setDouble(ExtendedServerConfig.npcGlobalSpawnDampingCoefficient, packet.getDouble());
					break;
				case BARROW_WIGHT_SPAWN_CHANCE:
					setInteger(ExtendedServerConfig.barrowWightChestFirstSpawnChance, packet.getInteger());
					break;
				case AGGRESSIVE_CAPTAINS:
					setBoolean(ExtendedServerConfig.enableLegacyCaptainPassiveBehavior, packet.getBoolean());
					break;
				case AGGRESSIVE_TRADERS:
					setBoolean(ExtendedServerConfig.enableLegacyTraderPassiveBehavior, packet.getBoolean());
					break;
				case WANDERING_TRADERS:
					setBoolean(ExtendedServerConfig.enableWanderingTraderSpawns, packet.getBoolean());
					break;
				case BANDIT_ENCOUNTERS:
					setBoolean(ExtendedServerConfig.enableBanditEncounters, packet.getBoolean());
					break;
				case TOLKIEN_ENCOUNTER:
					setBoolean(ExtendedServerConfig.enableTolkienSpawning, packet.getBoolean());
					break;
				case BARS_CONNECT_TO_BRANCHES:
					setBoolean(ExtendedServerConfig.barsConnectToBranches, packet.getBoolean());
					break;
				case BARS_CONNECT_TO_SLAB_SIDES:
					setBoolean(ExtendedServerConfig.barsConnectToSlabSides, packet.getBoolean());
					break;
				case METAL_DOORS_REQUIRE_POWER:
					setBoolean(ExtendedServerConfig.makeMetalDoorsRequirePower, packet.getBoolean());
					break;
				case DRIPSTONE_HURT_HEIGHT:
					setDouble(ExtendedServerConfig.dripstoneMinimumDamageHeight, packet.getDouble());
					break;
				case BELL_RING_CHAIN_LENGTH:
					setInteger(ExtendedServerConfig.bellRingChainMaxLength, packet.getInteger());
					break;
				case CHAIR_SITTING:
					setBoolean(ExtendedServerConfig.enableChairSlabSitting, packet.getBoolean());
					break;
				case CHAIR_SWAPPING:
					setBoolean(ExtendedServerConfig.enableChairSwapping, packet.getBoolean());
					break;
				case CHAIR_PROTECTION_OVERRIDE:
					setBoolean(ExtendedServerConfig.enableChairProtectionOverride, packet.getBoolean());
					break;
				case VANILLA_ENCHANTING:
					setBoolean(ExtendedServerConfig.enableVanillaEnchanting, packet.getBoolean());
					break;
				case VANILLA_XP:
					setBoolean(ExtendedServerConfig.enableVanillaXp, packet.getBoolean());
					break;
				case EXTENDED_MODIFIERS:
					setBoolean(ExtendedServerConfig.enableExtendedModifiers, packet.getBoolean());
					break;
				case EXTENDED_SPAWN_PROTECTION:
					setBoolean(ExtendedServerConfig.enableExtendedSpawnProtection, packet.getBoolean());
					break;
				case OVERWORLD_SPAWN_RADIUS:
					setInteger(ExtendedServerConfig.overworldSpawnProtectionRadius, packet.getInteger());
					break;
				case MIDDLE_EARTH_SPAWN_RADIUS:
					setInteger(ExtendedServerConfig.middleEarthSpawnProtectionRadius, packet.getInteger());
					break;
				case FIXED_STRUCTURES:
					setBoolean(ExtendedServerConfig.enableFixedStructures, packet.getBoolean());
					break;
				case FIXED_STRUCTURE_IMLADRIS:
					setBoolean(ExtendedServerConfig.enableFixedStructureImladris, packet.getBoolean());
					break;
				case FIXED_STRUCTURE_TOWER_HILLS:
					setBoolean(ExtendedServerConfig.enableFixedStructureTowerHills, packet.getBoolean());
					break;
				default:
					ExtendedLog.warn("Forgot to make handler for setting: "+packet.getType());
					break;
			}
		} catch (IllegalStateException e) {
			ExtendedLog.error("Packet type/value mismatch: " + e.getMessage());
		}
	}

	public boolean getBoolean() {
		if (valueType != ValueType.BOOLEAN) throw new IllegalStateException();
		return (Boolean) value;
	}

	public static void setBoolean(ForgeConfigSpec.BooleanValue config, boolean newValue) {
		if(config.get()!=newValue) {
			config.set(newValue);
			ExtendedPacketHandler.syncServerConfigWithAllClients();
		}
	}

	public int getInteger() {
		if (valueType != ValueType.INT) throw new IllegalStateException();
		return (Integer) value;
	}

	public static void setInteger(ForgeConfigSpec.IntValue config, int newValue) {
		if(config.get()!=newValue) {
			config.set(newValue);
			ExtendedPacketHandler.syncServerConfigWithAllClients();
		}
	}

	public double getDouble() {
		if (valueType != ValueType.DOUBLE) throw new IllegalStateException();
		return (Double) value;
	}


	public static void setDouble(ForgeConfigSpec.DoubleValue config, double newValue) {
		if(config.get()!=newValue) {
			config.set(newValue);
			ExtendedPacketHandler.syncServerConfigWithAllClients();
		}
	}

	public ValueType getValueType() {
		return valueType;
	}

	public ExtendedSettingToggles getType() {
		return this.type;
	}

	public enum ValueType {
		BOOLEAN,
		INT,
		DOUBLE
	}

	public enum ExtendedSettingToggles {
		// Gameplay
		BANNER_PROTECTION(ValueType.BOOLEAN),
		FELLOWSHIP_CREATION(ValueType.BOOLEAN),
		RENEWED_WAYPOINT_CREATE(ValueType.BOOLEAN),
		LEGACY_WAYPOINT_CREATE(ValueType.BOOLEAN),
		DEFAULT_WAYPOINT_TELEPORT(ValueType.BOOLEAN),
		CUSTOM_WAYPOINT_TELEPORT(ValueType.BOOLEAN),
		FEAST_MODE(ValueType.BOOLEAN),
		DECREASED_HUNGER(ValueType.BOOLEAN),
		GOBAL_NPC_SPAWN_DAMPING(ValueType.DOUBLE),
		// Entity
		BANDIT_ENCOUNTERS(ValueType.BOOLEAN),
		TOLKIEN_ENCOUNTER(ValueType.BOOLEAN),
		WANDERING_TRADERS(ValueType.BOOLEAN),
		BARROW_WIGHT_SPAWN_CHANCE(ValueType.INT),
		AGGRESSIVE_CAPTAINS(ValueType.BOOLEAN),
		AGGRESSIVE_TRADERS(ValueType.BOOLEAN),
		// Block
		BARS_CONNECT_TO_BRANCHES(ValueType.BOOLEAN),
		BARS_CONNECT_TO_SLAB_SIDES(ValueType.BOOLEAN),
		METAL_DOORS_REQUIRE_POWER(ValueType.BOOLEAN),
		BELL_RING_CHAIN_LENGTH(ValueType.INT),
		DRIPSTONE_HURT_HEIGHT(ValueType.DOUBLE),
		// Chair
		CHAIR_SITTING(ValueType.BOOLEAN),
		CHAIR_SWAPPING(ValueType.BOOLEAN),
		CHAIR_PROTECTION_OVERRIDE(ValueType.BOOLEAN),
		// Modifiers
		VANILLA_ENCHANTING(ValueType.BOOLEAN),
		VANILLA_XP(ValueType.BOOLEAN),
		EXTENDED_MODIFIERS(ValueType.BOOLEAN),
		// Protection
		EXTENDED_SPAWN_PROTECTION(ValueType.BOOLEAN),
		OVERWORLD_SPAWN_RADIUS(ValueType.INT),
		MIDDLE_EARTH_SPAWN_RADIUS(ValueType.INT),
		// World Generation
		FIXED_STRUCTURES(ValueType.BOOLEAN),
		FIXED_STRUCTURE_IMLADRIS(ValueType.BOOLEAN),
		FIXED_STRUCTURE_TOWER_HILLS(ValueType.BOOLEAN);

		private final ValueType valueType;

		ExtendedSettingToggles(ValueType valueType) {
			this.valueType = valueType;
		}

		public ValueType getValueType() {
			return valueType;
		}
	}

}
