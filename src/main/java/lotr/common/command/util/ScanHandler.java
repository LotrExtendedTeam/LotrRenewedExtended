package lotr.common.command.util;

import com.mojang.brigadier.context.CommandContext;

import net.minecraft.block.Blocks;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;

public class ScanHandler {
	private static ServerPlayerEntity selectedPlayer;
	private static BlockPos pos1;
	private static BlockPos pos2;
	private static Boolean blockStateFlag = false;
	private static Boolean entityStateFlag = false;
	private static Boolean nbtStateFlag = false;

	public static ServerPlayerEntity getSelectedPlayer() {
		return selectedPlayer;
	}
	public static void setSelectedPlayer(ServerPlayerEntity selectedPlayer) {
		ScanHandler.selectedPlayer = selectedPlayer;
	}
	public static BlockPos getPos1() {
		return pos1;
	}
	public static void setPos1(BlockPos pos1) {
		ScanHandler.pos1 = pos1;
	}
	public static BlockPos getPos2() {
		return pos2;
	}
	public static void setPos2(BlockPos pos2) {
		ScanHandler.pos2 = pos2;
	}
	public static Boolean getBlockStateFlag() {
		return blockStateFlag;
	}
	public static void setBlockStateFlag(Boolean blockStateFlag) {
		ScanHandler.blockStateFlag = blockStateFlag;
	}
	public static Boolean getEntityStateFlag() {
		return entityStateFlag;
	}
	public static void setEntityStateFlag(Boolean entityStateFlag) {
		ScanHandler.entityStateFlag = entityStateFlag;
	}
	public static Boolean getNbtStateFlag() {
		return nbtStateFlag;
	}
	public static void setNbtStateFlag(Boolean nbtStateFlag) {
		ScanHandler.nbtStateFlag = nbtStateFlag;
	}

	public static boolean scanStructure(CommandContext<CommandSource> context) {
		if(pos1==null||pos2==null) {
			return false;
		}
		int bottomFrontLeftX = 0;
		int bottomFrontLeftY = 0;
		int bottomFrontLeftZ = 0;
		int topBackRightX = 0;
		int topBackRightY = 0;
		int topBackRightZ = 0;
		//X
		if(pos1.getX()<pos2.getX()) {
			bottomFrontLeftX = pos1.getX();
			topBackRightX = pos2.getX();
		}else if(pos1.getX()>pos2.getX()) {
			bottomFrontLeftX = pos2.getX();
			topBackRightX = pos1.getX();
		}else {
			bottomFrontLeftX = pos1.getX();
			topBackRightX = pos1.getX();
		}
		//Y
		if(pos1.getY()<pos2.getY()) {
			bottomFrontLeftY = pos1.getY();
			topBackRightY = pos2.getY();
		}else if(pos1.getY()>pos2.getY()) {
			bottomFrontLeftY = pos2.getY();
			topBackRightY = pos1.getY();
		}else {
			bottomFrontLeftY = pos1.getY();
			topBackRightY = pos1.getY();
		}
		//Z
		if(pos1.getZ()<pos2.getZ()) {
			bottomFrontLeftZ = pos1.getZ();
			topBackRightZ = pos2.getZ();
		}else if(pos1.getZ()>pos2.getZ()) {
			bottomFrontLeftZ = pos2.getZ();
			topBackRightZ = pos1.getZ();
		}else {
			bottomFrontLeftZ = pos1.getZ();
			topBackRightZ = pos1.getZ();
		}
		if(bottomFrontLeftX==0||bottomFrontLeftY==0||bottomFrontLeftZ==0||topBackRightX==0||topBackRightY==0||topBackRightZ==0) {
			System.out.println("Non set Corner Value");
			return false;
		}
		BlockPos bottomFrontLeft = new BlockPos(bottomFrontLeftX,bottomFrontLeftY,bottomFrontLeftZ);
		BlockPos topBackRight = new BlockPos(topBackRightX,topBackRightY,topBackRightZ);
		for(int y = bottomFrontLeft.getY(); y < topBackRight.getY()+1; y++) {
			for(int x = bottomFrontLeft.getX(); x < topBackRight.getX()+1; x++) {
				for(int z = bottomFrontLeft.getZ(); z < topBackRight.getZ()+1; z++) {
					context.getSource().getLevel().setBlock(new BlockPos(x,y,z), Blocks.GLASS.defaultBlockState(), 0);
				}
			}
		}
		return true;
	}
}
