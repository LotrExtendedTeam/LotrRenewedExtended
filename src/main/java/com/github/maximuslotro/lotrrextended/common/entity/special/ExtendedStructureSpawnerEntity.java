package com.github.maximuslotro.lotrrextended.common.entity.special;

import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.common.config.ExtendedClientConfig;
import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;
import com.github.maximuslotro.lotrrextended.common.spawners.ExtendedStructureEntitySpawnManager;
import com.github.maximuslotro.lotrrextended.common.spawners.ExtendedStructureEntitySpawner;
import com.github.maximuslotro.lotrrextended.common.spawners.SpawnerType;
import com.github.maximuslotro.lotrrextended.common.utils.AxisAlignedBBUtils;
import com.github.maximuslotro.lotrrextended.mixins.lotr.common.entity.npc.MixinNPCEntityInvoker;

import lotr.common.entity.npc.ExtendedNPCEntity;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.entity.npc.data.ExtendedNpcMarriageHolder;
import lotr.common.entity.npc.data.NPCGenderProvider;
import lotr.common.init.ExtendedEntities;
import lotr.common.init.ExtendedItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap.Type;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

public class ExtendedStructureSpawnerEntity extends Entity{
	private static final DataParameter<String> DATA_SPAWNER_TYPE = EntityDataManager.defineId(ExtendedStructureSpawnerEntity.class, DataSerializers.STRING);
	private static final DataParameter<BlockPos> DATA_SPAWNER_CBoundMin = EntityDataManager.defineId(ExtendedStructureSpawnerEntity.class, DataSerializers.BLOCK_POS);
	private static final DataParameter<BlockPos> DATA_SPAWNER_CBoundMax = EntityDataManager.defineId(ExtendedStructureSpawnerEntity.class, DataSerializers.BLOCK_POS);

	private static final DataParameter<BlockPos> DATA_SPAWNER_SBoundMin = EntityDataManager.defineId(ExtendedStructureSpawnerEntity.class, DataSerializers.BLOCK_POS);
	private static final DataParameter<BlockPos> DATA_SPAWNER_SBoundMax = EntityDataManager.defineId(ExtendedStructureSpawnerEntity.class, DataSerializers.BLOCK_POS);

	private ExtendedStructureEntitySpawner spawnerInstance = ExtendedStructureEntitySpawner.EMPTY;
	private ResourceLocation spawnerType;
	private static boolean isInvisible = true; // Always true on Servers
	private boolean queueToRemove = false;
	private boolean firstTick = true;
	public double spin = 0;
	public double spinO = 0;
	public ExtendedStructureSpawnerEntity(EntityType<? extends ExtendedStructureSpawnerEntity> pType, World pLevel) {
		super(pType, pLevel);
		this.noCulling = true;
	}

