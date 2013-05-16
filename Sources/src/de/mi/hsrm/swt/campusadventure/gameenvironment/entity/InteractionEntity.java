package de.mi.hsrm.swt.campusadventure.gameenvironment.entity;

import java.io.Serializable;
import java.util.List;

import de.mi.hsrm.swt.campusadventure.exception.CombineNotSupportedException;
import de.mi.hsrm.swt.campusadventure.repository.Option;

/**
 * Ein Entity, das mit andern kombiniert werden kann.
 * Beinhaltet eine Interaction.
 * Kann nach dem Ausführen einer Interaction verschwinden.
 * */
public abstract class InteractionEntity extends Entity implements Serializable {
	private static final long serialVersionUID = 6639421643310889088L;
	
	protected Interaction interactions;
	protected boolean disappearsAfterCombining;
	
	/**
	 * Erzeugt neues InteractionEntity
	 * 
	 * @param name - Name des Entity
	 * @param description - String, der bei der Inspektion durch den Player angezeigt wird
	 * @param options - Liste mit Optionen (vgl Option), die das Entity hat
	 * @param imagePath - Pfad zur zugehörigen Bilddatei.
	 * @param interactions - Interaktionen des InteractionEntity
	 * @param disappearsAfterCombining - Flag, das aufzeigt ob das InteractionEntity nach kombinieren verschwindet
	 * */
	public InteractionEntity(String name, String description, List<Option> options, String imagePath, Interaction interactions, boolean disappearsAfterCombining) {
		super(name, description, options, imagePath);
		this.interactions = interactions;
		this.disappearsAfterCombining = disappearsAfterCombining;
	}
	
	/**
	 * Überprüft, ob mit dem Entity item kombiniert werden kann
	 * @param item Entity mit dem kombiniert werden soll
	 * @return true wenn ja, false sonst
	 * */
	public boolean interactsWith(Entity item) {
		return interactions.contains(item);
	}
	
	/**
	 * Fügt dem Entity eine neue Kombinationsmöglichkeit hinzu
	 * @param item Kombination mit Entity
	 * @param result Resultat der Kombination
	 */
	public void addInteraction(Entity item, Entity result) {
		this.interactions.put(item, result);
	}
	
	/**
	 * Fügt dem Entity eine neue Kombinationsmöglichkeit hinzu
	 * @param item Kombination mit Entity
	 * @param result Resultate der Kombination
	 */
	public void addInteraction(Entity item, List<Entity> result) {
		this.interactions.put(item, result);
	}

	/**
	 * Fügt dem Entity eine neue Kombinationsmöglichkeit hinzu
	 * @param item Kombination mit Entity
	 */
	public void addInteraction(Entity item) {
		this.interactions.put(item);
	}
	
	/**
	 * kombiniert das Entity mit einem anderen
	 * @param e InteractionEntity mit dem kombiniert werden soll
	 * @return Ergebnis der Interaction
	 * */
	public abstract List<Entity> combine(InteractionEntity e) throws CombineNotSupportedException;
	
	// GETTER / SETTER
	public Interaction getInteractions() {
		return interactions;
	}

	public boolean disappearsAfterCombining() {
		return disappearsAfterCombining;
	}

	public void setInteractions(Interaction interactions) {
		this.interactions = interactions;
	}
	
	public void setDisappearsAfterCombining(boolean disappearsAfterCombining) {
		this.disappearsAfterCombining = disappearsAfterCombining;
	}
	
	@Override
	public abstract InteractionEntity clone() throws CloneNotSupportedException;
	
	@Override
	public boolean equals(Object other) {
		return super.equals(other);
	}
}