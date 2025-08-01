package com.github.maximuslotro.lotrextended.mixins.lotr.common.entity.npc;

import org.spongepowered.asm.mixin.Mixin;

import lotr.common.entity.npc.BlueDwarfEntity;
import lotr.common.entity.npc.DwarfEntity;
import lotr.common.entity.npc.data.name.ExtendedNPCNameGenerators;
import lotr.common.entity.npc.data.name.NPCNameGenerator;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

/**
 * Mixin class for {@link BlueDwarfEntity} allowing behavior injection into the Blue Dwarf NPC entity.
 * <p>
 * This mixin extends {@link DwarfEntity} to override specific methods for Blue Dwarf behavior
 * while retaining the base functionalities of dwarves in the mod.
 * <p>
 *
 * @Mixin (BlueDwarfEntity.class)
 */
@Mixin(BlueDwarfEntity.class)
public abstract class MixinBlueDwarfEntity extends DwarfEntity {
	/**
     * Required constructor for Mixins extending classes.
     * This constructor will be stripped at runtime during Mixin transformation.
     *
     * @param type The {@link EntityType} of this DwarfEntity.
     * @param w The {@link World} in which this entity exists.
     */
	public MixinBlueDwarfEntity(EntityType<? extends DwarfEntity> type, World w) {
		super(type, w);
	}

	/**
     * Provides the name generator used for Blue Dwarves.
     * Overrides the base DwarfEntity's name generator to use the {@link ExtendedNPCNameGenerators#BLUE_DWARF}
     * name list for Blue Dwarf NPCs.
     *
     * @return The {@link NPCNameGenerator} specific to Blue Dwarves.
     */
	@Override
	protected NPCNameGenerator getNameGenerator() {
		return ExtendedNPCNameGenerators.BLUE_DWARF;
	}
}
