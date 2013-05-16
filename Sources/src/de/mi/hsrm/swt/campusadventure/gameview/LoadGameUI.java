package de.mi.hsrm.swt.campusadventure.gameview;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import de.mi.hsrm.swt.campusadventure.gameenvironment.player.Player;
import de.mi.hsrm.swt.campusadventure.gamelogic.SystemManager;

/**
 * Spezialisiertes BackgroundPanel, das die Ansicht zum Laden eines
 * Spielstandes im Haupmenü darstellt
 * 
 */
public class LoadGameUI extends BackgroundPanel {

	private final Color SEL_COLOR = new Color(0, 204, 0, 175);
	private final Color BG_COLOR = new Color(0, 0, 0, 175);

	private MainFrameUI mainFrame;
	private JButton backButton;
	private JButton loadButton;
	private JList playerlist;
	private JList gameslist;
	private JLabel playerNameLabel;
	private JLabel savegameLabel;
	private JPanel buttonPanel;
	private DefaultListModel playermodel;

	/**
	 * Erstellt ein neues LoadGameUI Objekt
	 * 
	 * @param mainFrame - Hauptfester der Anwendung
	 */
	public LoadGameUI(MainFrameUI mainFrame) {
		super(new ImageIcon("img/menu/titelscreen.png"));
		setLayout(null);
		setSize(1024, 768);
		this.mainFrame = mainFrame;
		this.backButton = new MenuButton("zurück");
		this.loadButton = new MenuButton("load");
		this.buttonPanel = new JPanel();
		this.playerNameLabel = new JLabel("Spielername:");
		this.savegameLabel = new JLabel("Spielstand:");

		initPlayerList();
		gameslist = new JList();
		addComponents();
	}

	/**
	 * Initialisiert die Liste der Spieler, die bereits einen Spielstand gespeichert haben
	 */
	public void initPlayerList() {
		playermodel = new DefaultListModel();
		List<String> players = SystemManager.getInstance().loadSavegames();
		for (String player : players) {
			playermodel.addElement(player);
		}

		playerlist = new JList(playermodel);

	}
	
	/**
	 * Aktualisiert die Liste der Spieler, die bereits einen Spielstand gespeichert haben
	 */
	public void updateListModel() {
		playermodel.removeAllElements();
		List<String> players = SystemManager.getInstance().loadSavegames();
		for (String player : players) {
			playermodel.addElement(player);
		}
	}

	private void addComponents() {
		backButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				mainFrame.displayStartMenuUI();
			}
		});

		loadButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!playerlist.isSelectionEmpty()
						&& !gameslist.isSelectionEmpty()) {
					String playerName = (String) playerlist.getSelectedValue();
					String savename = (String) gameslist.getSelectedValue();

					Player player = SystemManager.getInstance().loadGame(
							playerName, savename);
					mainFrame.displayPlayerUI(player);
				}

			}
		});

		playerlist.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent evt) {
				JList list = (JList) evt.getSource();
				if (!list.isSelectionEmpty()) {
					String playerName = (String) list.getSelectedValue();
					
					// init gameslist
					DefaultListModel listmodel = new DefaultListModel();
					List<String> games = SystemManager.getInstance()
							.loadPlayerSaves(playerName);
					for (String game : games) {
						listmodel.addElement(game);
					}
					gameslist.setModel(listmodel);
				}
			}
		});

		playerlist.setBounds(0, 30, 195, 200);
		playerlist.setBackground(BG_COLOR);
		playerlist.setForeground(Color.WHITE);
		playerlist.setSelectionBackground(SEL_COLOR);

		gameslist.setBounds(205, 30, 195, 200);
		gameslist.setBackground(BG_COLOR);
		gameslist.setForeground(Color.WHITE);
		gameslist.setSelectionBackground(SEL_COLOR);

		playerNameLabel.setBounds(0, 5, 195, 15);
		playerNameLabel.setOpaque(true);
		playerNameLabel.setForeground(Color.white);
		playerNameLabel.setFont(new Font("Serif", Font.BOLD, 12));
		playerNameLabel.setBackground(BG_COLOR);

		savegameLabel.setBounds(205, 5, 195, 15);
		savegameLabel.setOpaque(true);
		savegameLabel.setForeground(Color.white);
		savegameLabel.setFont(new Font("Serif", Font.BOLD, 12));
		savegameLabel.setBackground(BG_COLOR);

		backButton.setBounds(125, 250, 75, 30);
		loadButton.setBounds(200, 250, 75, 30);


		buttonPanel.setOpaque(false);
		buttonPanel.setLayout(null);
		buttonPanel.setSize(400, 300);
		buttonPanel.setLocation((getWidth() - buttonPanel.getWidth()) / 2, 250);

		buttonPanel.add(backButton);
		buttonPanel.add(playerlist);
		buttonPanel.add(gameslist);
		buttonPanel.add(loadButton);
		buttonPanel.add(playerNameLabel);
		buttonPanel.add(savegameLabel);

		add(buttonPanel);
	}

}
