package lotr.common.entity.npc;

import com.github.maximuslotro.lotrrextended.common.utils.ExtendedNBTUtils;

import lotr.common.init.LOTRItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class ExtendedHobbitFarmhandEntity extends HobbitEntity implements ExtendedINPCEntitySettings, ExtendedIFarmhand {
	private Inventory farmhandInventory;
    public ExtendedHobbitFarmhandEntity(EntityType<? extends HobbitEntity> type, World w) {
        super(type, w);
        farmhandInventory = new Inventory(4);
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return HobbitEntity.regAttrs();
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

	@Override
	public Inventory getFarmhandInventory() {
		return farmhandInventory;
	}

	@Override
	public void addAdditionalSaveData(final CompoundNBT nbt) {
		super.addAdditionalSaveData(nbt);
		ExtendedNBTUtils.writeInventoryToNBT(nbt, farmhandInventory, FARMHANDITEMSLIST);
	}

	@Override
	public void readAdditionalSaveData(final CompoundNBT nbt) {
		super.readAdditionalSaveData(nbt);
		ExtendedNBTUtils.readInventoryFromNBT(nbt, farmhandInventory, FARMHANDITEMSLIST);
	}
}
