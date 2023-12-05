package lotr.common.structure;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;

import lotr.common.init.ExtendedItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public class StructureScanner {
	private static ServerPlayerEntity selectedPlayer;
	private static String wandID;
	private static BlockPos pos1;
	private static BlockPos pos2;
	private static Boolean blockStateFlag = false;
	private static Boolean entityStateFlag = false;
	private static Boolean nbtStateFlag = false;

	@SubscribeEvent(priority = EventPriority.HIGHEST)
    public void playerInteractEvent(PlayerInteractEvent.LeftClickBlock event)
    {
    	// Only handle server events
        if (event.getPlayer().level.isClientSide)
            return;

        PlayerEntity player = event.getPlayer();

        if (!player.abilities.instabuild || !ServerLifecycleHooks.getCurrentServer().getPlayerList().isOp(player.getGameProfile())){
            return;
        }

        if (event.getHand() == Hand.OFF_HAND || player.getMainHandItem() == null || !player.getMainHandItem().getItem().equals(ExtendedItems.ANGMAR_AXE.get())){
            return;
        }

        pos1 = event.getPos();
        player.createCommandSourceStack().sendSuccess(new StringTextComponent("Pos1 set to "+pos1.getX()+", "+pos1.getY()+", "+pos1.getZ()), false);
        event.setCanceled(true);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void playerInteractEvent(PlayerInteractEvent.RightClickBlock event)
    {
    	// Only handle server events
        if (event.getPlayer().level.isClientSide)
            return;

        PlayerEntity player = event.getPlayer();

        if (!player.abilities.instabuild || !ServerLifecycleHooks.getCurrentServer().getPlayerList().isOp(player.getGameProfile())){
            return;
        }

        if (event.getHand() == Hand.OFF_HAND || player.getMainHandItem() == null || !player.getMainHandItem().getItem().equals(ExtendedItems.ANGMAR_AXE.get())){
            return;
        }

        pos2 = event.getPos();
        player.createCommandSourceStack().sendSuccess(new StringTextComponent("Pos2 set to "+pos2.getX()+", "+pos2.getY()+", "+pos2.getZ()), false);
        event.setCanceled(true);

    }
    
	public static ServerPlayerEntity getSelectedPlayer() {
		return selectedPlayer;
	}
	public static void setSelectedPlayer(ServerPlayerEntity selectedPlayer) {
		StructureScanner.selectedPlayer = selectedPlayer;
	}
	public static BlockPos getPos1() {
		return pos1;
	}
	public static void setPos1(BlockPos pos1) {
		StructureScanner.pos1 = pos1;
	}
	public static BlockPos getPos2() {
		return pos2;
	}
	public static void setPos2(BlockPos pos2) {
		StructureScanner.pos2 = pos2;
	}
	public static Boolean getBlockStateFlag() {
		return blockStateFlag;
	}
	public static void setBlockStateFlag(Boolean blockStateFlag) {
		StructureScanner.blockStateFlag = blockStateFlag;
	}
	public static Boolean getEntityStateFlag() {
		return entityStateFlag;
	}
	public static void setEntityStateFlag(Boolean entityStateFlag) {
		StructureScanner.entityStateFlag = entityStateFlag;
	}
	public static Boolean getNbtStateFlag() {
		return nbtStateFlag;
	}
	public static void setNbtStateFlag(Boolean nbtStateFlag) {
		StructureScanner.nbtStateFlag = nbtStateFlag;
	}

	public static boolean scanStructure(CommandContext<CommandSource> context) {
		String name = StringArgumentType.getString(context, "name");
		if(pos1==null||pos2==null) {
			context.getSource().sendSuccess(new StringTextComponent("No corners set!") , true);
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
			context.getSource().sendSuccess(new StringTextComponent("Non set Corner Value!") , true);
			return false;
		}

		ListNBT blockDataMap = new ListNBT();
		BlockPos bottomFrontLeft = new BlockPos(bottomFrontLeftX,bottomFrontLeftY,bottomFrontLeftZ);
		BlockPos topBackRight = new BlockPos(topBackRightX,topBackRightY,topBackRightZ);
		int ypos = -1;
		int xpos = -1;
		int zpos = -1;
		for(int y = bottomFrontLeft.getY(); y < topBackRight.getY()+1; y++) {
			ypos++;
			for(int x = bottomFrontLeft.getX(); x < topBackRight.getX()+1; x++) {
				xpos++;
				for(int z = bottomFrontLeft.getZ(); z < topBackRight.getZ()+1; z++) {
					zpos++;
					BlockState state = context.getSource().getLevel().getBlockState(new BlockPos(x,y,z));
					if(state.getBlock().equals(Blocks.REDSTONE_BLOCK)) {
						state = Blocks.AIR.defaultBlockState();
					}
					CompoundNBT dataHolder = new CompoundNBT();
					dataHolder.put("blockData", NBTUtil.writeBlockState(state));
					dataHolder.put("relativeLocation", StructureFileHandler.writeBlockPos(xpos, ypos, zpos));
					blockDataMap.add(dataHolder);
				}
				zpos = -1;
			}
			xpos = -1;
		}
		CompoundNBT dataCompound = new CompoundNBT();
		dataCompound.put("blockMap", blockDataMap);
		dataCompound.put("structureSize", StructureFileHandler.createStructureSize(xpos, ypos, zpos));
		dataCompound.putString("structureName", name);
		StructureFileHandler.save(name, context.getSource().getLevel(), dataCompound);
		return true;
	}
}
