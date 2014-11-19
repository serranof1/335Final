package tiles;


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
}
