package lotr.common.structure.StructureV4.abstractPieces;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import com.github.maximuslotro.lotroExtended.ExtendedLog;
import com.github.maximuslotro.lotroExtended.LotrExtendedMod;
import com.github.maximuslotro.lotroExtended.enums.StructureType;

import lotr.common.init.LOTRBlocks;
import lotr.common.structure.StructureFile;
import lotr.common.structure.StructureV4.parts.ExtendedEntitySpawningv4;
import lotr.common.structure.StructureV4.parts.ExtendedKegDrinksv4;
import lotr.common.structure.util.RelativePosition;
import lotr.common.structure.util.StructureStrings;
import lotr.common.tileentity.KegTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public abstract class ExtendedFunctionalStructurePiece extends ExtendedBaseStructurePiece {
	private Map<String, Boolean> placedFeatures = new HashMap<>();

	protected ExtendedFunctionalStructurePiece(IStructurePieceType pieceType, Random random, BlockPos position, StructureType structureType) {
		this(pieceType, random, position, Direction.Plane.HORIZONTAL.getRandomDirection(random), structureType);
	}

	protected ExtendedFunctionalStructurePiece(IStructurePieceType pieceType, Random random, BlockPos position, Direction direction, StructureType structureType) {
		super(pieceType, random, position, direction, structureType);
	}

	protected ExtendedFunctionalStructurePiece(IStructurePieceType piece, CompoundNBT nbt) {
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

	public boolean isCodeDefined() {
		return true;
	}

	protected void setBlockAndMetadata(ISeedReader world, BlockState blockState, int x, int y, int z, MutableBoundingBox box, CompoundNBT nbt, Random random) {
		super.placeBlock(world, blockState, x, y, z, box);
		if (blockState.getBlock() == Blocks.BARREL || blockState.getBlock() == Blocks.CHEST) {
			setLockableLootTileEntityContents(world, random, x, y, z, nbt, box);
		}
	}

	// TODO merge with above
	public void doFileGen(ISeedReader world, Random random, StructureFile file, MutableBoundingBox box) {
		setSignTextFromFile(world, random, file, box);
		spawnEntitiesFile(world, random, file, box);
	}
	public void genKegForCurrentBlock(ISeedReader world, Random random, BlockState blockstate, int x, int y, int z, MutableBoundingBox box, List<RegistryObject<Item>>  list) {
		if(blockstate.getBlock()==LOTRBlocks.KEG.get()) {
			setKegTileEntityContents(world, random, x, y, z, box, list);
		}
	}

	public void setLockableLootTileEntityContents(ISeedReader world, Random random, int x, int y, int z, CompoundNBT nbt, MutableBoundingBox box) {
		if (nbt.contains(StructureStrings.structureLootTableHolder)) {
//			if(!nbt.contains(StructureStrings.structureLootTableNumberHolder)) {
//				ExtendedLog.error("Missing chest identifier number for X: " + x + ", Y: " + y + ", Z:" + z + ", NBT: " + nbt.toString());
//			}
			String table = nbt.getString(StructureStrings.structureLootTableHolder);
			if (!table.equals(StructureStrings.EMPTY)) {
//				String data = nbt.getString(StructureStrings.structureLootTableNumberHolder);
//				if(placedFeatures.containsKey(data)&&placedFeatures.get(data)) {
//					return;
//				}
//				placedFeatures.put(data, setLockableLootTileEntityContents(world, random, locToBlockPos(x, y, z), table, box));
				setLockableLootTileEntityContents(world, random, locToBlockPos(x, y, z), table, box);
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
				((KegTileEntity) tileentity).fillBrewedWith(ExtendedKegDrinksv4.SetDrink(random, pool));
				return true;
			} else {
				ExtendedLog.error("FAILED TO SET BREW FOR: " + pos.toString() + ", " + ", NOT INSTANCE OF " + KegTileEntity.class.getName());
			}
		}
		return false;
	}

	public void setSignTextFromFile(ISeedReader world, Random random, StructureFile file, MutableBoundingBox box) {
		if(file.getSigns().isEmpty()) {
			return;
		}
		Map<Integer, RelativePosition> signs = new HashMap<>();
		for (int i = 0; i < file.getSigns().size(); i++) {
			CompoundNBT nbt = file.getSigns().getCompound(i);
			int num = nbt.getInt(StructureStrings.signNumberHolder);
			RelativePosition rpos = StructureFile.readBlockPos(nbt.getCompound(StructureStrings.relativeLocationHolder));
			if(signs.containsKey(num)) {
				ExtendedLog.error("DUPLICATE SIGN COUNT FOUND IN STRUCTURE FILE: "+file.name);
				return;
			}
			signs.put(num, rpos);
		}
		for (Entry<Integer, RelativePosition> sign : signs.entrySet()) {
			setSignTextFromFileData(world, random, rPToBlockPos(sign.getValue()), sign.getKey(), box);
		}
	}

	public abstract void setSignTextFromFileData(ISeedReader world, Random random, BlockPos blockPos, int num, MutableBoundingBox box);

	public void setSignTileEntity(ISeedReader world, BlockPos pos, List<String> text, MutableBoundingBox box) {
		if (box.isInside(pos)) {
			TileEntity tileentity = world.getBlockEntity(pos);
			if (tileentity instanceof SignTileEntity) {
				if(text.size()<1) {
					ExtendedLog.error("FAILED TO SET TEXT FOR: " + pos.toString() + ", " + ", INVALID TEXT ARRAY PASSED!");
					return;
				}
				ITextComponent comp1 = StringTextComponent.EMPTY;
				ITextComponent comp2 = StringTextComponent.EMPTY;
				ITextComponent comp3 = StringTextComponent.EMPTY;
				ITextComponent comp4 = StringTextComponent.EMPTY;
				if(text.size()>=1) {comp1 = new StringTextComponent(text.get(0));}
				if(text.size()>=2) {comp2 = new StringTextComponent(text.get(1));}
				if(text.size()>=3) {comp3 = new StringTextComponent(text.get(2));}
				if(text.size()>=4) {comp4 = new StringTextComponent(text.get(3));}
				if(text.size()>4) {
					ExtendedLog.error("FAILED TO SET TEXT FOR: " + pos.toString() + ", " + ", INVALID TEXT ARRAY PASSED!");
					return;
				}
				ITextComponent[] messages = new ITextComponent[]{comp1, comp2, comp3, comp4};
	
				ObfuscationReflectionHelper.setPrivateValue(SignTileEntity.class, (SignTileEntity) tileentity, 
						messages, "field_145915_a");
			}
			else {
				ExtendedLog.error("FAILED TO SET TEXT FOR: " + pos.toString() + ", " + ", NOT INSTANCE OF " + SignTileEntity.class.getName());
			}
		}
	}

	public void spawnEntitiesFile(ISeedReader world, Random random, StructureFile file, MutableBoundingBox box) {
		if(file.getEntities().isEmpty()) {
			return;
		}
		Map<String, Map<Integer, RelativePosition>> entities = new HashMap<>();
		for (int i = 0; i < file.getEntities().size(); i++) {
			CompoundNBT nbt = file.getEntities().getCompound(i);
			int spawnNumber = nbt.getInt("spawn");
			String entityType = nbt.getString("entity");
			RelativePosition rpos = StructureFile.readBlockPos(nbt.getCompound(StructureStrings.relativeLocationHolder));
			if(entities.containsKey(entityType)) {
				Map<Integer, RelativePosition> entitie = entities.get(entityType);
				if(entitie.containsKey(spawnNumber)) {
					ExtendedLog.error("FOUND DUPLICATE SPAWN KEY IN STRUCTURE FILE: "+file.name);
					return;
				}
				entitie.put(spawnNumber, rpos);
				entities.put(entityType, entitie);
			} else {
				Map<Integer, RelativePosition> entitie = new HashMap<>();
				entitie.put(spawnNumber, rpos);
				entities.put(entityType, entitie);
			}
		}
		for (Entry<String, Map<Integer, RelativePosition>> entity : entities.entrySet()) {
			if(entity.getValue().size()==1) {
				if(!entity.getValue().containsKey(1)) {
					ExtendedLog.error("FOUND NON-SINGULAR KEY FOR SINGLE KEY: "+file.name+", entity: "+entity.getKey());
					ExtendedLog.error("ENTITY NOT SUMMONED!");
					continue;
				}
				ExtendedEntitySpawningv4.spawnEntity(world, entity.getKey(), rPToBlockPos(entity.getValue().get(1)), box);
				continue;
			}
			if(entity.getValue().size()>4||entity.getValue().size()<1) {
				ExtendedLog.error("FOUND TOO MANY/TOO LITTLE KEYS FOR: "+file.name+", entity: "+entity.getKey()+", keys count: "+entity.getValue().size());
				ExtendedLog.error("ENTITY NOT SUMMONED!");
				continue;
			}
			int pick = random.nextInt(100);
			if(entity.getValue().size()==2) {
				if(pick<90) {
					ExtendedEntitySpawningv4.spawnEntity(world, entity.getKey(), rPToBlockPos(entity.getValue().get(1)), box);
				} else {
					ExtendedEntitySpawningv4.spawnEntity(world, entity.getKey(), rPToBlockPos(entity.getValue().get(2)), box);
				}
				continue;
			}
			if(entity.getValue().size()==3) {
				if(pick<90) {
					ExtendedEntitySpawningv4.spawnEntity(world, entity.getKey(), rPToBlockPos(entity.getValue().get(1)), box);
				} else {
					pick = random.nextInt(100);
					if(pick<90) {
						ExtendedEntitySpawningv4.spawnEntity(world, entity.getKey(), rPToBlockPos(entity.getValue().get(2)), box);
					} else {
						ExtendedEntitySpawningv4.spawnEntity(world, entity.getKey(), rPToBlockPos(entity.getValue().get(3)), box);
					}
				}
				continue;
			}
			if(entity.getValue().size()==4) {
				if(pick<90) {
					ExtendedEntitySpawningv4.spawnEntity(world, entity.getKey(), rPToBlockPos(entity.getValue().get(1)), box);
				} else {
					pick = random.nextInt(100);
					if(pick<90) {
						ExtendedEntitySpawningv4.spawnEntity(world, entity.getKey(), rPToBlockPos(entity.getValue().get(2)), box);
					} else {
						pick = random.nextInt(100);
						if(pick<90) {
							ExtendedEntitySpawningv4.spawnEntity(world, entity.getKey(), rPToBlockPos(entity.getValue().get(3)), box);
						} else {
							ExtendedEntitySpawningv4.spawnEntity(world, entity.getKey(), rPToBlockPos(entity.getValue().get(4)), box);
						}
					}
				}
				continue;
			}
		}
	}

	public BlockPos locToBlockPos(int x, int y, int z) {
		return new BlockPos(getWorldX(x, z), getWorldY(y), getWorldZ(x, z));
	}
	public BlockPos rPToBlockPos(RelativePosition pos) {
		return new BlockPos(getWorldX(pos.getX(),pos.getZ()), getWorldY(pos.getY()), getWorldZ(pos.getX(),pos.getZ()));
	}
}