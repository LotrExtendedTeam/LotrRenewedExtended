package com.github.maximuslotro.lotrrextended.common.spawners;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import net.minecraft.util.JSONUtils;
import net.minecraft.util.math.BlockPos;

public class ExtendedSpawnerBoundsEntry {
	public static final ExtendedSpawnerBoundsEntry DEFAULT = new ExtendedSpawnerBoundsEntry(0, 0, 0);

	public int topYvalue;
	public int lowerYvalue;
	public int horizontalBounds;

	public ExtendedSpawnerBoundsEntry(int topY, int bottomY, int horizontal) {
		this.topYvalue = topY;
		this.lowerYvalue = bottomY;
		this.horizontalBounds = horizontal;
	}

	public BlockPos getMinPos(int x, int y, int z) {
		return new BlockPos(x - horizontalBounds, y + lowerYvalue, z - horizontalBounds);
	}

	public BlockPos getMaxPos(int x, int y, int z) {
		return new BlockPos(x + horizontalBounds, y + topYvalue, z + horizontalBounds);
	}

	public boolean isInYRange(int blockHeight, int entityHeight) {
		return entityHeight + lowerYvalue < blockHeight && blockHeight < entityHeight + topYvalue;
	}

	public static class Serializer implements JsonDeserializer<ExtendedSpawnerBoundsEntry>, JsonSerializer<ExtendedSpawnerBoundsEntry> {
		@Override
		public ExtendedSpawnerBoundsEntry deserialize(JsonElement json, Type p_deserialize_2_, JsonDeserializationContext context) throws JsonParseException {
			JsonObject jsonobject = JSONUtils.convertToJsonObject(json, "ExtendedStructureEntitySpawner");
			int topY = jsonobject.get("topYvalue").getAsInt();
			int bottomY = jsonobject.get("lowerYvalue").getAsInt();
			int horizontal = jsonobject.get("horizontal_bounds").getAsInt();
			return new ExtendedSpawnerBoundsEntry(topY, bottomY, horizontal);
		}

		@Override
		public JsonElement serialize(ExtendedSpawnerBoundsEntry entitySpawner, Type p_serialize_2_, JsonSerializationContext context) {
			JsonObject jsonobject = new JsonObject();
			jsonobject.addProperty("topYvalue", entitySpawner.topYvalue);
			jsonobject.addProperty("lowerYvalue", entitySpawner.lowerYvalue);
			jsonobject.addProperty("horizontal_bounds", entitySpawner.horizontalBounds);
			return jsonobject;
		}
	}
}
