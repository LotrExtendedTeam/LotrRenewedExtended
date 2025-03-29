package lotr.common.entity.npc;

import java.util.ArrayList;
import java.util.List;
import lotr.common.entity.npc.data.NPCGenderProvider;
import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRItems;
import lotr.common.util.representation.ExtendedTrade;
import lotr.common.util.representation.ExtendedTradeEntries;
import lotr.common.util.representation.ExtendedTradeEntry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class ExtendedBreelandSmithEntity extends ExtendedBreelandTraderEntity {

	private final ExtendedTradeEntries BREE_SMITHY_BOUGHT = new ExtendedTradeEntries(ExtendedTradeEntries.TradeType.BOUGHT, new ExtendedTradeEntry[] {
			// Ores
			new ExtendedTradeEntry(new ItemStack(LOTRItems.COPPER_INGOT.get()), 3),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.TIN_INGOT.get()), 3),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.BRONZE_INGOT.get()), 3),
			new ExtendedTradeEntry(new ItemStack(Items.IRON_INGOT), 4),
			new ExtendedTradeEntry(new ItemStack(Items.IRON_NUGGET, 9), 3),
			new ExtendedTradeEntry(new ItemStack(Items.GOLD_INGOT), 15),
			new ExtendedTradeEntry(new ItemStack(Items.GOLD_NUGGET, 9), 15),
			new ExtendedTradeEntry(new ItemStack(Items.COAL), 1),
			// Misc
			new ExtendedTradeEntry(new ItemStack(Items.STRING, 3), 1),
			new ExtendedTradeEntry(new ItemStack(Items.LEATHER), 2),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.FUR.get()), 2),
			new ExtendedTradeEntry(new ItemStack(ExtendedItems.BLACK_FUR.get()), 2),
			new ExtendedTradeEntry(new ItemStack(ExtendedItems.GRAY_FUR.get()), 2),
			new ExtendedTradeEntry(new ItemStack(ExtendedItems.SILVER_FUR.get()), 3),
			new ExtendedTradeEntry(new ItemStack(ExtendedItems.WHITE_FUR.get()), 50),
			// Gems
			new ExtendedTradeEntry(new ItemStack(ExtendedItems.AMBER.get()), 10),
			new ExtendedTradeEntry(new ItemStack(ExtendedItems.EMERALD.get()), 15),
			new ExtendedTradeEntry(new ItemStack(ExtendedItems.SAPPHIRE.get()), 12),
			new ExtendedTradeEntry(new ItemStack(ExtendedItems.TOPAZ.get()), 8)
	});

	private final ExtendedTradeEntries BREE_SMITHY_SOLD = new ExtendedTradeEntries(ExtendedTradeEntries.TradeType.SOLD, new ExtendedTradeEntry[] {
			// Armor
			new ExtendedTradeEntry(new ItemStack(LOTRItems.BRONZE_HELMET.get()), 10),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.BRONZE_CHESTPLATE.get()), 14),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.BRONZE_LEGGINGS.get()), 12),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.BRONZE_BOOTS.get()), 8),
			new ExtendedTradeEntry(new ItemStack(Items.CHAINMAIL_HELMET), 12),
			new ExtendedTradeEntry(new ItemStack(Items.CHAINMAIL_CHESTPLATE), 16),
			new ExtendedTradeEntry(new ItemStack(Items.CHAINMAIL_LEGGINGS), 14),
			new ExtendedTradeEntry(new ItemStack(Items.CHAINMAIL_BOOTS), 10),
			new ExtendedTradeEntry(new ItemStack(Items.IRON_HELMET), 16),
			new ExtendedTradeEntry(new ItemStack(Items.IRON_CHESTPLATE), 22),
			new ExtendedTradeEntry(new ItemStack(Items.IRON_LEGGINGS), 18),
			new ExtendedTradeEntry(new ItemStack(Items.IRON_BOOTS), 14),
			new ExtendedTradeEntry(new ItemStack(Items.IRON_HORSE_ARMOR), 30),
			// Blocks
			new ExtendedTradeEntry(new ItemStack(LOTRItems.BREE_CRAFTING_TABLE.get()), 100),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.BRONZE_BARS.get(), 8), 20),
			new ExtendedTradeEntry(new ItemStack(Items.IRON_BARS, 8), 20),
			// Weapons
			new ExtendedTradeEntry(new ItemStack(LOTRItems.BRONZE_DAGGER.get()), 7),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.BRONZE_SWORD.get()), 10),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.BRONZE_SPEAR.get()), 13),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.IRON_DAGGER.get()), 9),
			new ExtendedTradeEntry(new ItemStack(Items.IRON_SWORD), 12),
			new ExtendedTradeEntry(new ItemStack(LOTRItems.IRON_SPEAR.get()), 15),
			// Tools
			new ExtendedTradeEntry(new ItemStack(LOTRItems.BLACKSMITH_HAMMER.get()), 16),

	});

	private ArrayList<ExtendedTrade> itemsSoldByTrader;
	private ArrayList<ExtendedTrade> itemsBoughtByTrader;

	public ExtendedBreelandSmithEntity(EntityType<? extends BreeManEntity> type, World w) {
		super(type, w);

		itemsBoughtByTrader = BREE_SMITHY_BOUGHT.generateTrades();
		itemsSoldByTrader = BREE_SMITHY_SOLD.generateTrades();
	}

	@Override
	public ILivingEntityData finalizeSpawn(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT dataTag) {
		spawnData = super.finalizeSpawn(sw, diff, reason, spawnData, dataTag);
		this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRItems.BLACKSMITH_HAMMER.get(), 1));
		this.npcItemsInv.setIdleItemsFromMeleeWeapons();
		this.setItemSlot(EquipmentSlotType.HEAD, new ItemStack(ExtendedItems.LEATHER_HAT.get()));
		return spawnData;
	}


	public static AttributeModifierMap.MutableAttribute createAttributes() {
		return BreeManEntity.regAttrs().add(Attributes.MAX_HEALTH, 25.0D);
	}

	@Override
	public NPCGenderProvider getGenderProvider() {
		return NPCGenderProvider.MALE;
	}


	@Override
	public List<ExtendedTrade> getBuys() {
		return itemsBoughtByTrader;
	}

	@Override
	public List<ExtendedTrade> getSells() {
		return itemsSoldByTrader;
	}

	@Override
	public void setBuys(ArrayList<ExtendedTrade> trades) {
		itemsBoughtByTrader = trades;
	}

	@Override
	public void setSells(ArrayList<ExtendedTrade> trades) {
		itemsSoldByTrader = trades;
	}
}