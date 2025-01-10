package lotr.common.entity.npc;

import lotr.common.entity.npc.ai.goal.*;
import lotr.common.entity.npc.data.NPCFoodPool;
import lotr.common.entity.npc.data.NPCFoodPools;
import lotr.common.entity.npc.data.NPCGenderProvider;
import lotr.common.entity.npc.data.name.NPCNameGenerator;
import lotr.common.entity.npc.data.name.NPCNameGenerators;
import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTREntities;
import lotr.common.init.LOTRItems;
import lotr.common.item.VesselDrinkItem;
import lotr.common.util.DrinkUtils;
import lotr.common.util.ExtendedInteractableHelper;
import lotr.common.util.ExtendedTraderHelper;
import lotr.common.util.TradeUtils;
import lotr.common.util.representation.ExtendedMultitradeEntry;
import lotr.common.util.representation.ExtendedTrade;
import lotr.common.util.representation.ExtendedTradeEntries;
import lotr.common.util.representation.ExtendedTradeEntry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
public class ExtendedBreelandMerchantEntity extends BreeManEntity implements ExtendedTraderEntity {

    private static final SpawnEquipmentTable WEAPONS;

    int lastTickCooldownReduced = 0;
    int lastTickVerifiedPlayerTradingWithIsOnline = 0;

