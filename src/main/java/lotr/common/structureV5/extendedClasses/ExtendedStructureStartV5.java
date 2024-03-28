package lotr.common.structureV5.extendedClasses;

import java.util.Iterator;
import java.util.Random;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.jigsaw.JigsawManager;
import net.minecraft.world.gen.feature.structure.AbstractVillagePiece;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraft.world.gen.feature.template.TemplateManager;

public abstract class ExtendedStructureStartV5<C extends IFeatureConfig> extends StructureStart<C>{
	public abstract String getLocation();
	
	public ExtendedStructureStartV5(Structure<C> feature, int chunkX, int chunkZ, MutableBoundingBox boundingBox, int references, long seed) {
		super(feature, chunkX, chunkZ, boundingBox, references, seed);
	}

	@Override
	public void generatePieces(DynamicRegistries dynamicRegistryManager, ChunkGenerator chunkGenerator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn, C config) {
		// Turns the chunk coordinates into actual coordinates we can use. (Gets center of that chunk)
        int x = (chunkX << 4) + 7;
        int z = (chunkZ << 4) + 7;
        BlockPos blockpos = new BlockPos(x, 0, z);

        JigsawManager.addPieces(dynamicRegistryManager,
                new VillageConfig(() -> dynamicRegistryManager.registryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
                		.get(new ResourceLocation(LotrExtendedMod.MODID, getLocation())), maxDepth()), AbstractVillagePiece::new, 
                chunkGenerator, templateManagerIn, blockpos, this.pieces, this.random, false,true);

        this.pieces.forEach(piece -> piece.move(0, yShift(), 0));
        
//        Vector3i structureCenter = this.pieces.get(0).getBoundingBox().getCenter();
//        int xOffset = blockpos.getX() - structureCenter.getX();
//        int zOffset = blockpos.getZ() - structureCenter.getZ();
//        for(StructurePiece structurePiece : this.pieces){
//            structurePiece.move(xOffset, 0, zOffset);
//        }
        this.calculateBoundingBox();
	}

	public int yShift() {
		return 0;
	}

	public int maxDepth() {
		return 7;
	}

	@Override
	public void placeInChunk(ISeedReader p_230366_1_, StructureManager p_230366_2_, ChunkGenerator p_230366_3_, Random p_230366_4_, MutableBoundingBox p_230366_5_, ChunkPos p_230366_6_) {
		synchronized(this.pieces) {
			if (!this.pieces.isEmpty()) {
				MutableBoundingBox mutableboundingbox = (this.pieces.get(0)).boundingBox;
				Vector3i vector3i = mutableboundingbox.getCenter();
				BlockPos blockpos = new BlockPos(vector3i.getX(), mutableboundingbox.y0, vector3i.getZ());
				Iterator<StructurePiece> iterator = this.pieces.iterator();

				while(iterator.hasNext()) {
					StructurePiece structurepiece = iterator.next();
					if (structurepiece.getBoundingBox().intersects(p_230366_5_) && !structurepiece.postProcess(p_230366_1_, p_230366_2_, p_230366_3_, p_230366_4_, p_230366_5_, p_230366_6_, blockpos)) {
						iterator.remove();
					}
				}
				this.calculateBoundingBox();
			}
		}
	}
}
