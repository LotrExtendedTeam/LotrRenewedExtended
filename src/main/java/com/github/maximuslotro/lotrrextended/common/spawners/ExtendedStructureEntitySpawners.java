package com.github.maximuslotro.lotrrextended.common.spawners;

import java.util.Collections;
import java.util.Set;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.google.common.collect.Sets;

import net.minecraft.util.ResourceLocation;

/**
 * Stores IDs for built in entity spawners.
 */
public class ExtendedStructureEntitySpawners {
   private static final Set<ResourceLocation> LOCATIONS = Sets.newHashSet();
   private static final Set<ResourceLocation> IMMUTABLE_LOCATIONS = Collections.unmodifiableSet(LOCATIONS);
   public static final ResourceLocation EMPTY = new ResourceLocation("empty");
   public static final ResourceLocation HOBBIT_BARTENDER = register("hobbit_bartender_spawner");

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
