package de.mi.hsrm.swt.campusadventure.gameview;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import de.mi.hsrm.swt.campusadventure.gameenvironment.entity.Entity;

/**
 * 
 * Spezialisierter JButton, dessen Darstellung lediglich aus einem
 * ImageIcon besteht und der skalierbar ist.
 * Dabei werden nur die gesetzen Pixel (nicht die Transparenzen) als Teil des
 * Buttons angesehen.
 *
 */
public class EntityButton extends JButton {
	private Entity entity;

	private BufferedImage image;
	private int transparentRGB;
	protected int actZoom;

	final private int buttonWidth; // Defaultbreite des Buttons
	final private int buttonHeight;// Defaulthoehe des Buttons

	private boolean isRollOver = false;

	public EntityButton(ImageIcon icon, Entity entity) {
		super(icon);
		transparentRGB = new Color(255, 255, 255, 0).getAlpha();
		image = this.getBufferedImage();
		this.actZoom = 100;
		this.entity = entity;
		setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());
		setMargin(new Insets(0, 0, 0, 0));
		/* Rahmen nicht zeichnen */
		setBorderPainted(false);
		/* Transparenz nicht fuellen */
		setContentAreaFilled(false);
		buttonWidth = getIcon().getIconWidth(); // ...soll als default die
												// Iconbreite haben
		buttonHeight = getIcon().getIconHeight();// ...soll als default die
													// Iconhoehe haben
		/*
		 * Anonymer MouseAdapter, welcher den Rolloverstatus erkennt und
		 * aktiviert bzw. deaktiviert
		 */
		addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {
				setIsRollOver(true);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				setIsRollOver(false);
			}
		});
	}

	/**
	 * Contains Methode so überschrieben, dass Transparenzen eines PNG Bildes
	 * nicht als Teil des Buttons gelten.
	 */
	@Override
	public boolean contains(int posX, int posY) {
		if (super.contains(posX, posY)) {
			posX = (int) ((posX / (double) getWidth()) * buttonWidth);
			posY = (int) ((posY / (double) (getHeight()) * buttonHeight));
			return !(transparentRGB == image.getRGB(posX, posY));
		}
		return false;
	}

	/**
	 * Hilfsmethode zum Erstellen eines BufferedImage Objekts aus dem Icon des
	 * Buttons.
	 * 
	 * @return image - BufferedImage generiert aus dem Button Icon
	 */
	private BufferedImage getBufferedImage() {
		BufferedImage image = new BufferedImage(getIcon().getIconWidth(),
				getIcon().getIconHeight(), BufferedImage.TYPE_INT_ARGB);
		image.getGraphics().drawImage(((ImageIcon) super.getIcon()).getImage(),
				0, 0, null);
		return image;
	}

	/**
	 * Hilfsmethode zum ermitteln der aktuellen X-Position des Buttons
	 * 
	 * @return - aktualisierte X-Position des Buttons
	 */
	private int getActX() {
		return (int) (getWidth() / 2.0)
				- (int) (getIcon().getIconWidth() * actZoom / 100.0) / 2;
	}

	/**
	 * Hilfsmethode zum ermitteln der aktuellen Y-Position des Buttons
	 * 
	 * @return - aktualisierte Y-Position des Buttons
	 */
	private int getActY() {
		return (int) (getHeight() / 2.0)
				- (int) (getIcon().getIconHeight() * actZoom / 100.0) / 2;
	}

	/**
	 * Überschriebene paintComponent() Methode.
	 * 
	 * Aktualisiert die Position und die Größe des Buttons anhand der aktuellen
	 * Icongröße des Buttons.
	 */
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(
				((ImageIcon) getIcon()).getImage(),
				getActX(),
				getActY(),
				(int) (((ImageIcon) getIcon()).getIconWidth() * actZoom / 100.0),
				(int) (((ImageIcon) getIcon()).getIconHeight() * actZoom / 100.0),
				null);

	}

	/**
	 * Setzt die Buttongröße und aktualisiert die Darstellung des Buttons
	 * entsprechend der neuen Buttongröße
	 * 
	 * @param imgSize
	 *            - Buttongröße in % der original Bildgröße
	 */
	public void setButtonSize(int imgSize) {
		this.actZoom = imgSize;
		setSize((int) (buttonWidth * this.actZoom / 100.0), (int) (buttonHeight
				* this.actZoom / 100.0));
	}

	/**
	 * Setzt die Buttongröße und aktualisiert die Darstellung des Buttons
	 * entsprechend der neuen Buttongröße
	 * 
	 * @param newLongerSideSize
	 *            - gewünschte neue Größe der Längeren Seite
	 */
	public void setButtonSizeByLongerSide(int newLongerSideSize) {
		int longerSideSize;
		int newZoom;

		if (buttonWidth > buttonHeight) {
			longerSideSize = buttonWidth;
		} else {
			longerSideSize = buttonHeight;
		}

		newZoom = (int) (((double) newLongerSideSize / longerSideSize) * 100);
		setButtonSize(newZoom);

	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	public boolean getIsRollOver() {
		return isRollOver;
	}

	public void setIsRollOver(boolean isRollOver) {
		this.isRollOver = isRollOver;
	}
}