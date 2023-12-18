package lotr.common.command;

import java.util.Random;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;

import lotr.common.world.gen.feature.structure.standalone.HobbitBarnFeature;
import lotr.common.world.gen.feature.structure.standalone.HobbitHoleFeature;
import lotr.common.world.gen.feature.structure.standalone.HobbitTavernSmallFeature;
import lotr.common.world.gen.feature.structure.standalone.HobbitWindmillFeature;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.PlayerEntity;


public class ExtendedStructureSummon extends LOTRBaseCommand {
	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		dispatcher.register(Commands.literal("lotr_structure_summon")
				.requires(context -> context.hasPermission(4))
				.then(Commands.literal("hobbit_tavern")
						.executes(context -> spawnHobbitTavern(context)))
				.then(Commands.literal("hobbit_hole")
						.executes(context -> spawnHobbitHole(context)))
				.then(Commands.literal("hobbit_barn")
						.executes(context -> spawnHobbitBarn(context)))
				.then(Commands.literal("hobbit_windmill")
						.executes(context -> spawnHobbitBarn(context))));
	}

	private static int spawnHobbitHole(CommandContext<CommandSource> context) {
		if(context.getSource().getEntity()!=null &&context.getSource().getEntity() instanceof PlayerEntity) {
			(new HobbitHoleFeature()).place(context.getSource().getLevel(), null, new Random(), context.getSource().getEntity().blockPosition());
		}
		return 0;
	}
	private static int spawnHobbitTavern(CommandContext<CommandSource> context) {
		if(context.getSource().getEntity()!=null &&context.getSource().getEntity() instanceof PlayerEntity) {
			(new HobbitTavernSmallFeature()).place(context.getSource().getLevel(), null, new Random(), context.getSource().getEntity().blockPosition());
		}
		return 0;
	}
	private static int spawnHobbitBarn(CommandContext<CommandSource> context) {
		if(context.getSource().getEntity()!=null &&context.getSource().getEntity() instanceof PlayerEntity) {
			(new HobbitBarnFeature()).place(context.getSource().getLevel(), null, new Random(), context.getSource().getEntity().blockPosition());
		}
		return 0;
	}
	private static int spawnHobbitWindmill(CommandContext<CommandSource> context) {
		if(context.getSource().getEntity()!=null &&context.getSource().getEntity() instanceof PlayerEntity) {
			(new HobbitWindmillFeature()).place(context.getSource().getLevel(), null, new Random(), context.getSource().getEntity().blockPosition());
		}
		return 0;
	}
}
