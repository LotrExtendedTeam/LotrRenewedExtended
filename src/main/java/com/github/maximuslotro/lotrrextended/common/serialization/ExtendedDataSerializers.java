package com.github.maximuslotro.lotrrextended.common.serialization;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;

import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.IDataSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DataSerializerEntry;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ExtendedDataSerializers {
	public static final IDataSerializer<Vector3d> VECTOR3D_POS = new IDataSerializer<Vector3d>() {
		@Override
		public void write(PacketBuffer pBuffer, Vector3d pValue) {
			pBuffer.writeDouble(pValue.x());
			pBuffer.writeDouble(pValue.y());
			pBuffer.writeDouble(pValue.z());
		}

		@Override
		public Vector3d read(PacketBuffer pBuffer) {
			return new Vector3d(pBuffer.readDouble(), pBuffer.readDouble(), pBuffer.readDouble());
		}

		@Override
		public Vector3d copy(Vector3d pValue) {
			return pValue;
		}
	};
	public static final IDataSerializer<ResourceLocation> RESOURCE_LOCATION = new IDataSerializer<ResourceLocation>() {
		@Override
		public void write(PacketBuffer pBuffer, ResourceLocation pValue) {
			pBuffer.writeUtf(pValue.getNamespace());
			pBuffer.writeUtf(pValue.getPath());
		}

		@Override
		public ResourceLocation read(PacketBuffer pBuffer) {
			return new ResourceLocation(pBuffer.readUtf(), pBuffer.readUtf());
		}

		@Override
		public ResourceLocation copy(ResourceLocation pValue) {
			return pValue;
		}
	};
	public static final DeferredRegister<DataSerializerEntry> DATA_SERIALIZERS = DeferredRegister.create(ForgeRegistries.DATA_SERIALIZERS, LotrExtendedMod.MODID);
	public static final RegistryObject<DataSerializerEntry> VECTOR3F_POS_OBJ = DATA_SERIALIZERS.register("vector3d", () -> new DataSerializerEntry(VECTOR3D_POS));
	public static final RegistryObject<DataSerializerEntry> RESOURCE_LOCATION_OBJ = DATA_SERIALIZERS.register("resource_location", () -> new DataSerializerEntry(RESOURCE_LOCATION));

	public static void register(IEventBus bus) {
		DATA_SERIALIZERS.register(bus);
	}
}
