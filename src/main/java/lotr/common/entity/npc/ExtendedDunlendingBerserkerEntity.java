package lotr.common.entity.npc;

import lotr.common.entity.npc.ai.goal.NPCMeleeAttackGoal;
import lotr.common.init.LOTRItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class ExtendedDunlendingBerserkerEntity extends DunlendingWarriorEntity implements ExtendedINPCEntitySettings {
	private static final SpawnEquipmentTable WEAPONS = SpawnEquipmentTable.of(Items.IRON_AXE, LOTRItems.BRONZE_AXE);

	public ExtendedDunlendingBerserkerEntity(EntityType<? extends ExtendedDunlendingBerserkerEntity> type, World w) {
		super(type, w);
	}

	public static AttributeModifierMap.MutableAttribute createAttributes() {
		return DunlendingWarriorEntity.regAttrs().add(Attributes.MAX_HEALTH, 30.0D)
			.add(Attributes.FOLLOW_RANGE, 24.0D)
			.add(Attributes.ATTACK_DAMAGE, 2.0D)
			.add(Attributes.KNOCKBACK_RESISTANCE, 0.4D)
			.add(Attributes.ATTACK_KNOCKBACK, 0.9D);
	}

	@Override
	protected Goal createAttackGoal() {
		return new NPCMeleeAttackGoal(this, 1.7D);
	}

	@Override
	public ILivingEntityData finalizeSpawn(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT dataTag) {
		spawnData = super.finalizeSpawn(sw, diff, reason, spawnData, dataTag);
		this.npcItemsInv.setMeleeWeapon(WEAPONS.getRandomItem(random));
		this.npcItemsInv.setIdleItemsFromMeleeWeapons();
		this.setItemSlot(EquipmentSlotType.FEET, new ItemStack(LOTRItems.FUR_BOOTS.get()));
		this.setItemSlot(EquipmentSlotType.LEGS, new ItemStack(LOTRItems.FUR_LEGGINGS.get()));
		this.setItemSlot(EquipmentSlotType.CHEST, new ItemStack(LOTRItems.FUR_CHESTPLATE.get()));
		this.setItemSlot(EquipmentSlotType.HEAD, new ItemStack(LOTRItems.DUNLENDING_HELMET.get()));
		this.setItemSlot(EquipmentSlotType.OFFHAND, ItemStack.EMPTY);
		return spawnData;
	}
}