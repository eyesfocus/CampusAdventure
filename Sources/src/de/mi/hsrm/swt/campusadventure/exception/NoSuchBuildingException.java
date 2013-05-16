package de.mi.hsrm.swt.campusadventure.exception;

/**
 * Eine Exception, die geworfen wird, falls ein gesuchtes Building 
 * nicht existiert (nicht in der Datenhaltung erstellt wurde).
 */
public class NoSuchBuildingException extends GameRuntimeException {
	
	private static final long serialVersionUID = 3401230912076864502L;

	public NoSuchBuildingException(String msg) {
		super(msg);
	}
}
