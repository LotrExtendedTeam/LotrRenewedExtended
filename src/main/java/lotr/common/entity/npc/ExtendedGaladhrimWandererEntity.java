package lotr.common.entity.npc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;
import com.github.maximuslotro.lotrrextended.common.traders.ExtendedTraderTypes;

import lotr.common.entity.npc.ai.AttackGoalsHolder;
import lotr.common.entity.npc.ai.StandardAttackModeUpdaters;
import lotr.common.entity.npc.ai.goal.*;
import lotr.common.entity.npc.data.name.NPCNameGenerator;
import lotr.common.entity.npc.data.name.NPCNameGenerators;
import lotr.common.init.LOTREntities;
import lotr.common.init.LOTRItems;
import lotr.common.util.ExtendedInteractableHelper;
import lotr.common.util.ExtendedTraderHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.OpenDoorGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class ExtendedGaladhrimWandererEntity extends ExtendedAbstractCloakingElfEntity implements ExtendedWanderingTraderEntity {

    int lastTickCooldownReduced = 0;
    int lastTickVerifiedPlayerTradingWithIsOnline = 0;

    private static final DataParameter<Optional<UUID>> PLAYER_TRADING_WITH = EntityDataManager.defineId(ExtendedGaladhrimWandererEntity.class, DataSerializers.OPTIONAL_UUID);

    private List<UUID> escorts = new ArrayList<>();


    @Override
	protected NPCNameGenerator getNameGenerator() {
        return NPCNameGenerators.SINDARIN;
    }

	@SuppressWarnings("unchecked")
	public ExtendedGaladhrimWandererEntity(EntityType<? extends ExtendedAbstractCloakingElfEntity> type, World w) {
        super(type, w);
        this.getNPCCombatUpdater().setAttackModeUpdater(StandardAttackModeUpdaters.meleeOnly());
    }

    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT dataTag) {
        spawnData = super.finalizeSpawn(sw, diff, reason, spawnData, dataTag);
        this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRItems.GALADHRIM_DAGGER.get()));
        this.npcItemsInv.clearRangedWeapon(); // Eleven wanderers do not have ranged weapons
        this.npcItemsInv.clearIdleItem();
        return spawnData;
    }

    @Override
    public void updatePlayerInteractingWith(UUID uuid) {
        this.entityData.set(PLAYER_TRADING_WITH, Optional.of(uuid));
    }

    @Override
	public Optional<UUID> getPlayerInteractingWith() {
        return this.entityData.get(PLAYER_TRADING_WITH);
    }

    // Remove, drink, smoke and eat AIs
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
        this.goalSelector.addGoal(9, new ExtendedInteractableWaterAvoidingRandomWalkingGoal(this, this,1.0));
        this.goalSelector.addGoal(10, new NPCEatGoal(this, this.getEatPool(), 12000));
        this.goalSelector.addGoal(10, new NPCDrinkGoal(this, this.getDrinkPool(), 8000));
        this.goalSelector.addGoal(11, new LookAtGoal(this, PlayerEntity.class, 8.0F, 0.02F));
        this.goalSelector.addGoal(11, new LookAtGoal(this, NPCEntity.class, 5.0F, 0.02F));
        this.goalSelector.addGoal(12, new LookAtGoal(this, LivingEntity.class, 8.0F, 0.02F));
        this.goalSelector.addGoal(13, new LookRandomlyGoal(this));
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
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(PLAYER_TRADING_WITH, Optional.empty());
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return ElfEntity.regAttrs();
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT tag) {
        super.addAdditionalSaveData(tag);

        ExtendedTraderHelper.saveWanderingTraderEscortUUIDs(this, escorts, tag);
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT tag) {
        super.readAdditionalSaveData(tag);

        escorts = ExtendedTraderHelper.loadEscortUUIDs(tag);
    }

    @Override
    protected void initialiseAttackGoals(AttackGoalsHolder holder) {
        holder.setMeleeAttackGoal(this.createElfMeleeAttackGoal());
    }

    @Override
    public void playTradeSound() {
        ExtendedTraderHelper.playTraderSound(level, getOnPos());
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
    public void openCoinExchange(PlayerEntity player) {
        ExtendedTraderHelper.openCoinExchange(this, player, this);
    }

    @Override
    public void openTradeMenu(PlayerEntity player) {
        ExtendedTraderHelper.openTradeMenu(this, player, this);
    }

    @Override
    public String getTraderName() {
        return ExtendedTraderHelper.getTraderName(getDisplayName());
    }

    @Override
    public void tick() {
        super.tick();

        if (level.isClientSide) {
        	moveCloak();
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

        ExtendedTraderHelper.tickTrader(this, this);
    }

    @Override
    public ResourceLocation getCapeTextureLocation() {
        return new ResourceLocation("lotrextended", "textures/capes/galadhrim_trader.png");
    }

    @Override
    public TranslationTextComponent getDepartureLine(PlayerEntity player) {
        return new TranslationTextComponent("speechbank.galadhrim_wanderer.departure." + (random.nextInt(8) + 1), getName(), player.getName());
    }

    @Override
    public List<UUID> getEscortUUIDs() {
        return escorts;
    }

    @Override
    public void addEscort(UUID uuid) {
        escorts.add(uuid);
    }

    @Override
    public NPCEntity createEscort() {
        return LOTREntities.GALADHRIM_ELF.get().create(level);
    }

	@Override
	public int setLastTickCooldownReduced() {
		return lastTickCooldownReduced;
	}

	@Override
	public void getLastTickCooldownReduced(int cooldown) {
		lastTickCooldownReduced = cooldown;
	}

	@Override
	public ResourceLocation getTraderProfileId() {
		return ExtendedTraderTypes.GALADHRIM_WANDERER;
	}

    @Override
    public int getFactionColor() {
        return this.getFaction().getColor();
    }

	@Override
	public ResourceLocation getCloakTextureLocation() {
		return new ResourceLocation("lotr", "textures/entity/elf/galadhrim_trader_cloak.png");
	}
}
