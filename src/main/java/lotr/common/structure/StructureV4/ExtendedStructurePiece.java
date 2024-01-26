package lotr.common.structure.StructureV4;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotroExtended.ExtendedLog;
import com.github.maximuslotro.lotroExtended.LotrExtendedMod;

import lotr.common.init.LOTRBlocks;
import lotr.common.structure.StructureFile;
import lotr.common.structure.StructureFileHandler;
import lotr.common.structure.util.StructureSize;
import lotr.common.structure.util.StructureStrings;
import lotr.common.tileentity.KegTileEntity;
import lotr.common.world.gen.feature.structuresV3.parts.ExtendedKegDrinks;
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
import net.minecraft.world.gen.feature.structure.ScatteredStructurePiece;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public abstract class ExtendedStructurePiece extends ScatteredStructurePiece {
	private Map<String, Boolean> placedFeatures = new HashMap<>();
	protected ExtendedStructurePiece(IStructurePieceType piece, Random random, int x0, int y0, int z0, int width, int height, int depth, @Nullable Direction dir) {
		super(piece, random, x0, y0, z0, width, height, depth);
	    if(dir!=null && Direction.Plane.HORIZONTAL.test(dir)) {
	    	this.setOrientation(dir);
	    	if (this.getOrientation().getAxis() == Direction.Axis.Z) {
		         this.boundingBox = new MutableBoundingBox(x0, y0, z0, x0 + width - 1, y0 + height - 1, z0 + depth - 1);
		      } else {
		         this.boundingBox = new MutableBoundingBox(x0, y0, z0, x0 + depth - 1, y0 + height - 1, z0 + width - 1);
		      }
	    }
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

	public void generateOffsets(ISeedReader world, Random rand, Direction nativeDir, StructureSize structureSize, MutableBoundingBox box) {
		if(Direction.Plane.HORIZONTAL.test(nativeDir)) {
	    	this.setOrientation(nativeDir);
	    	if (this.getOrientation().getAxis() == Direction.Axis.Z) {
		         this.boundingBox = new MutableBoundingBox(box.x0, box.y0, box.z0, box.x0 + structureSize.getLength() - 1, box.y0 + structureSize.getHeight() - 1, box.z0 + structureSize.getWidth() - 1);
		      } else {
		         this.boundingBox = new MutableBoundingBox(box.x0, box.y0, box.z0, box.x0 + structureSize.getWidth() - 1, box.y0 + structureSize.getLength() - 1, box.z0 + structureSize.getLength() - 1);
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
	public abstract boolean postProcess(ISeedReader world, StructureManager structureManager, ChunkGenerator chunkGen,
			Random random, MutableBoundingBox box, ChunkPos cPos, BlockPos bPos);

	public abstract String fileName();

	public StructureFile getStructure() {
		return getStructure(fileName());
	}

	public StructureFile getStructure(String name) {
		StructureFile file = StructureFileHandler.getStructureFile(name, ServerLifecycleHooks.getCurrentServer());
		if (file == null) {
			ExtendedLog.error("LOADED NULL STRUCTURE FILE!: " + name);
		}
		return file;
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

	public void setLockableLootTileEntityContents(ISeedReader world, Random random, int x, int y, int z,
			CompoundNBT nbt, MutableBoundingBox box) {
		if (nbt.contains(StructureStrings.structureLootTableHolder)&& nbt.contains(StructureStrings.structureLootTableNumberHolder)) {
			String table = nbt.getString(StructureStrings.structureLootTableHolder);
			if (!table.equals(StructureStrings.EMPTY)) {
				String data = nbt.getString(StructureStrings.structureLootTableNumberHolder);
				if(placedFeatures.containsKey(data)&&placedFeatures.get(data)) {
					return;
				}
				placedFeatures.put(data, setLockableLootTileEntityContents(world, random, locToBlockPos(x, y, z), table, box));
			}
		} else {
			ExtendedLog.error("MISSING LOOTTABLE VALUES FOR X: " + x + ", Y: " + y + ", Z:" + z + ", NBT: " + nbt.toString());
		}
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
