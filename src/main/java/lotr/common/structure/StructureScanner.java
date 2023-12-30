package lotr.common.structure;

import com.github.maximuslotro.lotroExtended.ExtendedLog;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;

import lotr.common.init.ExtendedItems;
import net.minecraft.block.AbstractSignBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ContainerBlock;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public class StructureScanner {
	public static final String relativeLocationHolder = "relativeLocation";
	public static final String blockMapHolder = "blockDataMap";
	public static final String entityMapHolder = "entitySpawnMap";
	public static final String signMapHolder = "signTextMap";
	public static final String signNumberHolder = "signText";
	public static final String structureSizeHolder = "structureSize";
	public static final String structureNameHolder = "structureName";
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
		ListNBT entitySpawnMap = new ListNBT();
		ListNBT signTextMap = new ListNBT();
		BlockPos bottomFrontLeft = new BlockPos(bottomFrontLeftX,bottomFrontLeftY,bottomFrontLeftZ);
		BlockPos topBackRight = new BlockPos(topBackRightX,topBackRightY,topBackRightZ);
		
		int xpos = -1;
		int ypos = -1;
		int zpos = -1;
		int xSize = 0;
		int ySize = 0;
		int zSize = 0;
		for(int y = bottomFrontLeft.getY(); y < topBackRight.getY()+1; y++) {
			ypos++;
			for(int x = bottomFrontLeft.getX(); x < topBackRight.getX()+1; x++) {
				xpos++;
				for(int z = bottomFrontLeft.getZ(); z < topBackRight.getZ()+1; z++) {
					zpos++;
					BlockState state = context.getSource().getLevel().getBlockState(new BlockPos(x,y,z));
					CompoundNBT dataHolder = new CompoundNBT();
					if(state.getBlock().equals(Blocks.REDSTONE_BLOCK)||state.getBlock().equals(Blocks.AIR)) {
						continue;
					}
					if(state.getBlock().equals(Blocks.STRUCTURE_VOID)) {
						state = Blocks.AIR.defaultBlockState();
					}
					if(state.getBlock() instanceof AbstractSignBlock) {
						CompoundNBT entity = StructureScannerUtils.parseSignInfo(context.getSource().getLevel(), new BlockPos(x,y,z));
						if(entity!=null) {
							entity.put(relativeLocationHolder, writeBlockPos(xpos, ypos, zpos));
							if(entity.contains("entity")) {
								entitySpawnMap.add(entity);
								state = Blocks.AIR.defaultBlockState();
							}
							else if(entity.contains("signText")) {
								signTextMap.add(entity);
							}
							else {
								ExtendedLog.error("FOUND INVALID ENTITY TYPE!");
							}
						}
					}
					if(state.getBlock() instanceof ContainerBlock) {
						TileEntity tileentity = context.getSource().getLevel().getBlockEntity(new BlockPos(x,y,z));
						if (tileentity instanceof LockableLootTileEntity) {
							LockableLootTileEntity te =((LockableLootTileEntity)tileentity);
							if(te.getCustomName()!=null) {
								dataHolder.putString("loot-table", te.getCustomName().getString());
							}
							else {
								ExtendedLog.error("FOUND MISSING LockableLootTileEntity LOOTTABLE! X: "+x+", Y: "+y+", Z:"+z);
								dataHolder.putString("loot-table", "EMPTY");
							}
						}
					}
					dataHolder.put(blockMapHolder, NBTUtil.writeBlockState(state));
					dataHolder.put(relativeLocationHolder, writeBlockPos(xpos, ypos, zpos));
					blockDataMap.add(dataHolder);
				}
				if(zpos+1>zSize &&zSize==0) {
					zSize=zpos+1;
				}else if(zpos+1>zSize &&zSize!=0){
					ExtendedLog.error("ERROR SCANNING STRUCTURE! FOUND DIFFRENT SIZE LAYER ON Z CONSICUTIVE PASS!");
					ExtendedLog.error("CANCELING SCAN!");
					return false;
				}
				zpos = -1;
			}
			if(xpos+1>xSize &&xSize==0) {
				xSize=xpos+1;
			}else if(xpos+1>xSize &&xSize!=0){
				ExtendedLog.error("ERROR SCANNING STRUCTURE! FOUND DIFFRENT SIZE LAYER ON X CONSICUTIVE PASS!");
				ExtendedLog.error("CANCELING SCAN!");
				return false;
			}
			xpos = -1;
		}
		ySize=ypos+1;
		
		CompoundNBT dataCompound = new CompoundNBT();
		dataCompound.put(blockMapHolder, blockDataMap);
		dataCompound.put(entityMapHolder, entitySpawnMap);
		dataCompound.put(signMapHolder, signTextMap);
		dataCompound.put(structureSizeHolder, createStructureSize(xSize, ySize, zSize));
		dataCompound.putString(structureNameHolder, name);
		StructureFileIO.save(name, context.getSource().getLevel().getServer(), dataCompound);
		return true;
	}

	private static CompoundNBT writeBlockPos(int X, int Y, int Z) {
		CompoundNBT compoundnbt = new CompoundNBT();
		compoundnbt.putInt("X", X);
		compoundnbt.putInt("Y", Y);
		compoundnbt.putInt("Z", Z);
		return compoundnbt;
	}

	private static CompoundNBT createStructureSize(int X, int Y, int Z) {
		CompoundNBT compoundnbt = new CompoundNBT();
		compoundnbt.putInt("X", X);
		compoundnbt.putInt("Y", Y);
		compoundnbt.putInt("Z", Z);
		return compoundnbt;
	}
}
