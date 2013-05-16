package de.mi.hsrm.swt.campusadventure.repository;

import java.util.ArrayList;
import java.util.List;

import de.mi.hsrm.swt.campusadventure.exception.NoSuchBuildingException;
import de.mi.hsrm.swt.campusadventure.exception.NoSuchEntityException;
import de.mi.hsrm.swt.campusadventure.gameenvironment.entity.EnergyItem;
import de.mi.hsrm.swt.campusadventure.gameenvironment.entity.Entity;
import de.mi.hsrm.swt.campusadventure.gameenvironment.entity.EnvironmentItem;
import de.mi.hsrm.swt.campusadventure.gameenvironment.entity.Interaction;
import de.mi.hsrm.swt.campusadventure.gameenvironment.entity.InventoryItem;
import de.mi.hsrm.swt.campusadventure.gameenvironment.entity.Person;
import de.mi.hsrm.swt.campusadventure.gameenvironment.entity.Robot;
import de.mi.hsrm.swt.campusadventure.gameenvironment.level.Building;

/**
 * Hält alle Objektvorlagen, welche in dem Spiel benötigt werden um die Story
 * korrekt darzustellen. Static
 */
public class EntityInitializer {
	private static EntityInitializer instance;
	
	private final String IMAGE_PATH = "img/entities/";
	
	private List<Building> buildings = new ArrayList<Building>();
	private List<Entity> basicEntities = new ArrayList<Entity>();
	private List<Entity> allEntities = new ArrayList<Entity>();
	
	private int maxRobotNumber;

	public static EntityInitializer getInstance() {
		if (instance == null) {
			instance = new EntityInitializer();
		}
		return instance;
	}
	
	public static void resetInstance() {
		instance = null;
	}
	
	/**
	 * Initialisiert die Spielwelt
	 */
	private EntityInitializer() {
		maxRobotNumber = 0;
		initBuildings();
		initEntities();
	}

	/**
	 * Initialisiert die Gebäude der Spielwelt
	 */
	private void initBuildings() {
		buildings.add(new Building("Hauptgebäude", 2f));
		buildings.add(new Building("Camera", 0f));
	}

