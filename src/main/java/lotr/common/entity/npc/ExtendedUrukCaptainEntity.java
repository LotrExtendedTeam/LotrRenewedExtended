package lotr.common.entity.npc;

import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedCaptainTypes;

import lotr.common.entity.npc.ai.goal.ExtendedShieldSwapMeleeAttackGoal;
import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class ExtendedUrukCaptainEntity extends UrukEntity implements ExtendedNPCEntitySettings, ExtendedCaptainEntity{

	public ExtendedUrukCaptainEntity(EntityType<? extends UrukEntity> type, World w) {
		super(type, w);
	}

	public static AttributeModifierMap.MutableAttribute createAttributes() {
		return UrukEntity.regAttrs().add(Attributes.MAX_HEALTH, 30.0D);
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
	protected Goal createOrcAttackGoal() {
		return new ExtendedShieldSwapMeleeAttackGoal(this, 1.4D, new ItemStack(ExtendedItems.URUK_SHIELD.get()), ItemStack.EMPTY);
	}

	@Override
	public ILivingEntityData finalizeSpawn(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT dataTag) {
		spawnData = super.finalizeSpawn(sw, diff, reason, spawnData, dataTag);
		this.setItemSlot(EquipmentSlotType.FEET, new ItemStack(LOTRItems.URUK_BOOTS.get()));
		this.setItemSlot(EquipmentSlotType.LEGS, new ItemStack(LOTRItems.URUK_LEGGINGS.get()));
		this.setItemSlot(EquipmentSlotType.CHEST, new ItemStack(LOTRItems.URUK_CHESTPLATE.get()));
		setItemSlot(EquipmentSlotType.HEAD, ItemStack.EMPTY);
		this.setItemSlot(EquipmentSlotType.OFFHAND, ItemStack.EMPTY);
		return spawnData;
	}

	@Override
	public ResourceLocation getCaptainProfile() {
		return ExtendedCaptainTypes.ISENGARD_CAPTAIN;
	}
}
