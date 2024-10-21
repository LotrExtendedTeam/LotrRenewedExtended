package com.github.maximuslotro.lotrrextended.common.bannerprotection;

import java.util.List;

import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;
import com.github.maximuslotro.lotrrextended.common.entity.special.ExtendedBannerEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedMEBannerTileEntity;
import com.github.maximuslotro.lotrrextended.common.utils.AxisAlignedBBUtils;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

public class ExtendedBannerProtectionManager {
	public static boolean isBannerProtected(World level, int pX, int pY, int pZ, SingleProtectionInstance protInstance) {
		if(!ExtendedServerConfig.enableBannerProtection.get()) {
			return false;
		}
		AxisAlignedBB bannerScan = AxisAlignedBBUtils.makeCroppedBB(pX, pY, pZ).inflate(64.0D);
		List<ExtendedBannerEntity> bannersInArea = level.getEntitiesOfClass(ExtendedBannerEntity.class, bannerScan, ExtendedBannerEntity::isValid);
		if(bannersInArea.isEmpty()) {
			return false;
		}
		AxisAlignedBB disputedArea = AxisAlignedBBUtils.makeCroppedBB(pX, pY, pZ);
		for(ExtendedBannerEntity banner : bannersInArea) {
			if(banner.getProtectionRange()>0 && banner.getProtectionArea().intersects(disputedArea)) {
				
			}
		}
		//TODO check if protInstance canProtect the selected area
		//TODO send feedback to the players
		return false;
	}

	public abstract static class SingleProtectionInstance {
		public abstract boolean canProtect(ExtendedMEBannerTileEntity bannerInstance);
	}
}
