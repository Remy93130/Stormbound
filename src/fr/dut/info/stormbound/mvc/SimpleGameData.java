package fr.dut.info.stormbound.mvc;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Set;

import fr.dut.info.stormbound.board.Player;
import fr.dut.info.stormbound.cards.Building;
import fr.dut.info.stormbound.cards.Card;
import fr.dut.info.stormbound.cards.SpecialCard;
import fr.dut.info.stormbound.cards.Spell;
import fr.dut.info.stormbound.cards.Warrior;
import fr.dut.info.stormbound.cards.enumeration.Frequency;
import fr.dut.info.stormbound.cards.enumeration.Target;

public class SimpleGameData {
	private final Cell[][] matrix;
	private final Card[][] ownedCard;
	private Coordinates selected;

	public SimpleGameData(int nbLines, int nbColumns) {
		matrix = new Cell[nbLines][nbColumns];
		ownedCard = new Card[6][12];
	}

	/**
	 * 
	 * @param o
	 *            the cards owned by the player
	 */
	public void initOwnedCard(Set<Card> o) {
		int i = 0;
		int j = 0;
		for (Card card : o) {
			ownedCard[i][j] = card;
			i++;
			if (i == 6) {
				i = 0;
				j++;
				if (j == 10) {
					return;
				}
			}
		}

	}

	/**
	 * Add the selected card in the potential new deck if its not already in and
	 * it's not full. Remove the selected from the potential new deck if the card is
	 * already in the deck.
	 * 
	 * @param i
	 *            the first coordinates of the card in the matrix
	 * @param j
	 *            the second coordinates of the card in the matrix
	 * @param s
	 *            The cards selected
	 */
	public void addCard(int i, int j, Set<Card> s) {
		if (ownedCard[i - 1][j] != null) {
			if (s.contains(ownedCard[i - 1][j])) {
				s.remove(ownedCard[i - 1][j]);
			} else if (s.size() < 12) {
				s.add(ownedCard[i - 1][j]);
			}
		}
	}

	/**
	 * The number of lines in the matrix contained in this GameData.
	 * 
	 * @return the number of lines in the matrix.
	 */
	public int getNbLines() {
		return matrix.length;
	}

	/**
	 * The number of columns in the matrix contained in this GameData.
	 * 
	 * @return the number of columns in the matrix.
	 */
	public int getNbColumns() {
		return matrix[0].length;
	}

	/**
	 * The color of the cell specified by its coordinates.
	 * 
	 * @param i
	 *            the first coordinate of the cell.
	 * @param j
	 *            the second coordinate of the cell.
	 * @return the color of the cell specified by its coordinates
	 */
	public Color getCellColor(int i, int j) {
		return matrix[i][j].getColor();
	}

	/**
	 * The value of the cell specified by its coordinates.
	 * 
	 * @param i
	 *            the first coordinate of the cell.
	 * @param j
	 *            the second coordinate of the cell.
	 * @return the value of the cell specified by its coordinates
	 */
	public int getCellValue(int i, int j) {
		return matrix[i][j].getValue();
	}

	/**
	 * The owner of the cell specified by its coordinates.
	 * 
	 * @param i
	 *            the first coordinate of the cell.
	 * @param j
	 *            the second coordinate of the cell.
	 * @return the owner of the cell specified by its coordinates
	 */
	public int getCellOwner(int i, int j) {
		return matrix[i][j].getOwner();
	}

	/**
	 * Updates the data contained in the GameData.
	 */
	public void updateData(int i, int j, int n, int player, Card card) {
		matrix[i][j].updateCell(n, player, card);
	}

	/**
	 * The coordinates of the cell selected, if a cell is selected.
	 * 
	 * @return the coordinates of the selected cell; null otherwise.
	 */
	public Coordinates getSelected() {
		return selected;
	}

	/**
	 * Unselects the previous selected cell (whether it was selected or not) and
	 * selects the cell identified by the specified coordinates.
	 * 
	 * @param i
	 *            the first coordinate of the cell.
	 * @param j
	 *            the second coordinate of the cell.
	 */
	public void selectCell(int i, int j) {
		selected = null;
		selected = new Coordinates(i, j);
	}

	/**
	 * Unselects both the first and the second cell (whether they were selected or
	 * not).
	 */
	public void unselect() {
		selected = null;
	}

	/**
	 * 
	 * @param i
	 *            the first coordinate of the cell
	 * @param j
	 *            the second coordinate of the cell
	 * 
	 * @return true if the specified coordinates are on the board, false otherwise
	 */
	public boolean isInGame(int i, int j) {
		return i >= 0 && i < matrix.length && j >= 0 && j < matrix[0].length;
	}

