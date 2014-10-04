package project.ilyagorban.model.figures;

import project.ilyagorban.model.Rank;

// ♗♝figures
public class Bishop extends Figure {

	public Bishop(int p, Rank r) {
		super(p, r);
		setMoveDirections(moveDirectionsOfBishop);
		setKillDirections(moveDirectionsOfBishop);
		setMoveLen(8);
		setKillLen(8);
	}

}
