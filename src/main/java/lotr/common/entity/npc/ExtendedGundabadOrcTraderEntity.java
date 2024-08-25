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

	// items a Gundabad Orc trader will sell
    private final ExtendedTradeEntries GUNDABAD_ORC_SOLD = new ExtendedTradeEntries(ExtendedTradeEntries.TradeType.BOUGHT, new ExtendedTradeEntry[] {
			// TODO: Add poisoined bronze daggers as trades eventually
            new ExtendedTradeEntry(new ItemStack(ExtendedItems.GUNDABAD_CRAFTING_TABLE.get()), 30, 50),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.BRONZE_HELMET.get()), 15, 25),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.BRONZE_CHESTPLATE.get()), 23, 38),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.BRONZE_LEGGINGS.get()), 20, 33),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.BRONZE_BOOTS.get()), 14, 23),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.BRONZE_SWORD.get()), 11, 18),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.BRONZE_SPEAR.get()), 11, 19),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.BRONZE_DAGGER.get()), 6, 10),
			new ExtendedMultitradeEntry(new ExtendedTradeEntry[] {
					new ExtendedTradeEntry(DrinkUtils.drinkOfPotency(LOTRItems.ORC_DRAUGHT.get(), VesselDrinkItem.Potency.LIGHT), 5, 8),
					new ExtendedTradeEntry(DrinkUtils.drinkOfPotency(LOTRItems.ORC_DRAUGHT.get(), VesselDrinkItem.Potency.MODERATE), 6, 10),
					new ExtendedTradeEntry(DrinkUtils.drinkOfPotency(LOTRItems.ORC_DRAUGHT.get(), VesselDrinkItem.Potency.STRONG), 8, 13)}
			),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.FUR.get()), 3, 5),
			// TODO: Finish adding rest of trades
    });

    // items a Gundabad Orc trader will buy from a player
    private final ExtendedTradeEntries GUNDABAD_ORC_BOUGHT = new ExtendedTradeEntries(ExtendedTradeEntries.TradeType.SOLD, new ExtendedTradeEntry[] {
           new ExtendedTradeEntry(new ItemStack(LOTRItems.ORC_STEEL_INGOT.get()), 2, 4), new ExtendedTradeEntry(new ItemStack(LOTRItems.URUK_STEEL_INGOT.get()), 2, 4),
			// TODO: Finish adding rest of trades
    });

	public ExtendedGundabadOrcTraderEntity(EntityType<? extends GundabadOrcEntity> type, World w) {
		super(type, w);

		setPersistenceRequired();

		itemsBoughtByTrader = GUNDABAD_ORC_BOUGHT.generateTrades();
		itemsSoldByTrader = GUNDABAD_ORC_SOLD.generateTrades();
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
		return isFriendlyAndAlignedWithLevel(player, this, 50F);
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
