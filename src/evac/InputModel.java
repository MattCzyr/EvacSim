package evac;

import java.util.List;

class InputModel {
	final List<Node> nodes;
	final List<Edge> edges;
	final List<Hurricane> hurricanes;
	
	public InputModel() { // probably loaded from a file
		nodes = null;
		edges = null;
		hurricanes = null;
	}
}
