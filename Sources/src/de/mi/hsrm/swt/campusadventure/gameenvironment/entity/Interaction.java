package de.mi.hsrm.swt.campusadventure.gameenvironment.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Eine Sammlung von Interaktionen der einzelnen InteractionEntity Instanzen untereinander.
 * Beinhaltet eine HashMap, die aufzeigt, mit welchem InteractionEntity das Entity kombiniert werden kann, das diese Interaction besitzt.
 * */
public class Interaction implements Serializable  {

	private static final long serialVersionUID = 7258125951144062402L;
	private Map<Entity, List<Entity>> interactions;
	
	/**
	 * Erzeugt ein Interaction Objekt mit einer neuen HashMap
	 * */
	public Interaction() {
		interactions = new HashMap<Entity, List<Entity>>();
	}
	
	public Interaction(Entity key, Entity value) {
		interactions = new HashMap<Entity, List<Entity>>();
		this.put(key, value);
	}
	
	/**
	 * Fügt der HashMap interactions eine neue Interaktion zu
	 * @param key - Entity mit dem kombiniert werden kann
	 * @param value - Ergebnis bei erfolgreicher Kombination
	 * */
	public void put(Entity key, Entity value) {
		List<Entity> list = new ArrayList<Entity>();
		list.add(value);
		interactions.put(key, list);
	}
	
	/**
	 * Fügt der HashMap interactions eine neue Interaktion zu
	 * @param key - Entity mit dem kombiniert werden kann
	 * @param value - Ergebnis bei erfolgreicher Kombination
	 * */
	public void put(Entity key, List<Entity> value) {
		interactions.put(key, value);
	}
	
	/**
	 * Fügt der HashMap interactions eine neue Interaktion zu
	 * @param key - Entity mit dem kombiniert werden kann
	 * */
	public void put(Entity key) {
		interactions.put(key, null);
	}
	
	/**
	 * Gibt das Ergebnis der Interaktion zurück
	 * @param key - Entity mit dem kombiniert wird
	 * @return Ergebnis der Interaction
	 * */
	public List<Entity> getInteractionResults(Entity key) {
		return interactions.get(key);
	}

	/**
	 * Überprüft, ob ein Entity in der HashMap interactions enthalten ist
	 * @return true wenn ja, false sonst
	 * 
	 * */
	public boolean contains(Entity item) {
		return interactions.containsKey(item);
	}
}
