package com.github.maximuslotro.lotrrextended.common.datagen;

import java.util.ArrayList;
import java.util.List;

import com.github.maximuslotro.lotrrextended.common.loot.modifiers.global.EnchantmentedInMEDisabler;
import com.github.maximuslotro.lotrrextended.common.loot.modifiers.global.ExtendedGlobalLootModifiers;

import net.minecraft.data.DataGenerator;
import net.minecraft.item.Items;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.data.GlobalLootModifierProvider;

public class ExtendedGlobalLootModifierGenerator extends GlobalLootModifierProvider{

	public ExtendedGlobalLootModifierGenerator(DataGenerator gen, String modid) {
		super(gen, modid);
	}

	@Override
	protected void start() {
		//add("warg_fur_colorizer", ExtendedGlobalLootModifiers.WARG_FUR.get(), new WargFurColorizer(new ILootCondition[0]));
		List<ResourceLocation> fishingRods = new ArrayList<>();
		fishingRods.add(Items.FISHING_ROD.getRegistryName());
		add("me_enchantment_disabler", ExtendedGlobalLootModifiers.ENCHANTMENT_DISABLER.get(), new EnchantmentedInMEDisabler(new ILootCondition[0], fishingRods));
	}

	@Override
	public String getName() {
		return "Renewed Extended Global Loot Modifier Provider";
	}
}
