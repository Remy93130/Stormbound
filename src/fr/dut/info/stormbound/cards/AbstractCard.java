package fr.dut.info.stormbound.cards;

import java.io.Serializable;
import java.util.Objects;

import fr.dut.info.stormbound.cards.enumeration.Rarity;
import fr.dut.info.stormbound.exceptions.CardManaException;

abstract class AbstractCard implements Card, Serializable {
	private static final long serialVersionUID = -7557643328666600620L;
	private final Rarity rarity;
	private final String name;
	private final int manaCost;

	/**
	 * Main constructor for all cards
	 * 
	 * @param rarity
	 *            the rarity of the card
	 * @param name
	 *            the name of the card
	 * @param manaCost
	 *            the cost for summon the card
	 * 
	 * @throws CardManaException
	 * @throws NullPointerException
	 */
	public AbstractCard(Rarity rarity, String name, int manaCost) throws CardManaException {
		this.rarity = Objects.requireNonNull(rarity);
		this.name = Objects.requireNonNull(name);
		if (manaCost < 1) {
			throw new CardManaException("The mana for this card his invalid : " + name + " - " + manaCost);
		}
		this.manaCost = Objects.requireNonNull(manaCost);
	}

	public void summon() {
		System.out.println("Card summoned");

	}

	@Override
	public String toString() {
		return "Card : Rarity : " + rarity + ", nom : " + name + ", mana : " + manaCost;
	}

	public String getInfoCard() {
		return name + ", " + manaCost;
	}

	public String getName() {
		return name;
	}

	@Override
	public int getManaCost() {
		return manaCost;
	}

	public int getStrength() {
		return 0;
	}

	public int getSpeed() {
		return 0;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof AbstractCard)) {
			return false;
		}
		AbstractCard c = (AbstractCard) o;
		return name.equals(c.name) && manaCost == c.manaCost;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, manaCost);
	}

	public boolean isMoving() {
		return false;
	}

	@Override
	public boolean hasPower() {
		return false;
	}
}