package project.ilyagorban.controllers;

import project.ilyagorban.model.ChessModel;
import project.ilyagorban.model.Owner;
import project.ilyagorban.view.Visualizable;
import static project.ilyagorban.model.ChessModel.*;

public class ChessController {
	private static boolean WHITE = true;
	private static boolean BLACK = false;

	private final ChessModel cm;
	private final Visualizable cv;

	public ChessController(ChessModel cm, Visualizable cv) {
		this.cm = cm;
		this.cv = cv;
	}

	public void start() {
		boolean isGameStarted = cm.initializeGame();
		if (isGameStarted == false) {
			System.out.println("the game is not started");
			return;
		}
		boolean currentOwner = WHITE;
		while (true) {
			String input = cv.showBoard(cm.getBoard(), currentOwner);

			if ("exit".equals(input))
				break; // end of game

			int returnMessage = cm.move(input, currentOwner);
			if (returnMessage > GAME_ENDINGS) {
				if (returnMessage > DRAW) {
					cv.getMessageToView("There is possibility for draw!!!!");
					break; // end of game
				} else if (returnMessage == CHECKMATE_TO_WHITE) {
					cv.getMessageToView("Black wins!!!!!");
					break; // end of game
				} else if (returnMessage == CHECKMATE_TO_BLACK) {
					cv.getMessageToView("White wins!!!!!");
					break; // end of game
				}
			} else if (returnMessage >= CORRECT_MOVE) {
				switch (returnMessage) {
				case EN_PASSANT:
					cv.getMessageToView("Made en-passant");
					break;
				case CHECK_TO_AWAITING_SIDE:
					if (currentOwner == Owner.BLACK) {
						cv.getMessageToView("White make check");
					} else {
						cv.getMessageToView("Black make check");
					}
					break;
				case PAWN_PROMOTION:
					boolean success = false;
					while (success == false) {
						String promotion = cv
								.getInput("Your pawn is ready to be promoted. To which figure you want to promote it (r)ook/k(n)ight/(b)ishop/(q)ueen?");
						success = cm.promotePawn(input, promotion);
					}
					break;
				}

				cm.saveMove();
				currentOwner = currentOwner.oppositeOwner();
			} else {
				switch (returnMessage) {
				case CHECK_TO_CURRENT_SIDE:
					cv.getMessageToView("Incorrect move - you are under check");
					break;
				case INCORRECT_INPUT:
					cv.setMessage("incorrect input string");
					break;
				case DONT_TOUCH_NOT_YOUR_FIGURE_TO_MOVE:
					cv.setMessage("there is no " + currentOwner.name()
							+ "'s figure on the start coordinate");
					break;
				case INCORRECT_MOVE:
					cv.setMessage("incorrect move for this figure");
					break;
				case OBSTACLE_ON_WAY:
					cv.setMessage("there is an unpassable obstacle on the end point of your move");
					break;
				}

			}
		}
	}
}
