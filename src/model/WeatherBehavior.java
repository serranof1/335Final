package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import buildings.Building;
import tiles.Tile;
import tiles.WeatherEnum;
import tiles.WeatherTile;
import game.Map;

/**
 * This class maintains the behavior of weather on the {@link Map}.
 * @author Team Rosetta
 *
 */
public class WeatherBehavior implements Serializable{
	private final WeatherTile night = new WeatherTile(WeatherEnum.NIGHT);
	private final WeatherTile day = new WeatherTile(WeatherEnum.DAY);
	private ArrayList<Storm> stormList;
	int border1;
	int border2;
	Random rand = new Random();
	
	/**
	 * The instance of WeatherBehavior maintains the two day/night borders as well as a list of
	 * any "storms" taking place.
	 */
	public WeatherBehavior() {
		border1 = -1;
		border2 = -1;
		stormList = new ArrayList<Storm>();
	}
	
	/**
	 * This method controls the day/night cycle. Each game loop the day/night border moves one tick over.
	 * @param map - The {@link Map} on which to be drawing day and night.
	 */
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
	
	/**
	 * This method has each Storm do its associated action, typically damaging drones and buildings.
	 * @param droneList - The full list of {@link Drone}s to act upon.
	 * @param map - The {@link Map} to act upon.
	 */
	public void StormActions(ArrayList<ArrayList<Drone>> droneList, Map map) {
		boolean stormEffect;
		ArrayList<Storm> toBeRemoved = new ArrayList<Storm>();
		for (Storm eachStorm : stormList) {
			eachStorm.setTimeRemaining(eachStorm.getTimeRemaining() - 1);
			for (Building eachBuilding : map.getAllBuildings()) {
				stormEffect = (eachBuilding.getLocation().getX() > eachStorm.getX() - eachStorm.getSize() &&
						eachBuilding.getLocation().getX() < eachStorm.getX() + eachStorm.getSize() &&
						eachBuilding.getLocation().getY() > eachStorm.getY() - eachStorm.getSize() &&
						eachBuilding.getLocation().getY() < eachStorm.getY() + eachStorm.getSize());
				eachStorm.execute(eachBuilding, stormEffect);
			}
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
			eachStorm.decrement();
		}
		for (Storm eachStorm: toBeRemoved) {
			stormList.remove(eachStorm);
		}
	}
	
	public void addTestStorm(Map map) {
		//stormList.add(new Storm(1, 1, 6, map));
	}
	
	/**
	 * This method places a storm on the {@link Map}.
	 * @param map - The {@link Map} on which to place a storm.
	 */
	public void addStorm(Map map) {
		if (rand.nextFloat() < .3) {
			int x = rand.nextInt(map.getSize());
			int y = rand.nextInt(map.getSize());
			stormList.add(new Storm(x, y, 4, map));
		} 
	}
	
	private class Storm implements Serializable {
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
		
		public void decrement() {
			if (size < timeRemaining) {
				currentRadius++;
			} else {
				currentRadius--;
			}

//			System.out.println("Current radius: " + currentRadius);
		}
		
		public boolean execute(Drone drone, boolean stormEffect, Map map) {
			/*
			if (size < timeRemaining) {
				currentRadius++;
			} else {
				currentRadius--;
			}*/
			//This will need to be converted to node logic.
			for (int i = y - size; i < y + size; i++) {
				for (int j = x - size; j < x + size; j++) {
					if (Math.sqrt(Math.pow(y-i, 2) + Math.pow(x-j,  2)) <= currentRadius) {
						map.getTile2(j, i).setWeather(stormTile);
					} else {
						map.getTile2(j, i).setWeather((map.getTile2(j, y - size - 1)).getNorth().getWeather());
					}
				}
			}
			if (stormEffect) {
				drone.setRepair(drone.getRepair() / 20);
				drone.setPower(drone.getPower() / 20);
			}
			if (timeRemaining <= 0) {
				return true;
			}
			return false;
		}
		
		public void execute(Building build, boolean stormEffect) {
			if (stormEffect) {
				build.setHealth(build.getHealth() / 20);
			}
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
