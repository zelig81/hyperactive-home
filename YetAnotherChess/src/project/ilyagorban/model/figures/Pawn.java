package project.ilyagorban.model.figures;

import static project.ilyagorban.model.ChessModel.*;

import project.ilyagorban.model.XY;
import project.ilyagorban.model.Rank;

// ♙♟ figures
public class Pawn extends Figure {
	public Pawn(int xy, Rank r) {
		super(xy, r);
	}

	@Override
	public int checkIllegalMove(Figure[] board, int to, Figure lastMoved, int lastFrom) {
		int from = getXY();
		if (to > 63 || to < 0 || from == to)
			return INCORRECT_INPUT;

		boolean owner = getRank().getOwner();
		int dirY = owner == WHITE ? 1 : -1;
		Figure figTo = board[to];
		int[] difXY = XY.getDifferenceXY(from, to);
		int output = INCORRECT_MOVE;

		if (difXY[0] == 0) {
			boolean isMovingOneSquare = difXY[1] * dirY == 1 && figTo == null;
			if (isMovingOneSquare == true)
				output = CORRECT_MOVE;

			boolean isMovingTwoSquare =
					output != CORRECT_MOVE && isTouched() == false && difXY[1] * dirY == 2 && figTo == null
							&& board[XY.addToIndex(this.getXY(), 0, dirY)] == null;
			if (isMovingTwoSquare)
				output = CORRECT_MOVE;
		}

		boolean isTaking = output != CORRECT_MOVE && difXY[1] * dirY * Math.abs(difXY[0]) == 1;
		if (isTaking) {
			boolean isAbleToTakeFigureRegular = figTo != null && figTo.isEnemy(this) == true;
			if (isAbleToTakeFigureRegular)
				output = CORRECT_MOVE;

			int rightEnpassantY = output != CORRECT_MOVE && owner == WHITE ? 4 : 3;
			int y = XY.getY(this.getXY());
			if (y == rightEnpassantY) {
				Figure victimOfEnpassant = board[XY.addToIndex(from, difXY[0], 0)];
				if (victimOfEnpassant != null && (XY.getY(lastFrom) == 1 || XY.getY(lastFrom) == 6)
						&& victimOfEnpassant.equals(lastMoved))
					return EN_PASSANT;
			}
		}

		if (output == CORRECT_MOVE) {
			int yTo = XY.getY(to);
			if (yTo == 0 || yTo == 7)
				output = PAWN_PROMOTION;
		}

		return output;
	}
}
