package model;

import game.Map;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import task.BuildTask;
import task.ChargeTask;
import task.DepositTask;
import task.ItemBuildTask;
import task.MethaneTask;
import task.RepairTask;
import tiles.BuildingEnum;
import tiles.Tile;
import buildings.Building;

/**
 * This method separates the ArrayList of all the ArrayLists of {@link Drone}s from the {@link MainGame}.
 * @author Team Rosetta
 *
 */
public class ListOfLists extends ArrayList<ArrayList<Drone>> implements Serializable {

	private ArrayList<ArrayList<Drone>> allDrones;
	private ArrayList<Drone> defaultList, builders, miners, resourceCollectors,
			itemBuilders;
	private int numDrones;

	public ListOfLists() {
		defaultList = new ArrayList<Drone>();
		builders = new ArrayList<Drone>();
		miners = new ArrayList<Drone>();
		resourceCollectors = new ArrayList<Drone>();
		itemBuilders = new ArrayList<Drone>();

		add(defaultList);
		add(builders);
		add(miners);
		add(resourceCollectors);
		add(itemBuilders);

		numDrones = 0;

	}

	/**
	 * 
	 * @param drone
	 *            - When the down(decrement) arrow is used in the interface, it
	 *            will find and remove the drone from that list and add it to
	 *            the default list so that it may be assigned another task;
	 * @param oldList
	 *            - The list the the drone was previously in. This is only a
	 *            parameter to make searching faster, if it makes it easier to
	 *            use without the list as a parameter then the other method of
	 *            the same name should be used.
	 * 
	 */
	public void moveToDefaultList(Drone drone, ArrayList<Drone> oldList) {
		for (int index = 0; index < oldList.size(); index++) {
			if (oldList.get(index).getName() == drone.getName()) {
				oldList.remove(index);
				defaultList.add(drone);
			}
		}

	}
	/**
	 * This method moves a {@link Drone} to the list of {@link Drone}s doing the default {@link Task}
	 * @param drone - The {@link Drone} to be moved.
	 */
	public void moveToDefaultList(Drone drone) {
		for (int i = 0; i < allDrones.size(); i++) {
			for (int j = 0; j < allDrones.get(i).size(); j++) {

				if (allDrones.get(i).get(j).getName() == drone.getName()) {
					allDrones.get(i).remove(j);
					defaultList.add(drone);
				}
			}
		}

	}
	
	/**
	 * This moves a {@link Drone} from the default list to a new list.
	 * @param drone - The {@link Drone} to be moved.
	 * @param newList - The list to which to move it.
	 */
	public void moveFromDefaultList(Drone drone, ArrayList<Drone> newList) {
		for (int i = 0; i < defaultList.size(); i++) {
			if (defaultList.get(i).getName() == drone.getName()) {
				defaultList.remove(i);
				newList.add(drone);
			}
		}

	}

	public void addNewDrone(Drone drone) {
		defaultList.add(drone);
		numDrones++;
	}

	public int numberOfDrones() {
		return numDrones;
	}

	public void deadDrone(Drone dead) {
		for (int i = 0; i < allDrones.size(); i++) {
			for (int j = 0; j < allDrones.get(i).size(); j++) {

				if (allDrones.get(i).get(j).getName() == dead.getName()) {
					allDrones.get(i).remove(j);
					numDrones--;
				}
			}
		}
	}

	public String getDroneInformation(int x, int y){
		for(ArrayList<Drone> list : allDrones){
			for(Drone currentDrone : list){
				if(currentDrone.getLocationX() == x && currentDrone.getLocationY() == y){
					return currentDrone.toString();
				} else {
					return null;
				}
			
			}
		}
		return null;
	}
	
	public ArrayList<Drone> get(String listName) {
		switch (listName) {
		case "defaultList": return get(0);
		case "builders": return get(1);
		case "miners": return get(2);
		case "resourceCollectors": return get(3);
		case "itemBuilders": return get(4);
		default: System.out.println("Not an available list"); return null;
		}
	}
}
