package com.github.maximuslotro.lotrrextended.common.network;

import java.util.function.Supplier;

import com.github.maximuslotro.lotrrextended.common.inventory.container.ExtendedRepairContainerNew;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.SharedConstants;
import net.minecraftforge.fml.network.NetworkEvent.Context;

/**
 * Network packet used for sending item rename requests from the client to the server.
 * <p>
 * This packet is responsible for transmitting the new item name entered by a player 
 * within an {@link ExtendedRepairContainer}. The server validates and applies the name 
 * change to the item being repaired.
 * </p>
 */
public class ExtendedCRenameItemPacket {
	/** The new name to apply to the item. */
	private String name;
	/**
	 * Constructs a new rename packet.
	 *
	 * @param name The name the player wants to assign to the item.
	 */
	public ExtendedCRenameItemPacket(String name) {
		this.name = name;
	}
	/**
	 * Encodes the packet data for transmission over the network.
	 *
	 * @param packet The rename packet to encode.
	 * @param buf    The buffer to write data into.
	 */
	public static void encode(ExtendedCRenameItemPacket packet, PacketBuffer buf) {
		buf.writeUtf(packet.name);
	}
	/**
	 * Decodes packet data received from the network.
	 *
	 * @param buf The buffer containing the encoded packet data.
	 * @return A new instance of {@link ExtendedCRenameItemPacket}.
	 */
	public static ExtendedCRenameItemPacket decode(PacketBuffer buf) {
		return new ExtendedCRenameItemPacket(buf.readUtf(32767));
	}
	/**
	 * Handles the received rename packet on the server side.
	 * <p>
	 * Ensures that the sender's current container is an {@link ExtendedRepairContainer}, 
	 * validates the proposed name (via {@link SharedConstants#filterText(String)}), 
	 * and applies it if it does not exceed the 35-character limit.
	 * </p>
	 *
	 * @param packet  The rename packet to process.
	 * @param context The network context supplier containing sender and handling state.
	 */
	public static void handle(ExtendedCRenameItemPacket packet, Supplier<Context> context) {
		if (context.get().getSender().containerMenu instanceof ExtendedRepairContainerNew) {
			ExtendedRepairContainerNew repaircontainer = (ExtendedRepairContainerNew)context.get().getSender().containerMenu;
			String s = SharedConstants.filterText(packet.getName());
			if (s.length() <= 35) {
				repaircontainer.setItemName(s);
			}
		}
		context.get().setPacketHandled(true);
	}
	/**
	 * Gets the new item name carried by this packet.
	 *
	 * @return The name string.
	 */
	private String getName() {
		return name;
	}
}
