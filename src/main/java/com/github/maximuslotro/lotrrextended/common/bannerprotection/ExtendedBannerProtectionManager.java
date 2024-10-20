package com.github.maximuslotro.lotrrextended.common.bannerprotection;

import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;
import com.github.maximuslotro.lotrrextended.common.tileentity.banner.ExtendedMEBannerTileEntity;

import net.minecraft.world.World;

public class ExtendedBannerProtectionManager {
	public static boolean isBannerProtected(World level, int pX, int pY, int pZ, SingleProtectionInstance protInstance) {
		if(!ExtendedServerConfig.enableBannerProtection.get()) {
			return false;
		}
		//TODO Scan for banners in area
		//TODO check if banner isValid and getProtectionRange is greater than zero
		//TODO check if protInstance canProtect the selected area
		//TODO send feedback to the players
		return false;
	}

	public abstract static class SingleProtectionInstance {
		public abstract boolean canProtect(ExtendedMEBannerTileEntity bannerInstance);
	}
}
