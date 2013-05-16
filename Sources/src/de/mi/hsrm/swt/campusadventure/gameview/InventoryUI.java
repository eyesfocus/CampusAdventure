package de.mi.hsrm.swt.campusadventure.gameview;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import de.mi.hsrm.swt.campusadventure.gameenvironment.entity.InventoryItem;
import de.mi.hsrm.swt.campusadventure.gameenvironment.player.Inventory;
/**
 * Stellt das Inventar dar.
 *
 */
public class InventoryUI extends JPanel implements Observer, ActionListener {
	private Inventory playerInventory;
	private PlayerUI playerUI;

	private final int WIDTH = 224;
	private final int HEIGHT = 600;
	
	private final Color BACKGROUND = new Color(200, 200, 200, 230);
	
	public InventoryUI(PlayerUI playerUI, Inventory inventory) {
		super();
		this.playerUI = playerUI;
		GridLayout gl = new GridLayout(4, 2, 5, 5);
		gl.preferredLayoutSize(this);
		setLayout(gl);
		setSize(WIDTH, HEIGHT);
		playerInventory = inventory;
		playerInventory.addObserver(this);
		displayInventory();
		
	}

	/**
	 * füllt das Inventar-Panel mit EntityButton's
	 */
	private void displayInventory() {
		removeAll();
		for (int i = 0; i < playerInventory.size(); i++) {
			
			InventoryItem entity = playerInventory.getItem(i);
			EntityButton button = new EntityButton(new ImageIcon(entity.getImagePath()), entity);
			button.setButtonSizeByLongerSide(100);
			JPanel buttonContainerPanel = new JPanel();
			buttonContainerPanel.setLayout(null);
			button.setToolTipText(entity.getName());
			button.addActionListener(this);
			button.setMaximumSize(new Dimension(button.getWidth(), button.getHeight()));
			buttonContainerPanel.add(button);
			add(buttonContainerPanel);
		}
	}

	@Override
	public void update(Observable obs, Object obj) {
		if (obj instanceof Inventory) {
			playerInventory = (Inventory) obj;
			displayInventory();
			updateUI();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		playerUI.useEntity(((EntityButton)e.getSource()).getEntity());		
	}
}