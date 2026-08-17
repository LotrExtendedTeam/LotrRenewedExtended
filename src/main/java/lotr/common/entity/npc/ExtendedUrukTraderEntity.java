package lotr.common.entity.npc;

import java.util.Optional;
import java.util.UUID;

import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;

import lotr.common.util.ExtendedInteractableHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;

public abstract class ExtendedUrukTraderEntity extends UrukEntity implements ExtendedINPCEntitySettings, ExtendedIMerchantEntity {

	int lastTickVerifiedPlayerTradingWithIsOnline = 0;

	private static final DataParameter<Optional<UUID>> PLAYER_TRADING_WITH = EntityDataManager.defineId(ExtendedUrukTraderEntity.class, DataSerializers.OPTIONAL_UUID);

	public ExtendedUrukTraderEntity(EntityType<? extends UrukEntity> type, World w) {
		super(type, w);
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
		return UrukEntity.regAttrs();
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