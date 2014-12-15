package tiles;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.imageio.ImageIO;

import resources.Resource;

/**
 * Tile represents each individual cell of the {@link Map}. It holds the different layers, such as ground
 * and resources. It also holds its north, south, east, and west as a node.
 * @author Gateway
 *
 */
public class Tile implements Serializable {
	private TileWrapper[] tileStack = new TileWrapper[4];
	private int x, y;
	private boolean hasDrone, visited;
	transient private Tile north, south, east, west;
	private Color color;
	transient private BufferedImage droneImage;
	//private String droneImage;
	
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
			droneImage = ImageIO.read(new File("images/drone.png"));
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
			droneImage = ImageIO.read(new File("images/drone.png"));
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
	
	/**
	 * This method draws the tile to a Graphics panel.
	 * @param g - the Graphics panel on which to draw.
	 * @param x - the x location to draw
	 * @param y - the y location to draw
	 */
	public void draw(Graphics g, int x, int y) {
		for (int i = 0; i < 3; i++) {
			g.drawImage(tileStack[i].getImage(), x, y, null);
		}
		if (hasDrone) {
			try {
			BufferedImage image = ImageIO.read(new File("images/drone.png"));
			g.drawImage(image, x, y, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
		g.drawImage(tileStack[3].getImage(), x, y, null);
	}
	//Quick method to check if the tile is in sunlight
	/**
	 * This method checks if it is daytime on the tile.
	 * @return boolean
	 */
	public boolean daytime() {
		return (((WeatherTile) tileStack[3]).getWeatherType() == WeatherEnum.DAY);
	}
	
	/**
	 * This method determines if a {@link Drone} can move through the Tile.
	 * @return boolean
	 */
	public boolean canMove() {
		int moveCost = ((GroundTile)tileStack[0]).getMovementCost();
		
		/*if(((BuildingTile)tileStack[2]).getBuildingType() != BuildingEnum.NOTHING) {
			return false;
		}*/
		
		return moveCost <= 1 && !hasDrone; //the canMove will have to be dependent on the tile
		//to which it is moving. We probably want to pass a tile to it
	}
	
	/**
	 * This method determines if a {@link Drone} can move through the Tile, based on the Drone's movementAbility.
	 * @param movementAbility - the {@link Drone}'s movement ability.
	 * @return boolean
	 */
	public boolean canMove(int movementAbility) {
		int moveCost = ((GroundTile)tileStack[0]).getMovementCost();
		return moveCost < movementAbility && !hasDrone;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}
	/*
	private void writeObject(ObjectOutputStream objOut) throws IOException {
		objOut.writeObject(tileStack);
		objOut.writeObject(x);
		objOut.writeObject(y);
		objOut.writeObject(color);
		objOut.writeObject(droneImage);
		objOut.writeObject(hasDrone);
		objOut.writeObject(visited);
	}
	
	private void readObject(ObjectInputStream objIn) throws Exception {
		tileStack = (TileWrapper[])objIn.readObject();
		x = (Integer)objIn.readObject();
		y = (Integer)objIn.readObject();
		color = (Color)objIn.readObject();
		//droneImage = (String)objIn.readObject();
		hasDrone = (boolean)objIn.readObject();
		visited = (boolean)objIn.readObject();
	}
	*/
	public void reloadImages() {
		try {
			droneImage = ImageIO.read(new File("images/drone.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
