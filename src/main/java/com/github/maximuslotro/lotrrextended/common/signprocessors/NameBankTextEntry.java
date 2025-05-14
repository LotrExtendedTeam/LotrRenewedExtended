package com.github.maximuslotro.lotrrextended.common.signprocessors;

import java.util.Random;

import lotr.common.entity.npc.data.name.NameBankManager;
import net.minecraft.util.ResourceLocation;

public class NameBankTextEntry extends BasicTextEntry {
	private ResourceLocation nameBank;
	private boolean shouldAddApostrophe;

	public NameBankTextEntry(String pNameBank, boolean pShouldAddApostrophe) {
		this.nameBank = new ResourceLocation(pNameBank);
		this.shouldAddApostrophe = pShouldAddApostrophe;
	}

	@Override
	public String getText(Random pRand) {
		return NameBankManager.INSTANCE.fetchLoadedNameBank(nameBank).getRandomName(pRand) + (shouldAddApostrophe ? "'s" : "");
	}

}
