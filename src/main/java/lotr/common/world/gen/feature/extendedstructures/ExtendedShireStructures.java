package lotr.common.world.gen.feature.extendedstructures;

import java.util.Random;

import com.mojang.serialization.Codec;

import lotr.common.world.gen.feature.extendedstructures.standalone.HobbitBarnFeature;
import lotr.common.world.gen.feature.extendedstructures.standalone.HobbitHoleFeature;
import lotr.common.world.gen.feature.extendedstructures.standalone.HobbitHoleSmallFeature;
import lotr.common.world.gen.feature.extendedstructures.standalone.HobbitHouseFeature;
import lotr.common.world.gen.feature.extendedstructures.standalone.HobbitTavernSmallFeature;
import lotr.common.world.gen.feature.extendedstructures.standalone.HobbitWindmillFeature;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class ExtendedShireStructures extends Feature<NoFeatureConfig> {
	public ExtendedShireStructures(Codec<NoFeatureConfig> codec) {
		super(codec);
	}

	@Override
	public boolean place(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig p_241855_5_) {
		int random = rand.nextInt(6);
		boolean done = false;
		if(random==0) {
			done = (new HobbitHoleFeature()).place(world, generator, rand, pos);
		}
		else if(random==1) {
			done = (new HobbitBarnFeature()).place(world, generator, rand, pos);
		}
		else if(random==2) {
			done = (new HobbitWindmillFeature()).place(world, generator, rand, pos);
		}
		else if(random==3) {
			done = (new HobbitHouseFeature()).place(world, generator, rand, pos);
		}
		else if(random==4) {
			done = (new HobbitHoleSmallFeature()).place(world, generator, rand, pos);
		}
		else {
			done = (new HobbitTavernSmallFeature()).place(world, generator, rand, pos);
		}
		return done;
	}
}
