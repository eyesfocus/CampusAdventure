package de.mi.hsrm.swt.campusadventure.exception;

/**
 * Vorlage für alle Exceptions die nicht abgefangen werden müssen.
 */
public class GameRuntimeException extends RuntimeException {

	private static final long serialVersionUID = -8314852358127340767L;
	
	public GameRuntimeException(String msg) {
		super(msg);
	}
}
