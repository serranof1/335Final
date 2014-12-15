package buildings;

import java.util.ArrayList;

import model.Drone;
import game.Map;
import resources.Resource;
import tiles.BuildingEnum;
import tiles.Tile;

/**
 * A Base is a {@link Building} that is automatically placed on the map and is used as a center for 
 * resource storage and drone operations.
 * 
 * @author Team Rosetta
 *
 */
public class Base extends Building {

	private final static String BUILDING_NAME = "B";
	private final static int BASE_WIDTH = 4;
	private final static int BASE_LENGTH = 4;
	private final static int MAX_CAP = 35000;
	
	
	/**
	 * 
	 * @param x - The x location of the Base on the {@link Map}
	 * @param y - The y location of the Base on the {@link Map}
	 */
	public Base(int x, int y) {
		super(x, y, BASE_WIDTH, BASE_LENGTH, MAX_CAP, BUILDING_NAME, BuildingEnum.BASE);
		setMethane(15000);
		setPower(15000);
		setIron(3000);
		setCarbon(2500);
	}
	
	/**
	 * @param map - Each {@link Building} has an executeOnBuilding method to account for any behavior
	 * it needs to do each turn. These behaviors typically affect or need the {@link Map}.
	 */
	@Override
	public void executeOnBuilding(Map map) {
		// TODO Auto-generated method stub
		
	}


	public String getStockPile(){
		
		String stock = new String();
		stock += "METHANE  " +getMethane() +"\n";
		stock += "	ELECTRICITY  " +getPower()+"\n";
		stock += "	IRON  " +getIron()+"\n";
		stock += "	CARBON  " +getCarbon();
		return stock;
		
	}
	
	/**
	 * @param map - Each {@link Building} has a collectResource method to account for any behavior
	 * it needs to do each turn. These behaviors typically affect or need the {@link Map}.
	 */
	public void collectResource(Map map) {
		// TODO Auto-generated method stub
		
	}
}
