package com.github.maximuslotro.lotrrextended.common.entity.item;

import java.util.function.Supplier;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedBlocks;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedEntitiesNew;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedItems;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class ExtendedBoatEntity extends BoatEntity {
	private static final DataParameter<Integer> DATA_ID_TYPE_EXTENDED = EntityDataManager.defineId(ExtendedBoatEntity.class, DataSerializers.INT);

	public ExtendedBoatEntity(EntityType<? extends BoatEntity> pType, World pLevel) {
		super(pType, pLevel);
		this.blocksBuilding = true;
	}

	public ExtendedBoatEntity(World pLevel, double pX, double pY, double pZ) {
		this(ExtendedEntitiesNew.BOAT.get(), pLevel);
		this.setPos(pX, pY, pZ);
		this.setDeltaMovement(Vector3d.ZERO);
		this.xo = pX;
		this.yo = pY;
		this.zo = pZ;
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DATA_ID_TYPE_EXTENDED, Type.CHESTNUT.ordinal());
	}

	public void setTypeExtended(Type p_184458_1_) {
		this.entityData.set(DATA_ID_TYPE_EXTENDED, p_184458_1_.ordinal());
	}

	public Type getBoatTypeExtended() {
		return Type.byId(this.entityData.get(DATA_ID_TYPE_EXTENDED));
	}

	@Override
	protected void addAdditionalSaveData(CompoundNBT pCompound) {
		pCompound.putString("ExtType", getBoatTypeExtended().getName());
	}

	@Override
	protected void readAdditionalSaveData(CompoundNBT pCompound) {
		if (pCompound.contains("ExtType", 8)) {
			setTypeExtended(Type.byName(pCompound.getString("ExtType")));
		}
	}

	@Override
	public Item getDropItem() {
		switch(getBoatTypeExtended()) {
		case CHESTNUT:
		default:
			return ExtendedItems.CHESTNUT_BOAT.get();
		case BANANA:
			return ExtendedItems.BANANA_BOAT.get();
		case PLUM:
			return ExtendedItems.PLUM_BOAT.get();
		case WILLOW:
			return ExtendedItems.WILLOW_BOAT.get();
		case EBONY:
			return ExtendedItems.EBONY_BOAT.get();
		}
	}

	@Override
	public IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	public static enum Type {
		CHESTNUT(ExtendedBlocks.CHESTNUT_PLANKS, "chestnut"),
		BANANA(ExtendedBlocks.BANANA_PLANKS, "banana"),
		PLUM(ExtendedBlocks.PLUM_PLANKS, "plum"),
		WILLOW(ExtendedBlocks.WILLOW_PLANKS, "willow"),
		EBONY(ExtendedBlocks.EBONY_PLANKS, "ebony");

		private final String name;
		private final Supplier<Block> planks;
		private final ResourceLocation textureLocation;

		private Type(Block pBlock, String pName) {
			this.name = pName;
			this.planks = ()->pBlock;
			this.textureLocation = new ResourceLocation(LotrExtendedMod.MODID, String.format("textures/entity/boat/%s.png", pName));
		}

		private Type(Supplier<Block> pSupplierBlock, String pName) {
			this.name = pName;
			this.planks = pSupplierBlock;
			this.textureLocation = new ResourceLocation(LotrExtendedMod.MODID, String.format("textures/entity/boat/%s.png", pName));
		}

		public String getName() {
			return this.name;
		}

		public Block getPlanks() {
			return this.planks.get();
		}

		public ResourceLocation getTextureLocation() {
			return textureLocation;
		}

		@Override
		public String toString() {
			return this.name;
		}

		public static ExtendedBoatEntity.Type byId(int pOrdeal) {
			ExtendedBoatEntity.Type[] vallueArray = values();
			if (pOrdeal < 0 || pOrdeal >= vallueArray.length) {
				pOrdeal = 0;
			}

			return vallueArray[pOrdeal];
		}

		public static ExtendedBoatEntity.Type byName(String pName) {
			ExtendedBoatEntity.Type[] vallueArray = values();
			for(int i = 0; i < vallueArray.length; ++i) {
				if (vallueArray[i].getName().equals(pName)) {
					return vallueArray[i];
				}
			}
			return vallueArray[0];
		}
	}
}
