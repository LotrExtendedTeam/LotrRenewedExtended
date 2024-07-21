package com.github.maximuslotro.lotrrextended.common.datagen.advancements.regions;

import com.github.maximuslotro.lotrrextended.common.datagen.advancements.BaseAdvancementType;

import lotr.common.fac.FactionPointers;
import lotr.common.init.LOTRBiomes;
import lotr.common.init.LOTREntities;
import lotr.common.init.LOTRItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.item.Items;


public class RohanAdvancementsProvider extends BaseAdvancementType{

	@Override
	public void generateAdvancements() {
		Advancement horse_lords = build(true, data(LOTRItems.ROHAN_BRICK.get(), "horse_lords", true, FrameType.TASK, true, true, false)
				.setTitle("Home of the Horse-lords").setDescription("Enter the land of Rohan."), 
				addBiomeTriggers(make(), LOTRBiomes.ROHAN, LOTRBiomes.WOLD));

		Advancement vale_of_the_wizard = build(true, data(LOTRItems.URUK_BERSERKER_CLEAVER.get(), "vale_of_the_wizard", false, FrameType.TASK, true, true, false)
				.setTitle("Vale of the Wizard").setDescription("Enter the valley of Nan Curunír."), 
				addBiomeTrigger(make(), LOTRBiomes.NAN_CURUNIR).parent(horse_lords));

		Advancement entwash = build(true, data(Items.DIRT, "entwash", false, FrameType.TASK, true, true, false)
		.setTitle("Last Marsh of the Ents").setDescription("Enter the Mouths of Entwash."), 
		addBiomeTrigger(make(), LOTRBiomes.MOUTHS_OF_ENTWASH).parent(horse_lords));

		Advancement rohan_slayer = build(true, data(Items.BONE, "rohan_slayer", false, FrameType.TASK, true, true, false)
				.setTitle("Rohirrim Slayer").setDescription("Kill one of the Rohirrim."), 
				addKillEntityTriggers(make(), LOTREntities.ROHAN_MAN.get(), LOTREntities.ROHIRRIM_BOWMAN.get(), 
						LOTREntities.ROHIRRIM_WARRIOR.get()).parent(horse_lords));

		Advancement rohan_armor = build(true, data(LOTRItems.ROHAN_CHESTPLATE.get(), "rohan_armor", false, FrameType.TASK, true, true, false)
				.setTitle("Rider of Rohan").setDescription("Equip a full suit of Rohirric armour."), 
				addArmorTrigger(make(), "rohan", LOTRItems.ROHAN_HELMET.get(), LOTRItems.ROHAN_CHESTPLATE.get(), 
						LOTRItems.ROHAN_LEGGINGS.get(), LOTRItems.ROHAN_BOOTS.get()).parent(horse_lords));

		Advancement rohan_marshal_armor = build(true, data(LOTRItems.ROHAN_MARSHAL_CHESTPLATE.get(), "rohan_marshal_armor", false, FrameType.TASK, true, true, false)
				.setTitle("Marshal of Rohan").setDescription("Equip a full suit of Rohirric marshal armour."), 
				addArmorTrigger(make(), "rohan_marshal", LOTRItems.ROHAN_MARSHAL_HELMET.get(), LOTRItems.ROHAN_MARSHAL_CHESTPLATE.get(), 
						LOTRItems.ROHAN_MARSHAL_LEGGINGS.get(), LOTRItems.ROHAN_MARSHAL_BOOTS.get()).parent(rohan_armor));

		Advancement rohan_10 = build(true, data(LOTRItems.GOLD_RING.get(), "rohan_10", false, FrameType.TASK, true, true, false)
				.setTitle("Rohan Guest").setDescription("Reach +10.0 alignment with the Rohirrim."), 
				addAlignmentTrigger(make(), FactionPointers.ROHAN, 10).parent(horse_lords));
		Advancement rohan_50 = build(true, data(LOTRItems.GOLD_RING.get(), "rohan_50", false, FrameType.TASK, true, true, false)
				.setTitle("Eorling Footman").setDescription("Reach +50.0 alignment with the Rohirrim."), 
				addAlignmentTrigger(make(), FactionPointers.ROHAN, 50).parent(rohan_10));
		Advancement rohan_100 = build(true, data(LOTRItems.GOLD_RING.get(), "rohan_100", false, FrameType.TASK, true, true, false)
				.setTitle("Eorling-at-Arms").setDescription("Reach +100.0 alignment with the Rohirrim."), 
				addAlignmentTrigger(make(), FactionPointers.ROHAN, 100).parent(rohan_50));
		Advancement rohan_250 = build(true, data(LOTRItems.GOLD_RING.get(), "rohan_250", false, FrameType.TASK, true, true, false)
				.setTitle("Rider of Rohan").setDescription("Reach +250.0 alignment with the Rohirrim."), 
				addAlignmentTrigger(make(), FactionPointers.ROHAN, 250).parent(rohan_100));
		Advancement rohan_500 = build(true, data(LOTRItems.GOLD_RING.get(), "rohan_500", false, FrameType.TASK, true, true, false)
				.setTitle("Esquire of Rohan").setDescription("Reach +500.0 alignment with the Rohirrim."), 
				addAlignmentTrigger(make(), FactionPointers.ROHAN, 500).parent(rohan_250));
		Advancement rohan_1000 = build(true, data(LOTRItems.GOLD_RING.get(), "rohan_1000", false, FrameType.GOAL, true, true, false)
				.setTitle("Eorling Captain").setDescription("Reach +1000.0 alignment with the Rohirrim."), 
				addAlignmentTrigger(make(), FactionPointers.ROHAN, 1000).parent(rohan_500));
		Advancement rohan_2000 = build(true, data(LOTRItems.GOLD_RING.get(), "rohan_2000", false, FrameType.CHALLENGE, true, true, false)
				.setTitle("Eorling Marshal").setDescription("Reach +2000.0 alignment with the Rohirrim."), 
				addAlignmentTrigger(make(), FactionPointers.ROHAN, 2000).parent(rohan_1000));
	
		Advancement isengard_10 = build(true, data(LOTRItems.GOLD_RING.get(), "isengard_10", false, FrameType.TASK, true, true, false)
				.setTitle("Rohan Guest").setDescription("Reach +10.0 alignment with Isengard."), 
				addAlignmentTrigger(make(), FactionPointers.ISENGARD, 10).parent(horse_lords));
		Advancement isengard_50 = build(true, data(LOTRItems.GOLD_RING.get(), "isengard_50", false, FrameType.TASK, true, true, false)
				.setTitle("Eorling Footman").setDescription("Reach +50.0 alignment with Isengard."), 
				addAlignmentTrigger(make(), FactionPointers.ISENGARD, 50).parent(isengard_10));
		Advancement isengard_100 = build(true, data(LOTRItems.GOLD_RING.get(), "isengard_100", false, FrameType.TASK, true, true, false)
				.setTitle("Eorling-at-Arms").setDescription("Reach +100.0 alignment with Isengard."), 
				addAlignmentTrigger(make(), FactionPointers.ISENGARD, 100).parent(isengard_50));
		Advancement isengard_200 = build(true, data(LOTRItems.GOLD_RING.get(), "isengard_200", false, FrameType.TASK, true, true, false)
				.setTitle("Rider of Rohan").setDescription("Reach +200.0 alignment with Isengard."), 
				addAlignmentTrigger(make(), FactionPointers.ISENGARD, 200).parent(isengard_100));
		Advancement isengard_500 = build(true, data(LOTRItems.GOLD_RING.get(), "isengard_500", false, FrameType.TASK, true, true, false)
				.setTitle("Esquire of Rohan").setDescription("Reach +500.0 alignment with Isengard."), 
				addAlignmentTrigger(make(), FactionPointers.ISENGARD, 500).parent(isengard_200));
		Advancement isengard_1000 = build(true, data(LOTRItems.GOLD_RING.get(), "isengard_1000", false, FrameType.TASK, true, true, false)
				.setTitle("Eorling Captain").setDescription("Reach +1000.0 alignment with Isengard."), 
				addAlignmentTrigger(make(), FactionPointers.ISENGARD, 1000).parent(isengard_500));
		Advancement isengard_2000 = build(true, data(LOTRItems.GOLD_RING.get(), "isengard_2000", false, FrameType.GOAL, true, true, false)
				.setTitle("Eorling Marshal").setDescription("Reach +2000.0 alignment with Isengard."), 
				addAlignmentTrigger(make(), FactionPointers.ISENGARD, 2000).parent(isengard_1000));
		Advancement isengard_3000 = build(true, data(LOTRItems.GOLD_RING.get(), "isengard_3000", false, FrameType.CHALLENGE, true, true, false)
				.setTitle("Eorling Marshal").setDescription("Reach +3000.0 alignment with Isengard."), 
				addAlignmentTrigger(make(), FactionPointers.ISENGARD, 3000).parent(isengard_2000));

		Advancement uruk_slayer = build(true, data(LOTRItems.ORC_BONE.get(), "uruk_slayer", false, FrameType.TASK, true, true, false)
				.setTitle("Uruk Slayer").setDescription("Kill an Uruk."), 
				addKillEntityTriggers(make(), LOTREntities.URUK.get(), LOTREntities.URUK_ARCHER.get()).parent(vale_of_the_wizard));

		Advancement snaga_slayer = build(true, data(LOTRItems.ORC_BONE.get(), "snaga_slayer", false, FrameType.TASK, true, true, false)
				.setTitle("Snaga Slayer").setDescription("Kill an Isengard Snaga."), 
				addKillEntityTriggers(make(), LOTREntities.ISENGARD_SNAGA.get(), LOTREntities.ISENGARD_SNAGA_ARCHER.get())
				.parent(vale_of_the_wizard));

		Advancement uruk_armor = build(true, data(LOTRItems.URUK_CHESTPLATE.get(), "uruk_armor", false, FrameType.TASK, true, true, false)
				.setTitle("The Feared").setDescription("Equip a full suit of Uruk armour."), 
				addArmorTrigger(make(), "uruk", LOTRItems.URUK_HELMET.get(), LOTRItems.URUK_CHESTPLATE.get(), 
						LOTRItems.URUK_LEGGINGS.get(), LOTRItems.URUK_BOOTS.get()).parent(vale_of_the_wizard));
	}

	@Override
	public String getFolder() {
		return "rohan";
	}

}
