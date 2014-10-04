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
	private int xy;
	private boolean touched;
	private Rank rank;
	private int[][] moveDirections;
	private int[][] killDirections;
	private int moveLen;
	private int killLen;

	public static final int[][] moveDirectionsOfBishop = new int[][] {
			{ 1, 1 }, { 1, -1 }, { -1, -1 }, { -1, 1 } };
	public static final int[][] moveDirectionsOfRook = new int[][] { { 0, 1 },
			{ 0, -1 }, { 1, 0 }, { -1, 0 } };
	public static final int[][] moveDirectionsOfQueen = new int[][] { { 1, 0 },
			{ 1, 1 }, { 0, 1 }, { -1, 1 }, { -1, 0 }, { -1, -1 }, { 0, -1 },
			{ 1, -1 } };
	public static final int[][] moveDirectionsOfKnight = new int[][] {
			{ 2, 1 }, { 2, -1 }, { -2, 1 }, { -2, -1 }, { 1, 2 }, { -1, 2 },
			{ 1, -2 }, { -1, -2 } };
	public static final int[][] moveDirectionsOfWhitePawn = new int[][] { { 0,
			1 } };
	public static final int[][] moveDirectionsOfBlackPawn = new int[][] { { 0,
			-1 } };
	public static final int[][] killDirectionsOfWhitePawn = new int[][] {
			{ 1, 1, }, { -1, 1 } };
	public static final int[][] killDirectionsOfBlackPawn = new int[][] {
			{ 1, -1, }, { -1, -1 } };
	public static final int[][][] allKillDirections = { moveDirectionsOfKnight,
			moveDirectionsOfBishop, moveDirectionsOfRook };
	public static final int[] allMoveLen = { 1, 8, 8 };
	public static final String[][] allKillerIndex = { { "n" }, { "q", "b" },
			{ "q", "r" } };

	protected Figure(int xy, Rank r) {
		this.xy = xy;
		this.setRank(r);
	}

	public int[][] getKillDirections() {
		return this.killDirections;
	}

	public int getKillLen() {
		return killLen;
	}

	public int[][] getMoveDirections() {
		return this.moveDirections;
	}

	public int getMoveLen() {
		return moveLen;
	}

	public Rank getRank() {
		return rank;
	}

	public int getSpecialCorrectMoveName(int to) {
		return CORRECT_MOVE;
	}

	public int getXY() {
		return xy;
	}

	public boolean isEnemy(boolean currentOwner) {
		return this.getRank().getOwner() != currentOwner;
	}

	public boolean isEnemy(Figure fig) {
		return this.getRank().getOwner() != fig.getRank().getOwner();
	}

	public boolean isTouched() {
		return touched;
	}

	protected void setKillDirections(int[][] directions) {
		this.killDirections = directions;
	}

	void setKillLen(int killLen) {
		this.killLen = killLen;
	}

	protected void setMoveDirections(int[][] directions) {
		this.moveDirections = directions;
	}

	public void setMoveLen(int moveLen) {
		this.moveLen = moveLen;
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
		return getRank().toLog() + XY.xyToString(xy);
	}

	@Override
	public String toString() {
		return getRank().toString();
	}

	public int checkIllegalMove(Figure[] board, int from, int to) {

		if (this instanceof MarkerRook) {
		}
		if (this instanceof MarkerBishop) {
		}
		if (this instanceof Knight) {
		}
		return 0;
	}
}
