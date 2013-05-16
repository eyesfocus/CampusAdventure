package de.mi.hsrm.swt.campusadventure.test;

import de.mi.hsrm.swt.campusadventure.gameenvironment.level.Building;
import de.mi.hsrm.swt.campusadventure.gameenvironment.level.CorridorField;
import de.mi.hsrm.swt.campusadventure.gameenvironment.level.Field;
import de.mi.hsrm.swt.campusadventure.gameenvironment.level.RoomField;
import de.mi.hsrm.swt.campusadventure.helper.Point;

/**
 * Erstellt ein festes Level
 */
public class TestField {
	/* 
	 * SXXX______
	 * _R_X______
	 * ___X______
	 * ___G______
	 * __________
	 * GX________ 
	 */

	private Field startfield;
	private Building building;
	private Building camera;

	
	public TestField() {

		building = new Building("Hauptgebäude", 2f);
		camera = new Building("Camera", 0f);		
		
		startfield = new CorridorField(new Point(0, 0), building);
		startfield.setEast(new CorridorField(new Point(1, 0), building));

		Field act = startfield.getEast();
		act.setWest(startfield);
		act.setSouth(new RoomField(new Point(1, 1), building));
		act.setEast(new CorridorField(new Point(2, 0), building));

		Field p_act = act;
		act = act.getSouth();
		act.setNorth(p_act);
		
		act = p_act.getEast();
		
		act.setWest(p_act);
		act.setEast(new CorridorField(new Point(3, 0), building));
		p_act = act;
		
		act = p_act.getEast();
		act.setWest(p_act);
		act.setSouth(new CorridorField(new Point(3, 1), building));
		p_act = act;
		
		act = p_act.getSouth();
		act.setNorth(p_act);
		act.setSouth(new CorridorField(new Point(3, 2), building));
		
		p_act = act;
		
		act = p_act.getSouth();
		act.setNorth(p_act);
		act.setSouth(new CorridorField(new Point(3, 3), building));
		
		p_act = act;
		act = p_act.getSouth();
		act.setNorth(p_act);
		act.setSouth(new CorridorField(new Point(0, 5), camera));
		
		p_act = act;
		act = p_act.getSouth();
		act.setNorth(p_act);
		act.setEast(new CorridorField(new Point(1, 5), camera));

		
		p_act = act;
		act = p_act.getEast();
		act.setWest(p_act);
	}

	public Field getStartField() {
		return startfield;
	}
}
