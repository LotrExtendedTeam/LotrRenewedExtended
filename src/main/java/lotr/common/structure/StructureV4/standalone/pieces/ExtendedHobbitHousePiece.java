package lotr.common.structure.StructureV4.standalone.pieces;

import java.util.List;
import java.util.Random;

import com.github.maximuslotro.lotroExtended.enums.StructureType;

import lotr.common.structure.StructureV4.ExtendedStructurePieces;
import lotr.common.structure.StructureV4.abstractPieces.ExtendedFileBasedStructurePiece;
import lotr.common.structure.StructureV4.parts.ExtendedKegDrinksv4;
import lotr.common.structure.StructureV4.parts.ExtendedSignTextsv4;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraftforge.fml.RegistryObject;

public class ExtendedHobbitHousePiece extends ExtendedFileBasedStructurePiece {

	public ExtendedHobbitHousePiece(Random random, BlockPos position, Direction direction, StructureType structureType) {
		super(ExtendedStructurePieces.HOBBIT_HOUSE_START, random, position, direction, structureType);
	}

	public ExtendedHobbitHousePiece(TemplateManager p_i51350_1_, CompoundNBT p_i51350_2_) {
		super(ExtendedStructurePieces.HOBBIT_HOUSE_START, p_i51350_2_);
	}

	@Override
	protected void addMoreSaveData(CompoundNBT p_143011_1_) {}

	@Override
	public List<RegistryObject<Item>> defaultDrinks(){
		return ExtendedKegDrinksv4.shireDrinksPool;
	}

	@Override
	public String fileName() {
		return "hobbit_house";
	}

	@Override
	public boolean isCodeDefined() {
		return false;
	}

	@Override
	public void setSignTextFromFileData(ISeedReader world, Random random, BlockPos blockPos, int num, MutableBoundingBox box) {
		setSignTileEntity(world, blockPos, ExtendedSignTextsv4.ShireTavernTexts(random), box);
	}
}