package com.github.maximuslotro.lotrrextended.common.commands;

import java.util.UUID;

import com.github.maximuslotro.lotrrextended.common.stats.ExtendedStats;
import com.github.maximuslotro.lotrrextended.common.utils.ExtendedReflectionHelper;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.suggestion.SuggestionProvider;

import lotr.common.command.LOTRBaseCommand;
import lotr.common.data.ExtendedFellowshipDataModule;
import lotr.common.fellowship.ExtendedFellowship;
import lotr.common.util.UsernameHelper;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public class ExtendedFellowshipCommand extends LOTRBaseCommand {
	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		dispatcher.register(Commands.literal("fellowship").requires(context -> context.hasPermission(0))
				.then(Commands.literal("create").requires(context -> context.hasPermission(0))
						.then(Commands.argument("Name", StringArgumentType.string())
								.executes(context -> createFellowship(context.getSource(), (PlayerEntity) context.getSource().getEntity(), StringArgumentType.getString(context, "Name"), false))
								)
						)
				.then(Commands.literal("acceptInvite").requires(context -> context.hasPermission(0))
						.then(Commands.argument("fellowship", StringArgumentType.greedyString())
								.suggests(SUGGEST_INVITES_USER)
								.executes(context -> processInvite(context.getSource(), (PlayerEntity) context.getSource().getEntity(), StringArgumentType.getString(context, "fellowship"), true))
								)
						)
				.then(Commands.literal("declineInvite").requires(context -> context.hasPermission(0))
						.then(Commands.argument("fellowship", StringArgumentType.greedyString())
								.suggests(SUGGEST_INVITES_USER)
								.executes(context -> processInvite(context.getSource(), (PlayerEntity) context.getSource().getEntity(), StringArgumentType.getString(context, "fellowship"), false))
								)
						)
				.then(Commands.literal("viewInfo").requires(context -> context.hasPermission(0))
						.then(Commands.argument("fellowship", StringArgumentType.greedyString())
								.suggests(SUGGEST_FELLOWSHIP_USER)
								.executes(context -> displayInfo(context.getSource(), (PlayerEntity) context.getSource().getEntity(), StringArgumentType.getString(context, "fellowship")))
								)
						)
				.then(Commands.literal("modify").requires(context -> context.hasPermission(0))
						.then(Commands.argument("Name", StringArgumentType.string())
								.suggests(SUGGEST_FELLOWSHIP_USER)
								.then(Commands.literal("rename")
										.then(Commands.argument("NewName", StringArgumentType.string())
												.executes(context -> renameFellowship(context.getSource(), (PlayerEntity) context.getSource().getEntity(), StringArgumentType.getString(context, "Name"), StringArgumentType.getString(context, "NewName")))
												)
										)
								.then(Commands.literal("invite")
										.then(Commands.argument("playerToAdd", EntityArgument.player())
												.executes(context -> invite(context.getSource(), (PlayerEntity) context.getSource().getEntity(), StringArgumentType.getString(context, "Name"), EntityArgument.getPlayer(context, "playerToAdd")))
												)
										)
								.then(Commands.literal("remove")
										.then(Commands.argument("playerToRemove", EntityArgument.player())
												.executes(context -> removePlayer(context.getSource(), (PlayerEntity) context.getSource().getEntity(), StringArgumentType.getString(context, "Name"), EntityArgument.getPlayer(context, "playerToRemove")))
												)
										)
								.then(Commands.literal("transfer")
										.then(Commands.argument("newOwner", EntityArgument.player())
												.executes(context -> setOwner(context.getSource(), (PlayerEntity) context.getSource().getEntity(), StringArgumentType.getString(context, "Name"), EntityArgument.getPlayer(context, "newOwner")))
												)
										)
								.then(Commands.literal("disband")
										.executes(context -> disband(context.getSource(), (PlayerEntity) context.getSource().getEntity(), StringArgumentType.getString(context, "Name")))
										)
								.then(Commands.literal("icon")
										.executes(context -> icon(context.getSource(), (PlayerEntity) context.getSource().getEntity(), StringArgumentType.getString(context, "Name")))
										)
								.then(Commands.literal("prevent-pvp")
										.then(Commands.argument("toggle", BoolArgumentType.bool())
												.executes(context -> pvp(context.getSource(), (PlayerEntity) context.getSource().getEntity(), StringArgumentType.getString(context, "Name"), BoolArgumentType.getBool(context, "toggle")))
												)
										)
								.then(Commands.literal("prevent-hired-ff")
										.then(Commands.argument("toggle", BoolArgumentType.bool())
												.executes(context -> hiredUnits(context.getSource(), (PlayerEntity) context.getSource().getEntity(), StringArgumentType.getString(context, "Name"), BoolArgumentType.getBool(context, "toggle")))
												)
										)
								.then(Commands.literal("map-show")
										.then(Commands.argument("toggle", BoolArgumentType.bool())
												.executes(context -> mapLoc(context.getSource(), (PlayerEntity) context.getSource().getEntity(), StringArgumentType.getString(context, "Name"), BoolArgumentType.getBool(context, "toggle")))
												)
										)
								.then(Commands.literal("op")
										.then(Commands.argument("addingPlayer", EntityArgument.player())
												.executes(context -> admin(context.getSource(), (PlayerEntity) context.getSource().getEntity(), StringArgumentType.getString(context, "Name"), EntityArgument.getPlayer(context, "addingPlayer"), true))
												)
										)
								.then(Commands.literal("deop")
										.then(Commands.argument("removingPlayer", EntityArgument.player())
												.executes(context -> admin(context.getSource(), (PlayerEntity) context.getSource().getEntity(), StringArgumentType.getString(context, "Name"), EntityArgument.getPlayer(context, "removingPlayer"), false))
												)
										)
								.then(Commands.literal("leave")
										.executes(context -> leave(context.getSource(), (PlayerEntity) context.getSource().getEntity(), StringArgumentType.getString(context, "Name")))
										)
								)
						)
				.then(Commands.literal("admin").requires(context -> context.hasPermission(2))
						.then(Commands.argument("player", EntityArgument.player())
								.then(Commands.literal("create")
										.then(Commands.argument("Name", StringArgumentType.string())
												.executes(context -> createFellowship(context.getSource(), EntityArgument.getPlayer(context, "player"), StringArgumentType.getString(context, "Name"), true))
												)
										)
								.then(Commands.literal("option")
										.then(Commands.argument("Name", StringArgumentType.string())
												.suggests(SUGGEST_FELLOWSHIP_PLAYER)
												.then(Commands.literal("rename")
														.then(Commands.argument("NewName", StringArgumentType.string())
																.executes(context -> renameFellowship(context.getSource(), EntityArgument.getPlayer(context, "player"), StringArgumentType.getString(context, "Name"), StringArgumentType.getString(context, "NewName")))
																)
														)
												.then(Commands.literal("add")
														.then(Commands.argument("playerToAdd", EntityArgument.player())
																.executes(context -> add(context.getSource(), EntityArgument.getPlayer(context, "player"), StringArgumentType.getString(context, "Name"), EntityArgument.getPlayer(context, "playerToAdd")))
																)
														)
												.then(Commands.literal("invite")
														.then(Commands.argument("playerToAdd", EntityArgument.player())
																.executes(context -> invite(context.getSource(), EntityArgument.getPlayer(context, "player"), StringArgumentType.getString(context, "Name"), EntityArgument.getPlayer(context, "playerToAdd")))
																)
														)
												.then(Commands.literal("remove")
														.then(Commands.argument("playerToRemove", EntityArgument.player())
																.executes(context -> removePlayer(context.getSource(), EntityArgument.getPlayer(context, "player"), StringArgumentType.getString(context, "Name"), EntityArgument.getPlayer(context, "playerToRemove")))
																)
														)
												.then(Commands.literal("transfer")
														.then(Commands.argument("newOwner", EntityArgument.player())
																.executes(context -> setOwner(context.getSource(), EntityArgument.getPlayer(context, "player"), StringArgumentType.getString(context, "Name"), EntityArgument.getPlayer(context, "newOwner")))
																)
														)
												.then(Commands.literal("disband")
														.executes(context -> disband(context.getSource(), EntityArgument.getPlayer(context, "player"), StringArgumentType.getString(context, "Name")))
														)
												.then(Commands.literal("icon")
														.executes(context -> icon(context.getSource(), EntityArgument.getPlayer(context, "player"), StringArgumentType.getString(context, "Name")))
														)
												.then(Commands.literal("prevent-pvp")
														.then(Commands.argument("toggle", BoolArgumentType.bool())
																.executes(context -> pvp(context.getSource(), EntityArgument.getPlayer(context, "player"), StringArgumentType.getString(context, "Name"), BoolArgumentType.getBool(context, "toggle")))
																)
														)
												.then(Commands.literal("prevent-hired-ff")
														.then(Commands.argument("toggle", BoolArgumentType.bool())
																.executes(context -> hiredUnits(context.getSource(), EntityArgument.getPlayer(context, "player"), StringArgumentType.getString(context, "Name"), BoolArgumentType.getBool(context, "toggle")))
																)
														)
												.then(Commands.literal("map-show")
														.then(Commands.argument("toggle", BoolArgumentType.bool())
																.executes(context -> mapLoc(context.getSource(), EntityArgument.getPlayer(context, "player"), StringArgumentType.getString(context, "Name"), BoolArgumentType.getBool(context, "toggle")))
																)
														)
												.then(Commands.literal("op")
														.then(Commands.argument("addingPlayer", EntityArgument.player())
																.executes(context -> admin(context.getSource(), EntityArgument.getPlayer(context, "player"), StringArgumentType.getString(context, "Name"), EntityArgument.getPlayer(context, "addingPlayer"), true))
																)
														)
												.then(Commands.literal("deop")
														.then(Commands.argument("removingPlayer", EntityArgument.player())
																.executes(context -> admin(context.getSource(), EntityArgument.getPlayer(context, "player"), StringArgumentType.getString(context, "Name"), EntityArgument.getPlayer(context, "removingPlayer"), false))
																)
														)
												.then(Commands.literal("leave")
														.executes(context -> leave(context.getSource(), EntityArgument.getPlayer(context, "player"), StringArgumentType.getString(context, "Name")))
														)
												)
										)
								)				
						)
				);
	}
	
	public static final SuggestionProvider<CommandSource> SUGGEST_FELLOWSHIP_USER = (ctx, builder) -> {
		ExtendedFellowshipDataModule fellowshipData = ExtendedReflectionHelper.getFellowshipData(getLevelData().getData(
				((PlayerEntity) ctx.getSource().getEntity()).level, ((PlayerEntity) ctx.getSource().getEntity()).getUUID()));
		return ISuggestionProvider.suggest(fellowshipData.listAllFellowshipNames(), builder);
	};
    
	public static final SuggestionProvider<CommandSource> SUGGEST_INVITES_USER = (ctx, builder) -> {
		ExtendedFellowshipDataModule fellowshipData = ExtendedReflectionHelper.getFellowshipData(getLevelData().getData(
				((PlayerEntity) ctx.getSource().getEntity()).level, ((PlayerEntity) ctx.getSource().getEntity()).getUUID()));
		return ISuggestionProvider.suggest(fellowshipData.listAllInviteNames(), builder);
	};
    
	public static final SuggestionProvider<CommandSource> SUGGEST_FELLOWSHIP_PLAYER = (ctx, builder) -> {
		ExtendedFellowshipDataModule fellowshipData = ExtendedReflectionHelper.getFellowshipData(getLevelData().getData(
				EntityArgument.getPlayer(ctx, "player").level, EntityArgument.getPlayer(ctx, "player").getUUID()));
		return ISuggestionProvider.suggest(fellowshipData.listAllFellowshipNames(), builder);
	};

	private static int createFellowship(CommandSource source, PlayerEntity player, String fellowshipname, boolean adminOverride) {
		ExtendedFellowshipDataModule fellowshipData = ExtendedReflectionHelper.getFellowshipData(getLevelData().getData(player.level, player.getUUID()));
		ExtendedFellowship fellowship = fellowshipData.getFellowshipByName(fellowshipname);
        if (fellowship == null) {
        	if(fellowshipData.createFellowship(fellowshipname, adminOverride)) {
    			player.awardStat(ExtendedStats.FELLOWSHIP_CREATE);
        	}
        	return 0;
        }
		source.sendSuccess(new TranslationTextComponent("command.lotrextended.fellowship.error.exists", fellowshipname).withStyle(TextFormatting.RED), true);
		return 0;
	}
	private static int renameFellowship(CommandSource source, PlayerEntity player, String fellowshipname, String fellowshipNewName) {
		ExtendedFellowshipDataModule fellowshipData = ExtendedReflectionHelper.getFellowshipData(getLevelData().getData(player.level, player.getUUID()));
		ExtendedFellowship fellowship = fellowshipData.getFellowshipByName(fellowshipname);
        if (fellowship != null && fellowship.hasEditPowers(player.getUUID())) {
        	fellowshipData.renameFellowship(fellowship, fellowshipNewName);
        	source.sendSuccess(new TranslationTextComponent("command.lotrextended.fellowship.renamed", fellowshipname, fellowshipNewName), true);
        	return 0;
        }
        source.sendSuccess(new TranslationTextComponent("command.lotrextended.fellowship.error").withStyle(TextFormatting.RED), true);
		return 0;
	}
	private static int pvp(CommandSource source, PlayerEntity player, String fellowshipname, boolean toggle) {
		ExtendedFellowshipDataModule fellowshipData = ExtendedReflectionHelper.getFellowshipData(getLevelData().getData(player.level, player.getUUID()));
		ExtendedFellowship fellowship = fellowshipData.getFellowshipByName(fellowshipname);
        if (fellowship != null && fellowship.hasEditPowers(player.getUUID())) {
        	fellowshipData.setFellowshipPreventPVP(fellowship, toggle);
        	TextComponent text = toggle ? new TranslationTextComponent("gui.lotrextended.base.disabled") : new TranslationTextComponent("gui.lotrextended.base.enabled");
        	source.sendSuccess(text.append(" ").append(new TranslationTextComponent("command.lotrextended.fellowship.pvp")), true);
        	return 0;
        }
        source.sendSuccess(new TranslationTextComponent("command.lotrextended.fellowship.error").withStyle(TextFormatting.RED), true);
		return 0;
	}
	private static int hiredUnits(CommandSource source, PlayerEntity player, String fellowshipname, boolean toggle) {
		ExtendedFellowshipDataModule fellowshipData = ExtendedReflectionHelper.getFellowshipData(getLevelData().getData(player.level, player.getUUID()));
		ExtendedFellowship fellowship = fellowshipData.getFellowshipByName(fellowshipname);
        if (fellowship != null && fellowship.hasEditPowers(player.getUUID())) {
        	fellowshipData.setFellowshipPreventHiredFF(fellowship, toggle);
        	TextComponent text = toggle ? new TranslationTextComponent("gui.lotrextended.base.disabled") : new TranslationTextComponent("gui.lotrextended.base.enabled");
        	source.sendSuccess(text.append(" ").append(new TranslationTextComponent("command.lotrextended.fellowship.hiredunitff")).withStyle(TextFormatting.RED), true);
        	return 0;
        }
        source.sendSuccess(new TranslationTextComponent("command.lotrextended.fellowship.error").withStyle(TextFormatting.RED), true);
		return 0;
	}
	private static int mapLoc(CommandSource source, PlayerEntity player, String fellowshipname, boolean toggle) {
		ExtendedFellowshipDataModule fellowshipData = ExtendedReflectionHelper.getFellowshipData(getLevelData().getData(player.level, player.getUUID()));
		ExtendedFellowship fellowship = fellowshipData.getFellowshipByName(fellowshipname);
        if (fellowship != null && fellowship.hasEditPowers(player.getUUID())) {
        	fellowshipData.setFellowshipShowMapLocations(fellowship, toggle);
        	TextComponent text = toggle ? new TranslationTextComponent("gui.lotrextended.base.shown") : new TranslationTextComponent("gui.lotrextended.base.hidden");
        	source.sendSuccess(new TranslationTextComponent("command.lotrextended.fellowship.maplocation").append(" ").append(text), true);
        	return 0;
        }
        source.sendSuccess(new TranslationTextComponent("command.lotrextended.fellowship.error").withStyle(TextFormatting.RED), true);
		return 0;
	}
	private static int removePlayer(CommandSource source, PlayerEntity player, String fellowshipname, PlayerEntity playerToRemove) {
		ExtendedFellowshipDataModule fellowshipData = ExtendedReflectionHelper.getFellowshipData(getLevelData().getData(player.level, player.getUUID()));
		ExtendedFellowship fellowship = fellowshipData.getFellowshipByName(fellowshipname);
        if (fellowship != null && fellowship.hasEditPowers(player.getUUID())) {
        	fellowshipData.removePlayer(fellowship, playerToRemove.getUUID());
        	source.sendSuccess(new TranslationTextComponent("command.lotrextended.fellowship.player.removed", playerToRemove.getDisplayName().getString()), true);
        	return 0;
        }
        source.sendSuccess(new TranslationTextComponent("command.lotrextended.fellowship.error").withStyle(TextFormatting.RED), true);
		return 0;
	}
	private static int setOwner(CommandSource source, PlayerEntity player, String fellowshipname, PlayerEntity newOwner) {
		ExtendedFellowshipDataModule fellowshipData = ExtendedReflectionHelper.getFellowshipData(getLevelData().getData(player.level, player.getUUID()));
		ExtendedFellowship fellowship = fellowshipData.getFellowshipByName(fellowshipname);
        if (fellowship != null && fellowship.isFellowshipOwner(player.getUUID())) {
        	fellowshipData.setOwner(fellowship, newOwner.getUUID());
        	source.sendSuccess(new TranslationTextComponent("command.lotrextended.fellowship.player.setowner", newOwner.getDisplayName().getString()), true);
        	return 0;
        }
        source.sendSuccess(new TranslationTextComponent("command.lotrextended.fellowship.error").withStyle(TextFormatting.RED), true);
		return 0;
	}
	private static int invite(CommandSource source, PlayerEntity player, String fellowshipname, PlayerEntity newMember) {
		ExtendedFellowshipDataModule fellowshipData = ExtendedReflectionHelper.getFellowshipData(getLevelData().getData(player.level, player.getUUID()));
		ExtendedFellowship fellowship = fellowshipData.getFellowshipByName(fellowshipname);
        if (fellowship != null && fellowship.hasEditPowers(player.getUUID())) {
        	ExtendedFellowshipDataModule fellowshipData2 = ExtendedReflectionHelper.getFellowshipData(getLevelData().getData(newMember.level, newMember.getUUID()));
        	fellowshipData2.addInvitePlayer(fellowship, player.getUUID());
        	source.sendSuccess(new TranslationTextComponent("command.lotrextended.fellowship.player.invited", newMember.getDisplayName().getString()), true);
        	return 0;
        }
        source.sendSuccess(new TranslationTextComponent("command.lotrextended.fellowship.error").withStyle(TextFormatting.RED), true);
		return 0;
	}
	private static int add(CommandSource source, PlayerEntity player, String fellowshipname, PlayerEntity newMember) {
		ExtendedFellowshipDataModule fellowshipData = ExtendedReflectionHelper.getFellowshipData(getLevelData().getData(player.level, player.getUUID()));
		ExtendedFellowship fellowship = fellowshipData.getFellowshipByName(fellowshipname);
        if (fellowship != null && fellowship.isFellowshipOwner(player.getUUID())) {
        	fellowshipData.addPlayer(fellowship, newMember.getUUID());
        	source.sendSuccess(new TranslationTextComponent("command.lotrextended.fellowship.player.added", newMember.getDisplayName().getString()), true);
        	return 0;
        }
        source.sendSuccess(new TranslationTextComponent("command.lotrextended.fellowship.error").withStyle(TextFormatting.RED), true);
		return 0;
	}
	private static int admin(CommandSource source, PlayerEntity player, String fellowshipname, PlayerEntity changing, boolean toggle) {
		ExtendedFellowshipDataModule fellowshipData = ExtendedReflectionHelper.getFellowshipData(getLevelData().getData(player.level, player.getUUID()));
		ExtendedFellowship fellowship = fellowshipData.getFellowshipByName(fellowshipname);
        if (fellowship != null && fellowship.isFellowshipOwner(player.getUUID())) {
        	if(toggle) {
        		fellowshipData.addFellowshipAdmin(fellowship, changing.getUUID());
        		source.sendSuccess(new TranslationTextComponent("command.lotrextended.fellowship.admin.added", changing.getName().getString()), true);
            	return 0;
        	}
        	fellowshipData.removeFellowshipAdmin(fellowship, changing.getUUID());
        	source.sendSuccess(new TranslationTextComponent("command.lotrextended.fellowship.admin.removed", changing.getName().getString()), true);
        	return 0;
        }
        source.sendSuccess(new TranslationTextComponent("command.lotrextended.fellowship.error").withStyle(TextFormatting.RED), true);
		return 0;
	}
	private static int leave(CommandSource source, PlayerEntity player, String fellowshipname) {
		ExtendedFellowshipDataModule fellowshipData = ExtendedReflectionHelper.getFellowshipData(getLevelData().getData(player.level, player.getUUID()));
		ExtendedFellowship fellowship = fellowshipData.getFellowshipByName(fellowshipname);
        if (fellowship != null && fellowship.isFellowshipMember(player.getUUID())) {
        	fellowshipData.leaveFellowship(fellowship);
        	source.sendSuccess(new TranslationTextComponent("command.lotrextended.fellowship.left", fellowshipname), true);
        	return 0;
        }
        source.sendSuccess(new TranslationTextComponent("command.lotrextended.fellowship.error").withStyle(TextFormatting.RED), true);
		return 0;
	}
	private static int icon(CommandSource source, PlayerEntity player, String fellowshipname) {
		ExtendedFellowshipDataModule fellowshipData = ExtendedReflectionHelper.getFellowshipData(getLevelData().getData(player.level, player.getUUID()));
		ExtendedFellowship fellowship = fellowshipData.getFellowshipByName(fellowshipname);
        if (fellowship != null && fellowship.hasEditPowers(player.getUUID())) {
        	if(!player.getMainHandItem().isEmpty()) {
        		fellowshipData.setIcon(fellowship, player.getUUID(), player.getMainHandItem());
        		source.sendSuccess(new TranslationTextComponent("command.lotrextended.fellowship.icon.set"), true);
        		return 0;
        	}
        	fellowshipData.setIcon(fellowship, player.getUUID(), ItemStack.EMPTY);
        	source.sendSuccess(new TranslationTextComponent("command.lotrextended.fellowship.icon.removed"), true);
        	return 0;
        }
        source.sendSuccess(new TranslationTextComponent("command.lotrextended.fellowship.error").withStyle(TextFormatting.RED), true);
		return 0;
	}
	private static int disband(CommandSource source, PlayerEntity player, String fellowshipname) {
		ExtendedFellowshipDataModule fellowshipData = ExtendedReflectionHelper.getFellowshipData(getLevelData().getData(player.level, player.getUUID()));
		ExtendedFellowship fellowship = fellowshipData.getFellowshipByName(fellowshipname);
        if (fellowship != null && fellowship.isFellowshipOwner(player.getUUID())) {
        	if(fellowshipData.disbandFellowship(fellowship)) {
        		player.awardStat(ExtendedStats.FELLOWSHIP_DISBAND);
            	source.sendSuccess(new TranslationTextComponent("command.lotrextended.fellowship.disband"), true);
            	return 0;
        	}
        	source.sendFailure(new TranslationTextComponent("command.lotrextended.fellowship.disband.error"));
        	return 0;
        }
        source.sendSuccess(new TranslationTextComponent("command.lotrextended.fellowship.error").withStyle(TextFormatting.RED), true);
		return 0;
	}
	private static int processInvite(CommandSource source, PlayerEntity player, String fellowshipname, boolean accept) {
		ExtendedFellowshipDataModule fellowshipData = ExtendedReflectionHelper.getFellowshipData(getLevelData().getData(player.level, player.getUUID()));
		ExtendedFellowship fellowship = fellowshipData.getFellowshipByInviteName(fellowshipname);
		if (fellowship != null) {
			if(accept) {
				fellowshipData.acceptInvitePlayer(fellowship);
	        	return 0;
			}
			fellowshipData.removeInvitePlayer(fellowship, true);
        	return 0;
        }
		source.sendSuccess(new TranslationTextComponent("command.lotrextended.fellowship.acceptinvite.error").withStyle(TextFormatting.RED), true);
        return 0;
	}
	private static int displayInfo(CommandSource source, PlayerEntity player, String fellowshipname) {
		ExtendedFellowshipDataModule fellowshipData = ExtendedReflectionHelper.getFellowshipData(getLevelData().getData(player.level, player.getUUID()));
		ExtendedFellowship fellowship = fellowshipData.getFellowshipByName(fellowshipname);
		if (fellowship != null) {
			//Send Name
			source.sendSuccess(new TranslationTextComponent("command.lotrextended.fellowship.data.name").withStyle(TextFormatting.GREEN)
					.append(new StringTextComponent(fellowship.getFellowshipName()).withStyle(TextFormatting.GOLD)), true);
			//Send PVP and HYPVP data
			source.sendSuccess(new TranslationTextComponent("command.lotrextended.fellowship.data.pvp").withStyle(TextFormatting.GREEN)
					.append(new StringTextComponent(String.valueOf(fellowship.isPreventPVP())).withStyle(TextFormatting.GOLD))
					.append(new StringTextComponent(", ").withStyle(TextFormatting.WHITE))
					.append(new TranslationTextComponent("command.lotrextended.fellowship.data.hupvp").withStyle(TextFormatting.GREEN))
					.append(new StringTextComponent(String.valueOf(fellowship.isPreventHiredFF())).withStyle(TextFormatting.GOLD)), true);
			//Send MAPLOC and ICON data
			source.sendSuccess(new TranslationTextComponent("command.lotrextended.fellowship.data.maploc").withStyle(TextFormatting.GREEN)
					.append(new StringTextComponent(String.valueOf(fellowship.isHideMapLocation())).withStyle(TextFormatting.GOLD))
					.append(new StringTextComponent(", ").withStyle(TextFormatting.WHITE))
					.append(new TranslationTextComponent("command.lotrextended.fellowship.data.icon").withStyle(TextFormatting.GREEN))
					.append(new StringTextComponent(fellowship.getIcon().isEmpty() ? "NOTSET" : fellowship.getIcon().getItem().getRegistryName().getPath()).withStyle(TextFormatting.GOLD)), true);
			//Send Owner
			source.sendSuccess(new TranslationTextComponent("command.lotrextended.fellowship.data.owner").withStyle(TextFormatting.GREEN)
					.append(new StringTextComponent(UsernameHelper.getLastKnownUsernameOrFallback(fellowship.getOwnerUUID())).withStyle(TextFormatting.GOLD)), true);
			//Send Administrators
			TextComponent admins = new TranslationTextComponent("command.lotrextended.fellowship.data.admins");
			admins.withStyle(TextFormatting.GREEN);
			for(UUID admin :fellowship.getFellowshipAdmins()) {
				admins.append(new StringTextComponent(UsernameHelper.getLastKnownUsernameOrFallback(admin)+", ").withStyle(TextFormatting.GOLD));
			}
			source.sendSuccess(admins, true);
			//Send all players
			TextComponent members = new TranslationTextComponent("command.lotrextended.fellowship.data.members");
			members.withStyle(TextFormatting.GREEN);
			for(UUID member :fellowship.getAllFellowshipMembers()) {
				members.append(new StringTextComponent(UsernameHelper.getLastKnownUsernameOrFallback(member)+", ").withStyle(TextFormatting.GOLD));
			}
			source.sendSuccess(members, true);
        	return 0;
        }
		source.sendSuccess(new TranslationTextComponent("command.lotrextended.fellowship.error.noexists").withStyle(TextFormatting.RED), true);
        return 0;
	}
}
