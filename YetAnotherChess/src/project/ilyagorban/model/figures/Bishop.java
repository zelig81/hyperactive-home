package project.ilyagorban.model.figures;

import project.ilyagorban.model.Rank;

// ♗♝figures
public class Bishop extends Figure implements MarkerBishop {

	public Bishop(int p, Rank r) {
		super(p, r);
		this.setKillLen(8);
	}

}
