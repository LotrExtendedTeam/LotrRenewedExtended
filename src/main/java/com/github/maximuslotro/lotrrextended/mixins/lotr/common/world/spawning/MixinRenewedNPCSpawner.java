package com.github.maximuslotro.lotrrextended.mixins.lotr.common.world.spawning;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;
import com.google.common.collect.Streams;

import lotr.common.entity.npc.NPCEntity;
import lotr.common.world.spawning.RenewedNPCSpawner;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.server.ServerWorld;

@Mixin(RenewedNPCSpawner.class)
public class MixinRenewedNPCSpawner {

	@Shadow(remap=false)
	private boolean includeInNPCCount(Entity e) {return false;}

	@Shadow(remap=false)
	private double performSpawningInChunk(ServerWorld world, ChunkPos chunkPos, Random rand) {return 0.0;}

	@Shadow(remap=false)
	private List<ChunkPos> gatherSpawnableChunks(ServerWorld world) {return null;}

	@Shadow(remap=false)
	private double getNPCDensityCapForChunk(ServerWorld world, ChunkPos chunkPos) {return 0.0;}

	/**
	 * @author maximuslotro
	 * @reason need to inject npcGlobalSpawnDampingCoefficient into cap calculation 
	 * */
	@Overwrite(remap=false)
	private void runSpawnCycle(ServerWorld world) {
		List<ChunkPos> chunks = gatherSpawnableChunks(world);
		double sumDensity = chunks.stream().collect(Collectors.summingDouble(chunk -> getNPCDensityCapForChunk(world, chunk)));
		int cap = (int) (sumDensity * 0.51D/*Extended Start*/ * ExtendedServerConfig.npcGlobalSpawnDampingCoefficient.get()/*Extended End*/);
		double count = Streams.stream(world.getAllEntities()).filter(this::includeInNPCCount).map(e -> ((NPCEntity) e)).collect(Collectors.summingDouble(NPCEntity::getSpawnCountWeight));
		if (count < cap) {
			Collections.shuffle(chunks);
			Random rand = world.random;

			for (ChunkPos chunkPos : chunks) {
				count += performSpawningInChunk(world, chunkPos, rand);
				if (count >= cap) {
					break;
				}
			}
		}
	}
}
