package com.github.maximuslotro.lotrrextended.common.bannerprotection;

import java.util.List;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;
import com.github.maximuslotro.lotrrextended.common.entity.special.ExtendedBannerEntity;
import com.github.maximuslotro.lotrrextended.common.enums.BannerPermission;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedMEBannerTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedMEBannerTileEntity.ProtectionEntry;
import com.github.maximuslotro.lotrrextended.common.utils.AxisAlignedBBUtils;

import lotr.common.data.LOTRLevelData;
import lotr.common.entity.npc.data.NPCEntitySettingsManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.Util;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;

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
				boolean isProtected = protInstance.isProtected(banner.getConnectedTileEntity());
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

		public abstract boolean isProtected(ExtendedMEBannerTileEntity bannerInstance);

		public class PlayerProtection extends SingleProtectionInstance{
			private final PlayerEntity offendingPlayer;
			private BannerPermission queryingPermission = BannerPermission.EVERYTHING;

			public PlayerProtection(PlayerEntity offendingPlayer) {
				this.offendingPlayer = offendingPlayer;
			}

			public PlayerProtection(PlayerEntity offendingPlayer, BannerPermission queryingPermission) {
				this(offendingPlayer);
				this.queryingPermission = queryingPermission;
			}

			@Override
			public boolean isProtected(ExtendedMEBannerTileEntity bannerInstance) {
				if(offendingPlayer.abilities.instabuild) {
					return false;
				}
				if(bannerInstance.isFactionProtecting()) {
					if(bannerInstance.hasDefaultPerm(queryingPermission)) {
						return false;
					}
					float alignment = LOTRLevelData.serverInstance().getData(offendingPlayer).getAlignmentData().getAlignment(bannerInstance.getFactionProtcting());
					if(alignment<bannerInstance.getAlignmentLevel()) {
						return true;
					}
					if(bannerInstance.getFactionProtcting().resolveFaction(bannerInstance.getLevel()).isPresent()) {
						tellPlayerYSNP(bannerInstance.getFactionProtcting().resolveFaction(bannerInstance.getLevel()).get().getDisplayName());
					}
					tellPlayerYSNP(new StringTextComponent("UNKNOWN"));
				}
				if(bannerInstance.isWhitelistProtecting()) {
					if(offendingPlayer.getUUID().equals(bannerInstance.getPlacedPlayerUUID()) || bannerInstance.hasDefaultPerm(queryingPermission)) {
						return false;
					}
					boolean isWhitelisted=false;
					List<ProtectionEntry> whitelist = bannerInstance.getProtectionWhitelist();
					for(ProtectionEntry entry : whitelist) {
						if(entry!=null&&entry.hasPermission(queryingPermission)) {
							if(entry.getProfile().isPlayerContained(offendingPlayer)) {
								isWhitelisted = true;
							}
						}
					}
					if(!isWhitelisted) {
						return true;
					}
					tellPlayerYSNP(new StringTextComponent(bannerInstance.getPlacedPlayerName()));
				}
				return false;
			}

			public void tellPlayerYSNP(IFormattableTextComponent iFormattableTextComponent) {
				if(offendingPlayer instanceof FakePlayer) {
					return;
				}
				offendingPlayer.inventoryMenu.broadcastChanges();
				offendingPlayer.sendMessage(new TranslationTextComponent("gui.lotrextended.bow.speed", iFormattableTextComponent), Util.NIL_UUID);
			}
		}

		public class EntityProtection extends SingleProtectionInstance{
			final LivingEntity offendingEntity;

			public EntityProtection(LivingEntity offendingEntity) {
				this.offendingEntity = offendingEntity;
			}
			@Override
			public boolean isProtected(ExtendedMEBannerTileEntity bannerInstance) {
				if(offendingEntity instanceof PlayerEntity) {
					return new SingleProtectionInstance.PlayerProtection((PlayerEntity)offendingEntity).isProtected(bannerInstance);
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
			public boolean isProtected(ExtendedMEBannerTileEntity bannerInstance) {
				LivingEntity offender = offendingEntity.getOwner();
				if(offender==null) {
					return true;
				}
				if(offender instanceof PlayerEntity) {
					return new SingleProtectionInstance.PlayerProtection((PlayerEntity)offender).isProtected(bannerInstance);
				}
				if(offender instanceof LivingEntity) {
					return new SingleProtectionInstance.EntityProtection(offender).isProtected(bannerInstance);
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
			public boolean isProtected(ExtendedMEBannerTileEntity bannerInstance) {
				Entity offender = offendingEntity.getOwner();
				if(offender==null) {
					return true;
				}
				if(offender instanceof PlayerEntity) {
					return new SingleProtectionInstance.PlayerProtection((PlayerEntity)offender).isProtected(bannerInstance);
				}
				if(offender instanceof LivingEntity) {
					return new SingleProtectionInstance.EntityProtection((LivingEntity)offender).isProtected(bannerInstance);
				}
				return false;
			}
		}

		public class AnyProtection extends SingleProtectionInstance{
			@Override
			public boolean isProtected(ExtendedMEBannerTileEntity bannerInstance) {
				return true;
			}
		}
	}
}