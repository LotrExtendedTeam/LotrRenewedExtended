package lotr.common.util.client;

import com.github.maximuslotro.lotrrextended.client.gui.screen.ExtendedBannerConfigurationScreen;
import com.github.maximuslotro.lotrrextended.client.gui.screen.inventory.ExtendedWargInventoryScreen;
import com.github.maximuslotro.lotrrextended.common.inventory.ExtendedWargInventoryContainer;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedSBannerOpenChangedScreenPacket;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedSBannerOpenScreenPacket;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedSOpenWargWindowPacket;

import lotr.client.gui.ExtendedCaptainMainScreen;
import lotr.client.gui.ExtendedTraderMainScreen;
import lotr.common.entity.npc.ExtendedCaptainEntity;
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

	public static void openCaptainGui(NPCEntity entity, PlayerEntity player) {
		Minecraft.getInstance().setScreen(new ExtendedCaptainMainScreen(entity, (ExtendedCaptainEntity) entity, player));
	}

	public static void handleWargScreenOpen(ExtendedSOpenWargWindowPacket pPacket) {
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

	public static void openBannerGUI(ExtendedSBannerOpenScreenPacket packet) {
		Minecraft.getInstance().setScreen(new ExtendedBannerConfigurationScreen(packet));
	}

	public static void handleBannerGUIUpdate(ExtendedSBannerOpenChangedScreenPacket packet) {
		Minecraft mc = Minecraft.getInstance();
		if(mc.screen instanceof ExtendedBannerConfigurationScreen && ((ExtendedBannerConfigurationScreen)mc.screen).getConfigurePos().equals(packet.configurePos)) {
			((ExtendedBannerConfigurationScreen)mc.screen).updatefromPacket(packet);
		}
	}
}
