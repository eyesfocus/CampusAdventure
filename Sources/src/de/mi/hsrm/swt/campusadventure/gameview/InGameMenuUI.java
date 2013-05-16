package de.mi.hsrm.swt.campusadventure.gameview;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import de.mi.hsrm.swt.campusadventure.gameenvironment.player.Player;
import de.mi.hsrm.swt.campusadventure.gamelogic.SystemManager;
import de.mi.hsrm.swt.campusadventure.repository.EntityInitializer;

/**
 * Spezialisiertes JDialog, das das In-Game-Menu darstellt,
 * welches die Möglichkeit zum Speichern und Beenden des Spiels
 * anbietet.
 * @author mbech001
 *
 */
public class InGameMenuUI extends JDialog implements ActionListener{

	private Player player;
	private JPanel buttonPanel;
	private JButton save;
	private JButton endGame;
	private MainFrameUI mainFrame;
	private final ImageIcon backgroundIcon = new ImageIcon("img/menu/ingamemenu.png");

	/**
	 * Erstellt ein neues InGameMenu Objekt
	 * 
	 * @param mainFrame - Hauptfester der Anwendung
	 */
	public InGameMenuUI(MainFrameUI mainFrame) {
		super(mainFrame);
		setSize(200, 200);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setModalityType(ModalityType.APPLICATION_MODAL);
		this.buttonPanel = new BackgroundPanel(backgroundIcon);
		this.save = new MenuButton("Speichern");
		this.endGame = new MenuButton("Spiel Beenden");
		this.mainFrame = mainFrame;
		addComponents();
	}

	private void addComponents() {
		save.addActionListener(this);
		endGame.addActionListener(this);
		buttonPanel.add(save);
		buttonPanel.add(endGame);
		this.add(buttonPanel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(save == e.getSource()) {
			SystemManager.getInstance().saveGame(player);
            setVisible(false);
        }
        else if(endGame == e.getSource()) {
			EntityInitializer.resetInstance();
            mainFrame.displayStartMenuUI();
            setVisible(false);
        }
		
	}

	/**
	 * Zeigt das InGameMenuUI an.
	 * 
	 * @param player - Instanz des aktuellen Spielerobjekts, welches zum Speichern benötigt wird
	 */
	public void showMenu(Player player) {
		this.player = player;
		this.setLocationRelativeTo(mainFrame);
		this.setVisible(true);		
	}
	
}
