package project.ilyagorban.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import project.ilyagorban.model.figures.*;

public class Board {
	private static final ArrayList<String>	startGamePositions	= new ArrayList<>();

	static {
		startGamePositions.add("wra1");
		startGamePositions.add("wnb1");
		startGamePositions.add("wbc1");
		startGamePositions.add("wqd1");
		startGamePositions.add("wke1");
		startGamePositions.add("wbf1");
		startGamePositions.add("wng1");
		startGamePositions.add("wrh1");
		startGamePositions.add("wpa2");
		startGamePositions.add("wpb2");
		startGamePositions.add("wpc2");
		startGamePositions.add("wpd2");
		startGamePositions.add("wpe2");
		startGamePositions.add("wpf2");
		startGamePositions.add("wpg2");
		startGamePositions.add("wph2");

		startGamePositions.add("bra8");
		startGamePositions.add("bnb8");
		startGamePositions.add("bbc8");
		startGamePositions.add("bqd8");
		startGamePositions.add("bke8");
		startGamePositions.add("bbf8");
		startGamePositions.add("bng8");
		startGamePositions.add("brh8");
		startGamePositions.add("bpa7");
		startGamePositions.add("bpb7");
		startGamePositions.add("bpc7");
		startGamePositions.add("bpd7");
		startGamePositions.add("bpe7");
		startGamePositions.add("bpf7");
		startGamePositions.add("bpg7");
		startGamePositions.add("bph7");
	}

	public static boolean initializeGame(Figure[] board,
			HashMap<Boolean, HashSet<Figure>> hmFigures,
			HashMap<Boolean, Figure> kings) {
		if (board == null || hmFigures == null || kings == null) {
			return false;
		}
		for (String position : startGamePositions) {
			Figure fig = Figures.newInstance(position);
			if (fig == null) {
				return false;
			}
			board[fig.getXY()] = fig;
			boolean color = fig.getRank().getOwner();
			hmFigures.get(color).add(fig);
			if (fig instanceof King) {
				if (kings.get(color) != null) {
					return false;
				}
				kings.put(color, fig);
			}
		}
		if (kings.size() != 2) {
			return false;
		}
		return true;
	}
}
