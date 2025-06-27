package lotr.common.entity.npc;

import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;
import lotr.common.entity.npc.ai.goal.*;
import lotr.common.entity.npc.data.NPCGenderProvider;
import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class ExtendedBlueMountainsCommanderEntity extends BlueDwarfEntity implements ExtendedCaptainEntity {

	public ExtendedBlueMountainsCommanderEntity(EntityType<? extends ExtendedBlueMountainsCommanderEntity> type, World w) {
		super(type, w);
		setPersistenceRequired();
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

	@Override
    protected Goal createDwarfAttackGoal() {
        return new ExtendedShieldSwapMeleeAttackGoal(this, 1.3, new ItemStack(ExtendedItems.BLUE_MOUNTAINS_SHIELD.get()), ItemStack.EMPTY);
    }

	@Override
	public ILivingEntityData finalizeSpawn(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT dataTag) {
		spawnData = super.finalizeSpawn(sw, diff, reason, spawnData, dataTag);
		this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRItems.BLUE_DWARVEN_SWORD.get()));
		this.npcItemsInv.setIdleItemsFromMeleeWeapons();
		setItemSlot(EquipmentSlotType.FEET, new ItemStack(LOTRItems.BLUE_DWARVEN_BOOTS.get()));
		setItemSlot(EquipmentSlotType.LEGS, new ItemStack(LOTRItems.BLUE_DWARVEN_LEGGINGS.get()));
		setItemSlot(EquipmentSlotType.CHEST, new ItemStack(LOTRItems.BLUE_DWARVEN_CHESTPLATE.get()));
		return spawnData;
	}

	@Override
	protected NPCGenderProvider getGenderProvider() {
		return NPCGenderProvider.MALE;
	}

	@Override
	public ResourceLocation getHireableProfile() {
		// TODO Auto-generated method stub
		return null;
	}
}