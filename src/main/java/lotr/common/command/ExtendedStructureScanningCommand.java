package lotr.common.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import lotr.common.init.ExtendedItems;
import lotr.common.structure.ScanHandler;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.BlockPosArgument;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.StringTextComponent;


public class ExtendedStructureScanningCommand extends LOTRBaseCommand {
	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		dispatcher.register(Commands.literal("lotr_structure_scan")
				.requires(context -> context.hasPermission(4))
				.then(Commands.literal("scan")
						.then(Commands.argument("name", StringArgumentType.word())
								.executes(context -> scanStructure(context))))
				.then(Commands.literal("pos1")
						.then(Commands.argument("corner", BlockPosArgument.blockPos())
								.executes(context -> setCorner(context, true))))
				.then(Commands.literal("pos2")
						.then(Commands.argument("corner", BlockPosArgument.blockPos())
								.executes(context -> setCorner(context, false))))
				.then(Commands.literal("wand")
						.executes(context -> giveWand(context))));
	}

	private static int scanStructure(CommandContext<CommandSource> context) {
		if(ScanHandler.scanStructure(context)){
			context.getSource().sendSuccess(new StringTextComponent("Scanned Successfully") , true);
		}
		else {
			context.getSource().sendSuccess(new StringTextComponent("Scan Failed") , true);
		}
		return 0;
	}
	private static int setCorner(CommandContext<CommandSource> context, boolean one) throws CommandSyntaxException {
		if(one){
			ScanHandler.setPos1(BlockPosArgument.getLoadedBlockPos(context, "corner"));
			context.getSource().sendSuccess(new StringTextComponent("Set Pos1") , true);
		}
		else {
			ScanHandler.setPos2(BlockPosArgument.getLoadedBlockPos(context, "corner"));
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
