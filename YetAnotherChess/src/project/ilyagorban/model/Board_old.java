package project.ilyagorban.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import static project.ilyagorban.model.ChessModel.*;
import project.ilyagorban.model.figures.Figure;
import project.ilyagorban.model.figures.King;
import project.ilyagorban.model.figures.Pawn;

public class Board_old {
	public static Board_old getInstance() {
		return new Board_old();
	}

	private Figure[][] board = new Figure[8][8];
	private HashMap<Owner, HashSet<XY>> xyOfSides;
	private HashSet<HashSet<String>> log;
	private HashMap<Owner, XY> xyOfKings;
	private XY startPositions;
	private XY endPositions;
	private Figure lastMovedFigure;
	private int numberOfMove;
	private int numberOfFiftyRule;
	private Figure possibleLastMovedFigure;
	private XY possibleStartPosition;
	private XY possibleEndPosition;
	private ArrayList<XY> possibleMovesFrom;
	private ArrayList<XY> possibleMovesTo;
	private static final ArrayList<String> startGamePositions = new ArrayList<>();

	static {
		startGamePositions.add("wra1");
		startGamePositions.add("wnb1");
		startGamePositions.add("wbc1");
		startGamePositions.add("wqd1");
		startGamePositions.add("wke1");
		startGamePositions.add("wbf1");
		startGamePositions.add("wng1");
		startGamePositions.add("wrh1");
		startGamePositions.add("wpa2");
		startGamePositions.add("wpb2");
		startGamePositions.add("wpc2");
		startGamePositions.add("wpd2");
		startGamePositions.add("wpe2");
		startGamePositions.add("wpf2");
		startGamePositions.add("wpg2");
		startGamePositions.add("wph2");

		startGamePositions.add("bra8");
		startGamePositions.add("bnb8");
		startGamePositions.add("bbc8");
		startGamePositions.add("bqd8");
		startGamePositions.add("bke8");
		startGamePositions.add("bbf8");
		startGamePositions.add("bng8");
		startGamePositions.add("brh8");
		startGamePositions.add("bpa7");
		startGamePositions.add("bpb7");
		startGamePositions.add("bpc7");
		startGamePositions.add("bpd7");
		startGamePositions.add("bpe7");
		startGamePositions.add("bpf7");
		startGamePositions.add("bpg7");
		startGamePositions.add("bph7");
	}

	private Board_old() {
	}

	protected int assessDraw(Figure fig, XY to) {
		if (numberOfFiftyRule >= 50) {
			return DRAW_50_RULE;
		} else if (log.size() < numberOfMove - 3) {
			return DRAW_3_FOLD_REPETITION;
		} else if (assessImpossibilityOfMate() == true) {
			return DRAW_IMPOSSIBILITY_OF_MATE;
		} else
			return CORRECT_MOVE;
	}

	protected boolean assessImpossibilityOfMate() {
		int importanceSum = 0;
		int[] numFigures = { 0, 0 };
		boolean isAllFiguresBesidesKingAreBishopsAndSameColor = true;
		boolean isFirstBishop = true;
		int colour = -1;
		for (int i = 0; i < 2; i++) {
			Owner o = Owner.values()[i];
			for (XY xy : xyOfSides.get(o)) {
				Figure fig = getFigure(xy);
				Rank r = fig.getRank();
				int importance = r.getImportance();
				if (importance != 2)
					return false;

				numFigures[i]++;

				if (isAllFiguresBesidesKingAreBishopsAndSameColor == true
						&& r.getIndex().equals("b") == false) {
					isAllFiguresBesidesKingAreBishopsAndSameColor = false;
				}
				if (isAllFiguresBesidesKingAreBishopsAndSameColor == true) {
					if (isFirstBishop == true) {
						colour = (fig.getXY().getX() + fig.getXY().getY()) % 2;
						isFirstBishop = false;
					} else {
						int newColour = (fig.getXY().getX() + fig.getXY()
								.getY()) % 2;
						if (newColour != colour) {
							isAllFiguresBesidesKingAreBishopsAndSameColor = false;
						}
					}
				}
				importanceSum += importance;
			}
		}

		if (importanceSum <= 1) {
			// both sides have nothing or a bishop or a knight
			return true;
		} else if (isAllFiguresBesidesKingAreBishopsAndSameColor == true
				&& (numFigures[0] * numFigures[1] != 0)) {
			// All Figures Besides the King Are Bishops And Same Color and each
			// side has at least one of them
			return true;
		} else
			return false;

	}

