package com.github.maximuslotro.lotrrextended.common.signprocessors;

import java.util.Random;

import lotr.common.entity.npc.data.name.NameBankManager;
import net.minecraft.util.ResourceLocation;

public class NameBankTextEntry extends BasicTextEntry {

	ResourceLocation nameBank;
	
	public NameBankTextEntry(ResourceLocation nameBank) {
		this.nameBank = nameBank;
	}

	@Override
	public String getText(Random rand) {
		return NameBankManager.INSTANCE.fetchLoadedNameBank(nameBank).getRandomName(rand);
	}

}
