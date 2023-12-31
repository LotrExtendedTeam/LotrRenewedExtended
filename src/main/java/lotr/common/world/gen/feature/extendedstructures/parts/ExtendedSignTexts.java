package lotr.common.world.gen.feature.extendedstructures.parts;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class ExtendedSignTexts {
	public static List<String> ShireTavernTexts(Random rand) {
		List<String> hobbitTavern = new ArrayList<String>() {{add(" ");add("Tavern Text");add("Place-holder");add(" ");}};
		return hobbitTavern;
	}

	public static List<String> ShireHouseTexts(Random random) {
		List<String> hobbitHouse = new ArrayList<String>() {{add(" ");add("A Hobbit's");add("Home");add(" ");}};
		return hobbitHouse;
	}
}
