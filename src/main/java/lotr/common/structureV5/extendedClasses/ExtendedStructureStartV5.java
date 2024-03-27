package lotr.common.structureV5.extendedClasses;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.jigsaw.JigsawManager;
import net.minecraft.world.gen.feature.structure.AbstractVillagePiece;
import net.minecraft.world.gen.feature.structure.Structure;
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
}
