package com.github.maximuslotro.lotrrextended.common.entity.special;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.client.ExtendedClientProxy;
import com.github.maximuslotro.lotrrextended.common.advancements.ExtendedCriteriaTriggers;
import com.github.maximuslotro.lotrrextended.common.data.ExtendedLevelData;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedSounds;
import com.github.maximuslotro.lotrrextended.common.serialization.ExtendedDataSerializers;

import lotr.common.LOTRLog;
import lotr.common.data.ExtendedLOTRPlayerData;
import lotr.common.data.LOTRLevelData;
import lotr.common.data.LOTRPlayerData;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.fac.Faction;
import lotr.common.fac.FactionPointer;
import lotr.common.fac.FactionSettings;
import lotr.common.fac.FactionSettingsManager;
import lotr.common.init.ExtendedEntities;
import lotr.common.world.ExtendedInvasions;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.*;
import net.minecraft.world.BossInfo;
import net.minecraft.world.Difficulty;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerBossInfo;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.*;
import java.util.stream.Collectors;

public class ExtendedInvasionSpawnerEntity extends Entity {
    private static final DataParameter<ResourceLocation> DATA_TYPE_KEY = EntityDataManager.defineId(ExtendedInvasionSpawnerEntity.class, ExtendedDataSerializers.RESOURCE_LOCATION);

    private static final float ROTATION_SPEED = 0.5F;
    private static final double INVASION_FOLLOW_RANGE = 40.0;
    private int tickCounter = 0;
	public double spin = 0;
	public double spinO = 0;
	private Set<EntityType<?>> spawnableTypes=null;
    private ServerBossInfo bossBar = new ServerBossInfo(StringTextComponent.EMPTY, BossInfo.Color.WHITE, BossInfo.Overlay.PROGRESS);;
    private int invasionEnemiesLeft = 0;
    private int invasionSize = 0;

    public ExtendedInvasionSpawnerEntity(EntityType<? extends ExtendedInvasionSpawnerEntity> pType, World pLevel) {
        super(pType, pLevel);
    }

    public ExtendedInvasionSpawnerEntity(World pLevel) {
        this(ExtendedEntities.INVASION_SPAWNER.get(), pLevel);
    }

    @Override
    protected void defineSynchedData() {
        this.getEntityData().define(DATA_TYPE_KEY, new ResourceLocation("empty"));
    }

    private void setBossBarText() {
    	Optional<ExtendedInvasions> invasion = ExtendedInvasions.fromTypeKey(getDisplayKey());
    	if (!invasion.isPresent()) {
    		bossBar.setName(new StringTextComponent("Invalid").withStyle(TextFormatting.DARK_RED));
    		return;
    	}
    	Optional<Faction> faction = attemptToGetFactionFromKey(invasion.get().getFactionPointer());
    	Style nameStyle = faction.isPresent() ? Style.EMPTY.withColor(Color.fromRgb(faction.get().getColor())) : Style.EMPTY;
    	bossBar.setName(invasion.get().getTranslatableName().withStyle(nameStyle));
    }

    private void updateBossBar() {
        if (level.isClientSide || getInvasionSize() <= 0/*prevent divide by zero*/) return;
        float percent = (float) getEnemiesLeft() / getInvasionSize();
        bossBar.setPercent(percent);
        bossBar.setVisible(getEnemiesLeft()>0);
    }

    private Optional<Faction> attemptToGetFactionFromKey(FactionPointer factionPointer) {
        if (FactionSettingsManager.serverInstance() == null) {
        	return Optional.empty();
        }
        FactionSettings settings = FactionSettingsManager.serverInstance().getCurrentLoadedFactions();
        if (settings == null) {
        	return Optional.empty();
        }
        return factionPointer.resolveFaction(FactionSettingsManager.serverInstance().getCurrentLoadedFactions());
    }

    @Override
    protected void addAdditionalSaveData(CompoundNBT compoundNBT) {
        compoundNBT.putString("faction_key", getDisplayKey().toString());
        compoundNBT.putInt("invasion_size", getInvasionSize());
        compoundNBT.putInt("enemies_left", getEnemiesLeft());
    }

    @Override
    protected void readAdditionalSaveData(CompoundNBT compoundNBT) {
        if(compoundNBT.contains("faction_key")) {
            this.getEntityData().set(DATA_TYPE_KEY, new ResourceLocation(compoundNBT.getString("faction_key")));
        }
        setBossBarText();
        if(compoundNBT.contains("invasion_size")) {
        	setInvasionSize(compoundNBT.getInt("invasion_size"));
        }
        invasionEnemiesLeft = compoundNBT.getInt("enemies_left");
        ExtendedLevelData.serverInstance().getInvasionData().registerInvasion(getUUID(), invasionEnemiesLeft);
    }

