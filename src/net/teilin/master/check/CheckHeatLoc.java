package net.teilin.master.check;

import java.util.List;

public class CheckHeatLoc {
	private String locationName;
	private List<CheckHeat> heat;
	private int heatCap;
	private int sumHeat;
	
	public CheckHeatLoc(String name, CheckHeat[] heat, int cap) {
		this.locationName = name;
		this.heatCap = cap;
		this.sumHeat = 0;
		for(int i=0;i<heat.length;i++) {
			this.heat.add(heat[i]);
		}
	}
	
	private void checkHeat() {
		
	}
}
