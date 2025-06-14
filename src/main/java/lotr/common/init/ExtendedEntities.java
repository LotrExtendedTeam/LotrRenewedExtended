package lotr.common.init;

import java.util.ArrayList;
import java.util.List;

import com.github.maximuslotro.lotrrextended.common.entity.passive.ExtendedDuckEntity;
import com.github.maximuslotro.lotrrextended.common.entity.projectile.ExtendedConkerEntity;
import com.github.maximuslotro.lotrrextended.common.entity.projectile.ExtendedDuckEggEntity;
import com.github.maximuslotro.lotrrextended.common.entity.special.ExtendedBannerEntity;
import com.github.maximuslotro.lotrrextended.common.entity.special.ExtendedChairEntity;
import com.github.maximuslotro.lotrrextended.common.entity.special.ExtendedStructureSpawnerEntity;
import com.github.maximuslotro.lotrrextended.common.entity.special.ExtendedTraderRespawnEntity;

import lotr.common.entity.npc.*;
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
	public static RegistryObject<EntityType<ExtendedGundabadOrcChieftain>> GUNDABAD_ORC_CHIEFTAIN;
	public static RegistryObject<EntityType<ExtendedBarrowWightEntity>> BARROW_WIGHT;
	public static RegistryObject<EntityType<ExtendedBannerEntity>> BANNER_HOLDER;
	public static RegistryObject<EntityType<ExtendedBreelandMerchantEntity>> BREE_MERCHANT;
	public static RegistryObject<EntityType<ExtendedChairEntity>> CHAIR;
	public static RegistryObject<EntityType<ExtendedHiredBreeGuard>> HIRED_BREE_GUARD;
	public static RegistryObject<EntityType<ExtendedHiredGundabadOrc>> HIRED_GUNDABAD_ORC;
	public static RegistryObject<EntityType<ExtendedBreelandSheriffEntity>> BREE_SHERIFF;
	public static RegistryObject<EntityType<ExtendedBreelandFarmerEntity>> BREE_FARMER;
	public static RegistryObject<EntityType<ExtendedBreelandPotterEntity>> BREE_POTTER;
	public static RegistryObject<EntityType<ExtendedBreelandBakerEntity>> BREE_BAKER;
	public static RegistryObject<EntityType<ExtendedBreelandButcherEntity>> BREE_BUTCHER;
	public static RegistryObject<EntityType<ExtendedBreelandLumbermanEntity>> BREE_LUMBERMAN;
	public static RegistryObject<EntityType<ExtendedBreelandStoneMasonEntity>> BREE_STONEMASON;
	public static RegistryObject<EntityType<ExtendedBreelandInnkeeperEntity>> BREE_INNKEEPER;
	public static RegistryObject<EntityType<ExtendedBreelandFloristEntity>> BREE_FLORIST;
	public static RegistryObject<EntityType<ExtendedBreelandCheesemongerEntity>> BREE_CHEESEMONGER;
	public static RegistryObject<EntityType<ExtendedBreelandLeatherworkerEntity>> BREE_LEATHERWORKER;
	public static RegistryObject<EntityType<ExtendedBreelandBrewerEntity>> BREE_BREWER;
	public static RegistryObject<EntityType<ExtendedBreelandStablemasterEntity>> BREE_STABLEMASTER;
	public static RegistryObject<EntityType<ExtendedBreelandHobbitBakerEntity>> BREE_HOBBIT_BAKER;
	public static RegistryObject<EntityType<ExtendedBreelandHobbitBrewerEntity>> BREE_HOBBIT_BREWER;
	public static RegistryObject<EntityType<ExtendedBreelandHobbitButcherEntity>> BREE_HOBBIT_BUTCHER;
	public static RegistryObject<EntityType<ExtendedBreelandHobbitFloristEntity>> BREE_HOBBIT_FLORIST;
	public static RegistryObject<EntityType<ExtendedBreelandHobbitInnkeeperEntity>> BREE_HOBBIT_INNKEEPER;
	public static RegistryObject<EntityType<ExtendedBreelandHobbitPotterEntity>> BREE_HOBBIT_POTTER;
	public static RegistryObject<EntityType<ExtendedHiredDunedainRangerEntity>> HIRED_DUNEDAIN_RANGER;
	public static RegistryObject<EntityType<ExtendedDunedainRangerCaptainEntity>> DUNEDAIN_RANGER_CAPTAIN;
	public static RegistryObject<EntityType<ExtendedBreelandSmithEntity>> BREE_SMITH;
	public static RegistryObject<EntityType<ExtendedTolkienEntity>> TOLKIEN;
	public static RegistryObject<EntityType<ExtendedBlueMountainsMinerEntity>> BLUE_DWARVEN_MINER;
	public static RegistryObject<EntityType<ExtendedDwarfMinerEntity>> DWARVEN_MINER;
	public static RegistryObject<EntityType<ExtendedBlueMountainsSmithEntity>> BLUE_DWARVEN_SMITH;
	public static RegistryObject<EntityType<ExtendedDwarfSmithEntity>> DWARVEN_SMITH;


}
