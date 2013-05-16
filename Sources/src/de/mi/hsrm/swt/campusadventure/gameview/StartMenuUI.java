package de.mi.hsrm.swt.campusadventure.gameview;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Spezialisiertes BackgroundPanel, welches das StartMenü der Anwendung darstellt.
 * 
 */
public class StartMenuUI extends BackgroundPanel{
	
	private JButton startGame;
	private JButton loadGame;
	private JButton exitApplication;
	private JPanel buttonPanel;
	
	private MainFrameUI mainFrame;
	
	public StartMenuUI(MainFrameUI mainFrame) {
		super(new ImageIcon("img/menu/titelscreen.png"));
		setLayout(null);
		setSize(1024,630);
		this.mainFrame = mainFrame;
		this.buttonPanel = new JPanel();
		initComponents();
		addComponents();
	}
	
	/**
	 * Inizialisiert die Komponenten der StartMenuUI
	 */
	private void initComponents(){
		startGame = new MenuButton("Spiel Starten");
		loadGame = new MenuButton("Spiel Laden");
		exitApplication = new MenuButton("Beenden");
		buttonPanel.setOpaque(false);
		buttonPanel.setSize(150, 100);
		buttonPanel.setLocation((getWidth() - buttonPanel.getWidth()) / 2, 300);
	}
	
	/**
	 * Fügt die Komponenten der StartMenuUI hinzu
	 */
	private void addComponents(){
		startGame.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent evt) {
				mainFrame.displayNewGameUI();
				
			}
		});
		
		loadGame.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent evt) {
				mainFrame.displayLoadGameUI();
				
			}
		});
		
		exitApplication.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent evt) {
				mainFrame.dispose();
			}
		});
		buttonPanel.add(startGame);
		buttonPanel.add(loadGame);
		buttonPanel.add(exitApplication);	
		add(buttonPanel);
	}
}
