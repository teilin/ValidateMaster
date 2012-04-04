package net.teilin.master.validator;

public class Activity {
	
	private String name;
	private int start;
	private int duration;
	private String crew;
	private String location;
	private String crane;
	private String dependency;
	
	public Activity(String name,
					int start,
					int duration,
					String crew,
					String location,
					String crane,
					String dependency) {
		this.name = name;
		this.start = start;
		this.duration = duration;
		this.crew = crew;
		this.location = location;
		this.crane = crane;
		this.dependency = dependency;
	}
	
	//Getters and setters
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public String getCrew() {
		return crew;
	}
	public void setCrew(String crew) {
		this.crew = crew;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getCrane() {
		return crane;
	}
	public void setCrane(String crane) {
		this.crane = crane;
	}
	public String getDependency() {
		return dependency;
	}
	public void setDependency(String dependency) {
		this.dependency = dependency;
	}
	
	
}
