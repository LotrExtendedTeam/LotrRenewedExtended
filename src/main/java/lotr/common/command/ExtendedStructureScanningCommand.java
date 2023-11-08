package lotr.common.command;

import com.mojang.brigadier.CommandDispatcher;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.StringTextComponent;


public class ExtendedStructureScanningCommand extends LOTRBaseCommand {
	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		dispatcher.register(Commands.literal("lotr_structure_scan").requires(context -> context.hasPermission(4))
				.executes(context -> scanStructure(context.getSource())));
	}
	private static int scanStructure(CommandSource source) {
		source.sendSuccess(new StringTextComponent("test") , true);
		return 0;
	}
}
