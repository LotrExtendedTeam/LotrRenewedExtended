package lotr.common.command;

import java.util.Random;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;

import lotr.common.world.gen.feature.structure.ExtendedStructureBase;
import lotr.common.world.gen.feature.structure.standalone.HobbitBarnFeature;
import lotr.common.world.gen.feature.structure.standalone.HobbitHoleFeature;
import lotr.common.world.gen.feature.structure.standalone.HobbitTavernSmallFeature;
import lotr.common.world.gen.feature.structure.standalone.HobbitWindmillFeature;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;


public class ExtendedStructureSummon extends LOTRBaseCommand {
	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		dispatcher.register(Commands.literal("lotr_structure_summon")
				.requires(context -> context.hasPermission(4))
				.then(Commands.literal("hobbit_tavern")
						.executes(context -> spawnStructure(context, new HobbitTavernSmallFeature())))
				.then(Commands.literal("hobbit_hole")
						.executes(context -> spawnStructure(context, new HobbitHoleFeature())))
				.then(Commands.literal("hobbit_barn")
						.executes(context -> spawnStructure(context, new HobbitBarnFeature())))
				.then(Commands.literal("hobbit_windmill")
						.executes(context -> spawnStructure(context, new HobbitWindmillFeature()))));
	}

	private static int spawnStructure(CommandContext<CommandSource> context, ExtendedStructureBase structure) {
		if(context.getSource().getEntity()!=null &&context.getSource().getEntity() instanceof PlayerEntity) {
			if(structure.place(context.getSource().getLevel(), null, new Random(), context.getSource().getEntity().blockPosition())) {
				context.getSource().sendSuccess(new StringTextComponent("Loaded Successfully"), false);
				return 0;
			}
			else {
				context.getSource().sendSuccess(new StringTextComponent("Loading Failed, check console"), false);
				return 1;
			}
		}
		else {
			return 1;
		}
	}
}
