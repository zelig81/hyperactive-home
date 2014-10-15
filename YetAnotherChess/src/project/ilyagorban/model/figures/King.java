package project.ilyagorban.model.figures;

import project.ilyagorban.model.XY;
import project.ilyagorban.model.Rank;
import static project.ilyagorban.model.ChessModel.*;

// ♚♔ figures
public class King extends Figure implements MarkerRook, MarkerBishop {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -85033431322851245L;
	
	public King(int p, Rank r) {
		super(p, r);
		this.setKillLen(1);
	}
	
	@Override
	public int checkIllegalMove(Figure[] board, int to, Figure f, int lf) {
		int output = super.checkIllegalMove(board, to, f, lf);
		if (output != INCORRECT_MOVE) {
			return output; // correct_move || obstacle_on_the_way ||
			// incorrect_input
		}
		
		int from = this.getXY();
		boolean isAbleToCastle = false;
		if (this.isTouched() == false) {
			int xFrom = XY.getX(from);
			int dx = XY.getX(to) - xFrom;
			if (Math.abs(dx) == 2) {
				int y = XY.getY(from);
				int rookX = dx < 0 ? 0 : 7;
				Figure rook = board[XY.getIndexFromXY(rookX, y)];
				if (rook.isTouched() == false) {
					for (int i = xFrom + dx / 2; i != rookX; i = i + dx / 2) {
						Figure temp = board[XY.getIndexFromXY(i, y)];
						if (temp != null) {
							return INCORRECT_MOVE;
						}
					}
					isAbleToCastle = true;
				}
			}
		}
		
		output = isAbleToCastle == true ? CASTLING : INCORRECT_MOVE;
		return output;
	}
}
