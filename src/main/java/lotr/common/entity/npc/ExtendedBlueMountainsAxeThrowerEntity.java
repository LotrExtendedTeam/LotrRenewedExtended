package lotr.common.entity.npc;

import com.github.maximuslotro.lotrrextended.common.entity.ai.goal.ThrowingAxeAttackGoal;
import com.github.maximuslotro.lotrrextended.common.entity.projectile.ExtendedThrowableAxeEntity;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedThrowableAxeItem;

import lotr.common.entity.npc.ai.AttackGoalsHolder;
import lotr.common.entity.npc.ai.StandardAttackModeUpdaters;
import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRAttributes;
import lotr.common.init.LOTRItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.projectile.AbstractArrowEntity.PickupStatus;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class ExtendedBlueMountainsAxeThrowerEntity extends BlueDwarfWarriorEntity {
	@SuppressWarnings("unchecked")
	public ExtendedBlueMountainsAxeThrowerEntity(EntityType<? extends BlueDwarfWarriorEntity> type, World w) {
		super(type, w);
		this.getNPCCombatUpdater().setAttackModeUpdater(StandardAttackModeUpdaters.meleeRangedSwitching());
	}

	@Override
	public ILivingEntityData finalizeSpawn(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT dataTag) {
		spawnData = super.finalizeSpawn(sw, diff, reason, spawnData, dataTag);
		npcItemsInv.setRangedWeapon(new ItemStack(ExtendedItems.BLUE_DWARVEN_THROWABLE_AXE.get()));
		npcItemsInv.setMeleeWeapon(new ItemStack(LOTRItems.BLUE_DWARVEN_DAGGER.get()));
		npcItemsInv.setIdleItemsFromRangedWeapons();
		setItemSlot(EquipmentSlotType.OFFHAND, ItemStack.EMPTY);
		return spawnData;
	}

	@Override
	protected void initialiseAttackGoals(AttackGoalsHolder holder) {
		super.initialiseAttackGoals(holder);
		holder.setRangedAttackGoal(new ThrowingAxeAttackGoal<ExtendedBlueMountainsAxeThrowerEntity>(this, 1.25D, 20, 16.0F));
	}

	@Override
	public void performRangedAttack(LivingEntity target, float charge) {
		// Disregard the charge for now as it is calculated for bow charge, not actual damage
		ItemStack axe = this.getNPCItemsInv().getRangedWeapon();
		if(axe.getItem() instanceof ExtendedThrowableAxeItem) {
			axe = new ItemStack(ExtendedItems.BLUE_DWARVEN_THROWABLE_AXE.get());
		}
		axe.setCount(1);
		ExtendedThrowableAxeEntity axeEntity = new ExtendedThrowableAxeEntity(this.level, this, axe);

		// ===== OFFSET CALCULATION (like players) =====
		double offsetDistance = 0.5; // How far to the right
		double rightX = -Math.sin(Math.toRadians(this.yRot + 90.0));
		double rightZ = Math.cos(Math.toRadians(this.yRot + 90.0));

		axeEntity.setPos(
			this.getX() + rightX * offsetDistance,
			this.getEyeY() - 0.1, // Slightly below eye level
			this.getZ() + rightZ * offsetDistance
		);
		
		double dx = target.getX() - this.getX();
		double dy = target.getY(0.333D) - axeEntity.getY();
		double dz = target.getZ() - this.getZ();
		double dxzSq = MathHelper.sqrt(dx * dx + dz * dz);
		axeEntity.pickup = PickupStatus.CREATIVE_ONLY;
		axeEntity.shoot(dx, dy + dxzSq * 0.2D, dz, 1.0F, (float)getAttributeValue(LOTRAttributes.NPC_RANGED_INACCURACY.get()));
		playSound(SoundEvents.TRIDENT_THROW, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F));
		level.addFreshEntity(axeEntity);
	}
}
