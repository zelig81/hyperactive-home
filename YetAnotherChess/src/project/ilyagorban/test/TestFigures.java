package project.ilyagorban.test;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.ilyagorban.Main;
import project.ilyagorban.model.Board;
import project.ilyagorban.model.XY;
import project.ilyagorban.model.figures.Figure;
import project.ilyagorban.view.ChessViewTest;

public class TestFigures extends Main {
	public static void main(String[] args) {
		System.out.println(3.5 + 0.5 * (-1));
		System.out.println(3.5 + 0.5 * (1));
	}

	ChessViewTest	cvt;
	Board			b;
	Figure			fig;

	ArrayList<XY>	pm;

	@Before
	public void setUp() throws Exception {
		prepareGame("test");
		this.cvt = (ChessViewTest) Main.cv.getView();
	}

	@After
	public void tearDown() throws Exception {
		this.cvt = null;
		this.b = null;
		this.fig = null;
		this.pm = null;
	}

	@Test
	public void testBishop() throws Exception {
		this.setUp();
		this.cvt.addMove("e2e4");
		this.cvt.addMove("exit");
		cc.start();
		// b = Main.cm.getBoardObject();
		// fig = b.getFigure("f1");
		// assertEquals(
		// "bishop at [" + fig.getXY()
		// + "] should be able to move for 5 squares. its get "
		// + b.getPossibleMoves(fig), b.getPossibleMoves(fig)
		// .size(), 5);
		// tearDown();
		//
		// setUp();
		// cvt.addMove("e2e4");
		// cvt.addMove("e7e5");
		// cvt.addMove("f1e2");
		// cvt.addMove("exit");
		// cc.start();
		// b = Main.cm.getBoardObject();
		// fig = b.getFigure("e2");
		// assertEquals(
		// "bishop at [" + fig.getXY()
		// + "] should be able to move for 8 squares. its get "
		// + b.getPossibleMoves(fig), b.getPossibleMoves(fig)
		// .size(), 8);
		this.tearDown();

	}

	@Test
	public void testKing() throws Exception {
		this.setUp();
		this.cvt.addMove("e2e4");
		this.cvt.addMove("e7e5");
		this.cvt.addMove("f1a6");
		this.cvt.addMove("b7a6");
		this.cvt.addMove("g1f3");
		this.cvt.addMove("c8b7");
		this.cvt.addMove("exit");
		cc.start();
		// b = Main.cm.getBoardObject();
		// fig = b.getFigure("e8");
		// pm = b.getPossibleMoves(fig);
		// assertEquals(fig.getRank() + " at [" + fig.getXY()
		// + "] should be able to move for 1 squares. its get " + pm,
		// pm.size(), 1);
		//
		// fig = b.getFigure("e1");
		// pm = b.getPossibleMoves(fig);
		// assertEquals(fig.getRank() + " at [" + fig.getXY()
		// + "] should be able to move for 3 squares. its get " + pm,
		// pm.size(), 3);
		this.tearDown();

		this.setUp();
		this.cvt.addMove("e2e4");
		this.cvt.addMove("e7e5");
		this.cvt.addMove("f1a6");
		this.cvt.addMove("b7a6");
		this.cvt.addMove("g1f3");
		this.cvt.addMove("d8h4");
		this.cvt.addMove("d1e2");
		this.cvt.addMove("b8c6");
		this.cvt.addMove("b1c3");
		this.cvt.addMove("h4f2");
		this.cvt.addMove("exit");
		// // ░|░A░| ░B░| ░C░| ░D░|░E░| ░F░| ░G░| ░H░|░
		// // 8|▓♜▓|░░░|▓♝▓|░░░|▓♚▓|░♝░|▓♞▓|░♜░|8
		// // 7|░♟░|▓▓▓|░♟░|▓♟▓|░░░|▓♟▓|░♟░|▓♟▓|7
		// // 6|▓♟▓|░░░|▓♞▓|░░░|▓▓▓|░░░|▓▓▓|░░░|6
		// // 5|░░░|▓▓▓|░░░|▓▓▓|░♟░|▓▓▓|░░░|▓▓▓|5
		// // 4|▓▓▓|░░░|▓▓▓|░░░|▓♙▓|░░░|▓▓▓|░░░|4
		// // 3|░░░|▓▓▓|░♘░|▓▓▓|░░░|▓♘▓|░░░|▓▓▓|3
		// // 2|▓♙▓|░♙░|▓♙▓|░♙░|▓♕▓|░♛░|▓♙▓|░♙░|2
		// // 1|░♖░|▓▓▓|░♗░|▓▓▓|░♔░|▓▓▓|░░░|▓♖▓|1
		// // ░|░A░| ░B░| ░C░| ░D░|░E░| ░F░| ░G░| ░H░|░
		// // WHITE your move (for example [e2e4]) or enter [exit] to quit the
		// // game:
		// // without check for check
		// cc.start();
		// b = Main.cm.getBoardObject();
		// fig = b.getFigure("e8");
		// pm = b.getPossibleMoves(fig);
		// assertEquals(fig.getRank() + " at [" + fig.getXY()
		// + "] should be able to move for 2 squares. its get " + pm,
		// pm.size(), 2);
		//
		// fig = b.getFigure("e1");
		// pm = b.getPossibleMoves(fig);
		// assertEquals(
		// fig.getRank()
		// + " at ["
		// + fig.getXY()
		// + "] should be able to move for 4 squares without check. its get "
		// + pm, pm.size(), 4);
		this.tearDown();
	}

