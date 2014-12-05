package game;

import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import model.Battery;
import model.Drone;
import model.WeatherBehavior;
import pathfinding.AStarPathFinder;
import task.BuildTask;
import task.ChargeTask;
import task.ItemBuildTask;
import task.MoveTask;
import task.RepairTask;
import task.ResourceTask;
import tiles.Tile;
import view.MainGUI;
import buildings.Base;
import buildings.Building;

public class MainGame {

	
	private static Map map;
	

	private static LinkedList<Tile> resourceList = new LinkedList<Tile>();
	private static LinkedList<Building> buildingList = new LinkedList<Building>();

	private static ArrayList<ArrayList<Drone>> allDrones = new ArrayList<ArrayList<Drone>>();
	private static ArrayList<Drone> defaultList = new ArrayList<Drone>();
	private static ArrayList<Drone> builders = new ArrayList<Drone>();
	private static ArrayList<Drone> miners = new ArrayList<Drone>();
	private static ArrayList<Drone> resourceCollectors = new ArrayList<Drone>();
	private static ArrayList<Drone> itemBuilders = new ArrayList<Drone>();
	
	private MainGUI gui;

	private static Drone startDroneOne, startDroneTwo, startDroneThree, startDroneFour, startDroneFive;
	private static Building base, plant1;
	
	private static WeatherBehavior wb = new WeatherBehavior();

	private Drone testMove;
	
	
	/**
	 * @author Cody Jensen
	 * 
	 * A MainGame is where the game loop runs, it will contain/control elements that rely on the game clock(update method, draw method for animation)
	 * 
	 */
	public MainGame() {

		setupVariables();
		mapSpawnCheck();
		initializeDrones();	
		debugMethod();
		
	}
	
	private void debugMethod() {
		
		testMove = new Drone("test", 400.0, map.getTile(30, 30));
		testMove.getTaskList().push(new MoveTask(testMove, map.getTile(10, 10), false));
		defaultList.add(testMove);
		
	}
	
	private void mapSpawnCheck() {
		
		base = new Base(10, 10);
		
		//when a game is started if main base cannot be built generate new map
		if(base.canBuild(map.getTile(10,10)) != true){
			map = new Map(5);
			mapSpawnCheck();
		}
		initializeBuildings();
	}


	private void initializeBuildings() {
		
		map.build(base);
		base.setFinished();
		buildingList.add(base);
		wb.addTestStorm(map);

		
	}
	/**
	 * @author Cody Jensen
	 * 
	 * This is where the starting groups of drones will be added, later a method to add a drone to the map can be added to replace this
	 */
	private static void initializeDrones() {

		
		/*startDroneOne = new Drone("startDroneOne", 400.0, map.getTile(10,15));	
		startDroneTwo = new Drone("startDroneTwo", 400.0, map.getTile(15,15));
		startDroneThree = new Drone("startDroneThree", 400.0, map.getTile(17,17));
		startDroneFour = new Drone("startDroneFour", 400.0, map.getTile(20,21));
		startDroneFive = new Drone("startDroneFive", 400.0, map.getTile(20, 15));
		
		defaultList.add(startDroneOne);
		defaultList.add(startDroneTwo);
		defaultList.add(startDroneThree);
		defaultList.add(startDroneFour);
		defaultList.add(startDroneFive);*/

		allDrones.add(defaultList);
		allDrones.add(miners);
		allDrones.add(builders);
		allDrones.add(resourceCollectors);
		allDrones.add(itemBuilders);
		
	}

	/**
	 * @author Cody Jensen
	 * 
	 * eventually time reliant display windows will go here
	 */
	private void setupVariables() {
		int n = JOptionPane.showConfirmDialog(null, "Do you want to enter a seed?", "Do you want to enter a seed?", JOptionPane.YES_NO_OPTION);
		if (n == JOptionPane.NO_OPTION) {
			map = new Map(6);
		} else {
			String s = JOptionPane.showInputDialog("Enter a long:");
			map = new Map(6, Long.parseLong(s));
		}
	
		
	}
	
	public void assignTasks(){
		buildTasks();
		mineTasks();
		itemBuildTasks();
		resourceTasks();
	}
	
	private void resourceTasks() {
		for (int i = 0; i < resourceCollectors.size(); i++) {
			resourceCollectors.get(i).getTaskList().push(new ResourceTask(resourceCollectors.get(i), buildingList.get(0)));
			checkNeeds(resourceCollectors.get(i));
			
		}
	}
	
	private void buildTasks() {
		
		for (int i = 0; i < buildingList.size(); i++) {
			for (int j = 0; j < builders.size(); j++) {
				if(!buildingList.get(i).isAssigned() && !buildingList.get(i).isFinished()){
						builders.get(j).getTaskList().push(new BuildTask(builders.get(j), buildingList.get(i)));
						checkNeeds(builders.get(j));
						System.out.println("Builder has been assigned a building task.");
				}
			}
		}
	}

	private void mineTasks() {
		//
		
	}
	
	private void itemBuildTasks() {
			Battery battery = new Battery();
		for (int i = 0; i < itemBuilders.size(); i++) {
			
			itemBuilders.get(i).getTaskList().push(new ItemBuildTask(itemBuilders.get(i), battery, map.whereToBuild(battery)));
			checkNeeds(resourceCollectors.get(i));
			
		}
			
	}
	
	private void checkNeeds(Drone drone){
		if(drone.getRepair()<30){
			Building repairAt = map.findNearestRepair(drone.getCurrentTile());
			drone.getTaskList().push(new RepairTask(drone, repairAt, repairAt.getEmptyTile() ));
		}
		if(drone.getPower() < 80){
			Building chargeAt = map.findNearestPower(drone.getCurrentTile());
			drone.getTaskList().push(new ChargeTask(drone, chargeAt, chargeAt.getEmptyTile()));
		}
	}
	
	public void doBuildingTasks() {
		for(Building building: buildingList){
			if(building.isFinished())
				building.executeOnBuilding(map);
		}
	}
	
	

	public void doDroneTasks() {
		// Goes through every drone, checks if they're dead and removes them if they are. 
		// Then it calls execute on every drone's current task.
		System.out.println("**************************************************************");
		for(int i = 0; i < allDrones.size(); i++){
			for(int j = 0; j< allDrones.get(i).size(); j++){
				allDrones.get(i).get(j).executeTaskList(map);
				System.out.println("Repair: " + allDrones.get(i).get(j).getRepair());
			}
		}
		System.out.println("**************************************************************");
	}
	
	public void doDroneTasksTest() {
		
		
		
		
		System.out.println("**************************************************************");
		for(int i = 0; i < allDrones.size(); i++){
			for(int j = 0; j< allDrones.get(i).size(); j++){
				allDrones.get(i).get(j).executeTaskList(map);
			}
		}
		System.out.println("**************************************************************");
	}


	public Map getMap() {
		
		return this.map;
	}

	public void doWeather() {
		wb.LightMovement(map);
		wb.StormActions(allDrones, map);
	}



}

