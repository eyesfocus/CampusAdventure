package de.mi.hsrm.swt.campusadventure.gameview;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import de.mi.hsrm.swt.campusadventure.gameenvironment.entity.EnergyItem;
import de.mi.hsrm.swt.campusadventure.gameenvironment.level.Field;
import de.mi.hsrm.swt.campusadventure.gameenvironment.player.Player;
import de.mi.hsrm.swt.campusadventure.repository.Direction;
import de.mi.hsrm.swt.campusadventure.repository.FloorType;
import de.mi.hsrm.swt.campusadventure.repository.IconBuilder;

/**
 * Spezialisiertes JLayeredPane, das die Ego-Perspektive des Spieler in der 
 * In-Game-Ansicht darstellt.
 * Die Ego-Perspektive besteht aus vier Ebenen, welche bis zu drei
 * Felder  darstellt die sich vor dem Spieler befinden. Die vierte Ebene 
 * stellt dar, ob hinter dem dritten Feld weitere Felder Folgen, oder ob eine
 * Wand vorhanden ist.
 * Falls sich auf einem Feld vor dem Spieler ein Entity befindet, wird dieses auf
 * der entsprechenden Ebene dargestellt.
 * 
 *
 */
public class ViewAreaUI extends JLayeredPane {

	private IconBuilder iconBuilder;
	private final int scaleMode = Image.SCALE_FAST;
	
	private PlayerUI playerUI;
	private Player player;
	private PropertyChangeListener fieldListener;

	private ViewPane l1;
	private ViewPane l2;
	private ViewPane l3;
	private ViewPane l4;
	private ArrayList<ViewPane> allLabels;

	private final int WIDTH = 800;
	private final int HEIGHT = 600;

	/**
	 * Erstellt ein neuers ViewAreaUI Objekt
	 * 
	 * @param playerUI - aktuelles PlayerUI Objekt 
	 * @param player - aktuelles Player Objekt
	 */
	public ViewAreaUI(PlayerUI playerUI, Player player) {
		super();
		setLayout(null);
		
		this.player = player;
		this.playerUI = playerUI;
		this.l1 = new ViewPane();
		this.l2 = new ViewPane();
		this.l3 = new ViewPane();
		this.l4 = new ViewPane();
		this.l1.setBackground(Color.white);
		this.l2.setBackground(Color.white);
		this.l3.setBackground(Color.white);
		this.l4.setBackground(Color.white);
		this.allLabels = new ArrayList<ViewPane>();
		allLabels.add(l1);
		allLabels.add(l2);
		allLabels.add(l3);
		
		this.iconBuilder = IconBuilder.getInstance();
		
		initComponents();
		displayLevelView();
		addComponents();

	}

	private void initComponents() {

		setBounds(0, 0, WIDTH, HEIGHT);
		
		int widthL1 = (int) (WIDTH);
		int heightL1 = (int) (HEIGHT);

		int widthL2 = 476;
		int heightL2 = 357;

		int widthL3 = 282;
		int heightL3 = 213;
		
		int widthL4 = 171;
		int heightL4 = 131;

		int xPosL1 = 0;
		int yPosL1 = 0;

		int xPosL2 = (int) ((widthL1 - widthL2) / 2.0);
		int yPosL2 = (int) ((heightL1 - heightL2) / 2.0);

		int xPosL3 = (int) ((widthL1 - widthL3) / 2.0);
		int yPosL3 = (int) ((heightL1 - heightL3) / 2.0);
		
		int xPosL4 = (int) ((widthL1 - widthL4) / 2.0);
		int yPosL4 = (int) ((heightL1 - heightL4) / 2.0);

		l1.setBounds(xPosL1, yPosL1, widthL1, heightL1);
		l2.setBounds(xPosL2, yPosL2, widthL2, heightL2);
		l3.setBounds(xPosL3, yPosL3, widthL3, heightL3);
		l4.setBounds(xPosL4, yPosL4, widthL4, heightL4);
		
	}

	private void addComponents() {	
		add(l1);
		add(l2);
		add(l3);
		add(l4);
	}

	/**
	 * Stellt die aktuelle Ansicht des Spielers dar.
	 */
	public void displayLevelView() {

		l1.removeAll();
		l2.removeAll();
		l3.removeAll();
		l4.removeAll();
	
		setLayerImg(player);
	}
	
