package evac;

class Node {
	final int population;
	final int capacity;
	final Position position;
	
	Node(int population, int capacity, Position position) {
		this.population = population;
		this.capacity = capacity;
		this.position = position;
	}
}
