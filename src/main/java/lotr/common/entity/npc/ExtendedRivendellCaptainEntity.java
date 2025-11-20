package lotr.common.entity.npc;

import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedHiredUnitTypes;

import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

import java.util.List;

public class ExtendedRivendellCaptainEntity extends RivendellWarriorEntity implements ExtendedCaptainEntity {

	public ExtendedRivendellCaptainEntity(EntityType<? extends ExtendedRivendellCaptainEntity> type, World w) {
		super(type, w);
	}

	public static AttributeModifierMap.MutableAttribute createAttributes() {
		return RivendellWarriorEntity.regAttrs();
	}

	@Override
	protected void addNPCTargetingAI() {
		if(ExtendedServerConfig.enableLegacyCaptainPassiveBehavior.get()) {
			this.addNonAggressiveTargetingGoals();
		}else {
			this.addAggressiveTargetingGoals();
		}
	}

	@Override
	public ILivingEntityData finalizeSpawn(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT dataTag) {
		spawnData = super.finalizeSpawn(sw, diff, reason, spawnData, dataTag);
		this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRItems.RIVENDELL_SWORD.get()));
		this.npcItemsInv.setRangedWeapon(new ItemStack(ExtendedItems.RIVENDELL_BOW.get()));
		this.npcItemsInv.setIdleItemsFromMeleeWeapons();
		setItemSlot(EquipmentSlotType.FEET, new ItemStack(LOTRItems.RIVENDELL_BOOTS.get()));
		setItemSlot(EquipmentSlotType.LEGS, new ItemStack(LOTRItems.RIVENDELL_LEGGINGS.get()));
		setItemSlot(EquipmentSlotType.CHEST, new ItemStack(LOTRItems.RIVENDELL_CHESTPLATE.get()));
		setItemSlot(EquipmentSlotType.HEAD, ItemStack.EMPTY);
		return spawnData;
	}

	@Override
	public List<ResourceLocation> getHireableProfiles() {
		return java.util.Arrays.asList(ExtendedHiredUnitTypes.RIVENDELL_ELF, ExtendedHiredUnitTypes.RIVENDELL_WARRIOR);
	}
}