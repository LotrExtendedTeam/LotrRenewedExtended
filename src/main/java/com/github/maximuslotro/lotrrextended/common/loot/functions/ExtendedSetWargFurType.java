package com.github.maximuslotro.lotrrextended.common.loot.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;

import lotr.common.entity.npc.WargEntity;
import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRItems;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootFunction;
import net.minecraft.loot.LootFunctionType;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.ILootCondition;

public class ExtendedSetWargFurType extends LootFunction {

	private ExtendedSetWargFurType(ILootCondition[] conditions) {
		super(conditions);
	}

	@Override
	public LootFunctionType getType() {
		return ExtendedLootFunctions.WARG_FUR_TYPE;
	}

	@Override
	public ItemStack run(ItemStack stack, LootContext context) {
		if (stack.getItem() == LOTRItems.FUR.get()) {
			Entity entity = context.getParamOrNull(LootParameters.THIS_ENTITY);
			if (entity != null) {
				if(entity instanceof WargEntity) {
					WargEntity warg = (WargEntity) entity;
					int count = stack.getCount();
					if(warg.getWargType().getId()==1) {
						stack = new ItemStack(ExtendedItems.GRAY_FUR.get());
					}else if(warg.getWargType().getId()==2) {
						stack = new ItemStack(ExtendedItems.BLACK_FUR.get());
					}else if(warg.getWargType().getId()==3) {
						stack = new ItemStack(ExtendedItems.SILVER_FUR.get());
					}else if(warg.getWargType().getId()==4) {
						stack = new ItemStack(ExtendedItems.WHITE_FUR.get());
					}
					stack.setCount(count);
					return stack;
				}
			}
		}
		if (stack.getItem() == ExtendedItems.BROWN_WARG_HELMET.get()) {
			Entity entity = context.getParamOrNull(LootParameters.THIS_ENTITY);
			if (entity != null) {
				if(entity instanceof WargEntity) {
					WargEntity warg = (WargEntity) entity;
					int count = stack.getCount();
					if(warg.getWargType().getId()==1) {
						stack = new ItemStack(ExtendedItems.GRAY_WARG_HELMET.get());
					}else if(warg.getWargType().getId()==2) {
						stack = new ItemStack(ExtendedItems.BLACK_WARG_HELMET.get());
					}else if(warg.getWargType().getId()==3) {
						stack = new ItemStack(ExtendedItems.SILVER_WARG_HELMET.get());
					}else if(warg.getWargType().getId()==4) {
						stack = new ItemStack(ExtendedItems.WHITE_WARG_HELMET.get());
					}
					stack.setCount(count);
					return stack;
				}
			}
		}
		return stack;
	}

	public static Builder<?> setWargFurTypeBuilder() {
		return simpleBuilder(conditions -> new ExtendedSetWargFurType(conditions));
	}

	public static class Serializer extends net.minecraft.loot.LootFunction.Serializer<ExtendedSetWargFurType>  {
		@Override
		public ExtendedSetWargFurType deserialize(JsonObject object, JsonDeserializationContext context, ILootCondition[] conditions) {
			return new ExtendedSetWargFurType(conditions);
		}

		@Override
		public void serialize(JsonObject object, ExtendedSetWargFurType function, JsonSerializationContext context) {
			super.serialize(object, function, context);
		}
	}
}
