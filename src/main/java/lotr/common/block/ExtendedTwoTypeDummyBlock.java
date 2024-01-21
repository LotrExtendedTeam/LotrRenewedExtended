package lotr.common.block;

import java.util.function.Supplier;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
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
}
