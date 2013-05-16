package de.mi.hsrm.swt.campusadventure.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import de.mi.hsrm.swt.campusadventure.gameenvironment.level.Building;
import de.mi.hsrm.swt.campusadventure.gameenvironment.level.CorridorField;
import de.mi.hsrm.swt.campusadventure.gameenvironment.level.Field;
import de.mi.hsrm.swt.campusadventure.gameenvironment.level.RoomField;
import de.mi.hsrm.swt.campusadventure.helper.Point;
import de.mi.hsrm.swt.campusadventure.repository.EntityInitializer;
// TODO: Rundgänge fixen, Update: Fuck off -.-
/**
 * Liest die Leveldatei ein und interpretiert Sie.
 * Erstellt die leeren Gebäude mit den Gängen.
 * Setzt beim Speichern des Spielstandes die Startposition um
 */
public class TxtLevelParser implements LevelParser {
	private final int MAX_X = 20;
	private final int MAX_Y = 20;
	
	private final char CORRIDOR = 'X';
	private final char ROOM = 'R';
	private final char GATEWAY = 'G';
	private final char START = 'S';
	
	private char[][] levelMatrixList;
	private Point startPosition;
	private File levelfile;
	
	/**
	 * Liest die Leveldatei ein und interpretiert Sie.
	 * Erstellt die leeren Gebäude mit den Gängen.
	 * Setzt beim Speichern des Spielstandes die Startposition um
	 * 
	 * @param levelpath Pfad zur Level-Datei
	 */
	public TxtLevelParser(String levelpath) {
		levelMatrixList = new char[MAX_X][MAX_Y];
		loadLevelfile(levelpath);
	}
	
	/**
	 * Läd das Levelfile aus dem angegebenen Pfad aus und erstellt eine Liste 
	 * aus 2-Dimensionalen char-Arrays
	 */
	private void loadLevelfile(String path) {
		levelfile = new File(path);
	}

	/**
	 * Erstellt ein zweidimensionales char-Array aus der Level-Datei
	 * 
	 * @return true wenn die Leveldatei eingelesen und erstellt werden konnte
	 */
	private boolean buildLevelMatrix() {
		boolean success = true;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(levelfile));
			int y = 0;
			while(reader.ready()) {
				String line = reader.readLine();
				for (int x = 0; x < line.length(); x++) {
					levelMatrixList[y][x] = line.charAt(x);
					if (levelMatrixList[y][x] == START) {
						startPosition = new Point(x,y);
					}
				}
				y+=1;
			}
			
		} catch (FileNotFoundException e) {
			success = false;
			System.out.println(e.getMessage());
		} catch (IOException e) {
			success = false;
			System.out.println(e.getMessage());
		}
		return success;
	}

	/**
	 * Überprüft ob sich neben dem mitgegebenen Feld weitere Felder befinden und 
	 * hängt das erste gefundene Feld an das mitgegebene Feld an. 
	 * Ruft danach die Methode mit dem gefundenen Feld auf.
	 */
	private void buildMap(Field field) {
		Point fieldPos = field.getPosition();
		Field nextField = null;

		int x = fieldPos.getX()+1;
		int y = fieldPos.getY();
		// überprüfe östlicher Nachbar
		if (field.getEast() == null && x >= 0 && x < levelMatrixList[0].length) {
			nextField = testNextFieldType(field, x, y);
			if (nextField != null) {
				field.setEast(nextField);
				nextField.setWest(field);
				buildMap(nextField);
			}
		}
		
		x = fieldPos.getX()-1;
		// überprüfe westlicher Nachbar
		if (field.getWest() == null && x >= 0 && x < levelMatrixList[0].length) {
			nextField = testNextFieldType(field, x, y);
			if (nextField != null) {
				field.setWest(nextField);
				nextField.setEast(field);
				buildMap(nextField);
			}
		}
		
		x = fieldPos.getX();
		y = fieldPos.getY()+1;
		// überprüfe südlicher Nachbar
		if (field.getSouth() == null && y >= 0 && y < levelMatrixList.length) {
			nextField = testNextFieldType(field, x, y);
			if (nextField != null) {
				field.setSouth(nextField);
				nextField.setNorth(field);
				buildMap(nextField);
			}
		}
		
		y = fieldPos.getY()-1;
		// überprüfe nördlicher Nachbar
		if (field.getNorth() == null && y >= 0 && y < levelMatrixList.length) {
			nextField = testNextFieldType(field, x, y);
			if (nextField != null) {
				field.setNorth(nextField);
				nextField.setSouth(field);
				buildMap(nextField);
			}
		}
	}

	/**
	 *  Überprüft, welcher Feldtyp als nächstes erstellt werden soll.
	 * @param field aktuelles Feld
	 * @param x x-Position
	 * @param y y-Position
	 * @return nächtes Feld
	 */
	private Field testNextFieldType(Field field, int x, int y) {
		Field nextField = null;
		if (levelMatrixList[y][x] == GATEWAY) {
			nextField = new CorridorField(new Point(x,y), field.getBuilding(), true);
			beamToGateway(nextField);
			return nextField;
		} else if (levelMatrixList[y][x] == CORRIDOR) {
			return new CorridorField(new Point(x,y), field.getBuilding());
		} else if (levelMatrixList[y][x] == ROOM){
			return new RoomField(new Point(x,y), field.getBuilding());
		} else {
			return nextField;
		}
	}

	/**
	 * Sucht den nächsten Gebäudeeingang, wechelt das Gebäude und erstellt 
	 * die Gebäudeliste.
	 * @param actField aktuelles Feld mit Übergang
	 */
	private void beamToGateway(Field actField) {
		Point point = findGatewayWichIsNotAtPoint(actField.getPosition());
		Field nextField = null;
		Building mainbuilding = EntityInitializer.getInstance().getBuilding("Hauptgebäude");
		Building camera = EntityInitializer.getInstance().getBuilding("Camera");
		if(actField.getBuilding().equals(mainbuilding)) {
			nextField = new CorridorField(point, camera, true);
		} else {
			nextField = new CorridorField(point, mainbuilding, true);
		}
		actField.setSouth(nextField);
		nextField.setNorth(actField);
		buildMap(nextField);
	}

	/**
	 * Sucht den nächsten Gebäudedurchgang (ignoriert die übergebene Position)
	 * @param position zu ignorierender Punkt
	 * @return Gebäudedurchgang
	 */
	private Point findGatewayWichIsNotAtPoint(Point position) {
		for (int x = 0; x < levelMatrixList.length; x++) {
			for (int y = 0; y < levelMatrixList[x].length; y++) {
				if (levelMatrixList[y][x] == GATEWAY && position.getX() != x && position.getY() != y) {
					return new Point(x,y);
				}
			}
		}
		return position;
	}

	@Override
	public Field buildWorld() {
		buildLevelMatrix();
		Field startfield = new CorridorField(startPosition, EntityInitializer.getInstance().getBuilding("Hauptgebäude"));
		buildMap(startfield);
		return startfield;
	}
}