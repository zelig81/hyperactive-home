package project.ilyagorban.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import project.ilyagorban.model.figures.*;

public class ChessModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	public static final int CHECK = -7;
	public static final int OBSTACLE_ON_THE_WAY = -6;
	public static final int DONT_TOUCH_NOT_YOUR_FIGURE_TO_MOVE = -5;
	public static final int INCORRECT_MOVE = -2;
	public static final int INCORRECT_INPUT = -1;
	public static final int CORRECT_MOVE = 0;
	public static final int PAWN_PROMOTION = 1;
	public static final int CASTLING = 2;
	public static final int EN_PASSANT = 3;
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
	private final static int[][] pool = new int[][] { { 0, 1 }, { 1, 1 }, { 1, 0 }, { -1, 1 },
			{ -1, 0 }, { -1, -1 }, { 0, -1 }, { 1, -1 } };
	
	private Figure[] board = new Figure[64];
	private HashMap<Boolean, HashSet<Figure>> hmFigures = new HashMap<>();
	private HashMap<Boolean, Figure> kings = new HashMap<>();
	private Figure lastMoved = null;
	private int lastFrom = 0;
	private int rule50Draw = 0;
	private int movesCount = 0;
	private HashSet<HashSet<String>> rule3FoldSet = null;
	private ArrayList<String> movesLog = null;
	transient private HashMap<Integer, ArrayList<Object>> savedState = new HashMap<>(3);
	
	public int assessPositions(int returnMessage, boolean currentOwner) {
		int oppositeCheck = this.check(!currentOwner);
		if (oppositeCheck != CHECK) {
			if (this.rule50Draw >= 50) {
				returnMessage = DRAW_50_RULE;
			}
			if (this.rule3FoldSet != null && this.movesCount - this.rule3FoldSet.size() >= 3) {
				returnMessage = DRAW_3_FOLD_REPETITION;
			}
			int wCount = this.hmFigures.get(WHITE).size();
			int bCount = this.hmFigures.get(BLACK).size();
			if (wCount * bCount == 1) {
				returnMessage = DRAW_IMPOSSIBILITY_OF_MATE;
			} else if (wCount * bCount == 2) {
				boolean side = wCount == 2 ? WHITE : BLACK;
				for (Figure fig : this.hmFigures.get(side)) {
					int importance = fig.getRank().getImportance();
					if (importance == 100) {
						continue;
					}
					if (importance == 1) {
						returnMessage = DRAW_IMPOSSIBILITY_OF_MATE;
					}
					
				}
			} else {
				int oddnessOfBishop = -1;
				boolean suiteToUnsufficientMaterialForMate = true;
				for (int i = 0; i < 2; i++) {
					boolean side = i % 2 == 0 ? WHITE : BLACK;
					for (Figure fig : this.hmFigures.get(side)) {
						if (fig.equals(this.kings.get(side)) == true) {
							continue;
						}
						if (fig instanceof Bishop) {
							if (oddnessOfBishop == -1) {
								oddnessOfBishop = fig.getXY() % 2;
								continue;
							} else {
								if (oddnessOfBishop == fig.getXY() % 2) {
									continue;
								}
							}
						}
						suiteToUnsufficientMaterialForMate = false;
						break;
					}
					if (suiteToUnsufficientMaterialForMate == false) {
						break;
					}
				}
				if (suiteToUnsufficientMaterialForMate == true) {
					returnMessage = DRAW_IMPOSSIBILITY_OF_MATE;
				}
			}
			
		}
		if (returnMessage >= CORRECT_MOVE) {
			Figure oppositeKing = this.kings.get(!currentOwner);
			Figure ownKing = this.kings.get(currentOwner);
			int xyOppKing = oppositeKing.getXY();
			int xyOwnKing = ownKing.getXY();
			int[] difXY = XY.getDifferenceXY(xyOppKing, xyOwnKing);
			if (Math.abs(difXY[0]) == 1 && Math.abs(difXY[1]) == 1
					|| Math.abs(difXY[0]) + Math.abs(difXY[1]) == 1) {
				return INCORRECT_MOVE;
			}
			for (int[] coord : pool) {
				int illegalMoveResult =
						oppositeKing.checkIllegalMove(this.board,
								XY.addToIndex(xyOppKing, coord[0], coord[1]),
								this.lastMoved,
								this.lastFrom);
				if (illegalMoveResult >= CORRECT_MOVE) {
					int checkOnMove = this.check(!currentOwner);
					if (checkOnMove != CHECK) {
						return CORRECT_MOVE;
					}
				}
			}
			if (oppositeCheck == CHECK) {
				returnMessage = currentOwner == WHITE ? CHECKMATE_TO_BLACK : CHECKMATE_TO_WHITE;
			} // TODO DRAW_STALEMATE check
		}
		return returnMessage;
	}
	
	public int check(boolean ownerUnderCheck) {
		for (Figure fig : this.hmFigures.get(!ownerUnderCheck)) {
			int result =
					fig.checkIllegalMove(this.board,
							this.kings.get(ownerUnderCheck).getXY(),
							this.lastMoved,
							this.lastFrom);
			if (result == CORRECT_MOVE) {
				return CHECK;
			}
		}
		return CORRECT_MOVE;
	}
	
	public int check(int[] moves, int afterTryingToMove, boolean currentOwner) {
		int output = this.check(currentOwner);
		if (output >= CORRECT_MOVE && afterTryingToMove == CASTLING) {
			this.move(moves[1], moves[0]);
			output = this.check(currentOwner);
			if (output >= CORRECT_MOVE) {
				this.move(moves[0], (moves[0] + moves[1]) / 2);
				output = this.check(currentOwner);
			}
			this.move(this.kings.get(currentOwner).getXY(), moves[1]);
		}
		if (output >= CORRECT_MOVE) {
			output = afterTryingToMove;
		}
		return output;
	}
	
	public boolean doGameInitialize() {
		this.hmFigures.put(WHITE, new HashSet<Figure>());
		this.hmFigures.put(BLACK, new HashSet<Figure>());
		boolean result = Board.doGameInitialize(this.board, this.hmFigures, this.kings);
		if (result == false) {
			return false;
		}
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public int doGameLoad(String fileName) {
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		int output = -1;
		try {
			fis = new FileInputStream("d:\\!Ilya\\programming\\" + fileName + ".ser");
			ois = new ObjectInputStream(fis);
			Object temp;
			boolean currentOwner = ois.readBoolean();
			System.out.println(currentOwner);
			this.board = (Figure[]) ois.readObject();
			temp = ois.readObject();
			this.hmFigures = (temp != null) ? (HashMap<Boolean, HashSet<Figure>>) temp : null;
			temp = ois.readObject();
			this.kings = (temp != null) ? (HashMap<Boolean, Figure>) temp : null;
			temp = ois.readObject();
			this.lastMoved = (temp != null) ? (Figure) temp : null;
			this.lastFrom = ois.readInt();
			this.rule50Draw = ois.readInt();
			this.movesCount = ois.readInt();
			temp = ois.readObject();
			this.rule3FoldSet = (temp != null) ? (HashSet<HashSet<String>>) temp : null;
			temp = ois.readObject();
			this.movesLog = (temp != null) ? (ArrayList<String>) temp : null;
			output = (currentOwner == WHITE) ? 1 : 0;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassCastException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ois != null) {
					ois.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return output;
	}
	
	public boolean doGameSave(boolean currentOwner, String fileName) {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		boolean output = false;
		try {
			fos = new FileOutputStream("d:\\!Ilya\\programming\\" + fileName + ".ser");
			oos = new ObjectOutputStream(fos);
			oos.writeBoolean(currentOwner);
			oos.writeObject(this.board);
			oos.writeObject(this.hmFigures);
			oos.writeObject(this.kings);
			oos.writeObject(this.lastMoved);
			oos.writeInt(this.lastFrom);
			oos.writeInt(this.rule50Draw);
			oos.writeInt(this.movesCount);
			oos.writeObject(this.rule3FoldSet);
			oos.writeObject(this.movesLog);
			output = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return output;
	}
	
	public Figure[] getBoard() {
		return this.board;
	}
	
	public void makeCastling(int[] moves) {
		// check for correctness of action has been made before
		int yFrom = XY.getY(moves[0]);
		boolean rightCastling = XY.getDifferenceXY(moves[0], moves[1])[0] > 0;
		int xFrom = rightCastling == true ? 7 : 0;
		int dx = rightCastling == true ? -2 : 3;
		int rookXY = XY.getIndexFromXY(xFrom, yFrom);
		int rookXYFrom = XY.addToIndex(rookXY, dx, 0);
		int rookXYTo = XY.addToIndex(rookXY, dx, 0);
		this.move(moves[0], moves[1]);
		this.move(rookXYFrom, rookXYTo);
		
		this.rule50Draw++;
		
	}
	
	public void makeCorrectMove(int[] moves) {
		// possible move + possible take
		Figure figTo = this.board[moves[1]];
		if (this.board[moves[0]] instanceof Pawn) {
			this.rule50Draw = 0;
		}
		if (figTo != null) {
			this.hmFigures.get(figTo.getRank().getOwner()).remove(figTo);
			this.rule50Draw = 0;
		}
		this.move(moves[0], moves[1]);
		
	}
	
	public void makeEnpassant(int[] moves) {
		int[] difXY = XY.getDifferenceXY(moves[0], moves[1]);
		int xyOfRemoved = XY.addToIndex(moves[0], difXY[0], 0);
		Figure figEP = this.board[xyOfRemoved];
		System.out.println(figEP.toLog() + XY.toLog(xyOfRemoved)); // XXX remove
		this.hmFigures.get(figEP.getRank().getOwner()).remove(figEP);
		this.board[xyOfRemoved] = null;
		this.rule50Draw = 0;
		this.move(moves[0], moves[1]);
		
	}
	
	private void move(int from, int to) {
		Figure fig = this.board[from];
		fig.setXY(to);
		this.board[to] = this.board[from];
		this.board[from] = null;
		
	}
	
	public boolean promotePawn(int[] moves, String promotion) {
		Figure pawn = this.board[moves[1]];
		Rank gotRank = Rank.getRank(promotion, pawn.getRank().getOwner());
		if (gotRank == null) {
			return false;
		} else {
			pawn.setRank(gotRank);
			return true;
		}
	}
	
	public void restoreStateBeforeMove(int afterTryingToMove) {
		ArrayList<Object> list = this.savedState.get(afterTryingToMove);
		this.rule50Draw = (Integer) list.get(0);
		int[] moves;
		switch (afterTryingToMove) {
		case CASTLING:
			moves = (int[]) list.get(1);
			this.move(moves[1], moves[0]);
			moves = (int[]) list.get(2);
			this.move(moves[1], moves[0]);
			break;
		case EN_PASSANT:
			moves = (int[]) list.get(1);
			this.move(moves[1], moves[0]);
			Figure removedPawn = (Figure) list.get(2);
			this.board[removedPawn.getXY()] = removedPawn;
			break;
		case CORRECT_MOVE:
			moves = (int[]) list.get(1);
			this.move(moves[1], moves[0]);
			Figure removedFigure = (Figure) list.get(2);
			if (removedFigure != null) {
				this.board[removedFigure.getXY()] = removedFigure;
			}
			break;
		}
		
	}
	
	public void saveMove(int from, int to) {
		// add to log;
		if (this.movesLog == null) {
			this.movesLog = new ArrayList<>();
		}
		String madeMove = this.board[to].toLog() + XY.toLog(from) + XY.toLog(to);
		this.movesLog.add(madeMove);
		
		// add to 3 fold set check
		if (this.rule3FoldSet == null) {
			this.rule3FoldSet = new HashSet<>();
		}
		HashSet<String> positions = new HashSet<>();
		for (int i = 0; i < 2; i++) {
			boolean color = i % 2 == 0;
			for (Figure fig : this.hmFigures.get(color)) {
				positions.add(fig.toLog() + XY.toLog(fig.getXY()));
			}
		}
		this.rule3FoldSet.add(positions);
		// save lastmoved + its state
		this.lastMoved = this.board[to];
		this.lastFrom = from;
		this.movesCount++;
	}
	
	public void saveStateBeforeMove(int result, int[] moves) {
		ArrayList<Object> list = new ArrayList<>(3);
		list.add(this.rule50Draw);
		switch (result) {
		case CASTLING:
			int yFrom = XY.getY(moves[0]);
			boolean rightCastling = XY.getDifferenceXY(moves[0], moves[1])[0] > 0;
			int xFrom = rightCastling == true ? 7 : 0;
			int dx = rightCastling == true ? -2 : 3;
			int rookXY = XY.getIndexFromXY(xFrom, yFrom);
			// check for castling move correctness has been made before but not checking for been under check
			int[] rookXYmoves =
					new int[] { XY.addToIndex(rookXY, dx, 0), XY.addToIndex(rookXY, dx, 0) };
			list.add(moves);
			list.add(rookXYmoves);
			this.savedState.put(CASTLING, list);
			break;
		case EN_PASSANT:
			int[] difXY = XY.getDifferenceXY(moves[0], moves[1]);
			list.add(moves);
			list.add(this.board[XY.addToIndex(moves[0], difXY[0], 0)]);
			this.savedState.put(EN_PASSANT, list);
			break;
		case CORRECT_MOVE:
			Figure figTo = this.board[moves[1]];
			list.add(moves);
			list.add(figTo);
			this.savedState.put(CORRECT_MOVE, list);
			break;
		default:
		}
		
	}
	
	public int tryToMove(int[] moves, boolean currentOwner) {
		int from = moves[0];
		int to = moves[1];
		Figure figFrom = this.board[from];
		
		if (figFrom != null && figFrom.isEnemy(currentOwner) == false) {
			int checkMove = figFrom.checkIllegalMove(this.board, to, this.lastMoved, this.lastFrom);
			
			if (checkMove >= CORRECT_MOVE) {
				this.saveStateBeforeMove(checkMove, moves);
				switch (checkMove) {
				case PAWN_PROMOTION:
				case CORRECT_MOVE:
					this.makeCorrectMove(moves);
					break;
				case CASTLING:
					this.makeCastling(moves);
					break;
				case EN_PASSANT:
					this.makeEnpassant(moves);
					break;
				}
				
			}
			return checkMove;
			
		} else {
			return DONT_TOUCH_NOT_YOUR_FIGURE_TO_MOVE;
		}
		
	}
}
