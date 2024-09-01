package com.github.maximuslotro.lotrrextended.mixins.lotr.common.block;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import lotr.common.LOTRLog;
import lotr.common.block.SignSetupHelper;
import net.minecraft.block.Block;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ForgeRegistries;

@Mixin(SignSetupHelper.class)
public class MixinSignSetupHelperJavaPatch {
	
	@Shadow(remap=false)
	private static List<Block> preparedSignBlocks;
	
	/**
	 * @author Maximuslotro
	 * @reason Move code to Access Transformers for Java 9+ systems
	 */
	@Overwrite(remap=false)
	public static void register(RegistryEvent.Register<Block> event) {
		if (event.getRegistry() == ForgeRegistries.BLOCKS) {
			TileEntityType<SignTileEntity> signType = TileEntityType.SIGN;
			Set<Block> validBlocks = signType.validBlocks;
			Set<Block> newSet = new HashSet<>(validBlocks);
			newSet.addAll(preparedSignBlocks);
			signType.validBlocks = newSet;
			LOTRLog.info("Auto-registered %d sign blocks", preparedSignBlocks.size());
		}
	}
}
