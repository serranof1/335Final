package tiles;

import java.awt.Image;

/**
 * WeatherTile represents the layer of a {@link Tile} that has weather, such as day or a storm.
 * @author Team Rosetta
 *
 */
public class WeatherTile extends TileWrapper {
	
	private WeatherEnum weather;
	
	public WeatherTile(WeatherEnum weather) {
		this.weather = weather;
	}
	@Override
	public String drawTextForm() {
		return weather.getTextRepresentation();
	}
	
	public int getDarkness() {
		return weather.getDarkness();
	}
	@Override
	public Image getImage() {
		return weather.getImage();
	}
	
	public WeatherEnum getWeatherType() {
		return weather;
	}
	
	public void setWeatherType(WeatherEnum weather) {
		this.weather = weather;
	}
}
