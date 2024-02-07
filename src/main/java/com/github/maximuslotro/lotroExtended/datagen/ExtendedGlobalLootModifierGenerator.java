package com.github.maximuslotro.lotroExtended.datagen;


import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.GlobalLootModifierProvider;

public class ExtendedGlobalLootModifierGenerator extends GlobalLootModifierProvider{

	public ExtendedGlobalLootModifierGenerator(DataGenerator gen, String modid) {
		super(gen, modid);
	}

	@Override
	protected void start() {
		//add("warg_fur_colorizer", ExtendedLootModifiers.WARG_FUR.get(), new WargFurColorizer(new ILootCondition[0]));
	}

	@Override
	public String getName() {
		return "Renewed Extended Global Loot Modifier Provider";
	}
}
