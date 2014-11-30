package model;

import game.Map;

import java.util.ArrayList;

import resources.Resource;
import tiles.BuildingEnum;
import tiles.Tile;

public class SolarPlant extends Building {

	private final static int BUILD_RATE = 10;
	private final static int BUILD_COST = 50;
	private final static int SOLAR_WIDTH = 2;
	private final static int SOLAR_HEIGHT = 2;
	private final static String BUILDING_NAME = "SP";
	private final static int MAX_CAP = 10000;
	

	
	public SolarPlant(int x, int y, Resource source, Tile tile) {
		super(x, y, SOLAR_WIDTH, SOLAR_HEIGHT, BUILDING_NAME);
		//super.setBuildSite(tile);
		super.setCost(BUILD_COST);
		typeOfBuilding = BuildingEnum.POWERPLANT;
		droneList = new ArrayList<Drone>();
		tileList = new ArrayList<Tile>(4);
	}

	public void getBuild() {
		super.build(BUILD_RATE);
	}

	@Override
	public void executeOnBuilding(Map map) {
		// TODO Auto-generated method stub
		for(int i = 0; i<tileList.size(); i++){
			if(tileList.get(i).daytime()){
				inventory+=100;
				if(inventory> MAX_CAP){
					inventory = MAX_CAP;
				}
			}
		}
	}
}
