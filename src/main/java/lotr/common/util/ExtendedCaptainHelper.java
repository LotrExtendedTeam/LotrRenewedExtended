package lotr.common.util;

import com.github.maximuslotro.lotrrextended.common.network.ExtendedCOpenCaptainPacket;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedPacketHandler;

import lotr.common.entity.npc.ExtendedCaptainEntity;
import lotr.common.entity.npc.NPCEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;

public class ExtendedCaptainHelper {

    public static ActionResultType openCaptainGui(NPCEntity entity, PlayerEntity player, ExtendedCaptainEntity captain) {
        if (entity.level.isClientSide()) {
        	ExtendedPacketHandler.sendToServer(new ExtendedCOpenCaptainPacket(entity.getId()));
        }
        return ActionResultType.sidedSuccess(entity.level.isClientSide());
    }
}
