package de.mi.hsrm.swt.campusadventure.gameenvironment.player;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.List;

import de.mi.hsrm.swt.campusadventure.exception.CombineNotSupportedException;
import de.mi.hsrm.swt.campusadventure.gameenvironment.entity.EnergyItem;
import de.mi.hsrm.swt.campusadventure.gameenvironment.entity.Entity;
import de.mi.hsrm.swt.campusadventure.gameenvironment.entity.InteractionEntity;
import de.mi.hsrm.swt.campusadventure.gameenvironment.entity.InventoryItem;
import de.mi.hsrm.swt.campusadventure.gameenvironment.entity.Person;
import de.mi.hsrm.swt.campusadventure.gameenvironment.entity.Robot;
import de.mi.hsrm.swt.campusadventure.gameenvironment.level.Field;
import de.mi.hsrm.swt.campusadventure.repository.Direction;

/**
 * Der Akteur in dem Spiel, kann sich bewegen, Entity Instanzen aus dem Inventory
 * entfernen / hinzufügen / benutzen, mit anderen Entity Instanzen kommunizieren
 */
public class Player implements Serializable {

	private static final long serialVersionUID = -3712541652276578252L;

	private PropertyChangeSupport changes;

	private Direction direction;
	private String name;
	private float mutationFactor;
	private final float MAX_MUTATION = 200;

	private float actMutation;
	private InteractionEntity activeEntity;
	private Inventory inventory;
	private Field actField;
	
	private int numDestroyedRobots;

	/**
	 * Erzeugt eine neue, "frische" Spielfigur (newGame() in SystemManager)
	 * 
	 * @param name - Name des Players
	 * @param direction - Blickrichtung (vgl Direction)
	 * @param actField - Field, auf dem der Player steht
	 */
	public Player(String name, Direction direction, Field actField) {
		this(name, direction, actField, new Inventory(), 0f);
	}

	/**
	 * Erzeugt einen Player mit definiertem Inventory und Mutationswert
	 * 
	 * @param name - Name der Spielfigur
	 * @param direction - Blickrichtung (vgl Direction)
	 * @param actField - Field, auf dem der Player steht
	 * @param inventory - Inventory des Player
	 * @param actMutation - Mutationsstand
	 */
	public Player(String name, Direction direction, Field actField,
			Inventory inventory, float actMutation) {
		this.changes = new PropertyChangeSupport(this);

		this.name = name;
		this.direction = direction;
		this.actField = actField;
		this.inventory = inventory;
		this.actMutation = actMutation;
		this.mutationFactor = 1;
		this.setNumDestroyedRobots(0);
	}

	/**
	 * Überprüft, ob die Mutation des Players das Mindestmaß überschritten
	 * hat
	 * 
	 * @return true wenn MAX_MUTATION überschritten ist.
	 */
	public boolean gameOver() {
		return actMutation >= MAX_MUTATION;
	}

	/**
	 * Fügt der actMutation des Spielers den Wert von value zu. Wird in
	 * pickUpEntity() aufgerufen
	 * @param value - Wert, der zugefuegt wird
	 * @
	 */
	private void addMutation(float value) {
		setActMutation(value * mutationFactor + actMutation);
		if (actMutation < 0 ){
			actMutation = 0;
		}
		if (gameOver()){
			changes.firePropertyChange("gameover", false, true);
		}
	}

	/**
	 * Bewegt den Spieler ein Field in die aktuelle Direction nach "vorne".
	 * Wenn er ein Entity aktiv hat, wird dieses auf null gesetzt
	 */
	public void move() {
		if (hasActiveEntity()) {
			setActiveEntity(null);
		}
		if (actField.hasNextField(direction)) {
			setActField(actField.getNextField(direction));
			addMutation(actField.getAreaMutationFactor());
		}
	}

	/**
	 * Spricht eine Person an. und gibt deren Dialog zurück
	 * @param p - Person, die der Player ansprechen möchte
	 * @return jeweiligen Dialog
	 * */
	public String address(Person p) {
		String dialog = null;
		if (p.hasItem()){
			inventory.addItem(p.getItem());
			dialog = p.showDialog();
			p.removeItem();
		} else {
			dialog = p.showDialog();
		}
		return dialog;
	}

	/**
	 * Dreht den Player in die entgegengesetzte Direction
	 * */
	public void turnAround() {
		setDirection(direction.getBack());
	}

	/**
	 * Dreht den Player nach links (vgl Direction)
	 */
	public void turnLeft() {
		setDirection(direction.getLeft());
	}

	/**
	 * Dreht die Spielfigur nach rechts (vgl Direction)
	 */
	public void turnRight() {
		setDirection(direction.getRight());
	}

