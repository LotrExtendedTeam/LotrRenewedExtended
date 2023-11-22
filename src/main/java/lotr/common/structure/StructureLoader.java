package lotr.common.structure;


import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

public class StructureLoader {

	public static boolean loadStructure(String strName, ServerWorld world, BlockPos pos) {
		CompoundNBT compound = StructureFileHandler.load(strName, world);
		if(compound==null) {
			return false;
		}
		
		CompoundNBT size = compound.getCompound("structureSize");
		ListNBT blockMap = compound.getList("blockMap", 10);
		
		for (int i = 0; i < blockMap.size(); i++) {
			CompoundNBT nbt = blockMap.getCompound(i);
			RelativePosition rpos = StructureFileHandler.readBlockPos(nbt.getCompound("relativeLocation"));
			CompoundNBT blockData = nbt.getCompound("blockData");
			BlockState state = NBTUtil.readBlockState(blockData);
			BlockPos bPos = new BlockPos(pos.getX()+rpos.X, pos.getY()+rpos.Y, pos.getZ()+rpos.Z);
			world.setBlockAndUpdate(bPos, state);
		}
		return true;
	}

}
