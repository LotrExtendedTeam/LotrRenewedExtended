package com.github.maximuslotro.lotrrextended.common.datagen.advancements;

import java.util.List;
import java.util.function.Consumer;

import com.github.maximuslotro.lotrrextended.common.advancements.AlignmentLevelTrigger;
import com.github.maximuslotro.lotrrextended.common.advancements.TraderTradedTrigger;
import com.github.maximuslotro.lotrrextended.common.advancements.predicates.AlignmentPredicate;
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
import net.minecraft.advancements.criterion.FishingRodHookedTrigger;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.KilledTrigger;
import net.minecraft.advancements.criterion.LocationPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds.FloatBound;
import net.minecraft.advancements.criterion.PositionTrigger;
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

	public static Advancement.Builder addBiomeAltTriggers(Advancement.Builder builder, List<ResourceLocation> biomes) {
		for(ResourceLocation lotrBiomeObject : biomes) {
			builder.addCriterion("enter_"+lotrBiomeObject.getPath(), inBiome(lotrBiomeObject));
		}
		return builder.requirements(IRequirementsStrategy.OR);
	}

	public static Advancement.Builder addDimensionTrigger(Advancement.Builder builder, RegistryKey<World> dim) {
		builder.addCriterion("entered_"+dim.location().getPath(), ChangeDimensionTrigger.Instance.changedDimensionTo(dim));
		return builder;
	}

	public static Advancement.Builder addArmorTrigger(Advancement.Builder builder, String name, Item helmet, Item chestplate, Item leggings, Item boots) {
		return builder.addCriterion(name+"_armor", InventoryChangeTrigger.Instance.hasItems(helmet, chestplate, leggings, boots));
	}

	public static Advancement.Builder addItemTrigger(Advancement.Builder builder, Item item) {
		return addItemTrigger(builder, "has_"+item.getRegistryName().getPath(), item);
	}
	public static Advancement.Builder addItemTrigger(Advancement.Builder builder, String name, IItemProvider... items) {
		return builder.addCriterion("has_"+name, InventoryChangeTrigger.Instance.hasItems(items));
	}

	public static Advancement.Builder addAlignmentTrigger(Advancement.Builder builder, FactionPointer factionPointer, float level) {
		AlignmentPredicate pred = new AlignmentPredicate(FloatBound.atLeast(level), factionPointer.getNameString());
		AlignmentLevelTrigger.Instance trigegr = AlignmentLevelTrigger.Instance.alignmentLevel(pred);
		return builder.addCriterion("has_"+factionPointer.getName().getPath()+"_level_"+Math.round(level), trigegr);
	}

	public static Advancement.Builder addFishTrigger(Advancement.Builder builder, Item item) {
		return builder.addCriterion("fished"+item.getRegistryName().getPath(), FishingRodHookedTrigger.Instance.fishedItem(ItemPredicate.ANY, EntityPredicate.ANY, ItemPredicate.Builder.item().of(item).build()));
	}

	public static Advancement.Builder addConsumeItemTrigger(Advancement.Builder builder, Item item) {
		return addItemTrigger(builder, "consume_"+item.getRegistryName().getPath(), item);
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

	public static Advancement.Builder addTradeEntityTrigger(Advancement.Builder builder, EntityType<?> entityType) {
		builder.addCriterion("trade_"+entityType.getRegistryName().getPath()+"_buy", TraderTradedTrigger.Instance.tradedWithALLItem(entityType, false));
		builder.addCriterion("trade_"+entityType.getRegistryName().getPath()+"_sell", TraderTradedTrigger.Instance.tradedWithALLItem(entityType, true));
		return builder.requirements(IRequirementsStrategy.OR);
	}

	public static Advancement.Builder addTradeTrigger(Advancement.Builder builder, EntityType<?> entityType, boolean isPlayerBuyingItem, Item item) {
		builder.addCriterion("trade_"+entityType.getRegistryName().getPath()+(isPlayerBuyingItem ? "_sell" : "_buy"), TraderTradedTrigger.Instance.tradedWithAndItem(entityType, isPlayerBuyingItem, item));
		return builder.requirements(IRequirementsStrategy.OR);
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
		}else if(data.getFrame()==FrameType.GOAL) {
			data.setTitleStype(TextFormatting.GOLD);
		}else {
			data.setTitleStype(TextFormatting.GREEN);
		}
		builder.display(data.makeInfo());
		if(isLegacy) {
			ExtendedAdvancementProvider.advancementsLegacyNum +=1;
		}else {
			ExtendedAdvancementProvider.advancementsExtendedNum +=1;
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
		private TextFormatting formatting = null;
		
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
			if(formatting!=null) {
				titleI.withStyle(formatting);
			}
			String descriptionString = "advancements.lotrextended."+getFolder()+"."+name+".description";
			TranslationTextComponent descriptionI = new TranslationTextComponent("advancements.lotrextended."+getFolder()+"."+name+".description");
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
			this.formatting = style;
			return this;
		}

		public BaseData setDescription(String description) {
			this.descriptionMain = description;
			return this;
		}

		public FrameType getFrame() {
			return frame;
		}
	}
}
