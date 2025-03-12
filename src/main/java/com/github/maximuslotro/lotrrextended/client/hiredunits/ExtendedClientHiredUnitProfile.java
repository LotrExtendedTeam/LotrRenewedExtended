package com.github.maximuslotro.lotrrextended.client.hiredunits;

import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedHiredUnitProfile;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

public class ExtendedClientHiredUnitProfile {
	private final ResourceLocation id;
	private final boolean translateStrings;
	private final String entity_unit_type;
	private final int hiringCost;
	private final boolean hiringRequiresPledge;
	private final int hiringAlignmentRequired;

	public ExtendedClientHiredUnitProfile(ResourceLocation id, boolean translateStrings, String entity_unit_type, int hiringCost, boolean hiringRequiresPledge, int hiringAlignmentRequired) {
		this.id = id;
		this.translateStrings = translateStrings;
		this.entity_unit_type = entity_unit_type;
		this.hiringCost = hiringCost;
		this.hiringRequiresPledge = hiringRequiresPledge;
		this.hiringAlignmentRequired = hiringAlignmentRequired;
	}

	/*
	 * Get the id of this ExtendedHiredUnitProfile
	 * */
	public ResourceLocation getId() {
		return id;
	}

	/*
	 * Check if the strings of this profile should be localized
	 * */
	public boolean stringsAreTranslated() {
		return translateStrings;
	}


	/*
	 * Gets the string or key for the unit's type name
	 * */
	public String getUnitTypeName() {
		return entity_unit_type;
	}

	/**
	 * Hiring cost for this unit profile
	 * @return the cost in coins
	 * */
	public int getHiringCost() {
		return hiringCost;
	}

	/**
	 * Does this unit profile require pledge to hire?
	 * @return if pledge is required
	 * */
	public boolean isHiringRequiresPledge() {
		return hiringRequiresPledge;
	}

	/**
	 * Hiring aligment level for the unit
	 * @return the alignment level
	 * */
	public int getHiringAlignmentRequired() {
		return hiringAlignmentRequired;
	}

	public static ExtendedClientHiredUnitProfile fromNetwork(PacketBuffer buf) {
		ResourceLocation id = buf.readResourceLocation();

		boolean translateStrings = buf.readBoolean();
		String entity_unit_type = buf.readUtf();
		int hiringCost = buf.readInt();
		boolean hiringRequiresPledge = buf.readBoolean();
		int hiringAlignmentRequired = buf.readInt();
		return new ExtendedClientHiredUnitProfile(id, translateStrings, entity_unit_type, hiringCost, hiringRequiresPledge, hiringAlignmentRequired);
	}

	public static void toNetwork(ExtendedHiredUnitProfile serverProfile, PacketBuffer buf) {
		buf.writeResourceLocation(serverProfile.getId());
		buf.writeBoolean(serverProfile.stringsAreTranslated());
		buf.writeUtf(serverProfile.getUnitTypeName());
		buf.writeInt(serverProfile.getHiringCost());
		buf.writeBoolean(serverProfile.isHiringRequiresPledge());
		buf.writeInt(serverProfile.getHiringAlignmentRequired());
	}
}