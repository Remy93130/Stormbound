package fr.dut.info.stormbound.mvc;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import fr.dut.info.stormbound.board.OwnedCards;
import fr.dut.info.stormbound.board.Player;
import fr.dut.info.stormbound.cards.Building;
import fr.dut.info.stormbound.cards.Card;
import fr.dut.info.stormbound.cards.Spell;
import fr.dut.info.stormbound.cards.enumeration.Target;
import fr.umlv.zen5.ApplicationContext;

public class SimpleGameView {
	private final int xOrigin;
	private final int yOrigin;
	private final int width;
	private final int length;
	private final int squareSize;

	private SimpleGameView(int xOrigin, int yOrigin, int length, int width, int squareSize) {
		this.xOrigin = xOrigin;
		this.yOrigin = yOrigin;
		this.length = length;
		this.width = width;
		this.squareSize = squareSize;
	}

	public static SimpleGameView initGameGraphics(int xOrigin, int yOrigin, int length, SimpleGameData data) {
		int squareSize = (int) (length * 1.0 / data.getNbLines());
		return new SimpleGameView(xOrigin, yOrigin, length, data.getNbColumns() * squareSize, squareSize);
	}

	private int indexFromReaCoord(float coord, int origin) {
		return (int) Math.floor(((coord - origin) / squareSize));
	}

	/**
	 * Transforms a real y-coordinate into the index of the corresponding line.
	 * 
	 * @param y
	 *            a float y-coordinate
	 * @return the index of the corresponding line.
	 * @throws IllegalArgumentException
	 *             if the float coordinate doesn't fit in the game board.
	 */
	public int lineFromY(float y) {
		return indexFromReaCoord(y, yOrigin);
	}

	/**
	 * Transforms a real x-coordinate into the index of the corresponding column.
	 * 
	 * @param x
	 *            a float x-coordinate
	 * @return the index of the corresponding column.
	 * @throws IllegalArgumentException
	 *             if the float coordinate doesn't fit in the game board.
	 */
	public int columnFromX(float x) {
		return indexFromReaCoord(x, xOrigin);
	}
	
	public int findCoord(float x) {
		return indexFromReaCoord(x, 0);
	}

	private float realCoordFromIndex(int index, int origin) {
		return origin + index * squareSize;
	}

	private float xFromI(int i) {
		return realCoordFromIndex(i, xOrigin);
	}

	private float yFromJ(int j) {
		return realCoordFromIndex(j, yOrigin);
	}

	private RectangularShape drawCell(int i, int j) {
		return new Rectangle2D.Float(xFromI(j) + 2, yFromJ(i) + 2, squareSize - 4, squareSize - 4);
	}

	/*
	 * private RectangularShape drawSelectedCell(int i, int j) { return new
	 * Rectangle2D.Float(xFromI(j), yFromJ(i), squareSize, squareSize); }
	 */

	private void drawMenu(Graphics2D graphics) {
		graphics.setColor(Color.pink);
		graphics.fill(drawCell(1, -4));
		graphics.fill(drawCell(3, -4));
		graphics.fill(drawCell(4, 7));

		graphics.setColor(Color.RED);

		graphics.setFont(new Font("TimesRoman", Font.PLAIN, (int) (squareSize / 2.5)));
		graphics.drawString("Bienvenue sur THE Stormbound !! ", xFromI(-1) + squareSize / 4,
				yFromJ(2) + squareSize / 2);

		graphics.setColor(Color.BLACK);
		graphics.setFont(new Font("TimesRoman", Font.PLAIN, squareSize / 6));
		graphics.drawString("Jouer", xFromI(-4) + squareSize / 4, yFromJ(1) + (int) (squareSize / 1.8));
		graphics.drawString("Quitter", xFromI(7) + squareSize / 7, yFromJ(4) + (int) (squareSize / 1.8));

		graphics.setFont(new Font("TimesRoman", Font.PLAIN, squareSize / 8));
		graphics.drawString("Creer un deck", xFromI(-4) + 5, yFromJ(3) + squareSize / 2);

	}
	
