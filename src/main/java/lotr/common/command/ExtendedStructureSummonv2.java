package lotr.common.command;

import java.util.ArrayList;
import java.util.List;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;

import lotr.common.structure.StructureV4.ExtendedStructures;
import lotr.common.structure.StructureV4.ExtendedSurfaceStructure;
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
import net.minecraft.util.math.SectionPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.server.ServerWorld;


public class ExtendedStructureSummonv2 extends LOTRBaseCommand {
	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		dispatcher.register(Commands.literal("lotr_structure_summon")
				.requires(context -> context.hasPermission(4))
				.then(Commands.argument("structure", StringArgumentType.string())
						.suggests(SUGGEST_group)
								.executes(context -> spawnStructure(context, StringArgumentType.getString(context, "structure"))
								)
                        ));
	}

	public static final SuggestionProvider<CommandSource> SUGGEST_group = (ctx, builder) -> {
        List<String> listgroup = new ArrayList<>(ExtendedStructures.CONFIGURED_STRUCTURES.keySet());
        return ISuggestionProvider.suggest(listgroup, builder);
    };

	private static int spawnStructure(CommandContext<CommandSource> context, String structureName) throws CommandSyntaxException {
		if(context.getSource().getEntity()!=null &&context.getSource().getEntity() instanceof PlayerEntity) {
			if(!ExtendedStructures.CONFIGURED_STRUCTURES.containsKey(structureName)) {
				context.getSource().sendFailure(new TranslationTextComponent("command.lotrextended.structureSummon.failure"));
			}
			Direction dir =((PlayerEntity)context.getSource().getEntity()).getDirection();
			ServerWorld serverlevel = context.getSource().getLevel();
		    StructureFeature<?, ?> structure = ExtendedStructures.CONFIGURED_STRUCTURES.get(structureName).get();
		    ChunkGenerator chunkgenerator = serverlevel.getChunkSource().getGenerator();
		    BlockPos pos = context.getSource().getEntity().blockPosition();
		    StructureStart<?> structurestart;
		    if(structure.feature instanceof ExtendedSurfaceStructure) {
		    	structurestart = ((ExtendedSurfaceStructure<?>)structure.feature).generate(context.getSource().registryAccess(), chunkgenerator, 
		    			chunkgenerator.getBiomeSource(), serverlevel.getStructureManager(), 
		    			serverlevel.getSeed(), new ChunkPos(pos), 
		    			serverlevel.getBiome(pos), 0, 
		    			new SharedSeedRandom(), ExtendedStructures.SETTINGS.get(structure.feature), 
		    			structure.config, dir);
		    }
		    else {
		    	structurestart = structure.generate(context.getSource().registryAccess(), chunkgenerator, 
						chunkgenerator.getBiomeSource(), serverlevel.getStructureManager(), 
						serverlevel.getSeed(), new ChunkPos(pos), 
						serverlevel.getBiome(pos), 0, 
						ExtendedStructures.SETTINGS.get(structure.feature));
		    }
		    if (!structurestart.isValid()) {
		    	context.getSource().sendFailure(new TranslationTextComponent("command.lotrextended.structureSummon.failure"));
		    	return 1;
		    } else {
		       MutableBoundingBox boundingbox = structurestart.getBoundingBox();
		       ChunkPos chunkpos = new ChunkPos(SectionPos.blockToSectionCoord(Math.min(boundingbox.x0, boundingbox.x1)), SectionPos.blockToSectionCoord(Math.min(boundingbox.z0, boundingbox.z1)));
		       ChunkPos chunkpos1 = new ChunkPos(SectionPos.blockToSectionCoord(Math.max(boundingbox.x0, boundingbox.x1)), SectionPos.blockToSectionCoord(Math.max(boundingbox.z0, boundingbox.z1)));
		       checkLoaded(serverlevel, chunkpos, chunkpos1);
		       ChunkPos.rangeClosed(chunkpos, chunkpos1).forEach((p_269763_) -> {
		    	   MutableBoundingBox box = new MutableBoundingBox(p_269763_.getMinBlockX(), 0, p_269763_.getMinBlockZ(), p_269763_.getMaxBlockX(), serverlevel.getMaxBuildHeight(), p_269763_.getMaxBlockZ());
		          structurestart.placeInChunk(serverlevel, serverlevel.structureFeatureManager(), chunkgenerator, serverlevel.getRandom(), box, p_269763_);
		       });
		       context.getSource().sendSuccess(new TranslationTextComponent("command.lotrextended.structureSummon.success"), false);
		    }
		    return 0;
		}
		else {
			return 1;
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
