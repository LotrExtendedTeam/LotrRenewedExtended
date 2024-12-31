package com.github.maximuslotro.lotrrextended.common.state.properties;

import com.github.maximuslotro.lotrrextended.common.enums.RopeType;

import net.minecraft.state.EnumProperty;

public class ExtendedBlockStateProperties {
	public static final EnumProperty<RopeType> ROPE_TYPE = EnumProperty.create("section_type", RopeType.class);
}
