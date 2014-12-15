package game;

import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import model.Battery;
import model.Drone;
import model.ListOfLists;
import model.WeatherBehavior;
import task.BuildTask;
import task.ChargeTask;
import task.CollectResourcesTask;
import task.ItemBuildTask;
import task.MethaneTask;
import task.MoveTask;
import task.RepairTask;
import tiles.BuildingEnum;
import tiles.ResourceEnum;
import tiles.Tile;
import view.MainGUI;
import buildings.Base;
import buildings.Building;
import buildings.Engineering;
import buildings.Farm;
import buildings.MethanePlant;
import buildings.SolarPlant;
/**
 * The MainGame controls the game loop, as well as holds objects such as the {@link Map} or each
 * {@link Drone}
 * @author Team Rosetta
 *
 */
public class MainGame implements Serializable {

	private static Map map;

	private static LinkedList<Tile> resourceList = new LinkedList<Tile>();
	private LinkedList<Building> buildingList = new LinkedList<Building>();

	private ListOfLists allDrones;

	private MainGUI gui;
	private int nameInt = 0;

	private static Drone startDroneOne, startDroneTwo, startDroneThree,
			startDroneFour, startDroneFive;

	private Building base, plant1;

	private static WeatherBehavior wb = new WeatherBehavior();

	/**
	 * @author Cody Jensen
	 * 
	 *         A MainGame is where the game loop runs, it will contain/control
	 *         elements that rely on the game clock(update method, draw method
	 *         for animation)
	 * 
	 */
	public MainGame() {

		setupVariables();
		mapSpawnCheck();
		initializeDrones();
		assignAndDoTasks();

	}
	
	public LinkedList<Building> getBuildingList(){
		return buildingList;
	}
	
	/**
	 * @author Cody Jensen
	 * 
	 *         eventually time reliant display windows will go here
	 */
	private void setupVariables() {
		int n = JOptionPane.showConfirmDialog(null,
				"Do you want to enter a seed?", "Do you want to enter a seed?",
				JOptionPane.YES_NO_OPTION);
		if (n == JOptionPane.NO_OPTION) {
			map = new Map(6);
		} else {
			String s = JOptionPane.showInputDialog("Enter a long:");
			map = new Map(6, Long.parseLong(s));
		}
		
	}
	/**
	 * This method attempts to spawn a {@link Base}. If it could not be built, it re-generates the
	 * {@link Map} in an attempt to place the {@link Base}.
	 */
	private void mapSpawnCheck() {

		base = new Base(10, 10);

		// when a game is started if main base cannot be built generate new map
		if (base.canBuild(map.getTile(10, 10)) != true) {
			map = new Map(6);
			mapSpawnCheck();
		}
		initializeBuildings();
	}
	
	/**
	 * This method, at the moment, is primarily a testing method for placing a variety of 
	 * {@link Building}s
	 */
	private void initializeBuildings() {

		map.build(base);
		base.setFinished();
		buildingList.add(base);

		Building farmTest = new Farm(3, 3);
		map.build(farmTest);
		farmTest.setFinished();
		buildingList.add(farmTest);

		Building engineeringTest = new Engineering(10, 3);
		map.build(engineeringTest);
		buildingList.add(engineeringTest);
		engineeringTest.setFinished();

		Building methPlanTest = new MethanePlant(15, 3);
		map.build(methPlanTest);
		buildingList.add(methPlanTest);
		methPlanTest.setFinished();

		plant1 = new SolarPlant(50, 50);
		map.build(plant1);
		buildingList.add(plant1);
		plant1.setFinished();

		wb.addTestStorm(map);

	}

	/**
	 * @author Cody Jensen
	 * 
	 *         This is where the starting groups of drones will be added, later
	 *         a method to add a drone to the map can be added to replace this
	 */
	private void initializeDrones() {
		allDrones = new ListOfLists();
		
		Drone powerTest = new Drone("powerTest", 50, map.getTile(10, 5));
		Drone gasTest = new Drone("gasTest", 120, map.getTile(15, 5));
		gasTest.setGas(20);
		Drone repairTest = new Drone("repairTest", 400, map.getTile(20, 5));
		repairTest.setRepair(20);

		allDrones.addNewDrone(powerTest);
		allDrones.addNewDrone(gasTest);
		allDrones.addNewDrone(repairTest);

	}

