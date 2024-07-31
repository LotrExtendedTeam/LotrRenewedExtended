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
   // HOBBITS
   public static final ResourceLocation HOBBIT_BARTENDER_SMALL = register("hobbit_bartender_small_spawner");
   public static final ResourceLocation HOBBIT_BARTENDER_LARGE = register("hobbit_bartender_large_spawner");
   public static final ResourceLocation HOBBIT_FARMER = register("hobbit_farmer_spawner");
   public static final ResourceLocation HOBBIT_WINDMILL = register("hobbit_windmill_spawner");
   public static final ResourceLocation HOBBIT_FAMILY_HOUSE = register("hobbit_family_house_spawner");
   public static final ResourceLocation HOBBIT_FAMILY_HOLE_SMALL = register("hobbit_family_hole_small_spawner");
   public static final ResourceLocation HOBBIT_FAMILY_HOLE_LARGE = register("hobbit_family_hole_large_spawner");
   // RANGERS
   public static final ResourceLocation DUNEDAIN_RANGER_WATCHTOWER = register("dunedain_watchtower_ranger_spawner");
   public static final ResourceLocation DUNEDAIN_RANGER_CAMP = register("dunedain_camp_ranger_spawner");
   public static final ResourceLocation DUNEDAIN_RANGER_CAMP_ANIMALS = register("dunedain_camp_animal_spawner");



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
