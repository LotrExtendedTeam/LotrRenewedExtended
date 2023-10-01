package lotr.common.command;

import com.github.maximuslotro.lotroExtended.LOTRHooks;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;

import lotr.common.command.data.FellowshipDataModule;
import lotr.common.command.fellowship.ExtendedFellowship;
import lotr.common.data.LOTRLevelData;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;

public class FellowshipCommand extends LOTRBaseCommand {
	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		dispatcher.register(Commands.literal("fellowship").requires(context -> context.hasPermission(0))
				.then(Commands.literal("create").requires(context -> context.hasPermission(2))
						.then(Commands.argument("player", EntityArgument.player())
								.then(Commands.argument("Name", StringArgumentType.string())
										.executes(context -> createFellowship(context.getSource(), EntityArgument.getPlayer(context, "player"), StringArgumentType.getString(context, "Name")))
										)
								)
						)
				.then(Commands.literal("option")
						.then(Commands.literal("rename")
								.then(Commands.argument("player", EntityArgument.player())
										.then(Commands.argument("Name", StringArgumentType.string())
												.then(Commands.argument("NewName", StringArgumentType.string())
														.executes(context -> renameFellowship(context.getSource(), EntityArgument.getPlayer(context, "player"), StringArgumentType.getString(context, "Name"), StringArgumentType.getString(context, "NewName")))
														)
												)
										)
								)
						)
				);
	}
	private static int createFellowship(CommandSource source, PlayerEntity player, String fellowshipname) {
		FellowshipDataModule fellowshipData = LOTRHooks.getFellowshipData(LOTRLevelData.serverInstance().getData(player.level, player.getUUID()));
		ExtendedFellowship fellowship = fellowshipData.getFellowshipByName(fellowshipname);
        if (fellowship == null) {
        	fellowshipData.createFellowship(fellowshipname);
        	source.sendSuccess(new StringTextComponent("Successfully created fellowship: "+fellowshipname), true);
        	return 0;
        } 
		source.sendSuccess(new StringTextComponent("Fellowship by the name ["+fellowshipname + "] already exists") , true);
		return 0;
	}
	private static int renameFellowship(CommandSource source, PlayerEntity player, String fellowshipname, String fellowshipNewName) {
		FellowshipDataModule fellowshipData = LOTRHooks.getFellowshipData(LOTRLevelData.serverInstance().getData(player.level, player.getUUID()));
		ExtendedFellowship fellowship = fellowshipData.getFellowshipByName(fellowshipname);
        if (fellowship != null && fellowship.isOwner(player.getUUID())) {
        	fellowshipData.renameFellowship(fellowship, fellowshipNewName);
        	source.sendSuccess(new StringTextComponent("Successfully renamed fellowship: "+fellowshipname+ " to "+fellowshipNewName), true);
        	return 0;
        }
		return 0;
	}
}
