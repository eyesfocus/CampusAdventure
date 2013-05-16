package de.mi.hsrm.swt.campusadventure.gameview;

import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

import de.mi.hsrm.swt.campusadventure.gameenvironment.player.Player;
import de.mi.hsrm.swt.campusadventure.repository.EntityInitializer;

/**
 * Das Hauptfenster und die Startklasse des Spiels
 */
public class MainFrameUI extends JFrame {
	private NewGameUI newGameUI;
	private LoadGameUI loadGameUI;
	private StartMenuUI startMenuUI;
	private PlayerUI playerUI;
	private InGameMenuUI inGameMenuUI;

	private CardLayout mainCardLayout;
	private JPanel mainCardPanel;

	private int FRAME_WIDTH = 1024;
	private int FRAME_HEIGHT = 630;

	public MainFrameUI() {
		super();
		EntityInitializer.getInstance();
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setTitle("CampusAdventure");
		setResizable(false);
		getContentPane().setBackground(Color.blue);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.playerUI = null;
		this.newGameUI = new NewGameUI(this);
		this.loadGameUI = new LoadGameUI(this);
		this.startMenuUI = new StartMenuUI(this);
		this.inGameMenuUI = new InGameMenuUI(this);

		// CardLayout initialisieren
		mainCardLayout = new CardLayout();
		mainCardPanel = new JPanel();
		mainCardPanel.setLayout(mainCardLayout);

		mainCardPanel.add(startMenuUI, "startMenuUI");
		mainCardPanel.add(newGameUI, "newGameUI");
		mainCardPanel.add(loadGameUI, "loadGameUI");


		add(mainCardPanel);
		displayStartMenuUI();
	}

	/**
	 * Zeigt das Startmenü an.
	 */
	public void displayStartMenuUI() {
		mainCardLayout.show(mainCardPanel, "startMenuUI");
	}
	
	
	/**
	 * Zeigt das Menü zum Laden eines Spielstandes an.
	 */
	public void displayLoadGameUI() {
		loadGameUI.updateListModel();
		mainCardLayout.show(mainCardPanel, "loadGameUI");
	}
	
	/**
	 * Zeigt die Menü zum Starten eines Spiels an, in welchem der Spielername gewählt werden muss.
	 */
	public void displayNewGameUI() {
		newGameUI.clearTextField();
		mainCardLayout.show(mainCardPanel, "newGameUI");
	}
	
	/**
	 * Zeigt die In-Game Ansicht für den aktuellen Spieler an.
	 * 
	 * @param playerName - Name des Spielers
	 */
	public void displayPlayerUI(String playerName) {
		playerUI = new PlayerUI(this);
		mainCardPanel.add(playerUI, "playerUI");
		playerUI.initPlayer(playerName);
		mainCardLayout.show(mainCardPanel, "playerUI");
	}
	
	/**
	 * Zeigt die In-Game Ansicht für den aktuellen Spieler an.
	 * 
	 * @param player - Player
	 */
	public void displayPlayerUI(Player player) {
		playerUI = new PlayerUI(this);
		mainCardPanel.add(playerUI, "playerUI");
		playerUI.initPlayer(player);
		mainCardLayout.show(mainCardPanel, "playerUI");
	}

	/**
	 * Zeigt das In-Game Menü, zum Beenden und Speichern eines Spiels
	 */
	public void displayInGameMenu(Player player) {
		inGameMenuUI.showMenu(player);

	}

	public static void main(String[] args) {
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
					.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(MainFrameUI.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(MainFrameUI.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(MainFrameUI.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(MainFrameUI.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		}

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				new MainFrameUI().setVisible(true);
			}
		});
	}
}