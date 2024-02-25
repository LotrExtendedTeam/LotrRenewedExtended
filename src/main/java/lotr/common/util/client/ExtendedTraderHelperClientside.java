package lotr.common.util.client;

import lotr.client.gui.ExtendedTraderMainScreen;
import lotr.common.entity.npc.ExtendedTraderEntity;
import lotr.common.entity.npc.NPCEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;

public class ExtendedTraderHelperClientside {

    private ExtendedTraderHelperClientside() {}

    public static void openTraderGui(NPCEntity entity, PlayerEntity player) {
        Minecraft.getInstance().setScreen(new ExtendedTraderMainScreen(entity, (ExtendedTraderEntity) entity, player));
    }
}
