package lotr.common.entity.npc;

import lotr.common.entity.npc.ai.goal.ExtendedTraderFriendlyNPCConversationGoal;
import lotr.common.entity.npc.ai.goal.ExtendedTraderTalkToCurrentGoal;
import lotr.common.entity.npc.ai.goal.ExtendedTraderWaterAvoidingRandomWalkingGoal;
import lotr.common.entity.npc.ai.goal.WatchSunriseSunsetGoal;
import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRItems;
import lotr.common.item.LOTRSpawnEggItem;
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
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SpawnEggItem;
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

public class ExtendedRivendellWandererEntity extends RivendellElfEntity implements ExtendedTraderEntity {

    int lastTickCooldownReduced = 0;
    int lastTickVerifiedPlayerTradingWithIsOnline = 0;

    private static final DataParameter<Optional<UUID>> PLAYER_TRADING_WITH = EntityDataManager.defineId(ExtendedRivendellWandererEntity.class, DataSerializers.OPTIONAL_UUID);

    // items Rivendell Wanderers will buy from you
    // TODO: Add Bows, Longspears, and and Battlestaffs when they are added
    private final ExtendedTradeEntries RIVENDELL_WANDERER_BOUGHT = new ExtendedTradeEntries(ExtendedTradeEntries.TradeType.BOUGHT, new ExtendedTradeEntry[] {
            new ExtendedTradeEntry(new ItemStack(LOTRItems.RIVENDELL_HELMET.get()), 19, 31),
            new ExtendedTradeEntry(new ItemStack(LOTRItems.RIVENDELL_CHESTPLATE.get()), 27, 45),
            new ExtendedTradeEntry(new ItemStack(LOTRItems.RIVENDELL_LEGGINGS.get()), 23, 38),
            new ExtendedTradeEntry(new ItemStack(LOTRItems.RIVENDELL_BOOTS.get()), 17, 28),
            new ExtendedTradeEntry(new ItemStack(LOTRItems.RIVENDELL_DAGGER.get()), 10, 16),
            new ExtendedTradeEntry(new ItemStack(LOTRItems.RIVENDELL_SPEAR.get()), 16, 26),
            new ExtendedTradeEntry(new ItemStack(LOTRItems.RIVENDELL_SWORD.get()), 16, 26),
            new ExtendedTradeEntry(new ItemStack(Items.ARROW, 4), 2, 4),
            new ExtendedTradeEntry(new ItemStack(LOTRItems.HIGH_ELVEN_TORCH.get(), 4), 6, 10),
            new ExtendedTradeEntry(new ItemStack(LOTRItems.LEMBAS.get()), 12, 20),
            new ExtendedMultitradeEntry(new ExtendedTradeEntry[] {
                new ExtendedTradeEntry(DrinkUtils.drinkOfPotency(LOTRItems.MIRUVOR.get(), VesselDrinkItem.Potency.LIGHT), 5, 8),
                new ExtendedTradeEntry(DrinkUtils.drinkOfPotency(LOTRItems.MIRUVOR.get(), VesselDrinkItem.Potency.MODERATE), 6, 10),
                new ExtendedTradeEntry (DrinkUtils.drinkOfPotency(LOTRItems.MIRUVOR.get(), VesselDrinkItem.Potency.STRONG), 8, 13)
            }),
            new ExtendedTradeEntry(new ItemStack(LOTRItems.BEECH_LOG.get(), 4), 3, 5)
    });

    // items you can buy from a Rivendell Wanderer
    // TODO: Add Coral when it's added
    private final ExtendedTradeEntries RIVENDELL_WANDERER_SOLD = new ExtendedTradeEntries(ExtendedTradeEntries.TradeType.SOLD, new ExtendedTradeEntry[] {
           new ExtendedTradeEntry(new ItemStack(Items.FEATHER), 2, 3), new ExtendedTradeEntry(new ItemStack(Items.IRON_INGOT), 2, 4),
           new ExtendedTradeEntry(new ItemStack(LOTRItems.MITHRIL_NUGGET.get()), 30, 50), new ExtendedTradeEntry(new ItemStack(LOTRItems.WOODEN_MUG.get()), 1, 1),
           new ExtendedTradeEntry(new ItemStack(LOTRItems.ORC_BONE.get()), 1, 1), new ExtendedTradeEntry(new ItemStack(Items.STRING, 2), 1, 1),
           new ExtendedTradeEntry(new ItemStack(LOTRItems.WARG_BONE.get()), 1, 1), new ExtendedTradeEntry(new ItemStack(Items.WHEAT, 3), 1, 1),
           new ExtendedTradeEntry(new ItemStack(ExtendedItems.EMERALD.get()), 11, 19), new ExtendedTradeEntry(new ItemStack(ExtendedItems.OPAL.get()), 8, 13),
           new ExtendedTradeEntry(new ItemStack(ExtendedItems.PEARL.get()), 19, 31)
    });

