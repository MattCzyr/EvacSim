package evac;

class Node {
	final String name;
	final int population;
	final int capacity;
	final Position position;
	
	Node(String name, int population, int capacity, Position position) {
		this.name = name;
		this.population = population;
		this.capacity = capacity;
		this.position = position;
	}
}
