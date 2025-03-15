package lotr.common.entity.npc;

import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedHiredUnitTypes;

import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRItems;
import lotr.common.util.ExtendedCaptainHelper;
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
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class ExtendedDunedainRangerCaptainEntity extends ExtendedDunedainRangerEntity implements ExtendedCaptainEntity{

	public ExtendedDunedainRangerCaptainEntity(EntityType<? extends ExtendedDunedainRangerEntity> type, World w) {
		super(type, w);
		setPersistenceRequired();
	}

	public static AttributeModifierMap.MutableAttribute regAttrs() {
		return ExtendedDunedainRangerEntity.regAttrs().add(Attributes.MAX_HEALTH, 25.0);
	}

	@Override
	protected void addNPCTargetingAI() {
		this.addNonAggressiveTargetingGoals();
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
	public ResourceLocation getHireableProfile() {
		return ExtendedHiredUnitTypes.DUNEDAIN_RANGER;
	}
}