    public ExtendedRivendellWandererEntity(EntityType<? extends RivendellElfEntity> type, World w) {
        super(type, w);
        setPersistenceRequired();

        itemsBoughtByTrader = RIVENDELL_WANDERER_BOUGHT.generateTrades();
        itemsSoldByTrader = RIVENDELL_WANDERER_SOLD.generateTrades();
    }

    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT dataTag) {
        spawnData = super.finalizeSpawn(sw, diff, reason, spawnData, dataTag);
        this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRItems.RIVENDELL_DAGGER.get()));
        this.npcItemsInv.setRangedWeapon(null); // Rivendell Wanderers do not have ranged weapons
        this.npcItemsInv.clearIdleItem();
        return spawnData;
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

    // Remove, drink, smoke and eat AIs
    protected void addNPCAI() {
        ((GroundPathNavigator)this.getNavigation()).setCanOpenDoors(true);
        this.getNavigation().setCanFloat(true);
        this.setPathfindingMalus(PathNodeType.DANGER_FIRE, 16.0F);
        this.setPathfindingMalus(PathNodeType.DAMAGE_FIRE, -1.0F);
        this.initialiseAttackGoals(this.getAttackGoalsHolder());
        this.addNPCTargetingAI();

        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<OrcEntity>(this, OrcEntity.class, 12.0F, 1.5, 1.8));
        this.goalSelector.addGoal(2, new PanicGoal(this, 1.6));
        this.goalSelector.addGoal(9, new OpenDoorGoal(this, true));
        this.goalSelector.addGoal(10, new ExtendedTraderTalkToCurrentGoal(this, this));
        this.goalSelector.addGoal(11, new ExtendedTraderFriendlyNPCConversationGoal(this, this, 0.001F));
        this.goalSelector.addGoal(12, new WatchSunriseSunsetGoal(this, 0.01F));
        this.goalSelector.addGoal(13, new ExtendedTraderWaterAvoidingRandomWalkingGoal(this, this, 1.1));
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

    public boolean canTradeWith(PlayerEntity player) {
        return isFriendlyAndAligned(player);
    }
    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return HobbitEntity.regAttrs();
    }

    @Override
    public ActionResultType mobInteract(PlayerEntity player, Hand hand) {
        if (player.getItemInHand(hand).getItem() instanceof SpawnEggItem
                || player.getItemInHand(hand).getItem() instanceof LOTRSpawnEggItem) {
            return ActionResultType.SUCCESS;
        }
        if (canTradeWith(player)) {
            return ExtendedTraderHelper.openTraderGui(this, player, this);
        } else {
            if (!player.level.isClientSide)
                player.sendMessage(new TranslationTextComponent("gui.lotrextended.trading.trader_alignment_too_low"), player.getUUID());
            return ActionResultType.FAIL;
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT tag) {
        super.addAdditionalSaveData(tag);

        TradeUtils.saveAllTrades(ExtendedTradeEntries.TradeType.BOUGHT, itemsBoughtByTrader, tag);
        TradeUtils.saveAllTrades(ExtendedTradeEntries.TradeType.SOLD, itemsSoldByTrader, tag);
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

    @Override
    public void playTradeSound() {
        ExtendedTraderHelper.playTraderSound(level, getOnPos());
    }

    @Override
    public boolean isInteractingWithPlayer() {
        return ExtendedTraderHelper.isInteractingWithPlayer(getPlayerTradingWith());
    }

    // TODO: move this to ExtendedTraderHelper eventually
    @Override
    public void removePlayerFromTrading(PlayerEntity player) {
        if (this.entityData.get(PLAYER_TRADING_WITH).isPresent() && this.entityData.get(PLAYER_TRADING_WITH).get().equals(player.getUUID())) {
            this.entityData.set(PLAYER_TRADING_WITH, Optional.empty());
        }
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
        // TODO: Add support for different trades to tick back at different rates - e.g. a rare powerful sword should take
        //  20-30 minutes to become available again whereas selling another individual pipe-weed might be just a few seconds
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
    }
}
