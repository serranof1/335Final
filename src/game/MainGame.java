package game;

import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import model.Battery;
import model.Drone;
import model.ListOfLists;
import model.WeatherBehavior;
import task.BuildTask;
import task.ChargeTask;
import task.ItemBuildTask;
import task.MethaneTask;
import task.MoveTask;
import task.RepairTask;
import tiles.BuildingEnum;
import tiles.Tile;
import view.MainGUI;
import buildings.Base;
import buildings.Building;
import buildings.Engineering;
import buildings.Farm;

public class MainGame {

	private static Map map;

	private static LinkedList<Tile> resourceList = new LinkedList<Tile>();
	private static LinkedList<Building> buildingList = new LinkedList<Building>();

	private ListOfLists allDrones;

	private MainGUI gui;

	private static Drone startDroneOne, startDroneTwo, startDroneThree,
			startDroneFour, startDroneFive;

	private static Building base, plant1;

	private static WeatherBehavior wb = new WeatherBehavior();

	private Drone testMove;
	private Drone testMove2;
	private Drone testMove3;

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
		allDrones = new ListOfLists();
		initializeDrones();
		assignAndDoTasks();

	}

	private void mapSpawnCheck() {

		base = new Base(10, 10);

		// when a game is started if main base cannot be built generate new map
		if (base.canBuild(map.getTile(10, 10)) != true) {
			map = new Map(7);
			mapSpawnCheck();
		}
		initializeBuildings();
	}

	private void initializeBuildings() {

		map.build(base);
		base.setFinished();
		buildingList.add(base);
		Building farmTest = new Farm(3, 3);
		map.build(farmTest);
		farmTest.setFinished();
		buildingList.add(farmTest);
		wb.addTestStorm(map);

	}

	/**
	 * @author Cody Jensen
	 * 
	 *         This is where the starting groups of drones will be added, later
	 *         a method to add a drone to the map can be added to replace this
	 */
	private void initializeDrones() {
		testMove = new Drone("test1", 400.0, map.getTile(30, 30));
		testMove2 = new Drone("test2", 400.0, map.getTile(10, 30));
		testMove3 = new Drone("test3", 400.0, map.getTile(10, 10));

		testMove.getTaskList().push(
				new MoveTask(testMove, map.getTile(10, 10), false));
		testMove2.getTaskList().push(
				new MoveTask(testMove2, map.getTile(10, 10), false));

		allDrones.addNewDrone(testMove);
		allDrones.addNewDrone(testMove2);
		allDrones.addNewDrone(testMove3);

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

	public void assignAndDoTasks() {
		resourceTasks();
		buildTasks();
		mineTasks();
		itemBuildTasks();
		doDroneTasks();
		checkNeeds();
	}

	public void checkNeeds() {
		for (int i = 0; i < allDrones.size(); i++) {
			for (int j = 0; j < allDrones.get(i).size(); j++) {
				Drone drone = allDrones.get(i).get(j);
				if (drone.getRepair() < 30) {
					Building repairAt = map.findNearest(drone.getCurrentTile(),	BuildingEnum.ENGINEERING);
					drone.getTaskList().push(new RepairTask(drone, repairAt, repairAt.getEmptyTile()));
				}
				if (drone.getGas() < 50) {
					Building cookAt = map.findNearest(drone.getCurrentTile(), BuildingEnum.METHANEPLANT);
					drone.getTaskList().push(new MethaneTask(drone, cookAt, cookAt.getEmptyTile()));
				}
				if (drone.getPower() < 80) {
					Building chargeAt = map.findNearest(drone.getCurrentTile(), BuildingEnum.POWERPLANT);
					drone.getTaskList().push(new ChargeTask(drone, chargeAt, chargeAt.getEmptyTile()));
				}
			}
		}
	}

	private void resourceTasks() {
		// for (int i = 0; i < resourceCollectors.size(); i++) {
		// resourceCollectors.get(i).getTaskList().push(new
		// DepositTask(resourceCollectors.get(i), buildingList.get(0)));
		// checkNeeds(resourceCollectors.get(i));
		//
		// }
	}

	private void buildTasks() {
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
	}

	private void mineTasks() {

	}

	private void itemBuildTasks() {
		// Battery battery = new Battery();
		// for (int i = 0; i < itemBuilders.size(); i++) {
		// itemBuilders.get(i).getTaskList().push(new
		// ItemBuildTask(itemBuilders.get(i), battery,
		// map.whereToBuild(battery)));
		// }

	}

	public void doDroneTasks() {
		// Goes through every drone, checks if they're dead and removes them if
		// they are.
		// Then it calls execute on every drone's current task.
		System.out
				.println("**************************************************************");
		for (int i = 0; i < allDrones.size(); i++) {
			for (int j = 0; j < allDrones.get(i).size(); j++) {
				allDrones.get(i).get(j).executeTaskList(map);
			}
		}
		System.out
				.println("**************************************************************");
	}

	public void doBuildingTasks() {
		for (Building building : buildingList) {
			if (building.isFinished())
				building.executeOnBuilding(map);
		}
	}

	public void doWeather() {
		wb.LightMovement(map);
		wb.StormActions(allDrones, map);
		wb.addStorm(map);
	}

	public Map getMap() {

		return this.map;
	}

	public boolean checkWin() {
		return map.getTerraformed() > 80;
	}

	public boolean checkLose() {
		return allDrones.numberOfDrones() > 0;
	}

	public Drone createDrone() {
		Base base = (Base) buildingList.get(0);
		Engineering factory = null;
		for (int i = 0; i < buildingList.size(); i++) {
			if (buildingList.get(0).getTypeOfBuilding() == BuildingEnum.ENGINEERING) {
				factory = (Engineering) buildingList.get(i);
				break;
			}
		}
		if (base.getPower() >= 400 || base.getIron() >= 3000
				|| base.getMethane() >= 400) {
			Drone newDrone = new Drone("NewDrone", 400, factory.getEmptyTile());
			return newDrone;
		}
		return null;
	}
}
