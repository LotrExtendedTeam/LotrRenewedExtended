package com.github.maximuslotro.lotrrextended.common.entity.special;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.advancements.ExtendedCriteriaTriggers;
import com.github.maximuslotro.lotrrextended.common.bannerprotection.ExtendedBannerProtectionManager;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedSounds;
import lotr.common.LOTRLog;
import lotr.common.data.ExtendedLOTRPlayerData;
import lotr.common.data.ExtendedLevelData;
import lotr.common.data.LOTRLevelData;
import lotr.common.data.LOTRPlayerData;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.fac.Faction;
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
import net.minecraft.network.datasync.DataSerializers;
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

    private static final DataParameter<String> DATA_FACTION_KEY = EntityDataManager.defineId(ExtendedInvasionSpawnerEntity.class, DataSerializers.STRING);
    private static final DataParameter<Integer> DATA_INVASION_SIZE = EntityDataManager.defineId(ExtendedInvasionSpawnerEntity.class, DataSerializers.INT);
    private static final DataParameter<Boolean> DATA_INVASION_STARTED = EntityDataManager.defineId(ExtendedInvasionSpawnerEntity.class, DataSerializers.BOOLEAN);

    private static final float ROTATION_SPEED = 0.5F;
    private static final double INVASION_FOLLOW_RANGE = 40.0;
    private int tickCounter = 0;
	public double spin = 0;
	public double spinO = 0;
	private Set<EntityType<?>> spawnableTypes=null;
    private ServerBossInfo bossBar = null;
    private Set<UUID> playersInBossBar = new HashSet<>();

    public ExtendedInvasionSpawnerEntity(EntityType<? extends ExtendedInvasionSpawnerEntity> pType, World pLevel) {
        super(pType, pLevel);
    }

    public ExtendedInvasionSpawnerEntity(World pLevel) {
        this(ExtendedEntities.INVASION_SPAWNER.get(), pLevel);
    }

    @Override
    protected void defineSynchedData() {
        this.getEntityData().define(DATA_FACTION_KEY, "empty");
        this.getEntityData().define(DATA_INVASION_SIZE, 0);
        this.getEntityData().define(DATA_INVASION_STARTED, false);
    }

    private boolean initializeBossBar() {
        boolean initialized = false;
        if (level.isClientSide) return initialized; // only run on the server
        Optional<ExtendedInvasions> invasion = ExtendedInvasions.fromKey(getFactionKey());
        if (!invasion.isPresent()) return initialized;

        if (ExtendedLevelData.serverInstance().getEnemiesLeft(getUUID()) > 0 && getInvasionStarted()) {
            Faction faction = attemptToGetFactionFromKey(invasion.get().getBaseFactionKey());
            Style nameStyle;
            if (faction != null) {
                nameStyle = Style.EMPTY.withColor(Color.fromRgb(faction.getColor()));
            } else {
                nameStyle = Style.EMPTY;
            }
            IFormattableTextComponent text = invasion.get().getTranslatableName().copy().withStyle(nameStyle);
            bossBar = new ServerBossInfo(
                text,
                BossInfo.Color.WHITE,
                BossInfo.Overlay.PROGRESS
            );
        }

        initialized = true;
        return initialized;
    }

    private void updateBossBar() {
        if (bossBar == null || level.isClientSide) return;

        if (getInvasionSize() <= 0) return;

        int enemiesLeft = ExtendedLevelData.serverInstance().getEnemiesLeft(getUUID());
        if (enemiesLeft <= 0) return;

        float percent = (float) enemiesLeft / getInvasionSize();

        bossBar.setVisible(true);
        bossBar.setPercent(percent);
    }

    private Faction attemptToGetFactionFromKey(String factionKey) {
        if (FactionSettingsManager.serverInstance() == null) return null;

        ResourceLocation resLoc = ResourceLocation.of(factionKey, ':');
        FactionSettings settings = FactionSettingsManager.serverInstance().getCurrentLoadedFactions();
        if (settings == null) return null;

        return settings.getFactionByName(resLoc);
    }

    @Override
    protected void readAdditionalSaveData(CompoundNBT compoundNBT) {
        if(compoundNBT.contains("faction_key")) {
            this.getEntityData().set(DATA_FACTION_KEY, compoundNBT.getString("faction_key"));
        }
        if(compoundNBT.contains("invasion_size")) {
            this.getEntityData().set(DATA_INVASION_SIZE, compoundNBT.getInt("invasion_size"));
        }
        if(compoundNBT.contains("invasion_started")) {
            this.getEntityData().set(DATA_INVASION_STARTED, compoundNBT.getBoolean("invasion_started"));
        }
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
		}

        if (!this.level.isClientSide()) {
            if(level.getDifficulty() == Difficulty.PEACEFUL) {
            	endInvasion(false);
            	return;
            }
            
            if (bossBar == null && getInvasionStarted()) {
                boolean result = initializeBossBar();
                if (result) updateBossBar();
            } else {
                updateBossBar();
            }

            tickCounter++;

            // only attempt to spawn a mob every second
            if (tickCounter >= 20) {
                // attempt to add nearby players to BossBar
                AxisAlignedBB bb = new AxisAlignedBB(
                        blockPosition().getX(), blockPosition().getY(), blockPosition().getZ(),
                        blockPosition().getX() + 1, blockPosition().getY() + 1, blockPosition().getZ() + 1);

                List<PlayerEntity> surroundingPlayers = level.getEntitiesOfClass(PlayerEntity.class, bb.inflate(INVASION_FOLLOW_RANGE));
                for (PlayerEntity player : surroundingPlayers) {
                    if (bossBar != null) bossBar.addPlayer((ServerPlayerEntity) player);
                    playersInBossBar.add(player.getUUID());
                }

                // remove any players who may have stepped out
                Set<UUID> surroundingUUIDs = surroundingPlayers.stream()
                        .map(PlayerEntity::getUUID)
                        .collect(Collectors.toSet());
                Set<UUID> playersToRemove = playersInBossBar.stream()
                        .filter(uuid -> !surroundingUUIDs.contains(uuid))
                        .collect(Collectors.toSet());

                for (UUID uuid : playersToRemove) {
                    if (uuid == null) continue;
                    PlayerEntity player = level.getPlayerByUUID(uuid);
                    if (player != null) {
                        playersInBossBar.remove(uuid);
                        if (bossBar != null) bossBar.removePlayer((ServerPlayerEntity) player);
                    }
                }

                tickCounter = 0;

                int totalInvasionMobsInArea = getInvasionTypeMobsInRange();
                int enemiesLeft = ExtendedLevelData.serverInstance().getEnemiesLeft(getUUID());
                if (totalInvasionMobsInArea > -1 && totalInvasionMobsInArea < 16
                        && enemiesLeft > 0) {
                    Optional<ExtendedInvasions> invasion = ExtendedInvasions.fromKey(getFactionKey());
                    ExtendedInvasions.InvasionSpawnEntry result = WeightedRandom.getRandomItem(this.random, invasion.get().getSpawnEntries());

                    if (attemptMobSpawn(result.getEntityType())) {
                        if (!level.isClientSide()) {
                            // send flames to client
                            ServerWorld world = (ServerWorld) level;
                            world.sendParticles(ParticleTypes.FLAME,
                                    getX(), getY() + 2.75, getZ(),
                                    5, 0.25, .75F, 0.25, 0.02);
                        }
                    }
                }
            }

            int enemiesLeft = ExtendedLevelData.serverInstance().getEnemiesLeft(getUUID());
            if (getInvasionStarted() && enemiesLeft == 0) {
                endInvasion(true);
            }
        }

    }

    private boolean getInvasionStarted() {
        return getEntityData().get(DATA_INVASION_STARTED);
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
        Optional<ExtendedInvasions> invasion = ExtendedInvasions.fromKey(getFactionKey());
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
        if (!level.isClientSide && bossBar != null) bossBar.removeAllPlayers();

        if (completed) {
            Optional<ExtendedInvasions> invasion = ExtendedInvasions.fromKey(getFactionKey());
            Faction invasionFaction = null;
            if (invasion.isPresent()) {
                ResourceLocation resLoc = ResourceLocation.of(invasion.get().getBaseFactionKey(), ':');
                invasionFaction = FactionSettingsManager.serverInstance().getCurrentLoadedFactions().getFactionByName(resLoc);
            }

            if (!level.isClientSide && invasionFaction != null) {
                Set<UUID> playersWhoFought = ExtendedLevelData.serverInstance().getPlayersWhoFoughtInvasion(getUUID());
                for (UUID uuid : playersWhoFought) {
                    PlayerEntity playerEntity = level.getPlayerByUUID(uuid);
                    if (playerEntity == null) continue;

                    LOTRPlayerData playerData = LOTRLevelData.getSidedData(playerEntity);
                    if (playerData.getAlignmentData().getAlignment(invasionFaction) < 0.0F) {
                        ExtendedCriteriaTriggers.ABSTRACT_STRING_TRIGGER.trigger((ServerPlayerEntity) playerEntity, "defeatedInvasion");
                    }
                }
            }
        }

        if (!level.isClientSide) {
            level.explode(this, this.getX(), this.getY(), this.getZ(), 2F, Explosion.Mode.NONE);
        }

        // unregister invasion from server data
        if (!level.isClientSide) {
            ExtendedLevelData.serverInstance().unregisterInvasion(getUUID());
        }

        this.remove();
    }

    private void setInvasionStarted(boolean invasionStarted) {
        this.getEntityData().set(DATA_INVASION_STARTED, invasionStarted);
    }

    /**
     * Note: This method should only be run on the server
     *
     * @param type Entity type to spawn
     * @return True if m ob spawned
     */
    private boolean attemptMobSpawn(EntityType<? extends MobEntity> type) {
        boolean couldSpawnMob = false;

        for (int attempts = 0; attempts < 40; attempts++) {
            int x = (int) (this.getX() + MathHelper.nextInt(this.random, -6, 6));
            int z = (int) (this.getZ() + MathHelper.nextInt(this.random, -6, 6));
            int y = this.level.getHeight(Heightmap.Type.WORLD_SURFACE, x, z);
            BlockPos pos = new BlockPos(x, y, z);

            // all possible conditions that should make us quit and pick a new location
            BlockPos plusOneY = new BlockPos(x, y + 1, z);
            if (y < 62 || level.getBlockState(pos).isSuffocating(level, pos)
                    || level.getBlockState(plusOneY).isSuffocating(level, plusOneY)) {
                continue;
            }

            MobEntity entity = type.create((ServerWorld) this.level, null, null, null, pos, SpawnReason.SPAWNER, true, false);
            if (entity != null) {
                // check protection
                if (ExtendedBannerProtectionManager.getProtectionStatus(level, pos, new ExtendedBannerProtectionManager.SingleProtectionInstance.AnyProtection()).isNotAllowed()) {
                    ExtendedLog.debug("Invasion mob could not be spawned at " + pos + " because it was inside protection banner.");
                    continue;
                }

                float yaw = level.random.nextFloat() * 360.0F; // set look angle
                entity.setYBodyRot(yaw);
                entity.setYHeadRot(yaw);
                entity.setPersistenceRequired();

                // set NBT data for invasion
                entity.getPersistentData().putUUID("lotr:invasion_id", this.getUUID());

                level.addFreshEntity(entity);
                return true;
            }
        }

        return couldSpawnMob;
    }

    @Override
    protected void addAdditionalSaveData(CompoundNBT compoundNBT) {
        compoundNBT.putString("faction_key", getFactionKey());
        compoundNBT.putInt("invasion_size", getInvasionSize());
        compoundNBT.putBoolean("invasion_started", getInvasionStarted());
    }

    public void setFaction(String factionKey) {
        this.getEntityData().set(DATA_FACTION_KEY, factionKey);
    }

    public String getFactionKey() {
        return this.getEntityData().get(DATA_FACTION_KEY);
    }

    public int getInvasionSize() {
        return this.getEntityData().get(DATA_INVASION_SIZE);
    }

    @Override
    public ITextComponent getDisplayName() {
        return new StringTextComponent("Invasion Spawner: " + getFactionKey());
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
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
            Entity entity = pSource.getEntity();
            if(entity !=null && entity instanceof PlayerEntity && ((PlayerEntity)entity).abilities.instabuild) {
                if(entity.isCrouching()) {
                    this.remove();
                }
            }else {
                this.remove();
            }
            return false;
        }
    }

    @Override
    public ActionResultType interact(PlayerEntity pPlayer, Hand pHand) {
        if(!pPlayer.level.isClientSide() && pPlayer.isCreative()) {
            pPlayer.sendMessage(new StringTextComponent("Faction Key: ").withStyle(TextFormatting.GREEN)
                    .append(new StringTextComponent(getFactionKey() !=null ? getFactionKey() : "Unset").withStyle(TextFormatting.WHITE)), Util.NIL_UUID);
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

    public boolean startInvasion() {
        return startInvasion(MathHelper.nextInt(this.random, 30, 70));
    }

    public boolean startInvasion(int invasionSize) {
        Optional<ExtendedInvasions> invasion = ExtendedInvasions.fromKey(getFactionKey());
        if (!invasion.isPresent()) {
            LOTRLog.error("Could not start invasion from entity " + this + " because factionKey: " + getFactionKey() + " was not valid.");
            return false;
        }

        // determine Faction object for faction color
        ResourceLocation resLoc = ResourceLocation.of(invasion.get().getBaseFactionKey(), ':');
        Faction faction = FactionSettingsManager.serverInstance().getCurrentLoadedFactions().getFactionByName(resLoc);
        Style npcNameStyle;
        if (faction != null) {
            npcNameStyle = Style.EMPTY.withColor(Color.fromRgb(faction.getColor()));
        } else {
            npcNameStyle = Style.EMPTY.withColor(TextFormatting.YELLOW);
        }

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
        setInvasionSize(invasionSize);
        ExtendedLevelData.serverInstance().registerInvasion(getUUID(), invasionSize);

        setInvasionStarted(true);

        return true;
    }

    private void playHorn() {
        this.level.playSound(null, this.blockPosition(), ExtendedSounds.HORN_BLOW.get(), SoundCategory.PLAYERS, 3.0F, 1.0F);
    }

    public void setInvasionSize(int invasionSize) {
        this.getEntityData().set(DATA_INVASION_SIZE, invasionSize);
    }

}
