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
				tile = map.getTile(i, 0);
				if (!nightChanged) {
					if (tile.getWeather().getWeatherType() == WeatherEnum.DAY && tile.getWest().getWeather().getWeatherType() == WeatherEnum.NIGHT) {
						border1 = i;
						for (int j = 0; j < map.getSize(); j++) {
							map.getTile(i, j).setWeather(night);
						}
						nightChanged = true;
					}
				}
				if (!dayChanged) {
					if (tile.getWeather().getWeatherType() == WeatherEnum.NIGHT && tile.getWest().getWeather().getWeatherType() == WeatherEnum.DAY) {
						border2 = i;
						for (int j = 0; j < map.getSize(); j++) {
							map.getTile(i, j).setWeather(day);
						}
						dayChanged = true;
					}
				}
			}
		} else {
			for (int j = 0; j < map.getSize(); j++) {
				if (map.getTile(border1, j).getWeather().getWeatherType() == WeatherEnum.DAY) {
					map.getTile(border1, j).setWeather(night);
				}
				if (map.getTile(border2, j).getWeather().getWeatherType() == WeatherEnum.NIGHT) {
					map.getTile(border2, j).setWeather(day);
				}
			}
		}
		border1 = (1 + border1) % map.getSize();
		border2 = (1 + border2) % map.getSize();
	}
	
	public void StormActions(ArrayList<ArrayList<Drone>> droneList, Map map) {
		boolean stormEffect;
		ArrayList<Storm> toBeRemoved = new ArrayList<Storm>();
		for (Storm eachStorm : stormList) {
			eachStorm.setTimeRemaining(eachStorm.getTimeRemaining() - 1);
			for (ArrayList<Drone> eachList : droneList) {
				for (Drone eachDrone : eachList) {
					stormEffect = (eachDrone.getLocationX() > eachStorm.getX() - eachStorm.getSize() &&
						eachDrone.getLocationX() < eachStorm.getX() + eachStorm.getSize() &&
						eachDrone.getLocationY() > eachStorm.getY() - eachStorm.getSize() &&
						eachDrone.getLocationY() < eachStorm.getY() + eachStorm.getSize());
					if (eachStorm.execute(eachDrone, stormEffect, map)) {
						//eachStorm.endStorm(map);
						toBeRemoved.add(eachStorm);
					}
				}				
			}
		}
		for (Storm eachStorm: toBeRemoved) {
			stormList.remove(eachStorm);
		}
	}
	
	public void addTestStorm(Map map) {
		stormList.add(new Storm(20, 20, 5, map));
	}
	
	private class Storm {
		private int x, y, size, timeRemaining, currentRadius;
		private final WeatherTile stormTile = new WeatherTile(WeatherEnum.STORM);
		private final WeatherTile dayTile = new WeatherTile(WeatherEnum.DAY);
		private final WeatherTile nightTile = new WeatherTile(WeatherEnum.NIGHT);
		
		public Storm(int x, int y, int size, Map map) {
			this.x = x;
			this.y = y;
			this.size = size;
			//for (int i = y; i < y + size; i++) {
			//	for (int j = x; j < x + size; j++) {
			//		map.getTile(j, i).setWeather(stormTile);
			//	}
			//}
			map.getTile(x, y).setWeather(stormTile);
			timeRemaining = 2 * size;
			currentRadius = 0;
		}
		
		public boolean execute(Drone drone, boolean stormEffect, Map map) {
			if (size < timeRemaining) {
				currentRadius++;
			} else {
				currentRadius--;
			}
			System.out.println("Current radius: " + currentRadius);
			for (int i = y - size; i < y + size; i++) {
				for (int j = x - size; j < x + size; j++) {
					if (Math.sqrt(Math.pow(y-i, 2) + Math.pow(x-j,  2)) <= currentRadius) {
						map.getTile(j, i).setWeather(stormTile);
					} else {
						map.getTile(j, i).setWeather((map.getTile(j, y - size - 1)).getNorth().getWeather());
					}
				}
			}
			if (stormEffect) {
				drone.setRepair(drone.getRepair() / 20);
			}
			if (timeRemaining <= 0) {
				return true;
			}
			return false;
		}
		
		public int getX() {
			return x;
		}
		
		public int getY() {
			return y;
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
			for (int i = y - size; i < y + size; i++) {
				for (int j = x - size; j < x + size; j++) {
					//map.getTile(i, j).setWeather(nightTile);
					map.getTile(j, i).setWeather((map.getTile(j, y)).getNorth().getWeather());
				}
			}
		}
	}
}
