package project.ilyagorban;

import project.ilyagorban.model.XYconverter;

public class MyTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(XYconverter.xyToIndex(0, 0));
		System.out.println(XYconverter.xyToIndex(7, 7));
		int[] xy;
		xy = XYconverter.indexToXY(0);
		System.out.println(xy[0] + "/" + xy[1]);
		xy = XYconverter.indexToXY(3);
		System.out.println(xy[0] + "/" + xy[1]);
		xy = XYconverter.indexToXY(63);
		System.out.println(xy[0] + "/" + xy[1]);

	}

}
