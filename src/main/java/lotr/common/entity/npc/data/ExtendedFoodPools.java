package lotr.common.entity.npc.data;

import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRItems;
import lotr.common.item.VesselType;
import net.minecraft.item.Items;

public class ExtendedFoodPools {

    public static final NPCFoodPool RANGER;
    public static final NPCFoodPool RANGER_DRINK;

    private ExtendedFoodPools() {
    }

    static {
        RANGER = NPCFoodPool.of(Items.COOKED_PORKCHOP, Items.COOKED_CHICKEN, Items.COOKED_BEEF, Items.COOKED_COD, Items.COOKED_SALMON, Items.COOKED_MUTTON, Items.BAKED_POTATO, Items.APPLE, LOTRItems.GREEN_APPLE, LOTRItems.PEAR, ExtendedItems.BLUEBERRY, ExtendedItems.BLACKBERRY, ExtendedItems.CRANBERRY, ExtendedItems.RASPBERRY, ExtendedItems.ELDERBERRY, Items.BREAD);
        RANGER_DRINK = NPCFoodPool.of(LOTRItems.WATER_DRINK, LOTRItems.ALE, LOTRItems.ALE, LOTRItems.CIDER, LOTRItems.PERRY, LOTRItems.APPLE_JUICE).setDrinkVessels(VesselType.WOODEN_MUG, VesselType.CERAMIC_MUG, VesselType.WOODEN_CUP, VesselType.WATERSKIN);
    }

}
