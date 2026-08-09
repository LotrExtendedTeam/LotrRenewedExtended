package com.github.maximuslotro.lotrextended.mixins.lotr.common.init;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.github.maximuslotro.lotrrextended.common.entity.passive.ExtendedCrabanEntity;
import com.github.maximuslotro.lotrrextended.common.entity.special.*;
import lotr.common.entity.npc.*;

import net.minecraft.entity.*;
import net.minecraft.world.gen.Heightmap;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.maximuslotro.lotrrextended.common.entity.item.ExtendedOrcBombEntity;
import com.github.maximuslotro.lotrrextended.common.entity.item.ExtendedTapestryEntity;
import com.github.maximuslotro.lotrrextended.common.entity.passive.ExtendedDuckEntity;
import com.github.maximuslotro.lotrrextended.common.entity.passive.ExtendedHedgehogEntity;
import com.github.maximuslotro.lotrrextended.common.entity.passive.ExtendedHorseBoarEntity;
import com.github.maximuslotro.lotrrextended.common.entity.projectile.ExtendedConkerEntity;
import com.github.maximuslotro.lotrrextended.common.entity.projectile.ExtendedDuckEggEntity;
import com.github.maximuslotro.lotrrextended.common.entity.projectile.ExtendedThrowableAxeEntity;

import lotr.common.init.ExtendedEntities;
import lotr.common.init.LOTREntities;
import lotr.common.init.LOTREntities.EntitySizeHolder;
import lotr.common.init.LOTREntities.SpawnEggInfo;
import net.minecraft.entity.EntitySpawnPlacementRegistry.IPlacementPredicate;
import net.minecraft.entity.EntitySpawnPlacementRegistry.PlacementType;
import net.minecraft.entity.EntityType.Builder;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.AttributeModifierMap.MutableAttribute;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.world.gen.Heightmap.Type;
import net.minecraftforge.fml.DatagenModLoader;
import net.minecraftforge.fml.RegistryObject;

/**
 * Used to register new entities added by Extended
 * @Mixin (LOTRBiomes.class)
 * */
@Mixin(LOTREntities.class)
public class MixinLOTREntities {

	/**
	 * Get the Entity Attribute Factory for Renewed/Extended entities if needed, currently unused
	 * @Shadow (remap=false)
	 * @Final Mark the field to be essentially final
	 * */
	@Final
	@Shadow(remap = false)
	private static Map<EntityType<? extends LivingEntity>, Supplier<MutableAttribute>> ENTITY_ATTRIBUTE_FACTORIES;

	@Shadow(remap=false)
	private static <T extends MobEntity> RegistryObject<EntityType<T>> regLiving(String key, EntityType.IFactory<T> factory, Supplier<AttributeModifierMap.MutableAttribute> attribFactory, EntityClassification classif, EntitySizeHolder size, int trackRange, int updateFreq, boolean velUpdates, SpawnEggInfo.SpawnEggColors eggColors, EntitySpawnPlacementRegistry.PlacementType placementType, Heightmap.Type heightmapType, EntitySpawnPlacementRegistry.IPlacementPredicate<T> placementPredicate) {
		return null;
	}

	private static <T extends CreatureEntity> RegistryObject<EntityType<T>> regCreatureD(String key, EntityType.IFactory<T> factory, Supplier<AttributeModifierMap.MutableAttribute> attribFactory, EntitySizeHolder size, int trackRange, int updateFreq, SpawnEggInfo.SpawnEggColors eggColors, EntitySpawnPlacementRegistry.PlacementType placementType, Heightmap.Type heightmapType) {
		RegistryObject<EntityType<T>> entity = regLiving(key, factory, attribFactory, EntityClassification.CREATURE, size, trackRange, updateFreq, true, eggColors, placementType, heightmapType, CreatureEntity::checkMobSpawnRules);
		if(DatagenModLoader.isRunningDataGen()) {
			ExtendedEntities.REGISTEREDANIMALENTITIES.add(key);
		}
		return entity;
	}

