package project.ilyagorban.model.figures;

import project.ilyagorban.model.Rank;

// ♗♝figures
public class Bishop extends Figure implements MarkerBishop {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8943852294700306607L;
	
	public Bishop(int p, Rank r) {
		super(p, r);
		this.setKillLen(8);
	}
	
}
