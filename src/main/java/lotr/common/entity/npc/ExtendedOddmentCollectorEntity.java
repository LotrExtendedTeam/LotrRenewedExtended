package lotr.common.entity.npc;

import lotr.common.entity.npc.ai.goal.*;
import lotr.common.entity.npc.data.NPCFoodPool;
import lotr.common.entity.npc.data.NPCFoodPools;
import lotr.common.entity.npc.data.NPCGenderProvider;
import lotr.common.entity.npc.data.name.NPCNameGenerator;
import lotr.common.entity.npc.data.name.NPCNameGenerators;
import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRItems;
import lotr.common.util.ExtendedTraderHelper;
import lotr.common.util.TradeUtils;
import lotr.common.util.representation.ExtendedTrade;
import lotr.common.util.representation.ExtendedTradeEntries;
import lotr.common.util.representation.ExtendedTradeEntry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
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

public class ExtendedOddmentCollectorEntity extends ManEntity implements ExtendedTraderEntity {

    private static final SpawnEquipmentTable WEAPONS;

    int lastTickCooldownReduced = 0;
    int lastTickVerifiedPlayerTradingWithIsOnline = 0;

    private static final DataParameter<Optional<UUID>> PLAYER_TRADING_WITH = EntityDataManager.defineId(ExtendedOddmentCollectorEntity.class, DataSerializers.OPTIONAL_UUID);

    // items Oddment Collectors can buy from players
    // TODO: Following Items missing from Renewed Extended:
    //  Party Hat, Blue Dwarven Steel Ingot
    // TODO: Add coral? I don't think it is accessible in the world so maybe it makes sense to keep out for now
    private static final ExtendedTradeEntries ODDMENT_COLLECTOR_BOUGHT = new ExtendedTradeEntries(ExtendedTradeEntries.TradeType.BOUGHT, new ExtendedTradeEntry[] {
            new ExtendedTradeEntry(new ItemStack(ExtendedItems.BLACK_URUK_STEEL_INGOT.get()), 4, 6, 192),
            new ExtendedTradeEntry(new ItemStack(LOTRItems.BRONZE_INGOT.get()), 2, 4, 192),
            new ExtendedTradeEntry(new ItemStack(LOTRItems.COPPER_INGOT.get()), 2, 4, 192),
            new ExtendedTradeEntry(new ItemStack(LOTRItems.DWARVEN_STEEL_INGOT.get()), 4, 6, 192),
            new ExtendedTradeEntry(new ItemStack(LOTRItems.ELVEN_STEEL_INGOT.get()), 4, 6, 192),
            new ExtendedTradeEntry(new ItemStack(Items.FLINT), 1, 1, 192),
            new ExtendedTradeEntry(new ItemStack(Items.IRON_INGOT), 2, 4, 192),
            new ExtendedTradeEntry(new ItemStack(Items.LEATHER), 2, 3, 192),
            new ExtendedTradeEntry(new ItemStack(Items.ROTTEN_FLESH), 2, 3, 192),
            new ExtendedTradeEntry(new ItemStack(LOTRItems.FUR.get()), 2, 3, 192),
            new ExtendedTradeEntry(new ItemStack(Items.STRING, 2), 1, 1, 96),
            new ExtendedTradeEntry(new ItemStack(Items.WHEAT_SEEDS, 2), 1, 1, 96),
            new ExtendedTradeEntry(new ItemStack(Items.STICK, 8), 1, 1, 24),
            new ExtendedTradeEntry(new ItemStack(LOTRItems.TIN_INGOT.get()), 2, 4, 192),
            new ExtendedTradeEntry(new ItemStack(LOTRItems.URUK_STEEL_INGOT.get()), 4, 6, 192),
            new ExtendedTradeEntry(new ItemStack(LOTRItems.ORC_STEEL_INGOT.get()), 2, 4, 192),
            new ExtendedTradeEntry(new ItemStack(ExtendedItems.DIAMOND.get()), 19, 31, 128),
            new ExtendedTradeEntry(new ItemStack(Items.EMERALD, 8), 11, 19, 128),
            new ExtendedTradeEntry(new ItemStack(ExtendedItems.SAPPHIRE.get()), 9, 15, 128),
            new ExtendedTradeEntry(new ItemStack(ExtendedItems.RUBY.get()), 9, 15, 128),
            new ExtendedTradeEntry(new ItemStack(ExtendedItems.OPAL.get()), 8, 13, 128),
            new ExtendedTradeEntry(new ItemStack(ExtendedItems.AMETHYST.get()), 6, 10, 128),
            new ExtendedTradeEntry(new ItemStack(ExtendedItems.TOPAZ.get()), 6, 10, 128),
            new ExtendedTradeEntry(new ItemStack(ExtendedItems.PEARL.get()), 19, 31, 128),
            new ExtendedTradeEntry(new ItemStack(Items.COAL, 2), 1, 1, 192),
            new ExtendedTradeEntry(new ItemStack(ExtendedItems.OBSIDIAN_SHARD.get()), 2, 3, 192),
            new ExtendedTradeEntry(new ItemStack(Items.FEATHER), 2, 3, 192),
            new ExtendedTradeEntry(new ItemStack(LOTRItems.HORN.get()), 2, 4, 192),
            new ExtendedTradeEntry(new ItemStack(ExtendedItems.CONKER.get()), 1, 1, 192),
    });

