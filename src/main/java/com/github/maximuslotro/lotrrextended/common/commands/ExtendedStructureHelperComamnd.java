package com.github.maximuslotro.lotrrextended.common.commands;

import java.util.List;

import com.github.maximuslotro.lotrrextended.common.entity.special.ExtendedStructureSpawnerEntity;
import com.github.maximuslotro.lotrrextended.common.loot.ExtendedLootTables;
import com.github.maximuslotro.lotrrextended.common.spawners.ExtendedSpawners;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedWeaponRackTileEntity;
import com.github.maximuslotro.lotrrextended.common.utils.ExtendedReflectionHelper;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.suggestion.SuggestionProvider;

import lotr.common.init.ExtendedEntities;
import lotr.common.tileentity.KegTileEntity;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.BlockPosArgument;
import net.minecraft.command.arguments.ResourceLocationArgument;
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
								.then(Commands.argument("location", ResourceLocationArgument.id())
										.suggests(LOCATIONS)
										.executes(context -> execute(context.getSource(), ResourceLocationArgument.getId(context, "location"), BlockPosArgument.getLoadedBlockPos(context, "pos")))
										)
								)
						)
				.then(Commands.literal("setEntitySpawner")
						.then(Commands.argument("location", ResourceLocationArgument.id())
								.suggests(SPAWNERS)
								.executes(context -> executeEntity(context.getSource(), ResourceLocationArgument.getId(context, "location"), context.getSource().getEntity().blockPosition()))
								)
						)
				);
	}

	public static final SuggestionProvider<CommandSource> LOCATIONS = (ctx, builder) -> {
        return ISuggestionProvider.suggestResource(ExtendedLootTables.getLocations(), builder);
    };

	public static final SuggestionProvider<CommandSource> SPAWNERS = (ctx, builder) -> {
        return ISuggestionProvider.suggestResource(ExtendedSpawners.all(), builder);
    };

    public static int executeEntity(CommandSource source, ResourceLocation resourceLocation, BlockPos pos) {
    	List<ExtendedStructureSpawnerEntity> entities = source.getLevel()
    			.getEntities(ExtendedEntities.STRUCTURE_ENTITY_SPAWNER.get(), source.getEntity().getBoundingBox(), (entitiyCheck) -> {
    		return entitiyCheck instanceof  ExtendedStructureSpawnerEntity ? true : false;
    	});
    	ExtendedStructureSpawnerEntity entity;
    	if(!entities.isEmpty()) {
    		entity = entities.get(0);
    	}else {
        	source.sendFailure(new StringTextComponent("Destanation is not a Entity Spawner!"));
        	return Command.SINGLE_SUCCESS;
    	}

    	entity.setSpawnerToDefaultPreset(resourceLocation, false);
    	source.sendSuccess(new StringTextComponent("Set Spawner Type: "+resourceLocation.toString()), false);
    	return Command.SINGLE_SUCCESS;
    }

	public static int execute(CommandSource source, ResourceLocation resourceLocation, BlockPos pos) {
		TileEntity tileentity = source.getLevel().getBlockEntity(pos);
		if (tileentity instanceof KegTileEntity) {
			ExtendedReflectionHelper.setKegloottable((KegTileEntity)tileentity, resourceLocation, 0L);
			source.sendSuccess(new StringTextComponent("Set Keg Location: "+resourceLocation.toString()), false);
			return Command.SINGLE_SUCCESS;
		}
		if (tileentity instanceof ExtendedWeaponRackTileEntity) {
			((ExtendedWeaponRackTileEntity)tileentity).setLootTable(resourceLocation, 0L);
			source.sendSuccess(new StringTextComponent("Set Weapon Rack Location: "+resourceLocation.toString()), false);
			return Command.SINGLE_SUCCESS;
		}
		if (tileentity instanceof LockableLootTileEntity) {
			((LockableLootTileEntity)tileentity).setLootTable(resourceLocation, 0L);
			source.sendSuccess(new StringTextComponent("Set Chest/Barrel Location: "+resourceLocation.toString()), false);
			return Command.SINGLE_SUCCESS;
		}
		source.sendFailure(new StringTextComponent("Destanation is not instanceof supported tile entities!"));
        return Command.SINGLE_SUCCESS;
    }

}
