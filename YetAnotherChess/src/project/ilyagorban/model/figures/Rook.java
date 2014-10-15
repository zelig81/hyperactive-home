package project.ilyagorban.model.figures;

import project.ilyagorban.model.Rank;

// ♜♖
public class Rook extends Figure implements MarkerRook {
	
	public Rook(int p, Rank r) {
		super(p, r);
		this.setKillLen(8);
	}
	
}