    @Override
	public void tick() {
        this.noPhysics = true;
        super.tick();
        this.noPhysics = false;
        this.setNoGravity(true);

        if(this.level.isClientSide()) {
			this.spinO = this.spin;
			this.spin = (this.spin + ROTATION_SPEED) % 360.0D;
			return;
		}

        if(level.getDifficulty() == Difficulty.PEACEFUL) {
        	endInvasion(false);
        	return;
        }
        // Sanity check to ensure the invasion is registered (should never be called in practice)
        if(!ExtendedLevelData.serverInstance().getInvasionData().hasInvasion(getUUID()) && invasionEnemiesLeft>0) {
        	ExtendedLog.warn("Had to sanity check register invasion! This should not happen.");
        	ExtendedLevelData.serverInstance().getInvasionData().registerInvasion(uuid, invasionEnemiesLeft);
        }
        // Gets how many enemies are left as tracked by the server
        // Will can return -1 if invalid or >=0 otherwise
        setEnemiesLeft(ExtendedLevelData.serverInstance().getInvasionData().getEnemiesLeft(getUUID()));
        updateBossBar();

        // only attempt to spawn a mob every second
        tickCounter++;
        if (tickCounter >= 20) {
            tickCounter = 0;
            int totalInvasionMobsInArea = getInvasionTypeMobsInRange();
            if (totalInvasionMobsInArea > -1 && totalInvasionMobsInArea < 16 && getEnemiesLeft() > 0) {
                Optional<ExtendedInvasions> invasion = ExtendedInvasions.fromTypeKey(getDisplayKey());
                ExtendedInvasions.InvasionSpawnEntry result = WeightedRandom.getRandomItem(this.random, invasion.get().getSpawnEntries());

                if (attemptMobSpawn(result.getEntityType())) {
                	// send flames to client
                	((ServerWorld) level).sendParticles(ParticleTypes.FLAME, getX(), getY() + 2.75, getZ(), 5, 0.25, .75F, 0.25, 0.02);
                }
            }
        }
        // Remove invasion if all enemies have been killed, or the invasion is invalid
        // Should only be -1 here if invasionEnemiesLeft sanity check above is less than 0, so remove anyways
        if (getEnemiesLeft() <= 0) {
            endInvasion(true);
        }

    }

    private Set<EntityType<?>> getSpawnableEntities(Optional<ExtendedInvasions> invasion){
    	if(spawnableTypes==null) {
    		spawnableTypes = invasion.get().getSpawnEntries().stream()
                    .map(ExtendedInvasions.InvasionSpawnEntry::getEntityType).collect(Collectors.toSet());
    	}
    	return spawnableTypes;
    }
    /***
     * Calculates how many mobs are in the INVASION_FOLLOW_RANGE that
     * match the type of the invasion. A helper method to prevent the invasion
     * from flooding the area with mobs.
     *
     * @return the number of mobs or -1 if invasion is not set correctly
     */
    private int getInvasionTypeMobsInRange() {
        Optional<ExtendedInvasions> invasion = ExtendedInvasions.fromTypeKey(getDisplayKey());
        if (!invasion.isPresent()) {
            return -1;
        }

        Set<EntityType<?>> spawnableTypes = getSpawnableEntities(invasion);

        AxisAlignedBB bb = new AxisAlignedBB(
                blockPosition().getX(), blockPosition().getY(), blockPosition().getZ(),
                blockPosition().getX() + 1, blockPosition().getY() + 1, blockPosition().getZ() + 1);
        List<NPCEntity> surroundingMobs = level.getLoadedEntitiesOfClass(NPCEntity.class, bb.inflate(INVASION_FOLLOW_RANGE),
        		(npc) -> spawnableTypes.contains(npc.getType()));
        return surroundingMobs.size();
    }

