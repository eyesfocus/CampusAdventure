package de.mi.hsrm.swt.campusadventure.gameenvironment.entity;

/**
 * Stellt die Methode zur Verfügung um ein Entity ansprechbar zu machen.
 */
public interface Addressable {

	/**
	 * Dient zur Kommunikation zwischen dem Player und den Entity Instanzen.
	 * @return Dialog
	 */
	public String showDialog();
}