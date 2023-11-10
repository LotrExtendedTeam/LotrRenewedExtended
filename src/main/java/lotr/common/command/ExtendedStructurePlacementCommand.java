package lotr.common.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;

import lotr.common.structure.StructureLoader;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.StringTextComponent;


public class ExtendedStructurePlacementCommand extends LOTRBaseCommand {
	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		dispatcher.register(Commands.literal("lotr_structure_spawn")
				.requires(context -> context.hasPermission(4))
				.then(Commands.literal("load")
						.then(Commands.argument("name", StringArgumentType.word())
								.executes(context -> scanStructure(context)))));
	}

	private static int scanStructure(CommandContext<CommandSource> context) {
		if(StructureLoader.loadStructure(context)){
			context.getSource().sendSuccess(new StringTextComponent("Loaded Successfully"), false);
		}
		else {
			context.getSource().sendSuccess(new StringTextComponent("Loading Failed"), false);
		}
		return 0;
	}
}
