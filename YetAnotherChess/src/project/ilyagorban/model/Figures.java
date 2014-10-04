package project.ilyagorban.model;

import project.ilyagorban.model.figures.Bishop;
import project.ilyagorban.model.figures.Figure;
import project.ilyagorban.model.figures.King;
import project.ilyagorban.model.figures.Knight;
import project.ilyagorban.model.figures.Pawn;
import project.ilyagorban.model.figures.Queen;
import project.ilyagorban.model.figures.Rook;
import static project.ilyagorban.model.ChessModel.*;

public class Figures {
	public static Figure newInstance(String startGamePosition) {
		if (startGamePosition.length() != 4) {
			return null;
		}
		String[] aChar = startGamePosition.split("");
		int index = ConvXY.getIndexFromXY(aChar[2], aChar[3]);
		if (index == INCORRECT_INPUT) {
			return null;
		}
		boolean owner;
		switch (aChar[0].toLowerCase()) {
		case "w":
			owner = WHITE;
			break;
		case "b":
			owner = BLACK;
			break;
		default:
			return null;
		}

		Rank rank = Rank.getRank(aChar[1], owner);
		if (rank == null) {
			return null;
		}
		switch (aChar[1]) {
		case "p":
			return new Pawn(index, rank);
		case "n":
			return new Knight(index, rank);
		case "b":
			return new Bishop(index, rank);
		case "r":
			return new Rook(index, rank);
		case "q":
			return new Queen(index, rank);
		case "k":
			return new King(index, rank);
		default:
			return null;
		}
	}

}
