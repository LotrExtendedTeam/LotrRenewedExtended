package lotr.common.world.gen.feature.structure;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class HobbitTavernSmallFeatureConfig implements IFeatureConfig {
	  public static final Codec<HobbitTavernSmallFeatureConfig> CODEC= RecordCodecBuilder.create(instance -> instance.group(
			  BlockState.CODEC.fieldOf("table_state").forGetter(feature -> feature.block)).apply(instance, HobbitTavernSmallFeatureConfig::new));
	  
	  public final BlockState block;

	  public HobbitTavernSmallFeatureConfig(BlockState block) {
	    this.block = block;
	  }
}
