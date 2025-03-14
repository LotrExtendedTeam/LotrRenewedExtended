package com.github.maximuslotro.lotrrextended.client.hiredunits;

import com.github.maximuslotro.lotrrextended.common.enums.ExtendedUnitAttackType;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedServerHiredUnitProfile;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

public class ExtendedClientHiredUnitProfile {
	private final ResourceLocation id;
	private final boolean translateStrings;
	private final String entity_unit_type;
	private final int hiringCost;
	private final boolean hiringRequiresPledge;
	private final int hiringAlignmentRequired;
	private final ExtendedUnitAttackType attackType;

	public ExtendedClientHiredUnitProfile(ResourceLocation id, boolean translateStrings, String entity_unit_type, int hiringCost, boolean hiringRequiresPledge, int hiringAlignmentRequired, ExtendedUnitAttackType attackType) {
		this.id = id;
		this.translateStrings = translateStrings;
		this.entity_unit_type = entity_unit_type;
		this.hiringCost = hiringCost;
		this.hiringRequiresPledge = hiringRequiresPledge;
		this.hiringAlignmentRequired = hiringAlignmentRequired;
		this.attackType = attackType;
	}

	/*
	 * Get the id of this ExtendedServerHiredUnitProfile
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
	public String getEntityUnitTypeName() {
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

	/**
	 * Gets the attack type enum for this profile
	 * @see ExtendedUnitAttackType
	 * @return The attack type enum instance
	 * */
	public ExtendedUnitAttackType getAttackType() {
		return attackType;
	}

	public static ExtendedClientHiredUnitProfile fromNetwork(PacketBuffer buf) {
		ResourceLocation id = buf.readResourceLocation();
		boolean translateStrings = buf.readBoolean();
		String entity_unit_type = buf.readUtf();
		int hiringCost = buf.readInt();
		boolean hiringRequiresPledge = buf.readBoolean();
		int hiringAlignmentRequired = buf.readInt();
		ExtendedUnitAttackType attackType = ExtendedUnitAttackType.byName(buf.readUtf());
		return new ExtendedClientHiredUnitProfile(id, translateStrings, entity_unit_type, hiringCost, hiringRequiresPledge, hiringAlignmentRequired, attackType);
	}

	public static void toNetwork(ExtendedServerHiredUnitProfile serverProfile, PacketBuffer buf) {
		buf.writeResourceLocation(serverProfile.getId());
		buf.writeBoolean(serverProfile.stringsAreTranslated());
		buf.writeUtf(serverProfile.getEntityUnitTypeName());
		buf.writeInt(serverProfile.getHiringCost());
		buf.writeBoolean(serverProfile.isHiringRequiresPledge());
		buf.writeInt(serverProfile.getHiringAlignmentRequired());
		buf.writeUtf(serverProfile.getAttackType().getAttackTypeName());
	}

	public static ExtendedClientHiredUnitProfile fromNetwork(CompoundNBT nbt) {
		ResourceLocation id = new ResourceLocation(nbt.getString("id"));
		boolean translateStrings = nbt.getBoolean("translateStrings");
		String entity_unit_type = nbt.getString("entity_unit_type");
		int hiringCost = nbt.getInt("hiringCost");
		boolean hiringRequiresPledge = nbt.getBoolean("hiringRequiresPledge");
		int hiringAlignmentRequired = nbt.getInt("hiringAlignmentRequired");
		ExtendedUnitAttackType attackType = ExtendedUnitAttackType.byName(nbt.getString("attack_type"));
		return new ExtendedClientHiredUnitProfile(id, translateStrings, entity_unit_type, hiringCost, hiringRequiresPledge, hiringAlignmentRequired, attackType);
	}

	public static void toNetwork(ExtendedServerHiredUnitProfile serverProfile, CompoundNBT nbt) {
		nbt.putString("id", serverProfile.getId().toString());
		nbt.putBoolean("translateStrings", serverProfile.stringsAreTranslated());
		nbt.putString("entity_unit_type", serverProfile.getEntityUnitTypeName());
		nbt.putInt("hiringCost", serverProfile.getHiringCost());
		nbt.putBoolean("hiringRequiresPledge", serverProfile.isHiringRequiresPledge());
		nbt.putInt("hiringAlignmentRequired", serverProfile.getHiringAlignmentRequired());
		nbt.putString("attack_type", serverProfile.getAttackType().getAttackTypeName());
	}
}