package lotr.common.world.spawning;

import lotr.common.init.ExtendedEntities;
import net.minecraft.entity.EntityType;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class WanderingTradersList {

    public static Map<String, List<EntityType>> traders;

    private static final List<EntityType> ALL_WANDERERS = Arrays.asList(ExtendedEntities.RIVENDELL_WANDERER.get(), ExtendedEntities.ODDMENT_COLLECTOR.get(), ExtendedEntities.GALADHRIM_WANDERER.get());

    public static void initialize() {
        // Shire
        traders.put("lotr:shire", ALL_WANDERERS );
        traders.put("lotr:shire_moors", ALL_WANDERERS );
        traders.put("lotr:shire_marshes", ALL_WANDERERS);
        traders.put("lotr:shire_woodlands", ALL_WANDERERS);
        traders.put("lotr:white_downs", ALL_WANDERERS);

        // Eriador
    }


}
