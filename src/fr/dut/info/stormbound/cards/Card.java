package fr.dut.info.stormbound.cards;

public interface Card {
	
	String getName();
	int getManaCost();
	String getInfoCard();
	boolean isMoving();
	int getStrength();
	int getSpeed();
	boolean hasPower();
}
