package com.github.maximuslotro.lotroExtended.commands;

import java.util.Random;

import com.github.maximuslotro.lotroExtended.hooks.MixinHooks;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;

import lotr.common.tileentity.KegTileEntity;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.BlockPosArgument;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;

public class ExtendedStructureHelperComamnd {
	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		dispatcher.register(Commands.literal("lotrExtendedStructureHelper")
				.requires(context -> context.hasPermission(2))
				.then(Commands.literal("setloottable")
						.then(Commands.argument("pos", BlockPosArgument.blockPos())
						.then(Commands.argument("location", StringArgumentType.greedyString())
								.executes(context -> execute(context.getSource(),StringArgumentType.getString(context,"location"), BlockPosArgument.getLoadedBlockPos(context, "pos")))))));
	}

	public static int execute(CommandSource source, String location, BlockPos pos) {
		TileEntity tileentity = source.getLevel().getBlockEntity(pos);
		if (tileentity instanceof LockableLootTileEntity) {
			ResourceLocation loc = new ResourceLocation(location);
			((LockableLootTileEntity)tileentity).lootTable = loc;
			source.sendSuccess(new StringTextComponent("Set Chest/Barrel Location: "+loc.toString()), false);
			return Command.SINGLE_SUCCESS;
		}
		if (tileentity instanceof KegTileEntity) {
			ResourceLocation loc = new ResourceLocation(location);
			MixinHooks.setKegloottable((KegTileEntity)tileentity, loc, new Random().nextLong());
			source.sendSuccess(new StringTextComponent("Set Keg Location: "+loc.toString()), false);
			return Command.SINGLE_SUCCESS;
		}
		source.sendFailure(new StringTextComponent("Destanation is not instanceof supported tile entities!"));
        return Command.SINGLE_SUCCESS;
    }

}