	/**
	 * This rolls all the assignment and execution of {@link Drone} behavior into one method.
	 */
	public void assignAndDoTasks() {
		resourceTasks();
//		buildTasks();
		mineTasks();
		itemBuildTasks();
		doDroneTasks();
		checkNeeds();
	}
	
	/**
	 * This method iterates through each {@link Drone} and checks all its needs, power, repair, and gas.
	 */
	public void checkNeeds() {
		for (int i = 0; i < allDrones.size(); i++) {
			for (int j = 0; j < allDrones.get(i).size(); j++) {
				Drone drone = allDrones.get(i).get(j);
				if (drone.getRepair() < 30 && !drone.isRepairing()) {
					Building repairAt = map.findNearest(drone.getCurrentTile(),	BuildingEnum.ENGINEERING);
					drone.getTaskList().push(new RepairTask(drone, repairAt, repairAt.getEmptyTile()));
				}
				if (drone.getGas() < 50 && !drone.isFilling()) {
					Building gasAt = map.findNearest(drone.getCurrentTile(), BuildingEnum.METHANEPLANT);
					drone.getTaskList().push(new MethaneTask(drone, gasAt, gasAt.getEmptyTile()));
					System.out.println("DRONE GETTING GAS");
				}
				if (drone.getPower() < 80 && !drone.isCharging()) {
					Building chargeAt = map.findNearest(drone.getCurrentTile(), BuildingEnum.POWERPLANT);
					drone.getTaskList().push(new ChargeTask(drone, chargeAt, chargeAt.getEmptyTile()));
					System.out.println("DRONE CHARGING");
				}
			}
		}
	}
	
	/**
	 * This method does not do anything right now.
	 */
	private void resourceTasks() {
		ArrayList<Drone> resourceCollectors = allDrones.get("resourceCollectors");
		
		for (Drone drone : resourceCollectors) {
			if(!resourceList.isEmpty()){
				if(!drone.isCollecting()){
					drone.getTaskList().push(new CollectResourcesTask(drone, resourceList, (Base)buildingList.get(0)));
					System.out.println("Resource Task Assigned\n" +resourceList.getFirst().getX() +"   " +resourceList.getFirst().getY() );
					resourceList.removeFirst();
				}
			}
		}
		// for (int i = 0; i < resourceCollectors.size(); i++) {
		// resourceCollectors.get(i).getTaskList().push(new
		// DepositTask(resourceCollectors.get(i), buildingList.get(0)));
		// checkNeeds(resourceCollectors.get(i));
		//
		// }
	}

/*	private void buildTasks() {
		ArrayList<Drone> builders = allDrones.get("builders");
		for (int i = 0; i < buildingList.size(); i++) {
			for (int j = 0; j < builders.size(); j++) {
				if (!buildingList.get(i).isFinished()) {
					builders.get(j)
							.getTaskList()
							.push(new BuildTask(builders.get(j), buildingList
									.get(i)));
				}
			}
		}
	}*/

	private void mineTasks() {

	}

	private void itemBuildTasks() {

	}
	
	/**
	 * This method iterates through each {@link Drone} and executes their assigned {@link Task}.
	 */
	public void doDroneTasks() {
		// Goes through every drone, checks if they're dead and removes them if
		// they are.
		// Then it calls execute on every drone's current task.
		System.out
				.println("**************************************************************");
		for (int i = 0; i < allDrones.size(); i++) {
			for (int j = 0; j < allDrones.get(i).size(); j++) {
				if(allDrones.get(i).get(j).executeTaskList(map)){
				 allDrones.moveToDefaultList(allDrones.get(i).get(j), allDrones.get(i));	
				}
			}
		}
		System.out
				.println("**************************************************************");
	}
	
