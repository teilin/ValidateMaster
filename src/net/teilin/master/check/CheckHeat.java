package net.teilin.master.check;

public class CheckHeat {
	private int start;
	private int end;
	private int heat;
	
	public CheckHeat(int start, int end, int heat) {
		this.start = start;
		this.end = end;
		this.heat = heat;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public int getHeat() {
		return heat;
	}

	public void setHeat(int heat) {
		this.heat = heat;
	}
}
