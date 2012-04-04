package net.teilin.master.file;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSV {
	private String filename;
	private FileWriter writer;
	
	public CSV(String filename) throws IOException {
		this.filename = filename;
		this.writer = new FileWriter(this.filename);
		this.writer.append("Benchmark");
		this.writer.append(",");
		this.writer.append("Theoretical lower bound");
		this.writer.append(",");
		this.writer.append("Theoretical upper bound");
		this.writer.append(",");
		this.writer.append("Makespan");
		this.writer.append(",");
		this.writer.append("Dependency");
		this.writer.append("\n");
	}
	
	public void generateCSV(String name, int uBound, int lbound, int ms, String dep) throws IOException {
		this.writer.append(name);
		this.writer.append(",");
		this.writer.append(Integer.toString(uBound));
		this.writer.append(",");
		this.writer.append(Integer.toString(lbound));
		this.writer.append(",");
		this.writer.append(Integer.toString(ms));
		this.writer.append(",");
		this.writer.append(dep);
		this.writer.append("\n");
	}
	
	public void closeCSV() throws IOException {
		this.writer.flush();
		this.writer.close();
	}
}
