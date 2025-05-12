package com.github.maximuslotro.lotrrextended.common.signprocessors;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import net.minecraft.util.JSONUtils;

public class ExtendedSignTextList {
	public static final ExtendedSignTextList EMPTY = new ExtendedSignTextList(new ArrayList<>(),new ArrayList<>(),new ArrayList<>(),new ArrayList<>());

	private final List<String> line0;
	private final List<String> line1;
	private final List<String> line2;
	private final List<String> line3;

	public ExtendedSignTextList(List<String> line0, List<String> line1, List<String> line2, List<String> line3) {
		this.line0 = line0;
		this.line1 = line1;
		this.line2 = line2;
		this.line3 = line3;
	}

	public String getLine0(Random random) {
		return getLine(random, line0);
	}

	public String getLine1(Random random) {
		return getLine(random, line1);
	}

	public String getLine2(Random random) {
		return getLine(random, line2);
	}

	public String getLine3(Random random) {
		return getLine(random, line3);
	}

	public String getLine(Random random, List<String> map) {
		if(map.size()==0) {
			return "";
		}
		return map.get(random.nextInt(map.size()));
	}

	public static class Serializer implements JsonDeserializer<ExtendedSignTextList>, JsonSerializer<ExtendedSignTextList> {
		@Override
		public ExtendedSignTextList deserialize(JsonElement json, Type p_deserialize_2_, JsonDeserializationContext context) throws JsonParseException {
			JsonObject jsonobject = JSONUtils.convertToJsonObject(json, "ExtendedSignTextList");
			List<String> line0 = Lists.newArrayList(JSONUtils.getAsObject(jsonobject, "line0", new String[0], context, String[].class));
			List<String> line1 = Lists.newArrayList(JSONUtils.getAsObject(jsonobject, "line1", new String[0], context, String[].class));
			List<String> line2 = Lists.newArrayList(JSONUtils.getAsObject(jsonobject, "line2", new String[0], context, String[].class));
			List<String> line3 = Lists.newArrayList(JSONUtils.getAsObject(jsonobject, "line3", new String[0], context, String[].class));
			return new ExtendedSignTextList(line0, line1, line2, line3);
		}

		@Override
		public JsonElement serialize(ExtendedSignTextList signLists, Type p_serialize_2_, JsonSerializationContext context) {
			JsonObject jsonobject = new JsonObject();
			jsonobject.add("line0", context.serialize(signLists.line0));
			jsonobject.add("line1", context.serialize(signLists.line1));
			jsonobject.add("line2", context.serialize(signLists.line2));
			jsonobject.add("line3", context.serialize(signLists.line3));
			return jsonobject;
		}
	}
}
