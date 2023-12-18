package lotr.common.world.gen.feature.structure;

import java.util.Random;

import com.mojang.serialization.Codec;

import lotr.common.world.gen.feature.structure.standalone.HobbitBarnFeature;
import lotr.common.world.gen.feature.structure.standalone.HobbitHoleFeature;
import lotr.common.world.gen.feature.structure.standalone.HobbitTavernSmallFeature;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class ShireStructures extends Feature<NoFeatureConfig> {
	public ShireStructures(Codec<NoFeatureConfig> codec) {
		super(codec);
	}

	@Override
	public boolean place(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig p_241855_5_) {
		int random = rand.nextInt(3);
		if(random==0) {
			return (new HobbitHoleFeature()).place(world, generator, rand, pos);
		}
		else if(random==1) {
			return (new HobbitBarnFeature()).place(world, generator, rand, pos);
		}
		else {
			return (new HobbitTavernSmallFeature()).place(world, generator, rand, pos);
		}
	}
}
