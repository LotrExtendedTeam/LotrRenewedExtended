package com.github.maximuslotro.lotrrextended.common.datagen.advancements.regions;

import com.github.maximuslotro.lotrrextended.common.datagen.advancements.ExtendedBaseAdvancementType;

import lotr.common.fac.FactionPointers;
import lotr.common.init.ExtendedBiomes;
import lotr.common.init.ExtendedEntities;
import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRBiomes;
import lotr.common.init.LOTRBlocks;
import lotr.common.init.LOTREntities;
import lotr.common.init.LOTRItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.item.Items;


public class AdvancementsProviderRohan extends ExtendedBaseAdvancementType{

	@Override
	public void generateAdvancements() {
		Advancement horse_lords = build(true, dataMain(LOTRItems.ROHAN_SPEAR.get(), "horse_lords", FrameType.TASK)
				.setTitle("Home of the Horse-lords").setDescription("Enter the land of Rohan."),
				addBiomeTriggers(make(), LOTRBiomes.ROHAN, LOTRBiomes.WOLD));

		Advancement vale_of_the_wizard = build(true, data(LOTRItems.URUK_BERSERKER_CLEAVER.get(), "vale_of_the_wizard", FrameType.TASK)
				.setTitle("Vale of the Wizard").setDescription("Enter the valley of Nan Curunír."),
				addBiomeTrigger(make(), LOTRBiomes.NAN_CURUNIR).parent(horse_lords));
	
		Advancement uruk_highlands = build(true, data(LOTRItems.URUK_BERSERKER_CLEAVER.get(), "uruk_highlands", FrameType.TASK)
			.setTitle("Place of Devastation").setDescription("Enter the Uruk Highlands."),
			addBiomeTrigger(make(), ExtendedBiomes.URUK_HIGHLANDS).parent(vale_of_the_wizard));

		Advancement entwash = build(true, data(Items.FERN, "entwash", FrameType.TASK)
		.setTitle("Last Marsh of the Ents").setDescription("Enter the Mouths of Entwash."),
		addBiomeTrigger(make(), LOTRBiomes.MOUTHS_OF_ENTWASH).parent(horse_lords));

		Advancement rohan_slayer = build(true, data(LOTRItems.ROHAN_SWORD.get(), "rohan_slayer", FrameType.TASK)
				.setTitle("Rohirrim Slayer").setDescription("Kill one of the Rohirrim."),
				addKillEntityTriggers(make(), LOTREntities.ROHAN_MAN.get(), LOTREntities.ROHIRRIM_BOWMAN.get(),
						LOTREntities.ROHIRRIM_WARRIOR.get()).parent(horse_lords));

		Advancement rohan_armor = build(true, data(LOTRItems.ROHAN_CHESTPLATE.get(), "rohan_armor", FrameType.TASK)
				.setTitle("Rider of Rohan").setDescription("Equip a full suit of Rohirric armor."),
				addArmorTrigger(make(), "rohan", LOTRItems.ROHAN_HELMET.get(), LOTRItems.ROHAN_CHESTPLATE.get(),
						LOTRItems.ROHAN_LEGGINGS.get(), LOTRItems.ROHAN_BOOTS.get()).parent(horse_lords));

		Advancement rohan_marshal_armor = build(true, data(LOTRItems.ROHAN_MARSHAL_CHESTPLATE.get(), "rohan_marshal_armor", FrameType.TASK)
				.setTitle("Marshal of Rohan").setDescription("Equip a full suit of Rohirric marshal armor."),
				addArmorTrigger(make(), "rohan_marshal", LOTRItems.ROHAN_MARSHAL_HELMET.get(), LOTRItems.ROHAN_MARSHAL_CHESTPLATE.get(),
						LOTRItems.ROHAN_MARSHAL_LEGGINGS.get(), LOTRItems.ROHAN_MARSHAL_BOOTS.get()).parent(rohan_armor));

		Advancement rohan_10 = build(true, data(LOTRItems.GOLD_RING.get(), "rohan_10", FrameType.TASK)
				.setTitle("Rohan Guest").setDescription("Reach +10.0 alignment with the Rohirrim."),
				addAlignmentTrigger(make(), FactionPointers.ROHAN, 10).parent(horse_lords));
		Advancement rohan_50 = build(true, data(LOTRItems.GOLD_RING.get(), "rohan_50", FrameType.TASK)
				.setTitle("Eorling Footman").setDescription("Reach +50.0 alignment with the Rohirrim."),
				addAlignmentTrigger(make(), FactionPointers.ROHAN, 50).parent(rohan_10));
		Advancement rohan_100 = build(true, data(LOTRItems.GOLD_RING.get(), "rohan_100", FrameType.TASK)
				.setTitle("Eorling-at-Arms").setDescription("Reach +100.0 alignment with the Rohirrim."),
				addAlignmentTrigger(make(), FactionPointers.ROHAN, 100).parent(rohan_50));
		Advancement rohan_250 = build(true, data(LOTRItems.GOLD_RING.get(), "rohan_250", FrameType.TASK)
				.setTitle("Esquire of Rohan").setDescription("Reach +250.0 alignment with the Rohirrim."),
				addAlignmentTrigger(make(), FactionPointers.ROHAN, 250).parent(rohan_100));
		Advancement rohan_500 = build(true, data(LOTRItems.GOLD_RING.get(), "rohan_500", FrameType.TASK)
				.setTitle("Rider of Rohan").setDescription("Reach +500.0 alignment with the Rohirrim."),
				addAlignmentTrigger(make(), FactionPointers.ROHAN, 500).parent(rohan_250));
		Advancement rohan_1000 = build(true, data(LOTRItems.GOLD_RING.get(), "rohan_1000", FrameType.GOAL)
				.setTitle("Eorling Captain").setDescription("Reach +1000.0 alignment with the Rohirrim."),
				addAlignmentTrigger(make(), FactionPointers.ROHAN, 1000).parent(rohan_500));
		Advancement rohan_2000 = build(true, data(LOTRItems.GOLD_RING.get(), "rohan_2000", FrameType.CHALLENGE)
				.setTitle("Eorling Marshal").setDescription("Reach +2000.0 alignment with the Rohirrim."),
				addAlignmentTrigger(make(), FactionPointers.ROHAN, 2000).parent(rohan_1000));

		Advancement isengard_10 = build(true, data(LOTRItems.GOLD_RING.get(), "isengard_10", FrameType.TASK)
				.setTitle("Isengard Thrall").setDescription("Reach +10.0 alignment with Isengard."),
				addAlignmentTrigger(make(), FactionPointers.ISENGARD, 10).parent(horse_lords));
		Advancement isengard_50 = build(true, data(LOTRItems.GOLD_RING.get(), "isengard_50", FrameType.TASK)
				.setTitle("Isengard Snaga").setDescription("Reach +50.0 alignment with Isengard."),
				addAlignmentTrigger(make(), FactionPointers.ISENGARD, 50).parent(isengard_10));
		Advancement isengard_100 = build(true, data(LOTRItems.GOLD_RING.get(), "isengard_100", FrameType.TASK)
				.setTitle("Isengard Soldier").setDescription("Reach +100.0 alignment with Isengard."),
				addAlignmentTrigger(make(), FactionPointers.ISENGARD, 100).parent(isengard_50));
		Advancement isengard_200 = build(true, data(LOTRItems.GOLD_RING.get(), "isengard_200", FrameType.TASK)
				.setTitle("Isengard Tree-feller").setDescription("Reach +200.0 alignment with Isengard."),
				addAlignmentTrigger(make(), FactionPointers.ISENGARD, 200).parent(isengard_100));
		Advancement isengard_500 = build(true, data(LOTRItems.GOLD_RING.get(), "isengard_500", FrameType.TASK)
				.setTitle("Isengard Berserker").setDescription("Reach +500.0 alignment with Isengard."),
				addAlignmentTrigger(make(), FactionPointers.ISENGARD, 500).parent(isengard_200));
		Advancement isengard_1000 = build(true, data(LOTRItems.GOLD_RING.get(), "isengard_1000", FrameType.TASK)
				.setTitle("Isengard Corporal").setDescription("Reach +1000.0 alignment with Isengard."),
				addAlignmentTrigger(make(), FactionPointers.ISENGARD, 1000).parent(isengard_500));
		Advancement isengard_2000 = build(true, data(LOTRItems.GOLD_RING.get(), "isengard_2000", FrameType.GOAL)
				.setTitle("Hand of Isengard").setDescription("Reach +2000.0 alignment with Isengard."),
				addAlignmentTrigger(make(), FactionPointers.ISENGARD, 2000).parent(isengard_1000));
		Advancement isengard_3000 = build(true, data(LOTRItems.GOLD_RING.get(), "isengard_3000", FrameType.CHALLENGE)
				.setTitle("Captain of the Hand").setDescription("Reach +3000.0 alignment with Isengard."),
				addAlignmentTrigger(make(), FactionPointers.ISENGARD, 3000).parent(isengard_2000));

		Advancement snaga_slayer = build(true, data(LOTRItems.ORC_BONE.get(), "snaga_slayer", FrameType.TASK)
				.setTitle("Snaga Slayer").setDescription("Kill an Isengard Snaga."),
				addKillEntityTriggers(make(), LOTREntities.ISENGARD_SNAGA.get(), LOTREntities.ISENGARD_SNAGA_ARCHER.get())
				.parent(vale_of_the_wizard));

		Advancement uruk_slayer = build(true, data(LOTRItems.ORC_BONE.get(), "uruk_slayer", FrameType.TASK)
				.setTitle("Uruk Slayer").setDescription("Kill an Uruk."),
				addKillEntityTriggers(make(), LOTREntities.URUK.get(), LOTREntities.URUK_ARCHER.get(),
						ExtendedEntities.URUK_CAPTAIN.get(), ExtendedEntities.URUK_SMITH.get(),
						ExtendedEntities.URUK_SUPPLIER.get(), ExtendedEntities.URUK_BERSERKER.get()).parent(snaga_slayer));

		Advancement pillaging_time = build(true, data(LOTRItems.URUK_CLEAVER.get(), "pillaging_time", FrameType.TASK)
				.setTitle("Pillaging Time").setDescription("Hire a unit from an Uruk Captain."),
				addUnitHireTrigger(make(), ExtendedEntities.URUK_CAPTAIN.get()).parent(vale_of_the_wizard));

		Advancement shady_deal = build(true, data(ExtendedItems.SILVER_COIN_ONE.get(), "shady_deal", FrameType.TASK)
				.setTitle("Shady Deal").setDescription("Trade with an Uruk Supplier."),
				addTradeEntityTriggers(make(), ExtendedEntities.URUK_SUPPLIER.get()).parent(vale_of_the_wizard));

		Advancement war_forge_deal = build(false, data(ExtendedItems.SILVER_COIN_ONE.get(), "war_forge_deal", FrameType.TASK)
				.setTitle("War-Forge Deal").setDescription("Trade with an Uruk Blacksmith."),
				addTradeEntityTriggers(make(), ExtendedEntities.URUK_SMITH.get()).parent(vale_of_the_wizard));

		Advancement uruk_armor = build(true, data(LOTRItems.URUK_CHESTPLATE.get(), "uruk_armor", FrameType.TASK)
				.setTitle("The Feared").setDescription("Equip a full suit of Uruk armor."),
				addArmorTrigger(make(), "uruk", LOTRItems.URUK_HELMET.get(), LOTRItems.URUK_CHESTPLATE.get(),
						LOTRItems.URUK_LEGGINGS.get(), LOTRItems.URUK_BOOTS.get()).parent(vale_of_the_wizard));

		Advancement rohan_crafter = build(true, data(LOTRItems.ROHAN_CRAFTING_TABLE.get(), "rohan_crafter", FrameType.TASK)
			.setTitle("Rohirric Crafter").setDescription("Craft an item on the Rohirric Crafting Table."),
			addCraftingTrigger(make(), LOTRBlocks.ROHAN_CRAFTING_TABLE.get()).parent(horse_lords));

		Advancement uruk_crafter = build(true, data(LOTRItems.URUK_CRAFTING_TABLE.get(), "uruk_crafter", FrameType.TASK)
			.setTitle("Uruk Crafter").setDescription("Craft an item on the Uruk Crafting Table."),
			addCraftingTrigger(make(), LOTRBlocks.URUK_CRAFTING_TABLE.get()).parent(vale_of_the_wizard));

		Advancement uruk_smith = build(true, data(LOTRItems.URUK_STEEL_INGOT.get(), "uruk_smith", FrameType.TASK)
			.setTitle("Uruk Smith").setDescription("Smelt an ingot of Uruk Steel in the Orc Forge."),
			addCraftingTrigger(make(), LOTRBlocks.ORC_FORGE.get(), LOTRItems.URUK_STEEL_INGOT.get()).parent(vale_of_the_wizard));
	}

	@Override
	public String getFolder() {
		return "rohan";
	}

	@Override
	public int getLegacyCount() {
		return 42;
	}

}
