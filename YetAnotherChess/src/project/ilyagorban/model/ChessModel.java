package project.ilyagorban.model;

import static project.ilyagorban.model.ChessModel.BLACK;
import static project.ilyagorban.model.ChessModel.WHITE;

import java.util.ArrayList;
import java.util.HashMap;

import project.ilyagorban.model.figures.Figure;

public class ChessModel {
	public static final int CHECK_TO_CURRENT_SIDE = -7;
	public static final int OBSTACLE_ON_THE_WAY = -6;
	public static final int DONT_TOUCH_NOT_YOUR_FIGURE_TO_MOVE = -5;
	public static final int INCORRECT_MOVE = -2;
	public static final int INCORRECT_INPUT = -1;
	public static final int CORRECT_MOVE = 0;
	public static final int PAWN_PROMOTION = 1;
	public static final int CASTLING = 2;
	public static final int EN_PASSANT = 3;
	public static final int CHECK_TO_AWAITING_SIDE = 4;
	public static final int GAME_ENDINGS = 100;
	public static final int CHECKMATE_TO_WHITE = 101;
	public static final int CHECKMATE_TO_BLACK = 102;
	public static final int DRAW = 200;
	public static final int DRAW_50_RULE = 203;
	public static final int DRAW_3_FOLD_REPETITION = 204;
	public static final int DRAW_IMPOSSIBILITY_OF_MATE = 205;
	public static final int DRAW_STALEMATE = 206;

	public static boolean WHITE = true;
	public static boolean BLACK = false;
	public static HashMap<Boolean, String> mColors = new HashMap<>(2);
	static {
		mColors.put(WHITE, "Whites");
		mColors.put(BLACK, "Blacks");
	}

	private final Figure[] board = new Figure[64];
	private final HashMap<Boolean, ArrayList<Figure>> hmFigures = new HashMap<>();
	private final HashMap<Boolean, Figure> kings = new HashMap<>();

	public boolean initializeGame() {
		hmFigures.put(WHITE, new ArrayList<Figure>());
		hmFigures.put(BLACK, new ArrayList<Figure>());
		boolean result = Board.initializeGame(board, hmFigures, kings);
		if (result == false)
			return false;
		return true;
	}

	public Figure[] getBoard() {
		return board;
	}

	public int tryToMove(String input, boolean currentOwner) {
		int[] arrIndices = XY.getIndicesfromInput(input);
		if (arrIndices == null) {
			return INCORRECT_INPUT;
		}
		int from = arrIndices[0];
		int to = arrIndices[1];
		Figure figFrom = board[from];

		if (figFrom != null && figFrom.isEnemy(currentOwner) == false) {
			int checkMove = figFrom.checkIllegalMove(board, to);

			if (checkMove >= CORRECT_MOVE) {
				// board.savePossibleLastMovedFigure(figFrom, from, to);
				if (figFrom.getRank().getIndex().equals("p")) { // pawn move
					// board.resetNumberOfFiftyRule();
				}
				if (checkMove == CORRECT_MOVE) {
					Figure figTo = board[to];
					if (figTo != null) {
						// board.remove(to);
						// board.resetNumberOfFiftyRule();
					}
					// board.move(figFrom, to);
					// board.savePossibleMove(from, to);
				} else if (checkMove == CASTLING) {
					// board.castling(figFrom, to);
				} else if (checkMove == EN_PASSANT) {
					// board.enPassant(figFrom);
				}
			}
			return checkMove;

		} else {
			return DONT_TOUCH_NOT_YOUR_FIGURE_TO_MOVE;
		}

	}

	public boolean promotePawn(String input, String promotion) {
		int to = XY.getIndexFromXY(input);
		Figure pawn = board[to];
		Rank gotRank = Rank.getRank(promotion, pawn.getRank().getOwner());
		if (gotRank == null)
			return false;
		else
			return true;// board.promotePawn(pawn, gotRank, to);
	}

	public void saveMove() {
		// board.saveMove();

	}
}
