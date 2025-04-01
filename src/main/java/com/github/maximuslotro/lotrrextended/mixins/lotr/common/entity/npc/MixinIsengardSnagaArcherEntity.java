package com.github.maximuslotro.lotrrextended.mixins.lotr.common.entity.npc;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import lotr.common.entity.npc.IsengardSnagaArcherEntity;
import lotr.common.entity.npc.SpawnEquipmentTable;
import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRItems;
/**
 * @reason Need to change Isengard Orc Archer equipment
 * @Mixin (IsengardSnagaArcherEntity.class)
 * */
@Mixin(IsengardSnagaArcherEntity.class)
public class MixinIsengardSnagaArcherEntity{

	/**
	 * @reason Need to add bone dagger to equipment pool
	 * @Shadow (remap=false)
	 * @Final Mark the field to be essentially final
	 * @Mutable Tell Mixin that it can modify the Final field
	 * */
	@Final
	@Mutable
	@Shadow(remap=false)
	private static final SpawnEquipmentTable DAGGERS = SpawnEquipmentTable.of(LOTRItems.STONE_DAGGER, LOTRItems.IRON_DAGGER,
			LOTRItems.BRONZE_DAGGER, LOTRItems.URUK_DAGGER, ExtendedItems.BONE_DAGGER);
}