	/**
	 * This method iterates through each {@link Building} and performs its associated behavior.
	 */
	public void doBuildingTasks() {
		ArrayList<Drone> builders = allDrones.get("builders");
		for (Building building : buildingList) {
			if (building.isFinished()){
				building.executeOnBuilding(map);
			}else if(!building.inProgress()){
				//We need to somehow pick a drone from the builders list
				//that isn't already working on a building
				//drone . push new build task with that building and set its state
				//to be "in progress"
			}	
		}
	}
	
	/**
	 * This method performs weather behavior, such as the day/night cycle and storms.
	 */
	public void doWeather() {
		wb.LightMovement(map);
		wb.StormActions(allDrones, map);
		wb.addStorm(map);
	}

	public Map getMap() {

		return map;
	}

	public boolean checkWin() {
		return map.getTerraformed() > 150;
	}

	public boolean checkLose() {
		return allDrones.numberOfDrones() <= 0;
	}
	
	/**
	 * This method, provided the resources are available, spawns a new {@link Drone} in the
	 * {@link Engineering} {@link Building}
	 * @return Drone - The {@link Drone} that is spawned.
	 */
	public Drone createDrone() {
		
		Base base = (Base) buildingList.get(0);
		Engineering factory = null;
		for (int i = 0; i < buildingList.size(); i++) {
			if (buildingList.get(0).getTypeOfBuilding() == BuildingEnum.ENGINEERING) {
				factory = (Engineering) buildingList.get(i);
				break;
			}
		}
//		if (base.getPower() >= 400 && base.getIron() >= 3000
//				&& base.getMethane() >= 400) {
			
			Drone newDrone = new Drone("Drone: " + nameInt, 400, map.getTile(10, 5));
			allDrones.addNewDrone(newDrone);
			nameInt++;
			return newDrone;
//		}
//		return null;
	}
	
	public ListOfLists getAllDrones(){
		return allDrones;
	}
	
	public WeatherBehavior getWeatherBehavior() {
		return wb;
	}
	
	public void setMap(Map map) {
		this.map = map;
	}
	
	public void setAllDrones(ListOfLists allDrones) {
		this.allDrones = allDrones;
	}
	
	public void setBuildingList(LinkedList<Building> buildingList) {
		this.buildingList = buildingList;
	}
	
	public void setWeatherBehavior(WeatherBehavior wb) {
		this.wb = wb;
	}

	public void gatherResources(Point upperLeft, Point bottomRight) {
		
		System.out.println("gather resources input points:  " +upperLeft.toString() + bottomRight.toString());
		
		for(int i = upperLeft.x; i <= bottomRight.x; i++){
			for(int j = upperLeft.y; j <= bottomRight.y; j++){
				
				if(map.getTile(i, j).getResource().getResource() == ResourceEnum.CARBON || 
						map.getTile(i, j).getResource().getResource() == ResourceEnum.IRON ){
					System.out.println("gather resources add:  " +map.getTile(i, j).getX() +"   " +map.getTile(i, j).getY());
					resourceList.add(map.getTile(i, j));
				}
			}
		}
	}
	
	public void placeBuilding(Building toBeBuilt) {
		map.build(toBeBuilt);
		buildingList.add(toBeBuilt);
		toBeBuilt.setFinished();
	}
	/*
	public void saveGame() {
		saveMap();
	}
	
	private void saveMap() {
		try {
			FileOutputStream fileOut = new FileOutputStream(new File("gameLibrary.dat"));
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
			
			int mapSize = map.getSize();
			objectOut.writeObject(mapSize);
			objectOut.writeObject(map);
			System.out.println("Save Game sucessful");
		} catch (Exception e ) {
			e.printStackTrace();
			System.err.println("An error occured when attempting to save the game");
		}
	}
	
	public void loadGame() {
		loadMap();
	}
	
	private void loadMap() {
		try{
			FileInputStream fileIn = new FileInputStream(new File("gameLibrary.dat"));
			ObjectInputStream objectIn = new ObjectInputStream(fileIn);
			
			int mapSize = (Integer) objectIn.readObject();
//			map.setSize(mapSize);
			map = (Map)objectIn.readObject();
			System.out.println("load map successful");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("An error occured when loading the game");
		}
	}*/
}
