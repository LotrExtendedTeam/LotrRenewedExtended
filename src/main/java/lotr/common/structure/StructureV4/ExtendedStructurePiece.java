package lotr.common.structure.StructureV4;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import com.github.maximuslotro.lotroExtended.ExtendedLog;
import com.github.maximuslotro.lotroExtended.LotrExtendedMod;

import lotr.common.init.LOTRBlocks;
import lotr.common.structure.StructureV4.parts.ExtendedKegDrinks;
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
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraftforge.fml.RegistryObject;

public abstract class ExtendedStructurePiece extends ExtendedScatteredStructurePiece {
	private Map<String, Boolean> placedFeatures = new HashMap<>();

	protected ExtendedStructurePiece(IStructurePieceType pieceType, Random random, int posX, int posY, int posZ, int width, int height, int depth) {
		this(pieceType, random, posX, posY, posZ, width, height, depth, Direction.Plane.HORIZONTAL.getRandomDirection(random));
	}

	protected ExtendedStructurePiece(IStructurePieceType pieceType, Random random, int posX, int posY, int posZ, int width, int height, int depth, Direction direction) {
		super(pieceType, random, posX, posY, posZ, width, height, depth, direction);
	}

	protected ExtendedStructurePiece(IStructurePieceType piece, CompoundNBT nbt) {
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
		ListNBT placedFeaturesList = new ListNBT();
		for(Entry<String, Boolean> feature : placedFeatures.entrySet()) {
			CompoundNBT dataHolder = new CompoundNBT();
			dataHolder.putBoolean(feature.getKey(), feature.getValue());
			placedFeaturesList.add(dataHolder);
		}
		nbt.put("placedFeatures", placedFeaturesList);
	}

	protected abstract void addMoreSaveData(CompoundNBT nbt);

	@Override
	public abstract boolean postProcess(ISeedReader world, StructureManager structureManager, ChunkGenerator chunkGen, Random random, MutableBoundingBox box, ChunkPos cPos, BlockPos bPos);

	public boolean isCodeDefined() {
		return true;
	}

	protected void setBlockAndMetadata(ISeedReader world, BlockState blockState, int x, int y, int z,
			MutableBoundingBox box, CompoundNBT nbt, Random random) {
		super.placeBlock(world, blockState, x, y, z, box);
		if (blockState.getBlock() == Blocks.BARREL || blockState.getBlock() == Blocks.CHEST) {
			setLockableLootTileEntityContents(world, random, x, y, z, nbt, box);
		}
	}

	public void genKegForCurrentBlock(ISeedReader world, Random random, BlockState blockstate, int x, int y, int z, MutableBoundingBox box, List<RegistryObject<Item>>  list) {
		if(blockstate.getBlock()==LOTRBlocks.KEG.get()) {
			setKegTileEntityContents(world, random, x, y, z, box, list);
		}
	}

	public void setLockableLootTileEntityContents(ISeedReader world, Random random, int x, int y, int z, CompoundNBT nbt, MutableBoundingBox box) {
//		if (nbt.contains(StructureStrings.structureLootTableHolder)) {
//			if(!nbt.contains(StructureStrings.structureLootTableNumberHolder)) {
//				ExtendedLog.error("Missing chest identifier number for X: " + x + ", Y: " + y + ", Z:" + z + ", NBT: " + nbt.toString());
//			}
//			String table = nbt.getString(StructureStrings.structureLootTableHolder);
//			if (!table.equals(StructureStrings.EMPTY)) {
//				String data = nbt.getString(StructureStrings.structureLootTableNumberHolder);
//				if(placedFeatures.containsKey(data)&&placedFeatures.get(data)) {
//					return;
//				}
//				placedFeatures.put(data, setLockableLootTileEntityContents(world, random, locToBlockPos(x, y, z), table, box));
//			}
//		} else {
//			ExtendedLog.error("MISSING LOOTTABLE VALUES FOR X: " + x + ", Y: " + y + ", Z:" + z + ", NBT: " + nbt.toString());
//		}
	}

	public boolean setLockableLootTileEntityContents(ISeedReader world, Random random, BlockPos pos, String table,
			MutableBoundingBox box) {
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

	public void setKegTileEntityContents(ISeedReader world, Random random, int x, int y, int z, MutableBoundingBox box, List<RegistryObject<Item>> pool) {
		if(placedFeatures.containsKey("kegValueX"+x+"Y"+y+"Z"+z)&&placedFeatures.get("kegValueX"+x+"Y"+y+"Z"+z)) {
			return;
		}
		placedFeatures.put("kegValueX"+x+"Y"+y+"Z"+z, setKegTileEntityContents(world, random, locToBlockPos(x, y, z), box, pool));
	}

	public boolean setKegTileEntityContents(ISeedReader world, Random random, BlockPos pos, MutableBoundingBox box, List<RegistryObject<Item>> pool) {
		if (box.isInside(pos)) {
			TileEntity tileentity = world.getBlockEntity(pos);
			if (tileentity instanceof KegTileEntity) {
				if (random.nextInt(5) == 0) {
					return true;
				}
				((KegTileEntity) tileentity).fillBrewedWith(ExtendedKegDrinks.SetDrink(random, pool));
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
}