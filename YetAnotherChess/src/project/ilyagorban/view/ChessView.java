package project.ilyagorban.view;

import project.ilyagorban.model.figures.Figure;

public class ChessView implements Visualizable {
	
	private Visualizable view;
	
	public ChessView() {
		this("console");
	}
	
	public ChessView(String typeOfView) {
		if (typeOfView == null) {
			throw new IllegalArgumentException("Wrong argument for ChessView constructor");
		}
		switch (typeOfView) {
		case "console":
			this.view = new ChessViewConsole();
			break;
		case "gui":
			this.view = new ChessViewWindow();
			break;
		case "test":
			this.view = new ChessViewTest();
			break;
		default:
			break;
		}
	}
	
	@Override
	public String getInput(String string) {
		return this.view.getInput(string);
	}
	
	@Override
	public void getMessageToView(String string) {
		this.view.getMessageToView(string);
	}
	
	public Visualizable getView() {
		return this.view;
	}
	
	@Override
	public void setMessage(String message) {
		this.view.setMessage(message);
		
	}
	
	@Override
	public String showBoard(Figure[] figures, String currentOwner) {
		return this.view.showBoard(figures, currentOwner);
	}
	
}
