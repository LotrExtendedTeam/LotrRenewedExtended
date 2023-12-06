package lotr.common.loot.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;

import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootFunction;
import net.minecraft.loot.LootFunctionType;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.JSONUtils;

public class SetWargFurType extends LootFunction {
	private final float chance;

	private SetWargFurType(ILootCondition[] conditions, float chance) {
		super(conditions);
		this.chance = chance;
	}

	// $FF: synthetic method
	SetWargFurType(ILootCondition[] x0, float x1, Object x2) {
		this(x0, x1);
	}

	@Override
	public LootFunctionType getType() {
		return ExtendedLootFunctions.WARG_FUR_TYPE;
	}

	@Override
	public ItemStack run(ItemStack stack, LootContext context) {
//		if (context.getRandom().nextFloat() < chance && stack.getItem() instanceof PouchItem) {
//			Entity entity = context.getParamOrNull(LootParameters.THIS_ENTITY);
//			if (entity != null) {
//				Faction faction = EntityFactionHelper.getFaction(entity);
//				if (faction.isPlayableAlignmentFaction()) {
//					PouchItem.setPouchDyedByFaction(stack, faction);
//				}
//			}
//		}

		return stack;
	}

	public static Builder<?> setPouchColorFromEntityFactionBuilder(float chance) {
		return simpleBuilder(conditions -> new SetWargFurType(conditions, chance));
	}

	public static class Serializer extends net.minecraft.loot.LootFunction.Serializer<SetWargFurType>  {
		@Override
		public SetWargFurType deserialize(JsonObject object, JsonDeserializationContext context, ILootCondition[] conditions) {
			float chance = JSONUtils.getAsFloat(object, "chance", 1.0F);
			return new SetWargFurType(conditions, chance);
		}

		public void serialize(JsonObject object, SetWargFurType function, JsonSerializationContext context) {
			super.serialize(object, function, context);
			object.addProperty("chance", function.chance);
		}
	}
}