	/**
	 * Get the base NPC register method from parent class
	 * @Shadow (remap=false)
	 * @param <T> the type of module extending NPCEntity
	 * @param key A String name of the entity
	 * @param factory A builder function for the entitie's EntityType
	 * @param size A factory for defining the size of the entity on registration
	 * @param attribFactory An AttributeModifierMap for the registered entity
	 * @param eggColors A SpawnEggColors instance for auto registering the entity's spawn egg
	 * @return A RegistryObject instance for the supplied EntityType
	 * */
	@Shadow(remap=false)
	private static <T extends NPCEntity> RegistryObject<EntityType<T>> regNPC(String key, EntityType.IFactory<T> factory, Supplier<AttributeModifierMap.MutableAttribute> attribFactory, LOTREntities.EntitySizeHolder size, LOTREntities.SpawnEggInfo.SpawnEggColors eggColors) {
		return null;
	}
	/**
	 * Exteded's base NPC registering method, used for handling dataGen stuff
	 * @param <T> the type of module extending NPCEntity
	 * @param key A String name of the entity
	 * @param factory A builder function for the entity EntityType
	 * @param attribFactory An AttributeModifierMap for the registered entity
	 * @param size A factory for defining the size of the entity on registration
	 * @param eggColors A SpawnEggColors instance for auto registering the entity's spawn egg
	 * @return A RegistryObject instance for the supplied EntityType
	 * */
	@Unique
	private static <T extends NPCEntity> RegistryObject<EntityType<T>> regNPCD(String key, EntityType.IFactory<T> factory, Supplier<AttributeModifierMap.MutableAttribute> attribFactory, LOTREntities.EntitySizeHolder size, LOTREntities.SpawnEggInfo.SpawnEggColors eggColors) {
		RegistryObject<EntityType<T>> entity = regNPC(key, factory, attribFactory, size, eggColors);
		if(DatagenModLoader.isRunningDataGen()) {
			ExtendedEntities.REGISTEREDNPCENTITIES.add(key);
		}
		return entity;
	}
	/**
	 * Get the base animal register method from parent class
	 * @Shadow (remap=false)
	 * @param <T> the type of module extending AnimalEntity
	 * @param key A String name of the entity
	 * @param factory A builder function for the entitie's EntityType
	 * @param attribFactory An AttributeModifierMap for the registered entity
	 * @param size The EntitySizeHolder for setting the entity's size
	 * @param trackRange Value to set the entitie's tracking range to
	 * @param updateFreq Value to set the entitie's update frequency to
	 * @param eggColors A SpawnEggColors instance for auto registering the entity's spawn egg
	 * @param placementType Set the PlacementTypenatural spawn position for the entity (PlacementType)
	 * @param heightmapType Set the heightmap spawn settings for the entity (Type)
	 * @param placementPredicate Adds additional IPlacementPredicate spawn rules for the entity
	 * @return A RegistryObject instance for the supplied EntityType
	 * */
	@Shadow(remap=false)
	private static <T extends AnimalEntity> RegistryObject<EntityType<T>> regAnimal(String key, EntityType.IFactory<T> factory,Supplier<MutableAttribute> attribFactory,
									LOTREntities.EntitySizeHolder size, int trackRange, int updateFreq, LOTREntities.SpawnEggInfo.SpawnEggColors eggColors,
									PlacementType placementType, Type heightmapType, IPlacementPredicate<T> placementPredicate) {
		return null;
	}
	/**
	 * Extended's animal register method, with default animal spawn rules, and dataGen handling
	 * @param <T> the type of module extending AnimalEntity
	 * @param key A String name of the entity
	 * @param factory A builder function for the entity EntityType
	 * @param attribFactory An AttributeModifierMap for the registered entity
	 * @param size The EntitySizeHolder for setting the entity's size
	 * @param trackRange Value to set the entitie's tracking range to
	 * @param updateFreq Value to set the entitie's update frequency to
	 * @param eggColors A SpawnEggColors instance for auto registering the entity's spawn egg
	 * @return A RegistryObject instance for the supplied EntityType
	 * */
	@Unique
	private static <T extends AnimalEntity> RegistryObject<EntityType<T>> regAnimalD(String key, EntityType.IFactory<T> factory, Supplier<MutableAttribute> attribFactory,
																					 LOTREntities.EntitySizeHolder size, int trackRange, int updateFreq, LOTREntities.SpawnEggInfo.SpawnEggColors eggColors) {
		RegistryObject<EntityType<T>> entity = regAnimal(key, factory, attribFactory, size, trackRange, updateFreq, eggColors, PlacementType.ON_GROUND,
										Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::checkAnimalSpawnRules);
		if(DatagenModLoader.isRunningDataGen()) {
			ExtendedEntities.REGISTEREDANIMALENTITIES.add(key);
		}
		return entity;
	}
	/**
	 * Extended's animal register method, with default animal spawn rules, and dataGen handling
	 * @param <T> the type of module extending AnimalEntity
	 * @param key A String name of the entity
	 * @param factory A builder function for the entity EntityType
	 * @param attribFactory An AttributeModifierMap for the registered entity
	 * @param size The EntitySizeHolder for setting the entity's size
	 * @param trackRange Value to set the entitie's tracking range to
	 * @param updateFreq Value to set the entitie's update frequency to
	 * @param eggColors A SpawnEggColors instance for auto registering the entity's spawn egg
	 * @param placementPredicate Adds additional IPlacementPredicate spawn rules for the entity
	 * @return A RegistryObject instance for the supplied EntityType
	 * */
	@Unique
	private static <T extends AnimalEntity> RegistryObject<EntityType<T>> regAnimalD(String key, EntityType.IFactory<T> factory, Supplier<MutableAttribute> attribFactory,
																					 LOTREntities.EntitySizeHolder size, int trackRange, int updateFreq, LOTREntities.SpawnEggInfo.SpawnEggColors eggColors,
																					 IPlacementPredicate<T> placementPredicate) {
		RegistryObject<EntityType<T>> entity = regAnimal(key, factory, attribFactory, size, trackRange, updateFreq, eggColors, PlacementType.ON_GROUND,
										Type.MOTION_BLOCKING_NO_LEAVES, placementPredicate);
		if(DatagenModLoader.isRunningDataGen()) {
			ExtendedEntities.REGISTEREDANIMALENTITIES.add(key);
		}
		return entity;
	}

