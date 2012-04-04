package net.teilin.master.validator;

public class Crane {

	private String name;
	private String location;
	
	public Crane(String name, String location) {
		this.name = name;
		this.location = location;
	}
	
	//Getters and setters
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
}
