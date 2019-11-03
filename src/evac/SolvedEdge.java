package evac;

class SolvedEdge {
	final Edge edge;
	final int flow;
	
	SolvedEdge(Edge edge, int flow) {
		this.edge = edge;
		this.flow = flow;
	}
}
