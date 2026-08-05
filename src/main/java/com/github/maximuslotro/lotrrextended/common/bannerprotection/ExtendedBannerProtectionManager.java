package com.github.maximuslotro.lotrrextended.common.bannerprotection;

import java.util.List;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;
import com.github.maximuslotro.lotrrextended.common.entity.special.ExtendedBannerEntity;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedBannerPermissions;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedMEBannerTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedMEBannerTileEntity.ProtectionEntry;

import lotr.common.data.LOTRLevelData;
import lotr.common.entity.npc.data.NPCEntitySettingsManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Util;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IWorld;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public class ExtendedBannerProtectionManager {
	public enum ExtendedProtReturn{
		ALLOWED(true), 
		DENIED(false), 
		NOBANNER(true);

		private final boolean isAllowed;

		private ExtendedProtReturn(boolean allowed){
			isAllowed = allowed;
		}

		public boolean isAllowed() {
			return isAllowed;
		}

		public boolean isNotAllowed() {
			return !isAllowed;
		}
	}
	public static ExtendedProtReturn getProtectionStatus(IWorld iWorld, BlockPos position, ProtectionInstance protInstance) {
		return getProtectionStatus(iWorld, position, protInstance, false, null);
	}

	public static ExtendedProtReturn getProtectionStatus(IWorld iWorld, BlockPos position, ProtectionInstance protInstance, boolean doLog, String source) {
		if(!ExtendedServerConfig.enableBannerProtection.get()) {
			return ExtendedProtReturn.ALLOWED;
		}
		ExtendedProtReturn isInteractionDenied = getActualProtectionStatus(iWorld, position, protInstance);
		if(doLog && isInteractionDenied!=ExtendedProtReturn.NOBANNER && ExtendedServerConfig.enableExtendedLogging.get()) {
			ExtendedLog.info("BannerInstance[{},PE=[{}], rejected={}, source:{}]", position.toString(), protInstance.toString(), isInteractionDenied, source);
		}
		return isInteractionDenied;
	}

	private static ExtendedProtReturn getActualProtectionStatus(IWorld iWorld, BlockPos position, ProtectionInstance protInstance) {
		AxisAlignedBB bannerScan = new AxisAlignedBB(position).inflate(64.0D);
		List<ExtendedBannerEntity> bannersInArea = iWorld.getEntitiesOfClass(ExtendedBannerEntity.class, bannerScan, ExtendedBannerEntity::isValid);
		if(bannersInArea.isEmpty()) {
			return ExtendedProtReturn.NOBANNER;
		}
		AxisAlignedBB disputedArea = new AxisAlignedBB(position);
		for(ExtendedBannerEntity banner : bannersInArea) {
			if(banner.getProtectionRange()>0 && banner.getProtectionArea().intersects(disputedArea)) {
				boolean denyEdits = protInstance.isProtected(banner.getConnectedTileEntity());
				if(denyEdits) {
					return ExtendedProtReturn.DENIED;
				}
			}
		}
		return ExtendedProtReturn.ALLOWED;
	}

	public abstract static class ProtectionInstance {

		public abstract boolean isProtected(ExtendedMEBannerTileEntity bannerInstance);

		@Override
		public String toString() {
			return getStringData();
		}

		public abstract String getStringData();

		public static class PlayerProtection extends ProtectionInstance {
			private final PlayerEntity offendingPlayer;
			private ExtendedBannerPermissions queryingPermission = ExtendedBannerPermissions.EVERYTHING;

			public PlayerProtection(PlayerEntity offendingPlayer) {
				this.offendingPlayer = offendingPlayer;
			}

			public PlayerProtection(PlayerEntity offendingPlayer, ExtendedBannerPermissions queryingPermission) {
				this(offendingPlayer);
				this.queryingPermission = queryingPermission;
			}

			@Override
			public boolean isProtected(ExtendedMEBannerTileEntity bannerInstance) {
				if(offendingPlayer==null) {
					return false;
				}
				if(bannerInstance.isCustomProtecting()) {
					if(ServerLifecycleHooks.getCurrentServer().getPlayerList().isOp(offendingPlayer.getGameProfile()) || bannerInstance.hasGlobalPerm(queryingPermission)) {
						return false;
					}
					if(bannerInstance.getProtectionOverrideMessage().isEmpty()) {
						tellPlayerYSNP(new StringTextComponent("The Server"));
					} else {
						tellPlayerYSNP(new StringTextComponent(bannerInstance.getProtectionOverrideMessage()));
					}
					return true;
				}
				if(offendingPlayer.abilities.instabuild) {
					return false;
				}
				if(bannerInstance.isFactionProtecting()) {
					if(offendingPlayer.getUUID().equals(bannerInstance.getPlacedPlayerUUID()) || bannerInstance.hasGlobalPerm(queryingPermission)) {
						return false;
					}
					float alignment = LOTRLevelData.serverInstance().getData(offendingPlayer).getAlignmentData().getAlignment(bannerInstance.getFactionProtcting());
					if(alignment<bannerInstance.getAlignmentLevel() || !bannerInstance.hasFactionPerm(queryingPermission)) {
						if(bannerInstance.getFactionProtcting().resolveFaction(bannerInstance.getLevel()).isPresent()) {
							tellPlayerYSNP(bannerInstance.getFactionProtcting().resolveFaction(bannerInstance.getLevel()).get().getColoredDisplayName());
						}else {
							tellPlayerYSNP(new StringTextComponent("UNKNOWN"));
						}
						return true;
					}
					return false;
				}
				if(bannerInstance.isWhitelistProtecting()) {
					if(offendingPlayer.getUUID().equals(bannerInstance.getPlacedPlayerUUID()) || bannerInstance.hasGlobalPerm(queryingPermission)) {
						return false;
					}
					boolean isWhitelisted=false;
					List<ProtectionEntry> whitelist = bannerInstance.getProtectionWhitelist();
					for(ProtectionEntry entry : whitelist) {
						// Check which has better performance
						/*
						if(entry!=null&&entry.hasPermission(queryingPermission)) {
							if(entry.getProfile().isPlayerContained(offendingPlayer)) {
								isWhitelisted = true;
							}
						}*/
						if(entry!=null&&entry.getProfile().isPlayerContained(offendingPlayer)) {
							if(entry.hasPermission(queryingPermission)) {
								isWhitelisted = true;
								break;
							}
						}
					}
					if(!isWhitelisted) {
						tellPlayerYSNP(new StringTextComponent(bannerInstance.getPlacedPlayerName()));
						return true;
					}
				}
				// Since banner must at this point be under NONE protection, return false
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

		public static class EntityProtection extends ProtectionInstance {
			final Entity offendingEntity;

			public EntityProtection(Entity offendingEntity) {
				this.offendingEntity = offendingEntity;
			}
			@Override
			public boolean isProtected(ExtendedMEBannerTileEntity bannerInstance) {
				if(offendingEntity instanceof PlayerEntity) {
					return new ProtectionInstance.PlayerProtection((PlayerEntity)offendingEntity).isProtected(bannerInstance);
				}
				if(bannerInstance.getFactionProtcting().resolveFaction(bannerInstance.getLevel()).isPresent()) {
					if(bannerInstance.getFactionProtcting().resolveFaction(bannerInstance.getLevel()).get().isBadRelation(NPCEntitySettingsManager.getEntityTypeFaction(offendingEntity))) {
						return true;
					}
					return false;
				}else {
					ExtendedLog.error("Banner tried resolving a faction that does not exist! "+bannerInstance.getFactionProtcting().getNameString());
				}
				return true;
			}
			@Override
			public String getStringData() {
				return "EntityProtection:UUID"+offendingEntity.getStringUUID()+":Type_"+offendingEntity.getType().getDescriptionId();
			}
		}

		public static class TNTProtection extends ProtectionInstance {
			final TNTEntity offendingEntity;

			public TNTProtection(TNTEntity offendingEntity) {
				this.offendingEntity = offendingEntity;
			}

			@Override
			public boolean isProtected(ExtendedMEBannerTileEntity bannerInstance) {
				LivingEntity offender = offendingEntity.getOwner();
				if(offender instanceof PlayerEntity) {
					return new ProtectionInstance.PlayerProtection((PlayerEntity)offender).isProtected(bannerInstance);
				}
				return true;
			}

			@Override
			public String getStringData() {
				return "TNTProtection:UUID"+offendingEntity.getStringUUID()+":Loc_"+offendingEntity.blockPosition().toString();
			}
		}

		public static class LaunchedProtection extends ProtectionInstance{
			final ProjectileEntity offendingEntity;

			public LaunchedProtection(ProjectileEntity offendingEntity) {
				this.offendingEntity = offendingEntity;
			}

			@Override
			public boolean isProtected(ExtendedMEBannerTileEntity bannerInstance) {
				Entity offender = offendingEntity.getOwner();
				if(offender instanceof PlayerEntity) {
					return new ProtectionInstance.PlayerProtection((PlayerEntity)offender).isProtected(bannerInstance);
				}
				return true;
			}

			@Override
			public String getStringData() {
				return "LaunchedProtection:Type_"+offendingEntity.getType().getDescriptionId()+ (offendingEntity.getOwner()!=null ? ":OwnerType_"+offendingEntity.getOwner().getType().getDescriptionId() : "");
			}
		}

		public static class DamageSourceProtection extends ProtectionInstance {
			final DamageSource source;

			public DamageSourceProtection(DamageSource source) {
				this.source = source;
			}

			@Override
			public boolean isProtected(ExtendedMEBannerTileEntity bannerInstance) {
				Entity offender = source.getEntity();
				if(offender instanceof PlayerEntity) {
					return new ProtectionInstance.PlayerProtection((PlayerEntity)offender).isProtected(bannerInstance);
				}
				if(offender instanceof ProjectileEntity) {
					return new ProtectionInstance.LaunchedProtection((ProjectileEntity) offender).isProtected(bannerInstance);
				}
				return true;
			}

			@Override
			public String getStringData() {
				return "DamageSourceProtection:Type_"+source.toString();
			}
		}

		public static class AnyProtection extends ProtectionInstance {
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