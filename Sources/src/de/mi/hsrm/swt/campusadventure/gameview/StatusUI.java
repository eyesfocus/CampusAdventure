package de.mi.hsrm.swt.campusadventure.gameview;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import de.mi.hsrm.swt.campusadventure.gameenvironment.player.Player;

/**
 * Ein angepasstes JPanel, welches die obere Leiste in dem Spiel darstellt 
 * um dem Spieler weitere Informationen bereit zu stellen.
 */
public class StatusUI extends JPanel {
	private Player player;
	private MainFrameUI mainFrame;
	private MutationUI mutationUI;
	private JLabel playerLabel;
	private JLabel buildingLabel;
	
	private final int WIDTH = 800;
	private final int HEIGHT = 50;
	private final int PANEL_BORDER = 10;
	private final int NAME_LABEL_WIDTH = 350;
	
	private final Color BACKGROUND = new Color(200, 200, 200, 230);
	
	private JButton menuButton;
	
	/**
	 * Erstellt ein neues StatusUI Objekt
	 * 
	 * @param mainFrame - Hauptfenster der Anwendung
	 * @param player - Aktuelles Spieler Objekt
	 */
	public StatusUI(MainFrameUI mainFrame, Player player) {
		super();
		setLayout(null);
		setSize(WIDTH,HEIGHT);
		setOpaque(false);
		this.player = player;
		this.mainFrame = mainFrame;
		initComponents();
		addComponents();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(BACKGROUND);
		g.fillRect(0, 0, WIDTH, HEIGHT);
	}
	
	/**
	 * Initialisiert die Kompoenten der StatusUI
	 */
	private void initComponents(){
		this.playerLabel = new JLabel();
		this.buildingLabel = new JLabel();
		this.menuButton = new JButton("Menü");
		this.mutationUI = new MutationUI(player);
		menuButton.setBounds(PANEL_BORDER, PANEL_BORDER,70,30);
		int playerLabelPos = PANEL_BORDER*2+menuButton.getWidth();
		playerLabel.setBounds(playerLabelPos, PANEL_BORDER, NAME_LABEL_WIDTH, HEIGHT-PANEL_BORDER*2);
		int buildingLabelWidth = WIDTH-playerLabelPos-mutationUI.getWidth()-PANEL_BORDER;
		buildingLabel.setBounds(playerLabelPos+playerLabel.getWidth()+PANEL_BORDER, PANEL_BORDER, buildingLabelWidth, HEIGHT-PANEL_BORDER*2);
		mutationUI.setLocation(getWidth()-mutationUI.getWidth() - PANEL_BORDER, PANEL_BORDER);
		
		menuButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent evt) {
				mainFrame.displayInGameMenu(player);
			}
		});
		playerLabel.setText("Spieler: "  + player.getName());
		updateBuilding();

	}
	
	/**
	 * Aktualisiert die Anzeige für das aktuelle Gebäude des Spielers
	 */
	private void updateBuilding(){
		buildingLabel.setText("Gebäude: " + player.getActField().getBuilding().getName());
	}
	
	/**
	 * FÜgt die Komponenten und ActionListener der StatusUI hinzu
	 */
	private void addComponents(){
		add(mutationUI);
		add(playerLabel);
		add(buildingLabel);
		add(menuButton);
		
		player.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				String eventName = evt.getPropertyName();

				if (eventName.equals("actMutation")) {
					mutationUI.repaint();
				}
				if (eventName.equals("actField")) {
					updateBuilding();
				}
			}
		});
	}
}
