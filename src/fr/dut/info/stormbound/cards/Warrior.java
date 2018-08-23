package fr.dut.info.stormbound.cards;

import java.util.Objects;

import fr.dut.info.stormbound.cards.enumeration.Rarity;
import fr.dut.info.stormbound.exceptions.CardManaException;
import fr.dut.info.stormbound.exceptions.CardSpeedException;

public class Warrior extends Building {
	private static final long serialVersionUID = -835917610047389463L;
	private final int speed;

	/**
	 * Create a warrior
	 * 
	 * @param rarity
	 * @param owner
	 * @param name
	 * @param manaCost
	 * @param strength
	 * @param speed
	 *            number of move when the card is summon
	 * @throws CardManaException
	 */
	public Warrior(Rarity rarity, String name, int manaCost, int strength, int speed) {
		super(rarity, name, manaCost, strength);
		if (speed < 0) {
			throw new CardSpeedException("A card cant have a negative speed");
		}
		this.speed = speed;
	}

	@Override
	public boolean isMoving() {
		return true;
	}

	@Override
	public int getSpeed() {
		return speed;
	}

	@Override
	public String toString() {
		return super.toString() + ", speed : " + speed;
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), speed);
	}
}