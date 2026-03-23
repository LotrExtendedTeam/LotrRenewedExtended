package com.github.maximuslotro.lotrrextended.common.network;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;
import com.github.maximuslotro.lotrrextended.common.staffserver.ExtendedSpecialConfig;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.moddiscovery.ModInfo;
import net.minecraftforge.fml.network.FMLConnectionData;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkHooks;

public class ExtendedCModListPacket {
	private List<String> mods;

	public ExtendedCModListPacket() {
		this.mods = ModList.get().getMods().stream().map(ModInfo::getModId).collect(Collectors.toList());
	}

	private ExtendedCModListPacket(List<String> mods) {
		this.mods = mods;
	}

	private List<String> getMods() {
		return mods;
	}

	public static ExtendedCModListPacket decode(PacketBuffer input) {
		List<String> mods = new ArrayList<>();
		int len = input.readVarInt();
		for (int x = 0; x < len; x++)
			mods.add(input.readUtf(0x100));
		return new ExtendedCModListPacket(mods);
	}

	public static void encode(ExtendedCModListPacket encode, PacketBuffer output) {
		output.writeVarInt(encode.mods.size());
		encode.mods.forEach(m -> output.writeUtf(m, 0x100));
	}

	public static void handle(ExtendedCModListPacket packet, Supplier<NetworkEvent.Context> context) {
		context.get().setPacketHandled(true);
		if (!ExtendedServerConfig.enableStaffServer.get()) {
			return;
		}
		FMLConnectionData data = NetworkHooks.getConnectionData(context.get().getNetworkManager());
		if (data != null) {
			ITextComponent disconnectReason = ExtendedSpecialConfig.handleModsList(context.get().getNetworkManager(), context.get().getSender().server, context.get().getSender().getGameProfile(), packet.getMods(), true);
			if(disconnectReason!=null) {
				context.get().getNetworkManager().disconnect(disconnectReason);
				return;
			}
		}
	}
}
