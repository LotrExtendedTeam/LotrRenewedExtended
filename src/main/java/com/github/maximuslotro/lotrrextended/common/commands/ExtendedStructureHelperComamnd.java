package com.github.maximuslotro.lotrrextended.common.commands;

import java.util.List;

import com.github.maximuslotro.lotrrextended.common.entity.special.ExtendedStructureSpawnerEntity;
import com.github.maximuslotro.lotrrextended.common.loot.ExtendedLootTables;
import com.github.maximuslotro.lotrrextended.common.spawners.ExtendedSpawners;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedBookshelfTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedLargePotTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedWeaponRackTileEntity;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.suggestion.SuggestionProvider;

import lotr.common.init.ExtendedEntities;
import lotr.common.tileentity.KegTileEntity;
import lotr.common.tileentity.PlateTileEntity;
import lotr.common.tileentity.VesselDrinkTileEntity;
import lotr.common.util.tileentity.ExtendedLootTableAccessor;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.BlockPosArgument;
import net.minecraft.command.arguments.ResourceLocationArgument;
import net.minecraft.item.ItemStack;
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
						.then(Commands.argument("location", ResourceLocationArgument.id())
								.suggests(LOCATIONS)
								.then(Commands.argument("pos", BlockPosArgument.blockPos())
										.executes(context -> execute(context.getSource(), ResourceLocationArgument.getId(context, "location"), BlockPosArgument.getLoadedBlockPos(context, "pos")))
										)
								.then(Commands.literal("here")
										.executes(context -> execute(context.getSource(), ResourceLocationArgument.getId(context, "location"), new BlockPos(context.getSource().getPosition())))
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

	public static final SuggestionProvider<CommandSource> LOCATIONS = (ctx, builder) -> ISuggestionProvider.suggestResource(ExtendedLootTables.getLocations(), builder);

	public static final SuggestionProvider<CommandSource> SPAWNERS = (ctx, builder) -> ISuggestionProvider.suggestResource(ExtendedSpawners.getSpawners(), builder);

    public static int executeEntity(CommandSource source, ResourceLocation resourceLocation, BlockPos pos) {
    	List<ExtendedStructureSpawnerEntity> entities = source.getLevel()
    			.getEntities(ExtendedEntities.STRUCTURE_ENTITY_SPAWNER.get(), source.getEntity().getBoundingBox(), (entitiyCheck) -> entitiyCheck instanceof ExtendedStructureSpawnerEntity);
    	ExtendedStructureSpawnerEntity entity;
    	if(!entities.isEmpty()) {
    		entity = entities.get(0);
    	}else {
        	source.sendFailure(new StringTextComponent("Destination is not a Entity Spawner!"));
        	return Command.SINGLE_SUCCESS;
    	}

    	entity.setSpawnerToDefaultPreset(resourceLocation, false);
    	source.sendSuccess(new StringTextComponent("Set Spawner Type: "+resourceLocation.toString()), false);
    	return Command.SINGLE_SUCCESS;
    }

	public static int execute(CommandSource source, ResourceLocation resourceLocation, BlockPos pos) {
		TileEntity tileentity = source.getLevel().getBlockEntity(pos);
		if (tileentity instanceof KegTileEntity) {
			((KegTileEntity)tileentity).clearContent();
			((ExtendedLootTableAccessor)tileentity).setLootTable(resourceLocation, 0L);
			source.sendSuccess(new StringTextComponent("Set Keg Location: "+resourceLocation.toString()), false);
			return Command.SINGLE_SUCCESS;
		}
		if (tileentity instanceof VesselDrinkTileEntity) {
			((VesselDrinkTileEntity)tileentity).setEmpty();
			((ExtendedLootTableAccessor)tileentity).setLootTable(resourceLocation, 0L);
			source.sendSuccess(new StringTextComponent("Set Vessel Location: "+resourceLocation.toString()), false);
			return Command.SINGLE_SUCCESS;
		}
		if (tileentity instanceof PlateTileEntity) {
			((PlateTileEntity)tileentity).setFoodItem(ItemStack.EMPTY);
			((ExtendedLootTableAccessor)tileentity).setLootTable(resourceLocation, 0L);
			source.sendSuccess(new StringTextComponent("Set Plate Location: "+resourceLocation.toString()), false);
			return Command.SINGLE_SUCCESS;
		}
		if (tileentity instanceof ExtendedWeaponRackTileEntity) {
			((ExtendedWeaponRackTileEntity)tileentity).setContainedItem(ItemStack.EMPTY);
			((ExtendedWeaponRackTileEntity)tileentity).setLootTable(resourceLocation, 0L);
			source.sendSuccess(new StringTextComponent("Set Weapon Rack Location: "+resourceLocation.toString()), false);
			return Command.SINGLE_SUCCESS;
		}
		if (tileentity instanceof ExtendedBookshelfTileEntity) {
			((ExtendedBookshelfTileEntity)tileentity).clearContent();
			((ExtendedBookshelfTileEntity)tileentity).setLootTable(resourceLocation, 0L);
			source.sendSuccess(new StringTextComponent("Set Bookshelf Location: "+resourceLocation.toString()), false);
			return Command.SINGLE_SUCCESS;
		}
		if (tileentity instanceof ExtendedLargePotTileEntity) {
			((ExtendedLargePotTileEntity)tileentity).setContainedItem(ItemStack.EMPTY);
			((ExtendedLargePotTileEntity)tileentity).setLootTable(resourceLocation, 0L);
			source.sendSuccess(new StringTextComponent("Set Large Pot Location: "+resourceLocation.toString()), false);
			return Command.SINGLE_SUCCESS;
		}
		if (tileentity instanceof LockableLootTileEntity) {
			((LockableLootTileEntity)tileentity).clearContent();
			((LockableLootTileEntity)tileentity).setLootTable(resourceLocation, 0L);
			source.sendSuccess(new StringTextComponent("Set Chest/Barrel Location: "+resourceLocation.toString()), false);
			return Command.SINGLE_SUCCESS;
		}
		source.sendFailure(new StringTextComponent("Destination is not instanceof supported tile entities!"));
        return Command.SINGLE_SUCCESS;
    }

}
