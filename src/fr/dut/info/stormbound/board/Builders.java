package fr.dut.info.stormbound.board;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import fr.dut.info.stormbound.cards.Bonus;
import fr.dut.info.stormbound.cards.Building;
import fr.dut.info.stormbound.cards.Deck;
import fr.dut.info.stormbound.cards.SpecialBuilding;
import fr.dut.info.stormbound.cards.SpecialWarrior;
import fr.dut.info.stormbound.cards.Spell;
import fr.dut.info.stormbound.cards.Warrior;
import fr.dut.info.stormbound.cards.enumeration.Frequency;
import fr.dut.info.stormbound.cards.enumeration.Rarity;
import fr.dut.info.stormbound.cards.enumeration.Target;
import fr.dut.info.stormbound.cards.enumeration.Type;

public class Builders {
	static final String folder = "ressources/";
	static final String[] item = { "deck_1", "deck_2", "deck_3", "deck_4", "deck_5", "OwnedCards", "allCards" };

	/**
	 * Method to create all the cards in the game and save it in a file named
	 * allCard
	 * 
	 * @throws IOException
	 */
	public static void generateAllCard() throws IOException {
		CardCollection collection = new CardCollection();

		Bonus b01 = new Bonus(Target.ENEMY, Frequency.SUMMON, Type.ATTACK, -2, true, true);
		Bonus b02 = new Bonus(Target.ALLY, Frequency.SUMMON, Type.BUFF, 2, true, false);
		Bonus b03 = new Bonus(Target.ALL_ALLY, Frequency.ROUND, Type.BUFF, 2, true, false);
		Bonus b04 = new Bonus(Target.ENEMY, Frequency.ATTACKING, Type.ATTACK, -3, true, false);
		Bonus b05 = new Bonus(Target.ENEMY, Frequency.SUMMON, Type.ATTACK, -4, false, true);
		Bonus b06 = new Bonus(Target.ENEMY, Frequency.ROUND, Type.ATTACK, -3, true, false);
		Bonus b07 = new Bonus(Target.ENEMY, Frequency.SUMMON, Type.ATTACK, -3, true, false);
		Bonus b08 = new Bonus(Target.ALLY, Frequency.DEATH, Type.BUFF, 4, true, false);
		
		collection.add(Rarity.COMMON, new Warrior(Rarity.COMMON, "Heroic Soldier", 5, 5, 1));
		collection.add(Rarity.COMMON, new Warrior(Rarity.COMMON, "Veterans of War", 7, 8, 1));
		collection.add(Rarity.COMMON, new Warrior(Rarity.COMMON, "Warfront Runners", 4, 2, 2));
		collection.add(Rarity.COMMON, new Warrior(Rarity.COMMON, "Gifted Recruits", 2, 2, 1));
		collection.add(Rarity.COMMON, new Warrior(Rarity.COMMON, "Salty Outcasts", 7, 5, 2));
		collection.add(Rarity.COMMON, new Warrior(Rarity.COMMON, "Westwind Sailors", 3, 3, 1));
		collection.add(Rarity.COMMON, new Warrior(Rarity.COMMON, "Lawless Herd", 1, 1, 0));
		collection.add(Rarity.COMMON, new Warrior(Rarity.COMMON, "Bluesail Raiders", 5, 3, 2));
		collection.add(Rarity.COMMON, new Warrior(Rarity.COMMON, "Cabin Girls", 4, 5, 0));

		collection.add(Rarity.COMMON, new SpecialWarrior(Rarity.COMMON, "Felflares", 3, 2, 0, b01));
		collection.add(Rarity.COMMON, new SpecialWarrior(Rarity.COMMON, "Personal Server", 4, 2, 1, b02));
		collection.add(Rarity.RARE, new SpecialWarrior(Rarity.RARE, "Joust Champion", 8, 3, 2, b04));
		collection.add(Rarity.RARE, new SpecialWarrior(Rarity.RARE, "SiegeBreakers", 4, 4, 0, b05));
		collection.add(Rarity.LEGENDARY, new SpecialWarrior(Rarity.LEGENDARY, "Siren of the Seas", 10, 6, 3, b04));
		collection.add(Rarity.RARE, new SpecialBuilding(Rarity.RARE, "EmeraldTower", 4, 3, b03));
		collection.add(Rarity.EPIC, new SpecialBuilding(Rarity.EPIC, "Trueshot Post", 5, 4, b06));
		collection.add(Rarity.RARE, new SpecialWarrior(Rarity.RARE, "Boomstick Officers", 6, 6, 0, b07));
		collection.add(Rarity.RARE, new SpecialWarrior(Rarity.RARE, "Snowmasons",4,2,1,b08));
		
		collection.add(Rarity.COMMON, new Building(Rarity.COMMON, "Fort of Ebonrock", 3, 4));
		

		collection.add(Rarity.COMMON, new Spell(Rarity.COMMON, "Bladestorm", 5, Target.ALL_ENEMY, Type.ATTACK, -1));
		collection.add(Rarity.COMMON, new Spell(Rarity.COMMON, "Execution", 4, Target.ENEMY, Type.ATTACK, -3));
		collection.add(Rarity.COMMON, new Spell(Rarity.COMMON, "Confinement", 4, Target.ENEMY, Type.ATTACK, -5));
		collection.add(Rarity.COMMON, new Spell(Rarity.COMMON, "Potion of Growth", 3, Target.ALLY, Type.BUFF, 3));

		collection.saveCollection();
	}

