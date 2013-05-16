package de.mi.hsrm.swt.campusadventure.exception;

/**
 * Vorlage für alle Exceptions die abgefangen werden müssen.
 */
public class GameException extends Exception {

	private static final long serialVersionUID = -8314852358127340767L;
	
	public GameException(String msg) {
		super(msg);
	}
}
