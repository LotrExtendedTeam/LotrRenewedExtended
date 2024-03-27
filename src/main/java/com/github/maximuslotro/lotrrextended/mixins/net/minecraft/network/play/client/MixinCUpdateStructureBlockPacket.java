package com.github.maximuslotro.lotrrextended.mixins.net.minecraft.network.play.client;

import java.io.IOException;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import com.github.maximuslotro.lotrrextended.mixinhooks.MixinHooks;

import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.CUpdateStructureBlockPacket;
import net.minecraft.state.properties.StructureMode;
import net.minecraft.tileentity.StructureBlockTileEntity;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

@Mixin(CUpdateStructureBlockPacket.class)
public class MixinCUpdateStructureBlockPacket {
	@Shadow
	private BlockPos pos;
	@Shadow
	private StructureBlockTileEntity.UpdateCommand updateType;
	@Shadow
	private StructureMode mode;
	@Shadow
	private String name;
	@Shadow
	private BlockPos offset;
	@Shadow
	private BlockPos size;
	@Shadow
	private Mirror mirror;
	@Shadow
	private Rotation rotation;
	@Shadow
	private String data;
	@Shadow
	private boolean ignoreEntities;
	@Shadow
	private boolean showAir;
	@Shadow
	private boolean showBoundingBox;
	@Shadow
	private float integrity;
	@Shadow
	private long seed;

	/**
	 * @author Maximuslotro
	 * @reason minecraft is stupid and used small data types
	 */
	@Overwrite
	public void read(PacketBuffer p_148837_1_) throws IOException {
		this.pos = p_148837_1_.readBlockPos();
		updateType = p_148837_1_
				.readEnum(StructureBlockTileEntity.UpdateCommand.class);
		mode = p_148837_1_.readEnum(StructureMode.class);
		name = p_148837_1_.readUtf(32767);
		offset = new BlockPos(
				MathHelper.clamp(p_148837_1_.readVarInt(), MixinHooks.StructureLimitNegBounds, MixinHooks.StructureLimitPosBounds),
				MathHelper.clamp(p_148837_1_.readVarInt(), MixinHooks.StructureLimitNegBounds, MixinHooks.StructureLimitPosBounds),
				MathHelper.clamp(p_148837_1_.readVarInt(), MixinHooks.StructureLimitNegBounds, MixinHooks.StructureLimitPosBounds));
		size = new BlockPos(
				MathHelper.clamp(p_148837_1_.readVarInt(), 0, MixinHooks.StructureLimitPosBounds),
				MathHelper.clamp(p_148837_1_.readVarInt(), 0, MixinHooks.StructureLimitPosBounds),
				MathHelper.clamp(p_148837_1_.readVarInt(), 0, MixinHooks.StructureLimitPosBounds));
		mirror = p_148837_1_.readEnum(Mirror.class);
		rotation = p_148837_1_.readEnum(Rotation.class);
		data = p_148837_1_.readUtf(12);
		integrity = MathHelper.clamp(p_148837_1_.readFloat(), 0.0F, 1.0F);
		seed = p_148837_1_.readVarLong();
		int k = p_148837_1_.readByte();
		ignoreEntities = (k & 1) != 0;
		showAir = (k & 2) != 0;
		showBoundingBox = (k & 4) != 0;
	}
	  
	/**
	 * @author Maximuslotro
	 * @reason minecraft is stupid and used small data types
	 */
	@Overwrite
	public void write(PacketBuffer p_148840_1_) throws IOException {
		      p_148840_1_.writeBlockPos(pos);
		      p_148840_1_.writeEnum(updateType);
		      p_148840_1_.writeEnum(mode);
		      p_148840_1_.writeUtf(name);
		      p_148840_1_.writeVarInt(offset.getX());
		      p_148840_1_.writeVarInt(offset.getY());
		      p_148840_1_.writeVarInt(offset.getZ());
		      p_148840_1_.writeVarInt(size.getX());
		      p_148840_1_.writeVarInt(size.getY());
		      p_148840_1_.writeVarInt(size.getZ());
		      p_148840_1_.writeEnum(mirror);
		      p_148840_1_.writeEnum(rotation);
		      p_148840_1_.writeUtf(data);
		      p_148840_1_.writeFloat(integrity);
		      p_148840_1_.writeVarLong(seed);
		      int i = 0;
		      if (ignoreEntities) {
		         i |= 1;
		      }

		      if (showAir) {
		         i |= 2;
		      }

		      if (showBoundingBox) {
		         i |= 4;
		      }

		      p_148840_1_.writeByte(i);
		   }
}
