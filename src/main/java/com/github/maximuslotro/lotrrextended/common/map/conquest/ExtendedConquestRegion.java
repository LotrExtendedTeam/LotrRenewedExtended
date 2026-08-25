package com.github.maximuslotro.lotrrextended.common.map.conquest;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;

public class ExtendedConquestRegion {
	private final ExtendedConquestPos pos;
	private final Map<ResourceLocation, Float> conquestLevels = new HashMap<>();
	private long lastDecayedTime;
	private boolean isDirty = false;

	public ExtendedConquestRegion(int x, int z) {
		this(new ExtendedConquestPos(x, z));
	}

	public ExtendedConquestRegion(ExtendedConquestPos conquestPos) {
		this.pos = conquestPos;
	}

	public int getPosX() {
		return pos.getPosX();
	}

	public int getPosZ() {
		return pos.getPosZ();
	}

	public float getConquest(ResourceLocation faction) {
		return conquestLevels.getOrDefault(faction, 0.0f);
	}

	public void addConquest(ResourceLocation faction, float amount) {
		float current = conquestLevels.getOrDefault(faction, 0.0f);
		float newTotal = current + amount;
		if(newTotal <= 0.0f) {
			if(conquestLevels.remove(faction) != null) {
				markDirty();
			}
		}else {
			Float previous = conquestLevels.put(faction, newTotal);
			if(previous == null || previous != newTotal) {
				markDirty();
			}
		}
	}

	public Map<ResourceLocation, Float> getConquestLevels() {
		return Collections.unmodifiableMap(conquestLevels);
	}

	public long getLastDecayedTime() {
		return lastDecayedTime;
	}

	public void setLastDecayedTime(long lastDecayedTime) {
		if(this.lastDecayedTime != lastDecayedTime) {
			this.lastDecayedTime = lastDecayedTime;
			markDirty();
		}
	}

	public void markDirty() {
		this.isDirty = true;
	}

	public boolean needsSave() {
		return isDirty;
	}

	public CompoundNBT save() {
		CompoundNBT output = new CompoundNBT();
		output.putInt("x", getPosX());
		output.putInt("z", getPosZ());
		output.putLong("LastDecayedTime", lastDecayedTime);

		CompoundNBT factionsNbt = new CompoundNBT();
		for(Map.Entry<ResourceLocation, Float> entry : conquestLevels.entrySet()) {
			factionsNbt.putFloat(entry.getKey().toString(), entry.getValue());
		}
		output.put("Factions", factionsNbt);

		this.isDirty = false;
		return output;
	}

	public static ExtendedConquestRegion loadFromNBT(CompoundNBT nbt) {
		int x = nbt.getInt("x");
		int z = nbt.getInt("z");
		ExtendedConquestRegion region = new ExtendedConquestRegion(x, z);
		region.lastDecayedTime = nbt.getLong("LastDecayedTime");

		if(nbt.contains("Factions", 10)) {
			CompoundNBT factionsNbt = nbt.getCompound("Factions");
			for(String key : factionsNbt.getAllKeys()) {
				ResourceLocation faction = new ResourceLocation(key);
				float level = factionsNbt.getFloat(key);
				region.conquestLevels.put(faction, level);
			}
		}
		region.isDirty = false;
		return region;
	}
}
