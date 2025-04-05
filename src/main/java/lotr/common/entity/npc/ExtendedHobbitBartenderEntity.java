package lotr.common.entity.npc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import lotr.common.entity.npc.ai.goal.*;
import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRItems;
import lotr.common.item.VesselDrinkItem.Potency;
import lotr.common.util.DrinkUtils;
import lotr.common.util.ExtendedInteractableHelper;
import lotr.common.util.ExtendedTraderHelper;
import lotr.common.util.representation.ExtendedMultitradeEntry;
import lotr.common.util.representation.ExtendedTrade;
import lotr.common.util.representation.ExtendedTradeEntries;
import lotr.common.util.representation.ExtendedTradeEntry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.OpenDoorGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.player.PlayerEntity;
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
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

/**
 * @author RichardB122
 */
public class ExtendedHobbitBartenderEntity extends HobbitEntity implements ExtendedTraderEntity {

    int lastTickCooldownReduced = 0;
    int lastTickVerifiedPlayerTradingWithIsOnline = 0;

    private static final DataParameter<Optional<UUID>> PLAYER_TRADING_WITH = EntityDataManager.defineId(ExtendedHobbitBartenderEntity.class, DataSerializers.OPTIONAL_UUID);

    // trading pools that bartenders will buy from players
    private final ExtendedTradeEntries HOBBIT_BARTENDER_BOUGHT = new ExtendedTradeEntries(ExtendedTradeEntries.TradeType.BOUGHT, new ExtendedTradeEntry[] {
            new ExtendedTradeEntry(128, new ItemStack(Items.BEEF), 2, 4), 
            new ExtendedTradeEntry(128, new ItemStack(Items.PORKCHOP), 2, 4),
            new ExtendedTradeEntry(128, new ItemStack(Items.MUTTON), 2, 4), 
            new ExtendedTradeEntry(128, new ItemStack(Items.CHICKEN), 2, 4),
            new ExtendedTradeEntry(128, new ItemStack(Items.RABBIT), 2, 4), 
            new ExtendedTradeEntry(128, new ItemStack(ExtendedItems.RAW_DUCK.get()), 2, 4),
            new ExtendedTradeEntry(new ItemStack(LOTRItems.CLAY_PLATE.get()), 1, 1), 
            new ExtendedTradeEntry(new ItemStack(LOTRItems.CLAY_MUG.get()), 1, 1),
            new ExtendedTradeEntry(160, new ItemStack(LOTRItems.PIPEWEED_LEAF.get()), 1, 1), 
            new ExtendedTradeEntry(128, new ItemStack(Items.POTATO, 3), 1, 1),
            new ExtendedTradeEntry(128, new ItemStack(Items.COD), 2, 3), 
            new ExtendedTradeEntry(128, new ItemStack(Items.SALMON), 2, 3),
            new ExtendedTradeEntry(32, new ItemStack(Items.WATER_BUCKET), 3, 5), 
            new ExtendedTradeEntry(128, new ItemStack(Items.COAL, 2), 1, 1),
            new ExtendedTradeEntry(128, new ItemStack(LOTRItems.SALT.get()), 8, 13), 
            new ExtendedTradeEntry(16, new ItemStack(Items.BUCKET), 2, 4),
            new ExtendedTradeEntry(32, new ItemStack(LOTRItems.FOUR_LEAF_CLOVER.get()), 30, 50)
    });

