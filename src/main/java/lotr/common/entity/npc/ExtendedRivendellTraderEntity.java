package lotr.common.entity.npc;

import java.util.Optional;
import java.util.UUID;

import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;

import lotr.common.entity.npc.ai.goal.*;
import lotr.common.util.ExtendedInteractableHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.OpenDoorGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.world.World;

public abstract class ExtendedRivendellTraderEntity extends RivendellElfEntity implements ExtendedNPCEntitySettings, ExtendedMerchantEntity {

	int lastTickVerifiedPlayerTradingWithIsOnline = 0;

	private static final DataParameter<Optional<UUID>> PLAYER_TRADING_WITH = EntityDataManager.defineId(ExtendedRivendellTraderEntity.class, DataSerializers.OPTIONAL_UUID);

	public ExtendedRivendellTraderEntity(EntityType<? extends ExtendedRivendellTraderEntity> type, World w) {
		super(type, w);
	}

	@Override
	protected void addNPCAI() {
		((GroundPathNavigator)this.getNavigation()).setCanOpenDoors(true);
		this.getNavigation().setCanFloat(true);
		this.setPathfindingMalus(PathNodeType.DANGER_FIRE, 16.0F);
		this.setPathfindingMalus(PathNodeType.DAMAGE_FIRE, -1.0F);
		this.initialiseAttackGoals(this.getAttackGoalsHolder());
		this.addNPCTargetingAI();

		this.goalSelector.addGoal(0, new SwimGoal(this));
		this.addAttackGoal(2);
		this.goalSelector.addGoal(4, new OpenDoorGoal(this, true));
		this.goalSelector.addGoal(5, new ExtendedInteractableTalkToCurrentGoal(this, this));
		this.goalSelector.addGoal(6, new ExtendedInteractableFriendlyNPCConversationGoal(this, this, 0.001F));
		this.goalSelector.addGoal(7, new WatchSunriseSunsetGoal(this, 0.01F));
		this.goalSelector.addGoal(8, new StargazingGoal(this, 4.0E-4F));
		this.goalSelector.addGoal(9, new ExtendedInteractableWaterAvoidingRandomWalkingGoal(this, this, 1.0D));
		this.goalSelector.addGoal(10, new NPCEatGoal(this, this.getEatPool(), 12000));
		this.goalSelector.addGoal(10, new NPCDrinkGoal(this, this.getDrinkPool(), 8000));
		this.goalSelector.addGoal(11, new LookAtGoal(this, PlayerEntity.class, 8.0F, 0.02F));
		this.goalSelector.addGoal(11, new LookAtGoal(this, NPCEntity.class, 5.0F, 0.02F));
		this.goalSelector.addGoal(12, new LookAtGoal(this, LivingEntity.class, 8.0F, 0.02F));
		this.goalSelector.addGoal(13, new LookRandomlyGoal(this));
	}

	@Override
	public void updatePlayerInteractingWith(UUID uuid) {
		this.entityData.set(PLAYER_TRADING_WITH, Optional.of(uuid));
	}

	@Override
	public Optional<UUID> getPlayerInteractingWith() {
		return this.entityData.get(PLAYER_TRADING_WITH);
	}

	public static AttributeModifierMap.MutableAttribute createAttributes() {
		return ElfEntity.regAttrs();
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(PLAYER_TRADING_WITH, Optional.empty());
	}

	@Override
	protected void addNPCTargetingAI() {
		if(ExtendedServerConfig.enableLegacyTraderPassiveBehavior.get()) {
			this.addNonAggressiveTargetingGoals();
		}else {
			this.addAggressiveTargetingGoals();
		}
	}

	@Override
	public boolean isInteractingWithPlayer() {
		return ExtendedInteractableHelper.isInteractingWithPlayer(getPlayerInteractingWith());
	}

	@Override
	public void removePlayerFromInteracting(PlayerEntity player) {
		ExtendedInteractableHelper.removePlayerFromInteracting(player, entityData, PLAYER_TRADING_WITH);
	}

	@Override
	public void tick() {
		super.tick();

		if (level.isClientSide) {
			return;
		}

		if (lastTickVerifiedPlayerTradingWithIsOnline >= 100) {
			if (entityData.get(PLAYER_TRADING_WITH).isPresent()) {
				UUID uuid = entityData.get(PLAYER_TRADING_WITH).get();
				if (level.getPlayerByUUID(uuid) == null) entityData.set(PLAYER_TRADING_WITH, Optional.empty());
			}

			lastTickVerifiedPlayerTradingWithIsOnline = 0;
		} else {
			lastTickVerifiedPlayerTradingWithIsOnline++;
		}
	}
}