package lotr.common.entity.npc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import lotr.common.entity.npc.ai.goal.*;
import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRItems;
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
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.OpenDoorGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
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
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class ExtendedBreelandPotterEntity extends BreeManEntity implements ExtendedTraderEntity {

    int lastTickCooldownReduced = 0;
    int lastTickVerifiedPlayerTradingWithIsOnline = 0;

    private static final DataParameter<Optional<UUID>> PLAYER_TRADING_WITH = EntityDataManager.defineId(ExtendedBreelandPotterEntity.class, DataSerializers.OPTIONAL_UUID);

    private final ExtendedTradeEntries BREE_POTTER_BOUGHT = new ExtendedTradeEntries(ExtendedTradeEntries.TradeType.BOUGHT, new ExtendedTradeEntry[] {
    		new ExtendedTradeEntry(new ItemStack(Items.CLAY_BALL, 4), 1, 1), 
            new ExtendedTradeEntry(new ItemStack(Items.CLAY), 1, 1), 
            new ExtendedTradeEntry(new ItemStack(Items.BUCKET), 2, 4, 16), 
            new ExtendedTradeEntry(new ItemStack(Items.WATER_BUCKET), 3, 5, 16),
            new ExtendedTradeEntry(new ItemStack(Items.WOODEN_SHOVEL), 1, 2, 32),
            new ExtendedTradeEntry(new ItemStack(Items.STONE_SHOVEL), 1, 2, 32),
            new ExtendedTradeEntry(new ItemStack(LOTRItems.BRONZE_SHOVEL.get()), 2, 6, 16),
            new ExtendedTradeEntry(new ItemStack(Items.IRON_SHOVEL), 2, 8, 16),
            new ExtendedMultitradeEntry(new ExtendedTradeEntry[] {
                    new ExtendedTradeEntry(new ItemStack(Items.BLACK_DYE), 1, 1),
                    new ExtendedTradeEntry(new ItemStack(Items.BLUE_DYE), 1, 1),
                    new ExtendedTradeEntry(new ItemStack(Items.BROWN_DYE), 1, 1),
                    new ExtendedTradeEntry(new ItemStack(Items.CYAN_DYE), 1, 1),
                    new ExtendedTradeEntry(new ItemStack(Items.GRAY_DYE), 1, 1),
                    new ExtendedTradeEntry(new ItemStack(Items.GREEN_DYE), 1, 1),
                    new ExtendedTradeEntry(new ItemStack(Items.LIGHT_BLUE_DYE), 1, 1),
                    new ExtendedTradeEntry(new ItemStack(Items.LIGHT_GRAY_DYE), 1, 1),
                    new ExtendedTradeEntry(new ItemStack(Items.LIME_DYE), 1, 1),
                    new ExtendedTradeEntry(new ItemStack(Items.MAGENTA_DYE), 1, 1),
                    new ExtendedTradeEntry(new ItemStack(Items.ORANGE_DYE), 1, 1),
                    new ExtendedTradeEntry(new ItemStack(Items.PINK_DYE), 1, 1),
                    new ExtendedTradeEntry(new ItemStack(Items.PURPLE_DYE), 1, 1),
                    new ExtendedTradeEntry(new ItemStack(Items.RED_DYE), 1, 1),
                    new ExtendedTradeEntry(new ItemStack(Items.WHITE_DYE), 1, 1),
                    new ExtendedTradeEntry(new ItemStack(Items.YELLOW_DYE), 1, 1)})
    });

    private final ExtendedTradeEntries BREE_POTTER_SOLD = new ExtendedTradeEntries(ExtendedTradeEntries.TradeType.SOLD, new ExtendedTradeEntry[] {
            new ExtendedTradeEntry(new ItemStack(Items.TERRACOTTA, 4), 4, 6),
            new ExtendedMultitradeEntry(new ExtendedTradeEntry[] {
                    new ExtendedTradeEntry(new ItemStack(Items.BLACK_TERRACOTTA, 4), 4, 6),
                    new ExtendedTradeEntry(new ItemStack(Items.BLUE_TERRACOTTA, 4), 4, 6),
                    new ExtendedTradeEntry(new ItemStack(Items.BROWN_TERRACOTTA, 4), 4, 6),
                    new ExtendedTradeEntry(new ItemStack(Items.CYAN_TERRACOTTA, 4), 4, 6),
                    new ExtendedTradeEntry(new ItemStack(Items.GRAY_TERRACOTTA, 4), 4, 6),
                    new ExtendedTradeEntry(new ItemStack(Items.GREEN_TERRACOTTA, 4), 4, 6),
                    new ExtendedTradeEntry(new ItemStack(Items.LIGHT_BLUE_TERRACOTTA, 4), 4, 6),
                    new ExtendedTradeEntry(new ItemStack(Items.LIGHT_GRAY_TERRACOTTA, 4), 4, 6),
                    new ExtendedTradeEntry(new ItemStack(Items.LIME_TERRACOTTA, 4), 4, 6),
                    new ExtendedTradeEntry(new ItemStack(Items.MAGENTA_TERRACOTTA, 4), 4, 6),
                    new ExtendedTradeEntry(new ItemStack(Items.ORANGE_TERRACOTTA, 4), 4, 6),
                    new ExtendedTradeEntry(new ItemStack(Items.PINK_TERRACOTTA, 4), 4, 6),
                    new ExtendedTradeEntry(new ItemStack(Items.PURPLE_TERRACOTTA, 4), 4, 6),
                    new ExtendedTradeEntry(new ItemStack(Items.RED_TERRACOTTA, 4), 4, 6),
                    new ExtendedTradeEntry(new ItemStack(Items.WHITE_TERRACOTTA, 4), 4, 6),
                    new ExtendedTradeEntry(new ItemStack(Items.YELLOW_TERRACOTTA, 4), 4, 6)}),
            new ExtendedTradeEntry(new ItemStack(Items.FLOWER_POT), 5, 8),
            new ExtendedTradeEntry(new ItemStack(Items.BRICK, 2), 2, 4), 
            new ExtendedTradeEntry(new ItemStack(ExtendedItems.LARGE_POT.get()), 6, 8),
            new ExtendedTradeEntry(new ItemStack(ExtendedItems.LARGE_FLOWER_POT.get()), 6, 8), 
            new ExtendedTradeEntry(new ItemStack(Items.BRICKS, 4), 5, 7),
            new ExtendedTradeEntry(new ItemStack(LOTRItems.WATTLE_AND_DAUB.get(), 4), 5, 8), 
            new ExtendedTradeEntry(new ItemStack(LOTRItems.WATTLE_AND_DAUB_PILLAR.get(), 3), 3, 6),
            new ExtendedTradeEntry(new ItemStack(ExtendedItems.CROSSED_WATTLE_AND_DAUB.get(), 4), 6, 8), 
            new ExtendedTradeEntry(new ItemStack(LOTRItems.CLAY_MUG.get()), 2, 4),
            new ExtendedTradeEntry(new ItemStack(LOTRItems.CLAY_PLATE.get(), 2), 1, 2),
            new ExtendedTradeEntry(new ItemStack(LOTRItems.CERAMIC_MUG.get()), 3, 5),
            new ExtendedTradeEntry(new ItemStack(LOTRItems.STONEWARE_PLATE.get(), 2), 2, 4),
            new ExtendedTradeEntry(new ItemStack(LOTRItems.CLAY_TILING.get(), 4), 4, 6),
            new ExtendedMultitradeEntry(new ExtendedTradeEntry[] {
                    new ExtendedTradeEntry(new ItemStack(LOTRItems.BLACK_CLAY_TILING.get(), 4), 4, 6),
                    new ExtendedTradeEntry(new ItemStack(LOTRItems.BLUE_CLAY_TILING.get(), 4), 4, 6),
                    new ExtendedTradeEntry(new ItemStack(LOTRItems.BROWN_CLAY_TILING.get(), 4), 4, 6),
                    new ExtendedTradeEntry(new ItemStack(LOTRItems.CYAN_CLAY_TILING.get(), 4), 4, 6),
                    new ExtendedTradeEntry(new ItemStack(LOTRItems.GRAY_CLAY_TILING.get(), 4), 4, 6),
                    new ExtendedTradeEntry(new ItemStack(LOTRItems.GREEN_CLAY_TILING.get(), 4), 4, 6),
                    new ExtendedTradeEntry(new ItemStack(LOTRItems.LIGHT_BLUE_CLAY_TILING.get(), 4), 4, 6),
                    new ExtendedTradeEntry(new ItemStack(LOTRItems.LIGHT_GRAY_CLAY_TILING.get(), 4), 4, 6),
                    new ExtendedTradeEntry(new ItemStack(LOTRItems.LIME_CLAY_TILING.get(), 4), 4, 6),
                    new ExtendedTradeEntry(new ItemStack(LOTRItems.MAGENTA_CLAY_TILING.get(), 4), 4, 6),
                    new ExtendedTradeEntry(new ItemStack(LOTRItems.ORANGE_CLAY_TILING.get(), 4), 4, 6),
                    new ExtendedTradeEntry(new ItemStack(LOTRItems.PINK_CLAY_TILING.get(), 4), 4, 6),
                    new ExtendedTradeEntry(new ItemStack(LOTRItems.PURPLE_CLAY_TILING.get(), 4), 4, 6),
                    new ExtendedTradeEntry(new ItemStack(LOTRItems.RED_CLAY_TILING.get(), 4), 4, 6),
                    new ExtendedTradeEntry(new ItemStack(LOTRItems.WHITE_CLAY_TILING.get(), 4), 4, 6),
                    new ExtendedTradeEntry(new ItemStack(LOTRItems.YELLOW_CLAY_TILING.get(), 4), 4, 6)})
            
    });

    private ArrayList<ExtendedTrade> itemsSoldByTrader;
    private ArrayList<ExtendedTrade> itemsBoughtByTrader;

    public ExtendedBreelandPotterEntity(EntityType<? extends BreeManEntity> type, World w) {
        super(type, w);
        setPersistenceRequired();

        itemsBoughtByTrader = BREE_POTTER_BOUGHT.generateTrades();
        itemsSoldByTrader = BREE_POTTER_SOLD.generateTrades();

        setItemInHand(Hand.MAIN_HAND, new ItemStack(Items.CLAY_BALL.getItem(), 1));
        setItemSlot(EquipmentSlotType.HEAD, new ItemStack(ExtendedItems.LEATHER_HAT.get(), 1));
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
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(PLAYER_TRADING_WITH, Optional.empty());
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
    public void updatePlayerInteractingWith(UUID uuid) {
        this.entityData.set(PLAYER_TRADING_WITH, Optional.of(uuid));
    }

    @Override
    public Optional<UUID> getPlayerInteractingWith() {
        return this.entityData.get(PLAYER_TRADING_WITH);
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
    public ILivingEntityData finalizeSpawn(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT dataTag) {
        spawnData = super.finalizeSpawn(sw, diff, reason, spawnData, dataTag);
        this.npcItemsInv.setIdleItem(new ItemStack(Items.CLAY_BALL.getItem()));

        this.setItemSlot(EquipmentSlotType.HEAD, new ItemStack(ExtendedItems.LEATHER_HAT.get()));
        setItemInHand(Hand.MAIN_HAND, new ItemStack(Items.CLAY_BALL.getItem(), 1));

        return spawnData;
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
}
