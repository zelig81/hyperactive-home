package project.ilyagorban.model.figures;

import project.ilyagorban.model.XY;
import project.ilyagorban.model.Rank;
import static project.ilyagorban.model.ChessModel.*;

// ♚♔ figures
public class King extends Figure {

	public King(int p, Rank r) {
		super(p, r);
		setMoveDirections(moveDirectionsOfQueen);
		setKillDirections(moveDirectionsOfQueen);
		setMoveLen(1);
		setKillLen(1);
	}

	@Override
	public int getSpecialCorrectMoveName(int to) {
		int output = INCORRECT_MOVE;
		if (to < 0 || to > 63) {
			return INCORRECT_MOVE;
		}
		int from = this.getXY();

		boolean isAbleToCastle = false;
		if (this.isTouched() == false) {
			isAbleToCastle = (Math.abs(XY.getX(from) - XY.getX(to)) == 2);
		}
		output = (isAbleToCastle == true) ? CASTLING : CORRECT_MOVE;
		return output;
	}
}
