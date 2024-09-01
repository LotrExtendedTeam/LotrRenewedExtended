package com.github.maximuslotro.lotrrextended.mixins.lotr.common.entity.npc;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import lotr.common.entity.npc.GundabadOrcArcherEntity;
import lotr.common.entity.npc.SpawnEquipmentTable;
import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRItems;

@Mixin(GundabadOrcArcherEntity.class)
public class MixinGundabadOrcArcherEntity{

	@Final
	@Mutable
	@Shadow(remap=false)
	private static final SpawnEquipmentTable DAGGERS = SpawnEquipmentTable.of(LOTRItems.STONE_DAGGER, LOTRItems.IRON_DAGGER, 
			LOTRItems.BRONZE_DAGGER, LOTRItems.MORDOR_DAGGER, ExtendedItems.BONE_DAGGER);
}
