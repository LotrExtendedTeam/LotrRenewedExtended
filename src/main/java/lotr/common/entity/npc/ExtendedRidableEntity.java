package lotr.common.entity.npc;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public interface ExtendedRidableEntity {
	// Armor Methods
	public boolean canWearArmor();

	public boolean isWearingArmor();

	public boolean isArmor(ItemStack pStack);

	public ItemStack getArmor();

	public void setArmorEquipment(ItemStack pStack);

	// Tame Methods
	public boolean isTamed();

	public boolean tameWithName(PlayerEntity pPlayer);

	// Temper Methods
	public int getTemper();

	public int getMaxTemper();

	public int modifyTemper(int pAddedTemper);

	public void makeMad();

	// Inventory methods
	public int getInventorySize();

	public void openInventory(PlayerEntity pPlayer);
}
