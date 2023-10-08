package lotr.common.item;

import lotr.client.gui.ExtendedGuiHornSelect;
import lotr.common.init.LOTRItemGroups;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class CommandHornItem extends Item {
	public static final String type = "usageType";
	public CommandHornItem(Properties properties) {
		super(properties);
	}
	public CommandHornItem() {
		super((new Item.Properties()).stacksTo(1).tab(LOTRItemGroups.MISC));
	}

	@Override
	public UseAction getUseAnimation(ItemStack p_77661_1_) {
		return UseAction.BOW;
	}
	
	@Override
	public int getUseDuration(ItemStack p_77626_1_) {
		return 40;
	}
	
	@Override
	public ItemStack finishUsingItem(ItemStack itemstack, World world, LivingEntity entityplayer) {
		CompoundNBT nbtTagCompound = itemstack.getTag();
		if (nbtTagCompound == null) {
	        nbtTagCompound = new CompoundNBT();
	        itemstack.setTag(nbtTagCompound);
	    }
//		if (!world.field_72995_K) {
//			List<LOTREntityNPC> entities = world.field_72996_f;
//			for (int l = 0; l < entities.size(); l++) {
//				if (entities.get(l) instanceof LOTREntityNPC) {
//					LOTREntityNPC npc = entities.get(l);
//					if (npc.hiredNPCInfo.isActive && npc.hiredNPCInfo
//							.getHiringPlayer() == entityplayer)
//						if (LOTRSquadrons.areSquadronsCompatible(npc, itemstack))
//							if (itemstack.func_77960_j() == 1
//									&& npc.hiredNPCInfo.getObeyHornHaltReady()) {
//								npc.hiredNPCInfo.halt();
//							} else if (itemstack.func_77960_j() == 2
//									&& npc.hiredNPCInfo.getObeyHornHaltReady()) {
//								npc.hiredNPCInfo.ready();
//							} else if (itemstack.func_77960_j() == 3
//									&& npc.hiredNPCInfo.getObeyHornSummon()) {
//								npc.hiredNPCInfo.tryTeleportToHiringPlayer(true);
//							}
//				}
//			}
//		}
		if (nbtTagCompound!=null&&nbtTagCompound.contains(type) && nbtTagCompound.getInt(type) == 1) {
			nbtTagCompound.putInt(type, 2);
		} else if (nbtTagCompound!=null&&nbtTagCompound.contains(type) && nbtTagCompound.getInt(type) == 2) {
			nbtTagCompound.putInt(type, 1);
		}
		//world.func_72956_a((Entity) entityplayer, "lotr:item.horn", 4.0F, 1.0F);
		return itemstack;
	}
	
	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		ItemStack itemstack = player.getItemInHand(hand);
		CompoundNBT nbtTagCompound = itemstack.getTag();
		if (nbtTagCompound == null) {
	        nbtTagCompound = new CompoundNBT();
	        itemstack.setTag(nbtTagCompound);
	    }
		if (!nbtTagCompound.contains(type)) {
			Minecraft.getInstance().setScreen(new ExtendedGuiHornSelect());
		} else {
			//itemstack.func_71008_a(itemstack, func_77626_a(itemstack));
		}
		return ActionResult.pass(itemstack);
	}
	@Override
	public String getDescriptionId(ItemStack itemstack) {
		String s = "";
		CompoundNBT nbtTagCompound = itemstack.getTag();
		if (nbtTagCompound != null && nbtTagCompound.contains(type)
				&& nbtTagCompound.getInt(type) == 1) {
			s = ".halt";
		} else if (nbtTagCompound != null && nbtTagCompound.contains(type)
				&& nbtTagCompound.getInt(type) == 2) {
			s = ".ready";
		} else if (nbtTagCompound != null && nbtTagCompound.contains(type)
				&& nbtTagCompound.getInt(type) == 3) {
			s = ".summon";
		}
		return super.getDescriptionId(itemstack) + s;
	}
}
