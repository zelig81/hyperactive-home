package project.ilyagorban.model.figures;

import static project.ilyagorban.model.ChessModel.CORRECT_MOVE;
import static project.ilyagorban.model.ChessModel.INCORRECT_INPUT;
import static project.ilyagorban.model.ChessModel.INCORRECT_MOVE;
import static project.ilyagorban.model.ChessModel.OBSTACLE_ON_THE_WAY;
import project.ilyagorban.model.Rank;
import project.ilyagorban.model.XY;

// ♘♞ figures
public class Knight extends Figure {

	public Knight(int p, Rank r) {
		super(p, r);
		setKillLen(1);
	}

	@Override
	public int checkIllegalMove(Figure[] board, int to, Figure f, boolean b) {
		int from = this.getXY();
		if (to > 63 || to < 0 || from == to)
			return INCORRECT_INPUT;
		Figure figTo = board[to];
		if (figTo != null && figTo.isEnemy(this) == false) {
			return OBSTACLE_ON_THE_WAY;
		}
		int[] difXY = XY.getDifferenceXY(from, to);
		int dx = difXY[0];
		int dy = difXY[1];
		int output = Math.abs(dx) * Math.abs(dy) == 2 ? CORRECT_MOVE
				: INCORRECT_MOVE;
		return output;
	}

}
