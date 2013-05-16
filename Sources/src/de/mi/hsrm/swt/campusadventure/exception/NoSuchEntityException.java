package de.mi.hsrm.swt.campusadventure.exception;

/**
 * Eine Exception, die geworfen wird, falls ein gesuchtes Entity 
 * nicht existiert (nicht in der Datenhaltung erstellt wurde).
 */
public class NoSuchEntityException extends GameRuntimeException {

	private static final long serialVersionUID = -7984029752854821883L;

	public NoSuchEntityException(String msg) {
		super(msg);
	}
}