	/**
	 * Prüft, obder Player ein aktives Entity hat
	 * 
	 * @return true wenn ein Entity aktiv ist
	 */
	public boolean hasActiveEntity() {
		boolean test = (activeEntity != null);
		return test;
	}

	/**
	 * Der Player hebt ein Entity vom aktuellen Field auf.
	 * Ist es ein InventoryItem, legt er es ins Inventory,
	 * ist es ein EnergyItem, wird die Mutation beeinflusst. 
	 * 
	 * @param entity -aufzuhebendes Entity
	 */
	public void pickUpEntity(Entity entity) {
		if (entity instanceof EnergyItem) {
			EnergyItem item = (EnergyItem) entity;
			if (!item.isAbsolute()) {
				mutationFactor *= item.getMutationValue();
			} else {
				addMutation(item.getMutationValue());
			}
		} else if (entity instanceof InventoryItem) {
			inventory.addItem((InventoryItem) entity);
		} else {
			// TODO: Exception?
		}
		actField.getNextField(direction).deleteEntity();
	}

	/**
	 * Kombiniert das aktuell ausgewählte Entity und das bereits vorher
	 * aktivierte Entity miteinander und entscheidet wohin das Resultat gelegt
	 * werden soll.
	 * 
	 * @param entity - aktuell ausgewähltes Entity
	 * @throws CombineNotSupportedException
	 *             sollte die Interaction dieser Entity Objekte nicht vorgesehen
	 *             (kein Resultat) sein, wird diese Exception geworfen.
	 */
	public void combineEntities(InteractionEntity entity)
			throws CombineNotSupportedException {
		Field interactionField = actField.getNextField(this.direction);
		List<Entity> newEntity = null;
		if (activeEntity instanceof Robot) {
			newEntity = activeEntity.combine(entity);
			setNumDestroyedRobots(getNumDestroyedRobots() + 1);
		} else {
			newEntity = entity.combine(activeEntity);
		}
		
		if (entity instanceof Robot) {
			setNumDestroyedRobots(getNumDestroyedRobots() + 1);
		}
		
		handleCombinedEntity(entity, interactionField);
		handleCombinedEntity(activeEntity, interactionField);
		
		// Verarbeitung des entstandenen Entities
		if (newEntity != null) {
			for (Entity e : newEntity) {
				if (e instanceof InventoryItem) {
					inventory.addItem((InventoryItem) e);
				} else {
					interactionField.setEntity(e);
				}
			}
		}
		setActiveEntity(null);
	}
	
	private void handleCombinedEntity(InteractionEntity entity, Field interactionField) {
		if (entity.disappearsAfterCombining()) {
			if (inventory.hasItem(entity)) {
				inventory.removeItem((InventoryItem) entity);
			} else if (interactionField.getEntity() != null
					&& interactionField.getEntity().equals(entity)) {
				interactionField.deleteEntity();
			}
		}
	}

	public Direction getDirection() {
		return direction;
	}

	public String getName() {
		return name;
	}

	public float getMutationFactor() {
		return mutationFactor;
	}

	public float getActMutation() {
		return actMutation;
	}

	public InteractionEntity getActiveEntity() {
		return activeEntity;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public Field getActField() {
		return actField;
	}

	public int getNumDestroyedRobots() {
		return numDestroyedRobots;
	}

	public void setDirection(Direction direction) {
		Direction oldDirection = this.direction;
		this.direction = direction;
		changes.firePropertyChange("direction", oldDirection, this.direction);
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setMutationFactor(float mutationFactor) {
		this.mutationFactor = mutationFactor;
	}

	public void setActMutation(float actMutation) {
		float oldMutation = this.actMutation;
		this.actMutation = actMutation;
		changes.firePropertyChange("actMutation", oldMutation, this.actMutation);
	}

	public void setActiveEntity(InteractionEntity activeEntity) {
		InteractionEntity oldEntity = this.activeEntity;
		this.activeEntity = activeEntity;
		changes.firePropertyChange("activeEntity", oldEntity, this.activeEntity);
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public void setActField(Field actField) {
		Field oldField = this.actField;
		this.actField = actField;
		changes.firePropertyChange("actField", oldField, this.actField);
	}
	
	public float getMaxMutation() {
		return MAX_MUTATION;
	}

	public void setNumDestroyedRobots(int numDestroyedRobots) {
		this.numDestroyedRobots = numDestroyedRobots;
		changes.firePropertyChange("robotDestroyed", numDestroyedRobots-1, numDestroyedRobots);
	}

	public void addPropertyChangeListener(PropertyChangeListener l) {
		changes.addPropertyChangeListener(l);
	}

	public void removePropertyChangeListener(PropertyChangeListener l) {
		changes.removePropertyChangeListener(l);
	}

	@Override
	public String toString() {
		return this.name + " steht auf " + actField + " und guckt nach "
				+ direction;
	}
}