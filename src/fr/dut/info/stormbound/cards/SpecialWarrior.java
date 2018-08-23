package fr.dut.info.stormbound.cards;

import java.util.Objects;

import fr.dut.info.stormbound.cards.enumeration.Rarity;

public class SpecialWarrior extends Warrior implements SpecialCard {
	private static final long serialVersionUID = -3632897116793523307L;
	private final Bonus bonus;

	/**
	 * Create a warrior with a bonus
	 * 
	 * @param rarity
	 * @param name
	 * @param manaCost
	 * @param strength
	 * @param speed
	 * 
	 * @throws NullPointerException
	 */
	public SpecialWarrior(Rarity rarity, String name, int manaCost, int strength, int speed, Bonus bonus) {
		super(rarity, name, manaCost, strength, speed);
		this.bonus = Objects.requireNonNull(bonus);
	}

	@Override
	public String toString() {
		return super.toString() + bonus;
	}

	@Override
	public boolean hasPower() {
		return true;
	}

	public Bonus getBonus() {
		return bonus;
	}
}
