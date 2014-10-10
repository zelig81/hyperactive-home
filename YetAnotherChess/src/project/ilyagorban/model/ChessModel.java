package project.ilyagorban.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import project.ilyagorban.model.figures.*;

public class ChessModel {
	public static final int CHECK = -7;
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
	private HashMap<Integer, ArrayList<Object>> savedState = new HashMap<>(3);
	private int rule50Draw = 0;
	private HashSet<String> rule3FoldSet = null;
	private ArrayList<String> movesLog = null;
	private int rule3FoldCount = 0;
	private int Rule3FoldPreviousLen = 0;

	public int assessPositions(int returnMessage) {
		// TODO assessPositions
		return returnMessage;
	}

	public int check(boolean ownerUnderCheck) {
		for (Figure fig : hmFigures.get(!ownerUnderCheck)) {
			int result = fig.checkIllegalMove(board, kings.get(ownerUnderCheck)
					.getXY(), lastMoved, true);
			if (result == CORRECT_MOVE) {
				return CHECK;
			}
		}

		return 0;
	}

	public int check(int[] moves, int afterTryingToMove, boolean currentOwner) {
		int output = check(currentOwner);
		if (output >= CORRECT_MOVE && afterTryingToMove == CASTLING) {
			move(moves[1], moves[0]);
			output = check(currentOwner);
			if (output >= CORRECT_MOVE) {
				move(moves[0], (moves[0] + moves[1]) / 2);
				output = check(currentOwner);
			}
			move(kings.get(currentOwner).getXY(), moves[1]);
		}
		return output;
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

	public void makeCastling(int[] moves) {
		int yFrom = XY.getY(moves[0]);
		boolean rightCastling = XY.getDifferenceXY(moves[0], moves[1])[0] > 0;
		int xFrom = rightCastling == true ? 7 : 0;
		int dx = rightCastling == true ? -2 : 3;
		int rookXY = XY.getIndexFromXY(xFrom, yFrom);
		int rookXYFrom = XY.addToIndex(rookXY, dx, 0);
		int rookXYTo = XY.addToIndex(rookXY, dx, 0);
		move(moves[0], moves[1]);
		move(rookXYFrom, rookXYTo);

		rule50Draw++;

	}

	public void makeCorrectMove(int[] moves) {
		// possible move + possible take
		Figure figTo = board[moves[1]];
		if (board[moves[0]] instanceof Pawn)
			rule50Draw = 0;
		if (figTo != null) {
			hmFigures.get(figTo.getRank().getOwner()).remove(figTo);
			rule50Draw = 0;
		}
		move(moves[0], moves[1]);

	}

	public void makeEnpassant(int[] moves) {
		int[] difXY = XY.getDifferenceXY(moves[0], moves[1]);
		Figure figEP = board[XY.addToIndex(moves[0], difXY[0], 0)];
		hmFigures.get(figEP.getRank().getOwner()).remove(figEP);
		rule50Draw = 0;
		move(moves[0], moves[1]);

	}

	private void move(int from, int to) {
		Figure fig = board[from];
		fig.setXY(to);
		board[to] = board[from];
		board[from] = null;

	}

	public boolean promotePawn(int[] moves, String promotion) {
		Figure pawn = board[moves[1]];
		Rank gotRank = Rank.getRank(promotion, pawn.getRank().getOwner());
		if (gotRank == null)
			return false;
		else {
			pawn.setRank(gotRank);
			return true;
		}
	}

	public void restoreStateBeforeMove(int afterTryingToMove) {
		ArrayList<Object> list = savedState.get(afterTryingToMove);
		rule50Draw = (Integer) list.get(0);
		int[] moves;
		switch (afterTryingToMove) {
		case CASTLING:
			moves = (int[]) list.get(1);
			move(moves[1], moves[0]);
			moves = (int[]) list.get(2);
			move(moves[1], moves[0]);
			break;
		case EN_PASSANT:
			moves = (int[]) list.get(1);
			move(moves[1], moves[0]);
			Figure removedPawn = (Figure) list.get(2);
			board[removedPawn.getXY()] = removedPawn;
			break;
		case CORRECT_MOVE:
			moves = (int[]) list.get(1);
			move(moves[1], moves[0]);
			Figure removedFigure = (Figure) list.get(2);
			if (removedFigure != null)
				board[removedFigure.getXY()] = removedFigure;
			break;
		}

	}

	public void saveMove(int from, int to) {
		// add to log;
		if (movesLog == null)
			movesLog = new ArrayList<>();
		String madeMove = board[to].toLog() + XY.xyToString(from)
				+ XY.xyToString(to);
		movesLog.add(madeMove);

		// add to 3 fold set check
		if (rule3FoldSet == null)
			rule3FoldSet = new HashSet<>();
		rule3FoldSet.add(madeMove);
		int len = rule3FoldSet.size();
		if (Rule3FoldPreviousLen == len)
			rule3FoldCount++;

		// save lastmoved + its state
	}

	public void saveStateBeforeMove(int result, int[] moves) {
		ArrayList<Object> list = new ArrayList<>(3);
		list.add(rule50Draw);
		switch (result) {
		case CASTLING:
			int yFrom = XY.getY(moves[0]);
			boolean rightCastling = XY.getDifferenceXY(moves[0], moves[1])[0] > 0;
			int xFrom = rightCastling == true ? 7 : 0;
			int dx = rightCastling == true ? -2 : 3;
			int rookXY = XY.getIndexFromXY(xFrom, yFrom);
			int[] rookXYmoves = new int[] { XY.addToIndex(rookXY, dx, 0),
					XY.addToIndex(rookXY, dx, 0) };
			list.add(moves);
			list.add(rookXYmoves);
			savedState.put(CASTLING, list);
			break;
		case EN_PASSANT:
			int[] difXY = XY.getDifferenceXY(moves[0], moves[1]);
			list.add(moves);
			list.add(board[XY.addToIndex(moves[0], difXY[0], 0)]);
			savedState.put(EN_PASSANT, list);
			break;
		case CORRECT_MOVE:
			Figure figTo = board[moves[1]];
			list.add(moves);
			list.add(figTo);
			savedState.put(CORRECT_MOVE, list);
			break;
		default:
		}

	}

	public int tryToMove(int[] moves, boolean currentOwner) {
		int from = moves[0];
		int to = moves[1];
		Figure figFrom = board[from];

		if (figFrom != null && figFrom.isEnemy(currentOwner) == false) {
			int checkMove = figFrom.checkIllegalMove(board, to, lastMoved,
					isLastMovedWasNotTouchedBefore);

			if (checkMove >= CORRECT_MOVE) {
				saveStateBeforeMove(checkMove, moves);
				switch (checkMove) {
				case PAWN_PROMOTION:
				case CORRECT_MOVE:
					makeCorrectMove(moves);
					break;
				case CASTLING:
					makeCastling(moves);
					break;
				case EN_PASSANT:
					makeEnpassant(moves);
					break;
				}

			}
			return checkMove;

		} else {
			return DONT_TOUCH_NOT_YOUR_FIGURE_TO_MOVE;
		}

	}
}
