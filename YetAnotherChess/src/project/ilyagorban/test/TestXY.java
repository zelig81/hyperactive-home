package project.ilyagorban.test;

import static org.junit.Assert.*;

import org.junit.Test;

import project.ilyagorban.model.XY;

public class TestXY {
	
	@Test
	public void testAddToIndex() {
		assertEquals("a1 + (1,1) == b2",
				XY.getIndexFromXY("b2"),
				XY.addToIndex(XY.getIndexFromXY("a1"), 1, 1));
		assertEquals("b2 + (3,0) == e2",
				XY.getIndexFromXY("e2"),
				XY.addToIndex(XY.getIndexFromXY("b2"), 3, 0));
		assertEquals("d7 + (1,2) == -1", -1, XY.addToIndex(XY.getIndexFromXY("d7"), 1, 2));
		assertEquals("d7 + (8,2) == -1", -1, XY.addToIndex(XY.getIndexFromXY("d7"), 2, 2));
	}
	
	@Test
	public void testGetDifferenceXY() {
		int[] dif = XY.getDifferenceXY(XY.getIndexFromXY("a1"), XY.getIndexFromXY("d5"));
		assertEquals("a1,d5 == 3,4", true, dif[0] == 3 && dif[1] == 4);
		dif = XY.getDifferenceXY(XY.getIndexFromXY("d5"), XY.getIndexFromXY("a1"));
		assertEquals("d5,a1 == -3,-4", true, dif[0] == -3 && dif[1] == -4);
		dif = XY.getDifferenceXY(XY.getIndexFromXY("e4"), XY.getIndexFromXY("d5"));
		assertEquals("e4,d5 == -1,1", true, dif[0] == -1 && dif[1] == 1);
	}
	
	@Test
	public void testGetIndexFromXYIntInt() {
		assertEquals("0/0 or a1 == 0", 0, XY.getIndexFromXY(0, 0));
		assertEquals("3/4 or d5 == 28", 28, XY.getIndexFromXY(3, 4));
		assertEquals("4/3 or e4 == 35", 35, XY.getIndexFromXY(4, 3));
		assertEquals("7/7 or h8 == 63", 63, XY.getIndexFromXY(7, 7));
		assertEquals("8/7 or incorrect == -1", -1, XY.getIndexFromXY(8, 7));
		assertEquals("7/8 or incorrect == -1", -1, XY.getIndexFromXY(7, 8));
		assertEquals("7/-1 or incorrect == -1", -1, XY.getIndexFromXY(7, -1));
		
	}
	
	@Test
	public void testGetIndexFromXYString() {
		assertEquals("0/0 or a1 == 0", 0, XY.getIndexFromXY("a1"));
		assertEquals("3/4 or d5 == 28", 28, XY.getIndexFromXY("d5"));
		assertEquals("4/3 or e4 == 35", 35, XY.getIndexFromXY("e4"));
		assertEquals("7/7 or h8 == 63", 63, XY.getIndexFromXY("h8"));
		assertEquals("m8 == -1", -1, XY.getIndexFromXY("m8"));
		assertEquals("a9 == -1", -1, XY.getIndexFromXY("a9"));
	}
	
	@Test
	public void testGetIndexFromXYStringString() {
		assertEquals("0/0 or a1 == 0", 0, XY.getIndexFromXY("a", "1"));
		assertEquals("3/4 or d5 == 28", 28, XY.getIndexFromXY("d", "5"));
		assertEquals("4/3 or e4 == 35", 35, XY.getIndexFromXY("e", "4"));
		assertEquals("7/7 or h8 == 63", 63, XY.getIndexFromXY("h", "8"));
	}
	
	@Test
	public void testGetIndicesfromInput() {
		int[] in = XY.getIndicesfromInput("e2e4");
		assertEquals("e2e4 == e2, e4", true, in[0] == 33 && in[1] == 35);
		in = XY.getIndicesfromInput("e2e41");
		assertEquals("e2e41 == null", null, in);
		in = XY.getIndicesfromInput("e2e9");
		assertEquals("e2e9 == null", null, in);
		in = XY.getIndicesfromInput("e9e3");
		assertEquals("e9e3 == null", null, in);
		in = XY.getIndicesfromInput("m2e3");
		assertEquals("m2e3 == null", null, in);
	}
	
	@Test
	public void testGetX() {
		assertEquals("0/0 or a1 == 0", 0, XY.getX(XY.getIndexFromXY(0, 0)));
		assertEquals("3/4 or d5 == 4", 3, XY.getX(XY.getIndexFromXY(3, 4)));
		assertEquals("4/3 or e4 == 3", 4, XY.getX(XY.getIndexFromXY(4, 3)));
		assertEquals("7/7 or h8 == 7", 7, XY.getX(XY.getIndexFromXY(7, 7)));
	}
	
	@Test
	public void testGetXYFromIndex() {
		int[] xy = XY.getXYFromIndex(0);
		assertEquals("0/0 or a1 == 0", true, xy[0] == 0 && xy[1] == 0);
		xy = XY.getXYFromIndex(28);
		assertEquals("3/4 or d5 == 28", true, xy[0] == 3 && xy[1] == 4);
		xy = XY.getXYFromIndex(35);
		assertEquals("4/3 or e4 == 35", true, xy[0] == 4 && xy[1] == 3);
		xy = XY.getXYFromIndex(63);
		assertEquals("7/7 or h8 == 63", true, xy[0] == 7 && xy[1] == 7);
	}
	
	@Test
	public void testGetY() {
		assertEquals("0/0 or a1 == 0", 0, XY.getY(XY.getIndexFromXY(0, 0)));
		assertEquals("3/4 or d5 == 4", 4, XY.getY(XY.getIndexFromXY(3, 4)));
		assertEquals("4/3 or e4 == 3", 3, XY.getY(XY.getIndexFromXY(4, 3)));
		assertEquals("7/7 or h8 == 7", 7, XY.getY(XY.getIndexFromXY(7, 7)));
	}
	
	@Test
	public void testToLog() {
		assertEquals("0/0 == a1", "a1", XY.toLog(XY.getIndexFromXY(0, 0)));
		assertEquals("3/4 == d5", "d5", XY.toLog(XY.getIndexFromXY(3, 4)));
		assertEquals("4/3 == e4", "e4", XY.toLog(XY.getIndexFromXY(4, 3)));
		assertEquals("7/7 == h8", "h8", XY.toLog(XY.getIndexFromXY(7, 7)));
		
	}
	
}
