package lotr.common.structure;

import java.util.ArrayList;
import java.util.List;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;

import lotr.common.block.BranchBlock;
import lotr.common.block.CandleBlock;
import lotr.common.block.CloverBlock;
import lotr.common.block.DirectionalMineralBlock;
import lotr.common.block.DoubleTorchBlock;
import lotr.common.block.DripstoneBlock;
import lotr.common.block.ExtendedBerryBushBlock;
import lotr.common.block.GateBlock;
import lotr.common.block.HearthBlock;
import lotr.common.block.KegBlock;
import lotr.common.block.LOTRBlockStates;
import lotr.common.block.LOTRPillarBlock;
import lotr.common.block.LOTRSlabBlock;
import lotr.common.block.MordorRockBlock;
import lotr.common.block.NonWaterloggableLanternBlock;
import lotr.common.block.ReedsBlock;
import lotr.common.block.StrippableBranchBlock;
import lotr.common.block.StrippableWoodBlock;
import lotr.common.block.VerticalOnlySlabBlock;
import lotr.common.block.VesselDrinkBlock;
import lotr.common.block.WattleAndDaubBlock;
import lotr.common.init.ExtendedItems;
import net.minecraft.block.AbstractButtonBlock;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.AbstractSignBlock;
import net.minecraft.block.AnvilBlock;
import net.minecraft.block.BannerBlock;
import net.minecraft.block.BarrelBlock;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.BubbleColumnBlock;
import net.minecraft.block.CakeBlock;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.CarvedPumpkinBlock;
import net.minecraft.block.CauldronBlock;
import net.minecraft.block.ChainBlock;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.DirectionalBlock;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.DoublePlantBlock;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.LadderBlock;
import net.minecraft.block.LanternBlock;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.LecternBlock;
import net.minecraft.block.LeverBlock;
import net.minecraft.block.PaneBlock;
import net.minecraft.block.PistonBlock;
import net.minecraft.block.PistonHeadBlock;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.RailBlock;
import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SkullBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.SnowBlock;
import net.minecraft.block.SnowyDirtBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.StandingSignBlock;
import net.minecraft.block.TrapDoorBlock;
import net.minecraft.block.TripWireBlock;
import net.minecraft.block.TripWireHookBlock;
import net.minecraft.block.VineBlock;
import net.minecraft.block.WallBannerBlock;
import net.minecraft.block.WallBlock;
import net.minecraft.block.WallSignBlock;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.block.WeightedPressurePlateBlock;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.state.Property;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public class ScanHandler {
	private static ServerPlayerEntity selectedPlayer;
	private static String wandID;
	private static BlockPos pos1;
	private static BlockPos pos2;
	private static Boolean blockStateFlag = false;
	private static Boolean entityStateFlag = false;
	private static Boolean nbtStateFlag = false;
	private static List<String> found;

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

		ServerWorld world = context.getSource().getLevel();
		ListNBT blockDataMap = new ListNBT();

		BlockPos bottomFrontLeft = new BlockPos(bottomFrontLeftX,bottomFrontLeftY,bottomFrontLeftZ);
		BlockPos topBackRight = new BlockPos(topBackRightX,topBackRightY,topBackRightZ);
		int ypos = -1;
		for(int y = bottomFrontLeft.getY(); y < topBackRight.getY()+1; y++) {
			ypos++;
			int xpos = -1;
			for(int x = bottomFrontLeft.getX(); x < topBackRight.getX()+1; x++) {
				xpos++;
				int zpos = -1;
				for(int z = bottomFrontLeft.getZ(); z < topBackRight.getZ()+1; z++) {
					zpos++;
					found = new ArrayList<>();
					BlockState state = context.getSource().getLevel().getBlockState(new BlockPos(x,y,z));
					CompoundNBT blockData = NBTUtil.writeBlockState(state);
					blockData.put("relativeLocation", StructureSaveHandler.writeBlockPos(xpos, ypos, zpos));
					blockDataMap.add(blockData);
					
					String parms = "";
					if(state.getBlock() instanceof DirectionalBlock) {
						parms += getProperty(state, DirectionalBlock.FACING);
					}
					if(state.getBlock() instanceof AbstractFurnaceBlock) {
						parms += getProperty(state, AbstractFurnaceBlock.FACING);
						parms += getProperty(state, AbstractFurnaceBlock.LIT);
					}
					if(state.getBlock() instanceof SnowyDirtBlock) {
						parms += getProperty(state, SnowyDirtBlock.SNOWY);
					}
					if(state.getBlock() instanceof LeverBlock) {
						parms += getProperty(state, LeverBlock.POWERED);
						parms += getProperty(state, LeverBlock.FACE);
						parms += getProperty(state, LeverBlock.FACING);
					}
					if(state.getBlock() instanceof AbstractButtonBlock) {
						parms += getProperty(state, AbstractButtonBlock.POWERED);
						parms += getProperty(state, AbstractButtonBlock.FACE);
						parms += getProperty(state, AbstractButtonBlock.FACING);
					}
					if(state.getBlock() instanceof PressurePlateBlock) {
						parms += getProperty(state, PressurePlateBlock.POWERED);
					}
					if(state.getBlock() instanceof WeightedPressurePlateBlock) {
						parms += getProperty(state, WeightedPressurePlateBlock.POWER);
					}
					if(state.getBlock() instanceof PistonHeadBlock) {
						parms += getProperty(state, PistonHeadBlock.SHORT);
						parms += getProperty(state, PistonHeadBlock.TYPE);
						parms += getProperty(state, PistonHeadBlock.FACING);
					}
					if(state.getBlock() instanceof ChestBlock) {
						parms += getProperty(state, ChestBlock.FACING);
						parms += getProperty(state, ChestBlock.WATERLOGGED);
						parms += getProperty(state, ChestBlock.TYPE);
					}
					if(state.getBlock() instanceof PistonBlock) {
						parms += getProperty(state, PistonBlock.EXTENDED);
						parms += getProperty(state, PistonBlock.FACING);
					}
					if(state.getBlock() instanceof RedstoneWireBlock) {
						parms += getProperty(state, RedstoneWireBlock.EAST);
						parms += getProperty(state, RedstoneWireBlock.NORTH);
						parms += getProperty(state, RedstoneWireBlock.POWER);
						parms += getProperty(state, RedstoneWireBlock.SOUTH);
						parms += getProperty(state, RedstoneWireBlock.WEST);
					}
					if(state.getBlock() instanceof CampfireBlock) {
						parms += getProperty(state, CampfireBlock.FACING);
						parms += getProperty(state, CampfireBlock.LIT);
						parms += getProperty(state, CampfireBlock.SIGNAL_FIRE);
						parms += getProperty(state, CampfireBlock.WATERLOGGED);
					}
					if(state.getBlock() instanceof LeavesBlock) {
						parms += getProperty(state, LeavesBlock.PERSISTENT);
						parms += getProperty(state, LeavesBlock.DISTANCE);
					}
					if(state.getBlock() instanceof ExtendedBerryBushBlock) {
						parms += getProperty(state, ExtendedBerryBushBlock.AGE);
						parms += getProperty(state, ExtendedBerryBushBlock.FULL);
					}
					if(state.getBlock() instanceof NonWaterloggableLanternBlock) {
						parms += getProperty(state, NonWaterloggableLanternBlock.HANGING);
					}
					if(state.getBlock() instanceof StairsBlock) {
						parms += getProperty(state, StairsBlock.WATERLOGGED);
						parms += getProperty(state, StairsBlock.FACING);
						parms += getProperty(state, StairsBlock.HALF);
						parms += getProperty(state, StairsBlock.SHAPE);
					}
					if(state.getBlock() instanceof VerticalOnlySlabBlock) {
						parms += getProperty(state, LOTRBlockStates.VERTICAL_ONLY_SLAB_AXIS);
					}
					if(state.getBlock() instanceof CakeBlock) {
						parms += getProperty(state, CakeBlock.BITES);
					}
					if(state.getBlock() instanceof LOTRSlabBlock) {
						parms += getProperty(state, LOTRBlockStates.SLAB_AXIS);
					}
					if(state.getBlock() instanceof CloverBlock) {
						parms += getProperty(state, LOTRBlockStates.CLOVERS_1_4);
					}
					if(state.getBlock() instanceof SlabBlock) {
						parms += getProperty(state, SlabBlock.WATERLOGGED);
						parms += getProperty(state, SlabBlock.TYPE);
					}
					if(state.getBlock() instanceof WallBlock) {
						parms += getProperty(state, WallBlock.WATERLOGGED);
						parms += getProperty(state, WallBlock.NORTH_WALL);
						parms += getProperty(state, WallBlock.SOUTH_WALL);
						parms += getProperty(state, WallBlock.EAST_WALL);
						parms += getProperty(state, WallBlock.WEST_WALL);
						parms += getProperty(state, WallBlock.UP);
					}
					if(state.getBlock() instanceof TripWireBlock) {
						parms += getProperty(state, TripWireBlock.ATTACHED);
						parms += getProperty(state, TripWireBlock.DISARMED);
						parms += getProperty(state, TripWireBlock.EAST);
						parms += getProperty(state, TripWireBlock.NORTH);
						parms += getProperty(state, TripWireBlock.POWERED);
						parms += getProperty(state, TripWireBlock.SOUTH);
						parms += getProperty(state, TripWireBlock.WEST);
					}
					if(state.getBlock() instanceof RailBlock) {
						parms += getProperty(state, RailBlock.SHAPE);
					}
					if(state.getBlock() instanceof LecternBlock) {
						parms += getProperty(state, LecternBlock.FACING);
						parms += getProperty(state, LecternBlock.HAS_BOOK);
						parms += getProperty(state, LecternBlock.POWERED);
					}
					if(state.getBlock() instanceof MordorRockBlock) {
						parms += getProperty(state, MordorRockBlock.MOSSY);
					}
					if(state.getBlock() instanceof ChainBlock) {
						parms += getProperty(state, ChainBlock.WATERLOGGED);
					}
					if(state.getBlock() instanceof StandingSignBlock) {
						parms += getProperty(state, StandingSignBlock.ROTATION);
					}
					if(state.getBlock() instanceof BannerBlock) {
						parms += getProperty(state, BannerBlock.ROTATION);
					}
					if(state.getBlock() instanceof SkullBlock) {
						parms += getProperty(state, SkullBlock.ROTATION);
					}
					if(state.getBlock() instanceof AnvilBlock) {
						parms += getProperty(state, AnvilBlock.FACING);
					}
					if(state.getBlock() instanceof WallBannerBlock) {
						parms += getProperty(state, WallBannerBlock.FACING);
					}
					if(state.getBlock() instanceof DoubleTorchBlock) {
						parms += getProperty(state, DoubleTorchBlock.HALF);
					}
					if(state.getBlock() instanceof LanternBlock) {
						parms += getProperty(state, LanternBlock.HANGING);
						parms += getProperty(state, LanternBlock.WATERLOGGED);
					}
					if(state.getBlock() instanceof RailBlock) {
						parms += getProperty(state, RailBlock.SHAPE);
					}
					if(state.getBlock() instanceof CauldronBlock) {
						parms += getProperty(state, CauldronBlock.LEVEL);
					}
					if(state.getBlock() instanceof TripWireHookBlock) {
						parms += getProperty(state, TripWireHookBlock.ATTACHED);
						parms += getProperty(state, TripWireHookBlock.FACING);
						parms += getProperty(state, TripWireHookBlock.POWERED);
					}
					if(state.getBlock() instanceof TrapDoorBlock) {
						parms += getProperty(state, TrapDoorBlock.FACING);
						parms += getProperty(state, TrapDoorBlock.HALF);
						parms += getProperty(state, TrapDoorBlock.OPEN);
						parms += getProperty(state, TrapDoorBlock.WATERLOGGED);
						parms += getProperty(state, TrapDoorBlock.POWERED);
					}
					if(state.getBlock() instanceof WallTorchBlock) {
						parms += getProperty(state, WallTorchBlock.FACING);
					}
					if(state.getBlock() instanceof DirectionalMineralBlock) {
						parms += getProperty(state, DirectionalMineralBlock.FACING);
					}
					if(state.getBlock() instanceof BarrelBlock) {
						parms += getProperty(state, BarrelBlock.FACING);
						parms += getProperty(state, BarrelBlock.OPEN);
					}
					if(state.getBlock() instanceof DoublePlantBlock) {
						parms += getProperty(state, DoublePlantBlock.HALF);
					}
					if(state.getBlock() instanceof SnowBlock) {
						parms += getProperty(state, SnowBlock.LAYERS);
					}
					if(state.getBlock() instanceof VineBlock) {
						parms += getProperty(state, VineBlock.EAST);
						parms += getProperty(state, VineBlock.NORTH);
						parms += getProperty(state, VineBlock.SOUTH);
						parms += getProperty(state, VineBlock.UP);
						parms += getProperty(state, VineBlock.WEST);
					}
					if(state.getBlock() instanceof DoorBlock) {
						parms += getProperty(state, DoorBlock.FACING);
						parms += getProperty(state, DoorBlock.HALF);
						parms += getProperty(state, DoorBlock.HINGE);
						parms += getProperty(state, DoorBlock.OPEN);
						parms += getProperty(state, DoorBlock.POWERED);
					}
					if(state.getBlock() instanceof LOTRPillarBlock) {
						parms += getProperty(state, LOTRBlockStates.PILLAR_ABOVE);
						parms += getProperty(state, LOTRBlockStates.PILLAR_BELOW);
					}
					if(state.getBlock() instanceof RotatedPillarBlock) {
						parms += getProperty(state, BlockStateProperties.AXIS);
					}
					if(state.getBlock() instanceof PaneBlock) {
						parms += getProperty(state, PaneBlock.WATERLOGGED);
						parms += getProperty(state, PaneBlock.NORTH);
						parms += getProperty(state, PaneBlock.SOUTH);
						parms += getProperty(state, PaneBlock.EAST);
						parms += getProperty(state, PaneBlock.WEST);
					}
					if(state.getBlock() instanceof FenceBlock) {
						parms += getProperty(state, FenceBlock.WATERLOGGED);
						parms += getProperty(state, FenceBlock.NORTH);
						parms += getProperty(state, FenceBlock.SOUTH);
						parms += getProperty(state, FenceBlock.EAST);
						parms += getProperty(state, FenceBlock.WEST);
					}
					if(state.getBlock() instanceof LadderBlock) {
						parms += getProperty(state, LadderBlock.WATERLOGGED);
						parms += getProperty(state, LadderBlock.FACING);
					}
					if(state.getBlock() instanceof FarmlandBlock) {
						parms += getProperty(state, FarmlandBlock.MOISTURE);
					}
					if(state.getBlock() instanceof StrippableWoodBlock) {
						parms += getProperty(state, StrippableWoodBlock.AXIS);
					}
					if(state.getBlock() instanceof StrippableBranchBlock) {
						parms += getProperty(state, StrippableBranchBlock.EAST);
						parms += getProperty(state, StrippableBranchBlock.NORTH);
						parms += getProperty(state, StrippableBranchBlock.SOUTH);
						parms += getProperty(state, StrippableBranchBlock.WATERLOGGED);
						parms += getProperty(state, StrippableBranchBlock.WEST);
					}
					if(state.getBlock() instanceof VesselDrinkBlock) {
						parms += getProperty(state, VesselDrinkBlock.FACING);
					}
					if(state.getBlock() instanceof CropsBlock) {
						parms += getProperty(state, CropsBlock.AGE);
					}
					if(state.getBlock() instanceof WallSignBlock) {
						parms += getProperty(state, WallSignBlock.FACING);
					}
					if(state.getBlock() instanceof AbstractSignBlock) {
						parms += getProperty(state, AbstractSignBlock.WATERLOGGED);
					}
					if(state.getBlock() instanceof HearthBlock) {
						parms += getProperty(state, HearthBlock.LIT);
					}
					if(state.getBlock() instanceof WattleAndDaubBlock) {
						parms += getProperty(state, WattleAndDaubBlock.CONNECTED);
					}
					if(state.getBlock() instanceof BranchBlock) {
						parms += getProperty(state, BranchBlock.EAST);
						parms += getProperty(state, BranchBlock.NORTH);
						parms += getProperty(state, BranchBlock.SOUTH);
						parms += getProperty(state, BranchBlock.WATERLOGGED);
						parms += getProperty(state, BranchBlock.WEST);
					}
					if(state.getBlock() instanceof BedBlock) {
						parms += getProperty(state, BedBlock.OCCUPIED);
						parms += getProperty(state, BedBlock.PART);
						parms += getProperty(state, BedBlock.FACING);
					}
					if(state.getBlock() instanceof CarvedPumpkinBlock) {
						parms += getProperty(state, CarvedPumpkinBlock.FACING);
					}
					if(state.getBlock() instanceof GateBlock) {
						parms += getProperty(state, GateBlock.DOWN);
						parms += getProperty(state, GateBlock.EAST);
						parms += getProperty(state, GateBlock.FACING);
						parms += getProperty(state, GateBlock.NORTH);
						parms += getProperty(state, GateBlock.OPEN);
						parms += getProperty(state, GateBlock.POWERED);
						parms += getProperty(state, GateBlock.SOUTH);
						parms += getProperty(state, GateBlock.UP);
						parms += getProperty(state, GateBlock.WATERLOGGED);
						parms += getProperty(state, GateBlock.WEST);
					}
					if(state.getBlock() instanceof DripstoneBlock) {
						parms += getProperty(state, DripstoneBlock.WATERLOGGED);
						parms += getProperty(state, DripstoneBlock.DRIPSTONE_TYPE);
					}
					if(state.getBlock() instanceof KegBlock) {
						parms += getProperty(state, KegBlock.OPEN);
						parms += getProperty(state, KegBlock.UP);
						parms += getProperty(state, KegBlock.WATERLOGGED);
						parms += getProperty(state, KegBlock.FACING);
					}
					if(state.getBlock() instanceof ReedsBlock) {
						parms += getProperty(state, ReedsBlock.AGE);
						parms += getProperty(state, ReedsBlock.REEDS_TYPE);
						parms += getProperty(state, ReedsBlock.WATERLOGGED);
					}
					if(state.getBlock() instanceof FenceGateBlock) {
						parms += getProperty(state, FenceGateBlock.FACING);
						parms += getProperty(state, FenceGateBlock.IN_WALL);
						parms += getProperty(state, FenceGateBlock.OPEN);
						parms += getProperty(state, FenceGateBlock.POWERED);
					}
					if(state.getBlock() instanceof CandleBlock) {
						parms += getProperty(state, LOTRBlockStates.CANDLES_1_4);
					}
					if(state.getBlock() instanceof FlowingFluidBlock) {
						parms += getProperty(state, FlowingFluidBlock.LEVEL);
					}
					if(state.getBlock() instanceof BubbleColumnBlock) {
						parms += getProperty(state, BubbleColumnBlock.DRAG_DOWN);
					}
					for(Property<?> key : state.getProperties()) {
						if(!found.contains(key.getName())) {
							System.out.println("ERROR found unused key: "+key.getName()+" : "+getName(state));
						}
					}
					if(state.getBlock().getRegistryName().getPath().equals("air")) {
						continue;
					}
					context.getSource().sendSuccess(new StringTextComponent(getName(state)+"["+parms+"]") , false);
					//System.out.println(getName(state)+"["+parms+"]");
					found.clear();
				}
			}
		}
		CompoundNBT dataCompound = new CompoundNBT();
		dataCompound.put("blockMap", blockDataMap);
		dataCompound.putString("structureName", name);
		StructureSaveHandler.save(name, world, dataCompound);
		return true;
	}
	public static String getProperty(BlockState state, Property<?> prop) {
		String temp = "";
		try {
			if(!found.contains(prop.getName())) {
				temp = "["+prop.getName()+":"+state.getValue(prop).toString()+"]";
				found.add(prop.getName());
			}
		}catch(IllegalArgumentException e) {
			System.out.println("ERROR failed to find key: "+prop.getName()+" : "+getName(state));
		}
		return temp;
	}
	
	public static String getName(BlockState state) {
		return state.getBlock().getRegistryName().getNamespace()+":"+state.getBlock().getRegistryName().getPath();
	}
}
