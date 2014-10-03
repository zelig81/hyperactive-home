package project.ilyagorban.model;

import java.util.HashMap;

public enum CoordX {
	A, B, C, D, E, F, G, H;

	public static CoordX getCoordX(char x) {
		return CoordX.valueOf(String.valueOf(x).toUpperCase());
	}

	public static CoordX getCoordX(int x) {
		return map.get(x);
	}

	private static HashMap<Integer, CoordX> map = new HashMap<>();

	static {
		for (CoordX cX : CoordX.values()) {
			map.put(cX.ordinal(), cX);
		}
	}

	public int getX() {
		return this.ordinal();
	}

	@Override
	public String toString() {
		return super.toString().toLowerCase();
	}

}
