package lotr.common.item;

import java.util.UUID;

import javax.annotation.Nullable;

import lotr.common.init.LOTRItemGroups;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;

public class ExtendedBrandingIronItem extends Item{
	public static final String isHot = "heated";
	public static final String UserUUID = "TempererName";
	public static final String Name = "TempererName";
	public static final String USES_LEFT = "Uses";

	public ExtendedBrandingIronItem(Properties p_i48487_1_) {
		super(p_i48487_1_);
	}

	public ExtendedBrandingIronItem() {
		super(new Properties().stacksTo(1).tab(LOTRItemGroups.TOOLS).durability(100));
	}

	@Override
	public boolean isValidRepairItem(ItemStack p_82789_1_, ItemStack p_82789_2_) {
		return p_82789_2_.getItem() == Items.IRON_INGOT;
	}

	//Item Heated Handling
	public static boolean isTempered(ItemStack itemStack) {
		CompoundNBT compoundnbt = itemStack.getTag();
		return compoundnbt != null && compoundnbt.contains(isHot) && compoundnbt.getBoolean(isHot);
	}

	public static void setTempered(ItemStack itemStack, boolean type) {
		if(!itemStack.hasTag()) {
			itemStack.setTag(new CompoundNBT());
		}
		itemStack.getTag().putBoolean(isHot, type);
		if(type) {
			setUses(itemStack, 5);
		}
		else {
			setUses(itemStack, 0);
		}
	}

	//Item Uses Data Handling
	public static int getUses(ItemStack itemStack) {
		CompoundNBT compoundnbt = itemStack.getTag();
		if(compoundnbt != null && compoundnbt.contains(USES_LEFT)) {
			return compoundnbt.getInt(USES_LEFT);
		}
		return 0;
	}

	public static void setUses(ItemStack itemStack, int number) {
		if(!itemStack.hasTag()) {
			itemStack.setTag(new CompoundNBT());
		}
		itemStack.getTag().putInt(USES_LEFT, number);
	}

	//Item Renamed Data Handling
	@Nullable
	public static String getTemperedName(ItemStack itemStack) {
		CompoundNBT compoundnbt = itemStack.getTag();
		if(compoundnbt != null && compoundnbt.contains(Name)) {
			return compoundnbt.getString(Name);
		}
		return null;
	}

	public static void setTemperedName(ItemStack itemStack, String name) {
		if(!itemStack.hasTag()) {
			itemStack.setTag(new CompoundNBT());
		}
		itemStack.getTag().putString(Name, name);
	}

	//Entity Master Data Handling
	@Nullable
	public static UUID getTempererName(Entity entity) {
		CompoundNBT compoundnbt = entity.getPersistentData();
		if(compoundnbt != null && compoundnbt.contains(UserUUID)) {
			return UUID.fromString(compoundnbt.getString(UserUUID));
		}
		return null;
	}

	public static void setTempererName(Entity entity, UUID master) {
		entity.getPersistentData().putString(UserUUID, master.toString());
	}
}
