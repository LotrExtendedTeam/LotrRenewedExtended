package lotr.common.entity.npc;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedItems;

import lotr.common.entity.npc.ai.goal.NPCMeleeAttackGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap.MutableAttribute;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class ExtendedGundabadUrukEntity extends GundabadOrcEntity implements ExtendedINPCEntitySettings {
	private static final SpawnEquipmentTable WEAPONS = SpawnEquipmentTable.of(
		ExtendedItems.GUNDABAD_URUK_CLEAVER, ExtendedItems.GUNDABAD_URUK_CLEAVER,
		ExtendedItems.GUNDABAD_URUK_DAGGER, ExtendedItems.GUNDABAD_URUK_DAGGER,
		ExtendedItems.GUNDABAD_URUK_SPEAR, ExtendedItems.GUNDABAD_URUK_PIKE);

	public ExtendedGundabadUrukEntity(EntityType<? extends GundabadOrcEntity> type, World w) {
		super(type, w);
		isOrcWeakInSun = false;
	}

	@Override
	protected Goal createOrcAttackGoal() {
		return new NPCMeleeAttackGoal(this, 1.5D);
	}

	@Override
	public ILivingEntityData finalizeSpawn(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT dataTag) {
		spawnData = super.finalizeSpawn(sw, diff, reason, spawnData, dataTag);
		npcItemsInv.setMeleeWeapon(WEAPONS.getRandomItem(random));
		npcItemsInv.setIdleItemsFromMeleeWeapons();
		setItemSlot(EquipmentSlotType.FEET, new ItemStack(ExtendedItems.GUNDABAD_URUK_BOOTS.get()));
		setItemSlot(EquipmentSlotType.LEGS, new ItemStack(ExtendedItems.GUNDABAD_URUK_LEGGINGS.get()));
		setItemSlot(EquipmentSlotType.CHEST, new ItemStack(ExtendedItems.GUNDABAD_URUK_CHESTPLATE.get()));
		setItemSlot(EquipmentSlotType.HEAD, new ItemStack(ExtendedItems.GUNDABAD_URUK_HELMET.get()));
		setItemSlot(EquipmentSlotType.OFFHAND, new ItemStack(ExtendedItems.GUNDABAD_SHIELD.get()));
		return spawnData;
	}

	@Override
	protected float getVoicePitch() {
		return super.getVoicePitch() * 0.75F;
	}

	public static MutableAttribute createAttributes() {
		return OrcEntity.regAttrs().add(Attributes.MAX_HEALTH, 25.0D).add(Attributes.MOVEMENT_SPEED, 0.22D);
	}
}
