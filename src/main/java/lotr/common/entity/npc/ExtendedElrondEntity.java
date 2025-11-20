package lotr.common.entity.npc;

import lotr.common.entity.npc.data.NPCGenderProvider;
import lotr.common.entity.npc.data.name.NPCNameGenerator;
import lotr.common.entity.npc.data.name.NPCNameGenerators;
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

public class ExtendedElrondEntity extends ExtendedAbstractCloakingElfEntity {

	public ExtendedElrondEntity(EntityType<? extends ExtendedElrondEntity> type, World w) {
		super(type, w);
	}

	public static AttributeModifierMap.MutableAttribute createAttributes() {
		return RivendellWarriorEntity.regAttrs().add(Attributes.MAX_HEALTH, 30.0D).add(Attributes.ARMOR, 9.0D);
	}

	@Override
	public void tick() {
		super.tick();
		if (level.isClientSide) {
			moveCloak();
			return;
		}
	}

	@Override
	protected NPCNameGenerator getNameGenerator() {
		return NPCNameGenerators.NAMELESS_THING;
	}

	@Override
	protected NPCGenderProvider getGenderProvider() {
		return NPCGenderProvider.MALE;
	}

	@Override
	public ILivingEntityData finalizeSpawn(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT dataTag) {
		spawnData = super.finalizeSpawn(sw, diff, reason, spawnData, dataTag);
		this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRItems.RIVENDELL_SWORD.get()));
		this.npcItemsInv.clearIdleItem();
		setItemSlot(EquipmentSlotType.FEET, ItemStack.EMPTY);
		setItemSlot(EquipmentSlotType.LEGS, ItemStack.EMPTY);
		setItemSlot(EquipmentSlotType.CHEST, ItemStack.EMPTY);
		setItemSlot(EquipmentSlotType.HEAD, ItemStack.EMPTY);
		setItemSlot(EquipmentSlotType.OFFHAND, ItemStack.EMPTY);
		return spawnData;
	}

	@Override
	public ResourceLocation getCapeTextureLocation() {
		return new ResourceLocation("lotrextended", "textures/capes/elrond.png");
	}

	@Override
	public ResourceLocation getCloakTextureLocation() {
		return null;
	}
}