	public int assessMateOrStalemate(Figure fig, XY to, int checkOutput) {
		int output = CORRECT_MOVE;
		XY origXY = fig.getXY();
		Owner o = fig.getRank().getOwner();
		move(fig, to);
		for (XY xy : xyOfSides.get(o.oppositeOwner())) {
			output = checkInAssessMateOrStalemate(xy);
			if (output != CHECK_TO_CURRENT_SIDE)
				break;
		}
		move(fig, origXY);
		if (output == CHECK_TO_CURRENT_SIDE) {
			// mate or stalemate to awaiting side
			if (checkOutput == CORRECT_MOVE) {
				output = DRAW_STALEMATE;
			} else {
				output = (o == Owner.WHITE) ? CHECKMATE_TO_BLACK
						: CHECKMATE_TO_WHITE;
			}
		}
		return output;

	}

	public int assessPositions(Figure fig, XY to, int correctMoveResult) {
		// TODO refactor assessPositions
		int output = check(fig, to);
		if (output >= CORRECT_MOVE) {
			if (output != CHECK_TO_AWAITING_SIDE) {
				output = assessDraw(fig, to);
			}

			if (output < DRAW) {
				output = assessMateOrStalemate(fig, to, output);
			}
		}
		if (output >= CORRECT_MOVE && output < GAME_ENDINGS) {
			// TODO could be problem with draw
			output = correctMoveResult;
		}
		return output;
	}

	public void castling(Figure king, XY to) {
		XY kingXY = king.getXY();
		int rookX = (to.getX() == 6) ? 7 : 0;
		int kingY = kingXY.getY();
		int newRookX = (to.getX() == 6) ? 5 : 3;
		savePossibleMove(kingXY, to);
		move(king, to);
		Figure rook = getFigure(rookX, kingY);
		XY newRookXY = XY.getNewXY(newRookX, kingY);
		savePossibleMove(rook.getXY(), newRookXY);
		move(rook, newRookXY);

	}

	public int check(Figure figFrom, XY to) {
		Owner w = Owner.WHITE;
		Owner b = Owner.BLACK;
		Owner o = figFrom.getRank().getOwner();
		XY origXY = figFrom.getXY();
		int result = CORRECT_MOVE;
		move(figFrom, to);

		if (to.equals(xyOfKings.get(o))) {
			boolean isKingsNotOnNeighborSquares = Math.abs(xyOfKings.get(w)
					.getX() - xyOfKings.get(b).getX()) > 1
					&& Math.abs(xyOfKings.get(w).getY()
							- xyOfKings.get(b).getY()) > 1;
			if (isKingsNotOnNeighborSquares == false) {
				result = INCORRECT_MOVE;
			}
		}
		result = check(o);

		move(figFrom, origXY);
		return result;

	}

	// TODO move isUnderAttack to board.check() foreach xy on opposite side
	// isUnderAttack(xy, currentKingXY)!!!
	// public boolean isUnderAttack(Board board) {
	// boolean output = false;
	// for (int i = 0; i < 3; i++) {
	// if (output == true)
	// break;
	// this.setMoveDirections(allKillDirections[i]);
	// this.setMoveLen(allMoveLen[i]);
	// ArrayList<XY> pm = board.getPossibleMoves(this);
	// if (pm == null) {
	// System.out.println(this.toLog() + allKillDirections[i].toString());
	// break;
	// }
	// if (pm.size() != 0)
	// for (XY xy : pm) {
	// Figure fig = board.getFigure(xy);
	// if (fig != null) {
	// for (String s : allKillerIndex[i]) {
	// boolean isThreat = fig.isEnemy(this) &&
	// fig.getRank().getIndex().equals(s);
	// if (isThreat) {
	// output = true;
	// break;
	// }
	// }
	// }
	// }
	// }
	// if (output == false) {
	// ArrayList<XY> ppa = board.getPawnPossibleAttack(null, null);
	// for (XY xy : ppa) {
	// Figure fig = board.getFigure(xy);
	// if (this.isEnemy(fig) && fig.getRank().getIndex() == "p") {
	// output = true;
	// break;
	// }
	// }
	// }
	// this.setMoveDirections(moveDirectionsOfQueen);
	// this.setMoveLen(1);
	// return output;
	//
	// }