    private static final DataParameter<Optional<UUID>> PLAYER_TRADING_WITH = EntityDataManager.defineId(ExtendedBreelandMerchantEntity.class, DataSerializers.OPTIONAL_UUID);
    private static final DataParameter<Boolean> IS_WANDERING_TRADER = EntityDataManager.defineId(ExtendedBreelandMerchantEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> WANDERING_TRADER_TIME_LEFT = EntityDataManager.defineId(ExtendedBreelandMerchantEntity.class, DataSerializers.INT);

    private List<UUID> escorts = new ArrayList<>();

    // items Bree-land Merchants can buy from players
    private static final ExtendedTradeEntries BREELAND_MERCHANT_BOUGHT = new ExtendedTradeEntries(ExtendedTradeEntries.TradeType.BOUGHT, new ExtendedTradeEntry[] {
            new ExtendedTradeEntry(new ItemStack(LOTRItems.HOBBIT_OVEN.get()), 22, 35, 8),
            new ExtendedTradeEntry(new ItemStack(LOTRItems.CHERRY_PIE.get()), 7, 13, 16),
            new ExtendedTradeEntry(new ItemStack(LOTRItems.APPLE_CRUMBLE.get()), 7, 13, 16),
            new ExtendedMultitradeEntry(new ExtendedTradeEntry[] {
                    new ExtendedTradeEntry(new ItemStack(LOTRItems.PIPEWEED_LEAF.get()), 1, 1, 192),
                    new ExtendedTradeEntry(new ItemStack(LOTRItems.PIPEWEED.get(), 6), 8, 16, 64),
                    new ExtendedTradeEntry(new ItemStack(LOTRItems.PIPEWEED_SEEDS.get(), 6), 6, 10, 16)}),
            new ExtendedTradeEntry(new ItemStack(ExtendedItems.HOBBIT_PANCAKE.get()), 4, 8, 128),
            new ExtendedTradeEntry(new ItemStack(LOTRItems.DRYSTONE.get(), 16), 2, 6, 16),
            new ExtendedTradeEntry(new ItemStack(LOTRItems.MAPLE_LOG.get(), 16), 2, 6, 16),
            new ExtendedTradeEntry(new ItemStack(LOTRItems.SMOKING_PIPE.get()), 8, 14, 1)
    });

    // items Bree-land Merchants can sell to players
    private static final ExtendedTradeEntries BREELAND_MERCHANT_SOLD = new ExtendedTradeEntries(ExtendedTradeEntries.TradeType.SOLD, new ExtendedTradeEntry[]{
            new ExtendedTradeEntry(new ItemStack(LOTRItems.BREE_CRAFTING_TABLE.get()), 75, 125, 8),
            new ExtendedMultitradeEntry(new ExtendedTradeEntry[]{
                    new ExtendedTradeEntry(new ItemStack(Items.IRON_SWORD), 9, 15, 8),
                    new ExtendedTradeEntry(new ItemStack(LOTRItems.BRONZE_SWORD.get()), 8, 13, 8),
            }),
            new ExtendedMultitradeEntry(new ExtendedTradeEntry[]{
                    new ExtendedTradeEntry(new ItemStack(LOTRItems.IRON_DAGGER.get()), 6, 10, 8),
                    new ExtendedTradeEntry(new ItemStack(LOTRItems.BRONZE_DAGGER.get()), 5, 9, 8),
            }),
            new ExtendedMultitradeEntry(new ExtendedTradeEntry[]{
                    new ExtendedTradeEntry(new ItemStack(Items.IRON_CHESTPLATE), 17, 28, 8),
                    new ExtendedTradeEntry(new ItemStack(Items.IRON_LEGGINGS), 14, 23, 8),
            }),
            new ExtendedMultitradeEntry(new ExtendedTradeEntry[]{
                    new ExtendedTradeEntry(new ItemStack(Items.IRON_HELMET), 12, 20, 8),
                    new ExtendedTradeEntry(new ItemStack(Items.IRON_BOOTS), 11, 18, 8),
            }),
            new ExtendedTradeEntry(new ItemStack(LOTRItems.MAPLE_SYRUP.get()), 2, 3, 16),
            new ExtendedMultitradeEntry(new ExtendedTradeEntry[]{
                    new ExtendedTradeEntry(DrinkUtils.drinkOfPotency(LOTRItems.MEAD.get(), VesselDrinkItem.Potency.LIGHT), 5, 8, 8),
                    new ExtendedTradeEntry(DrinkUtils.drinkOfPotency(LOTRItems.MEAD.get(), VesselDrinkItem.Potency.MODERATE), 6, 10, 8),
                    new ExtendedTradeEntry(DrinkUtils.drinkOfPotency(LOTRItems.MEAD.get(), VesselDrinkItem.Potency.STRONG), 8, 13, 8)
            }),
            new ExtendedMultitradeEntry(new ExtendedTradeEntry[]{
                    new ExtendedTradeEntry(DrinkUtils.drinkOfPotency(LOTRItems.MAPLE_BEER.get(), VesselDrinkItem.Potency.LIGHT), 5, 8, 8),
                    new ExtendedTradeEntry(DrinkUtils.drinkOfPotency(LOTRItems.MAPLE_BEER.get(), VesselDrinkItem.Potency.MODERATE), 6, 10, 8),
                    new ExtendedTradeEntry(DrinkUtils.drinkOfPotency(LOTRItems.MAPLE_BEER.get(), VesselDrinkItem.Potency.STRONG), 8, 13, 8)
            }),
            new ExtendedTradeEntry(new ItemStack(LOTRItems.IRON_SPEAR.get()), 11, 19, 8),
            new ExtendedTradeEntry(new ItemStack(LOTRItems.MAPLE_SAPLING.get()), 6, 10, 32)
    });

    public static AttributeModifierMap.MutableAttribute regAttrs() {
        return BreeManEntity.regAttrs();
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
        this.goalSelector.addGoal(6, new ExtendedInteractableFriendlyNPCConversationGoal(this, this,5.0E-4F));
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

    private ArrayList<ExtendedTrade> itemsSoldByTrader;
    private ArrayList<ExtendedTrade> itemsBoughtByTrader;

    public ExtendedBreelandMerchantEntity(EntityType<? extends ExtendedBreelandMerchantEntity> type, World w) {
        super(type, w);
        setPersistenceRequired();

        itemsBoughtByTrader = BREELAND_MERCHANT_BOUGHT.generateTrades();
        itemsSoldByTrader = BREELAND_MERCHANT_SOLD.generateTrades();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(PLAYER_TRADING_WITH, Optional.empty());
        this.entityData.define(IS_WANDERING_TRADER, false);
        // start at 1000 ticks just to make sure there's no chance they immediately despawn
        this.entityData.define(WANDERING_TRADER_TIME_LEFT, 1000);
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT tag) {
        super.addAdditionalSaveData(tag);

        saveAllTrades(tag);
        ExtendedTraderHelper.saveWanderingTraderData(this, escorts, tag);
    }

    @Override
    protected void addNPCTargetingAI() {
        this.addNonAggressiveTargetingGoals();
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT tag) {
        super.readAdditionalSaveData(tag);

        loadAllTrades(tag);

        // load values related to wandering traders
        int wanderingTraderTimeLeft = tag.getInt("wanderingTraderTimeLeft");
        entityData.set(WANDERING_TRADER_TIME_LEFT, wanderingTraderTimeLeft);

        boolean isWanderingTrader = tag.getBoolean("isWanderingTrader");
        entityData.set(IS_WANDERING_TRADER, isWanderingTrader);

        escorts = ExtendedTraderHelper.loadEscortUUIDs(tag);
    }

    // in Legacy the eat and drink pools for Oddment Collectors are based off Dunland
    protected NPCFoodPool getEatPool() {
        return NPCFoodPools.DUNLENDING;
    }

    protected NPCFoodPool getDrinkPool() {
        return NPCFoodPools.DUNLENDING_DRINK;
    }

    protected NPCNameGenerator getNameGenerator() {
        return NPCNameGenerators.BREE;
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

    public boolean canTradeWith(PlayerEntity player){
        return isFriendlyAndAlignedWithLevel(player, this, 0);
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
    public Inventory getDisplayInventory() {
        return TradeUtils.generateDisplayInventory(itemsSoldByTrader, itemsBoughtByTrader);
    }

    @Override
    public List<ExtendedTrade> getBuys() {
        return itemsBoughtByTrader;
    }

    @Override
    public int[] getBoughtPrices() {
        return ExtendedTraderHelper.getBoughtPrices(itemsBoughtByTrader);
    }

    @Override
    public int[] getBoughtQuantities() {
        return ExtendedTraderHelper.getBoughtQuantity(itemsBoughtByTrader);
    }

    @Override
    public int[] getSoldPrices() {
        return ExtendedTraderHelper.getSoldPrices(itemsSoldByTrader);
    }

    @Override
    public int[] getSoldQuantities() {
        return ExtendedTraderHelper.getSoldQuantity(itemsSoldByTrader);
    }

    @Override
    public int[] getSoldOriginalQuantities() {
        return ExtendedTraderHelper.getSoldOriginalQuantity(itemsSoldByTrader);
    }

    @Override
    public int[] getBoughtOriginalQuantities() {
        return ExtendedTraderHelper.getBoughtOriginalQuantity(itemsBoughtByTrader);
    }

    @Override
    public boolean isWanderingTrader() {
        return entityData.get(IS_WANDERING_TRADER);
    }

    @Override
    public void setWanderingTrader(boolean isWanderingTrader) {
        entityData.set(IS_WANDERING_TRADER, isWanderingTrader);
    }

    @Override
    public int getWanderingTraderTicksLeft() {
        return entityData.get(WANDERING_TRADER_TIME_LEFT);
    }

    @Override
    public void setWanderingTraderTicksLeft(int ticksLeft) {
        entityData.set(WANDERING_TRADER_TIME_LEFT, ticksLeft);
    }

    @Override
    public List<ExtendedTrade> getSells() {
        return itemsSoldByTrader;
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

        // every 1 minute allow one more trade in both buys and sells to be done again
        if (lastTickCooldownReduced >= 1200) {
            for (ExtendedTrade trade : itemsSoldByTrader) {
                if (trade.getQuantityLeft() < trade.getOriginalQuantity()) {
                    trade.setQuantityLeft(trade.getQuantityLeft() + 1);
                }
            }

            for (ExtendedTrade trade : itemsBoughtByTrader) {
                if (trade.getQuantityLeft() < trade.getOriginalQuantity()) {
                    trade.setQuantityLeft(trade.getQuantityLeft() + 1);
                }
            }
            lastTickCooldownReduced = 0;
        } else {
            lastTickCooldownReduced++;
        }

        ExtendedTraderHelper.tickTrader(this, this);
    }

    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT dataTag) {
        spawnData = super.finalizeSpawn(sw, diff, reason, spawnData, dataTag);
        this.npcItemsInv.setMeleeWeapon(WEAPONS.getRandomItem(this.random));

        this.setItemSlot(EquipmentSlotType.HEAD, new ItemStack(ExtendedItems.LEATHER_HAT.get()));

        return spawnData;
    }

    @Override
    public TranslationTextComponent getDepartureLine(PlayerEntity player) {
        return new TranslationTextComponent("speechbank.breeland_merchant.departure." + (random.nextInt(5) + 1), getName(), player.getName());
    }

    static {
        WEAPONS = SpawnEquipmentTable.of(LOTRItems.IRON_DAGGER, LOTRItems.BRONZE_DAGGER);
    }

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
        NPCEntity entity = LOTREntities.BREE_GUARD.get().create(level);
        return entity;
    }

	@Override
	public void setBuys(ArrayList<ExtendedTrade> trades) {
		itemsBoughtByTrader = trades;
	}

	@Override
	public void setSells(ArrayList<ExtendedTrade> trades) {
		itemsSoldByTrader = trades;		
	}
}