	private void winningCard(Graphics2D graphics, Card c) {
		
		graphics.setColor(Color.MAGENTA);
		graphics.setFont(new Font("TimesRoman", Font.PLAIN, squareSize / 4));
		graphics.drawString("Carte gagné : " , xFromI(-4), yFromJ(1)-(squareSize/2));
		
		graphics.fill(new Rectangle2D.Float(xFromI(-4), yFromJ(1), squareSize, squareSize));
		graphics.setColor(Color.WHITE);
		graphics.fill(drawCell(1, -4));
		
		graphics.setColor(Color.MAGENTA);
		graphics.setFont(new Font("TimesRoman", Font.PLAIN, squareSize / 10));
		graphics.drawString(c.getName(), xFromI(-4) + squareSize / 14, yFromJ(1) + squareSize / 6);
		graphics.setFont(new Font("TimesRoman", Font.PLAIN, squareSize / 4));
		graphics.drawString(Integer.toString(c.getManaCost()), xFromI(-4) + (int) (squareSize / 2.6),
				yFromJ(1) + (int) (squareSize / 1.8));
		graphics.setFont(new Font("TimesRoman", Font.PLAIN, squareSize / 10));
		graphics.drawString("Mana :", xFromI(-4) + squareSize / 14, yFromJ(1) + squareSize / 3);

		if (c instanceof Building) {
			Building card = (Building) c;
			graphics.drawString("Force :", xFromI(-4) + squareSize / 14, yFromJ(1) + (int) (squareSize / 1.1));
			graphics.drawString(Integer.toString(card.getStrength()), xFromI(-4) + (int) (squareSize / 1.3),
					yFromJ(1) + (int) (squareSize / 1.1));
			if (card.isMoving()) {
				graphics.drawString("Mouvement :", xFromI(-4) + squareSize / 14,
						yFromJ(1) + (int) (squareSize / 1.4));
				graphics.drawString(Integer.toString(card.getSpeed()), xFromI(-4) + (int) (squareSize / 1.3),
						yFromJ(1) + (int) (squareSize / 1.4));
			}
		} else if (c instanceof Spell) {
			Spell card = (Spell) c;
			graphics.drawString("Force :", xFromI(-4) + squareSize / 14, yFromJ(1) + (int) (squareSize / 1.1));
			graphics.drawString(Integer.toString(card.getValue()), xFromI(-4) + (int) (squareSize / 1.3),
					yFromJ(1) + (int) (squareSize / 1.1));
		}
		
	}

	private void reset(Graphics2D graphics, float wid, float hei) {
		graphics.setColor(Color.WHITE);
		graphics.fill(new Rectangle2D.Float(0, 0, wid, hei));
	}

