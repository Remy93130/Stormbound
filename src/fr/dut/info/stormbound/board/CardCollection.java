package fr.dut.info.stormbound.board;

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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import fr.dut.info.stormbound.cards.Card;
import fr.dut.info.stormbound.cards.enumeration.Rarity;

public class CardCollection implements Serializable {
	private static final long serialVersionUID = -135899840756693689L;
	private final Map<Rarity, Set<Card>> collection;

	public CardCollection() {
		collection = new HashMap<>();
		for (Rarity r : Rarity.values()) {
			collection.put(r, new HashSet<>());
		}
	}

	public void saveCollection() throws IOException {
		Path path = Paths.get("ressources/allCards");
		try (OutputStream save = Files.newOutputStream(path); ObjectOutputStream c = new ObjectOutputStream(save)) {
			c.writeObject(this);
		}
	}

	public static CardCollection loadCollection() throws IOException, ClassNotFoundException {
		Path path = Paths.get("ressources/allCards");
		try (InputStream save = Files.newInputStream(path); ObjectInputStream c = new ObjectInputStream(save)) {
			CardCollection cardCollection = (CardCollection) c.readObject();
			return cardCollection;
		}
	}

	public Map<Rarity, Set<Card>> getCollection() {
		return collection;
	}

	public void add(Rarity rarity, Card c) {
		collection.get(rarity).add(c);
	}

	@Override
	public String toString() {
		return collection.toString();
	}

	/**
	 * Generate a card with RNG
	 * 
	 * @return Card looted
	 */
	public Card lootRandomCard() {
		int rng = ThreadLocalRandom.current().nextInt(0, 100 + 1);
		Rarity type = null;
		if (rng < 74) {
			type = Rarity.COMMON;
		} else if (rng < 94) {
			type = Rarity.RARE;
		} else if (rng < 99) {
			type = Rarity.EPIC;
		} else if (rng == 100) {
			type = Rarity.LEGENDARY;
		}
		System.out.println(type);
		System.out.println(collection);
		Set<Card> possibleLoot = collection.get(type);
		ArrayList<Card> loot = new ArrayList<>();
		loot.addAll(possibleLoot);
		System.out.println("SIZE ARRAY : " + possibleLoot.size());
		rng = ThreadLocalRandom.current().nextInt(possibleLoot.size());
		Card lootedCard = loot.get(rng);
		return lootedCard;
	}
}