	public ExtendedStructureSpawnerEntity(World pLevel) {
		this(ExtendedEntities.STRUCTURE_ENTITY_SPAWNER.get(), pLevel);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public boolean shouldRender(double pX, double pY, double pZ) {
		return true;
	}

	@Override
	public void tick() {
		this.noPhysics = true;
		super.tick();
		this.noPhysics = false;
		this.setNoGravity(true);
		this.setDeltaMovement(Vector3d.ZERO);
		if(this.level.isClientSide()) {
			this.spinO = this.spin;
			this.spin = (this.spin + 0.1D) % 360.0D;
		}
		setInvulnerable(true);
		if(!level.isClientSide()) {
			if(queueToRemove) {
				this.remove();
			}else {
				if (spawnerInstance.isEmptySpawner()) {
					if (spawnerType!=null && !spawnerType.equals(LotrExtendedMod.EMPTY) && !ExtendedServerConfig.enableDevMode.get()) {
						setSpawnerFromResourceLocation();
					}
				}
				if(!spawnerInstance.isEmptySpawner() && spawnerInstance.shouldTick() && !spawnerInstance.getSpawnType().isUnknown()) {
					if(this.tickCount % spawnerInstance.getEntitySpawnTickRate() == 0 || firstTick) {
						if(trySpawningEntities((ServerWorld) this.level)) {
							firstTick = false;
						}
					}
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	private boolean trySpawningEntities(ServerWorld world) {
		// Check if spawner bounds exist
		if(!world.hasChunksAt(getCMinBlockPos(), getCMaxBlockPos())) {
			return false;
		}
		// Check if player has a proper distance from the spawner
		if(world.hasNearbyAlivePlayer(getX(), getY(), getZ(), spawnerInstance.getPlayerMinSpawnDistance())) {
			//world.players().forEach(player -> player.sendMessage(new StringTextComponent("Player to close"), Util.NIL_UUID));
			return false;
		} else if (!world.hasNearbyAlivePlayer(getX(), getY(), getZ(), spawnerInstance.getPlayerMaxSpawnDistance())) {
			//world.players().forEach(player -> player.sendMessage(new StringTextComponent("Player to far away"), Util.NIL_UUID));
			return false;
		}
		// Get spawn cap if not single type
		int nearbyEntitySpawnCount = 0;
		if(spawnerInstance.getSpawnType().isShouldCheckBoundries()) {
			//System.out.println("Running Area Checks");
			AxisAlignedBB checkBox = new AxisAlignedBB(getCMinBlockPos(), getCMaxBlockPos());
			Predicate<LivingEntity> pred = (entity) ->{
				return ExtendedStructureEntitySpawnManager.INSTANCE.getEntities(spawnerInstance.getId().toString(), spawnerInstance.getEntries())
						.contains(entity.getType().getRegistryName().toString());
			};
			nearbyEntitySpawnCount = world.getEntitiesOfClass(LivingEntity.class, checkBox, pred).size();
		}
		//System.out.println("Suscessfully Ran Area Checks: "+nearbyEntitySpawnCount);
		for(int roll = 0; roll < spawnerInstance.getPoolRolls(); roll++) {
			if(spawnerInstance.shouldCheckRandomDiscardChance() && random.nextInt(100)+1 <= spawnerInstance.getRandomDiscardChance()) {
				continue;
			}
			// Try spawning with 16 re-roles in invalid spawn found
			for(int attempt = 0; attempt <16; attempt++) {
				// Check spawn cap number if not single type
				//System.out.println("Main Loop, roll:"+roll+", attempt:"+attempt);
				if(nearbyEntitySpawnCount < spawnerInstance.getLocalSpawnCap() || !spawnerInstance.getSpawnType().isShouldCheckBoundries()) {
					int x;
					int y;
					int z;
					// Set spawn cords based upon spawner type
					if(spawnerInstance.getSpawnType().isShouldCheckBoundries() && !shouldSpawnInside()) {
						x = MathHelper.floor(getX()) + MathHelper.nextInt(random, -spawnerInstance.getSpawnBoundaries().horizontalBounds, spawnerInstance.getSpawnBoundaries().horizontalBounds);
						z = MathHelper.floor(getZ()) + MathHelper.nextInt(random, -spawnerInstance.getSpawnBoundaries().horizontalBounds, spawnerInstance.getSpawnBoundaries().horizontalBounds);
						y = world.getHeight(Type.MOTION_BLOCKING_NO_LEAVES, x, z);
						if(!spawnerInstance.getSpawnBoundaries().isInYRange(y, MathHelper.floor(getY()))) {
							//System.out.println("Failed Y bounds check");
							continue; // Try spawning again
						}
					}else {
						x = MathHelper.floor(getX());
						y = MathHelper.floor(getY());
						z = MathHelper.floor(getZ());
					}
					// Set spawn position
					BlockPos footPos = new BlockPos(x, y, z);
					// Check if spawn position is valid for boundary check spawns, or when inside spawning instead of bound spawning
					if(spawnerInstance.getSpawnType().isShouldCheckBoundries()) {
						// Add check for inside spawning in a bounds check spawn type
						if(spawnerInstance.insideSpawning()) {
							BlockState potentialFloorBlock1 = world.getBlockState(footPos);
							if(!Block.isFaceFull(potentialFloorBlock1.getCollisionShape(world, footPos, ISelectionContext.empty()), Direction.UP) || !potentialFloorBlock1.getMaterial().isSolid()) {
								BlockState potentialFloorBlock2 = world.getBlockState(footPos.below());
								if(!Block.isFaceFull(potentialFloorBlock2.getCollisionShape(world, footPos.below(), ISelectionContext.empty()), Direction.UP) || !potentialFloorBlock2.getMaterial().isSolid()) {
									BlockState potentialFloorBlock3 = world.getBlockState(footPos.below().below());
									if(!Block.isFaceFull(potentialFloorBlock3.getCollisionShape(world, footPos.below().below(), ISelectionContext.empty()), Direction.UP) || !potentialFloorBlock3.getMaterial().isSolid()) {
										continue; // Try spawning again
									}else {
										BlockState footBlock = world.getBlockState(footPos);
										BlockState headBlock = world.getBlockState(footPos.above());
										if(!footBlock.getBlock().isPossibleToRespawnInThis() || !headBlock.getBlock().isPossibleToRespawnInThis()) {
											continue; // Try spawning again
										}
									}
								}
								else {
									BlockState footBlock = world.getBlockState(footPos.above());
									BlockState headBlock = world.getBlockState(footPos.above().above());
									if(!footBlock.getBlock().isPossibleToRespawnInThis() || !headBlock.getBlock().isPossibleToRespawnInThis()) {
										continue; // Try spawning again
									}
								}
							}else {
								BlockState footBlock = world.getBlockState(footPos);
								BlockState headBlock = world.getBlockState(footPos.above());
								if(!footBlock.getBlock().isPossibleToRespawnInThis() || !headBlock.getBlock().isPossibleToRespawnInThis()) {
									continue; // Try spawning again
								}
							}
						} else {
							BlockState floorBlock = world.getBlockState(new BlockPos(x, y -1, z));
							if(!Block.isFaceFull(floorBlock.getCollisionShape(world, new BlockPos(x, y -1, z), ISelectionContext.empty()), Direction.UP)
									|| !floorBlock.getMaterial().isSolid()) {
								//System.out.println("Failed spawn check fullface+solid");
								continue; // Try spawning again
							}
							BlockState footBlock = world.getBlockState(footPos);
							BlockState headBlock = world.getBlockState(new BlockPos(x, y +1, z));
							if(!footBlock.getBlock().isPossibleToRespawnInThis() || !headBlock.getBlock().isPossibleToRespawnInThis()) {
								//System.out.println("Failed spawn check isPossibleToRespawnInThis");
								continue; // Try spawning again
							}
						}
					}
					//System.out.println("Successfully ran Spawn checks");
					List<EntityType<?>> generatedSpawns = spawnerInstance.getSpawns(random);
					for(EntityType<?> generatedSpawn : generatedSpawns) {
						//System.out.println("Attempting to Spawn Entity");
						if(spawnerInstance.getSpawnType().equals(SpawnerType.SINGLE_FAMILY)) {
							Entity entityMale = generatedSpawn.create(world);
							Entity entityFemale = generatedSpawn.create(world);
							if(!(entityMale instanceof NPCEntity)||!(entityFemale instanceof NPCEntity)) {

							}
							NPCEntity entityMaleNpc = (NPCEntity) entityMale;
							NPCEntity entityFemaleNpc = (NPCEntity) entityFemale;
							if(((MixinNPCEntityInvoker)entityMaleNpc).invokeGenderProvider().equals(NPCGenderProvider.MALE)
									||((MixinNPCEntityInvoker)entityMaleNpc).invokeGenderProvider().equals(NPCGenderProvider.FEMALE) || ((MixinNPCEntityInvoker)entityFemaleNpc).invokeGenderProvider().equals(NPCGenderProvider.MALE)
									||((MixinNPCEntityInvoker)entityFemaleNpc).invokeGenderProvider().equals(NPCGenderProvider.FEMALE)) {
								ExtendedLog.error("Failed spawning %s because it does not support multiple genders - %s", generatedSpawn.getRegistryName().toString(), spawnerInstance.getId().toString());
								return false;
							}
							entityMaleNpc.getPersonalInfo().setMale(true);
							entityMaleNpc.getPersonalInfo().setName(((MixinNPCEntityInvoker)entityMaleNpc).invokeNameGenerator().generateName(random, true));
							entityFemaleNpc.getPersonalInfo().setMale(false);
							entityFemaleNpc.getPersonalInfo().setName(((MixinNPCEntityInvoker)entityFemaleNpc).invokeNameGenerator().generateName(random, false));
							entityMaleNpc.moveTo(footPos, random.nextFloat() * 360.0F, 0.0F);
							entityFemaleNpc.moveTo(footPos, random.nextFloat() * 360.0F, 0.0F);
							entityMaleNpc.finalizeSpawn(world, world.getCurrentDifficultyAt(footPos), SpawnReason.SPAWNER, null, null);
							entityMaleNpc.setPersistenceRequired();
							entityFemaleNpc.finalizeSpawn(world, world.getCurrentDifficultyAt(footPos), SpawnReason.SPAWNER, null, null);
							entityFemaleNpc.setPersistenceRequired();
							if(!spawnerInstance.isSetHomePositionOnSpawn()) {
								entityMaleNpc.clearHomePos();
								entityFemaleNpc.clearHomePos();
							}else {
								entityMaleNpc.restrictTo(this.blockPosition(), spawnerInstance.getHomeRadius());
								entityFemaleNpc.restrictTo(this.blockPosition(), spawnerInstance.getHomeRadius());
							}
							ExtendedNpcMarriageHolder marriageInfoMale = ((ExtendedNPCEntity) entityMaleNpc).getMarriageInfo();
							marriageInfoMale.maleID=entityMaleNpc.getUUID();
							marriageInfoMale.femaleID=entityFemaleNpc.getUUID();
							marriageInfoMale.spouseID=entityFemaleNpc.getUUID();
							ExtendedNpcMarriageHolder marriageInfoFemale = ((ExtendedNPCEntity) entityFemaleNpc).getMarriageInfo();
							marriageInfoFemale.maleID=entityMaleNpc.getUUID();
							marriageInfoFemale.femaleID=entityFemaleNpc.getUUID();
							marriageInfoFemale.spouseID=entityMaleNpc.getUUID();
							world.addFreshEntityWithPassengers(entityMaleNpc);
							world.addFreshEntityWithPassengers(entityFemaleNpc);
							// Break out of entire loop
							attempt = 100;
							roll = 100;
							//System.out.println("Spawned couple successfully");
							break;
						}else {
							// Moved code from ExtendedStructureSpawnerTileEntity
							Entity entity = generatedSpawn.create(world);
							entity.moveTo(footPos, random.nextFloat() * 360.0F, 0.0F);
							// Set entity info based upon the superclass
							if(entity instanceof NPCEntity) {
								((NPCEntity)entity).finalizeSpawn(world, world.getCurrentDifficultyAt(footPos), SpawnReason.SPAWNER, null, null);
								((NPCEntity)entity).setPersistenceRequired();
								if(!spawnerInstance.isSetHomePositionOnSpawn()) {
									((NPCEntity)entity).clearHomePos();
								}else {
									if(spawnerInstance.isSetHomeRandom()) {
										((NPCEntity)entity).restrictTo(footPos, spawnerInstance.getHomeRadius());
									}else {
										((NPCEntity)entity).restrictTo(this.blockPosition(), spawnerInstance.getHomeRadius());
									}
								}
							}else if(entity instanceof MobEntity) {
								((MobEntity)entity).finalizeSpawn(world, world.getCurrentDifficultyAt(footPos), SpawnReason.SPAWNER, null, null);
								((MobEntity)entity).setPersistenceRequired();
								if(!spawnerInstance.isSetHomePositionOnSpawn()) {
									((MobEntity)entity).restrictTo(BlockPos.ZERO, -1);
								}else {
									if(spawnerInstance.isSetHomeRandom()) {
										((MobEntity)entity).restrictTo(footPos, spawnerInstance.getHomeRadius());
									}else {
										((MobEntity)entity).restrictTo(this.blockPosition(), spawnerInstance.getHomeRadius());
									}
								}
							}else {
								ExtendedLog.error("Failed spawning %s because it is not a supported Entity Class - %s", generatedSpawn.getRegistryName().toString(), spawnerInstance.getId().toString());
								return false;
							}
							world.addFreshEntityWithPassengers(entity);
							nearbyEntitySpawnCount++;
						}
						//System.out.println("Spawned entity successfully: "+nearbyEntitySpawnCount);
						if(nearbyEntitySpawnCount >= spawnerInstance.getLocalSpawnCap() && spawnerInstance.getSpawnType().isShouldCheckBoundries()) {
							//System.out.println("Spawning surpasses cap, exiting!");
							attempt = 100;
							roll = 100;
							break;
						}
					}
				}else {
					//System.out.println("Main Loop failed check, exiting!");
					attempt = 100;
					roll = 100;
					break;
				}
				//System.out.println("Spawned entity ");
				// Use up attempts because we have successfully spawned
				attempt = 100;
			}
			//System.out.println("Closing Attempt loop");
		}
		//System.out.println("Closing Attempt roll");
		if(spawnerInstance.getSpawnType().isSingleSpawnType()) {
			//System.out.println("Removing spawner because of single type affiliations!");
			queueToRemove=true;
		}
		return true;
	}

	private boolean shouldSpawnInside() {
		return spawnerInstance.insideSpawning() && (this.random.nextInt(100)+1)<=spawnerInstance.getinsideSpawningChance();
	}

	private void setSpawnerFromResourceLocation() {
		spawnerInstance = ExtendedStructureEntitySpawnManager.INSTANCE.get(spawnerType);
		if(spawnerInstance.isEmptySpawner()) {
			ExtendedLog.warn("Structure Spawner loaded and invalid SpawnerType %s, spawner has been set to default %s preset!", spawnerType.toString(), LotrExtendedMod.EMPTY.toString());
		}
		this.getEntityData().set(DATA_SPAWNER_TYPE, spawnerType.toString());
		reCalculateBounds();
	}

	public void setSpawnerToDefaultPreset(ResourceLocation preset, boolean isCreation) {
		spawnerType = preset;
		if(!isCreation) {
			setSpawnerFromResourceLocation();
		}else {
			spawnerInstance = ExtendedStructureEntitySpawner.EMPTY;
		}
	}

	@Override
	protected void readAdditionalSaveData(CompoundNBT pCompound) {
		if(pCompound.contains("spawnerData")) {
			spawnerInstance = ExtendedStructureEntitySpawnManager.deserialize(ExtendedStructureEntitySpawnManager.GSON, pCompound.getString("spawnerData"), ExtendedStructureEntitySpawner.class);
			this.getEntityData().set(DATA_SPAWNER_TYPE, spawnerInstance.getId().toString());
			reCalculateBounds();
		} else if(pCompound.contains("setSpawnerType")) {
			spawnerType = new ResourceLocation(pCompound.getString("setSpawnerType"));
			this.getEntityData().set(DATA_SPAWNER_TYPE, spawnerType.toString());
			reCalculateBounds();
		}
		if(pCompound.contains("firstTick")) {
			this.firstTick = pCompound.getBoolean("firstTick");
		}
	}

	@Override
	protected void addAdditionalSaveData(CompoundNBT pCompound) {
		if(!spawnerInstance.isEmptySpawner() && spawnerInstance.isCustom()) {
			pCompound.putString("spawnerData", ExtendedStructureEntitySpawnManager.serialize(ExtendedStructureEntitySpawnManager.GSON, spawnerInstance));
		} else if(!spawnerInstance.isEmptySpawner() && !spawnerInstance.isCustom()) {
			pCompound.putString("setSpawnerType", spawnerInstance.getId().toString());
		} else if(spawnerInstance.isEmptySpawner() && spawnerType !=null){
			pCompound.putString("setSpawnerType", spawnerType.toString());
		}else {
			pCompound.putString("setSpawnerType", LotrExtendedMod.EMPTY.toString());
		}
		if(!this.firstTick) {
			pCompound.putBoolean("firstTick", false);
		}
	}

	@Override
	public ActionResultType interact(PlayerEntity pPlayer, Hand pHand) {
		if(!pPlayer.level.isClientSide() && pPlayer.isCreative()) {
			pPlayer.sendMessage(new StringTextComponent("Spawner Raw Type: ").withStyle(TextFormatting.GREEN)
					.append(new StringTextComponent(spawnerType!=null ? spawnerType.getPath() : "Unset").withStyle(TextFormatting.WHITE)), Util.NIL_UUID);
			pPlayer.sendMessage(new StringTextComponent("Spawner Instanced: ").withStyle(TextFormatting.GREEN)
					.append(new StringTextComponent(spawnerInstance.getId().getPath()).withStyle(TextFormatting.WHITE)), Util.NIL_UUID);
			pPlayer.sendMessage(new StringTextComponent("Is Empty Spawner: ").withStyle(TextFormatting.GREEN)
					.append(new StringTextComponent(Boolean.toString(spawnerInstance.isEmptySpawner())).withStyle(TextFormatting.WHITE))
					.append(new StringTextComponent(", Is Ticking: ").withStyle(TextFormatting.GREEN))
					.append(new StringTextComponent(Boolean.toString(spawnerInstance.shouldTick())).withStyle(TextFormatting.WHITE)), Util.NIL_UUID);
			pPlayer.sendMessage(new StringTextComponent("Local Spawn Cap: ").withStyle(TextFormatting.GREEN)
					.append(new StringTextComponent(Integer.toString(spawnerInstance.getLocalSpawnCap())).withStyle(TextFormatting.WHITE))
					.append(new StringTextComponent(", Spawn TickRate: ").withStyle(TextFormatting.GREEN))
					.append(new StringTextComponent(Integer.toString(spawnerInstance.getEntitySpawnTickRate())).withStyle(TextFormatting.WHITE)), Util.NIL_UUID);
			pPlayer.sendMessage(new StringTextComponent("Inside Spawning: ").withStyle(TextFormatting.GREEN)
					.append(new StringTextComponent(Boolean.toString(spawnerInstance.insideSpawning())).withStyle(TextFormatting.WHITE))
					.append(new StringTextComponent(", Inside Chance: ").withStyle(TextFormatting.GREEN))
					.append(new StringTextComponent(Integer.toString(spawnerInstance.getinsideSpawningChance())).withStyle(TextFormatting.WHITE)), Util.NIL_UUID);
			String str = "";
			for(String entity : ExtendedStructureEntitySpawnManager.INSTANCE.getEntities(spawnerInstance.getId().toString(), spawnerInstance.getEntries())) {
				if(str.equals("")) {
					str = entity;
				}else {
					str = str+ ", "+entity;
				}
			}
			pPlayer.sendMessage(new StringTextComponent("Pool Entities: ").withStyle(TextFormatting.GREEN)
					.append(new StringTextComponent(str).withStyle(TextFormatting.WHITE)), Util.NIL_UUID);
			if(spawnerInstance.shouldTick()) {
				int tickModulus = this.tickCount % spawnerInstance.getEntitySpawnTickRate();
				double percent = tickModulus/(float)spawnerInstance.getEntitySpawnTickRate() ;
				pPlayer.sendMessage(new StringTextComponent("Current tick times: ").withStyle(TextFormatting.GREEN)
						.append(new StringTextComponent(Integer.toString(tickModulus)).withStyle(TextFormatting.WHITE)), Util.NIL_UUID);
				pPlayer.sendMessage(new StringTextComponent("Current spawns percents: ").withStyle(TextFormatting.GREEN)
						.append(new StringTextComponent(String.format(Locale.US,"%.2f", percent*100)).withStyle(TextFormatting.WHITE)), Util.NIL_UUID);
				pPlayer.sendMessage(new StringTextComponent("Current spawns sec till: ").withStyle(TextFormatting.GREEN)
						.append(new StringTextComponent(String.format(Locale.US,"%.2f", (1-percent) * (spawnerInstance.getEntitySpawnTickRate()/20.0F))).withStyle(TextFormatting.WHITE)), Util.NIL_UUID);
				pPlayer.sendMessage(new StringTextComponent("Current spawns min till: ").withStyle(TextFormatting.GREEN)
						.append(new StringTextComponent(String.format(Locale.US,"%.2f", (1-percent) * ((spawnerInstance.getEntitySpawnTickRate()/20.0F)/60.0F))).withStyle(TextFormatting.WHITE)), Util.NIL_UUID);
			}
			pPlayer.sendMessage(new StringTextComponent("In future will open GUI"), Util.NIL_UUID);
		}
		return ActionResultType.sidedSuccess(this.level.isClientSide);
	}

	@Override
	public ItemStack getPickedResult(RayTraceResult target) {
		ItemStack stack = new ItemStack(ExtendedItems.ENTITY_SPAWNER.get());
		if(!getSpawnerName().equals(LotrExtendedMod.EMPTY.toString())) {
			if(stack.hasTag()) {
				CompoundNBT nbt = stack.getTag();
				nbt.putString("setSpawnerType", getSpawnerName());
				stack.setTag(nbt);
			} else {
				CompoundNBT nbt = new CompoundNBT();
				nbt.putString("setSpawnerType", getSpawnerName());
				stack.setTag(nbt);
			}
		}
		return stack;
	}
	@Override
	public void setInvisible(boolean pInvisible) {
		if(!this.level.isClientSide()) {
			super.setInvisible(pInvisible);
		}
	}

	@Override
	public boolean isInvisible() {
		if(!this.level.isClientSide()) {
			return super.isInvisible();
		}
		return isInvisible;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean isInvisibleTo(PlayerEntity pPlayer) {
		if (pPlayer.isSpectator() || pPlayer.isCreative() || ExtendedClientConfig.enableDevMode.get()) {
			isInvisible = false;
			return false;
		}
		isInvisible = true;
		return true;
	}

	public BlockPos getCMinBlockPos() {
		return this.getEntityData().get(DATA_SPAWNER_CBoundMin);
	}
	public BlockPos getCMaxBlockPos() {
		return this.getEntityData().get(DATA_SPAWNER_CBoundMax);
	}
	public BlockPos getSMinBlockPos() {
		return this.getEntityData().get(DATA_SPAWNER_SBoundMin);
	}
	public BlockPos getSMaxBlockPos() {
		return this.getEntityData().get(DATA_SPAWNER_SBoundMax);
	}
	public String getSpawnerName() {
		return this.getEntityData().get(DATA_SPAWNER_TYPE);
	}

	public void reCalculateBounds() {
		this.getEntityData().set(DATA_SPAWNER_CBoundMin, spawnerInstance.getCheckBoundaries().getMinPos(MathHelper.floor(getX()), MathHelper.floor(getY()), MathHelper.floor(getZ())));
		this.getEntityData().set(DATA_SPAWNER_CBoundMax, spawnerInstance.getCheckBoundaries().getMaxPos(MathHelper.floor(getX()), MathHelper.floor(getY()), MathHelper.floor(getZ())));
		this.getEntityData().set(DATA_SPAWNER_SBoundMin, spawnerInstance.getSpawnBoundaries().getMinPos(MathHelper.floor(getX()), MathHelper.floor(getY()), MathHelper.floor(getZ())));
		this.getEntityData().set(DATA_SPAWNER_SBoundMax, spawnerInstance.getSpawnBoundaries().getMaxPos(MathHelper.floor(getX()), MathHelper.floor(getY()), MathHelper.floor(getZ())));
	}

	public AxisAlignedBB getSpawnArea() {
		return AxisAlignedBBUtils.makeCroppedBB(getSMinBlockPos(), 0, 0, getSMaxBlockPos(), 1, 1);
	}

	public AxisAlignedBB getCheckArea() {
		return AxisAlignedBBUtils.makeCroppedBB(getCMinBlockPos(), 0, 0, getCMaxBlockPos(), 1, 1);
	}

	@Override
	public ITextComponent getDisplayName() {
		if(!getSpawnerName().equals(LotrExtendedMod.EMPTY.toString())) {
			return new StringTextComponent("Spawner type: "+getSpawnerName());
		}
		return this.getName();
	}

	@Override
	public void remove() {
		if(!queueToRemove) {
			this.level.playSound((PlayerEntity)null, this.getX(), this.getY(), this.getZ(), SoundEvents.GLASS_BREAK, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
		}
		super.remove();
	}

	@Override
	protected void defineSynchedData() {
		this.getEntityData().define(DATA_SPAWNER_TYPE, LotrExtendedMod.EMPTY.toString());
		this.getEntityData().define(DATA_SPAWNER_CBoundMin, BlockPos.ZERO);
		this.getEntityData().define(DATA_SPAWNER_CBoundMax, BlockPos.ZERO);
		this.getEntityData().define(DATA_SPAWNER_SBoundMin, BlockPos.ZERO);
		this.getEntityData().define(DATA_SPAWNER_SBoundMax, BlockPos.ZERO);
	}

	@Override
	public boolean canChangeDimensions() {
		return false;
	}

	@Override
	public boolean isIgnoringBlockTriggers() {
		return true;
	}

	@Override
	public boolean ignoreExplosion() {
		return true;
	}

	@Override
	public boolean fireImmune() {
		return true;
	}

	@Override
	public boolean isOnFire() {
		return false;
	}

	@Override
	public boolean hurt(DamageSource pSource, float pAmount) {
		if (this.isInvulnerableTo(pSource)) {
			return false;
		} else {
			Entity entity = pSource.getEntity();
			if(entity !=null && entity instanceof PlayerEntity && ((PlayerEntity)entity).abilities.instabuild) {
				if(((PlayerEntity)entity).isCrouching()) {
					this.remove();
				}
			}else {
				this.remove();
			}
			return false;
		}
	}

	/**
	 * Return true if other Entities should be prevented from moving through this Entity.
	 */
	@Override
	public boolean isPickable() {
		return true;
	}

	/**
	 * Return true if this entity should push and be pushed by other entities when colliding.
	 */
	@Override
	public boolean isPushable() {
		return false;
	}

	@Override
	public boolean canBeCollidedWith() {
		return false;
	}

	@Override
	protected boolean canRide(Entity pEntity) {
		return false;
	}

	@Override
	public boolean isInvulnerable() {
		return true;
	}

	@Override
	public PushReaction getPistonPushReaction() {
		return PushReaction.IGNORE;
	}

	@Override
	public IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}
