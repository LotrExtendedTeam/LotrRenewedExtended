package lotr.common.command;

import com.github.maximuslotro.lotroExtended.LOTRHooks;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;

import lotr.common.command.data.FellowshipDataModule;
import lotr.common.fellowship.ExtendedFellowship;
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
						.then(Commands.literal("invite")
								.then(Commands.argument("player", EntityArgument.player())
										.then(Commands.argument("Name", StringArgumentType.string())
												.then(Commands.argument("playerToAdd", EntityArgument.player())
														.executes(context -> invite(context.getSource(), EntityArgument.getPlayer(context, "player"), StringArgumentType.getString(context, "Name"), EntityArgument.getPlayer(context, "playerToAdd")))
														)
												)
										)
								)
						.then(Commands.literal("remove")
								.then(Commands.argument("player", EntityArgument.player())
										.then(Commands.argument("Name", StringArgumentType.string())
												.then(Commands.argument("playerToRemove", EntityArgument.player())
														.executes(context -> removePlayer(context.getSource(), EntityArgument.getPlayer(context, "player"), StringArgumentType.getString(context, "Name"), EntityArgument.getPlayer(context, "playerToRemove")))
														)
												)
										)
								)
						.then(Commands.literal("transfer")
								.then(Commands.argument("player", EntityArgument.player())
										.then(Commands.argument("Name", StringArgumentType.string())
												.then(Commands.argument("newOwner", EntityArgument.player())
														.executes(context -> setOwner(context.getSource(), EntityArgument.getPlayer(context, "player"), StringArgumentType.getString(context, "Name"), EntityArgument.getPlayer(context, "newOwner")))
														)
												)
										)
								)
						.then(Commands.literal("disband")
								.then(Commands.argument("player", EntityArgument.player())
										.then(Commands.argument("Name", StringArgumentType.string())
												.executes(context -> disband(context.getSource(), EntityArgument.getPlayer(context, "player"), StringArgumentType.getString(context, "Name")))
												)
										)
								)
						.then(Commands.literal("icon")
								.then(Commands.argument("player", EntityArgument.player())
										.then(Commands.argument("Name", StringArgumentType.string())
												.then(Commands.argument("NewName", StringArgumentType.string())
														.executes(context -> renameFellowship(context.getSource(), EntityArgument.getPlayer(context, "player"), StringArgumentType.getString(context, "Name"), StringArgumentType.getString(context, "NewName")))
														)
												)
										)
								)
						.then(Commands.literal("prevent-pvp")
								.then(Commands.argument("player", EntityArgument.player())
										.then(Commands.argument("Name", StringArgumentType.string())
												.then(Commands.argument("toggle", BoolArgumentType.bool())
														.executes(context -> pvp(context.getSource(), EntityArgument.getPlayer(context, "player"), StringArgumentType.getString(context, "Name"), BoolArgumentType.getBool(context, "toggle")))
														)
												)
										)
								)
						.then(Commands.literal("prevent-hired-ff")
								.then(Commands.argument("player", EntityArgument.player())
										.then(Commands.argument("Name", StringArgumentType.string())
												.then(Commands.argument("toggle", BoolArgumentType.bool())
														.executes(context -> hiredUnits(context.getSource(), EntityArgument.getPlayer(context, "player"), StringArgumentType.getString(context, "Name"), BoolArgumentType.getBool(context, "toggle")))
														)
												)
										)
								)
						.then(Commands.literal("map-show")
								.then(Commands.argument("player", EntityArgument.player())
										.then(Commands.argument("Name", StringArgumentType.string())
												.then(Commands.argument("toggle", BoolArgumentType.bool())
														.executes(context -> mapLoc(context.getSource(), EntityArgument.getPlayer(context, "player"), StringArgumentType.getString(context, "Name"), BoolArgumentType.getBool(context, "toggle")))
														)
												)
										)
								)
						.then(Commands.literal("op")
								.then(Commands.argument("player", EntityArgument.player())
										.then(Commands.argument("Name", StringArgumentType.string())
												.then(Commands.argument("addingPlayer", EntityArgument.player())
														.executes(context -> admin(context.getSource(), EntityArgument.getPlayer(context, "player"), StringArgumentType.getString(context, "Name"), EntityArgument.getPlayer(context, "addingPlayer"), true))
														)
												)
										)
								)
						.then(Commands.literal("deop")
								.then(Commands.argument("player", EntityArgument.player())
										.then(Commands.argument("Name", StringArgumentType.string())
												.then(Commands.argument("removingPlayer", EntityArgument.player())
														.executes(context -> admin(context.getSource(), EntityArgument.getPlayer(context, "player"), StringArgumentType.getString(context, "Name"), EntityArgument.getPlayer(context, "removingPlayer"), false))
														)
												)
										)
								)
						.then(Commands.literal("leave")
								.then(Commands.argument("player", EntityArgument.player())
										.then(Commands.argument("Name", StringArgumentType.string())
												.executes(context -> leave(context.getSource(), EntityArgument.getPlayer(context, "player"), StringArgumentType.getString(context, "Name")))
												)
										)
								)
						)
				);
	}
	private static int createFellowship(CommandSource source, PlayerEntity player, String fellowshipname) {
		FellowshipDataModule fellowshipData = LOTRHooks.getFellowshipData(getLevelData().getData(player.level, player.getUUID()));
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
		FellowshipDataModule fellowshipData = LOTRHooks.getFellowshipData(getLevelData().getData(player.level, player.getUUID()));
		ExtendedFellowship fellowship = fellowshipData.getFellowshipByName(fellowshipname);
        if (fellowship != null && fellowship.hasEditPowers(player.getUUID())) {
        	fellowshipData.renameFellowship(fellowship, fellowshipNewName);
        	source.sendSuccess(new StringTextComponent("Successfully renamed fellowship: "+fellowshipname+ " to "+fellowshipNewName), true);
        	return 0;
        }
        source.sendSuccess(new StringTextComponent("Fellowship does not exist or you are missing permissions!"), true);
		return 0;
	}
	private static int pvp(CommandSource source, PlayerEntity player, String fellowshipname, boolean toggle) {
		FellowshipDataModule fellowshipData = LOTRHooks.getFellowshipData(getLevelData().getData(player.level, player.getUUID()));
		ExtendedFellowship fellowship = fellowshipData.getFellowshipByName(fellowshipname);
        if (fellowship != null && fellowship.hasEditPowers(player.getUUID())) {
        	fellowshipData.setFellowshipPreventPVP(fellowship, toggle);
        	source.sendSuccess(new StringTextComponent((toggle ? "Disabled":"Allowed")+" pvp"), true);
        	return 0;
        }
        source.sendSuccess(new StringTextComponent("Fellowship does not exist or you are missing permissions!"), true);
		return 0;
	}
	private static int hiredUnits(CommandSource source, PlayerEntity player, String fellowshipname, boolean toggle) {
		FellowshipDataModule fellowshipData = LOTRHooks.getFellowshipData(getLevelData().getData(player.level, player.getUUID()));
		ExtendedFellowship fellowship = fellowshipData.getFellowshipByName(fellowshipname);
        if (fellowship != null && fellowship.hasEditPowers(player.getUUID())) {
        	fellowshipData.setFellowshipPreventHiredFF(fellowship, toggle);
        	source.sendSuccess(new StringTextComponent((toggle ? "Disabled":"Allowed")+" hired unit friendly fire"), true);
        	return 0;
        }
        source.sendSuccess(new StringTextComponent("Fellowship does not exist or you are missing permissions!"), true);
		return 0;
	}
	private static int mapLoc(CommandSource source, PlayerEntity player, String fellowshipname, boolean toggle) {
		FellowshipDataModule fellowshipData = LOTRHooks.getFellowshipData(getLevelData().getData(player.level, player.getUUID()));
		ExtendedFellowship fellowship = fellowshipData.getFellowshipByName(fellowshipname);
        if (fellowship != null && fellowship.hasEditPowers(player.getUUID())) {
        	fellowshipData.setFellowshipShowMapLocations(fellowship, toggle);
        	source.sendSuccess(new StringTextComponent("Fellowship map location is now "+(toggle ? "shown":"hidden")), true);
        	return 0;
        }
        source.sendSuccess(new StringTextComponent("Fellowship does not exist or you are missing permissions!"), true);
		return 0;
	}
	private static int removePlayer(CommandSource source, PlayerEntity player, String fellowshipname, PlayerEntity playerToRemove) {
		FellowshipDataModule fellowshipData = LOTRHooks.getFellowshipData(getLevelData().getData(player.level, player.getUUID()));
		ExtendedFellowship fellowship = fellowshipData.getFellowshipByName(fellowshipname);
        if (fellowship != null && fellowship.hasEditPowers(player.getUUID())) {
        	fellowshipData.removePlayer(fellowship, playerToRemove.getUUID());
        	source.sendSuccess(new StringTextComponent("Removed "+playerToRemove.getDisplayName().getString()+" from the fellowship"), true);
        	return 0;
        }
        source.sendSuccess(new StringTextComponent("Fellowship does not exist or you are missing permissions!"), true);
		return 0;
	}
	private static int setOwner(CommandSource source, PlayerEntity player, String fellowshipname, PlayerEntity newOwner) {
		FellowshipDataModule fellowshipData = LOTRHooks.getFellowshipData(getLevelData().getData(player.level, player.getUUID()));
		ExtendedFellowship fellowship = fellowshipData.getFellowshipByName(fellowshipname);
        if (fellowship != null && fellowship.isFellowshipOwner(player.getUUID())) {
        	fellowshipData.setOwner(fellowship, newOwner.getUUID());
        	source.sendSuccess(new StringTextComponent("Made "+newOwner.getDisplayName().getString()+" the new owner of the fellowship"), true);
        	return 0;
        }
        source.sendSuccess(new StringTextComponent("Fellowship does not exist or you are missing permissions!"), true);
		return 0;
	}
	private static int invite(CommandSource source, PlayerEntity player, String fellowshipname, PlayerEntity newMember) {
		FellowshipDataModule fellowshipData = LOTRHooks.getFellowshipData(getLevelData().getData(player.level, player.getUUID()));
		ExtendedFellowship fellowship = fellowshipData.getFellowshipByName(fellowshipname);
        if (fellowship != null && fellowship.isFellowshipOwner(player.getUUID())) {
        	fellowshipData.addPlayer(fellowship, newMember.getUUID());
        	source.sendSuccess(new StringTextComponent("Added "+newMember.getDisplayName().getString()+" to the fellowship"), true);
        	return 0;
        }
        source.sendSuccess(new StringTextComponent("Fellowship does not exist or you are missing permissions!"), true);
		return 0;
	}
	private static int admin(CommandSource source, PlayerEntity player, String fellowshipname, PlayerEntity changing, boolean toggle) {
		FellowshipDataModule fellowshipData = LOTRHooks.getFellowshipData(getLevelData().getData(player.level, player.getUUID()));
		ExtendedFellowship fellowship = fellowshipData.getFellowshipByName(fellowshipname);
        if (fellowship != null && fellowship.isFellowshipOwner(player.getUUID())) {
        	if(toggle) {
        		fellowshipData.addFellowshipAdmin(fellowship, changing.getUUID());
        		source.sendSuccess(new StringTextComponent("Added fellowship admin: "+changing.getName().getString()), true);
            	return 0;
        	}
        	fellowshipData.removeFellowshipAdmin(fellowship, changing.getUUID());
        	source.sendSuccess(new StringTextComponent("Removed fellowship admin: "+changing.getName().getString()), true);
        	return 0;
        }
        source.sendSuccess(new StringTextComponent("Fellowship does not exist or you are missing permissions!"), true);
		return 0;
	}
	private static int leave(CommandSource source, PlayerEntity player, String fellowshipname) {
		FellowshipDataModule fellowshipData = LOTRHooks.getFellowshipData(getLevelData().getData(player.level, player.getUUID()));
		ExtendedFellowship fellowship = fellowshipData.getFellowshipByName(fellowshipname);
        if (fellowship != null && fellowship.isFellowshipMember(player.getUUID())) {
        	fellowshipData.leaveFellowship(fellowship);
        	source.sendSuccess(new StringTextComponent("You have left "+fellowshipname), true);
        	return 0;
        }
        source.sendSuccess(new StringTextComponent("Fellowship does not exist or you are not a member!"), true);
		return 0;
	}
	private static int disband(CommandSource source, PlayerEntity player, String fellowshipname) {
		FellowshipDataModule fellowshipData = LOTRHooks.getFellowshipData(getLevelData().getData(player.level, player.getUUID()));
		ExtendedFellowship fellowship = fellowshipData.getFellowshipByName(fellowshipname);
        if (fellowship != null && fellowship.isFellowshipOwner(player.getUUID())) {
        	fellowshipData.disbandFellowship(fellowship);
        	source.sendSuccess(new StringTextComponent("Disbanded the fellowship"), true);
        	return 0;
        }
        source.sendSuccess(new StringTextComponent("Fellowship does not exist or you are missing permissions!"), true);
		return 0;
	}
}
