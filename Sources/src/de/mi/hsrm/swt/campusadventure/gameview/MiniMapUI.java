package de.mi.hsrm.swt.campusadventure.gameview;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import de.mi.hsrm.swt.campusadventure.gameenvironment.level.Field;
import de.mi.hsrm.swt.campusadventure.gameenvironment.player.Player;
import de.mi.hsrm.swt.campusadventure.helper.Point;
import de.mi.hsrm.swt.campusadventure.repository.Direction;

/**
 * Spezialisiertes JPanel, das in der In-Game-Ansicht, die die Umgebung
 * des Spielers in einer Übersichtskarte darstellt.
 *
 */
public class MiniMapUI extends JPanel {
	private Player player;

	private final int BLOCK_SIZE = 20;

	private final int WIDTH = 140;
	private final int HEIGHT = 140;

	private final Color BORDER_COLOR = new Color(150, 150, 150, 200);
	private final Color WALL_FILL_COLOR = new Color(50, 50, 50, 200);
	private final Color CORRIDOR_FILL_COLOR = new Color(255, 255, 255, 200);
	private final Color CAMERA_FILL_COLOR = new Color(175, 235, 235, 200);
	private final Color ARROW_COLOR = Color.ORANGE;
	private final Color ARROW_BORDER_COLOR = new Color(100, 100, 100);

	private Point playerPosition;

	/**
	 * Erstellt eine neue MiniMap welche auf Änderungen 
	 * vom Player hört und sich entsprechend updatet.
	 * 
	 * @param player Player-Objekt
	 */
	public MiniMapUI(Player player) {
		super();
		setLayout(null);
		setSize(WIDTH, HEIGHT);
		setOpaque(false);
		this.player = player;
		playerPosition = calcPlayerPosition();
	}

	/**
	 * Berechnet die Mitte des Rasters und legt damit die angezeigte
	 * Spielerposition fest.
	 * 
	 * @return x, y Koordinate des Mittelpunktes (gerundet)
	 */
	private Point calcPlayerPosition() {
		int x = (int) Math.floor(WIDTH / BLOCK_SIZE / 2f);
		int y = (int) Math.floor(HEIGHT / BLOCK_SIZE / 2f);

		return new Point(x * BLOCK_SIZE, y * BLOCK_SIZE);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		clearGround(g);

		paintLevel(g, player.getActField(), null, playerPosition);
		paintArrow(g);
	}

	/**
	 * Malt die gesamte Minimap mit Wänden.
	 * 
	 * @param g
	 *            Graphics-Objekt
	 */
	private void clearGround(Graphics g) {
		for (int x = 0; x < WIDTH; x += BLOCK_SIZE) {
			for (int y = 0; y < HEIGHT; y += BLOCK_SIZE) {
				paintWall(g, x, y);
			}
		}
	}

	/**
	 * Malt das geladenen Level nach.
	 * 
	 * @param g
	 *            Graphics-Objekt
	 */
	private void paintLevel(Graphics g, Field field, Direction ignoreDirection,
			Point mapPos) {
		int x = mapPos.getX(), y = mapPos.getY();
		if (field.getEast() != null && ignoreDirection != Direction.EAST
				&& x + BLOCK_SIZE < WIDTH) {
			paintLevel(g, field.getEast(), Direction.WEST, new Point(x
					+ BLOCK_SIZE, y));
		}
		if (field.getWest() != null && ignoreDirection != Direction.WEST
				&& x - BLOCK_SIZE >= 0) {
			paintLevel(g, field.getWest(), Direction.EAST, new Point(x
					- BLOCK_SIZE, y));
		}
		if (field.getSouth() != null && ignoreDirection != Direction.SOUTH
				&& y + BLOCK_SIZE < HEIGHT) {
			paintLevel(g, field.getSouth(), Direction.NORTH, new Point(x, y
					+ BLOCK_SIZE));
		}
		if (field.getNorth() != null && ignoreDirection != Direction.NORTH
				&& y - BLOCK_SIZE >= 0) {
			paintLevel(g, field.getNorth(), Direction.SOUTH, new Point(x, y
					- BLOCK_SIZE));
		}
		paintCorridor(g, x, y, field);
	}

