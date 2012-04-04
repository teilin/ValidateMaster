package net.teilin.master.validator;

public class Crew {

	private String name;
	private int heatConsumption;
	private int firstCap;
	private int secondCap;
	
	public Crew(String name,
				int heatConsumption,
				int firstCap,
				int secondCap) {
		this.name = name;
		this.heatConsumption = heatConsumption;
		this.firstCap = firstCap;
		this.secondCap = secondCap;
	}
	
	//Getters and setters
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getHeatConsumption() {
		return heatConsumption;
	}
	public void setHeatConsumption(int heatConsumption) {
		this.heatConsumption = heatConsumption;
	}
	public int getFirstCap() {
		return firstCap;
	}
	public void setFirstCap(int firstCap) {
		this.firstCap = firstCap;
	}
	public int getSecondCap() {
		return secondCap;
	}
	public void setSecondCap(int secondCap) {
		this.secondCap = secondCap;
	}
}
