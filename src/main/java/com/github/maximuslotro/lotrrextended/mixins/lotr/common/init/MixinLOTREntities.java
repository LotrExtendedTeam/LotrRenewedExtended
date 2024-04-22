package com.github.maximuslotro.lotrrextended.mixins.lotr.common.init;

import lotr.common.entity.npc.*;
import lotr.common.entity.projectile.ExtendedConkerEntity;
import lotr.common.init.ExtendedEntities;
import lotr.common.init.LOTREntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EntityType.IFactory;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.AttributeModifierMap.MutableAttribute;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraftforge.fml.DatagenModLoader;
import net.minecraftforge.fml.RegistryObject;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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
    		ExtendedEntities.REGISTEREDMOBENTITIES.add(key);
    	}
    	return entity;
    }
    
    @Shadow(remap=false)
    private static <T extends AnimalEntity> RegistryObject<EntityType<T>> regAnimal(String key, IFactory<T> factory,Supplier<MutableAttribute> attribFactory,
    		LOTREntities.EntitySizeHolder size, int trackRange,int updateFreq, LOTREntities.SpawnEggInfo.SpawnEggColors eggColors) {
    	return null;
    }
    private static <T extends AnimalEntity> RegistryObject<EntityType<T>> regAnimalD(String key, IFactory<T> factory,Supplier<MutableAttribute> attribFactory,
    		LOTREntities.EntitySizeHolder size, int trackRange,int updateFreq, LOTREntities.SpawnEggInfo.SpawnEggColors eggColors) {
    	RegistryObject<EntityType<T>> entity = regAnimal(key, factory, attribFactory, size, trackRange, updateFreq, eggColors);
    	if(DatagenModLoader.isRunningDataGen()) {
    		ExtendedEntities.REGISTEREDMOBENTITIES.add(key);
    	}
    	return entity;
    }

    @Shadow(remap=false)
	private static <T extends Entity> RegistryObject<EntityType<T>> regEntity(String key, IFactory<T> factory, EntityClassification classif, float width, float height, int trackRange, int updateFreq) {
		return null;
	}
    private static <T extends Entity> RegistryObject<EntityType<T>> regEntityNOND(String key, IFactory<T> factory, EntityClassification classif, float width, float height, int trackRange, int updateFreq) {
    	RegistryObject<EntityType<T>> entity = regEntity(key, factory, classif, width, height, trackRange, updateFreq);
    	if(DatagenModLoader.isRunningDataGen()) {
    		ExtendedEntities.REGISTEREDNONMOBENTITIES.add(key);
    	}
    	return entity;
	}

    @Inject(method = "register()V", at = @At("HEAD"), remap=false)
    private static void bootstrapExtendedItems(CallbackInfo ci) {
        ExtendedEntities.DUNEDAIN_RANGER = regNPCD("dunedain_ranger", ExtendedDunedainRangerEntity::new, ExtendedDunedainRangerEntity::regAttrs, LOTREntities.EntitySizeHolder.manSize(), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(4473912, 2302748));
		ExtendedEntities.GALADHRIM_WANDERER = regNPCD("galadhrim_wanderer", ExtendedGaladhrimWandererEntity::new, ExtendedGaladhrimWandererEntity::regAttrs, LOTREntities.EntitySizeHolder.elfSize(), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(2047778, 16762150));
        ExtendedEntities.HOBBIT_BARTENDER = regNPCD("hobbit_bartender", ExtendedHobbitBartenderEntity::new, ExtendedHobbitBartenderEntity::regAttrs, LOTREntities.EntitySizeHolder.hobbitSize(), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(16752511, 8010275));
        ExtendedEntities.HOBBIT_FARMER = regNPCD("hobbit_farmer", ExtendedHobbitFarmerEntity::new, ExtendedHobbitFarmerEntity::regAttrs, LOTREntities.EntitySizeHolder.hobbitSize(), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(16752511, 8010275));
        ExtendedEntities.ODDMENT_COLLECTOR = regNPCD("oddment_collector", ExtendedOddmentCollectorEntity::new, ExtendedOddmentCollectorEntity::regAttrs, LOTREntities.EntitySizeHolder.manSize(), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(16225652, 5323553));
		ExtendedEntities.RIVENDELL_WANDERER = regNPCD("rivendell_wanderer", ExtendedRivendellWandererEntity::new, ExtendedRivendellWandererEntity::regAttrs, LOTREntities.EntitySizeHolder.elfSize(), LOTREntities.SpawnEggInfo.SpawnEggColors.egg(869480, 15003391));
        ExtendedEntities.THROWN_CONKER = regEntityNOND("thrown_conker", ExtendedConkerEntity::new, EntityClassification.MISC, 0.25F, 0.25F, 4, 10);
    }

}
