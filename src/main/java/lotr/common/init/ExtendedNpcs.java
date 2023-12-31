package lotr.common.init;

import lotr.common.entity.npc.ExtendedHobbitFarmerEntity;
import lotr.common.entity.npc.HobbitBartenderEntity;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;

public class ExtendedNpcs {

    public static RegistryObject<EntityType<HobbitBartenderEntity>> HOBBIT_BARTENDER;
    public static RegistryObject<EntityType<ExtendedHobbitFarmerEntity>> HOBBIT_FARMER;

}
