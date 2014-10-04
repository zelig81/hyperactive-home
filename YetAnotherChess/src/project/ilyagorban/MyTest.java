package project.ilyagorban;

import project.ilyagorban.model.XY;

public class MyTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(XY.getIndexFromXY(0, 0));
		System.out.println(XY.getIndexFromXY(7, 7));
		int[] xy;
		xy = XY.getXYFromIndex(0);
		System.out.println(xy[0] + "/" + xy[1]);
		xy = XY.getXYFromIndex(3);
		System.out.println(xy[0] + "/" + xy[1]);
		xy = XY.getXYFromIndex(63);
		System.out.println(xy[0] + "/" + xy[1]);

	}

}
