package project.ilyagorban.view;

import project.ilyagorban.model.figures.Figure;

public interface Visualizable {
	
	String getInput(String string);
	
	void getMessageToView(String string);
	
	void setMessage(String message);
	
	String showBoard(Figure[] board, String string);
	
}
