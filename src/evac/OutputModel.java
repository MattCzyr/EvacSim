package evac;

import java.util.List;

public class OutputModel {
	final List<Hurricane> hurricanes;
	final List<SolvedEdge> flows;
	final List<Node> nodes;
	
	public OutputModel(List<Hurricane> hurricanes, List<SolvedEdge> flows, List<Node> nodes) { // should be generated from NumberCruncher
		this.hurricanes = hurricanes;
		this.flows = flows;
		this.nodes = nodes;
	}
}