	/**
	 * Draws the game board from its data, using an existing Graphics2D object.
	 * 
	 * @param graphics
	 *            a Graphics2D object provided by the default method
	 *            {@code draw(ApplicationContext, GameData)}
	 * @param data
	 *            the GameData containing the game data.
	 */
	private void draw(Graphics2D graphics, SimpleGameData data, Card card, Player us) {
		graphics.setColor(Color.LIGHT_GRAY);
		graphics.fill(new Rectangle2D.Float(xOrigin, yOrigin, width, length));

		graphics.setColor(Color.WHITE);
		for (int i = 0; i <= data.getNbLines(); i++) {
			graphics.draw(
					new Line2D.Float(xOrigin, yOrigin + i * squareSize, xOrigin + width, yOrigin + i * squareSize));
		}

		for (int i = 0; i <= data.getNbColumns(); i++) {
			graphics.draw(
					new Line2D.Float(xOrigin + i * squareSize, yOrigin, xOrigin + i * squareSize, yOrigin + length));
		}

		for (int i = 0; i < data.getNbLines(); i++) {
			for (int j = 0; j < data.getNbColumns(); j++) {
				graphics.setColor(Color.WHITE);
				graphics.fill(drawCell(i, j));
				graphics.setColor(data.getCellColor(i, j));
				if (data.getCellColor(i, j) == Color.BLACK) {
					continue;
				}
				graphics.drawString(Integer.toString(data.getCellValue(i, j)), xFromI(j) + squareSize / 2,
						yFromJ(i) + squareSize / 2);
			}
		}

		if (card != null) { // Fill the cell where one can play in green
			if (card.getManaCost() <= us.getMana()) {
				if (card instanceof Spell) { // if its a spell
					Spell c = (Spell) card;
					for (int i = 0; i < data.getNbLines(); i++) {
						for (int j = 0; j < data.getNbColumns(); j++) {
							if (data.getCellOwner(i, j) == 2 && c.getTarget().equals(Target.ENEMY)) { // if the spell
																										// attacks the
																										// enemies
								graphics.setColor(Color.GREEN);
								graphics.fill(drawCell(i, j));
								graphics.setColor(data.getCellColor(i, j));
								if (data.getCellColor(i, j) == Color.BLACK) {
									continue;
								}
								graphics.drawString(Integer.toString(data.getCellValue(i, j)),
										xFromI(j) + squareSize / 2, yFromJ(i) + squareSize / 2);

							} else if (data.getCellOwner(i, j) == 1 && c.getTarget().equals(Target.ALLY)) { // if the
																											// spell
																											// helps the
																											// allies
								graphics.setColor(Color.GREEN);
								graphics.fill(drawCell(i, j));
								graphics.setColor(data.getCellColor(i, j));
								if (data.getCellColor(i, j) == Color.BLACK) {
									continue;
								}
								graphics.drawString(Integer.toString(data.getCellValue(i, j)),
										xFromI(j) + squareSize / 2, yFromJ(i) + squareSize / 2);
							}
						}

					}

				} else { // if its a unit or structure
					for (int i = us.getFrontLine(); i < data.getNbLines(); i++) {
						for (int j = 0; j < data.getNbColumns(); j++) {
							if (data.getCellOwner(i, j) == 0) {
								graphics.setColor(Color.GREEN);
								graphics.fill(drawCell(i, j));
							}
						}
					}

				}
			}
		}

		// Button "FIN DU TOUR"
		graphics.setColor(Color.LIGHT_GRAY);
		graphics.fill(drawCell(4, 4));
		graphics.setColor(Color.RED);
		graphics.drawString("Fin du tour", xFromI(4) + squareSize / 4, yFromJ(4) + +squareSize / 2);

	}

	/**
	 * Draws the base and point base, using an existing Graphics2D object.
	 * 
	 * @param graphics
	 *            a Graphics2D object provided by the default method
	 *            {@code draw(ApplicationContext, GameData)}
	 * @param player
	 *            the concerned player
	 */
	private void drawBase(Graphics2D graphics, Player player) {
		if (player.getNum() == 1) {
			graphics.setColor(Color.BLUE);
			graphics.fill(
					new Rectangle2D.Float(xOrigin, yOrigin + squareSize * 5, squareSize * 4, (int) (squareSize / 1.2)));
			graphics.setFont(new Font("TimesRoman", Font.PLAIN, (int) (squareSize / 1.6)));
			graphics.setColor(Color.WHITE);
			graphics.drawString(Integer.toString(player.getBase()), xOrigin + (squareSize * 2 - squareSize / 2),
					yOrigin + (squareSize * 6 - squareSize / 3));

		}

		else if (player.getNum() == 2) {
			graphics.setColor(Color.RED);
			graphics.fill(new Rectangle2D.Float(xOrigin, yOrigin - (int) (squareSize / 1.2), squareSize * 4,
					(int) (squareSize / 1.2)));
			graphics.setFont(new Font("TimesRoman", Font.PLAIN, (int) (squareSize / 1.6)));
			graphics.setColor(Color.WHITE);
			graphics.drawString(Integer.toString(player.getBase()), xOrigin + (squareSize * 2 - squareSize / 2),
					yOrigin - squareSize / 5);

		}
	}

	/**
	 * Draws the player's frontline, using an existing Graphics2D object.
	 * 
	 * @param graphics
	 *            a Graphics2D object provided by the default method
	 *            {@code draw(ApplicationContext, GameData)}
	 * @param player
	 *            the concerned player
	 */
	private void drawFront(Graphics2D graphics, Player player) {
		if (player.getNum() == 1) {
			graphics.setColor(Color.BLUE);
		}

		else if (player.getNum() == 2) {
			graphics.setColor(Color.RED);
		}

		graphics.setStroke(new BasicStroke(3));
		graphics.draw(new Line2D.Double(xOrigin, yOrigin + (player.getFrontLine() * squareSize),
				xOrigin + (4 * squareSize), yOrigin + (player.getFrontLine() * squareSize)));
	}

