package fr.dut.info.stormbound.mvc;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.io.IOException;

import fr.dut.info.stormbound.board.Builders;
import fr.dut.info.stormbound.board.CardCollection;
import fr.dut.info.stormbound.board.OwnedCards;
import fr.dut.info.stormbound.board.Player;
import fr.dut.info.stormbound.cards.Card;
import fr.dut.info.stormbound.cards.Deck;
import fr.dut.info.stormbound.cards.SpecialCard;
import fr.dut.info.stormbound.cards.Spell;
import fr.dut.info.stormbound.cards.enumeration.Frequency;
import fr.umlv.zen5.Application;
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.ScreenInfo;
import fr.umlv.zen5.Event.Action;
import fr.umlv.zen5.KeyboardKey;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SimpleGameController {

	private static void menu(SimpleGameView view, ApplicationContext context, SimpleGameData data, float width,
			float height) {
		SimpleGameView.drawMenu(context, view);
		Point2D.Float location;
		while (true) {
			Event event = context.pollOrWaitEvent(10);
			if (event == null) { // no event
				continue;
			}

			Action action = event.getAction();
			if (action == Action.KEY_PRESSED || action == Action.KEY_RELEASED) {
				continue;
			}

			if (action != Action.POINTER_DOWN) {
				continue;
			}

			location = event.getLocation();
			int i = view.lineFromY(location.y);
			int j = view.columnFromX(location.x);
			if (i == 1 && j == -4) { // To play
				SimpleGameView.reset(context, view, width, height);
				// Playing display
				playing(view, context, data, width, height);
				return;
			}

			else if (i == 4 && j == 7) { // To quit
				context.exit(0);
				return;
			}

			else if (i == 3 && j == -4) { // To create a deck
				SimpleGameView.reset(context, view, width, height);
				OwnedCards o = new OwnedCards();
				try {
					o = OwnedCards.load(); // Loading of the cards owned by the player
					Deck d = new Deck();
					int choice = 0; // deck's choice
					Set<Card> newdeck = new HashSet<>();
					data.initOwnedCard(o.getOwnedCard());
					SimpleGameView.cards(context, view, o, newdeck, choice);
					while (true) {
						Event event2 = context.pollOrWaitEvent(10);
						if (event2 == null) { // no event
							continue;
						}

						Action action2 = event2.getAction();
						if (action2 == Action.KEY_PRESSED || action2 == Action.KEY_RELEASED) {
							continue;
						}

						if (action2 != Action.POINTER_DOWN) {
							continue;
						}

						location = event2.getLocation();
						int m = view.findCoord(location.y);
						int n = view.findCoord(location.x);
						if (m > 0) { // add the selected card at the new deck
							data.addCard(m, n, newdeck);
						} else if (n > 2 && n < 8) { // the player choice the deck
							choice = n - 2;
						}

						else if (m == 0 && n == 11) { // return to the menu
							break;
						}

						else if (m == 0 && n == 10 && newdeck.size() == 12 && choice > 0 && choice < 6) { // Saving deck
							for (Card c : newdeck) {
								d.add(c);
							}
							SimpleGameView.reset(context, view, width, height);
							Deck.saveDeck(choice, d);
							break;
						}
						SimpleGameView.cards(context, view, o, newdeck, choice);

					}
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				SimpleGameView.reset(context, view, width, height);
				SimpleGameView.drawMenu(context, view);
			}

		}

	}

	static void playing(SimpleGameView view, ApplicationContext context, SimpleGameData data, float width,
			float height) {
		// Initialization
		data.InitMatrix();
		int choice = 0;
		Deck deck1 = new Deck();
		Deck deck2 = new Deck();
		Player Us = new Player(1, 4);
		Player Ordi = new Player(2, 1);
		Player currentPlayer;
		Player ennemyPlayer;
		Player temp;
		boolean endTour;
		boolean changeCard;
		Card selectedCard = null;
		Point2D.Float location;

		SimpleGameView.selectionDeck(context, view, choice); // the player selects his deck
		while (true) {
			Event event2 = context.pollOrWaitEvent(10);
			if (event2 == null) { // no event
				continue;
			}

			Action action2 = event2.getAction();
			if (action2 == Action.KEY_PRESSED || action2 == Action.KEY_RELEASED) {
				KeyboardKey key = event2.getKey();
				if (key.equals(KeyboardKey.Q)) {
					context.exit(0);
				}
				continue;
			}

			if (action2 != Action.POINTER_DOWN) {
				continue;
			}

			location = event2.getLocation();
			int m = view.lineFromY(location.y);
			int n = view.columnFromX(location.x);

			if (m == 4 && n == 5 && choice != 0) { // the AI has the same deck as the player
				try {
					deck1 = Deck.loadDeck(choice);
					deck2 = Deck.loadDeck(choice);
					Us.setDeck(deck1);
					Ordi.setDeck(deck2);
					Us.generateHand();
					Ordi.generateHand();
					break;
				} catch (Exception e) {
					SimpleGameView.canNotPlay(context, view);
				}

			} else if (m == 2 && n > -1 && n < 5) { // the gale start
				SimpleGameView.removeSentence(context, view);
				choice = n + 1;
			}
			SimpleGameView.selectionDeck(context, view, choice);

		}
		SimpleGameView.reset(context, view, width, height);

		// Game Display
		SimpleGameView.draw(context, data, view, null, Us);
		SimpleGameView.drawBase(context, view, Us);
		SimpleGameView.drawBase(context, view, Ordi);
		SimpleGameView.drawFront(context, view, Us);
		SimpleGameView.drawFront(context, view, Ordi);
		SimpleGameView.drawMana(context, view, Us);
		SimpleGameView.drawMain(context, view, Us, data);

		currentPlayer = Ordi;
		ennemyPlayer = Us;
		Ordi.newRoundMana();

		while (true) {

			endTour = false;
			changeCard = false;

			// Exchange of the current player
			temp = currentPlayer;
			currentPlayer = ennemyPlayer;
			ennemyPlayer = temp;

			SimpleGameView.drawCurrentPlayer(context, view, currentPlayer);

			// Advance the unit of the current Player at the start of the turn
			data.startMove(currentPlayer, ennemyPlayer);

			// Change the display
			SimpleGameView.drawBase(context, view, ennemyPlayer);
			SimpleGameView.draw(context, data, view, null, Us);
			SimpleGameView.drawFront(context, view, Us);
			SimpleGameView.drawFront(context, view, Ordi);

			// if the current player has win
			if (ennemyPlayer.getBase() == 0) {
				SimpleGameView.drawFinish(context, view, currentPlayer);
				if (currentPlayer.getNum() == 1) {
					try {
						CardCollection c = new CardCollection();
						OwnedCards o = new OwnedCards();
						o = OwnedCards.load();
						c = CardCollection.loadCollection();
						Card card = c.lootRandomCard();
						System.out.println("carte gagne :" + card);
						SimpleGameView.winningCard(context, view, card);
						o.add(card);
						OwnedCards.save(o);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}

				}
				return;
			}

			if (currentPlayer.getNum() == Ordi.getNum()) { // IA's turn
				System.out.println("changement de tour");
				while (bootCanPlay(Ordi)) {
					data.bootPlaying(Ordi, Us);
					SimpleGameView.draw(context, data, view, selectedCard, Us);
					SimpleGameView.drawFront(context, view, Ordi);
					SimpleGameView.drawFront(context, view, Us);
					SimpleGameView.drawBase(context, view, Us);
					if (Us.getBase() == 0) { // If the IA has win
						SimpleGameView.drawFinish(context, view, Ordi);
						return;
					}

					sleep(500);
				}
				Ordi.newRoundMana();
				Ordi.fillHand();
				sleep(500);
			}

			else { // Player's turn
				while (!endTour) {

					Event event = context.pollOrWaitEvent(10);
					if (event == null) { // no event
						continue;
					}

					// Si on appuie sur une touche
					Action action = event.getAction();
					if (action == Action.KEY_PRESSED || action == Action.KEY_RELEASED) {
						KeyboardKey key = event.getKey();
						// if Q is pressed, the game closes
						if (key.equals(KeyboardKey.Q)) {
							context.exit(0);
							return;
						}
						continue;
					}

					if (action != Action.POINTER_DOWN) {
						continue;
					}

					location = event.getLocation();
					int i = view.lineFromY(location.y);
					int j = view.columnFromX(location.x);
					data.unselect();

					if (data.isInCard(i, j)) {// Select a card
						selectedCard = data.selectCard(i, Us);
						data.selectCell(i, j);
					}

					else if (i == 4 && j == -2 && selectedCard != null && changeCard == false) { // Exchange a card of
																									// the hand by a
																									// card of the deck

						data.echangeCard(selectedCard, Us);
						selectedCard = null;
						changeCard = true; // once by round
					}

					else if (data.isInGame(i, j) && selectedCard != null) { // if a card if selected

						if (currentPlayer.getMana() >= selectedCard.getManaCost()) { // if the player has enough mana

							if (selectedCard instanceof Spell) {
								if (data.useSpell(selectedCard, Us, Ordi, i, j)) {
									currentPlayer.updateMana(selectedCard.getManaCost());
									SimpleGameView.drawMana(context, view, Us);
									data.deleteCard(selectedCard, Us);
									selectedCard = null;
								}
							}

							else if (data.getCellValue(i, j) == 0 && i >= Us.getFrontLine()) {
								data.updateData(i, j, selectedCard.getStrength(), Us.getNum(), selectedCard);
								if (selectedCard.hasPower()) { // if the card has a bonus
									SpecialCard c = (SpecialCard) selectedCard;
									if (c.getBonus().getFrequency().equals(Frequency.SUMMON)) {
										data.useBonus(selectedCard, Us, Ordi);
									}
								}

								if (selectedCard.isMoving()) {
									data.move(i, j, selectedCard, Us, Ordi);
								}

								currentPlayer.updateMana(selectedCard.getManaCost()); // when the card is played, the
																						// player's mana decreases
								SimpleGameView.drawMana(context, view, Us);
								data.deleteCard(selectedCard, Us);
								selectedCard = null;
							}
						}
					}

					// Button "fin de tour"
					else if (data.isEndRound(i, j)) {
						endTour = true;
						selectedCard = null;
						Us.newRoundMana();
						Us.fillHand();
						SimpleGameView.drawMana(context, view, Us);
					}

					else {
						selectedCard = null;
					}

					// Change of the display
					SimpleGameView.drawBase(context, view, Us);
					SimpleGameView.drawBase(context, view, Ordi);
					SimpleGameView.draw(context, data, view, selectedCard, Us);
					SimpleGameView.drawFront(context, view, Us);
					SimpleGameView.drawFront(context, view, Ordi);
					SimpleGameView.drawMain(context, view, Us, data);
					if (Ordi.getBase() == 0) { // if the player has win
						SimpleGameView.drawFinish(context, view, Us);
						try {
							CardCollection c = new CardCollection();
							OwnedCards o = new OwnedCards();
							o = OwnedCards.load();
							c = CardCollection.loadCollection();
							Card card = c.lootRandomCard();
							System.out.println("carte gagne :" + card);
							SimpleGameView.winningCard(context, view, card);
							o.add(card);
							OwnedCards.save(o);
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
						return;
					}

				}
			}
		}
	}

	static void simpleGame(ApplicationContext context) {
		// Initialization
		ScreenInfo screenInfo = context.getScreenInfo();
		float width = screenInfo.getWidth();
		float height = screenInfo.getHeight();
		SimpleGameData data = new SimpleGameData(5, 4);
		SimpleGameView view = SimpleGameView.initGameGraphics((int) (width / 3), (int) (height / 7),
				(int) (width / 2.5), data);

		// Menu Display
		menu(view, context, data, width, height);

		while (true) {
			// if one has win
			Event event = context.pollOrWaitEvent(10);
			if (event == null) { // no event
				continue;
			}

			Action action = event.getAction();
			if (action == Action.KEY_PRESSED || action == Action.KEY_RELEASED) {
				KeyboardKey key = event.getKey();
				if (key.equals(KeyboardKey.Q)) { // The player return to the menu
					SimpleGameView.reset(context, view, width, height);
					menu(view, context, data, width, height);

				}
				continue;
			}

			if (action != Action.POINTER_DOWN) {
				continue;
			}
		}

	}

	/**
	 * 
	 * @param player
	 * 
	 * @return true if the AI can play a card false otherwise
	 */
	public static boolean bootCanPlay(Player player) {
		ArrayList<Card> main = player.getHand();
		for (int i = 0; i < main.size(); i++) {
			if (main.get(i) != null) {
				if (player.getMana() >= main.get(i).getManaCost()) {
					return true;
				}
			}
		}
		return false;
	}

	public static void sleep(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	public static void main(String[] args) throws ClassNotFoundException, IOException {
		if (!Builders.checkFileExist()) {
			Builders.createTheGame();
		}
		Application.run(Color.WHITE, SimpleGameController::simpleGame);
	}
}
