package de.mi.hsrm.swt.campusadventure.repository;

import java.util.Map;
import java.util.TreeMap;

import javax.swing.ImageIcon;

import de.mi.hsrm.swt.campusadventure.gameenvironment.level.Field;

/**
 * Erstellt ImageIcons für die InGame Ansichten
 */
public class IconBuilder {

	private static IconBuilder instance;
	
	private Map <String, ImageIcon> miIcons;
	private Map <String, ImageIcon> cameraIcons;
	
	private final String miIconPath = "img/view_perspective/mi/";
	private final String cameraIconPath = "img/view_perspective/camera/";
	
	public static IconBuilder getInstance() {
		if (instance == null) {
			instance = new IconBuilder();
		}
		return instance;
	}
	
	private IconBuilder() {
		miIcons = new TreeMap<String, ImageIcon>();
		cameraIcons = new TreeMap<String, ImageIcon>();
		
		initIcons();
	}
	
	/**
	 * Initialisiert eine Map mit den benötigten ImageIcons
	 */
	private void initIcons(){
		//MI Icons
		miIcons.put("floorIcon", new ImageIcon(miIconPath + "gang.png"));
		miIcons.put("floorLeftIcon", new ImageIcon(miIconPath + "gang_links.png"));
		miIcons.put("floorRightIcon", new ImageIcon(miIconPath + "gang_rechts.png"));
		miIcons.put("floorBothIcon", new ImageIcon(miIconPath + "gang_rechts_links.png"));
		miIcons.put("wallIcon", new ImageIcon(miIconPath + "gang_wand.png"));
		miIcons.put("infinitIcon",new ImageIcon(miIconPath + "unendlich.png"));
		//Camera Icons
		cameraIcons.put("floorIcon", new ImageIcon(cameraIconPath + "gang.png"));
		cameraIcons.put("floorLeftIcon", new ImageIcon(cameraIconPath + "gang_links.png"));
		cameraIcons.put("floorRightIcon", new ImageIcon(cameraIconPath + "gang_rechts.png"));
		cameraIcons.put("floorBothIcon", new ImageIcon(cameraIconPath + "gang_rechts_links.png"));
		cameraIcons.put("wallIcon", new ImageIcon(cameraIconPath + "gang_wand.png"));
		cameraIcons.put("infinitIcon",new ImageIcon(cameraIconPath + "unendlich.png"));
		
	}
	
	/**
	 * Holt sich das benötigte ImageIcon anhand des Field
	 * 
	 * @param field - Feld welches dargestellt werden soll
	 * @param iconType - Art des Icons
	 * @return ImageIcon für das angeforderte Feld
	 */
	public ImageIcon getIcon(Field field, String iconType){
		String buildingName = field.getBuilding().getName();
		
		if(buildingName.equals("Hauptgebäude")){
			return miIcons.get(iconType);
		}
		
		if(buildingName.equals("Camera")){
			return cameraIcons.get(iconType);
		}
		
		return null;
	}
}