	/**
	 * Draws the mana of the player, using an existing Graphics2D object.
	 * 
	 * @param graphics
	 *            a Graphics2D object provided by the default method
	 *            {@code draw(ApplicationContext, GameData)}
	 * @param player
	 *            the concerned player
	 */
	private void drawMana(Graphics2D graphics, Player player) {

		graphics.setColor(Color.BLACK);
		graphics.fill(new Rectangle2D.Float(xFromI(6), yFromJ(2), squareSize, squareSize));
		graphics.setColor(Color.WHITE);
		graphics.fill(drawCell(2, 6));
		graphics.setColor(Color.black);
		graphics.setFont(new Font("TimesRoman", Font.PLAIN, squareSize / 3));
		graphics.drawString("Mana:", xFromI(6), yFromJ(2) - squareSize / 17);
		graphics.setColor(Color.RED);
		graphics.setFont(new Font("TimesRoman", Font.PLAIN, squareSize / 2));
		graphics.drawString(Integer.toString(player.getMana()), xFromI(6) + squareSize / 3,
				(int) (yFromJ(2) + squareSize / 1.5));
	}

	/**
	 * Draws the hand of the player, using an existing Graphics2D object.
	 * 
	 * @param graphics
	 *            a Graphics2D object provided by the default method
	 *            {@code draw(ApplicationContext, GameData)}
	 * @param data
	 *            the GameData containing the game data.
	 * @param player
	 *            the concerned player
	 */
	private void drawMain(Graphics2D graphics, Player player, SimpleGameData data) {

		graphics.setColor(Color.BLACK);
		graphics.fill(new Rectangle2D.Float(xFromI(-2), yFromJ(4), squareSize, squareSize));
		graphics.setColor(Color.WHITE);
		graphics.fill(drawCell(4, -2));
		graphics.setColor(Color.black);
		graphics.setFont(new Font("TimesRoman", Font.PLAIN, squareSize / 10));
		graphics.drawString("Changer de carte", xFromI(-2) + squareSize / 14, yFromJ(4) + squareSize / 2);

		ArrayList<Card> main = player.getHand();
		for (int i = 0; i < 4; i++) {
			graphics.setColor(Color.BLACK);
			graphics.fill(new Rectangle2D.Float(xFromI(-2), yFromJ(i), squareSize, squareSize));
			graphics.setColor(Color.WHITE);
			graphics.fill(drawCell(i, -2));
		}

		Coordinates c = data.getSelected(); // if a card is selected, the selected cell is green
		if (c != null) {
			if (c.getI() != 5) {
				graphics.setColor(Color.GREEN);
				graphics.fill(drawCell(c.getI(), c.getJ()));
			}
		}

		for (int i = 0; i < main.size(); i++) {
			if (main.get(i) != null) {
				graphics.setColor(Color.black);
				graphics.setFont(new Font("TimesRoman", Font.PLAIN, squareSize / 10));
				graphics.drawString(main.get(i).getName(), xFromI(-2) + squareSize / 14, yFromJ(i) + squareSize / 6);
				graphics.setFont(new Font("TimesRoman", Font.PLAIN, squareSize / 4));
				graphics.drawString(Integer.toString(main.get(i).getManaCost()), xFromI(-2) + (int) (squareSize / 2.6),
						yFromJ(i) + (int) (squareSize / 1.8));
				graphics.setFont(new Font("TimesRoman", Font.PLAIN, squareSize / 10));
				graphics.drawString("Mana :", xFromI(-2) + squareSize / 14, yFromJ(i) + squareSize / 3);

				if (main.get(i) instanceof Building) {
					Building card = (Building) main.get(i);
					graphics.drawString("Force :", xFromI(-2) + squareSize / 14, yFromJ(i) + (int) (squareSize / 1.1));
					graphics.drawString(Integer.toString(card.getStrength()), xFromI(-2) + (int) (squareSize / 1.3),
							yFromJ(i) + (int) (squareSize / 1.1));
					if (card.isMoving()) {
						graphics.drawString("Mouvement :", xFromI(-2) + squareSize / 14,
								yFromJ(i) + (int) (squareSize / 1.4));
						graphics.drawString(Integer.toString(card.getSpeed()), xFromI(-2) + (int) (squareSize / 1.3),
								yFromJ(i) + (int) (squareSize / 1.4));
					}
				} else if (main.get(i) instanceof Spell) {
					Spell card = (Spell) main.get(i);
					graphics.drawString("Force :", xFromI(-2) + squareSize / 14, yFromJ(i) + (int) (squareSize / 1.1));
					graphics.drawString(Integer.toString(card.getValue()), xFromI(-2) + (int) (squareSize / 1.3),
							yFromJ(i) + (int) (squareSize / 1.1));
				}

			} else {
				graphics.setColor(Color.BLACK);
				graphics.fill(new Rectangle2D.Float(xFromI(-2), yFromJ(i), squareSize, squareSize));
				graphics.setColor(Color.WHITE);
				graphics.fill(drawCell(i, -2));
			}

		}
	}

