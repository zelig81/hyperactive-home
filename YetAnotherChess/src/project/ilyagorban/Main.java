/**
 * 
 */
package project.ilyagorban;

import project.ilyagorban.controllers.ChessController;
import project.ilyagorban.model.ChessModel;
import project.ilyagorban.view.ChessView;

/**
 * @author Ilya Gorban
 * @category yet another chess
 * 
 * 
 */
public class Main {
	
	protected static ChessModel cm;
	protected static ChessView cv;
	protected static ChessController cc;
	
	public static void main(String[] args) {
		prepareGame("console");
		cc.start();
	}
	
	public static void prepareGame(String output) {
		cm = new ChessModel();
		cv = new ChessView(output);
		cc = new ChessController(cm, cv);
	}
	
}
