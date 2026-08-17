package com.github.maximuslotro.lotrrextended.common.commands;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;

import com.github.maximuslotro.lotrrextended.common.datafixers.ExtendedMissingMappings;
import com.github.maximuslotro.lotrrextended.common.entity.special.ExtendedStructureSpawnerEntity;
import com.github.maximuslotro.lotrrextended.common.loot.ExtendedLootTables;
import com.github.maximuslotro.lotrrextended.common.spawners.ExtendedSpawners;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedBookshelfTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedILootTableAccessor;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedLargePotTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedWeaponRackTileEntity;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.suggestion.SuggestionProvider;

import lotr.common.init.ExtendedBlocks;
import lotr.common.init.ExtendedEntities;
import lotr.common.tileentity.KegTileEntity;
import lotr.common.tileentity.PlateTileEntity;
import lotr.common.tileentity.VesselDrinkTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.BlockPosArgument;
import net.minecraft.command.arguments.ResourceLocationArgument;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.ListNBT;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.server.ServerWorld;

public class ExtendedStructureHelperComamnd {
	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		dispatcher.register(Commands.literal("lotrExtendedStructureHelper")
			.requires(context -> context.hasPermission(2))
			.then(Commands.literal("setloottable")
				.then(Commands.argument("table", ResourceLocationArgument.id())
					.suggests(LOOT_TABLES)
					.then(Commands.argument("pos", BlockPosArgument.blockPos())
						.executes(context -> execute(context.getSource(), ResourceLocationArgument.getId(context, "table"), BlockPosArgument.getLoadedBlockPos(context, "pos")))
						)
					.then(Commands.literal("here")
						.executes(context -> execute(context.getSource(), ResourceLocationArgument.getId(context, "table"), new BlockPos(context.getSource().getPosition())))
						)
					)
				)
			.then(Commands.literal("setEntitySpawner")
				.then(Commands.argument("type", ResourceLocationArgument.id())
					.suggests(SPAWNERS)
					.executes(context -> executeEntity(context.getSource(), ResourceLocationArgument.getId(context, "type"), context.getSource().getEntity().blockPosition()))
					)
				)
			.then(Commands.literal("fixstructureids")
				.requires(context -> context.hasPermission(3))
				.executes(context -> updateStructures(context.getSource()))
				)
			);
	}

	public static final SuggestionProvider<CommandSource> LOOT_TABLES = (ctx, builder) -> ISuggestionProvider.suggestResource(ExtendedLootTables.getLootTables(), builder);

	public static final SuggestionProvider<CommandSource> SPAWNERS = (ctx, builder) -> ISuggestionProvider.suggestResource(ExtendedSpawners.getSpawnersCommand(), builder);

	public static int updateStructures(CommandSource source) {
		ServerWorld level = source.getLevel();
		IResourceManager manager = level.getServer().getDataPackRegistries().getResourceManager();
		ExtendedMissingMappings.ensureMappingsCreated();
		Collection<ResourceLocation> structures = manager.listResources("structures", p -> p.endsWith(".nbt"));

		int fixed = 0;

		for (ResourceLocation found : structures) {
			try {
				IResource resource = manager.getResource(found);
				CompoundNBT tag = CompressedStreamTools.readCompressed(resource.getInputStream());
				boolean changed = remapUsingExtendedMappings(tag);
				if (changed) {
					Path out = Paths.get(level.getServer().getServerDirectory().toString(), "world", "generated", found.getNamespace(), found.getPath());
					Files.createDirectories(out.getParent());
					CompressedStreamTools.writeCompressed(tag, Files.newOutputStream(out));
					fixed++;
					source.sendSuccess(new StringTextComponent("Remapped IDs in " + found), false);
				}

			} catch (Exception e) {
				source.sendFailure(new StringTextComponent("Failed remapping IDs in: " + found));
				e.printStackTrace();
			}
		}

		source.sendSuccess(new StringTextComponent("Remapped IDs in " + fixed + " structures."), false);
		return Command.SINGLE_SUCCESS;

	}

	//TODO handle tile-entity remapping, and item remapping if needed
	private static boolean remapUsingExtendedMappings(CompoundNBT root) {
		boolean changed = false;
		if (root.contains("palette", 9)) {
	        ListNBT palette = root.getList("palette", 10);
	        for (int i = 0; i < palette.size(); i++) {
	            CompoundNBT state = palette.getCompound(i);
	            ResourceLocation oldId = new ResourceLocation(state.getString("Name"));
	            if (ExtendedMissingMappings.BLOCKS.containsKey(oldId)) {
					Block newBlock = ExtendedMissingMappings.BLOCKS.get(oldId);
					ResourceLocation newId = newBlock.getRegistryName();
					state.putString("Name", newId.toString());
					changed = true;
				}
	        }
	    }
		if (root.contains("entities", 9)) {
	        ListNBT entities = root.getList("entities", 10);
	        for (int i = 0; i < entities.size(); i++) {
	            CompoundNBT entry = entities.getCompound(i);
	            if (!entry.contains("nbt", 10)) continue;
	            CompoundNBT entityNbt = entry.getCompound("nbt");
	            if (!entityNbt.contains("id", 8)) continue;
	            ResourceLocation oldId = new ResourceLocation(entityNbt.getString("id"));
	            if (ExtendedMissingMappings.ENTITIES.containsKey(oldId)) {
	            	EntityType<?> newEntity = ExtendedMissingMappings.ENTITIES.get(oldId);
					ResourceLocation newId = newEntity.getRegistryName();
					entityNbt.putString("id", newId.toString());
					changed = true;
				}
	        }
	    }
		return changed;
	}

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

		entity.setSpawnerToPreset(resourceLocation);
		source.sendSuccess(new StringTextComponent("Set Spawner Type: "+resourceLocation.toString()), false);
		return Command.SINGLE_SUCCESS;
	}

	public static int execute(CommandSource source, ResourceLocation resourceLocation, BlockPos pos) {
		TileEntity tileentity = source.getLevel().getBlockEntity(pos);
		if (tileentity instanceof KegTileEntity && resourceLocation.toString().contains("kegs")) {
			((KegTileEntity)tileentity).clearContent();
			((ExtendedILootTableAccessor)tileentity).setLootTable(resourceLocation, 0L);
			source.sendSuccess(new StringTextComponent("Set Keg Location: "+resourceLocation.toString()), false);
			return Command.SINGLE_SUCCESS;
		}
		if (tileentity instanceof VesselDrinkTileEntity && resourceLocation.toString().contains("vessels")) {
			((VesselDrinkTileEntity)tileentity).setEmpty();
			((ExtendedILootTableAccessor)tileentity).setLootTable(resourceLocation, 0L);
			source.sendSuccess(new StringTextComponent("Set Vessel Location: "+resourceLocation.toString()), false);
			return Command.SINGLE_SUCCESS;
		}
		if (tileentity instanceof PlateTileEntity && resourceLocation.toString().contains("plates")) {
			((PlateTileEntity)tileentity).setFoodItem(ItemStack.EMPTY);
			((ExtendedILootTableAccessor)tileentity).setLootTable(resourceLocation, 0L);
			source.sendSuccess(new StringTextComponent("Set Plate Location: "+resourceLocation.toString()), false);
			return Command.SINGLE_SUCCESS;
		}
		if (tileentity instanceof ExtendedWeaponRackTileEntity && resourceLocation.toString().contains("weaponracks")) {
			((ExtendedWeaponRackTileEntity)tileentity).setContainedItem(ItemStack.EMPTY);
			((ExtendedWeaponRackTileEntity)tileentity).setLootTable(resourceLocation, 0L);
			source.sendSuccess(new StringTextComponent("Set Weapon Rack Location: "+resourceLocation.toString()), false);
			return Command.SINGLE_SUCCESS;
		}
		if (resourceLocation.toString().contains("bookshelves")) {
			if (tileentity instanceof ExtendedBookshelfTileEntity) {
				((ExtendedBookshelfTileEntity)tileentity).clearContent();
				((ExtendedBookshelfTileEntity)tileentity).setLootTable(resourceLocation, 0L);
				source.sendSuccess(new StringTextComponent("Set Bookshelf Location: "+resourceLocation.toString()), false);
				return Command.SINGLE_SUCCESS;
			}else if(source.getLevel().getBlockState(pos).getBlock()==Blocks.BOOKSHELF) {
				source.getLevel().setBlock(pos, ExtendedBlocks.EXTENDED_BOOKSHELF.get().defaultBlockState(), 3);
				tileentity = source.getLevel().getBlockEntity(pos);
				if (tileentity instanceof ExtendedBookshelfTileEntity) {
					((ExtendedBookshelfTileEntity)tileentity).clearContent();
					((ExtendedBookshelfTileEntity)tileentity).setLootTable(resourceLocation, 0L);
					source.sendSuccess(new StringTextComponent("Set Bookshelf Location: "+resourceLocation.toString()), false);
					return Command.SINGLE_SUCCESS;
				}
			}
		}
		if (tileentity instanceof ExtendedLargePotTileEntity && resourceLocation.toString().contains("pots")) {
			((ExtendedLargePotTileEntity)tileentity).setContainedItem(ItemStack.EMPTY);
			((ExtendedLargePotTileEntity)tileentity).setLootTable(resourceLocation, 0L);
			source.sendSuccess(new StringTextComponent("Set Large Pot Location: "+resourceLocation.toString()), false);
			return Command.SINGLE_SUCCESS;
		}
		if (tileentity instanceof LockableLootTileEntity && resourceLocation.toString().contains("chests")) {
			((LockableLootTileEntity)tileentity).clearContent();
			((LockableLootTileEntity)tileentity).setLootTable(resourceLocation, 0L);
			source.sendSuccess(new StringTextComponent("Set Chest/Barrel Location: "+resourceLocation.toString()), false);
			return Command.SINGLE_SUCCESS;
		}
		if (resourceLocation.toString().contains("armorstand")) {
			AxisAlignedBB area = new AxisAlignedBB(pos);
			List<ArmorStandEntity> armorStands = source.getLevel().getEntitiesOfClass(ArmorStandEntity.class, area);
			if (!armorStands.isEmpty()) {
		        ArmorStandEntity armorStand = armorStands.get(0);
		        armorStand.setItemSlot(EquipmentSlotType.HEAD, ItemStack.EMPTY);
		        armorStand.setItemSlot(EquipmentSlotType.CHEST, ItemStack.EMPTY);
		        armorStand.setItemSlot(EquipmentSlotType.LEGS, ItemStack.EMPTY);
		        armorStand.setItemSlot(EquipmentSlotType.FEET, ItemStack.EMPTY);
		        CompoundNBT forgeData = armorStand.getPersistentData();
		        forgeData.putString("StandLootTable", resourceLocation.toString());
		        source.sendSuccess(new StringTextComponent("Set ArmorStand Location: " + resourceLocation.toString()), false);
		        return Command.SINGLE_SUCCESS;
		    } else {
		        source.sendFailure(new StringTextComponent("No armor stand found at the specified block position!"));
		        return Command.SINGLE_SUCCESS;
		    }
		}
		source.sendFailure(new StringTextComponent("Destination is not instanceof supported objects or tried setting invalid loot-table for object!"));
		return Command.SINGLE_SUCCESS;
	}

}
