package com.github.maximuslotro.lotrrextended.common.spawners;

import java.util.Collections;
import java.util.Set;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.google.common.collect.Sets;

import net.minecraft.util.ResourceLocation;

/**
 * Stores IDs for built in entity spawners.
 */
public class ExtendedSpawners {
   private static final Set<ResourceLocation> LOCATIONS = Sets.newHashSet();
   private static final Set<ResourceLocation> IMMUTABLE_LOCATIONS = Collections.unmodifiableSet(LOCATIONS);
   public static final ResourceLocation EMPTY = register("empty");
   // ANIMALS
   public static final ResourceLocation DUCK_SINGLE  = register("duck_single_spawner");
   // HOBBITS
   public static final ResourceLocation HOBBIT_PICNIC_TABLE = register("hobbit_picnic_table_spawner");
   public static final ResourceLocation HOBBIT_PICNIC_BLANKET = register("hobbit_picnic_blanket_spawner");
   public static final ResourceLocation HOBBIT_BARTENDER_SMALL = register("hobbit_bartender_small_spawner");
   public static final ResourceLocation HOBBIT_BARTENDER_LARGE = register("hobbit_bartender_large_spawner");
   public static final ResourceLocation HOBBIT_TAVERN_SMALL = register("hobbit_tavern_small_spawner");
   public static final ResourceLocation HOBBIT_TAVERN_LARGE = register("hobbit_tavern_large_spawner");
   public static final ResourceLocation HOBBIT_TAVERN_LARGE_SHERIFF = register("hobbit_tavern_large_sheriff_spawner");
   public static final ResourceLocation HOBBIT_BARN_FARMER = register("hobbit_barn_farmer_spawner");
   public static final ResourceLocation HOBBIT_BARN_FARMHAND = register("hobbit_barn_farmhand_spawner");
   public static final ResourceLocation HOBBIT_BARN_ANIMALS = register("hobbit_barn_animals_spawner");
   public static final ResourceLocation HOBBIT_WINDMILL = register("hobbit_windmill_spawner");
   public static final ResourceLocation HOBBIT_FAMILY_HOUSE = register("hobbit_family_house_spawner");
   public static final ResourceLocation HOBBIT_FAMILY_HOLE_SMALL = register("hobbit_family_hole_small_spawner");
   public static final ResourceLocation HOBBIT_FAMILY_HOLE_LARGE = register("hobbit_family_hole_large_spawner");
   // RANGERS
   public static final ResourceLocation DUNEDAIN_RANGER_SINGLE_CHANCE = register("dunedain_ranger_single_chance_spawner");
   public static final ResourceLocation DUNEDAIN_RANGER_WATCHTOWER = register("dunedain_watchtower_ranger_spawner");
   public static final ResourceLocation DUNEDAIN_RANGER_HOUSE_CAMP = register("dunedain_house_ranger_spawner");
   public static final ResourceLocation DUNEDAIN_RANGER_ENCAMPMENT = register("dunedain_encampment_ranger_spawner");
   public static final ResourceLocation DUNEDAIN_RANGER_ENCAMPMENT_CAPTAIN = register("dunedain_encampment_captain_spawner");
   public static final ResourceLocation DUNEDAIN_RANGER_CAMP = register("dunedain_camp_ranger_spawner");
   public static final ResourceLocation DUNEDAIN_RANGER_CAMP_CAPTAIN = register("dunedain_camp_captain_spawner");
   public static final ResourceLocation DUNEDAIN_RANGER_CAMP_ANIMALS = register("dunedain_camp_animal_spawner");
   // GUNDABAD ORCS
   public static final ResourceLocation GUNDABAD_CAMP_ORC = register("gundabad_camp_orc_spawner");
   public static final ResourceLocation GUNDABAD_CAMP_CAPTAIN = register("gundabad_camp_captain_spawner");
   public static final ResourceLocation GUNDABAD_CAMP_PRISONERS = register("gundabad_camp_prisoners_spawner");
   public static final ResourceLocation GUNDABAD_ENCAMPMENT_ORC = register("gundabad_encampment_orc_spawner");
   public static final ResourceLocation GUNDABAD_ENCAMPMENT_TRADER = register("gundabad_encampment_trader_spawner");
   public static final ResourceLocation GUNDABAD_ENCAMPMENT_CAPTAIN = register("gundabad_encampment_captain_spawner");
   public static final ResourceLocation GUNDABAD_ENCAMPMENT_PRISONERS = register("gundabad_encampment_prisoners_spawner");


   private static ResourceLocation register(String pId) {
      return register(new ResourceLocation(LotrExtendedMod.MODID, pId));
   }

   private static ResourceLocation register(ResourceLocation pId) {
      if (LOCATIONS.add(pId)) {
         return pId;
      } else {
         throw new IllegalArgumentException(pId + " is already a registered built-in extended structure entity spawner");
      }
   }

   public static Set<ResourceLocation> all() {
      return IMMUTABLE_LOCATIONS;
   }
}
