package model;

import resources.Resource;
import tiles.Tile;

public class Base extends Building {

	public Base(int x, int y, int width, int length, Resource source, Tile base) {
		super(x, y, width, length, source);
		super.setBuildSite(base);
		// TODO Auto-generated constructor stub
	}

}
