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
import java.util.HashSet;
import java.util.Set;

import fr.dut.info.stormbound.cards.Card;

public class OwnedCards implements Serializable {
	private static final long serialVersionUID = -1028665613048501934L;
	private final Set<Card> ownedCard;

	public OwnedCards() {
		this.ownedCard = new HashSet<>();
	}

	@Override
	public String toString() {
		return ownedCard.toString();
	}

	public void add(Card c) {
		this.ownedCard.add(c);
	}

	public Set<Card> getOwnedCard() {
		return ownedCard;
	}

	public static void save(OwnedCards o) throws IOException {
		Path path = Paths.get("ressources/OwnedCards");
		try (OutputStream save = Files.newOutputStream(path); ObjectOutputStream d = new ObjectOutputStream(save)) {
			d.writeObject(o);
		}
	}

	public static OwnedCards load() throws ClassNotFoundException, IOException {
		Path path = Paths.get("ressources/OwnedCards");
		try (InputStream save = Files.newInputStream(path); ObjectInputStream d = new ObjectInputStream(save)) {
			OwnedCards o = (OwnedCards) d.readObject();
			return o;
		}
	}
}
