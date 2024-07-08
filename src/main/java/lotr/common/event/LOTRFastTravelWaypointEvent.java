package lotr.common.event;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.event.lifecycle.IModBusEvent;
public class LOTRFastTravelWaypointEvent extends Event implements IModBusEvent{

    private ServerPlayerEntity player;
    private ServerWorld world;
    private BlockPos travelPos;

    private BlockPos originalPos;

    public LOTRFastTravelWaypointEvent(ServerPlayerEntity player, ServerWorld world, BlockPos originalPos, BlockPos travelPos) {
        this.player = player;
        this.world = world;
        this.originalPos = originalPos;
        this.travelPos = travelPos;
    }

    public ServerPlayerEntity getPlayer() {
        return player;
    }

    public ServerWorld getWorld() {
        return world;
    }

    public BlockPos getTravelPos() {
        return travelPos;
    }

    public BlockPos getOriginalPos() {
        return originalPos;
    }
}