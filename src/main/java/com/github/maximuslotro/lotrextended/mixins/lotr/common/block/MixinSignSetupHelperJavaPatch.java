package com.github.maximuslotro.lotrextended.mixins.lotr.common.block;

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

/**
 * Renewed registers new sign types using reflection, breaking in Java9+. This class fixes that
 * @Mixin (SignSetupHelper.class)
 * */
@Mixin(SignSetupHelper.class)
public class MixinSignSetupHelperJavaPatch {

	/**
	 * A list of Renewed/Extended sign blocks to register
	 * @Shadow (remap=false)
	 * */
	@Shadow(remap=false)
	private static List<Block> preparedSignBlocks;

	/**
	 * @author maximuslotro
	 * @reason Move code to Access Transformers for Java 9+ systems, registers new sign types
	 * @Overwrite (remap=false)
	 * @param event The Block registry event to register the new sign types for
	 */
	@Overwrite(remap=false)
	public static void register(RegistryEvent.Register<Block> event) {
		if (event.getRegistry() == ForgeRegistries.BLOCKS) {
			TileEntityType<SignTileEntity> signType = TileEntityType.SIGN;
			// Extended Start
			Set<Block> validBlocks = signType.validBlocks;
			// Extended End
			Set<Block> newSet = new HashSet<>(validBlocks);
			newSet.addAll(preparedSignBlocks);
			signType.validBlocks = newSet;
			LOTRLog.info("Auto-registered %d sign blocks", preparedSignBlocks.size());
		}
	}
}
