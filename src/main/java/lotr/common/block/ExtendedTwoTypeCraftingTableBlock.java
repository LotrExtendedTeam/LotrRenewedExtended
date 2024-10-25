package lotr.common.block;

import java.util.function.Supplier;

import lotr.common.fac.FactionPointer;
import lotr.common.recipe.FactionTableType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class ExtendedTwoTypeCraftingTableBlock extends FactionCraftingTableBlock {
	//True if table is alt type
	public static final BooleanProperty ALTERNATITE = BooleanProperty.create("table_alternative_texture");
	public ExtendedTwoTypeCraftingTableBlock(Supplier<? extends Block> block, FactionPointer tableFac, FactionTableType factionRecipeType, String tableScreenName) {
		super(block, tableFac, factionRecipeType, tableScreenName);
		this.registerDefaultState(this.stateDefinition.any().setValue(ALTERNATITE, Boolean.FALSE));
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> p_206840_1_) {
		p_206840_1_.add(ALTERNATITE);
	}

	@Override
	public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult trace) {
		if (world.isClientSide) {
			return ActionResultType.SUCCESS;
		}
		if(player.isCrouching()) {
			state = state.cycle(ALTERNATITE);
			world.setBlock(pos, state, 3);
			return ActionResultType.CONSUME;
		}
		return super.use(state, world, pos, player, hand, trace);
	}
}
