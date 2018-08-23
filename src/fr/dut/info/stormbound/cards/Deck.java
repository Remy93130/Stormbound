package fr.dut.info.stormbound.cards;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import fr.dut.info.stormbound.board.Player;

public class Deck implements Serializable {
	private static final long serialVersionUID = 1L;
	private final ArrayList<Card> deck;
	private final ArrayList<Card> deadCard;

	public Deck() {
		this.deck = new ArrayList<>();
		this.deadCard = new ArrayList<>();
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("[\n");
		for (Card e : deck) {
			str.append("\t" + e.getInfoCard() + "\n");
		}
		str.append("]");
		return str.toString();
	}

	public boolean add(Card e) {
		if (deck.size() < 12 && !deck.contains(e)) {
			return deck.add(e);
		}
		return false;
	}

	public Card getElement(int position) {
		return deck.get(position);
	}

	public void restoreDeck() {
		if (deck.isEmpty()) {
			for (Card card : deadCard) {
				deck.add(card);
			}
			deadCard.removeAll(deadCard);
		}
	}

	public boolean remove(Card e) {
		return deck.remove(e);
	}

	public int getSize() {
		return deck.size();
	}

	public void usedCard(Card e) {
		deadCard.add(e);
		deck.remove(e);
	}
	
	public boolean canBeUsed() {
		if (deck.size() == 12) {
			return true;
		} else {
			return false;
		}
	}

	public static Deck deckReadyToPlay(Player o) {
		Deck deck = new Deck();
		o.setDeck(deck);
		return deck;
	}

	public static void saveDeck(int number, Deck deck) throws IOException {
		Path path = Paths.get("ressources/deck_" + number);
		try (OutputStream save = Files.newOutputStream(path); ObjectOutputStream d = new ObjectOutputStream(save)) {
			d.writeObject(deck);
		}
	}

	public static Deck loadDeck(int number) throws IOException, ClassNotFoundException {
		Path path = Paths.get("ressources/deck_" + number);
		try (InputStream save = Files.newInputStream(path); ObjectInputStream d = new ObjectInputStream(save)) {
			Deck deck = (Deck) d.readObject();
			return deck;
		}
	}
}