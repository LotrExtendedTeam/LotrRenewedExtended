package lotr.common.structure.StructureV4.abstractPieces;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import com.github.maximuslotro.lotroExtended.ExtendedLog;
import com.github.maximuslotro.lotroExtended.enums.StructureType;
import com.github.maximuslotro.lotroExtended.utils.DirectionTools;

import lotr.common.structure.StructureFile;
import lotr.common.structure.StructureFileHandler;
import lotr.common.structure.StructureV4.parts.ExtendedEntitySpawningv4;
import lotr.common.structure.util.RelativePosition;
import lotr.common.structure.util.StructureSize;
import lotr.common.structure.util.StructureStrings;
import lotr.common.structure.util.StructureSize.StructureBoxValues;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.FluidState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public abstract class ExtendedFileBasedStructurePiece extends ExtendedFunctionalStructurePiece{
	private static StructureFile structureFile;
	public final Direction nativeDirection;
	public ExtendedFileBasedStructurePiece(IStructurePieceType pieceType, Random random, BlockPos position, StructureType structureType) {
		this(pieceType, random, position, Direction.Plane.HORIZONTAL.getRandomDirection(random), structureType);
	}

	public ExtendedFileBasedStructurePiece(IStructurePieceType pieceType, Random random, BlockPos position, Direction direction, StructureType structureType) {
		super(pieceType, random, position, direction, structureType);
		if(getStructure()!=null) {
			nativeDirection = structureFile.getNaturalFacingDirection();
			updateStructureParamiters(structureFile);
		}else {
			ExtendedLog.error("FAILED LOADING STRUCTURE FILE!: " + fileName());
			nativeDirection=null;
		}
	}

	public ExtendedFileBasedStructurePiece(IStructurePieceType piece, CompoundNBT nbt) {
		super(piece, nbt);
		int i = nbt.getInt("NativeDirection");
	    this.nativeDirection = (i == -1) ? null : Direction.from2DDataValue(i);
	}

	@Override
	public boolean placeBlocks(ISeedReader world, StructureManager structureManager, ChunkGenerator chunkGen, Random random, ChunkPos cPos, StructureType structureType) {
		StructureFile file = getStructure();
		if (file != null) {
			for (int i = 0; i < file.getLayers().size(); i++) {
				CompoundNBT nbt = file.getLayers().getCompound(i);
				RelativePosition rpos = StructureFile.readBlockPos(nbt.getCompound(StructureStrings.relativeLocationHolder));
				CompoundNBT blockData = nbt.getCompound(StructureStrings.blockMapHolder);
				BlockState blockstate = NBTUtil.readBlockState(blockData);
				setBlockAndMetadata(world, blockstate, rpos.getX(), rpos.getY(), rpos.getZ(), boundingBox, nbt, random);
			}
			doFileGen(world, random, file, boundingBox);
			return true;
		}
		return false;
	}

	@Override
	protected void addAdditionalSaveData(CompoundNBT nbt) {
		super.addAdditionalSaveData(nbt);
		nbt.putInt("NativeDirection", nativeDirection == null ? -1 : nativeDirection.get2DDataValue());
	}

	protected void updateStructureParamiters(StructureFile file) {
		StructureSize structureSize = file.readGenv4StructureSize();
		this.height = structureSize.getHeight();
		this.width = structureSize.getWidth();
		this.depth = structureSize.getDepth();
		StructureBoxValues boxModifer = structureSize.makeStructureBox(file.getLayers());
		this.boundingBox = MutableBoundingBox.createProper(getWorldX(boxModifer.widthLowX,boxModifer.depthLowZ), 
										getWorldY(boxModifer.heightLowY), 
										getWorldZ(boxModifer.widthLowX,boxModifer.depthLowZ), 
										getWorldX(boxModifer.widthHighX,boxModifer.depthHighZ), 
										getWorldY(boxModifer.heightHighY), 
										getWorldZ(boxModifer.widthHighX,boxModifer.depthHighZ));
	}

	public StructureFile getStructure() {
		if(isCodeDefined()) {
			throw new IllegalStateException("Tried to load a structure file for a code-defined structure! Fix your code! Offendinding class: "+this.getClass().getSimpleName());
		}
		if(structureFile==null) {
			structureFile = getStructure(fileName());
		}
		return structureFile;
	}

	@Override
	protected int getWorldX(int xCord, int zCord) {
		if (getSsDirection() == null || nativeDirection == null) {
			return 0;
		} else {
			if(getSsDirection().equals(nativeDirection)||nativeDirection.equals(Direction.UP)) {
				return initialX + xCord;
			}
			if(getSsDirection().getOpposite().equals(nativeDirection)) {
				return initialX - xCord;
			}
			if(getSsDirection().getClockWise().equals(nativeDirection)) {
				return initialX + zCord;
			}
			if(getSsDirection().getCounterClockWise().equals(nativeDirection)) {
				return initialX - zCord;
			}
		}
		throw new IllegalArgumentException("Found Invalid Rotation Type combination: "+getSsDirection().toString()+" + "+nativeDirection.toString());
	}

	@Override
	protected int getWorldZ(int xCord, int zCord) {
		if (getSsDirection() == null || nativeDirection == null) {
			return 0;
		} else {
			if(getSsDirection().equals(nativeDirection)||nativeDirection.equals(Direction.UP)) {
				return initialZ + zCord;
			}
			if(getSsDirection().getOpposite().equals(nativeDirection)) {
				return initialZ - zCord;
			}
			if(getSsDirection().getClockWise().equals(nativeDirection)) {
				return initialZ - xCord;
			}
			if(getSsDirection().getCounterClockWise().equals(nativeDirection)) {
				return initialZ + xCord;
			}
		}
		throw new IllegalArgumentException("Found Invalid Rotation Type combination: "+getSsDirection().toString()+" + "+nativeDirection.toString());
	}
	@Override
	protected void placeBlockInternal(ISeedReader world, BlockState blockState, BlockPos blockpos, MutableBoundingBox box) {
		if (box.isInside(blockpos)&& nativeDirection!=null) {
			Rotation rot = DirectionTools.getRotationDirection(getSsDirection(), nativeDirection);
			if(rot!=Rotation.NONE) {
				blockState = blockState.rotate(world, blockpos, rot);
			}

			world.setBlock(blockpos, blockState, 2);
			FluidState fluidstate = world.getFluidState(blockpos);
			if (!fluidstate.isEmpty()) {
				world.getLiquidTicks().scheduleTick(blockpos, fluidstate.getType(), 0);
			}
		}
	}
	public StructureFile getStructure(String name) {
		StructureFile file = StructureFileHandler.getStructureFile(name, ServerLifecycleHooks.getCurrentServer());
		if (file == null) {
			ExtendedLog.error("LOADED NULL STRUCTURE FILE!: " + name);
		}
		return file;
	}

	public abstract String fileName();
	
	public void doFileGen(ISeedReader world, Random random, StructureFile file, MutableBoundingBox box) {
		setSignTextFromFile(world, random, file, box);
		spawnEntitiesFile(world, random, file, box);
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
}
