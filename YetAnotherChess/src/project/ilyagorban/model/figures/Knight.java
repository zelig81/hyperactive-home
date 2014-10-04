package project.ilyagorban.model.figures;

import project.ilyagorban.model.Rank;

// ♘♞ figures
public class Knight extends Figure {

	public Knight(int p, Rank r) {
		super(p, r);
		setMoveDirections(moveDirectionsOfKnight);
		setKillDirections(moveDirectionsOfKnight);
		setMoveLen(1);
		setKillLen(1);
	}

}
