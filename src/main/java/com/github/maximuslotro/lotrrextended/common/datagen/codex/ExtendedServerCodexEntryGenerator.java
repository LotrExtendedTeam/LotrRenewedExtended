package com.github.maximuslotro.lotrrextended.common.datagen.codex;

import java.util.Map.Entry;
import java.util.function.Consumer;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.codex.ExtendedCodexEntry;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedCodexEntryType;

import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.potion.Effect;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class ExtendedServerCodexEntryGenerator extends ExtendedServerCodexProvider{

	public ExtendedServerCodexEntryGenerator(DataGenerator generatorIn, ExistingFileHelper fileHelperIn) {
		super(generatorIn, fileHelperIn);
	}

	@Override
	public void registerServerCodexEntries(Consumer<ExtendedCodexEntry> consumer, ExistingFileHelper fileHelper) {
		for(Entry<RegistryKey<Biome>, Biome> str : ForgeRegistries.BIOMES.getEntries()) {
			make(ExtendedCodexEntryType.BIOME).save(consumer, str.getKey().location());
		}
		for(Entry<RegistryKey<Block>, Block> str : ForgeRegistries.BLOCKS.getEntries()) {
			make(ExtendedCodexEntryType.BLOCK).save(consumer, str.getKey().location());
		}
		for(Entry<RegistryKey<Effect>, Effect> str : ForgeRegistries.POTIONS.getEntries()) {
			make(ExtendedCodexEntryType.EFFECT).save(consumer, str.getKey().location());
		}
		for(Entry<RegistryKey<EntityType<?>>, EntityType<?>> str : ForgeRegistries.ENTITIES.getEntries()) {
			make(ExtendedCodexEntryType.ENTITYTYPE).save(consumer, str.getKey().location());
		}
		for(Entry<RegistryKey<Item>, Item> str : ForgeRegistries.ITEMS.getEntries()) {
			make(ExtendedCodexEntryType.ITEM).save(consumer, str.getKey().location());
		}
		for(Entry<RegistryKey<Structure<?>>, Structure<?>> str : ForgeRegistries.STRUCTURE_FEATURES.getEntries()) {
			make(ExtendedCodexEntryType.STRUCTURE).save(consumer, str.getKey().location());
		}
	}
}