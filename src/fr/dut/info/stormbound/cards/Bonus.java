package fr.dut.info.stormbound.cards;

import java.io.Serializable;
import java.util.Objects;

import fr.dut.info.stormbound.cards.enumeration.Frequency;
import fr.dut.info.stormbound.cards.enumeration.Target;
import fr.dut.info.stormbound.cards.enumeration.Type;

public class Bonus implements Serializable {
	private static final long serialVersionUID = 4647316480976182236L;
	private final Target target;
	private final Frequency frequency;
	private final Type type;
	private final int value;
	private final boolean attackUnit;
	private final boolean attackStructure;
	

	/**
	 * @param target
	 *            the target of the bonus
	 * @param freqency
	 *            when is active
	 * @param type
	 *            type of bonus
	 * @param value
	 *            the value
	 * @param attackUnit
	 *            if the card attack/power up unit on board
	 * @param attackStructure
	 *            if the card attack/power up structure on board
	 * @throws NullPointerException
	 */
	public Bonus(Target target, Frequency frequency, Type type, int value, boolean attackUnit, boolean attackStructure) {
		super();
		this.target = Objects.requireNonNull(target);
		this.frequency = Objects.requireNonNull(frequency);
		this.type = Objects.requireNonNull(type);
		this.value = value;
		this.attackStructure = attackStructure;
		this.attackUnit =  attackUnit;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("Bonus : ");
		str.append("Target -> " + target);
		str.append(", Type -> " + type);
		str.append(", Frenquency -> " + frequency);
		str.append(", Value -> " + value);
		return str.toString();
	}
	
	public Frequency getFrequency() {
		return frequency;
	}
	
	public Target getTarget() {
		return target;
	}

	public int getValue() {
		return value;
	}
	
	public boolean getAttackUnit() {
		return attackUnit;
	}
	
	public boolean getAttackStructure() {
		return attackStructure;
	}
}
