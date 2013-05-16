package de.mi.hsrm.swt.campusadventure.gameview;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.JTextArea;


/**
 * Panel zeigt die Information über das Spiel und die Spielsteuerung zu Beginn eines neuen 
 * oder geladenen Spiels mit hilfe einer TextArea an.
 * */

public class ControlInfoPanel extends JPanel {

	private int width;
	private int height;
	private JTextArea info;

	private final Color TRANCPARENCY = new Color(0, 0, 0, 0);

	/**
	 * @param width - Breite des Panels
	 * @param height - Höhe des Panels
	 * */
	public ControlInfoPanel(int width, int height) {
		super();
		this.width = width;
		this.height = height;

		setSize(width, height);
		setOpaque(false);

		setInfoLabel();
	}

	
	/**
	 * Fügt den Informationstext hinzu und legt das Erscheinungbild fest.
	 */

	private void setInfoLabel() {
		info = new JTextArea(
				"Es ist Montagmorgen und du hast wieder einmal verschlafen. Als du zu spät zur FH kommst und das Medieninformatikgebäude betrittst, merkst du sofort, dass sich über das Wochenende einiges geändert hat."
						+ "Die angewandten Informatiker wollen die Weltherrschaft an sich reißen und haben all deine Freunde eingesperrt. Schnell, rette deine Kommilitonen aus den Fängen der Roboter und mach das Gebäude wieder zu dem, was es einmal war! Aber beeil dich! Je länger du dich ungeschützt im Gebäude aufhältst, umso nerdiger wirst du selbst.\n\n" +
						"Bewege dich mit den Pfeiltasten vorwärts.\n"+
						"Klicke Gegenstände an, um mit ihnen zu interagieren.\"+" +
						"Mit ESC kannst du das Spiel verlassen.");
		info.setSize(width - 10, height - 10);
		info.setEditable(false);
		info.setFocusable(false);
		info.setBackground(TRANCPARENCY);
		info.setBorder(null);
		info.setLineWrap(true);
		info.setWrapStyleWord(true);
		info.setLocation(this.getLocation());
		add(info);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(new Color(200, 200, 200, 220));
		g.fillRoundRect(0, 0, width, height, 20, 20);
		g.setColor(new Color(0, 0, 0, 220));

	}
}
