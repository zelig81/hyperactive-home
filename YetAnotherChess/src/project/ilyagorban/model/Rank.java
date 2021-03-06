package project.ilyagorban.model;

import static project.ilyagorban.model.ChessModel.*;

public enum Rank {
	WHITE_KING(99, "k", WHITE, "♔"),
	WHITE_QUEEN(4, "q", WHITE, "♕"),
	WHITE_ROOK(3, "r", WHITE, "♖"),
	WHITE_BISHOP(1, "b", WHITE, "♗"),
	WHITE_KNIGHT(1, "n", WHITE, "♘"),
	WHITE_PAWN(2, "p", WHITE, "♙"),
	BLACK_KING(99, "k", BLACK, "♚"),
	BLACK_QUEEN(4, "q", BLACK, "♛"),
	BLACK_ROOK(3, "r", BLACK, "♜"),
	BLACK_BISHOP(1, "b", BLACK, "♝"),
	BLACK_KNIGHT(1, "n", BLACK, "♞"),
	BLACK_PAWN(2, "p", BLACK, "♟");
	
	// ♔♕♖♗♘♙♚♛♜♝♞♟
	
	public static Rank getRank(String s, boolean owner) {
		for (Rank r : Rank.values()) {
			if (r.index.equals(s) && r.owner == owner) {
				return r;
			}
		}
		return null;
	}
	
	private String index;
	private int importance;
	private String picture;
	
	private boolean owner;
	
	private Rank(int i, String s, boolean o, String c) {
		this.importance = i;
		this.owner = o;
		this.picture = c;
		this.index = s;
	}
	
	public int getImportance() {
		return this.importance;
	}
	
	public String getIndex() {
		return this.index;
	}
	
	public boolean getOwner() {
		return this.owner;
	}
	
	public String toLog() {
		return (this.owner ? "w" : "b") + this.index;
	}
	
	@Override
	public String toString() {
		return this.picture;
	}
}
