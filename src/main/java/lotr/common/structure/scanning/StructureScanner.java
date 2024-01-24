package lotr.common.structure.scanning;

import com.github.maximuslotro.lotroExtended.ExtendedLog;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;

import lotr.common.init.ExtendedItems;
import lotr.common.structure.StructureFileIO;
import lotr.common.structure.util.StructureStrings;
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
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
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
	private static BlockPos origin;
	private static Boolean blockStateFlag = false;
	private static Boolean entityStateFlag = false;
	private static Boolean nbtStateFlag = false;
	private static Direction direction;

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

        setPos1(event.getPos());
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

        if(player.isCrouching()) {
        	setOrigin(event.getPos());
            player.createCommandSourceStack().sendSuccess(new StringTextComponent("Origin set to "+origin.getX()+", "+origin.getY()+", "+origin.getZ()), false);
        }
        else {
        	setPos2(event.getPos());
            player.createCommandSourceStack().sendSuccess(new StringTextComponent("Pos2 set to "+pos2.getX()+", "+pos2.getY()+", "+pos2.getZ()), false);
        }
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
		StructureScanner.pos1 = new BlockPos(MathHelper.floor(pos1.getX()),MathHelper.floor(pos1.getY()),MathHelper.floor(pos1.getZ()));
	}
	public static BlockPos getPos2() {
		return pos2;
	}
	public static void setOrigin(BlockPos origin) {
		StructureScanner.origin = new BlockPos(MathHelper.floor(origin.getX()),MathHelper.floor(origin.getY()),MathHelper.floor(origin.getZ()));
	}
	public static BlockPos getOrigin() {
		return origin;
	}
	public static void setPos2(BlockPos pos2) {
		StructureScanner.pos2 = new BlockPos(MathHelper.floor(pos2.getX()),MathHelper.floor(pos2.getY()),MathHelper.floor(pos2.getZ()));
	}
	public static Direction getFacingDirection() {
		return direction;
	}
	public static void setFacingDirection(Direction direction) {
		StructureScanner.direction = direction;
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
			context.getSource().sendSuccess(new StringTextComponent("Corners not set!") , true);
			return false;
		}
		if(origin==null) {
			context.getSource().sendSuccess(new StringTextComponent("Origin not set!") , true);
			return false;
		}
		if(direction==null) {
			context.getSource().sendSuccess(new StringTextComponent("Direction not set!") , true);
			return false;
		}
		int bottomX = 0;
		int bottomY = 0;
		int bottomZ = 0;
		int topX = 0;
		int topY = 0;
		int topZ = 0;
		//X
		bottomX = Math.min(pos1.getX(), pos2.getX());
		topX = Math.max(pos1.getX(), pos2.getX());
		//Y
		bottomY = Math.min(pos1.getY(), pos2.getY());
		topY = Math.max(pos1.getY(), pos2.getY());
		//Z
		bottomZ = Math.min(pos1.getZ(), pos2.getZ());
		topZ = Math.max(pos1.getZ(), pos2.getZ());

		ListNBT blockDataMap = new ListNBT();
		ListNBT entitySpawnMap = new ListNBT();
		ListNBT signTextMap = new ListNBT();
		
		for(int y = bottomY; y < topY+1; y++) {
			for(int x = bottomX; x < topX+1; x++) {
				for(int z = bottomZ; z < topZ+1; z++) {
					int posX = x - origin.getX();
					int posY = y - origin.getY();
					int posZ = z - origin.getZ();
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
							entity.put(StructureStrings.relativeLocationHolder, writeBlockPos(posX, posY, posZ));
							if(entity.contains("entity")) {
								entitySpawnMap.add(entity);
								state = Blocks.AIR.defaultBlockState();
							}
							else if(entity.contains(StructureStrings.signNumberHolder)) {
								signTextMap.add(entity);
							}
							else {
								ExtendedLog.error("FOUND INVALID SIGN TYPE!");
							}
						}
					}
					if(state.getBlock() instanceof ContainerBlock) {
						TileEntity tileentity = context.getSource().getLevel().getBlockEntity(new BlockPos(x,y,z));
						if (tileentity instanceof LockableLootTileEntity) {
							LockableLootTileEntity te =((LockableLootTileEntity)tileentity);
							if(te.getCustomName()!=null) {
								dataHolder.putString(StructureStrings.structureLootTableHolder, te.getCustomName().getString());
							}
							else {
								ExtendedLog.error("FOUND MISSING LockableLootTileEntity LOOTTABLE! X: "+x+", Y: "+y+", Z:"+z);
								dataHolder.putString(StructureStrings.structureLootTableHolder, StructureStrings.EMPTY);
							}
						}
					}
					dataHolder.put(StructureStrings.blockMapHolder, NBTUtil.writeBlockState(state));
					dataHolder.put(StructureStrings.relativeLocationHolder, writeBlockPos(posX, posY, posZ));
					blockDataMap.add(dataHolder);
				}
			}
		}
		
		CompoundNBT dataCompound = new CompoundNBT();
		dataCompound.put(StructureStrings.blockMapHolder, blockDataMap);
		dataCompound.put(StructureStrings.entityMapHolder, entitySpawnMap);
		dataCompound.put(StructureStrings.signMapHolder, signTextMap);
		int yHeight = Math.abs(topY - bottomY)+1;
		int XWidth = Math.abs(topX - bottomX)+1;
		int ZWidth = Math.abs(topZ - bottomZ)+1;
		dataCompound.put(StructureStrings.structureSizeHolder, createStructureSize(XWidth, yHeight, ZWidth));
		dataCompound.putString(StructureStrings.structureDirectionHolder, direction.getName());
		dataCompound.putString(StructureStrings.structureNameHolder, name);
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
