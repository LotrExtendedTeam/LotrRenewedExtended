package lotr.common.world.gen.feature.structure.standalone;

import java.util.Random;

import lotr.common.init.LOTRBlocks;
import lotr.common.structure.RelativePosition;
import lotr.common.structure.StructureFile;
import lotr.common.world.gen.feature.structure.ExtendedStructureBase;
import lotr.common.world.gen.feature.structure.parts.KegDrinks;
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
		StructureFile file = getStructure("hobbit_barn");
		if(file!=null){
			generateOffsets(world, rand, pos, rot);
			for (int i = 0; i < file.getLayers().size(); i++) {
				CompoundNBT nbt = file.getLayers().getCompound(i);
				RelativePosition rpos = StructureFile.readBlockPos(nbt.getCompound("relativeLocation"));
				CompoundNBT blockData = nbt.getCompound("blockData");
				BlockState blockstate = NBTUtil.readBlockState(blockData);
				if(rpos.Y==0 && blockstate.getBlock()!=Blocks.AIR) {
					fixBelowBlocks(world, rpos.X, rpos.Y, rpos.Z, Blocks.GRASS_BLOCK, Blocks.DIRT);
				}
				setBlockAndMetadata(world, rpos.X, rpos.Y, rpos.Z, blockstate);
				if(blockstate.getBlock()==Blocks.BARREL) {
					setLockableLootTileEntityContents(world, rand, rpos.X, rpos.Y, rpos.Z, "hobbit_chest");
				}
				if(blockstate.getBlock()==Blocks.CHEST) {
					setLockableLootTileEntityContents(world, rand, rpos.X, rpos.Y, rpos.Z, "hobbit_chest");
				}
				if(blockstate.getBlock()==LOTRBlocks.KEG.get()) {
					setKegTileEntityContents(world, rand, rpos.X, rpos.Y, rpos.Z, KegDrinks.shireDrinksPool);
				}
				//context.getSource().getLevel().setBlockAndUpdate(bPos, state);
			}
			return true;
		}
		return false;
	}
}
