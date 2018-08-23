package fr.dut.info.stormbound.test;

import java.io.IOException;

import fr.dut.info.stormbound.board.Builders;
import fr.dut.info.stormbound.board.CardCollection;
import fr.dut.info.stormbound.board.Player;
import fr.dut.info.stormbound.cards.*;
import fr.dut.info.stormbound.cards.enumeration.Frequency;
import fr.dut.info.stormbound.cards.enumeration.Rarity;
import fr.dut.info.stormbound.cards.enumeration.Target;
import fr.dut.info.stormbound.cards.enumeration.Type;

public class Test {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		Test.testRNG();
	}

	public static void testDeck() {
		Deck deck1 = new Deck();
		Player remy = new Player(1, 0);
		Warrior c01 = new Warrior(Rarity.COMMON, "StupidSoldier", 1, 2, 0);
		Warrior c02 = new Warrior(Rarity.COMMON, "StupidRunner", 2, 2, 1);
		Warrior c03 = new Warrior(Rarity.COMMON, "StrongSoldier", 3, 3, 1);
		Warrior c04 = new Warrior(Rarity.COMMON, "LazySoldier", 3, 4, 0);
		Warrior c05 = new Warrior(Rarity.COMMON, "StrongRunner", 4, 2, 2);
		Warrior c06 = new Warrior(Rarity.COMMON, "DamnSoldier", 4, 4, 1);
		Warrior c07 = new Warrior(Rarity.COMMON, "OpSoldier", 5, 5, 1);
		Warrior c08 = new Warrior(Rarity.COMMON, "NerfPlease", 7, 7, 1);
		Warrior c09 = new Warrior(Rarity.COMMON, "God", 8, 9, 1);
		Building c10 = new Building(Rarity.COMMON, "Fort of Ebonrock", 3, 4);
		Spell c11 = new Spell(Rarity.COMMON, "Potion of Growth", 3, Target.ALLY, Type.BUFF, 3);
		Spell c12 = new Spell(Rarity.COMMON, "Execution", 4, Target.ENEMY, Type.ATTACK, 3);

		deck1.add(c01);
		deck1.add(c02);
		deck1.add(c03);
		deck1.add(c04);
		deck1.add(c05);
		deck1.add(c06);
		deck1.add(c07);
		deck1.add(c08);
		deck1.add(c09);
		deck1.add(c10);
		deck1.add(c11);
		deck1.add(c12);
		remy.setDeck(deck1);
		remy.generateHand();

		System.out.println(remy);

		Bonus bonus1 = new Bonus(Target.ALLY, Frequency.SUMMON, Type.BUFF, 2, true, true);
		Bonus bonus2 = new Bonus(Target.ALLY, Frequency.DEATH, Type.BUFF, 2, true, false);

		System.out.println(bonus1);
		System.out.println(bonus2);
	}

	public static void testCollection() throws IOException {
		CardCollection c = new CardCollection();
		System.out.println(c);
		c.saveCollection();
	}

	public static void getCollection() throws ClassNotFoundException, IOException {
		CardCollection c = CardCollection.loadCollection();
		System.out.println(c);
	}

	public static void getDeckFromFile() throws IOException, ClassNotFoundException {
		Builders.generateStarterDeck();
		Deck d = new Deck();
		d = Deck.loadDeck(1);
		Player p = new Player(1, 2);
		p.setDeck(d);
		p.generateHand();

		System.out.println(d);
		System.out.println(p);
	}

	public static void readBeginnerDeck() throws IOException {
		Builders.generateStarterDeck();

	}

	public static void tryAllFileHere() {
		System.out.println(Builders.checkFileExist());
	}

	public static void testRNG() throws ClassNotFoundException, IOException {
		CardCollection coll = CardCollection.loadCollection();
		System.out.println(coll.lootRandomCard());
	}
}