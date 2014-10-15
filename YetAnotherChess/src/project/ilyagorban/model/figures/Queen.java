package project.ilyagorban.model.figures;

import project.ilyagorban.model.Rank;

// ♛♕ figures
public class Queen extends Figure implements MarkerRook, MarkerBishop {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7510481219645793029L;
	
	public Queen(int p, Rank r) {
		super(p, r);
		this.setKillLen(8);
	}
	
}
