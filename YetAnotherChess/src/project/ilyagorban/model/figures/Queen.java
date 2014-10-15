package project.ilyagorban.model.figures;

import project.ilyagorban.model.Rank;

// ♛♕ figures
public class Queen extends Figure implements MarkerRook, MarkerBishop {

	public Queen(int p, Rank r) {
		super(p, r);
		this.setKillLen(8);
	}

}
