package lotr.common.block;

import java.util.function.Supplier;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.RegistryObject;

public class ExtendedTwoTypeDummyBlock extends Block {
	private final RegistryObject<Block> altBlock;
	public ExtendedTwoTypeDummyBlock(Supplier<Block> blockSup, RegistryObject<Block> alt) {
		super(Properties.copy(blockSup.get()));
		this.altBlock = alt;
	}
	public RegistryObject<Block> getAltBlock() {
		return altBlock;
	}

	@Override
	public void setPlacedBy(World world, BlockPos pos, BlockState state, @Nullable LivingEntity p_180633_4_, ItemStack p_180633_5_) {
		world.setBlock(pos, altBlock.get().defaultBlockState().setValue(ExtendedTwoTypeCraftingTableBlock.ALTERNATITE, true) , 2);
	}

	@Override
	public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult trace) {
		if (world.isClientSide) {
			player.displayClientMessage(new StringTextComponent("This is a gliched crafting table block, break and re-place to fix"), false);
			world.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.TOTEM_USE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
			return ActionResultType.SUCCESS;
		}

		ServerWorld serverWorld = (ServerWorld) world;
		for (int l = 0; l < 10; ++l) {
			double x = (double) ((float) pos.getX() + world.random.nextFloat());
			double y = (double) (pos.getY() + 1);
			double z = (double) ((float) pos.getZ() + world.random.nextFloat());
			serverWorld.sendParticles(ParticleTypes.TOTEM_OF_UNDYING, x, y, z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
		}

		return ActionResultType.SUCCESS;
	}
}
