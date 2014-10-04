package project.ilyagorban;

import project.ilyagorban.model.ConvXY;

public class MyTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(ConvXY.getIndexFromXY(0, 0));
		System.out.println(ConvXY.getIndexFromXY(7, 7));
		int[] xy;
		xy = ConvXY.getXYFromIndex(0);
		System.out.println(xy[0] + "/" + xy[1]);
		xy = ConvXY.getXYFromIndex(3);
		System.out.println(xy[0] + "/" + xy[1]);
		xy = ConvXY.getXYFromIndex(63);
		System.out.println(xy[0] + "/" + xy[1]);

	}

}
