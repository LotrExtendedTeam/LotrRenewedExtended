package lotr.common.util.client;

import com.github.maximuslotro.lotrrextended.client.gui.screen.inventory.ExtendedWargInventoryScreen;
import com.github.maximuslotro.lotrrextended.common.inventory.ExtendedWargInventoryContainer;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedSOpenWargWindowPacket;

import lotr.client.gui.ExtendedTraderMainScreen;
import lotr.common.entity.npc.ExtendedTraderEntity;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.entity.npc.WargEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;

public class ExtendedClientsideSeperatedScreenHandler {

	public static void openTraderGui(NPCEntity entity, PlayerEntity player) {
		Minecraft.getInstance().setScreen(new ExtendedTraderMainScreen(entity, (ExtendedTraderEntity) entity, player));
	}

	public static void handle(ExtendedSOpenWargWindowPacket pPacket) {
		Minecraft mc = Minecraft.getInstance();
		ClientPlayerEntity player = mc.player;
		Entity entity = mc.level.getEntity(pPacket.getEntityId());
		if (entity instanceof WargEntity) {
			ClientPlayerEntity clientplayerentity = mc.player;
			WargEntity abstracthorseentity = (WargEntity)entity;
			Inventory inventory = new Inventory(pPacket.getSize());
			ExtendedWargInventoryContainer warginventorycontainer = new ExtendedWargInventoryContainer(pPacket.getContainerId(), clientplayerentity.inventory, inventory, abstracthorseentity);
			clientplayerentity.containerMenu = warginventorycontainer;
			mc.setScreen(new ExtendedWargInventoryScreen(warginventorycontainer, clientplayerentity.inventory, abstracthorseentity));
		}
	}
}
