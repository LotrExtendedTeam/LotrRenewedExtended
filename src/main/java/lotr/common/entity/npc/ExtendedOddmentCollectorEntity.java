package lotr.common.entity.npc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.github.maximuslotro.lotrrextended.common.traders.ExtendedTraderTypes;

import lotr.common.entity.npc.ai.goal.ExtendedInteractableWaterAvoidingRandomWalkingGoal;
import lotr.common.entity.npc.ai.goal.NPCMeleeAttackGoal;
import lotr.common.entity.npc.ai.goal.WatchSunriseSunsetGoal;
import lotr.common.entity.npc.data.NPCFoodPool;
import lotr.common.entity.npc.data.NPCFoodPools;
import lotr.common.entity.npc.data.NPCGenderProvider;
import lotr.common.entity.npc.data.name.NPCNameGenerator;
import lotr.common.entity.npc.data.name.NPCNameGenerators;
import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRItems;
import lotr.common.util.ExtendedInteractableHelper;
import lotr.common.util.ExtendedTraderHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.OpenDoorGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class ExtendedOddmentCollectorEntity extends ManEntity implements ExtendedWanderingTraderEntity {

    private static final SpawnEquipmentTable WEAPONS = SpawnEquipmentTable.of(LOTRItems.IRON_DAGGER, LOTRItems.BRONZE_DAGGER);

    int lastTickCooldownReduced = 0;
    int lastTickVerifiedPlayerTradingWithIsOnline = 0;

    private static final DataParameter<Optional<UUID>> PLAYER_TRADING_WITH = EntityDataManager.defineId(ExtendedOddmentCollectorEntity.class, DataSerializers.OPTIONAL_UUID);

    private List<UUID> escorts = new ArrayList<>();


    // give them the 5 armor points they have in Legacy
    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return AbstractMannishEntity.regAttrs().add(Attributes.ARMOR, 5.0);
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
        this.goalSelector.addGoal(7, new WatchSunriseSunsetGoal(this, 0.005F));
        this.goalSelector.addGoal(8, new ExtendedInteractableWaterAvoidingRandomWalkingGoal(this, this, 1.0));
        this.addConsumingGoals(9);
        this.goalSelector.addGoal(10, new LookAtGoal(this, PlayerEntity.class, 8.0F, 0.02F));
        this.goalSelector.addGoal(10, new LookAtGoal(this, NPCEntity.class, 5.0F, 0.02F));
        this.goalSelector.addGoal(11, new LookAtGoal(this, LivingEntity.class, 8.0F, 0.02F));
        this.goalSelector.addGoal(12, new LookRandomlyGoal(this));
    }

    @Override
    public void updatePlayerInteractingWith(UUID uuid) {
        this.entityData.set(PLAYER_TRADING_WITH, Optional.of(uuid));
    }

    @Override
	public Optional<UUID> getPlayerInteractingWith() {
        return this.entityData.get(PLAYER_TRADING_WITH);
    }

    public ExtendedOddmentCollectorEntity(EntityType<? extends ExtendedOddmentCollectorEntity> type, World w) {
        super(type, w);
        setPersistenceRequired();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(PLAYER_TRADING_WITH, Optional.empty());
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT tag) {
        super.addAdditionalSaveData(tag);

        ExtendedTraderHelper.saveWanderingTraderEscortUUIDs(this, escorts, tag);
    }

    @Override
    protected void addNPCTargetingAI() {
        this.addNonAggressiveTargetingGoals();
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT tag) {
        super.readAdditionalSaveData(tag);

        escorts = ExtendedTraderHelper.loadEscortUUIDs(tag);
    }

    // in Legacy the eat and drink pools for Oddment Collectors are based off Dunland
    @Override
	protected NPCFoodPool getEatPool() {
        return NPCFoodPools.DUNLENDING;
    }

    @Override
	protected NPCFoodPool getDrinkPool() {
        return NPCFoodPools.DUNLENDING_DRINK;
    }

    @Override
	protected NPCNameGenerator getNameGenerator() {
        // TODO: In legacy it can pick Rohan, Gondor, Dunland or Bree for names, but I am not sure how that will work
        //  or if it will be consistent so just doing Dunland for now
        return NPCNameGenerators.DUNLENDING;
    }

    @Override
    protected Goal createAttackGoal() {
        return new NPCMeleeAttackGoal(this, 1.3);
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

    // TODO: Maybe some sort of negative reputation limit should prevent a player from trading but for now I don't want killing
    //  an Oddment Collector once to prevent you from being able to sell to them
    @Override
	public boolean canTradeWithPlayer(PlayerEntity player) {
        return this.isFriendly(player);
    }

    @Override
    public ActionResultType mobInteract(PlayerEntity player, Hand hand) {
        return ExtendedTraderHelper.mobInteract(this, this, player, hand);
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
    public ILivingEntityData finalizeSpawn(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT dataTag) {
        spawnData = super.finalizeSpawn(sw, diff, reason, spawnData, dataTag);
        this.npcItemsInv.setMeleeWeapon(WEAPONS.getRandomItem(this.random));
        this.npcItemsInv.setIdleItem(new ItemStack(LOTRItems.SHIRE_HEATHER.get()));

        this.setItemSlot(EquipmentSlotType.HEAD, new ItemStack(ExtendedItems.LEATHER_HAT.get()));

        return spawnData;
    }

    @Override
    public TranslationTextComponent getDepartureLine(PlayerEntity player) {
        return new TranslationTextComponent("speechbank.oddment_collector.departure." + (random.nextInt(18) + 1), getName(), player.getName());
    }

    @Override
	protected NPCGenderProvider getGenderProvider() {
        return NPCGenderProvider.MALE;
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
        return null;
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
		return ExtendedTraderTypes.ODDMENT_COLLECTOR;
	}
}

