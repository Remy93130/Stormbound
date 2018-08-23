package fr.dut.info.stormbound.cards;

import fr.dut.info.stormbound.cards.enumeration.Rarity;
import fr.dut.info.stormbound.cards.enumeration.Target;
import fr.dut.info.stormbound.cards.enumeration.Type;

public class Spell extends AbstractCard {
	private static final long serialVersionUID = -7026940423204316479L;
	private final Target target;
	private final Type type;
	private final int value;

	/**
	 * Create a spell
	 * 
	 * @param name
	 * @param manaCost
	 * @param target
	 *            the spell's target
	 * @param type
	 *            the spell's type
	 * @param value
	 *            the value of the type
	 */
	public Spell(Rarity rarity, String name, int manaCost, Target target, Type type, int value) {
		super(rarity, name, manaCost);
		this.target = target;
		this.type = type;
		this.value = value;
	}

	@Override
	public String toString() {
		return super.toString() + ", type : " + type + ", target : " + target + ", value : " + value;
	}

	public int getValue() {
		return value;
	}

	@Override
	public int getStrength() {
		return Math.abs(value);
	}

	public Target getTarget() {
		return target;
	}

}