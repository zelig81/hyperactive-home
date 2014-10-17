/**
 * 
 */
package project.ilyagorban.model.figures;

import static project.ilyagorban.model.ChessModel.*;

import java.io.Serializable;

import project.ilyagorban.model.Rank;
import project.ilyagorban.model.XY;

/**
 * @author ilya gorban
 * 
 */
public abstract class Figure implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int xy;
	private boolean touched;
	private Rank rank;
	private int killLen;
	private int hashCode;
	
	public static final int[][] moveDirectionsOfBishop = new int[][] { { 1, 1 }, { 1, -1 },
			{ -1, -1 }, { -1, 1 } };
	public static final int[][] moveDirectionsOfRook = new int[][] { { 0, 1 }, { 0, -1 }, { 1, 0 },
			{ -1, 0 } };
	public static final int[][] moveDirectionsOfQueen = new int[][] { { 1, 0 }, { 1, 1 }, { 0, 1 },
			{ -1, 1 }, { -1, 0 }, { -1, -1 }, { 0, -1 }, { 1, -1 } };
	public static final int[][] moveDirectionsOfKnight = new int[][] { { 2, 1 }, { 2, -1 },
			{ -2, 1 }, { -2, -1 }, { 1, 2 }, { -1, 2 }, { 1, -2 }, { -1, -2 } };
	
	protected Figure(int moveCount, int xy, Rank r) {
		this.xy = xy;
		this.setRank(r);
		this.hashCode = moveCount * 10000 + xy * 100 + r.getImportance();
	}
	
	public int checkIllegalMove(Figure[] board, int to, Figure lastMoved, int lastFrom) {
		int from = this.getXY();
		if (to > 63 || to < 0 || from == to) {
			return INCORRECT_INPUT;
		}
		Figure figTo = board[to];
		if (figTo != null && figTo.isEnemy(this) == false) {
			return OBSTACLE_ON_THE_WAY;
		}
		int[] difXY = XY.getDifferenceXY(from, to);
		int dx = difXY[0];
		int dy = difXY[1];
		int output = INCORRECT_MOVE;
		int jumpLength = 0;
		
		if (this instanceof MarkerRook) {
			if (dx * dy == 0) {
				output = CORRECT_MOVE;
				jumpLength = Math.abs(dx + dy);
			}
		}
		
		if (this instanceof MarkerBishop && output == INCORRECT_MOVE) {
			if (Math.abs(dx) == Math.abs(dy)) {
				output = CORRECT_MOVE;
				jumpLength = Math.abs(dx);
			}
		}
		
		if (output == CORRECT_MOVE) {
			if (jumpLength <= this.getKillLen()) {
				int dirX = dx / jumpLength;
				int dirY = dy / jumpLength;
				for (int i = 1; i < jumpLength; i++) {
					int newXY = XY.addToIndex(from, dirX * i, dirY * i);
					if (newXY == -1) {
						continue;
					}
					Figure temp = board[newXY];
					if (temp != null && newXY != to) {
						return OBSTACLE_ON_THE_WAY;
					}
				}
			} else {
				output = INCORRECT_MOVE;
			}
		}
		return output;
	}
	
	@Override
	public boolean equals(Object obj) {
		return this.hashCode == ((Figure) obj).hashCode;
	}
	
	public int getKillLen() {
		return this.killLen;
	}
	
	public Rank getRank() {
		return this.rank;
	}
	
	public int getXY() {
		return this.xy;
	}
	
	@Override
	public int hashCode() {
		return this.hashCode;
	}
	
	public boolean isEnemy(boolean currentOwner) {
		return this.getRank().getOwner() != currentOwner;
	}
	
	public boolean isEnemy(Figure fig) {
		return this.getRank().getOwner() != fig.getRank().getOwner();
	}
	
	public boolean isTouched() {
		return this.touched;
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
		return this.getRank().toLog();
	}
	
	@Override
	public String toString() {
		return this.getRank().toString();
	}
	
}
