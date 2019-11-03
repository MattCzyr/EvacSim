package evac;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class InputModel {
	final Map<String, Node> nodes;
	final List<Edge> edges;
	final List<Hurricane> hurricanes;
	
	public InputModel() { // probably loaded from a file
		nodes = new HashMap<String, Node>();
		edges = new ArrayList<Edge>();
		hurricanes = new ArrayList<Hurricane>();
	}
	
	public void addNode(Node n) {
		nodes.put(n.name, n);
	}
	
	public void addEdge(Edge e) {
		edges.add(e);
	}
	
	public void addHurricane(Hurricane h) {
		hurricanes.add(h);
	}
	
	
}

