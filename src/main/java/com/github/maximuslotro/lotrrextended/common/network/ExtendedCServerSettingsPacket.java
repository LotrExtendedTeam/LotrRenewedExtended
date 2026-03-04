package com.github.maximuslotro.lotrrextended.common.network;

import java.util.function.Supplier;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
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
		if(!ServerLifecycleHooks.getCurrentServer().getPlayerList().isOp(player.getGameProfile())) {
			ExtendedLog.warn("{} tried editing server settings while not OP!", player.getName().getString());
			return;
		}
        try {
            switch (packet.getType()) {
                case BANNER_PROTECTION:
                    ExtendedServerConfig.enableBannerProtection.set(packet.getBoolean());
                    break;
                default:
                    ExtendedLog.info("For got to make handler for setting: "+packet.getType());
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

	public int getInt() {
	    if (valueType != ValueType.INT) throw new IllegalStateException();
	    return (Integer) value;
	}

	public double getDouble() {
	    if (valueType != ValueType.DOUBLE) throw new IllegalStateException();
	    return (Double) value;
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
	    BANNER_PROTECTION(ValueType.BOOLEAN),
	    SOME_INTEGER_SETTING(ValueType.INT),
	    SOME_DOUBLE_SETTING(ValueType.DOUBLE);

	    private final ValueType valueType;

	    ExtendedSettingToggles(ValueType valueType) {
	        this.valueType = valueType;
	    }

	    public ValueType getValueType() {
	        return valueType;
	    }
	}

}
