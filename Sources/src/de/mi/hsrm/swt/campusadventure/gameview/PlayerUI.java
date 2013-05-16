package de.mi.hsrm.swt.campusadventure.gameview;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JLayeredPane;
import javax.swing.KeyStroke;

import de.mi.hsrm.swt.campusadventure.exception.CombineNotSupportedException;
import de.mi.hsrm.swt.campusadventure.gameenvironment.entity.Entity;
import de.mi.hsrm.swt.campusadventure.gameenvironment.entity.InteractionEntity;
import de.mi.hsrm.swt.campusadventure.gameenvironment.entity.Robot;
import de.mi.hsrm.swt.campusadventure.gameenvironment.level.Field;
import de.mi.hsrm.swt.campusadventure.gameenvironment.player.Player;
import de.mi.hsrm.swt.campusadventure.gamelogic.SystemManager;
import de.mi.hsrm.swt.campusadventure.helper.RandomTextGenerator;

/**
 * Ein angepasstes JPanel welches alle Ansichten 
 * für die InGame darstellung anzeigt.
 */
public class PlayerUI extends JLayeredPane {

	private InventoryUI inventoryUI;
	private DialogUI dialogUI;
	private ViewAreaUI viewAreaUI;
	private MiniMapUI miniMapUI;
	private StatusUI statusUI;
	private GameEndUI gameOverUI;
	private RobotProgressUI robotProgressUI;
	private MainFrameUI mainFrame;

	private SystemManager systemManager;
	private Player player;

	private ControlInfoPanel infoPanel;

	public PlayerUI(MainFrameUI mainFrame) {
		super();
		setLayout(null);
		setSize(mainFrame.getWidth(), mainFrame.getHeight());
		this.systemManager = SystemManager.getInstance();
		this.mainFrame = mainFrame;
		addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				int button = e.getButton();
				if (button == MouseEvent.BUTTON3) {
					if (player.hasActiveEntity()) {
						player.setActiveEntity(null);
					}
				}

			}
		});
		useKeyBinding();

	}

	/**
	 * Initialisiert und fügt der PlayerUI Keybindings hinzu, um Keyboardevents
	 * zu verarbeiten. (z.B. Spieler Bewegen per w,a,s,d oder In-Game Menü
	 * aufrufen per 'Esc')
	 */
	private void useKeyBinding() {
		setFocusable(true);
		InputMap im = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap am = getActionMap();

		Action upAction = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				player.move();
				hideDialogUI();
			}
		};
		Action downAction = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				player.turnAround();
				hideDialogUI();
			}
		};
		Action leftAction = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				player.turnLeft();
				hideDialogUI();
			}
		};
		Action rightAction = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				player.turnRight();
				hideDialogUI();
			}
		};
		Action escAction = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				mainFrame.displayInGameMenu(player);
			}
		};

		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "up");
		am.put("up", upAction);

		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "down");
		am.put("down", downAction);

		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "left");
		am.put("left", leftAction);

		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "right");
		am.put("right", rightAction);

		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "esc");
		am.put("esc", escAction);
	}

	private void setCursorImage() {
		if (player.hasActiveEntity()) {
			ImageIcon ii = new ImageIcon(player.getActiveEntity()
					.getImagePath());
			Cursor c = getToolkit().createCustomCursor(ii.getImage(),
					new Point(0, 0), "Cursor");
			setCursor(c);
		} else {
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
	}

	public void initPlayer(Player player) {
		this.player = player;
		initView();
	}

	/**
	 * Initialisiert den aktuellen Spieler und erstellt die passenden
	 * UI-Komponenten für die In-Game Ansicht
	 * 
	 * @param playerName
	 *            - Name des Spielers
	 */
	public void initPlayer(String playerName) {
		this.player = systemManager.newGame(playerName);
		initView();
	}

	private void initView() {
		player.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				String eventName = evt.getPropertyName();
				if (eventName.equals("direction")) {
					viewAreaUI.addFieldListener(player.getActField());
					viewAreaUI.displayLevelView();
					miniMapUI.repaint();
				}
				if (eventName.equals("actField")) {
					viewAreaUI.removeFieldListener((Field) evt.getOldValue());
					viewAreaUI.addFieldListener((Field) evt.getNewValue());
					viewAreaUI.displayLevelView();
					miniMapUI.repaint();
				}
				if (eventName.equals("gameover")) {
					displayGameOverUI();
				}
				if (eventName.equals("activeEntity")) {
					setCursorImage();
				}

			}
		});

		infoPanel = new ControlInfoPanel(300, 300);

		viewAreaUI = new ViewAreaUI(this, player);
		viewAreaUI.setLocation(getWidth() - viewAreaUI.getWidth(), 0);
		inventoryUI = new InventoryUI(this, player.getInventory());
		inventoryUI.setLocation(0, 0);
		miniMapUI = new MiniMapUI(player);
		miniMapUI.setLocation(getWidth() - miniMapUI.getWidth(),
				viewAreaUI.getHeight() - miniMapUI.getHeight());
		statusUI = new StatusUI(mainFrame, player);
		statusUI.setLocation(getWidth() - viewAreaUI.getWidth(), 0);
		dialogUI = new DialogUI(this, player);
		dialogUI.setLocation(getWidth() - viewAreaUI.getWidth() + 100,
				statusUI.getHeight() + 20);
		robotProgressUI = new RobotProgressUI(this, player);
		robotProgressUI.setLocation(inventoryUI.getWidth(), getHeight()
				- robotProgressUI.getHeight());
		infoPanel.setLocation(getWidth() - viewAreaUI.getWidth() + 250,
				statusUI.getHeight() + 100);

		add(infoPanel);
		add(robotProgressUI);
		add(miniMapUI);
		add(dialogUI);
		add(statusUI);
		add(viewAreaUI);
		add(inventoryUI);
	}

	public void displayGameOverUI() {
		gameOverUI = new GameEndUI(mainFrame, true);
		gameOverUI.setLocationRelativeTo(mainFrame);
		gameOverUI.setVisible(true);
	}

	public void displayGameWonUI() {
		gameOverUI = new GameEndUI(mainFrame, false);
		gameOverUI.setLocationRelativeTo(mainFrame);
		gameOverUI.setVisible(true);
	}

	/**
	 * Zeigt das aktuelle Dialogfentenster des angeklickten Entites an.
	 * 
	 * @param entity
	 *            - angeklicktes Entity
	 */
	public void displayDialog(Entity entity) {
		dialogUI.updateComponents(entity);
	}

	/**
	 * Blendet das aktuelle Dialogfenster aus.
	 */
	public void hideDialogUI() {
		dialogUI.setVisible(false);
		infoPanel.setVisible(false);
	}

	public void useEntity(Entity entity) {
		if (entity instanceof InteractionEntity && player.hasActiveEntity()) {
			try {
				Entity lastentity = player.getActiveEntity();
				player.combineEntities((InteractionEntity) entity);
				dialogUI.showOnlyText();
				if (entity instanceof Robot) {
					dialogUI.setText("Du hast " + entity + " mit deinem "
							+ lastentity + " vernichtet!");
				} else if (lastentity instanceof Robot) {
					dialogUI.setText("Du hast " + lastentity + " mit einem "
							+ entity + " zerstört!");
				} else {
					dialogUI.setText("Du hast " + lastentity + " und " + entity
							+ " erfolgreich miteinander kombiniert!");
				}
			} catch (CombineNotSupportedException e) {
				dialogUI.setText(RandomTextGenerator.getRandomText());
			}
		} else {
			displayDialog(entity);
		}
	}
}
