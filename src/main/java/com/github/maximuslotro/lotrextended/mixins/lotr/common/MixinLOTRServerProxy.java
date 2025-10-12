package com.github.maximuslotro.lotrextended.mixins.lotr.common;

import java.io.File;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import lotr.common.LOTRServerProxy;

@Mixin(LOTRServerProxy.class)
public class MixinLOTRServerProxy {

	/**
	 * @author Maximuslotro
	 * @reason Fix NullPointerException
	 * @return File location of server directory
	 * 
	 * 
	 */
	@Overwrite(remap = false)
	public File getGameRootDirectory() {
		return new File(".");
	}
}
