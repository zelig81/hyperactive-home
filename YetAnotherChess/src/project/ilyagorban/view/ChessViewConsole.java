package project.ilyagorban.view;

import java.util.Scanner;

import project.ilyagorban.model.XY;
import project.ilyagorban.model.figures.Figure;

public class ChessViewConsole implements Visualizable {
	private final Scanner sc = new Scanner(System.in);
	private String message;

	@Override
	public String getInput(String string) {
		System.out.println(string);
		String result = sc.nextLine();
		return result;
	}

	@Override
	public void getMessageToView(String string) {
		System.out.println(string);

	}

	@Override
	public void setMessage(String message) {
		this.message = message;

	}

	// 254B cross
	// 2501 horizontal
	// 2503 vertical
	@Override
	public String showBoard(Figure[] figures, boolean currentOwner) {
		System.out.println("\n\n\n\n\n\n\n\n\n\n");
		if (message != null) {
			System.out.println(message);
			message = null;
		}
		System.out.println("░|░A░| ░B░| ░C░| ░D░|░E░| ░F░| ░G░| ░H░|░");
		// 8|▓♜▓|░♞░|▓♝▓|░♛░|▓♚▓|░♝░|▓♞▓|░♜░|
		for (int y = 7; y >= 0; y--) {
			StringBuilder output = (new StringBuilder(String.valueOf(y + 1)))
					.append("|");
			for (int x = 0; x <= 7; x++) {
				String color = ((x + y) % 2 == 1) ? "▓" : "░";
				Figure fig = figures[XY.getIndexFromXY(x, y)];
				if (fig != null) {
					output.append(color).append(fig).append(color).append("|");
				} else {
					output.append(color).append(color).append(color)
							.append("|");
				}
			}
			output.append(y + 1);
			System.out.println(output);
		}
		System.out.println("░|░A░| ░B░| ░C░| ░D░|░E░| ░F░| ░G░| ░H░|░");
		System.out
				.println(currentOwner
						+ " your move (for example [e2e4]) or enter [exit] to quit the game:");
		String input = sc.nextLine();
		return input;
	}

}
