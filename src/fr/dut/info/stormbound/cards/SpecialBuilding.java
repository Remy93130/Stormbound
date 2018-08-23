package fr.dut.info.stormbound.cards;

import java.util.Objects;

import fr.dut.info.stormbound.cards.enumeration.Rarity;

public class SpecialBuilding extends Building implements SpecialCard {
	private static final long serialVersionUID = 148283679728334080L;
	private final Bonus bonus;

	/**
	 * Create a building with a bonus
	 * 
	 * @param rarity
	 * @param name
	 * @param manaCost
	 * @param strength
	 * @param bonus
	 * 
	 * @throws NullPointerException
	 */
	public SpecialBuilding(Rarity rarity, String name, int manaCost, int strength, Bonus bonus) {
		super(rarity, name, manaCost, strength);
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

	@Override
	public Bonus getBonus() {
		return bonus;
	}
}
