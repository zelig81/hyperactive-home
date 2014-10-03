package project.ilyagorban.model;

public enum Owner {
	WHITE(1, "w"), BLACK(-1, "b");
	private int direction;
	private String letter;

	private Owner(int direction, String letter) {
		this.direction = direction;
		this.letter = letter;
	}

	public int getDirection() {
		return direction;
	}

	public static Owner getOwner(String letter) {
		if (letter == null || letter.length() != 1)
			return null;
		switch (letter) {
		case "w":
			return WHITE;
		case "b":
			return BLACK;
		default:
			return null;
		}
	}

	String getLetter() {
		return letter;
	}

	public Owner oppositeOwner() {
		return Owner.values()[(this.ordinal() + 1) % 2];
	}
}
