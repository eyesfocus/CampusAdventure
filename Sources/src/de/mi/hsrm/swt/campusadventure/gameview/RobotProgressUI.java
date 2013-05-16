package de.mi.hsrm.swt.campusadventure.gameview;

import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import de.mi.hsrm.swt.campusadventure.gameenvironment.player.Player;
import de.mi.hsrm.swt.campusadventure.repository.EntityInitializer;

/**
 * Stellt die Anzahl der bereits zerstörten / noch zu zerstörenden Roboter dar.
 */
public class RobotProgressUI extends JPanel {

	private JLabel robotLabel;
	private PlayerUI playerUI;
	private Player player;
	
	private final Color BACKGROUND = new Color(200, 200, 200, 230);
	
	/**
	 * Erstellt eine neue Instanz von RobotProgressUI
	 * 
	 * @param playerUI UIPanel auf dem das Panel dargestellt wird
	 * @param player Player Objekt
	 */
	public RobotProgressUI(PlayerUI playerUI, Player player) {
		this.playerUI = playerUI;
		this.player = player;
		this.player.addPropertyChangeListener(new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getPropertyName().equals("robotDestroyed")) {
					setText();
				}	
			}
		});
		setSize(220, 55);
		setBackground(BACKGROUND);
		setFocusable(false);
		addComponents();
	}
	
	/**
	 * Füge Komponenten zum Panel hinzu
	 */
	public void addComponents() {
		robotLabel = new JLabel();
		setText();
		add(robotLabel);
	}
	
	/**
	 * Setzt/Aktualisiert den angezeigten Text des Panels
	 */
	public void setText() {
		int actRobots = player.getNumDestroyedRobots();
		int maxRobots = EntityInitializer.getInstance().getMaxRobotNumber();
		robotLabel.setText("Anzahl zerstörter Roboter: "+actRobots+" / "+maxRobots);
		if (actRobots >= maxRobots) {
			playerUI.displayGameWonUI();
		}
	}
}
