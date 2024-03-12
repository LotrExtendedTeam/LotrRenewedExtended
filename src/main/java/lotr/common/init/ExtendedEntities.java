package lotr.common.init;

import lotr.common.entity.npc.ExtendedDunedainRangerEntity;
import lotr.common.entity.npc.ExtendedHobbitFarmerEntity;

import java.util.ArrayList;
import java.util.List;

import lotr.common.entity.npc.ExtendedHobbitBartenderEntity;
import lotr.common.entity.npc.ExtendedOddmentCollectorEntity;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;

public class ExtendedEntities {

	public static List<String> REGISTEREDENTITIES = new ArrayList<>();
	public static  RegistryObject<EntityType<ExtendedDunedainRangerEntity>> DUNEDAIN_RANGER;
	public static RegistryObject<EntityType<ExtendedHobbitBartenderEntity>> HOBBIT_BARTENDER;
	public static RegistryObject<EntityType<ExtendedHobbitFarmerEntity>> HOBBIT_FARMER;
	public static  RegistryObject<EntityType<ExtendedOddmentCollectorEntity>> ODDMENT_COLLECTOR;
}