	/**
	 * Initialisiert die Gegenstande der Spielwelt
	 */
	private void initEntities() {
		// EnvironmentItems
		EnvironmentItem waschbecken = new EnvironmentItem("Waschbecken",
				"Ein Waschbecken! Endlich mal die Gelegenheit, was zu trinken.", IMAGE_PATH + "sink.png", new Interaction(), false);
		EnvironmentItem steckdose = new EnvironmentItem("Steckdose", "Wenn doch nur ein Kabel daran hängen würde...",
				IMAGE_PATH + "outlet.png", new Interaction(), true);
		EnvironmentItem steckdose_kabel = new EnvironmentItem(
				"Steckdose mit Kabel", "Da hängt ein MacBook Ladekabel dran...", IMAGE_PATH + "outletwcable.png", new Interaction(), false);
		EnvironmentItem kotze = new EnvironmentItem("Kotze", "Uäh. Da war wohl doch Chili in der Suppe..", IMAGE_PATH + "vomit.png",
				new Interaction(), true);

		// InventoryItems
		InventoryItem mac_load = new InventoryItem("MacBook (geladen)",
				"Endlich ist das MacBook geladen. Jetzt kannst du deine Emails checken!",  IMAGE_PATH + "macload.png", new Interaction(), false);
		InventoryItem mac_unload = new InventoryItem("MacBook (ungeladen)",
				"Mist, der Akku von deinem MacBook ist leer. So ist es für nichts zu gebrauchen.", IMAGE_PATH + "macunload.png", new Interaction(), true);
		InventoryItem schraubenzieher = new InventoryItem("Schraubenzieher",
				"Kreuzschlitz...", IMAGE_PATH + "screwdriver.png", new Interaction(), true);
		InventoryItem muenze = new InventoryItem("Münze", "Eine 1€ Münze! Vielleicht ist in der Camera ja heute was im Angebot..",IMAGE_PATH + "coin.png", 
				new Interaction(), true);
		InventoryItem suppe = new InventoryItem("Fleischbällchensuppe", "Mhhhmm, hoffentlich ist kein Chili drin..",
				IMAGE_PATH + "meatballs.png", new Interaction(), true);
		InventoryItem klammer = new InventoryItem("Büroklammer", "Die Büroklammer ist sehr dünn. Perfekt um etwas zu manipulieren..", IMAGE_PATH + "clip.png",
				new Interaction(), true);
		InventoryItem glas = new InventoryItem("Glas", "Ein leeres Glas.", IMAGE_PATH + "glass.png",
				new Interaction(), true);
		InventoryItem wasserglas = new InventoryItem("Wasserglas", "Jetzt ist Wasser im Glas. Vielleicht hat ja jemand Durst.",
				IMAGE_PATH + "glassfull.png", new Interaction(), true);
		InventoryItem ladekabel = new InventoryItem("Ladekabel", "Dein Ladekabel! Zum Glück hast du es gestern hier vergessen!", IMAGE_PATH + "cable.png",
				new Interaction(), true);
		InventoryItem email = new InventoryItem("Email", "Die Email von Herrn Barth könnte man vielleicht irgendwie verschicken. Nur wie?", IMAGE_PATH + "email.png",
				new Interaction(), true);
		InventoryItem mime = new InventoryItem("Mime", "Eine Mime. Super um Emails zu versenden", IMAGE_PATH + "mime.png",
				new Interaction(), true);
		InventoryItem code = new InventoryItem("Deaktivierungscode", "Hier stehen ein paar Zahlen. Vielleicht bringen die ja Glück.",
				IMAGE_PATH + "code.png", new Interaction(), true);
		InventoryItem emp = new InventoryItem("EMP", "Ein EMP! Perfekt!", IMAGE_PATH + "emp.png",
				new Interaction(), true);

		// Persons
		Person mindermann = new Person("Frau Mindermann und Chester", "Frau Mindermann steht mit Chester mal wieder untätig rum.",
				IMAGE_PATH + "mindermann.png", new Interaction(), true, "Chester hat so furchtbaren Hunger. Los, hol' ihm mal was!");
		Person weitz = new Person("Herr Weitz", "Herr Weitz hat doch immer etwas zu sagen.", IMAGE_PATH + "weitz.png",
				new Interaction(), false, glas, "Wie Sie sehen, sehen Sie nichts.", "Das Glück liegt in meinen Händen. Nämlich in Form DIESES Glases.");
		Person barth = new Person("Meister Barth", "Herr Barth ist wie immer, ganz ungestresst.", IMAGE_PATH + "barth.png",
				new Interaction(), false, email, "Die Email hab ich Ihne doch gegebe. Jetz is alles lässig. ", "Kein Stress. Einfach die Email hier weiterleite un scho is alles cool.");
		Person camerafrau = new Person("Camerafrau", "Die nette Kamerafrau hat heute noch niemandem etwas zu essen verkauft.", IMAGE_PATH + "camerafrau.png",
				new Interaction(), false, "Heute haben wir leckere Fleischsuppe im Angebot");

		// Robots
		Robot r_schrauben = new Robot(
				"Schraubenroboter",
				"Ein Roboter, der aus vielen Einzelteilen besteht und entsprechend viele Schrauben hat",
				IMAGE_PATH + "robot_screw.png", new Interaction());
		Robot r_klammer = new Robot(
				"Kabelroboter",
				"Ein Roboter aus dessen Rücken ziemliche viele Kabel heraus hängen.",
				IMAGE_PATH + "robot_clip.png", new Interaction());
		Robot r_wasser = new Robot("Lochroboter",
				"Ein Roboter mit einer Öffnung an der Schädeldecke", IMAGE_PATH + "robot_hole.png",
				new Interaction());
		Robot r_emp = new Robot(
				"Perfekter Roboter",
				"Dieser Roboter ist einfach makelos. Du musst schon schweres Geschütz auffahren um ihn klein zu kriegen",
				IMAGE_PATH + "robot_emp.png", new Interaction());
		Robot r_code = new Robot("Displayroboter",
				"Der Roboter hat an seinem Rücken ein Eingabedisplay", IMAGE_PATH + "robot_code.png",
				new Interaction());

		// EnergyItems
		EnergyItem kitkat = new EnergyItem("KitKat Chunky", -20f, true);
		EnergyItem booklet_good = new EnergyItem("gutes Booklet", -10f, true);
		EnergyItem booklet_normal = new EnergyItem("normales Booklet", -1f, true);
		EnergyItem booklet_bad = new EnergyItem("grottiges Booklet", +20f, true);
		EnergyItem nerdglasses = new EnergyItem("Nerdbrille", +2f, false);

		// Alle Interaktionen setzen
		mindermann.addInteraction(suppe, kotze);
		camerafrau.addInteraction(muenze, suppe);
		waschbecken.addInteraction(glas, wasserglas);
		steckdose.addInteraction(ladekabel,	steckdose_kabel);
		steckdose_kabel.addInteraction(mac_unload, mac_load);
		List<Entity> kotzeResults = new ArrayList<Entity>();
		kotzeResults.add(klammer);
		kotzeResults.add(glas);
		kotze.addInteraction(wasserglas, kotzeResults);
		mac_load.addInteraction(email, mime);
		mac_load.addInteraction(mime, code);
		mac_unload.addInteraction(steckdose_kabel, mac_load);
		schraubenzieher.addInteraction(r_schrauben);
		muenze.addInteraction(camerafrau, suppe);
		suppe.addInteraction(mindermann, kotze);
		klammer.addInteraction(r_klammer);
		glas.addInteraction(waschbecken, wasserglas);
		wasserglas.addInteraction(kotze, kotzeResults);
		wasserglas.addInteraction(r_wasser, glas);
		ladekabel.addInteraction(steckdose,	steckdose_kabel);
		email.addInteraction(mac_load,mime);
		mime.addInteraction(mac_load, code);
		code.addInteraction(r_code);
		emp.addInteraction(r_emp);
		r_schrauben.addInteraction(schraubenzieher);
		r_klammer.addInteraction(klammer);
		r_wasser.addInteraction(wasserglas, glas);
		r_emp.addInteraction(emp);
		r_code.addInteraction(code);

		// Entity's zuweisen
		basicEntities.add(mindermann);
		basicEntities.add(camerafrau);
		basicEntities.add(weitz);
		basicEntities.add(barth);
		basicEntities.add(waschbecken);
		basicEntities.add(steckdose);
		basicEntities.add(schraubenzieher);
		basicEntities.add(muenze);
		basicEntities.add(ladekabel);
		basicEntities.add(emp);
		basicEntities.add(r_code);
		basicEntities.add(r_emp);
		basicEntities.add(r_klammer);
		basicEntities.add(r_schrauben);
		basicEntities.add(r_wasser);
		basicEntities.add(kitkat);
		basicEntities.add(booklet_bad);
		basicEntities.add(booklet_good);
		basicEntities.add(booklet_normal);
		basicEntities.add(nerdglasses);

		allEntities.addAll(basicEntities);
		allEntities.add(code);
		allEntities.add(email);
		allEntities.add(wasserglas);
		allEntities.add(glas);
		allEntities.add(mime);
		allEntities.add(steckdose_kabel);
		allEntities.add(mac_load);
		allEntities.add(suppe);
		allEntities.add(klammer);
		allEntities.add(kotze);
		allEntities.add(mac_unload);
		
		setMaxRobotNumber();
	}
	
