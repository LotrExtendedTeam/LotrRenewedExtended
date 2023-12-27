package lotr.common.world.gen.feature.structure;

import java.util.Random;

import com.mojang.serialization.Codec;

import lotr.common.world.gen.feature.structure.standalone.HobbitBarnFeature;
import lotr.common.world.gen.feature.structure.standalone.HobbitHoleFeature;
import lotr.common.world.gen.feature.structure.standalone.HobbitHoleSmallFeature;
import lotr.common.world.gen.feature.structure.standalone.HobbitHouseFeature;
import lotr.common.world.gen.feature.structure.standalone.HobbitTavernSmallFeature;
import lotr.common.world.gen.feature.structure.standalone.HobbitWindmillFeature;
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
		if(random==0) {
			return (new HobbitHoleFeature()).place(world, generator, rand, pos);
		}
		else if(random==1) {
			return (new HobbitBarnFeature()).place(world, generator, rand, pos);
		}
		else if(random==2) {
			return (new HobbitWindmillFeature()).place(world, generator, rand, pos);
		}
		else if(random==3) {
			return (new HobbitHouseFeature()).place(world, generator, rand, pos);
		}
		else if(random==4) {
			return (new HobbitHoleSmallFeature()).place(world, generator, rand, pos);
		}
		else {
			return (new HobbitTavernSmallFeature()).place(world, generator, rand, pos);
		}
	}
}
