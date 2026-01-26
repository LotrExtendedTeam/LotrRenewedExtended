package lotr.common.entity.npc;

import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedHiredUnitTypes;

import java.util.Arrays;
import java.util.List;

import lotr.common.entity.npc.ai.goal.ExtendedShieldSwapMeleeAttackGoal;
import lotr.common.entity.npc.data.NPCGenderProvider;
import lotr.common.entity.npc.util.LeatherDyeUtil;
import lotr.common.init.ExtendedItems;
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
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class ExtendedBreelandSheriffEntity extends BreeManEntity  implements ExtendedNPCEntitySettings, ExtendedCaptainEntity {

    private static final int[] SUIT_COLORS = new int[]{11373426, 7823440, 5983041, 9535090};

    public ExtendedBreelandSheriffEntity(EntityType<? extends ExtendedBreelandSheriffEntity> type, World w) {
        super(type, w);
    }

    @Override
    protected void addNPCTargetingAI() {
    	if(ExtendedServerConfig.enableLegacyCaptainPassiveBehavior.get()) {
    		this.addNonAggressiveTargetingGoals();
    	}else {
    		this.addAggressiveTargetingGoals();
    	}
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return BreeManEntity.regAttrs().add(Attributes.MAX_HEALTH, 25.0D);
    }

    @Override
    protected Goal createAttackGoal() {
        return new ExtendedShieldSwapMeleeAttackGoal(this, 1.45, new ItemStack(ExtendedItems.BREE_SHIELD.get()), ItemStack.EMPTY);
    }

    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT dataTag) {
        spawnData = super.finalizeSpawn(sw, diff, reason, spawnData, dataTag);
        this.npcItemsInv.setMeleeWeapon(new ItemStack(Items.IRON_SWORD));
        this.npcItemsInv.setIdleItem(new ItemStack(Items.WRITABLE_BOOK));

        this.setItemSlot(EquipmentSlotType.FEET, LeatherDyeUtil.dyeLeather(Items.LEATHER_BOOTS, 3354152));
        this.setItemSlot(EquipmentSlotType.LEGS, LeatherDyeUtil.dyeLeather(Items.LEATHER_LEGGINGS, SUIT_COLORS, this.random));
        this.setItemSlot(EquipmentSlotType.CHEST, LeatherDyeUtil.dyeLeather(Items.LEATHER_CHESTPLATE, SUIT_COLORS, this.random));
        this.setItemSlot(EquipmentSlotType.HEAD, new ItemStack(ExtendedItems.LEATHER_HAT.get()));
        this.setItemSlot(EquipmentSlotType.OFFHAND, ItemStack.EMPTY);
        return spawnData;
    }

    @Override
	protected NPCGenderProvider getGenderProvider() {
        return NPCGenderProvider.MALE;
    }

	@Override
	public List<ResourceLocation> getHireableProfiles() {
		return Arrays.asList(ExtendedHiredUnitTypes.BREE_HOBBIT, 
			ExtendedHiredUnitTypes.BREE_MAN, 
			ExtendedHiredUnitTypes.BREE_GUARD);
	}

	@Override
	public boolean addExtendedGoals() {
		return true;
	}
}