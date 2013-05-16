package de.mi.hsrm.swt.campusadventure.gameview;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import de.mi.hsrm.swt.campusadventure.repository.EntityInitializer;

/**
 * Spezialisiertes JDialog, das die Informationsmeldung beinhaltet,
 * die beim Spielende angezeigt wird.
 * Das Spielende kann entweder aus dem GameOverContentUI oder aus dem GameWonContentUI bestehen
 * 
 */
public class GameEndUI extends JDialog{

	private MainFrameUI mainFrame;
	private final Color TRANSPARENCY = new Color(0,0,0,0);
	
	/**
	 * Erstellt ein neues GameEndUI Objekt
	 * 
	 * @param mainFrame - Hauptfester der Anwendung
	 * @param gameLost - flag, ob Spieler verloren hat
	 */
	public GameEndUI(MainFrameUI mainFrame, boolean gameLost) {
		super(mainFrame);
		setSize(550, 450);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setUndecorated(true);
		this.mainFrame = mainFrame;
		getRootPane().setOpaque(false);
		getContentPane().setBackground(TRANSPARENCY);
		setBackground(TRANSPARENCY);
		if(gameLost) {
			add(new GameOverContentUI());
		} else {
			add(new GameWonContentUI());
		}
	}
	
	/**
	 * Stellt den Siegesscreen dar.
	 *
	 */
	private class GameWonContentUI extends JPanel implements ActionListener {
		
		private JLabel textLabel1;
		private JLabel textLabel2;
		private JLabel imageLabel;
		private JButton accept;
		
		private final int HEIGHT = 450;
		private final int WIDTH = 550;
		private final int RECT_ROUND = 40;

		private final Color BACKGROUND = new Color(200, 200, 200, 230);
		private final Color TRANCPARENCY = new Color(0, 0, 0, 0);

		public GameWonContentUI() {
			setSize(WIDTH, HEIGHT);
			setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
			setOpaque(false);
			setFocusable(false);
			this.textLabel1 = new JLabel("Der Himmel klärt sich auf und du siehst die Sonne wieder scheinen");
			textLabel1.setAlignmentX(Component.CENTER_ALIGNMENT);
			this.textLabel2 = new JLabel("Du hast es geschfft! Das MI-Gebäude ist endlich wieder Nerd-Frei *hust*");
			textLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
			this.imageLabel = new JLabel(new ImageIcon("img/menu/heaven.png"));
			imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			this.accept = new JButton("Cool, nochmaaaal!!!");
			accept.setAlignmentX(Component.CENTER_ALIGNMENT);
			addComponents();
		}
		
		private void addComponents() {
			imageLabel.setBackground(TRANCPARENCY);
			accept.addActionListener(this);
			add(textLabel1);
			add(textLabel2);
			add(imageLabel);
			add(accept);		
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if(accept == e.getSource()) {
				EntityInitializer.resetInstance();
	            mainFrame.displayStartMenuUI();
	            GameEndUI.this.dispose();
	        }		
		}
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(BACKGROUND);
			g.fillRoundRect(0, 0, WIDTH, HEIGHT, RECT_ROUND, RECT_ROUND);
		}
	}
	
	/**
	 * Stellt den GameOver Screen dar.
	 *
	 */
	private class GameOverContentUI extends JPanel implements ActionListener{
		
		private JLabel textLabel1;
		private JLabel textLabel2;
		private JLabel imageLabel;
		private JButton accept;
		
		private final int HEIGHT = 450;
		private final int WIDTH = 550;
		private final int RECT_ROUND = 40;

		private final Color BACKGROUND = new Color(200, 200, 200, 230);
		private final Color TRANCPARENCY = new Color(0, 0, 0, 0);

		public GameOverContentUI() {
			setSize(WIDTH, HEIGHT);
			setOpaque(false);
			setFocusable(false);
			this.textLabel1 = new JLabel("Das spiegelnde Display deines hochwertigen MacBooks lässt dich in Angst erstarren!");
			this.textLabel2 = new JLabel("Es ist passiert, du bist mutiert!!!");
			this.imageLabel = new JLabel(new ImageIcon("img/menu/nerd.png"));
			this.accept = new JButton("Akzeptiere dein Schicksal...");
			addComponents();
		}
		
		private void addComponents() {
			imageLabel.setBackground(TRANCPARENCY);
			accept.addActionListener(this);
			add(textLabel1);
			add(textLabel2);
			add(imageLabel);
			add(accept);		
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if(accept == e.getSource()) {
				EntityInitializer.resetInstance();
	            mainFrame.displayStartMenuUI();
	            GameEndUI.this.dispose();
	        }		
		}
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(BACKGROUND);
			g.fillRoundRect(0, 0, WIDTH, HEIGHT, RECT_ROUND, RECT_ROUND);
		}
	}
}
