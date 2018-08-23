package fr.dut.info.stormbound.mvc;

import java.awt.Color;
import java.util.Objects;

import fr.dut.info.stormbound.cards.Card;

//import java.util.Random;

public class Cell {
	private Color color;
	private int value;
	private int owner;
	private Card card;

	private final static Color[] colors = { Color.BLACK, Color.BLUE, Color.RED }; 
	// Color Black = No one
	// Color Blue : player 1 
	// Color Red : AI

	public Cell(int i, int value) {
		this.color = colors[Objects.checkIndex(i, colors.length)];
		this.value = value;
		owner = i;
		card = null;
	}

	public static Cell InitGameCell() {
		return new Cell(0, 0);
	}

	public int getValue() {
		return value;
	}

	public Color getColor() {
		return color;
	}

	public Card getCard() {
		return card;
	}

	public int getOwner() {
		return owner;
	}

	@Override
	public String toString() {
		return color.toString();
	}

	public void resetCell() {
		value = 0;
		owner = 0;
		color = colors[Objects.checkIndex(owner, colors.length)];
		card = null;
	}

	public void updateCell(int n, int player, Card card) {
		value = n;
		owner = player;
		color = colors[Objects.checkIndex(owner, colors.length)];
		this.card = card;
	}
}