	@Test
	public void testKnight() throws Exception {
		this.setUp();
		this.cvt.addMove("e2e4");
		this.cvt.addMove("exit");
		cc.start();
		// b = Main.cm.getBoardObject();
		// fig = b.getFigure("g1");
		// assertEquals(
		// fig.getRank() + " at [" + fig.getXY()
		// + "] should be able to move for 3 squares. its get "
		// + b.getPossibleMoves(fig), b.getPossibleMoves(fig)
		// .size(), 3);
		//
		// fig = b.getFigure("b1");
		// assertEquals(
		// fig.getRank() + " at [" + fig.getXY()
		// + "] should be able to move for 2 squares. its get "
		// + b.getPossibleMoves(fig), b.getPossibleMoves(fig)
		// .size(), 2);
		this.tearDown();

		this.setUp();
		this.cvt.addMove("e2e4");
		this.cvt.addMove("b8c6");
		this.cvt.addMove("exit");
		cc.start();
		// b = Main.cm.getBoardObject();
		// fig = b.getFigure("c6");
		// ArrayList<XY> pm = b.getPossibleMoves(fig);
		// assertEquals(fig.getRank() + " at [" + fig.getXY()
		// + "] should be able to move for 5 squares. its get " + pm,
		// pm.size(), 5);
		this.tearDown();
	}

