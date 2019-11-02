package evac;

public class SolvedEdge {
	final Edge edge;
	final int flow;
	
	public SolvedEdge(Edge edge, int flow) {
		this.edge = edge;
		this.flow = flow;
	}
}
