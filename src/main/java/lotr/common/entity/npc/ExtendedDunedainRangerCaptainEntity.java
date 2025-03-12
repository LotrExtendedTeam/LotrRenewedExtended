package lotr.common.entity.npc;

import java.util.Optional;
import java.util.UUID;

import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedHiredUnitTypes;

import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRItems;
import lotr.common.util.ExtendedCaptainHelper;
import lotr.common.util.ExtendedInteractableHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class ExtendedDunedainRangerCaptainEntity extends ExtendedDunedainRangerEntity implements ExtendedCaptainEntity{
    private static final DataParameter<Optional<UUID>> PLAYER_INTERACTING_WITH = EntityDataManager.defineId(ExtendedDunedainRangerCaptainEntity.class, DataSerializers.OPTIONAL_UUID);

	public ExtendedDunedainRangerCaptainEntity(EntityType<? extends ExtendedDunedainRangerEntity> type, World w) {
		super(type, w);
		setPersistenceRequired();
	}

    public static AttributeModifierMap.MutableAttribute regAttrs() {
        return OrcEntity.regAttrs().add(Attributes.MAX_HEALTH, 25.0);
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
    public ActionResultType mobInteract(PlayerEntity player, Hand hand) {
        return ExtendedCaptainHelper.mobInteract(this, this, player, hand);
    }

    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT dataTag) {
        spawnData = super.finalizeSpawn(sw, diff, reason, spawnData, dataTag);

        setItemSlot(EquipmentSlotType.FEET, new ItemStack(LOTRItems.RANGER_NORTH_BOOTS.get()));
        setItemSlot(EquipmentSlotType.LEGS, new ItemStack(LOTRItems.RANGER_NORTH_LEGGINGS.get()));
        setItemSlot(EquipmentSlotType.CHEST, new ItemStack(LOTRItems.RANGER_NORTH_CHESTPLATE.get()));
        setItemSlot(EquipmentSlotType.HEAD, ItemStack.EMPTY);
        setItemSlot(EquipmentSlotType.OFFHAND, ItemStack.EMPTY);

        npcItemsInv.setMeleeWeapon(new ItemStack(Items.IRON_SWORD));
        npcItemsInv.setRangedWeapon(new ItemStack(ExtendedItems.DUNEDAIN_BOW.get()));
        npcItemsInv.setIdleItemsFromMeleeWeapons();

        return spawnData;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(PLAYER_INTERACTING_WITH, Optional.empty());
    }

	@Override
	public ResourceLocation getHireableProfile() {
		return ExtendedHiredUnitTypes.DUNEDAIN_RANGER;
	}
}