	/**
	 * Display the current player, using an existing Graphics2D object.
	 * 
	 * @param graphics
	 *            a Graphics2D object provided by the default method
	 *            {@code draw(ApplicationContext, GameData)}
	 * @param player
	 *            the current player
	 */
	private void drawCurrentPlayer(Graphics2D graphics, Player player) {
		graphics.setColor(Color.WHITE);
		graphics.fill(new Rectangle2D.Float(xFromI(6), yFromJ(-1), squareSize * 2, squareSize * 2));
		graphics.setColor(Color.black);
		graphics.setFont(new Font("TimesRoman", Font.PLAIN, squareSize / 3));
		graphics.drawString("Joueur " + Integer.toString(player.getNum()), xFromI(6), yFromJ(0));
	}

	/**
	 * Display which player has win, using an existing Graphics2D object.
	 * 
	 * @param graphics
	 *            a Graphics2D object provided by the default method
	 *            {@code draw(ApplicationContext, GameData)}
	 * @param player
	 *            the winning player
	 */
	private void drawFinish(Graphics2D graphics, Player player) {
		graphics.setColor(Color.MAGENTA);
		graphics.setFont(new Font("TimesRoman", Font.PLAIN, squareSize / 3));
		graphics.drawString("Joueur " + Integer.toString(player.getNum()) + " a gagne", xFromI(-4), yFromJ(0)-(squareSize/2));

	}
	
	private void removeSentence(Graphics2D graphics) {
		graphics.setColor(Color.WHITE);
		graphics.fill(new Rectangle2D.Float(xFromI(-1), yFromJ(4), squareSize*4, squareSize));
	}
	
	private void selectionDeck(Graphics2D graphics, int choice) {
		graphics.setColor(Color.RED);
		graphics.fill(new Rectangle2D.Float(xFromI(4), yFromJ(2), squareSize, squareSize));
		graphics.fill(new Rectangle2D.Float(xFromI(3), yFromJ(2), squareSize, squareSize));
		graphics.fill(new Rectangle2D.Float(xFromI(2), yFromJ(2), squareSize, squareSize));
		graphics.fill(new Rectangle2D.Float(xFromI(1), yFromJ(2), squareSize, squareSize));
		graphics.fill(new Rectangle2D.Float(xFromI(0), yFromJ(2), squareSize, squareSize));
		graphics.fill(new Rectangle2D.Float(xFromI(5), yFromJ(4), squareSize, squareSize));
		graphics.setColor(Color.WHITE);
		graphics.fill(drawCell(2, 4));
		graphics.fill(drawCell(2, 3));
		graphics.fill(drawCell(2, 2));
		graphics.fill(drawCell(2, 1));
		graphics.fill(drawCell(2, 0));
		graphics.fill(drawCell(4, 5));
		
		if (choice != 0) {
			graphics.setColor(Color.GREEN);
			graphics.fill(drawCell(2, choice-1));
		}
		
		graphics.setColor(Color.BLACK);
		graphics.setFont(new Font("TimesRoman", Font.PLAIN, squareSize / 6));
		graphics.drawString("Deck 5", xFromI(4) + squareSize / 5, yFromJ(2) + squareSize / 2);
		graphics.drawString("Deck 4", xFromI(3) + squareSize / 5, yFromJ(2) + squareSize / 2);
		graphics.drawString("Deck 3", xFromI(2) + squareSize / 5, yFromJ(2) + squareSize / 2);
		graphics.drawString("Deck 2", xFromI(1) + squareSize / 5, yFromJ(2) + squareSize / 2);
		graphics.drawString("Deck 1", xFromI(0) + squareSize / 5, yFromJ(2) + squareSize / 2);
		graphics.drawString("Jouer", xFromI(5) + squareSize / 4, yFromJ(4) + squareSize / 2);
	}
	
