package com.github.maximuslotro.lotrrextended.mixins.lotr.common.init;

import lotr.common.entity.npc.*;
import lotr.common.init.ExtendedEntities;
import lotr.common.init.LOTREntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry.IPlacementPredicate;
import net.minecraft.entity.EntitySpawnPlacementRegistry.PlacementType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EntityType.Builder;
import net.minecraft.entity.EntityType.IFactory;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.AttributeModifierMap.MutableAttribute;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.world.gen.Heightmap.Type;
import net.minecraftforge.fml.DatagenModLoader;
import net.minecraftforge.fml.RegistryObject;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.maximuslotro.lotrrextended.common.entity.passive.ExtendedDuckEntity;
import com.github.maximuslotro.lotrrextended.common.entity.projectile.ExtendedConkerEntity;
import com.github.maximuslotro.lotrrextended.common.entity.projectile.ExtendedDuckEggEntity;
import com.github.maximuslotro.lotrrextended.common.entity.special.ExtendedBannerEntity;
import com.github.maximuslotro.lotrrextended.common.entity.special.ExtendedStructureSpawnerEntity;
import com.github.maximuslotro.lotrrextended.common.entity.special.ExtendedTraderRespawnEntity;

import java.util.function.Consumer;
import java.util.function.Supplier;

@Mixin(LOTREntities.class)
public class MixinLOTREntities {

	@Shadow(remap=false)
	private static <T extends NPCEntity> RegistryObject<EntityType<T>> regNPC(String key, EntityType.IFactory<T> factory, Supplier<AttributeModifierMap.MutableAttribute> attribFactory, LOTREntities.EntitySizeHolder size, LOTREntities.SpawnEggInfo.SpawnEggColors eggColors) {
		return null;
	}
	private static <T extends NPCEntity> RegistryObject<EntityType<T>> regNPCD(String key, EntityType.IFactory<T> factory, Supplier<AttributeModifierMap.MutableAttribute> attribFactory, LOTREntities.EntitySizeHolder size, LOTREntities.SpawnEggInfo.SpawnEggColors eggColors) {
		RegistryObject<EntityType<T>> entity = regNPC(key, factory, attribFactory, size, eggColors);
		if(DatagenModLoader.isRunningDataGen()) {
			ExtendedEntities.REGISTEREDNPCENTITIES.add(key);
		}
		return entity;
	}

	@Shadow(remap=false)
	private static <T extends AnimalEntity> RegistryObject<EntityType<T>> regAnimal(String key, IFactory<T> factory,Supplier<MutableAttribute> attribFactory, 
									LOTREntities.EntitySizeHolder size, int trackRange, int updateFreq, LOTREntities.SpawnEggInfo.SpawnEggColors eggColors, 
									PlacementType placementType, Type heightmapType, IPlacementPredicate<T> placementPredicate) {
		return null;
	}

	private static <T extends AnimalEntity> RegistryObject<EntityType<T>> regAnimalD(String key, IFactory<T> factory,Supplier<MutableAttribute> attribFactory,
									LOTREntities.EntitySizeHolder size, int trackRange,int updateFreq, LOTREntities.SpawnEggInfo.SpawnEggColors eggColors) {
		RegistryObject<EntityType<T>> entity = regAnimal(key, factory, attribFactory, size, trackRange, updateFreq, eggColors, PlacementType.ON_GROUND,
										Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::checkAnimalSpawnRules);
		if(DatagenModLoader.isRunningDataGen()) {
			ExtendedEntities.REGISTEREDANIMALENTITIES.add(key);
		}
		return entity;
	}

	private static <T extends AnimalEntity> RegistryObject<EntityType<T>> regAnimalD(String key, IFactory<T> factory,Supplier<MutableAttribute> attribFactory,
									LOTREntities.EntitySizeHolder size, int trackRange,int updateFreq, LOTREntities.SpawnEggInfo.SpawnEggColors eggColors, 
									IPlacementPredicate<T> placementPredicate) {
		RegistryObject<EntityType<T>> entity = regAnimal(key, factory, attribFactory, size, trackRange, updateFreq, eggColors, PlacementType.ON_GROUND,
										Type.MOTION_BLOCKING_NO_LEAVES, placementPredicate);
		if(DatagenModLoader.isRunningDataGen()) {
			ExtendedEntities.REGISTEREDANIMALENTITIES.add(key);
		}
		return entity;
	}

	@Shadow(remap=false)
	private static <T extends Entity> RegistryObject<EntityType<T>> regEntity(String key, IFactory<T> factory, EntityClassification classif, float width, float height, int trackRange, int updateFreq) {
		return null;
	}

	@Shadow(remap=false)
	private static <T extends Entity> RegistryObject<EntityType<T>> regEntity(String key, IFactory<T> factory, EntityClassification classif, float width, float height, int trackRange, int updateFreq, Consumer<Builder<T>> extraProps) {
		return null;
	}

