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

	private ArrayList<Drone> defaultList, builders, resourceCollectors,
			itemBuilders;
	private int numDrones;

	public ListOfLists() {
		
		defaultList = new ArrayList<Drone>();
		builders = new ArrayList<Drone>();
		resourceCollectors = new ArrayList<Drone>();
		itemBuilders = new ArrayList<Drone>();

		add(defaultList);
		add(builders);
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
		for (int i = 0; i < this.size(); i++) {
			for (int j = 0; j < this.get(i).size(); j++) {

				if (this.get(i).get(j).getName() == drone.getName()) {
					this.get(i).remove(j);
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
		for (int i = 0; i < this.size(); i++) {
			for (int j = 0; j < this.get(i).size(); j++) {

				if (this.get(i).get(j).getName() == dead.getName()) {
					this.get(i).remove(j);
					numDrones--;
				}
			}
		}
	}

	public String getDroneInformation(int x, int y){
		String result = new String();
		System.out.println("alldrones size: " +this.size());
		for (int i = 0; i < this.size(); i++) {
			System.out.println("TEST2");
			for (int j = 0; j < this.get(i).size(); j++) {
				
				Drone currentDrone = this.get(i).get(j);
				System.out.println("TEST3");
				if(currentDrone.getLocationX() == x && currentDrone.getLocationY() == y){
					
					result = currentDrone.toString();
				} else {
					result = "found no drone";
				}
			}
		}
		return result;

	}

	public ArrayList<Drone> get(String listName) {
		switch (listName) {
		case "defaultList": return get(0);
		case "builders": return get(1);
		case "resourceCollectors": return get(2);
		case "itemBuilders": return get(3);
		default: System.out.println("Not an available list"); return null;
		}
	}
}