	private void canNotPlay(Graphics2D graphics) {
		graphics.setColor(Color.BLACK);
		graphics.setFont(new Font("TimesRoman", Font.PLAIN, squareSize / 4));
		graphics.drawString("Ce deck n'a pas encore été crée.", xFromI(-1), yFromJ(4) + squareSize / 2);
		
	}

	private void cards(Graphics2D graphics, OwnedCards o, Set<Card> s, int choice) {
		graphics.setColor(Color.RED);
		graphics.fill(new Rectangle2D.Float(xFromI(6), yFromJ(-1), squareSize, squareSize));
		graphics.fill(new Rectangle2D.Float(xFromI(7), yFromJ(-1), squareSize, squareSize));
		graphics.fill(new Rectangle2D.Float(xFromI(3), yFromJ(-1), squareSize, squareSize));
		graphics.fill(new Rectangle2D.Float(xFromI(2), yFromJ(-1), squareSize, squareSize));
		graphics.fill(new Rectangle2D.Float(xFromI(1), yFromJ(-1), squareSize, squareSize));
		graphics.fill(new Rectangle2D.Float(xFromI(0), yFromJ(-1), squareSize, squareSize));
		graphics.fill(new Rectangle2D.Float(xFromI(-1), yFromJ(-1), squareSize, squareSize));
		graphics.setColor(Color.WHITE);
		graphics.fill(new Rectangle2D.Float(xFromI(-1), yFromJ(4), squareSize*3, squareSize));
		graphics.fill(drawCell(-1, 6));
		graphics.fill(drawCell(-1, 7));
		graphics.fill(drawCell(-1, 3));
		graphics.fill(drawCell(-1, 2));
		graphics.fill(drawCell(-1, 1));
		graphics.fill(drawCell(-1, 0));
		graphics.fill(drawCell(-1, -1));
		
		if (choice != 0) {
			graphics.setColor(Color.GREEN);
			graphics.fill(drawCell(-1, choice -2));
		}
		
		graphics.setColor(Color.BLACK);
		graphics.setFont(new Font("TimesRoman", Font.PLAIN, squareSize / 6));
		graphics.drawString("Save deck", xFromI(6) + squareSize / 5, yFromJ(-1) + squareSize / 2);
		graphics.drawString("Retour", xFromI(7) + squareSize / 5, yFromJ(-1) + squareSize / 2);
		graphics.drawString("Deck 5", xFromI(3) + squareSize / 5, yFromJ(-1) + squareSize / 2);
		graphics.drawString("Deck 4", xFromI(2) + squareSize / 5, yFromJ(-1) + squareSize / 2);
		graphics.drawString("Deck 3", xFromI(1) + squareSize / 5, yFromJ(-1) + squareSize / 2);
		graphics.drawString("Deck 2", xFromI(0) + squareSize / 5, yFromJ(-1) + squareSize / 2);
		graphics.drawString("Deck 1", xFromI(-1) + squareSize / 5, yFromJ(-1) + squareSize / 2);
		
		
		
		Set<Card> cards = new HashSet<>();
		cards = o.getOwnedCard();
		int i = 0;
		int j = -4;
		for (Card c : cards) {
			graphics.setColor(Color.BLUE);
			graphics.fill(new Rectangle2D.Float(xFromI(j), yFromJ(i), squareSize, squareSize));
			graphics.setColor(Color.WHITE);
			graphics.fill(drawCell(i, j));
			
			if (s.contains(c)) {
				graphics.setColor(Color.GREEN);
				graphics.fill(drawCell(i, j));
			}
			
			graphics.setColor(Color.black);
			graphics.setFont(new Font("TimesRoman", Font.PLAIN, squareSize / 10));
			graphics.drawString(c.getName(), xFromI(j) + squareSize / 14, yFromJ(i) + squareSize / 6);
			graphics.setFont(new Font("TimesRoman", Font.PLAIN, squareSize / 4));
			graphics.drawString(Integer.toString(c.getManaCost()), xFromI(j) + (int) (squareSize / 2.6),
					yFromJ(i) + (int) (squareSize / 1.8));
			graphics.setFont(new Font("TimesRoman", Font.PLAIN, squareSize / 10));
			graphics.drawString("Mana :", xFromI(j) + squareSize / 14, yFromJ(i) + squareSize / 3);

			if (c instanceof Building) {
				Building card = (Building) c;
				graphics.drawString("Force :", xFromI(j) + squareSize / 14, yFromJ(i) + (int) (squareSize / 1.1));
				graphics.drawString(Integer.toString(card.getStrength()), xFromI(j) + (int) (squareSize / 1.3),
						yFromJ(i) + (int) (squareSize / 1.1));
				if (card.isMoving()) {
					graphics.drawString("Mouvement :", xFromI(j) + squareSize / 14,
							yFromJ(i) + (int) (squareSize / 1.4));
					graphics.drawString(Integer.toString(card.getSpeed()), xFromI(j) + (int) (squareSize / 1.3),
							yFromJ(i) + (int) (squareSize / 1.4));
				}
			} else if (c instanceof Spell) {
				Spell card = (Spell) c;
				graphics.drawString("Force :", xFromI(j) + squareSize / 14, yFromJ(i) + (int) (squareSize / 1.1));
				graphics.drawString(Integer.toString(card.getValue()), xFromI(j) + (int) (squareSize / 1.3),
						yFromJ(i) + (int) (squareSize / 1.1));
			}
			i = ((i + 1) % 6);
			if (i == 0) {
				j = (j + 1) % 12;
			}

		}

	}