	private void setMaxRobotNumber() {
		for (Entity e : allEntities) {
			if(e instanceof Robot) {
				maxRobotNumber += 1;
			}
		}
	}
	
	public int getMaxRobotNumber() {
		return maxRobotNumber;
	}

	/**
	 * Gibt das Entity-Objekt mit dem agegenbenen Namen zurück wenn es existiert.
	 * 
	 * @param key Name des gesuchten Entities
	 * @return Entity
	 */
	public Entity getEntity(String key) throws NoSuchEntityException {
		for (Entity item : allEntities)
			if (item.getName().equalsIgnoreCase(key))
				return item;
		throw new NoSuchEntityException("Kein Entity mit diesem Namen ("+key+") gefunden");
	}

	/**
	 *  Gibt das Building-Objekt mit dem agegenbenen Namen zurück wenn es existiert.
	 *  
	 * @param key Name des gesuchten Buildings
	 * @return Building
	 */
	public Building getBuilding(String key) throws NoSuchBuildingException {
		for (Building building : buildings)
			if (building.getName().equalsIgnoreCase(key))
				return building;
		throw new NoSuchBuildingException("Kein Geäude mit diesem Namen ("+key+") gefunden!");
	}
	
	
	/**
	 * Gibt eine Liste von Entity-Objektens zurück, 
	 * welche zum Start des Spiels vorhanden sein müssen.
	 * 
	 * @return Liste von Entities
	 */
	public List<Entity> getBasicEntities() {
		return basicEntities;
	}
}