package lotr.common.init;

import java.util.HashMap;
import java.util.Map;

import com.github.maximuslotro.lotrrextended.common.tileentity.beds.ExtendedNoRenderTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedBookshelfTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.beds.ExtendedBlackFurBedTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.beds.ExtendedFurBedTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.beds.ExtendedGrayFurBedTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.beds.ExtendedOrcBedTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.beds.ExtendedSilverFurBedTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.beds.ExtendedStrawBedTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.beds.ExtendedWhiteFurBedTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.chests.ExtendedStoneChestTileEntity;

import lotr.common.tileentity.spawners.ExtendedStructureSpawnerTileEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;

public class ExtendedTileEntities {
	public static final Map<String, RegistryObject<TileEntityType<ExtendedStructureSpawnerTileEntity<? extends TileEntity, ? extends MobEntity>>>> SPAWNER_TILEENTITYS = new HashMap<>();
	public static RegistryObject<TileEntityType<ExtendedStoneChestTileEntity>> STONE_CHEST;
	public static RegistryObject<TileEntityType<ExtendedStrawBedTileEntity>> STRAW_BED;
	public static RegistryObject<TileEntityType<ExtendedOrcBedTileEntity>> ORC_BED;
	public static RegistryObject<TileEntityType<ExtendedBlackFurBedTileEntity>> BLACK_FUR_BED;
	public static RegistryObject<TileEntityType<ExtendedFurBedTileEntity>> FUR_BED;
	public static RegistryObject<TileEntityType<ExtendedGrayFurBedTileEntity>> GRAY_FUR_BED;
	public static RegistryObject<TileEntityType<ExtendedSilverFurBedTileEntity>> SILVER_FUR_BED;
	public static RegistryObject<TileEntityType<ExtendedWhiteFurBedTileEntity>> WHITE_FUR_BED;
	public static RegistryObject<TileEntityType<ExtendedNoRenderTileEntity>> NORENDER;
	public static RegistryObject<TileEntityType<ExtendedBookshelfTileEntity>> BOOKSHELF;
}

