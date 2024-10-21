package com.github.maximuslotro.lotrrextended.common.bannerprotection;

import java.util.List;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;
import com.github.maximuslotro.lotrrextended.common.entity.special.ExtendedBannerEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedMEBannerTileEntity;
import com.github.maximuslotro.lotrrextended.common.utils.AxisAlignedBBUtils;

import lotr.common.entity.npc.data.NPCEntitySettingsManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

public class ExtendedBannerProtectionManager {
	public static boolean isBannerProtected(World level, int pX, int pY, int pZ, SingleProtectionInstance protInstance) {
		if(!ExtendedServerConfig.enableBannerProtection.get()) {
			return false;
		}
		AxisAlignedBB bannerScan = AxisAlignedBBUtils.makeCroppedBB(pX, pY, pZ).inflate(64.0D);
		if(protInstance.extraScanArea>0) {
			bannerScan = bannerScan.inflate(protInstance.extraScanArea);
		}
		List<ExtendedBannerEntity> bannersInArea = level.getEntitiesOfClass(ExtendedBannerEntity.class, bannerScan, ExtendedBannerEntity::isValid);
		if(bannersInArea.isEmpty()) {
			return false;
		}
		AxisAlignedBB disputedArea = AxisAlignedBBUtils.makeCroppedBB(pX, pY, pZ);
		if(protInstance.extraDisputedArea>0) {
			disputedArea = disputedArea.inflate(protInstance.extraDisputedArea);
		}
		for(ExtendedBannerEntity banner : bannersInArea) {
			if(banner.getProtectionRange()>0 && banner.getProtectionArea().intersects(disputedArea)) {
				boolean isProtected = protInstance.canProtect(banner.getConnectedTileEntity());
				if(isProtected) {
					return true;
				}
			}
		}
		//TODO send feedback to the players
		return false;
	}

	public abstract static class SingleProtectionInstance {
		protected int extraScanArea = 0;
		protected int extraDisputedArea = 0;

		public void setExtraScanArea(int extraScanArea) {
			this.extraScanArea = extraScanArea;
		}

		public void setExtraDisputedArea(int extraDisputedArea) {
			this.extraDisputedArea = extraDisputedArea;
		}

		public abstract boolean canProtect(ExtendedMEBannerTileEntity bannerInstance);
		
		public class PlayerProtection extends SingleProtectionInstance{
			final PlayerEntity offendingPlayer;

			public PlayerProtection(PlayerEntity offendingPlayer) {
				this.offendingPlayer = offendingPlayer;
			}

			@Override
			//TODO run player spicific logic
			public boolean canProtect(ExtendedMEBannerTileEntity bannerInstance) {
				return false;
			}
		}

		public class EntityProtection extends SingleProtectionInstance{
			final LivingEntity offendingEntity;

			public EntityProtection(LivingEntity offendingEntity) {
				this.offendingEntity = offendingEntity;
			}
			@Override
			public boolean canProtect(ExtendedMEBannerTileEntity bannerInstance) {
				if(offendingEntity instanceof PlayerEntity) {
					return new SingleProtectionInstance.PlayerProtection((PlayerEntity)offendingEntity).canProtect(bannerInstance);
				}
				if(bannerInstance.getFactionProtcting().resolveFaction(bannerInstance.getLevel()).isPresent()) {
					if(bannerInstance.getFactionProtcting().resolveFaction(bannerInstance.getLevel()).get().isBadRelation(NPCEntitySettingsManager.getEntityTypeFaction(offendingEntity))) {
						return true;
					}
				}else {
					ExtendedLog.error("Banner tried resolving a faction that does not exist! "+bannerInstance.getFactionProtcting().getNameString());
				}
				return false;
			}
		}

		public class TNTProtection extends SingleProtectionInstance{
			final TNTEntity offendingEntity;

			public TNTProtection(TNTEntity offendingEntity) {
				this.offendingEntity = offendingEntity;
			}

			@Override
			public boolean canProtect(ExtendedMEBannerTileEntity bannerInstance) {
				LivingEntity offender = offendingEntity.getOwner();
				if(offender==null) {
					return true;
				}
				if(offender instanceof PlayerEntity) {
					return new SingleProtectionInstance.PlayerProtection((PlayerEntity)offender).canProtect(bannerInstance);
				}
				if(offender instanceof LivingEntity) {
					return new SingleProtectionInstance.EntityProtection((LivingEntity)offender).canProtect(bannerInstance);
				}
				return false;
			}
		}

		public class LaunchedProtection extends SingleProtectionInstance{
			final ProjectileEntity offendingEntity;

			public LaunchedProtection(ProjectileEntity offendingEntity) {
				this.offendingEntity = offendingEntity;
			}

			@Override
			public boolean canProtect(ExtendedMEBannerTileEntity bannerInstance) {
				Entity offender = offendingEntity.getOwner();
				if(offender==null) {
					return true;
				}
				if(offender instanceof PlayerEntity) {
					return new SingleProtectionInstance.PlayerProtection((PlayerEntity)offender).canProtect(bannerInstance);
				}
				if(offender instanceof LivingEntity) {
					return new SingleProtectionInstance.EntityProtection((LivingEntity)offender).canProtect(bannerInstance);
				}
				return false;
			}
		}
	}
}
