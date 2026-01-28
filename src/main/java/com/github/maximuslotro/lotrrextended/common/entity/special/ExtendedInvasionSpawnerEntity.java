package com.github.maximuslotro.lotrrextended.common.entity.special;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedSounds;
import lotr.common.LOTRLog;
import lotr.common.data.ExtendedLOTRPlayerData;
import lotr.common.data.LOTRLevelData;
import lotr.common.entity.npc.GondorManEntity;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.fac.Faction;
import lotr.common.fac.FactionSettingsManager;
import lotr.common.init.ExtendedEntities;
import lotr.common.init.LOTREntities;
import lotr.common.world.ExtendedInvasions;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class ExtendedInvasionSpawnerEntity extends Entity {

    private static final DataParameter<String> DATA_FACTION_KEY = EntityDataManager.defineId(ExtendedInvasionSpawnerEntity.class, DataSerializers.STRING);
    private static final DataParameter<Integer> DATA_ENEMIES_LEFT = EntityDataManager.defineId(ExtendedInvasionSpawnerEntity.class, DataSerializers.INT);
    private static final DataParameter<Integer> DATA_INVASION_SIZE = EntityDataManager.defineId(ExtendedInvasionSpawnerEntity.class, DataSerializers.INT);

    private static final float ROTATION_SPEED = 1.0F;
    private static final double INVASION_FOLLOW_RANGE = 40.0;
    private int tickCounter = 0;

    public ExtendedInvasionSpawnerEntity(EntityType<? extends ExtendedInvasionSpawnerEntity> pType, World pLevel) {
        super(pType, pLevel);
    }

    public ExtendedInvasionSpawnerEntity(World pLevel) {
        this(ExtendedEntities.INVASION_SPAWNER.get(), pLevel);
    }

    @Override
    protected void defineSynchedData() {
        this.getEntityData().define(DATA_FACTION_KEY, "empty");
        this.getEntityData().define(DATA_ENEMIES_LEFT, 0);
        this.getEntityData().define(DATA_INVASION_SIZE, 0);
    }

    @Override
    protected void readAdditionalSaveData(CompoundNBT compoundNBT) {
        if(compoundNBT.contains("faction_key")) {
            this.getEntityData().set(DATA_FACTION_KEY, compoundNBT.getString("faction_key"));
        }
        if(compoundNBT.contains("enemies_left")) {
            this.getEntityData().set(DATA_ENEMIES_LEFT, compoundNBT.getInt("enemies_left"));
        }
        if(compoundNBT.contains("invasion_size")) {
            this.getEntityData().set(DATA_INVASION_SIZE, compoundNBT.getInt("invasion_size"));
        }
    }

    public void tick() {
        this.noPhysics = true;
        super.tick();
        this.noPhysics = false;
        this.setNoGravity(true);

        if(!this.level.isClientSide()) {
            this.yRot += ROTATION_SPEED;

            if (this.yRot >= 360.0F) {
                this.yRot -= 360.0F;
            }
        }

        if (!this.level.isClientSide) {
            tickCounter++;

            // only attempt to spawn a mob every second
            if (tickCounter >= 20) {
                tickCounter = 0;

                int totalInvasionMobsInArea = getInvasionTypeMobsInRange();
                if (totalInvasionMobsInArea > -1 && totalInvasionMobsInArea < 16
                        && getEnemiesLeft() > 0) {
                    if (attemptMobSpawn()) {
                       setEnemiesLeft(getEnemiesLeft() - 1);
                    }
                }
            }
        }
    }

    private void setEnemiesLeft(int enemiesLeft) {
        this.getEntityData().set(DATA_ENEMIES_LEFT, enemiesLeft);
    }

    /***
     * Calculates how many mobs are in the INVASION_FOLLOW_RANGE * 2 that
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

        AxisAlignedBB bb = new AxisAlignedBB(
                blockPosition().getX(), blockPosition().getY(), blockPosition().getZ(),
                blockPosition().getX() + 1, blockPosition().getY() + 1, blockPosition().getZ() + 1
        );
        List<NPCEntity> surroundingMobs = level.getEntitiesOfClass(NPCEntity.class, bb.inflate(INVASION_FOLLOW_RANGE * 2));

        // pre-create the set
        Set<EntityType<?>> spawnableTypes = invasion.get().getSpawnEntries().stream()
                .map(ExtendedInvasions.InvasionSpawnEntry::getEntityType)
                .collect(Collectors.toSet());

        return (int) surroundingMobs.stream()
                .filter(npc -> spawnableTypes.contains(npc.getType()))
                .count();
    }

    private boolean attemptMobSpawn() {
       // TODO: Finish this method
       return false;
    }

    @Override
    protected void addAdditionalSaveData(CompoundNBT compoundNBT) {
        compoundNBT.putString("faction_key", getFactionKey());
        compoundNBT.putInt("invasion_size", getInvasionSize());
        compoundNBT.putInt("enemies_left", getEnemiesLeft());
    }

    public void setFaction(String factionKey) {
        this.getEntityData().set(DATA_FACTION_KEY, factionKey);
    }

    public String getFactionKey() {
        return this.getEntityData().get(DATA_FACTION_KEY);
    }

    public int getEnemiesLeft() {
        return this.getEntityData().get(DATA_ENEMIES_LEFT);
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
                blockPosition().getX() + 1, blockPosition().getY() + 1, blockPosition().getZ() + 1
        );
        List<PlayerEntity> surroundingPlayers = level.getEntitiesOfClass(PlayerEntity.class, bb.inflate(announceRange));

        // announce to all surrounding players
        for (PlayerEntity player : surroundingPlayers) {
            if(((ExtendedLOTRPlayerData) LOTRLevelData.serverInstance().getData(player)).getExtendedMiscData().doHighContrastMessages()) {
                IFormattableTextComponent text = invasion.get().getTranslatableName().copy().withStyle(npcNameStyle);
                player.sendMessage(new TranslationTextComponent("chat.invasion.started", text), Util.NIL_UUID);
            }else {
                IFormattableTextComponent text = invasion.get().getTranslatableName().copy().withStyle(TextFormatting.YELLOW);
                player.sendMessage(new TranslationTextComponent("chat.invasion.started", invasion.get().getTranslatableName().copy().withStyle(TextFormatting.YELLOW)), Util.NIL_UUID);
            }
        }

        playHorn();
        return true;
    }

    private void playHorn() {
        this.level.playSound(null, this.blockPosition(), ExtendedSounds.HORN_BLOW.get(), SoundCategory.PLAYERS, 4.0F, 1.0F);
    }

}
