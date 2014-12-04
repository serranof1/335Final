package model;

import java.util.ArrayList;

import tiles.Tile;
import tiles.WeatherEnum;
import tiles.WeatherTile;
import game.Map;

public class WeatherBehavior {
	private final WeatherTile night = new WeatherTile(WeatherEnum.NIGHT);
	private final WeatherTile day = new WeatherTile(WeatherEnum.DAY);
	private ArrayList<Storm> stormList;
	int border1;
	int border2;
	
	public WeatherBehavior() {
		border1 = -1;
		border2 = -1;
		stormList = new ArrayList<Storm>();
	}
	
	public void LightMovement(Map map) {
		Tile tile;
		boolean nightChanged = false;
		boolean dayChanged = false;
		if (border1 == -1 && border2 == -1) {
			for (int i = 0; i < map.getSize(); i++) {
				tile = map.getTile(0, i);
				if (!nightChanged) {
					if (tile.getWeather().getWeatherType() == WeatherEnum.DAY && tile.getWest().getWeather().getWeatherType() == WeatherEnum.NIGHT) {
						border1 = i;
						for (int j = 0; j < map.getSize(); j++) {
							map.getTile(j, i).setWeather(night);
						}
						nightChanged = true;
					}
				}
				if (!dayChanged) {
					if (tile.getWeather().getWeatherType() == WeatherEnum.NIGHT && tile.getWest().getWeather().getWeatherType() == WeatherEnum.DAY) {
						border2 = i;
						for (int j = 0; j < map.getSize(); j++) {
							map.getTile(j, i).setWeather(day);
						}
						dayChanged = true;
					}
				}
			}
		} else {
			for (int j = 0; j < map.getSize(); j++) {
				if (map.getTile(j, border1).getWeather().getWeatherType() == WeatherEnum.DAY) {
					map.getTile(j, border1).setWeather(night);
				}
				if (map.getTile(j, border2).getWeather().getWeatherType() == WeatherEnum.NIGHT) {
					map.getTile(j, border2).setWeather(day);
				}
			}
		}
		border1 = (1 + border1) % map.getSize();
		border2 = (1 + border2) % map.getSize();
	}
	
	public void StormActions(ArrayList<ArrayList<Drone>> droneList, Map map) {
		boolean stormEffect;
		for (Storm eachStorm : stormList) {
			eachStorm.setTimeRemaining(eachStorm.getTimeRemaining() - 1);
			for (ArrayList<Drone> eachList : droneList) {
				for (Drone eachDrone : eachList) {
					stormEffect = (eachDrone.getLocationX() > eachStorm.getXCorner() &&
						eachDrone.getLocationX() < eachStorm.getXCorner() + eachStorm.getSize() &&
						eachDrone.getLocationY() > eachStorm.getYCorner() &&
						eachDrone.getLocationY() < eachStorm.getYCorner() + eachStorm.getSize());
					if (eachStorm.execute(eachDrone, stormEffect)) {
						eachStorm.endStorm(map);
						stormList.remove(eachStorm);
					}
				}				
			}
		}
	}
	
	public void addTestStorm(Map map) {
		stormList.add(new Storm(0, 0, 10, map));
	}
	
	private class Storm {
		private int xCorner, yCorner, size, timeRemaining;
		private final WeatherTile stormTile = new WeatherTile(WeatherEnum.STORM);
		private final WeatherTile dayTile = new WeatherTile(WeatherEnum.DAY);
		private final WeatherTile nightTile = new WeatherTile(WeatherEnum.NIGHT);
		
		public Storm(int x, int y, int size, Map map) {
			xCorner = x;
			yCorner = y;
			this.size = size;
			for (int i = yCorner; i < yCorner + size; i++) {
				for (int j = xCorner; j < xCorner + size; j++) {
					map.getTile(i, j).setWeather(stormTile);
				}
			}
			timeRemaining = 5;
		}
		
		public boolean execute(Drone drone, boolean stormEffect) {
			System.out.println("Time remaining: " + timeRemaining);
			if (stormEffect) {
				drone.setRepair(drone.getRepair() / 20);
			}
			if (timeRemaining <= 0) {
				return true;
			}
			return false;
		}
		
		public int getXCorner() {
			return xCorner;
		}
		
		public int getYCorner() {
			return yCorner;
		}
		
		public int getSize() {
			return size;
		}
		
		public int getTimeRemaining() {
			return timeRemaining;
		}
		
		public void setTimeRemaining(int i) {
			timeRemaining = i;
		}
		
		//Change map to hold a reference to each type of tile, and then we can just grab that
		//from the map, rather than ever making new tiles.
		
		//border1 is initially on the right
		//border2 initially on the left
		public void endStorm(Map map) {
			for (int i = yCorner; i < yCorner + size; i++) {
				for (int j = xCorner; j < xCorner + size; j++) {
					//map.getTile(i, j).setWeather(nightTile);
					map.getTile(i, j).setWeather((map.getTile(yCorner, j)).getNorth().getWeather());
				}
			}
		}
	}
}
