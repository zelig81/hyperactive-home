package project.ilyagorban.model;

public enum Rank {
	WHITE_KING(100, "k", Owner.WHITE, "♔"), WHITE_QUEEN(4, "q", Owner.WHITE,
			"♕"), WHITE_ROOK(3, "r", Owner.WHITE, "♖"), WHITE_BISHOP(1, "b",
			Owner.WHITE, "♗"), WHITE_KNIGHT(1, "n", Owner.WHITE, "♘"), WHITE_PAWN(
			2, "p", Owner.WHITE, "♙"), BLACK_KING(100, "k", Owner.BLACK, "♚"), BLACK_QUEEN(
			4, "q", Owner.BLACK, "♛"), BLACK_ROOK(3, "r", Owner.BLACK, "♜"), BLACK_BISHOP(
			1, "b", Owner.BLACK, "♝"), BLACK_KNIGHT(1, "n", Owner.BLACK, "♞"), BLACK_PAWN(
			2, "p", Owner.BLACK, "♟");

	// ♔♕♖♗♘♙♚♛♜♝♞♟

	public static Rank getRank(String s, Owner o) {
		for (Rank r : Rank.values()) {
			if (r.index.equals(s) && r.owner == o)
				return r;
		}
		return null;
	}

	private String index;
	private int importance;
	private String picture;
	private Owner owner;

	private Rank(int i, String s, Owner o, String c) {
		this.importance = i;
		this.owner = o;
		this.picture = c;
		this.index = s;
	}

	public int getImportance() {
		return importance;
	}

	public String getIndex() {
		return index;
	}

	public Owner getOwner() {
		return owner;
	}

	public String getPicture() {
		return picture;
	}

	@Override
	public String toString() {
		return picture;
	}

	public String toLog() {
		return owner.getLetter() + index;
	}
}
