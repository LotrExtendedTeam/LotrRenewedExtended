package lotr.common.entity.npc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import lotr.common.entity.npc.ai.goal.*;
import lotr.common.entity.npc.data.NPCGenderProvider;
import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRItems;
import lotr.common.util.ExtendedInteractableHelper;
import lotr.common.util.ExtendedTraderHelper;
import lotr.common.util.representation.ExtendedTrade;
import lotr.common.util.representation.ExtendedTradeEntries;
import lotr.common.util.representation.ExtendedTradeEntry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
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

public class ExtendedBreelandSmithEntity extends BreeManEntity implements ExtendedTraderEntity {

    int lastTickCooldownReduced = 0;
    int lastTickVerifiedPlayerTradingWithIsOnline = 0;

    private static final DataParameter<Optional<UUID>> PLAYER_TRADING_WITH = EntityDataManager.defineId(ExtendedBreelandSmithEntity.class, DataSerializers.OPTIONAL_UUID);

    private final ExtendedTradeEntries BREE_SMITHY_BOUGHT = new ExtendedTradeEntries(ExtendedTradeEntries.TradeType.BOUGHT, new ExtendedTradeEntry[] {
    		// Ores
    		new ExtendedTradeEntry(new ItemStack(LOTRItems.COPPER_INGOT.get()), 3),
    		new ExtendedTradeEntry(new ItemStack(LOTRItems.TIN_INGOT.get()), 3),
    		new ExtendedTradeEntry(new ItemStack(LOTRItems.BRONZE_INGOT.get()), 3),
    		new ExtendedTradeEntry(new ItemStack(Items.IRON_INGOT), 4),
    		new ExtendedTradeEntry(new ItemStack(Items.IRON_NUGGET, 9), 3),
    		new ExtendedTradeEntry(new ItemStack(Items.GOLD_INGOT), 15),
    		new ExtendedTradeEntry(new ItemStack(Items.GOLD_NUGGET, 9), 15),
    		new ExtendedTradeEntry(new ItemStack(Items.COAL), 1),
    		// Misc
    		new ExtendedTradeEntry(new ItemStack(Items.STRING, 3), 1),
    		new ExtendedTradeEntry(new ItemStack(Items.LEATHER), 2),
    		new ExtendedTradeEntry(new ItemStack(LOTRItems.FUR.get()), 2),
    		new ExtendedTradeEntry(new ItemStack(ExtendedItems.BLACK_FUR.get()), 2),
    		new ExtendedTradeEntry(new ItemStack(ExtendedItems.GRAY_FUR.get()), 2),
    		new ExtendedTradeEntry(new ItemStack(ExtendedItems.SILVER_FUR.get()), 3),
    		new ExtendedTradeEntry(new ItemStack(ExtendedItems.WHITE_FUR.get()), 50),
    		// Gems
    		new ExtendedTradeEntry(new ItemStack(ExtendedItems.AMBER.get()), 10),
    		new ExtendedTradeEntry(new ItemStack(ExtendedItems.EMERALD.get()), 15),
    		new ExtendedTradeEntry(new ItemStack(ExtendedItems.SAPPHIRE.get()), 12),
    		new ExtendedTradeEntry(new ItemStack(ExtendedItems.TOPAZ.get()), 8)
    });

    private final ExtendedTradeEntries BREE_SMITHY_SOLD = new ExtendedTradeEntries(ExtendedTradeEntries.TradeType.SOLD, new ExtendedTradeEntry[] {
    		// Armor
    		new ExtendedTradeEntry(new ItemStack(LOTRItems.BRONZE_HELMET.get()), 10),
    		new ExtendedTradeEntry(new ItemStack(LOTRItems.BRONZE_CHESTPLATE.get()), 14),
    		new ExtendedTradeEntry(new ItemStack(LOTRItems.BRONZE_LEGGINGS.get()), 12),
    		new ExtendedTradeEntry(new ItemStack(LOTRItems.BRONZE_BOOTS.get()), 8),
    		new ExtendedTradeEntry(new ItemStack(Items.CHAINMAIL_HELMET), 12),
    		new ExtendedTradeEntry(new ItemStack(Items.CHAINMAIL_CHESTPLATE), 16),
    		new ExtendedTradeEntry(new ItemStack(Items.CHAINMAIL_LEGGINGS), 14),
    		new ExtendedTradeEntry(new ItemStack(Items.CHAINMAIL_BOOTS), 10),
    		new ExtendedTradeEntry(new ItemStack(Items.IRON_HELMET), 16),
    		new ExtendedTradeEntry(new ItemStack(Items.IRON_CHESTPLATE), 22),
    		new ExtendedTradeEntry(new ItemStack(Items.IRON_LEGGINGS), 18),
    		new ExtendedTradeEntry(new ItemStack(Items.IRON_BOOTS), 14),
    		new ExtendedTradeEntry(new ItemStack(Items.IRON_HORSE_ARMOR), 30),
    		// Blocks
    		new ExtendedTradeEntry(new ItemStack(LOTRItems.BREE_CRAFTING_TABLE.get()), 100),
    		new ExtendedTradeEntry(new ItemStack(LOTRItems.BRONZE_BARS.get(), 8), 20),
    		new ExtendedTradeEntry(new ItemStack(Items.IRON_BARS, 8), 20),
    		// Weapons
    		new ExtendedTradeEntry(new ItemStack(LOTRItems.BRONZE_DAGGER.get()), 7),
    		new ExtendedTradeEntry(new ItemStack(LOTRItems.BRONZE_SWORD.get()), 10),
    		new ExtendedTradeEntry(new ItemStack(LOTRItems.BRONZE_SPEAR.get()), 13),
    		new ExtendedTradeEntry(new ItemStack(LOTRItems.IRON_DAGGER.get()), 9),
    		new ExtendedTradeEntry(new ItemStack(Items.IRON_SWORD), 12),
    		new ExtendedTradeEntry(new ItemStack(LOTRItems.IRON_SPEAR.get()), 15),
    		// Tools
    		new ExtendedTradeEntry(new ItemStack(LOTRItems.BLACKSMITH_HAMMER.get()), 16),
            
    });

    private ArrayList<ExtendedTrade> itemsSoldByTrader;
    private ArrayList<ExtendedTrade> itemsBoughtByTrader;

    public ExtendedBreelandSmithEntity(EntityType<? extends BreeManEntity> type, World w) {
        super(type, w);
        setPersistenceRequired();

        itemsBoughtByTrader = BREE_SMITHY_BOUGHT.generateTrades();
        itemsSoldByTrader = BREE_SMITHY_SOLD.generateTrades();
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
        return BreeManEntity.regAttrs().add(Attributes.MAX_HEALTH, 25.0D);
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
    public NPCGenderProvider getGenderProvider() {
		return NPCGenderProvider.MALE;
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
    public ILivingEntityData finalizeSpawn(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT dataTag) {
        spawnData = super.finalizeSpawn(sw, diff, reason, spawnData, dataTag);
        this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRItems.BLACKSMITH_HAMMER.get(), 1));
        this.npcItemsInv.setIdleItemsFromMeleeWeapons();
        this.setItemSlot(EquipmentSlotType.HEAD, new ItemStack(ExtendedItems.LEATHER_HAT.get()));
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
