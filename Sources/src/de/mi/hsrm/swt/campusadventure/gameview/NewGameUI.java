package de.mi.hsrm.swt.campusadventure.gameview;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Spezialisiertes BackgroundPanel, das die Ansicht zum Starten eines
 * neuen Spiels im Haupmenü darstellt
 * 
 */
public class NewGameUI extends BackgroundPanel{
	
	private JLabel playerNameLabel;
	private JTextField nameTextField;
	private JButton startButton;
	private JButton backButton;
	private JPanel buttonPanel;
	
	private MainFrameUI mainFrame;
	
	/**
	 * Erstellt ein neues NewGameUI Objekt
	 * 
	 * @param mainFrame - Hauptfester der Anwendung
	 */
	public NewGameUI(MainFrameUI mainFrame) {
		super(new ImageIcon("img/menu/titelscreen.png"));
		setLayout(null);
		setSize(1024,768);
		this.playerNameLabel = new JLabel("Spielername");
		this.nameTextField = new JTextField(30);
		this.startButton = new MenuButton("Spiel starten");
		this.backButton = new MenuButton("zurück");
		this.buttonPanel = new JPanel();
		this.mainFrame = mainFrame;
		addComponents();
	}
	
	/**
	 * Setzt das Textfeld mit dem Spielernamen zurück
	 */
	public void clearTextField(){
		nameTextField.setText("");
	}
	
	private void addComponents(){
		
		startButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String playerName = nameTextField.getText();
				if (playerName.length() > 0) {
					mainFrame.displayPlayerUI(playerName);
				}
			}
		});
		
		backButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mainFrame.displayStartMenuUI();
			}
		});
		playerNameLabel.setOpaque(true);
		playerNameLabel.setForeground(Color.white);
		playerNameLabel.setFont(new Font("Serif", Font.BOLD, 16));
		playerNameLabel.setBackground(new Color(0,0,0,175));
		
		buttonPanel.setOpaque(false);
		buttonPanel.setSize(400, 100);
		buttonPanel.setLocation((getWidth() - buttonPanel.getWidth()) / 2, 300);
		buttonPanel.add(playerNameLabel);
		buttonPanel.add(nameTextField);
		buttonPanel.add(startButton);
		buttonPanel.add(backButton);
		add(buttonPanel);
	}
}
