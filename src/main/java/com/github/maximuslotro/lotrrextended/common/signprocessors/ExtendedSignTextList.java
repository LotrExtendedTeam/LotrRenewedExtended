package com.github.maximuslotro.lotrrextended.common.signprocessors;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

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

	private final List<BasicTextEntry> line0;
	private final List<BasicTextEntry> line1;
	private final List<BasicTextEntry> line2;
	private final List<BasicTextEntry> line3;

	public ExtendedSignTextList(List<BasicTextEntry> line0, List<BasicTextEntry> line1, List<BasicTextEntry> line2, List<BasicTextEntry> line3) {
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

	public String getLine(Random random, List<BasicTextEntry> map) {
		if(map.size()==0) {
			return "";
		}
		return map.get(random.nextInt(map.size())).getText(random);
	}

	public static class Serializer implements JsonDeserializer<ExtendedSignTextList>, JsonSerializer<ExtendedSignTextList> {
		@Override
		public ExtendedSignTextList deserialize(JsonElement json, Type p_deserialize_2_, JsonDeserializationContext context) throws JsonParseException {
			JsonObject jsonobject = JSONUtils.convertToJsonObject(json, "ExtendedSignTextList");
			JsonObject jsonobject0 = jsonobject.getAsJsonObject("line0");
			List<BasicTextEntry> line0 = Arrays.stream(JSONUtils.getAsObject(jsonobject0, "string", new String[0], context, String[].class)).map(StringTextEntry::new).collect(Collectors.toList());
			boolean shouldApos0 = JSONUtils.getAsBoolean(jsonobject0, "shouldAddApostrophe", false);
			line0.addAll(Arrays.stream(JSONUtils.getAsObject(jsonobject0, "namebank", new String[0], context, String[].class)).map(s -> new NameBankTextEntry(s, shouldApos0)).collect(Collectors.toList()));
			JsonObject jsonobject1 = jsonobject.getAsJsonObject("line1");
			List<BasicTextEntry> line1 = Arrays.stream(JSONUtils.getAsObject(jsonobject1, "string", new String[0], context, String[].class)).map(StringTextEntry::new).collect(Collectors.toList());
			boolean shouldApos1 = JSONUtils.getAsBoolean(jsonobject1, "shouldAddApostrophe", false);
			line1.addAll(Arrays.stream(JSONUtils.getAsObject(jsonobject1, "namebank", new String[0], context, String[].class)).map(s -> new NameBankTextEntry(s, shouldApos1)).collect(Collectors.toList()));
			JsonObject jsonobject2 = jsonobject.getAsJsonObject("line2");
			List<BasicTextEntry> line2 = Arrays.stream(JSONUtils.getAsObject(jsonobject2, "string", new String[0], context, String[].class)).map(StringTextEntry::new).collect(Collectors.toList());
			boolean shouldApos2 = JSONUtils.getAsBoolean(jsonobject2, "shouldAddApostrophe", false);
			line2.addAll(Arrays.stream(JSONUtils.getAsObject(jsonobject2, "namebank", new String[0], context, String[].class)).map(s -> new NameBankTextEntry(s, shouldApos2)).collect(Collectors.toList()));
			JsonObject jsonobject3 = jsonobject.getAsJsonObject("line3");
			List<BasicTextEntry> line3 = Arrays.stream(JSONUtils.getAsObject(jsonobject3, "string", new String[0], context, String[].class)).map(StringTextEntry::new).collect(Collectors.toList());
			boolean shouldApos3 = JSONUtils.getAsBoolean(jsonobject3, "shouldAddApostrophe", false);
			line3.addAll(Arrays.stream(JSONUtils.getAsObject(jsonobject3, "namebank", new String[0], context, String[].class)).map(s -> new NameBankTextEntry(s, shouldApos3)).collect(Collectors.toList()));
			return new ExtendedSignTextList(line0, line1, line2, line3);
		}

		@Override
		public JsonElement serialize(ExtendedSignTextList signLists, Type p_serialize_2_, JsonSerializationContext context) {
			// TODO write proper serializer
			return new JsonObject();
		}
	}
}
