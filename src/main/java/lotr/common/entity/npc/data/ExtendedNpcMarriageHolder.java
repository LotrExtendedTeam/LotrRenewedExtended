package lotr.common.entity.npc.data;

import java.util.UUID;

import com.github.maximuslotro.lotrrextended.common.network.ExtendedPacketHandler;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedSNpcMarriageInfoPacket;

import lotr.common.entity.npc.ExtendedINPCEntity;
import lotr.common.entity.npc.NPCEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;

public class ExtendedNpcMarriageHolder implements ExtendedNpcDataInterface {
	private final NPCEntity thisEntity;

	public int numberOfCurrentChildren = 0;
	public int maxNumberOfCurrentChildren = 0;
	public UUID spouseID;
	public UUID maleID;
	public UUID femaleID;
	public UUID playerID;

	private boolean resendData = true;

	public ExtendedNpcMarriageHolder(NPCEntity thisEntity) {
		this.thisEntity = thisEntity;
	}

	//TODO implement with changing other data types
	@Override
	public void markDirty() {
		if (!thisEntity.level.isClientSide) {
			if (thisEntity.tickCount > 0) {
				resendData = true;
			} else {
				sendDataToAllBeings();
			}
		}
	}

	@Override
	public void loadFromNbt(CompoundNBT nbt) {
		if(nbt.contains("marriageInfo")) {
			CompoundNBT holder = nbt.getCompound("marriageInfo");
			if (holder.contains("Children")) {
				numberOfCurrentChildren = holder.getInt("Children");
			}
			if (holder.contains("ChildrenMax")) {
				maxNumberOfCurrentChildren = holder.getInt("ChildrenMax");
			}
			if (holder.contains("SpouseUUIDMost") && holder.contains("SpouseUUIDLeast")) {
				spouseID = new UUID(holder.getLong("SpouseUUIDMost"), holder.getLong("SpouseUUIDLeast"));
			}
			if (holder.contains("ParentMaleUUIDMost") && holder.contains("ParentMaleUUIDLeast")) {
				maleID = new UUID(holder.getLong("ParentMaleUUIDMost"), holder.getLong("ParentMaleUUIDLeast"));
			}
			if (holder.contains("ParentFemaleUUIDMost") && holder.contains("ParentFemaleUUIDLeast")) {
				femaleID = new UUID(holder.getLong("ParentFemaleUUIDMost"), holder.getLong("ParentFemaleUUIDLeast"));
			}
			if (holder.contains("PlayerUUIDMost") && holder.contains("PlayerUUIDLeast")) {
				playerID = new UUID(holder.getLong("PlayerUUIDMost"), holder.getLong("PlayerUUIDLeast"));
			}
		}else {
			if (nbt.contains("Children")) {
				numberOfCurrentChildren = nbt.getInt("Children");
			}
			if (nbt.contains("ChildrenMax")) {
				maxNumberOfCurrentChildren = nbt.getInt("ChildrenMax");
			}
			if (nbt.contains("SpouseUUIDMost") && nbt.contains("SpouseUUIDLeast")) {
				spouseID = new UUID(nbt.getLong("SpouseUUIDMost"), nbt.getLong("SpouseUUIDLeast"));
			}
			if (nbt.contains("ParentMaleUUIDMost") && nbt.contains("ParentMaleUUIDLeast")) {
				maleID = new UUID(nbt.getLong("ParentMaleUUIDMost"), nbt.getLong("ParentMaleUUIDLeast"));
			}
			if (nbt.contains("ParentFemaleUUIDMost") && nbt.contains("ParentFemaleUUIDLeast")) {
				femaleID = new UUID(nbt.getLong("ParentFemaleUUIDMost"), nbt.getLong("ParentFemaleUUIDLeast"));
			}
			if (nbt.contains("PlayerUUIDMost") && nbt.contains("PlayerUUIDLeast")) {
				playerID = new UUID(nbt.getLong("PlayerUUIDMost"), nbt.getLong("PlayerUUIDLeast"));
			}
		}
	}

