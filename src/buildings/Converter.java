package buildings;

import tiles.BuildingEnum;
import game.Map;
/**
 * Converter is a {@link Building} that turns certain resources into other resources.
 * @author Team Rosetta
 *
 */
public class Converter extends Building {

	public Converter(int locX, int locY, int wid, int len, int cap, int time,
			String name, BuildingEnum type) {
		super(locX, locY, wid, len, cap, name, type);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void executeOnBuilding(Map map) {
		// TODO Auto-generated method stub
		
	}
}
