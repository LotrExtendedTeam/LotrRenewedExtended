package lotr.common.structureV4.scanning;

import java.util.ArrayList;
import java.util.List;

import com.github.maximuslotro.lotroExtended.enums.StructureType;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;

import lotr.common.structureV4.structure.ExtendedConfiguredStructures;
import lotr.common.structureV4.structure.ExtendedStructureStart;
import lotr.common.structureV4.structure.ExtendedSurfaceStructure;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.BlockPosArgument;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.server.ServerWorld;


public class ExtendedStructureSummonv2 {
	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		dispatcher.register(Commands.literal("lotr_structure_summon")
				.requires(context -> context.hasPermission(4))
				.then(Commands.argument("structure", StringArgumentType.string())
						.suggests(SUGGEST_group)
								.executes(context -> spawnStructure(context, StringArgumentType.getString(context, "structure"))
								)
                        ));
	}

	public static SuggestionProvider<CommandSource> SUGGEST_group = (ctx, builder) -> {
        List<String> listgroup = new ArrayList<>(ExtendedConfiguredStructures.CONFIGURED_STRUCTURES.keySet());
        return ISuggestionProvider.suggest(listgroup, builder);
    };

	private static int spawnStructure(CommandContext<CommandSource> context, String structureName) throws CommandSyntaxException {
		if(context.getSource().getEntity()!=null &&context.getSource().getEntity() instanceof PlayerEntity) {
			if(!ExtendedConfiguredStructures.CONFIGURED_STRUCTURES.containsKey(structureName)) {
				context.getSource().sendFailure((new TranslationTextComponent("command.lotrextended.structureSummon.failure")).withStyle((p_211704_1_) -> {
		               return p_211704_1_.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new StringTextComponent("Structure not found")));
	            }));
			}
			Direction dir =((PlayerEntity)context.getSource().getEntity()).getDirection().getOpposite();
			ServerWorld serverlevel = context.getSource().getLevel();
		    StructureFeature<?, ?> structure = ExtendedConfiguredStructures.CONFIGURED_STRUCTURES.get(structureName).get();
		    ChunkGenerator chunkgenerator = serverlevel.getChunkSource().getGenerator();
		    BlockPos pos = context.getSource().getEntity().blockPosition().below();
		    StructureStart<?> structurestart;
		    if(structure.feature instanceof ExtendedSurfaceStructure) {
		    	structurestart = ((ExtendedSurfaceStructure<?>)structure.feature).generateUnckecked(context.getSource().registryAccess(), chunkgenerator, 
		    			chunkgenerator.getBiomeSource(), serverlevel.getStructureManager(), 
		    			serverlevel.getSeed(), pos, 
		    			serverlevel.getBiome(pos), 0, 
		    			new SharedSeedRandom(), DimensionStructuresSettings.DEFAULTS.get(structure.feature), 
		    			structure.config, dir, StructureType.SPAWNED_SINGLE);
		    }
		    else {
		    	context.getSource().sendFailure((new TranslationTextComponent("command.lotrextended.structureSummon.failure")).withStyle((p_211704_1_) -> {
		               return p_211704_1_.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new StringTextComponent("Structure not ExtendedSurfaceStructure")));
	            }));
		    	return 0;
		    }
		    if (!structurestart.isValid()) {
		    	context.getSource().sendFailure((new TranslationTextComponent("command.lotrextended.structureSummon.failure")).withStyle((p_211704_1_) -> {
		               return p_211704_1_.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new StringTextComponent("Structure not valid")));
	            }));
		    	return 0;
		    } else {
		       MutableBoundingBox boundingbox = structurestart.getBoundingBox();
		       //ChunkPos chunkpos = new ChunkPos(SectionPos.blockToSectionCoord(Math.min(boundingbox.x0, boundingbox.x1)), SectionPos.blockToSectionCoord(Math.min(boundingbox.z0, boundingbox.z1)));
		       //ChunkPos chunkpos1 = new ChunkPos(SectionPos.blockToSectionCoord(Math.max(boundingbox.x0, boundingbox.x1)), SectionPos.blockToSectionCoord(Math.max(boundingbox.z0, boundingbox.z1)));
		       //checkLoaded(serverlevel, chunkpos, chunkpos1);
		       if(structure.feature instanceof ExtendedSurfaceStructure) {
			    	((ExtendedStructureStart<?>)structurestart).placeInChunk(serverlevel, serverlevel.structureFeatureManager(), chunkgenerator, serverlevel.getRandom(), boundingbox, new ChunkPos(pos));
			    }
			    else {
			    	context.getSource().sendFailure((new TranslationTextComponent("command.lotrextended.structureSummon.failure")).withStyle((p_211704_1_) -> {
			               return p_211704_1_.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new StringTextComponent("Structure not ExtendedStructureStart")));
		            }));
			    	return 0;
			    }
		       context.getSource().sendSuccess(new TranslationTextComponent("command.lotrextended.structureSummon.success"), false);
		    }
		    return 1;
		}
		else {
			return 0;
		}
	}
	private static void checkLoaded(ServerWorld p_214544_, ChunkPos p_214545_, ChunkPos p_214546_) throws CommandSyntaxException {
	      if (ChunkPos.rangeClosed(p_214545_, p_214546_).filter((p_214542_) -> {
	         return !p_214544_.isLoaded(p_214542_.getWorldPosition());
	      }).findAny().isPresent()) {
	         throw BlockPosArgument.ERROR_NOT_LOADED.create();
	      }
	   }
}