    // items Oddment Collectors can sell to players
    private static final ExtendedTradeEntries ODDMENT_COLLECTOR_SOLD = new ExtendedTradeEntries(ExtendedTradeEntries.TradeType.SOLD, new ExtendedTradeEntry[] {
            new ExtendedTradeEntry(new ItemStack(LOTRItems.SHIRE_HEATHER.get()), 6, 10), new ExtendedTradeEntry(new ItemStack(LOTRItems.SUSPICIOUS_MEAT.get()), 5, 9)
    });

    // give them the 5 armor points they have in Legacy
    public static AttributeModifierMap.MutableAttribute regAttrs() {
        return ManEntity.regAttrs().add(Attributes.ARMOR, 5.0);
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
        this.goalSelector.addGoal(8, new ExtendedTraderWaterAvoidingRandomWalkingGoal(this, this, 1.0));
        this.addConsumingGoals(9);
        this.goalSelector.addGoal(10, new LookAtGoal(this, PlayerEntity.class, 8.0F, 0.02F));
        this.goalSelector.addGoal(10, new LookAtGoal(this, NPCEntity.class, 5.0F, 0.02F));
        this.goalSelector.addGoal(11, new LookAtGoal(this, LivingEntity.class, 8.0F, 0.02F));
        this.goalSelector.addGoal(12, new LookRandomlyGoal(this));
    }

    @Override
    public void updatePlayerTradingWith(UUID uuid) {
        this.entityData.set(PLAYER_TRADING_WITH, Optional.of(uuid));
    }

    @Override
    public Optional<UUID> getPlayerTradingWith() {
        return this.entityData.get(PLAYER_TRADING_WITH);
    }

    private ArrayList<ExtendedTrade> itemsSoldByTrader;
    private ArrayList<ExtendedTrade> itemsBoughtByTrader;

    public ExtendedOddmentCollectorEntity(EntityType<? extends ExtendedOddmentCollectorEntity> type, World w) {
        super(type, w);
        setPersistenceRequired();

        itemsBoughtByTrader = ODDMENT_COLLECTOR_BOUGHT.generateTrades();
        itemsSoldByTrader = ODDMENT_COLLECTOR_SOLD.generateTrades();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(PLAYER_TRADING_WITH, Optional.empty());
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT tag) {
        super.addAdditionalSaveData(tag);

        TradeUtils.saveAllTrades(ExtendedTradeEntries.TradeType.BOUGHT, itemsBoughtByTrader, tag);
        TradeUtils.saveAllTrades(ExtendedTradeEntries.TradeType.SOLD, itemsSoldByTrader, tag);
    }

    @Override
    protected void addNPCTargetingAI() {
        this.addNonAggressiveTargetingGoals();
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT tag) {
        super.readAdditionalSaveData(tag);

        if (TradeUtils.loadAllTrades(ExtendedTradeEntries.TradeType.BOUGHT, tag) != null) {
            itemsBoughtByTrader = TradeUtils.loadAllTrades(ExtendedTradeEntries.TradeType.BOUGHT, tag);
        }
        if (TradeUtils.loadAllTrades(ExtendedTradeEntries.TradeType.SOLD, tag) != null) {
            itemsSoldByTrader = TradeUtils.loadAllTrades(ExtendedTradeEntries.TradeType.SOLD, tag);
        }
    }

    // in Legacy the eat and drink pools for Oddment Collectors are based off Dunland
    protected NPCFoodPool getEatPool() {
        return NPCFoodPools.DUNLENDING;
    }

    protected NPCFoodPool getDrinkPool() {
        return NPCFoodPools.DUNLENDING_DRINK;
    }

    protected NPCNameGenerator getNameGenerator() {
        // TODO: In legacy it can pick Rohan, Gondor, Dunlend or Bree for names, but I am not sure how that will work
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
        return ExtendedTraderHelper.isInteractingWithPlayer(getPlayerTradingWith());
    }

    @Override
    public void removePlayerFromTrading(PlayerEntity player) {
        if (this.entityData.get(PLAYER_TRADING_WITH).isPresent() && this.entityData.get(PLAYER_TRADING_WITH).get().equals(player.getUUID())) {
            this.entityData.set(PLAYER_TRADING_WITH, Optional.empty());
        }
    }

    // TODO: Maybe some sort of negative reputation limit should prevent a player from trading but for now I don't want killing
    //  an Oddment Collector once to prevent you from being able to sell to them
    public boolean canTradeWith(PlayerEntity player) {
        return this.isNotFighting(player);
    }

    @Override
    public ActionResultType mobInteract(PlayerEntity player, Hand hand) {
        if (canTradeWith(player)) {
            return ExtendedTraderHelper.openTraderGui(this, player, this);
        } else {
            if (!player.level.isClientSide)
                player.sendMessage(new TranslationTextComponent("gui.lotrextended.trading.trader_alignment_too_low"), player.getUUID());
        }

        return super.mobInteract(player, hand);
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

        super.tick();
    }

    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT dataTag) {
        spawnData = super.finalizeSpawn(sw, diff, reason, spawnData, dataTag);
        this.npcItemsInv.setMeleeWeapon(WEAPONS.getRandomItem(this.random));
        this.npcItemsInv.setIdleItem(new ItemStack(LOTRItems.SHIRE_HEATHER.get()));

        this.setItemSlot(EquipmentSlotType.HEAD, new ItemStack(ExtendedItems.LEATHER_HAT.get()));

        return spawnData;
    }

    static {
        WEAPONS = SpawnEquipmentTable.of(LOTRItems.IRON_DAGGER, LOTRItems.BRONZE_DAGGER);
    }

    protected NPCGenderProvider getGenderProvider() {
        return NPCGenderProvider.MALE;
    }

}