	/**
	 * Malt ein Gang-Block um dem Spieler zu signaliseren, dass er hier weiter
	 * kommt.
	 * 
	 * @param g
	 *            Graphics-Objekt
	 * @param x
	 *            x-Koordinate
	 * @param y
	 *            y-Koordinate
	 */
	private void paintCorridor(Graphics g, int x, int y, Field field) {
		if (field.getBuilding().getName().equals("Camera")) {
			g.setColor(CAMERA_FILL_COLOR);
		} else {
			g.setColor(CORRIDOR_FILL_COLOR);
		}
		g.fillRect(x, y, BLOCK_SIZE, BLOCK_SIZE);
		paintBorder(g, x, y);
	}

	/**
	 * Malt ein Wand-Block um dem Spieler zu signalisiern, dass es hier nicht
	 * weiter geht.
	 * 
	 * @param g
	 *            Graphics-Objekt
	 * @param x
	 *            x-Koordinate
	 * @param y
	 *            y-Koordinate
	 */
	private void paintWall(Graphics g, int x, int y) {
		g.setColor(WALL_FILL_COLOR);
		g.fillRect(x, y, BLOCK_SIZE, BLOCK_SIZE);
		paintBorder(g, x, y);
	}

	/**
	 * Malt einen Rand über die Felder.
	 * 
	 * @param g
	 *            Graphics-Objekt
	 * @param x
	 *            x-Koordinate
	 * @param y
	 *            y-Koordinate
	 */
	private void paintBorder(Graphics g, int x, int y) {
		g.setColor(BORDER_COLOR);
		g.drawRect(x, y, BLOCK_SIZE, BLOCK_SIZE);
	}

	/**
	 * Malt einen Pfeil in die Mitte der Karte um die Blickrichtung des Spielers
	 * zu markieren.
	 * 
	 * @param g
	 *            Graphics-Objekt
	 */
	private void paintArrow(Graphics g) {
		int x = playerPosition.getX();
		int y = playerPosition.getY();
		g.setColor(ARROW_COLOR);

		switch (player.getDirection()) {
		case NORTH:
			g.fillPolygon(new int[] { BLOCK_SIZE / 2 + x, BLOCK_SIZE + x, x },
					new int[] { y, BLOCK_SIZE / 2 + y, BLOCK_SIZE / 2 + y }, 3);
			g.setColor(ARROW_BORDER_COLOR);
			g.drawPolygon(new int[] { BLOCK_SIZE / 2 + x, BLOCK_SIZE + x, x },
					new int[] { y, BLOCK_SIZE / 2 + y, BLOCK_SIZE / 2 + y }, 3);
			break;
		case EAST:
			g.fillPolygon(new int[] { BLOCK_SIZE / 2 + x, BLOCK_SIZE + x,
					BLOCK_SIZE / 2 + x }, new int[] { y, BLOCK_SIZE / 2 + y,
					BLOCK_SIZE + y }, 3);
			g.setColor(ARROW_BORDER_COLOR);
			g.drawPolygon(new int[] { BLOCK_SIZE / 2 + x, BLOCK_SIZE + x,
					BLOCK_SIZE / 2 + x }, new int[] { y, BLOCK_SIZE / 2 + y,
					BLOCK_SIZE + y }, 3);
			break;
		case SOUTH:
			g.fillPolygon(new int[] { x, BLOCK_SIZE + x, BLOCK_SIZE / 2 + x },
					new int[] { BLOCK_SIZE / 2 + y, BLOCK_SIZE / 2 + y,
							BLOCK_SIZE + y }, 3);
			g.setColor(ARROW_BORDER_COLOR);
			g.drawPolygon(new int[] { x, BLOCK_SIZE + x, BLOCK_SIZE / 2 + x },
					new int[] { BLOCK_SIZE / 2 + y, BLOCK_SIZE / 2 + y,
							BLOCK_SIZE + y }, 3);
			break;
		case WEST:
			g.fillPolygon(
					new int[] { BLOCK_SIZE / 2 + x, BLOCK_SIZE / 2 + x, x },
					new int[] { y, BLOCK_SIZE + y, BLOCK_SIZE / 2 + y }, 3);
			g.setColor(ARROW_BORDER_COLOR);
			g.drawPolygon(
					new int[] { BLOCK_SIZE / 2 + x, BLOCK_SIZE / 2 + x, x },
					new int[] { y, BLOCK_SIZE + y, BLOCK_SIZE / 2 + y }, 3);
			break;
		default:
			break;
		}
	}
}
