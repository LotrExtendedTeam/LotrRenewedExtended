package com.github.maximuslotro.lotrrextended.common.loot.functions;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedItems;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;

import lotr.common.entity.npc.WargEntity;
import lotr.common.init.LOTRItems;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootFunction;
import net.minecraft.loot.LootFunctionType;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.ILootCondition;

/**
 * A custom loot function that replaces generic fur or helmet items
 * with a variant corresponding to the specific {@link WargEntity}
 * that dropped the loot.
 * <p>
 * When applied during loot generation:
 * <ul>
 *   <li>If the dropped item is {@link LOTRItems#FUR}, it will be converted
 *       into the fur type matching the Warg's variant (gray, black, silver, or white).</li>
 *   <li>If the dropped item is a {@link ExtendedItems#BROWN_WARG_HELMET},
 *       it will similarly be converted into the correct helmet variant
 *       for the Warg's type.</li>
 *   <li>If the entity is not a {@link WargEntity}, the item remains unchanged.</li>
 * </ul>
 * The stack size of the original item is preserved in the converted item.
 * </p>
 */
public class ExtendedSetWargFurType extends LootFunction {
	/**
	 * Creates a new Warg fur type setter with the given loot conditions.
	 *
	 * @param conditions loot conditions that must be satisfied for this function to run
	 */
	private ExtendedSetWargFurType(ILootCondition[] conditions) {
		super(conditions);
	}
	/**
	 * Gets the loot function type for this class.
	 *
	 * @return the {@link LootFunctionType} registered as {@link ExtendedLootFunctions#WARG_FUR_TYPE}
	 */
	@Override
	public LootFunctionType getType() {
		return ExtendedLootFunctions.WARG_FUR_TYPE;
	}
	/**
	 * Applies this loot function to the given {@link ItemStack}.
	 * <p>
	 * If the item is a generic fur or helmet and the associated entity in the loot
	 * context is a {@link WargEntity}, the item will be replaced with the fur or
	 * helmet variant corresponding to the Warg's type ID:
	 * <ul>
	 *   <li>ID 1 → Gray variant</li>
	 *   <li>ID 2 → Black variant</li>
	 *   <li>ID 3 → Silver variant</li>
	 *   <li>ID 4 → White variant</li>
	 * </ul>
	 * If no matching Warg type is found, or the item is not one of the supported
	 * types, the original item stack is returned unchanged.
	 * </p>
	 *
	 * @param stack   the {@link ItemStack} to modify
	 * @param context the current loot context, used to obtain the entity
	 * @return the modified {@link ItemStack}, or the original if no changes apply
	 */
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
	/**
	 * Creates a builder for {@link ExtendedSetWargFurType}, used to register
	 * this function in loot table JSON definitions.
	 *
	 * @return a new builder for {@link ExtendedSetWargFurType}
	 */
	public static Builder<?> setWargFurTypeBuilder() {
		return simpleBuilder(ExtendedSetWargFurType::new);
	}
	/**
	 * Handles JSON serialization and deserialization for
	 * {@link ExtendedSetWargFurType}.
	 */
	public static class Serializer extends LootFunction.Serializer<ExtendedSetWargFurType>  {
		/**
		 * Deserializes a {@link ExtendedSetWargFurType} from JSON.
		 *
		 * @param object     the JSON object defining this function
		 * @param context    the deserialization context
		 * @param conditions any conditions attached to the loot function
		 * @return a new {@link ExtendedSetWargFurType} instance
		 */
		@Override
		public ExtendedSetWargFurType deserialize(JsonObject object, JsonDeserializationContext context, ILootCondition[] conditions) {
			return new ExtendedSetWargFurType(conditions);
		}
		/**
		 * Serializes this {@link ExtendedSetWargFurType} into JSON.
		 *
		 * @param object   the JSON object to populate
		 * @param function the function instance to serialize
		 * @param context  the serialization context
		 */
		@Override
		public void serialize(JsonObject object, ExtendedSetWargFurType function, JsonSerializationContext context) {
			super.serialize(object, function, context);
		}
	}
}