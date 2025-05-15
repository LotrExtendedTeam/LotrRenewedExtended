package com.github.maximuslotro.lotrrextended.common.signprocessors;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import net.minecraft.util.JSONUtils;

public class ExtendedSignTextList {
	public static final ExtendedSignTextList EMPTY = new ExtendedSignTextList(null, null, null, null, null);

	private final List<ExtendedTextEntryBasic> line0;
	private final List<ExtendedTextEntryBasic> line1;
	private final List<ExtendedTextEntryBasic> line2;
	private final List<ExtendedTextEntryBasic> line3;
	private final List<ExtendedMessageEntryBasic> lines;
	
	public ExtendedSignTextList(List<ExtendedTextEntryBasic> line0, List<ExtendedTextEntryBasic> line1, List<ExtendedTextEntryBasic> line2, List<ExtendedTextEntryBasic> line3, List<ExtendedMessageEntryBasic> lines) {
		this.line0 = line0;
		this.line1 = line1;
		this.line2 = line2;
		this.line3 = line3;
		this.lines = lines;
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

	private String getLine(Random random, List<ExtendedTextEntryBasic> map) {
		if(map==null || map.size()==0) {
			return "";
		}
		return map.get(random.nextInt(map.size())).getText(random);
	}

	public boolean hasLines() {
		return lines!=null && !lines.isEmpty();
	}

	public ExtendedMessageEntryBasic getLine(Random random) {
		if(lines==null || lines.size()==0) {
			ExtendedLog.error("ExtendedBasicMessageEntry called without first checking if empty. SKIPPING!");
			return null;
		}
		return lines.get(random.nextInt(lines.size()));
	}
	public static class Serializer implements JsonDeserializer<ExtendedSignTextList>, JsonSerializer<ExtendedSignTextList> {
		@Override
		public ExtendedSignTextList deserialize(JsonElement json, Type p_deserialize_2_, JsonDeserializationContext context) throws JsonParseException {
			JsonObject jsonobject = JSONUtils.convertToJsonObject(json, "ExtendedSignTextList");
			// Get full line specifications
			List<ExtendedMessageEntryBasic> lines = null;
			List<ExtendedTextEntryBasic> line0 = null;
			List<ExtendedTextEntryBasic> line1 = null;
			List<ExtendedTextEntryBasic> line2 = null;
			List<ExtendedTextEntryBasic> line3 = null;
			// Non random line layout
			if(jsonobject.has("lines")) {
				lines = new ArrayList<>();
				JsonArray linesArray = JSONUtils.getAsJsonArray(jsonobject, "lines");
				for (JsonElement element : linesArray) {
					JsonArray innerArray = element.getAsJsonArray();
					if(innerArray.size()!=4) {
						ExtendedLog.error("Recieved a malformed sign text list, 4 lines required! SKIPPING! entry");
						ExtendedLog.error(innerArray.toString());
						return null;
					}
					lines.add(new ExtendedMessageEntryList(innerArray.get(0).getAsString(),innerArray.get(1).getAsString(),innerArray.get(2).getAsString(),innerArray.get(3).getAsString()));
				}
			}else {
				// Line 0
				JsonObject jsonobject0 = jsonobject.getAsJsonObject("line0");
				line0 = Arrays.stream(JSONUtils.getAsObject(jsonobject0, "string", new String[0], context, String[].class)).map(ExtendedTextEntryString::new).collect(Collectors.toList());
				boolean shouldApos0 = JSONUtils.getAsBoolean(jsonobject0, "shouldAddApostrophe", false);
				line0.addAll(Arrays.stream(JSONUtils.getAsObject(jsonobject0, "namebank", new String[0], context, String[].class)).map(s -> new ExtendedTextEntryNameBank(s, shouldApos0)).collect(Collectors.toList()));
				// Line 1
				JsonObject jsonobject1 = jsonobject.getAsJsonObject("line1");
				line1 = Arrays.stream(JSONUtils.getAsObject(jsonobject1, "string", new String[0], context, String[].class)).map(ExtendedTextEntryString::new).collect(Collectors.toList());
				boolean shouldApos1 = JSONUtils.getAsBoolean(jsonobject1, "shouldAddApostrophe", false);
				line1.addAll(Arrays.stream(JSONUtils.getAsObject(jsonobject1, "namebank", new String[0], context, String[].class)).map(s -> new ExtendedTextEntryNameBank(s, shouldApos1)).collect(Collectors.toList()));
				// Line 2
				JsonObject jsonobject2 = jsonobject.getAsJsonObject("line2");
				line2 = Arrays.stream(JSONUtils.getAsObject(jsonobject2, "string", new String[0], context, String[].class)).map(ExtendedTextEntryString::new).collect(Collectors.toList());
				boolean shouldApos2 = JSONUtils.getAsBoolean(jsonobject2, "shouldAddApostrophe", false);
				line2.addAll(Arrays.stream(JSONUtils.getAsObject(jsonobject2, "namebank", new String[0], context, String[].class)).map(s -> new ExtendedTextEntryNameBank(s, shouldApos2)).collect(Collectors.toList()));
				// Line 3
				JsonObject jsonobject3 = jsonobject.getAsJsonObject("line3");
				line3 = Arrays.stream(JSONUtils.getAsObject(jsonobject3, "string", new String[0], context, String[].class)).map(ExtendedTextEntryString::new).collect(Collectors.toList());
				boolean shouldApos3 = JSONUtils.getAsBoolean(jsonobject3, "shouldAddApostrophe", false);
				line3.addAll(Arrays.stream(JSONUtils.getAsObject(jsonobject3, "namebank", new String[0], context, String[].class)).map(s -> new ExtendedTextEntryNameBank(s, shouldApos3)).collect(Collectors.toList()));
			}
			return new ExtendedSignTextList(line0, line1, line2, line3, lines);
		}

		@Override
		public JsonElement serialize(ExtendedSignTextList signLists, Type p_serialize_2_, JsonSerializationContext context) {
			// TODO write proper serializer
			return new JsonObject();
		}
	}
}
