package fr.dut.info.stormbound.cards;

import fr.dut.info.stormbound.cards.enumeration.Rarity;
import fr.dut.info.stormbound.exceptions.CardManaException;
import fr.dut.info.stormbound.exceptions.CardStrengthException;

public class Building extends AbstractCard {
	private static final long serialVersionUID = -6990892562211501709L;
	private final int strength;

	/**
	 * Create a building
	 * 
	 * @param rarity
	 * @param owner
	 * @param name
	 * @param manaCost
	 * @param strength
	 *            the strength for the card
	 * @throws CardManaException
	 */
	public Building(Rarity rarity, String name, int manaCost, int strength) {
		super(rarity, name, manaCost);
		if (strength < 1) {
			throw new CardStrengthException("Strength for this card his invalid : " + name + " - " + strength);
		}
		this.strength = strength;
	}

	public int getStrength() {
		return strength;
	}

	@Override
	public String toString() {
		return super.toString() + ", strength : " + strength;
	}
}