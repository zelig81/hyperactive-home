package project.ilyagorban.model.figures;

import project.ilyagorban.model.Rank;

// ♜♖
public class Rook extends Figure implements MarkerRook {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7463867846070959914L;
	
	public Rook(int moveCount, int p, Rank r) {
		super(moveCount, p, r);
		this.setKillLen(8);
	}
	
}
