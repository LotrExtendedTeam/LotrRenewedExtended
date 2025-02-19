package com.github.maximuslotro.lotrrextended.common.datagen.codex;

import java.util.function.Consumer;

import com.github.maximuslotro.lotrrextended.client.codex.entry.ExtendedBaseCodexEntry;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ExtendedClientCodexEntryGenerator extends ExtendedClientCodexProvider{

	public ExtendedClientCodexEntryGenerator(DataGenerator generatorIn, ExistingFileHelper fileHelperIn, ExtendedServerCodexEntryGenerator serverCodex) {
		super(generatorIn, fileHelperIn, serverCodex);
	}

	@Override
	   public void registerClientCodexEntries(Consumer<ExtendedBaseCodexEntry> consumer, ExistingFileHelper fileHelper) {

	}
}