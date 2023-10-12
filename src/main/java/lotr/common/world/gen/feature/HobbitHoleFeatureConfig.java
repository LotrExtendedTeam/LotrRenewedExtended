package lotr.common.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class HobbitHoleFeatureConfig implements IFeatureConfig {
	  public static final Codec<HobbitHoleFeatureConfig> CODEC= RecordCodecBuilder.create(instance -> instance.group(
			  BlockState.CODEC.fieldOf("table_state").forGetter(feature -> feature.block)).apply(instance, HobbitHoleFeatureConfig::new));
	  
	  public final BlockState block;
  
	  public HobbitHoleFeatureConfig(BlockState block) {
	    this.block = block;
	  }
}
