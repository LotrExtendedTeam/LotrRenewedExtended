package com.github.maximuslotro.lotrrextended.mixins.net.minecraft.world.chunk.storage;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;
import com.google.common.collect.Maps;

import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.LongArrayNBT;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.storage.ChunkSerializer;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;

@Mixin(ChunkSerializer.class)
public class MixinChunkSerializer {
	@Final
	@Shadow
	private static Logger LOGGER;

	@Inject(method = "packStructureData(Lnet/minecraft/util/math/ChunkPos;Ljava/util/Map;Ljava/util/Map;)Lnet/minecraft/nbt/CompoundNBT;", at = @At("HEAD"), cancellable = true)
	private static void packStructureData(ChunkPos p_222649_0_, Map<Structure<?>, StructureStart<?>> p_222649_1_, Map<Structure<?>, LongSet> p_222649_2_, CallbackInfoReturnable<CompoundNBT> ctr) {
		CompoundNBT compoundnbt = new CompoundNBT();
		CompoundNBT compoundnbt1 = new CompoundNBT();

		for(Entry<Structure<?>, StructureStart<?>> entry : p_222649_1_.entrySet()) {
			if(entry.getKey()==null||entry.getKey().getFeatureName()==null||entry.getValue()==null) {
				if(ExtendedServerConfig.enableExtendedLogging.get()) {
					ExtendedLog.info("Removed an invald structure start from saving into the chunkMap:");
				}
				continue;
			}
			compoundnbt1.put(entry.getKey().getFeatureName(), entry.getValue().createTag(p_222649_0_.x, p_222649_0_.z));
		}

		compoundnbt.put("Starts", compoundnbt1);
		CompoundNBT compoundnbt2 = new CompoundNBT();

		for(Entry<Structure<?>, LongSet> entry1 : p_222649_2_.entrySet()) {
			if(entry1.getKey()==null||entry1.getKey().getFeatureName()==null||entry1.getValue()==null) {
				if(ExtendedServerConfig.enableExtendedLogging.get()) {
					ExtendedLog.info("Removed an invald structure reference from saving into the chunkMap");
				}
				continue;
			}
			compoundnbt2.put(entry1.getKey().getFeatureName(), new LongArrayNBT(entry1.getValue()));
		}

		compoundnbt.put("References", compoundnbt2);
		ctr.setReturnValue(compoundnbt);
	}

	@Inject(method = "unpackStructureReferences(Lnet/minecraft/util/math/ChunkPos;Lnet/minecraft/nbt/CompoundNBT;)Ljava/util/Map;", at = @At("HEAD"), cancellable = true)
	private static void unpackStructureReferences(ChunkPos p_227075_0_, CompoundNBT p_227075_1_, CallbackInfoReturnable<Map<Structure<?>, LongSet>> ctr) {
		Map<Structure<?>, LongSet> map = Maps.newHashMap();
		CompoundNBT compoundnbt = p_227075_1_.getCompound("References");

		for(String s : compoundnbt.getAllKeys()) {
			Structure<?> nulable = Structure.STRUCTURES_REGISTRY.get(s.toLowerCase(Locale.ROOT));
			if(nulable==null) {
				if(ExtendedServerConfig.enableExtendedLogging.get()) {
					ExtendedLog.info("Removed an invalid structure reference to "+s+" from loading into the chunkMap, Fixed a Potential Crash");
				}
				continue;
			}
			map.put(nulable, new LongOpenHashSet(Arrays.stream(compoundnbt.getLongArray(s)).filter((p_227074_2_) -> {
	            ChunkPos chunkpos = new ChunkPos(p_227074_2_);
	            if (chunkpos.getChessboardDistance(p_227075_0_) > 8) {
	               LOGGER.warn("Found invalid structure reference [ {} @ {} ] for chunk {}.", s, chunkpos, p_227075_0_);
	               return false;
	            } else {
	               return true;
	            }
	         }).toArray()));
	      }

	      ctr.setReturnValue(map);
	   }

	@Inject(method = "unpackStructureStart(Lnet/minecraft/world/gen/feature/template/TemplateManager;Lnet/minecraft/nbt/CompoundNBT;J)Ljava/util/Map;", at = @At("HEAD"), cancellable = true)
	private static void unpackStructureStart(TemplateManager p_235967_0_, CompoundNBT p_235967_1_, long p_235967_2_, CallbackInfoReturnable<Map<Structure<?>, StructureStart<?>>> ctr) {
		Map<Structure<?>, StructureStart<?>> map = Maps.newHashMap();
		CompoundNBT compoundnbt = p_235967_1_.getCompound("Starts");

		for(String s : compoundnbt.getAllKeys()) {
			String s1 = s.toLowerCase(Locale.ROOT);
			Structure<?> structure = Structure.STRUCTURES_REGISTRY.get(s1);
			if (structure == null) {
				if(s1.contains("lotrextended")) {
					if(ExtendedServerConfig.enableExtendedLogging.get()) {
						ExtendedLog.info("Removed an Unknown structure reference to "+s+" from loading into the chunkMap");
					}
				}
				else {
					LOGGER.error("Unknown structure start: {}", (Object)s1);
				}
			} else {
				StructureStart<?> structurestart = Structure.loadStaticStart(p_235967_0_, compoundnbt.getCompound(s), p_235967_2_);
				if (structurestart != null) {
					map.put(structure, structurestart);
				}
			}
		}
		ctr.setReturnValue(map);
	}
}
