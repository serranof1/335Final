package model;

import tiles.Tile;
import tiles.WeatherEnum;
import tiles.WeatherTile;
import game.Map;

public class WeatherBehavior {
	private final WeatherTile night = new WeatherTile(WeatherEnum.NIGHT);
	private final WeatherTile day = new WeatherTile(WeatherEnum.DAY);
	int border1;
	int border2;
	
	public WeatherBehavior() {
		border1 = -1;
		border2 = -1;
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
				map.getTile(j, border1).setWeather(night);
				map.getTile(j, border2).setWeather(day);
			}
		}
		border1 = (1 + border1) % map.getSize();
		border2 = (1 + border2) % map.getSize();
	}
}
