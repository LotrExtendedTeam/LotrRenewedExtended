package lotr.common.init;

import lotr.common.entity.npc.ExtendedHobbitFarmerEntity;
import lotr.common.entity.npc.ExtendedHobbitBartenderEntity;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;

public class ExtendedNpcs {

    public static RegistryObject<EntityType<ExtendedHobbitBartenderEntity>> HOBBIT_BARTENDER;
    public static RegistryObject<EntityType<ExtendedHobbitFarmerEntity>> HOBBIT_FARMER;

}
