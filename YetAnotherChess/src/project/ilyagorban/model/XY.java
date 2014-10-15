package project.ilyagorban.model;

import java.util.Arrays;
import static project.ilyagorban.model.ChessModel.*;
import java.util.List;

public class XY {
	public static int addToIndex(int from, int dx, int dy) {
		if (from > 63 || from < 0) {
			return -1;
		}
		int newX = getX(from) + dx;
		int newY = getY(from) + dy;
		if (newX > 7 || newX < 0 || newY > 7 || newY < 0) {
			return -1;
		}
		return getIndexFromXY(newX, newY);
	}
	
	public static int[] getDifferenceXY(int from, int to) {
		int difX = getX(to) - getX(from);
		int difY = getY(to) - getY(from);
		return new int[] { difX, difY };
	}
	
	public static int getIndexFromXY(int x, int y) {
		if (x > 7 || x < 0 || y > 7 || y < 0) {
			return INCORRECT_INPUT;
		} else {
			return x * 8 + y;
		}
	}
	
	public static int getIndexFromXY(String input) {
		if (input == null || input.length() != 2) {
			return INCORRECT_INPUT;
		}
		String[] xy = input.split("");
		return getIndexFromXY(xy[0], xy[1]);
	}
	
	public static int getIndexFromXY(String x, String y) {
		if (x == null || y == null || x.length() != 1 || y.length() != 1) {
			return INCORRECT_INPUT;
		}
		int iX = xRepresentations.indexOf(x);
		int iY = yRepresentations.indexOf(y);
		if (iX >= 0 && iY >= 0) {
			return getIndexFromXY(iX, iY);
		}
		return INCORRECT_INPUT;
	}
	
	public static int[] getIndicesfromInput(String input) {
		if (input == null || input.length() != 4) {
			return null;
		}
		String[] inputXY = input.split("");
		int from = getIndexFromXY(inputXY[0], inputXY[1]);
		int to = getIndexFromXY(inputXY[2], inputXY[3]);
		if (from == INCORRECT_INPUT || to == INCORRECT_INPUT) {
			return null;
		} else {
			return new int[] { from, to };
		}
	}
	
	public static int getX(int index) {
		return getXYFromIndex(index)[0];
	}
	
	public static int[] getXYFromIndex(int index) {
		if (index > 63 || index < 0) {
			return null;
		} else {
			int x = index / 8;
			int y = index % 8;
			return new int[] { x, y };
		}
	}
	
	public static int getY(int index) {
		return getXYFromIndex(index)[1];
	}
	
	public static String xyToString(int index) {
		if (index < 0 || index > 63) {
			return null;
		} else {
			int[] xy = getXYFromIndex(index);
			if (xy == null) {
				return null;
			}
			return xRepresentations.get(xy[0]) + yRepresentations.get(xy[1]);
		}
	}
	
	private static List<String> xRepresentations = Arrays.asList(new String[] { "a", "b", "c", "d",
			"e", "f", "g", "h" });
	
	private static List<String> yRepresentations = Arrays.asList(new String[] { "1", "2", "3", "4",
			"5", "6", "7", "8" });
	
	private XY() {
	}
}
