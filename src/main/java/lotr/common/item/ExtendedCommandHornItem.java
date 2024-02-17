package lotr.common.item;

import com.github.maximuslotro.lotrExtended.common.init.ExtendedSounds;

import lotr.common.init.LOTRItemGroups;
import lotr.common.item.clientside.ExtendedCommandHornClientSide;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ExtendedCommandHornItem extends Item {
	public static final String type = "usage_type";
	public ExtendedCommandHornItem(Properties properties) {
		super(properties);
	}
	public ExtendedCommandHornItem() {
		super((new Item.Properties()).stacksTo(1).tab(LOTRItemGroups.COMBAT));
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

		if (nbtTagCompound!=null&&nbtTagCompound.contains(type) && nbtTagCompound.getInt(type) == 1) {
			nbtTagCompound.putInt(type, 2);
		} else if (nbtTagCompound!=null&&nbtTagCompound.contains(type) && nbtTagCompound.getInt(type) == 2) {
			nbtTagCompound.putInt(type, 1);
		}
		world.playSound((PlayerEntity)null, entityplayer.blockPosition(), ExtendedSounds.HORN_BLOW.get(), SoundCategory.PLAYERS, 4.0F, 1.0F);
		return itemstack;
	}
	
	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		ItemStack heldItem = player.getItemInHand(hand);
		CompoundNBT nbtTagCompound = heldItem.getTag();
		if (nbtTagCompound == null || !nbtTagCompound.contains(type)) {
			if (world.isClientSide()) {
				new ExtendedCommandHornClientSide().openMenu();
			}
			return ActionResult.sidedSuccess(heldItem, world.isClientSide());
		}
		if (nbtTagCompound != null && nbtTagCompound.contains(type)) {
			player.startUsingItem(hand);
			return ActionResult.sidedSuccess(heldItem, world.isClientSide());
		}
		return ActionResult.fail(heldItem);
	}

	@Override
	public String getDescriptionId(ItemStack itemstack) {
		CompoundNBT nbtTagCompound = itemstack.getTag();
		String s = "";
		if (nbtTagCompound != null && nbtTagCompound.contains(type)
				&& nbtTagCompound.getInt(type) == 1) {
			s = ".halt";
		} else if (nbtTagCompound != null && nbtTagCompound.contains(type)
				&& nbtTagCompound.getInt(type) == 2) {
			s = ".reddy";
		} else if (nbtTagCompound != null && nbtTagCompound.contains(type)
				&& nbtTagCompound.getInt(type) == 3) {
			s = ".summon";
		}
		return super.getDescriptionId()+s;
	}
}
