package de.mi.hsrm.swt.campusadventure.gameview;

import java.awt.Color;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
/**
 * Spezialisiertes JPanel, dessen Hintergrund durch ein ImageIcon festgelegt wird.
 */
public class BackgroundPanel extends JPanel {
	
	private ImageIcon icon;
	
	/**
	 * Erstellt ein neues BackgroundPanel, auf dessen Hintergrund
	 * das übergebene ImageIcon gezeichnet wird.
	 * Der Hintergrund wird als default-Wert auf schwarz gesetzt
	 * 
	 * @param icon - Hintergrundbild des BackgroundPanels
	 */
	public BackgroundPanel(ImageIcon icon) {
		super();
		this.icon = icon;
		setBackground(Color.black);
	}
	
	/**
	 * Überschriebene Methode von JPanel, dir dafür sorgt,
	 * dass das mitgegebene ImageIcon horizontal zentriert gezeichnet wird.
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// X-Koordinate horzontal zentrieren
		int x = (getWidth() - icon.getIconWidth()) / 2;
		g.drawImage(icon.getImage(), x, 0, null);
	}
}