    // items you can buy from a hobbit bartender
    private final ExtendedTradeEntries HOBBIT_BARTENDER_SOLD = new ExtendedTradeEntries(ExtendedTradeEntries.TradeType.SOLD, new ExtendedTradeEntry[] {
            new ExtendedTradeEntry(32, new ItemStack(Items.COOKED_BEEF), 5, 9), 
            new ExtendedTradeEntry(32, new ItemStack(Items.COOKED_PORKCHOP), 5, 9),
            new ExtendedTradeEntry(32, new ItemStack(Items.COOKED_MUTTON), 5, 9), 
            new ExtendedTradeEntry(32, new ItemStack(Items.COOKED_CHICKEN), 5, 8),
            new ExtendedTradeEntry(32, new ItemStack(Items.COOKED_RABBIT), 5, 9), 
            new ExtendedTradeEntry(32, new ItemStack(ExtendedItems.DUCK.get()), 5, 8),
            new ExtendedTradeEntry(40, new ItemStack(Items.BREAD), 4, 6), 
            new ExtendedTradeEntry(64, new ItemStack(LOTRItems.PIPEWEED.get(), 6), 5, 8),
            new ExtendedTradeEntry(8, new ItemStack(LOTRItems.SMOKING_PIPE.get()), 11, 19), 
            new ExtendedTradeEntry(64, new ItemStack(LOTRItems.WOODEN_MUG.get()), 2, 3),
            new ExtendedTradeEntry(64, new ItemStack(LOTRItems.CERAMIC_MUG.get()), 2, 3), 
            new ExtendedTradeEntry(32, new ItemStack(Items.BAKED_POTATO, 2), 5, 9),
            new ExtendedTradeEntry(32, new ItemStack(Items.COOKED_SALMON), 5, 8), 
            new ExtendedTradeEntry(32, new ItemStack(Items.COOKED_COD), 5, 8),
            new ExtendedTradeEntry(20, new ItemStack(LOTRItems.CHOCOLATE_DRINK.get()), 3, 5), 
            new ExtendedTradeEntry(20, new ItemStack(LOTRItems.APPLE_JUICE.get()), 5, 8),
            new ExtendedTradeEntry(32, new ItemStack(LOTRItems.GAMMON.get()), 5, 9), 
            new ExtendedTradeEntry(8, new ItemStack(Items.MUSHROOM_STEW), 8, 13),
            new ExtendedTradeEntry(32, new ItemStack(LOTRItems.FINE_PLATE.get()), 2, 3), 
            new ExtendedTradeEntry(8, new ItemStack(ExtendedItems.HOBBIT_MARRIAGE_RING.get()), 11, 18),
            new ExtendedTradeEntry(2, new ItemStack(LOTRItems.HOBBIT_OVEN.get()), 30, 50), 
            new ExtendedTradeEntry(32, new ItemStack(LOTRItems.STONEWARE_PLATE.get()), 2, 3),
            new ExtendedTradeEntry(2, new ItemStack(LOTRItems.HOBBIT_CRAFTING_TABLE.get()), 30, 50), 
            new ExtendedTradeEntry(16, new ItemStack(ExtendedItems.HOBBIT_PANCAKE.get()), 5, 8),
            // multitrade entries for the drinks to keep the different levels of potency mutual exclusive
            new ExtendedMultitradeEntry(new ExtendedTradeEntry[] {
            		new ExtendedTradeEntry(16, DrinkUtils.drinkOfPotency(LOTRItems.ALE.get(), Potency.LIGHT), 5, 8), 
            		new ExtendedTradeEntry(16, DrinkUtils.drinkOfPotency(LOTRItems.ALE.get(), Potency.MODERATE), 6, 10), 
            		new ExtendedTradeEntry(16, DrinkUtils.drinkOfPotency(LOTRItems.ALE.get(), Potency.STRONG), 8, 13)}),
            new ExtendedMultitradeEntry(new ExtendedTradeEntry[] {
            		new ExtendedTradeEntry(16, DrinkUtils.drinkOfPotency(LOTRItems.CIDER.get(), Potency.LIGHT), 5, 8), 
            		new ExtendedTradeEntry(16, DrinkUtils.drinkOfPotency(LOTRItems.CIDER.get(), Potency.MODERATE), 6, 10), 
            		new ExtendedTradeEntry(16, DrinkUtils.drinkOfPotency(LOTRItems.CIDER.get(), Potency.STRONG), 8, 13)}),
            new ExtendedMultitradeEntry(new ExtendedTradeEntry[] {
            		new ExtendedTradeEntry(16, DrinkUtils.drinkOfPotency(LOTRItems.PERRY.get(), Potency.LIGHT), 5, 8), 
            		new ExtendedTradeEntry(16, DrinkUtils.drinkOfPotency(LOTRItems.PERRY.get(), Potency.MODERATE), 6, 10), 
            		new ExtendedTradeEntry(16, DrinkUtils.drinkOfPotency(LOTRItems.PERRY.get(), Potency.STRONG), 8, 13)})
    });

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

    public ExtendedHobbitBartenderEntity(EntityType<? extends HobbitEntity> type, World w) {
        super(type, w);
        setPersistenceRequired();

        itemsBoughtByTrader = HOBBIT_BARTENDER_BOUGHT.generateTrades();
        itemsSoldByTrader = HOBBIT_BARTENDER_SOLD.generateTrades();
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
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, OrcEntity.class, 12.0F, 1.5, 1.8));
        this.goalSelector.addGoal(2, new PanicGoal(this, 1.6));
        this.goalSelector.addGoal(9, new OpenDoorGoal(this, true));
        this.goalSelector.addGoal(10, new ExtendedInteractableTalkToCurrentGoal(this, this));
        this.goalSelector.addGoal(11, new ExtendedInteractableFriendlyNPCConversationGoal(this, this, 0.001F));
        this.goalSelector.addGoal(12, new WatchSunriseSunsetGoal(this, 0.01F));
        this.goalSelector.addGoal(13, new ExtendedInteractableWaterAvoidingRandomWalkingGoal(this, this, 1.1));
        this.goalSelector.addGoal(15, new LookAtGoal(this, PlayerEntity.class, 8.0F, 0.05F));
        this.goalSelector.addGoal(15, new LookAtGoal(this, NPCEntity.class, 5.0F, 0.05F));
        this.goalSelector.addGoal(16, new LookAtGoal(this, LivingEntity.class, 8.0F, 0.02F));
        this.goalSelector.addGoal(17, new LookRandomlyGoal(this));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(PLAYER_TRADING_WITH, Optional.empty());
    }

    @Override
	public boolean canTradeWith(PlayerEntity player) {
        return isFriendlyAndAlignedWithLevel(player, this, 0.0F);
    }
    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return HobbitEntity.regAttrs();
    }

    @Override
    public ActionResultType mobInteract(PlayerEntity player, Hand hand) {
        return ExtendedTraderHelper.mobInteract(this, this, player, hand);
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT tag) {
        super.addAdditionalSaveData(tag);

        saveAllTrades(tag);
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT tag) {
        super.readAdditionalSaveData(tag);

        loadAllTrades(tag);
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
    public List<ExtendedTrade> getBuys() {
        return itemsBoughtByTrader;
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

        ExtendedTraderHelper.tickTrader(this, this);
    }

	@Override
	public void setBuys(ArrayList<ExtendedTrade> trades) {
		itemsBoughtByTrader = trades;
	}

	@Override
	public void setSells(ArrayList<ExtendedTrade> trades) {
		itemsSoldByTrader = trades;
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
    public ILivingEntityData finalizeSpawn(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT dataTag) {
        spawnData = super.finalizeSpawn(sw, diff, reason, spawnData, dataTag);
        this.npcItemsInv.setIdleItem(new ItemStack(LOTRItems.WOODEN_MUG.get()));
        return spawnData;
    }
}