	/**
	 * Get the base entity register method from parent class
	 * @Shadow (remap=false)
	 * @param <T> the type of module extending Entity
	 * @param key A String name of the entity
	 * @param factory A builder function for the entitie's EntityType
	 * @param classification An AttributeModifierMap for the registered entity
	 * @param width The entity's hitbox's width
	 * @param height The entity's hitbox's height
	 * @param trackRange Value to set the entitie's tracking range to
	 * @param updateFreq Value to set the entitie's update frequency to
	 * @return A RegistryObject instance for the supplied EntityType
	 * */
	@Shadow(remap=false)
	private static <T extends Entity> RegistryObject<EntityType<T>> regEntity(String key, EntityType.IFactory<T> factory, EntityClassification classification, float width, float height, int trackRange, int updateFreq) {
		return null;
	}
	/**
	 * Get the base entity register method from parent class
	 * @Shadow (remap=false)
	 * @param <T> the type of module extending Entity
	 * @param key A String name of the entity
	 * @param factory A builder function for the entitie's EntityType
	 * @param classification An AttributeModifierMap for the registered entity
	 * @param width The entity's hitbox's width
	 * @param height The entity's hitbox's height
	 * @param trackRange Value to set the entitie's tracking range to
	 * @param updateFreq Value to set the entitie's update frequency to
	 * @param extraProps Extra EntityType properties builder
	 * @return A RegistryObject instance for the supplied EntityType
	 * */
	@Shadow(remap=false)
	private static <T extends Entity> RegistryObject<EntityType<T>> regEntity(String key, EntityType.IFactory<T> factory, EntityClassification classification, float width, float height, int trackRange, int updateFreq, Consumer<Builder<T>> extraProps) {
		return null;
	}
	/**
	 * Get the base entity register method from parent class
	 * @Shadow (remap=false)
	 * @param <T> the type of module extending Entity
	 * @param key A String name of the entity
	 * @param factory A builder function for the entitie's EntityType
	 * @param classification An AttributeModifierMap for the registered entity
	 * @param width The entity's hitbox's width
	 * @param height The entity's hitbox's height
	 * @param extraProps Extra EntityType properties builder
	 * @param builtTypeConsumer An EntityType builder consumer
	 * @return A RegistryObject instance for the supplied EntityType
	 * */
	@Shadow(remap = false)
	private static <T extends Entity> RegistryObject<EntityType<T>> regEntity(String key, EntityType.IFactory<T> factory, EntityClassification classification, float width, float height, Consumer<EntityType.Builder<T>> extraProps, Consumer<EntityType<T>> builtTypeConsumer) {
		return null;
	}
	/**
	 * Extended's base entity register method, handling dataGen, used for throwables currently
	 * @param key A String name of the entity
	 * @param <T> the type of module extending Entity
	 * @param factory A builder function for the entity EntityType
	 * @param classification An AttributeModifierMap for the registered entity
	 * @param width The entity's hitbox's width
	 * @param height The entity's hitbox's height
	 * @param trackRange Value to set the entitie's tracking range to
	 * @param updateFreq Value to set the entitie's update frequency to
	 * @return A RegistryObject instance for the supplied EntityType
	 * */
	@Unique
	private static <T extends Entity> RegistryObject<EntityType<T>> regEntityB(String key, EntityType.IFactory<T> factory, EntityClassification classification, float width, float height, int trackRange, int updateFreq) {
		RegistryObject<EntityType<T>> entity = regEntity(key, factory, classification, width, height, trackRange, updateFreq);
		if(DatagenModLoader.isRunningDataGen()) {
			ExtendedEntities.REGISTEREDNONMOBENTITIES.add(key);
		}
		return entity;
	}
	/**
	 * Extended's base register method, handling dataGen, and allowing for custom entity properties
	 * @param <T> the type of module extending Entity
	 * @param key A String name of the entity
	 * @param factory A builder function for the entity EntityType
	 * @param classification An AttributeModifierMap for the registered entity
	 * @param width The entity's hitbox's width
	 * @param height The entity's hitbox's height
	 * @param trackRange Value to set the entitie's tracking range to
	 * @param updateFreq Value to set the entitie's update frequency to
	 * @param extraProps Extra EntityType properties builder
	 * @return A RegistryObject instance for the supplied EntityType
	 * */
	@Unique
	private static <T extends Entity> RegistryObject<EntityType<T>> regEntityB(String key, EntityType.IFactory<T> factory, EntityClassification classification, float width, float height, int trackRange, int updateFreq, Consumer<Builder<T>> extraProps) {
		RegistryObject<EntityType<T>> entity = regEntity(key, factory, classification, width, height, trackRange, updateFreq, extraProps);
		if(DatagenModLoader.isRunningDataGen()) {
			ExtendedEntities.REGISTEREDNONMOBENTITIES.add(key);
		}
		return entity;
	}
	/**
	 * @reason Register Extended's entities after Renewed's static registering is done, but before the entities differed register is registered
	 * @Inject (method = "register()V", at = @At("HEAD"), remap=false)
	 * @param ci The local CallbackInfo instance for this Inject
	 * */
	@Inject(method = "register()V", at = @At("HEAD"), remap=false)
	private static void bootstrapExtendedItems(CallbackInfo ci) {
		// Animals
		ExtendedEntities.HORSE_BOAR = regAnimalD("wild_boar", ExtendedHorseBoarEntity::new, ExtendedHorseBoarEntity::createWildBoarAttributes, LOTREntities.EntitySizeHolder.size(0.9F, 0.9F), 10, 3, LOTREntities.SpawnEggInfo.SpawnEggColors.egg(6635562, 4069378));
		ExtendedEntities.DUCK = regAnimalD("duck", ExtendedDuckEntity::new, ExtendedDuckEntity::createAttributes, LOTREntities.EntitySizeHolder.size(0.4F, 0.7F), 10, 3, LOTREntities.SpawnEggInfo.SpawnEggColors.egg(940841, 11374685), ExtendedDuckEntity::checkDuckSpawnRules);
		ExtendedEntities.HEDGEHOG = regAnimalD("hedgehog", ExtendedHedgehogEntity::new, ExtendedHedgehogEntity::createHedgehogAttributes, LOTREntities.EntitySizeHolder.size(0.4F, 0.4F), 10, 3, LOTREntities.SpawnEggInfo.SpawnEggColors.egg(2167050, 14010033));
		ExtendedEntities.CRABAN = regCreatureD("craban", ExtendedCrabanEntity::new, ExtendedCrabanEntity::createCrabanAttributes, LOTREntities.EntitySizeHolder.size(0.4F, 0.4F), 10, 3, LOTREntities.SpawnEggInfo.SpawnEggColors.egg(2434341, 10490368), PlacementType.ON_GROUND, Type.MOTION_BLOCKING_NO_LEAVES);
		// Hostile
		ExtendedEntities.BARROW_WIGHT = regNPCD("barrow_wight", ExtendedBarrowWightEntity::new, ExtendedBarrowWightEntity::createAttributes, LOTREntities.EntitySizeHolder.size(0.8F, 1.9f), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(529926, 3111505));
		// Projectiles
		ExtendedEntities.THROWN_CONKER = regEntityB("thrown_conker", ExtendedConkerEntity::new, EntityClassification.MISC, 0.25F, 0.25F, 4, 10);
		ExtendedEntities.THROWN_AXE = regEntityB("thrown_axe", ExtendedThrowableAxeEntity::new, EntityClassification.MISC, 0.5F, 0.5F, 4, 20);
		ExtendedEntities.DUCK_EGG_ENTITY = regEntityB("duck_egg", ExtendedDuckEggEntity::new, EntityClassification.MISC, 0.25F, 0.25F, 4, 10);
		// Technical
		ExtendedEntities.STRUCTURE_ENTITY_SPAWNER = regEntityB("structure_spawner", ExtendedStructureSpawnerEntity::new, EntityClassification.MISC, 0.5F, 1.0F, 4, 3);
		ExtendedEntities.RESPAWNER_ENTITY = regEntityB("trader_respawner", ExtendedRespawnerEntity::new, EntityClassification.MISC, 0.5F, 0.5F, 4, 3);
		ExtendedEntities.INVASION_SPAWNER = regEntityB("invasion_spawner", ExtendedInvasionSpawnerEntity::new, EntityClassification.MISC, 1.0F, 2.0F, 4, 20);
		ExtendedEntities.BANNER_HOLDER = regEntityB("banner_dataholder", ExtendedBannerEntity::new, EntityClassification.MISC, 0.125F, 1.0F, 4, 10);
		ExtendedEntities.CHAIR = regEntityB("chair", ExtendedChairEntity::new, EntityClassification.MISC, 0.0001F, 0.0001F, 32, 2, (builder) -> {
			builder.canSpawnFarFromPlayer();
			builder.fireImmune();
			builder.noSummon();
			//builder.noSave();
		});
		ExtendedEntities.TAPESTRY = regEntityB("tapestry", ExtendedTapestryEntity::new, EntityClassification.MISC, 0.5F, 0.5F, 10, Integer.MAX_VALUE);
		ExtendedEntities.ORC_BOMB = regEntityB("orc_bomb", ExtendedOrcBombEntity::new, EntityClassification.MISC, 0.98F, 0.98F, 10, 10, (builder) -> {
			builder.fireImmune();
		});
		// Wandering
		ExtendedEntities.GALADHRIM_WANDERER = regNPCD("galadhrim_wanderer", ExtendedGaladhrimWandererEntity::new, ExtendedGaladhrimWandererEntity::createAttributes, LOTREntities.EntitySizeHolder.elfSize(), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(2047778, 16762150));
		ExtendedEntities.ODDMENT_COLLECTOR = regNPCD("oddment_collector", ExtendedOddmentCollectorEntity::new, ExtendedOddmentCollectorEntity::createAttributes, LOTREntities.EntitySizeHolder.manSize(), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(16225652, 5323553));
		ExtendedEntities.RIVENDELL_WANDERER = regNPCD("rivendell_wanderer", ExtendedRivendellWandererEntity::new, ExtendedRivendellWandererEntity::createAttributes, LOTREntities.EntitySizeHolder.elfSize(), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(869480, 15003391));
		// Rangers of the North
		ExtendedEntities.DUNEDAIN_RANGER = regNPCD("dunedain_ranger", ExtendedDunedainRangerEntity::new, ExtendedDunedainRangerEntity::createAttributes, LOTREntities.EntitySizeHolder.size(0.6F, 1.93f), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(4473912, 2302748));
		ExtendedEntities.DUNEDAIN_RANGER_CAPTAIN = regNPCD("dunedain_ranger_captain", ExtendedDunedainRangerCaptainEntity::new, ExtendedDunedainRangerCaptainEntity::createAttributes, LOTREntities.EntitySizeHolder.size(0.6F, 1.93f), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(4473912, 2302748));
		// Gundabad
		ExtendedEntities.GUNDABAD_CAMP_TRADER = regNPCD("gundabad_orc_scrounger", ExtendedGundabadOrcTraderEntity::new, ExtendedGundabadOrcTraderEntity::regAttrs, LOTREntities.EntitySizeHolder.orcSize(), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(5979436, 13421772));
		ExtendedEntities.GUNDABAD_URUK = regNPCD("gundabad_uruk", ExtendedGundabadUrukEntity::new, ExtendedGundabadUrukEntity::createAttributes, LOTREntities.EntitySizeHolder.urukSize(), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(2563350, 6382678));
		ExtendedEntities.GUNDABAD_URUK_ARCHER = regNPCD("gundabad_uruk_archer", ExtendedGundabadUrukArcherEntity::new, ExtendedGundabadUrukArcherEntity::createAttributes, LOTREntities.EntitySizeHolder.urukSize(), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(2563350, 6382678));
		ExtendedEntities.GUNDABAD_ORC_CHIEFTAIN = regNPCD("gundabad_orc_chieftain", ExtendedGundabadOrcChieftain::new, ExtendedGundabadOrcChieftain::createAttributes, LOTREntities.EntitySizeHolder.urukSize(), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(2563350, 6382678));
		// Hobbit
		ExtendedEntities.HOBBIT_BARTENDER = regNPCD("hobbit_bartender", ExtendedHobbitBartenderEntity::new, ExtendedHobbitBartenderEntity::createAttributes, LOTREntities.EntitySizeHolder.hobbitSize(), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(16752511, 8010275));
		ExtendedEntities.HOBBIT_FARMER = regNPCD("hobbit_farmer", ExtendedHobbitFarmerEntity::new, ExtendedHobbitFarmerEntity::createAttributes, LOTREntities.EntitySizeHolder.hobbitSize(), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(16752511, 8010275));
		ExtendedEntities.HOBBIT_FARMHAND = regNPCD("hobbit_farmhand", ExtendedHobbitFarmhandEntity::new, ExtendedHobbitFarmhandEntity::createAttributes, LOTREntities.EntitySizeHolder.hobbitSize(), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(16752511, 8010275));
		// Bree
		ExtendedEntities.BREE_SHERIFF = regNPCD("bree_sheriff", ExtendedBreelandSheriffEntity::new, ExtendedBreelandSheriffEntity::createAttributes, LOTREntities.EntitySizeHolder.manSize(), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(9335640, 3681573));
		ExtendedEntities.BREE_MERCHANT = regNPCD("bree_merchant", ExtendedBreelandMerchantEntity::new, ExtendedBreelandMerchantEntity::createAttributes, LOTREntities.EntitySizeHolder.manSize(), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(14254950, 6573346));
		ExtendedEntities.BREE_FARMER = regNPCD("bree_farmer", ExtendedBreelandFarmerEntity::new, ExtendedBreelandTraderEntity::createAttributes, LOTREntities.EntitySizeHolder.manSize(), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(14254950, 5193273));
		ExtendedEntities.BREE_FARMHAND = regNPCD("bree_farmhand", ExtendedBreelandFarmhandEntity::new, ExtendedBreelandFarmhandEntity::createAttributes, LOTREntities.EntitySizeHolder.manSize(), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(14254950, 6573367));
		ExtendedEntities.BREE_POTTER = regNPCD("bree_potter", ExtendedBreelandPotterEntity::new, ExtendedBreelandTraderEntity::createAttributes, LOTREntities.EntitySizeHolder.manSize(), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(14254950, 9218761));
		ExtendedEntities.BREE_HOBBIT_POTTER = regNPCD("bree_hobbit_potter", ExtendedBreelandHobbitPotterEntity::new, ExtendedBreelandHobbitTraderEntity::createAttributes, LOTREntities.EntitySizeHolder.hobbitSize(), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(14254950, 9218761));
		ExtendedEntities.BREE_SMITH = regNPCD("bree_smith", ExtendedBreelandSmithEntity::new, ExtendedBreelandSmithEntity::createAttributes, LOTREntities.EntitySizeHolder.manSize(), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(14254950, 2293760));
		ExtendedEntities.BREE_BAKER = regNPCD("bree_baker", ExtendedBreelandBakerEntity::new, ExtendedBreelandTraderEntity::createAttributes, LOTREntities.EntitySizeHolder.manSize(), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(14254950, 8010275));
		ExtendedEntities.BREE_HOBBIT_BAKER = regNPCD("bree_hobbit_baker", ExtendedBreelandHobbitBakerEntity::new, ExtendedBreelandHobbitTraderEntity::createAttributes, LOTREntities.EntitySizeHolder.hobbitSize(), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(14254950, 8010275));
		ExtendedEntities.BREE_BUTCHER = regNPCD("bree_butcher", ExtendedBreelandButcherEntity::new, ExtendedBreelandTraderEntity::createAttributes, LOTREntities.EntitySizeHolder.manSize(), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(14254950, 8199168));
		ExtendedEntities.BREE_HOBBIT_BUTCHER = regNPCD("bree_hobbit_butcher", ExtendedBreelandHobbitButcherEntity::new, ExtendedBreelandHobbitTraderEntity::createAttributes, LOTREntities.EntitySizeHolder.hobbitSize(), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(14254950, 8199168));
		ExtendedEntities.BREE_LUMBERMAN = regNPCD("bree_lumberman", ExtendedBreelandLumbermanEntity::new, ExtendedBreelandTraderEntity::createAttributes, LOTREntities.EntitySizeHolder.manSize(), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(14254950, 6043692));
		ExtendedEntities.BREE_STONEMASON = regNPCD("bree_stonemason", ExtendedBreelandStoneMasonEntity::new, ExtendedBreelandTraderEntity::createAttributes, LOTREntities.EntitySizeHolder.manSize(), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(14254950, 6777187));
		ExtendedEntities.BREE_INNKEEPER = regNPCD("bree_innkeeper", ExtendedBreelandInnkeeperEntity::new, ExtendedBreelandTraderEntity::createAttributes, LOTREntities.EntitySizeHolder.manSize(), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(14254950, 5193273));
		ExtendedEntities.BREE_HOBBIT_INNKEEPER = regNPCD("bree_hobbit_innkeeper", ExtendedBreelandHobbitInnkeeperEntity::new, ExtendedBreelandHobbitTraderEntity::createAttributes, LOTREntities.EntitySizeHolder.hobbitSize(), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(14254950, 5193273));
		ExtendedEntities.BREE_BREWER = regNPCD("bree_brewer", ExtendedBreelandBrewerEntity::new, ExtendedBreelandTraderEntity::createAttributes, LOTREntities.EntitySizeHolder.manSize(), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(14254950, 5193273));
		ExtendedEntities.BREE_HOBBIT_BREWER = regNPCD("bree_hobbit_brewer", ExtendedBreelandHobbitBrewerEntity::new, ExtendedBreelandHobbitTraderEntity::createAttributes, LOTREntities.EntitySizeHolder.hobbitSize(), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(14254950, 5193273));
		ExtendedEntities.BREE_FLORIST = regNPCD("bree_florist", ExtendedBreelandFloristEntity::new, ExtendedBreelandTraderEntity::createAttributes, LOTREntities.EntitySizeHolder.manSize(), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(14254950, 5193273));
		ExtendedEntities.BREE_HOBBIT_FLORIST = regNPCD("bree_hobbit_florist", ExtendedBreelandHobbitFloristEntity::new, ExtendedBreelandHobbitTraderEntity::createAttributes, LOTREntities.EntitySizeHolder.hobbitSize(), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(14254950, 5193273));
		ExtendedEntities.BREE_CHEESEMONGER = regNPCD("bree_cheesemonger", ExtendedBreelandCheesemongerEntity::new, ExtendedBreelandTraderEntity::createAttributes, LOTREntities.EntitySizeHolder.manSize(), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(14254950, 5193273));
		ExtendedEntities.BREE_LEATHERWORKER = regNPCD("bree_leatherworker", ExtendedBreelandLeatherworkerEntity::new, ExtendedBreelandTraderEntity::createAttributes, LOTREntities.EntitySizeHolder.manSize(), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(14254950, 5193273));
		ExtendedEntities.BREE_STABLEMASTER = regNPCD("bree_stablemaster", ExtendedBreelandStablemasterEntity::new, ExtendedBreelandTraderEntity::createAttributes, LOTREntities.EntitySizeHolder.manSize(), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(14254950, 2432021));
		// Blue Mountains
		ExtendedEntities.BLUE_MOUNTAINS_MERCHANT = regNPCD("blue_mountains_merchant", ExtendedBlueMountainsMerchantEntity::new, ExtendedBlueMountainsMerchantEntity::createAttributes, LOTREntities.EntitySizeHolder.dwarfSize(), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(16353133, 15357472));
		ExtendedEntities.BLUE_DWARVEN_MINER = regNPCD("blue_mountains_miner", ExtendedBlueMountainsMinerEntity::new, ExtendedBlueMountainsMinerEntity::createAttributes, LOTREntities.EntitySizeHolder.dwarfSize(), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(16353133, 15357472));
		ExtendedEntities.BLUE_DWARVEN_SMITH = regNPCD("blue_mountains_smith", ExtendedBlueMountainsSmithEntity::new, ExtendedBlueMountainsSmithEntity::createAttributes, LOTREntities.EntitySizeHolder.dwarfSize(), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(16353133, 15357472));
		ExtendedEntities.BLUE_DWARVEN_COMMANDER = regNPCD("blue_mountains_commander", ExtendedBlueMountainsCommanderEntity::new, ExtendedBlueMountainsCommanderEntity::createAttributes, LOTREntities.EntitySizeHolder.dwarfSize(), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(16353133, 15357472));
		ExtendedEntities.BLUE_DWARVEN_BARTENDER = regNPCD("blue_mountains_bartender", ExtendedBlueMountainsBartenderEntity::new, ExtendedBlueMountainsBartenderEntity::createAttributes, LOTREntities.EntitySizeHolder.dwarfSize(), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(16353133, 15357472));
		ExtendedEntities.BLUE_DWARVEN_BUTCHER = regNPCD("blue_mountains_butcher", ExtendedBlueMountainsButcherEntity::new, ExtendedBlueMountainsButcherEntity::createAttributes, LOTREntities.EntitySizeHolder.dwarfSize(), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(16353133, 15357472));
		ExtendedEntities.BLUE_DWARVEN_STONEMASON = regNPCD("blue_mountains_stonemason", ExtendedBlueMountainsStonemasonEntity::new, ExtendedBlueMountainsStonemasonEntity::createAttributes, LOTREntities.EntitySizeHolder.dwarfSize(), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(16353133, 15357472));
		ExtendedEntities.BLUE_DWARVEN_AXE_THROWER = regNPCD("blue_mountains_axe_thrower", ExtendedBlueMountainsAxeThrowerEntity::new, ExtendedBlueMountainsAxeThrowerEntity::regAttrs, LOTREntities.EntitySizeHolder.dwarfSize(), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(3161673, 6257551));
		// Dwarves
		ExtendedEntities.DWARVEN_MINER = regNPCD("dwarven_miner", ExtendedDwarvenMinerEntity::new, ExtendedDwarvenMinerEntity::createAttributes, LOTREntities.EntitySizeHolder.dwarfSize(), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(16353133, 15357472));
		ExtendedEntities.DWARVEN_SMITH = regNPCD("dwarven_smith", ExtendedDwarvenSmithEntity::new, ExtendedDwarvenSmithEntity::createAttributes, LOTREntities.EntitySizeHolder.dwarfSize(), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(16353133, 15357472));
		ExtendedEntities.DWARVEN_COMMANDER = regNPCD("dwarven_commander", ExtendedDwarvenCommanderEntity::new, ExtendedDwarvenCommanderEntity::createAttributes, LOTREntities.EntitySizeHolder.dwarfSize(), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(16353133, 15357472));
		// Rivendell
		ExtendedEntities.RIVENDELL_CAPTAIN = regNPCD("rivendell_captain", ExtendedRivendellCaptainEntity::new, ExtendedRivendellCaptainEntity::createAttributes, EntitySizeHolder.elfSize(), SpawnEggInfo.SpawnEggColors.egg(14738662, 10723248));
		ExtendedEntities.RIVENDELL_SMITH = regNPCD("rivendell_smith", ExtendedRivendellSmithEntity::new, ExtendedRivendellSmithEntity::createAttributes, EntitySizeHolder.elfSize(), SpawnEggInfo.SpawnEggColors.egg(16761223, 15721387));
		ExtendedEntities.RIVENDELL_WEAVER = regNPCD("rivendell_weaver", ExtendedRivendellWeaverEntity::new, ExtendedRivendellWeaverEntity::createAttributes, EntitySizeHolder.elfSize(), SpawnEggInfo.SpawnEggColors.egg(16761223, 15721387));
		ExtendedEntities.IMLADRIS_GUARD = regNPCD("imladris_guard", ExtendedRivendellGuardEntity::new, ExtendedRivendellGuardEntity::createAttributes, EntitySizeHolder.elfSize(), SpawnEggInfo.SpawnEggColors.egg(16761223, 15721387));
		ExtendedEntities.ELROND = regNPCD("elrond", ExtendedElrondEntity::new, ExtendedElrondEntity::createAttributes, EntitySizeHolder.elfSize(), SpawnEggInfo.SpawnEggColors.egg(16761223, 15721387));

		// Lindon
		ExtendedEntities.LINDON_LORD = regNPCD("lindon_lord", ExtendedLindonLordEntity::new, ExtendedLindonLordEntity::createAttributes, EntitySizeHolder.elfSize(), SpawnEggInfo.SpawnEggColors.egg(14935016, 7040410));
		ExtendedEntities.LINDON_SMITH = regNPCD("lindon_smith", ExtendedLindonSmithEntity::new, ExtendedLindonSmithEntity::createAttributes, EntitySizeHolder.elfSize(), SpawnEggInfo.SpawnEggColors.egg(16761223, 15721387));
		ExtendedEntities.LINDON_WEAVER = regNPCD("lindon_weaver", ExtendedLindonWeaverEntity::new, ExtendedLindonWeaverEntity::createAttributes, EntitySizeHolder.elfSize(), SpawnEggInfo.SpawnEggColors.egg(16761223, 15721387));
		ExtendedEntities.LINDON_FLORIST = regNPCD("lindon_florist", ExtendedLindonFloristEntity::new, ExtendedLindonFloristEntity::createAttributes, EntitySizeHolder.elfSize(), SpawnEggInfo.SpawnEggColors.egg(16761223, 15721387));

		// Special
		ExtendedEntities.TOLKIEN = regNPCD("tolkien", ExtendedTolkienEntity::new, ExtendedTolkienEntity::createAttributes, LOTREntities.EntitySizeHolder.manSize(), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(8160159, 5439358));

		// Bandits
		ExtendedEntities.BANDIT = regNPCD("bandit", ExtendedBanditEntity::new, ExtendedBanditEntity::createAttributes, LOTREntities.EntitySizeHolder.manSize(), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(2563350, 6382678));
		
		// Isengard
		ExtendedEntities.URUK_BERSERKER = regNPCD("uruk_berserker", ExtendedUrukBerserkerEntity::new, ExtendedUrukBerserkerEntity::createAttributes, LOTREntities.EntitySizeHolder.urukSize(), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(2369050, 14408662));
		ExtendedEntities.URUK_CAPTAIN = regNPCD("uruk_captain", ExtendedUrukCaptainEntity::new, ExtendedUrukCaptainEntity::createAttributes, LOTREntities.EntitySizeHolder.urukSize(), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(2369050, 5790015));
		ExtendedEntities.URUK_SMITH = regNPCD("uruk_smith", ExtendedUrukSmithEntity::new, ExtendedUrukSmithEntity::createAttributes, LOTREntities.EntitySizeHolder.urukSize(), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(2369050, 13421772));
		ExtendedEntities.URUK_SUPPLIER = regNPCD("uruk_supplier", ExtendedUrukSupplierEntity::new, ExtendedUrukSupplierEntity::createAttributes, LOTREntities.EntitySizeHolder.urukSize(), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(2369050, 13421772));
		// Dunlending
		ExtendedEntities.DUNLENDING_WARLORD = regNPCD("dunlending_warlord", ExtendedDunlendingWarlordEntity::new, ExtendedDunlendingWarlordEntity::createAttributes, LOTREntities.EntitySizeHolder.manSize(), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(5192753, 9337975));
		ExtendedEntities.DUNLENDING_AXE_THROWER = regNPCD("dunlending_axe_thrower", ExtendedDunlendingAxeThrowerEntity::new, ExtendedDunlendingAxeThrowerEntity::createAttributes, LOTREntities.EntitySizeHolder.manSize(), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(5192753, 9337975));
		ExtendedEntities.DUNLENDING_BERSERKER = regNPCD("dunlending_berserker", ExtendedDunlendingBerserkerEntity::new, ExtendedDunlendingBerserkerEntity::createAttributes, LOTREntities.EntitySizeHolder.manSize(), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(5192753, 16050121));
		ExtendedEntities.DUNLENDING_BARTENDER = regNPCD("dunlending_bartender", ExtendedDunlendingBartenderEntity::new, ExtendedDunlendingBartenderEntity::createAttributes, LOTREntities.EntitySizeHolder.manSize(), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(15897714, 3679258));
		ExtendedEntities.DUNLENDING_SMITH = regNPCD("dunlending_smith", ExtendedDunlendingSmithEntity::new, ExtendedDunlendingSmithEntity::createAttributes, LOTREntities.EntitySizeHolder.manSize(), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(15897714, 3679258));
		ExtendedEntities.DUNLENDING_FARMER = regNPCD("dunlending_farmer", ExtendedDunlendingFarmerEntity::new, ExtendedDunlendingFarmerEntity::createAttributes, LOTREntities.EntitySizeHolder.manSize(), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(15897714, 3679258));
		ExtendedEntities.DUNLENDING_FARMHAND = regNPCD("dunlending_farmhand", ExtendedDunlendingFarmhandEntity::new, ExtendedDunlendingFarmhandEntity::createAttributes, LOTREntities.EntitySizeHolder.manSize(), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(15897714, 3679258));
	}
}