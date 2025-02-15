package lotr.common.entity.npc;

import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedHiredUnitProfile;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedHiredUnitProfileManager;
import lotr.common.data.LOTRLevelData;
import lotr.common.data.LOTRPlayerData;
import lotr.common.init.ExtendedItems;
import lotr.common.util.ExtendedInteractableHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

import java.util.Optional;
import java.util.UUID;

public class ExtendedGundabadOrcChieftain extends GundabadOrcEntity implements ExtendedCaptainEntity {

    private static final SpawnEquipmentTable WEAPONS;
    private static final DataParameter<Optional<UUID>> PLAYER_INTERACTING_WITH = EntityDataManager.defineId(ExtendedGundabadOrcChieftain.class, DataSerializers.OPTIONAL_UUID);

    public ExtendedGundabadOrcChieftain(EntityType<? extends ExtendedGundabadOrcChieftain> type, World w) {
        super(type, w);
        setPersistenceRequired();
    }

    public static AttributeModifierMap.MutableAttribute regAttrs() {
        return OrcEntity.regAttrs().add(Attributes.MAX_HEALTH, 25.0);
    }

    @Override
    public boolean canHire(ResourceLocation unitProfileId, PlayerEntity player) {
        // TODO allow proper clientside validation
        if(player.level.isClientSide) {
            return isFriendlyAndAlignedWithLevel(player, this, 100);
        }
        // TODO: move pledge validation here maybe?
        ExtendedHiredUnitProfile unitProfile = ExtendedHiredUnitProfileManager.INSTANCE.get(unitProfileId);
        return isFriendlyAndAlignedWithLevel(player, this, unitProfile.getHiringAlignmentRequired());
    }

    @Override
    public int getCost(ResourceLocation unitProfileId, PlayerEntity player) {
        // TODO: add way to scale the pricing
        ExtendedHiredUnitProfile unitProfile = ExtendedHiredUnitProfileManager.INSTANCE.get(unitProfileId);
        LOTRPlayerData playerData = LOTRLevelData.getSidedData(player);
        if(unitProfile.isHiringRequiresPledge() && !playerData.getAlignmentData().isPledgedTo(getFaction())) {
            return 10000; //For now, due to the way this is coded, make pledge required units cost more than the player can spend
        }
        return unitProfile.getHiringCost();
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

    public ILivingEntityData finalizeSpawn(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT dataTag) {
        spawnData = super.finalizeSpawn(sw, diff, reason, spawnData, dataTag);
        this.npcItemsInv.setMeleeWeapon(WEAPONS.getRandomItem(this.random));

        this.setItemSlot(EquipmentSlotType.FEET, new ItemStack(ExtendedItems.GUNDABAD_URUK_BOOTS.get()));
        this.setItemSlot(EquipmentSlotType.LEGS, new ItemStack(ExtendedItems.GUNDABAD_URUK_LEGGINGS.get()));
        this.setItemSlot(EquipmentSlotType.CHEST, new ItemStack(ExtendedItems.GUNDABAD_URUK_CHESTPLATE.get()));

        this.setItemSlot(EquipmentSlotType.OFFHAND, ItemStack.EMPTY);
        return spawnData;
    }

    static {
        WEAPONS = SpawnEquipmentTable.of(ExtendedItems.GUNDABAD_URUK_CLEAVER);
    }
}