	/**
	 * Remove a file deck if this one is corrupt
	 * 
	 * @param d
	 *            Deck to remove
	 * 
	 * @throws IOException
	 */
	public static void removeDeck(int d) {
		Path path = Paths.get("ressource/Deck_" + d);
		try {
			Files.delete(path);
		} catch (IOException e) {
			System.out.println("Probleme de permissions");
			System.err.println(e);
		}
	}

	/**
	 * Method to create the beginner deck and 2 empty deck and save it in a file
	 * named deck_1 | deck_2 | deck_3...
	 * 
	 * @throws IOException
	 */
	public static void generateStarterDeck() throws IOException {
		Deck d1 = new Deck();
		Deck d2 = new Deck();
		Deck d3 = new Deck();
		Deck d4 = new Deck();
		Deck d5 = new Deck();
		Builders.beginnerCards(d1);
		Deck.saveDeck(1, d1);
		Deck.saveDeck(2, d2);
		Deck.saveDeck(3, d3);
		Deck.saveDeck(4, d4);
		Deck.saveDeck(5, d5);
	}

	private static void beginnerCards(Deck d) {
		Bonus b01 = new Bonus(Target.ENEMY, Frequency.SUMMON, Type.ATTACK, -2, true, true);
		Bonus b02 = new Bonus(Target.ALLY, Frequency.SUMMON, Type.BUFF, 2, true, false);
		Bonus b03 = new Bonus(Target.ALLY, Frequency.ROUND, Type.BUFF, 2, true, false);
		Bonus b04 = new Bonus(Target.ENEMY, Frequency.ATTACKING, Type.ATTACK, -3, true, true);
		Bonus b05 = new Bonus(Target.ENEMY, Frequency.SUMMON, Type.ATTACK, -4, false, true);

		Warrior c01 = new Warrior(Rarity.COMMON, "Heroic Soldier", 5, 5, 1);
		Warrior c02 = new Warrior(Rarity.COMMON, "Veterans of War", 7, 8, 1);
		Warrior c03 = new Warrior(Rarity.COMMON, "Warfront Runners", 4, 2, 2);
		Warrior c04 = new Warrior(Rarity.COMMON, "Gifted Recruits", 2, 2, 1);
		SpecialWarrior c05 = new SpecialWarrior(Rarity.COMMON, "Felflares", 3, 2, 0, b01);
		SpecialWarrior c06 = new SpecialWarrior(Rarity.COMMON, "Personal Server", 4, 2, 1, b02);
		SpecialBuilding c07 = new SpecialBuilding(Rarity.COMMON, "EmeraldTower", 4, 3, b03);
		SpecialWarrior c08 = new SpecialWarrior(Rarity.COMMON, "Joust Champion", 8, 3, 2, b04);
		SpecialWarrior c09 = new SpecialWarrior(Rarity.COMMON, "SiegeBreakers", 4, 4, 0, b05);
		Building c10 = new Building(Rarity.COMMON, "Fort of Ebonrock", 3, 4);
		Spell c11 = new Spell(Rarity.COMMON, "Bladestorm", 5, Target.ALL_ENEMY, Type.ATTACK, -1);
		Spell c12 = new Spell(Rarity.COMMON, "Execution", 4, Target.ENEMY, Type.ATTACK, -3);

		d.add(c01);
		d.add(c02);
		d.add(c03);
		d.add(c04);
		d.add(c05);
		d.add(c06);
		d.add(c07);
		d.add(c08);
		d.add(c09);
		d.add(c10);
		d.add(c11);
		d.add(c12);
	}

