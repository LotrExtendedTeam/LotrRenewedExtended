package com.github.maximuslotro.lotrrextended.common.network;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import com.github.maximuslotro.lotrrextended.client.gui.screen.ExtendedMapScreen;
import com.github.maximuslotro.lotrrextended.common.data.ExtendedLevelData;
import com.github.maximuslotro.lotrrextended.common.map.conquest.ExtendedConquestPos;
import com.github.maximuslotro.lotrrextended.common.map.conquest.ExtendedConquestRegion;

import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

public class ExtendedSConquestDataPacket {
	private Map<ExtendedConquestPos, ExtendedConquestRegion> conquestMap;
	private ResourceLocation faction;
	private int conquestScale;

	public ExtendedSConquestDataPacket(ResourceLocation pFaction, int pConquestScale, Map<ExtendedConquestPos, ExtendedConquestRegion> pConquestMap) {
		this.faction = pFaction;
		this.conquestScale = pConquestScale;
		this.conquestMap = pConquestMap;
	}

	public static void encode(ExtendedSConquestDataPacket packet, PacketBuffer buf) {
		buf.writeResourceLocation(packet.faction);
		buf.writeInt(packet.conquestScale);
		buf.writeInt(packet.conquestMap.size());
		for(ExtendedConquestRegion region : packet.conquestMap.values()) {
			region.writeBuf(buf);
		}
	}

	public static ExtendedSConquestDataPacket decode(PacketBuffer buf) {
		ResourceLocation faction = buf.readResourceLocation();
		int conquestScale = buf.readInt();
		int mapSize = buf.readInt();
		Map<ExtendedConquestPos, ExtendedConquestRegion> conquestMap = new HashMap<>();
		for(int i=0; i<mapSize; i++) {
			ExtendedConquestRegion region = ExtendedConquestRegion.readBuf(buf);
			ExtendedConquestPos pos = region.getConquestPos();
			conquestMap.put(pos, region);
		}
		return new ExtendedSConquestDataPacket(faction, conquestScale, conquestMap);
	}

	public ResourceLocation getFaction() {
		return faction;
	}

	public int getConquestScale() {
		return conquestScale;
	}

	public Map<ExtendedConquestPos, ExtendedConquestRegion> getConquestMap() {
		return conquestMap;
	}

	public static void handle(ExtendedSConquestDataPacket packet, Supplier<NetworkEvent.Context> context) {
		context.get().setPacketHandled(true);
		context.get().enqueueWork(() -> {
			DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () ->  {
				ExtendedLevelData.clientInstance().getConquestData().receiveConquestDataOnClient(packet);
				Minecraft mc = Minecraft.getInstance();
				if(mc!=null && mc.screen instanceof ExtendedMapScreen) {
					((ExtendedMapScreen)mc.screen).refreshConquestData();
				}
			});
		});
	}
}