	// TODO refactor checkFromKingsPointOfView
	// public int checkFromKingsPointOfView(Owner o) {
	// King currentKing = (King) getFigure(xyOfKings.get(o));
	// boolean isUnderAttack = currentKing.isUnderAttack(this);
	// if (isUnderAttack == true) {
	// return CHECK_TO_CURRENT_SIDE;
	// }
	// Owner oo = o.oppositeOwner();
	// King oppositeKing = (King) getFigure(xyOfKings.get(oo));
	// isUnderAttack = oppositeKing.isUnderAttack(this);
	// if (isUnderAttack == true) {
	// return CHECK_TO_AWAITING_SIDE;
	// }
	// return CORRECT_MOVE;
	//
	// }

	private int check(Owner o) {
		// TODO Auto-generated method stub
		return 0;
	}

	protected int checkInAssessMateOrStalemate(XY xy) {
		int output = CORRECT_MOVE;
		Figure figFromOtherSide = getFigure(xy);
		ArrayList<XY> possibleMoves = getPossibleMoves(figFromOtherSide);
		for (XY figureFromOtherSidePossibleMove : possibleMoves) {
			output = check(figFromOtherSide, figureFromOtherSidePossibleMove);
			if (output != CHECK_TO_CURRENT_SIDE) {
				break;
			}
		}
		return output;
	}

	public int checkMove(XY from, XY to) {
		Figure figFrom = getFigure(from);
		Figure figTo = getFigure(to);
		boolean isEndPointEmptyOrEnemy = (figTo == null || figTo
				.isEnemy(figFrom));
		if (isEndPointEmptyOrEnemy == false)
			return OBSTACLE_ON_WAY;

		int output = figFrom.getSpecialCorrectMoveName(to);
		if (output > CORRECT_MOVE) {
			ArrayList<XY> psm = getPossibleSpecialMove(figFrom, output);
			if (psm.contains(to) == false) {
				output = CORRECT_MOVE; // check if XY to exists in possibleMoves
										// (pm)
			}
		}

		if (output == CORRECT_MOVE) {
			ArrayList<XY> pm = getPossibleMoves(figFrom);
			if (pm.contains(to) == true) {
				output = CORRECT_MOVE;
			} else {
				output = INCORRECT_MOVE;
			}
		}

		if (output >= CORRECT_MOVE) {
			output = assessPositions(figFrom, to, output);
		}
		return output;

	}

	public void enPassant(Figure pawnKiller) {
		remove(endPositions);
		// TODO savePossibleRemove(possibleEndPosition);
		XY newXYOfPawnKiller = XY.getNewXY(endPositions.getX(),
				endPositions.getY()
						+ pawnKiller.getRank().getOwner().getDirection());
		savePossibleMove(pawnKiller.getXY(), newXYOfPawnKiller);
		move(pawnKiller, newXYOfPawnKiller);
	}

	public Figure[][] getBoard() {
		return board;
	}

	public XY getEndPositions() {
		return endPositions;
	}

	public Figure getFigure(char x, char y) {
		return board[x - 'a'][(Character.digit(y, 10) - 1)];
	}

	public Figure getFigure(int x, int y) {
		return board[x][y];
	}

	public Figure getFigure(String in) {
		return getFigure(in.charAt(0), in.charAt(1));
	}

	public Figure getFigure(XY from) {
		if (from == null) {
			return null;
		}
		return board[from.getX()][from.getY()];
	}

	public Figure getLastMovedFigure() {
		return lastMovedFigure;
	}

