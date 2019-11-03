package evac;

class Edge {
	final Node src;
	final Node dst;
	
	// TODO: list of positions that can be used to draw roads
	
	final double time;
	final double capacity;
	
	Edge(Node src, Node dst, double time, double capacity) {
		this.src = src;
		this.dst = dst;
		this.time = time;
		this.capacity = capacity;
	}
}
