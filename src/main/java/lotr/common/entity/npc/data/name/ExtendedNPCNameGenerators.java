package lotr.common.entity.npc.data.name;

import net.minecraft.util.ResourceLocation;

/**
 * Holds extended {@link NPCNameGenerator} instances for mod-specific NPCs.
 * <p>
 * Provides name generators for additional NPC types, such as Blue Dwarves,
 * using gendered name files stored under the mod's resources.
 */
public class ExtendedNPCNameGenerators {
	public static final NPCNameGenerator EASTERN_DWARF = new ParentonymicGenderedNameGenerator(extendedPath("dwarf/eastern_male"), extendedPath("dwarf/eastern_female"));
	public static final NPCNameGenerator KHUZDUL_DWARF = new ParentonymicGenderedNameGenerator(extendedPath("dwarf/khuzdul_male"), extendedPath("dwarf/khuzdul_female"));
	public static final NPCNameGenerator NORSE_DWARF = new ParentonymicGenderedNameGenerator(extendedPath("dwarf/norse_male"), extendedPath("dwarf/norse_female"));
	public static final NPCNameGenerator SINDARIAN_DWARF = new ParentonymicGenderedNameGenerator(extendedPath("dwarf/sindarin_male"), extendedPath("dwarf/sindarin_female"));

	public static final NPCNameGenerator BLUE_DWARF = WeightedNameGenerator.builder().add(KHUZDUL_DWARF, 10).add(NORSE_DWARF, 15).add(SINDARIAN_DWARF, 75).build();
	public static final NPCNameGenerator DURIN_DWARF = WeightedNameGenerator.builder().add(EASTERN_DWARF, 3).add(KHUZDUL_DWARF, 7).add(SINDARIAN_DWARF, 5).add(NORSE_DWARF, 85).build();
	public static final NPCNameGenerator WICKED_DWARF = WeightedNameGenerator.builder().add(KHUZDUL_DWARF, 45).add(EASTERN_DWARF, 50).add(NORSE_DWARF, 5).build();
	public static final NPCNameGenerator RED_DWARF = WeightedNameGenerator.builder().add(KHUZDUL_DWARF, 10).add(NORSE_DWARF, 5).add(SINDARIAN_DWARF, 85).build();

	/**
	 * Utility method to create a {@link ResourceLocation} for the given NPC name file
	 * under the {@code lotrextended} namespace.
	 * <p>
	 * This method automatically appends {@code npcs/names} as the base folder and
	 * adds the {@code .txt} extension for consistency.
	 *
	 * @param filePath The file name (without extension) representing the name list.
	 * @return A {@link ResourceLocation} pointing to the NPC name file under {@code lotrextended}.
	 */
	public static ResourceLocation extendedPath(String filePath) {
		return new ResourceLocation("lotrextended", "npcs/names/" + filePath + ".txt");
	}
}
