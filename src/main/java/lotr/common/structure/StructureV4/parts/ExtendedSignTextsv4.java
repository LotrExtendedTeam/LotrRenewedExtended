package lotr.common.structure.StructureV4.parts;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// TODO move to Json driven loading texts
public class ExtendedSignTextsv4 {
	public static List<String> ShireTavernTexts(Random rand) {
		List<String> hobbitTavern = new ArrayList<String>() {{add(" ");add("The Tipsy");add("Turtle");add(" ");}};
		return hobbitTavern;
	}

	public static List<String> ShireHouseTexts(Random random) {
		List<String> hobbitHouse = new ArrayList<String>() {{add(" ");add("A Hobbit's");add("Home");add(" ");}};
		return hobbitHouse;
	}
}
