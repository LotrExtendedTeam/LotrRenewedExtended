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

		if (isHalt(itemstack)) {
			nbtTagCompound.putFloat(type, 2.0F);
		} else if (isReddy(itemstack)) {
			nbtTagCompound.putFloat(type, 1.0F);
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
		if (isHalt(itemstack)) {
			s = ".halt";
		} else if (isReddy(itemstack)) {
			s = ".reddy";
		} else if (isSummon(itemstack)) {
			s = ".summon";
		}
		return super.getDescriptionId()+s;
	}

	public static boolean isNotEmpty(ItemStack itemStack) {
		CompoundNBT compoundnbt = itemStack.getTag();
		return compoundnbt != null && compoundnbt.contains(type);
	}

	public static boolean isHalt(ItemStack itemStack) {
		CompoundNBT compoundnbt = itemStack.getTag();
		return compoundnbt != null && compoundnbt.contains(type) && compoundnbt.getInt(type) == 1.0F;
	}

	public static boolean isReddy(ItemStack itemStack) {
		CompoundNBT compoundnbt = itemStack.getTag();
		return compoundnbt != null && compoundnbt.contains(type) && compoundnbt.getInt(type) == 2.0F;
	}

	public static boolean isSummon(ItemStack itemStack) {
		CompoundNBT compoundnbt = itemStack.getTag();
		return compoundnbt != null && compoundnbt.contains(type) && compoundnbt.getInt(type) == 3.0F;
	}
}
