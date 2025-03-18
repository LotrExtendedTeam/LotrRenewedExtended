package com.github.maximuslotro.lotrrextended.common.loot.modifiers.global;

import java.util.List;

import com.google.gson.JsonObject;

import lotr.common.entity.npc.WargEntity;
import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRItems;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;

public class ExtendedWargFurColorizer extends LootModifier {

	public ExtendedWargFurColorizer(ILootCondition[] conds) {
		super(conds);
	}

	@Override
	public List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
		Entity entity = context.getParamOrNull(LootParameters.THIS_ENTITY);
		if (entity instanceof WargEntity) {
			WargEntity warg = (WargEntity) entity;
			if(warg.getWargType().getId()==0) {
				return generatedLoot;
			}
			int numFur = 0;
			for(ItemStack stack : generatedLoot) {
	            if(stack.getItem() == LOTRItems.FUR.get())
	            	numFur+=stack.getCount();
	        }
			generatedLoot.removeIf(x -> x.getItem() == LOTRItems.FUR.get());
			if(warg.getWargType().getId()==1) {
				generatedLoot.add(new ItemStack(ExtendedItems.GRAY_FUR.get(), numFur));
			}else if(warg.getWargType().getId()==2) {
				generatedLoot.add(new ItemStack(ExtendedItems.BLACK_FUR.get(), numFur));
			}else if(warg.getWargType().getId()==3) {
				generatedLoot.add(new ItemStack(ExtendedItems.SILVER_FUR.get(), numFur));
			}else if(warg.getWargType().getId()==4) {
				generatedLoot.add(new ItemStack(ExtendedItems.WHITE_FUR.get(), numFur));
			}
		}
		return generatedLoot;
	}

	public static class Serializer extends GlobalLootModifierSerializer<ExtendedWargFurColorizer> {
		@Override
		public ExtendedWargFurColorizer read(ResourceLocation name, JsonObject obj, ILootCondition[] conditions) {
			return new ExtendedWargFurColorizer(conditions);
		}

		@Override
		public JsonObject write(ExtendedWargFurColorizer instance) {
			return this.makeConditions(new ILootCondition[0]);
		}
	}
}
