package lotr.common.init;

import lotr.common.entity.npc.*;

import java.util.ArrayList;
import java.util.List;

import com.github.maximuslotro.lotrrextended.common.entity.passive.ExtendedDuckEntity;
import com.github.maximuslotro.lotrrextended.common.entity.projectile.ExtendedConkerEntity;
import com.github.maximuslotro.lotrrextended.common.entity.projectile.ExtendedDuckEggEntity;
import com.github.maximuslotro.lotrrextended.common.entity.special.ExtendedBannerEntity;
import com.github.maximuslotro.lotrrextended.common.entity.special.ExtendedStructureSpawnerEntity;
import com.github.maximuslotro.lotrrextended.common.entity.special.ExtendedTraderRespawnEntity;

import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;

public class ExtendedEntities {

	public static List<String> REGISTEREDNPCENTITIES = new ArrayList<>();
	public static List<String> REGISTEREDANIMALENTITIES = new ArrayList<>();
	public static List<String> REGISTEREDNONMOBENTITIES = new ArrayList<>();
	public static RegistryObject<EntityType<ExtendedDunedainRangerEntity>> DUNEDAIN_RANGER;
	public static RegistryObject<EntityType<ExtendedGaladhrimWandererEntity>> GALADHRIM_WANDERER;
	public static RegistryObject<EntityType<ExtendedHobbitBartenderEntity>> HOBBIT_BARTENDER;
	public static RegistryObject<EntityType<ExtendedHobbitFarmerEntity>> HOBBIT_FARMER;
	public static RegistryObject<EntityType<ExtendedOddmentCollectorEntity>> ODDMENT_COLLECTOR;
	public static RegistryObject<EntityType<ExtendedConkerEntity>> THROWN_CONKER;
	public static RegistryObject<EntityType<ExtendedRivendellWandererEntity>> RIVENDELL_WANDERER;
	public static RegistryObject<EntityType<ExtendedDuckEntity>> DUCK;
	public static RegistryObject<EntityType<ExtendedDuckEggEntity>> DUCK_EGG_ENTITY;
	public static RegistryObject<EntityType<ExtendedStructureSpawnerEntity>> STRUCTURE_ENTITY_SPAWNER;
	public static RegistryObject<EntityType<ExtendedTraderRespawnEntity>> TRADER_RESPAWNER_ENTITY;
	public static RegistryObject<EntityType<ExtendedGundabadOrcTraderEntity>> GUNDABAD_CAMP_TRADER;
	public static RegistryObject<EntityType<ExtendedBlueMountainsMerchantEntity>> BLUE_MOUNTAINS_MERCHANT;
	public static RegistryObject<EntityType<ExtendedGundabadUrukEntity>> GUNDABAD_URUK;
	public static RegistryObject<EntityType<ExtendedGundabadUrukArcherEntity>> GUNDABAD_URUK_ARCHER;
	public static RegistryObject<EntityType<ExtendedBarrowWrightEntity>> BARROW_WIGHT;
	public static RegistryObject<EntityType<ExtendedBannerEntity>> BANNER_HOLDER;
	public static RegistryObject<EntityType<ExtendedBreelandMerchantEntity>> BREE_MERCHANT;

	public static RegistryObject<EntityType<ExtendedHiredBreeGuard>> HIRED_BREE_GUARD;
}
