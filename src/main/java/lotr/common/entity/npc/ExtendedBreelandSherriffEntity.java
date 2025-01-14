package lotr.common.entity.npc;

import lotr.common.entity.npc.ai.goal.*;
import lotr.common.entity.npc.data.NPCGenderProvider;
import lotr.common.entity.npc.data.name.NPCNameGenerator;
import lotr.common.entity.npc.data.name.NPCNameGenerators;
import lotr.common.entity.npc.util.LeatherDyeUtil;
import lotr.common.init.ExtendedItems;
import lotr.common.util.ExtendedCaptainHelper;
import lotr.common.util.ExtendedInteractableHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
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

import java.util.Optional;
import java.util.UUID;

public class ExtendedBreelandSherriffEntity extends BreeManEntity implements ExtendedCaptainEntity {

    private static final SpawnEquipmentTable WEAPONS;

    public static AttributeModifierMap.MutableAttribute regAttrs() {
        return BreeManEntity.regAttrs().add(Attributes.MAX_HEALTH, 25.0);
    }

    private static final DataParameter<Optional<UUID>> PLAYER_INTERACTING_WITH = EntityDataManager.defineId(ExtendedBreelandSherriffEntity.class, DataSerializers.OPTIONAL_UUID);
    private static final int[] SUIT_COLORS;

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

    public ExtendedBreelandSherriffEntity(EntityType<? extends ExtendedBreelandSherriffEntity> type, World w) {
        super(type, w);
        setPersistenceRequired();
    }

    @Override
    public boolean isInteractingWithPlayer() {
        return ExtendedInteractableHelper.isInteractingWithPlayer(getPlayerInteractingWith());
    }

    @Override
    public Optional<UUID> getPlayerInteractingWith() {
        return this.entityData.get(PLAYER_INTERACTING_WITH);
    }

    @Override
    public void updatePlayerInteractingWith(UUID uuid) {
        this.entityData.set(PLAYER_INTERACTING_WITH, Optional.of(uuid));
    }

    @Override
    public void removePlayerFromInteracting(PlayerEntity player) {
        ExtendedInteractableHelper.removePlayerFromInteracting(player, entityData, PLAYER_INTERACTING_WITH);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(PLAYER_INTERACTING_WITH, Optional.empty());
    }

    @Override
    protected void addNPCTargetingAI() {
        this.addNonAggressiveTargetingGoals();
    }

    @Override
    protected NPCNameGenerator getNameGenerator() {
        return NPCNameGenerators.BREE;
    }

    @Override
    protected Goal createAttackGoal() {
        return new ExtendedShieldSwapMeleeAttackGoal(this, 1.45,
                new ItemStack(Items.SHIELD), ItemStack.EMPTY);
    }

    @Override
    public boolean canHire(PlayerEntity player){
        // TODO: Make sure this gets used when adding support for hiring
        return isFriendlyAndAlignedWithLevel(player, this, 100);
    }

    @Override
    public ActionResultType mobInteract(PlayerEntity player, Hand hand) {
        return ExtendedCaptainHelper.mobInteract(this, this, player, hand);
    }

    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT dataTag) {
        spawnData = super.finalizeSpawn(sw, diff, reason, spawnData, dataTag);
        this.npcItemsInv.setMeleeWeapon(WEAPONS.getRandomItem(this.random));
        this.npcItemsInv.setIdleItem(new ItemStack(Items.WRITABLE_BOOK));

        this.setItemSlot(EquipmentSlotType.FEET, LeatherDyeUtil.dyeLeather(Items.LEATHER_BOOTS, 3354152));
        this.setItemSlot(EquipmentSlotType.LEGS, LeatherDyeUtil.dyeLeather(Items.LEATHER_LEGGINGS, SUIT_COLORS, this.random));
        this.setItemSlot(EquipmentSlotType.CHEST, LeatherDyeUtil.dyeLeather(Items.LEATHER_CHESTPLATE, SUIT_COLORS, this.random));

        this.setItemSlot(EquipmentSlotType.HEAD, new ItemStack(ExtendedItems.LEATHER_HAT.get()));
        this.setItemSlot(EquipmentSlotType.OFFHAND, ItemStack.EMPTY);
        return spawnData;
    }

    static {
        WEAPONS = SpawnEquipmentTable.of(Items.IRON_SWORD);
        SUIT_COLORS = new int[]{11373426, 7823440, 5983041, 9535090};
    }

    protected NPCGenderProvider getGenderProvider() {
        return NPCGenderProvider.MALE;
    }

}