    public void endInvasion(boolean completed) {
        if (!level.isClientSide) {
        	bossBar.removeAllPlayers();
        }

        if (completed) {
            Optional<ExtendedInvasions> invasion = ExtendedInvasions.fromTypeKey(getDisplayKey());
            Optional<Faction> invasionFaction = invasion.isPresent() ? attemptToGetFactionFromKey(invasion.get().getFactionPointer()) : Optional.empty();

            if (!level.isClientSide && invasionFaction.isPresent()) {
                Set<UUID> playersWhoFought = ExtendedLevelData.serverInstance().getInvasionData().getPlayersWhoFoughtInvasion(getUUID());
                for (UUID uuid : playersWhoFought) {
                    PlayerEntity playerEntity = level.getPlayerByUUID(uuid);
                    if (playerEntity == null) continue;

                    LOTRPlayerData playerData = LOTRLevelData.getSidedData(playerEntity);
                    if (playerData.getAlignmentData().getAlignment(invasionFaction.get()) < 0.0F) {
                        ExtendedCriteriaTriggers.ABSTRACT_STRING_TRIGGER.trigger((ServerPlayerEntity) playerEntity, "defeatedInvasion");
                    }
                }
            }
        }
        // Handles explosion and unregistering
        this.remove();
    }

    /**
     * Note: This method should only be run on the server
     *
     * @param type Entity type to spawn
     * @return True if mob spawned
     */
    private boolean attemptMobSpawn(EntityType<? extends MobEntity> type) {

        for (int attempts = 0; attempts < 40; attempts++) {
            int x = (int) (this.getX() + MathHelper.nextInt(this.random, -6, 6));
            int z = (int) (this.getZ() + MathHelper.nextInt(this.random, -6, 6));
            int y = level.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, x, z);
            BlockPos pos = new BlockPos(x, y, z);

            // all possible conditions that should make us quit and pick a new location
            BlockPos plusOneY = new BlockPos(x, y + 1, z);
            if (level.getBlockState(pos).isSuffocating(level, pos)
                    || level.getBlockState(plusOneY).isSuffocating(level, plusOneY)) {
                continue;
            }
            ServerWorld world = (ServerWorld) level;
            MobEntity entity = type.create(world, null, null, null, pos, SpawnReason.SPAWNER, true, false);
            if (entity != null) {
            	// check spawn rules (banner protection for EVENT types)
				if (!entity.checkSpawnRules(world, SpawnReason.EVENT)) {
					// Disable spawning under opposing banner protection
                	continue;
                }
                entity.setPersistenceRequired();
                // set NBT data for invasion
                entity.getPersistentData().putUUID("ExtInvasion", this.getUUID());

                world.addFreshEntityWithPassengers(entity);
                return true;
            }
        }

