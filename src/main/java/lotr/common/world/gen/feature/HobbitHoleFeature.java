package lotr.common.world.gen.feature;

import java.util.Random;

import com.mojang.serialization.Codec;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

public class HobbitHoleFeature extends Feature<HobbitHoleFeatureConfig> {
	public HobbitHoleFeature(Codec<HobbitHoleFeatureConfig> codec) {
		super(codec);
	}

	@Override
	public boolean place(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos,
			HobbitHoleFeatureConfig config) {
		if (LOTRFeatures.isSurfaceBlock((IWorld) world, pos.below())) {
			int up = 2 + rand.nextInt(3);
			BlockPos tablePos = pos.above(up);
			world.setBlock(tablePos, config.block, 2);
			return true;
		}
		return false;
	}
}