	/**
	 * 
	 * @param i
	 *            the first coordinate of the cell
	 * @param j
	 *            the second coordinate of the cell
	 * 
	 * @return true if the specified coordinates are on the button "Fin de tour",
	 *         false otherwise
	 */
	public boolean isEndRound(int i, int j) {
		return i == 4 && j == 4;
	}

	/**
	 * 
	 * @param i
	 *            the first coordinate of the cell
	 * @param j
	 *            the second coordinate of the cell
	 * 
	 * @return true if the specified coordinates are on the player's hand, false
	 *         otherwise
	 */
	public boolean isInCard(int i, int j) {
		return i >= 0 && i < 4 && j == -2;
	}

	public void InitMatrix() {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				matrix[i][j] = Cell.InitGameCell();
			}
		}
	}

	/**
	 * play a card of the Ai's hand
	 * 
	 * @param player
	 *            The Ai
	 * @param enemy
	 *            The enemy player
	 */
	public void bootPlaying(Player player, Player enemy) {
		Card selectedCard = null;
		int maxStrenght = -1;
		int pos = -1;
		ArrayList<Card> main = player.getHand();
		for (int i = 0; i < main.size(); i++) {
			if (main.get(i) != null) {
				if (player.getMana() >= main.get(i).getManaCost()) {
					if (main.get(i).getStrength() > maxStrenght) { // play the card with the most strenght
						maxStrenght = main.get(i).getStrength();
						selectedCard = main.get(i);
						pos = i;
					} else if (main.get(i).getStrength() == maxStrenght) {
						if (main.get(i).getManaCost() < selectedCard.getManaCost()) { // if two cards have the same
																						// strenght, l'IA playe the one
																						// with the less mana cost
							selectedCard = main.get(i);
							pos = i;
						}
					}
				}
			}
		}

		System.out.println(selectedCard);
		player.updateMana(selectedCard.getManaCost());
		PlacingOnBoard(selectedCard, player, enemy);
		player.updateHand(pos);

	}

	/**
	 * Place/Use the selected Card on the board
	 * 
	 * @param card
	 *            the selected card that will be played
	 * @param player
	 *            the current player
	 * @param enemy
	 *            the enemy player
	 */
	private void PlacingOnBoard(Card card, Player player, Player enemy) {
		for (int i = player.getFrontLine() - 1; i >= 0; i--) {
			for (int j = 0; j < matrix[0].length; j++) {
				if (card instanceof Spell) {
					if (useSpell(card, player, enemy, i, j)) {
						return;
					}
				} else if (matrix[i][j].getValue() == 0) {
					updateData(i, j, card.getStrength(), player.getNum(), card);

					if (card.isMoving()) {
						move(i, j, card, player, enemy);
					}
					return;
				}
			}
		}
	}

	/**
	 * 
	 * @param i
	 *            the position of the card in the player's hand
	 * @param player
	 *            the current player
	 * @return the selected card
	 */
	public Card selectCard(int i, Player player) {
		return player.getHand().get(i);

	}

	/**
	 * Delete the used card from the player's hand
	 * 
	 * @param c
	 *            the card that has been used
	 * @param player
	 *            the current player
	 */
	public void deleteCard(Card c, Player player) {
		ArrayList<Card> main = player.getHand();
		for (int i = 0; i < main.size(); i++) {
			if (main.get(i) != null) {
				if (main.get(i).equals(c)) {
					player.updateHand(i);
				}
			}
		}
	}

	/**
	 * Exchange the selected card by another card from the deck
	 * 
	 * @param c
	 *            the selected card
	 * @param player
	 *            the current player
	 */
	public void echangeCard(Card c, Player player) {
		ArrayList<Card> main = player.getHand();
		for (int i = 0; i < main.size(); i++) {
			if (main.get(i) != null) {
				if (main.get(i).equals(c)) {
					player.modifyHand(i);
				}
			}
		}
	}

	/**
	 * 
	 * @param card
	 *            the spell card played
	 * @param player
	 *            the current player
	 * @param enemy
	 *            the enemy player
	 * @param i
	 *            the first coordinate of the selected cell
	 * @param j
	 *            the second coordinate of the selected cell
	 * @return true if the spell has been used, false otherwise
	 */
	public boolean useSpell(Card card, Player player, Player enemy, int i, int j) {
		Spell c = (Spell) card;
		if (c.getTarget().equals(Target.ENEMY)) {
			if (useOneSpellMatrix(c.getValue(), enemy, i, j)) {
				return true;
			}
		} else if (c.getTarget().equals(Target.ALLY)) {
			if (useOneSpellMatrix(c.getValue(), player, i, j)) {
				return true;
			}
		} else if (c.getTarget().equals(Target.ALL_ENEMY)) {
			useAllSpellMatrix(c.getValue(), enemy);
			return true;

		} else if (c.getTarget().equals(Target.ALL_ALLY)) {
			useAllSpellMatrix(c.getValue(), player);
			return true;

		}
		return false;

	}

	/**
	 * 
	 * @param point
	 *            the value of the spell
	 * @param target
	 *            the player who will be affected by the spell
	 * @param i
	 *            the first coordinate of the selected cell
	 * @param j
	 *            the second coordinate of the selected cell
	 * @return true if the selected cell's owner was the target and the spell has
	 *         been used, false otherwise
	 */
	private boolean useOneSpellMatrix(int point, Player target, int i, int j) {

		if (matrix[i][j].getOwner() == target.getNum()) {
			int result = matrix[i][j].getValue() + point;
			if (result <= 0) {
				matrix[i][j].resetCell();
				lookingFrontLine(target);
				return true;
			} else {
				matrix[i][j].updateCell(result, target.getNum(), matrix[i][j].getCard());
				return true;
			}
		}
		return false;
	}

	/**
	 * Parse the matrix and use the spell
	 * 
	 * @param point
	 *            the value of the spell
	 * @param target
	 *            the player who will be affected by the spell
	 */
	private void useAllSpellMatrix(int point, Player target) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				useOneSpellMatrix(point, target, i, j);
			}
		}
	}

	/**
	 * 
	 * @param card
	 *            the selected card
	 * @param target
	 *            the player who will be affected by the bonus
	 */
	public void useBonus(Card card, Player player, Player enemy) {
		SpecialCard c = (SpecialCard) card;

		if (c.getBonus().getTarget().equals(Target.ALLY)) {
			useOneBonus(c, player);

		} else if (c.getBonus().getTarget().equals(Target.ENEMY)) {
			useOneBonus(c, enemy);
		}

	}

	/**
	 * Use the bonus once on the board
	 * 
	 * @param card
	 *            the selected card
	 * @param target
	 *            the player who will be affected by the spell
	 */
	private void useOneBonus(SpecialCard card, Player target) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				if (matrix[i][j].getOwner() == target.getNum()) {
					if (matrix[i][j].getCard() instanceof Warrior && card.getBonus().getAttackUnit()) { // Si concerne
																										// unités
						lookingForResult(card, target, i, j);
						return;
					} else if (matrix[i][j].getCard() instanceof Warrior && !card.getBonus().getAttackUnit()) {
						continue;
					} else if (matrix[i][j].getCard() instanceof Building && card.getBonus().getAttackStructure()) { // si
																														// concerne
																														// strutures
						lookingForResult(card, target, i, j);
						return;
					}
				}
			}
		}
	}

	/**
	 * 
	 * @param card
	 *            the selected card
	 * @param target
	 *            the player who will be affected by the spell
	 * @param m
	 *            the line from which the bonus is used
	 */
	private void useRoundBonus(SpecialCard card, Player target, int m) {
		if (target.getNum() == 1) {
			for (int i = 0; i < m; i++) {
				for (int j = 0; j < matrix[0].length; j++) {
					if (matrix[i][j].getOwner() == target.getNum()) {
						if (matrix[i][j].getCard() instanceof Warrior && card.getBonus().getAttackUnit()) {
							lookingForResult(card, target, i, j);
						}
					}
				}
			}
		} else if (target.getNum() == 2) {
			for (int i = m; i < matrix.length; i++) {
				for (int j = 0; j < matrix[0].length; j++) {
					if (matrix[i][j].getOwner() == target.getNum()) {
						if (matrix[i][j].getCard() instanceof Warrior && card.getBonus().getAttackUnit()) {
							lookingForResult(card, target, i, j);
						}
					}

				}
			}
		}
	}

	/**
	 * Figure out the value of the cell once the bonus used
	 * 
	 * @param card
	 *            the selected card
	 * @param target
	 *            the player who will be affected by the spell
	 * @param i
	 *            the first coordinate of the selected cell
	 * @param j
	 *            the second coordinate of the selected cell
	 */
	private void lookingForResult(SpecialCard card, Player target, int i, int j) {
		int result = matrix[i][j].getValue() + card.getBonus().getValue();
		if (result <= 0) {
			matrix[i][j].resetCell();
			lookingFrontLine(target);
			return;
		} else {
			matrix[i][j].updateCell(result, target.getNum(), matrix[i][j].getCard());
			return;
		}
	}

	/**
	 * if it's a unit : It attacks if there is an enemy on the left, otherwise, it
	 * attacks if there is an enemy on the right, otherwise it advances of the board
	 * (fight if there is an enemy)
	 * 
	 * @param i
	 *            the first coordinate of the cell where the unit is
	 * @param j
	 *            the first coordinate of the cell where the unit is
	 * @param card
	 *            the card of the cell
	 * @param player
	 *            the current player
	 * @param enemy
	 *            the enemy player
	 */
	public void move(int i, int j, Card card, Player player, Player enemy) {

		if (card.getSpeed() != 0) {
			int a = 1;
			if (player.getNum() == 1) { // depending on the player we parse the matrix backwards
				a = -1;
			}

			if (j - 1 >= 0) { // if enemy on the left
				if (matrix[i][j - 1].getOwner() == enemy.getNum()) {
					confrontation(i, j, i, j - 1, player, enemy);
					matrix[i][j].updateCell(0, 0, null);
					return;

				}
			}

			if (j + 1 < matrix[0].length) { // If enemy on the right
				if (matrix[i][j + 1].getOwner() == enemy.getNum()) {
					confrontation(i, j, i, j + 1, player, enemy);
					matrix[i][j].updateCell(0, 0, null);
					return;
				}
			}

			int speed = card.getSpeed();
			for (int k = 1; k <= speed; k++) {
				if (i + k * a >= 0 && i + k * a < matrix.length) {
					if (matrix[i + k * a][j].getOwner() == enemy.getNum()) {
						confrontation(i, j, i + k * a, j, player, enemy);
						matrix[i][j].resetCell();
						return;
					}
				} else {
					enemy.updateBase(matrix[i][j].getValue());
					matrix[i][j].resetCell();
					lookingFrontLine(player);
					return;
				}

			}
			updateData(i + speed * a, j, matrix[i][j].getValue(), player.getNum(), card);
			lookingFrontLine(player);
			matrix[i][j].resetCell();
		}
	}

	/**
	 * At the start of the turn, move forward all the units of one
	 * 
	 * @param player
	 *            the current player
	 * @param enemy
	 *            the enemy player
	 */
	public void startMove(Player player, Player enemy) {

		if (player.getNum() == 1) {
			for (int i = 0; i < matrix.length; i++) {
				for (int j = 0; j < matrix[0].length; j++) {
					if (i - 1 >= 0) { // if the unit is still on the board
						moveUpdate(i, j, player, enemy, -1);
					} else { // if the unit attack the base of the opponent
						if (matrix[i][j].getOwner() == player.getNum()) {
							enemy.updateBase(matrix[i][j].getValue());
							matrix[i][j].resetCell();
							lookingFrontLine(player);
						}
					}
				}
			}
		}

		else {
			for (int i = matrix.length - 1; i > -1; i--) {
				for (int j = matrix[0].length - 1; j > -1; j--) {
					if (i + 1 < matrix.length) {
						moveUpdate(i, j, player, enemy, 1);
					} else {
						if (matrix[i][j].getOwner() == player.getNum()) {
							enemy.updateBase(matrix[i][j].getValue());
							matrix[i][j].resetCell();
							lookingFrontLine(player);
						}
					}

				}
			}

		}
	}

	/**
	 * 
	 * @param i
	 *            the first coordinate of the next cell
	 * @param j
	 *            the second coordinate of the next cell
	 * @param player
	 *            the current player
	 * @param enemy
	 *            the opponent
	 * @param a
	 *            how the unit will move of the board (depending on the player)
	 */
	private void moveUpdate(int i, int j, Player player, Player enemy, int a) {

		if (matrix[i][j].getOwner() == player.getNum()) {
			if (matrix[i][j].getCard().hasPower()) {
				SpecialCard c = (SpecialCard) matrix[i][j].getCard();
				if (c.getBonus().getFrequency().equals(Frequency.ROUND)) { // if the card has a bonus that is used each
																			// round
					if (c.getBonus().getTarget().equals(Target.ALL_ALLY)) {
						useRoundBonus(c, player, i);
					} else if (c.getBonus().getTarget().equals(Target.ALL_ENEMY)) {
						useRoundBonus(c, enemy, i);
					} else {
						useBonus(matrix[i][j].getCard(), player, enemy);
					}
				}
			}

			if (matrix[i][j].getCard().isMoving()) { // if the card is a unit
				if (matrix[i + a][j].getOwner() == 0) { // if there is no one on the following cell, the unit move
														// forward
					matrix[i + a][j].updateCell(matrix[i][j].getValue(), player.getNum(), matrix[i][j].getCard());
					matrix[i][j].resetCell();
					if (player.getNum() == 2) {
						if (i + a + 1 > player.getFrontLine()) {
							player.updateFrontLine(i + a + 1);
						}
					} else if (player.getFrontLine() > i + a) {
						player.updateFrontLine(i + a);
					}

				} else if (matrix[i + a][j].getOwner() == enemy.getNum()) { // if there is the opponent, there is a
																			// battle
					confrontation(i, j, i + a, j, player, enemy);
				}
			}

		}
	}

	/**
	 * Update the board when there is a battle between the two players
	 * 
	 * @param i
	 *            the first coordinate of the first cell
	 * @param j
	 *            the second coordinate of the first cell
	 * @param m
	 *            the first coordinate of the following cell
	 * @param n
	 *            the second coordinate of the following cell
	 * @param player
	 *            the current player
	 * @param enemy
	 *            the opponent
	 */
	private void confrontation(int i, int j, int m, int n, Player player, Player enemy) {
		int resul;

		if (matrix[i][j].getCard().hasPower()) {
			SpecialCard c = (SpecialCard) matrix[i][j].getCard();
			if (c.getBonus().getFrequency().equals(Frequency.ATTACKING)) { // if the unit has a bonus when attacking
				int temp = matrix[m][n].getValue() + c.getBonus().getValue();
				if (temp < 0) {
					temp = 0;
				}
				resul = matrix[i][j].getValue() - temp;

			} else {
				resul = matrix[i][j].getValue() - matrix[m][n].getValue();
			}
		} else {
			resul = matrix[i][j].getValue() - matrix[m][n].getValue();
		}

		if (resul > 0) { // the current player has win the battle

			if (matrix[m][n].getCard().hasPower()) {
				SpecialCard c = (SpecialCard) matrix[m][n].getCard();
				if (c.getBonus().getFrequency().equals(Frequency.DEATH)) {
					useBonus(matrix[m][n].getCard(), player, enemy);
				}
			}

			matrix[m][n].updateCell(resul, player.getNum(), matrix[i][j].getCard());
			matrix[i][j].resetCell();
			if (player.getNum() == 2) {
				player.updateFrontLine(m + 1);
			} else {
				player.updateFrontLine(m);
			}
			lookingFrontLine(enemy);

		} else if (resul < 0) { // the opponent has win the battle
			if (matrix[i][j].getCard().hasPower()) {
				SpecialCard c = (SpecialCard) matrix[i][j].getCard();
				if (c.getBonus().getFrequency().equals(Frequency.DEATH)) {
					useBonus(matrix[i][j].getCard(), player, enemy);
				}
			}
			matrix[m][n].updateCell(-resul, enemy.getNum(), matrix[m][n].getCard());
			matrix[i][j].resetCell();
			lookingFrontLine(player);

		} else if (resul == 0) { // nobody win
			if (matrix[m][n].getCard().hasPower()) {
				SpecialCard c = (SpecialCard) matrix[m][n].getCard();
				if (c.getBonus().getFrequency().equals(Frequency.DEATH)) {
					useBonus(matrix[m][n].getCard(), player, enemy);
				}
			}
			if (matrix[i][j].getCard().hasPower()) {
				SpecialCard c = (SpecialCard) matrix[i][j].getCard();
				if (c.getBonus().getFrequency().equals(Frequency.DEATH)) {
					useBonus(matrix[i][j].getCard(), player, enemy);
				}
			}
			matrix[m][n].resetCell();
			matrix[i][j].resetCell();
			lookingFrontLine(player);
			lookingFrontLine(enemy);
		}
	}

	/**
	 * Parse the board and look for the frontline of the player
	 * 
	 * @param player
	 *            the concerned player
	 */
	private void lookingFrontLine(Player player) {
		if (player.getNum() == 1) {
			player.updateFrontLine(4); // if there is no cell owned by the player
			for (int i = 0; i < matrix.length; i++) {
				for (int j = 0; j < matrix[0].length; j++) {
					if (matrix[i][j].getOwner() == player.getNum()) {
						player.updateFrontLine(i);
						return;
					}
				}
			}
		} else {
			player.updateFrontLine(1); // if there is no cell owned by the player
			for (int i = matrix.length - 1; i > -1; i--) {
				for (int j = matrix[0].length - 1; j > -1; j--) {
					if (matrix[i][j].getOwner() == player.getNum()) {
						player.updateFrontLine(i + 1);
						return;
					}
				}
			}
		}
	}
}
