package com.github.maximuslotro.lotrrextended.common.block;

import java.util.HashSet;
import java.util.Set;

import com.github.maximuslotro.lotrrextended.ExtendedLog;

import net.minecraft.block.Block;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ForgeRegistries;

public class ExtendedSignTileEntitySetup {
	private static Set<Block> injectBlocks = new HashSet<>();

	public static void add(Block block) {
		injectBlocks.add(block);
	}

	public static void setup(RegistryEvent.Register<Block> event) {
		if (event.getRegistry() == ForgeRegistries.BLOCKS) {
			TileEntityType<SignTileEntity> signType = TileEntityType.SIGN;
			Set<Block> validBlocks = signType.validBlocks;
			Set<Block> newSet = new HashSet<>(validBlocks);
			newSet.addAll(injectBlocks);
			signType.validBlocks = newSet;
			ExtendedLog.info("Auto-registered {} sign blocks", injectBlocks.size());
		}
	}
}
