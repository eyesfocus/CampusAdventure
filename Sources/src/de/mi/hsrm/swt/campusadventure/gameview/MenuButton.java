package de.mi.hsrm.swt.campusadventure.gameview;

import java.awt.Color;

/**
 * Spezialisierter JButton, der das Erscheinungsbild der Menübuttons festlegt.
 */

import javax.swing.JButton;

/**
 * Ein angepasster JButton, 
 * welcher für Menüoptionen benötigt wird
 */
public class MenuButton extends JButton {
	
	private final Color BG_COLOR = new Color(0, 0, 0, 175);

	public MenuButton(String text) {
		super(text);
		setBackground(BG_COLOR);
		setForeground(Color.WHITE);
	}
	public MenuButton() {
		super();
		setBackground(BG_COLOR);
		setForeground(Color.WHITE);
	}
}
