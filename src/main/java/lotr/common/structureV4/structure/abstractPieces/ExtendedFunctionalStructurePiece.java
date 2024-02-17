package lotr.common.structureV4.structure.abstractPieces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.github.maximuslotro.lotrExtended.ExtendedLog;
import com.github.maximuslotro.lotrExtended.LotrExtendedMod;
import com.github.maximuslotro.lotrExtended.common.enums.StructureType;

import lotr.common.init.LOTRBlocks;
import lotr.common.structureV4.structure.parts.ExtendedKegDrinksv4;
import lotr.common.structureV4.util.RelativePosition;
import lotr.common.structureV4.util.StructureStrings;
import lotr.common.tileentity.KegTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraftforge.fml.RegistryObject;

public abstract class ExtendedFunctionalStructurePiece extends ExtendedBaseStructurePiece {
	private Map<String, Boolean> placedFeatures = new HashMap<>();

	public ExtendedFunctionalStructurePiece(IStructurePieceType pieceType, Random random, BlockPos position, StructureType structureType) {
		this(pieceType, random, position, Direction.Plane.HORIZONTAL.getRandomDirection(random), structureType);
	}

	public ExtendedFunctionalStructurePiece(IStructurePieceType pieceType, Random random, BlockPos position, Direction direction, StructureType structureType) {
		super(pieceType, random, position, direction, structureType);
	}

	public ExtendedFunctionalStructurePiece(IStructurePieceType piece, CompoundNBT nbt) {
		super(piece, nbt);
		ListNBT placedFeaturesList = nbt.getList("placedFeatures", 10);
		for (int i = 0; i < placedFeaturesList.size(); i++) {
			CompoundNBT dataHolder = placedFeaturesList.getCompound(i);
			for (String key : dataHolder.getAllKeys()) {
				placedFeatures.put(key, dataHolder.getBoolean(key));
			}
		}
	}

	@Override
	protected void addAdditionalSaveData(CompoundNBT nbt) {
		super.addAdditionalSaveData(nbt);
		this.addMoreSaveData(nbt);
//		ListNBT placedFeaturesList = new ListNBT();
//		for(Entry<String, Boolean> feature : placedFeatures.entrySet()) {
//			CompoundNBT dataHolder = new CompoundNBT();
//			dataHolder.putBoolean(feature.getKey(), feature.getValue());
//			placedFeaturesList.add(dataHolder);
//		}
//		if(!placedFeaturesList.isEmpty()) {
//			nbt.put("placedFeatures", placedFeaturesList);
//		}
	}

	protected abstract void addMoreSaveData(CompoundNBT nbt);

	public boolean isCodeDefined() {
		return true;
	}

	protected void setBlockAndMetadata(ISeedReader world, BlockState blockState, int x, int y, int z, MutableBoundingBox box, CompoundNBT nbt, Random random) {
		super.placeBlock(world, blockState, x, y, z, box);
		if (blockState.getBlock() == Blocks.BARREL || blockState.getBlock() == Blocks.CHEST) {
			setLockableLootTileEntityContents(world, random, x, y, z, nbt, box);
		}
		if(blockState.getBlock()==LOTRBlocks.KEG.get()) {
			setKegTileEntityContents(world, random, x, y, z, box);
		}
	}

	public void setLockableLootTileEntityContents(ISeedReader world, Random random, int x, int y, int z, CompoundNBT nbt, MutableBoundingBox box) {
		if (nbt.contains(StructureStrings.structureLootTableHolder)) {
			String table = nbt.getString(StructureStrings.structureLootTableHolder);
			if (!table.equals(StructureStrings.EMPTY)) {
				if(placedFeatures.containsKey("conVX"+x+"Y"+y+"Z"+z)&&placedFeatures.get("conVX"+x+"Y"+y+"Z"+z)) {
					return;
				}
				placedFeatures.put("conVX"+x+"Y"+y+"Z"+z, setLockableLootTileEntityContents(world, random, locToBlockPos(x, y, z), table, box));
			}
		} else {
			ExtendedLog.error("MISSING LOOTTABLE VALUES FOR X: " + x + ", Y: " + y + ", Z:" + z + ", NBT: " + nbt.toString());
		}
	}

	public boolean setLockableLootTileEntityContents(ISeedReader world, Random random, BlockPos pos, String table, MutableBoundingBox box) {
		if (box.isInside(pos)) {
			TileEntity tileentity = world.getBlockEntity(pos);
			ResourceLocation resource = new ResourceLocation(LotrExtendedMod.MODID, "chests/" + table);
			if (tileentity instanceof LockableLootTileEntity) {
				((LockableLootTileEntity) tileentity).setLootTable(resource, random.nextLong());
				return true;
			} else {
				ExtendedLog.error("FAILED TO SET LOOTTABLE FOR: " + pos.toString() + ", " + resource + ", NOT INSTANCE OF " + LockableLootTileEntity.class.getName());
			}
		}
		return false;
	}

	public void setKegTileEntityContents(ISeedReader world, Random random, int x, int y, int z, MutableBoundingBox box) {
		if(placedFeatures.containsKey("kegVX"+x+"Y"+y+"Z"+z)&&placedFeatures.get("kegVX"+x+"Y"+y+"Z"+z)) {
			return;
		}
		placedFeatures.put("kegVX"+x+"Y"+y+"Z"+z, setKegTileEntityContents(world, random, locToBlockPos(x, y, z), box, defaultDrinks()));
	}

	public List<RegistryObject<Item>> defaultDrinks(){
		return new ArrayList<>();
	}

	public boolean setKegTileEntityContents(ISeedReader world, Random random, BlockPos pos, MutableBoundingBox box, List<RegistryObject<Item>> pool) {
		if (box.isInside(pos)) {
			TileEntity tileentity = world.getBlockEntity(pos);
			if (tileentity instanceof KegTileEntity) {
				((KegTileEntity) tileentity).fillBrewedWith(ExtendedKegDrinksv4.SetDrink(random, pool));
				return true;
			} else {
				ExtendedLog.error("FAILED TO SET BREW FOR: " + pos.toString() + ", " + ", NOT INSTANCE OF " + KegTileEntity.class.getName());
			}
		}
		return false;
	}

	public BlockPos locToBlockPos(int x, int y, int z) {
		return new BlockPos(getWorldX(x, z), getWorldY(y), getWorldZ(x, z));
	}

	public BlockPos rPToBlockPos(RelativePosition pos) {
		return new BlockPos(getWorldX(pos.getX(),pos.getZ()), getWorldY(pos.getY()), getWorldZ(pos.getX(),pos.getZ()));
	}
}