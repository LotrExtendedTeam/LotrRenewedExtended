package lotr.common.entity.npc;

import lotr.common.init.ExtendedSoundEvents;
import lotr.common.network.ExtendedPacketHandler;
import lotr.common.network.ExtendedSPacketOpenCoinExchange;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.Random;
import java.util.UUID;

public class ExtendedTraderHelper {

    public static void playTraderSound(World level, BlockPos pos) {
        int number = new Random().nextInt(3) + 1;
        SoundEvent sound = null;
        switch (number) {
            case 1: sound = ExtendedSoundEvents.TRADE_SOUND_1;
                break;
            case 2: sound = ExtendedSoundEvents.TRADE_SOUND_2;
                break;
            case 3: sound = ExtendedSoundEvents.TRADE_SOUND_3;
                break;
            case 4: sound = ExtendedSoundEvents.TRADE_SOUND_4;
        }

        level.playSound(null, pos, sound, SoundCategory.MASTER, 1, 1);
    }

    public static ActionResultType openCoinExchange(NPCEntity entity, PlayerEntity player, HashSet<UUID> uuids) {
        ExtendedPacketHandler.sendToServer(new ExtendedSPacketOpenCoinExchange(entity.getId(), true));
        uuids.add(player.getUUID());
        return ActionResultType.sidedSuccess(entity.level.isClientSide());
    }

    public static ActionResultType openTraderGui(NPCEntity entity, PlayerEntity player, HashSet<UUID> uuids) {
        uuids.add(player.getUUID());

        //if (entity.level.isClientSide()) {
        //    Minecraft.getInstance().setScreen(new ExtendedTraderMainScreen(entity, (ExtendedTraderEntity) entity, player));
        //}
        return ActionResultType.sidedSuccess(entity.level.isClientSide());
    }

        public static boolean isInteractingWithPlayer(HashSet<UUID> uuids) {
        return !uuids.isEmpty();
    }
}