	/**
	 * Anhand des aktuellen Felders des Spielers, werden die Bilder
	 * zur Darstellung der Spielerumgebung gesetzt.
	 * Dazu wird auf vier aufeinander liegenden, verschiedengroßen Labels
	 * das jeweilige Bild gesetzt, um die Spielerumgebung darzustellen.
	 * @param player
	 */
	private void setLayerImg(Player player){
		Field aktField = player.getActField();
		Direction playerDirection = player.getDirection();
		Icon aktIcon = new ImageIcon();
		boolean buttonEnabled = true;
		
		//alle sichtbaren Felder mit Bildern der Perspektive inizialisieren
		for(ViewPane aktLabel : allLabels){
			ImageIcon imIcon = new ImageIcon();
			if (aktField.hasNextField(playerDirection)) {
				aktField = aktField.getNextField(playerDirection);
				FloorType aktFloorType = aktField.getFloorType(playerDirection);
				
				
				switch (aktFloorType) {
				case NONE:
					aktIcon = iconBuilder.getIcon(aktField, "floorIcon");
					break;
				case LEFT:
					aktIcon = iconBuilder.getIcon(aktField, "floorLeftIcon");
					break;
				case RIGHT:
					aktIcon = iconBuilder.getIcon(aktField, "floorRightIcon");
					break;
				case BOTH:
					aktIcon = iconBuilder.getIcon(aktField, "floorBothIcon");
					break;
				default:
					aktIcon = iconBuilder.getIcon(aktField, "wallIcon");
				}
				
				//Skalieren
				imIcon.setImage(((ImageIcon) aktIcon).getImage().getScaledInstance(
						(int) (aktLabel.getWidth()), (int) (aktLabel.getHeight()),
						scaleMode));				
				if(aktField.hasEntity()){
					placeEntities(aktField, aktLabel, buttonEnabled);
				}
				buttonEnabled = false;
				
			} else {
				aktIcon = iconBuilder.getIcon(aktField, "wallIcon");
				//Skalieren
				imIcon.setImage(((ImageIcon) aktIcon).getImage().getScaledInstance(
						(int) (aktLabel.getWidth()), (int) (aktLabel.getHeight()),
						scaleMode));

			}
			aktLabel.setIcon(imIcon.getImage());

		}
		
		//Prüfen, ob es hinter dem letzen sichbaren Feld noch weitereht
		if(!aktField.hasNextField(playerDirection)){
			aktIcon = iconBuilder.getIcon(aktField, "wallIcon");
			ImageIcon imIcon = new ImageIcon();
			imIcon.setImage(((ImageIcon) aktIcon).getImage().getScaledInstance(
					(int) (l4.getWidth()), (int) (l4.getHeight()),
					scaleMode));
			l4.setIcon(imIcon.getImage());
		}else{
			l4.setIcon(iconBuilder.getIcon(aktField.getNextField(playerDirection), "infinitIcon").getImage());
		}
	}
	
	/**
	 * Hilfsmethode zum Platzieren der Entites in der aktuellen Ansicht des Spielers.
	 * Nur Entities, die unmittelbar vor dem Spieler sind, sollen 'clickbar' sein
	 * 
	 * @param aktField - aktuelles Feld des Spielers
	 * @param aktLabel - das entsprechende Label zu dem aktuellen Feld des Spielers
	 * @param buttonEnabled - Flag, ob Button clickbar ist
	 */
	private void placeEntities(final Field aktField, JLayeredPane aktLabel, boolean buttonEnabled){
		
			int buttonSize = (int)((double)aktLabel.getWidth() / l1.getWidth() * 100);
			EntityButton eb = new EntityButton(new ImageIcon(aktField.getEntity().getImagePath()), aktField.getEntity()); 
			//eb.setRolloverIcon(new ImageIcon("img/entities/schraubenzieher_sel.png"));
			eb.setButtonSize(buttonSize);
			eb.setEnabled(buttonEnabled);
			//horizontal mittig zentriert zeichnen
			eb.setLocation((int)(aktLabel.getWidth() / 2.0 - (eb.getWidth() / 2.0)), (int)(aktLabel.getHeight() - eb.getHeight() - 20));
			eb.setToolTipText((aktField.getEntity() instanceof EnergyItem) ? "Energiegegenstand" : aktField.getEntity().getName());
			eb.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					playerUI.useEntity(aktField.getEntity());
				}
			});
			aktLabel.add(eb);
		
	}

	/**
	 * Löscht den Listener von dem Field.
	 * @param oldValue 
	 */
	public void removeFieldListener(Field oldValue) {
		oldValue = oldValue.getNextField(player.getDirection());
		oldValue.removePropertyChangeListener(fieldListener);
	}

	/**
	 * Fügt einen listener für das Field vor dem Player hinzu
	 * @param newValue
	 */
	public void addFieldListener(Field newValue) {
		if (newValue.hasNextField(player.getDirection())) {
			newValue = newValue.getNextField(player.getDirection());
			fieldListener = new PropertyChangeListener() {
			
				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					if (evt.getPropertyName().equals("fieldEntity")) {
						displayLevelView();
						playerUI.hideDialogUI();
					}
				}
			};
			newValue.addPropertyChangeListener(fieldListener);
		}
	}
	
	/**
	 * Angepasstes JLayeredPane welches die Ebenenbilder des Levels darstellt
	 */
	private class ViewPane extends JLayeredPane {
		
		private Image img;
		
		/**
		 * Setzt ein neues Image in das Pane
		 * @param img Image Objekt
		 */
		public void setIcon(Image img) {
			this.img = img;
			repaint();
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(img, 0, 0, null);
		}
	}
}