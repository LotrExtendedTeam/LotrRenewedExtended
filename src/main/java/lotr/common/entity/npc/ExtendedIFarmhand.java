package lotr.common.entity.npc;

import net.minecraft.inventory.Inventory;

public interface ExtendedIFarmhand {
	public static String FARMHANDITEMSLIST = "farmItems";

	Inventory getFarmhandInventory();
}