	public static void generateStarterCards() throws IOException {
		OwnedCards oc = new OwnedCards();

		Bonus b01 = new Bonus(Target.ENEMY, Frequency.SUMMON, Type.ATTACK, -2, true, true);
		Bonus b02 = new Bonus(Target.ALLY, Frequency.SUMMON, Type.BUFF, 2, true, false);
		Bonus b03 = new Bonus(Target.ALLY, Frequency.ROUND, Type.BUFF, 2, true, false);
		Bonus b04 = new Bonus(Target.ENEMY, Frequency.ATTACKING, Type.ATTACK, -3, true, true);
		Bonus b05 = new Bonus(Target.ENEMY, Frequency.SUMMON, Type.ATTACK, -4, false, true);

		Warrior c01 = new Warrior(Rarity.COMMON, "Heroic Soldier", 5, 5, 1);
		Warrior c02 = new Warrior(Rarity.COMMON, "Veterans of War", 7, 8, 1);
		Warrior c03 = new Warrior(Rarity.COMMON, "Warfront Runners", 4, 2, 2);
		Warrior c04 = new Warrior(Rarity.COMMON, "Gifted Recruits", 2, 2, 1);
		SpecialWarrior c05 = new SpecialWarrior(Rarity.COMMON, "Felflares", 3, 2, 0, b01);
		SpecialWarrior c06 = new SpecialWarrior(Rarity.COMMON, "Personal Server", 4, 2, 1, b02);
		SpecialBuilding c07 = new SpecialBuilding(Rarity.COMMON, "EmeraldTower", 4, 3, b03);
		SpecialWarrior c08 = new SpecialWarrior(Rarity.COMMON, "Joust Champion", 8, 3, 2, b04);
		SpecialWarrior c09 = new SpecialWarrior(Rarity.COMMON, "SiegeBreakers", 4, 4, 0, b05);
		Building c10 = new Building(Rarity.COMMON, "Fort of Ebonrock", 3, 4);
		Spell c11 = new Spell(Rarity.COMMON, "Bladestorm", 5, Target.ALL_ENEMY, Type.ATTACK, -1);
		Spell c12 = new Spell(Rarity.COMMON, "Execution", 4, Target.ENEMY, Type.ATTACK, -3);

		oc.add(new Warrior(Rarity.LEGENDARY, "Remy", 5, 8, 0));
		oc.add(new Warrior(Rarity.LEGENDARY, "Melissa", 5, 6, 3));

		oc.add(c01);
		oc.add(c02);
		oc.add(c03);
		oc.add(c04);
		oc.add(c05);
		oc.add(c06);
		oc.add(c07);
		oc.add(c08);
		oc.add(c09);
		oc.add(c10);
		oc.add(c11);
		oc.add(c12);

		OwnedCards.save(oc);
	}

	/**
	 * Check if all files to launch the game is present
	 * 
	 * @return boolean
	 */
	public static boolean checkFileExist() {
		try {
			for (String file : item) {
				Path p = Paths.get(folder + file);
				if (!(Files.isRegularFile(p) && Files.isRegularFile(p))) {
					return false;
				}
			}

		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * Generate all files to launch the game
	 * 
	 * @throws IOException
	 */
	public static void createTheGame() throws IOException {
		Path path = Paths.get("ressources");
		if (Files.notExists(path)) {
			File ressources = new File("ressources");
			ressources.mkdir();
		}
		Builders.generateAllCard();
		Builders.generateStarterCards();
		Builders.generateStarterDeck();

	}
}