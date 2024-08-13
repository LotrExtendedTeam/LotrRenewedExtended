package lotr.common.entity.npc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import lotr.common.init.ExtendedItems;
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
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class ExtendedGundabadOrcTraderEntity extends GundabadOrcEntity implements ExtendedTraderEntity {
    int lastTickCooldownReduced = 0;
    int lastTickVerifiedPlayerTradingWithIsOnline = 0;

    private ArrayList<ExtendedTrade> itemsSoldByTrader;
    private ArrayList<ExtendedTrade> itemsBoughtByTrader;

    private static final DataParameter<Optional<UUID>> PLAYER_TRADING_WITH = EntityDataManager.defineId(ExtendedGundabadOrcTraderEntity.class, DataSerializers.OPTIONAL_UUID);
    private static final DataParameter<Boolean> IS_WANDERING_TRADER = EntityDataManager.defineId(ExtendedGundabadOrcTraderEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> WANDERING_TRADER_TIME_LEFT = EntityDataManager.defineId(ExtendedGundabadOrcTraderEntity.class, DataSerializers.INT);

    private List<UUID> escorts = new ArrayList<>();

    // items Galadhrim Wanderers sell to a player
    private final ExtendedTradeEntries GALADHRIM_WANDERER_SOLD = new ExtendedTradeEntries(ExtendedTradeEntries.TradeType.BOUGHT, new ExtendedTradeEntry[] {
            new ExtendedTradeEntry(new ItemStack(LOTRItems.GALADHRIM_HELMET.get()), 19, 31),
            new ExtendedTradeEntry(new ItemStack(LOTRItems.GALADHRIM_CHESTPLATE.get()), 27, 45),
            new ExtendedTradeEntry(new ItemStack(LOTRItems.GALADHRIM_LEGGINGS.get()), 23, 38),
            new ExtendedTradeEntry(new ItemStack(LOTRItems.GALADHRIM_BOOTS.get()), 17, 28),
            new ExtendedTradeEntry(new ItemStack(LOTRItems.GALADHRIM_DAGGER.get()), 10, 16),
            new ExtendedTradeEntry(new ItemStack(LOTRItems.GALADHRIM_SPEAR.get()), 16, 26),
            new ExtendedTradeEntry(new ItemStack(LOTRItems.GALADHRIM_SWORD.get()), 16, 26),
            new ExtendedTradeEntry(new ItemStack(Items.ARROW, 4), 2, 4),
            new ExtendedTradeEntry(new ItemStack(LOTRItems.MALLORN_PLANKS.get(), 4), 3, 5),
            new ExtendedMultitradeEntry(new ExtendedTradeEntry[] {
                    new ExtendedTradeEntry(new ItemStack(LOTRItems.BLUE_MALLORN_TORCH.get(), 4), 6, 10),
                    new ExtendedTradeEntry(new ItemStack(LOTRItems.GOLD_MALLORN_TORCH.get(), 4), 6, 10),
                    new ExtendedTradeEntry(new ItemStack(LOTRItems.GREEN_MALLORN_TORCH.get(), 4), 6, 10),
                    new ExtendedTradeEntry(new ItemStack(LOTRItems.SILVER_MALLORN_TORCH.get(), 4), 6, 10)
            }),
            new ExtendedTradeEntry(new ItemStack(LOTRItems.LEMBAS.get()), 12, 20),
            new ExtendedTradeEntry(new ItemStack(LOTRItems.ELANOR.get()), 2, 3),
            new ExtendedMultitradeEntry(new ExtendedTradeEntry[] {
                new ExtendedTradeEntry(DrinkUtils.drinkOfPotency(LOTRItems.MIRUVOR.get(), VesselDrinkItem.Potency.LIGHT), 5, 8),
                new ExtendedTradeEntry(DrinkUtils.drinkOfPotency(LOTRItems.MIRUVOR.get(), VesselDrinkItem.Potency.MODERATE), 6, 10),
                new ExtendedTradeEntry (DrinkUtils.drinkOfPotency(LOTRItems.MIRUVOR.get(), VesselDrinkItem.Potency.STRONG), 8, 13)
            }),
            new ExtendedTradeEntry(new ItemStack(LOTRItems.NIPHREDIL.get()), 2, 3),
            new ExtendedTradeEntry(new ItemStack(ExtendedItems.HITHLAIN.get()), 3, 5),
            // TODO: Add Mallorn Boxes when they are added
    });

    // items a Galadhrim Wanderer will buy from a player
    // TODO: Add Coral when it's added
    private final ExtendedTradeEntries GALADHRIM_WANDERER_BOUGHT = new ExtendedTradeEntries(ExtendedTradeEntries.TradeType.SOLD, new ExtendedTradeEntry[] {
           new ExtendedTradeEntry(new ItemStack(Items.FEATHER), 2, 3), new ExtendedTradeEntry(new ItemStack(Items.IRON_INGOT), 2, 4),
           new ExtendedTradeEntry(new ItemStack(LOTRItems.MITHRIL_NUGGET.get()), 30, 50), new ExtendedTradeEntry(new ItemStack(LOTRItems.WOODEN_MUG.get()), 1, 1),
           new ExtendedTradeEntry(new ItemStack(LOTRItems.ORC_BONE.get()), 1, 1), new ExtendedTradeEntry(new ItemStack(Items.STRING, 2), 1, 1),
           new ExtendedTradeEntry(new ItemStack(LOTRItems.WARG_BONE.get()), 1, 1), new ExtendedTradeEntry(new ItemStack(Items.WHEAT, 3), 1, 1),
           new ExtendedTradeEntry(new ItemStack(ExtendedItems.EMERALD.get()), 11, 19), new ExtendedTradeEntry(new ItemStack(ExtendedItems.OPAL.get()), 8, 13),
           new ExtendedTradeEntry(new ItemStack(ExtendedItems.PEARL.get()), 19, 31),
           new ExtendedTradeEntry(new ItemStack(ExtendedItems.DIAMOND.get()), 19, 31)
    });

	public ExtendedGundabadOrcTraderEntity(EntityType<? extends GundabadOrcEntity> type, World w) {
		super(type, w);
	}

    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT dataTag) {
        spawnData = super.finalizeSpawn(sw, diff, reason, spawnData, dataTag);
        this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRItems.MORDOR_DAGGER.get()));
        this.npcItemsInv.clearRangedWeapon(); // orc traders do not have ranged weapons
        this.npcItemsInv.clearIdleItem();
        return spawnData;
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

	@Override
	public void updatePlayerTradingWith(UUID uuid) {
		this.entityData.set(PLAYER_TRADING_WITH, Optional.of(uuid));
	}

	@Override
	public Optional<UUID> getPlayerTradingWith() {
		return this.entityData.get(PLAYER_TRADING_WITH);
	}

	@Override
	public void openCoinExchange(PlayerEntity player) {
		// TODO Auto-generated method stub
		
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
	public void setBuys(ArrayList<ExtendedTrade> trades) {
		itemsBoughtByTrader = trades;
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
	public void setSells(ArrayList<ExtendedTrade> trades) {
		itemsSoldByTrader = trades;	
	}

	@Override
	public boolean canTradeWith(PlayerEntity player) {
		return isFriendlyAndAlignedWithLevel(player, this, 25.0F);
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
	public TranslationTextComponent getDepartureLine(PlayerEntity player) {
		// TODO Auto-generated method stub
		return null;
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
}
