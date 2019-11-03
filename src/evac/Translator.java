package evac;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class Translator {
	
	private InputModel model;
	
	private String nodesFilename;
	private String edgesFilename;
	private String hurricanesFilename;
	
	public Translator(InputModel model, String nodesFilename, String edgesFilename, String hurricanesFilename) {
		this.model = model;
		this.nodesFilename = nodesFilename;
		this.edgesFilename = edgesFilename;
		this.hurricanesFilename = hurricanesFilename;
	}
	
	public boolean translateNodes() {
		CSVParser csvParser = null;
		try {
			csvParser = CSVFormat.DEFAULT.parse(new FileReader(new File(nodesFilename)));
		} catch (FileNotFoundException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
		for (CSVRecord record : csvParser) {
			if (record.getRecordNumber() > 1) {
				if (vibeCheckNode(record)) {
				    int enabled = Integer.valueOf(record.get(0));
				    if (enabled >= 0) {
				    	String name = record.get(1);
				    	float latitude = Float.valueOf(record.get(2));
				    	float longitude = Float.valueOf(record.get(3));
				    	int population = Integer.valueOf(record.get(4));
				    	int capacity = Integer.valueOf(record.get(5));
				    	
				    	System.out.println("ADDING NODE: " + record.toString());
				    	Node n = new Node(name, population, capacity, new Position(latitude, longitude));
				    	model.addNode(n);
				    }
				} else {
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean vibeCheckNode(CSVRecord record) {
		return record.size() == 6
				&& isInt(record.get(0))
				&& isFloat(record.get(2))
				&& isFloat(record.get(3))
				&& isInt(record.get(4))
				&& isInt(record.get(5));
	}
	
	public boolean translateEdges() {
		CSVParser csvParser = null;
		try {
			csvParser = CSVFormat.DEFAULT.parse(new FileReader(new File(edgesFilename)));
		} catch (FileNotFoundException e1) {
			return false;
		} catch (IOException e) {
			return false;
		}
		for (CSVRecord record : csvParser) {
			if (record.getRecordNumber() > 1) {
				if (vibeCheckEdges(record)) {
				    int enabled = Integer.valueOf(record.get(0));
				    if (enabled >= 0) {
				    	Node source = model.nodes.get(record.get(1));
				    	Node destination = model.nodes.get(record.get(2));
				    	float time = Float.valueOf(record.get(3));
				    	float capacity = Float.valueOf(record.get(4));
				    	
				    	if (source != null && destination != null) {
				    		System.out.println("ADDING EDGE: " + record.toString());
				    		Edge e = new Edge(source, destination, time, capacity);
				    		model.addEdge(e);
				    	} else {
				    		return false;
				    	}
				    }
				} else {
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean vibeCheckEdges(CSVRecord record) {
		return record.size() == 5
				&& isInt(record.get(0))
				&& isFloat(record.get(3))
				&& isFloat(record.get(4));
	}
	
	public boolean translateHurricanes() {
		CSVParser csvParser = null;
		try {
			csvParser = CSVFormat.DEFAULT.parse(new FileReader(new File(hurricanesFilename)));
		} catch (FileNotFoundException e1) {
			return false;
		} catch (IOException e) {
			return false;
		}
		for (CSVRecord record : csvParser) {
			if (record.getRecordNumber() > 1) {
				if (vibeCheckHurricane(record)) {
				    int enabled = Integer.valueOf(record.get(0));
				    if (enabled >= 0) {
				    	String name = record.get(1);
				    	float latitude = Float.valueOf(record.get(2));
				    	float longitude = Float.valueOf(record.get(3));
				    	float startSpeed = Float.valueOf(record.get(4));
				    	float endSpeed = Float.valueOf(record.get(5));
				    	float trajectory = Float.valueOf(record.get(6));
				    	
				    	System.out.println("ADDING HURRICANE: " + record.toString());
				    	
			    		Hurricane h = new Hurricane(name, new Position(latitude, longitude), startSpeed, endSpeed, trajectory);
			    		model.addHurricane(h);
				    }
				} else {
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean vibeCheckHurricane(CSVRecord record) {
		return record.size() == 7
				&& isInt(record.get(0))
				&& isFloat(record.get(2))
				&& isFloat(record.get(3))
				&& isFloat(record.get(4))
				&& isFloat(record.get(5))
				&& isFloat(record.get(6));
	}
	
	public static boolean isInt(String s) {
	    try {
	        double d = Integer.parseInt(s);
	    } catch (NumberFormatException e) {
	        return false;
	    } catch (NullPointerException e) {
	    	return false;
	    }
	    return true;
	}
	
	public static boolean isFloat(String s) {
	    try {
	        double d = Float.parseFloat(s);
	    } catch (NumberFormatException e) {
	        return false;
	    } catch (NullPointerException e) {
	    	return false;
	    }
	    return true;
	}
	
}
