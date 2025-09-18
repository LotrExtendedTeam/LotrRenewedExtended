package com.github.maximuslotro.lotrrextended.common.datagen;

import com.github.maximuslotro.lotrrextended.common.loot.modifiers.EnchantmentedInMEDisabler;
import com.github.maximuslotro.lotrrextended.common.loot.modifiers.ExtendedGlobalLootModifiers;

import net.minecraft.data.DataGenerator;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;

public class ExtendedGlobalLootModifierGenerator extends GlobalLootModifierProvider{

	public ExtendedGlobalLootModifierGenerator(DataGenerator gen, String modid) {
		super(gen, modid);
	}

	@Override
	protected void start() {
		//add("warg_fur_colorizer", ExtendedGlobalLootModifiers.WARG_FUR.get(), new WargFurColorizer(new ILootCondition[0]));
		add("me_enchantment_disabler", ExtendedGlobalLootModifiers.ENCHANTMENT_DISABLER.get(), new EnchantmentedInMEDisabler(new ILootCondition[0]));
	}

	@Override
	public String getName() {
		return "Renewed Extended Global Loot Modifier Provider";
	}
}
