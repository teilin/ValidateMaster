package net.teilin.master.validator;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import net.teilin.master.gant.GantActivity;
import net.teilin.master.gant.GantCrew;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Validator {
	//Validator variables
	private List<Location> locations = new ArrayList<Location>();
	private List<Crew> crew = new ArrayList<Crew>();
	private List<Crane> crane = new ArrayList<Crane>();
	private List<Activity> activity = new ArrayList<Activity>();
	
	//Temp arrays
	private HashMap actTmp = new HashMap();
	private HashMap craneTmp = new HashMap();
	private Set setAct;
	private Set setCrane;
	
	private Scanner scanner;
	private File solutionFile;
	private File problemFile;
	private String problemXML;
	private String solutionTXT;
	
	public Validator(String problemXML, String solutionTXT) {
		this.problemXML = problemXML;
		this.solutionTXT = solutionTXT;
	}
	
	public void init() throws Exception {
		this.solutionFile = new File(this.solutionTXT);
		this.problemFile = new File(this.problemXML);
		this.scanner = new Scanner(this.solutionFile);
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(this.problemFile);
		doc.getDocumentElement().normalize();
		
		try {
			//Parse solution file
			while(this.scanner.hasNextLine()) {
				processLine(this.scanner.nextLine());
			}
		} finally{
			scanner.close();
		}
		
		try {
			readXML("activity", doc);
			readXML("crane", doc);
			readXML("crew", doc);
			readXML("location", doc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//Count lists
		System.out.println("== Summary of input XML ==");
		System.out.println("Activities: " + this.activity.size());
		System.out.println("Crews: " + this.crew.size());
		System.out.println("Cranes: " + this.crane.size());
		System.out.println("Locations: " + this.locations.size());
	}
	
	private void processLine(String aLine) {
		Scanner scanner = new Scanner(aLine);
		scanner.useDelimiter(",");
		if(scanner.hasNext()) {
			String name = scanner.next();
		    String value = scanner.next();
		    name = getName(name);
		    value = getValue(value);
		    if(name.startsWith("A")) {
		    	this.actTmp.put(name, value);
		    	this.setAct = this.actTmp.entrySet();
		    } else {
		    	this.craneTmp.put(name.replace("crane(", ""), value);
		    	this.setCrane = this.craneTmp.entrySet();
		    }
		} else {
		}
	}
	
	private String getName(String input) {
		return input.replace("start('", "").replace("'", "");
	}
	
	private String getValue(String input) {
		return input.replace("start('Act1', ", "").replace(").", "").replace(" ", "");
	}
	
	private void readXML(String input, Document doc) throws Exception {
		if(this.problemFile.exists()) {
			
			NodeList nodes = null;
			
			if(input == "crew") {
				nodes = doc.getElementsByTagName("activity");
				int actLength = nodes.getLength();
				nodes = doc.getElementsByTagName(input);
				for (int i = 0; i < nodes.getLength()-actLength; i++) {
					Node node = nodes.item(i);
					if (node.getNodeType() == Node.ELEMENT_NODE) {
						Element element = (Element) node;
						String crewName = getValue("name", element);
						int crewFirst = Integer.parseInt(getValue("firstcap", element).replace(" ", ""));
						int crewSecond = Integer.parseInt(getValue("secondcap", element).replace(" ", ""));
						int crewHeat = Integer.parseInt(getValue("heat", element).replace(" ", ""));
						this.crew.add(new Crew(crewName, crewHeat, crewFirst, crewSecond));
					}
				}
			} else if(input == "activity") {
				nodes = doc.getElementsByTagName(input);
				for (int i = 0; i < nodes.getLength(); i++) {
					Node node = nodes.item(i);
					if (node.getNodeType() == Node.ELEMENT_NODE) {
						Element element = (Element) node;
						String name = getValue("name", element);
						String start = (String) this.actTmp.get(getValue("name", element));
						int startAct = Integer.parseInt(start);
						int duration = Integer.parseInt(getValue("duration", element).replace(" ", ""));
						String crew = getValue("crew", element);
						String location = getValue("location", element);
						String craneStr = getValue("crane", element).replace(" ", "");
						if(craneStr.equals("yes")) {
							craneStr = (String) this.craneTmp.get(name);
						}
						String dependency = getValue("dependency", element);
						this.activity.add(new Activity(name, startAct, duration, crew, location, craneStr, dependency));
					}
				}
			} else if(input == "crane") {
				nodes = doc.getElementsByTagName("activity");
				int actLength = nodes.getLength();
				nodes = doc.getElementsByTagName(input);
				for (int i = 0; i < nodes.getLength()-actLength; i++) {
					Node node = nodes.item(i);
					if (node.getNodeType() == Node.ELEMENT_NODE) {
						Element element = (Element) node;
						String name = getValue("name", element);
						String location = getValue("location", element);
						this.crane.add(new Crane(name, location));
					}
				}
			} else if(input == "location") {
				List<Integer> tmp = new ArrayList<Integer>();
				nodes = doc.getElementsByTagName("activity");
				int actLength = nodes.getLength();
				nodes = doc.getElementsByTagName(input);
				for(int i=0;i < nodes.getLength()-actLength-2; i++) {
					Node node = nodes.item(i);
					if(node.getNodeType() == Node.ELEMENT_NODE) {
						Element element = (Element) node;
						String name = getValue("name", element);
						int heatCap = Integer.parseInt(getValue("heatcapacity", element).replace(" ", ""));
						this.locations.add(new Location(name, heatCap, tmp, tmp));
					}
				}
			}
		}
	}
	
	private static String getValue(String tag, Element element) {
		NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
		Node node = (Node) nodes.item(0);
		return node.getNodeValue();
		
	}
	
	public void validateSolution() {
		System.out.println("== Validation ==");
		//Check dependency
		if(checkDep()) System.out.println("Dependency: OK!");
		else System.out.println("Dependency: BREAK!");
		//Check heat
		if(checkHeat()) System.out.println("Heat: OK!");
		else System.out.println("Heat: BREAK!");
		System.out.println("Makespan: "+getMakespan());
		//Check crane constraints
		craneConstraints();
	}
	
	private int getActNumber(String actName) {
		if(actName.startsWith("Act")) return Integer.parseInt(actName.replace("Act", ""));
		else return 0;
	}
	
	private int getCraneNumber(String craneName) {
		return Integer.parseInt(craneName.replace("crane", ""));
	}
	
	private int getCrewNumber(String crewName) {
		return Integer.parseInt(crewName.replace("crew", ""));
	}
	
	private boolean checkDep() {
		int check = 0;
		for(int i=0;i < this.activity.size();i++) {
			int tmpName = getActNumber(this.activity.get(i).getName());
			int tmpDep = getActNumber(this.activity.get(i).getDependency());
			if(tmpDep != 0) {
				int startAct = this.activity.get(i).getStart();
				int depStart = this.activity.get(tmpDep-1).getStart();
				if(startAct < depStart) check += 1;
			}
		}
		if(check > 0) return false;
		else return true;
	}
	
	private boolean checkCrew(String crewName, int num) {
		if(crew.get(num).getName().equals(crewName)) return true;
		else return false;
	}
	
	private boolean checkHeat() {
		int checkHeat = 0;
		
		return true;
	}
	
	private void craneConstraints() {
		int craneMax = getCraneNumber(this.crane.get(this.crane.size()-1).getName());
		int crewMax = getCrewNumber(this.crew.get(this.crew.size()-1).getName());
		int[] actDurCrane = new int[craneMax];
		int[] durActCrew = new int[crewMax];
		int[] capCrew = new int[crewMax];
		System.out.println("== Crane constraints ==");
		for(int cr=1;cr<=craneMax;cr++) {
			actDurCrane[cr-1] = 0;
			for(int i=0;i<this.activity.size();i++) {
				if(this.activity.get(i).getCrane().equals("crane" + cr)) {
					actDurCrane[cr-1] += this.activity.get(i).getDuration();
				}
			}
			System.out.println("Sum dur(Act)->Crane" + cr + ": " + actDurCrane[cr-1]);
		}
		for(int cr=1;cr<=crewMax;cr++) {
			durActCrew[cr-1] = 0;
			capCrew[cr-1] = 0;
			for(int i=0;i<this.activity.size();i++) {
				if(this.activity.get(i).getCrew().equals("crew" + cr)) {
					durActCrew[cr-1] += this.activity.get(i).getDuration();
				}
			}
			for(int i=0;i<this.crew.size();i++) {
				if(this.crew.get(i).getName().equals("crew" + cr)) {
					capCrew[cr-1] += this.crew.get(i).getFirstCap();
				}
			}
			System.out.println("Sum dur(Act)->Crew"+cr+" / Cap(Crew"+cr+"): " + durActCrew[cr-1] / capCrew[cr-1]);
		}
	}
	
	public String getProblemName() {
		String tmp = this.problemXML;
		tmp = tmp.replace("data/", "");
		tmp = tmp.replace(".xml", "");
		return tmp;
	}
	
	public String getDependency() {
		if(checkDep()) return "OK!";
		else return "BREAK!";
	}
	
	public int getLowerBound() {
		int[] lowerBound = new int[crew.size()];
		
		for(int i=0; i<crew.size(); i++) {
			int cLoad = 0;
			int cRelload = 0;
			for(int j=0; j<activity.size(); j++) {
				if(activity.get(j).getCrew().equals(crew.get(i).getName())) {
					cLoad += activity.get(j).getDuration();
				}
			}
			cRelload = cLoad / crew.get(i).getFirstCap();
			lowerBound[i] = cRelload;
		}
		
		return getMaxValue(lowerBound);
	}
	
	public int getUpperBound() {
		int cUb = 0;
		for(int i=0;i<activity.size();i++) {
			cUb += activity.get(i).getDuration();
		}
		return cUb;
	}
	
	public int getMakespan() {
		int makespan = 0;
		for(int i=0;i<this.activity.size();i++) {
			int tmpMakespan = 0;
			tmpMakespan = this.activity.get(i).getStart() + this.activity.get(i).getDuration();
			if(makespan < tmpMakespan) makespan = tmpMakespan;
		}
		return makespan;
	}
	
	private static final int getMaxValue(int[] array) {
		int max = array[0];
		for(int i=1;i<array.length;i++) {
			if(array[i] > max) {
				max = array[i];
			}
		}
		return max;
	}
	
	public void createGant(String fileName) throws ParseException {
		GantActivity g = new GantActivity();
		g.run(activity, fileName);
		GantCrew c = new GantCrew();
		c.run(activity, crew, fileName);
	}
}
