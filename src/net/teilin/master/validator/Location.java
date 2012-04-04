package net.teilin.master.validator;

import java.util.List;

public class Location {

	private String name;
	private int heatCapacity;
	private List<Integer> crewNumber;
	private List<Integer> crewLimit;
	
	public Location(String name,
					int heatCap,
					List<Integer> crewNumber,
					List<Integer> crewLimit) {
		this.name = name;
		this.heatCapacity = heatCap;
		this.crewNumber = crewNumber;
		this.crewLimit = crewLimit;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getHeatCapacity() {
		return heatCapacity;
	}

	public void setHeatCapacity(int heatCapacity) {
		this.heatCapacity = heatCapacity;
	}

	public List<Integer> getCrewNumber() {
		return crewNumber;
	}

	public void setCrewNumber(List<Integer> crewNumber) {
		this.crewNumber = crewNumber;
	}

	public List<Integer> getCrewLimit() {
		return crewLimit;
	}

	public void setCrewLimit(List<Integer> crewLimit) {
		this.crewLimit = crewLimit;
	}
}