	private void getPossibleKillOrMoveForOneDirection(ArrayList<XY> list,
			Figure figFrom, int[] dir, int length, boolean isKillAction) {
		boolean isPawn = figFrom instanceof Pawn;
		int thisX = figFrom.getXY().getX();
		int thisY = figFrom.getXY().getY();
		int i = 1;
		while (true) {
			if (i > length)
				break;
			int newX = thisX + dir[0] * i;
			int newY = thisY + dir[1] * i;
			XY xy = XY.getNewXY(newX, newY);
			if (xy == null) {
				break;
			}
			i++;
			Figure figTo = getFigure(newX, newY);
			if (figTo == null) {
				boolean isPawnKiller = (isPawn == true && isKillAction == true);
				if (isPawnKiller == false) {
					list.add(xy);
					continue;
				}
			} else if (figFrom.isEnemy(figTo)) {
				boolean isPawnNotKiller = (isPawn == true && isKillAction == false);
				if (isPawnNotKiller == false) {
					list.add(xy);
				}
			}
			break;
		}

	}

	public ArrayList<XY> getPossibleMoves(Figure figFrom) {
		ArrayList<XY> output = new ArrayList<>();
		// cycle of directions
		for (int[] dir : figFrom.getKillDirections()) {
			getPossibleKillOrMoveForOneDirection(output, figFrom, dir,
					figFrom.getKillLen(), true);
		}
		if (figFrom instanceof Pawn) {
			for (int[] dir : figFrom.getMoveDirections()) {
				getPossibleKillOrMoveForOneDirection(output, figFrom, dir,
						figFrom.getMoveLen(), false);
			}
		}
		return output;
	}

	private ArrayList<XY> getPossibleSpecialMove(Figure figFrom,
			int possibleOutcome) {
		ArrayList<XY> output = new ArrayList<>();
		if (possibleOutcome <= CORRECT_MOVE)
			return output;
		switch (possibleOutcome) {
		case PAWN_PROMOTION:
			output = getPossibleMoves(figFrom);
			break;
		case EN_PASSANT:
			if (isEnPassantPossible(figFrom) == true)
				output.add(xyEnPassantPossible(figFrom));
			break;
		case CASTLING:
			output = xyCastlingPossible(figFrom);
		}
		return output;

	}

	public XY getStartPositions() {
		return startPositions;
	}

	public XY getXyOfKing(Owner o) {
		return xyOfKings.get(o);
	}

	public HashSet<XY> getXyOfSides(Owner o) {
		return xyOfSides.get(o);
	}

	public boolean initializeGame() {
		numberOfMove = 0;
		log = new HashSet<>();
		numberOfFiftyRule = 0;
		return initializeGame(numberOfMove, log, numberOfFiftyRule,
				startGamePositions);
	}

	/*
	 * need to make this check after making move
	 */

	public boolean initializeGame(int numberOfMove,
			HashSet<HashSet<String>> log, int numberOfFiftyRule,
			ArrayList<String> startGamePositions) {
		this.numberOfMove = numberOfMove;
		this.log = log;
		boolean output = true;
		xyOfKings = new HashMap<>();
		xyOfSides = new HashMap<>();
		xyOfSides.put(Owner.WHITE, new HashSet<XY>());
		xyOfSides.put(Owner.BLACK, new HashSet<XY>());
		for (String startGamePosition : startGamePositions) {
			output = setFigureToPosition(startGamePosition);
			if (output == false) {
				break;
			}
		}
		return output;

	}

	public boolean isEnPassantPossible(Figure p) {
		boolean isLastMovedFigurePawnAndInEnPassantLetalZone = (lastMovedFigure
				.getRank().getIndex().equals("p")
				&& (Math.abs(endPositions.getY() - startPositions.getY()) == 2) && (Math
				.abs(endPositions.getX() - p.getXY().getX()) == 1));
		return isLastMovedFigurePawnAndInEnPassantLetalZone;

	}

	public void move(Figure figFrom, int x, int y) {
		board[figFrom.getXY().getX()][figFrom.getXY().getY()] = null;
		XY newXY = XY.getNewXY(x, y);
		figFrom.setXY(newXY);
		Owner o = figFrom.getRank().getOwner();
		if (figFrom.getXY().equals(xyOfKings.get(o))) {
			xyOfKings.get(o).setXY(x, y);
		}
		board[x][y] = figFrom;
	}

	public void move(Figure figFrom, XY to) {
		move(figFrom, to.getX(), to.getY());
	}

	public boolean promotePawn(Figure pawn, Rank gotRank, XY to) {
		pawn.setRank(gotRank);
		return true;
	}

	public void remove(int x, int y) {
		Figure fig = board[x][y];
		xyOfSides.get(fig.getRank().getOwner()).remove(XY.getNewXY(x, y));
		board[x][y] = null;

	}

