package project.ilyagorban.model;

import static project.ilyagorban.model.ChessModel.*;

public enum Rank {
	WHITE_KING(100, "k", WHITE, "♔"),
	WHITE_QUEEN(4, "q", WHITE, "♕"),
	WHITE_ROOK(3, "r", WHITE, "♖"),
	WHITE_BISHOP(1, "b", WHITE, "♗"),
	WHITE_KNIGHT(1, "n", WHITE, "♘"),
	WHITE_PAWN(2, "p", WHITE, "♙"),
	BLACK_KING(100, "k", BLACK, "♚"),
	BLACK_QUEEN(4, "q", BLACK, "♛"),
	BLACK_ROOK(3, "r", BLACK, "♜"),
	BLACK_BISHOP(1, "b", BLACK, "♝"),
	BLACK_KNIGHT(1, "n", BLACK, "♞"),
	BLACK_PAWN(2, "p", BLACK, "♟");

	// ♔♕♖♗♘♙♚♛♜♝♞♟

	private String	index;

	private int		importance;
	private String	picture;
	private boolean	owner;

	private Rank(int i, String s, boolean o, String c) {
		this.importance = i;
		this.owner = o;
		this.picture = c;
		this.index = s;
	}

	public static Rank getRank(String s, boolean owner) {
		for (Rank r : Rank.values()) {
			if (r.index.equals(s) && r.owner == owner)
				return r;
		}
		return null;
	}

	public int getImportance() {
		return importance;
	}

	public String getIndex() {
		return index;
	}

	public boolean getOwner() {
		return owner;
	}

	public String toLog() {
		return (owner ? "w" : "b") + index;
	}

	@Override
	public String toString() {
		return picture;
	}
}
