package lotr.common.entity.npc;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedItems;
import com.github.maximuslotro.lotrrextended.common.traders.ExtendedTraderTypes;

import lotr.common.entity.npc.data.NPCGenderProvider;
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

public class ExtendedBreelandSmithEntity extends ExtendedBreelandTraderEntity {

	public ExtendedBreelandSmithEntity(EntityType<? extends BreeManEntity> type, World w) {
		super(type, w);
	}

	@Override
	public ILivingEntityData finalizeSpawn(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT dataTag) {
		spawnData = super.finalizeSpawn(sw, diff, reason, spawnData, dataTag);
		this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRItems.BLACKSMITH_HAMMER.get(), 1));
		this.npcItemsInv.setIdleItemsFromMeleeWeapons();
		this.setItemSlot(EquipmentSlotType.HEAD, new ItemStack(ExtendedItems.LEATHER_HAT.get()));
		return spawnData;
	}


	public static AttributeModifierMap.MutableAttribute createAttributes() {
		return BreeManEntity.regAttrs().add(Attributes.MAX_HEALTH, 25.0D);
	}

	@Override
	public NPCGenderProvider getGenderProvider() {
		return NPCGenderProvider.MALE;
	}

	@Override
	public ResourceLocation getTraderProfileId() {
		return ExtendedTraderTypes.BREE_SMITH;
	}
}