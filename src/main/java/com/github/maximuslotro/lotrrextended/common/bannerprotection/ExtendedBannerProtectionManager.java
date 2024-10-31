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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IWorld;
import net.minecraftforge.common.util.FakePlayer;

public class ExtendedBannerProtectionManager {
	public static boolean isBannerProtected(IWorld iWorld, BlockPos pos, SingleProtectionInstance protInstance, boolean doLog) {
		return isBannerProtected(iWorld, MathHelper.floor(pos.getX()), MathHelper.floor(pos.getY()), MathHelper.floor(pos.getZ()), protInstance, doLog);
	}

	public static boolean isBannerProtected(IWorld iWorld, int pX, int pY, int pZ, SingleProtectionInstance protInstance, boolean doLog) {
		if(!ExtendedServerConfig.enableBannerProtection.get()) {
			return false;
		}
		boolean isInteractionDenied = isBannerProtectedInner(iWorld, pX, pY, pZ, protInstance);
		if(doLog && ExtendedServerConfig.enableExtendedLogging.get()) {
			ExtendedLog.info("BannerInstance[x="+pX+",y="+pY+",z="+pZ+",PE=["+protInstance.toString()+"], rejected="+isInteractionDenied+"]");
		}
		return isInteractionDenied;
	}

	private static boolean isBannerProtectedInner(IWorld iWorld, int pX, int pY, int pZ, SingleProtectionInstance protInstance) {
		AxisAlignedBB bannerScan = AxisAlignedBBUtils.makeCroppedBB(pX, pY, pZ).inflate(64.0D);
		if(protInstance.extraScanArea>0) {
			bannerScan = bannerScan.inflate(protInstance.extraScanArea);
		}
		List<ExtendedBannerEntity> bannersInArea = iWorld.getEntitiesOfClass(ExtendedBannerEntity.class, bannerScan, ExtendedBannerEntity::isValid);
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

		@Override
		public String toString() {
			return getStringData();
		}

		public abstract String getStringData();

		public static class PlayerProtection extends SingleProtectionInstance{
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
				if(offendingPlayer==null || offendingPlayer.abilities.instabuild) {
					return false;
				}
				if(bannerInstance.isFactionProtecting()) {
					if(bannerInstance.hasDefaultPerm(queryingPermission)) {
						return false;
					}
					float alignment = LOTRLevelData.serverInstance().getData(offendingPlayer).getAlignmentData().getAlignment(bannerInstance.getFactionProtcting());
					if(alignment<bannerInstance.getAlignmentLevel()) {
						if(bannerInstance.getFactionProtcting().resolveFaction(bannerInstance.getLevel()).isPresent()) {
							tellPlayerYSNP(bannerInstance.getFactionProtcting().resolveFaction(bannerInstance.getLevel()).get().getColoredDisplayName());
						}else {
							tellPlayerYSNP(new StringTextComponent("UNKNOWN"));
						}
						return true;
					}
				}
				if(bannerInstance.isWhitelistProtecting()) {
					if(offendingPlayer.getUUID().equals(bannerInstance.getPlacedPlayerUUID()) || bannerInstance.hasDefaultPerm(queryingPermission)) {
						return false;
					}
					boolean isWhitelisted=false;
					List<ProtectionEntry> whitelist = bannerInstance.getProtectionWhitelist();
					for(ProtectionEntry entry : whitelist) {
						// Check which has better performance
//						if(entry!=null&&entry.hasPermission(queryingPermission)) {
//							if(entry.getProfile().isPlayerContained(offendingPlayer)) {
//								isWhitelisted = true;
//							}
//						}
						if(entry!=null&&entry.getProfile().isPlayerContained(offendingPlayer)) {
							if(entry.hasPermission(queryingPermission)) {
								isWhitelisted = true;
							}
						}
					}
					if(!isWhitelisted) {
						tellPlayerYSNP(new StringTextComponent(bannerInstance.getPlacedPlayerName()));
						return true;
					}
				}
				return false;
			}

			public void tellPlayerYSNP(IFormattableTextComponent iFormattableTextComponent) {
				if(offendingPlayer instanceof FakePlayer) {
					return;
				}
				offendingPlayer.inventoryMenu.broadcastChanges();
				offendingPlayer.sendMessage(new TranslationTextComponent("message.lotrextended.banner.land_protected", iFormattableTextComponent), Util.NIL_UUID);
			}

			@Override
			public String getStringData() {
				return "PlayerProtection:UUID_"+offendingPlayer.getStringUUID()+":Name_"+offendingPlayer.getGameProfile().getName()+":Perm_"+queryingPermission.toString();
			}
		}

		public static class EntityProtection extends SingleProtectionInstance{
			final Entity offendingEntity;

			public EntityProtection(Entity offendingEntity) {
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
			@Override
			public String getStringData() {
				return "EntityProtection:UUID"+offendingEntity.getStringUUID()+":Type_"+offendingEntity.getType().getDescriptionId();
			}
		}

		public static class TNTProtection extends SingleProtectionInstance{
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

			@Override
			public String getStringData() {
				return "TNTProtection:UUID"+offendingEntity.getStringUUID()+":Loc_"+offendingEntity.blockPosition().toString();
			}
		}

		public static class LaunchedProtection extends SingleProtectionInstance{
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

			@Override
			public String getStringData() {
				return "EntityProtection:Type_"+offendingEntity.getType().getDescriptionId()+ (offendingEntity.getOwner()!=null ? ":OwnerType_"+offendingEntity.getOwner().getType().getDescriptionId() : "");
			}
		}

		public static class AnyProtection extends SingleProtectionInstance{
			@Override
			public boolean isProtected(ExtendedMEBannerTileEntity bannerInstance) {
				return true;
			}

			@Override
			public String getStringData() {
				return "AnyProtection";
			}
		}
	}
}