        return false;
    }

    public void setTypeKey(ResourceLocation factionKey) {
        this.getEntityData().set(DATA_TYPE_KEY, factionKey);
    }

    public ResourceLocation getDisplayKey() {
        return this.getEntityData().get(DATA_TYPE_KEY);
    }

    public void setInvasionSize(int pInvasionSize) {
       invasionSize = pInvasionSize;
    }
   
    public int getInvasionSize() {
        return invasionSize;
    }

    public void setEnemiesLeft(int invasionSize) {
    	invasionEnemiesLeft = invasionSize;
    }
    public int getEnemiesLeft() {
        return invasionEnemiesLeft;
    }

    @Override
    public ITextComponent getDisplayName() {
        return new StringTextComponent("Invasion Spawner: " + getDisplayKey());
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

	@Override
	public boolean canChangeDimensions() {
		return false;
	}

	@Override
	public boolean isIgnoringBlockTriggers() {
		return true;
	}

	@Override
	public boolean ignoreExplosion() {
		return true;
	}

	@Override
	public boolean fireImmune() {
		return true;
	}

    @Override
    public boolean isOnFire() {
        return false;
    }

    @Override
	public boolean hurt(DamageSource pSource, float pAmount) {
		if (this.isInvulnerableTo(pSource)) {
			return false;
		} else {
			if(pSource.getEntity()!=null) {
				if(pSource.isCreativePlayer()) {
					this.remove();
				}
			}else {
				this.remove();
			}
			return false;
		}
	}

	@Override
	public boolean isPickable() {
		if (!this.level.isClientSide()) {
			return false;
		}
		PlayerEntity entityplayer = ExtendedClientProxy.INSTANCE.getClientPlayerEntity();
		return (entityplayer != null && entityplayer.isCreative());
	}

    @Override
	public void remove() {
    	if (!level.isClientSide) {
    		// Pass null so entities don't try to target this spawner
            level.explode(null, this.getX(), this.getY(), this.getZ(), 2F, Explosion.Mode.NONE);
            ExtendedLevelData.serverInstance().getInvasionData().unregisterInvasion(getUUID());
            // Sanity check remove bossbar in the instance a creative player breaks the invasion with their hand
            if (bossBar != null) {
            	bossBar.removeAllPlayers();
            }
        }
		super.remove();
	}
   
    @Override
    public ActionResultType interact(PlayerEntity pPlayer, Hand pHand) {
        if(!pPlayer.level.isClientSide() && pPlayer.isCreative()) {
            pPlayer.sendMessage(new StringTextComponent("Faction Key: ").withStyle(TextFormatting.GREEN)
                    .append(new StringTextComponent(getDisplayKey() !=null ? getDisplayKey().toString() : "Unset").withStyle(TextFormatting.WHITE)), Util.NIL_UUID);
            pPlayer.sendMessage(new StringTextComponent("UUID: ").withStyle(TextFormatting.GREEN)
                    .append(new StringTextComponent(getStringUUID())).withStyle(TextFormatting.WHITE), Util.NIL_UUID);
        }
        return ActionResultType.sidedSuccess(this.level.isClientSide);
    }

    /**
     * Return true if this entity should push and be pushed by other entities when colliding.
     */
    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    protected boolean canRide(Entity pEntity) {
        return false;
    }

    @Override
    public boolean isInvulnerable() {
        return true;
    }

    @Override
    public PushReaction getPistonPushReaction() {
        return PushReaction.IGNORE;
    }

	/**
	 * Add the given player to the list of players tracking this entity. For instance, a player may track a boss in order
	 * to view its associated boss bar.
	 */
	@Override
	public void startSeenByPlayer(ServerPlayerEntity pServerPlayer) {
		super.startSeenByPlayer(pServerPlayer);
		bossBar.addPlayer(pServerPlayer);
	}

	/**
	 * Removes the given player from the list of players tracking this entity. See {@link Entity#startSeenByPlayer} for
	 * more information on tracking.
	 */
	@Override
	public void stopSeenByPlayer(ServerPlayerEntity pServerPlayer) {
		super.stopSeenByPlayer(pServerPlayer);
		bossBar.removePlayer(pServerPlayer);
	}
	
    public boolean startInvasion() {
        return startInvasion(MathHelper.nextInt(this.random, 30, 70));
    }

    public boolean startInvasion(int pInvasionSize) {
        Optional<ExtendedInvasions> invasion = ExtendedInvasions.fromTypeKey(getDisplayKey());
        if (!invasion.isPresent()) {
            LOTRLog.error("Could not start invasion from entity " + this + " because factionKey: " + getDisplayKey() + " was not valid.");
            return false;
        }

        // determine Faction object for faction color
        Optional<Faction> faction =attemptToGetFactionFromKey(invasion.get().getFactionPointer());
        Style npcNameStyle = faction.isPresent() ? Style.EMPTY.withColor(Color.fromRgb(faction.get().getColor())) : Style.EMPTY.withColor(TextFormatting.YELLOW);

        // determine all nearby players
        double announceRange = INVASION_FOLLOW_RANGE * 2;
        AxisAlignedBB bb = new AxisAlignedBB(
                blockPosition().getX(), blockPosition().getY(), blockPosition().getZ(),
                blockPosition().getX() + 1, blockPosition().getY() + 1, blockPosition().getZ() + 1);
        List<PlayerEntity> surroundingPlayers = level.getEntitiesOfClass(PlayerEntity.class, bb.inflate(announceRange));

        // announce to all surrounding players
        for (PlayerEntity player : surroundingPlayers) {
            if(((ExtendedLOTRPlayerData) LOTRLevelData.serverInstance().getData(player)).getExtendedMiscData().doHighContrastMessages()) {
                IFormattableTextComponent text = invasion.get().getTranslatableName().copy().withStyle(npcNameStyle);
                player.sendMessage(new TranslationTextComponent("chat.lotrextended.invasion.started", text), Util.NIL_UUID);
            }else {
                IFormattableTextComponent text = invasion.get().getTranslatableName().copy().withStyle(TextFormatting.YELLOW);
                player.sendMessage(new TranslationTextComponent("chat.lotrextended.invasion.started", invasion.get().getTranslatableName().copy().withStyle(TextFormatting.YELLOW)), Util.NIL_UUID);
            }
        }

        playHorn();

        // set initial size
        setInvasionSize(pInvasionSize);
        setEnemiesLeft(pInvasionSize);
        setBossBarText();
        ExtendedLevelData.serverInstance().getInvasionData().registerInvasion(getUUID(), pInvasionSize);
        return true;
    }

    private void playHorn() {
        this.level.playSound(null, this.blockPosition(), ExtendedSounds.HORN_BLOW.get(), SoundCategory.PLAYERS, 3.0F, 1.0F);
    }

}
