package lotr.common.init;

import lotr.common.entity.npc.ExtendedDunedainRangerEntity;
import lotr.common.entity.npc.ExtendedHobbitBartenderEntity;
import lotr.common.entity.npc.ExtendedHobbitFarmerEntity;
import lotr.common.entity.npc.ExtendedRivendellWandererEntity;

import java.util.ArrayList;
import java.util.List;

import lotr.common.entity.npc.ExtendedOddmentCollectorEntity;
import lotr.common.entity.projectile.ExtendedConkerEntity;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;

public class ExtendedEntities {

	public static List<String> REGISTEREDMOBENTITIES = new ArrayList<>();
	public static List<String> REGISTEREDNONMOBENTITIES = new ArrayList<>();
	public static List<String> REGISTEREDENTITIES = new ArrayList<>();
	public static  RegistryObject<EntityType<ExtendedDunedainRangerEntity>> DUNEDAIN_RANGER;
	public static RegistryObject<EntityType<ExtendedHobbitBartenderEntity>> HOBBIT_BARTENDER;
	public static RegistryObject<EntityType<ExtendedHobbitFarmerEntity>> HOBBIT_FARMER;
	public static RegistryObject<EntityType<ExtendedOddmentCollectorEntity>> ODDMENT_COLLECTOR;
	public static RegistryObject<EntityType<ExtendedConkerEntity>> THROWN_CONKER;
	public static RegistryObject<EntityType<ExtendedRivendellWandererEntity>> RIVENDELL_WANDERER;

}
