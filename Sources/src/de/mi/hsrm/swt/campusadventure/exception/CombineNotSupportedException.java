package de.mi.hsrm.swt.campusadventure.exception;


/**
 * Exception für eine nicht unterstüze Kombination von zwei Entity
 */
public class CombineNotSupportedException extends GameException {

	private static final long serialVersionUID = -3289650565158916170L;

	public CombineNotSupportedException(String msg) {
		super(msg);
	}

}
