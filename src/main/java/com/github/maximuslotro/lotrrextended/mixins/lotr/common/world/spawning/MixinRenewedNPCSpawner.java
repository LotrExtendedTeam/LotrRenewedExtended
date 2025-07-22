package com.github.maximuslotro.lotrrextended.mixins.lotr.common.world.spawning;

import java.util.Collections;
import java.util.List;
import java.util.Random;

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
/**
 * Mixin to modify the NPC spawning behavior in {@link RenewedNPCSpawner}.
 * 
 * <p>This mixin injects a global spawn damping coefficient into the NPC density cap calculation,
 * effectively controlling the overall NPC spawn rate across the server.</p>
 * 
 * <p>It overwrites the {@code runSpawnCycle} method to:</p>
 * <ul>
 *   <li>Gather spawnable chunks</li>
 *   <li>Calculate the NPC density cap per chunk, scaled by the damping coefficient</li>
 *   <li>Sum current NPC spawn weights in the world</li>
 *   <li>Perform spawning in chunks until the cap is reached or exceeded</li>
 * </ul>
 * 
 * @author maximuslotro
 * @Mixin (RenewedNPCSpawner.class)
 */
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
     * Runs one cycle of NPC spawning.
     * 
     * <p>This method collects all spawnable chunks and calculates an NPC density cap
     * which is modified by a global damping coefficient from server config. It sums
     * the current NPC spawn weights and attempts to spawn NPCs in shuffled chunks
     * until the cap is reached.</p>
     * 
     * @param world The server world in which to run the spawn cycle.
     * @author maximuslotro
	 * @reason need to inject npcGlobalSpawnDampingCoefficient into cap calculation
	 * @Overwrite (remap=false)
     */
	@Overwrite(remap=false)
	private void runSpawnCycle(ServerWorld world) {
		List<ChunkPos> chunks = gatherSpawnableChunks(world);
		double sumDensity = chunks.stream().mapToDouble(chunk -> getNPCDensityCapForChunk(world, chunk)).sum();
		int cap = (int) (sumDensity * 0.51D/*Extended Start*/ * ExtendedServerConfig.npcGlobalSpawnDampingCoefficient.get()/*Extended End*/);
		double count = Streams.stream(world.getAllEntities()).filter(this::includeInNPCCount).map(e -> ((NPCEntity) e)).mapToDouble(NPCEntity::getSpawnCountWeight).sum();
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
