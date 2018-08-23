package fr.dut.info.stormbound.board;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import fr.dut.info.stormbound.cards.Card;
import fr.dut.info.stormbound.cards.Deck;

public class Player {
	private final int num;
	private int frontLine;
	private int base;
	private Deck deck;
	private ArrayList<Card> hand;
	private int mana;
	private int manamax;

	public Player(int num, int frontLine) {
		this.num = num;
		this.deck = new Deck();
		this.hand = new ArrayList<>();
		this.frontLine = frontLine;
		this.base = 10;
		this.mana = 3;
		manamax = mana;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("--------- Joueur ---------\n");
		str.append("Numero : " + num + "\n");
		str.append("Niveau de la ligne de front : " + frontLine + "\n");
		str.append("Composition du deck :\n");
		str.append(deck + "\n");
		str.append("Main actuelle :\n");
		for (Card card : hand) {
			str.append("\t" + card + "\n");
		}
		str.append("\n--------------------------");
		return str.toString();
	}

	public int getNum() {
		return num;
	}

	public int getBase() {
		return base;
	}

	public void updateBase(int point) {
		base -= point;
		if (base < 0) {
			base = 0;
		}
	}

	public int getFrontLine() {
		return frontLine;
	}

	public void updateFrontLine(int pos) {
		frontLine = pos;
	}

	public int getMana() {
		return mana;
	}

	public void updateMana(int point) {
		mana -= point;
	}

	public void newRoundMana() {
		manamax++;
		mana = manamax;
	}

	public ArrayList<Card> getHand() {
		return hand;
	}

	public void generateHand() {
		while (hand.size() < 4) {
			int rng = ThreadLocalRandom.current().nextInt(0, deck.getSize());
			if (!hand.contains(deck.getElement(rng))) {
				hand.add(deck.getElement(rng));
				deck.remove(deck.getElement(rng));
			}
		}
	}

	public void updateHand(int i) {
		deck.usedCard(hand.get(i));
		hand.set(i, null);
	}

	public void modifyHand(int i) {

		Card echangeCard = hand.get(i);

		deck.restoreDeck();
		int rng = ThreadLocalRandom.current().nextInt(0, deck.getSize());
		if (!hand.contains(deck.getElement(rng))) {
			hand.set(i, deck.getElement(rng));
			deck.remove(deck.getElement(rng));
		}

		deck.add(echangeCard);

	}

	public void fillHand() {
		for (int i = 0; i < hand.size(); i++) {
			deck.restoreDeck();
			if (hand.get(i) == null) {
				int rng = ThreadLocalRandom.current().nextInt(0, deck.getSize());
				if (!hand.contains(deck.getElement(rng))) {
					hand.set(i, deck.getElement(rng));
					deck.remove(deck.getElement(rng));
				}
			}
		}
	}
	
	public void setDeck(Deck deck) {
		this.deck = deck;
	}
}