	/**
	 * Draws the game board from its data, using an existing
	 * {@code ApplicationContext}.
	 * 
	 * @param context
	 *            the {@code ApplicationContext} of the game
	 * @param data
	 *            the GameData containing the game data.
	 * @param view
	 *            the GameView on which to draw.
	 */
	public static void draw(ApplicationContext context, SimpleGameData data, SimpleGameView view, Card card,
			Player us) {
		context.renderFrame(graphics -> view.draw(graphics, data, card, us)); // do not modify
	}

	public static void drawMenu(ApplicationContext context, SimpleGameView view) {
		context.renderFrame(graphics -> view.drawMenu(graphics));
	}

	public static void reset(ApplicationContext context, SimpleGameView view, float wid, float hei) {
		context.renderFrame(graphics -> view.reset(graphics, wid, hei));
	}

	public static void drawBase(ApplicationContext context, SimpleGameView view, Player player) {
		context.renderFrame(graphics -> view.drawBase(graphics, player));
	}

	public static void drawFront(ApplicationContext context, SimpleGameView view, Player player) {
		context.renderFrame(graphics -> view.drawFront(graphics, player));
	}

	public static void drawMana(ApplicationContext context, SimpleGameView view, Player player) {
		context.renderFrame(graphics -> view.drawMana(graphics, player));
	}

	public static void drawMain(ApplicationContext context, SimpleGameView view, Player player, SimpleGameData data) {
		context.renderFrame(graphics -> view.drawMain(graphics, player, data));
	}

	public static void drawCurrentPlayer(ApplicationContext context, SimpleGameView view, Player player) {
		context.renderFrame(graphics -> view.drawCurrentPlayer(graphics, player));
	}

	public static void drawFinish(ApplicationContext context, SimpleGameView view, Player player) {
		context.renderFrame(graphics -> view.drawFinish(graphics, player));
	}

	public static void cards(ApplicationContext context, SimpleGameView view, OwnedCards o, Set<Card> s, int choice) {
		context.renderFrame(graphics -> view.cards(graphics, o, s, choice));
	}
	
	public static void selectionDeck(ApplicationContext context, SimpleGameView view,int choice) {
		context.renderFrame(graphics -> view.selectionDeck(graphics,choice));
	}
	
	public static void canNotPlay(ApplicationContext context, SimpleGameView view) {
		context.renderFrame(graphics -> view.canNotPlay(graphics));
	}
	
	public static void removeSentence(ApplicationContext context, SimpleGameView view) {
		context.renderFrame(graphics -> view.removeSentence(graphics));
	}
	
	public static void winningCard(ApplicationContext context, SimpleGameView view, Card c) {
		context.renderFrame(graphics -> view.winningCard(graphics, c));
	}

}
