package project.ilyagorban.model.figures;

import project.ilyagorban.model.Rank;
import project.ilyagorban.model.XY;
import static project.ilyagorban.model.ChessModel.*;

// ♚♔ figures
public class King extends Figure {

	public King(XY p, Rank r) {
		super(p, r);
		setMoveDirections(moveDirectionsOfQueen);
		setKillDirections(moveDirectionsOfQueen);
		setMoveLen(1);
		setKillLen(1);
	}

	@Override
	public int getSpecialCorrectMoveName(XY to) {
		int output = INCORRECT_MOVE;
		if (to == null) {
			return INCORRECT_MOVE;
		}

		boolean isAbleToCastle = false;
		if (this.isTouched() == false) {
			isAbleToCastle = (Math.abs(this.getXY().getX() - to.getX()) == 2);
		}
		output = (isAbleToCastle == true) ? CASTLING : CORRECT_MOVE;
		return output;
	}

}
