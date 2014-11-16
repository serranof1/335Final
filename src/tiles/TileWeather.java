package tiles;

public abstract class TileWeather extends TileWrapper {
	protected float chanceToChange;
	private float getChanceToChange() {
		return chanceToChange;
	}
}