	@Test
	public void testPawn() throws Exception {
		this.setUp();
		this.cvt.addMove("a2a4");
		this.cvt.addMove("a7a5");
		this.cvt.addMove("h2h4");
		this.cvt.addMove("g7g5");
		this.cvt.addMove("b2b3");
		this.cvt.addMove("g8h6");
		this.cvt.addMove("c2c4");
		this.cvt.addMove("b7b6");
		this.cvt.addMove("c4c5");
		this.cvt.addMove("d7d5");
		this.cvt.addMove("exit");
		cc.start();
		// ░|░A░| ░B░| ░C░| ░D░|░E░| ░F░| ░G░| ░H░|░
		// 8|▓♜▓|░♞░|▓♝▓|░♛░|▓♚▓|░♝░|▓▓▓|░♜░|8
		// 7|░░░|▓▓▓|░♟░|▓▓▓|░♟░|▓♟▓|░░░|▓♟▓|7
		// 6|▓▓▓|░♟░|▓▓▓|░░░|▓▓▓|░░░|▓▓▓|░♞░|6
		// 5|░♟░|▓▓▓|░♙░|▓♟▓|░░░|▓▓▓|░♟░|▓▓▓|5
		// 4|▓♙▓|░░░|▓▓▓|░░░|▓▓▓|░░░|▓▓▓|░♙░|4
		// 3|░░░|▓♙▓|░░░|▓▓▓|░░░|▓▓▓|░░░|▓▓▓|3
		// 2|▓▓▓|░░░|▓▓▓|░♙░|▓♙▓|░♙░|▓♙▓|░░░|2
		// 1|░♖░|▓♘▓|░♗░|▓♕▓|░♔░|▓♗▓|░♘░|▓♖▓|1
		// ░|░A░| ░B░| ░C░| ░D░|░E░| ░F░| ░G░| ░H░|░
		// WHITE your move (for example [e2e4]) or enter [exit] to quit the
		// game:
		// b = Main.cm.getBoardObject();
		// fig = b.getFigure("a4");
		// pm = b.getPossibleMoves(fig);
		// assertEquals(fig.getRank() + " at [" + fig.getXY()
		// + "] should be able to move for 0 squares. its get " + pm,
		// pm.size(), 0);
		//
		// fig = b.getFigure("h4");
		// pm = b.getPossibleMoves(fig);
		// assertEquals("pawn at [" + fig.getXY()
		// + "] should be able to move for 2 squares. its get " + pm,
		// pm.size(), 2);
		//
		// fig = b.getFigure("e7");
		// pm = b.getPossibleMoves(fig);
		// assertEquals(fig.getRank() + " at [" + fig.getXY()
		// + "] should be able to move for 2 squares. its get " + pm,
		// pm.size(), 2);
		//
		// fig = b.getFigure("b3");
		// pm = b.getPossibleMoves(fig);
		// assertEquals(fig.getRank() + " at [" + fig.getXY()
		// + "] should be able to move for 1 squares. its get " + pm,
		// pm.size(), 1);
		//
		// fig = b.getFigure("c5"); // en passante
		// pm = b.getPossibleMoves(fig);
		// assertEquals(fig.getRank() + " at [" + fig.getXY()
		// + "] should be able to move for 3 squares. its get " + pm,
		// pm.size(), 3);
		//
		this.tearDown();

	}

	@Test
	public void testQueen() throws Exception {
		this.setUp();
		this.cvt.addMove("e2e4");
		this.cvt.addMove("c7c5");
		this.cvt.addMove("d1f3");
		this.cvt.addMove("d8b6");
		this.cvt.addMove("exit");
		cc.start();
		// b = Main.cm.getBoardObject();
		// fig = b.getFigure("f3");
		// pm = b.getPossibleMoves(fig);
		// assertEquals(fig.getRank() + " at [" + fig.getXY()
		// + "] should be able to move for 15 squares. its get " + pm,
		// pm.size(), 15);
		//
		// fig = b.getFigure("b6");
		// pm = b.getPossibleMoves(fig);
		// assertEquals(fig.getRank() + " at [" + fig.getXY()
		// + "] should be able to move for 14 squares. its get " + pm,
		// pm.size(), 14);
		this.tearDown();

	}

	@Test
	public void testRook() throws Exception {
		this.setUp();
		this.cvt.addMove("a2a4");
		this.cvt.addMove("a7a6");
		this.cvt.addMove("h2h4");
		this.cvt.addMove("h7h5");
		this.cvt.addMove("a1a3");
		this.cvt.addMove("a8a7");
		this.cvt.addMove("h1h2");
		this.cvt.addMove("h8h7");
		this.cvt.addMove("exit");
		cc.start();
		// b = Main.cm.getBoardObject();
		// fig = b.getFigure("a3");
		// pm = b.getPossibleMoves(fig);
		// assertEquals(fig.getRank() + " at [" + fig.getXY()
		// + "] should be able to move for 2+7 squares. its get " + pm,
		// pm.size(), 9);
		//
		// fig = b.getFigure("a7");
		// pm = b.getPossibleMoves(fig);
		// assertEquals(fig.getRank() + " at [" + fig.getXY()
		// + "] should be able to move for 1 squares. its get " + pm,
		// pm.size(), 1);
		//
		// fig = b.getFigure("h2");
		// pm = b.getPossibleMoves(fig);
		// assertEquals(fig.getRank() + " at [" + fig.getXY()
		// + "] should be able to move for 2 squares. its get " + pm,
		// pm.size(), 2);
		//
		// fig = b.getFigure("h7");
		// pm = b.getPossibleMoves(fig);
		// assertEquals(fig.getRank() + " at [" + fig.getXY()
		// + "] should be able to move for 2 squares. its get " + pm,
		// pm.size(), 2);
		//
		this.tearDown();

	}

}
