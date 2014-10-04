package project.ilyagorban.model.figures;

import project.ilyagorban.model.Rank;

// ♛♕ figures
public class Queen extends Figure {

	public Queen(int p, Rank r) {
		super(p, r);
		setMoveDirections(moveDirectionsOfQueen);
		setKillDirections(moveDirectionsOfQueen);
		setMoveLen(8);
		setKillLen(8);
	}

}
