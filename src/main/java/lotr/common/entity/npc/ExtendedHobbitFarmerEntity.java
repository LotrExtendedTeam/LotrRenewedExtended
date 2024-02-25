package lotr.common.entity.npc;

import lotr.common.entity.npc.ai.goal.*;
import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRItems;

import lotr.common.util.ExtendedTraderHelper;
import lotr.common.util.TradeUtils;
import lotr.common.util.representation.ExtendedTrade;
import lotr.common.util.representation.ExtendedTradeEntries;
import lotr.common.util.representation.ExtendedTradeEntry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public class ExtendedHobbitFarmerEntity extends HobbitEntity implements ExtendedTraderEntity {

    int lastTickCooldownReduced = 0;

    HashSet<UUID> playersTradingWith = new HashSet<>();

    private final ExtendedTradeEntries HOBBIT_FARMER_BOUGHT = new ExtendedTradeEntries(ExtendedTradeEntries.TradeType.BOUGHT, new ExtendedTradeEntry[] {
            new ExtendedTradeEntry(new ItemStack(Items.BONE_MEAL, 6), 1, 1), new ExtendedTradeEntry(new ItemStack(LOTRItems.BRONZE_HOE.get()), 5, 8),
            new ExtendedTradeEntry(new ItemStack(Items.BUCKET), 2, 4), new ExtendedTradeEntry(new ItemStack(Items.IRON_HOE), 2, 4), new ExtendedTradeEntry(new ItemStack(Items.STONE_HOE), 1, 1),
            new ExtendedTradeEntry(new ItemStack(Items.WATER_BUCKET), 3, 5)
    });

    private final ExtendedTradeEntries HOBBIT_FARMER_SOLD = new ExtendedTradeEntries(ExtendedTradeEntries.TradeType.SOLD, new ExtendedTradeEntry[] {
            new ExtendedTradeEntry(new ItemStack(Items.CARROT), 2, 4), new ExtendedTradeEntry(new ItemStack(Items.HAY_BLOCK), 9, 15),
            new ExtendedTradeEntry(new ItemStack(Items.LEAD), 6, 10), new ExtendedTradeEntry(new ItemStack(LOTRItems.LETTUCE.get()), 2, 4),
            new ExtendedTradeEntry(new ItemStack(Items.MILK_BUCKET), 6, 10), new ExtendedTradeEntry(new ItemStack(LOTRItems.PIPEWEED_LEAF.get()), 3, 5),
            new ExtendedTradeEntry(new ItemStack(LOTRItems.PIPEWEED_SEEDS.get()), 2, 4), new ExtendedTradeEntry(new ItemStack(Items.POTATO), 2, 3),
            new ExtendedTradeEntry(new ItemStack(Items.WHEAT_SEEDS), 1, 1), new ExtendedTradeEntry(new ItemStack(Items.WHEAT), 2, 3),
            new ExtendedTradeEntry(new ItemStack(Items.WHITE_WOOL), 2,3)
    });

    private ArrayList<ExtendedTrade> itemsSoldByTrader;
    private ArrayList<ExtendedTrade> itemsBoughtByTrader;

    public ExtendedHobbitFarmerEntity(EntityType<? extends HobbitEntity> type, World w) {
        super(type, w);
        setPersistenceRequired();

        itemsBoughtByTrader = HOBBIT_FARMER_BOUGHT.generateTrades();
        itemsSoldByTrader = HOBBIT_FARMER_SOLD.generateTrades();

        setItemInHand(Hand.MAIN_HAND, new ItemStack(Items.IRON_HOE.getItem(), 1));
        setItemSlot(EquipmentSlotType.HEAD, new ItemStack(ExtendedItems.LEATHER_HAT.get(), 1));
    }

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

    public boolean canTradeWith(PlayerEntity player) {
        return isFriendlyAndAligned(player);
    }
    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return HobbitEntity.regAttrs();
    }

    public ActionResultType mobInteract(PlayerEntity player, Hand hand) {
        if (canTradeWith(player)) {
            return ExtendedTraderHelper.openTraderGui(this, player, playersTradingWith);
        } else {
            player.sendMessage(new StringTextComponent("Your alignment is too low"), player.getUUID());
        }

        return super.mobInteract(player, hand);
    }

    @Override
    public void playTradeSound() {
        ExtendedTraderHelper.playTraderSound(level, getOnPos());
    }

    @Override
    public boolean isInteractingWithPlayer() {
        return ExtendedTraderHelper.isInteractingWithPlayer(playersTradingWith);
    }

    @Override
    public void removePlayerFromTrading(PlayerEntity player) {
        playersTradingWith.remove(player.getUUID());
    }

    @Override
    public void openCoinExchange(PlayerEntity player) {
        ExtendedTraderHelper.openCoinExchange(this, player, playersTradingWith);
    }

    @Override
    public void openTradeMenu(PlayerEntity player) {
        ExtendedTraderHelper.openTradeMenu(this, player, playersTradingWith);
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

}
