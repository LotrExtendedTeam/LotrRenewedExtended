package lotr.common.structure;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;

import net.minecraft.block.BlockState;
import net.minecraft.command.CommandSource;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

public class StructureLoader {

	public static boolean loadStructure(CommandContext<CommandSource> context) {
		String name = StringArgumentType.getString(context, "name");
		ServerWorld world = context.getSource().getLevel();
		CompoundNBT compound = StructureFileHandler.load(name, world);
		if(compound==null) {
			return false;
		}
		
		CompoundNBT size = compound.getCompound("structureSize");
		ListNBT blockMap = compound.getList("blockMap", 10);
		
		for (int i = 0; i < blockMap.size(); i++) {
			CompoundNBT nbt = blockMap.getCompound(i);
			RelativePosition pos = StructureFileHandler.readBlockPos(nbt.getCompound("relativeLocation"));
			CompoundNBT blockData = nbt.getCompound("blockData");
			BlockState state = NBTUtil.readBlockState(blockData);
			BlockPos bPos = new BlockPos(context.getSource().getPosition().x+pos.X, context.getSource().getPosition().y+pos.Y, context.getSource().getPosition().z+pos.Z);
			world.setBlockAndUpdate(bPos, state);
		}
		return true;
	}

}
