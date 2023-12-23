package lotr.common.network;

import lotr.client.gui.inv.ExtendedCoinExchangeScreen;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.inv.ExtendedCoinExchangeContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class ExtendedOpenCoinExchangePacket {

    private final int id;
    private final int entityId;

    public ExtendedOpenCoinExchangePacket(int id, int entityId) {
        this.id = id;
        this.entityId = entityId;
    }

    public static ExtendedOpenCoinExchangePacket decode(PacketBuffer buf) {
        return new ExtendedOpenCoinExchangePacket(buf.readUnsignedByte(), buf.readInt());
    }

    public static void encode(ExtendedOpenCoinExchangePacket msg, PacketBuffer buf) {
        buf.writeByte(msg.id);
        buf.writeInt(msg.entityId);
    }

    public int getId() {
        return this.id;
    }

    public int getEntityId() {
        return this.entityId;
    }

    public static void handle(ExtendedOpenCoinExchangePacket msg, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            openInventory(msg);
        });
        context.get().setPacketHandled(true);
    }

    public static void openInventory(ExtendedOpenCoinExchangePacket packet) {
        PlayerEntity player = Minecraft.getInstance().player;
        if (player != null && player.level != null) {
            Entity entity = player.level.getEntity(packet.getEntityId());

            if (entity == null) {
                return; // TODO: log error
            }

            NPCEntity npc = (NPCEntity) entity;
            ClientPlayerEntity clientplayerentity = Minecraft.getInstance().player;

            ExtendedCoinExchangeContainer container = new ExtendedCoinExchangeContainer(packet.getId(), player, npc);
            clientplayerentity.containerMenu = container;
            Minecraft.getInstance().setScreen(new ExtendedCoinExchangeScreen(container, player.inventory));
        }
    }
}
