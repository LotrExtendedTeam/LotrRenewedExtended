package lotr.common.entity.npc.data.name;

import net.minecraft.util.ResourceLocation;

/**
 * Holds extended {@link NPCNameGenerator} instances for mod-specific NPCs.
 * <p>
 * Provides name generators for additional NPC types, such as Blue Dwarves,
 * using gendered name files stored under the mod's resources.
 */
public class ExtendedNPCNameGenerators {
	/**
     * Name generator for Blue Dwarf NPCs, providing male and female names loaded from
     * resource files specific to Blue Dwarves.
     * <p>
     * Uses {@code npcs/names/blue_dwarf_male.txt} and {@code npcs/names/blue_dwarf_female.txt}
     * located under the {@code lotrextended} namespace in your resource path.
     */
	public static final NPCNameGenerator BLUE_DWARF = new ParentonymicGenderedNameGenerator(extendedPath("blue_dwarf_male"), extendedPath("blue_dwarf_female"));
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
