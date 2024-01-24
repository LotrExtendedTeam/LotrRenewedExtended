package lotr.common.world.gen.feature.structuresV3.standalone;

import java.util.Random;

import lotr.common.structure.StructureFile;
import lotr.common.structure.util.RelativePosition;
import lotr.common.structure.util.StructureStrings;
import lotr.common.world.gen.feature.structuresV3.ExtendedStructureBase;
import lotr.common.world.gen.feature.structuresV3.parts.ExtendedKegDrinks;
import lotr.common.world.gen.feature.structuresV3.parts.ExtendedSignTexts;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;

public class HobbitHoleSmallFeature extends ExtendedStructureBase {

	@Override
	public boolean placeWithRotation(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos, Direction direction) {
		StructureFile file = getStructure();
		if(file!=null){
			generateOffsets(world, rand, pos.below(), direction, file.readStructureSize(), file.getNaturalFacingDirection());
			for (int i = 0; i < file.getLayers().size(); i++) {
				CompoundNBT nbt = file.getLayers().getCompound(i);
				RelativePosition rpos = StructureFile.readBlockPos(nbt.getCompound(StructureStrings.relativeLocationHolder));
				CompoundNBT blockData = nbt.getCompound(StructureStrings.blockMapHolder);
				BlockState blockstate = NBTUtil.readBlockState(blockData);
				if(rpos.Y==0 && blockstate.getBlock()!=Blocks.AIR) {
					fixBelowBlocks(world, rpos.X, rpos.Y, rpos.Z, Blocks.GRASS_BLOCK, Blocks.DIRT);
				}
				setBlockAndMetadata(world, rpos.X, rpos.Y, rpos.Z, blockstate);
				genNBTForCurrentBlock(world, rand, blockstate, rpos.X, rpos.Y, rpos.Z, nbt);
				genKegForCurrentBlock(world, rand, blockstate, rpos.X, rpos.Y, rpos.Z, ExtendedKegDrinks.shireDrinksPool);
			}
			doFileGen(world, rand, file);
			return true;
		}
		return false;
	}

	@Override
	public void setSignTextFromFileData(ISeedReader world, Random random, BlockPos blockPos, int num) {
		setSignTileEntity(world, blockPos, ExtendedSignTexts.ShireHouseTexts(random));
	}

	@Override
	public String fileName() {
		return "hobbit_hole_small";
	}
}
