package de.mi.hsrm.swt.campusadventure.gameview;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import de.mi.hsrm.swt.campusadventure.gameenvironment.player.Player;

/**
 * Spezialisiertes JPanel, das die aktuelle Mutation des Spielers ind einer
 * Mutationleiste anzeigt.
 *
 */
public class MutationUI extends JPanel {
	
	private Player player;
	
	private final int WIDTH = 150;
	private final int HEIGHT = 30;
	private final int PANEL_BORDER = 6;
	private final int ROUND_RECT = 5;
	
	private final Color PROGRESS_COLOR_LIGHT = new Color(50, 205, 50, 230);
	private final Color PROGRESS_COLOR_MEDIUM = new Color(255, 165, 0, 230);
	private final Color PROGRESS_COLOR_HEAVY = new Color(255, 0, 0, 230);
	private final Color PROGRESS_BAR = new Color(100,100,100);
	
	
	/**
	 * Erstellt eine neue Instanz von MutationUI, 
	 * welche die aktuelle Mutaion vom Player grafisch darstellt.
	 * 
	 * @param player Player Objekt
	 */
	public MutationUI(Player player) {
		super();
		setLayout(null);
		setSize(WIDTH, HEIGHT);
		setOpaque(false);
		this.player = player;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		paintProgress(g);
		paintProgressBar(g);
		paintLabel(g);

	}
	
	/**
	 * Zeichnet einen Beschreibungstext in den Balken
	 * 
	 * @param g Graphics Objekt
	 */
	private void paintLabel(Graphics g) {
		g.drawString("Mutation", 10, 20);
	}
	
	/**
	 * Zeichnet die umrandung für den Mutationsbalken
	 * 
	 * @param g Graphics Objekt
	 */
	private void paintProgressBar(Graphics g) {
		g.setColor(PROGRESS_BAR);
		g.drawRoundRect(PANEL_BORDER, PANEL_BORDER, WIDTH-PANEL_BORDER*2, HEIGHT-PANEL_BORDER*2, ROUND_RECT, ROUND_RECT);
	}
	
	/**
	 * Zeichnet den Mutationsfortschritt.
	 * 
	 * @param g Graphics Objekt
	 */
	private void paintProgress(Graphics g) {
		float relativeMutation = player.getActMutation()/player.getMaxMutation();
		if (relativeMutation > 1f) {
			relativeMutation = 1f;
		}
		int absoluteMutation = WIDTH-PANEL_BORDER*2;
		int progress = (int) (absoluteMutation*relativeMutation);
		if (relativeMutation < 0.25f) {
			g.setColor(PROGRESS_COLOR_LIGHT);
		} else if (relativeMutation < 0.6f) {
			g.setColor(PROGRESS_COLOR_MEDIUM);
		} else {
			g.setColor(PROGRESS_COLOR_HEAVY);
		}
		g.fillRoundRect(PANEL_BORDER, PANEL_BORDER, progress, HEIGHT-PANEL_BORDER*2, ROUND_RECT, ROUND_RECT);
	}
}
