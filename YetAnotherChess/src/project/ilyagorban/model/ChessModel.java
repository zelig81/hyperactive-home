package project.ilyagorban.model;

import static project.ilyagorban.model.ChessModel.BLACK;
import static project.ilyagorban.model.ChessModel.CASTLING;
import static project.ilyagorban.model.ChessModel.CHECK_TO_AWAITING_SIDE;
import static project.ilyagorban.model.ChessModel.CORRECT_MOVE;
import static project.ilyagorban.model.ChessModel.EN_PASSANT;

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
	private Figure lastMoved = null;
	private boolean isLastMovedWasNotTouchedBefore = false;
	private HashMap<Integer, Object> savedState = new HashMap<>(3);
	private int draw50Rule = 0;

	public int assessPositions(int returnMessage) {
		return returnMessage;
	}

	public int check(int afterTryingToMove) {
		// TODO Auto-generated method stub
		return 0;
	}

	public Figure[] getBoard() {
		return board;
	}

	public boolean initializeGame() {
		hmFigures.put(WHITE, new ArrayList<Figure>());
		hmFigures.put(BLACK, new ArrayList<Figure>());
		boolean result = Board.initializeGame(board, hmFigures, kings);
		if (result == false)
			return false;
		return true;
	}

	public void makeCastling(String input) {
		int[] moves = XY.getIndicesfromInput(input);
		int yFrom = XY.getY(moves[0]);
		boolean rightCastling = XY.getDifferenceXY(moves[0], moves[1])[0] > 0;
		int xFrom = rightCastling == true ? 7 : 0;
		int dx = rightCastling == true ? -2 : 3;
		int rookXY = XY.getIndexFromXY(xFrom, yFrom);
		int[] rookXYmoves = new int[] { XY.addToIndex(rookXY, dx, 0),
				XY.addToIndex(rookXY, dx, 0) };
		move(moves);
		move(rookXYmoves);

		draw50Rule++;

	}

	public void makeCorrectMove(String input) {
		// possible move + possible take
		int[] moves = XY.getIndicesfromInput(input);
		Figure figTo = board[moves[1]];
		if (figTo != null) {
			hmFigures.get(figTo.getRank().getOwner()).remove(figTo);
			draw50Rule = 0;
		}
		move(moves);

	}

	public void makeEnpassant(String input) {
		int[] moves = XY.getIndicesfromInput(input);
		int[] difXY = XY.getDifferenceXY(moves[0], moves[1]);
		Figure figEP = board[XY.addToIndex(moves[0], difXY[0], 0)];
		hmFigures.get(figEP.getRank().getOwner()).remove(figEP);

		move(moves);

	}

	private void move(int[] moves) {
		board[moves[1]] = board[moves[0]];
		board[moves[0]] = null;
		Figure fig = board[moves[1]];
		fig.setXY(moves[1]);
	}

	public boolean promotePawn(String input, String promotion) {
		int[] moves = XY.getIndicesfromInput(input);
		Figure pawn = board[moves[1]];
		Rank gotRank = Rank.getRank(promotion, pawn.getRank().getOwner());
		if (gotRank == null)
			return false;
		else {
			pawn.setRank(gotRank);
			return true;
		}
	}

	public void saveMove() {
		// board.saveMove();
		// add to 3 fold set check
	}

	public void saveStateBeforeMove(int result, String input) {
		int[] move = XY.getIndicesfromInput(input);
		ArrayList<Object> list = new ArrayList<>(3);
		list.add(new int[] { draw50Rule, });
		switch (result) {
		case CASTLING:
			list.add(move);
			int yFrom = XY.getY(move[0]);
			boolean rightCastling = XY.getDifferenceXY(move[0], move[1])[0] > 0;
			int xFrom = rightCastling == true ? 7 : 0;
			int dx = rightCastling == true ? -2 : 3;
			int rookXY = XY.getIndexFromXY(xFrom, yFrom);
			int[] rookXYmoves = new int[] { XY.addToIndex(rookXY, dx, 0),
					XY.addToIndex(rookXY, dx, 0) };
			list.add(rookXYmoves);
			savedState.put(CASTLING, list);
			break;
		case EN_PASSANT:
			list.add(move);
			int[] difXY = XY.getDifferenceXY(move[0], move[1]);
			list.add(board[XY.addToIndex(move[0], difXY[0], 0)]);
			savedState.put(EN_PASSANT, list);
			break;
		case CORRECT_MOVE:
			Figure figTo = board[move[1]];
			list.add(move);
			list.add(figTo);
			savedState.put(CORRECT_MOVE, list);
			break;
		default:
		}

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
			int checkMove = figFrom.checkIllegalMove(board, to, lastMoved,
					isLastMovedWasNotTouchedBefore);

			if (checkMove >= CORRECT_MOVE) {
				saveStateBeforeMove(checkMove, from, to);
				switch (checkMove) {
				case PAWN_PROMOTION:
				case CORRECT_MOVE:
					makeCorrectMove(input);
					break;
				case CASTLING:
					makeCastling(input);
					break;
				case EN_PASSANT:
					makeEnpassant(input);
					break;
				}

			}
			return checkMove;

		} else {
			return DONT_TOUCH_NOT_YOUR_FIGURE_TO_MOVE;
		}

	}
}
