package lotr.common.world.gen.feature.extendedstructures.standalone;

import java.util.Random;

import lotr.common.structure.RelativePosition;
import lotr.common.structure.StructureFile;
import lotr.common.structure.StructureScanner;
import lotr.common.world.gen.feature.extendedstructures.ExtendedStructureBase;
import lotr.common.world.gen.feature.extendedstructures.parts.ExtendedKegDrinks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;

public class HobbitBarnFeature extends ExtendedStructureBase {

	@Override
	public boolean placeWithRotation(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos, int rot) {
		StructureFile file = getStructure();
		if(file!=null){
			generateOffsets(world, rand, pos.below(), rot, file.readStructureSize());
			for (int i = 0; i < file.getLayers().size(); i++) {
				CompoundNBT nbt = file.getLayers().getCompound(i);
				RelativePosition rpos = StructureFile.readBlockPos(nbt.getCompound(StructureScanner.relativeLocationHolder));
				CompoundNBT blockData = nbt.getCompound(StructureScanner.blockMapHolder);
				BlockState blockstate = NBTUtil.readBlockState(blockData);
				if(rpos.Y==0 && blockstate.getBlock()!=Blocks.AIR) {
					fixBelowBlocks(world, rpos.X, rpos.Y, rpos.Z, Blocks.GRASS_BLOCK, Blocks.DIRT);
				}
				setBlockAndMetadata(world, rpos.X, rpos.Y, rpos.Z, blockstate);
				doNBTGen(world, rand, blockstate, rpos.X, rpos.Y, rpos.Z, nbt);
				doKegGen(world, rand, blockstate, rpos.X, rpos.Y, rpos.Z, ExtendedKegDrinks.shireDrinksPool);
			}
			doFileGen(world, rand, file);
			return true;
		}
		return false;
	}

	@Override
	public void setSignTextFromFileData(ISeedReader world, Random random, BlockPos blockPos, int num) {}

	@Override
	public String fileName() {
		return "hobbit_barn";
	}
}
