package com.github.maximuslotro.lotrrextended.common.init;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.common.entity.item.ExtendedBoatEntity;

import lotr.common.init.ExtendedEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EntityType.Builder;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DatagenModLoader;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ExtendedEntitiesNew {
	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, LotrExtendedMod.MODID);

	public static final RegistryObject<EntityType<ExtendedBoatEntity>> BOAT = register("boat", EntityType.Builder.<ExtendedBoatEntity>of(ExtendedBoatEntity::new, EntityClassification.MISC).sized(1.375F, 0.5625F).clientTrackingRange(10));

	private static <T extends Entity> RegistryObject<EntityType<T>> register(String pEntityName, Builder<T> pEntityTypeBuilder) {
		if(DatagenModLoader.isRunningDataGen()) {
			ExtendedEntities.REGISTEREDNONMOBENTITIESEXTENDED.add(pEntityName);
		}
		return ENTITIES.register(pEntityName, () -> pEntityTypeBuilder.build(LotrExtendedMod.MODID + ":" +pEntityName));
	}

	public static void register(IEventBus bus) {
		ENTITIES.register(bus);
	}

}
