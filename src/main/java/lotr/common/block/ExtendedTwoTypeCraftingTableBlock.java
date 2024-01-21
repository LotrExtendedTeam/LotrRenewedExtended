package lotr.common.block;

import java.util.function.Supplier;

import lotr.common.fac.FactionPointer;
import lotr.common.recipe.FactionTableType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraftforge.fml.RegistryObject;

public class ExtendedTwoTypeCraftingTableBlock extends FactionCraftingTableBlock {
	//True if table is alt type
	public static final BooleanProperty ALTERNATITE = BooleanProperty.create("table_alternative_texture");
	private final RegistryObject<Block> altBlock;
	public ExtendedTwoTypeCraftingTableBlock(Supplier<? extends Block> block, FactionPointer tableFac, FactionTableType factionRecipeType, String tableScreenName, RegistryObject<Block> alt) {
		super(block, tableFac, factionRecipeType, tableScreenName);
		this.registerDefaultState(this.stateDefinition.any().setValue(ALTERNATITE, Boolean.FALSE));
		this.altBlock = alt;
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> p_206840_1_) {
		p_206840_1_.add(ALTERNATITE);
	}

	public RegistryObject<Block> getAltBlock() {
		return altBlock;
	}
}
