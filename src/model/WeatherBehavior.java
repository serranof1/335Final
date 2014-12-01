package model;

import tiles.Tile;
import tiles.WeatherEnum;
import tiles.WeatherTile;
import game.Map;

public class WeatherBehavior {
	static WeatherTile night = new WeatherTile(WeatherEnum.NIGHT);
	static WeatherTile day = new WeatherTile(WeatherEnum.DAY);
	
	public static void LightMovement(Map map) {
		Tile tile;
		boolean nightChanged = false;
		boolean dayChanged = false;
		for (int i = 0; i < map.getSize(); i++) {
			tile = map.getTile(0, i);
			if (!nightChanged) {
				if (tile.getWeather().getWeatherType() == WeatherEnum.DAY && tile.getEast().getWeather().getWeatherType() == WeatherEnum.NIGHT) {
					for (int j = 0; j < map.getSize(); j++) {
						map.getTile(j, i).setWeather(night);
					}
					nightChanged = true;
				}
			}
			if (!dayChanged) {
				if (tile.getWeather().getWeatherType() == WeatherEnum.NIGHT && tile.getEast().getWeather().getWeatherType() == WeatherEnum.DAY) {
					for (int j = 0; j < map.getSize(); j++) {
						map.getTile(j, i).setWeather(day);
					}
					dayChanged = true;
				}
			}
		}
	}
}
