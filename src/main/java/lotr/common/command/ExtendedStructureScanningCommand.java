package lotr.common.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import lotr.common.init.ExtendedItems;
import lotr.common.structure.StructureFile;
import lotr.common.structure.StructureFileHandler;
import lotr.common.structure.scanning.StructureScanner;
import lotr.common.structure.util.RelativePosition;
import lotr.common.structure.util.StructureStrings;
import net.minecraft.block.BlockState;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.BlockPosArgument;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;


public class ExtendedStructureScanningCommand extends LOTRBaseCommand {
	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		dispatcher.register(Commands.literal("lotr_structure")
				.requires(context -> context.hasPermission(4))
				.then(Commands.literal("scan")
						.then(Commands.argument("name", StringArgumentType.word())
								.executes(context -> scanStructure(context))))
				.then(Commands.literal("pos1")
						.then(Commands.argument("corner", BlockPosArgument.blockPos())
								.executes(context -> setCorner(context, true))))
				.then(Commands.literal("origin")
						.then(Commands.argument("origin", BlockPosArgument.blockPos())
								.executes(context -> setOrigin(context))))
				.then(Commands.literal("pos2")
						.then(Commands.argument("corner", BlockPosArgument.blockPos())
								.executes(context -> setCorner(context, false))))
				.then(Commands.literal("wand")
						.executes(context -> giveWand(context)))
				.then(Commands.literal("direction")
						.then(Commands.literal("NORTH")
								.executes(context -> setDirection(context, "NORTH")))
						.then(Commands.literal("SOUTH")
								.executes(context -> setDirection(context, "SOUTH")))
						.then(Commands.literal("WEST")
								.executes(context -> setDirection(context, "WEST")))
						.then(Commands.literal("EAST")
								.executes(context -> setDirection(context, "EAST")))
						.then(Commands.literal("NODIRECTION")
								.executes(context -> setDirection(context, "UP"))))
				.then(Commands.literal("load")
						.then(Commands.argument("name", StringArgumentType.word())
								.executes(context -> loadStructure(context)))));
	}

	private static int setDirection(CommandContext<CommandSource> context, String direction) {
		Direction dir = Direction.byName(direction);
		if(dir!=null){
			StructureScanner.setFacingDirection(dir);
			if(dir==Direction.UP) {
				context.getSource().sendSuccess(new StringTextComponent("Set facing direction to directionless") , true);
			}
			else {
				context.getSource().sendSuccess(new StringTextComponent("Set facing direction to: "+dir.toString()) , true);
			}
		}
		else {
			context.getSource().sendSuccess(new StringTextComponent("Invalid direction: "+direction) , true);
		}
		return 0;
	}

	private static int scanStructure(CommandContext<CommandSource> context) {
		if(StructureScanner.scanStructure(context)){
			context.getSource().sendSuccess(new StringTextComponent("Scanned Successfully") , true);
		}
		else {
			context.getSource().sendSuccess(new StringTextComponent("Scan Failed") , true);
		}
		return 0;
	}
	private static int loadStructure(CommandContext<CommandSource> context) {
		StructureFile file = StructureFileHandler.getStructureFile(StringArgumentType.getString(context, "name"), context.getSource().getLevel().getServer());
		if(file!=null){
			BlockPos pos = context.getSource().getEntity().blockPosition();
			for (int i = 0; i < file.getLayers().size(); i++) {
				CompoundNBT nbt = file.getLayers().getCompound(i);
				RelativePosition rpos = StructureFile.readBlockPos(nbt.getCompound(StructureStrings.relativeLocationHolder));
				CompoundNBT blockData = nbt.getCompound(StructureStrings.blockMapHolder);
				BlockState state = NBTUtil.readBlockState(blockData);
				BlockPos bPos = new BlockPos(pos.getX()+rpos.getX(), pos.getY()+rpos.getY(), pos.getZ()+rpos.getZ());
				context.getSource().getLevel().setBlockAndUpdate(bPos, state);
			}
			context.getSource().sendSuccess(new StringTextComponent("Loaded Successfully"), false);
		}
		else {
			context.getSource().sendSuccess(new StringTextComponent("Loading Failed"), false);
		}
		return 0;
	}
	private static int setOrigin(CommandContext<CommandSource> context) throws CommandSyntaxException {
		StructureScanner.setOrigin(BlockPosArgument.getLoadedBlockPos(context, "origin"));
		context.getSource().sendSuccess(new StringTextComponent("Set Origin") , true);
		return 0;
	}
	private static int setCorner(CommandContext<CommandSource> context, boolean one) throws CommandSyntaxException {
		if(one){
			StructureScanner.setPos1(BlockPosArgument.getLoadedBlockPos(context, "corner"));
			context.getSource().sendSuccess(new StringTextComponent("Set Pos1") , true);
		}
		else {
			StructureScanner.setPos2(BlockPosArgument.getLoadedBlockPos(context, "corner"));
			context.getSource().sendSuccess(new StringTextComponent("Set Pos2") , true);
		}
		return 0;
	}
	private static int giveWand(CommandContext<CommandSource> context) {
		if(context.getSource().getEntity()!=null &&context.getSource().getEntity() instanceof PlayerEntity) {
			((PlayerEntity) context.getSource().getEntity()).inventory.add(new ItemStack(ExtendedItems.ANGMAR_AXE.get()));;
		}
		return 0;
	}
}
