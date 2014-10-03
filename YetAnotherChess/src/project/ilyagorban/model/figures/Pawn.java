package project.ilyagorban.model.figures;

import static project.ilyagorban.model.ChessModel.*;

import project.ilyagorban.model.Owner;
import project.ilyagorban.model.Rank;
import project.ilyagorban.model.XY;

// ♙♟ figures
public class Pawn extends Figure {
	public Pawn(XY p, Rank r) {
		super(p, r);
		if (r.getOwner() == Owner.BLACK) {
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
	public int getSpecialCorrectMoveName(XY to) {
		if (to == null) {
			return INCORRECT_MOVE;
		}

		int enpassantY = (this.getRank().getOwner() == Owner.WHITE) ? 4 : 3;
		if (this.getXY().getY() == enpassantY) {
			return EN_PASSANT;
		}

		int promotionY = (this.getRank().getOwner() == Owner.WHITE) ? 7 : 0;
		if (to.getY() == promotionY) {
			return PAWN_PROMOTION;
		}

		return CORRECT_MOVE;
	}

}
