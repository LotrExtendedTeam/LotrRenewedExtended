package com.github.maximuslotro.lotrrextended.common.datagen.models;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.Map;
import java.util.Set;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.client.render.item.ExtendedItemModelsProperties;
import com.github.maximuslotro.lotrrextended.client.render.model.ExtendedTransformWrapperModelBuilder;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedBarsBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedBirdcageBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedCakeBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedChainBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedCrystalChandelierBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedFlowerBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedFlowerEffectlessBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedLanternElvenBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedLargeFlowerPotBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedNarsilShardsBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedSaplingBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedStandingSignBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedVineBlock;
import com.github.maximuslotro.lotrrextended.common.block.containers.ExtendedLargePotBlock;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedItems;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedBerryItem;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedBowItem;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedBrandingIronItem;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedChiselItem;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedCommandHornItem;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedCrossbowItem;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedDaggerItem;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedEntitySpawnerItem;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedLeatherHatItem;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedMCBannerItem;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedMattockItem;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedCoinItem;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedSwordItem;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedModifierScrollItem;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedStaffItem;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedThrowableAxeItem;
import com.google.common.collect.ImmutableSet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lotr.common.block.ChandelierBlock;
import lotr.common.block.ExtendedLOTRTorchBlock;
import lotr.common.block.LOTRDoorBlock;
import lotr.common.block.VesselDrinkBlock;
import lotr.common.init.ExtendedEntities;
import lotr.common.init.LOTRItems;
import lotr.common.item.DaggerItem;
import lotr.common.item.FallenLeavesItem;
import lotr.common.item.HammerItem;
import lotr.common.item.LOTRArmorItem;
import lotr.common.item.LOTRHoeItem;
import lotr.common.item.LOTRShieldItem;
import lotr.common.item.SpearItem;
import lotr.common.item.VesselDrinkItem;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.DoorBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BannerPatternItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class ExtendedItemModelGenerator extends ItemModelProvider {
	private static final Set<String> DIVERTED_MODELS = ImmutableSet.of(
		"item/arnor_dagger", "item/blue_dwarven_dagger", "item/bronze_dagger", "item/dale_dagger",
		"item/dol_amroth_dagger", "item/dorwinion_elven_dagger", "item/dwarven_dagger", "item/galadhrim_dagger",
		"item/gondor_dagger", "item/harad_dagger", "item/iron_dagger", "item/lindon_dagger",
		"item/mithril_dagger", "item/mordor_dagger", "item/rivendell_dagger", "item/rohan_dagger",
		"item/stone_dagger", "item/umbar_dagger", "item/uruk_dagger", "item/wood_elven_dagger", "item/wooden_dagger");
	private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();

	public ExtendedItemModelGenerator(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper) {
		super(generator, modid, existingFileHelper);
	}

	@Override
	protected void generateAll(DirectoryCache cache) {
		for (ItemModelBuilder model : generatedModels.values()) {
			ResourceLocation loc = model.getLocation();
			Path target;
			if (DIVERTED_MODELS.contains(loc.getPath())) {
				target = generator.getOutputFolder().resolve("resourcepacks/extended/assets/" + loc.getNamespace() + "/models/" + loc.getPath() + ".json");
			} else {
				target = generator.getOutputFolder().resolve("assets/" + loc.getNamespace() + "/models/" + loc.getPath() + ".json");
			}
			try {
				IDataProvider.save(GSON, cache, model.toJson(), target);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public ResourceLocation blockLocation(ForgeRegistryEntry<?> object) {
		return new ResourceLocation(object.getRegistryName().getNamespace(), "block/"+object.getRegistryName().getPath());
	}
	public ResourceLocation itemLocation(ForgeRegistryEntry<?> object) {
		return itemLocation(object, "");
	}
	public ResourceLocation itemLocation(ForgeRegistryEntry<?> object, String suffex) {
		return new ResourceLocation(object.getRegistryName().getNamespace(), "item/"+object.getRegistryName().getPath()+suffex);
	}
	public ResourceLocation lotrExtendedLoc(String name) {
		return new ResourceLocation("lotrextended", name);
	}

	public ResourceLocation lotrLoc(String name) {
		return new ResourceLocation("lotr", name);
	}

	public void registerOverrides() {
		genGenerated(ExtendedItems.MORGUL_BRAZIER.get(), itemLocation(ExtendedItems.MORGUL_BRAZIER.get()));
		genGenerated(ExtendedItems.WEAPON_RACK.get(), itemLocation(ExtendedItems.WEAPON_RACK.get()));
		genGenerated(ExtendedItems.ENTITY_SPAWNER.get(), lotrExtendedLoc("block/spawner_block"));
		genGenerated(ExtendedItems.ROPE.get(), itemLocation(ExtendedItems.ROPE.get()));
		genGenerated(ExtendedItems.GRASS_VOID.get(), itemLocation(ExtendedItems.GRASS_VOID.get()));
		genGenerated(ExtendedItems.RIVERWEED.get(), blockLocation(ExtendedItems.RIVERWEED.get()));
		genGenerated(ExtendedItems.STONEROOT.get(), blockLocation(ExtendedItems.STONEROOT.get()));
		genGenerated(ExtendedItems.SPRINGLEAF.get(), itemLocation(ExtendedItems.SPRINGLEAF.get()));
	}
	@Override
	protected void registerModels() {
		for(RegistryObject<Item> item : ExtendedItems.ITEMS.getEntries()) {
			if(item.get() instanceof BlockItem) {
				if((((BlockItem)item.get()).getBlock() instanceof LOTRDoorBlock) || (((BlockItem)item.get()).getBlock() instanceof DoorBlock)
					|| (((BlockItem)item.get()).getBlock() instanceof ChandelierBlock)) {
					genGenerated(item.get(), itemLocation(item.get()));
					continue;
				}
				if(((BlockItem)item.get()).getBlock() instanceof ExtendedSaplingBlock) {
					genGenerated(item.get(), blockLocation(item.get()));
					continue;
				}
				if(((BlockItem)item.get()).getBlock() instanceof ExtendedLargePotBlock) {
					genGenerated(item.get(), itemLocation(item.get()));
					continue;
				}
				if(((BlockItem)item.get()).getBlock() instanceof ExtendedLargeFlowerPotBlock && !item.getId().getPath().contains("large_pot_")) {
					genGenerated(item.get(), itemLocation(item.get()));
					continue;
				}
				if(((BlockItem)item.get()).getBlock() instanceof ExtendedStandingSignBlock) {
					genGenerated(item.get(), itemLocation(item.get()));
					continue;
				}
				if(((BlockItem)item.get()).getBlock() instanceof ExtendedBirdcageBlock) {
					genGenerated(item.get(), itemLocation(item.get()));
					continue;
				}
				if(((BlockItem)item.get()).getBlock() instanceof VesselDrinkBlock) {
					genGenerated(item.get(), itemLocation(item.get()));
					continue;
				}
				if(((BlockItem)item.get()).getBlock() instanceof ExtendedChainBlock) {
					genGenerated(item.get(), itemLocation(item.get()));
					continue;
				}
				if(((BlockItem)item.get()).getBlock() instanceof ExtendedCakeBlock) {
					genGenerated(item.get(), itemLocation(item.get()));
					continue;
				}
				if(((BlockItem)item.get()).getBlock() instanceof ExtendedCrystalChandelierBlock) {
					genGenerated(item.get(), itemLocation(item.get()));
					continue;
				}
				if(((BlockItem)item.get()).getBlock() instanceof ExtendedLanternElvenBlock) {
					genGenerated(item.get(), itemLocation(item.get()));
					continue;
				}
				if(((BlockItem)item.get()).getBlock() instanceof ExtendedChainBlock) {
					genGenerated(item.get(), itemLocation(item.get()));
					continue;
				}
				if(((BlockItem)item.get()).getBlock() instanceof ExtendedBarsBlock) {
					genGenerated(item.get(), blockLocation(item.get()));
					continue;
				}
				if(((BlockItem)item.get()).getBlock() instanceof ExtendedLOTRTorchBlock) {
					genGenerated(item.get(), blockLocation(item.get()));
					continue;
				}
				if(((BlockItem)item.get()).getBlock() instanceof ExtendedFlowerBlock) {
					genGenerated(item.get(), blockLocation(item.get()));
					continue;
				}
				if(((BlockItem)item.get()).getBlock() instanceof ExtendedFlowerEffectlessBlock) {
					genGenerated(item.get(), blockLocation(item.get()));
					continue;
				}
				if(((BlockItem)item.get()).getBlock() instanceof ExtendedNarsilShardsBlock) {
					ItemModelBuilder guiModel = genHandheld(item.get(), "_gui", itemLocation(item.get()));
					ItemModelBuilder heldModel = genSwordHeld(item.get());
					genTransformWrapper(item.get(), guiModel, heldModel);
					continue;
				}
				if(((BlockItem)item.get()).getBlock() instanceof CropsBlock) {
					genGenerated(item.get(), itemLocation(item.get()));
					continue;
				}
				if(((BlockItem)item.get()).getBlock() instanceof ExtendedVineBlock) {
					if(item.getId().getPath().contains(ExtendedItems.DEAD_MOSS_VINES.get().getRegistryName().getPath())) {
						genGenerated(item.get(), itemLocation(item.get(), "_hand"));
						continue;
					}else {
						genGenerated(item.get(), blockLocation(item.get()));
					}
					continue;
				}
				if(item.get() instanceof FallenLeavesItem) {
					try {
						genGenerated(item.get(), true, lotrExtendedLoc("block/"+item.getId().getPath().substring(7)));
					}catch(IllegalArgumentException e) {
						genGenerated(item.get(), lotrExtendedLoc("block/"+item.getId().getPath().substring(7)+"_0"));
					}
					continue;
				}
				if(item.get() instanceof ExtendedMCBannerItem) {
					genGenerated(item.get(), lotrExtendedLoc("item/banner/"+item.getId().getPath()));
					continue;
				}
				if(item.get() instanceof ExtendedBerryItem) {
					genGenerated(item.get(), itemLocation(item.get()));
					continue;
				}
				if(item.get() instanceof ExtendedChiselItem) {
					genGenerated(item.get(), itemLocation(item.get()));
					continue;
				}
				continue;
			}
			if(item.get() instanceof ExtendedEntitySpawnerItem) {
				continue;
			}
			if(item.get() instanceof ExtendedLeatherHatItem) {
				genGenerated(item.get(), itemLocation(item.get()), itemLocation(item.get(), "_overlay"));
				continue;
			}
			if(item.getId().getPath().contains("torch")) {
				genGenerated(item.get(), blockLocation(item.get()));
				continue;
			}
			if(item.get() instanceof LOTRArmorItem) {
				genGenerated(item.get(), itemLocation(item.get()));
				continue;
			}
			if(item.get() instanceof VesselDrinkItem) {
				genDrink(item.get(), "item/"+item.getId().getPath());
				continue;
			}
			if(item.get() instanceof BannerPatternItem) {
				genGenerated(item.get(), lotrExtendedLoc("item/banner_pattern"));
				BannerPattern pattern = ((BannerPatternItem)item.get()).getBannerPattern();
				if (!this.existingFileHelper.exists(new ResourceLocation("minecraft", "textures/entity/banner/"+pattern.getFilename()+".png"), ResourcePackType.CLIENT_RESOURCES)) {
					System.out.println("Missing banner banner pattern texture: " + pattern.getFilename() + ".png");
				}
				if (!this.existingFileHelper.exists(new ResourceLocation("minecraft", "textures/entity/shield/"+pattern.getFilename()+".png"), ResourcePackType.CLIENT_RESOURCES)) {
					System.out.println("Missing shield banner pattern texture: " + pattern.getFilename() + ".png");
				}
				continue;
			}
			if(item.get() instanceof ExtendedDaggerItem) {
				genDagger(item.get(), itemLocation(item.get()), itemLocation(item.get(), "_poisoned"), false);
				continue;
			}
			if(item.get() instanceof SpearItem ||item.get() instanceof ExtendedStaffItem || item.get() instanceof ExtendedSwordItem || item.get() instanceof HammerItem) {
				ItemModelBuilder guiModel = genHandheld(item.get(), "_gui", itemLocation(item.get()));
				ItemModelBuilder heldModel = null;
				if(item.get() instanceof SpearItem){
					heldModel = genSpearHeld(item.get());
				}else if(item.get() instanceof ExtendedStaffItem) {
					heldModel = genStaffHeld(item.get());
				}else if(item.get() instanceof ExtendedSwordItem || item.get() instanceof HammerItem) {
					heldModel = genSwordHeld(item.get());
				}
				if(heldModel!=null) {
					genTransformWrapper(item.get(), guiModel, heldModel);
				}
				continue;
			}
			if(item.get() instanceof LOTRShieldItem) {
				try {
					getExistingFile(itemLocation(item.get()));
					getExistingFile(itemLocation(item.get(), "_blocking"));
				}catch(IllegalStateException e) {
					System.out.println("Missing shield model: "+item.getId().getPath());
					System.out.println("Missing shield model: "+item.getId().getPath()+"_blocking");
				}
				continue;
			}

			if(item.get() instanceof ExtendedBowItem) {
				genBow(item.get());
				continue;
			}

			if((item.get() instanceof PickaxeItem) || (item.get() instanceof ShovelItem)
				|| (item.get() instanceof AxeItem)
				|| (item.get() instanceof LOTRHoeItem)
				|| (item.get() instanceof ExtendedMattockItem)
				|| (item.get() instanceof ExtendedThrowableAxeItem)) {
				genHandheld(item.get(), itemLocation(item.get()));
				continue;
			}
			if(item.get() instanceof ExtendedCommandHornItem) {
				genCommandHorn(item.get());
				continue;
			}
			if(item.get() instanceof ExtendedBrandingIronItem) {
				genBrandingIron(item.get());
				continue;
			}
			if(item.get() instanceof ExtendedModifierScrollItem) {
				genModifierScroll(item.get());
				continue;
			}
			if(item.get() instanceof ExtendedCrossbowItem) {
				genCrossbow(item.get(), new ResourceLocation(item.get().getRegistryName().getNamespace(), "item/"+item.getId().getPath()));
				continue;
			}
			if(item.get() instanceof ExtendedCoinItem) {
				ItemModelBuilder builder = withExistingParent(item.get().getRegistryName().toString(), "lotrextended:item/template_coin");
				gen(builder, new ResourceLocation(item.get().getRegistryName().getNamespace(), "item/"+item.getId().getPath()));
				continue;
			}
			try {
				genGenerated(item.get(), itemLocation(item.get()));
				continue;
			}catch(Exception e) {
				e.printStackTrace();
			}
			System.out.println("Didn't make ItemModel for: "+item.getId().getPath());
		}
		for(String entity : ExtendedEntities.REGISTEREDNPCENTITIES) {
			withExistingParent("lotr:"+entity+"_spawn_egg", "item/template_spawn_egg");
		}
		for(String entity : ExtendedEntities.REGISTEREDANIMALENTITIES) {
			withExistingParent("lotr:"+entity+"_spawn_egg", "item/template_spawn_egg");
		}
		for(RegistryObject<Item> item : LOTRItems.ITEMS.getEntries()) {
			if(item.get() instanceof DaggerItem) {
				if(item.get()==LOTRItems.DORWINION_ELVEN_DAGGER.get()||item.get()==LOTRItems.GALADHRIM_DAGGER.get()
					||item.get()==LOTRItems.LINDON_DAGGER.get()||item.get()==LOTRItems.RIVENDELL_DAGGER.get()||item.get()==LOTRItems.WOOD_ELVEN_DAGGER.get()) {
					genDaggerElven(item.get(), lotrLoc("item/"+item.get().getRegistryName().getPath()), 
						lotrExtendedLoc("item/"+item.get().getRegistryName().getPath()+"_poisoned"), lotrLoc("item/"+item.get().getRegistryName().getPath()+"_glowing"), 
						lotrExtendedLoc("item/"+item.get().getRegistryName().getPath()+"_glowing_poisoned"), true);
					continue;
				}
				genDagger(item.get(), lotrLoc("item/"+item.get().getRegistryName().getPath()), lotrExtendedLoc("item/"+item.get().getRegistryName().getPath()+"_poisoned"), true);
				continue;
			}
		}
		registerOverrides();
	}

	@Override
	public String getName() {
		return "Renewed Extended Item Model Provider";
	}

	private void genBow(Item item) {
		String bowhandheldFileName = item.getRegistryName().getNamespace()+":item/"+item.getRegistryName().getPath()+"_held";
		ItemModelBuilder builder_bow_held = gen(withExistingParent(bowhandheldFileName, "lotrextended:item/template_bow"), new ResourceLocation(item.getRegistryName().getNamespace(), "item/"+item.getRegistryName().getPath()+"_large"));
		ItemModelBuilder builder_bow_held_p0 = gen(withExistingParent(bowhandheldFileName+"_pulling_0", bowhandheldFileName), new ResourceLocation(item.getRegistryName().getNamespace(), "item/"+item.getRegistryName().getPath()+"_large_pulling_0"));
		ItemModelBuilder builder_bow_held_p1 = gen(withExistingParent(bowhandheldFileName+"_pulling_1", bowhandheldFileName), new ResourceLocation(item.getRegistryName().getNamespace(), "item/"+item.getRegistryName().getPath()+"_large_pulling_1"));
		ItemModelBuilder builder_bow_held_p2 = gen(withExistingParent(bowhandheldFileName+"_pulling_2", bowhandheldFileName), new ResourceLocation(item.getRegistryName().getNamespace(), "item/"+item.getRegistryName().getPath()+"_large_pulling_2"));
		builder_bow_held.override().model(builder_bow_held_p0).predicate(ExtendedItemModelsProperties.pulling, 1);
		builder_bow_held.override().model(builder_bow_held_p1).predicate(ExtendedItemModelsProperties.pulling, 1).predicate(ExtendedItemModelsProperties.pull, 0.65F);
		builder_bow_held.override().model(builder_bow_held_p2).predicate(ExtendedItemModelsProperties.pulling, 1).predicate(ExtendedItemModelsProperties.pull, 0.9F);
		String bowFileName = item.getRegistryName().getNamespace()+":item/"+item.getRegistryName().getPath()+"_gui";
		ItemModelBuilder builder_bow_gui = gen(withExistingParent(bowFileName, "lotrextended:item/template_bow"), new ResourceLocation(item.getRegistryName().getNamespace(), "item/"+item.getRegistryName().getPath()));
		ItemModelBuilder builder_bow_gui_p0 = gen(withExistingParent(bowFileName+"_pulling_0", bowFileName), false, new ResourceLocation(item.getRegistryName().getNamespace(), "item/"+item.getRegistryName().getPath()+"_pulling_0"));
		ItemModelBuilder builder_bow_gui_p1 = gen(withExistingParent(bowFileName+"_pulling_1", bowFileName), false, new ResourceLocation(item.getRegistryName().getNamespace(), "item/"+item.getRegistryName().getPath()+"_pulling_1"));
		ItemModelBuilder builder_bow_gui_p2 = gen(withExistingParent(bowFileName+"_pulling_2", bowFileName), false, new ResourceLocation(item.getRegistryName().getNamespace(), "item/"+item.getRegistryName().getPath()+"_pulling_2"));
		builder_bow_gui.override().model(builder_bow_gui_p0).predicate(ExtendedItemModelsProperties.pulling, 1);
		builder_bow_gui.override().model(builder_bow_gui_p1).predicate(ExtendedItemModelsProperties.pulling, 1).predicate(ExtendedItemModelsProperties.pull, 0.65F);
		builder_bow_gui.override().model(builder_bow_gui_p1).predicate(ExtendedItemModelsProperties.pulling, 1).predicate(ExtendedItemModelsProperties.pull, 0.9F);
		genTransformWrapper(item, builder_bow_gui, builder_bow_held);
	}

	private ItemModelBuilder genDrink(Item item, String location) {
		ItemModelBuilder builder = withExistingParent(item.getRegistryName().toString(), "item/generated");
		builder.texture("liquid", new ResourceLocation(item.getRegistryName().getNamespace(), location+"_liquid"));
		builder.customLoader(ExtendedVesselModelBuilder::new);
		return builder;
	}

	private ItemModelBuilder genSwordHeld(Item item) {
		ItemModelBuilder builder = withExistingParent(item.getRegistryName().toString()+"_held", "lotr:item/handheld_large");
		return gen(builder, new ResourceLocation(item.getRegistryName().getNamespace(),"item/"+item.getRegistryName().getPath()+"_large"));
	}

	private ItemModelBuilder genStaffHeld(Item item) {
		ItemModelBuilder builder = withExistingParent(item.getRegistryName().toString()+"_held", "lotr:item/handheld_spear");
		return gen(builder, new ResourceLocation(item.getRegistryName().getNamespace(),"item/"+item.getRegistryName().getPath()+"_large"));
	}

	private ItemModelBuilder genSpearHeld(Item item) {
		ItemModelBuilder builder = withExistingParent(item.getRegistryName().toString()+"_held", "lotr:item/handheld_spear");
		ItemModelBuilder builderPulling = withExistingParent(item.getRegistryName().toString()+"_held_pulling", "lotr:item/handheld_spear_pulling");
		builder.override().model(builderPulling).predicate(mcLoc("pulling"), 1);
		return gen(builder, new ResourceLocation(item.getRegistryName().getNamespace(),"item/"+item.getRegistryName().getPath()+"_large"));
	}

	private ItemModelBuilder genDagger(Item item, ResourceLocation texture, ResourceLocation texturePoisoned, boolean useExtendedNamespace) {
		String namespace;
		if(useExtendedNamespace) {
			namespace = LotrExtendedMod.MODID;
		}else {
			namespace = item.getRegistryName().getNamespace();
		}
		ItemModelBuilder builderPos = gen(withExistingParent(namespace+":"+item.getRegistryName().getPath()+"_poisoned", "lotr:item/handheld_dagger"), texturePoisoned);
		ItemModelBuilder builderSneak = gen(withExistingParent(namespace+":"+item.getRegistryName().getPath()+"_sneaking", "lotr:item/handheld_dagger_sneaking"), texture);
		ItemModelBuilder builderSneakPos = gen(withExistingParent(namespace+":"+item.getRegistryName().getPath()+"_sneaking_poisoned", "lotr:item/handheld_dagger_sneaking"), texturePoisoned);
		ItemModelBuilder builder = withExistingParent(item.getRegistryName().toString(), "lotr:item/handheld_dagger");
		builder.override().model(builderPos).predicate(lotrLoc("sneaking"), 0).predicate(ExtendedItemModelsProperties.poisoned, 1);
		builder.override().model(builderSneak).predicate(lotrLoc("sneaking"), 1).predicate(ExtendedItemModelsProperties.poisoned, 0);
		builder.override().model(builderSneakPos).predicate(lotrLoc("sneaking"), 1).predicate(ExtendedItemModelsProperties.poisoned, 1);
		return gen(builder, texture);
	}

	private ItemModelBuilder genDaggerElven(Item item, ResourceLocation texture, ResourceLocation texturePoisoned, ResourceLocation textureGlowing, ResourceLocation textureGlowingPoisoned, boolean useExtendedNamespace) {
		String namespace;
		if(useExtendedNamespace) {
			namespace = LotrExtendedMod.MODID;
		}else {
			namespace = item.getRegistryName().getNamespace();
		}
		ItemModelBuilder builderGlow = gen(withExistingParent(namespace+":"+item.getRegistryName().getPath()+"_glowing", "lotr:item/handheld_dagger"), textureGlowing);
		ItemModelBuilder builderGlowSneak = gen(withExistingParent(namespace+":"+item.getRegistryName().getPath()+"_glowing_sneaking", "lotr:item/handheld_dagger_sneaking"), textureGlowing);
		ItemModelBuilder builderGlowPos = gen(withExistingParent(namespace+":"+item.getRegistryName().getPath()+"_glowing_poisoned", "lotr:item/handheld_dagger"), textureGlowingPoisoned);
		ItemModelBuilder builderGlowSneakPos = gen(withExistingParent(namespace+":"+item.getRegistryName().getPath()+"_glowing_sneaking_poisoned", "lotr:item/handheld_dagger_sneaking"), textureGlowingPoisoned);
		ItemModelBuilder builderSneak = gen(withExistingParent(namespace+":"+item.getRegistryName().getPath()+"_sneaking", "lotr:item/handheld_dagger_sneaking"), texture);
		ItemModelBuilder builderPos = gen(withExistingParent(namespace+":"+item.getRegistryName().getPath()+"_poisoned", "lotr:item/handheld_dagger"), texturePoisoned);
		ItemModelBuilder builderSneakPos = gen(withExistingParent(namespace+":"+item.getRegistryName().getPath()+"_sneaking_poisoned", "lotr:item/handheld_dagger_sneaking"), texturePoisoned);
		ItemModelBuilder builder = withExistingParent(item.getRegistryName().toString(), "lotr:item/handheld_dagger");
		builder.override().model(builderGlow).predicate(lotrLoc("elven_glow"), 1).predicate(lotrLoc("sneaking"), 0).predicate(ExtendedItemModelsProperties.poisoned, 0);
		builder.override().model(builderGlowSneak).predicate(lotrLoc("elven_glow"), 1).predicate(lotrLoc("sneaking"), 1).predicate(ExtendedItemModelsProperties.poisoned, 0);
		builder.override().model(builderGlowPos).predicate(lotrLoc("elven_glow"), 1).predicate(lotrLoc("sneaking"), 0).predicate(ExtendedItemModelsProperties.poisoned, 1);
		builder.override().model(builderGlowSneakPos).predicate(lotrLoc("elven_glow"), 1).predicate(lotrLoc("sneaking"), 1).predicate(ExtendedItemModelsProperties.poisoned, 1);
		builder.override().model(builderSneak).predicate(lotrLoc("elven_glow"), 0).predicate(lotrLoc("sneaking"), 1).predicate(ExtendedItemModelsProperties.poisoned, 0);
		builder.override().model(builderPos).predicate(lotrLoc("elven_glow"), 0).predicate(lotrLoc("sneaking"), 0).predicate(ExtendedItemModelsProperties.poisoned, 1);
		builder.override().model(builderSneakPos).predicate(lotrLoc("elven_glow"), 0).predicate(lotrLoc("sneaking"), 1).predicate(ExtendedItemModelsProperties.poisoned, 1);
		return gen(builder, texture);
	}

	private ItemModelBuilder genGenerated(Item item, ResourceLocation... layers) {
		return genGenerated(item, "", layers);
	}

	private ItemModelBuilder genGenerated(Item item, String additional, ResourceLocation... layers) {
		ItemModelBuilder builder = withExistingParent(item.getRegistryName().toString()+additional, "item/generated");
		return gen(builder, layers);
	}

	private ItemModelBuilder genGenerated(Item item, boolean canFail, ResourceLocation... layers) {
		ItemModelBuilder builder = withExistingParent(item.getRegistryName().toString(), "item/generated");
		return gen(builder, canFail, layers);
	}

	private ItemModelBuilder genHandheld(Item item, ResourceLocation... layers) {
		return genHandheld(item, "", layers);
	}
	private ItemModelBuilder genHandheld(Item item, String additional, ResourceLocation... layers) {
		ItemModelBuilder builder = withExistingParent(item.getRegistryName().toString()+additional, "item/handheld");
		return gen(builder, layers);
	}

	private ItemModelBuilder genCommandHorn(Item item) {
		ItemModelBuilder builder = genGenerated(item, itemLocation(item));
		ItemModelBuilder builder_halt = genGenerated(item, "_halt", itemLocation(item, "_halt"));
		ItemModelBuilder builder_reddy = genGenerated(item, "_reddy", itemLocation(item, "_ready"));
		ItemModelBuilder builder_summon = genGenerated(item, "_summon", itemLocation(item, "_summon"));
		builder.override().model(builder_halt).predicate(ExtendedItemModelsProperties.hornType, 1);
		builder.override().model(builder_reddy).predicate(ExtendedItemModelsProperties.hornType, 2);
		builder.override().model(builder_summon).predicate(ExtendedItemModelsProperties.hornType, 3);
		return builder;
	}

	private ItemModelBuilder genModifierScroll(Item item) {
		ItemModelBuilder builder = genGenerated(item, itemLocation(item, "_wooden"));//TODO maybe have a "basic" modifier scroll texture in the future?
		ItemModelBuilder builder_wooden = genGenerated(item, "_wooden", itemLocation(item, "_wooden"));
		ItemModelBuilder builder_bronze = genGenerated(item, "_bronze", itemLocation(item, "_bronze"));
		ItemModelBuilder builder_silver = genGenerated(item, "_silver", itemLocation(item, "_silver"));
		ItemModelBuilder builder_golden = genGenerated(item, "_golden", itemLocation(item, "_golden"));
		builder.override().model(builder_wooden).predicate(ExtendedItemModelsProperties.scroll_type, 0);
		builder.override().model(builder_bronze).predicate(ExtendedItemModelsProperties.scroll_type, 1);
		builder.override().model(builder_silver).predicate(ExtendedItemModelsProperties.scroll_type, 2);
		builder.override().model(builder_golden).predicate(ExtendedItemModelsProperties.scroll_type, 3);
		return builder_wooden;
	}

	private ItemModelBuilder genCrossbow(Item item, ResourceLocation location) {
		ItemModelBuilder builder = gen(withExistingParent(item.getRegistryName().toString(), mcLoc("item/crossbow")), new ResourceLocation(location.toString()+"_standby"));
		ItemModelBuilder builder_0 = gen(withExistingParent(item.getRegistryName().toString()+"_0", builder.getLocation()), new ResourceLocation(location.toString()+"_pulling_0"));
		ItemModelBuilder builder_1 = gen(withExistingParent(item.getRegistryName().toString()+"_1", builder.getLocation()), new ResourceLocation(location.toString()+"_pulling_1"));
		ItemModelBuilder builder_2 = gen(withExistingParent(item.getRegistryName().toString()+"_2", builder.getLocation()), new ResourceLocation(location.toString()+"_pulling_2"));
		ItemModelBuilder builder_arrow = gen(withExistingParent(item.getRegistryName().toString()+"_arrow", builder.getLocation()), new ResourceLocation(location.toString()+"_arrow"));
		ItemModelBuilder builder_firework = gen(withExistingParent(item.getRegistryName().toString()+"_firework", builder.getLocation()), new ResourceLocation(location.toString()+"_firework"));
		builder.override().model(builder_0).predicate(new ResourceLocation("pulling"), 1);
		builder.override().model(builder_1).predicate(new ResourceLocation("pulling"), 1).predicate(new ResourceLocation("pull"), 0.58F);
		builder.override().model(builder_2).predicate(new ResourceLocation("pulling"), 1).predicate(new ResourceLocation("pull"), 1.0F);
		builder.override().model(builder_arrow).predicate(new ResourceLocation("charged"), 1);
		builder.override().model(builder_firework).predicate(new ResourceLocation("charged"), 1).predicate(new ResourceLocation("firework"), 1);
		return builder;
	}

	private ItemModelBuilder genBrandingIron(Item item) {
		ItemModelBuilder builder = genHandheld(item, itemLocation(item));
		ItemModelBuilder builder_iron_hot = genHandheld(item, "_hot", itemLocation(item, "_hot"));
		builder.override().model(builder_iron_hot).predicate(ExtendedItemModelsProperties.heated, 1);
		return builder;
	}
	private ItemModelBuilder gen(ItemModelBuilder builder, ResourceLocation... layers) {
		return gen(builder, false, layers);
	}
	@SuppressWarnings("unchecked")
	private ItemModelBuilder gen(ItemModelBuilder builder, boolean canFail, ResourceLocation... layers) {
		try {
			for (int i = 0; i < layers.length; i++) {
				builder = builder.texture("layer" + i, layers[i]);
			}
		}catch(IllegalArgumentException e) {
			if(!canFail) {
				for (int i = 0; i < layers.length; i++) {
					try {
						Field f1 = ModelBuilder.class.getDeclaredField("textures");
						f1.setAccessible(true);
						((Map<String, String>)f1.get(builder)).put("layer" + i, layers[i].toString());
						System.out.println("Missing texture for: "+layers[i]);
					} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e1) {
						e1.printStackTrace();
					}
				}
			}else {
				throw e;
			}
		}
		return builder;
	}

	private ItemModelBuilder genTransformWrapper(Item item, ItemModelBuilder guiModel, ItemModelBuilder heldModel) {
		ItemModelBuilder builder = getBuilder(item.getRegistryName().toString());
		builder.customLoader(ExtendedTransformWrapperModelBuilder::begin).guiModel(guiModel).heldModel(heldModel);
		return builder;
	}
}