	/*
	 * already known that to current side this move is correct
	 * 
	 * if opposite side could move without further check it's asserted that it's
	 * not mate or stalemate so it's correct move
	 */

	public void remove(XY xy) {
		remove(xy.getX(), xy.getY());
	}

	public void resetNumberOfFiftyRule() {
		numberOfFiftyRule = 0;
	}

	public void saveMove() {
		numberOfMove++;
		startPositions = possibleStartPosition;
		endPositions = possibleEndPosition;
		lastMovedFigure = possibleLastMovedFigure;
		Rank r = lastMovedFigure.getRank();
		Owner currentOwner = r.getOwner();
		lastMovedFigure.setTouched(true);
		if (possibleMovesFrom != null) {
			xyOfSides.get(currentOwner).removeAll(possibleMovesFrom);
			xyOfSides.get(currentOwner).addAll(possibleMovesTo);
		}

		HashSet<String> thisMove = new HashSet<>();
		for (XY xy : xyOfSides.get(Owner.WHITE)) {
			thisMove.add(getFigure(xy).toLog());
		}
		for (XY xy : xyOfSides.get(Owner.BLACK)) {
			thisMove.add(getFigure(xy).toLog());
		}

		possibleMovesFrom = null;
		possibleMovesTo = null;

		log.add(thisMove);
	}

	public void savePossibleLastMovedFigure(Figure figFrom, XY from, XY to) {
		this.possibleLastMovedFigure = figFrom;
		this.possibleStartPosition = from;
		this.possibleEndPosition = to;

	}

	public void savePossibleMove(XY from, XY to) {
		if (possibleMovesFrom == null) {
			possibleMovesFrom = new ArrayList<>();
			possibleMovesTo = new ArrayList<>();
		}
		possibleMovesFrom.add(from);
		possibleMovesTo.add(to);
	}

	public void setFigureToPosition(Figure fig) {
		xyOfSides.get(fig.getRank().getOwner()).add(fig.getXY());
		board[fig.getXY().getX()][fig.getXY().getY()] = fig;
	}

	public boolean setFigureToPosition(String startGamePosition) {
		Figure fig = Figure.newInstance(startGamePosition);
		if (fig == null) {
			return false;
		}
		boolean isXYAlreadyPresent = xyOfSides.get(Owner.BLACK).contains(
				fig.getXY())
				|| xyOfSides.get(Owner.WHITE).contains(fig.getXY())
				|| xyOfKings.containsValue(fig.getXY());
		if (isXYAlreadyPresent == true) {
			return false;
		}
		if (fig instanceof King) {
			if (xyOfKings.get(fig.getRank().getOwner()) != null) {
				return false;
			}
			xyOfKings.put(fig.getRank().getOwner(), fig.getXY());
		}
		setFigureToPosition(fig);
		return true;
	}

	private ArrayList<XY> xyCastlingPossible(Figure figFrom) {
		ArrayList<XY> output = new ArrayList<>();
		int kingX = figFrom.getXY().getX();
		int kingY = figFrom.getXY().getY();
		for (int x : new int[] { 0, 7 }) {
			int stepsX = x - kingX;
			int direction = stepsX / Math.abs(stepsX);
			Figure rook = getFigure(x, kingY);
			if (rook != null && rook.isTouched() == false) {
				boolean isAbleToCastle = true;
				for (int i = 1; i < Math.abs(stepsX); i++) {
					int newX = kingX + i * direction;
					Figure fig = getFigure(newX, kingY);
					boolean isEmpty = (fig == null);
					if (isEmpty) {
						continue;
					} else {
						isAbleToCastle = false;
						break;
					}
				}
				if (isAbleToCastle == true)
					output.add(XY.getNewXY(kingX + direction * 2, kingY));
			}
		}
		return output;
	}

	public XY xyEnPassantPossible(Figure p) {
		boolean isLastMovedFigurePawnAndInEnPassantLetalZone = isEnPassantPossible(p);
		if (isLastMovedFigurePawnAndInEnPassantLetalZone) {
			return XY.getNewXY(endPositions.getX(), endPositions.getY()
					+ p.getRank().getOwner().getDirection());
		}
		return null;
	}
}
