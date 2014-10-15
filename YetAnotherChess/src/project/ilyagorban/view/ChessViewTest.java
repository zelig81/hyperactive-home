package project.ilyagorban.view;

import java.util.ArrayList;

import project.ilyagorban.model.figures.Figure;

public class ChessViewTest implements Visualizable {
	private ArrayList<String> moves = new ArrayList<>();
	
	private int move;
	
	public void addMove(String str) {
		this.moves.add(str);
	}
	
	@Override
	public String getInput(String string) {
		return null;
	}
	
	@Override
	public void getMessageToView(String string) {
		
	}
	
	public ArrayList<String> getMoves() {
		return this.moves;
	}
	
	public void resetMove() {
		this.move = 0;
	}
	
	public void setInput(ArrayList<String> input) {
		this.moves = input;
	}
	
	@Override
	public void setMessage(String message) {
		
	}
	
	@Override
	public String showBoard(Figure[] figures, boolean currentOwner) {
		String output = this.moves.get(this.move);
		this.move++;
		return output;
	}
}