package project.ilyagorban.model.figures;

import project.ilyagorban.model.Rank;
import project.ilyagorban.model.XY;

// ♜♖
public class Rook extends Figure {

	public Rook(XY p, Rank r) {
		super(p, r);
		setMoveDirections(moveDirectionsOfRook);
		setKillDirections(moveDirectionsOfRook);
		setMoveLen(8);
		setKillLen(8);
	}

}
