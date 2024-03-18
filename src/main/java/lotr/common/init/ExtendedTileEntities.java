package lotr.common.init;

import java.util.HashMap;
import java.util.Map;

import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedStoneChestTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedStrawBedTileEntity;

import lotr.common.tileentity.spawners.ExtendedStructureSpawnerTileEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;

public class ExtendedTileEntities {
	public static final Map<String, RegistryObject<TileEntityType<ExtendedStructureSpawnerTileEntity<? extends TileEntity, ? extends MobEntity>>>> SPAWNER_TILEENTITYS = new HashMap<>();
	public static RegistryObject<TileEntityType<ExtendedStoneChestTileEntity>> STONE_CHEST;
	public static RegistryObject<TileEntityType<ExtendedStrawBedTileEntity>> STRAW_BED;
}

