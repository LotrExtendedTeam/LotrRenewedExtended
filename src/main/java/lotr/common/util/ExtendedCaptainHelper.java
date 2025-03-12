package lotr.common.util;

import com.github.maximuslotro.lotrrextended.common.network.ExtendedCOpenCaptainPacket;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedPacketHandler;

import lotr.common.entity.npc.ExtendedCaptainEntity;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.item.LOTRSpawnEggItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.text.TranslationTextComponent;

public class ExtendedCaptainHelper {

    public static ActionResultType mobInteract(NPCEntity npc, ExtendedCaptainEntity captain, PlayerEntity player, Hand hand) {
        if (player.getItemInHand(hand).getItem() instanceof SpawnEggItem
                || player.getItemInHand(hand).getItem() instanceof LOTRSpawnEggItem) {
            return ActionResultType.SUCCESS;
        }
        return openCaptainGui(npc, player, captain);
    }

    public static ActionResultType openCaptainGui(NPCEntity entity, PlayerEntity player, ExtendedCaptainEntity captain) {
        if (captain.getPlayerInteractingWith().isPresent()) {
            if (!entity.level.isClientSide) {
                player.sendMessage(new TranslationTextComponent("gui.lotrextended.hiring.captain_busy"), Util.NIL_UUID);
            }
            return ActionResultType.sidedSuccess(entity.level.isClientSide());
        }

        captain.updatePlayerInteractingWith(player.getUUID());

        if (entity.level.isClientSide()) {
        	ExtendedPacketHandler.sendToServer(new ExtendedCOpenCaptainPacket(entity.getId()));
        }
        return ActionResultType.sidedSuccess(entity.level.isClientSide());
    }
}
