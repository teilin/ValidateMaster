package net.teilin.master.validator;

import java.io.File;

import net.teilin.master.file.CSV;

public class Program { 

	public static void main(String[] args) throws Exception {
		String dataPath = "data/";
		String solutionPath = "swoh/";
		File solutionDir = new File(solutionPath);
		File[] solutionFiles = solutionDir.listFiles();
		CSV csv = new CSV("/Users/teislindemark/Documents/UiB/Master/resultWithoutHeat.csv");
		
		for(int i=0;i<solutionFiles.length;i++) {
			if(solutionFiles[i].isFile()) {
				String solution = solutionFiles[i].getName();
				String data = solution.replace(".txt", "") + ".xml";
				System.out.println("== Benchmarkfile: " + data + " ==");
				Validator v = new Validator(dataPath + data, solutionPath + solution);
				v.init();
				v.validateSolution();
				csv.generateCSV(v.getProblemName(), v.getLowerBound() , v.getUpperBound(), v.getMakespan(),v.getDependency());
				//v.createGant(data.replace(".xml", ".jpg"));
			}
		}
		csv.closeCSV();
		/*Validator v = new Validator("data/14092011Act50Loc10Crew5Crane2_1.xml", "swh/14092011Act50Loc10Crew5Crane2_1.txt");
		v.init();
		v.validateSolution();
		csv.generateCSV(v.getProblemName(), v.getUpperBound(), v.getLowerBound(), v.getMakespan() );
		csv.closeCSV();*/
	}

}
