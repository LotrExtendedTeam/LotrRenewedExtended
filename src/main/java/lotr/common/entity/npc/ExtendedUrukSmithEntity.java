package lotr.common.entity.npc;

import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;
import com.github.maximuslotro.lotrrextended.common.traders.ExtendedTraderTypes;

import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class ExtendedUrukSmithEntity extends ExtendedUrukTraderEntity{

	public ExtendedUrukSmithEntity(EntityType<? extends ExtendedUrukTraderEntity> type, World w) {
		super(type, w);
	}

	public static AttributeModifierMap.MutableAttribute createAttributes() {
		return UrukEntity.regAttrs().add(Attributes.MAX_HEALTH, 27.0D);
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
		this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRItems.BLACKSMITH_HAMMER.get()));
        this.npcItemsInv.setIdleItemsFromMeleeWeapons();
        this.setItemSlot(EquipmentSlotType.FEET, new ItemStack(LOTRItems.URUK_BOOTS.get()));
		this.setItemSlot(EquipmentSlotType.LEGS, new ItemStack(LOTRItems.URUK_LEGGINGS.get()));
		this.setItemSlot(EquipmentSlotType.CHEST, new ItemStack(ExtendedItems.BROWN_WARG_CHESTPLATE.get()));
		this.setItemSlot(EquipmentSlotType.HEAD, ItemStack.EMPTY);
		this.setItemSlot(EquipmentSlotType.OFFHAND, ItemStack.EMPTY);
		return spawnData;
	}

	@Override
	public ResourceLocation getTraderProfileId() {
		return ExtendedTraderTypes.ISENGARD_URUK_SMITH;
	}

}
