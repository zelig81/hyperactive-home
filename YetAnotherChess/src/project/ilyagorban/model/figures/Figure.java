/**
 * 
 */
package project.ilyagorban.model.figures;

import static project.ilyagorban.model.ChessModel.*;

import project.ilyagorban.model.Rank;
import project.ilyagorban.model.XY;

/**
 * @author ilya gorban
 * 
 */
public abstract class Figure {
	private int					xy;
	private boolean				touched;
	private Rank				rank;
	private int					killLen;

	public static final int[][]	moveDirectionsOfBishop	= new int[][] { { 1, 1 }, { 1, -1 }, { -1, -1 }, { -1, 1 } };
	public static final int[][]	moveDirectionsOfRook	= new int[][] { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 } };
	public static final int[][]	moveDirectionsOfQueen	= new int[][] { { 1, 0 }, { 1, 1 }, { 0, 1 }, { -1, 1 },
			{ -1, 0 }, { -1, -1 }, { 0, -1 }, { 1, -1 } };
	public static final int[][]	moveDirectionsOfKnight	= new int[][] { { 2, 1 }, { 2, -1 }, { -2, 1 }, { -2, -1 },
			{ 1, 2 }, { -1, 2 }, { 1, -2 }, { -1, -2 }	};

	protected Figure(int xy, Rank r) {
		this.xy = xy;
		setRank(r);
	}

	public int checkIllegalMove(Figure[] board, int to, Figure lastMoved, int lastFrom) {
		int from = getXY();
		if (to > 63 || to < 0 || from == to)
			return INCORRECT_INPUT;
		Figure figTo = board[to];
		if (figTo != null && figTo.isEnemy(this) == false) {
			return OBSTACLE_ON_THE_WAY;
		}
		int[] difXY = XY.getDifferenceXY(from, to);
		int dx = difXY[0];
		int dy = difXY[1];
		int output = INCORRECT_MOVE;
		int jumpLength = 0;

		if (this instanceof MarkerRook)
			if (dx * dy == 0) {
				output = CORRECT_MOVE;
				jumpLength = dx + dy;
			}

		if (this instanceof MarkerBishop && output == INCORRECT_MOVE)
			if (Math.abs(dx) == Math.abs(dy)) {
				output = CORRECT_MOVE;
				jumpLength = Math.abs(dx);
			}

		if (output == CORRECT_MOVE) {
			int dirX = dx / jumpLength;
			int dirY = dy / jumpLength;
			for (int i = 1; i < killLen || i < dx + dy; i++) {
				int newXY = XY.addToIndex(from, dirX * i, dirY * i);
				Figure temp = board[newXY];
				if (temp != null && newXY != to)
					return OBSTACLE_ON_THE_WAY;
			}
		}
		return output;
	}

	@Override
	public boolean equals(Object obj) {
		return this.xy == ((Figure) obj).xy;
	}

	public Rank getRank() {
		return rank;
	}

	public int getXY() {
		return xy;
	}

	@Override
	public int hashCode() {
		return this.xy;
	}

	public boolean isEnemy(boolean currentOwner) {
		return getRank().getOwner() != currentOwner;
	}

	public boolean isEnemy(Figure fig) {
		return getRank().getOwner() != fig.getRank().getOwner();
	}

	public boolean isTouched() {
		return touched;
	}

	void setKillLen(int killLen) {
		this.killLen = killLen;
	}

	public void setRank(Rank rank) {
		this.rank = rank;
	}

	public void setTouched(boolean touched) {
		this.touched = touched;
	}

	public void setXY(int xy) {
		this.xy = xy;
	}

	public String toLog() {
		return getRank().toLog();
	}

	@Override
	public String toString() {
		return getRank().toString();
	}

}
