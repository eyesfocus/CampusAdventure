package de.mi.hsrm.swt.campusadventure.gameview;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import de.mi.hsrm.swt.campusadventure.gameenvironment.entity.EnergyItem;
import de.mi.hsrm.swt.campusadventure.gameenvironment.entity.Entity;
import de.mi.hsrm.swt.campusadventure.gameenvironment.entity.InteractionEntity;
import de.mi.hsrm.swt.campusadventure.gameenvironment.entity.Person;
import de.mi.hsrm.swt.campusadventure.gameenvironment.player.Player;
import de.mi.hsrm.swt.campusadventure.repository.Option;

/**
 * Spezialisiertes JPanel, das in der In-Game-Ansicht die Dialoge 
 * und Interaktionsmöglichkeiten für den Spieler darstellt.
 */

public class DialogUI extends JPanel {
	
	private JTextArea textArea;
	private DialogMenuUI dialogMenuUI;
	private Player player;
	
	private final int HEIGHT = 150;
	private final int WIDTH = 600;
	private final int RECT_ROUND = 40;
	private final Color BACKGROUND = new Color(200, 200, 200, 230);
	private final Color TRANCPARENCY = new Color(0, 0,0,0);
	
	/**
	 * Erstellt ein neues DialogUI Objekt.
	 * 
	 * @param playerUI - Die Vater-Komponente von
	 * @param player - Das Spielerobjekt, dessen In-Game-Ansicht dargestellt werden soll
	 */
	public DialogUI(PlayerUI playerUI, Player player) {
		super();
		this.player = player;
		addComponents();
	}
	
