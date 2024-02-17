package lotr.common.entity.npc;

import lotr.common.network.ExtendedPacketHandler;
import lotr.common.network.ExtendedSPacketOpenCoinExchange;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.UUID;

import com.github.maximuslotro.lotrExtended.common.init.ExtendedSounds;

public class ExtendedTraderHelper {

    public static void playTraderSound(World level, BlockPos pos) {
        level.playSound(null, pos, ExtendedSounds.TRADE.get(), SoundCategory.MASTER, 1, 1);
    }

    public static ActionResultType openCoinExchange(NPCEntity entity, PlayerEntity player, HashSet<UUID> uuids) {
        ExtendedPacketHandler.sendToServer(new ExtendedSPacketOpenCoinExchange(entity.getId(), true));
        uuids.add(player.getUUID());
        return ActionResultType.sidedSuccess(entity.level.isClientSide());
    }

    public static ActionResultType openTraderGui(NPCEntity entity, PlayerEntity player, HashSet<UUID> uuids) {
        uuids.add(player.getUUID());
        player.displayClientMessage(new StringTextComponent("This is still in development, please be patient :)"), false);
        //if (entity.level.isClientSide()) {
        //    Minecraft.getInstance().setScreen(new ExtendedTraderMainScreen(entity, (ExtendedTraderEntity) entity, player));
        //}
        return ActionResultType.sidedSuccess(entity.level.isClientSide());
    }

        public static boolean isInteractingWithPlayer(HashSet<UUID> uuids) {
        return !uuids.isEmpty();
    }
}
