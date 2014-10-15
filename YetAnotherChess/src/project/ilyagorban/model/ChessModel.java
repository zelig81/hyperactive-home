package project.ilyagorban.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import project.ilyagorban.model.figures.*;

public class ChessModel {
	public static final int							CHECK								= -7;
	public static final int							OBSTACLE_ON_THE_WAY					= -6;
	public static final int							DONT_TOUCH_NOT_YOUR_FIGURE_TO_MOVE	= -5;
	public static final int							INCORRECT_MOVE						= -2;
	public static final int							INCORRECT_INPUT						= -1;
	public static final int							CORRECT_MOVE						= 0;
	public static final int							PAWN_PROMOTION						= 1;
	public static final int							CASTLING							= 2;
	public static final int							EN_PASSANT							= 3;
	public static final int							GAME_ENDINGS						= 100;
	public static final int							CHECKMATE_TO_WHITE					= 101;
	public static final int							CHECKMATE_TO_BLACK					= 102;
	public static final int							DRAW								= 200;
	public static final int							DRAW_50_RULE						= 203;
	public static final int							DRAW_3_FOLD_REPETITION				= 204;
	public static final int							DRAW_IMPOSSIBILITY_OF_MATE			= 205;
	public static final int							DRAW_STALEMATE						= 206;

	public static boolean							WHITE								= true;
	public static boolean							BLACK								= false;
	public static HashMap<Boolean, String>			mColors								= new HashMap<>(2);
	static {
		mColors.put(WHITE, "Whites");
		mColors.put(BLACK, "Blacks");
	}

	private final Figure[]							board								= new Figure[64];
	private final HashMap<Boolean, HashSet<Figure>>	hmFigures							= new HashMap<>();
	private final HashMap<Boolean, Figure>			kings								= new HashMap<>();
	private Figure									lastMoved							= null;
	private int										lastFrom							= 0;
	private HashMap<Integer, ArrayList<Object>>		savedState							= new HashMap<>(3);
	private int										rule50Draw							= 0;
	private int										movesCount							= 0;
	private final static int[][]					pool								= new int[][] { { 0, 1 },
			{ 1, 1 }, { 1, 0 }, { -1, 1 }, { -1, 0 }, { -1, -1 }, { 0, -1 }, { 1, -1 }	};
	private HashSet<HashSet<String>>				rule3FoldSet						= null;
	private ArrayList<String>						movesLog							= null;

	public int assessPositions(int returnMessage, boolean currentOwner) {
		int oppositeCheck = check(!currentOwner);
		if (oppositeCheck != CHECK) {
			if (rule50Draw >= 50)
				returnMessage = DRAW_50_RULE;
			if (movesCount - rule3FoldSet.size() >= 3)
				returnMessage = DRAW_3_FOLD_REPETITION;
			int wCount = hmFigures.get(WHITE).size();
			int bCount = hmFigures.get(BLACK).size();
			if (wCount * bCount == 1) {
				returnMessage = DRAW_IMPOSSIBILITY_OF_MATE;
			} else if (wCount * bCount == 2) {
				boolean side = wCount == 2 ? WHITE : BLACK;
				for (Figure fig : hmFigures.get(side)) {
					int importance = fig.getRank().getImportance();
					if (importance == 100)
						continue;
					if (importance == 1)
						returnMessage = DRAW_IMPOSSIBILITY_OF_MATE;

				}
			} else {
				// TODO unsufficient material for mate k+b/k+b on same color
				int oddnessOfBishop = -1;
				boolean correctness = true;
				for (int i = 0; i < 2; i++) {
					boolean side = i % 2 == 0 ? WHITE : BLACK;
					for (Figure fig : hmFigures.get(side)) {
						if (fig.equals(kings.get(side)) == true)
							continue;
						if (fig instanceof Bishop) {
							if (oddnessOfBishop == -1) {
								oddnessOfBishop = fig.getXY() % 2;
								continue;
							} else {
								if (oddnessOfBishop == fig.getXY() % 2)
									continue;
							}
						}
						correctness = false;
						break;
					}
					if (correctness == false) {
						break;
					}
				}

			}

		}
		if (returnMessage >= CORRECT_MOVE) {
			Figure oppositeKing = kings.get(!currentOwner);
			Figure ownKing = kings.get(currentOwner);
			int xyOppKing = oppositeKing.getXY();
			int xyOwnKing = ownKing.getXY();
			int[] difXY = XY.getDifferenceXY(xyOppKing, xyOwnKing);
			if (Math.abs(difXY[0]) == 1 && Math.abs(difXY[1]) == 1 || Math.abs(difXY[0]) + Math.abs(difXY[1]) == 1)
				return INCORRECT_MOVE;
			for (int[] coord : pool) {
				int illegalMoveResult =
						oppositeKing.checkIllegalMove(board,
								XY.addToIndex(xyOppKing, coord[0], coord[1]),
								lastMoved,
								lastFrom);
				if (illegalMoveResult >= CORRECT_MOVE) {
					int checkOnMove = check(!currentOwner);
					if (checkOnMove != CHECK)
						return CORRECT_MOVE;
				}
			}
			if (oppositeCheck == CHECK) {
				returnMessage = currentOwner == WHITE ? CHECKMATE_TO_BLACK : CHECKMATE_TO_WHITE;
			} else {
				returnMessage = DRAW_STALEMATE;
			}
		}
		return returnMessage;
	}

	public int check(boolean ownerUnderCheck) {
		for (Figure fig : hmFigures.get(!ownerUnderCheck)) {
			int result = fig.checkIllegalMove(board, kings.get(ownerUnderCheck).getXY(), lastMoved, lastFrom);
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
		hmFigures.put(WHITE, new HashSet<Figure>());
		hmFigures.put(BLACK, new HashSet<Figure>());
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
		String madeMove = board[to].toLog() + XY.xyToString(from) + XY.xyToString(to);
		movesLog.add(madeMove);

		// add to 3 fold set check
		if (rule3FoldSet == null)
			rule3FoldSet = new HashSet<>();
		HashSet<String> positions = new HashSet<>();
		for (int i = 0; i < 2; i++) {
			boolean color = i % 2 == 0;
			for (Figure fig : hmFigures.get(color)) {
				positions.add(fig.toLog() + XY.xyToString(fig.getXY()));
			}
		}
		rule3FoldSet.add(positions);
		// save lastmoved + its state
		lastMoved = board[to];
		lastFrom = from;
		movesCount++;
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
			int[] rookXYmoves = new int[] { XY.addToIndex(rookXY, dx, 0), XY.addToIndex(rookXY, dx, 0) };
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
			int checkMove = figFrom.checkIllegalMove(board, to, lastMoved, lastFrom);

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
