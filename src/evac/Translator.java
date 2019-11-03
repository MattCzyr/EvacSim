package evac;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Instant;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class Translator {
	
	private InputModel model;
	
	private String mainFilename;
	private String nodesFilename;
	private String edgesFilename;
	private String hurricanesFilename;
	
	public Translator(InputModel model, String mainFilename, String nodesFilename, String edgesFilename, String hurricanesFilename) {
		this.model = model;
		this.mainFilename = mainFilename;
		this.nodesFilename = nodesFilename;
		this.edgesFilename = edgesFilename;
		this.hurricanesFilename = hurricanesFilename;
	}
	
	public boolean translateMain() {
		CSVParser csvParser = null;
		try {
			csvParser = CSVFormat.DEFAULT.parse(new FileReader(new File(mainFilename)));
		} catch (FileNotFoundException e1) {
			return false;
		} catch (IOException e) {
			return false;
		}
		for (CSVRecord record : csvParser) {
			if (record.getRecordNumber() > 1) {
			    int enabled = Integer.valueOf(record.get(0));
			    if (enabled >= 0) {
			    	String functionName = record.get(1);
			    	if (functionName.equals("setModelStart")) {
			    		String startTime = record.get(2);
			    		Instant instant = Instant.parse(startTime);
			    		Main.setModelStart(instant);
			    	} else if (functionName.equals("setModelEnd")) {
			    		String endTime = record.get(2);
			    		Instant instant = Instant.parse(endTime);
			    		Main.setModelEnd(instant);
			    	} else {
			    		return false;
			    	}
			    	System.out.println("EXECUTED FUNCTION : " + record.toString());
			    }
			}
		}
		return true;
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
				    	float startWindSpeed = Float.valueOf(record.get(4));
				    	float endWindSpeed = Float.valueOf(record.get(5));
				    	float velocity = Float.valueOf(record.get(6));
				    	float trajectory = Float.valueOf(record.get(7));
				    	
				    	System.out.println("ADDING HURRICANE: " + record.toString());
				    	
			    		Hurricane h = new Hurricane(name, new Position(latitude, longitude), new Velocity(trajectory, velocity), startWindSpeed, endWindSpeed);
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
		return record.size() == 8
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
