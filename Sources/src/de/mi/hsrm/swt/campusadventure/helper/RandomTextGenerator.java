package de.mi.hsrm.swt.campusadventure.helper;

import java.util.Random;

/**
 * Erstellt einen zufallsgenerierten Text
 */
public class RandomTextGenerator {

	private static Random rnd = new Random();
	
	private final static String TEXTS[] = {
			"Das wird dir nicht weiterhelfen.", 
			"Glaub mir, das ist falsch.", 
			"Wie sinnvoll .. warum sind wir da nicht daruf gekommen?",
			"Hey, da ein Einhorn!",
			"Mit Kanonen auf Spatzen schießen, hm?",
			"Aha, und was bringt dir das?",
			"Ähm .. ja ... warte ... ... Nein!",
			"Das passt nicht!"};
	
	
	/**
	 * Wählt zufällig einen String aus und gibt diesen zurück.
	 * 
	 * @return zufälligen String in TEXTS[]
	 */
	public static String getRandomText() {
		return TEXTS[rnd.nextInt(TEXTS.length)];
	}
}
