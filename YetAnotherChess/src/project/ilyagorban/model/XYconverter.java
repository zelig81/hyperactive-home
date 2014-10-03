package project.ilyagorban.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class XYconverter {
	private static List<String> xRepresentations = Arrays.asList(new String[] {
			"a", "b", "c", "d", "e", "f", "g", "h" });
	private static List<String> yRepresentations = Arrays.asList(new String[] {
			"1", "2", "3", "4", "5", "6", "7", "8" });

	private XYconverter() {
	}

	public static int xyToIndex(int x, int y) {
		if (x > 7 || x < 0 || y > 7 || y < 0)
			return -1;
		else
			return x * 8 + y;
	}

	public static int[] indexToXY(int index) {
		if (index > 63 || index < 0)
			return null;
		else {
			int x = index / 8;
			int y = index % 8;
			return new int[] { x, y };
		}
	}

	public static int xyToIndex(String x, String y) {
		if (x == null || y == null || x.length() != 1 || y.length() != 1)
			return -1;
		int iX = xRepresentations.indexOf(x);
		int iY = yRepresentations.indexOf(y);
		if (iX >= 0 && iY >= 0) {
			return xyToIndex(iX, iY);
		}
		return -1;
	}
}
