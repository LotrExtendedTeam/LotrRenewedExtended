package com.github.maximuslotro.lotrrextended.common.serialization;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.math.vector.Vector3i;

public class ExtendedSerialization {
	public static class Vector3fSerializer {
		public static CompoundNBT writeVector3f(Vector3f v) {
			CompoundNBT tag = new CompoundNBT();
			tag.putFloat("x", v.x());
			tag.putFloat("y", v.y());
			tag.putFloat("z", v.z());
			return tag;
		}
		public static Vector3f readVector3f(CompoundNBT tag) {
			return new Vector3f(tag.getFloat("x"), tag.getFloat("y"), tag.getFloat("z"));
		}
		public static JsonObject toJson(Vector3f vec) {
			JsonObject obj = new JsonObject();
			obj.addProperty("x", vec.x());
			obj.addProperty("y", vec.y());
			obj.addProperty("z", vec.z());
			return obj;
		}
		public static Vector3f fromJson(JsonElement element) {
			if (element == null || !element.isJsonObject()) {
				return new Vector3f(0.0F, 0.0F, 0.0F);
			}
			JsonObject obj = element.getAsJsonObject();
			float x = getFloat(obj, "x");
			float y = getFloat(obj, "y");
			float z = getFloat(obj, "z");

			return new Vector3f(x, y, z);
		}
	}
	public static class Vector3iSerializer {
		public static CompoundNBT writeVector3i(Vector3i v) {
			CompoundNBT tag = new CompoundNBT();
			tag.putInt("x", v.getX());
			tag.putInt("y", v.getY());
			tag.putInt("z", v.getY());
			return tag;
		}
		public static Vector3f readVector3f(CompoundNBT tag) {
			return new Vector3f(tag.getInt("x"), tag.getInt("y"), tag.getInt("z"));
		}
		public static JsonObject toJson(Vector3i vec) {
			JsonObject obj = new JsonObject();
			obj.addProperty("x", vec.getX());
			obj.addProperty("y", vec.getY());
			obj.addProperty("z", vec.getZ());
			return obj;
		}
		public static Vector3i fromJson(JsonElement element) {
			if (element == null || !element.isJsonObject()) {
				return new Vector3i(0, 0, 0);
			}
			JsonObject obj = element.getAsJsonObject();
			int x = getInt(obj, "x");
			int y = getInt(obj, "y");
			int z = getInt(obj, "z");

			return new Vector3i(x, y, z);
		}
	}

	private static float getFloat(JsonObject obj, String key) {
		return obj.has(key) && obj.get(key).isJsonPrimitive() ? obj.get(key).getAsFloat() : 0.0F;
	}
	private static int getInt(JsonObject obj, String key) {
		return obj.has(key) && obj.get(key).isJsonPrimitive() ? obj.get(key).getAsInt() : 0;
	}
}
