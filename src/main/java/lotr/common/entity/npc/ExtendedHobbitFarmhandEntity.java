package lotr.common.entity.npc;


import com.github.maximuslotro.lotrrextended.common.entity.ai.goal.ExtendedFarmingGoalHolder;

import lotr.common.init.LOTRItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class ExtendedHobbitFarmhandEntity extends HobbitEntity implements ExtendedINPCEntitySettings, ExtendedIFarmhand {
    public ExtendedHobbitFarmhandEntity(EntityType<? extends HobbitEntity> type, World w) {
        super(type, w);
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return HobbitEntity.regAttrs();
    }

	@Override
	protected void addNPCAI() {
		super.addNPCAI();
		this.goalSelector.addGoal(3, new ExtendedFarmingGoalHolder(this, 1.0D, 0.05F).standardOrder());
	}

    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT dataTag) {
        spawnData = super.finalizeSpawn(sw, diff, reason, spawnData, dataTag);
		if(this.random.nextInt(5)==0) {
	        npcItemsInv.setMeleeWeapon(new ItemStack(LOTRItems.BRONZE_HOE.get()));
		}else {
	        npcItemsInv.setMeleeWeapon(new ItemStack(Items.IRON_HOE));
		}
        npcItemsInv.setIdleItemsFromMeleeWeapons();
        return spawnData;
    }
}
