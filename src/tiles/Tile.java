package tiles;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import resources.Resource;

public class Tile {
	private TileWrapper[] tileStack = new TileWrapper[4];
	private int x, y;
	private boolean hasDrone, visited;
	private Tile north, south, east, west;
	private Color color;
	private BufferedImage droneImage;
	
	public Tile(TileWrapper[] tileStack) {
		this.tileStack = tileStack;
		color = ((ResourceTile) tileStack[1]).getColor();
		int n = ((WeatherTile) tileStack[3]).getDarkness();
		if (n < 0) {
			color = color.brighter();
		} else {
			for (int i = 0; i < n; i++) {
				color = color.darker();
			}
		}
		try {
			droneImage = ImageIO.read(new File("images/drone2.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		hasDrone = false;
		visited = false;
	}
	
	public Tile(TileWrapper ground, TileWrapper resource, TileWrapper building, TileWrapper weather) {
		tileStack[0] = ground;
		tileStack[1] = resource;
		tileStack[2] = building;
		tileStack[3] = weather;
		color = ((ResourceTile) tileStack[1]).getColor();
		int n = ((WeatherTile) tileStack[3]).getDarkness();
		if (n < 0) {
			color = color.brighter();
		} else {
			for (int i = 0; i < n; i++) {
				color = color.darker();
			}
		}
		try {
			droneImage = ImageIO.read(new File("images/drone2.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		hasDrone = false;
	}
	
	public Tile(TileWrapper ground, TileWrapper resource, TileWrapper building, TileWrapper weather, BufferedImage image) {
		droneImage = image;
		tileStack[0] = ground;
		tileStack[1] = resource;
		tileStack[2] = building;
		tileStack[3] = weather;
		color = ((ResourceTile) tileStack[1]).getColor();
		int n = ((WeatherTile) tileStack[3]).getDarkness();
		if (n < 0) {
			color = color.brighter();
		} else {
			for (int i = 0; i < n; i++) {
				color = color.darker();
			}
		}
		hasDrone = false;
	}
	
	public void setGround(GroundTile g){tileStack[0]=g;}
	public void setResource(ResourceTile r){tileStack[1]=r;}
	public void setBuilding(BuildingTile b){tileStack[2]=b;}
	public void setWeather(WeatherTile w){tileStack[3]=w;}
	public void setHasDrone(Boolean b){hasDrone=b;}
	public void setNorth(Tile t){north = t;}
	public void setSouth(Tile t){south = t;}
	public void setEast(Tile t){east = t;}
	public void setWest(Tile t){west = t;}
	public void setX(int x){this.x = x;}
	public void setY(int y){this.y = y;}
	public void setTileStack(TileWrapper[] tw){tileStack = tw;}
	public void setColor(Color c){color = c;}
	public void setImage(BufferedImage i){droneImage = i;}
	
	public GroundTile getGround(){return (GroundTile)tileStack[0];}
	public ResourceTile getResource(){return (ResourceTile)tileStack[1];}
	public BuildingTile getBuilding(){return (BuildingTile)tileStack[2];}
	public WeatherTile getWeather(){return (WeatherTile)tileStack[3];}
	public boolean getHasDrone(){return hasDrone;}
	public Tile getNorth(){return north;}
	public Tile getSouth(){return south;}
	public Tile getEast(){return east;}
	public Tile getWest(){return west;}
	public int getX(){return x;}
	public int getY(){return y;}
	public TileWrapper[] getTileStack(){return tileStack;}
	public Color getColor(){return color;}
	public BufferedImage getImage(){return droneImage;}
	
	public ResourceEnum gather() {return ((ResourceTile) tileStack[1]).gather();}
	public boolean hasResource() {return ((ResourceTile) tileStack[1]).getResource() != ResourceEnum.NOTHING;}
	
	//We will probably want a better way to draw the string format, since this has potentially weird priorty
	//ie, weather > building > resource > ground
	//This doesn't work super well if we have a mineral on a mountain; we won't all of that info, but we only
	//get that there is a mineral
	public String drawTextForm() {
		if (hasDrone) {
			return "@";
		} else {
			return (tileStack[2].drawTextForm() + tileStack[0].drawTextForm()).substring(0, 1);
		}
	}
	
	public void draw(Graphics g, int x, int y) {
		for (int i = 0; i < 3; i++) {
			g.drawImage(tileStack[i].getImage(), x, y, null);
		}
		if (hasDrone) {
			g.drawImage(droneImage, x, y, null);
		}
		g.drawImage(tileStack[3].getImage(), x, y, null);
	}
	//Quick method to check if the tile is in sunlight
	public boolean daytime() {
		return (((WeatherTile) tileStack[3]).getWeatherType() == WeatherEnum.DAY);
	}
	
	public boolean canMove() {
		int moveCost = ((GroundTile)tileStack[0]).getMovementCost();
		
		/*if(((BuildingTile)tileStack[2]).getBuildingType() != BuildingEnum.NOTHING) {
			return false;
		}*/
		
		return moveCost <= 1 && !hasDrone; //the canMove will have to be dependent on the tile
		//to which it is moving. We probably want to pass a tile to it
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}
}
