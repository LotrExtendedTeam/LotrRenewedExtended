package lotr.common.command;

import java.util.Random;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;

import lotr.common.world.gen.feature.extendedstructures.ExtendedStructureBase;
import lotr.common.world.gen.feature.extendedstructures.standalone.HobbitBarnFeature;
import lotr.common.world.gen.feature.extendedstructures.standalone.HobbitHoleFeature;
import lotr.common.world.gen.feature.extendedstructures.standalone.HobbitHoleSmallFeature;
import lotr.common.world.gen.feature.extendedstructures.standalone.HobbitHouseFeature;
import lotr.common.world.gen.feature.extendedstructures.standalone.HobbitTavernSmallFeature;
import lotr.common.world.gen.feature.extendedstructures.standalone.HobbitWindmillFeature;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.text.StringTextComponent;


public class ExtendedStructureSummon extends LOTRBaseCommand {
	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		dispatcher.register(Commands.literal("lotr_structure_summon")
				.requires(context -> context.hasPermission(4))
				.then(Commands.literal("hobbit_tavern_small")
						.executes(context -> spawnStructure(context, new HobbitTavernSmallFeature())))
				.then(Commands.literal("hobbit_hole_legacy")
						.executes(context -> spawnStructure(context, new HobbitHoleFeature())))
				.then(Commands.literal("hobbit_barn")
						.executes(context -> spawnStructure(context, new HobbitBarnFeature())))
				.then(Commands.literal("hobbit_house")
						.executes(context -> spawnStructure(context, new HobbitHouseFeature())))
				.then(Commands.literal("hobbit_hole_small")
						.executes(context -> spawnStructure(context, new HobbitHoleSmallFeature())))
				.then(Commands.literal("hobbit_windmill")
						.executes(context -> spawnStructure(context, new HobbitWindmillFeature()))));
	}

	private static int spawnStructure(CommandContext<CommandSource> context, ExtendedStructureBase structure) {
		if(context.getSource().getEntity()!=null &&context.getSource().getEntity() instanceof PlayerEntity) {
			Direction dir =((PlayerEntity)context.getSource().getEntity()).getDirection();
			if(structure.placeWithSetRotation(context.getSource().getLevel(), null, new Random(), context.getSource().getEntity().blockPosition(), dir.getOpposite())) {
				context.getSource().sendSuccess(new StringTextComponent("Structure Loaded Successfully"), false);
				return 0;
			}
			else {
				context.getSource().sendSuccess(new StringTextComponent("Structure Loading Failed, check console"), false);
				return 1;
			}
		}
		else {
			return 1;
		}
	}
}
