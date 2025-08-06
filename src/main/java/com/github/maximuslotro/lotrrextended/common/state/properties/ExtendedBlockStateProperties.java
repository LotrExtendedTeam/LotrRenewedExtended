package com.github.maximuslotro.lotrrextended.common.state.properties;

import com.github.maximuslotro.lotrrextended.common.enums.ExtendedPillarType;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedRopeType;

import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;

public class ExtendedBlockStateProperties {
	public static final EnumProperty<ExtendedPillarType> PILLAR_STATE = EnumProperty.create("pillar_state", ExtendedPillarType.class);
	public static final EnumProperty<ExtendedRopeType> ROPE_TYPE = EnumProperty.create("section_type", ExtendedRopeType.class);
	public static final BooleanProperty CLIPPED = BooleanProperty.create("clipped");
}
