package project.ilyagorban.model.figures;

import static project.ilyagorban.model.ChessModel.*;

import project.ilyagorban.model.ConvXY;
import project.ilyagorban.model.Rank;

// ♙♟ figures
public class Pawn extends Figure {
	public Pawn(int xy, Rank r) {
		super(xy, r);
		if (r.getOwner() == BLACK) {
			setMoveDirections(moveDirectionsOfBlackPawn);
			setKillDirections(killDirectionsOfBlackPawn);
		} else {
			setMoveDirections(moveDirectionsOfWhitePawn);
			setKillDirections(killDirectionsOfWhitePawn);
		}
		setMoveLen(2);
		setKillLen(1);
	}

	@Override
	public void setTouched(boolean touched) {
		super.setTouched(touched);
		setMoveLen(1);
	}

	@Override
	public int getSpecialCorrectMoveName(int to) {
		if (to < 0) {
			return INCORRECT_MOVE;
		}
		int from = this.getXY();
		int yFrom = ConvXY.getXYFromIndex(from)[1];
		int yTo = ConvXY.getXYFromIndex(to)[1];

		int enpassantY = (this.getRank().getOwner() == WHITE) ? 4 : 3;
		if (yFrom == enpassantY) {
			return EN_PASSANT;
		}

		int promotionY = (this.getRank().getOwner() == WHITE) ? 7 : 0;
		if (yTo == promotionY) {
			return PAWN_PROMOTION;
		}

		return CORRECT_MOVE;
	}
}
