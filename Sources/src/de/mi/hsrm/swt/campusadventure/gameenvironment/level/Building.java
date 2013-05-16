package de.mi.hsrm.swt.campusadventure.gameenvironment.level;

import java.io.Serializable;

/**
 * Gebäude in dem der Player sich befindet. Kennt das aktuelle Feld auf dem dieser steht.
 * Besitzt einen Mutationsfaktor, der sich auf den Player auswirkt, wenn er sich in diesem Gebäude befindet
 */
public class Building implements Serializable {

	private static final long serialVersionUID = 8429544082986683971L;
	private float mutationFactor;
	private String name;
	
	/**
	 * Erzeugt neues Building
	 * 
	 * @param name - Name des Building
	 * @param factor - Mutationsfaktor des Building
	 * */
	public Building(String name, float factor) {
		this.name = name;
		this.mutationFactor = factor;
	}
	
	public float getMutationFactor() {
		return mutationFactor;
	}
	
	public String getName() {
		return name;
	}
	
	public void setMutationFactor(float mutationFactor) {
		this.mutationFactor = mutationFactor;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public boolean equals(Object obj) {
		return (this.name.equals(((Building) obj).getName())) ? true : false;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
}