package lotr.common.entity.npc;

import lotr.common.block.VesselDrinkBlock;
import lotr.common.entity.npc.ai.AttackGoalsHolder;
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
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.player.PlayerEntity;
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

public class ExtendedBlueMountainsMerchantEntity extends BlueDwarfEntity implements ExtendedTraderEntity {

    private static final SpawnEquipmentTable WEAPONS;

    int lastTickCooldownReduced = 0;
    int lastTickVerifiedPlayerTradingWithIsOnline = 0;

    private static final DataParameter<Optional<UUID>> PLAYER_TRADING_WITH = EntityDataManager.defineId(ExtendedBlueMountainsMerchantEntity.class, DataSerializers.OPTIONAL_UUID);
    private static final DataParameter<Boolean> IS_WANDERING_TRADER = EntityDataManager.defineId(ExtendedBlueMountainsMerchantEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> WANDERING_TRADER_TIME_LEFT = EntityDataManager.defineId(ExtendedBlueMountainsMerchantEntity.class, DataSerializers.INT);

    private List<UUID> escorts = new ArrayList<>();

    // items Blue Mountains Merchants buy from players
    private static final ExtendedTradeEntries BLUE_MOUNTAINS_BOUGHT = new ExtendedTradeEntries(ExtendedTradeEntries.TradeType.BOUGHT, new ExtendedTradeEntry[] {
            // TODO: Add Venison when it's added
            new ExtendedTradeEntry(new ItemStack(Items.BREAD), 2, 3, 192), new ExtendedTradeEntry(new ItemStack(LOTRItems.BRONZE_INGOT.get()), 2, 4, 192),
            new ExtendedTradeEntry(new ItemStack(Items.COAL, 2), 1, 1, 192), new ExtendedTradeEntry(new ItemStack(Items.COOKED_CHICKEN), 2, 4, 192),
            new ExtendedTradeEntry(new ItemStack(Items.COOKED_COD), 2, 4, 192), new ExtendedTradeEntry(new ItemStack(Items.COOKED_SALMON), 2, 4, 192),
            new ExtendedTradeEntry(new ItemStack(Items.COOKED_MUTTON), 2, 4, 192), new ExtendedTradeEntry(new ItemStack(Items.COOKED_PORKCHOP), 2, 4, 192),
            new ExtendedTradeEntry(new ItemStack(Items.COOKED_RABBIT), 2, 4, 192), new ExtendedTradeEntry(new ItemStack(LOTRItems.COPPER_INGOT.get()), 2, 4, 192),
            new ExtendedTradeEntry(new ItemStack(LOTRItems.GAMMON.get()), 2, 4), new ExtendedTradeEntry(new ItemStack(Items.GOLD_INGOT), 11, 19),
            new ExtendedTradeEntry(new ItemStack(Items.IRON_INGOT), 2, 4), new ExtendedTradeEntry(new ItemStack(Items.COOKED_BEEF), 2, 4),
            new ExtendedTradeEntry(new ItemStack(LOTRItems.TIN_INGOT.get()), 2, 4), new ExtendedTradeEntry(new ItemStack(ExtendedItems.DIAMOND.get()), 19, 31),
            new ExtendedTradeEntry(new ItemStack(ExtendedItems.SAPPHIRE.get()), 9, 15), new ExtendedTradeEntry(new ItemStack(ExtendedItems.OPAL.get()), 8, 13),
            new ExtendedTradeEntry(new ItemStack(ExtendedItems.PEARL.get()), 19, 31)
    });

    // items Blue Mountains Merchants call sell to players
    private static final ExtendedTradeEntries BLUE_MOUNTAINS_SOLD = new ExtendedTradeEntries(ExtendedTradeEntries.TradeType.SOLD, new ExtendedTradeEntry[] {
            new ExtendedTradeEntry(new ItemStack(LOTRItems.BLUE_DWARVEN_HELMET.get()), 19, 31), new ExtendedTradeEntry(new ItemStack(LOTRItems.BLUE_DWARVEN_CHESTPLATE.get()), 27, 45),
            new ExtendedTradeEntry(new ItemStack(LOTRItems.BLUE_DWARVEN_LEGGINGS.get()), 23, 38), new ExtendedTradeEntry(new ItemStack(LOTRItems.BLUE_DWARVEN_BOOTS.get()), 17, 38),
            new ExtendedTradeEntry(new ItemStack(LOTRItems.BLUE_DWARVEN_AXE.get()), 11, 19), new ExtendedTradeEntry(new ItemStack(LOTRItems.BLUE_DWARVEN_DAGGER.get()), 10, 16),
            new ExtendedTradeEntry(new ItemStack(LOTRItems.BLUE_DWARVEN_PICKAXE.get()), 11, 18), new ExtendedTradeEntry(new ItemStack(LOTRItems.BLUE_DWARVEN_SHOVEL.get()), 9, 15),
            new ExtendedTradeEntry(new ItemStack(LOTRItems.BLUE_DWARVEN_SPEAR.get()), 14, 23), new ExtendedTradeEntry(new ItemStack(LOTRItems.BLUE_DWARVEN_SWORD.get()), 12, 20),
            new ExtendedTradeEntry(new ItemStack(Items.GLOWSTONE_DUST, 4), 2, 3), new ExtendedTradeEntry(new ItemStack(LOTRItems.DWARVEN_FORGE.get()), 30, 50),
            new ExtendedTradeEntry(new ItemStack(ExtendedItems.DWARF_MARRIAGE_RING.get()), 12, 20), new ExtendedTradeEntry(new ItemStack(LOTRItems.GLOWING_DWARVEN_BRICK.get()), 3, 5),
            new ExtendedTradeEntry(new ItemStack(LOTRItems.GLOWING_DWARVEN_BRICK.get()), 3, 5), new ExtendedTradeEntry(new ItemStack(ExtendedItems.EMERALD.get()), 19, 31),
            new ExtendedTradeEntry(new ItemStack(ExtendedItems.RUBY.get()), 15, 25), new ExtendedTradeEntry(new ItemStack(ExtendedItems.AMETHYST.get()), 9, 15),
            new ExtendedTradeEntry(new ItemStack(ExtendedItems.TOPAZ.get()), 9, 15), new ExtendedTradeEntry(new ItemStack(LOTRItems.BLUE_ROCK.get(), 8), 2, 3),
            new ExtendedMultitradeEntry(new ExtendedTradeEntry[] {
                    new ExtendedTradeEntry(DrinkUtils.drinkOfPotency(LOTRItems.DWARVEN_TONIC.get(), VesselDrinkItem.Potency.LIGHT), 8, 13, 8),
                    new ExtendedTradeEntry(DrinkUtils.drinkOfPotency(LOTRItems.DWARVEN_TONIC.get(), VesselDrinkItem.Potency.MODERATE), 11, 18, 8),
                    new ExtendedTradeEntry(DrinkUtils.drinkOfPotency(LOTRItems.DWARVEN_TONIC.get(), VesselDrinkItem.Potency.STRONG), 13, 22, 8)}
            ),
            new ExtendedMultitradeEntry(new ExtendedTradeEntry[] {
                    new ExtendedTradeEntry(DrinkUtils.drinkOfPotency(LOTRItems.DWARVEN_ALE.get(), VesselDrinkItem.Potency.LIGHT), 5, 8, 16),
                    new ExtendedTradeEntry(DrinkUtils.drinkOfPotency(LOTRItems.DWARVEN_ALE.get(), VesselDrinkItem.Potency.MODERATE), 7, 11, 16),
                    new ExtendedTradeEntry(DrinkUtils.drinkOfPotency(LOTRItems.DWARVEN_ALE.get(), VesselDrinkItem.Potency.STRONG), 8, 14, 16)}
            ),
            // TODO: Add the following: (looks pretty difficult to do might have to be in a follow up update)
            //  Barrel of Dwarven Ale (Moderate) 	90-150
            //  Barrel of Dwarven Ale (Strong)      120-200
    });

    public static AttributeModifierMap.MutableAttribute regAttrs() {
        return DwarfEntity.regAttrs().add(Attributes.ARMOR, 0.0);
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
        this.addAttackGoal(3);
        this.goalSelector.addGoal(9, new OpenDoorGoal(this, true));
        this.goalSelector.addGoal(10, new ExtendedTraderTalkToCurrentGoal(this, this));
        this.goalSelector.addGoal(11, new ExtendedTraderFriendlyNPCConversationGoal(this, this, 0.001F));
        this.goalSelector.addGoal(12, new WatchSunriseSunsetGoal(this, 0.001F));
        this.goalSelector.addGoal(13, new ExtendedTraderWaterAvoidingRandomWalkingGoal(this, this, 1.0));
        this.goalSelector.addGoal(14, new NPCEatGoal(this, this.getEatPool(), 6000));
        this.goalSelector.addGoal(14, new NPCDrinkGoal(this, this.getDrinkPool(), 6000));
        this.goalSelector.addGoal(15, new LookAtGoal(this, PlayerEntity.class, 8.0F, 0.02F));
        this.goalSelector.addGoal(15, new LookAtGoal(this, NPCEntity.class, 5.0F, 0.02F));
        this.goalSelector.addGoal(16, new LookAtGoal(this, LivingEntity.class, 8.0F, 0.02F));
        this.goalSelector.addGoal(17, new LookRandomlyGoal(this));
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

    public ExtendedBlueMountainsMerchantEntity(EntityType<? extends ExtendedBlueMountainsMerchantEntity> type, World w) {
        super(type, w);
        setPersistenceRequired();

        itemsBoughtByTrader = BLUE_MOUNTAINS_BOUGHT.generateTrades();
        itemsSoldByTrader = BLUE_MOUNTAINS_SOLD.generateTrades();
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

    @Override
    protected void initialiseAttackGoals(AttackGoalsHolder holder) {
        holder.setMeleeAttackGoal(this.createDwarfAttackGoal());
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
    	ExtendedTraderHelper.removePlayerFromTrading(player, entityData, PLAYER_TRADING_WITH);
    }

    public boolean canTradeWith(PlayerEntity player) {
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
        this.npcItemsInv.clearIdleItem();

        return spawnData;
    }

    @Override
    public TranslationTextComponent getDepartureLine(PlayerEntity player) {
        return new TranslationTextComponent("speechbank.blue_mountains_merchant.departure." + (random.nextInt(10) + 1), getName(), player.getName());
    }

    static {
        WEAPONS = SpawnEquipmentTable.of(LOTRItems.BLUE_DWARVEN_DAGGER);
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
        return LOTREntities.BLUE_MOUNTAINS_DWARF.get().create(level);
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

