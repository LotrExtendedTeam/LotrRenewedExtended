package lotr.common.entity.npc;

import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;
import com.github.maximuslotro.lotrrextended.common.traders.ExtendedTraderTypes;

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

public class ExtendedUrukSupplierEntity extends ExtendedUrukTraderEntity{

	public ExtendedUrukSupplierEntity(EntityType<? extends ExtendedUrukTraderEntity> type, World w) {
		super(type, w);
	}

	public static AttributeModifierMap.MutableAttribute createAttributes() {
		return UrukEntity.regAttrs();
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
		this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRItems.URUK_DAGGER.get()));
        this.npcItemsInv.setIdleItemsFromMeleeWeapons();
        ItemStack headItem;
        ItemStack chestItem;
        ItemStack legaItem;
        ItemStack bootsItem;
        if (random.nextInt(5) == 0) {
        	headItem= new ItemStack(ExtendedItems.BLACK_WARG_HELMET.get());
            chestItem= new ItemStack(ExtendedItems.BLACK_WARG_CHESTPLATE.get());
            legaItem= new ItemStack(ExtendedItems.BLACK_WARG_LEGGINGS.get());
            bootsItem= new ItemStack(ExtendedItems.BLACK_WARG_BOOTS.get());
		} else if(random.nextInt(3) == 0) {
        	headItem= new ItemStack(ExtendedItems.GRAY_WARG_HELMET.get());
            chestItem= new ItemStack(ExtendedItems.GRAY_WARG_CHESTPLATE.get());
            legaItem= new ItemStack(ExtendedItems.GRAY_WARG_LEGGINGS.get());
            bootsItem= new ItemStack(ExtendedItems.GRAY_WARG_BOOTS.get());
		}else{
			headItem= new ItemStack(ExtendedItems.BROWN_WARG_HELMET.get());
            chestItem= new ItemStack(ExtendedItems.BROWN_WARG_CHESTPLATE.get());
            legaItem= new ItemStack(ExtendedItems.BROWN_WARG_LEGGINGS.get());
            bootsItem= new ItemStack(ExtendedItems.BROWN_WARG_BOOTS.get());
		}
		this.setItemSlot(EquipmentSlotType.FEET, bootsItem);
		this.setItemSlot(EquipmentSlotType.LEGS, legaItem);
		this.setItemSlot(EquipmentSlotType.CHEST, chestItem);
		this.setItemSlot(EquipmentSlotType.HEAD, headItem);
		this.setItemSlot(EquipmentSlotType.OFFHAND, ItemStack.EMPTY);
		return spawnData;
	}

	@Override
	public ResourceLocation getTraderProfileId() {
		return ExtendedTraderTypes.ISENGARD_URUK_SUPPLIER;
	}

}
