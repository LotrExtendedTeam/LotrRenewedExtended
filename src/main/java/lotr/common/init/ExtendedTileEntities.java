package lotr.common.init;

import java.util.HashMap;
import java.util.Map;

import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedBookshelfTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedGrinderTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedMEBannerTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedNoRenderTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedWeaponRackTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.beds.ExtendedBlackFurBedTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.beds.ExtendedFurBedTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.beds.ExtendedGrayFurBedTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.beds.ExtendedOrcBedTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.beds.ExtendedSilverFurBedTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.beds.ExtendedStrawBedTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.beds.ExtendedWhiteFurBedTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.chests.ExtendedOrcBarrelTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.chests.ExtendedReedBasketTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.chests.ExtendedStoneChestTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.spawners.ExtendedStructureSpawnerTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.spawners.group.ExtendedStructureGroupSpawnerTileEntity;

import net.minecraft.entity.MobEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;

@SuppressWarnings("deprecation")
public class ExtendedTileEntities {
	public static final Map<String, RegistryObject<TileEntityType<ExtendedStructureSpawnerTileEntity<? extends TileEntity, ? extends MobEntity>>>> SPAWNER_TILEENTITYS = new HashMap<>();
	public static final Map<String, RegistryObject<TileEntityType<ExtendedStructureGroupSpawnerTileEntity<? extends TileEntity>>>> SPAWNER_GROUP_TILEENTITYS = new HashMap<>();

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
	public static RegistryObject<TileEntityType<ExtendedWeaponRackTileEntity>> WEAPON_RACK;
	public static RegistryObject<TileEntityType<ExtendedReedBasketTileEntity>> REED_BASKET;
	public static RegistryObject<TileEntityType<ExtendedOrcBarrelTileEntity>> ORC_BARREL;
	public static RegistryObject<TileEntityType<ExtendedGrinderTileEntity>> MILLSTONE;
	public static RegistryObject<TileEntityType<ExtendedMEBannerTileEntity>> BANNER;
}

