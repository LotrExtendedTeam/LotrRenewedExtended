package lotr.common.entity.npc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import lotr.common.entity.npc.ai.StandardAttackModeUpdaters;
import lotr.common.entity.npc.ai.goal.*;
import lotr.common.entity.npc.data.NPCFoodPools;
import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRItems;
import lotr.common.item.VesselDrinkItem;
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
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.OpenDoorGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
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

public class ExtendedGundabadOrcTraderEntity extends GundabadOrcEntity implements ExtendedTraderEntity {
    int lastTickCooldownReduced = 0;
    int lastTickVerifiedPlayerTradingWithIsOnline = 0;

    private ArrayList<ExtendedTrade> itemsSoldByTrader;
    private ArrayList<ExtendedTrade> itemsBoughtByTrader;

    private static final DataParameter<Optional<UUID>> PLAYER_TRADING_WITH = EntityDataManager.defineId(ExtendedGundabadOrcTraderEntity.class, DataSerializers.OPTIONAL_UUID);

	// items a Gundabad Orc trader will sell
    private final ExtendedTradeEntries GUNDABAD_ORC_SOLD = new ExtendedTradeEntries(ExtendedTradeEntries.TradeType.BOUGHT, new ExtendedTradeEntry[] {
			// TODO: Add poisoned bronze daggers as trades eventually
            new ExtendedTradeEntry(new ItemStack(ExtendedItems.NORTHERN_ORC_CRAFTING_TABLE.get()), 30, 50, 16),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.BRONZE_HELMET.get()), 15, 25, 8),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.BRONZE_CHESTPLATE.get()), 23, 38, 8),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.BRONZE_LEGGINGS.get()), 20, 33, 8),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.BRONZE_BOOTS.get()), 14, 23, 8),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.BRONZE_SWORD.get()), 11, 18, 8),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.BRONZE_SPEAR.get()), 11, 19, 8),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.BRONZE_DAGGER.get()), 6, 10, 8),
			new ExtendedMultitradeEntry(new ExtendedTradeEntry[] {
					new ExtendedTradeEntry(DrinkUtils.drinkOfPotency(LOTRItems.ORC_DRAUGHT.get(), VesselDrinkItem.Potency.LIGHT), 5, 8, 16),
					new ExtendedTradeEntry(DrinkUtils.drinkOfPotency(LOTRItems.ORC_DRAUGHT.get(), VesselDrinkItem.Potency.MODERATE), 6, 10, 16),
					new ExtendedTradeEntry(DrinkUtils.drinkOfPotency(LOTRItems.ORC_DRAUGHT.get(), VesselDrinkItem.Potency.STRONG), 8, 13, 16)}
			),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.FUR.get()), 3, 5),
			new ExtendedTradeEntry(new ItemStack(Items.ARROW, 4), 2, 4, 64),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.BRONZE_PICKAXE.get()), 11, 18, 8),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.BRONZE_AXE.get()), 11, 18, 8),
			new ExtendedTradeEntry(new ItemStack(Items.IRON_PICKAXE), 11, 18, 8),
			new ExtendedTradeEntry(new ItemStack(Items.IRON_AXE), 11, 18, 8),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.MAGGOTY_BREAD.get()), 3, 5),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.FUR_HELMET.get()), 11, 19, 8),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.FUR_CHESTPLATE.get()), 15, 25, 8),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.FUR_LEGGINGS.get()), 12, 20, 8),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.FUR_BOOTS.get()), 9, 15, 8),
			// TODO: Add Gundabad Orc Bow as a trade when it's added to the mod
    });

    // items a Gundabad Orc trader will buy from a player
    private final ExtendedTradeEntries GUNDABAD_ORC_BOUGHT = new ExtendedTradeEntries(ExtendedTradeEntries.TradeType.SOLD, new ExtendedTradeEntry[] {
           new ExtendedTradeEntry(new ItemStack(LOTRItems.ORC_STEEL_INGOT.get()), 2, 4), new ExtendedTradeEntry(new ItemStack(LOTRItems.URUK_STEEL_INGOT.get()), 2, 4),
			new ExtendedTradeEntry(new ItemStack(Items.IRON_INGOT), 2, 4), new ExtendedTradeEntry(new ItemStack(LOTRItems.BRONZE_INGOT.get()), 2, 4),
			new ExtendedTradeEntry(new ItemStack(ExtendedItems.DIAMOND.get()), 19, 31), new ExtendedTradeEntry(new ItemStack(ExtendedItems.EMERALD.get()), 11, 19),
			new ExtendedTradeEntry(new ItemStack(ExtendedItems.SAPPHIRE.get()), 9, 15), new ExtendedTradeEntry(new ItemStack(ExtendedItems.RUBY.get()), 9, 15),
			new ExtendedTradeEntry(new ItemStack(ExtendedItems.OPAL.get()), 8, 13), new ExtendedTradeEntry(new ItemStack(ExtendedItems.PEARL.get()), 19, 31),
			new ExtendedTradeEntry(new ItemStack(Items.STRING, 2), 1, 1), new ExtendedTradeEntry(new ItemStack(Items.FEATHER), 2, 3),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.WOODEN_MUG.get()), 1, 1), new ExtendedTradeEntry(new ItemStack(LOTRItems.WATERSKIN.get()), 2, 3),
			new ExtendedTradeEntry(new ItemStack(Items.WHEAT, 2), 1, 1), new ExtendedTradeEntry(new ItemStack(LOTRItems.ELF_BONE.get()), 1, 1),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.DWARF_BONE.get()), 1, 1), new ExtendedTradeEntry(new ItemStack(LOTRItems.HOBBIT_BONE.get()), 1, 1),
			new ExtendedTradeEntry(new ItemStack(Items.BONE), 1, 1), new ExtendedTradeEntry(new ItemStack(LOTRItems.MAN_FLESH.get()), 2, 4),
    });

	@SuppressWarnings("unchecked")
	public ExtendedGundabadOrcTraderEntity(EntityType<? extends GundabadOrcEntity> type, World w) {
		super(type, w);

		setPersistenceRequired();
		this.getNPCCombatUpdater().setAttackModeUpdater(StandardAttackModeUpdaters.meleeOnly());

		itemsBoughtByTrader = GUNDABAD_ORC_BOUGHT.generateTrades();
		itemsSoldByTrader = GUNDABAD_ORC_SOLD.generateTrades();
	}

	@Override
	public void addNPCAI() {
		((GroundPathNavigator)this.getNavigation()).setCanOpenDoors(true);
		this.getNavigation().setCanFloat(true);
		this.setPathfindingMalus(PathNodeType.DANGER_FIRE, 16.0F);
		this.setPathfindingMalus(PathNodeType.DAMAGE_FIRE, -1.0F);
		this.initialiseAttackGoals(this.getAttackGoalsHolder());
		this.addNPCTargetingAI();

		this.goalSelector.addGoal(0, new SwimGoal(this));
		this.addAttackGoal(4);
		this.goalSelector.addGoal(6, new OpenDoorGoal(this, true));
		this.goalSelector.addGoal(7, new ExtendedInteractableTalkToCurrentGoal(this, this));
		this.goalSelector.addGoal(8, new ExtendedInteractableFriendlyNPCConversationGoal(this, this, 2.0E-4F));
		this.goalSelector.addGoal(9, new ExtendedInteractableWaterAvoidingRandomWalkingGoal(this, this, 1.0));
		this.goalSelector.addGoal(10, new NPCEatGoal(this, NPCFoodPools.ORC, 6000));
		this.goalSelector.addGoal(10, new NPCDrinkGoal(this, NPCFoodPools.ORC_DRINK, 6000));
		this.goalSelector.addGoal(11, new LookAtGoal(this, PlayerEntity.class, 8.0F, 0.05F));
		this.goalSelector.addGoal(11, new LookAtGoal(this, NPCEntity.class, 5.0F, 0.05F));
		this.goalSelector.addGoal(12, new LookAtGoal(this, LivingEntity.class, 8.0F, 0.02F));
		this.goalSelector.addGoal(13, new LookRandomlyGoal(this));
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(PLAYER_TRADING_WITH, Optional.empty());
	}

    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT dataTag) {
        spawnData = super.finalizeSpawn(sw, diff, reason, spawnData, dataTag);
        this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRItems.MORDOR_DAGGER.get()));
        this.npcItemsInv.clearRangedWeapon(); // orc traders do not have ranged weapons
        this.npcItemsInv.clearIdleItem();
        ItemStack headItem;
        ItemStack chestItem;
        ItemStack legaItem;
        ItemStack bootsItem;
        if (random.nextInt(500) == 0) {
        	headItem= new ItemStack(ExtendedItems.WHITE_WARG_HELMET.get());
            chestItem= new ItemStack(ExtendedItems.WHITE_WARG_CHESTPLATE.get());
            legaItem= new ItemStack(ExtendedItems.WHITE_WARG_LEGGINGS.get());
            bootsItem= new ItemStack(ExtendedItems.WHITE_WARG_BOOTS.get());
		} else if (random.nextInt(24) == 0) {
        	headItem= new ItemStack(ExtendedItems.BLACK_WARG_HELMET.get());
            chestItem= new ItemStack(ExtendedItems.BLACK_WARG_CHESTPLATE.get());
            legaItem= new ItemStack(ExtendedItems.BLACK_WARG_LEGGINGS.get());
            bootsItem= new ItemStack(ExtendedItems.BLACK_WARG_BOOTS.get());
		} else if (random.nextInt(10) == 0) {
        	headItem= new ItemStack(ExtendedItems.SILVER_WARG_HELMET.get());
            chestItem= new ItemStack(ExtendedItems.SILVER_WARG_CHESTPLATE.get());
            legaItem= new ItemStack(ExtendedItems.SILVER_WARG_LEGGINGS.get());
            bootsItem= new ItemStack(ExtendedItems.SILVER_WARG_BOOTS.get());
		} else if(random.nextInt(3) == 0) {
        	headItem= new ItemStack(ExtendedItems.BROWN_WARG_HELMET.get());
            chestItem= new ItemStack(ExtendedItems.BROWN_WARG_CHESTPLATE.get());
            legaItem= new ItemStack(ExtendedItems.BROWN_WARG_LEGGINGS.get());
            bootsItem= new ItemStack(ExtendedItems.BROWN_WARG_BOOTS.get());
		}else{
        	headItem= new ItemStack(ExtendedItems.GRAY_WARG_HELMET.get());
            chestItem= new ItemStack(ExtendedItems.GRAY_WARG_CHESTPLATE.get());
            legaItem= new ItemStack(ExtendedItems.GRAY_WARG_LEGGINGS.get());
            bootsItem= new ItemStack(ExtendedItems.GRAY_WARG_BOOTS.get());
		}
		this.setItemSlot(EquipmentSlotType.FEET, bootsItem);
		this.setItemSlot(EquipmentSlotType.LEGS, legaItem);
		this.setItemSlot(EquipmentSlotType.CHEST, chestItem);
		this.setItemSlot(EquipmentSlotType.HEAD, headItem);
		this.setItemSlot(EquipmentSlotType.OFFHAND, ItemStack.EMPTY);

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
	public void readAdditionalSaveData(CompoundNBT tag) {
		super.readAdditionalSaveData(tag);

		loadAllTrades(tag);
	}

	@Override
	public void addAdditionalSaveData(CompoundNBT tag) {
		super.addAdditionalSaveData(tag);

		saveAllTrades(tag);
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
	public void updatePlayerInteractingWith(UUID uuid) {
		this.entityData.set(PLAYER_TRADING_WITH, Optional.of(uuid));
	}

	@Override
	public Optional<UUID> getPlayerInteractingWith() {
		return this.entityData.get(PLAYER_TRADING_WITH);
	}

	@Override
	public void openCoinExchange(PlayerEntity player) {
		ExtendedTraderHelper.openCoinExchange(this, player, this);
	}

	@Override
	public ActionResultType mobInteract(PlayerEntity player, Hand hand) {
		return ExtendedTraderHelper.mobInteract(this, this, player, hand);
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
	public void setBuys(ArrayList<ExtendedTrade> trades) {
		itemsBoughtByTrader = trades;
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
	protected void addNPCTargetingAI() {
		this.addNonAggressiveTargetingGoals();
	}

	@Override
	public boolean canTradeWith(PlayerEntity player) {
		return isFriendlyAndAlignedWithLevel(player, this, 50F);
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