	/**
	 * Initialisiert und fügt alle Komponenten des JDialogs hinzu.
	 */
	private void addComponents() {
		setSize(WIDTH, HEIGHT);
		setOpaque(false);
		setFocusable(false);
		setVisible(false);
		setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, TRANCPARENCY));
		this.textArea = new JTextArea(5, 51);
		this.dialogMenuUI = new DialogMenuUI();
		textArea.setEditable(false);
		textArea.setFocusable(false);
		textArea.setBackground(TRANCPARENCY);
		textArea.setBorder(null);
		textArea.setLineWrap(true);
		textArea.setText("Hallo Welt, Ich bin die neue DialogUI!!!");

		add(textArea);
		add(dialogMenuUI);
	}
	
	/**
	 * Hilfsmethode zum aktualisiern der TextArea auf die Beschreibung 
	 * des aktuell angeklickten Entity Objekts.
	 * 
	 * @param entity
	 */
	private void displayEntityInfo(Entity entity) {
		setText(entity.showInspectionDialog());
	}

	/**
	 * Aktualisiert die Komponenten des Dialogfensters anhand des aktuell angeklickten Entity Objekts.
	 * 
	 * @param entity - angeklicktes Entity
	 */
	public void updateComponents(Entity entity){
		displayEntityInfo(entity);
		setVisible(true);
		dialogMenuUI.setVisible(true);
		dialogMenuUI.updateOptions(entity);
	}
	
	/**
	 * Zeigt den Dialog der aktuell angeklickten Person an.
	 * 
	 * @param person -Person, deren Dialog angezeigt werden soll
	 */
	public void displayPersonDialog(Person person) {
		String dialog = player.address(person);
		setText(dialog);
	}
	
	/**
	 * Aktualisiert die Textanzeige (JTextarea) des JDialogUI Objekts.
	 * 
	 * @param text - Der neu zu setzende Text
	 */
	public void setText(String text) {
		textArea.setText(text);
	}

	/**
	 * Stellt nur den Text in dem Dialogfenster dar.
	 */
	public void showOnlyText() {
		setVisible(true);
		dialogMenuUI.setVisible(false);
	}
	
	/**
	 * Lässt den Spieler ein Entity aufheben, das sich in der Spielwelt befindet
	 * (nicht im Inventar)
	 * Das aufzuhebende Entity wird an den Spieler weitergeleitet und in der 
	 * DialogUI ein entsprechender Informationstext angezeigt, der die Auswirkung des
	 * aufgehobenen Entities anzeigt.
	 * 
	 * @param entity - Das aufzuhebende Entity 
	 */
	public void pickUpEntity(Entity entity) {
		player.pickUpEntity(entity);
		showOnlyText();
		if(entity instanceof EnergyItem){
			if(((EnergyItem)entity).isAbsolute()){
				setText("Du hast "+entity+" aufgehoben. Deine Mutation ändert sich um " + ((EnergyItem)entity).getMutationValue());
			}else{
				setText("Du hast "+entity+" aufgehoben. Deine Mutation ändert sich nun pro Schritt um " + player.getMutationFactor());
			}
		}else{
			setText("Du hast "+entity+" aufgehoben.");

		}
	}
	
	/**
	 * Aktiviert das ausgewählte Entity
	 * @param clickedEntity ausgewähltes Entity
	 */
	public void useEntity(InteractionEntity clickedEntity) {
		player.setActiveEntity(clickedEntity);
		showOnlyText();
		setText("Du hast "+clickedEntity.getName()+" aktiviert.\n\n(Mit Rechtsklick deaktivieren.)");
	}
	
	/**
	 * Überschriebene Methode von JPanel, dir dafür sorgt,
	 * dass das die DialogUI grau-transparent, mit abgerundeten Ecken zeichnet.
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(BACKGROUND);
		g.fillRoundRect(0, 0, WIDTH, HEIGHT, RECT_ROUND, RECT_ROUND);
	}

	/**
	 * Stellt die Optionen dar, die ein Entity hat.
	 *
	 */
	private class DialogMenuUI extends JPanel implements ActionListener {
		private Entity clickedEntity;
		private JLabel entityInfo;
		private JButton useButton;
		private JButton pickupButton;
		private JButton addressButton;
		
		/**
		 * Erstellt eine neue DialogMenuUI, deren Komponenten
		 * zwar inizialisiert aber noch nicht hinzugefügt sind.
		 */
		public DialogMenuUI() {
			super();
			initComponents();
		}
		
		/**
		 * Inizialisiert die Komponenten der DialogMenuUI.
		 */
		private void initComponents() {
			this.entityInfo = new JLabel();
			this.useButton = new JButton("benutzen");
			this.pickupButton = new JButton("aufheben");
			this.addressButton = new JButton("ansprechen");
			setFocusable(false);
			setOpaque(false);
			entityInfo.setFocusable(false);
			useButton.setFocusable(false);
			pickupButton.setFocusable(false);
			addressButton.setFocusable(false);
			useButton.addActionListener(this);
			pickupButton.addActionListener(this);
			addressButton.addActionListener(this);
		}

		/**
		 * Aktualisiert die Auswahlmöglichkeiten (Buttons) 
		 * anhand des aktuell angeklickten Entity Objekts.
		 * 
		 * @param entity
		 */
		public void updateOptions(Entity entity) {
			setClickedEntity(entity);
			removeAll();
			List<Option> entityOptions = entity.getOptions();
			for (Option option : entityOptions) {
				switch (option) {
				case ADDRESS:
					add(addressButton);
					break;
				case PICK_UP:
					if (!player.getInventory().hasItem(entity)) {
						add(pickupButton);
					}
					break;
				case USE:
					add(useButton);
					break;
				case INSPECT:
					add(entityInfo);
					/*
					 * Falls ein EneryItem angeklick wurde, soll dessen "wahrer" Name
					 * verborgen werden und stattdessen "Energiegegenstand" angezeigt werden.
					 * (Der Spieler soll ja nicht schon anhand des Namens erkennen können,
					 * ob es sich um ein "gutartiges" oder "bösartiges" EnergyItem handelt)
					 */
					entityInfo.setText((entity instanceof EnergyItem) ? "Energiegegenstand" : clickedEntity.getName());
					break;
				default:
					break;
				}
			}
		}
		
		/**
		 * Setter für das Entity Objekt des gerade angeklickten EntityButtons.
		 * 
		 * @param clickedEntity - Das Entity Objekt des gerade angeklickten EntityButtons
		 */
		public void setClickedEntity(Entity clickedEntity) {
			this.clickedEntity = clickedEntity;
		}

		/**
		 * Überschriebene Methode von ActionListener, die ActionEvents
		 * der Buttons registriert und verarbeitet.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton eb = (JButton) e.getSource();
			if (useButton == eb) {
				useEntity((InteractionEntity) clickedEntity);
			} else if (pickupButton == eb) {
				pickUpEntity(clickedEntity);
			} else if (addressButton == eb) {
				displayPersonDialog((Person) clickedEntity);
			}
		}
	}

}