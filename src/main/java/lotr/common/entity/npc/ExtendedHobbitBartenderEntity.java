package lotr.common.entity.npc;

import lotr.common.init.ExtendedItems;

import lotr.common.entity.npc.ai.goal.*;
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

/**
 * @author RichardB122
 */
public class ExtendedHobbitBartenderEntity extends HobbitEntity implements ExtendedTraderEntity {

    int lastTickCooldownReduced = 0;

    HashSet<UUID> playersTradingWith = new HashSet<>();

    // trading pools that bartenders will buy from players
    private final ExtendedTradeEntries HOBBIT_BARTENDER_BOUGHT = new ExtendedTradeEntries(ExtendedTradeEntries.TradeType.BOUGHT, new ExtendedTradeEntry[] {
            new ExtendedTradeEntry(new ItemStack(LOTRItems.CLAY_PLATE.get()), 1, 1), new ExtendedTradeEntry(new ItemStack(LOTRItems.CLAY_MUG.get()), 1, 1),
            new ExtendedTradeEntry(new ItemStack(Items.BEEF), 2, 4), new ExtendedTradeEntry(new ItemStack(Items.PORKCHOP), 2, 4),
            new ExtendedTradeEntry(new ItemStack(Items.RABBIT), 2, 4), new ExtendedTradeEntry(new ItemStack(Items.CHICKEN), 2, 4),
            new ExtendedTradeEntry(new ItemStack(Items.POTATO, 3), 1, 1), new ExtendedTradeEntry(new ItemStack(Items.COD), 2, 3),
            new ExtendedTradeEntry(new ItemStack(Items.WATER_BUCKET), 3, 5), new ExtendedTradeEntry(new ItemStack(Items.COAL, 2), 1, 1),
            new ExtendedTradeEntry(new ItemStack(LOTRItems.SALT.get()), 8, 13), new ExtendedTradeEntry(new ItemStack(Items.BUCKET), 2, 4),
            new ExtendedTradeEntry(new ItemStack(LOTRItems.FOUR_LEAF_CLOVER.get()), 30, 50), new ExtendedTradeEntry(new ItemStack(LOTRItems.PIPEWEED_LEAF.get()), 1, 1),
            new ExtendedTradeEntry(new ItemStack(Items.SALMON), 2, 3)
    });

    // items you can buy from a hobbit bartender
    // TODO: Change from using ExtendedTraderHelper to DrinkUtils
    private final ExtendedTradeEntries HOBBIT_BARTENDER_SOLD = new ExtendedTradeEntries(ExtendedTradeEntries.TradeType.SOLD, new ExtendedTradeEntry[] {
            new ExtendedTradeEntry(new ItemStack(Items.COOKED_BEEF), 5, 9), new ExtendedTradeEntry(new ItemStack(Items.COOKED_PORKCHOP), 5, 9),
            new ExtendedTradeEntry(new ItemStack(Items.COOKED_MUTTON), 5, 9), new ExtendedTradeEntry(new ItemStack(Items.COOKED_CHICKEN), 5, 8),
            new ExtendedTradeEntry(new ItemStack(Items.BREAD), 4, 6), new ExtendedTradeEntry(new ItemStack(LOTRItems.PIPEWEED.get(), 6), 5, 8),
            new ExtendedTradeEntry(new ItemStack(LOTRItems.WOODEN_MUG.get()), 2, 3), new ExtendedTradeEntry(new ItemStack(LOTRItems.CERAMIC_MUG.get()), 2, 3),
            new ExtendedTradeEntry(new ItemStack(Items.BAKED_POTATO, 2), 5, 9), new ExtendedTradeEntry(new ItemStack(Items.SALMON), 5, 8),
            new ExtendedTradeEntry(new ItemStack(Items.COD), 5,8),
            new ExtendedTradeEntry(new ItemStack(LOTRItems.CHOCOLATE_DRINK.get()), 3, 5), new ExtendedTradeEntry(new ItemStack(LOTRItems.APPLE_JUICE.get()), 5, 8),
            new ExtendedTradeEntry(new ItemStack(LOTRItems.GAMMON.get()), 5, 9), new ExtendedTradeEntry(new ItemStack(Items.MUSHROOM_STEW), 8, 13),
            new ExtendedTradeEntry(new ItemStack(LOTRItems.FINE_PLATE.get()), 2, 3), new ExtendedTradeEntry(new ItemStack(ExtendedItems.HOBBIT_MARRIAGE_RING.get()), 11, 18),
            new ExtendedTradeEntry(new ItemStack(LOTRItems.HOBBIT_OVEN.get()), 30, 50), new ExtendedTradeEntry(ExtendedTraderHelper.getLightAlcohol(LOTRItems.ALE.get()), 5, 8),
            new ExtendedTradeEntry(ExtendedTraderHelper.getModerateAlcohol(LOTRItems.ALE.get()), 6, 10), new ExtendedTradeEntry(ExtendedTraderHelper.getStrongAlcohol(LOTRItems.ALE.get()), 8, 13),
            new ExtendedTradeEntry(ExtendedTraderHelper.getLightAlcohol(LOTRItems.CIDER.get()), 5, 8), new ExtendedTradeEntry(ExtendedTraderHelper.getModerateAlcohol(LOTRItems.CIDER.get()), 6, 10),
            new ExtendedTradeEntry(ExtendedTraderHelper.getStrongAlcohol(LOTRItems.CIDER.get()), 8, 13), new ExtendedTradeEntry(ExtendedTraderHelper.getLightAlcohol(LOTRItems.PERRY.get()), 5, 8),
            new ExtendedTradeEntry(ExtendedTraderHelper.getModerateAlcohol(LOTRItems.PERRY.get()), 6, 10), new ExtendedTradeEntry(ExtendedTraderHelper.getStrongAlcohol(LOTRItems.PERRY.get()), 8, 13)
    });

    private ArrayList<ExtendedTrade> itemsSoldByTrader;
    private ArrayList<ExtendedTrade> itemsBoughtByTrader;

    public ExtendedHobbitBartenderEntity(EntityType<? extends HobbitEntity> type, World w) {
        super(type, w);
        setPersistenceRequired();

        itemsBoughtByTrader = HOBBIT_BARTENDER_BOUGHT.generateTrades();
        itemsSoldByTrader = HOBBIT_BARTENDER_SOLD.generateTrades();
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
    }

}
