package evac;

import java.util.List;

public class OutputModel {
	final List<Hurricane> hurricanes;
	final List<SolvedEdge> flows;
	
	public OutputModel() { // should be generated from NumberCruncher
		hurricanes = null;
		flows = null;
	}
}
