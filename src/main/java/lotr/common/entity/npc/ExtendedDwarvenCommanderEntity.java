package lotr.common.entity.npc;

import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedHiredUnitTypes;

import lotr.common.entity.npc.data.NPCGenderProvider;
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

public class ExtendedDwarvenCommanderEntity extends BlueDwarfEntity  implements ExtendedNPCEntitySettings, ExtendedCaptainEntity {

	public ExtendedDwarvenCommanderEntity(EntityType<? extends ExtendedDwarvenCommanderEntity> type, World w) {
		super(type, w);
	}

	public static AttributeModifierMap.MutableAttribute createAttributes() {
		return DwarfEntity.regAttrs();
	}

	@Override
	protected void addNPCTargetingAI() {
		if(ExtendedServerConfig.enableLegacyCaptainPassiveBehavior.get()) {
			this.addNonAggressiveTargetingGoals();
		}else {
			this.addAggressiveTargetingGoals();
		}
	}
//
//	@Override
//    protected Goal createDwarfAttackGoal() {
//        return new ExtendedShieldSwapMeleeAttackGoal(this, 1.3, new ItemStack(ExtendedItems.DWARVEN_SHIELD.get()), ItemStack.EMPTY);
//    }

	@Override
	public ILivingEntityData finalizeSpawn(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT dataTag) {
		spawnData = super.finalizeSpawn(sw, diff, reason, spawnData, dataTag);
		this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRItems.DWARVEN_SWORD.get()));
		this.npcItemsInv.setIdleItemsFromMeleeWeapons();
		setItemSlot(EquipmentSlotType.FEET, new ItemStack(LOTRItems.GOLD_TRIMMED_DWARVEN_BOOTS.get()));
		setItemSlot(EquipmentSlotType.LEGS, new ItemStack(LOTRItems.GOLD_TRIMMED_DWARVEN_LEGGINGS.get()));
		setItemSlot(EquipmentSlotType.CHEST, new ItemStack(LOTRItems.GOLD_TRIMMED_DWARVEN_CHESTPLATE.get()));
		setItemSlot(EquipmentSlotType.HEAD, ItemStack.EMPTY);
		setItemSlot(EquipmentSlotType.OFFHAND, ItemStack.EMPTY);
		return spawnData;
	}

	@Override
	protected NPCGenderProvider getGenderProvider() {
		return NPCGenderProvider.MALE;
	}

	@Override
	public List<ResourceLocation> getHireableProfiles() {
		return java.util.Arrays.asList(ExtendedHiredUnitTypes.DWARF, 
			ExtendedHiredUnitTypes.DWARF_WARRIOR, 
			ExtendedHiredUnitTypes.DWARF_ARCHER);
	}

	@Override
	public boolean addExtendedGoals() {
		return true;
	}
}