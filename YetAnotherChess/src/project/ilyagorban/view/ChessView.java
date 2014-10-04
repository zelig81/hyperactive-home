package project.ilyagorban.view;

import project.ilyagorban.model.Owner;
import project.ilyagorban.model.figures.Figure;

public class ChessView implements Visualizable {

	private Visualizable view;

	public ChessView() {
		this("console");
	}

	public ChessView(String typeOfView) {
		if (typeOfView == null)
			throw new IllegalArgumentException(
					"Wrong argument for ChessView constructor");
		switch (typeOfView) {
		case "console":
			view = new ChessViewConsole();
			break;
		case "gui":
			view = new ChessViewWindow();
			break;
		case "test":
			view = new ChessViewTest();
			break;
		default:
			break;
		}
	}

	@Override
	public String getInput(String string) {
		return view.getInput(string);
	}

	@Override
	public void getMessageToView(String string) {
		view.getMessageToView(string);
	}

	@Override
	public void setMessage(String message) {
		view.setMessage(message);

	}

	@Override
	public String showBoard(Figure[] figures, boolean currentOwner) {
		return view.showBoard(figures, currentOwner);
	}

	public Visualizable getView() {
		return view;
	}

}
