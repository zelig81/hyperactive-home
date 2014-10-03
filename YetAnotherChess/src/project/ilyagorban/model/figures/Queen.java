package project.ilyagorban.model.figures;

import project.ilyagorban.model.Rank;
import project.ilyagorban.model.XY;

// ♛♕ figures
public class Queen extends Figure {

	public Queen(XY p, Rank r) {
		super(p, r);
		setMoveDirections(moveDirectionsOfQueen);
		setKillDirections(moveDirectionsOfQueen);
		setMoveLen(8);
		setKillLen(8);
	}

}
