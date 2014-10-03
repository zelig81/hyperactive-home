package project.ilyagorban.test;

import static org.junit.Assert.assertEquals;

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

	ChessViewTest cvt;
	Board b;
	Figure fig;

	ArrayList<XY> pm;

	@Before
	public void setUp() throws Exception {
		prepareGame("test");
		cvt = (ChessViewTest) Main.cv.getView();
	}

	@After
	public void tearDown() throws Exception {
		cvt = null;
		b = null;
		fig = null;
		pm = null;
	}

	@Test
	public void testBishop() throws Exception {
		setUp();
		cvt.addMove("e2e4");
		cvt.addMove("exit");
		cc.start();
		b = Main.cm.getBoardObject();
		fig = b.getFigure("f1");
		assertEquals(
				"bishop at [" + fig.getXY()
						+ "] should be able to move for 5 squares. its get "
						+ b.getPossibleMoves(fig), b.getPossibleMoves(fig)
						.size(), 5);
		tearDown();

		setUp();
		cvt.addMove("e2e4");
		cvt.addMove("e7e5");
		cvt.addMove("f1e2");
		cvt.addMove("exit");
		cc.start();
		b = Main.cm.getBoardObject();
		fig = b.getFigure("e2");
		assertEquals(
				"bishop at [" + fig.getXY()
						+ "] should be able to move for 8 squares. its get "
						+ b.getPossibleMoves(fig), b.getPossibleMoves(fig)
						.size(), 8);
		tearDown();

	}

	@Test
	public void testKnight() throws Exception {
		setUp();
		cvt.addMove("e2e4");
		cvt.addMove("exit");
		cc.start();
		b = Main.cm.getBoardObject();
		fig = b.getFigure("g1");
		assertEquals(
				fig.getRank() + " at [" + fig.getXY()
						+ "] should be able to move for 3 squares. its get "
						+ b.getPossibleMoves(fig), b.getPossibleMoves(fig)
						.size(), 3);

		fig = b.getFigure("b1");
		assertEquals(
				fig.getRank() + " at [" + fig.getXY()
						+ "] should be able to move for 2 squares. its get "
						+ b.getPossibleMoves(fig), b.getPossibleMoves(fig)
						.size(), 2);
		tearDown();

		setUp();
		cvt.addMove("e2e4");
		cvt.addMove("b8c6");
		cvt.addMove("exit");
		cc.start();
		b = Main.cm.getBoardObject();
		fig = b.getFigure("c6");
		ArrayList<XY> pm = b.getPossibleMoves(fig);
		assertEquals(fig.getRank() + " at [" + fig.getXY()
				+ "] should be able to move for 5 squares. its get " + pm,
				pm.size(), 5);
		tearDown();
	}

	@Test
	public void testPawn() throws Exception {
		setUp();
		cvt.addMove("a2a4");
		cvt.addMove("a7a5");
		cvt.addMove("h2h4");
		cvt.addMove("g7g5");
		cvt.addMove("b2b3");
		cvt.addMove("g8h6");
		cvt.addMove("c2c4");
		cvt.addMove("b7b6");
		cvt.addMove("c4c5");
		cvt.addMove("d7d5");
		cvt.addMove("exit");
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
		b = Main.cm.getBoardObject();
		fig = b.getFigure("a4");
		pm = b.getPossibleMoves(fig);
		assertEquals(fig.getRank() + " at [" + fig.getXY()
				+ "] should be able to move for 0 squares. its get " + pm,
				pm.size(), 0);

		fig = b.getFigure("h4");
		pm = b.getPossibleMoves(fig);
		assertEquals("pawn at [" + fig.getXY()
				+ "] should be able to move for 2 squares. its get " + pm,
				pm.size(), 2);

		fig = b.getFigure("e7");
		pm = b.getPossibleMoves(fig);
		assertEquals(fig.getRank() + " at [" + fig.getXY()
				+ "] should be able to move for 2 squares. its get " + pm,
				pm.size(), 2);

		fig = b.getFigure("b3");
		pm = b.getPossibleMoves(fig);
		assertEquals(fig.getRank() + " at [" + fig.getXY()
				+ "] should be able to move for 1 squares. its get " + pm,
				pm.size(), 1);

		fig = b.getFigure("c5"); // en passante
		pm = b.getPossibleMoves(fig);
		assertEquals(fig.getRank() + " at [" + fig.getXY()
				+ "] should be able to move for 3 squares. its get " + pm,
				pm.size(), 3);

		tearDown();

	}

	@Test
	public void testRook() throws Exception {
		setUp();
		cvt.addMove("a2a4");
		cvt.addMove("a7a6");
		cvt.addMove("h2h4");
		cvt.addMove("h7h5");
		cvt.addMove("a1a3");
		cvt.addMove("a8a7");
		cvt.addMove("h1h2");
		cvt.addMove("h8h7");
		cvt.addMove("exit");
		cc.start();
		b = Main.cm.getBoardObject();
		fig = b.getFigure("a3");
		pm = b.getPossibleMoves(fig);
		assertEquals(fig.getRank() + " at [" + fig.getXY()
				+ "] should be able to move for 2+7 squares. its get " + pm,
				pm.size(), 9);

		fig = b.getFigure("a7");
		pm = b.getPossibleMoves(fig);
		assertEquals(fig.getRank() + " at [" + fig.getXY()
				+ "] should be able to move for 1 squares. its get " + pm,
				pm.size(), 1);

		fig = b.getFigure("h2");
		pm = b.getPossibleMoves(fig);
		assertEquals(fig.getRank() + " at [" + fig.getXY()
				+ "] should be able to move for 2 squares. its get " + pm,
				pm.size(), 2);

		fig = b.getFigure("h7");
		pm = b.getPossibleMoves(fig);
		assertEquals(fig.getRank() + " at [" + fig.getXY()
				+ "] should be able to move for 2 squares. its get " + pm,
				pm.size(), 2);

		tearDown();

	}

	@Test
	public void testQueen() throws Exception {
		setUp();
		cvt.addMove("e2e4");
		cvt.addMove("c7c5");
		cvt.addMove("d1f3");
		cvt.addMove("d8b6");
		cvt.addMove("exit");
		cc.start();
		b = Main.cm.getBoardObject();
		fig = b.getFigure("f3");
		pm = b.getPossibleMoves(fig);
		assertEquals(fig.getRank() + " at [" + fig.getXY()
				+ "] should be able to move for 15 squares. its get " + pm,
				pm.size(), 15);

		fig = b.getFigure("b6");
		pm = b.getPossibleMoves(fig);
		assertEquals(fig.getRank() + " at [" + fig.getXY()
				+ "] should be able to move for 14 squares. its get " + pm,
				pm.size(), 14);
		tearDown();

	}

	@Test
	public void testKing() throws Exception {
		setUp();
		cvt.addMove("e2e4");
		cvt.addMove("e7e5");
		cvt.addMove("f1a6");
		cvt.addMove("b7a6");
		cvt.addMove("g1f3");
		cvt.addMove("c8b7");
		cvt.addMove("exit");
		cc.start();
		b = Main.cm.getBoardObject();
		fig = b.getFigure("e8");
		pm = b.getPossibleMoves(fig);
		assertEquals(fig.getRank() + " at [" + fig.getXY()
				+ "] should be able to move for 1 squares. its get " + pm,
				pm.size(), 1);

		fig = b.getFigure("e1");
		pm = b.getPossibleMoves(fig);
		assertEquals(fig.getRank() + " at [" + fig.getXY()
				+ "] should be able to move for 3 squares. its get " + pm,
				pm.size(), 3);
		tearDown();

		setUp();
		cvt.addMove("e2e4");
		cvt.addMove("e7e5");
		cvt.addMove("f1a6");
		cvt.addMove("b7a6");
		cvt.addMove("g1f3");
		cvt.addMove("d8h4");
		cvt.addMove("d1e2");
		cvt.addMove("b8c6");
		cvt.addMove("b1c3");
		cvt.addMove("h4f2");
		cvt.addMove("exit");
		// ░|░A░| ░B░| ░C░| ░D░|░E░| ░F░| ░G░| ░H░|░
		// 8|▓♜▓|░░░|▓♝▓|░░░|▓♚▓|░♝░|▓♞▓|░♜░|8
		// 7|░♟░|▓▓▓|░♟░|▓♟▓|░░░|▓♟▓|░♟░|▓♟▓|7
		// 6|▓♟▓|░░░|▓♞▓|░░░|▓▓▓|░░░|▓▓▓|░░░|6
		// 5|░░░|▓▓▓|░░░|▓▓▓|░♟░|▓▓▓|░░░|▓▓▓|5
		// 4|▓▓▓|░░░|▓▓▓|░░░|▓♙▓|░░░|▓▓▓|░░░|4
		// 3|░░░|▓▓▓|░♘░|▓▓▓|░░░|▓♘▓|░░░|▓▓▓|3
		// 2|▓♙▓|░♙░|▓♙▓|░♙░|▓♕▓|░♛░|▓♙▓|░♙░|2
		// 1|░♖░|▓▓▓|░♗░|▓▓▓|░♔░|▓▓▓|░░░|▓♖▓|1
		// ░|░A░| ░B░| ░C░| ░D░|░E░| ░F░| ░G░| ░H░|░
		// WHITE your move (for example [e2e4]) or enter [exit] to quit the
		// game:
		// without check for check
		cc.start();
		b = Main.cm.getBoardObject();
		fig = b.getFigure("e8");
		pm = b.getPossibleMoves(fig);
		assertEquals(fig.getRank() + " at [" + fig.getXY()
				+ "] should be able to move for 2 squares. its get " + pm,
				pm.size(), 2);

		fig = b.getFigure("e1");
		pm = b.getPossibleMoves(fig);
		assertEquals(
				fig.getRank()
						+ " at ["
						+ fig.getXY()
						+ "] should be able to move for 4 squares without check. its get "
						+ pm, pm.size(), 4);
		tearDown();
	}

}
