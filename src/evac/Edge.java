package evac;

class Edge {
	final Node src;
	final Node dst;
	
	// TODO: list of positions that can be used to draw roads
	
	final float time;
	final float capacity;
	
	Edge(Node src, Node dst, float time, float capacity) {
		this.src = src;
		this.dst = dst;
		this.time = time;
		this.capacity = capacity;
	}
}
