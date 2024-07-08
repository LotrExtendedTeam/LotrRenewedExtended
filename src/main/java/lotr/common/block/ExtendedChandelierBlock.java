package lotr.common.block;

import java.util.Random;

import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;

public class ExtendedChandelierBlock extends ChandelierBlock{
	private final RegistryObject<BasicParticleType> typeOverride;

	public ExtendedChandelierBlock(RegistryObject<BasicParticleType> typeOverride) {
		super();
		this.typeOverride = typeOverride;
	}

	@Override
	protected void doChandelierParticles(World world, Random rand, double x, double y, double z) {
		world.addParticle(ParticleTypes.SMOKE, x, y, z, 0.0D, 0.0D, 0.0D);
		world.addParticle(typeOverride.get(), x, y, z, 0.0D, 0.0D, 0.0D);
	}
}
