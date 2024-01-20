package lotr.common.world.gen.feature.extendedstructuresV2;

import com.mojang.serialization.Codec;

import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;

public class ExtendedStructure<C extends IFeatureConfig> extends Structure<C>{

	public ExtendedStructure(Codec<C> p_i231997_1_) {
		super(p_i231997_1_);
	}

	@Override
	public IStartFactory<C> getStartFactory() {
		return null;
	}
}