	private static <T extends Entity> RegistryObject<EntityType<T>> regEntityNOND(String key, IFactory<T> factory, EntityClassification classif, float width, float height, int trackRange, int updateFreq) {
		RegistryObject<EntityType<T>> entity = regEntity(key, factory, classif, width, height, trackRange, updateFreq);
		if(DatagenModLoader.isRunningDataGen()) {
			ExtendedEntities.REGISTEREDNONMOBENTITIES.add(key);
		}
		return entity;
	}
	private static <T extends Entity> RegistryObject<EntityType<T>> regEntityExpanded(String key, IFactory<T> factory, EntityClassification classif, float width, float height) {
		RegistryObject<EntityType<T>> entity = regEntity(key, factory, classif, width, height, 5, 3, (builder) -> {
			builder.canSpawnFarFromPlayer();
			builder.fireImmune();
		});
		if(DatagenModLoader.isRunningDataGen()) {
			ExtendedEntities.REGISTEREDNONMOBENTITIES.add(key);
		}
		return entity;
	}

	@Inject(method = "register()V", at = @At("HEAD"), remap=false)
	private static void bootstrapExtendedItems(CallbackInfo ci) {
		ExtendedEntities.DUNEDAIN_RANGER = regNPCD("dunedain_ranger", ExtendedDunedainRangerEntity::new, ExtendedDunedainRangerEntity::regAttrs, LOTREntities.EntitySizeHolder.size(0.6F, 1.93f), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(4473912, 2302748));
		ExtendedEntities.GALADHRIM_WANDERER = regNPCD("galadhrim_wanderer", ExtendedGaladhrimWandererEntity::new, ExtendedGaladhrimWandererEntity::regAttrs, LOTREntities.EntitySizeHolder.elfSize(), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(2047778, 16762150));
		ExtendedEntities.HOBBIT_BARTENDER = regNPCD("hobbit_bartender", ExtendedHobbitBartenderEntity::new, ExtendedHobbitBartenderEntity::regAttrs, LOTREntities.EntitySizeHolder.hobbitSize(), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(16752511, 8010275));
		ExtendedEntities.HOBBIT_FARMER = regNPCD("hobbit_farmer", ExtendedHobbitFarmerEntity::new, ExtendedHobbitFarmerEntity::regAttrs, LOTREntities.EntitySizeHolder.hobbitSize(), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(16752511, 8010275));
		ExtendedEntities.ODDMENT_COLLECTOR = regNPCD("oddment_collector", ExtendedOddmentCollectorEntity::new, ExtendedOddmentCollectorEntity::regAttrs, LOTREntities.EntitySizeHolder.manSize(), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(16225652, 5323553));
		ExtendedEntities.RIVENDELL_WANDERER = regNPCD("rivendell_wanderer", ExtendedRivendellWandererEntity::new, ExtendedRivendellWandererEntity::regAttrs, LOTREntities.EntitySizeHolder.elfSize(), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(869480, 15003391));
		ExtendedEntities.THROWN_CONKER = regEntityNOND("thrown_conker", ExtendedConkerEntity::new, EntityClassification.MISC, 0.25F, 0.25F, 4, 10);
		ExtendedEntities.DUCK = regAnimalD("duck", ExtendedDuckEntity::new, ExtendedDuckEntity::createAttributes, LOTREntities.EntitySizeHolder.size(0.4F, 0.7F), 10, 3, LOTREntities.SpawnEggInfo.SpawnEggColors.egg(940841, 11374685), ExtendedDuckEntity::checkTurtleSpawnRules);
		ExtendedEntities.DUCK_EGG_ENTITY = regEntityNOND("duck_egg", ExtendedDuckEggEntity::new, EntityClassification.MISC, 0.25F, 0.25F, 4, 10);
		ExtendedEntities.STRUCTURE_ENTITY_SPAWNER = regEntityExpanded("structure_spawner", ExtendedStructureSpawnerEntity::new, EntityClassification.MISC, 0.5F, 1.0F);
		ExtendedEntities.TRADER_RESPAWNER_ENTITY = regEntityExpanded("trader_respawner", ExtendedTraderRespawnEntity::new, EntityClassification.MISC, 0.5F, 0.5F);
		ExtendedEntities.BLUE_MOUNTAINS_MERCHANT = regNPCD("blue_mountains_merchant", ExtendedBlueMountainsMerchantEntity::new, ExtendedBlueMountainsMerchantEntity::regAttrs, LOTREntities.EntitySizeHolder.dwarfSize(), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(16353133, 15357472));
		ExtendedEntities.GUNDABAD_CAMP_TRADER = regNPCD("gundabad_orc_scrounger", ExtendedGundabadOrcTraderEntity::new, ExtendedGundabadOrcTraderEntity::regAttrs, LOTREntities.EntitySizeHolder.orcSize(), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(5979436, 13421772));
		ExtendedEntities.GUNDABAD_URUK = regNPCD("gundabad_uruk", ExtendedGundabadUrukEntity::new, ExtendedGundabadUrukEntity::regAttrs, LOTREntities.EntitySizeHolder.urukSize(), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(2563350, 6382678));
		ExtendedEntities.GUNDABAD_URUK_ARCHER = regNPCD("gundabad_uruk_archer", ExtendedGundabadUrukArcherEntity::new, ExtendedGundabadUrukArcherEntity::regAttrs, LOTREntities.EntitySizeHolder.urukSize(), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(2563350, 6382678));
		ExtendedEntities.BANNER_HOLDER = regEntityExpanded("banner_dataholder", ExtendedBannerEntity::new, EntityClassification.MISC, 0.125F, 1.0F);
		ExtendedEntities.BREE_MERCHANT = regNPCD("bree_merchant", ExtendedBreelandMerchantEntity::new, ExtendedBreelandMerchantEntity::regAttrs, LOTREntities.EntitySizeHolder.manSize(), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(14254950, 6573367));
	}

}