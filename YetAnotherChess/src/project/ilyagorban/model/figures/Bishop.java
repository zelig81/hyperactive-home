package project.ilyagorban.model.figures;

import project.ilyagorban.model.Rank;
import project.ilyagorban.model.XY;

// ♗♝figures
public class Bishop extends Figure {

	public Bishop(XY p, Rank r) {
		super(p, r);
		setMoveDirections(moveDirectionsOfBishop);
		setKillDirections(moveDirectionsOfBishop);
		setMoveLen(8);
		setKillLen(8);
	}

}
