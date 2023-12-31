package lotr.common.world.gen.feature.structure;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.UUID;

import com.github.maximuslotro.lotroExtended.ExtendedLog;
import com.github.maximuslotro.lotroExtended.LotrExtendedMod;

import lotr.common.init.LOTRBlocks;
import lotr.common.structure.RelativePosition;
import lotr.common.structure.StructureFile;
import lotr.common.structure.StructureFileHandler;
import lotr.common.structure.StructureScanner;
import lotr.common.tileentity.KegTileEntity;
import lotr.common.world.gen.feature.LOTRFeatures;
import lotr.common.world.gen.feature.structure.parts.ExtendedEntitySpawning;
import lotr.common.world.gen.feature.structure.parts.ExtendedKegDrinks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.BushBlock;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public abstract class ExtendedStructureBase{
	public int initialX;
	public int initialY;
	public int initialZ;
	public int rot;
	public RelativePosition offset;

	public boolean place(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos) {
		try {
			UUID id = UUID.randomUUID();
			ExtendedLog.debug("Starting StructureGen: "+id.toString()+", id: "+fileName());
			Boolean catcvhCrash = placeWithRotation(world, generator, rand, pos, rand.nextInt(4));
			ExtendedLog.debug("Finished StructureGen: "+id.toString()+", id: "+fileName());
			return catcvhCrash;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public abstract boolean placeWithRotation(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos, int rot);
	
	public abstract String fileName();

	//TODO deal with rotation
	public void generateOffsets(ISeedReader world, Random rand, BlockPos pos, int rot, RelativePosition offset) {
		this.rot = rot;
		this.offset = offset;

		this.initialX = (pos.getX()-((int) Math.ceil(offset.X/2)));
		this.initialZ = (pos.getZ()-((int) Math.ceil(offset.Z/2)));
		this.initialY = pos.getY();
		//this.initialX = pos.getX();
		//this.initialZ = pos.getZ();
		//findSurface(world);
//		world.setBlock(new BlockPos(pos.getX(), 78, pos.getZ()), LOTRBlocks.SILVER_ORE.get().defaultBlockState(), Constants.BlockFlags.DEFAULT);
//
//		world.setBlock(new BlockPos(initialX, 79, initialZ), LOTRBlocks.MITHRIL_ORE.get().defaultBlockState(), Constants.BlockFlags.DEFAULT);
//		switch(rot) {
//		case 0:
//			world.setBlock(new BlockPos(initialX, 80, initialZ), LOTRBlocks.COPPER_ORE.get().defaultBlockState(), Constants.BlockFlags.DEFAULT);
//			break;
//		case 1:
//			world.setBlock(new BlockPos(initialX, 81, initialZ), LOTRBlocks.DURNOR_ORE.get().defaultBlockState(), Constants.BlockFlags.DEFAULT);
//			break;
//		case 2:
//			world.setBlock(new BlockPos(initialX, 82, initialZ), LOTRBlocks.GLOWSTONE_ORE.get().defaultBlockState(), Constants.BlockFlags.DEFAULT);
//			break;
//		case 3:
//			world.setBlock(new BlockPos(initialX, 83, initialZ), LOTRBlocks.GULDURIL_ORE_STONE.get().defaultBlockState(), Constants.BlockFlags.DEFAULT);
//
//		}
	}

	int makeYvalue(int y) {
		return initialY + y;
	}

	int makeXvalue(int x, int z) {
		return initialX + x;
//		switch(rot) {
//		case 0:
//			return initialX-x+((int) Math.ceil(offset.X/2));
//		case 1:
//			return initialX-z+((int) Math.ceil(offset.X/2));
//		case 2:
//			return initialX+x-((int) Math.ceil(offset.X/2));
//		case 3:
//			return initialX+z-((int) Math.ceil(offset.X/2));
//		}
//		return initialX;
	}

	int makeZvalue(int x, int z) {
		return initialZ + z;
//		switch(rot) {
//		case 0:
//			return initialZ+z;
//		case 1:
//			return initialZ-x;
//		case 2:
//			return initialZ-z;
//		case 3:
//			return initialZ+x;
//		}
//		return initialZ;
	}

	public void doFileGen(ISeedReader world, Random random, StructureFile file) {
		setSignTextFromFile(world, random, file);
		spawnEntitiesFile(world, random, file);
	}

	public void doNBTGen(ISeedReader world, Random random, BlockState blockstate, int x, int y, int z, CompoundNBT nbt) {
		if(blockstate.getBlock()==Blocks.BARREL||blockstate.getBlock()==Blocks.CHEST) {
			setLockableLootTileEntityContents(world, random, x, y, z, nbt);
		}
	}

	public void doKegGen(ISeedReader world, Random random, BlockState blockstate, int x, int y, int z, List<RegistryObject<Item>>  list) {
		if(blockstate.getBlock()==LOTRBlocks.KEG.get()) {
			setKegTileEntityContents(world, random, x, y, z, list);
		}
	}

	public void setAllBelowToBlock(ISeedReader world, int x, int y, int z, Block block) {
		for (int level = y-1; isNotSolid(world, x, level, z) && (makeYvalue(level)) >= 0; level--) {
			setBlockAndMetadata(world, x, level, z, block);
		}
	}

	public boolean setBelowToBlockIfBlock(ISeedReader world, int x, int y, int z, Block newBlock, Block oldBlock) {
		BlockPos pos = new BlockPos(makeXvalue(x,z), makeYvalue(y), makeZvalue(x,z));
		if(world.getBlockState(pos.below()).getBlock().equals(oldBlock)) {
			setBlockAndMetadata(world, pos, newBlock);
			return true;
		}
		return false;
	}

	public void fixBelowBlocks(ISeedReader world, int x, int y, int z, Block newBlock, Block oldBlock) {
		if(!setBelowToBlockIfBlock(world, x, y, z, newBlock, oldBlock)) {
			setAllBelowToBlock(world, x, y, z, oldBlock);
		}
	}
	
	public boolean isNotSolid(ISeedReader world, int x, int y, int z) {
		BlockState belowState = world.getBlockState(new BlockPos(makeXvalue(x,z), makeYvalue(y), makeZvalue(x,z)));
		return belowState.getBlock().equals(Blocks.AIR)||(belowState.getBlock() instanceof BushBlock)||belowState.getBlock() instanceof FlowingFluidBlock;
	}

	public boolean isOpaque(ISeedReader world, int x, int y, int z) {
		BlockState belowState = world.getBlockState(new BlockPos(makeXvalue(x,z), makeYvalue(y), makeZvalue(x,z)));
		return !belowState.isSolidRender(world, new BlockPos(makeXvalue(x,z), makeYvalue(y), makeZvalue(x,z)));
	}

	public void setGrassToDirt(ISeedReader world, int x, int y, int z) {
		LOTRFeatures.setGrassToDirtBelow(world, new BlockPos(makeXvalue(x,z), makeYvalue(y), makeZvalue(x,z)));
	}

	public void setBlockAndMetadata(ISeedReader world, int x, int y, int z, Block block) {
		setBlockAndMetadata(world, new BlockPos(makeXvalue(x,z), makeYvalue(y), makeZvalue(x,z)), block);
	}
	public void setBlockAndMetadata(ISeedReader world, BlockPos pos, Block block) {
		setBlockAndMetadata(world, pos, block.defaultBlockState());
	}

	public void setBlockAndMetadata(ISeedReader world, int x, int y, int z, BlockState blockstate) {
	    setBlockAndMetadata(world, new BlockPos(makeXvalue(x,z), makeYvalue(y), makeZvalue(x,z)), blockstate);
	}
	public void setBlockAndMetadata(ISeedReader world, BlockPos pos, BlockState blockstate) {
	    world.setBlock(pos, blockstate, Constants.BlockFlags.DEFAULT);
	}

	public StructureFile getStructure() {
		StructureFile file = StructureFileHandler.getStructureFile(fileName(), ServerLifecycleHooks.getCurrentServer());
		if(file == null) {
			ExtendedLog.error("LOADED NULL STRUCTURE FILE!: "+fileName());
		}
		return file;
	}
	public StructureFile getStructure(String name) {
		StructureFile file = StructureFileHandler.getStructureFile(name, ServerLifecycleHooks.getCurrentServer());
		if(file == null) {
			ExtendedLog.error("LOADED NULL STRUCTURE FILE!: "+name);
		}
		return file;
	}

	public BlockPos rPToBlockPos(RelativePosition pos) {
		return new BlockPos(makeXvalue(pos.X,pos.Z), makeYvalue(pos.Y), makeZvalue(pos.X,pos.Z));
	}

	public void setLockableLootTileEntityContents(ISeedReader world, Random random, int x, int y, int z, CompoundNBT nbt) {
		if(nbt.contains("loot-table")) {
			String table = nbt.getString("loot-table");
			if(!table.equals("EMPTY")) {
				setLockableLootTileEntityContents(world, random, x, y, z, nbt.getString("loot-table"));
			}
		}
		else {
			ExtendedLog.error("MISSING LOOTTABLE FOR X: "+x+", Y: "+y+", Z:"+z+", NBT: "+nbt.toString());
		}
	}

	public void setLockableLootTileEntityContents(ISeedReader world, Random random, int x, int y, int z, String table) {
		setLockableLootTileEntityContents(world, random, new BlockPos(makeXvalue(x,z), makeYvalue(y), makeZvalue(x,z)), table);
	}
	
	public void setLockableLootTileEntityContents(ISeedReader world, Random random, BlockPos pos, String table) {
		TileEntity tileentity = world.getBlockEntity(pos);
		ResourceLocation resource = makeReasourcePath(table);
		if (tileentity instanceof LockableLootTileEntity) {
			((LockableLootTileEntity)tileentity).setLootTable(resource, random.nextLong());
		}
		else {
			ExtendedLog.error("FAILED TO SET LOOTTABLE FOR: " + pos.toString() + ", " + resource + ", NOT INSTANCE OF " + LockableLootTileEntity.class.getName());
		}
	}

	private ResourceLocation makeReasourcePath(String part) {
		return new ResourceLocation(LotrExtendedMod.MODID, "chests/" + part);
	}
	
	public void setKegTileEntityContents(ISeedReader world, Random random, int x, int y, int z, List<RegistryObject<Item>> pool) {
		setKegTileEntityContents(world, random, new BlockPos(makeXvalue(x,z), makeYvalue(y), makeZvalue(x,z)), pool);
	}

	public void setKegTileEntityContents(ISeedReader world, Random random, BlockPos pos, List<RegistryObject<Item>> pool) {
		TileEntity tileentity = world.getBlockEntity(pos);
		if (tileentity instanceof KegTileEntity) {
			if(random.nextInt(5)==0) { return; }
			((KegTileEntity)tileentity).fillBrewedWith(ExtendedKegDrinks.SetDrink(random, pool));
		}
		else {
			ExtendedLog.error("FAILED TO SET BREW FOR: " + pos.toString() + ", " + ", NOT INSTANCE OF " + KegTileEntity.class.getName());
		}
	}

	public void setSignTextFromFile(ISeedReader world, Random random, StructureFile file) {
		if(file.getSigns().isEmpty()) {
			return;
		}
		Map<Integer, RelativePosition> signs = new HashMap<>();
		for (int i = 0; i < file.getSigns().size(); i++) {
			CompoundNBT nbt = file.getSigns().getCompound(i);
			int num = nbt.getInt(StructureScanner.signNumberHolder);
			RelativePosition rpos = StructureFile.readBlockPos(nbt.getCompound(StructureScanner.relativeLocationHolder));
			if(signs.containsKey(num)) {
				ExtendedLog.error("DUPLICATE SIGN COUNT FOUND IN STRUCTURE FILE: "+file.name);
				return;
			}
			signs.put(num, rpos);
		}
		for (Entry<Integer, RelativePosition> sign : signs.entrySet()) {
			setSignTextFromFileData(world, random, rPToBlockPos(sign.getValue()), sign.getKey());
		}
	}

	public abstract void setSignTextFromFileData(ISeedReader world, Random random, BlockPos blockPos, int num);

	public void setSignTileEntity(ISeedReader world, BlockPos pos, List<String> text) {
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
	
	public void spawnEntitiesFile(ISeedReader world, Random random, StructureFile file) {
		if(file.getEntities().isEmpty()) {
			return;
		}
		Map<String, Map<Integer, RelativePosition>> entities = new HashMap<>();
		for (int i = 0; i < file.getEntities().size(); i++) {
			CompoundNBT nbt = file.getEntities().getCompound(i);
			int spawnNumber = nbt.getInt("spawn");
			String entityType = nbt.getString("entity");
			RelativePosition rpos = StructureFile.readBlockPos(nbt.getCompound(StructureScanner.relativeLocationHolder));
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
					break;
				}
				ExtendedEntitySpawning.spawnEntity(world, entity.getKey(), rPToBlockPos(entity.getValue().get(1)));
				break;
			}
			if(entity.getValue().size()>4||entity.getValue().size()<1) {
				ExtendedLog.error("FOUND TOO MANY/TOO LITTLE KEYS FOR: "+file.name+", entity: "+entity.getKey()+", keys count: "+entity.getValue().size());
				ExtendedLog.error("ENTITY NOT SUMMONED!");
				break;
			}
			int pick = random.nextInt(100);
			if(entity.getValue().size()==2) {
				if(pick<90) {
					ExtendedEntitySpawning.spawnEntity(world, entity.getKey(), rPToBlockPos(entity.getValue().get(1)));
				} else {
					ExtendedEntitySpawning.spawnEntity(world, entity.getKey(), rPToBlockPos(entity.getValue().get(2)));
				}
				break;
			}
			if(entity.getValue().size()==3) {
				if(pick<90) {
					ExtendedEntitySpawning.spawnEntity(world, entity.getKey(), rPToBlockPos(entity.getValue().get(1)));
				} else {
					pick = random.nextInt(100);
					if(pick<90) {
						ExtendedEntitySpawning.spawnEntity(world, entity.getKey(), rPToBlockPos(entity.getValue().get(2)));
					} else {
						ExtendedEntitySpawning.spawnEntity(world, entity.getKey(), rPToBlockPos(entity.getValue().get(3)));
					}
				}
				break;
			}
			if(entity.getValue().size()==4) {
				if(pick<90) {
					ExtendedEntitySpawning.spawnEntity(world, entity.getKey(), rPToBlockPos(entity.getValue().get(1)));
				} else {
					pick = random.nextInt(100);
					if(pick<90) {
						ExtendedEntitySpawning.spawnEntity(world, entity.getKey(), rPToBlockPos(entity.getValue().get(2)));
					} else {
						pick = random.nextInt(100);
						if(pick<90) {
							ExtendedEntitySpawning.spawnEntity(world, entity.getKey(), rPToBlockPos(entity.getValue().get(3)));
						} else {
							ExtendedEntitySpawning.spawnEntity(world, entity.getKey(), rPToBlockPos(entity.getValue().get(4)));
						}
					}
				}
				break;
			}
			return;
		}
	}
}
