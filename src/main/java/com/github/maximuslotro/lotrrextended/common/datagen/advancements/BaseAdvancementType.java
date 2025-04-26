package com.github.maximuslotro.lotrrextended.common.datagen.advancements;

import java.util.List;
import java.util.function.Consumer;

import com.github.maximuslotro.lotrrextended.common.advancements.ExtendedAlignmentLevelTrigger;
import com.github.maximuslotro.lotrrextended.common.advancements.ExtendedBlockBreakTrigger;
import com.github.maximuslotro.lotrrextended.common.advancements.ExtendedTraderTradedTrigger;
import com.github.maximuslotro.lotrrextended.common.advancements.ExtendedUnitHiredTrigger;
import com.github.maximuslotro.lotrrextended.common.advancements.predicates.ExtendedAlignmentPredicate;
import com.github.maximuslotro.lotrrextended.common.datagen.DataGenValueHolder;

import lotr.common.fac.FactionPointer;
import lotr.common.init.PreRegisteredLOTRBiome;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.IRequirementsStrategy;
import net.minecraft.advancements.criterion.ChangeDimensionTrigger;
import net.minecraft.advancements.criterion.ConsumeItemTrigger;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.EntityTypePredicate;
import net.minecraft.advancements.criterion.FishingRodHookedTrigger;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.KilledTrigger;
import net.minecraft.advancements.criterion.LocationPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.advancements.criterion.MinMaxBounds.FloatBound;
import net.minecraft.advancements.criterion.PositionTrigger;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public abstract class BaseAdvancementType implements Consumer<Consumer<Advancement>>{

	public static Advancement.Builder addBiomeTriggers(Advancement.Builder builder, List<PreRegisteredLOTRBiome> biomes) {
		for(PreRegisteredLOTRBiome lotrBiomeObject : biomes) {
			addBiomeTrigger(builder, lotrBiomeObject);
		}
		return builder.requirements(IRequirementsStrategy.OR);
	}

	public static Advancement.Builder addBiomeTrigger(Advancement.Builder builder, PreRegisteredLOTRBiome biome) {
		builder.addCriterion("enter_"+biome.getName(), inBiome(biome));
		return builder;
	}
	public static Advancement.Builder addBiomeTriggers(Advancement.Builder builder, PreRegisteredLOTRBiome... biomes) {
		for(PreRegisteredLOTRBiome lotrBiomeObject : biomes) {
			addBiomeTrigger(builder, lotrBiomeObject);
		}
		return builder.requirements(IRequirementsStrategy.OR);
	}

	public static Advancement.Builder addBiomeAltTriggers(Advancement.Builder builder, List<ResourceLocation> biomes) {
		for(ResourceLocation lotrBiomeObject : biomes) {
			builder.addCriterion("enter_"+lotrBiomeObject.getPath(), inBiome(lotrBiomeObject));
		}
		return builder.requirements(IRequirementsStrategy.OR);
	}

	public static Advancement.Builder addBiomeANDTriggers(Advancement.Builder builder, List<ResourceLocation> biomes) {
		for(ResourceLocation lotrBiomeObject : biomes) {
			builder.addCriterion("enter_"+lotrBiomeObject.getPath(), inBiome(lotrBiomeObject));
		}
		return builder.requirements(IRequirementsStrategy.AND);
	}

	public static Advancement.Builder addDimensionTrigger(Advancement.Builder builder, RegistryKey<World> dim) {
		builder.addCriterion("entered_"+dim.location().getPath(), ChangeDimensionTrigger.Instance.changedDimensionTo(dim));
		return builder;
	}

	public static Advancement.Builder addArmorTrigger(Advancement.Builder builder, String name, Item helmet, Item chestplate, Item leggings, Item boots) {
		return builder.addCriterion(name+"_armor", InventoryChangeTrigger.Instance.hasItems(helmet, chestplate, leggings, boots));
	}

	public static Advancement.Builder addItemTrigger(Advancement.Builder builder, Item item) {
		return addItemTrigger(builder, item.getRegistryName().getPath(), item);
	}
	public static Advancement.Builder addItemTrigger(Advancement.Builder builder, String name, IItemProvider... items) {
		return builder.addCriterion("has_"+name, InventoryChangeTrigger.Instance.hasItems(items));
	}

	public static Advancement.Builder addBreakBlockTrigger(Advancement.Builder builder, Block block) {
		return builder.addCriterion("broke_"+block.getRegistryName().getPath(), ExtendedBlockBreakTrigger.Instance.brokenBlock(block));
	}
	public static Advancement.Builder addBreakBlockTriggers(Advancement.Builder builder, Block... blocks) {
		for(Block block : blocks) {
			builder.addCriterion("broke_"+block.getRegistryName().getPath(), ExtendedBlockBreakTrigger.Instance.brokenBlock(block));
		}
		return builder.requirements(IRequirementsStrategy.OR);
	}

	public static Advancement.Builder addAlignmentTrigger(Advancement.Builder builder, FactionPointer factionPointer, float level) {
		ExtendedAlignmentPredicate pred = new ExtendedAlignmentPredicate(FloatBound.atLeast(level), factionPointer.getNameString());
		ExtendedAlignmentLevelTrigger.Instance trigegr = ExtendedAlignmentLevelTrigger.Instance.alignmentLevel(pred);
		return builder.addCriterion("has_"+factionPointer.getName().getPath()+"_level_"+Math.round(level), trigegr);
	}

	public static Advancement.Builder addFishTrigger(Advancement.Builder builder, Item item) {
		return builder.addCriterion("fished"+item.getRegistryName().getPath(), FishingRodHookedTrigger.Instance.fishedItem(ItemPredicate.ANY, EntityPredicate.ANY, ItemPredicate.Builder.item().of(item).build()));
	}

	public static Advancement.Builder addConsumeItemTrigger(Advancement.Builder builder, Item item) {
		return addConsumeItemTrigger(builder, item.getRegistryName().getPath(), item);
	}
	public static Advancement.Builder addConsumeItemTrigger(Advancement.Builder builder, String name, IItemProvider... items) {
		for(IItemProvider item : items) {
			builder.addCriterion("consume_"+name, ConsumeItemTrigger.Instance.usedItem(item));
		}
		return builder.requirements(IRequirementsStrategy.OR);
	}

	public static Advancement.Builder addKillEntityTrigger(Advancement.Builder builder, EntityType<?> entity) {
		return builder.addCriterion("kill_"+entity.getRegistryName().getPath(), KilledTrigger.Instance.playerKilledEntity(EntityPredicate.Builder.entity().of(entity)));
	}
	public static Advancement.Builder addKillEntityTriggers(Advancement.Builder builder, EntityType<?>... entitys) {
		for(EntityType<?> entity : entitys) {
			builder.addCriterion("kill_"+entity.getRegistryName().getPath(), KilledTrigger.Instance.playerKilledEntity(EntityPredicate.Builder.entity().of(entity)));
		}
		return builder.requirements(IRequirementsStrategy.OR);
	}

	public static Advancement.Builder addTradeEntityTrigger(Advancement.Builder builder, EntityType<?> entityType) {
		builder.addCriterion("trade_"+entityType.getRegistryName().getPath()+"_buy", ExtendedTraderTradedTrigger.Instance.tradedWithALLItem(EntityTypePredicate.of(entityType), false));
		builder.addCriterion("trade_"+entityType.getRegistryName().getPath()+"_sell", ExtendedTraderTradedTrigger.Instance.tradedWithALLItem(EntityTypePredicate.of(entityType), true));
		return builder.requirements(IRequirementsStrategy.OR);
	}

	public static Advancement.Builder addTradeTrigger(Advancement.Builder builder, EntityType<?> entityType, boolean isPlayerBuyingItem, Item... items) {
		int amount=0;
		for(Item item: items){
			amount+=1;
		}
		if(amount>1) {
			for(Item item: items){
				builder.addCriterion("trade_"+entityType.getRegistryName().getPath()+(isPlayerBuyingItem ? "_sell" : "_buy")+"_"+item.getRegistryName().getPath(), ExtendedTraderTradedTrigger.Instance.tradedWithAndItem(EntityTypePredicate.of(entityType), isPlayerBuyingItem, item, MinMaxBounds.IntBound.ANY));
			}
		}
		else {
			builder.addCriterion("trade_"+entityType.getRegistryName().getPath()+(isPlayerBuyingItem ? "_sell" : "_buy"), ExtendedTraderTradedTrigger.Instance.tradedWithAndItem(EntityTypePredicate.of(entityType), isPlayerBuyingItem, items[0], MinMaxBounds.IntBound.ANY));

		}
		return builder.requirements(IRequirementsStrategy.OR);
	}

	public static Advancement.Builder addTradeNoEntityTrigger(Advancement.Builder builder, String identifier, boolean isPlayerBuyingItem, int threshold) {
		builder.addCriterion("trade_"+identifier+(isPlayerBuyingItem ? "_sell" : "_buy"), ExtendedTraderTradedTrigger.Instance.tradedWithALLItem(EntityTypePredicate.ANY, isPlayerBuyingItem, MinMaxBounds.IntBound.atLeast(threshold)));
		return builder.requirements(IRequirementsStrategy.OR);
	}

	public static Advancement.Builder addTradeNoEntityTrigger(Advancement.Builder builder, String identifier, int threshold) {
		builder.addCriterion("trade_"+identifier+"_sell", ExtendedTraderTradedTrigger.Instance.tradedWithALLItem(EntityTypePredicate.ANY, true, MinMaxBounds.IntBound.atLeast(threshold)));
		builder.addCriterion("trade_"+identifier+"_buy", ExtendedTraderTradedTrigger.Instance.tradedWithALLItem(EntityTypePredicate.ANY, false, MinMaxBounds.IntBound.atLeast(threshold)));
		return builder.requirements(IRequirementsStrategy.OR);
	}

	public static Advancement.Builder addTradeTrigger(Advancement.Builder builder, EntityType<?> entityType, boolean isPlayerBuyingItem, Item item, int threshold) {
		builder.addCriterion("trade_"+entityType.getRegistryName().getPath()+(isPlayerBuyingItem ? "_sell" : "_buy"), ExtendedTraderTradedTrigger.Instance.tradedWithAndItem(EntityTypePredicate.of(entityType), isPlayerBuyingItem, item, MinMaxBounds.IntBound.atLeast(threshold)));
		return builder.requirements(IRequirementsStrategy.OR);
	}

	public static Advancement.Builder addUnitHireTrigger(Advancement.Builder builder, EntityType<?> captainType) {
		builder.addCriterion("hire_units_from_"+captainType.getRegistryName().getPath(), ExtendedUnitHiredTrigger.Instance.tradedWithAndItem(EntityTypePredicate.of(captainType), EntityTypePredicate.ANY, MinMaxBounds.IntBound.ANY));
		return builder;
	}

	public static Advancement.Builder addUnitHireTrigger(Advancement.Builder builder, EntityType<?> captainType, EntityType<?> unitType) {
		builder.addCriterion("hire_"+unitType.getRegistryName().getPath()+"_from_"+captainType.getRegistryName().getPath(), ExtendedUnitHiredTrigger.Instance.tradedWithAndItem(EntityTypePredicate.of(captainType), EntityTypePredicate.of(unitType), MinMaxBounds.IntBound.ANY));
		return builder;
	}

	public static Advancement.Builder addUnitHireTrigger(Advancement.Builder builder, EntityType<?> captainType, EntityType<?> unitType, int coinAmount) {
		builder.addCriterion("hire_"+unitType.getRegistryName().getPath()+"_from_"+captainType.getRegistryName().getPath(), ExtendedUnitHiredTrigger.Instance.tradedWithAndItem(EntityTypePredicate.of(captainType), EntityTypePredicate.of(unitType), MinMaxBounds.IntBound.atLeast(coinAmount)));
		return builder;
	}

	Consumer<Advancement> con;
	@Override
	public void accept(Consumer<Advancement> t) {
		con= t;
		generateAdvancements();
	}

	public abstract void generateAdvancements();

	public abstract String getFolder();

	public static Advancement.Builder make(){
		return Advancement.Builder.advancement();
	}

	public BaseData data(Item icon, String name, boolean mainCat, FrameType frame, boolean showToast, boolean announceChat, boolean ishidden){
		return new BaseData((new ItemStack(icon)), name, mainCat, frame, showToast, announceChat, ishidden);
	}

	public BaseData data(ItemStack icon, String name, boolean mainCat, FrameType frame, boolean showToast, boolean announceChat, boolean ishidden){
		return new BaseData(icon, name, mainCat, frame, showToast, announceChat, ishidden);
	}

	public Advancement build(boolean isLegacy, BaseData data, Advancement.Builder builder){
		if(data.getFrame()==FrameType.CHALLENGE) {
			data.setTitleStype(TextFormatting.DARK_PURPLE);
			data.setDescriptionStype(TextFormatting.LIGHT_PURPLE);
		}else if(data.getFrame()==FrameType.GOAL) {
			data.setTitleStype(TextFormatting.GOLD);
			data.setDescriptionStype(TextFormatting.AQUA);
		}else {
			data.setDescriptionStype(TextFormatting.GREEN);
		}
		builder.display(data.makeInfo());
		if(isLegacy) {
			ExtendedAdvancementProvider.advancementsLegacyNum +=1;
		}else {
			ExtendedAdvancementProvider.advancementsExtendedNum +=1;
		}
		if(data.isHidden()) {
			ExtendedAdvancementProvider.secrets +=1;
		}
		return builder.save(con, "lotrextended:"+getFolder()+"/"+data.name);
	}

	public static RegistryKey<Biome> biome(PreRegisteredLOTRBiome biome){
		return RegistryKey.create(Registry.BIOME_REGISTRY, biome.getRegistryName());
	}

	public static PositionTrigger.Instance inBiome(PreRegisteredLOTRBiome biome){
		return PositionTrigger.Instance.located(LocationPredicate.inBiome(biome(biome)));
	}

	public static PositionTrigger.Instance inBiome(ResourceLocation biome){
		return PositionTrigger.Instance.located(LocationPredicate.inBiome(RegistryKey.create(Registry.BIOME_REGISTRY, biome)));
	}

	public class BaseData {
		private final String name;
		private String titleMain;
		private String descriptionMain;
		private final ItemStack icon;
		private final boolean mainCat;
		private final FrameType frame;
		private final boolean showToast;
		private final boolean announceChat;
		private final boolean hidden;
		private float x = 0.34F;
		private float y = 0.34F;
		private TextFormatting titleFormatting = null;
		private TextFormatting descriptionFormatting = null;

		public BaseData(ItemStack icon, String name, boolean mainCat, FrameType frame, boolean showToast, boolean announceChat, boolean ishidden) {
			this.icon = icon;
			this.name = name;
			this.mainCat = mainCat;
			this.frame = frame;
			this.showToast = showToast;
			this.announceChat = announceChat;
			this.hidden = ishidden;
		}

		public DisplayInfo makeInfo() {
			String titleString = "advancements.lotrextended."+getFolder()+"."+name+".title";
			TranslationTextComponent titleI = new TranslationTextComponent(titleString);
			if(titleFormatting!=null) {
				titleI.withStyle(titleFormatting);
			}
			String descriptionString = "advancements.lotrextended."+getFolder()+"."+name+".description";
			TranslationTextComponent descriptionI = new TranslationTextComponent("advancements.lotrextended."+getFolder()+"."+name+".description");
			if(descriptionFormatting!=null) {
				descriptionI.withStyle(descriptionFormatting);
			}
			ResourceLocation backgroundI = null;
			if(mainCat) {
				backgroundI = new ResourceLocation("lotrextended", "textures/gui/advancements/backgrounds/"+getFolder()+".png");
			}
			DisplayInfo info = new DisplayInfo(icon, titleI, descriptionI, backgroundI, frame, showToast, announceChat, hidden);
			if(x!=0.34F && y != 0.34F) {
				info.setLocation(x, y);
			}
			if(titleMain!=null) {
				DataGenValueHolder.advancementHolder.put(titleString, titleMain);
			}
			if(descriptionMain!=null) {
				DataGenValueHolder.advancementHolder.put(descriptionString, descriptionMain);
			}
			return info;
		}

		public BaseData setLocation(float p_192292_1_, float p_192292_2_) {
			this.x = p_192292_1_;
			this.y = p_192292_2_;
			return this;
		}

		public BaseData setTitle(String title) {
			this.titleMain = title;
			return this;
		}

		public BaseData setTitleStype(TextFormatting style) {
			this.titleFormatting = style;
			return this;
		}

		public BaseData setDescriptionStype(TextFormatting style) {
			this.descriptionFormatting = style;
			return this;
		}

		public BaseData setDescription(String description) {
			this.descriptionMain = description;
			return this;
		}

		public FrameType getFrame() {
			return frame;
		}

		public boolean isHidden() {
			return hidden;
		}
	}
}
