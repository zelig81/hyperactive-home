package project.ilyagorban.model.figures;

import project.ilyagorban.model.Rank;

// ♜♖
public class Rook extends Figure {

	public Rook(int p, Rank r) {
		super(p, r);
		setMoveDirections(moveDirectionsOfRook);
		setKillDirections(moveDirectionsOfRook);
		setMoveLen(8);
		setKillLen(8);
	}

}