	@Override
	public void writeToNbt(CompoundNBT nbt) {
		CompoundNBT holder = new CompoundNBT();
		if(numberOfCurrentChildren>0) {
			holder.putInt("Children", numberOfCurrentChildren);
		}
		if(maxNumberOfCurrentChildren>0) {
			holder.putInt("ChildrenMax", maxNumberOfCurrentChildren);
		}
		if (spouseID!=null) {
			holder.putLong("SpouseUUIDMost", spouseID.getMostSignificantBits());
			holder.putLong("SpouseUUIDLeast", spouseID.getLeastSignificantBits());
		}
		if (maleID!=null) {
			holder.putLong("ParentMaleUUIDMost", maleID.getMostSignificantBits());
			holder.putLong("ParentMaleUUIDLeast", maleID.getLeastSignificantBits());
		}
		if (femaleID!=null) {
			holder.putLong("ParentFemaleUUIDMost", femaleID.getMostSignificantBits());
			holder.putLong("ParentFemaleUUIDLeast", femaleID.getLeastSignificantBits());
		}
		if (playerID!=null) {
			holder.putLong("PlayerUUIDMost", playerID.getMostSignificantBits());
			holder.putLong("PlayerUUIDLeast", playerID.getLeastSignificantBits());
		}
		nbt.put("marriageInfo", holder);
	}

	@Override
	public PacketBuffer createDataPacketToClient(PacketBuffer buf) {
		buf.writeVarInt(thisEntity.getId());

		buf.writeBoolean(numberOfCurrentChildren!=0);
		if (numberOfCurrentChildren!=0) {
			buf.writeVarInt(numberOfCurrentChildren);
		}

		buf.writeBoolean(maxNumberOfCurrentChildren!=0);
		if (maxNumberOfCurrentChildren!=0) {
			buf.writeVarInt(maxNumberOfCurrentChildren);
		}

		buf.writeBoolean(spouseID!=null);
		if (spouseID!=null) {
			buf.writeLong(spouseID.getMostSignificantBits());
			buf.writeLong(spouseID.getLeastSignificantBits());
		}

		buf.writeBoolean(maleID!=null);
		if (maleID!=null) {
			buf.writeLong(maleID.getMostSignificantBits());
			buf.writeLong(maleID.getLeastSignificantBits());
		}

		buf.writeBoolean(femaleID!=null);
		if (femaleID!=null) {
			buf.writeLong(femaleID.getMostSignificantBits());
			buf.writeLong(femaleID.getLeastSignificantBits());
		}

		buf.writeBoolean(playerID!=null);
		if (playerID!=null) {
			buf.writeLong(playerID.getMostSignificantBits());
			buf.writeLong(playerID.getLeastSignificantBits());
		}
		return buf;
	}

	public static void readDataPacketFromServer(PacketBuffer buf, World world) {
		int entityId = buf.readVarInt();
		Entity entity = world.getEntity(entityId);
		if (entity instanceof NPCEntity) {
			NPCEntity npc = (NPCEntity) entity;
			ExtendedNpcMarriageHolder marriageInfo = ((ExtendedINPCEntity) npc).getMarriageInfo();
			if(marriageInfo!=null) {
				marriageInfo.numberOfCurrentChildren = buf.readBoolean() ? buf.readVarInt() : 0;
				marriageInfo.maxNumberOfCurrentChildren= buf.readBoolean() ? buf.readVarInt() : 0;
				marriageInfo.spouseID = buf.readBoolean() ? new UUID(buf.readLong(), buf.readLong()) : null;
				marriageInfo.maleID = buf.readBoolean() ? new UUID(buf.readLong(), buf.readLong()) : null;
				marriageInfo.femaleID = buf.readBoolean() ? new UUID(buf.readLong(), buf.readLong()) : null;
				marriageInfo.playerID = buf.readBoolean() ? new UUID(buf.readLong(), buf.readLong()) : null;
			}
		}
	}

	@Override
	public void sendDataToPlayer(ServerPlayerEntity player) {
		ExtendedSNpcMarriageInfoPacket packet = new ExtendedSNpcMarriageInfoPacket(this);
		ExtendedPacketHandler.sendToClient(packet, player);
	}

	@Override
	public void sendDataToAllBeings() {
		ExtendedSNpcMarriageInfoPacket packet = new ExtendedSNpcMarriageInfoPacket(this);
		ExtendedPacketHandler.sendToAllTrackingEntity(packet, thisEntity);
	}

	@Override
	public void tick() {
		if (!thisEntity.level.isClientSide) {
			if (resendData) {
				sendDataToAllBeings();
				resendData = false;
			}
		}
	}

	@Override
	public boolean shouldLoad() {
		return true;
	}

	@Override
	public boolean shouldTick() {
		return true;
	}
}
